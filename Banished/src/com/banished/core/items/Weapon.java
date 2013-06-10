package com.banished.core.items;

import com.banished.core.entities.EntityImageSet;
import com.banished.core.entities.Player;

public class Weapon extends Equippable
{
	
	public final double HITBOX_WIDTH, HITBOX_HEIGHT;
	public final double MAX_DAMAGE, STABILITY;
	
	public Weapon(int type, String name, Object imageId, EntityImageSet images,
			double hitBoxWidth, double hitBoxHeight, double att, double stability)
	{
		super(type, name, imageId, images);
		HITBOX_WIDTH = hitBoxWidth;
		HITBOX_HEIGHT = hitBoxHeight;
		STABILITY = stability;
		MAX_DAMAGE = att;
	}
	
	public Weapon(Weapon copy)
	{
		super(copy);
		this.HITBOX_WIDTH = copy.HITBOX_WIDTH;
		this.HITBOX_HEIGHT = copy.HITBOX_HEIGHT;
		this.STABILITY = copy.STABILITY;
		this.MAX_DAMAGE = copy.MAX_DAMAGE;
	}
	
	public void use()
	{
		Player.get().setWeapon(Player.get().isWearing(this) ? null : this);
	}
}
