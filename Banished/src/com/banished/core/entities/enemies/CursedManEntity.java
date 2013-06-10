package com.banished.core.entities.enemies;

import com.banished.core.Location;
import com.banished.core.Tile;
import com.banished.core.World;
import com.banished.core.entities.EntityImageSet;
import com.banished.core.entities.Player;
import com.banished.graphics.Image;

public class CursedManEntity extends EnemyEntity {
	
	private static final float IMG_W = 30, IMG_H = 58;
	public static final double	SHAPE_YOFFSET = .13,
			LOC_YOFFSET = .6,
			RADIUS = .2;
	
	private static final double CURSEDMAN_MAX_HEALTH = 100;
	private static final double CURSEDMAN_RANGE = .5;
	private static final double CURSEDMAN_STRENGTH = 25;
	private static final double CURSEDMAN_DEFENSE = 10;
	private static final double CURSEDMAN_SPEED = 2;
	private static final double CURSEDMAN_SIGHT_RANGE = 4.5;
	private static final double CURSEDMAN_TIME_TO_ATTACK = .3;
	private static final double CURSEDMAN_STABILITY = 0.5;
	private static final double CURSEDMAN_CRIT_RATE = 0.05;
	private static final double CURSEDMAN_CRIT_MULT = 1.2;
	private static final double CURSEDMAN_STURDINESS = 0.5;
	private static final int CURSEDMAN_EXP = 150;
	
	public CursedManEntity(Location location, World world)
	{
		super(location, world, IMG_W/Tile.SIZE, IMG_H/Tile.SIZE, SHAPE_YOFFSET, LOC_YOFFSET, RADIUS, CURSEDMAN_MAX_HEALTH, 
				CURSEDMAN_STRENGTH, CURSEDMAN_DEFENSE, CURSEDMAN_SPEED, CURSEDMAN_RANGE, CURSEDMAN_TIME_TO_ATTACK, CURSEDMAN_SIGHT_RANGE, CURSEDMAN_EXP, 3, 50);
		
		images = new EntityImageSet(new Image[]
				{
				Image.fromFile("entities/living_entities/zombie/plleft.png"),
				Image.fromFile("entities/living_entities/zombie/plleft1.png"),
				Image.fromFile("entities/living_entities/zombie/plleft2.png")
		},
		new Image[]
		{
				Image.fromFile("entities/living_entities/zombie/plright.png"),
				Image.fromFile("entities/living_entities/zombie/plright1.png"),
				Image.fromFile("entities/living_entities/zombie/plright2.png")
		},
		new Image[]
		{
				Image.fromFile("entities/living_entities/zombie/plback.png"),
				Image.fromFile("entities/living_entities/zombie/plback1.png"),
				Image.fromFile("entities/living_entities/zombie/plback2.png")
		},
		new Image[]
		{
				Image.fromFile("entities/living_entities/zombie/plfront.png"),
				Image.fromFile("entities/living_entities/zombie/plfront1.png"),
				Image.fromFile("entities/living_entities/zombie/plfront2.png")
		});
	}
	
	public void update(double frameTime)
	{
		super.update(frameTime);
		images.update(frameTime);
	}
	
	public void flashImage() {}
	public void revertImage() {}
	
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
		if (player.getLocation().distanceTo(this.getLocation()) > CURSEDMAN_SIGHT_RANGE)
			return null;
		
		return player;
	}
	
	protected void calcDamageSets() 
	{
		this.MAX_DAMAGE = CURSEDMAN_STRENGTH;
		this.STABILITY = CURSEDMAN_STABILITY;
		this.CRIT_RATE = CURSEDMAN_CRIT_RATE;
		this.CRIT_MULT = CURSEDMAN_CRIT_MULT;
	}
	
	protected void calcDefenseSets() 
	{
		this.DEFENSE = CURSEDMAN_DEFENSE;
		this.STURDINESS = CURSEDMAN_STURDINESS;
	}
	
	public void onDeath() 
	{
		Player.get().incrementExperience(EXP);
		this.getWorld().addEntityToDie(this);
	}
	
	public Image getImage()
	{
		return this.images.get(this.getDirection());
	}

}
