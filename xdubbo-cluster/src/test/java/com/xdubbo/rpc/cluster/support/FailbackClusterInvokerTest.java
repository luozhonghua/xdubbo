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
package com.xdubbo.rpc.cluster.support;

import com.xdubbo.common.URL;
import com.xdubbo.common.utils.LogUtil;
import com.xdubbo.rpc.Invoker;
import com.xdubbo.rpc.Result;
import com.xdubbo.rpc.RpcContext;
import com.xdubbo.rpc.RpcInvocation;
import com.xdubbo.rpc.RpcResult;
import com.xdubbo.rpc.cluster.Directory;

import junit.framework.Assert;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * FailbackClusterInvokerTest
 *
 */
@SuppressWarnings("unchecked")
public class FailbackClusterInvokerTest {

    List<Invoker<FailbackClusterInvokerTest>> invokers = new ArrayList<Invoker<FailbackClusterInvokerTest>>();
    URL url = URL.valueOf("test://test:11/test");
    Invoker<FailbackClusterInvokerTest> invoker = EasyMock.createMock(Invoker.class);
    RpcInvocation invocation = new RpcInvocation();
    Directory<FailbackClusterInvokerTest> dic;
    Result result = new RpcResult();

    /**
     * @throws java.lang.Exception
     */

    @Before
    public void setUp() throws Exception {

        dic = EasyMock.createMock(Directory.class);
        EasyMock.expect(dic.getUrl()).andReturn(url).anyTimes();
        EasyMock.expect(dic.list(invocation)).andReturn(invokers).anyTimes();
        EasyMock.expect(dic.getInterface()).andReturn(FailbackClusterInvokerTest.class).anyTimes();

        invocation.setMethodName("method1");
        EasyMock.replay(dic);

        invokers.add(invoker);
    }

    @After
    public void tearDown() {
        EasyMock.verify(invoker, dic);

    }

    private void resetInvokerToException() {
        EasyMock.reset(invoker);
        EasyMock.expect(invoker.invoke(invocation)).andThrow(new RuntimeException()).anyTimes();
        EasyMock.expect(invoker.getUrl()).andReturn(url).anyTimes();
        EasyMock.expect(invoker.getInterface()).andReturn(FailbackClusterInvokerTest.class).anyTimes();
        EasyMock.replay(invoker);
    }

    private void resetInvokerToNoException() {
        EasyMock.reset(invoker);
        EasyMock.expect(invoker.invoke(invocation)).andReturn(result).anyTimes();
        EasyMock.expect(invoker.getUrl()).andReturn(url).anyTimes();
        EasyMock.expect(invoker.getInterface()).andReturn(FailbackClusterInvokerTest.class).anyTimes();
        EasyMock.replay(invoker);
    }

    @Test
    public void testInvokeExceptoin() {
        resetInvokerToException();
        FailbackClusterInvoker<FailbackClusterInvokerTest> invoker = new FailbackClusterInvoker<FailbackClusterInvokerTest>(
                dic);
        invoker.invoke(invocation);
        Assert.assertNull(RpcContext.getContext().getInvoker());
    }

    @Test()
    public void testInvokeNoExceptoin() {

        resetInvokerToNoException();

        FailbackClusterInvoker<FailbackClusterInvokerTest> invoker = new FailbackClusterInvoker<FailbackClusterInvokerTest>(
                dic);
        Result ret = invoker.invoke(invocation);
        Assert.assertSame(result, ret);
    }

    @Test()
    public void testNoInvoke() {
        dic = EasyMock.createMock(Directory.class);

        EasyMock.expect(dic.getUrl()).andReturn(url).anyTimes();
        EasyMock.expect(dic.list(invocation)).andReturn(null).anyTimes();
        EasyMock.expect(dic.getInterface()).andReturn(FailbackClusterInvokerTest.class).anyTimes();

        invocation.setMethodName("method1");
        EasyMock.replay(dic);

        invokers.add(invoker);

        resetInvokerToNoException();

        FailbackClusterInvoker<FailbackClusterInvokerTest> invoker = new FailbackClusterInvoker<FailbackClusterInvokerTest>(
                dic);
        LogUtil.start();
        invoker.invoke(invocation);
        assertEquals(1, LogUtil.findMessage("Failback to invoke"));
        LogUtil.stop();
    }

    @Test()
    public void testRetryFailed() {

        resetInvokerToException();

        FailbackClusterInvoker<FailbackClusterInvokerTest> invoker = new FailbackClusterInvoker<FailbackClusterInvokerTest>(
                dic);
        invoker.invoke(invocation);
        Assert.assertNull(RpcContext.getContext().getInvoker());
        invoker.retryFailed();// when retry the invoker which get from failed map already is not the mocked invoker,so
        // it can be invoke successfully
    }
}