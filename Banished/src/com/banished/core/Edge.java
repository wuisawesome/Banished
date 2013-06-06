package com.banished.core;

public class Edge
{
	private Location loc1, loc2;
	private Location normal;
	
	public Edge(Location loc1, Location loc2)
	{
		this(loc1, loc2, new Location(loc2.getY() - loc1.getY(), loc1.getX() - loc2.getX()));
	}
	public Edge(double x1, double y1, double x2, double y2)
	{
		this(new Location(x1, y1), new Location(x2, y2));
	}
	public Edge(Location loc1, Location loc2, Location normal)
	{
		this.loc1 = new Location(Math.min(loc1.getX(), loc2.getX()), Math.min(loc1.getY(), loc2.getY()));
		this.loc2 = new Location(Math.max(loc1.getX(), loc2.getX()), Math.max(loc1.getY(), loc2.getY()));
		this.normal = normal.normalize();
	}
	public Edge(double x1, double y1, double x2, double y2, Location normal)
	{
		this(new Location(x1, y1), new Location(x2, y2), normal);
	}
	
	public Location getLoc1() { return new Location(loc1); }
	public Location getLoc2() { return new Location(loc2); }
	
	public double getX1() { return loc1.getX(); }
	public double getY1() { return loc1.getY(); }
	public double getX2() { return loc2.getX(); }
	public double getY2() { return loc2.getY(); }
	
	public Location getNormal()
	{
		return this.normal;
	}
	
	public boolean isHorizontal()
	{
		return this.getY1() == this.getY2();
	}
	public boolean isVertical()
	{
		return this.getX1() == this.getX2();
	}
	
	public double shortestDistanceTo(Location loc)
	{
		if (isHorizontal())
		{
			if (loc.getX() >= this.getX1() && loc.getX() <= this.getX2())
				return Math.abs(loc.getY() - this.getY1());
			else if (loc.getX() < this.getX1())
				return this.getLoc1().distanceTo(loc);
			else
				return this.getLoc2().distanceTo(loc);
		}
		else
		{
			if (loc.getY() >= this.getY1() && loc.getY() <= this.getY2())
				return Math.abs(loc.getX() - this.getX1());
			else if (loc.getY() < this.getY1())
				return this.getLoc1().distanceTo(loc);
			else
				return this.getLoc2().distanceTo(loc);
		}
	}
}
