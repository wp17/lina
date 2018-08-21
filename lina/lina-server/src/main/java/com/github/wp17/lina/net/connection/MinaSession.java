package com.github.wp17.lina.net.connection;

import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;

import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.net.LogicFuture;
import com.github.wp17.lina.common.net.LogicSession;
import com.github.wp17.lina.net.future.MinaWriteFuture;

public class MinaSession extends LogicSession {
	private static final AttributeKey OBJ_KEY = new AttributeKey(LogicSession.class, "session.logicObj");
	private static final AttributeKey CONNECTION_KEY = new AttributeKey(LogicSession.class, "session.connection");
	
	public static LogicSession getLogicSession(IoSession session){
		return (LogicSession) session.getAttribute(CONNECTION_KEY);
	}
	
	private IoSession session;
	
	public MinaSession(IoSession session){
		this.session = session;
		session.setAttribute(CONNECTION_KEY, this);
	}
	
	@Override
	public LogicFuture<Object> sendMsg(IMessage message){
		return new MinaWriteFuture(session.write(message));
	}
	
	@Override
	public void setObj(Object obj){
		session.setAttribute(OBJ_KEY, obj);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getObj() {
		try {
			return (T)session.getAttribute(OBJ_KEY);
		} catch (Exception e) {
			return null;
		}
	}
}
