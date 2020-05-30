package com.github.wp17.lina.rank.spring;

import com.github.wp17.lina.common.interfaces.IContext;
import com.github.wp17.lina.rank.RankConfig;
import com.github.wp17.lina.rank.db.RankDBConfig;
import com.github.wp17.lina.rank.mq.RankRabbitConfig;
import com.github.wp17.lina.util.StringUtil;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**使用spring管理一部分服务*/
public class RankSpringContext implements IContext {
    private RankSpringContext() {
    }

    private static final RankSpringContext instance = new RankSpringContext();

    public static final RankSpringContext getInstance() {
        return instance;
    }

    @Getter
    private AnnotationConfigApplicationContext context;

    public void init() {
        if (null == context) {
            synchronized (this) {
                if (null == context) {
                    context = new AnnotationConfigApplicationContext(
                            RankConfig.class, RankDBConfig.class, RankRabbitConfig.class);
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
