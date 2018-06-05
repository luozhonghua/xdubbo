package com.xdubbo.rmi.server2;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;


public class RMIServer {
    public static void main(String args[]) {
        try {

            RemoteInterface remoteObj2 = new RemoteImpl();// 创建远程对象
            Context namingContext = new InitialContext();// 初始化命名内容
            LocateRegistry.createRegistry(8892);// 在本地主机上创建和导出注册表实例，并在指定的端口上接受请求
            namingContext.rebind("rmi://localhost:8892/RemoteObj2", remoteObj2);// 注册对象，即把对象与一个名字绑定。
            System.out.println("服务器注册了一个远程对象");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}