package com.github.wp17.lina.message.msgs;

import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.net.packet.Inbound;
import com.github.wp17.lina.net.packet.Outbound;

public class HearBeatMessage implements IMessage {

	@Override
	public void encode(Outbound s) {

	}

	@Override
	public void decode(Inbound s) {

	}

	@Override
	public short getMsgID() {
		return 0;
	}

}
