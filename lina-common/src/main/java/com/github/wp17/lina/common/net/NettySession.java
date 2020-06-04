package com.github.wp17.lina.common.net;

import com.github.wp17.lina.common.interfaces.IObj;
import com.github.wp17.lina.common.util.CommonConst;
import com.github.wp17.lina.common.util.CommonUtil;
import com.github.wp17.lina.util.RandomUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Future;
@Slf4j
/**封装一条socket*/
public class NettySession extends AbstractSession {
    private static final AttributeKey<Object> SESSION_OBJ_KEY =
            AttributeKey.valueOf(NettySession.class, "session_obj_key");
    private static final AttributeKey<NettySession> SESSION_CONNECTION =
            AttributeKey.valueOf(NettySession.class, "session_connection");
    private static final AttributeKey<Boolean> SESSION_VERIFY =
            AttributeKey.valueOf(NettySession.class, "session_verify");

    private final Channel channel;
    private final long createTime;
    private volatile int salt = -1;
    private volatile boolean close = false;

    public NettySession(Channel channel) {
        Attribute<NettySession> attribute = channel.attr(SESSION_CONNECTION);
        attribute.set(this);
        this.channel = channel;
        createTime = System.currentTimeMillis();
        salt = RandomUtil.random.nextInt();
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
        packet.setSeq(getNextOutgoingSeq());// hole
        return channel.writeAndFlush(packet);
    }

    @Override
    public void close() {
        close = true;
        if (channel.isActive()) {
            ChannelFuture future = channel.close();
            future.syncUninterruptibly();
        }
        log.info("session({}) is close", getId());
    }

    @Override
    public boolean verified() {
        return channel.attr(SESSION_VERIFY).get();
    }

    @Override
    public void verified(boolean verify) {
        channel.attr(SESSION_VERIFY).set(verify);
    }

    public boolean isClose(){
        return close;
    }

    public String getId() {
        return channel.id().asLongText();
    }

    public boolean verify(long now) {
        /**验证是否长时间没有完成握手协议*/
        if (!verified() && now - createTime > CommonConst.mills_of_minute * 3) {
            return false;
        }
        return !close;
    }

    public int getSalt() {
        return salt;
    }

    public void setSalt(int salt) {
        this.salt = salt;
    }
}
