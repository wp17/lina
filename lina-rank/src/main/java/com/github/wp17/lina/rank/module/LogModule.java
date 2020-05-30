package com.github.wp17.lina.rank.module;

public class LogModule {
	private LogModule() {}
	private static final LogModule instance = new LogModule();
	public static LogModule getInstance() {
		return instance;
	}

	public void init() {
//		DOMConfigurator.configureAndWatch("log4j.xml");
	}

	public void shutdown() {

	}

}
