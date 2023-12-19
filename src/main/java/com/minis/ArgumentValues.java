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
    private final Map<Integer,ArgumentValue> indexedArgumentValues = new HashMap<>(0);


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
    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();

    private void addArgumentValue(Integer key,ArgumentValue newValue){
        indexedArgumentValues.put(key,newValue);
    }

    public boolean hasIndexedArgumentValue(Integer key){
        return indexedArgumentValues.containsKey(key);
    }

    public ArgumentValue getIndexedArgumentValue(Integer key){
        return indexedArgumentValues.get(key);
    }

    public void addGenericArgumentValue(Object value,String type){
        genericArgumentValues.add(new ArgumentValue(value,type));
    }

    private void addGenericArgumentValue(ArgumentValue newValue){
        if(newValue.getName() != null){
            // 去重
            for(Iterator<ArgumentValue> it = genericArgumentValues.iterator();it.hasNext();){
                ArgumentValue currentValue = it.next();
                if(newValue.getName().equals(currentValue.getName())){
                    it.remove();
                }
            }
        }
        genericArgumentValues.add(newValue);
    }

    public ArgumentValue getGenericArgumentValue(String requiredName){
        for (ArgumentValue valueHolder : genericArgumentValues) {
            if(valueHolder.getName() != null && (requiredName == null || !valueHolder.getName().equals(requiredName))){
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    public int getArgumentCount(){
        return genericArgumentValues.size();
    }

    public boolean isEmpty(){
        return genericArgumentValues.isEmpty();
    }






}
