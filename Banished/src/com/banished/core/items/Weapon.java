package com.banished.core.items;

import com.banished.core.entities.EntityImageSet;

public abstract class Weapon extends Equippable
{
	
	public final double HITBOX_WIDTH, HITBOX_HEIGHT;
	public final double MAX_DAMAGE, STABILITY;
	
	public Weapon(int type, int maxStackSize, String name, Object imageId, EntityImageSet images,
			double hitBoxWidth, double hitBoxHeight, double att, double stability)
	{
		super(type, name, imageId, images);
		HITBOX_WIDTH = hitBoxWidth;
		HITBOX_HEIGHT = hitBoxHeight;
		STABILITY = stability;
		MAX_DAMAGE = att;
	}
}
