package com.github.wp17.lina.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaClientHandler extends IoHandlerAdapter {
	private static final Logger log = LoggerFactory.getLogger(MinaClientHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		log.info(message.toString());
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error("", cause);
		super.exceptionCaught(session, cause);
	}
}
