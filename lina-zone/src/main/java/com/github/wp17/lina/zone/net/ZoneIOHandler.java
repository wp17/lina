package com.github.wp17.lina.zone.net;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.net.DefaultConnection;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.common.net.NettySession;
import com.github.wp17.lina.zone.msg.ZoneMsgModule;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;

public class ZoneIOHandler extends SimpleChannelInboundHandler<DefaultPacket> {
    private static final Logger logger = LoggerProvider.getLogger(ZoneIOHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettySession session = new NettySession(ctx.channel());
        DefaultConnection connection = new DefaultConnection(session);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DefaultPacket packet) {
        NettySession session = NettySession.getLogicSession(ctx.channel());
        if (null != session) {
            ZoneMsgModule.getInstance().process(session, packet);
        }else {
            LoggerProvider.addExceptionLog(new NullPointerException("channel associated session is null"));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("rank handler error", cause);
        NettySession session = NettySession.getLogicSession(ctx.channel());
        session.close();
        ctx.flush().close().syncUninterruptibly();
    }
}
