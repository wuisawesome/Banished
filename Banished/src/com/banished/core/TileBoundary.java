package com.banished.core;

public class TileBoundary
{
	private int minX, maxX, minY, maxY;
	
	public TileBoundary(int minX, int minY, int maxX, int maxY)
	{
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

	public int getMinX() { return minX; }
	public int getMaxX() { return maxX; }
	public int getMinY() { return minY; }
	public int getMaxY() { return maxY; }
}