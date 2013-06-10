package com.banished;

import processing.core.PConstants;

import com.banished.core.Coordinate;
import com.banished.core.Location;
import com.banished.core.State;
import com.banished.graphics.Color;
import com.banished.graphics.Graphics;
import com.banished.graphics.Image;
import com.banished.gui.Button;
import com.banished.gui.Button.ButtonCallback;
import com.banished.input.Key;

public class MainMenuState extends State
{
	Button playButton, helpButton, quitButton, creditsButton;
	private static class PlayButtonCallback implements ButtonCallback
	{
		private MainMenuState mainMenu;
		public PlayButtonCallback(MainMenuState mainMenu) { this.mainMenu = mainMenu; }
		public void onClicked(Button sender) { mainMenu.playButtonPressed(); }
	}
	private static class QuitButtonCallback implements ButtonCallback
	{
		private MainMenuState mainMenu;
		public QuitButtonCallback(MainMenuState mainMenu) { this.mainMenu = mainMenu; }
		public void onClicked(Button sender) { mainMenu.quitButtonPressed(); }
	}
	private static class HelpButtonCallback implements ButtonCallback
	{
		private MainMenuState mainMenu;
		public HelpButtonCallback(MainMenuState mainMenu) { this.mainMenu = mainMenu; }
		public void onClicked(Button sender) { mainMenu.helpButtonPressed(); }
	}
	private static class CreditsButtonCallback implements ButtonCallback
	{
		private MainMenuState mainMenu;
		public CreditsButtonCallback(MainMenuState mainMenu) { this.mainMenu = mainMenu; }
		public void onClicked(Button sender) { mainMenu.creditsButtonPressed(); }
	}

	public MainMenuState()
	{
		playButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2 - 110), false, new PlayButtonCallback(this), "Play");
		helpButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2), false, new HelpButtonCallback(this), "Instructions");
		creditsButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2 + 110), false, new CreditsButtonCallback(this), "Credits");
		quitButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2 + 220), false, new QuitButtonCallback(this), "Quit");
	}

	public void Update(double frameTime)
	{
		playButton.update();
		helpButton.update();
		quitButton.update();
		creditsButton.update();
		
		if (Key.wasPressed(Key.Escape))
			quitButtonPressed();
	}

	public void Draw()
	{		
		Graphics.Clear(Color.Black);
		
		Image background = Image.fromFile("tiles/Floor.png");
		
		int numTilesX = Banished.width() / background.getWidth() + 1,
		    numTilesY = Banished.height() / background.getHeight() + 1;
		
		for (int x = 0; x < numTilesX; x++)
			for (int y = 0; y < numTilesY; y++)
				Graphics.DrawImage(background, new Location(x * background.getWidth(), y * background.getHeight()));
		
		Graphics.Applet.fill(255, 255, 0);
		Graphics.Applet.textAlign(PConstants.CENTER);
		Graphics.Applet.textSize(60);
		Graphics.Applet.text("Banished", Banished.width() / 2, 80);
		Graphics.Applet.textSize(30);
		Graphics.Applet.fill(255);
		
		playButton.render();
		helpButton.render();
		quitButton.render();
		creditsButton.render();
	}
	
	public void playButtonPressed()
	{
		SoundPlayer.getPlayer(SoundPlayer.Sound.Harp).loop();
		State.EnterState(Game.getLevels().pop());
	}
	public void helpButtonPressed()
	{
		State.EnterState(new HelpState());
	}
	public void quitButtonPressed()
	{
		Game.exit();
	}
	public void creditsButtonPressed()
	{
		State.EnterState(new CreditsState());
	}
}
