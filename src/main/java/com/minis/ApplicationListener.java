package com.minis;

import java.util.EventListener;

public class ApplicationListener implements EventListener {
    void onApplicationEvent(ApplicationEvent event) {
        System.out.println("ApplicationListener.onApplicationEvent");
    }
}
