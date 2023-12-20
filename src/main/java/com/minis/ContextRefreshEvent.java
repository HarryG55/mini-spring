package com.minis;

public class ContextRefreshEvent extends ApplicationEvent{
    public ContextRefreshEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
