package com.github.wp17.lina.logic;

import org.slf4j.Logger;

import com.github.wp17.lina.log.LoggerProvider;
import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.message.IMsgProcessor;
import com.github.wp17.lina.net.connection.LogicSession;

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
