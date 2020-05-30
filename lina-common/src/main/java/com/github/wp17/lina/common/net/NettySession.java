package com.github.wp17.lina.common.net;

import com.github.wp17.lina.common.interfaces.IObj;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.concurrent.Future;

public class NettySession extends AbstractSession {
    private static final AttributeKey<Object> SESSION_OBJ_KEY =
            AttributeKey.valueOf(NettySession.class, "session_obj_key");
    private static final AttributeKey<NettySession> SESSION_CONNECTION =
            AttributeKey.valueOf(NettySession.class, "session_connection");
    private Channel channel;

    private volatile boolean verified = false;

    public NettySession(Channel channel) {
        Attribute<NettySession> attribute = channel.attr(SESSION_CONNECTION);
        attribute.set(this);
        this.channel = channel;
    }

    public static NettySession getLogicSession(Channel channel) {
        Attribute<NettySession> attribute = channel.attr(SESSION_CONNECTION);
        return attribute.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IObj> T getObj() {
        Attribute<Object> attribute = channel.attr(SESSION_OBJ_KEY);
        return (T) attribute.get();
    }

    @Override
    public void setObj(Object obj) {
        Attribute<Object> attribute = channel.attr(SESSION_OBJ_KEY);
        attribute.set(obj);
    }

    @Override
    public Future sendMsg(AbstractPacket packet) {
        return channel.writeAndFlush(packet);
    }

    @Override
    public void close() {
        ChannelFuture future = channel.close();
        future.syncUninterruptibly();
    }

    public boolean verified() {
        return verified;
    }

    public void verified(boolean verified) {
        this.verified =  verified;
    }
}
