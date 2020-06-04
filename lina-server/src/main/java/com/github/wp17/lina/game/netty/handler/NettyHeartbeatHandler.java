package com.github.wp17.lina.game.netty.handler;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.common.net.NettySession;

import com.github.wp17.lina.proto.msg.LoginProto;
import com.github.wp17.lina.proto.msg.Options;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;

/**no share*/
public class NettyHeartbeatHandler extends ChannelInboundHandlerAdapter {
	Logger logger = LoggerProvider.getLogger(NettyHeartbeatHandler.class);
    private static final DefaultPacket HEARTBEAT;
    static {
        HEARTBEAT = new DefaultPacket(LoginProto.HeartBeatReq.getDefaultInstance().toByteArray());
        int msgId = LoginProto.HeartBeatReq.getDescriptor().getOptions().getExtension(Options.messageId);
        HEARTBEAT.setMsgId(msgId);
        HEARTBEAT.setCheckSum(-1);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            logger.info("触发了 IdleStateEvent");
            NettySession session = NettySession.getLogicSession(ctx.channel());
            HEARTBEAT.setSeq(session.getNextOutgoingSeq());
            /**在 Netty 发送消息可以采用两种方式：直接写消息给 Channel 或者写入 ChannelHandlerContext 对象。
             * 这两者主要的区别是， 前一种方法会导致消息从 ChannelPipeline的尾部开始，
             * 而后者导致消息从 ChannelPipeline 下一个处理器开始。*/
            ctx.channel().writeAndFlush(HEARTBEAT);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
