package com.xdubbo.agent;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

/**
 * Created by luozhonghua on 2018/6/3.
 */
public class MyAgent {

    //agentArgs就是VirtualMachine.loadAgent()的第二个参数
    public static void agentmain(String agentArgs, Instrumentation inst)
    {
        try
        {
            System.out.println("args: " + agentArgs);
            System.out.println("重新定义 com.xdubbo.test.User -- 开始");

            //把新的User类文件的内容读出来
            File f = new File(agentArgs);
            byte[] reporterClassFile = new byte[(int) f.length()];
            DataInputStream in = new DataInputStream(new FileInputStream(f));
            in.readFully(reporterClassFile);
            in.close();

            //把User类的定义与新的类文件关联起来
            ClassDefinition reporterDef =
                    new ClassDefinition(Class.forName("com.xdubbo.test.User"), reporterClassFile);
            //重新定义User类， 妈呀， 太简单了
            inst.redefineClasses(reporterDef);
            System.out.println("重新定义 com.xdubbo.test.User -- 完成");
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
