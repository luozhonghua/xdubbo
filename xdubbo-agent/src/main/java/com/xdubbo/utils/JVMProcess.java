package com.xdubbo.utils;

import sun.jvmstat.monitor.*;
import sun.jvmstat.monitor.remote.RemoteHost;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luozhonghua on 2018/6/4.
 */
public class JVMProcess {
    /**
     * 根据进程名称查找进程ID
     * @param pn
     * @return  返回进程PID
     * @throws MonitorException
     * @throws URISyntaxException
     */
    public static Object findProcessIdByProcessName(String pn)  {
        try {
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
                crruProcessId.put(pn,process);
                System.out.println(process + " ------> " + processname+"   "+pn);
                /*if("xdubbo-agent-1.0-SNAPSHOT.jar".equalsIgnoreCase(pn)) {
                    System.out.println(process + " ------> " + processname+"  "+pn);
                    return crruProcessId.get(pn);
                }*/
            }
            return crruProcessId.get(pn);

        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}
