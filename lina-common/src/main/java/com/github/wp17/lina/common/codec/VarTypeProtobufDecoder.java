package com.github.wp17.lina.common.codec;

import com.github.wp17.lina.common.net.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.github.wp17.lina.common.log.LoggerProvider;

public class VarTypeProtobufDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        try {
            doDecode(ctx, in, out);
        } catch (Exception e) {
            LoggerProvider.addExceptionLog(e);
        } finally {
//            ReferenceCountUtil.release(in);
        }
    }

    private void doDecode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() > IPacket.HEADER_LEAGTH) {
            in.markReaderIndex();

            //读取header
            PacketHeader header = new PacketHeader();
            byte[] headerData = new byte[IPacket.HEADER_LEAGTH];
            in.readBytes(headerData);
            header.decode(headerData);

            int length = header.getBodyLength();
            if (in.readableBytes() < length) {
                in.resetReaderIndex();
                return;
            }

            /**读取body*/
            byte[] data = new byte[length];
            in.readBytes(data);

            int checkSum = header.getCheckSum();
            if (checkSum != -1) {
                LoggerProvider.addExceptionLog(new Exception("checkSum != -1"));
                ctx.close();
            }

            int seq = header.getSeq();
            AbstractSession session = NettySession.getLogicSession(ctx.channel());
            int expectSeq = session.getNextIncomingSeq();
            if (seq != expectSeq) {
                LoggerProvider.addExceptionLog(new Exception("expect seq is " + expectSeq + ", factual is " + seq));
                ctx.close();
            }

            DefaultPacket packet = new DefaultPacket(data);
            packet.setMsgID(header.getMsgId());
            packet.setCheckSum(checkSum);
            packet.setSeq(seq);
            packet.setBodyLength(length);

            out.add(packet);
        }
    }
}
