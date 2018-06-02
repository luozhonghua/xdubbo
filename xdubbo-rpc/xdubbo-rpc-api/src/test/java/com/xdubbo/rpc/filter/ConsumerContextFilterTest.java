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
import com.xdubbo.common.utils.NetUtils;
import com.xdubbo.rpc.Filter;
import com.xdubbo.rpc.Invocation;
import com.xdubbo.rpc.Invoker;
import com.xdubbo.rpc.RpcContext;
import com.xdubbo.rpc.support.DemoService;
import com.xdubbo.rpc.support.MockInvocation;
import com.xdubbo.rpc.support.MyInvoker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * ConsumerContextFilterTest.java
 */
public class ConsumerContextFilterTest {
    Filter consumerContextFilter = new ConsumerContextFilter();

    @Test
    public void testSetContext() {
        URL url = URL.valueOf("test://test:11/test?group=dubbo&version=1.1");
        Invoker<DemoService> invoker = new MyInvoker<DemoService>(url);
        Invocation invocation = new MockInvocation();
        consumerContextFilter.invoke(invoker, invocation);
        assertEquals(invoker, RpcContext.getContext().getInvoker());
        assertEquals(invocation, RpcContext.getContext().getInvocation());
        assertEquals(NetUtils.getLocalHost() + ":0", RpcContext.getContext().getLocalAddressString());
        assertEquals("test:11", RpcContext.getContext().getRemoteAddressString());

    }
}