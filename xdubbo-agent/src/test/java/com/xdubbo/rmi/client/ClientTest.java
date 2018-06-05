package com.xdubbo.rmi.client;


import com.xdubbo.rmi.server.RemoteInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by luozhonghua on 2018/6/4.
 */
//在JDK1.3版本或更低的版本，需要使用java.rmi.Naming来注册远程对象
public class ClientTest {
    public static void main(String args[]) {
        try {
            // 在RMI服务注册表中查找名称为RemoteObj的对象，并调用其上的方法
            // 客户端通过命名服务Naming获得指向远程对象的远程引用
           /// System.setProperty("java.rmi.server.hostname","192.168.176.136");
            //LocateRegistry.createRegistry(8891);
            RemoteInterface RmObj = (RemoteInterface) Naming
                    .lookup("rmi://192.168.176.136:8891/RemoteObj");
            System.out.println(RmObj.doSomething());
            System.out.println("远程服务器计算结果为：" + RmObj.Calculate(90, 1));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}