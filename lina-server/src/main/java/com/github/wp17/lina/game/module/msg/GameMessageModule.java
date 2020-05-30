package com.github.wp17.lina.game.module.msg;

import com.github.wp17.lina.common.msg.AbsMsgService;
import com.github.wp17.lina.game.module.AbsModule;

public class GameMessageModule extends AbsMsgService implements AbsModule {
	
	private GameMessageModule(){}
	private static final GameMessageModule instance = new GameMessageModule();
	public static GameMessageModule getInstance(){
		return instance;
	}

	@Override
	protected String getPackage() {
		return "com.github.wp17.lina.game.module.msg.processor";
	}

	@Override
	public void shutdown() {

	}
}
