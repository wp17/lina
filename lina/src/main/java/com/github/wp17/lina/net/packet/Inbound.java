package com.github.wp17.lina.net.packet;

public interface Inbound {
	
	public boolean readBoolean();
	
	public byte readByte();
	
	public short readShort();
	
	public int readInt();
	
	public long readLong();
	
	public char readChar();
	
	public float readFloat();
	
	public double readDouble();
	
	public String readString();
}
