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

public class PauseMenuState extends State
{
	private Image background;
	
	Button resumeButton, helpButton, quitButton;
	
	private static class ResumeButtonCallback implements ButtonCallback
	{
		private PauseMenuState pauseMenu;
		public ResumeButtonCallback(PauseMenuState pauseMenu) { this.pauseMenu = pauseMenu; }
		public void onClicked(Button sender) { pauseMenu.resumeButtonPressed(); }
	}
	private static class HelpButtonCallback implements ButtonCallback
	{
		private PauseMenuState pauseMenu;
		public HelpButtonCallback(PauseMenuState pauseMenu) { this.pauseMenu = pauseMenu; }
		public void onClicked(Button sender) { pauseMenu.helpButtonPressed(); }
	}
	private static class QuitButtonCallback implements ButtonCallback
	{
		private PauseMenuState pauseMenu;
		public QuitButtonCallback(PauseMenuState pauseMenu) { this.pauseMenu = pauseMenu; }
		public void onClicked(Button sender) { pauseMenu.quitButtonPressed(); }
	}

	public PauseMenuState()
	{
		background = Image.fromScreen();
		resumeButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2 - 125), false, new ResumeButtonCallback(this), "Resume");
		helpButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2), false, new HelpButtonCallback(this), "Instructions");
		quitButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2 + 125), false, new QuitButtonCallback(this), "Quit");
	}

	public void Update(double frameTime)
	{
		resumeButton.update();
		helpButton.update();
		quitButton.update();
		
		if (Key.wasPressed(Key.Escape))
			resumeButtonPressed();
	}

	public void Draw()
	{
		Graphics.DrawImage(background, new Color(100), new Location());
		Graphics.Applet.noStroke();
		Graphics.Applet.fill(0, 100);
		Graphics.Applet.rect(0, 0, Banished.width(), Banished.height());
		
		resumeButton.render();
		helpButton.render();
		quitButton.render();
	}
	
	public void resumeButtonPressed()
	{
		State.ExitState();
	}
	public void helpButtonPressed()
	{
		State.EnterState(new HelpState());
	}
	public void quitButtonPressed()
	{
		Game.exit();
	}
}
