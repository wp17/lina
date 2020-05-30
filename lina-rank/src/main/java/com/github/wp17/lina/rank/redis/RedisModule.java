package com.github.wp17.lina.rank.redis;

import com.github.wp17.lina.common.id.SnowFlakeIDService;
import com.github.wp17.lina.rank.module.LogModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisModule {
    private RedisModule() {
    }

    private static final RedisModule instance = new RedisModule();

    public static final RedisModule getInstance() {
        return instance;
    }

    private RedisService redisService = new RedisService();
    private RedisShardingService shardingService = new RedisShardingService();

    public void init() {
        redisService.init();
        shardingService.init();
    }

    public void shutdown() {
        redisService.shutdown();
        shardingService.shutdown();
    }

    public void set(String key, String val) {
        shardingService.set(key, val);
    }

    public String get(String key) {
        return shardingService.get(key);
    }

    public static void main(String[] args) {
        LogModule.getInstance().init();
        RedisModule.getInstance().init();
        SnowFlakeIDService idService = new SnowFlakeIDService(2, 1);
        idService.init();

        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    long id = idService.genId();
                    RedisModule.getInstance().set("" + id, "111");
                    System.out.println(RedisModule.getInstance().get("" + id));
                }
//                while (true) {
//                    long id = idService.genId();
//                    if (id == -1L) {
//                        log.debug("时钟回拨异常");
//                        throw new RuntimeException("时钟回拨异常");
//                    }
//
//                    RedisModule.getInstance().set("" + id, "111");
//                    System.out.println(RedisModule.getInstance().get("" + id));
//                }

            });
            thread.start();
        }
//        RedisModule.getInstance().shutdown();
    }
}
