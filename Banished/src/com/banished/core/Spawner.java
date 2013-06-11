package com.banished.core;

import com.banished.core.entities.enemies.*;
import com.banished.graphics.Color;

public class Spawner {
	
	public static SlimeEntity greenSlime(Location loc, World world)
	{
		return new SlimeEntity(loc, world, Color.Green, 1);
	}
	
	public static SlimeEntity redSlime(Location loc, World world)
	{
		return new SlimeEntity(loc, world, Color.Red, 2);
	}
	
	public static SlimeEntity blueSlime(Location loc, World world)
	{
		return new SlimeEntity(loc, world, Color.Cyan, 3);
	}
	
	public static SlimeEntity violetSlime(Location loc, World world)
	{
		return new SlimeEntity(loc, world, Color.Violet, 7);
	}
	
	public static BatEntity bat(Location loc, World world)
	{
		return new BatEntity(loc, world);
	}
	
	public static ShadowEntity shadow(Location loc, World world)
	{
		return new ShadowEntity(loc, world);
	}
	
	public static SpawnerEntity greenSpawner(Location loc, World world)
	{
		return new SpawnerEntity(loc, world, Color.Green, 1);
	}
	
	public static SpawnerEntity redSpawner(Location loc, World world)
	{
		return new SpawnerEntity(loc, world, Color.Red, 2);
	}
	
	public static SpawnerEntity blueSpawner(Location loc, World world)
	{
		return new SpawnerEntity(loc, world, Color.Blue, 3);
	}
	
	public static SpawnerEntity violetSpawner(Location loc, World world)
	{
		return new SpawnerEntity(loc, world, Color.Violet, 7);
	}
	
	public static SpawnerEntity whiteSpawner(Location loc, World world)
	{
		return new SpawnerEntity(loc, world, Color.White, 10);
	}
	
	public static CursedManEntity cursedMan(Location loc, World world)
	{
		return new CursedManEntity(loc, world);
	}
	
	public static ShadowEyeEntity shadowEye(Location loc, World world)
	{
		return new ShadowEyeEntity(loc, world);
	}
	
	public static BanishedEntity finalBoss(Location loc, World world)
	{
		return new BanishedEntity(loc, world);
	}

}
