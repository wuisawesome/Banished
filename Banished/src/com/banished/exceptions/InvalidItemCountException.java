package com.banished.exceptions;

public class InvalidItemCountException extends Exception
{
	private static final long serialVersionUID = 7663174942435832580L;
	
	private int itemType;
	private int itemCount;
	private int maxStackSize;
	
	public InvalidItemCountException(int itemType, int itemCount, int maxStackSize)
	{
		this.itemCount = itemCount;
		this.itemType = itemType;
		this.maxStackSize = maxStackSize;
	}
	
	public int getItemType() { return this.itemType; }
	public int getItemCount() { return this.itemCount; }
	public int getItemMaxStackSize() { return this.maxStackSize; }
	
	
	public String getMessage()
	{
		return "Invalid item count '" + itemCount + "'. Items of type '" + itemType
				+ "' have a maximum stack size of '" + maxStackSize + "'.";
	}
}
