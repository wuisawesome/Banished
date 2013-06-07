//package com.banished;
//
//import com.banished.core.Location;
//import com.banished.core.State;
//import com.banished.graphics.Graphics;
//import com.banished.input.Key;
//
//import processing.video.Movie;
//
//public class CutsceneState extends State {
//	
//	private Movie cutscene;
//	
//	public CutsceneState(String movie)
//	{
//		cutscene = new Movie(Graphics.Applet, movie);
//		cutscene.play();
//	}
//
//	public void Update(double frameTime) 
//	{
//		if(Key.wasPressed(Key.Escape))
//			State.ExitState();
//	}
//
//	public void Draw() 
//	{
//		Graphics.DrawImage(cutscene, new Location(0, 0));
//	}
//
//}
