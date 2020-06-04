package com.github.wp17.lina.game.netty.handler;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.common.net.NettySession;
import com.github.wp17.lina.game.module.msg.GameMsgId;
import com.github.wp17.lina.game.module.net.NetModule;
import com.github.wp17.lina.proto.msg.LoginProto;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理私有协议的握手逻辑
 */
@Sharable
public class GameHandshakeHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        DefaultPacket packet = (DefaultPacket) msg;
        if (packet.header.getMsgId() != GameMsgId.c2g_handshake_ack) {
            ctx.fireChannelRead(msg);
            return;
        }

        NettySession session = NettySession.getLogicSession(ctx.channel());
        NetModule.getInstance().remSession(session.getId());
        if (session.isClose()) {
            ctx.close();
            LoggerProvider.addExceptionLog("握手超时");
            return;
        }
        try {
            LoginProto.HandshakeAck handshakeAck = LoginProto.HandshakeAck.parseFrom(packet.body);
        } catch (InvalidProtocolBufferException e) {
            ctx.close();
            LoggerProvider.addExceptionLog("握手协议解析失败");
        }
        session.verified(true);
    }
}
