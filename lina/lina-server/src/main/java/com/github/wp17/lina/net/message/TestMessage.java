package com.github.wp17.lina.net.message;

import com.github.wp17.lina.common.message.AMsgProcessor;
import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.net.Inbound;
import com.github.wp17.lina.common.net.Outbound;
import com.github.wp17.lina.server.message.processor.TestMsgProcessor;
@AMsgProcessor(processor = TestMsgProcessor.class)
public class TestMessage implements IMessage {

	private long id;
	private String name;
	private int age;
	
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public void encode(Outbound s) {
		s.writeInt(age);
		s.writeString(name);
		s.writeLong(id);

	}

	@Override
	public void decode(Inbound s) {
		age = s.readInt();
		name = s.readString();
		id = s.readLong();

	}

	@Override
	public short getMsgID() {
		return 2;
	}

	@Override
	public String toString() {
		return "TestMessage [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
}