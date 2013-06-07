package com.banished;

import java.util.Stack;

import com.banished.core.*;
import com.banished.core.entities.*;
import com.banished.core.items.*;
import com.banished.levels.*;

import ddf.minim.Minim;

public class Game
{	
	public static Stack<LevelState> levels;
	
	World world;
	HUD hud;
	
	public static Minim MinimAudio;
	
	public static void run()
	{
		levels = new Stack<LevelState>();
		Player p = new Player(new Location(0, 0), null, 100);
		HUD hud = new HUD();
		
		fillLevels(hud, p);

		State.EnterState(new MainMenuState());

		Player.get().setPlayerInv(new Inventory());
		Inventories.add(Player.get().getPlayerInv());
		
		Player.get().getPlayerInv().add(new Armor(Items.IronChest));
		Player.get().getPlayerInv().add(new Armor(Items.IronLegs));
		Player.get().getPlayerInv().add(new Armor(Items.IronShoes));
		Player.get().getPlayerInv().add(new Armor(Items.IronGloves));
		Player.get().getPlayerInv().add(new Potion(Items.HealthPotion, 10));
		Player.get().getPlayerInv().add(new Potion(Items.StaminaPotion, 10));
		
		
		MinimAudio = new Minim(Banished.getApplet());
		SoundPlayer.loadSounds();
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
		SoundPlayer.getPlayer(SoundPlayer.Sound.Harp).loop();
	}
	
	public static void exit()
	{
		SoundPlayer.freeSounds();
		MinimAudio.stop();
		
		Banished.exitApp();
	}
}
