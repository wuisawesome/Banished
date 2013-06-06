package com.banished.core.entities.nonliving;

import com.banished.core.Location;
import com.banished.core.World;
import com.banished.graphics.Image;
import com.banished.levels.LevelState;

public class PortalForwardEntity extends PortalEntity {
	
	public PortalForwardEntity(World world, Location location, LevelState state)
	{
		super(world, location, Image.fromFile("entities/object_entities/portal/portalforward.png"), state);
	}

}
