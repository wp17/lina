package com.github.wp17.lina.game.config;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.wp17.lina.config.Loadable;
import com.github.wp17.lina.config.Reload;
import com.github.wp17.lina.game.module.AbsModule;
import com.github.wp17.lina.game.module.ModuleInitOrder;
import com.github.wp17.lina.game.config.provider.CommonDataProvider;
import com.github.wp17.lina.game.config.provider.LineDataProvider;
import com.github.wp17.lina.util.FileUtil;

public class ConfigLoadModule implements AbsModule {
	private ConfigLoadModule() {}
	private static ConfigLoadModule instance = new ConfigLoadModule();
	public static final ConfigLoadModule getInstance() {
		return instance;
	}

	public static final String parentPath = System.getProperty("user.dir");

	private List<Loadable> loadList = new ArrayList<Loadable>();
	private List<Reloadable> reloadList = new ArrayList<Reloadable>();
	
	@Override
	public void init() {
		register();
		for (Loadable loadable : loadList) {
			loadable.init();
		}
	}

	public void register(){
		register0(CommonDataProvider.getInstance());
		register0(LineDataProvider.getInstance());
	}

	public void register0(Loadable loadable) {
		loadList.add(loadable);
		Annotation annotation = loadable.getClass().getAnnotation(Reload.class);
		if (Objects.nonNull(annotation)) {
			Reloadable reloadable = new Reloadable(loadable);
			reloadList.add(reloadable);
		}
	}

	@Override
	public void shutdown() {

	}

	@Override
	public int order() {
		return ModuleInitOrder.configModule;
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
		for (int i = 0; i < reloadList.size(); i++) {
			Reloadable r = reloadList.get(i);
			long lastModifyTime = FileUtil.lastModifyTime(user_dir + r.l.path());
			if (r.lastLoadTime < lastModifyTime) {
				r.lastLoadTime = lastModifyTime;
				r.l.init();
			}
		}
	}
}
