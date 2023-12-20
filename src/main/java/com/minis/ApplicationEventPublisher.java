package com.minis;

/**
 * 应用事件监听
 *
 * todo 为后续观察者模式做准备
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
    void addApplicationListener(ApplicationListener listener);
}
