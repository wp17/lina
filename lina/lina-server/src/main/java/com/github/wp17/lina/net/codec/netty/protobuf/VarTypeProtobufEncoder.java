package com.github.wp17.lina.net.codec.netty.protobuf;

import com.github.wp17.lina.common.net.LogicSession;
import com.github.wp17.lina.net.connection.NettySession;
import com.github.wp17.lina.protomessage.ProtoMessageModule;
import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class VarTypeProtobufEncoder extends MessageToByteEncoder<MessageLite> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageLite msg, ByteBuf out) throws Exception {
		LogicSession session = NettySession.getLogicSession(ctx.channel());
		int seq = session.getNextOutgoingSeq();
		
		byte[] body = msg.toByteArray();
		int length = body.length;
		
		short msgId = getMsgId(msg);
		int checkSum = -1;
		
        byte[] header = encodeHeader(msgId, length, seq, checkSum);
        
        out.writeBytes(header);
        out.writeBytes(body);
        return;
	}
	
	private short getMsgId(MessageLite msg){
		short msgId = ProtoMessageModule.getInstance().getMessageID(msg.getClass());
        return msgId;
	}
	
	private byte[] encodeHeader(short msgId, int bodyLength, int seq, int checkSum) {
        byte[] header = new byte[14];
        
        header[0] = (byte) (msgId & 0xff);
        header[1] = (byte) ((msgId >> 8) & 0xff);
        
        header[2] = (byte)(checkSum & 0Xff);
        header[3] = (byte)((checkSum >> 8) & 0Xff);
        header[4] = (byte)((checkSum >> 16) & 0Xff);
        header[5] = (byte)((checkSum >> 24)& 0Xff);
        
        header[6] = (byte) (seq & 0xff);
        header[7] = (byte) ((seq >> 8) & 0xff);
        header[8] = (byte) ((seq >> 16) & 0xff);
        header[9] = (byte) ((seq >> 24) & 0xff);
        
        header[10] = (byte) (bodyLength & 0xff);
        header[11] = (byte) ((bodyLength >> 8) & 0xff);
        header[12] = (byte) ((bodyLength >> 16) & 0xff);
        header[13] = (byte) ((bodyLength >> 24) & 0xff);
        
        return header;

    }

}
