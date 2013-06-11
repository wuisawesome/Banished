package com.banished.core.entities.enemies;

import com.banished.core.Algorithms;
import com.banished.core.Direction;
import com.banished.core.Location;
import com.banished.core.Spawner;
import com.banished.core.Tile;
import com.banished.core.World;
import com.banished.core.entities.EntityImageSet;
import com.banished.core.entities.Player;
import com.banished.graphics.Image;

public class BanishedEntity extends EnemyEntity
{
	private static final double BANISHED_MAX_HEALTH = 50000;
	private static final double BANISHED_RANGE = 6;
	private static final double BANISHED_STRENGTH = 250;
	private static final double BANISHED_DEFENSE = 150;
	private static final double BANISHED_SPEED = 0;
	private static final double BANISHED_SIGHT_RANGE = 6;
	private static final double BANISHED_TIME_TO_ATTACK = 2;
	private static final double BANISHED_STABILITY = .95;
	private static final double BANISHED_CRIT_RATE = .4;
	private static final double BANISHED_CRIT_MULT = 3;
	private static final double BANISHED_STURDINESS = .95;
	private static final double BANISHED_SPAWN_RANGE = 7;
	private static final double BANISHED_MAX_HEAL_AMOUNT = 4000;
	private static final double BANISHED_HEAL_MULT = .25;
	private static final int BANISHED_EXP = 15000;

	private static final float IMG_W = 64, IMG_H = 96;
	
	private double timeSinceSpawn;
	private final double TIME_TO_SPAWN = 8;
	protected boolean canSpawn;
	
	private double timeSinceHeal;
	private final double TIME_TO_HEAL = 10;
	protected boolean canHeal;
	
	private Image[] normal;
	
	public BanishedEntity(Location location, World world)
	{
		super(location, world, IMG_W/Tile.SIZE, IMG_H/Tile.SIZE, 0, IMG_H/Tile.SIZE/2, IMG_W/2/Tile.SIZE,
				BANISHED_MAX_HEALTH, BANISHED_STRENGTH, BANISHED_DEFENSE, BANISHED_SPEED, BANISHED_RANGE, 
				BANISHED_TIME_TO_ATTACK, BANISHED_SIGHT_RANGE, BANISHED_EXP, 0, 40);
		
		normal = new Image[]
		{ 
			Image.fromFile("entities/living_entities/finalboss/finalBoss.png"),
			Image.fromFile("entities/living_entities/finalboss/finalBoss.png"),
			Image.fromFile("entities/living_entities/finalboss/finalBoss.png")
		};
		
		images = new EntityImageSet(normal, 1);
		
		this.canSpawn = false;
		this.timeSinceSpawn = 0;
	}
	
	private void resetTimeSinceSpawn()
	{
		canSpawn = false;
		timeSinceSpawn = 0;
	}
	
	private void resetTimeSinceHeal()
	{
		canHeal = false;
		timeSinceHeal = 0;
	}
	
	public void update(double frameTime)
	{
		super.update(frameTime);
		images.update(frameTime);
		
		timeSinceSpawn += frameTime;
		timeSinceHeal += frameTime;
		
		if(timeSinceSpawn >= TIME_TO_SPAWN)
			canSpawn = true;
		if(timeSinceHeal >= TIME_TO_HEAL)
			canHeal = true;
		
		if(canSpawn){
			world.addEntityToAdd(Spawner.shadowEye(new Location(
					Algorithms.randMult(getLocation().getX() - BANISHED_SPAWN_RANGE, getLocation().getX() + BANISHED_SPAWN_RANGE), 
					Algorithms.randMult(getLocation().getY() - BANISHED_SPAWN_RANGE, getLocation().getY() + BANISHED_SPAWN_RANGE)), world));
			resetTimeSinceSpawn();
		}
		if(canHeal){
			health = Algorithms.increment(health, Algorithms.getValInRange(BANISHED_MAX_HEAL_AMOUNT, BANISHED_HEAL_MULT, 1), 0, MAX_HEALTH);
			resetTimeSinceHeal();
		}
		
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
		this.MAX_DAMAGE = BANISHED_STRENGTH;
		this.STABILITY = BANISHED_STABILITY;
		this.CRIT_RATE = BANISHED_CRIT_RATE;
		this.CRIT_MULT = BANISHED_CRIT_MULT;
	}
	
	protected void calcDefenseSets()
	{
		this.DEFENSE = BANISHED_DEFENSE;
		this.STURDINESS = BANISHED_STURDINESS;
	}
	
	public void onDeath()
	{
		Player.get().incrementExperience(EXP);
		//this.getWorld().addEntityToAdd(new PortalForwardEntity(this.getWorld(), new Location(10, 2), (LevelState)State.GetCurrentState()));
		this.getWorld().addEntityToDie(this);
	}

	protected Location getTargetDirection() 
	{
		return this.getLocation();
	}
	
	public void flashImage(){}
	
	public void revertImage(){}
	
	public String toString(){ return "BANISHED";}
	
	public String toText()
	{
		return super.toText();
	}
}
