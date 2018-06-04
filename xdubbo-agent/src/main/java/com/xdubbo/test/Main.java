package com.xdubbo.test;


import java.lang.management.ManagementFactory;

/**
 * Created by luozhonghua on 2018/6/3.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {


        while (true) {
            //替换前，打印出 firstName.lastName
            //被替换后，打印lastName.firstName
            System.out.println(new User("firstName","lastName").getName()+"  pid:"+getPID());
            Thread.sleep(5000);
        }
    }

    public static String getPID() {
        // 获取当前jvm的进程pid
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        int indexOf = pid.indexOf('@');
        if (indexOf > 0) {
            pid = pid.substring(0, indexOf);
        }

        return pid;
    }


}
