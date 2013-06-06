package com.banished.levels;

import java.util.ArrayList;

import com.banished.core.items.Inventory;
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
public class LevelOne extends LevelState {
	
	public LevelOne(HUD hud, Player p)
	{
		super("levels/One.blvl", hud, p, new Location(5.5, 13.5), Direction.North, "book/pageOne.png");
		Initialize();
	}
	
	public void addEntities()
	{
		world.addEntity(new PortalForwardEntity(world, new Location(5, 1), this));
		world.addEntity(Spawner.greenSlime(new Location(5, 10), world));
		ArrayList<Entity> a = new ArrayList<Entity>();
		a.add(Spawner.bat(new Location(3, 5), world));
		world.getTiles().getTile(5, 11).setTriggerEntity(world, a);
	}

}
