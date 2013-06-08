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

public class CreditsState extends State
{
	final int TEXT_LEFT = 10, 
			  TEXT_TOP = 10,
			  LINE_HEIGHT = 25,
			  FONT_SIZE = 24;

	Button backButton;
	String[] creditsText;

	private static class BackButtonCallback implements ButtonCallback
	{
		private CreditsState credits;
		public BackButtonCallback(CreditsState credits) { this.credits = credits; }
		public void onClicked(Button sender) { credits.backButtonPressed(); }
	}

	public CreditsState()
	{
		backButton = new Button(new Coordinate(Banished.width() / 2, Banished.height() - 150), false, new BackButtonCallback(this), "Back to Menu");

		final String creditsFilePath = "gui/credits_text.txt";
		creditsText = Graphics.Applet.loadStrings(creditsFilePath);
		if (creditsText == null)
		{
			System.err.println("The file " + creditsFilePath + " could not be found.");
			creditsText = new String[] { "Whoops! The credits text file could not be found... :(" };
		}
	}

	public void backButtonPressed()
	{
		State.ExitState(); // back to main menu
	}

	public void Update(double frameTime)
	{
		backButton.update();

		if (Key.wasPressed(Key.Escape))
			backButtonPressed();
	}

	public void Draw()
	{
		Graphics.Clear(Color.Black);

		Image background = Image.fromFile("tiles/Floor.png");

		int numTilesX = Banished.width() / background.getWidth() + 1, 
			numTilesY = Banished.height() / background.getHeight() + 1;

		for (int x = 0; x < numTilesX; x++)
			for (int y = 0; y < numTilesY; y++)
				Graphics.DrawImage(background,
					new Location(x * background.getWidth(), y * background.getHeight()));
		
		Graphics.Applet.noStroke();
		Graphics.Applet.fill(255, 255, 100);
		Graphics.Applet.textSize(FONT_SIZE);
		Graphics.Applet.textAlign(PConstants.LEFT, PConstants.TOP);
		for (int i = 0; i < creditsText.length; i++)
			Graphics.Applet.text(creditsText[i], TEXT_LEFT, TEXT_TOP + LINE_HEIGHT * i);
		Graphics.Applet.textSize(14);

		backButton.render();
	}
}
