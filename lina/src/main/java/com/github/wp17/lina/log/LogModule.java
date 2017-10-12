package com.github.wp17.lina.log;

import org.apache.log4j.xml.DOMConfigurator;

import com.github.wp17.lina.module.IModule;
import com.github.wp17.lina.module.ModuleInitOrder;

public class LogModule implements IModule {
	private LogModule(){}
	private static LogModule instance = new LogModule();
	public static LogModule getInstance() {
		return instance;
	}
	
	@Override
	public int order() {
		return ModuleInitOrder.logModule;
	}

	@Override
	public void init() {
		String user_dir = System.getProperty("user.dir");
		String path = user_dir+"/msconfig/log4j.xml";
		DOMConfigurator.configure(path);
	}

	@Override
	public void destory() {

	}
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
	}

}
