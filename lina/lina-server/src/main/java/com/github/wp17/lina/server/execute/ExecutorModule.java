package com.github.wp17.lina.server.execute;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class ExecutorModule {
	private final ExecutorService lineExecutor = Executors.newCachedThreadPool();
	
	@PreDestroy
	public void destory() {
		lineExecutor.shutdown();
	}
	
	public void executeFUService(Runnable runnable){
		if (null != runnable) {
			lineExecutor.execute(runnable);
		}
	}
}
