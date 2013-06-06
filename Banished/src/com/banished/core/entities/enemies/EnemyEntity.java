package com.banished.core.entities.enemies;

import com.banished.core.Direction;
import com.banished.core.Location;
import com.banished.core.World;
import com.banished.core.entities.LivingEntity;
import com.banished.core.entities.Player;

public abstract class EnemyEntity extends LivingEntity {

	protected final double RANGE;
	protected final int EXP;
	private double damageCounter;
	private final double SIGHT_RANGE;
	
	private final double FLASH_DURATION = 1.2,
						 KNOCKBACK_DURATION = 1.07;
	
	private double knockback;
	
	protected Location wanderDir;
	
	public EnemyEntity(Location location, World world, double imageWidth, double imageHeight, double shapeYOffset,
			double locYOffset, double radius, double maxHealth, double strength, double defense,
			double speed, double range, double timeToAttack, double sight, int exp, double kb, double healthBarYOffset) {
		super(location, world, imageWidth, imageHeight, shapeYOffset, locYOffset, radius, maxHealth,
				strength, defense, speed, timeToAttack, healthBarYOffset);
		if (range <= 0) {
			RANGE = 2;
		}
		else{
			RANGE = range;
		}
		
		EXP = exp;
		damageCounter = 0;
		knockback = kb;
		SIGHT_RANGE= sight;
		
		double wanderAngle = Math.random() * 2 * Math.PI;
		this.wanderDir = new Location(Math.cos(wanderAngle), Math.sin(wanderAngle));
	}
//	public EnemyEntity(EnemyEntity toCopy)
//	{
//		super(toCopy);
//		this.RANGE = toCopy.RANGE;
//	}
	
	public void takeDamage(double damage)
	{
		super.takeDamage(damage);
		damageCounter = 1;
		flashImage();
	}
	
	public void update(double frameTime)
	{
		super.update(frameTime);
		if(damageCounter > 0 && damageCounter < FLASH_DURATION){
			damageCounter += frameTime;
			
			if(damageCounter < KNOCKBACK_DURATION)
				super.move(Direction.fromVector(getTargetDirection()).opposite().toVector().mult(knockback*frameTime));
		}
		else if(damageCounter >= FLASH_DURATION){
			damageCounter = 0;
			revertImage();
		}
		
		if (this.getLocation().distanceTo(Player.get().getLocation()) > SIGHT_RANGE)
		{
			//wander
			final double WANDER_ANGLE_CHANGE = Math.PI / 2;
			
			double angle = Math.atan2(wanderDir.getY(), wanderDir.getX());
			angle += (Math.random() - 0.5) * 2 * WANDER_ANGLE_CHANGE * frameTime;
			
			wanderDir = new Location(Math.cos(angle), Math.sin(angle));
			this.move(frameTime, wanderDir);
		}
		else
		{
			this.move(frameTime);
			if (this.canAttack())
				this.attackPlayer();
		}
	}
	
	public abstract void flashImage();
	
	public abstract void revertImage();

	public abstract void attackPlayer();
	
	protected abstract Location getTargetDirection();

	public Location move(double frameTime)
	{
		return this.move(frameTime, getTargetDirection());
	}
	public Location move(double frameTime, Location dir)
	{
		Location moveDir = dir;
		if (moveDir == null) return new Location();
		moveDir = moveDir.normalize();
		if (moveDir == null) return new Location(); // we're right on the target!
		
		return super.move(moveDir.mult(this.speed * frameTime));
	}
	
	public String toText()
	{
		/*
		 protected final double RANGE;
	protected final int EXP;
	private double damageCounter;
	private final double SIGHT_RANGE;
	
	private final double	FLASH_DURATION = 1.2,
							KNOCKBACK_DURATION = 1.07;
	
	private double knockback;
	
	protected Location wanderDir;
		 */
		return super.toText() + " range=" + RANGE + " exp=" + EXP + " damage-counter=" + damageCounter + " sight-range="
				+ SIGHT_RANGE + " knockback=" + knockback + " wander-dir=" + wanderDir;
	}
}

