package com.github.wp17.lina.net.packet;

import java.io.UnsupportedEncodingException;

import org.apache.mina.core.buffer.IoBuffer;

public class Outbound {
	 private IoBuffer buffer;
	
	public Outbound(){
		this.buffer = IoBuffer.allocate(20480).setAutoExpand(true);
	}

	public void writeByte(byte value){
		buffer.put(value);
	}
	
	public void writeShort(short value){
		buffer.putShort(value);
	}
	
	public void writeInt(int value){
		buffer.putInt(value);
	}
	
	public void writeLong(long value){
		buffer.putLong(value);
	}
	
	public void writeChar(char value){
		buffer.putChar(value);
	}
	
	public void writeString(String value){
		if (null == value) {
			value = "";
		}
		byte[] bytes = null;
		try {
			bytes = value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			writeShort((short)0);
			e.printStackTrace();
		}
		writeShort((short) bytes.length);
		buffer.put(bytes);
	}

	public IoBuffer getBuffer() {
		return buffer;
	}
}
