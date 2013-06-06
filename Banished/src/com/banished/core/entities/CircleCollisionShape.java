package com.banished.core.entities;

import com.banished.core.Coordinate;
import com.banished.core.Location;
import com.banished.core.Tile;
import com.banished.core.TileBoundary;
import com.banished.graphics.Graphics;

public class CircleCollisionShape extends CollisionShape
{
	private double x, y, radius; // x and y are the coordinates of the center of the circle.
	
	public CircleCollisionShape(double x, double y, double radius)
	{
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public double getX() { return x; }
	public void setX(double x) { this.x = x; }
	public double getY() { return y; }
	public void setY(double y) { this.y = y; }
	public double getRadius() { return radius; }
	public void setRadius(double radius) { this.radius = radius; }
	
	public Location getLocation() { return new Location(this.x, this.y); }
	public void setLocation(Location location) { this.x = location.getX(); this.y = location.getY(); }
	
	public boolean collidesWith(CollisionShape other)
	{
		if (other instanceof RectangleCollisionShape)
		{
			return ((RectangleCollisionShape)other).collidesWith(this);
		}
		else if (other instanceof CircleCollisionShape)
		{
			CircleCollisionShape circle = (CircleCollisionShape)other;
			return this.getLocation().squareDistanceTo(circle.getLocation())
				<= this.radius + circle.radius;
		}
		return false;
	}
	
	public CollisionShape translate(Location translation)
	{
		return new CircleCollisionShape(this.getX() + translation.getX(), this.getY() + translation.getY(),
				this.getRadius());
	}
	public TileBoundary getTileBoundary()
	{
		Location minLoc = new Location(this.getX() - this.getRadius(), this.getY() - this.getRadius()),
				 maxLoc = new Location(this.getX() + this.getRadius(), this.getY() + this.getRadius());
		
		Coordinate minCoord = minLoc.toCoordinate(),
				   maxCoord = maxLoc.toCoordinate();
		
		return new TileBoundary(minCoord.getX(), minCoord.getY(), maxCoord.getX(), maxCoord.getY());
	}
	
	public CollisionShape copy()
	{
		return new CircleCollisionShape(this.x, this.y, this.radius);
	}
	
	public void render()
	{
		Graphics.Applet.stroke(0, 255, 0);
		Graphics.Applet.strokeWeight(3);
		Graphics.Applet.noFill();
		Graphics.Applet.ellipse((float)getX() * Tile.SIZE, (float)getY() * Tile.SIZE,
				2 * (float)getRadius() * Tile.SIZE, 2 * (float)getRadius() * Tile.SIZE);
	}
	
	public Location getCenter()
	{
		return getLocation();
	}
}
