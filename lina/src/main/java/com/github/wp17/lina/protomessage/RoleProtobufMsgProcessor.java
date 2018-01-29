package com.github.wp17.lina.protomessage;

import com.github.wp17.lina.logic.Role;
import com.github.wp17.lina.net.connection.LogicSession;
import com.google.protobuf.MessageLite;

public abstract class RoleProtobufMsgProcessor implements IProtobufMsgProcessor {

	@Override
	public void processor(LogicSession session, MessageLite messageLite) {
		Role role = session.getObj();
		processor(role,messageLite);
	}
	
	protected abstract void processor(Role role, MessageLite messageLite);

}
