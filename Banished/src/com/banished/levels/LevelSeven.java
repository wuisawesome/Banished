package com.banished.levels;

import com.banished.core.Algorithms;
import com.banished.core.Direction;
import com.banished.core.HUD;
import com.banished.core.Location;
import com.banished.core.Spawner;
import com.banished.core.entities.Player;
import com.banished.core.entities.nonliving.PortalForwardEntity;

public class LevelSeven extends LevelState {
	
	public LevelSeven(HUD hud, Player p)
	{
		super("levels/Seven.blvl", hud, p, new Location(5.5, 18.5), Direction.South, "book/pageSeven.png");
	}
	
	public void addEntities()
	{
		world.addEntity(new PortalForwardEntity(world, new Location(5, 1), this));
		
		for(int i = 0; i < 10; i++)
			world.addEntity(Spawner.shadow(new Location(Algorithms.randMult(3, 8), Algorithms.randMult(3, 16)), world));
	}
}