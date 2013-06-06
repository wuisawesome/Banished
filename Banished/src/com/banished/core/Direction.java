package com.banished.core;

public enum Direction
{
	North, Northeast, East, Southeast, South, Southwest, West, Northwest;
	
	public static Direction fromBools(boolean left, boolean right, boolean up, boolean down)
	{
		if (left && right)
			left = right = false;
		if (up && down)
			up = down = false;
		
		if (left)
		{
			if (up) return Northwest;
			else if (down) return Southwest;
			else return West;
		}
		else if (right)
		{
			if (up) return Northeast;
			else if (down) return Southeast;
			else return East;
		}
		else if (up)
			return North;
		else if (down)
			return South;
		
		return null;
	}
	public static Direction fromVector(Location v)
	{
		if(v == null)
			return North;
		double angle = Math.atan2(-v.getY(), v.getX());
		if (angle < 0) angle += Math.PI * 2;
		
		angle = Math.toDegrees(angle);
		
		// each direction is 360/8 degrees = 45 degrees
		// so each direction is a multiple of 45 degrees, but it's offset by 45/2 degrees
		// so let's add 45/2 degrees.
		angle += 22.5;
		// now East is 0-45, and for everything else, just add multiples of 45
		if (angle >= 0 && angle < 45) return East;
		else if (angle >= 45 && angle < 90) return Northeast;
		else if (angle >= 90 && angle < 135) return North;
		else if (angle >= 135 && angle < 180) return Northwest;
		else if (angle >= 180 && angle < 225) return West;
		else if (angle >= 225 && angle < 270) return Southwest;
		else if (angle >= 270 && angle < 315) return South;
		else return Southeast;
	}
	public Direction opposite()
	{
		switch (this)
		{
		case North: return South;
		case Northeast: return Southwest;
		case East: return West;
		case Southeast: return Northwest;
		case South: return North;
		case Southwest: return Northeast;
		case West: return East;
		case Northwest: return Southeast;
		default: return null;
		}
	}
	public Location toVector()
	{
		int x = 0, y = 0;
		if (this == East || this == Southeast || this == Northeast)
			x++;
		else if (this == West || this == Southwest || this == Northwest)
			x--;
		if (this == North || this == Northwest || this == Northeast)
			y--;
		else if (this == South || this == Southwest || this == Southeast)
			y++;
		return new Location(x, y).normalize();
	}
}
