package com.banished.levels;

import com.banished.core.Direction;
import com.banished.core.HUD;
import com.banished.core.Location;
import com.banished.core.Spawner;
import com.banished.core.entities.Player;

public class LevelFinal extends LevelState {
	
	public LevelFinal(HUD hud, Player p)
	{
		super("levels/Final.blvl", hud, p, new Location(9.5, 47.5), Direction.North, "book/blankPage.png");
	}
	
	public void addEntities()
	{
		world.addEntity(Spawner.finalBoss(new Location(9.5, 9.5), world));
	}

}
