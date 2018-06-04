package com.xdubbo.test;

/**
 * Created by luozhonghua on 2018/6/4.
 */
public class User {
    private String firstName;

    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //打印出来的位置变了
    public String getName() {
        System.out.println("位置颠倒!");
        return lastName + "." + firstName;
    }
}
