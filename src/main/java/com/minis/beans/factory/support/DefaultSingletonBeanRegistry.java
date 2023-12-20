package com.minis.beans.factory.support;

import com.minis.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanRegistry仅定义了注册和获取bean的操作
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    // 容器中存放的所有bean名称列表
    protected List<String> beanNames = new ArrayList<>();
    // 容器中存放所有所有bean实例的map
    protected Map<String,Object> singletons = new ConcurrentHashMap<>(256);

    /**
     * 注册单例Bean
     * @param beanName
     * @param singletonObject
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons){
            this.singletons.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return this.beanNames.toArray(new String[0]);
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletons) {
            this.beanNames.remove(beanName);
            this.singletons.remove(beanName);
        }
    }
}
