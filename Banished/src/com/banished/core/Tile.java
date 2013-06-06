package com.banished.core;

import java.util.List;
import java.util.ListIterator;
import com.banished.core.entities.Entity;
import com.banished.exceptions.InvalidTileTypeException;
import com.banished.graphics.Image;

public class Tile {
	public static final int SIZE = 64;

	private Coordinate location;
	private int type;
	private Object imageId;
	private boolean solid;
	private TriggerHandler delegate;
	private boolean triggered;
	private World world;
	private List<Entity> toShow;

	public Tile(Coordinate location, int type, Object imageId, boolean solid)
			throws InvalidTileTypeException {
		if (type < 0)
			throw new InvalidTileTypeException(type);

		this.location = location;
		this.type = type;
		this.imageId = imageId;
		this.solid = solid;
		this.delegate = null;
		this.triggered = false;
	}

	public Tile(Tile copy, Coordinate location) throws InvalidTileTypeException {
		this(location, copy.getType(), copy.getImageId(), copy.isSolid());
	}

	public void setDeletage(TriggerHandler delegate) {
		this.delegate = delegate;
	}

	public Coordinate getLocation() {
		return new Coordinate(this.location);
	}

	public int getType() {
		return this.type;
	}

	public Object getImageId() {
		return this.imageId;
	}

	public boolean isSolid() {
		return this.solid;
	}

	public Image getImage() {
		return Tiles.tileImages.get(this.getImageId());
	}

	public void entitySteppedOnTile(Entity ent) {
		if (triggered)
			return;
		if (delegate != null) {
			delegate.tileSteppedOn(this, ent);
		}

		if ((world != null) && (toShow != null) && (toShow.size() > 0)) {
			ListIterator<Entity> iter = toShow.listIterator();
			while(iter.hasNext())
				world.addEntityToAdd(iter.next());
		}

		triggered = true;
	}

	public void setTriggerEntity(World world, List<Entity> ent) {
		this.world = world;
		this.toShow = ent;
	}
}
