package com.xdubbo.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

//声明一个远程接口RemoteInterface，该接口必须继承Remote接口
//接口中需要被远程调用的方法，必须抛出RemoteException异常
public interface RemoteInterface extends Remote {


    public String doSomething() throws RemoteException;

    // 声明一个计算方法Calculate
    public int Calculate(int num1, int num2) throws RemoteException;
}