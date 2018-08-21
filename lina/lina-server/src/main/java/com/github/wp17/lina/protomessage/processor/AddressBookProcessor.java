package com.github.wp17.lina.protomessage.processor;

import com.github.wp17.lina.protomessage.MessageClazz;
import com.github.wp17.lina.protomessage.MessageID;
import com.github.wp17.lina.protomessage.RoleProtobufMsgProcessor;
import com.github.wp17.lina.protomessage.msgs.AddressBookProtos;
import com.github.wp17.lina.protomessage.msgs.AddressBookProtos.AddressBook;
import com.github.wp17.lina.server.logic.Role;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;

@MessageID(10000)
@MessageClazz(AddressBookProtos.AddressBook.class)
public class AddressBookProcessor extends RoleProtobufMsgProcessor{
	
	@Override
	public MessageLite getMessageLite(byte[] data, int offset, int length) {
		try {
			return AddressBook.getDefaultInstance().getParserForType().parseFrom(data, offset, length);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void processor(Role role, MessageLite messageLite) {
		AddressBook addressBook = (AddressBook) messageLite;
	}
}
