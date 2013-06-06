package com.banished.core.entities;

import com.banished.core.*;
//import com.banished.core.entities.enemies.*;
//import com.banished.core.entities.nonliving.*;
import com.banished.graphics.Image;

public abstract class Entity
{
	protected World world;
	protected Location location;
	private CollisionShape shape;
	private Direction dir;
	
	private EntityEventListener eventListener;
	
	// imageTopLeft + imageOffset = location
	private Location imageOffset;
	
	public Entity(World world, Location location, CollisionShape shape, Direction dir, Location imageOffset)
	{
		this(world, location, shape, dir, null, imageOffset);
	}
	public Entity(World world, Location location, CollisionShape shape, Direction dir,
			EntityEventListener eventListener, Location imageOffset)
	{
		this.world = world;
		this.location = location;
		this.shape = shape;
		this.dir = dir;
		
		this.eventListener = eventListener;
		
		this.imageOffset = imageOffset;
	}
//	public Entity(Entity toCopy)
//	{
//		this(toCopy.world, new Location(toCopy.location), toCopy.shape.copy(), toCopy.dir, toCopy.eventListener);
//	}
	
	public Location getLocation() { return this.location; }
	public void setLocation(Location location) { this.location = location; }

	public World getWorld() { return this.world; }
	public void setWorld(World world) { this.world = world; }
	
	public CollisionShape getShape() { return this.shape; }
	public void setShape(CollisionShape shape) { this.shape = shape; }
	
	public Direction getDirection() { return this.dir; }
	public void setDirection(Direction direction) { this.dir = direction; }
	
	public Location getImageOffset() { return this.imageOffset; }
	public void setImageOffset(Location imageOffset) { this.imageOffset = imageOffset; }
	
	public void removeFromWorld()
	{
		this.world.addEntityToDie(this);
	}
	
	public abstract void update(double frameTime);
	
	public abstract Image getImage();
	
	protected void setEventListener(EntityEventListener listener)
	{
		this.eventListener = listener;
	}
	
	public void onTouched(Entity sender)
	{
		if (this.eventListener != null)
			this.eventListener.onTouched(sender);
	}
	public void onLeft(Entity sender)
	{
		if (this.eventListener != null)
			this.eventListener.onLeft(sender);
	}
	public void onInteractedWith()
	{
		if (this.eventListener != null)
			this.eventListener.onInteractedWith();
	}
	
	public String toText()
	{
		return "location=" + location + " direction=" + dir;
	}
	public static Entity readFromText(String text)
	{
//		final String PLAYER = "player",
//					 BAT = "bat",
//					 SHADOW = "shadow",
//					 SLIME = "slime",
//					 SPAWNER = "spawner",
//					 CHEST = "chest",
//					 DOOR = "door",
//					 ITEM = "item",
//					 PORTAL_FORWARD = "portal-forward";
//		
//		if (text.startsWith(PLAYER + ": "))
//			return Player.fromText(text.substring((PLAYER + ": ").length()));
//		else if (text.startsWith(BAT + ": "))
//			return BatEntity.fromText(text.substring((BAT + ": ").length()));
//		else if (text.startsWith(SHADOW + ": "))
//			return ShadowEntity.fromText(text.substring((SHADOW + ": ").length()));
//		else if (text.startsWith(SLIME + ": "))
//			return SlimeEntity.fromText(text.substring((SLIME + ": ").length()));
//		else if (text.startsWith(SPAWNER + ": "))
//			return SpawnerEntity.fromText(text.substring((SPAWNER + ": ").length()));
//		else if (text.startsWith(CHEST + ": "))
//			return ChestEntity.fromText(text.substring((CHEST + ": ").length()));
//		else if (text.startsWith(DOOR + ": "))
//			return DoorEntity.fromText(text.substring((DOOR + ": ").length()));
//		else if (text.startsWith(ITEM + ": "))
//			return ItemEntity.fromText(text.substring((ITEM + ": ").length()));
//		else if (text.startsWith(PORTAL_FORWARD + ": "))
//			return PortalForwardEntity.fromText(text.substring((PORTAL_FORWARD + ": ").length()));
		return null;
		// TODO: implement();
	}
}
