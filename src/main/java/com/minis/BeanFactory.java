package com.minis;

public interface BeanFactory {
    /**
     * 获取对应Bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 注册Bean
     * @param beanDefinition
     */
    void registerBeanDefinition(BeanDefinition beanDefinition);
}
