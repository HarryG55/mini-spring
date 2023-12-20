package com.minis;

import java.util.*;

/**
 * ArgumentValue的多值容器
 * 构造器方法注入的额外设计
 */
public class ArgumentValues {

    /**
     * 具有索引的构造参数
     *
     * 如下举例：
     *
     * 代码：
     *     public class MyClass{
     *         public MyClass(String name, int age) {
     *         }
     *     }
     *
     * 配置：
     * <bean id="myBean" class="com.example.MyClass">
     *     <constructor-arg index="0" value="test" />
     *     <constructor-arg index="1" value="123" />
     * </bean>
     */
//    private final Map<Integer,ArgumentValue> indexedArgumentValues = new HashMap<>(0);


    /**
     *
     * 不关心索引位置，仅根据索引类型进行匹配
     *
     * 代码：
     * public class MyClass {
     *     public MyClass(AnotherClass arg) {
     *         // ...
     *     }
     * }
     *
     * 配置：
     * <bean id="anotherBean" class="com.example.AnotherClass">
     *     <!-- bean configuration -->
     * </bean>
     *
     * <bean id="myBean" class="com.example.MyClass">
     *     <constructor-arg ref="anotherBean" />
     * </bean>
     */
    private final List<ArgumentValue> argumentValueList = new LinkedList<>();

    public void addArgumentValue(Object value,String type){
        argumentValueList.add(new ArgumentValue(value,type));
    }

    public void addArgumentValue(Object value,String type,String name){
        argumentValueList.add(new ArgumentValue(value,type,name));
    }

    public ArgumentValue getIndexdArgumentValue(int index){
        return argumentValueList.get(index);
    }

    public int getArgumentCount(){
        return argumentValueList.size();
    }

    public boolean isEmpty(){
        return argumentValueList.isEmpty();
    }

}
