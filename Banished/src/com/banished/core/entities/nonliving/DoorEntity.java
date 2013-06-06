package com.banished.core.entities.nonliving;

import com.banished.core.Coordinate;
import com.banished.core.Direction;
import com.banished.core.Location;
//import com.banished.core.Tile;
import com.banished.core.World;
import com.banished.core.entities.Entity;
import com.banished.core.entities.EntityEventListener;
import com.banished.core.entities.RectangleCollisionShape;
import com.banished.graphics.Image;

public class DoorEntity extends Entity
{
	private static final double DOOR_WIDTH = .1;
	
	private boolean closed;
	
	private Coordinate tile;
	
	private Image image;
	
	public DoorEntity(World world, Coordinate tile, Direction dir)
	{
		super(world, null, null, dir, new Location());
		
		this.setEventListener(new DoorEventListener(this));

		this.tile = tile;
		this.closed = true;
		
		this.updateDoorData();
	}

	public void update(double frameTime) { }
	
	public Image getImage()
	{
		return image;
	}
	
	public void updateDoorData()
	{
		if (this.closed)
		{
			switch (this.getDirection())
			{
				case North: case Northeast: case Northwest:
					this.setShape(
						new RectangleCollisionShape(tile.getX() - .5, tile.getY(), 2, 1));
					this.setLocation(tile.toLocation().sub(new Location(1, 0)));
					this.image = Image.fromFile("entities/object_entities/door/doorclosefront.png");
					break;
				case East: 
					this.setShape(
						new RectangleCollisionShape(tile.getX() + 1 - DOOR_WIDTH, tile.getY(), DOOR_WIDTH, 1));
					this.setLocation(tile.toLocation().add(new Location(1 - DOOR_WIDTH, 0)));
					this.image = Image.fromFile("entities/object_entities/door/doorcloseside.png");
					break;
				case South: case Southeast: case Southwest:
					this.setShape(
						new RectangleCollisionShape(tile.getX() - .5, tile.getY(), 2, 1));
					this.setLocation(tile.toLocation().sub(new Location(1, 0)));
					this.image = Image.fromFile("entities/object_entities/door/doorclosefront.png");
					break;
				case West:
					this.setShape(
							new RectangleCollisionShape(tile.getX(), tile.getY(), DOOR_WIDTH, 1));
					this.setLocation(tile.toLocation());
					this.image = Image.fromFile("entities/object_entities/door/doorcloseside.png");
					break;
			}
		}
		else
		{
			switch (this.getDirection())
			{
				case North: case Northeast: case Northwest:
					this.setShape(
						new RectangleCollisionShape(tile.getX(), tile.getY(), 1, DOOR_WIDTH));
					this.setLocation(tile.toLocation());
					this.image = Image.fromFile("entities/object_entities/door/dooropenfront.png");
					break;
				case East: 
					this.setShape(
						new RectangleCollisionShape(tile.getX() + 1 - DOOR_WIDTH, tile.getY(), DOOR_WIDTH, 1));
					this.setLocation(tile.toLocation().add(new Location(1 - DOOR_WIDTH, 0)));
					this.image = Image.fromFile("entities/object_entities/door/dooropenside.png");
					break;
				case South: case Southeast: case Southwest:
					this.setShape(
						new RectangleCollisionShape(tile.getX(), tile.getY() - DOOR_WIDTH, 1, DOOR_WIDTH));
					this.setLocation(tile.toLocation().add(new Location(0, 1 - DOOR_WIDTH)));
					this.image = Image.fromFile("entities/object_entities/door/dooropenfront.png");
					break;
				case West:
					this.setShape(
							new RectangleCollisionShape(tile.getX(), tile.getY(), DOOR_WIDTH, 1));
					this.setLocation(tile.toLocation());
					this.image = Image.fromFile("entities/object_entities/door/dooropenside.png");
					break;
		}
		}
	}
	
	private static class DoorEventListener implements EntityEventListener
	{
		private DoorEntity door;
		
		public DoorEventListener(DoorEntity door)
		{
			this.door = door;
		}

		public void onTouched(Entity sender) { }
		public void onLeft(Entity sender) { }
		public void onInteractedWith()
		{
			door.closed = !door.closed;
			door.updateDoorData();
		}
	}
	
	public String toText()
	{
		return super.toText() + " tile=" + tile + " closed=" + closed;
	}
}
