package com.xdubbo.agent;

import java.lang.instrument.Instrumentation;
import java.io.*;
/**
 * Created by luozhonghua on 2018/6/3.
 */
public class Agent {

    public static void agentmain(String args, Instrumentation inst) throws Exception
    {
        System.out.println("Args:" + args);
    }

    public static void premain(String args, Instrumentation inst) throws Exception
    {
        System.out.println("Pre Args:" + args);
        Class[] classes = inst.getAllLoadedClasses();
        for (Class clazz : classes)
        {
            System.out.println(clazz.getName());
        }
    }

}
