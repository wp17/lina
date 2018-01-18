package com.github.wp17.lina.server;

import java.lang.Thread.UncaughtExceptionHandler;

import com.github.wp17.lina.config.ConfigLoadModule;
import com.github.wp17.lina.db.DBModule;
import com.github.wp17.lina.executor.ExecutorModule;
import com.github.wp17.lina.line.LineServerModule;
import com.github.wp17.lina.log.LoggerProvider;
import com.github.wp17.lina.message.MessageModule;
import com.github.wp17.lina.module.ModuleManager;
import com.github.wp17.lina.timer.TimeTaskModule;

public abstract class AbstractServer implements Server{
	private final int id;
	
	protected AbstractServer(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public void startup(){
		preStartup();
		
		initModules();
		defaultExceptionHandle();
		
		aftStartup();
	}
	
	protected abstract void preStartup();
	protected abstract void aftStartup();
	
	private void initModules(){
		ExecutorModule.getInstance().register();
		MessageModule.getInstance().register();
		ConfigLoadModule.getInstance().register();
		TimeTaskModule.getInstance().register();
		DBModule.getInstance().register();
		LineServerModule.getInstance().register();
		
		ModuleManager.getInstance().init();
	}
	
	private void defaultExceptionHandle(){
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				LoggerProvider.addExceptionLog(t.toString(), e);
			}
		});
	}
}
