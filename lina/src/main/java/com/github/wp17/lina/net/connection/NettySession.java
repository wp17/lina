package com.github.wp17.lina.net.connection;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.net.future.LogicFuture;

public class NettySession extends LogicSession {
	private static final AttributeKey<Object> SESSION_OBJ_KEY = AttributeKey.valueOf(NettySession.class, "session_obj_key");
	private static final AttributeKey<Object> SESSION_CONNECTION = AttributeKey.valueOf(NettySession.class, "session_connection");
	private Channel channel;
	public NettySession(Channel channel){
		this.channel = channel;
	}
	
	public static LogicSession getLogicSession(Channel channel){
		Attribute<Object> attribute = channel.attr(SESSION_CONNECTION);
		return (LogicSession) attribute.get();
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
	public LogicFuture<Object> sendMsg(IMessage message) {
		channel.writeAndFlush(message);
		return null;
	}

}
