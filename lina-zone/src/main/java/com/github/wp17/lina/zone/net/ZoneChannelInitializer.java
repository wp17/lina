package com.github.wp17.lina.zone.net;

import com.github.wp17.lina.common.codec.VarTypeProtobufDecoder;
import com.github.wp17.lina.common.codec.VarTypeProtobufEncoder;
import com.github.wp17.lina.common.net.IPacket;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

public class ZoneChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline()
                .addLast(
                        new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, Integer.MAX_VALUE,
                        IPacket.LENGTHFIELD_OFFSET, IPacket.LENGTHFIELD_LENGTH, 0,
                                0, true))
                .addLast(new VarTypeProtobufDecoder())
                .addLast(new VarTypeProtobufEncoder())
                .addLast(new ZoneIOHandler());
    }
}
