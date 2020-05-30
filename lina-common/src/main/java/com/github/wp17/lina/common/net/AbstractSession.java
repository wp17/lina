package com.github.wp17.lina.common.net;

import com.github.wp17.lina.common.interfaces.IObj;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

public abstract class AbstractSession {
	private int outgoingSeq = 0;
	private int incomingSeq = 0;
	
	private final Queue<AbstractPacket> messages = new ConcurrentLinkedQueue<AbstractPacket>();
	
	public abstract <T extends IObj> T getObj();
	public abstract void setObj(Object obj);
	public abstract Future sendMsg(AbstractPacket message);
	public abstract void close();
	public abstract boolean verified();
	public abstract void verified(boolean verified);
	
	public int getNextOutgoingSeq() {
		return ++outgoingSeq;
	}
	
	public int getNextIncomingSeq() {
		return ++incomingSeq;
	}
	
	public void addMsg(AbstractPacket message){
		messages.offer(message);
	}

	public AbstractPacket pollMsg(){
		return messages.poll();
	}
}
