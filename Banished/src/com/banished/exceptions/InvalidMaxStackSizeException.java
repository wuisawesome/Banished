package com.banished.exceptions;

public class InvalidMaxStackSizeException extends Exception
{
	private static final long serialVersionUID = 4251177208982364834L;
	
	private int maxStackSize;
	
	public InvalidMaxStackSizeException(int maxStackSize)
	{
		this.maxStackSize = maxStackSize;
	}
	
	public int getMaxStackSize() { return this.maxStackSize; }
	
	public String getMessage()
	{
		return "Invalid max stack size '" + maxStackSize + "'.";
	}
}
