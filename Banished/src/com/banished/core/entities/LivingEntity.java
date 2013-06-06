package com.banished.core.entities;

import com.banished.Banished;
import com.banished.core.*;
import com.banished.core.entities.enemies.EnemyEntity;
import com.banished.graphics.*;

public abstract class LivingEntity extends Entity {
	protected EntityImageSet images;
	
	protected double speed;

	protected double MAX_HEALTH;
	// in caps because it should be seen as kind of constant
	protected double MAX_DAMAGE, STABILITY, STURDINESS;
	protected double CRIT_RATE, CRIT_MULT;
	protected double STRENGTH, DEFENSE;
	protected double health;
	
	protected double timeSinceAttack;
	private double timeToAttack;
	protected boolean canAttack;
	
	protected double width, height;
	
	// healthBarYLocation + healthBarYOffset = getLocation().getY()
	private double healthBarYOffset;
	
//	private Location force;
	
	public LivingEntity(Location location, World world, double imageWidth, double imageHeight, double shapeYOffset,
			double locYOffset, double radius, double maxHealth, double strength, double defense,
			double speed, double timeToAttack, double healthBarYOffset) {
		super(world, location,
				new CircleCollisionShape(location.getX(), location.getY() + shapeYOffset, radius),
				Direction.South, new Location(imageWidth / 2, locYOffset));
		MAX_HEALTH = maxHealth;
		STRENGTH = strength;
		DEFENSE = defense;
		health = MAX_HEALTH;
		this.speed = speed;
		this.canAttack = false;
		this.timeSinceAttack = 0;
		this.timeToAttack = timeToAttack;
		this.width = imageWidth;
		this.height = imageHeight;
		this.healthBarYOffset = healthBarYOffset;
		
//		force = new Location();
	}
//	public LivingEntity(LivingEntity toCopy)
//	{
//		super(toCopy);
//		this.MAX_HEALTH = toCopy.MAX_HEALTH;
//		// TODO: copy the rest of the fields
//	}
	
	public void takeDamage(double damage) {
		calcDefenseSets();
		double healthRed = Algorithms.increment(damage, -Algorithms.getValInRange(DEFENSE, STURDINESS, 1), 1, 1000);
//		System.out.println("Damage:" + healthRed);
		//TODO: make animation/hitboxes for GUI
		if(this instanceof EnemyEntity)
			world.addDispToAdd(new DamageDisplay((int)healthRed, new Location(Algorithms.randMult(getLocation().getX() - width/4, getLocation().getX() + width/4), 
					Algorithms.randMult(getLocation().getY() - height/4, getLocation().getY())), world, new Color(Color.Green)));
		else if(this instanceof Player)
			world.addDispToAdd(new DamageDisplay((int)healthRed, new Location(Algorithms.randMult(getLocation().getX() - width/4, getLocation().getX() + width/4), 
					Algorithms.randMult(getLocation().getY() - height/2, getLocation().getY() - height/4)), world, new Color(Color.Red)));
		if((health = Algorithms.increment(health, -healthRed, 0, MAX_HEALTH)) < 1)
			onDeath();
	}
	
	protected void resetTimeSinceAttack()
	{
		timeSinceAttack = 0;
		canAttack = false;
	}
	
	protected void attack(LivingEntity target) {
		attack(target, 1);
	}
	protected void attack(LivingEntity target, double mult) {
		resetTimeSinceAttack();
		calcDamageSets();
		if(CRIT_RATE < Math.random())
			CRIT_MULT = 1;
		target.takeDamage(Algorithms.getValInRange(MAX_DAMAGE, STABILITY * CRIT_MULT * mult, CRIT_MULT * mult));
	}
	
	public void update(double frameTime)
	{
		timeSinceAttack += frameTime;
		if (timeSinceAttack >= timeToAttack)
			canAttack = true;
		
		health = Algorithms.increment(health, frameTime, 0, MAX_HEALTH);
	}
	
	protected abstract void calcDefenseSets();
	protected abstract void calcDamageSets();
	
	public double getHealth() { return health; }
	public double getMaxHealth() { return MAX_HEALTH; }
	
	public double getTimeSinceAttack() { return timeSinceAttack; }
	public double getTimeToAttack() { return timeToAttack; }
	public boolean canAttack() { return canAttack; }
	
	public abstract void onDeath();	
	
	public abstract Location move(double frameTime);

	public static Entity toTrack;
	protected static Location lastDir = new Location();
	protected Location move(Location direction)
	{
		if (Banished.SHOW_EDGE_DEBUGGING_INFO) if (this == toTrack) lastDir = direction = new Location(direction);
		// movement + wall * dot(movement, wall)
		CollisionShape translated = this.getShape().translate(direction);
		TileBoundary tileBound = translated.getTileBoundary();
		
		for (Entity entity : this.getWorld().getEntities())
		{
			if (entity == this) continue;
			
			if (translated.collidesWith(entity.getShape()))
			{
				entity.onTouched(this);
				this.onTouched(entity);
				
				Location delta = translated.getCenter().sub(entity.getShape().getCenter());
				if (direction.dot(delta) < 0)
				direction = direction.sub(delta.mult(direction.dot(delta)));
//				force = force.sub(delta.mult(direction.dot(delta)));
				translated = this.getShape().translate(direction);
			}
		}
		
//		Edge closest = null;
		for (int x = tileBound.getMinX(); x <= tileBound.getMaxX(); x++)
		{
			for (int y = tileBound.getMinY(); y <= tileBound.getMaxY(); y++)
			{
				Edge[] edges = this.getWorld().getTiles().getTileEdges(x, y);
				for (Edge edge : edges)
				{
					if (edge != null)
					{
//						if (closest == null)
//							closest = edge;
//						else if (edge.shortestDistanceTo(getLocation()) < closest.shortestDistanceTo(getLocation()))
//							closest = edge;
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
		
//		if (closest != null)
//		{
//			Location wall = closest.getNormal();
//			direction = direction.sub(wall.mult(direction.dot(wall)));
//		}
		
//		this.setLocation(this.getLocation().add(force));
		
		this.setLocation(this.getLocation().add(direction));
		this.setShape(this.getShape().translate(direction));//.translate(force));
		
		return direction;
	}
	// USED ONLY FOR DEBUGGING PURPOSES
	public void renderEdges()
	{
		if (!Banished.SHOW_EDGE_DEBUGGING_INFO) return;
		Location direction = new Location(lastDir);
		CollisionShape translated = this.getShape().translate(direction);
		TileBoundary tileBound = this.getWorld().getVisibleTiles();
		
		Edge closest = null;
		
		for (int x = tileBound.getMinX(); x <= tileBound.getMaxX(); x++)
		{
			for (int y = tileBound.getMinY(); y <= tileBound.getMaxY(); y++)
			{
				Edge[] edges = this.getWorld().getTiles().getTileEdges(x, y);
				for (Edge edge : edges)
				{
					if (edge != null)
					{
						if (closest == null)
							closest = edge;
						else if (edge.shortestDistanceTo(getLocation()) < closest.shortestDistanceTo(getLocation()))
							closest = edge;
						
						Graphics.Applet.strokeWeight(2);
						if (translated.collidesWith(edge))
						{
							Graphics.Applet.stroke(0, 0, 255);
							Location wall = edge.getNormal();
							direction = direction.sub(wall.mult(direction.dot(wall)));
							translated = this.getShape().translate(direction);
						}
						else
							Graphics.Applet.stroke(255, 0, 0);
						Graphics.Applet.line((float)edge.getX1() * Tile.SIZE, (float)edge.getY1() * Tile.SIZE,
								(float)edge.getX2() * Tile.SIZE, (float)edge.getY2() * Tile.SIZE);
					}
				}
			}
		}
		
		if (closest != null)
		{
			Graphics.Applet.strokeWeight(2);
			Graphics.Applet.stroke(255, 100, 0);
			Graphics.Applet.line((float)closest.getX1() * Tile.SIZE, (float)closest.getY1() * Tile.SIZE,
					(float)closest.getX2() * Tile.SIZE, (float)closest.getY2() * Tile.SIZE);
		}
		
		Graphics.Applet.strokeWeight(3);
		Graphics.Applet.stroke(0);
		Graphics.Applet.noFill();
		Graphics.Applet.rect(tileBound.getMinX() * Tile.SIZE, tileBound.getMinY() * Tile.SIZE,
				(tileBound.getMaxX() - tileBound.getMinX() + 1) * Tile.SIZE, (tileBound.getMaxY() - tileBound.getMinY() + 1) * Tile.SIZE);
	}
	
	public String toText()
	{
		return super.toText() + " max-health=" + MAX_HEALTH + " max-damage=" + MAX_DAMAGE + " stability=" + STABILITY + " sturdiness" + STURDINESS
				+ " crit-rate=" + CRIT_RATE + " crit-mult=" + CRIT_MULT + " strength=" + STRENGTH + " defense=" + DEFENSE + " health=" + health
				+ "time-since-attack=" + timeSinceAttack + " can-attack=" + canAttack;
	}
	
	public double getHealthBarYOffset()
	{
		return this.healthBarYOffset;
	}
}
