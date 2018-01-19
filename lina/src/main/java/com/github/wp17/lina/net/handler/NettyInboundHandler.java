package com.github.wp17.lina.net.handler;

import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.net.connection.LogicSession;
import com.github.wp17.lina.net.connection.NettySession;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyInboundHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		IMessage message = (IMessage) msg;
		LogicSession session = NettySession.getLogicSession(ctx.channel());
		session.addMsg(message);
	}
}
