package com.banished.core;

public class Algorithms 
{
	
	public static double getValInRange(double base, double minMult, double maxMult)
	{
		return base * randMult(minMult, maxMult);
	}
	
	public static double randMult(double min, double max)
	{
		return min + Math.random() * (max - min);
	}
	
	public static double increment(double value, double inc, double min, double max)
	{
		value += inc;
		if(value < min) value = min;
		else if(value > max) value = max;
		return value;
	}
	
	public static int experience(int level)
	{
		return expRecur(1.1, level);
	}
	
	private static int expRecur(double rate, int count)
	{
		if(count == 0)
			return 0;
		return (int)(10 * Math.pow(rate, count)) + expRecur(rate, count - 1);
	}
}
