package com.github.wp17.lina.net.packet;

import com.github.wp17.lina.message.IMessage;

public interface Packet <T>{
	/**消息头总长度*/
	public static final int HEADER_LEAGTH = 14;
	/**长度字段的长度*/
	public static final int LENGTHFIELD_LENGTH = 4;
	/**长度字段的偏移量*/
	public static final int LENGTHFIELD_OFFSET = 10;
	
	T encode(IMessage msg);
	void decode();
}
