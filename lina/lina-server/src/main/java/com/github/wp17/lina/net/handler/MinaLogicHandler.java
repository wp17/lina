package com.github.wp17.lina.net.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.net.LogicSession;
import com.github.wp17.lina.net.connection.MinaSession;
import com.github.wp17.lina.server.line.LineServerModule;
import com.github.wp17.lina.server.logic.ObjType;
import com.github.wp17.lina.server.logic.Role;

@Component
public class MinaLogicHandler extends IoHandlerAdapter {
	private static final Logger logger = LoggerProvider.getLogger(MinaLogicHandler.class);
	@Autowired private LineServerModule lineServerModule;
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		LogicSession logicSession = MinaSession.getLogicSession(session);
		if (null != logicSession) {
			logicSession.addMsg((IMessage)message);
		}
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		LoggerProvider.addExceptionLog("", cause);
	}
	
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		LogicSession logicSession = new MinaSession(session);
		Role role = new Role(logicSession, ObjType.ROLE);
		lineServerModule.addRole(role);
		
		logger.info("create a new role");
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		LogicSession logicSession = MinaSession.getLogicSession(session);
		if (null != logicSession) {
			Role r = logicSession.getObj();
			if (null != r) {
				r.downline();
				logger.info("role(id={}) is downline", r.getRoleUuid());
			}
		}
	}
}
