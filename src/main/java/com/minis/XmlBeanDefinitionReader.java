package com.minis;

import org.dom4j.Element;

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
            beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
