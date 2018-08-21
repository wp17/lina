package com.github.wp17.lina.server;

import java.lang.Thread.UncaughtExceptionHandler;

import com.github.wp17.lina.common.log.LoggerProvider;

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
		defaultExceptionHandle();
		aftStartup();
	}
	
	protected abstract void preStartup();
	protected abstract void aftStartup();
	
	private void defaultExceptionHandle(){
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				LoggerProvider.addExceptionLog(t.toString(), e);
			}
		});
	}
}
