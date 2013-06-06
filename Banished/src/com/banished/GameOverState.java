package com.banished;

import processing.core.PConstants;

import com.banished.core.Coordinate;
import com.banished.core.State;
import com.banished.core.entities.Player;
import com.banished.graphics.Graphics;
import com.banished.gui.Button;
import com.banished.gui.Button.ButtonCallback;
import com.banished.levels.LevelState;

public class GameOverState extends State 
{

	Button retryButton, mainMenuButton, quitButton;
	
	private static class RetryButtonCallback implements ButtonCallback
	{
		private GameOverState gameOver;
		public RetryButtonCallback(GameOverState gameOver) { this.gameOver = gameOver; }
		public void onClicked(Button sender) { gameOver.retryButtonPressed(); }
	}
	
	private static class MainMenuButtonCallback implements ButtonCallback
	{
		private GameOverState gameOver;
		public MainMenuButtonCallback(GameOverState gameOver) { this.gameOver = gameOver; }
		public void onClicked(Button sender) { gameOver.mainMenuButtonPressed(); }
	}
	
	private static class QuitButtonCallback implements ButtonCallback
	{
		private GameOverState gameOver;
		public QuitButtonCallback(GameOverState gameOver) { this.gameOver = gameOver; }
		public void onClicked(Button sender) { gameOver.quitButtonPressed(); }
	}
	
	public GameOverState()
	{
		retryButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2 - 100), false, new RetryButtonCallback(this), "Retry");
		mainMenuButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2 + 20), false, new MainMenuButtonCallback(this), "Main Menu");
		quitButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2 + 140), false, new QuitButtonCallback(this), "Quit");
		Player.get().restorePlayerHealth();
	}
	
	public void mainMenuButtonPressed() 
	{
		while(!State.NoStates() && !(State.GetCurrentState() instanceof MainMenuState)){
			if(State.GetCurrentState() instanceof LevelState)
				Game.getLevels().push((LevelState)State.ExitState());
			else State.ExitState();
		}
		if(State.NoStates())
			State.ExitApplication();
	}

	public void quitButtonPressed() 
	{
		State.ExitApplication();
	}
	
	public void retryButtonPressed()
	{
		State.ExitState();
		if(State.GetCurrentState() instanceof LevelState)
			((LevelState)State.GetCurrentState()).Initialize();
	}

	public void Update(double frameTime) 
	{
		retryButton.update();
		mainMenuButton.update();
		quitButton.update();
	}
	
	public void Draw() 
	{
		
		Graphics.Applet.background(0);
		Graphics.Applet.noStroke();
		Graphics.Applet.fill(0, 100);
		Graphics.Applet.rect(0, 0, Banished.width(), Banished.height());
		Graphics.Applet.fill(255, 255, 0);
		Graphics.Applet.textAlign(PConstants.CENTER);
		Graphics.Applet.textSize(60);
		Graphics.Applet.text("GAME OVER", Banished.width() / 2, 80);
		Graphics.Applet.textSize(30);
		Graphics.Applet.fill(255);
		
		retryButton.render();
		mainMenuButton.render();
		quitButton.render();
	}
	
	
}
