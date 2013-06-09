package com.banished.levels;

import java.io.IOException;

import com.banished.Banished;
import com.banished.Game;
import com.banished.PauseMenuState;
import com.banished.core.Book;
import com.banished.core.Direction;
import com.banished.core.HUD;
import com.banished.core.Location;
import com.banished.core.Page;
import com.banished.core.Spawner;
import com.banished.core.State;
import com.banished.core.TileGrid;
import com.banished.core.World;
import com.banished.core.entities.CircleCollisionShape;
import com.banished.core.entities.LivingEntity;
import com.banished.core.entities.Player;
import com.banished.core.entities.nonliving.ChestEntity;
import com.banished.core.items.Inventories;
import com.banished.core.items.Inventory;
//import com.banished.core.items.Inventory;
import com.banished.graphics.Color;
import com.banished.graphics.Graphics;
import com.banished.input.Key;
import com.banished.input.Mouse;

public abstract class LevelState extends State {

	private TileGrid level;
	private int goNext;
	
	protected World world;
	protected HUD hud;
	
	private String levelFile;
	
	public static boolean ShowInventory;
	
	private Location locFromAbove;
	private Direction dirFromAbove;
	
	private boolean pageAdded;
	private Page page;
	
	public LevelState(String file, HUD hud, Player p, Location loc, Direction dir, String pageFile)
	{
		levelFile = file;
		this.hud = hud;
		goNext = 0;
		
		ShowInventory = false;
		
		locFromAbove = loc;
		dirFromAbove = dir;
		
		pageAdded = false;
		page = new Page(pageFile);
	}
	
	public void Initialize()
	{
		try {
			level = TileGrid.fromFile(levelFile);
		} catch (IOException e) { e.printStackTrace(); }
		world = new World(level);
		Player.get().setWorld(world);
		world.addEntity(world.getPlayer());
		LivingEntity.toTrack = world.getPlayer();
		Player.get().setLocation(locFromAbove);
		Player.get().setShape(new CircleCollisionShape(Player.get().getLocation().getX(), Player.get().getLocation().getY() + Player.SHAPE_YOFFSET, Player.RADIUS));
		Player.get().setDirection(dirFromAbove);
		addEntities();
		if(!pageAdded){
			Book.addPage(page);
			pageAdded = true;
		}
		
	}
	
	@SuppressWarnings("unused")
	public void Update(double frameTime)
	{
		world.update(frameTime);
		
		if(goNext == 1 && !Game.levels.isEmpty()){
			goNext = 0;
			LevelState next = Game.levels.pop();
			State.ExitState();
			State.EnterState(next);
		}
		else if(goNext == -1){
			goNext = 0;
			Game.levels.push((LevelState)ExitState());
		}
		if (Key.wasPressed(Key.Escape))
			State.EnterState(new PauseMenuState());
		
		
		if (Banished.DEBUGGING && Key.wasPressed(Key.F12))
			Banished.SHOW_EDGE_DEBUGGING_INFO ^= true;
		if (Banished.DEBUGGING && Key.isDown(Key.F1)){
			double rand = Math.random();
			if(rand < .6)
				world.addEntityToAdd(Spawner.greenSlime(World.toWorldCoords(Mouse.getLocation()), world));
			else if(rand < .9)
				world.addEntityToAdd(Spawner.redSlime(World.toWorldCoords(Mouse.getLocation()), world));
			else
				world.addEntityToAdd(Spawner.blueSlime(World.toWorldCoords(Mouse.getLocation()), world));
		}
		if (Banished.DEBUGGING && Key.wasPressed(Key.F2))
			world.addEntity(new ChestEntity(world, World.toWorldCoords(Mouse.getLocation()), new Inventory()));
	
		if (Key.wasPressed(Key.I))
		{
			ShowInventory ^= true;
			Player.get().getPlayerInv().setShown(ShowInventory);
		}
		
		if (Key.wasPressed(Key.B)){
			Book.toggleBook();
		}
		
		if (Key.wasPressed(Key.E)){
			if(Book.prevPage())
				Book.pageTurn();
		}
		
		if (Key.wasPressed(Key.R)){
			if(Book.nextPage())
				Book.pageTurn();
		}
		
		Inventories.update();
	}
	
	public void Draw()
	{
		Graphics.Clear(Color.Black);
		
		world.render();
		Book.render();
		hud.render();
		Inventories.render();
	}
	
	public void toNext()
	{
		goNext = 1;
	}
	
	public void toPrev()
	{
		goNext = -1;
	}
	
	public abstract void addEntities();
	
}
