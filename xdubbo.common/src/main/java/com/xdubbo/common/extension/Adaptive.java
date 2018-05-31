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
package com.xdubbo.common.extension;


import java.lang.annotation.*;

/**
 *@Adaptive 自适应扩展点,注解在类型和方法上
 *@Adaptive 注解在类上 , 这个类就是缺省的适配扩展
 *
 *@Adaptive 注解在扩展点 Interface 的方法上时 ,dubbo 动态的生成一个这个扩展点的适配扩展类（生成代码,动态编译实例化 Class ）,
 *  名称为 扩展点 Interface 的简单类名 + $Adaptive
 *  例如 ： ProxyFactory$Adpative
 *  这么做的目的是为了在运行时去适配不同的扩展实例 ,
 *  在运行时通过传入的 URL 类型的参数或者内部含有获取 URL 方法的参数 ,
 *  从 URL 中获取到要使用的扩展类的名称 ，再去根据名称加载对应的扩展实例 ,
 *  用这个扩展实例对象调用相同的方法 .如果运行时没有适配到运行的扩展实例 , 那么就使用 @SPI 注解缺省指定的扩展.通过这种方式就实现了运行时去适配到对应的扩展
 *
 *
 * Provide helpful information for {@link ExtensionLoader} to inject dependency extension instance.
 *
 * @see ExtensionLoader
 * @see URL
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Adaptive {
    /**
     * Decide which target extension to be injected. The name of the target extension is decided by the parameter passed
     * in the URL, and the parameter names are given by this method.
     * <p>
     * If the specified parameters are not found from {@link URL}, then the default extension will be used for
     * dependency injection (specified in its interface's {@link SPI}).
     * <p>
     * For examples, given <code>String[] {"key1", "key2"}</code>:
     * <ol>
     * <li>find parameter 'key1' in URL, use its value as the extension's name</li>
     * <li>try 'key2' for extension's name if 'key1' is not found (or its value is empty) in URL</li>
     * <li>use default extension if 'key2' doesn't appear either</li>
     * <li>otherwise, throw {@link IllegalStateException}</li>
     * </ol>
     * If default extension's name is not give on interface's {@link SPI}, then a name is generated from interface's
     * class name with the rule: divide classname from capital char into several parts, and separate the parts with
     * dot '.', for example: for {@code com.xdubbo.xxx.YyyInvokerWrapper}, its default name is
     * <code>String[] {"yyy.invoker.wrapper"}</code>. This name will be used to search for parameter from URL.
     *
     * @return parameter key names in URL
     */
    String[] value() default {};

}