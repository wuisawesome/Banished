package com.banished.core.entities;

import com.banished.core.Coordinate;
import com.banished.core.Location;
import com.banished.core.Tile;
import com.banished.core.TileBoundary;
import com.banished.graphics.Graphics;

public class RectangleCollisionShape extends CollisionShape
{
	private double top, left, width, height;
	
	public RectangleCollisionShape(double left, double top, double width, double height)
	{
		this.top = top;
		this.left = left;
		this.width = width;
		this.height = height;
	}
	public RectangleCollisionShape(Location topLeft, Location bottomRight)
	{
		this(topLeft.getX(), topLeft.getY(), bottomRight.getX() - topLeft.getX(), bottomRight.getY() - topLeft.getY());
	}
	
	public double getTop() { return this.top; }
	public void setTop(double top) { this.top = top; }
	public double getLeft() { return this.left;}
	public void setLeft(double left) { this.left = left; }
	public double getWidth() { return this.width; }
	public void setWidth(double width) { this.width = width; }
	public double getHeight() { return this.height; }
	public void setHeight(double height) { this.height = height; }
	
	public Location getTopLeft() { return new Location(this.left, this.top); }
	public void setTopLeft(Location topLeft) { this.left = topLeft.getX(); this.top = topLeft.getY(); }
	public Location getSize() { return new Location(this.width, this.height); }
	public void setSize(Location size) { this.width = size.getX(); this.height = size.getY(); }
	
	public double getRight() { return this.left + this.width; }
	public double getBottom() { return this.top + this.height; }
	public Location getBottomRight() { return new Location(getRight(), getBottom()); }
	
	public boolean collidesWith(CollisionShape other)
	{
		if (other instanceof RectangleCollisionShape)
		{
			RectangleCollisionShape rect = (RectangleCollisionShape)other;
			return !(this.getRight() < rect.getLeft()
				  || this.getBottom() < rect.getTop()
				  || this.getLeft() > rect.getRight()
				  || this.getTop() > rect.getBottom());
		}
		else if (other instanceof CircleCollisionShape)
		{
			CircleCollisionShape circle = (CircleCollisionShape)other;
			
			// from https://www.allegro.cc/forums/thread/604103
			double cx = circle.getX(), cy = circle.getY(), cr = circle.getRadius();
			double l = this.getLeft(), r = this.getRight(), t = this.getTop(), b = this.getBottom();
			
			
			return (l - cr < cx && cx < r + cr) && (t - cr < cy && cy < b + cr);
		}
		else if (other instanceof CircleCollisionShape)
		{
			return ((CircleCollisionShape)other).collidesWith(this);
		}
		return false;
	}
	
	public CollisionShape translate(Location translation)
	{
		return new RectangleCollisionShape(this.getLeft() + translation.getX(), this.getTop() + translation.getY(),
				this.getWidth(), this.getHeight());
	}
	public TileBoundary getTileBoundary()
	{
		Location minLoc = new Location(this.getLeft(), this.getTop()),
				 maxLoc = new Location(this.getRight(), this.getBottom());

		Coordinate minCoord = minLoc.toCoordinate(),
				   maxCoord = maxLoc.toCoordinate();
		
		return new TileBoundary(minCoord.getX(), minCoord.getY(), maxCoord.getX(), maxCoord.getY());
	}
	
	public CollisionShape copy()
	{
		return new RectangleCollisionShape(left, top, width, height);
	}
	
	public void render()
	{
		Graphics.Applet.stroke(0, 255, 0);
		Graphics.Applet.strokeWeight(3);
		Graphics.Applet.noFill();
		Graphics.Applet.rect((float)getLeft() * Tile.SIZE, (float)getTop() * Tile.SIZE,
				(float)getWidth() * Tile.SIZE, (float)getHeight() * Tile.SIZE);
	}
	
	public Location getCenter()
	{
		return getTopLeft().add(getBottomRight()).div(2);
	}
}
