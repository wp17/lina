package com.github.wp17.lina.net.packet;

import java.io.UnsupportedEncodingException;

import org.apache.mina.core.buffer.IoBuffer;

import com.github.wp17.lina.log.LoggerProvider;

public class MinaInbound implements Inbound{
	private IoBuffer buffer;
	
	public MinaInbound(IoBuffer buffer){
		this.buffer = buffer;
	}
	
	@Override
	public boolean readBoolean() {
		return buffer.get() > 0;
	}
	
	public byte readByte(){
		return buffer.get();
	}
	
	public short readShort(){
		return buffer.getShort();
	}
	
	public int readInt(){
		return buffer.getInt();
	}
	
	public long readLong(){
		return buffer.getLong();
	}
	
	public char readChar(){
		return buffer.getChar();
	}
	
	public float readFloat(){
		return buffer.getFloat();
	}
	
	public double readDouble(){
		return buffer.getDouble();
	}
	
	public String readString(){
		int length = buffer.getInt();
		byte[] bytes = new byte[length];
		buffer.get(bytes);
		
		try {
			String string = new String(bytes, "UTF-8");
			return string;
		} catch (Exception e) {
			LoggerProvider.addExceptionLog(e);
		}
		
		return "";
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("**"+new String(new byte[0], "UTF-8")+"**");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
