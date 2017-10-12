package com.github.wp17.lina.net.connection;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;

import com.github.wp17.lina.message.IMessage;

public class LogicSession {
	private static final AttributeKey OBJ_KEY = new AttributeKey(LogicSession.class, "session.logicObj");
	private static final AttributeKey CONNECTION_KEY = new AttributeKey(LogicSession.class, "session.connection");
	
	public static LogicSession getLogicSession(IoSession session){
		return (LogicSession) session.getAttribute(CONNECTION_KEY);
	}
	
	private IoSession session;
	private int nextOutgoingSeq = 0;
	private int nextIngoingSeq = 0;
	
	private final Queue<IMessage> messages = new ConcurrentLinkedQueue<IMessage>();
	
	public LogicSession(IoSession session){
		this.session = session;
		session.setAttribute(CONNECTION_KEY, this);
	}
	
	public WriteFuture sendMsg(IMessage message){
		return session.write(message);
	}
	
	public int getNextOutgoingSeq() {
		return ++nextOutgoingSeq;
	}
	
	public int getNextIngoingSeq() {
		return ++nextIngoingSeq;
	}
	
	public void setObj(Object obj){
		session.setAttribute(OBJ_KEY, obj);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getObj(){
		try {
			return (T)session.getAttribute(OBJ_KEY);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void addMsg(IMessage message){
		messages.offer(message);
	}
	
	public IMessage pollMsg(){
		return messages.poll();
	}
}
