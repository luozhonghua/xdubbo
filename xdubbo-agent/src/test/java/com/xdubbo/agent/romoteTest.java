package com.xdubbo.agent;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

/**
 * Created by luozhonghua on 2018/6/4.
 */
public class romoteTest {

    public static void main(String[] args) {
         /*
   * host: 远程机器的ip地址
* port: 远程java进程运行的jmxremote端口
*
*/
        try {

           /* JMXServiceURL serviceURL = new JMXServiceURL();
            JMXConnector conn = JMXConnectorFactory.connect(serviceURL);
            MBeanServerConnection mbs = conn.getMBeanServerConnection();

            //获取远程memorymxbean
            MemoryMXBean memBean = ManagementFactory.newPlatformMXBeanProxy
                    (mbs, ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);

            //获取远程opretingsystemmxbean
            com.sun.management.OperatingSystemMXBean opMXbean =
                    ManagementFactory.newPlatformMXBeanProxy(mbs,
                            ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
