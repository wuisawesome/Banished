package com.banished.core.items;

import com.banished.core.entities.Player;

public class Potion extends Item
{
	public static enum PotionType { Health, Stamina }
	
	private PotionType potionType;
	
	public Potion(int type, int maxStackSize, Object imageId, PotionType potionType)
	{
		super(type, 0, maxStackSize, potionType.toString() + " Potion", imageId, true);
		this.potionType = potionType;
	}
	public Potion(Potion copy, int count)
	{
		this(copy.getType(), copy.getMaxStackSize(), copy.getImageId(), copy.potionType);
		this.setCount(count);
	}
	
	public void use()
	{
		final double HEALTH = 100, STAMINA = 50;
		
		switch (potionType)
		{
			case Health: Player.get().heal(HEALTH); break;
			case Stamina: Player.get().addStamina(STAMINA); break;
		}
	}
}
