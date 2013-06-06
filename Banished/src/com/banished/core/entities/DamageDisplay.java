package com.banished.core.entities;

import com.banished.core.Location;
import com.banished.core.World;
import com.banished.graphics.Color;

public class DamageDisplay {
	
	public static final double DISP_TIME = 50;
	
	private Location location;
	private World world;
	private double counter;
	private int damage;
	private Color color;
	
	public DamageDisplay(int damage, Location location, World world, Color color)
	{
		this.damage = damage;
		this.location = location;
		this.world = world;
		this.color = color;
		counter = 0;
	}
	
	public void update(double frameTime)
	{
		setLocation(new Location(getLocation().getX(), getLocation().getY() - .5*frameTime));
		counter += frameTime;
		color.setA((float)((DISP_TIME - counter)/DISP_TIME));
	}
	
	public Location getLocation() { return location; }
	public void setLocation(Location loc) { location = loc; }
	
	public World getWorld() { return world; }
	public void setWorld(World w) { world = w; }
	
	public double getCount() { return counter; }
	public int getDamage() { return damage; }
	public Color getColor() { return color; }

}
