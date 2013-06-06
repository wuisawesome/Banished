package com.banished.core.items;

import com.banished.core.entities.EntityImageSet;
import com.banished.core.entities.Player;

public class Armor extends Equippable
{
	public static enum ArmorType { Chest, Legs, Shoes, Gloves }
	
	private ArmorType armorType;
	
	private double defense, health;

	public Armor(int type, String name, Object imageId, EntityImageSet images, ArmorType armorType, double defense, double health)
	{
		super(type, name, imageId, images);
		this.armorType = armorType;
		this.defense = defense;
		this.health = health;
	}
	public Armor(Armor copy)
	{
		super(copy);
		this.armorType = copy.armorType;
		this.defense = copy.defense;
		this.health = copy.health;
	}
	
	public ArmorType getArmorType() { return armorType; }
	
	public double getDefense() { return this.defense; }
	public double getHealth() { return this.health; }
	
	public void use()
	{
		switch (this.armorType)
		{
			case Chest: Player.get().setChest(this); break;
			case Legs: Player.get().setLegs(this); break;
			case Shoes: Player.get().setShoes(this); break;
			case Gloves: Player.get().setGloves(this); break;
		}
	}
}
