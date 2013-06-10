package com.banished.levels;

import com.banished.core.Direction;
import com.banished.core.HUD;
import com.banished.core.Location;
import com.banished.core.Spawner;
import com.banished.core.entities.Player;
import com.banished.core.entities.nonliving.ChestEntity;
import com.banished.core.entities.nonliving.PortalForwardEntity;
import com.banished.core.items.Armor;
import com.banished.core.items.Inventory;
import com.banished.core.items.Items;
import com.banished.core.items.Potion;

public class LevelSix extends LevelState {
	
	public LevelSix(HUD hud, Player p)
	{
		super("levels/Six.blvl", hud, p, new Location(9.5, 23.5), Direction.South, "book/pageSix.png");
	}
	
	public void addEntities()
	{
		world.addEntity(new PortalForwardEntity(world, new Location(9, 1), this));
		world.addEntity(Spawner.cursedMan(new Location(14.5, 17.5), world));
		world.addEntity(Spawner.cursedMan(new Location(4.5, 17.5), world));
		world.addEntity(Spawner.shadow(new Location(4, 10.5), world));
		world.addEntity(Spawner.shadow(new Location(15, 10.5), world));
		world.addEntity(Spawner.whiteSpawner(new Location(8.5, 13.5), world));
		world.addEntity(Spawner.whiteSpawner(new Location(8.5, 14.5), world));
		world.addEntity(Spawner.whiteSpawner(new Location(8.5, 15.5), world));
		world.addEntity(Spawner.whiteSpawner(new Location(9.5, 13.5), world));
		world.addEntity(Spawner.whiteSpawner(new Location(9.5, 15.5), world));
		world.addEntity(Spawner.whiteSpawner(new Location(10.5, 13.5), world));
		world.addEntity(Spawner.whiteSpawner(new Location(10.5, 14.5), world));
		world.addEntity(Spawner.whiteSpawner(new Location(10.5, 15.5), world));
		world.addEntity(Spawner.shadow(new Location(9.5, 6), world));
		world.addEntity(Spawner.shadow(new Location(9.5, 3), world));
		
		Inventory inv = new Inventory();
		inv.add(new Armor(Items.EnchantedChest));
		inv.add(new Potion(Items.HealthPotion, 40));
		inv.add(new Potion(Items.StaminaPotion, 10));
		world.addEntity(new ChestEntity(world, new Location(9.25, 14.25), inv));
	}
}