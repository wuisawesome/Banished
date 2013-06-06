package com.banished.core.items;

import com.banished.core.entities.EntityImageSet;

public abstract class Equippable extends Item
{
	private EntityImageSet images;
	
	public Equippable(int type, String name, Object imageId, EntityImageSet images)
	{
		super(type, 0, 1, name, imageId, false);
		this.images = images;
	}
	public Equippable(Equippable copy)
	{
		this(copy.getType(), copy.getName(), copy.getImageId(), copy.images);
		this.setCount(1);
	}
	
	public EntityImageSet getImages() { return images; }
}
