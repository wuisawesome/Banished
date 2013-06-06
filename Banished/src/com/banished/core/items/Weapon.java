package com.banished.core.items;

import com.banished.core.entities.EntityImageSet;
import com.banished.core.entities.Player;

public abstract class Weapon extends Equippable
{
	public final double HITBOX_WIDTH, HITBOX_HEIGHT;
	public final double MAX_DAMAGE, STABILITY;
	
	public Weapon(int type, int count, int maxStackSize, String name, Object imageId,
			double hitBoxWidth, double hitBoxHeight, double att, double stability, EntityImageSet images)
	{
		super(type, count, maxStackSize, name, imageId, images);
		HITBOX_WIDTH = hitBoxWidth;
		HITBOX_HEIGHT = hitBoxHeight;
		STABILITY = stability;
		MAX_DAMAGE = att;
	}
	public Weapon(Weapon copy, int count)
	{
		super(copy, count);
		this.HITBOX_WIDTH = copy.HITBOX_WIDTH;
		this.HITBOX_HEIGHT = copy.HITBOX_HEIGHT;
		this.MAX_DAMAGE = copy.MAX_DAMAGE;
		this.STABILITY = copy.STABILITY;
	}
	
	public void use()
	{
		Player.get().setWeapon(this);
	}
}
