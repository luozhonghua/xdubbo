package com.xdubbo.common.test.Decorator;

/**
 * Created by luozhonghua on 2018/6/1.
 *ComponentMan  具体被装饰对象 定义一个对象,  可以给这个对象添加一些职责。
 */
public class ComponentMan implements ComponentPerson {
    @Override
    public void eat() {

        System.out.println("-----base ComponentMan    eat()");
    }
}
