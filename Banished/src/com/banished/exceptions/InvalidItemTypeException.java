package com.banished.exceptions;

public class InvalidItemTypeException extends Exception
{
	private static final long serialVersionUID = -5110789322333182491L;
	
	private int itemType;
	
	public InvalidItemTypeException(int itemType)
	{
		this.itemType = itemType;
	}
	
	public int getItemType() { return this.itemType; }
	
	public String getMessage()
	{
		return "Invalid item type '" + itemType + "'.";
	}
}
