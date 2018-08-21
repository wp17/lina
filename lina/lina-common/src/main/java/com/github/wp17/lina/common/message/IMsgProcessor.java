package com.github.wp17.lina.common.message;

import com.github.wp17.lina.common.net.LogicSession;

public interface IMsgProcessor {
	void process(LogicSession session, IMessage message);
}
