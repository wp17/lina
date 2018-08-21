package com.github.wp17.lina.config;

import com.github.wp17.lina.common.interfaces.Init;

public interface Loadable extends Init{
//	default void register(){
//		ConfigLoadModule.getInstance().register(this);
//	}
	
	default String path(){
		return null;
	}
}
