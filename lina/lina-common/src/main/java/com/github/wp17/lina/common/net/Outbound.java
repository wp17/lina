package com.github.wp17.lina.common.net;

public interface Outbound {
	
	public void writeBoolean(boolean value);
	
	public void writeByte(byte value);
	
	public void writeShort(short value);
	
	public void writeInt(int value);
	
	public void writeLong(long value);
	
	public void writeChar(char value);
	
	public void writeFloat(float value);
	
	public void writeDouble(double value);
	
	public void writeString(String value);
}
