package com.banished.core.entities.enemies;

import com.banished.core.Algorithms;
import com.banished.core.Direction;
import com.banished.core.Location;
import com.banished.core.Spawner;
import com.banished.core.Tile;
import com.banished.core.World;
import com.banished.core.entities.EntityImageSet;
import com.banished.core.entities.LivingEntity;
import com.banished.core.entities.Player;
import com.banished.graphics.Image;

public class ShadowEyeEntity extends EnemyEntity
{
	private static final double SHADOWEYE_MAX_HEALTH = 700;
	private static final double SHADOWEYE_RANGE = 7;
	private static final double SHADOWEYE_STRENGTH = 0;
	private static final double SHADOWEYE_DEFENSE = 25;
	private static final double SHADOWEYE_SPEED = 0;
	private static final double SHADOWEYE_SIGHT_RANGE = 7;
	private static final double SHADOWEYE_TIME_TO_ATTACK = 20;
	private static final double SHADOWEYE_STABILITY = 0;
	private static final double SHADOWEYE_CRIT_RATE = 0;
	private static final double SHADOWEYE_CRIT_MULT = 0;
	private static final double SHADOWEYE_STURDINESS = .8;
	private static final int SHADOWEYE_EXP = 300;

	private static final float IMG_W = 192, IMG_H = 96;
	
	private Image[] damaged;
	private Image[] normal;
	
	public ShadowEyeEntity(Location location, World world)
	{
		super(location, world, IMG_W/Tile.SIZE, IMG_H/Tile.SIZE, 0, IMG_H/Tile.SIZE/2, IMG_W/2/Tile.SIZE,
				SHADOWEYE_MAX_HEALTH, SHADOWEYE_STRENGTH, SHADOWEYE_DEFENSE, SHADOWEYE_SPEED, SHADOWEYE_RANGE, 
				SHADOWEYE_TIME_TO_ATTACK, SHADOWEYE_SIGHT_RANGE, SHADOWEYE_EXP, 0, 40);
		
		normal = new Image[]
		{ 
			Image.fromFile("entities/living_entities/shadowface/shadoweye.png"),
			Image.fromFile("entities/living_entities/shadowface/shadoweye.png"),
			Image.fromFile("entities/living_entities/shadowface/shadoweye.png")
		};
		
		damaged = new Image[]
				{ 
					Image.fromFile("entities/living_entities/shadowface/shadoweyered.png"),
					Image.fromFile("entities/living_entities/shadowface/shadoweyered.png"),
					Image.fromFile("entities/living_entities/shadowface/shadoweyered.png")
				};
		
		images = new EntityImageSet(normal, 1);
	}
	
	public void attack(LivingEntity target)
	{
		resetTimeSinceAttack();
		world.addEntityToAdd(Spawner.shadow(getLocation(), getWorld()));
	}
	
	public void resetTimeSinceAttack()
	{
		timeSinceAttack = Algorithms.increment(timeSinceAttack, -Algorithms.getValInRange(SHADOWEYE_TIME_TO_ATTACK, .75, 1), 0, timeSinceAttack);
		canAttack = false;
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
		this.MAX_DAMAGE = SHADOWEYE_STRENGTH;
		this.STABILITY = SHADOWEYE_STABILITY;
		this.CRIT_RATE = SHADOWEYE_CRIT_RATE;
		this.CRIT_MULT = SHADOWEYE_CRIT_MULT;
	}
	
	protected void calcDefenseSets()
	{
		this.DEFENSE = SHADOWEYE_DEFENSE;
		this.STURDINESS = SHADOWEYE_STURDINESS;
	}
	
	public void onDeath()
	{
		Player.get().incrementExperience(EXP);
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
	
	public String toString(){ return "SHADOWEYE";}
	
	public String toText()
	{
		return super.toText();
	}
}
