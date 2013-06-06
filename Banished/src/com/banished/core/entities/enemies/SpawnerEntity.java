package com.banished.core.entities.enemies;

import com.banished.core.Direction;
import com.banished.core.Location;
import com.banished.core.Spawner;
import com.banished.core.Tile;
import com.banished.core.World;
import com.banished.core.entities.EntityImageSet;
import com.banished.core.entities.LivingEntity;
import com.banished.core.entities.Player;
import com.banished.graphics.Color;
import com.banished.graphics.Image;

public class SpawnerEntity extends EnemyEntity
{
	private static final double SPAWNER_MAX_HEALTH = 100;
	private static final double SPAWNER_RANGE = 5;
	private static final double SPAWNER_STRENGTH = 0;
	private static final double SPAWNER_DEFENSE = 4;
	private static final double SPAWNER_SPEED = 0;
	private static final double SPAWNER_SIGHT_RANGE = 5;
	private static final double SPAWNER_TIME_TO_ATTACK = 5;
	private static final double SPAWNER_STABILITY = 0;
	private static final double SPAWNER_CRIT_RATE = 0;
	private static final double SPAWNER_CRIT_MULT = 0;
	private static final double SPAWNER_STURDINESS = .5;
	private static final int SPAWNER_EXP = 15;
	
	private Color COLOR;
	private Color color;

	private static final float IMG_W = 64, IMG_H = 64;
	
	private double rotation;
	
	public SpawnerEntity(Location location, World world, Color color, double mult)
	{
		super(location, world, IMG_W/Tile.SIZE, IMG_H/Tile.SIZE, 0, IMG_H/Tile.SIZE/2, IMG_W/2/Tile.SIZE,
				SPAWNER_MAX_HEALTH, SPAWNER_STRENGTH, SPAWNER_DEFENSE, SPAWNER_SPEED, SPAWNER_RANGE, 
				SPAWNER_TIME_TO_ATTACK, SPAWNER_SIGHT_RANGE, SPAWNER_EXP*(int)mult, 0, 40);
		
		images = new EntityImageSet(new Image[]
		{ 
			Image.fromFile("entities/living_entities/spawner/spawner.png"),
			Image.fromFile("entities/living_entities/spawner/spawner.png"),
			Image.fromFile("entities/living_entities/spawner/spawner.png")
		}, .2);
		
		COLOR = color;
		this.color = color;
		rotation = 0;
		
	}
	
	public void attack(LivingEntity target)
	{
		resetTimeSinceAttack();
		if(color == Color.Green)
			world.addEntityToAdd(Spawner.greenSlime(getLocation(), getWorld()));
		else if(color == Color.Red)
			world.addEntityToAdd(Spawner.redSlime(getLocation(), getWorld()));
		else if(color == Color.Blue)
			world.addEntityToAdd(Spawner.blueSlime(getLocation(), getWorld()));
		else if(color == Color.Violet)
			world.addEntityToAdd(Spawner.violetSlime(getLocation(), getWorld()));
		else if(color == Color.White){
			double rand = Math.random();
			if(rand < .25)
				world.addEntityToAdd(Spawner.greenSlime(getLocation(), getWorld()));
			else if(rand < .5)
				world.addEntityToAdd(Spawner.redSlime(getLocation(), getWorld()));
			else if(rand < .75)
				world.addEntityToAdd(Spawner.blueSlime(getLocation(), getWorld()));
			else
				world.addEntityToAdd(Spawner.violetSlime(getLocation(), getWorld()));
		}
	}

	public void update(double frameTime)
	{
		super.update(frameTime);
		images.update(frameTime);
		rotation += 3;
	}

	public Image getImage()
	{
		return images.get(Direction.North);
	}
	
	public Color getSpawnerColor()
	{
		return this.color;
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
		this.MAX_DAMAGE = SPAWNER_STRENGTH;
		this.STABILITY = SPAWNER_STABILITY;
		this.CRIT_RATE = SPAWNER_CRIT_RATE;
		this.CRIT_MULT = SPAWNER_CRIT_MULT;
	}
	
	protected void calcDefenseSets()
	{
		this.DEFENSE = SPAWNER_DEFENSE;
		this.STURDINESS = SPAWNER_STURDINESS;
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
		color = Color.Black;
	}
	
	public void revertImage()
	{
		color = COLOR;
	}
	
	public float getRotation()
	{
		return (float)rotation;
	}
	
	public String toString(){ return "SPAWNER";}
	
	public String toText()
	{
		return super.toText() + " color=" + color;
	}
}
