package org.example;

import com.minis.BeansException;
import com.minis.ClassPathXmlApplicationContext;
import com.minis.test.TestService;

public class Main {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        TestService testService = null;
        try {
            testService = (TestService) context.getBean("testService");
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
        testService.sayHello();
    }
}