package com.github.wp17.lina.game.module.execute;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	@Override
	public void init() {
		lineExecutor = Executors.newCachedThreadPool(new NamedThreadFactory("line_thread_"));
		dbExecutor = Executors.newCachedThreadPool(new NamedThreadFactory("db_thread_"));
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
}
