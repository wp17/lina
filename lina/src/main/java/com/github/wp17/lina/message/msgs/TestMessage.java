package com.github.wp17.lina.message.msgs;

import com.github.wp17.lina.message.AMsgProcessor;
import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.net.packet.Inbound;
import com.github.wp17.lina.net.packet.Outbound;

@AMsgProcessor(processor = TestMsgProcessor.class)
public class TestMessage implements IMessage {

	private long id;
	private String name;
	private byte age;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getAge() {
		return age;
	}

	public void setAge(byte age) {
		this.age = age;
	}

	@Override
	public void encode(Outbound s) {
		s.writeByte(age);
		s.writeString(name);
		s.writeLong(id);

	}

	@Override
	public void decode(Inbound s) {
		age = s.readByte();
		name = s.readString();
		id = s.readLong();

	}

	@Override
	public short getMsgID() {
		return 1;
	}

	@Override
	public String toString() {
		return "TestMessage [id=" + id + ", name=" + name + ", age=" + age
				+ "]";
	}
}
