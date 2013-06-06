package com.banished.levels;

import java.util.ArrayList;

import com.banished.core.Direction;
import com.banished.core.HUD;
import com.banished.core.Location;
import com.banished.core.Spawner;
import com.banished.core.entities.Entity;
import com.banished.core.entities.Player;
import com.banished.core.entities.nonliving.ChestEntity;
import com.banished.core.entities.nonliving.PortalForwardEntity;
import com.banished.core.items.Inventory;

public class LevelThree extends LevelState {
	
	public LevelThree(HUD hud, Player p)
	{
		super("levels/Three.blvl", hud, p, new Location(13.5, 6.5), Direction.West, "book/pageThree.png");
	}
	
	public void addEntities()
	{
		world.addEntity(new PortalForwardEntity(world, new Location(1, 1), this));
		world.addEntity(Spawner.bat(new Location(6, 15), world));
		world.addEntity(Spawner.bat(new Location(20, 5), world));
		world.addEntity(Spawner.bat(new Location(1, 12), world));
		world.addEntity(Spawner.redSpawner(new Location(6.5, 1.5), world));
		world.addEntity(Spawner.greenSpawner(new Location(1.5, 3.5), world));
		world.addEntity(Spawner.greenSpawner(new Location(1.5, 4.5), world));
		ArrayList<Entity> a = new ArrayList<Entity>();
		a.add(Spawner.blueSpawner(new Location(14.5, 15.5), world));
		a.add(Spawner.redSpawner(new Location(20.5, 10.5), world));
		world.getTiles().getTile(15, 9).setTriggerEntity(world, a);
		a = new ArrayList<Entity>();
		a.add(new ChestEntity(world, new Location(20.25, 15.25), new Inventory()));
		world.getTiles().getTile(4, 1).setTriggerEntity(world, a);
	}

}
