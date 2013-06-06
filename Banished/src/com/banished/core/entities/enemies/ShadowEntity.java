package com.banished.core.entities.enemies;

import com.banished.core.Direction;
import com.banished.core.Location;
import com.banished.core.Tile;
import com.banished.core.World;
import com.banished.core.entities.EntityImageSet;
import com.banished.core.entities.Player;
import com.banished.graphics.Image;

public class ShadowEntity extends EnemyEntity {
	
	private static final float IMG_W = 128, IMG_H = 128;
	
	private static final double SHADOW_MAX_HEALTH = 150;
	private static final double SHADOW_RANGE = 2;
	private static final double SHADOW_STRENGTH = 85;
	private static final double SHADOW_DEFENSE = 45;
	private static final double SHADOW_SPEED = .2;
	private static final double SHADOW_SIGHT_RANGE = 10;
	private static final double SHADOW_TIME_TO_ATTACK = 4;
	private static final double SHADOW_STABILITY = .95;
	private static final double SHADOW_CRIT_RATE = .25;
	private static final double SHADOW_CRIT_MULT = 1.5;
	private static final double SHADOW_STURDINESS = .8;
	private static final int SHADOW_EXP = 100;
	
	private Image[] damaged;
	private Image[] normal;
	
	public ShadowEntity(Location location, World world)
	{
		super(location, world, IMG_W/Tile.SIZE, IMG_H/Tile.SIZE, 0, IMG_H/Tile.SIZE/2, IMG_W/2/Tile.SIZE,
				SHADOW_MAX_HEALTH, SHADOW_STRENGTH, SHADOW_DEFENSE, SHADOW_SPEED, SHADOW_RANGE, SHADOW_TIME_TO_ATTACK, 
				SHADOW_SIGHT_RANGE, SHADOW_EXP, 1, 50);
		
		normal = new Image[]
			{
				Image.fromFile("entities/living_entities/shadow/sdw0.png"),
				Image.fromFile("entities/living_entities/shadow/sdw1.png"),
				Image.fromFile("entities/living_entities/shadow/sdw2.png")
			};
		damaged = new Image[]
			{
				Image.fromFile("entities/living_entities/shadow/sdwhit.png"),
				Image.fromFile("entities/living_entities/shadow/sdwhit.png"),
				Image.fromFile("entities/living_entities/shadow/sdwhit.png")
			};
		
		images = new EntityImageSet(normal, .5);
	}
	
	public void update(double frameTime)
	{
		super.update(frameTime);
		images.update(frameTime);
	}

	public void flashImage() 
	{
		images = new EntityImageSet(damaged, .5);
	}

	public void revertImage()
	{
		images = new EntityImageSet(normal, .5);
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

	protected Location getTargetDirection() 
	{
		Player player = getPlayerAsTarget();
		if (player != null)
		{
			Location playerLoc = player.getLocation();
			return new Location(playerLoc.getX() - this.getLocation().getX(),
					playerLoc.getY() - this.getLocation().getY());
		}
		
		return null;
	}
	
	private Player getPlayerAsTarget()
	{
		Player player = Player.get();
		if (player == null) return null;
		if (player.getLocation().distanceTo(this.getLocation()) > SHADOW_SIGHT_RANGE)
			return null;
		
		return player;
	}

	protected void calcDamageSets() 
	{
		this.MAX_DAMAGE = SHADOW_STRENGTH;
		this.STABILITY = SHADOW_STABILITY;
		this.CRIT_RATE = SHADOW_CRIT_RATE;
		this.CRIT_MULT = SHADOW_CRIT_MULT;
	}
	
	protected void calcDefenseSets() 
	{
		this.DEFENSE = SHADOW_DEFENSE;
		this.STURDINESS = SHADOW_STURDINESS;
	}
	
	public void onDeath() 
	{
		Player.get().incrementExperience(EXP);
		this.getWorld().addEntityToDie(this);
	}

	public Image getImage() 
	{
		return images.get(Direction.North);
	}

}

