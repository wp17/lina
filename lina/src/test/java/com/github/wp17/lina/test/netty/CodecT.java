package com.github.wp17.lina.test.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.wp17.lina.log.LogModule;
import com.github.wp17.lina.log.LoggerProvider;
import com.github.wp17.lina.message.msgs.TestMessage;
import com.github.wp17.lina.net.codec.netty.priv.NettyByte2MsgDecoder;
import com.github.wp17.lina.net.packet.Packet;

public class CodecT {

	@Before
	public void init(){
		LogModule.getInstance().init();
	}
	
	@Test
	public void codec(){
		EmbeddedChannel channel = new EmbeddedChannel(
				new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, Packet.HEADER_LEAGTH-Packet.LENGTHFIELD_LENGTH, Packet.LENGTHFIELD_LENGTH),
				new NettyByte2MsgDecoder());
		
		TestMessage msg = new TestMessage();
		msg.setId(100);
		msg.setAge(21);
		msg.setName("wang鹏");
		
		ChannelFuture future = channel.writeOneInbound(msg);
		channel.flush();
		Assert.assertTrue(future.isSuccess());

        Assert.assertTrue(channel.finish());
        TestMessage message = channel.readInbound();
		
		LoggerProvider.getLogger(CodecT.class).info(message.toString());
	}
}
