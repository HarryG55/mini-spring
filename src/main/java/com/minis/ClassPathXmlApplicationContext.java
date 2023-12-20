package com.minis;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.support.SimpleBeanFactory;
import com.minis.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 将上下文配置加载和Bean的相关操作逻辑解耦
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

    AbstractAutowireCapableBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName){
        this(fileName,true);
    }

    public ClassPathXmlApplicationContext(String fileName,boolean isRefresh){
        Resource resouce = new ClassPathXmlResource(fileName);
        AbstractAutowireCapableBeanFactory beanFactory = new AbstractAutowireCapableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        reader.loadBeanDefinition(resouce);
        this.beanFactory = beanFactory;
        if(isRefresh){
            beanFactory.refresh();
        }
    }

//    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors(){
//        return beanFactory.getBeanFactoryPostProcessors();
//    }
//
//    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor){
//        beanFactory.addBeanFactoryPostProcessor(beanFactoryPostProcessor);
//    }

    public void refresh() throws BeansException, IllegalStateException{
        registerBeanPostProcessors(beanFactory);
        onRefresh();
    }

    private void registerBeanPostProcessors(AbstractAutowireCapableBeanFactory beanFactory){
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    private void onRefresh(){
        beanFactory.refresh();
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
