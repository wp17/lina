package com.github.wp17.lina.net.handler;

import com.github.wp17.lina.line.LineServerModule;
import com.github.wp17.lina.logic.ObjType;
import com.github.wp17.lina.logic.Role;
import com.github.wp17.lina.net.connection.NettySession;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyConnectionHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		NettySession session = new NettySession(ctx.channel());
		Role role = new Role(session, ObjType.ROLE);
		LineServerModule.getInstance().addRole(role);
		
		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}

}
