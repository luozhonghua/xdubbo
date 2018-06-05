package com.xdubbo.agent;

import sun.jvmstat.monitor.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luozhonghua on 2018/6/4.
 */
public class JVMProcessTest {

    public static void main (String [] args){
        try {


          System.out.println("pid:"+ findProcessIdByProcessName("xdubbo-agent-1.0-SNAPSHOT.jar"));


             //System.out.println("pid:"+getProcess(MyAgent.class));



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据进程名称查找进程ID
     * @param processName
     * @return  返回进程PID
     * @throws MonitorException
     * @throws URISyntaxException
     */
    private static Object findProcessIdByProcessName(String processName) throws MonitorException, URISyntaxException {
        Map<String,Object> crruProcessId=new ConcurrentHashMap<String,Object>();
        // 获取监控主机
        MonitoredHost local = MonitoredHost.getMonitoredHost("localhost");
        // 取得所有在活动的虚拟机集合
        Set<?> vmlist = new HashSet<Object>(local.activeVms());
        // 遍历集合，输出PID和进程名
        for (Object process : vmlist) {
            MonitoredVm vm = local.getMonitoredVm(new VmIdentifier("//" + process));
            // 获取类名
            String processname = MonitoredVmUtil.mainClass(vm, true);
            crruProcessId.put(processname,process);
            System.out.println(process + " ------> " + processname);
            if("xdubbo-agent-1.0-SNAPSHOT.jar".equalsIgnoreCase(processname))
            return  process;
        }
        return -1;
    }


    public static int getProcess(Class<?> cls) throws MonitorException, URISyntaxException {
        if(cls == null) {
            return -1;
        }
        // 获取监控主机
        MonitoredHost local = MonitoredHost.getMonitoredHost("localhost");
        // 取得所有在活动的虚拟机集合
        Set<?> vmlist = new HashSet<Object>(local.activeVms());
        // 遍历集合，输出PID和进程名
        for(Object process : vmlist) {
            MonitoredVm vm = local.getMonitoredVm(new VmIdentifier("//" + process));
            // 获取类名
            String processname = MonitoredVmUtil.mainClass(vm, true);
            if(cls.getName().equals(processname)) {
                return ((Integer)process).intValue();
            }
        }
        return -1;
    }
}
