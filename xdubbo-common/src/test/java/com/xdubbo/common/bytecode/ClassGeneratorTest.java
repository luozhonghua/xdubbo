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
package com.xdubbo.common.bytecode;

import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

interface Builder<T> {
    T getName(Bean bean);

    void setName(Bean bean, T name);
}

public class ClassGeneratorTest extends TestCase {


    /**
     * 动态注入在原有类增加属性FNAME和set法,并为新增属性赋值
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void testMain() throws Exception {
        System.out.println(Thread.currentThread().getContextClassLoader());

        Bean b = new Bean();
        Field fname = null, fs[] = Bean.class.getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true); //设置可访问
            if (f.getName().equals("name"))
                System.out.println(f);
                fname = f;//private java.lang.String com.xdubbo.common.bytecode.Bean.name
        }

        ClassGenerator cg = ClassGenerator.newInstance();

        cg.setClassName(Bean.class.getName() + "$Builder");
        cg.addInterface(Builder.class);

        cg.addField("public static java.lang.reflect.Field FNAME;");

        cg.addMethod("public Object getName(" + Bean.class.getName() + " o){ boolean[][][] bs = new boolean[0][][]; return (String)FNAME.get($1); }");
        cg.addMethod("public void setName(" + Bean.class.getName() + " o, Object name){ FNAME.set($1, $2); }");

        cg.addDefaultConstructor();
        Class<?> cl = cg.toClass();
        System.out.println(cl.getField("FNAME"));  //public static java.lang.reflect.Field com.xdubbo.common.bytecode.Bean$Builder.FNAME
        cl.getField("FNAME").set(null, fname);


        System.out.println(Bean.class.getSimpleName());
        System.out.println(Bean.class.getName());
        System.out.println(cl.getName());
        Builder<String> builder = (Builder<String>) cl.newInstance();
        System.out.println(b.getName());
        builder.setName(b, "ok");
        System.out.println(b.getName());


        System.out.println("--------- ---属性列表------------");
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getType()+"\t"+field.getName());
        }

        System.out.println("--------- ---方法列表------------");
        Method[] methods = cl.getMethods();
        for (Method method: methods){
            System.out.println(method.getReturnType()+"\t"+method.getName()+"\t"+ Arrays.toString(method.getParameterTypes()));
        }

    }
}

class Bean {
    int age = 30;

    private String name = "qianlei";

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}