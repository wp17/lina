package com.github.wp17.lina.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.wp17.lina.module.IModule;
import com.github.wp17.lina.module.ModuleInitOrder;

public class ExecutorModule implements IModule {
	private ExecutorModule(){}
	private final static ExecutorModule instance = new ExecutorModule();
	public static ExecutorModule getInstance() {
		return instance;
	}
	
	@Override
	public int order() {
		return ModuleInitOrder.executorModule;
	}
	
	private final ExecutorService lineExecutor = Executors.newCachedThreadPool();
	
	@Override
	public void init() {
	}

	@Override
	public void destory() {
		lineExecutor.shutdown();
	}
	
	public void executeFUService(Runnable runnable){
		if (null != runnable) {
			lineExecutor.execute(runnable);
		}
	}

	public static void main(String[] args) {
	}
}
