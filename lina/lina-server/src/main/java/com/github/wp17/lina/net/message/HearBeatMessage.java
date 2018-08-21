package com.github.wp17.lina.net.message;

import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.net.Inbound;
import com.github.wp17.lina.common.net.Outbound;

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
