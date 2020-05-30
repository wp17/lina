package com.github.wp17.lina.game.module.msg;

import com.github.wp17.lina.common.msg.IMsgProcessor;
import com.github.wp17.lina.common.net.AbstractPacket;
import com.github.wp17.lina.common.net.AbstractSession;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.game.logic.Role;

public abstract class RoleMsgProcessor implements IMsgProcessor {

	@Override
	public void process(AbstractSession session, AbstractPacket msg) {
		Role role = session.getObj();
		DefaultPacket packet = (DefaultPacket) msg;
		process(role,packet);
	}
	
	protected abstract void process(Role role, DefaultPacket msg);

}
