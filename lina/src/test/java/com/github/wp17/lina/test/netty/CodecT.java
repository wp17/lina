package com.github.wp17.lina.test.netty;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import org.junit.Test;

import com.github.wp17.lina.message.msgs.TestMessage;
import com.github.wp17.lina.net.codec.netty.NettyByte2MsgDecoder;
import com.github.wp17.lina.net.handler.NettyInboundLogicHandler;
import com.github.wp17.lina.net.packet.Packet;


public class CodecT {

	@Test
	public void codec(){
		EmbeddedChannel channel = new EmbeddedChannel(
				new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, Packet.HEADER_LEAGTH-Packet.LENGTHFIELD_LENGTH, Packet.LENGTHFIELD_LENGTH),
				new NettyByte2MsgDecoder()
				,new NettyInboundLogicHandler());
		
		TestMessage msg = new TestMessage();
		
		msg.setId(100);
		msg.setAge(21);
		msg.setName("wang鹏");
		channel.writeAndFlush(msg);
		
		TestMessage message = channel.readInbound();
		
		System.out.println(message);
	}
	
	public static void main(String[] args) {
		EmbeddedChannel channel = new EmbeddedChannel(
				new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, Packet.HEADER_LEAGTH-Packet.LENGTHFIELD_LENGTH, Packet.LENGTHFIELD_LENGTH),
				new NettyByte2MsgDecoder()
				,new NettyInboundLogicHandler());
		
		TestMessage msg = new TestMessage();
		
		msg.setId(100);
		msg.setAge(21);
		msg.setName("wang鹏");
		channel.writeAndFlush(msg);
		
		TestMessage message = channel.readOutbound();
		
		System.out.println(message);
	}
}
