package com.github.wp17.lina.rank.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Objects;

@Slf4j
public class RedisService {
    private JedisPool pool;

    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setTestOnCreate(true);
        config.setMaxTotal(100);
        config.setMaxIdle(10);
        config.setMinIdle(10);
        config.setMaxWaitMillis(30000);
        pool = new JedisPool(config, "localhost", 6500);
    }

    public void set(String key, String val) {
        Jedis jedis = getJedis();
        if (Objects.isNull(jedis)) {
            log.error("get jedis error, is null", new RuntimeException());
        }
        jedis.set(key, val);
        jedis.close();
    }

    public String get(String key) {
        Jedis jedis = getJedis();
        if (Objects.isNull(jedis)) {
            log.error("get jedis error, is null", new RuntimeException());
        }
        String val = jedis.get(key);
        jedis.close();
        return val;
    }

    private Jedis getJedis() {
        int count = 0;
        Jedis jedis = null;
        try {
            while (count++ < 3 && (jedis = pool.getResource()) == null)
                log.warn("getJedis timeout count={}", count);

        }catch (Exception e) {
            log.warn("jedisInfo ... NumActive=" + pool.getNumActive()
                    + ", NumIdle=" + pool.getNumIdle()
                    + ", NumWaiters=" + pool.getNumWaiters()
                    + ", isClosed=" + pool.isClosed());
            log.error("get jedis error", e);
        }
        return jedis;
    }

    public void shutdown() {
        pool.close();
    }
}
