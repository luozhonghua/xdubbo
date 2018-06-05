package com.xdubbo.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//实现远程接口RemoteInterface，并继承UnicastRemoteObject
//注意RemoteObject这个类，实现了Serializable, Remote这两个接口
public class RemoteImpl extends UnicastRemoteObject implements RemoteInterface {

	// 因为UnicastRemoteObject的构造方法抛出了RemoteException异常，因此这里默认的构造方法必须写，
	// 必须声明抛出RemoteException异常
	public RemoteImpl() throws RemoteException {
	}


	public String doSomething() throws RemoteException {
		return "OK ,You can do......";
	}


	public int Calculate(int num1, int num2) throws RemoteException {
		return (num1 + num2);
	}
}