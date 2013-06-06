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
	
	Button resumeButton, quitButton;
	
	private static class ResumeButtonCallback implements ButtonCallback
	{
		private PauseMenuState pauseMenu;
		public ResumeButtonCallback(PauseMenuState pauseMenu) { this.pauseMenu = pauseMenu; }
		public void onClicked(Button sender) { pauseMenu.resumeButtonPressed(); }
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
		resumeButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2 - 75), false, new ResumeButtonCallback(this), "Resume");
		quitButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() / 2 + 25), false, new QuitButtonCallback(this), "Quit");
	}

	public void Update(double frameTime)
	{
		resumeButton.update();
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
		quitButton.render();
	}
	
	public void resumeButtonPressed()
	{
		State.ExitState();
	}
	public void quitButtonPressed()
	{
		Banished.exitApp();
	}
}
