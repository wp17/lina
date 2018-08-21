package com.github.wp17.lina.protomessage;

import com.github.wp17.lina.common.net.LogicSession;
import com.google.protobuf.MessageLite;

/**stateless*/
public interface IProtobufMsgProcessor {
	void processor(LogicSession session, MessageLite messageLite);
	MessageLite getMessageLite(byte[] data, int offset, int length);
}
