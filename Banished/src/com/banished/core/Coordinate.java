package com.banished.core;

public class Coordinate
{
	private int x, y;
	
	public Coordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public Coordinate(Coordinate copy)
	{
		this(copy.getX(), copy.getY());
	}

	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	
	/**
	 * 
	 * @return The Location value of the Coordinate
	 */
	public Location toLocation()
	{
		return new Location(this.x, this.y);
	}
	
	/**
	 * 
	 * @param location The Location to be converted into Coordinate
	 * @return The Coordinate value for the Location in the parameter
	 */
	public static Coordinate fromLocation(Location location)
	{
		return location.toCoordinate();
	}
	
	/**
	 * 
	 * @param other The other coordinate to determine the manhattan distance to
	 * @return The manhattan distance between this and other
	 */
	public int manhattanDistanceTo(Coordinate other)
	{
		// manhattan distance
		return Math.abs(other.x - this.x) + Math.abs(other.y - this.y);
	}
	
	/**
	 * 
	 * @param coord1 The first Coordinate
	 * @param coord2 The second Coordinate
	 * @return The manhattan distance between coord1 and coord2
	 */
	public static int manhattanDistanceBetween(Coordinate coord1, Coordinate coord2)
	{
		return coord1.manhattanDistanceTo(coord2);
	}
	
	public String toString()
	{
		return "[" + x + "," + y + "]";
	}
	public static Coordinate fromString(String str)
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
			int x = Integer.parseInt(data[0]),
				y = Integer.parseInt(data[1]);
			return new Coordinate(x, y);
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
}
