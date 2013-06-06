package com.banished.core.items;

import com.banished.exceptions.InvalidItemCountException;
import com.banished.exceptions.InvalidItemTypeException;
import com.banished.exceptions.InvalidMaxStackSizeException;

public abstract class Weapon extends Equippable {
	
	public final double HITBOX_WIDTH, HITBOX_HEIGHT;
	public final double MAX_DAMAGE, STABILITY;
	
	public Weapon(int type, int count, int maxStackSize, String name, Object imageId,
			double hitBoxWidth, double hitBoxHeight, double att, double stability)
				throws InvalidItemTypeException, InvalidItemCountException, InvalidMaxStackSizeException
	{
		super(type, count, maxStackSize, name, imageId);
		HITBOX_WIDTH = hitBoxWidth;
		HITBOX_HEIGHT = hitBoxHeight;
		STABILITY = stability;
		MAX_DAMAGE = att;
	}
}
