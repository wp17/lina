package com.github.wp17.lina.game.module.execute;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.wp17.lina.common.util.NamedThreadFactory;
import com.github.wp17.lina.game.module.AbsModule;
import com.github.wp17.lina.game.module.ModuleInitOrder;

public class ExecutorModule implements AbsModule {
	private ExecutorModule() {}
	private static final ExecutorModule instance = new ExecutorModule();
	public static final ExecutorModule getInstance() {
		return instance;
	}

	private ExecutorService lineExecutor;
	private ExecutorService dbExecutor;
	private ScheduledExecutorService scheduledExecutorService;

	@Override
	public void init() {
		lineExecutor = Executors.newCachedThreadPool(new NamedThreadFactory("line_thread_"));
		dbExecutor = Executors.newCachedThreadPool(new NamedThreadFactory("db_thread_"));
		scheduledExecutorService =
				Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("scheduled_thread_"));
	}

	@Override
	public void shutdown() {
		lineExecutor.shutdown();
	}

	@Override
	public int order() {
		return ModuleInitOrder.executorModule;
	}

	public void executeFUService(Runnable runnable){
		if (null != runnable) {
			lineExecutor.execute(runnable);
		}
	}

	public void addDbTask(Runnable runnable){
		dbExecutor.execute(runnable);
	}

	public void addSessionCheckTask(Runnable runnable) {
		scheduledExecutorService.schedule(runnable, 1000, TimeUnit.MILLISECONDS);
	}
}
