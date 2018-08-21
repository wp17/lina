package com.github.wp17.lina.server.message.processor;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.net.message.TestMessage;
import com.github.wp17.lina.server.db.provider.UserDataProvider;
import com.github.wp17.lina.server.logic.Role;
import com.github.wp17.lina.server.logic.RoleMsgProcessor;

@Component
public class TestMsgProcessor extends RoleMsgProcessor {
	private static final Logger logger = LoggerProvider.getLogger(TestMsgProcessor.class);
	
	@Autowired UserDataProvider userDataProvider;
	
	@Override
	public void process(Role role, IMessage message) {
		logger.info(message.toString());
		
		TestMessage testMessage = new TestMessage();
		testMessage.setId(1);
		testMessage.setName(""+Math.random());
		testMessage.setAge(28);
		role.sendMsg(testMessage);
	}
}
