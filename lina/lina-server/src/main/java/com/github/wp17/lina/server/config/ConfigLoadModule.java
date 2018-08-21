package com.github.wp17.lina.server.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.wp17.lina.common.module.IModule;
import com.github.wp17.lina.common.module.ModuleInitOrder;
import com.github.wp17.lina.config.Loadable;
import com.github.wp17.lina.server.config.provider.CommonDataProvider;
import com.github.wp17.lina.server.config.provider.LineDataProvider;
import com.github.wp17.lina.server.util.SpringUtil;
import com.github.wp17.lina.util.FileUtil;
import com.github.wp17.lina.util.StringUtil;

@Component
@Order(ModuleInitOrder.configModule)
public class ConfigLoadModule implements IModule, CommandLineRunner{
	public static final String parentPath = System.getProperty("user.dir");
	private List<Loadable> loadables = new ArrayList<Loadable>();
	private List<Reloadable> reloadables = new ArrayList<Reloadable>();
	
	@Override
	public void run(String... args) throws Exception {
		registers();
		for (Loadable loadable : loadables) {
			loadable.init();
		}
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void destory() {

	}
	
	public void registers(){
		register(SpringUtil.getBean(CommonDataProvider.class));
		register(SpringUtil.getBean(LineDataProvider.class));
	}
	
	public void register(Loadable loadable){
		loadables.add(loadable);
		if (StringUtil.isNotEmpty(loadable.path())) {
			Reloadable reloadable = new Reloadable(loadable);
			reloadables.add(reloadable);
		}
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
}
