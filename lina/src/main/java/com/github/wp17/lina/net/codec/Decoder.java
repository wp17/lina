package com.github.wp17.lina.net.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.message.MessageModule;
import com.github.wp17.lina.net.connection.LogicSession;
import com.github.wp17.lina.net.packet.Inbound;
import com.github.wp17.lina.net.packet.PacketHeader;

public class Decoder extends CumulativeProtocolDecoder {
	private static final Logger LOGGER = LoggerFactory.getLogger(Decoder.class);
	private static final AttributeKey HEADER_KEY = new AttributeKey(Decoder.class, "netpacket.header");
	@Override
	protected boolean doDecode(IoSession session, IoBuffer ioBuffer, ProtocolDecoderOutput output) throws Exception {
		
		if (ioBuffer.remaining() < PacketHeader.HEADER_LEAGTH) {
			return false;
		}
		
		PacketHeader header = (PacketHeader) session.getAttribute(HEADER_KEY);
		if (null == header) {
			header = new PacketHeader();
			header.decode(ioBuffer);
			session.setAttribute(HEADER_KEY, header);
		}
		
		int bodyLength = header.getBodyLength();
		if (ioBuffer.remaining() < bodyLength) {
			return false;
		}
		session.removeAttribute(HEADER_KEY);
		
		short msgId = header.getMsgID();
		
		IMessage message = MessageModule.getInstance().getMessage(msgId);
		if (null == message) {
			LOGGER.error("the message of msgid: "+msgId+" is null", new NullPointerException());
			return false;
		}
		
		LogicSession player = LogicSession.getLogicSession(session);
		if (null == player) {
			player = new LogicSession(session);
		}
		
		int except = player.getNextIngoingSeq();
		if (header.getSeq() != except) {
			LOGGER.error("message disorder, id: "+msgId+" except is "+except+", actual is "+header.getSeq());
			return false;
		}
		
		byte[] bytes = new byte[bodyLength];
		ioBuffer.get(bytes);
		IoBuffer body = IoBuffer.wrap(bytes);
		
		message.decode(new Inbound(body));
		output.write(message);
		return true;
	}
}
