package com.minis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory{

    private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

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


    @Override
    public Object getBean(String beanName) throws BeansException {
        // 尝试直接拿Bean实例
        Object singleton = getSingleton(beanName);
        if(singleton == null){
            // todo 这里的Definition需要在初始化
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition == null){
                throw new BeansException("No bean named '" + beanName + "' is defined");
            }

            try{
                // 尝试通过反射获取对应单例
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            // 注册bean单例
            this.registerSingleton(beanName,singleton);
        }
        return singleton;
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
