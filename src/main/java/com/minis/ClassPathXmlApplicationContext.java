package com.minis;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassPathXmlApplicationContext {
    // Bean定义
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    // 单例Bean映射
    private Map<String,Object> singletonObjects = new HashMap<>();

    public ClassPathXmlApplicationContext(String fileName) {
        this.readXml(fileName);
        this.instanceBeans();
    }

    /**
     * 读取XML配置文件
     * @param fileName
     */
    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();

        URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
        Document document = null;
        try {
            document = saxReader.read(xmlPath);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        Element rootElement = document.getRootElement();
        for (Element element : rootElement.elements()) {
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);
            beanDefinitions.add(beanDefinition);
        }
    }

    /**
     *
     */
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                singletonObjects.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
            } catch (InstantiationException e) {
                Logger.getGlobal().log(Level.WARNING, e.getMessage());
            } catch (IllegalAccessException e) {
                Logger.getGlobal().log(Level.WARNING, e.getMessage());
            } catch (ClassNotFoundException e) {
                Logger.getGlobal().log(Level.WARNING, e.getMessage());
            }
        }
    }

    public Object getBean(String beanId) {
        return singletonObjects.get(beanId);
    }
}
