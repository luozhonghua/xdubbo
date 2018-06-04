package com.xdubbo.test;

/**
 * Created by luozhonghua on 2018/6/3.
 */
public class User {
    private String firstName;

    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + "." + lastName;
    }
}
