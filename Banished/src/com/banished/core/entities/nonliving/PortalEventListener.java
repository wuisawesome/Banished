package com.banished.core.entities.nonliving;

import com.banished.core.entities.Entity;
import com.banished.core.entities.EntityEventListener;
import com.banished.core.entities.Player;
import com.banished.levels.LevelState;

public class PortalEventListener implements EntityEventListener {
	
	private PortalEntity portal;
	private LevelState state;
	
	public PortalEventListener(PortalEntity portal, LevelState state)
	{
		this.portal = portal;
		this.state = state;
	}

	public void onTouched(Entity sender) 
	{
		if(portal instanceof PortalForwardEntity && sender instanceof Player)
			state.toNext();
	}

	public void onLeft(Entity sender) {}

	public void onInteractedWith() {}
	
	
}
