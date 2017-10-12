package com.github.wp17.lina;

import java.lang.Thread.UncaughtExceptionHandler;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.slf4j.Logger;

import com.github.wp17.lina.config.ConfigLoadModule;
import com.github.wp17.lina.db.DBModule;
import com.github.wp17.lina.executor.ExecutorModule;
import com.github.wp17.lina.line.LineServerModule;
import com.github.wp17.lina.log.LogModule;
import com.github.wp17.lina.log.LoggerProvider;
import com.github.wp17.lina.message.MessageModule;
import com.github.wp17.lina.module.ModuleManager;
import com.github.wp17.lina.timer.TimeTaskModule;

public class LinaServer extends Server {
	private static final Logger logger = LoggerProvider.getLogger(LinaServer.class);
	
	private LinaServer(){}
	private static final LinaServer server = new LinaServer();
	public static LinaServer getServer() {
		return server;
	}
	
	@Override
	protected void initModules() {
		LogModule.getInstance().register();
		ExecutorModule.getInstance().register();
		MessageModule.getInstance().register();
		ConfigLoadModule.getInstance().register();
		TimeTaskModule.getInstance().register();
		DBModule.getInstance().register();
		LineServerModule.getInstance().register();
		
		ModuleManager.getInstance().init();
		logger.info("all modules inited");
	}
	
	@Override
	public int getServerId() {
		return 1;
	}

	@Override
	protected void preStartup() {
		
	}
	
	@Override
	protected void aftStartup() {
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				LoggerProvider.addExceptionLog(t.toString(), e);
			}
		});
	}
	
	public static void main(String[] args) {
		SocketAddress address = new InetSocketAddress(8000);
		LinaServer.getServer().getAddresses().add(address);
		LinaServer.getServer().startup();
		
		logger.info("server started");
	}
}
