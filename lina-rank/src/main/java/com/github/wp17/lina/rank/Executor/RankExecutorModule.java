package com.github.wp17.lina.rank.Executor;

import com.github.wp17.lina.common.util.NamedThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RankExecutorModule {
    private RankExecutorModule() {}
    private static final RankExecutorModule instance = new RankExecutorModule();
    public static RankExecutorModule getInstance() {
        return instance;
    }

    private ExecutorService dbService =
            Executors.newFixedThreadPool(10, new NamedThreadFactory("rank_db_"));

    public void addDBTask(Runnable task) {
        dbService.execute(task);
    }

    public void shutdown() {
        dbService.shutdown();
//        dbService.awaitTermination()
    }
}
