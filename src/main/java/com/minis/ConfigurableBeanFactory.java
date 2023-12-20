package com.minis;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.SingletonBeanRegistry;

/**
 * 维护Bean之间的依赖关系
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
    int getBeanPostProcessorCount();
    void registerDepedentBean(String beanName,String dependentBeanName);
    String[] getDependentBeans(String beanName);
    String[] getDependenciesForBean(String beanName);

}
