package com.banished.levels;

import com.banished.core.Direction;
import com.banished.core.HUD;
import com.banished.core.Location;
import com.banished.core.entities.Player;

public class LevelFinal extends LevelState {
	
	public LevelFinal(HUD hud, Player p)
	{
		super("levels/Final.blvl", hud, p, new Location(10.5, 27.5), Direction.North, "book/blankPage.png");
	}
	
	public void addEntities()
	{
		
	}

}
