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
package com.xdubbo.config.support;

import com.xdubbo.common.URL;
import com.xdubbo.rpc.Exporter;
import com.xdubbo.rpc.Invocation;
import com.xdubbo.rpc.Invoker;
import com.xdubbo.rpc.Protocol;
import com.xdubbo.rpc.Result;
import com.xdubbo.rpc.RpcException;

public class MockProtocol implements Protocol {

    /* (non-Javadoc)
     * @see com.xdubbo.rpc.Protocol#getDefaultPort()
     */
    public int getDefaultPort() {

        return 0;
    }

    /* (non-Javadoc)
     * @see com.xdubbo.rpc.Protocol#export(com.xdubbo.rpc.Invoker)
     */
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.xdubbo.rpc.Protocol#refer(java.lang.Class, com.xdubbo.common.URL)
     */
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {

        final URL u = url;

        return new Invoker<T>() {
            public Class<T> getInterface() {
                return null;
            }

            public URL getUrl() {
                return u;
            }

            public boolean isAvailable() {
                return true;
            }

            public Result invoke(Invocation invocation) throws RpcException {
                return null;
            }

            public void destroy() {

            }
        };
    }

    /* (non-Javadoc)
     * @see com.xdubbo.rpc.Protocol#destroy()
     */
    public void destroy() {

    }

}