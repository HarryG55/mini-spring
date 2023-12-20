package com.minis.beans.factory.support;

import com.minis.*;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private Map<String,Object> earlySingletonObjects = new ConcurrentHashMap(256);
    private List<String> beanDefinitionNames = new ArrayList<>();

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition){
        beanDefinitionMap.put(beanName,beanDefinition);
        beanDefinitionNames.add(beanName);
        if(!beanDefinition.isLazyInit()){
            try{
                getBean(beanName);
            } catch (BeansException e) {
            }
        }
    }

    public void removeBeanDefinition(String beanName){
        beanDefinitionMap.remove(beanName);
        beanDefinitionNames.remove(beanName);
        removeSingleton(beanName);
    }

    public boolean getBeanDefinition(String beanName){
        return beanDefinitionMap.containsKey(beanName);
    }

    public boolean containBeanDefinition(String beanName){
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public boolean isSingleton(String name) {
        return beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return beanDefinitionMap.get(name).getClass();
    }

    private Object createBean(BeanDefinition beanDefinition){
        Class<?> clz = null;
        Object obj = doCreateBean(beanDefinition);
        earlySingletonObjects.put(beanDefinition.getId(),obj);
        try{
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // 属性注入
        handleProperties(beanDefinition,clz,obj);

        return obj;
    }

    /**
     * 创建毛坯实例，仅调用构造方法，不进行属性处理
     * @param beanDefinition
     * @return
     */
    private Object doCreateBean(BeanDefinition beanDefinition){
        Class<?> clz = null;
        Object obj = null;

        handleArgument(beanDefinition,clz,obj);

        return obj;
    }

    private void handleArgument(BeanDefinition bd,Class<?> clz,Object obj){
        Constructor<?> constructor = null;
        try {
            // 获取类
            clz = Class.forName(bd.getClassName());
            // 处理构造器参数
            ConstructorArgumentValues avs = bd.getConstrutorArgumentValues();
            if(!avs.isEmpty()){
                Class<?>[] paramTypes = new Class<?>[avs.getArgumentCount()];
                Object[] paramValues = new Object[avs.getArgumentCount()];
                for (int i = 0; i < avs.getArgumentCount(); i++) {
                    ConstructorArgumentValue av = avs.getIndexdArgumentValue(i);

                    // 类型判断
                    if("String".equals(av.getType()) || "java.lang.String".equals(av.getType())){
                        paramTypes[i] = String.class;
                        paramValues[i] = av.getValue();
                    } else if("int".equals(av.getType()) || "java.lang.int".equals(av.getType())){
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.parseInt(av.getValue().toString());
                    } else if("float".equals(av.getType()) || "java.lang.float".equals(av.getType())){
                        paramTypes[i] = float.class;
                        paramValues[i] = Float.parseFloat(av.getValue().toString());
                    } else if("double".equals(av.getType()) || "java.lang.double".equals(av.getType())){
                        paramTypes[i] = double.class;
                        paramValues[i] = Double.parseDouble(av.getValue().toString());
                    } else if("long".equals(av.getType()) || "java.lang.long".equals(av.getType())){
                        paramTypes[i] = long.class;
                        paramValues[i] = Long.parseLong(av.getValue().toString());
                    } else if("boolean".equals(av.getType()) || "java.lang.boolean".equals(av.getType())){
                        paramTypes[i] = boolean.class;
                        paramValues[i] = Boolean.parseBoolean(av.getValue().toString());
                    } else {
                        // 默认为String
                        paramTypes[i] = String.class;
                        paramValues[i] = av.getValue();
                    }
                }
                try{
                    // 使用反射机制，和特定构造器创建对象
                    constructor = clz.getConstructor(paramTypes);
                    obj = constructor.newInstance(paramValues);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                // 使用默认构造器创建对象
                obj = clz.newInstance();
            }
        } catch (Exception e) {}
    }

    /**
     * 属性注入
     * @param bd
     * @param clz
     * @param obj
     */
    private void handleProperties(BeanDefinition bd,Class<?> clz, Object obj){
        System.out.println("handle properties for bean:"+bd.getId());

        PropertyValues pvs = bd.getPropertyValues();
        if(!pvs.isEmpty()){
            for (int i = 0; i < pvs.size(); i++) {
                PropertyValue propertyValue = pvs.getPropertyValueList().get(i);
                String pType =  propertyValue.getType();
                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.isRef();

                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];

                // 判断是否为引用类型
                if(!isRef){
                    if(pValue.getClass().equals(String.class)){
                        paramTypes[0] = String.class;
                    } else if(pValue.getClass().equals(Integer.class)){
                        paramTypes[0] = int.class;
                    } else if(pValue.getClass().equals(Float.class)){
                        paramTypes[0] = float.class;
                    } else if(pValue.getClass().equals(Double.class)){
                        paramTypes[0] = double.class;
                    } else if(pValue.getClass().equals(Long.class)){
                        paramTypes[0] = long.class;
                    } else if(pValue.getClass().equals(Boolean.class)){
                        paramTypes[0] = boolean.class;
                    } else {
                        paramTypes[0] = String.class;
                    }
                } else {
                    try{
                        paramTypes[0] = Class.forName(pType);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    try{
                        paramValues[0] = getBean((String)pValue);
                    } catch (BeansException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 使用默认的set方法来找到对应的属性设置方法
                String methodName = "set"+ pName.substring(0,1).toUpperCase() + pName.substring(1);
                Method method = null;
                try{
                    method = clz.getMethod(methodName,paramTypes);
                    // 调用对应方法并赋值
                    method.invoke(obj,pValue);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    @Override
    public Object getBean(String beanName) throws BeansException {
        // 尝试直接拿Bean实例
        Object singleton = getSingleton(beanName);

        if(singleton == null){
            // 尝试通过毛坯获取
            singleton = earlySingletonObjects.get(beanName);
            if(singleton == null){
                // 连毛坯都没有的话，需要生成
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                singleton = createBean(beanDefinition);
                registerSingleton(beanName,singleton);
                // 预留beanPostProcessor位置
                /*
                 */
            }
        }
        return singleton;
    }

    /**
     * 创建所有的Bean实例
     */
    public void refresh(){
        for (String beanDefinitionName : beanDefinitionNames) {
            try{
               getBean(beanDefinitionName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean containsBean(String name) {
        return containsSingleton(name);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        registerSingleton(beanName,obj);
    }

}
