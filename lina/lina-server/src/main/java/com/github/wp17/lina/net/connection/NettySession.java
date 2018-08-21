package com.github.wp17.lina.net.connection;

import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.net.LogicFuture;
import com.github.wp17.lina.common.net.LogicSession;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class NettySession extends LogicSession {
	private static final AttributeKey<Object> SESSION_OBJ_KEY = AttributeKey.valueOf(NettySession.class, "session_obj_key");
	private static final AttributeKey<LogicSession> SESSION_CONNECTION = AttributeKey.valueOf(NettySession.class, "session_connection");
	private Channel channel;
	
	public NettySession(Channel channel){
		Attribute<LogicSession> attribute = channel.attr(SESSION_CONNECTION);
		attribute.set(this);
		this.channel = channel;
	}
	
	public static LogicSession getLogicSession(Channel channel){
		Attribute<LogicSession> attribute = channel.attr(SESSION_CONNECTION);
		return attribute.get();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getObj() {
		Attribute<Object> attribute = channel.attr(SESSION_OBJ_KEY);
		return (T) attribute.get();
	}

	@Override
	public void setObj(Object obj) {
		Attribute<Object> attribute = channel.attr(SESSION_OBJ_KEY);
		attribute.set(obj);
	}

	@Override
	public LogicFuture<Object> sendMsg(IMessage msg) {
		channel.writeAndFlush(msg);
		return null;
	}

}
