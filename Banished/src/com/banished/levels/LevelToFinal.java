package com.banished.levels;

import com.banished.core.Direction;
import com.banished.core.HUD;
import com.banished.core.Location;
import com.banished.core.entities.Player;
import com.banished.core.entities.nonliving.PortalForwardEntity;

public class LevelToFinal extends LevelState {
	
	public LevelToFinal(HUD hud, Player p)
	{
		super("levels/ToFinal.blvl", hud, p, new Location(1.5, 38.5), Direction.North, "book/blankPage.png");
	}
	
	public void addEntities()
	{
		world.addEntity(new PortalForwardEntity(world, new Location(1, 1), this));
	}

}
