package com.github.wp17.lina.net.handler;

import org.slf4j.Logger;

import com.github.wp17.lina.line.LineServerModule;
import com.github.wp17.lina.log.LoggerProvider;
import com.github.wp17.lina.logic.ObjType;
import com.github.wp17.lina.logic.Role;
import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.net.connection.LogicSession;
import com.github.wp17.lina.net.connection.NettySession;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class NettyInboundLogicHandler extends SimpleChannelInboundHandler<IMessage> {
	private static final Logger logger = LoggerProvider.getLogger(NettyInboundLogicHandler.class);
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		NettySession session = new NettySession(ctx.channel());
		Role role = new Role(session, ObjType.ROLE);
		LineServerModule.getInstance().addRole(role);

		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		LogicSession session = NettySession.getLogicSession(ctx.channel());
		Role role = session.<Role>getObj();
		LineServerModule.getInstance().remRole(role);
		
		if (null != role) {
			role.downline();
			logger.info("role(id={}) is downline", role.getRoleUuid());
		}
		super.channelInactive(ctx);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IMessage msg) throws Exception {
		LogicSession session = NettySession.getLogicSession(ctx.channel());
		session.addMsg(msg);
	}
}
