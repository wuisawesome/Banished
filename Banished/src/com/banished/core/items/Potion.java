package com.banished.core.items;

import com.banished.core.entities.Player;

public class Potion extends Item
{
	public static enum PotionType { Health, Stamina }
	
	private PotionType potionType;
	
	public Potion(Potion copy, int count)
	{
		this(copy.getType(), copy.getMaxStackSize(), copy.getImageId(), copy.getName(), copy.potionType);
		
		this.setCount(count);
	}
	public Potion(int type, int maxStackSize, Object imageId, String name, PotionType potionType)
	{
		super(type, 0, maxStackSize, name, imageId, true);
		
		if (this.getImage() == null)
			Items.itemImages.load("items/potion/" + potionType.toString().toLowerCase() + "potion.png", imageId);
		
		this.potionType = potionType;
	}
	
	public void use()
	{
		final int HEAL = 50, STAMINA = 100;
		switch (potionType)
		{
			case Health: Player.get().heal(HEAL); break;
			case Stamina: Player.get().addStamina(STAMINA); break;
		}
	}
}
