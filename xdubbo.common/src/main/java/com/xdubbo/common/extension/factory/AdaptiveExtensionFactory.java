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
package com.xdubbo.common.extension.factory;

import com.xdubbo.common.extension.Adaptive;
import com.xdubbo.common.extension.ExtensionFactory;
import com.xdubbo.common.extension.ExtensionLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自适应扩展工厂
 * AdaptiveExtensionFactory
 *
 * 默认实现
 * AdaptiveExtensionFactotry被@Adaptive注解注释，也就是它就是ExtensionFactory对应的自适应扩展实现(每个扩展点最多只能有一个自适应实现，如果所有实现中没有被@Adaptive注释的，那么dubbo会动态生成一个自适应实现类)，也就是说，所有对ExtensionFactory调用的地方，实际上调用的都是AdpativeExtensionFactory
 */
@Adaptive
public class AdaptiveExtensionFactory implements ExtensionFactory {

    private final List<ExtensionFactory> factories;

    public AdaptiveExtensionFactory() {
        ExtensionLoader<ExtensionFactory> loader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
        List<ExtensionFactory> list = new ArrayList<ExtensionFactory>();
        for (String name : loader.getSupportedExtensions()) { // 加载所有的工厂扩展,将所有ExtensionFactory实现保存起来
            list.add(loader.getExtension(name));
        }
        factories = Collections.unmodifiableList(list);//unmodifiableList线程安全和充分利用内存
    }

    public <T> T getExtension(Class<T> type, String name) {
        // 依次遍历各个ExtensionFactory实现的getExtension方法，一旦获取到Extension即返回
        // 如果遍历完所有的ExtensionFactory实现均无法找到Extension,则返回null
        for (ExtensionFactory factory : factories) {
            T extension = factory.getExtension(type, name); //适配扩展获取接口
            if (extension != null) {
                return extension;
            }
        }
        return null;
    }

}
