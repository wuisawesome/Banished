package com.banished.levels;

import java.util.ArrayList;

import com.banished.core.Direction;
import com.banished.core.HUD;
import com.banished.core.Location;
import com.banished.core.entities.Entity;
import com.banished.core.entities.Player;
import com.banished.core.entities.enemies.ShadowFaceEntity;
import com.banished.core.entities.nonliving.ChestEntity;
import com.banished.core.items.Inventory;
import com.banished.core.items.Items;
import com.banished.core.items.Weapon;

public class LevelPenultimate extends LevelState {
	
	public LevelPenultimate(HUD hud, Player p)
	{
		super("levels/Penultimate.blvl", hud, p, new Location(10.5, 19.5), Direction.North, "book/finalEntry.png");
	}
	
	public void addEntities()
	{
		world.addEntity(new ShadowFaceEntity(new Location(10.5, 8.5), world));
		Inventory inv = new Inventory();
		inv.add(new Weapon(Items.EnchantedSword));
		ArrayList<Entity> a = new ArrayList<Entity>();
		a.add(new ChestEntity(world, new Location(10.25, 4.25), inv));
		world.getTiles().getTile(19, 19).setTriggerEntity(world, a);
	}

}
