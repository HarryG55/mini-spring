package com.minis.impl;

import com.minis.test.TestService;

public class TestServiceImpl implements TestService {
    @Override
    public void sayHello() {
        System.out.println("Hello world!");
    }
}
