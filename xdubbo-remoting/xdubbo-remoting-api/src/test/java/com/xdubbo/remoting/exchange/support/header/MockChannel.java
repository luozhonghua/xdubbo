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

package com.xdubbo.remoting.exchange.support.header;

import com.xdubbo.common.URL;
import com.xdubbo.remoting.Channel;
import com.xdubbo.remoting.ChannelHandler;
import com.xdubbo.remoting.RemotingException;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockChannel implements Channel {

    private Map<String, Object> attributes = new HashMap<String, Object>();

    private volatile boolean closed = false;
    private volatile boolean closing = false;
    private List<Object> sentObjects = new ArrayList<Object>();

    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    public boolean isConnected() {
        return false;
    }

    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public URL getUrl() {
        return null;
    }

    public ChannelHandler getChannelHandler() {
        return null;
    }

    public InetSocketAddress getLocalAddress() {
        return null;
    }

    public void send(Object message) throws RemotingException {
        sentObjects.add(message);
    }

    public void send(Object message, boolean sent) throws RemotingException {
        sentObjects.add(message);
    }

    public void close() {
        closed = true;
    }

    public void close(int timeout) {
        closed = true;
    }

    @Override
    public void startClose() {
        closing = true;
    }

    public boolean isClosed() {
        return closed;
    }

    public List<Object> getSentObjects() {
        return Collections.unmodifiableList(sentObjects);
    }
}