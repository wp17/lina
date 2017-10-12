package com.github.wp17.lina.module;

import com.github.wp17.lina.interfaces.Destory;
import com.github.wp17.lina.interfaces.Init;

public interface IModule extends Init, Destory {
	default int order(){
		return ModuleInitOrder.defaultOrder;
	}
	
	default void register(){
		ModuleManager.getInstance().register(this);
	}
}
