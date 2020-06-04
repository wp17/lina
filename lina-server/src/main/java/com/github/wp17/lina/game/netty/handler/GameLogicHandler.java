package com.github.wp17.lina.game.netty.handler;

import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.game.module.msg.GameMessageModule;
import com.github.wp17.lina.game.module.msg.GameMsgId;
import com.github.wp17.lina.game.module.net.NetModule;
import com.github.wp17.lina.proto.msg.LoginProto;
import com.github.wp17.lina.util.encrypt.Crypt;
import org.slf4j.Logger;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.net.NettySession;
import com.github.wp17.lina.game.logic.Role;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class GameLogicHandler extends SimpleChannelInboundHandler<DefaultPacket> {
	private static final Logger log = LoggerProvider.getLogger(GameLogicHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		NettySession session = new NettySession(ctx.channel());
		NetModule.getInstance().addSession(session);

		DefaultPacket packet = buildHandshakeSyn(session);
		session.sendMsg(packet);
		super.channelActive(ctx);
	}

	/**
	 * 构造握手协议
	 */
	private DefaultPacket buildHandshakeSyn(NettySession session) {
		LoginProto.HandshakeSyn.Builder handshakeSyn = LoginProto.HandshakeSyn.newBuilder();
		handshakeSyn.setSalt(session.getSalt());
		DefaultPacket packet = new DefaultPacket(handshakeSyn.build().toByteArray());
		packet.setMsgId(GameMsgId.g2c_handshake_syn);
		packet.setCheckSum(Crypt.getCRC16(packet.body, 0, packet.header.getBodyLength(), session.getSalt()));
		return packet;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DefaultPacket packet) {
		NettySession session = NettySession.getLogicSession(ctx.channel());
		boolean isValid = isValid(packet, session);
		if (!isValid) {
			ctx.close();
		}
		if (packet.getMsgId() == GameMsgId.c2g_handshake_ack) {
			ctx.fireChannelRead(packet);
			return;
		}
		if (!session.verified()) {
			LoggerProvider.addExceptionLog(new NullPointerException("role login but session verify is false"));
			ctx.close();
			return;
		}
		if (packet.getMsgId() == GameMsgId.c2g_login_req) {
			GameMessageModule.getInstance().getProcessor(GameMsgId.c2g_login_req).process(session, packet);
			return;
		}

		Role role = session.<Role>getObj();
		if (null == role) {
			LoggerProvider.addExceptionLog(new NullPointerException("channel associated role is null"));
			ctx.close();
			return;
		}
		session.addMsg(packet);
	}

	private boolean isValid(DefaultPacket packet, NettySession session) {
		int checkSum = packet.getCheckSum();
		int expectCheckSum = Crypt.getCRC16(packet.body, 0, packet.getBodyLength(), session.getSalt());
		if (checkSum != -1 && checkSum != expectCheckSum) {
			LoggerProvider.addExceptionLog(new Exception("checkSum is invalid"));
			return false;
		}
		int seq = packet.getSeq();
		int expectSeq = session.getNextIncomingSeq();
		if (seq != expectSeq) {
			LoggerProvider.addExceptionLog(new Exception("expect seq is " + expectSeq + ", factual is " + seq));
			return false;
		}
		return true;
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		NettySession session = NettySession.getLogicSession(ctx.channel());
		if (session != null) {
			Role role = session.<Role>getObj();
			if (null != role && !role.getDownLine().get()) {
				role.downLine();
			}else {
				if (!session.isClose()){
					session.close();
				}
			}
		}
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		NettySession session = NettySession.getLogicSession(ctx.channel());
		if (null != session) {
			if (!session.isClose()){
				session.close();
			}
		}else {
			ctx.close();
		}
		log.error("handler error", cause);
	}
}
