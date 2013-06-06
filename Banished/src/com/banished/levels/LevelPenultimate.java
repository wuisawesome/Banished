package com.banished.levels;

import com.banished.core.Direction;
import com.banished.core.HUD;
import com.banished.core.Location;
import com.banished.core.Spawner;
import com.banished.core.entities.Player;
import com.banished.core.entities.nonliving.PortalForwardEntity;

public class LevelPenultimate extends LevelState {
	
	public LevelPenultimate(HUD hud, Player p)
	{
		super("levels/Penultimate.blvl", hud, p, new Location(10.5, 19.5), Direction.North, "book/finalEntry.png");
	}
	
	public void addEntities()
	{
		world.addEntity(new PortalForwardEntity(world, new Location(10, 2), this));
		world.addEntity(Spawner.shadowEye(new Location(8, 5.5), world));
		world.addEntity(Spawner.shadowEye(new Location(13, 5.5), world));
	}

}
