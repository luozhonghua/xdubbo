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

import com.xdubbo.common.extension.ExtensionFactory;
import com.xdubbo.common.extension.ExtensionLoader;
import com.xdubbo.common.extension.SPI;

/**
 * @spi扩展工厂实现
 * SpiExtensionFactory
 * 负责加载扩展点@SPI的自适应扩展点对象
 */
public class SpiExtensionFactory implements ExtensionFactory {

    /**
     *
     * @param type object type.  标注@SPI扩展接口
     * @param name object name.  扩展对象
     * @param <T>
     * @return @SPI扩展点的返回自适应扩展实例,没标注的返回null
     */
    public <T> T getExtension(Class<T> type, String name) {
        if (type.isInterface() && type.isAnnotationPresent(SPI.class)) {
            ExtensionLoader<T> loader = ExtensionLoader.getExtensionLoader(type);
            if (loader.getSupportedExtensions().size() > 0) {
                return loader.getAdaptiveExtension();
            }
        }
        return null;
    }

}
