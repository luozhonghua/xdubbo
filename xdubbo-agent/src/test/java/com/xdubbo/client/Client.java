package com.xdubbo.client;

import java.lang.reflect.Field;
import com.sun.tools.attach.*;

/**
 * Created by luozhonghua on 2018/6/3.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        //注意，是jre的bin目录，不是jdk的bin目录
        //VirtualMachine need the attach.dll in the jre of the JDK.
        System.setProperty("java.library.path", "C:\\Program Files\\Java\\jre1.8.0_121\\bin");
        Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
        System.out.println(fieldSysPath.getClass().getName());
        fieldSysPath.setAccessible(true);
        fieldSysPath.set(null, null);
        //目标进程的进程id -- 记得改成正确的数字
        VirtualMachine vm = VirtualMachine.attach("4856");

        //参数1：代理jar的位置
        //参数2， 传递给代理的参数
       vm.loadAgent("F:/springboot/sharding-share-work/xdubbo/xdubbo-agent/target/xdubbo-agent-1.0-SNAPSHOT.jar", "F:/springboot/sharding-share-work/xdubbo/xdubbo-agent/target/test-classes/com/xdubbo/test/User.class");

        vm.detach();
    }
}
