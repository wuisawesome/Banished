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
import com.banished.core.items.Weapon;

public class LevelFour extends LevelState {
	
	public LevelFour(HUD hud, Player p)
	{
		super("levels/Four.blvl", hud, p, new Location(18.5, 1.5), Direction.South, "book/pageFour.png");
	}
	
	public void addEntities()
	{
		world.addEntity(new PortalForwardEntity(world, new Location(16, 1), this));
		Inventory inv = new Inventory();
		inv.add(new Potion(Items.HealthPotion, 10));
		inv.add(new Armor(Items.IronGloves));
		inv.add(new Armor(Items.IronShoes));
		inv.add(new Weapon(Items.IronSword));
		world.addEntity(new ChestEntity(null, new Location(3.5, 4), inv));
		ArrayList<Entity> a = new ArrayList<Entity>();
		a.add(Spawner.greenSlime(new Location(15, 7), world));
		a.add(Spawner.greenSlime(new Location(13, 8), world));
		a.add(Spawner.blueSlime(new Location(14, 10), world));
		a.add(Spawner.bat(new Location(14, 9), world));
		world.getTiles().getTile(17, 10).setTriggerEntity(world, a);
		a = new ArrayList<Entity>();
		a.add(Spawner.cursedMan(new Location(4, 6), world));
		a.add(Spawner.cursedMan(new Location(6, 6), world));
		world.getTiles().getTile(6,  12).setTriggerEntity(world, a);
		world.addEntity(Spawner.redSpawner(new Location(10.5, 17), world));
		a = new ArrayList<Entity>();
		a.add(Spawner.blueSpawner(new Location(4.5, 13.5), world));
		for(int i = 0; i < 10; i++)
			a.add(Spawner.bat(new Location(Algorithms.randMult(10, 29), Algorithms.randMult(16, 17)), world));
		world.getTiles().getTile(23, 13).setTriggerEntity(world, a);
	}

}
