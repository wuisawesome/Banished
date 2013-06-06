package com.banished.exceptions;

public class InvalidTileTypeException extends Exception
{
	private static final long serialVersionUID = 349907576054132509L;
	
	private int tileType;
	
	public InvalidTileTypeException(int tileType)
	{
		this.tileType = tileType;
	}
	
	public int getTileType() { return this.tileType; }
	
	
	public String getMessage()
	{
		return "Invalid tile type '" + tileType + "'. Tile types must be greater than zero.";
	}
}
