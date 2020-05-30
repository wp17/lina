package com.github.wp17.lina.game.netty.handler;

import com.github.wp17.lina.common.net.DefaultPacket;
import org.slf4j.Logger;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.net.NettySession;
import com.github.wp17.lina.game.module.line.LineServerModule;
import com.github.wp17.lina.game.logic.ObjType;
import com.github.wp17.lina.game.logic.Role;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class GameLogicHandler extends SimpleChannelInboundHandler<DefaultPacket> {
	private static final Logger logger = LoggerProvider.getLogger(GameLogicHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		NettySession session = new NettySession(ctx.channel());
		Role role = new Role(session, ObjType.ROLE);
		LineServerModule.getInstance().addRole(role);

		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		NettySession session = NettySession.getLogicSession(ctx.channel());
		Role role = session.<Role>getObj();
		if (null != role) {
			role.downLine();
			logger.info("role(id={}) is down", role.getRoleUuid());
		}
		super.channelInactive(ctx);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DefaultPacket packet) {
		NettySession session = NettySession.getLogicSession(ctx.channel());
		if (null == session) {
			ctx.close();
			LoggerProvider.addExceptionLog(new NullPointerException("channel associated session is null"));
			return;
		}
		Role role = session.<Role>getObj();
		if (null == role) {
			ctx.close();
			return;
		}
		if(!role.verified()) {
			role.downLine();
			return;
		}
		session.addMsg(packet);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error("handler error", cause);
		ctx.close();
	}
}
