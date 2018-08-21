package com.github.wp17.lina.net.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.net.LogicSession;
import com.github.wp17.lina.net.connection.NettySession;
import com.github.wp17.lina.server.line.LineServerModule;
import com.github.wp17.lina.server.logic.ObjType;
import com.github.wp17.lina.server.logic.Role;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
@Component
public class NettyInboundLogicHandler extends SimpleChannelInboundHandler<IMessage> {
	private static final Logger logger = LoggerProvider.getLogger(NettyInboundLogicHandler.class);
	@Autowired private LineServerModule lineServerModule;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		NettySession session = new NettySession(ctx.channel());
		Role role = new Role(session, ObjType.ROLE);
		lineServerModule.addRole(role);

		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		LogicSession session = NettySession.getLogicSession(ctx.channel());
		Role role = session.<Role>getObj();
		lineServerModule.remRole(role);
		
		if (null != role) {
			role.downline();
			logger.info("role(id={}) is downline", role.getRoleUuid());
		}
		super.channelInactive(ctx);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IMessage msg) throws Exception {
		LogicSession session = NettySession.getLogicSession(ctx.channel());
		if (null != session) {
			session.addMsg(msg);
		}else {
			LoggerProvider.addExceptionLog(new NullPointerException("channel associated session is null"));
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("", cause);
		ctx.close();
	}
}
