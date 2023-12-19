package com.minis;

import org.dom4j.Element;

/**
 * XMLBean定义读取器
 *
 * 1. 从相关资源中获取Bean信息
 * 2. 将Bean信息注册到对应的BeanFactory中
 */
public class XmlBeanDefinitionReader {
    BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 加载定义资源
     * @param resource
     */
    public void loadBeanDefinition(Resource resource){
        while(resource.hasNext()){
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClass = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId,beanClass);
            beanFactory.registerBean(beanId,beanDefinition);
        }
    }
}
