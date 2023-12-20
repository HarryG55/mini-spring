package com.minis.beans.factory.xml;

import com.minis.*;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.config.ConstructorArgumentValues;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

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

            // 处理属性
            // 传入属性
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if(pValue != null && !pValue.equals("")) {
                    // 优先判断是否对该属性直接赋值
                    isRef = false;
                    pV = pValue;
                } else if(pRef != null && !pRef.equals("")){
                    // 如果pRef的值非空的话，则标记为isRef为真，按照引用对象注入处理
                    pV = pRef;
                    refs.add(pRef);
                }
                PVS.addPropertyValue(new PropertyValue(pType,pName,pV,isRef));
            }
            // 构造器参数
            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues AVS = new ConstructorArgumentValues();
            for (Element e : constructorElements) {
                String aType = e.attributeValue("type");
                String aName = e.attributeValue("name");
                String aValue = e.attributeValue("value");
                AVS.addArgumentValue(aValue,aType,aName);
            }
            beanDefinition.setConstrutorArgumentValues(AVS);
            beanFactory.registerBean(beanId,beanDefinition);
        }
    }
}
