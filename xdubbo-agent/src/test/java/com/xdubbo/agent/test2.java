package com.xdubbo.agent;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by luozhonghua on 2018/6/3.
 */
public class test2 {
    public static void main(String[] args) {

       /* List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list)
        {
            System.out.println("pid:" + vmd.id() + ":" + vmd.displayName());
        }*/
        try {


            //System.out.println(getCurrentVm());
            System.setProperty("java.library.path", "C:\\Program Files\\Java\\jdk1.8.0_121\\jre\\bin");
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);

            VirtualMachine vm = getCurrentVm();
            String agentjarpath = "F:/springboot/sharding-share-work/xdubbo/xdubbo-agent/src/main/java/com/xdubbo/agent/xxx.jar"; //agentjar路径
            //vm = VirtualMachine.attach("5092");//目标JVM的进程ID（PID）
            vm.loadAgent(agentjarpath, "This is Args to the Agent.");
            vm.detach();

        } catch (Throwable e) {
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
            System.out.println(pid);
            return VirtualMachine.attach(pid);
        } catch (AttachNotSupportedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
