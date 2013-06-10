package com.banished.levels;

import java.util.ArrayList;

import com.banished.core.Algorithms;
import com.banished.core.Direction;
import com.banished.core.HUD;
import com.banished.core.Location;
import com.banished.core.Spawner;
import com.banished.core.entities.Entity;
import com.banished.core.entities.Player;
import com.banished.core.entities.nonliving.ChestEntity;
import com.banished.core.entities.nonliving.PortalForwardEntity;
import com.banished.core.items.Armor;
import com.banished.core.items.Inventory;
import com.banished.core.items.Items;
import com.banished.core.items.Potion;

public class LevelFive extends LevelState {
	
	public LevelFive(HUD hud, Player p)
	{
		super("levels/Five.blvl", hud, p, new Location(20.5, 4.5), Direction.South, "book/pageFive.png");
	}
	
	public void addEntities()
	{
		world.addEntity(new PortalForwardEntity(world, new Location(1, 1), this));
		world.addEntity(Spawner.violetSpawner(new Location(16.5, 4.5), world));
		world.addEntity(Spawner.shadow(new Location(6, 7), world));
		Inventory inv = new Inventory();
		inv.add(new Armor(Items.EnchantedLegs));
		inv.add(new Potion(Items.StaminaPotion, 10));
		world.addEntity(new ChestEntity(world, new Location(9.25, 1.25), inv));
		ArrayList<Entity> a = new ArrayList<Entity>();
		for(int i = 0; i < 7; i++)
			a.add(Spawner.cursedMan(new Location(Algorithms.randMult(14, 20), 1.5), world));
		world.getTiles().getTile(12, 1).setTriggerEntity(world, a);
	}
}