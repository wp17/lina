package com.github.wp17.lina.server.logic;

import org.slf4j.Logger;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.message.IMsgProcessor;
import com.github.wp17.lina.common.net.LogicSession;

public abstract class RoleMsgProcessor implements IMsgProcessor {
	private static final Logger logger = LoggerProvider.getLogger(RoleMsgProcessor.class);
	@Override
	public void process(LogicSession session, IMessage message) {
		if (null != session) {
			Role role = session.getObj();
			process(role, message);
		}else {
			logger.info(message.toString());
		}
	}
	
	public abstract void process(Role role, IMessage message);
}
