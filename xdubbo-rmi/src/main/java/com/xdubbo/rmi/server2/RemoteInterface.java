package com.xdubbo.rmi.server2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {

	public String doSomething() throws RemoteException;

	public int Calculate(int num1, int num2) throws RemoteException;
}