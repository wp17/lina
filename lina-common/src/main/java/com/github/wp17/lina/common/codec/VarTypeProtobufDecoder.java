package com.github.wp17.lina.common.codec;

import com.github.wp17.lina.common.net.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class VarTypeProtobufDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        while (in.readableBytes() >= IPacket.HEADER_LEAGTH) {
            in.markReaderIndex();

            PacketHeader header = new PacketHeader();
            byte[] headerData = new byte[IPacket.HEADER_LEAGTH];
            in.readBytes(headerData);
            header.decode(headerData);

            int length = header.getBodyLength();
            if (in.readableBytes() < length) {
                in.resetReaderIndex();
                return;
            }

            byte[] data = new byte[length];
            in.readBytes(data);

            DefaultPacket packet = new DefaultPacket(data, header);
            out.add(packet);
        }
    }
}
