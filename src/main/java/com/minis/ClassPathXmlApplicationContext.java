package com.minis;

/**
 * 将上下文配置加载和Bean的相关操作逻辑解耦
 */
public class ClassPathXmlApplicationContext implements BeanFactory{

    SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName){
        this(fileName,true);
    }

    public ClassPathXmlApplicationContext(String fileName,boolean isRefresh){
        Resource resouce = new ClassPathXmlResource(fileName);
        SimpleBeanFactory simpleBeanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(simpleBeanFactory);

        reader.loadBeanDefinition(resouce);
        this.beanFactory = simpleBeanFactory;
        if(isRefresh){
            beanFactory.refresh();
        }
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
