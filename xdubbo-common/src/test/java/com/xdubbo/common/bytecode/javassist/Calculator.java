package com.xdubbo.common.bytecode.javassist;

/**
 * Created by luozhonghua on 2018/6/1.
 */
public class Calculator {

    public void getSum(long n) {
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
        System.out.println("n="+n+",sum="+sum);
    }

}
