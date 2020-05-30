package com.github.wp17.lina.game.spring;

import com.github.wp17.lina.common.interfaces.IContext;
import com.github.wp17.lina.game.ServerConfig;
import com.github.wp17.lina.game.module.db.RoleDbConfig;
import com.github.wp17.lina.game.module.mq.GameRabbitConfig;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 使用spring管理一部分服务
 */
public class SpringContext implements IContext {
    private SpringContext() {
    }

    private static final SpringContext instance = new SpringContext();

    public static final SpringContext getInstance() {
        return instance;
    }

    private AnnotationConfigApplicationContext context;

    public void init() {
        if (null == context) {
            synchronized (this) {
                if (null == context) {
                    context = new AnnotationConfigApplicationContext
                            (ServerConfig.class, RoleDbConfig.class, GameRabbitConfig.class);
                }
            }
        }
    }

    public void shutdown() {
        context.close();
    }

    public <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }

    public String getProperty(String key) {
        return context.getEnvironment().getProperty(key);
    }

    public int getIntProperty(String key) {
        return context.getEnvironment().getProperty(key, Integer.class);
    }
}
