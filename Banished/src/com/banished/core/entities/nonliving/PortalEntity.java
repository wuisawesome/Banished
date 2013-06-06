package com.banished.core.entities.nonliving;

import com.banished.core.Direction;
import com.banished.core.Location;
import com.banished.core.World;
import com.banished.core.entities.CircleCollisionShape;
import com.banished.core.entities.Entity;
import com.banished.graphics.Image;
import com.banished.levels.LevelState;

public abstract class PortalEntity extends Entity {
	
	private Image image;
	private float rotation;
	private LevelState state;
	
	private static final double	RADIUS = .5;
	
	public PortalEntity(World world, Location location, Image image, LevelState state)
	{
		super(world, location, new CircleCollisionShape(location.getX() + RADIUS, location.getY() + RADIUS, RADIUS), Direction.North, new Location());
		this.image = image;
		rotation = 0;
		this.state = state;
		this.setEventListener(new PortalEventListener(this, this.state));
	}
	
	public void update(double frameTime)
	{
		rotation++;
		rotation %= 360;
	}
	
	public Image getImage() { return this.image; }
	public float getRotation() { return rotation; }
	
}