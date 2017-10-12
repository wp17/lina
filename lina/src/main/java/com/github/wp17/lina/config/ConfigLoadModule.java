package com.github.wp17.lina.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.github.wp17.lina.config.csv.CSVReader;
import com.github.wp17.lina.config.data.common.CommonDataProvider;
import com.github.wp17.lina.config.data.line.LineDataProvider;
import com.github.wp17.lina.log.LogModule;
import com.github.wp17.lina.module.IModule;
import com.github.wp17.lina.module.ModuleInitOrder;
import com.github.wp17.lina.util.FileUtil;
import com.github.wp17.lina.util.StringUtil;

public class ConfigLoadModule implements IModule{
	private ConfigLoadModule(){}
	private static ConfigLoadModule instance = new ConfigLoadModule();
	public static ConfigLoadModule getInstance(){
		return instance;
	}
	
	private List<Loadable> loadables = new ArrayList<Loadable>();
	private List<Reloadable> reloadables = new ArrayList<Reloadable>();
	
	@Override
	public void init() {
		csvReaderInit();
		for (Loadable loadable : loadables) {
			loadable.init();
		}
	}
	
	private void csvReaderInit(){
		List<String> list = new ArrayList<String>();
		list.add("com.peter.lina.config");
		try {
			CSVReader.init(list, System.getProperty("user.dir"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destory() {

	}
	
	@Override
	public int order() {
		return ModuleInitOrder.configModule;
	}
	
	private void registers(){
		register(CommonDataProvider.getInstance());
		register(LineDataProvider.getInstance());
	}
	
	public void register(Loadable loadable){
		loadables.add(loadable);
		if (StringUtil.isNotEmpty(loadable.path())) {
			Reloadable reloadable = new Reloadable(loadable);
			reloadables.add(reloadable);
		}
	}
	
	@Override
	public void register() {
		registers();
		IModule.super.register();
	}
	
	private class Reloadable {
		Loadable l;
		long lastLoadTime = System.currentTimeMillis();
		Reloadable(Loadable loadable){
			this.l = loadable;
		}
	}
	
	public void checkReload(){
		String user_dir = System.getProperty("user.dir");
		for (int i = 0; i < reloadables.size(); i++) {
			Reloadable r = reloadables.get(i);
			long lastModifyTime = FileUtil.lastModifyTime(user_dir + r.l.path());
			if (r.lastLoadTime < lastModifyTime) {
				r.lastLoadTime = lastModifyTime;
				r.l.init();
			}
		}
	}
	
	public static void main(String[] args) {
		LogModule.getInstance().init();
		instance.registers();
		instance.init();
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				instance.checkReload();
				
			}
		}, 0, 10000);
	}
}
