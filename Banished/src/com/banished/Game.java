package com.banished;

//import java.io.IOException;
//import java.util.ArrayList;
import java.io.File;
import java.util.Stack;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.banished.core.*;
import com.banished.core.entities.*;
//import com.banished.core.entities.nonliving.*;
import com.banished.core.items.*;
//import com.banished.graphics.*;
//import com.banished.input.*;
import com.banished.levels.*;

public class Game
{	
	public static Stack<LevelState> levels;
	
	World world;
	HUD hud;
	
	public static void run()
	{
		levels = new Stack<LevelState>();
		Player p = new Player(new Location(0, 0), null, 100);
		HUD hud = new HUD();
		
		fillLevels(hud, p);

//		State.EnterState(new CutsceneState("cutscenes/scene0.mov"));
		/* 
		 * Now throws a java.lang.ArrayIndexOutOfBoundsException: Coordinate out of bounds!`
		 */
		State.EnterState(new MainMenuState());

		Player.get().setPlayerInv(new Inventory());
		Inventories.add(Player.get().getPlayerInv());
		
		Player.get().getPlayerInv().add(new Armor(Items.BronzeChest));
		Player.get().getPlayerInv().add(new Armor(Items.BronzeLegs));
		Player.get().getPlayerInv().add(new Armor(Items.BronzeShoes));
		Player.get().getPlayerInv().add(new Armor(Items.BronzeGloves));
		Player.get().getPlayerInv().add(new Armor(Items.IronChest));
		Player.get().getPlayerInv().add(new Armor(Items.IronLegs));
		Player.get().getPlayerInv().add(new Armor(Items.IronShoes));
		Player.get().getPlayerInv().add(new Armor(Items.IronGloves));
		Player.get().getPlayerInv().add(new Potion(Items.HealthPotion, 10));
		Player.get().getPlayerInv().add(new Potion(Items.StaminaPotion, 10));
		
		loopMusic();
	}
	
	private static void fillLevels(HUD hud, Player p){
		levels.push(new LevelFinal(hud, p));
		levels.push(new LevelToFinal(hud, p));
		levels.push(new LevelPenultimate(hud, p));
		levels.push(new LevelFour(hud, p));
		levels.push(new LevelThree(hud, p));
		levels.push(new LevelTwo(hud, p));
		levels.push(new LevelOne(hud, p));
	}
	
	public static Stack<LevelState> getLevels()
	{
		return levels;
	}
	
	private static void loopMusic()
	{
		try{
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("data/music/harp.wav"));
	        Clip clip = AudioSystem.getClip();
	        clip.open(inputStream);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
//	public static class WallTestState extends State
//	{
//		World world;
////		Inventory inv;
//		HUD hud;
//		private boolean showInventory;
//		
//		public WallTestState()
//		{
//			try { world = new World(TileGrid.fromFile("levels/A.blvl")); }
//			catch (IOException e) { e.printStackTrace(); }
//			Player p = new Player(new Location(18.5, 2.5), null, 100);
//			p.setWorld(world);
//			world.addEntity(world.getPlayer());
//			world.addEntity(new ChestEntity(null, new Location(3.5, 4)));
//			ArrayList<Entity> a = new ArrayList<Entity>();
//			a.add(Spawner.greenSlime(new Location(15, 7), world));
//			a.add(Spawner.greenSlime(new Location(13, 8), world));
//			a.add(Spawner.blueSlime(new Location(14, 10), world));
//			a.add(Spawner.bat(new Location(14, 9), world));
//			world.getTiles().getTile(17, 10).setTriggerEntity(world, a);
//			a = new ArrayList<Entity>();
//			a.add(Spawner.shadow(new Location(4, 6), world));
//			world.getTiles().getTile(6,  12).setTriggerEntity(world, a);
//			world.addEntity(Spawner.redSpawner(new Location(10.5, 17), world));
//			
//			//for (int i = 0; i < 1000; i++) world.addEntity(new SlimeEntity(new Location (3,-1*i/1000), world, Color.Red));
//			
////			//For testing
////			SlimeEntity slime = new SlimeEntity(new Location (5,5), world, Color.Red);
////			world.getTiles().getTile(2, 2).setTriggerEntity(world, slime);
//			
//			
//			LivingEntity.toTrack = world.getPlayer();
//			
////			inv = new Inventory();
//			hud = new HUD();
//			showInventory = false;
//			
//			Inventories.add(new Inventory());
//		}
//		
//		public void Update(double frameTime)
//		{
//			super.Update(frameTime);
//			if (Key.wasPressed(Key.Escape))
//				State.EnterState(new PauseMenuState());
//			
//			world.update(frameTime);
//			if (Banished.DEBUGGING && Key.wasPressed(Key.F12))
//				SHOW_EDGE_DEBUGGING_INFO ^= true;
//			if (Banished.DEBUGGING && Key.isDown(Key.F1)){
//				double rand = Math.random();
//				if(rand < .6)
//					world.addEntityToAdd(Spawner.greenSlime(World.toWorldCoords(Mouse.getLocation()), world));
//				else if(rand < .9)
//					world.addEntityToAdd(Spawner.redSlime(World.toWorldCoords(Mouse.getLocation()), world));
//				else
//					world.addEntityToAdd(Spawner.blueSlime(World.toWorldCoords(Mouse.getLocation()), world));
//			}
//			if (Banished.DEBUGGING && Key.wasPressed(Key.F2))
//				world.addEntity(new ChestEntity(world, World.toWorldCoords(Mouse.getLocation())));
//		
//			if (Key.wasPressed(Key.I))
//			{
//				showInventory ^= true;
//				for (IInventory inv : Inventories.getInventories())
//					if (inv instanceof Inventory)
//						((Inventory)inv).setShown(showInventory);
//			}
//			Inventories.update();
//		}
//		public void Draw()
//		{
//			Graphics.Clear(Color.Black);
//			
//			world.render();
//			//if (showInventory)
////				inv.render();
//			hud.render();
//			Inventories.render();
//		}
//	}
}
