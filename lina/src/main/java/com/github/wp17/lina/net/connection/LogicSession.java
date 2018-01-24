package com.github.wp17.lina.net.connection;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.net.future.LogicFuture;

public abstract class LogicSession {
	private int outgoingSeq = 0;
	private int incomingSeq = 0;
	
	private final Queue<IMessage> messages = new ConcurrentLinkedQueue<IMessage>();
	
	public abstract <T> T getObj();
	public abstract void setObj(Object obj);
	
	public abstract LogicFuture<Object> sendMsg(IMessage message);
	
	public int getNextOutgoingSeq() {
		return ++outgoingSeq;
	}
	
	public int getNextIncomingSeq() {
		return ++incomingSeq;
	}
	
	public void addMsg(IMessage message){
		messages.offer(message);
	}
	
	public IMessage pollMsg(){
		return messages.poll();
	}
}
