package com.banished.core;

import processing.core.PVector;

public class Location
{
	private double x, y;
	
	public Location()
	{
		this(0, 0);
	}
	public Location(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public Location(Location copy)
	{
		this(copy.getX(), copy.getY());
	}
	
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	
	public String toString()
	{
		return "[" + x + ", " + y + "]";
	}
	public static Location fromString(String str)
	{
		if (!str.startsWith("[") || !str.endsWith("]"))
			return null;
		if (!str.contains(","))
			return null;
		
		str = str.substring(1, str.length() - 1);
		
		String[] data = str.split(",");
		if (data.length != 2) return null;
		
		try
		{
			double x = Double.parseDouble(data[0]),
				   y = Double.parseDouble(data[1]);
			return new Location(x, y);
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
	
	/**
	 * 
	 * @return The Coordinate value of the Location
	 */
	public Coordinate toCoordinate()
	{
		return new Coordinate((int)this.x, (int)this.y);
	}
	
	/**
	 * 
	 * @param coordinate The Coordinate to be converted into Location
	 * @param tileSize The size of the tile on the grid
	 * @return The Location value for the Coordinate in the parameter
	 */
	public static Location fromCoordinate(Coordinate coordinate)
	{
		return coordinate.toLocation();
	}
	
	public PVector toPVector()
	{
		return new PVector((float)x, (float)y);
	}
	
	public double distanceTo(Location other)
	{
		return Math.sqrt(this.squareDistanceTo(other));
	}
	public double squareDistanceTo(Location other)
	{
		double dx = other.x - this.x,
				  dy = other.y - this.y;
		return dx*dx + dy*dy;
	}
	
	public Location add(Location other)
	{
		return new Location(this.x + other.x, this.y + other.y);
	}
	public Location sub(Location other)
	{
		return new Location(this.x - other.x, this.y - other.y);
	}
	public Location mult(double scalar)
	{
		return new Location(this.x * scalar, this.y * scalar);
	}
	public Location pairwiseMult(Location other)
	{
		return new Location(this.x * other.x, this.y * other.y);
	}
	public Location div(double scalar)
	{
		if (scalar == 0) return null; // divide by zero == bad
		return this.mult(1 / scalar);
	}
	public Location pairwiseDiv(Location other)
	{
		if (other.getX() == 0 || other.getY() == 0) return null;
		return this.pairwiseMult(new Location(1 / other.getX(), 1 / other.getY()));
	}
	
	public double getMagnitude()
	{
		return Math.sqrt(this.getSquareMagnitude());
	}
	public double getSquareMagnitude()
	{
		return this.x * this.x + this.y * this.y;
	}
	
	public Location normalize()
	{
		return this.div(this.getMagnitude());
	}
	
	public double dot(Location other)
	{
		return this.x * other.x + this.y * other.y;
	}
	
	public Location getNormal()
	{
		return new Location(-y, x);
	}
	
	public boolean equals(Object other)
	{
		if (other instanceof Location)
			return ((Location)other).x == x && ((Location)other).y == y;
		return false;
	}
}
