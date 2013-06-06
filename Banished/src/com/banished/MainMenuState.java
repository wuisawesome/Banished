package com.banished;

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
	Button playButton, quitButton;
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

	public MainMenuState()
	{
		playButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2 - 60), false, new PlayButtonCallback(this), "Play");
		quitButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2 + 60), false, new QuitButtonCallback(this), "Quit");
	}

	public void Update(double frameTime)
	{
		playButton.update();
		quitButton.update();
		
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
		
		playButton.render();
		quitButton.render();
	}
	
	public void playButtonPressed()
	{
		State.EnterState(Game.getLevels().pop());
	}
	public void quitButtonPressed()
	{
		Banished.exitApp();
	}
}
