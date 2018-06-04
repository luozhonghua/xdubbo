package com.xdubbo.agent;

import com.sun.tools.attach.*;
import com.sun.tools.attach.spi.AttachProvider;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Properties;

/**
 * Created by luozhonghua on 2018/6/3.
 */
public class test {
    public static void main(String[] args) {

       // getV();

        try {

            VirtualMachine vm=VirtualMachine.attach("5092");
            vm.loadAgent("F:/springboot/sharding-share-work/xdubbo/xdubbo-agent/target/xdubbo-agent-1.0-SNAPSHOT.jar");
            //		vm.loadAgent("d:/myagent.jar","myagent");
            System.out.println(vm.getAgentProperties().toString());
            System.in.read();


         /*   VirtualMachine vm=VirtualMachine.attach("5092");  //target java process pid
            System.out.println(vm);
            vm.loadAgent("F:\\springboot\\sharding-share-work\\xdubbo\\xdubbo-agent\\target\\xdubbo-agent-1.0-SNAPSHOT.jar","Powered by luozhonghua!");
            Thread.sleep(1000);
            vm.detach();*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static VirtualMachine getCurrentVm() {
        // 获取当前jvm的进程pid
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        int indexOf = pid.indexOf('@');
        if (indexOf > 0) {
            pid = pid.substring(0, indexOf);
        }
        // 获取当前jvm
        try {
            return VirtualMachine.attach(pid);
        } catch (AttachNotSupportedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    static void getV() {
        try {
            // 获取当前jvm的进程pid
            String pid = ManagementFactory.getRuntimeMXBean().getName();
            int indexOf = pid.indexOf('@');
            if (indexOf > 0) {
                pid = pid.substring(0, indexOf);
            }
            System.out.println("当前JVM Process ID: " + pid);


            // attach to target VM
            VirtualMachine vm = VirtualMachine.attach("5092");

            // get system properties in target VM
            Properties props = vm.getSystemProperties();
            System.out.println(props);

            // construct path to management agent
            String home = props.getProperty("java.home");
            String agent = home + File.separator + "lib" + File.separator
                    + "management-agent.jar";
            System.out.println(agent);
            // load agent into target VM
            vm.loadAgent(agent, "com.sun.management.jmxremote.port=5000");

            // detach
            vm.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
