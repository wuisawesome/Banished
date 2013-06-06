package com.banished.core.entities.nonliving;

import com.banished.core.*;
import com.banished.core.entities.Entity;
import com.banished.core.entities.RectangleCollisionShape;
import com.banished.core.items.Item;
import com.banished.graphics.Image;

public class ItemEntity extends Entity
{
	private Item item;
	
	public ItemEntity(World world, Location location, Item item)
	{
		super(world, location,
				new RectangleCollisionShape(location.getX(), location.getY(),
						(float)Item.IMAGE_WIDTH / Tile.SIZE, (float)Item.IMAGE_HEIGHT / Tile.SIZE),
				Direction.North, new Location());
		this.item = item;
	}
	
	public Item getItem() { return this.item; }
	
	public void update(double frameTime)
	{
		// TODO: Implement this method.
	}
	
	public Image getImage()
	{
		return this.item.getImage();
	}
	
	public String toText()
	{
		return super.toText() + " item=[" + item.toText() + "]";
	}
}
