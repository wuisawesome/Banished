package com.banished.levels;

import com.banished.core.Direction;
import com.banished.core.HUD;
import com.banished.core.Location;
import com.banished.core.Spawner;
import com.banished.core.entities.Player;
import com.banished.core.entities.nonliving.PortalForwardEntity;

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
	}
}