package com.banished.core.items;

import com.banished.exceptions.InvalidItemCountException;
import com.banished.exceptions.InvalidItemTypeException;
import com.banished.exceptions.InvalidMaxStackSizeException;

public abstract class Equippable extends Item
{
	public Equippable(int type, int count, int maxStackSize, String name, Object imageId)
			throws InvalidItemTypeException, InvalidItemCountException, InvalidMaxStackSizeException
	{
		super(type, count, maxStackSize, name, imageId);
	}
}
