package com.xdubbo.rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

//在JDK1.3版本或更低的版本，需要使用java.rmi.Naming来注册远程对象
//创建RMI注册表，启动RMI服务，并将远程对象注册到RMI注册表中。
public class RMIServer {


    public static void main(String args[]) throws java.rmi.AlreadyBoundException {
        try {
            // 创建一个远程对象RemoteObj，实质上隐含了是生成stub和skeleton,并返回stub代理引用
            RemoteInterface remoteObj = new RemoteImpl();

            // 本地创建并启动RMI Service，被创建的Registry服务将在指定的端口,侦听请求
            // Java默认端口是1099，缺少注册表创建，则无法绑定对象到远程注册表上
            LocateRegistry.createRegistry(8891);

            // 把远程对象注册到RMI注册服务器上，并命名为RemoteObj（名字可自定义，客户端要对应）
            // 绑定的URL标准格式为：rmi://host:port/name(其中协议名可以省略，下面两种写法都是正确的）
            Naming.rebind("rmi://192.168.176.136:8891/RemoteObj", remoteObj);// 将stub代理绑定到Registry服务的URL上
            // Naming.bind("//localhost:8880/RemoteObj",remoteObj);

            System.out.println(">>>>>INFO:远程IHello对象绑定成功！");
        } catch (RemoteException e) {
            System.out.println("创建远程对象发生异常！");

            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("发生URL畸形异常！");
            e.printStackTrace();
        }
    }
}