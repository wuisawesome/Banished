package com.banished.core.items;

import com.banished.core.entities.EntityImageSet;

public class Equippable extends Item
{	
	private EntityImageSet images;
	
	public Equippable(int type, int count, int maxStackSize, String name, Object imageId, EntityImageSet images)
	{
		super(type, count, maxStackSize, name, imageId, false);
		this.images = images;
	}
	public Equippable(Equippable copy, int count)
	{
		super(copy, count);
		this.images = copy.images;
	}
	
	public EntityImageSet getImages() { return images; }
	
	public void use()
	{
		// swap with player's equipment
	}
}
