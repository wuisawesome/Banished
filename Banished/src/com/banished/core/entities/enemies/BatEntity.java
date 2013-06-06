package com.banished.core.entities.enemies;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import com.banished.core.Direction;
import com.banished.core.Location;
import com.banished.core.Tile;
import com.banished.core.World;
import com.banished.core.entities.EntityImageSet;
import com.banished.core.entities.Player;
import com.banished.graphics.Image;

public class BatEntity extends EnemyEntity {
	
	private static final float IMG_W = 30, IMG_H = 20;
	
	private static final double BAT_MAX_HEALTH = 35;
	private static final double BAT_RANGE = .5;
	private static final double BAT_STRENGTH = 2;
	private static final double BAT_DEFENSE = 1;
	private static final double BAT_SPEED = 2;
	private static final double BAT_SIGHT_RANGE = 6;//5;
	private static final double BAT_TIME_TO_ATTACK = .5;
	private static final double BAT_STABILITY = .15;
	private static final double BAT_CRIT_RATE = .01;
	private static final double BAT_CRIT_MULT = 3;
	private static final double BAT_STURDINESS = .5;
	private static final int BAT_EXP = 5;
	
	private Image[] damaged;
	private Image[] normal;
	
	public BatEntity(Location location, World world)
	{
		super(location, world, IMG_W/Tile.SIZE, IMG_H/Tile.SIZE, 0, IMG_H/Tile.SIZE/2, IMG_W/2/ Tile.SIZE,
				BAT_MAX_HEALTH, BAT_STRENGTH, BAT_DEFENSE, BAT_SPEED, BAT_RANGE, BAT_TIME_TO_ATTACK, 
				BAT_SIGHT_RANGE, BAT_EXP, 25, 20);
		
		normal = new Image[]
			{
				Image.fromFile("entities/living_entities/bat/bat1.png"),
				Image.fromFile("entities/living_entities/bat/bat0.png"),
				Image.fromFile("entities/living_entities/bat/bat2.png")
			};
		damaged = new Image[]
			{
				Image.fromFile("entities/living_entities/bat/bhit1.png"),
				Image.fromFile("entities/living_entities/bat/bhit0.png"),
				Image.fromFile("entities/living_entities/bat/bhit2.png")
			};
		
		images = new EntityImageSet(normal, .08);
	}
	
	public void update(double frameTime)
	{
		super.update(frameTime);
		images.update(frameTime);
	}
	
	public void takeDamage(double damage)
	{
		super.takeDamage(damage);
		 try{
		        AudioInputStream audio = AudioSystem.getAudioInputStream(new File("data/entities/living_entities/bat/batsound.wav").getAbsoluteFile());
		        Clip clip = AudioSystem.getClip();
		        clip.open(audio);
		        FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		        gain.setValue(-5.0f);
		        clip.start();
		    } catch(Exception e){
		        System.out.println("Cannot play sound");
		        e.printStackTrace();
		    }
	}

	public void flashImage() 
	{
		images = new EntityImageSet(damaged, .08);
	}

	public void revertImage()
	{
		images = new EntityImageSet(normal, .08);
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
		if (player.getLocation().distanceTo(this.getLocation()) > BAT_SIGHT_RANGE)
			return null;
		
		return player;
	}

	protected void calcDamageSets() 
	{
		this.MAX_DAMAGE = BAT_STRENGTH;
		this.STABILITY = BAT_STABILITY;
		this.CRIT_RATE = BAT_CRIT_RATE;
		this.CRIT_MULT = BAT_CRIT_MULT;
	}
	
	protected void calcDefenseSets() 
	{
		this.DEFENSE = BAT_DEFENSE;
		this.STURDINESS = BAT_STURDINESS;
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

