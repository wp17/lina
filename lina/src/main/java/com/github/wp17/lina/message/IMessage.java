package com.github.wp17.lina.message;

import com.github.wp17.lina.net.packet.Inbound;
import com.github.wp17.lina.net.packet.Outbound;

public interface IMessage {
	void encode(Outbound s);
	void decode(Inbound s);
	short getMsgID();
}
