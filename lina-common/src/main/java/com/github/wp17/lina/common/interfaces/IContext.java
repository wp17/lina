package com.github.wp17.lina.common.interfaces;

public interface IContext {
    <T> T getBean(Class<T> clazz);

    <T> T getBean(String name, Class<T> clazz);

    String getProperty(String key);

    int getIntProperty(String key);
}
