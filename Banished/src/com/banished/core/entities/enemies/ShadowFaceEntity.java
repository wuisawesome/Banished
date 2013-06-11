package com.banished.core.entities.enemies;

import com.banished.core.Direction;
import com.banished.core.Location;
import com.banished.core.Spawner;
import com.banished.core.State;
import com.banished.core.Tile;
import com.banished.core.World;
import com.banished.core.entities.EntityImageSet;
import com.banished.core.entities.Player;
import com.banished.core.entities.nonliving.PortalForwardEntity;
import com.banished.graphics.Image;
import com.banished.levels.LevelState;

public class ShadowFaceEntity extends EnemyEntity
{
	private static final double SHADOWFACE_MAX_HEALTH = 10000;
	private static final double SHADOWFACE_RANGE = 6.5;
	private static final double SHADOWFACE_STRENGTH = 100;
	private static final double SHADOWFACE_DEFENSE = 60;
	private static final double SHADOWFACE_SPEED = 0;
	private static final double SHADOWFACE_SIGHT_RANGE = 6.5;
	private static final double SHADOWFACE_TIME_TO_ATTACK = 3;
	private static final double SHADOWFACE_STABILITY = .9;
	private static final double SHADOWFACE_CRIT_RATE = .25;
	private static final double SHADOWFACE_CRIT_MULT = 5;
	private static final double SHADOWFACE_STURDINESS = .9;
	private static final int SHADOWFACE_EXP = 1000;

	private static final float IMG_W = 320, IMG_H = 128;
	
	private ShadowEyeEntity left, right;
	
	private Image[] damaged;
	private Image[] normal;
	
	public ShadowFaceEntity(Location location, World world)
	{
		super(location, world, IMG_W/Tile.SIZE, IMG_H/Tile.SIZE, 0, IMG_H/Tile.SIZE/2, IMG_W/2/Tile.SIZE,
				SHADOWFACE_MAX_HEALTH, SHADOWFACE_STRENGTH, SHADOWFACE_DEFENSE, SHADOWFACE_SPEED, SHADOWFACE_RANGE, 
				SHADOWFACE_TIME_TO_ATTACK, SHADOWFACE_SIGHT_RANGE, SHADOWFACE_EXP, 0, 40);
		
		normal = new Image[]
		{ 
			Image.fromFile("entities/living_entities/shadowface/shadowmouth.png"),
			Image.fromFile("entities/living_entities/shadowface/shadowmouth.png"),
			Image.fromFile("entities/living_entities/shadowface/shadowmouth.png")
		};
		
		damaged = new Image[]
				{ 
					Image.fromFile("entities/living_entities/shadowface/shadowmouthhit.png"),
					Image.fromFile("entities/living_entities/shadowface/shadowmouthhit.png"),
					Image.fromFile("entities/living_entities/shadowface/shadowmouthhit.png")
				};
		
		images = new EntityImageSet(normal, 1);
		
		left = Spawner.shadowEye(new Location(8, 5.5), world);
		right = Spawner.shadowEye(new Location(13, 5.5), world);
		
		getWorld().addEntityToAdd(left);
		getWorld().addEntityToAdd(right);
	}
	
	public ShadowEyeEntity getLeft()
	{
		return left;
	}
	
	public ShadowEyeEntity getRight()
	{
		return right;
	}

	public void update(double frameTime)
	{
		super.update(frameTime);
		images.update(frameTime);
	}

	public Image getImage()
	{
		return images.get(Direction.North);
	}

	public void attackPlayer()
	{
		if (Player.get() != null)
		{
			if (Player.get().getLocation().distanceTo(this.getLocation()) > this.RANGE)
				return;
			this.attack(Player.get());
		}
	}
	
	protected void calcDamageSets()
	{ 
		this.MAX_DAMAGE = SHADOWFACE_STRENGTH;
		this.STABILITY = SHADOWFACE_STABILITY;
		this.CRIT_RATE = SHADOWFACE_CRIT_RATE;
		this.CRIT_MULT = SHADOWFACE_CRIT_MULT;
	}
	
	protected void calcDefenseSets()
	{
		this.DEFENSE = SHADOWFACE_DEFENSE;
		this.STURDINESS = SHADOWFACE_STURDINESS;
	}
	
	public void onDeath()
	{
		Player.get().incrementExperience(EXP);
		this.getWorld().addEntityToAdd(new PortalForwardEntity(this.getWorld(), new Location(10, 2), (LevelState)State.GetCurrentState()));
		this.getWorld().addEntityToDie(this);
	}

	protected Location getTargetDirection() 
	{
		return this.getLocation();
	}
	
	public void flashImage()
	{
		images = new EntityImageSet(damaged, 1);
	}
	
	public void revertImage()
	{
		images = new EntityImageSet(normal, 1);
	}
	
	public String toString(){ return "SHADOWFACE";}
	
	public String toText()
	{
		return super.toText();
	}
}
