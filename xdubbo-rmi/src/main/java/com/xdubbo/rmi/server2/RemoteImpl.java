package com.xdubbo.rmi.server2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteImpl extends UnicastRemoteObject implements RemoteInterface {

	public RemoteImpl() throws RemoteException {
	}

	public String doSomething() throws RemoteException {
		return "OK ,You can do......";
	}

	public int Calculate(int num1, int num2) throws RemoteException {
		return (num1 + num2);
	}
}