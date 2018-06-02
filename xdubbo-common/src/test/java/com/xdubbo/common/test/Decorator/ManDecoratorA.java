package com.xdubbo.common.test.Decorator;

/**
 * Created by luozhonghua on 2018/6/1.
 * ManDecoratorA 具体装饰者  具体的装饰对象，给内部持有的具体被装饰对象，增加具体的职责
 */
public class ManDecoratorA extends Decorator{
    public void eat() {
        System.out.println("-----装饰增强 ManDecoratorA类");
        super.eat();
        //reEat();

    }

    public void reEat() {
        System.out.println("再吃一顿饭");
    }
}
