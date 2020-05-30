package com.github.wp17.lina.rank.redis;

import com.google.common.collect.Lists;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.util.Hashing;

import java.util.List;

public class RedisShardingService {
    private ShardedJedisPool pool;

    public void init() {
        JedisShardInfo shardInfo1 = new JedisShardInfo("localhost", 6500);
        JedisShardInfo shardInfo2 = new JedisShardInfo("localhost", 6501);
        List<JedisShardInfo> shardInfos = Lists.newArrayList(shardInfo1, shardInfo2);

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMinIdle(5);
        poolConfig.setMaxIdle(10);
        poolConfig.setMaxTotal(100);
        pool = new ShardedJedisPool(poolConfig, shardInfos, Hashing.MURMUR_HASH);
    }

    public void set(String key, String val) {
        ShardedJedis shardedJedis = getJedis();
        shardedJedis.set(key, val);
        shardedJedis.close();
    }

    public String get(String key) {
        ShardedJedis shardedJedis = getJedis();
        String val = shardedJedis.get(key);
        shardedJedis.close();
        return val;
    }

    private ShardedJedis getJedis() {
        ShardedJedis shardedJedis = pool.getResource();
        return shardedJedis;
    }

    public void shutdown() {
        pool.close();
    }
}
