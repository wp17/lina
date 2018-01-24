package com.github.wp17.lina.net.codec.netty.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.github.wp17.lina.log.LoggerProvider;
import com.github.wp17.lina.message.proto.AddressBookProtos.AddressBook;
import com.github.wp17.lina.message.proto.AddressBookProtos.Person;
import com.github.wp17.lina.net.connection.LogicSession;
import com.github.wp17.lina.net.connection.NettySession;
import com.github.wp17.lina.net.packet.Packet;
import com.google.protobuf.MessageLite;

public class VarTypeProtobufDecoder extends ByteToMessageDecoder{
	@Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() > Packet.HEADER_LEAGTH) {
            in.markReaderIndex();

            int msgId = in.readShortLE();
            
            int checkSum = in.readIntLE();
            if (checkSum != -1) {
				LoggerProvider.addExceptionLog(new Exception("checkSum != -1"));
				ctx.close();
			}
            
            int seq = in.readIntLE();
            LogicSession session = NettySession.getLogicSession(ctx.channel());
            int expectSeq = session.getNextIncomingSeq();
            if (seq != expectSeq) {
            	LoggerProvider.addExceptionLog(new Exception("expect seq is "+expectSeq+", factual is "+seq));
            	ctx.close();
			}
            
            // 获取长度字段
            byte lb0 = in.readByte();
            byte lb1 = in.readByte();
            byte lb2 = in.readByte();
            byte lb3 = in.readByte();
            
            int s0 = (int) (lb0 & 0xff);
            int s1 = (int) (lb1 & 0xff);
            int s2 = (int) (lb2 & 0xff);
            int s3 = (int) (lb3 & 0xff);
            
            s1 <<= 8;
            s2 <<= 16;
            s3 <<= 24;
            int length = (s0 | s1 | s2 | s3);

            if (in.readableBytes() < length) {
                in.resetReaderIndex();
                return;
            }

            // 读取body
            ByteBuf bodyBuf = in.readBytes(length);

            byte[] data;
            int offset;

            int readableLen= bodyBuf.readableBytes();
            if (bodyBuf.hasArray()) {
                data = bodyBuf.array();
                offset = bodyBuf.arrayOffset() + bodyBuf.readerIndex();
            } else {
                data = new byte[readableLen];
                bodyBuf.getBytes(bodyBuf.readerIndex(), data, 0, readableLen);
                offset = 0;
            }
            
            MessageLite result = decodeBody(msgId, data, offset, readableLen);
            out.add(result);
        }
    }

    public MessageLite decodeBody(int msgId, byte[] data, int offset, int length) throws Exception {
        if (msgId == 0x00) {
        	return AddressBook.getDefaultInstance().getParserForType().parseFrom(data, offset, length);

        } else if (msgId == 0x01) {
           return Person.getDefaultInstance().getParserForType().parseFrom(data, offset, length);
        }

        return null;
    }
}
