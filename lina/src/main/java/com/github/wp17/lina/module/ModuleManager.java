package com.github.wp17.lina.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.github.wp17.lina.interfaces.Destory;
import com.github.wp17.lina.interfaces.Init;

public class ModuleManager implements Init, Destory{
	private ModuleManager(){}
	private static ModuleManager instance = new ModuleManager();
	public static ModuleManager getInstance(){
		return instance;
	}
	
	private List<IModule> modules = new ArrayList<IModule>();

	@Override
	public void init() {
		Collections.sort(modules, new Comparator<IModule>() {
			@Override
			public int compare(IModule o1, IModule o2) {
				return o1.order() - o2.order();
			}
		});
		
		for (IModule iModule : modules) {
			iModule.init();
		}
	}

	@Override
	public void destory() {
		for (IModule iModule : modules) {
			iModule.destory();
		}
	}
	
	public void register(IModule module){
		modules.add(module);
	}
}
