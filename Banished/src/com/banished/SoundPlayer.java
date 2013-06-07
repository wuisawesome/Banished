package com.banished;

import java.util.HashMap;
import java.util.Map;

import ddf.minim.AudioPlayer;

public class SoundPlayer
{
	public static enum Sound { ShadowFace_Battle, Harp, FinalBoss, Sword, ChestOpen, ChestClose, ShadowBreath, BatSound, FlipPages  }
	
	private static Map<Sound, AudioPlayer> sounds;
	
	public static void loadSounds()
	{
		sounds = new HashMap<Sound, AudioPlayer>();
		
		sounds.put(Sound.ShadowFace_Battle, Game.MinimAudio.loadFile("music/shadowface_battle.mp3"));
		sounds.put(Sound.Harp, Game.MinimAudio.loadFile("music/harp.mp3"));
		sounds.put(Sound.FinalBoss, Game.MinimAudio.loadFile("music/finalboss.mp3"));
		sounds.put(Sound.Sword, Game.MinimAudio.loadFile("entities/player/swords/swordSound.wav"));
		getPlayer(Sound.Sword).gain().setValue(-15);
		sounds.put(Sound.ChestOpen, Game.MinimAudio.loadFile("entities/object_entities/chest/chestopen.wav"));
		getPlayer(Sound.ChestOpen).gain().setValue(-10);
		sounds.put(Sound.ChestClose, Game.MinimAudio.loadFile("entities/object_entities/chest/chestclose.wav"));
		getPlayer(Sound.ChestClose).gain().setValue(-10);
		sounds.put(Sound.ShadowBreath, Game.MinimAudio.loadFile("entities/living_entities/shadow/shadowbreath.wav"));
		sounds.put(Sound.BatSound, Game.MinimAudio.loadFile("entities/living_entities/bat/batsound.wav"));
		getPlayer(Sound.BatSound).gain().setValue(-5);
		sounds.put(Sound.FlipPages, Game.MinimAudio.loadFile("book/flipPages.wav"));
	}
	
	public static AudioPlayer getPlayer(Sound sound)
	{
		return sounds.get(sound);
	}
	
	public static void freeSounds()
	{
		for (AudioPlayer player : sounds.values())
			player.close();
		
		sounds = null;
	}
}
