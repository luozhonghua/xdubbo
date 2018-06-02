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
package com.xdubbo.rpc.protocol.dubbo.telnet;

import com.xdubbo.common.URL;
import com.xdubbo.common.utils.NetUtils;
import com.xdubbo.common.utils.ReflectUtils;
import com.xdubbo.remoting.Channel;
import com.xdubbo.remoting.RemotingException;
import com.xdubbo.remoting.telnet.TelnetHandler;
import com.xdubbo.rpc.Invocation;
import com.xdubbo.rpc.Invoker;
import com.xdubbo.rpc.RpcResult;
import com.xdubbo.rpc.protocol.dubbo.DubboProtocol;
import com.xdubbo.rpc.protocol.dubbo.support.DemoService;
import com.xdubbo.rpc.protocol.dubbo.support.ProtocolUtils;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * CountTelnetHandlerTest.java
 */
public class ListTelnetHandlerTest {

    private static TelnetHandler list = new ListTelnetHandler();
    private static String detailMethods;
    private static String methodsName;
    private Channel mockChannel;
    private Invoker<DemoService> mockInvoker;

    @BeforeClass
    public static void setUp() {
        StringBuilder buf = new StringBuilder();
        StringBuilder buf2 = new StringBuilder();
        Method[] methods = DemoService.class.getMethods();
        for (Method method : methods) {
            if (buf.length() > 0) {
                buf.append("\r\n");
            }
            if (buf2.length() > 0) {
                buf2.append("\r\n");
            }
            buf2.append(method.getName());
            buf.append(ReflectUtils.getName(method));
        }
        detailMethods = buf.toString();
        methodsName = buf2.toString();

        ProtocolUtils.closeAll();
    }

    @After
    public void after() {
        ProtocolUtils.closeAll();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testListDetailService() throws RemotingException {
        mockInvoker = EasyMock.createMock(Invoker.class);
        EasyMock.expect(mockInvoker.getInterface()).andReturn(DemoService.class).anyTimes();
        EasyMock.expect(mockInvoker.getUrl()).andReturn(URL.valueOf("dubbo://127.0.0.1:20885/demo")).anyTimes();
        EasyMock.expect(mockInvoker.invoke((Invocation) EasyMock.anyObject())).andReturn(new RpcResult("ok")).anyTimes();
        mockChannel = EasyMock.createMock(Channel.class);
        EasyMock.expect(mockChannel.getAttribute("telnet.service")).andReturn("com.xdubbo.rpc.protocol.dubbo.support.DemoService").anyTimes();
        EasyMock.replay(mockChannel, mockInvoker);
        DubboProtocol.getDubboProtocol().export(mockInvoker);
        String result = list.telnet(mockChannel, "-l DemoService");
        assertEquals(detailMethods, result);
        EasyMock.reset(mockChannel, mockInvoker);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testListService() throws RemotingException {
        mockInvoker = EasyMock.createMock(Invoker.class);
        EasyMock.expect(mockInvoker.getInterface()).andReturn(DemoService.class).anyTimes();
        EasyMock.expect(mockInvoker.getUrl()).andReturn(URL.valueOf("dubbo://127.0.0.1:20885/demo")).anyTimes();
        EasyMock.expect(mockInvoker.invoke((Invocation) EasyMock.anyObject())).andReturn(new RpcResult("ok")).anyTimes();
        mockChannel = EasyMock.createMock(Channel.class);
        EasyMock.expect(mockChannel.getAttribute("telnet.service")).andReturn("com.xdubbo.rpc.protocol.dubbo.support.DemoService").anyTimes();
        EasyMock.replay(mockChannel, mockInvoker);
        DubboProtocol.getDubboProtocol().export(mockInvoker);
        String result = list.telnet(mockChannel, "DemoService");
        assertEquals(methodsName, result);
        EasyMock.reset(mockChannel, mockInvoker);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testList() throws RemotingException {
        mockInvoker = EasyMock.createMock(Invoker.class);
        EasyMock.expect(mockInvoker.getInterface()).andReturn(DemoService.class).anyTimes();
        EasyMock.expect(mockInvoker.getUrl()).andReturn(URL.valueOf("dubbo://127.0.0.1:20885/demo")).anyTimes();
        EasyMock.expect(mockInvoker.invoke((Invocation) EasyMock.anyObject())).andReturn(new RpcResult("ok")).anyTimes();
        mockChannel = EasyMock.createMock(Channel.class);
        EasyMock.expect(mockChannel.getAttribute("telnet.service")).andReturn(null).anyTimes();
        EasyMock.replay(mockChannel, mockInvoker);
        DubboProtocol.getDubboProtocol().export(mockInvoker);
        String result = list.telnet(mockChannel, "");
        assertEquals("com.xdubbo.rpc.protocol.dubbo.support.DemoService", result);
        EasyMock.reset(mockChannel);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testListDetail() throws RemotingException {
        int port = NetUtils.getAvailablePort();
        mockInvoker = EasyMock.createMock(Invoker.class);
        EasyMock.expect(mockInvoker.getInterface()).andReturn(DemoService.class).anyTimes();
        EasyMock.expect(mockInvoker.getUrl()).andReturn(URL.valueOf("dubbo://127.0.0.1:" + port + "/demo")).anyTimes();
        EasyMock.expect(mockInvoker.invoke((Invocation) EasyMock.anyObject())).andReturn(new RpcResult("ok")).anyTimes();
        mockChannel = EasyMock.createMock(Channel.class);
        EasyMock.expect(mockChannel.getAttribute("telnet.service")).andReturn(null).anyTimes();
        EasyMock.replay(mockChannel, mockInvoker);
        DubboProtocol.getDubboProtocol().export(mockInvoker);
        String result = list.telnet(mockChannel, "-l");
        assertEquals("com.xdubbo.rpc.protocol.dubbo.support.DemoService -> dubbo://127.0.0.1:" + port + "/demo", result);
        EasyMock.reset(mockChannel);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testListDefault() throws RemotingException {
        mockInvoker = EasyMock.createMock(Invoker.class);
        EasyMock.expect(mockInvoker.getInterface()).andReturn(DemoService.class).anyTimes();
        EasyMock.expect(mockInvoker.getUrl()).andReturn(URL.valueOf("dubbo://127.0.0.1:20885/demo")).anyTimes();
        EasyMock.expect(mockInvoker.invoke((Invocation) EasyMock.anyObject())).andReturn(new RpcResult("ok")).anyTimes();
        mockChannel = EasyMock.createMock(Channel.class);
        EasyMock.expect(mockChannel.getAttribute("telnet.service")).andReturn("com.xdubbo.rpc.protocol.dubbo.support.DemoService").anyTimes();
        EasyMock.replay(mockChannel, mockInvoker);
        DubboProtocol.getDubboProtocol().export(mockInvoker);
        String result = list.telnet(mockChannel, "");
        assertEquals("Use default service com.xdubbo.rpc.protocol.dubbo.support.DemoService.\r\n\r\n"
                + methodsName, result);
        EasyMock.reset(mockChannel);
    }

    @Test
    public void testInvaildMessage() throws RemotingException {
        mockChannel = EasyMock.createMock(Channel.class);
        EasyMock.expect(mockChannel.getAttribute("telnet.service")).andReturn(null).anyTimes();
        EasyMock.replay(mockChannel);
        String result = list.telnet(mockChannel, "xx");
        assertEquals("No such service xx", result);
        EasyMock.reset(mockChannel);
    }
}