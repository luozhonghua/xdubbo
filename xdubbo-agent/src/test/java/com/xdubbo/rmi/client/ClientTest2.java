package com.xdubbo.rmi.client;

import com.xdubbo.rmi.server2.RemoteInterface;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Created by luozhonghua on 2018/6/4.
 */
public class ClientTest2 {
    public static void main(String args[]) {
        try {
            Context namingContext = new InitialContext();// 初始化命名内容
            RemoteInterface RmObj2 = (RemoteInterface) namingContext
                    .lookup("rmi://localhost:8892/RemoteObj2");//获得远程对象的存根对象
            System.out.println(RmObj2.doSomething());//通过远程对象，调用doSomething方法
            System.out.println("远程服务器计算结果为：" + RmObj2.Calculate(90, 2));
        } catch (Exception e) {
        }
    }
}
