/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xdubbo.monitor.support;

import com.xdubbo.common.Constants;
import com.xdubbo.common.URL;
import com.xdubbo.common.utils.NetUtils;
import com.xdubbo.monitor.Monitor;
import com.xdubbo.monitor.MonitorFactory;
import com.xdubbo.monitor.MonitorService;
import com.xdubbo.rpc.Invocation;
import com.xdubbo.rpc.Invoker;
import com.xdubbo.rpc.Result;
import com.xdubbo.rpc.RpcContext;
import com.xdubbo.rpc.RpcException;
import com.xdubbo.rpc.RpcInvocation;

import junit.framework.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * MonitorFilterTest
 */
public class MonitorFilterTest {

    private volatile URL lastStatistics;

    private volatile Invocation lastInvocation;

    private final Invoker<MonitorService> serviceInvoker = new Invoker<MonitorService>() {
        public Class<MonitorService> getInterface() {
            return MonitorService.class;
        }

        public URL getUrl() {
            try {
                return URL.valueOf("dubbo://" + NetUtils.getLocalHost() + ":20880?" + Constants.APPLICATION_KEY + "=abc&" + Constants.SIDE_KEY + "=" + Constants.CONSUMER_SIDE + "&" + Constants.MONITOR_KEY + "=" + URLEncoder.encode("dubbo://" + NetUtils.getLocalHost() + ":7070", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }

        public boolean isAvailable() {
            return false;
        }

        public Result invoke(Invocation invocation) throws RpcException {
            lastInvocation = invocation;
            return null;
        }

        public void destroy() {
        }
    };

    private MonitorFactory monitorFactory = new MonitorFactory() {
        public Monitor getMonitor(final URL url) {
            return new Monitor() {
                public URL getUrl() {
                    return url;
                }

                public boolean isAvailable() {
                    return true;
                }

                public void destroy() {
                }

                public void collect(URL statistics) {
                    MonitorFilterTest.this.lastStatistics = statistics;
                }

                public List<URL> lookup(URL query) {
                    return Arrays.asList(MonitorFilterTest.this.lastStatistics);
                }
            };
        }
    };

    @Test
    public void testFilter() throws Exception {
        MonitorFilter monitorFilter = new MonitorFilter();
        monitorFilter.setMonitorFactory(monitorFactory);
        Invocation invocation = new RpcInvocation("aaa", new Class<?>[0], new Object[0]);
        RpcContext.getContext().setRemoteAddress(NetUtils.getLocalHost(), 20880).setLocalAddress(NetUtils.getLocalHost(), 2345);
        monitorFilter.invoke(serviceInvoker, invocation);
        while (lastStatistics == null) {
            Thread.sleep(10);
        }
        Assert.assertEquals("abc", lastStatistics.getParameter(MonitorService.APPLICATION));
        Assert.assertEquals(MonitorService.class.getName(), lastStatistics.getParameter(MonitorService.INTERFACE));
        Assert.assertEquals("aaa", lastStatistics.getParameter(MonitorService.METHOD));
        Assert.assertEquals(NetUtils.getLocalHost() + ":20880", lastStatistics.getParameter(MonitorService.PROVIDER));
        Assert.assertEquals(NetUtils.getLocalHost(), lastStatistics.getAddress());
        Assert.assertEquals(null, lastStatistics.getParameter(MonitorService.CONSUMER));
        Assert.assertEquals(1, lastStatistics.getParameter(MonitorService.SUCCESS, 0));
        Assert.assertEquals(0, lastStatistics.getParameter(MonitorService.FAILURE, 0));
        Assert.assertEquals(1, lastStatistics.getParameter(MonitorService.CONCURRENT, 0));
        Assert.assertEquals(invocation, lastInvocation);
    }

    @Test
    public void testGenericFilter() throws Exception {
        MonitorFilter monitorFilter = new MonitorFilter();
        monitorFilter.setMonitorFactory(monitorFactory);
        Invocation invocation = new RpcInvocation("$invoke", new Class<?>[]{String.class, String[].class, Object[].class}, new Object[]{"xxx", new String[]{}, new Object[]{}});
        RpcContext.getContext().setRemoteAddress(NetUtils.getLocalHost(), 20880).setLocalAddress(NetUtils.getLocalHost(), 2345);
        monitorFilter.invoke(serviceInvoker, invocation);
        while (lastStatistics == null) {
            Thread.sleep(10);
        }
        Assert.assertEquals("abc", lastStatistics.getParameter(MonitorService.APPLICATION));
        Assert.assertEquals(MonitorService.class.getName(), lastStatistics.getParameter(MonitorService.INTERFACE));
        Assert.assertEquals("xxx", lastStatistics.getParameter(MonitorService.METHOD));
        Assert.assertEquals(NetUtils.getLocalHost() + ":20880", lastStatistics.getParameter(MonitorService.PROVIDER));
        Assert.assertEquals(NetUtils.getLocalHost(), lastStatistics.getAddress());
        Assert.assertEquals(null, lastStatistics.getParameter(MonitorService.CONSUMER));
        Assert.assertEquals(1, lastStatistics.getParameter(MonitorService.SUCCESS, 0));
        Assert.assertEquals(0, lastStatistics.getParameter(MonitorService.FAILURE, 0));
        Assert.assertEquals(1, lastStatistics.getParameter(MonitorService.CONCURRENT, 0));
        Assert.assertEquals(invocation, lastInvocation);
    }

}