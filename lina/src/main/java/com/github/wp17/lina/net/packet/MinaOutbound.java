package com.github.wp17.lina.net.packet;

import org.apache.mina.core.buffer.IoBuffer;

import com.github.wp17.lina.log.LoggerProvider;

public class MinaOutbound implements Outbound {
	private IoBuffer buffer;
	public IoBuffer getBuffer() {
		return buffer;
	}
	
	public MinaOutbound(){
		this.buffer = IoBuffer.allocate(20480).setAutoExpand(true);
	}

	@Override
	public void writeBoolean(boolean value) {
		if (value) {
			buffer.put((byte)1);
		}else {
			buffer.put((byte)0);
		}
	}
	
	@Override
	public void writeByte(byte value){
		buffer.put(value);
	}
	
	@Override
	public void writeShort(short value){
		buffer.putShort(value);
	}
	
	@Override
	public void writeInt(int value){
		buffer.putInt(value);
	}
	
	@Override
	public void writeLong(long value){
		buffer.putLong(value);
	}
	
	@Override
	public void writeChar(char value){
		buffer.putChar(value);
	}
	
	@Override
	public void writeFloat(float value) {
		buffer.putFloat(value);
		
	}

	@Override
	public void writeDouble(double value) {
		buffer.putDouble(value);
	}
	
	@Override
	public void writeString(String value){
		if (null == value) {
			buffer.putInt(0);
			return;
		}
		
		byte[] bytes = null;
		try {
			bytes = value.getBytes("UTF-8");
		} catch (Exception e) {
			buffer.putInt(0);
			LoggerProvider.addExceptionLog(e);
			return;
		}
		
		if (null != bytes) {
			buffer.putInt(bytes.length);
			buffer.put(bytes);
		}else {
			buffer.putInt(0);
		}
		
	}
}
