package com.github.wp17.lina.net.handler;

import org.springframework.stereotype.Component;

import com.github.wp17.lina.net.message.HearBeatMessage;
import com.github.wp17.lina.net.packet.NettyPacket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

@Component
public class NettyHeartbeatHandler extends ChannelInboundHandlerAdapter {
	  private static final ByteBuf HEARTBEAT = Unpooled.buffer(20);
	  
	  static{
		  NettyPacket packet = new NettyPacket();
		  packet.setSeq(-1);
		  HearBeatMessage hearBeat = new HearBeatMessage();
		  HEARTBEAT.writeBytes(packet.encode(hearBeat));
	  }
	  
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			/**在 Netty 发送消息可以采用两种方式：直接写消息给 Channel 或者写入 ChannelHandlerContext 对象。这两者主要的区别是， 前一种方法会导致消息从 ChannelPipeline的尾部开始，而后者导致消息从 ChannelPipeline 下一个处理器开始。*/
			ctx.writeAndFlush(HEARTBEAT.duplicate());
		}else {
			super.userEventTriggered(ctx, evt);
		}
	}
}
