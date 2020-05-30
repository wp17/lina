package com.github.wp17.lina.game.redis;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

import java.util.ArrayList;

public class RedisService {
    private RedisClusterClient client;
    StatefulRedisClusterConnection<String, String> connect;
    private RedisAdvancedClusterCommands<String, String> syncCommands;
    private RedisAdvancedClusterAsyncCommands<String, String> asyncCommands;
    private volatile boolean init = false;

    public void init() {
        if (!init) {
            ArrayList<RedisURI> list = new ArrayList<>();
            list.add(RedisURI.create("redis://localhost:6379"));
            list.add(RedisURI.create("redis://localhost:6380"));
            list.add(RedisURI.create("redis://localhost:6381"));
            client = RedisClusterClient.create(list);

            ClusterClientOptions options = ClusterClientOptions.builder()
                    .autoReconnect(true)
                    .maxRedirects(1)
                    .build();

            client.setOptions(options);
            connect = client.connect();

            syncCommands = connect.sync();
            asyncCommands = connect.async();
            init = true;
        }
    }

    public void shutdown() {
        connect.close();
        client.shutdown();
    }

    public String getSync(String key) {
        return syncCommands.get(key);
    }

    public boolean invalidate(String key) {
        long result = syncCommands.del(key);
        if (result == 1) return true;
        else return false;
    }

    public boolean expire(String key, long seconds) {
        return syncCommands.expire(key, seconds);
    }

    public boolean setSync(String key, String value) {
        String result = syncCommands.set(key, value);
        if ("OK".equals(result)) return true;
        else return false;
    }

    public boolean setnx(String key, String value) {
        return syncCommands.setnx(key, value);
    }
}
