package com.github.wp17.lina.common.message;

import com.github.wp17.lina.common.net.Inbound;
import com.github.wp17.lina.common.net.Outbound;

public interface IMessage {
	void encode(Outbound s);
	void decode(Inbound s);
	short getMsgID();
}
