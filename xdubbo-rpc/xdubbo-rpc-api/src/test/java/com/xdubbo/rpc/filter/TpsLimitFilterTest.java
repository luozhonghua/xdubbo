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

import com.xdubbo.common.Constants;
import com.xdubbo.common.URL;
import com.xdubbo.rpc.Invocation;
import com.xdubbo.rpc.Invoker;
import com.xdubbo.rpc.RpcException;
import com.xdubbo.rpc.support.MockInvocation;
import com.xdubbo.rpc.support.MyInvoker;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TpsLimitFilterTest {

    private TpsLimitFilter filter = new TpsLimitFilter();

    @Test
    public void testWithoutCount() throws Exception {
        URL url = URL.valueOf("test://test");
        url = url.addParameter(Constants.INTERFACE_KEY,
                "com.xdubbo.rpc.file.TpsService");
        url = url.addParameter(Constants.TPS_LIMIT_RATE_KEY, 5);
        Invoker<TpsLimitFilterTest> invoker = new MyInvoker<TpsLimitFilterTest>(url);
        Invocation invocation = new MockInvocation();
        filter.invoke(invoker, invocation);
    }

    @Test(expected = RpcException.class)
    public void testFail() throws Exception {
        URL url = URL.valueOf("test://test");
        url = url.addParameter(Constants.INTERFACE_KEY,
                "com.xdubbo.rpc.file.TpsService");
        url = url.addParameter(Constants.TPS_LIMIT_RATE_KEY, 5);
        Invoker<TpsLimitFilterTest> invoker = new MyInvoker<TpsLimitFilterTest>(url);
        Invocation invocation = new MockInvocation();
        for (int i = 0; i < 10; i++) {
            try {
                filter.invoke(invoker, invocation);
            } catch (Exception e) {
                assertTrue(i >= 5);
                throw e;
            }
        }
    }

}