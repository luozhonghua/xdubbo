package com.xdubbo.common.bytecode.javassist;

import javassist.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by luozhonghua on 2018/6/1.
 */
public class JavassistDemo {

    //创建class Leader
    final static  ClassPool   pool = ClassPool.getDefault();

    public static void main(String[] args) {
        try {
            //CtClass LeaderClass= createClassByJavassist();
           // ClassPool   pool = ClassPool.getDefault();
           // CtClass LeaderClass = pool.makeClass("com.xdubbo.luozhonghua.Leader");
           // insertSuperClass(LeaderClass);

            injectAndreplaceMethod();


        }catch (Exception e){

        }


    }

    private static void injectAndreplaceMethod() throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        // 定义类
        CtClass ctClass = pool.get("com.xdubbo.common.bytecode.javassist.Calculator");
        // 需要修改的方法名称
        String mname = "getSum";
        CtMethod mold = ctClass.getDeclaredMethod(mname);
        // 修改原有的方法名称
        String nname = mname + "$impl";
        mold.setName(nname);

        //创建新的方法，复制原来的方法
        CtMethod mnew = CtNewMethod.copy(mold, mname, ctClass, null);
        // 主要的注入代码
        StringBuffer body = new StringBuffer();
        body.append("{\nlong start = System.currentTimeMillis();\n");
        // 调用原有代码，类似于method();($$)表示所有的参数
        body.append(nname + "($$);\n");
        body.append("System.out.println(\"Call to method " + mname
                + " took \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");
        body.append("}");
        // 替换新方法
        mnew.setBody(body.toString());
        // 增加新方法
        ctClass.addMethod(mnew);
        Calculator calculator =(Calculator)ctClass.toClass().newInstance();
        calculator.getSum(10000);
    }


    private static void insertSuperClass(CtClass leaderClass) throws CannotCompileException, NotFoundException {
        //设置父类
        leaderClass.setSuperclass(pool.get("com.xdubbo.luozhonghua.domain.Person"));

        //sex属性
        CtField sexField = new CtField(pool.getCtClass("java.util.List"), "sex", leaderClass);
        leaderClass.addField(sexField);

        Class<?> clazz = leaderClass.toClass();
        System.out.println("class:"+clazz.getName());

        System.out.println("---------Superclass---属性列表------------");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getType()+"\t"+field.getName());
        }

        System.out.println("---------Superclass---方法列表------------");
        Method[] methods = clazz.getMethods();
        for (Method method: methods){
            System.out.println(method.getReturnType()+"\t"+method.getName()+"\t"+ Arrays.toString(method.getParameterTypes()));
        }
    }

    private static CtClass createClassByJavassist() throws CannotCompileException, NotFoundException {

        //定义类
        CtClass LeaderClass = pool.makeClass("com.xdubbo.luozhonghua.Leader");
        //加载类
        //   CtClass cc =  pool.get(Leader);

        //id属性
        CtField idField = new CtField(CtClass.longType, "id", LeaderClass);
        LeaderClass.addField(idField);

        //name属性
        CtField nameField = new CtField(pool.get("java.lang.String"), "name", LeaderClass);
        LeaderClass.addField(nameField);


        //age属性
        CtField ageField = new CtField(CtClass.intType, "age", LeaderClass);
        LeaderClass.addField(ageField);

        //构造age get set 方法
        CtMethod getMethod = CtNewMethod.make("public int getAge() { return this.age;}", LeaderClass);
        CtMethod setMethod = CtNewMethod.make("public void setAge(int age) { this.age = age;}", LeaderClass);

        //添加到Class
        LeaderClass.addMethod(getMethod);
        LeaderClass.addMethod(setMethod);

        //LeaderClass.writeFile("F:\springboot\sharding-share-work\xdubbo\xdubbo.common\src\test\java\com\xdubbo\common\bytecode\javassist");

        Class<?> clazz = LeaderClass.toClass();
        System.out.println("class:"+clazz.getName());

        System.out.println("------------属性列表------------");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getType()+"\t"+field.getName());
        }


        System.out.println("------------方法列表------------");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method: methods){
            System.out.println(method.getReturnType()+"\t"+method.getName()+"\t"+ Arrays.toString(method.getParameterTypes()));
        }
        return LeaderClass;
    }


}

