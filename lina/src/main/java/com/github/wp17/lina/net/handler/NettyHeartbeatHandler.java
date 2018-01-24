package com.github.wp17.lina.net.handler;

import com.github.wp17.lina.message.msgs.HearBeatMessage;
import com.github.wp17.lina.net.packet.NettyPacket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

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
			 ctx.writeAndFlush(HEARTBEAT.duplicate());
		}else {
			super.userEventTriggered(ctx, evt);
		}
	}
}
