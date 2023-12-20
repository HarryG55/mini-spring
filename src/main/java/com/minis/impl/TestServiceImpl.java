package com.minis.impl;

import com.minis.test.BaseService;
import com.minis.test.TestService;

public class TestServiceImpl implements TestService {

    private String name;
    private int level;
    private String property1;
    private String property2;
    private BaseService ref1;

    public TestServiceImpl() {
    }

    public TestServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public void sayHello() {
        System.out.println("Hello world!");
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public BaseService getRef1() {
        return ref1;
    }

    public void setRef1(BaseService ref1) {
        this.ref1 = ref1;
    }
}
