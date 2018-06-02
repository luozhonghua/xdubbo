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
package com.xdubbo.rpc.filter;

import com.xdubbo.common.URL;
import com.xdubbo.common.utils.LogUtil;
import com.xdubbo.rpc.Filter;
import com.xdubbo.rpc.Invocation;
import com.xdubbo.rpc.Invoker;
import com.xdubbo.rpc.support.MockInvocation;
import com.xdubbo.rpc.support.MyInvoker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * AccessLogFilterTest.java
 */
public class AccessLogFilterTest {

    Filter accessLogFilter = new AccessLogFilter();

    // Test filter won't throw an exception
    @Test
    public void testInvokeException() {
        Invoker<AccessLogFilterTest> invoker = new MyInvoker<AccessLogFilterTest>(null);
        Invocation invocation = new MockInvocation();
        LogUtil.start();
        accessLogFilter.invoke(invoker, invocation);
        assertEquals(1, LogUtil.findMessage("Exception in AcessLogFilter of service"));
        LogUtil.stop();
    }

    // TODO how to assert thread action
    @Test
    public void testDefault() {
        URL url = URL.valueOf("test://test:11/test?accesslog=true&group=dubbo&version=1.1");
        Invoker<AccessLogFilterTest> invoker = new MyInvoker<AccessLogFilterTest>(url);
        Invocation invocation = new MockInvocation();
        accessLogFilter.invoke(invoker, invocation);
    }

    @Test
    public void testCustom() {
        URL url = URL.valueOf("test://test:11/test?accesslog=alibaba");
        Invoker<AccessLogFilterTest> invoker = new MyInvoker<AccessLogFilterTest>(url);
        Invocation invocation = new MockInvocation();
        accessLogFilter.invoke(invoker, invocation);
    }

}