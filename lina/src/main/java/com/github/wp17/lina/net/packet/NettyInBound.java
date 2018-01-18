package com.github.wp17.lina.net.packet;

import java.io.UnsupportedEncodingException;

import com.github.wp17.lina.log.LoggerProvider;

import io.netty.buffer.ByteBuf;

public class NettyInBound implements Inbound {
	private ByteBuf byteBuf;
	
	public NettyInBound(ByteBuf buf){
		this.byteBuf = buf;
	}
	
	@Override
	public boolean readBoolean() {
		return byteBuf.readBoolean();
	}

	@Override
	public byte readByte() {
		return byteBuf.readByte();
	}

	@Override
	public short readShort() {
		return byteBuf.readShort();
	}

	@Override
	public int readInt() {
		return byteBuf.readInt();
	}

	@Override
	public long readLong() {
		return byteBuf.readLong();
	}

	@Override
	public char readChar() {
		return byteBuf.readChar();
	}

	@Override
	public float readFloat() {
		return byteBuf.readFloat();
	}

	@Override
	public double readDouble() {
		return byteBuf.readDouble();
	}

	@Override
	public String readString() {
		short length = byteBuf.readShort();
		byte[] bytes = new byte[length];
		byteBuf.readBytes(bytes);
		
		try {
			String string = new String(bytes, "UTF-8");
			return string;
		} catch (UnsupportedEncodingException e) {
			LoggerProvider.addExceptionLog(e);
		}
		return "";
	}

}
