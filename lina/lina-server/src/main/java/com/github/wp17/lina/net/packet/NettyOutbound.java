package com.github.wp17.lina.net.packet;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.net.Outbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class NettyOutbound implements Outbound {
	private ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(1024);
	
	public ByteBuf getByteBuf(){
		return buf;
	}
	
	@Override
	public void writeBoolean(boolean value) {
		buf.writeBoolean(value);

	}

	@Override
	public void writeByte(byte value) {
		buf.writeByte(value);

	}

	@Override
	public void writeShort(short value) {
		buf.writeShort(value);

	}

	@Override
	public void writeInt(int value) {
		buf.writeInt(value);

	}

	@Override
	public void writeLong(long value) {
		buf.writeLong(value);

	}

	@Override
	public void writeChar(char value) {
		buf.writeChar(value);

	}

	@Override
	public void writeFloat(float value) {
		buf.writeFloat(value);

	}

	@Override
	public void writeDouble(double value) {
		buf.writeDouble(value);

	}

	@Override
	public void writeString(String value) {
		if (null == value) {
			buf.writeInt(0);
			return;
		}
		
		byte[] bytes = null;
		try {
			bytes = value.getBytes("UTF-8");
		} catch (Exception e) {
			buf.writeInt(0);
			LoggerProvider.addExceptionLog(e);
			return;
		}
		
		if (null != bytes) {
			buf.writeInt(bytes.length);
			buf.writeBytes(bytes);
		}else {
			buf.writeInt(0);
		}
	}
	
	public void release(){
		buf.release();
	}
}
