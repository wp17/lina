package com.github.wp17.lina.client.net;

import com.github.wp17.lina.client.module.msg.ClientMsgId;
import com.github.wp17.lina.client.module.msg.ClientMsgModule;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.common.net.NettySession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientLogicHandler extends SimpleChannelInboundHandler<DefaultPacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettySession session = new NettySession(ctx.channel());

        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DefaultPacket packet) {
        NettySession session = NettySession.getLogicSession(ctx.channel());
        ClientMsgModule.getInstance().process(session, packet);
    }
}
