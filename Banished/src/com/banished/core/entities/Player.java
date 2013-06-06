package com.banished.core.entities;

import java.io.File;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import com.banished.Banished;
import com.banished.GameOverState;
import com.banished.core.*;
import com.banished.core.entities.enemies.EnemyEntity;
import com.banished.core.items.Armor;
import com.banished.core.items.Inventory;
import com.banished.core.items.Weapon;
import com.banished.graphics.Animation;
import com.banished.graphics.Image;
import com.banished.input.Key;
import com.banished.input.Mouse;

public class Player extends LivingEntity
{
	private static final double PLAYER_SPEED = 4,
		PLAYER_STRENGTH = 2,
		PLAYER_DEFENSE = 0;
	private static final double PLAYER_TIME_TO_ATTACK = .3;
	
	private static final double FIST_HITBOX_WIDTH = .5,
								FIST_HITBOX_HEIGHT = .5,
								AOE_RADIUS = 3,
								DASH_DIST = 3.5;
	private static final double FIST_DAMAGE = 10,
								FIST_STABILITY = 0.5,
								BASE_CRIT_RATE = 0.05,
								BASE_CRIT_MULT = 1.2,
								BASE_STURDINESS = 0;
	private static final double MAX_STAMINA = 250,
								ATT_STAM_RED = 25,
								AOE_STAM_RED = 200,
								DASH_STAM_RED = 125;
	
	public static final double INTERACTION_RANGE = 1;
	
	public static final double	HEALTH_LIMIT = 9999,
								STAT_LIMIT = 1000;
	
	public static double STAMINA_SLOW_GROWTH_FRAC = 0.1;
	
	private double stamina;
	
	private int level, experience;
	
	public static final double	SHAPE_YOFFSET = .13,
								LOC_YOFFSET = .6,
								RADIUS = .2;
	
	private Inventory inv;
	
	private Animation levelUpAnim;
	
	private static Player singleton;
	public static Player get()
	{
		return singleton;
	}
	
	private Armor chest, legs, shoes, gloves;
	private Weapon weapon;
	private boolean invincibility = false;
	
	
	private boolean moving;
	
	private static double IMG_W = 30, IMG_H = 58;
	public Player(Location location, World world, double maxHealth)
	{
		super(location, world, IMG_W/Tile.SIZE, IMG_H/Tile.SIZE, SHAPE_YOFFSET, LOC_YOFFSET, RADIUS, maxHealth,
				PLAYER_STRENGTH, PLAYER_DEFENSE, PLAYER_SPEED, PLAYER_TIME_TO_ATTACK, Double.MAX_VALUE);
		
		singleton = this;
		
		this.stamina = MAX_STAMINA;
		this.level = 1;
		this.experience = 0;
		
		this.chest = this.legs = this.shoes = this.gloves = null;
		this.weapon = null;
		
		this.images = new EntityImageSet(new Image[]
		{
				Image.fromFile("entities/player/plleft.png"),
				Image.fromFile("entities/player/plleft1.png"),
				Image.fromFile("entities/player/plleft2.png")
		},
		new Image[]
		{
				Image.fromFile("entities/player/plright.png"),
				Image.fromFile("entities/player/plright1.png"),
				Image.fromFile("entities/player/plright2.png")
		},
		new Image[]
		{
				Image.fromFile("entities/player/plback.png"),
				Image.fromFile("entities/player/plback1.png"),
				Image.fromFile("entities/player/plback2.png")
		},
		new Image[]
		{
				Image.fromFile("entities/player/plfront.png"),
				Image.fromFile("entities/player/plfront1.png"),
				Image.fromFile("entities/player/plfront2.png")
		});		
		
		final int LEVEL_UP_FRAMES = 8;
		Image[] levelUpFrames = new Image[LEVEL_UP_FRAMES];
		for (int i = 0; i < LEVEL_UP_FRAMES; i++)
			levelUpFrames[i] = Image.fromFile("effects/levelup/levelup" + i + ".png");
		this.levelUpAnim = new Animation(levelUpFrames, .5, false);
		
		moving = false;
	}
	
	@Override
	public void onDeath()
	{
		if (this != singleton)
		{
			singleton.onDeath();
			return;
		}
		// TODO: Fancy animation here
		removeFromWorld();
		
		//System.out.println("The player died.");		
		State.EnterState(new GameOverState());
	}
	
	public void takeDamage(double damage)
	{
		if(invincibility)
			return;
		super.takeDamage(damage);
	}

	public void attack()
	{
		if (this != singleton)
		{
			singleton.attack();
			return;
		}
		
		if(stamina < ATT_STAM_RED)
			return;
		
		stamina = Algorithms.increment(stamina, -ATT_STAM_RED, 0, MAX_STAMINA);
		
		this.attackSound();
		this.resetTimeSinceAttack();
		// TODO: test this
		
		//calcDamageSets();
		
		double hitBoxWidth = this.weapon == null ? FIST_HITBOX_WIDTH : this.weapon.HITBOX_WIDTH;
		double hitBoxHeight = this.weapon == null ? FIST_HITBOX_HEIGHT : this.weapon.HITBOX_HEIGHT;
		
		RectangleCollisionShape hitBox = null;
		switch (this.getDirection())
		{
		case North: hitBox = new RectangleCollisionShape(
				this.getLocation().getX() - 1.5 * hitBoxWidth,
				this.getLocation().getY() - hitBoxHeight,
				hitBoxWidth * 3, hitBoxHeight);
			break;
		case South: hitBox = new RectangleCollisionShape(
				this.getLocation().getX() - 1.5 * hitBoxWidth,
				this.getLocation().getY(),
				hitBoxWidth * 3, hitBoxHeight);
			break;
		case Northwest: case West: case Southwest: hitBox = new RectangleCollisionShape(
				this.getLocation().getX() - hitBoxHeight,
				this.getLocation().getY() - 1.5 * hitBoxWidth,
				hitBoxHeight, hitBoxWidth * 3);
			break;
		case Northeast: case East: case Southeast: hitBox = new RectangleCollisionShape(
				this.getLocation().getX(),
				this.getLocation().getY() - 1.5 * hitBoxWidth,
				hitBoxHeight, hitBoxWidth * 3);
			break;
		}
		List<EnemyEntity> entitiesToAttack = new ArrayList<EnemyEntity>();
		for (Entity entity  : this.getWorld().getEntities())
		{
			if (entity instanceof EnemyEntity)
			{
				EnemyEntity enemy = (EnemyEntity)entity;
				if (enemy.getShape().collidesWith(hitBox))
				{
					entitiesToAttack.add(enemy);
				}
			}
		}
		for (EnemyEntity target : entitiesToAttack)
			this.attack(target, 1.0 / entitiesToAttack.size());
	}
	
	public void aoe()
	{
		if(this != singleton){
			singleton.aoe();
			return;
		}
		
		if(stamina < AOE_STAM_RED)
			return;
		
		stamina = Algorithms.increment(stamina, -AOE_STAM_RED, 0, MAX_STAMINA);
		
		this.resetTimeSinceAttack();
		
		CircleCollisionShape hitCircle = new CircleCollisionShape(getLocation().getX(), getLocation().getY(), AOE_RADIUS);
		
		List<EnemyEntity> entitiesToAttack = new ArrayList<EnemyEntity>();
		for (Entity entity  : this.getWorld().getEntities())
		{
			if (entity instanceof EnemyEntity)
			{
				EnemyEntity enemy = (EnemyEntity)entity;
				if (enemy.getShape().collidesWith(hitCircle))
				{
					entitiesToAttack.add(enemy);
				}
			}
		}
		for (EnemyEntity target : entitiesToAttack)
			this.attack(target, 3.0 / Math.sqrt(entitiesToAttack.size()));
	}
	
	public void dash()
	{
		if(this != singleton){
			singleton.dash();
			return;
		}
		
		if(stamina < DASH_STAM_RED)
			return;
		
		stamina = Algorithms.increment(stamina, -DASH_STAM_RED, 0, MAX_STAMINA);
		
		this.resetTimeSinceAttack();
		
		RectangleCollisionShape hitBox = null;
		switch (this.getDirection())
		{
		case North: hitBox = new RectangleCollisionShape(
				this.getLocation().getX() - width/2,
				this.getLocation().getY() - DASH_DIST - height/2,
				width, DASH_DIST);
				//dashMove(new Location(getLocation().getX(), getLocation().getY() - DASH_DIST));
			break;
		case South: hitBox = new RectangleCollisionShape(
				this.getLocation().getX() - width/2,
				this.getLocation().getY() + height/2,
				width, DASH_DIST);
				//dashMove(new Location(getLocation().getX(), getLocation().getY() + DASH_DIST));
			break;
		case Northwest: case West: case Southwest: hitBox = new RectangleCollisionShape(
				this.getLocation().getX() - DASH_DIST - width/2,
				this.getLocation().getY() - height/2,
				DASH_DIST, height);
				//dashMove(new Location(getLocation().getX() - DASH_DIST, getLocation().getY()));
			break;
		case Northeast: case East: case Southeast: hitBox = new RectangleCollisionShape(
				this.getLocation().getX() + width/2,
				this.getLocation().getY() - height/2,
				DASH_DIST, height);
				//dashMove(new Location(getLocation().getX() + DASH_DIST, getLocation().getY()));
			break;
		}
		
		List<EnemyEntity> entitiesToAttack = new ArrayList<EnemyEntity>();
		for (Entity entity  : this.getWorld().getEntities())
		{
			if (entity instanceof EnemyEntity)
			{
				EnemyEntity enemy = (EnemyEntity)entity;
				if (enemy.getShape().collidesWith(hitBox))
				{
					entitiesToAttack.add(enemy);
				}
			}
		}
		for (EnemyEntity target : entitiesToAttack)
			this.attack(target, 3.5 / entitiesToAttack.size());
	}
	/*
	private void dashMove(Location direction)
	{
		if (Banished.SHOW_EDGE_DEBUGGING_INFO) if (this == toTrack) lastDir = direction = new Location(direction);
		// movement + wall * dot(movement, wall)
		CollisionShape translated = this.getShape().translate(direction);
		TileBoundary tileBound = translated.getTileBoundary();
		
		for (int x = tileBound.getMinX(); x <= tileBound.getMaxX(); x++)
		{
			for (int y = tileBound.getMinY(); y <= tileBound.getMaxY(); y++)
			{
				Edge[] edges = this.getWorld().getTiles().getTileEdges(x, y);
				for (Edge edge : edges)
				{
					if (edge != null)
					{
						if (translated.collidesWith(edge))
						{
							Location wall = edge.getNormal();
							
							double dot = direction.dot(wall);
							if (dot > 0) continue;
							
							direction = direction.sub(wall.mult(direction.dot(wall)));
							translated = this.getShape().translate(direction);
						}
					}
				}
			}
		}
	}
*/
	private void attackSound()
	{
	    try{
	        AudioInputStream audio = AudioSystem.getAudioInputStream(new File("data/entities/player/swords/swordSound.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audio);
	        FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
	        gain.setValue(-15.0f);
	        clip.start();
	    } catch(Exception e){
	        System.out.println("Cannot play sound");
	        e.printStackTrace();
	    }
	}
	
	protected void calcDamageSets()
	{
		if (this != singleton)
		{
			singleton.calcDamageSets();
			return;
		}
		if (weapon == null)
		{
			STABILITY = FIST_STABILITY;
			MAX_DAMAGE = FIST_DAMAGE * Math.log(STRENGTH);
			CRIT_RATE = BASE_CRIT_RATE;
			CRIT_MULT = BASE_CRIT_MULT;
		}
		else
		{
			STABILITY = weapon.STABILITY;
			MAX_DAMAGE = weapon.MAX_DAMAGE * Math.log(STRENGTH);
		}
	}
	
	protected void calcDefenseSets()
	{
		if(this != singleton)
		{
			singleton.calcDamageSets();
			return;
		}
		
		DEFENSE = PLAYER_DEFENSE;
		STURDINESS = BASE_STURDINESS;
	}
	
	public void heal(double health)
	{
		this.health += health;
		if (this.health > this.MAX_HEALTH)
			this.health = this.MAX_HEALTH;
	}
	public void addStamina(double stamina)
	{
		this.stamina += stamina;
		if (this.stamina >= Player.MAX_STAMINA)
			this.stamina = Player.MAX_STAMINA;
	}

	@Override
	public Location move(double frameTime)
	{
		if (this != singleton)
			return singleton.move(frameTime);
		
		Location movement = new Location(0, 0);
		
		if (Key.isDown(Key.Up))
			movement.setY(movement.getY() - 1);
		if (Key.isDown(Key.Down))
			movement.setY(movement.getY() + 1);
		if (Key.isDown(Key.Left))
			movement.setX(movement.getX() - 1);
		if (Key.isDown(Key.Right))
			movement.setX(movement.getX() + 1);
		
		Direction newDir = Direction.fromBools(movement.getX() == -1, movement.getX() == 1, movement.getY() == -1, movement.getY() == 1);
		if (newDir != null)
			this.setDirection(newDir);
		
		moving = (newDir != null);
		
		if (movement.getSquareMagnitude() != 0)
			movement = movement.normalize();
		movement = movement.mult(frameTime * this.speed);
		
		return this.move(movement);
	}

	public void update(double frameTime)
	{
		super.update(frameTime);
		
		if (Banished.DEBUGGING && Key.isDown(Key.F5))
			this.stamina = MAX_STAMINA;
		if (Banished.DEBUGGING && Key.isDown(Key.F6))
			this.health = MAX_HEALTH;
		if (Banished.DEBUGGING && Key.wasPressed(Key.F10))
			this.invincibility ^= true;
		if (Banished.DEBUGGING && Key.isDown(Key.F11))
			levelUp();
		if (Banished.DEBUGGING && Key.wasPressed(Key.F7))
		{
			Location pixels = Mouse.getLocation().sub(new Location(Banished.width() / 2, Banished.height() / 2));
			Location tiles = pixels.div(Tile.SIZE);
			this.move(tiles);
		}
		
		if (this != singleton)
		{
			singleton.update(frameTime);
			return;
		}
		this.move(frameTime);
		
		if (Key.wasPressed(Key.S) && this.canAttack())
			this.attack();
		else if (Key.wasPressed(Key.A) && this.canAttack())
			this.aoe();
		else if (Key.wasPressed(Key.D) && this.canAttack())
			this.dash();
		
//		if (Key.wasPressed(Key.Space))
//			this.getWorld().startInteracting();
//		if (Key.wasPressed(Key.Backspace))
//			this.getWorld().abortInteracting();
//		if (Key.wasReleased(Key.Space))
		if (Key.wasPressed(Key.Space))
			this.getWorld().interact();
		
//		System.out.println(getDirection());
		
		if(stamina/MAX_STAMINA > STAMINA_SLOW_GROWTH_FRAC)
			stamina = Algorithms.increment(stamina, (60 - 40*stamina/MAX_STAMINA)*frameTime, 0, MAX_STAMINA);
		else
			stamina = Algorithms.increment(stamina, 10*frameTime, 0, MAX_STAMINA);
		
		this.images.update(frameTime);
		
		if (this.chest != null)
			this.chest.getImages().update(frameTime);
		if (this.legs != null)
			this.legs.getImages().update(frameTime);
		if (this.shoes != null)
			this.shoes.getImages().update(frameTime);
		if (this.gloves != null)
			this.gloves.getImages().update(frameTime);
		
		this.levelUpAnim.update(frameTime);
		
		this.getWorld().getTiles().updateVisibility();
	}
	
	public void incrementExperience(int inc)
	{
		experience += inc;
		while(experience >= Algorithms.experience(level)){
			experience -= Algorithms.experience(level);
			levelUp();
		}
	}
	
	public void levelUp()
	{
		level++;
		health = MAX_HEALTH = Algorithms.increment(MAX_HEALTH, Algorithms.getValInRange(20 + level, 0.8, 1.2), MAX_HEALTH, HEALTH_LIMIT);
		if(level < 10){
			STRENGTH = Algorithms.increment(STRENGTH, Algorithms.getValInRange(level, 0, 1), STRENGTH, STAT_LIMIT);
			DEFENSE = Algorithms.increment(DEFENSE, Algorithms.getValInRange(level, 0, 1), DEFENSE, STAT_LIMIT);
		} else {
			STRENGTH = Algorithms.increment(STRENGTH, Algorithms.getValInRange(5 + level/2, 0, 1), STRENGTH, STAT_LIMIT);
			DEFENSE = Algorithms.increment(DEFENSE, Algorithms.getValInRange(5 + level/2, 0, 1), DEFENSE, STAT_LIMIT);
		}
		
		this.levelUpAnim.reset();
		this.levelUpAnim.start();
	}

	public Image getImage()
	{
		if (this != singleton)
			return singleton.getImage();
		
		return moving ? this.images.get(this.getDirection()) : this.images.get(this.getDirection(), 0);
	}
	public Image getChestImage()
	{
		if (this != singleton)
			return singleton.getChestImage();
		
		if (this.chest == null)
			return null;
		
		return moving ? this.chest.getImages().get(this.getDirection()) : this.chest.getImages().get(this.getDirection(), 0);
	}
	public Image getLegsImage()
	{
		if (this != singleton)
			return singleton.getImage();
		
		if (this.legs == null)
			return null;
		
		return moving ? this.legs.getImages().get(this.getDirection()) : this.legs.getImages().get(this.getDirection(), 0);
	}
	public Image getShoesImage()
	{
		if (this != singleton)
			return singleton.getImage();
		
		if (this.shoes == null)
			return null;
		
		return moving ? this.shoes.getImages().get(this.getDirection()) : this.shoes.getImages().get(this.getDirection(), 0);
	}
	public Image getGlovesImage()
	{
		if (this != singleton)
			return singleton.getImage();
		
		if (this.gloves == null)
			return null;
		
		return moving ? this.gloves.getImages().get(this.getDirection()) : this.gloves.getImages().get(this.getDirection(), 0);
	}
	
	public Armor getChest() { return chest; }
	public Armor getLegs() { return legs; }
	public Armor getShoes() { return shoes; }
	public Armor getGloves() { return gloves; }
	public Weapon getWeapon() { return weapon; }
	
	public void setChest(Armor chest) { this.chest = chest; }
	public void setLegs(Armor legs) { this.legs = legs; }
	public void setShoes(Armor shoes) { this.shoes = shoes; }
	public void setGloves(Armor gloves) { this.gloves = gloves; }
	public void setWeapon(Weapon weapon) { this.weapon = weapon; }
	
	public double getStamina(){ return stamina; }
	public double getMaxStamina(){ return MAX_STAMINA; }
	public int getLevel(){ return level; }
	public int getExperience(){ return experience; }
	public String toString(){ return "PLAYER";}
	
	public boolean showLevelUpAnim() { return this.levelUpAnim.isRunning(); }
	public Image getLevelUpAnimImage() { return this.levelUpAnim.getImage(); }
	
	public String toText()
	{
		return super.toText() + " stamina=" + stamina + " level=" + level + " experience=" + experience;
		// TODO: save worn items as well.
	}
	public static Player fromText(String text)
	{
		return null;
	}
	
	public Inventory getPlayerInv()
	{
		return inv;
	}
	
	public void setPlayerInv(Inventory inv)
	{
		this.inv = inv;
	}
	
	public void restorePlayerHealth()
	{
		health = MAX_HEALTH;
	}
}