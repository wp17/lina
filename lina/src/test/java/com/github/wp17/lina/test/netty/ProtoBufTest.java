package com.github.wp17.lina.test.netty;

import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

import org.junit.Assert;
import org.junit.Test;

import com.github.wp17.lina.protomessage.msgs.AddressBookProtos;
import com.github.wp17.lina.protomessage.msgs.AddressBookProtos.AddressBook;
import com.github.wp17.lina.protomessage.msgs.AddressBookProtos.Person;
import com.github.wp17.lina.protomessage.msgs.AddressBookProtos.AddressBook.Builder;
import com.github.wp17.lina.protomessage.msgs.AddressBookProtos.Person.PhoneNumber;
import com.github.wp17.lina.protomessage.msgs.AddressBookProtos.Person.PhoneType;
import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoBufTest {
	public byte[] encode(AddressBookProtos.AddressBook addressBook){
		return addressBook.toByteArray();
	}
	
	public AddressBook decode(byte[] data) throws InvalidProtocolBufferException{
		return AddressBook.parseFrom(data);
	}
	
	public AddressBook createAddressBook(){
		com.github.wp17.lina.protomessage.msgs.AddressBookProtos.Person.PhoneNumber.Builder phoneBuilder = PhoneNumber.newBuilder();
		phoneBuilder.setNumber("18311283060");
		phoneBuilder.setType(PhoneType.MOBILE);
		PhoneNumber phoneNumber = phoneBuilder.build();
		
		com.github.wp17.lina.protomessage.msgs.AddressBookProtos.Person.Builder personBuilder = Person.newBuilder();
		personBuilder.setId(1);
		personBuilder.setEmail("wangpg126@126.com");
		personBuilder.setName("peter");
		personBuilder.addPhones(phoneNumber);
		Person person = personBuilder.build();
		
		Builder builder = AddressBook.newBuilder();
		builder.addPeople(person);
		return builder.build();
	}
	
	@Test
	public void codec() throws InvalidProtocolBufferException{
		AddressBook addressBook = createAddressBook();
		
		AddressBook addressBook2 = decode(encode(addressBook));
		Assert.assertTrue(addressBook.equals(addressBook2));
		System.out.println(addressBook2);
		
		ProtobufVarint32FrameDecoder frameDecoder = new ProtobufVarint32FrameDecoder();
		ProtobufDecoder decoder = new ProtobufDecoder(addressBook, null);
		
		ProtobufEncoder encoder = new ProtobufEncoder();
	}
}
