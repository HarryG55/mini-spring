package com.minis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleBeanFactory implements BeanFactory{

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private List<String> beanNames = new ArrayList<>();
    private Map<String,Object> singletons = new HashMap<>();


    @Override
    public Object getBean(String beanName) throws BeansException {
        // 获取对应Bean
        Object singleton = singletons.get(beanName);
        if(singleton == null){
            // 如果没有Bean，则先查找是否有对应bean名
            int i = beanNames.indexOf(beanName);
            if(i == -1){
                throw new BeansException("Not found bean!beanName:"+beanName);
            } else {
                // 尝试加载bean
                BeanDefinition beanDefinition = beanDefinitions.get(i);
                try{
                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                } catch (Exception e){
                    throw new BeansException("Bean instantiation failed!beanName:"+beanName);
                }
                singletons.put(beanName,singleton);
            }
        }
        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }
}
