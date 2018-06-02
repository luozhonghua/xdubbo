package com.xdubbo.common.test.Decorator;


import com.xdubbo.common.model.Person;

/**
 * 装饰者抽象类   维持一个指向ComponentPerson实例的引用,并定义一个与ComponentPerson接口一致的接口
 * Created by luozhonghua on 2018/6/1.
 */
public abstract class Decorator implements ComponentPerson {

    protected ComponentPerson componentPerson;

    public void setComponentPerson(ComponentPerson componentPerson) {
        this.componentPerson = componentPerson;
    }

    @Override
    public void eat() {
        System.out.println("-----装饰增强 Decorator-----eat()");
        componentPerson.eat();

    }



}
