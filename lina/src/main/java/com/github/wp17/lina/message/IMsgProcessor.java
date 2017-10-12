package com.github.wp17.lina.message;

import com.github.wp17.lina.net.connection.LogicSession;

public interface IMsgProcessor {
	void process(LogicSession session, IMessage message);
}
