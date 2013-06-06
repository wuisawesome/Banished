package com.banished.levels;

import java.util.ArrayList;

import com.banished.core.items.Armor;
import com.banished.core.items.Equippable;
import com.banished.core.items.Inventory;
import com.banished.core.items.Item;
import com.banished.core.items.Items;
import com.banished.core.Direction;
import com.banished.core.HUD;
import com.banished.core.Location;
import com.banished.core.Spawner;
import com.banished.core.entities.CircleCollisionShape;
import com.banished.core.entities.Entity;
import com.banished.core.entities.Player;
import com.banished.core.entities.nonliving.ChestEntity;
import com.banished.core.entities.nonliving.PortalForwardEntity;

@SuppressWarnings("unused")
public class LevelTwo extends LevelState {
	
	public LevelTwo(HUD hud, Player p)
	{
		super("levels/Two.blvl", hud, p, new Location(1.5, 12.5), Direction.North, "book/pageTwo.png");
	}
	
	public void addEntities()
	{
		world.addEntity(new PortalForwardEntity(world, new Location(19, 5), this));
		world.addEntity(Spawner.redSlime(new Location(4, 5), world));
		world.addEntity(Spawner.greenSpawner(new Location(8.5, 1.5), world));
		world.addEntity(Spawner.bat(new Location(14,  8), world));
		world.addEntity(Spawner.redSlime(new Location(16,  8), world));
		world.addEntity(Spawner.blueSlime(new Location(18,  9), world));
		world.addEntity(Spawner.bat(new Location(22,  8), world));
		world.addEntity(Spawner.bat(new Location(22,  5), world));
		world.addEntity(Spawner.blueSpawner(new Location(17.5,  1.5), world));
		world.addEntity(Spawner.redSpawner(new Location(18.5,  5.5), world));
		
		Inventory inv = new Inventory();
		try
		{
			inv.add(new Armor(Items.IronChest));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		world.addEntity(new ChestEntity(world, new Location(10.25, 2.25), inv));
	}

}
