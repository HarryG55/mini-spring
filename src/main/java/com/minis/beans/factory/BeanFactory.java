package com.minis.beans.factory;

import com.minis.BeansException;

/**
 * Bean工厂 定义了Bean的创建，注册，管理等操作
 */
public interface BeanFactory {
    /**
     * 获取对应Bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 是否包含Bean
     * @param name
     * @return
     */
    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);

    /**
     * 注册Bean
     * @param beanName
     * @param obj
     */
    void registerBean(String beanName,Object obj);
}
