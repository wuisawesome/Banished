package com.banished.core.items;

import com.banished.core.entities.EntityImageSet;
import com.banished.graphics.Color;
import com.banished.graphics.Image;
import com.banished.graphics.ImageMap;

public class Items
{
	public static ImageMap itemImages;
	public static final int NumItems = 1;
	
	public static Armor IronChest, IronLegs, IronShoes, IronGloves,
						BronzeChest, BronzeLegs, BronzeShoes, BronzeGloves;
	public static Potion HealthPotion, StaminaPotion;
	
	static
	{
		itemImages = new ImageMap();
		
		final Color ironColor = new Color(.5f);
		IronChest = makeArmor(0, "Iron Chestplate", "items/iron chest.png",
				Armor.ArmorType.Chest, new EntityImageSet(new Image[]
		{
			Image.fromFile("entities/player/armor/white chest left.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white chest left 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white chest left 2.png").tint(ironColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white chest right.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white chest right 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white chest right 2.png").tint(ironColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white chest back.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white chest back 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white chest back 2.png").tint(ironColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white chest front.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white chest front 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white chest front 2.png").tint(ironColor)
		}),
		5, 30);
		IronLegs = makeArmor(1, "Iron Greaves", "items/iron legs.png",
				Armor.ArmorType.Legs, new EntityImageSet(new Image[]
		{
			Image.fromFile("entities/player/armor/white legs left.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white legs left 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white legs left 2.png").tint(ironColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white legs right.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white legs right 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white legs right 2.png").tint(ironColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white legs back.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white legs back 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white legs back 2.png").tint(ironColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white legs front.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white legs front 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white legs front 2.png").tint(ironColor)
		}),
		4, 15);
		IronShoes = makeArmor(2, "Iron Boots", "items/iron shoes.png",
				Armor.ArmorType.Shoes, new EntityImageSet(new Image[]
		{
			Image.fromFile("entities/player/armor/white shoes left.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white shoes left 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white shoes left 2.png").tint(ironColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white shoes right.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white shoes right 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white shoes right 2.png").tint(ironColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white shoes back.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white shoes back 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white shoes back 2.png").tint(ironColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white shoes front.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white shoes front 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white shoes front 2.png").tint(ironColor)
		}),
		3, 0);
		IronGloves = makeArmor(3, "Iron Gloves", "items/iron gloves.png",
				Armor.ArmorType.Gloves, new EntityImageSet(new Image[]
		{
			Image.fromFile("entities/player/armor/white gloves left.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white gloves left 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white gloves left 2.png").tint(ironColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white gloves right.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white gloves right 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white gloves right 2.png").tint(ironColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white gloves back.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white gloves back 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white gloves back 2.png").tint(ironColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white gloves front.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white gloves front 1.png").tint(ironColor),
			Image.fromFile("entities/player/armor/white gloves front 2.png").tint(ironColor)
		}),
		3, 0);
		
		final Color bronzeColor = new Color(205/Color.COLOR_MAX_VALUE, 127/Color.COLOR_MAX_VALUE, 50/Color.COLOR_MAX_VALUE);
		BronzeChest = makeArmor(4, "Bronze Chestplate", "items/bronze chest.png",
				Armor.ArmorType.Chest, new EntityImageSet(new Image[]
		{
			Image.fromFile("entities/player/armor/white chest left.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white chest left 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white chest left 2.png").tint(bronzeColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white chest right.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white chest right 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white chest right 2.png").tint(bronzeColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white chest back.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white chest back 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white chest back 2.png").tint(bronzeColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white chest front.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white chest front 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white chest front 2.png").tint(bronzeColor)
		}),
		3, 10);
		BronzeLegs = makeArmor(5, "Bronze Greaves", "items/bronze legs.png",
				Armor.ArmorType.Legs, new EntityImageSet(new Image[]
		{
			Image.fromFile("entities/player/armor/white legs left.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white legs left 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white legs left 2.png").tint(bronzeColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white legs right.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white legs right 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white legs right 2.png").tint(bronzeColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white legs back.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white legs back 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white legs back 2.png").tint(bronzeColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white legs front.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white legs front 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white legs front 2.png").tint(bronzeColor)
		}),
		2, 5);
		BronzeShoes = makeArmor(6, "Bronze Boots", "items/bronze shoes.png",
				Armor.ArmorType.Shoes, new EntityImageSet(new Image[]
		{
			Image.fromFile("entities/player/armor/white shoes left.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white shoes left 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white shoes left 2.png").tint(bronzeColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white shoes right.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white shoes right 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white shoes right 2.png").tint(bronzeColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white shoes back.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white shoes back 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white shoes back 2.png").tint(bronzeColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white shoes front.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white shoes front 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white shoes front 2.png").tint(bronzeColor)
		}),
		1, 0);
		BronzeGloves = makeArmor(7, "Bronze Gloves", "items/bronze gloves.png",
				Armor.ArmorType.Gloves, new EntityImageSet(new Image[]
		{
			Image.fromFile("entities/player/armor/white gloves left.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white gloves left 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white gloves left 2.png").tint(bronzeColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white gloves right.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white gloves right 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white gloves right 2.png").tint(bronzeColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white gloves back.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white gloves back 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white gloves back 2.png").tint(bronzeColor)
		},
		new Image[]
		{
			Image.fromFile("entities/player/armor/white gloves front.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white gloves front 1.png").tint(bronzeColor),
			Image.fromFile("entities/player/armor/white gloves front 2.png").tint(bronzeColor)
		}),
		1, 0);
		
		HealthPotion = makePotion(8, 10, "items/potion/healthpotion.png", Potion.PotionType.Health);
		StaminaPotion = makePotion(9, 10, "items/potion/staminapotion.png", Potion.PotionType.Stamina);
	}
	
	private static Armor makeArmor(int type, String name, String imageName, Armor.ArmorType armorType, EntityImageSet images, double defense, double health)
	{
		Armor armor = new Armor(type, name, type, images, armorType, defense, health);
		itemImages.load(imageName, type);
		return armor;
	}
	private static Potion makePotion(int type, int maxStackSize, String imageName, Potion.PotionType potionType)
	{
		Potion potion = new Potion(type, maxStackSize, type, potionType);
		itemImages.load(imageName, type);
		return potion;
	}
}
