package com.github.wp17.lina.game.module.log;

import com.github.wp17.lina.game.module.AbsModule;

public class LogModule implements AbsModule {
	private LogModule() {}
	private static final LogModule instance = new LogModule();
	public static LogModule getInstance() {
		return instance;
	}

	@Override
	public void init() {
//		DOMConfigurator.configureAndWatch("log4j.xml");
	}

	@Override
	public void shutdown() {

	}

}
