package com.minis;

/**
 * 将上下文配置加载和Bean的相关操作逻辑解耦
 */
public class ClassPathXmlApplicationContext implements BeanFactory{

    BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        Resource resource = new ClassPathXmlResource(fileName);
        // 默认使用简单Bean工厂
        BeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinition(resource);
        this.beanFactory = beanFactory;
    }

    public Object getBean(String beanId) throws BeansException {
        return this.beanFactory.getBean(beanId);
    }

    @Override
    public boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        beanFactory.registerBean(beanName,obj);
    }

}
