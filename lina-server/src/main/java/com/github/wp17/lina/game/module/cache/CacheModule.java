package com.github.wp17.lina.game.module.cache;

import com.alibaba.fastjson.JSONObject;
import com.github.wp17.lina.common.cache.ICacheObj;
import com.github.wp17.lina.common.cache.InfoClass;
import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.game.gcache.GuavaCacheService;
import com.github.wp17.lina.game.module.AbsModule;
import com.github.wp17.lina.game.module.ModuleInitOrder;
import com.github.wp17.lina.game.redis.RedisService;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class CacheModule implements AbsModule {
    private CacheModule() {
    }

    private static final CacheModule instance = new CacheModule();

    public static CacheModule getInstance() {
        return instance;
    }

    private RedisService redisService;
    private GuavaCacheService guavaCacheService;

    public <T> T get(Object id, CacheKey cacheKey) {
        String key = buildKey(id, cacheKey);

        ICacheObj cacheObj = guavaCacheService.getIfPresent(key);
        if (Objects.nonNull(cacheObj)) {
            if (cacheObj.isLoad()) {
                return (T) cacheObj.get();
            }
        }

        try {
            cacheObj = cacheKey.clazz.newInstance();
        } catch (InstantiationException e) {
            LoggerProvider.addExceptionLog(e);
        } catch (IllegalAccessException e) {
            LoggerProvider.addExceptionLog(e);
        }

        cacheObj.loadRedis(id);
        if (cacheObj.isLoad()) {
            guavaCacheService.set(key, cacheObj);
            return (T) cacheObj.get();
        }

        cacheObj.loadDB(id);
        if (cacheObj.isLoad()) {
            guavaCacheService.set(key, cacheObj);
            redisService.setSync(key, JSONObject.toJSONString(cacheObj.get()));
            return (T) cacheObj.get();
        }

        return null;
    }

    public boolean invalidate(Object id, CacheKey cacheKey) {
        String key = buildKey(id, cacheKey);
        return invalidate(key);
    }

    public boolean invalidate(String completeKey) {
        boolean result = redisService.invalidate(completeKey);
        if (result) {
            guavaCacheService.invalidate(completeKey);
            return true;
        } else return false;
    }

    public void invalidateLocal(String completeKey) {
        guavaCacheService.invalidate(completeKey);
    }

    public <T> T loadRedis(Object id, CacheKey cacheKey) {
        String key = buildKey(id, cacheKey);
        String strValue = redisService.getSync(key);
        Class clazz = cacheKey.clazz.getAnnotation(InfoClass.class).value();
        return (T) JSONObject.parseObject(strValue, clazz);
    }

    private String buildKey(Object id, CacheKey cacheKey) {
        return new StringBuffer().append(id).append("_").append(cacheKey.name()).toString();
    }

    @Override
    public void init() {
        redisService = new RedisService();
        redisService.init();

        guavaCacheService = new GuavaCacheService();
        guavaCacheService.init();
    }

    @Override
    public void shutdown() {
        redisService.shutdown();
    }

    @Override
    public int order() {
        return ModuleInitOrder.highest;
    }
}
