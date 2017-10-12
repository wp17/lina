package com.github.wp17.lina.message.msgs;

import org.slf4j.Logger;

import com.github.wp17.lina.log.LoggerProvider;
import com.github.wp17.lina.logic.Role;
import com.github.wp17.lina.logic.RoleMsgProcessor;
import com.github.wp17.lina.message.IMessage;

public class TestMsgProcessor extends RoleMsgProcessor {
	private static final Logger logger = LoggerProvider.getLogger(TestMsgProcessor.class);
	
	@Override
	public void process(Role role, IMessage message) {
		logger.info(message.toString());
	}
}
