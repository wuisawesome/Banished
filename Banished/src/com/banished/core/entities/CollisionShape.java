package com.banished.core.entities;

import com.banished.core.Edge;
import com.banished.core.Location;
import com.banished.core.TileBoundary;

public abstract class CollisionShape
{
	public abstract boolean collidesWith(CollisionShape other);
	public boolean collidesWith(Edge edge)
	{
		RectangleCollisionShape edgeRect;
		if (edge.isHorizontal())
			edgeRect = new RectangleCollisionShape(Math.min(edge.getX1(), edge.getX2()),
					Math.min(edge.getY1(), edge.getY2()), Math.abs(edge.getX2() - edge.getX1()), 0);
		else // vertical
			edgeRect = new RectangleCollisionShape(Math.min(edge.getX1(), edge.getX2()),
					Math.min(edge.getY1(), edge.getY2()), 0, Math.abs(edge.getY2() - edge.getY1()));
		return this.collidesWith(edgeRect);
	}
	public abstract CollisionShape translate(Location translation);
	
	public abstract TileBoundary getTileBoundary();
	
	public abstract CollisionShape copy();
	
	public abstract void render();
	
	public abstract Location getCenter();
}
