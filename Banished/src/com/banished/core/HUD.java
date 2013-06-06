package com.banished.core;

import processing.core.PConstants;

import com.banished.Banished;
import com.banished.core.entities.Player;
import com.banished.graphics.Graphics;

public class HUD {
	
	private static final float	BAR_HEIGHT = 20,
								BAR_SPACING = 5,
								LABEL_DIST = 10,
								EXP_HEIGHT = 5,
								DIST_FROM_SIDE = 10,
								BAR_WIDTH = Banished.width() - 5*Tile.SIZE - DIST_FROM_SIDE;
	
	public HUD(){}
	
	public void render()
	{
		// boundary rectangle
		Graphics.Applet.strokeWeight(1);
		Graphics.Applet.stroke(0, 0, 0);
		Graphics.Applet.fill(77, 77, 77);
		Graphics.Applet.rect(DIST_FROM_SIDE, Graphics.Applet.getHeight() - (2*BAR_HEIGHT + 2*BAR_SPACING + EXP_HEIGHT), BAR_WIDTH, 20);
		Graphics.Applet.rect(DIST_FROM_SIDE, Graphics.Applet.getHeight() - (BAR_HEIGHT + BAR_SPACING + EXP_HEIGHT), BAR_WIDTH, 20);
		// health
		Graphics.Applet.fill(255, 0, 0);
		Graphics.Applet.rect(DIST_FROM_SIDE, Graphics.Applet.getHeight() - (2*BAR_HEIGHT + 2*BAR_SPACING + EXP_HEIGHT), (float)(BAR_WIDTH * Player.get().getHealth()/Player.get().getMaxHealth()), BAR_HEIGHT);
		// stamina
		Graphics.Applet.fill(255, 255, 0);
		Graphics.Applet.rect(DIST_FROM_SIDE, Graphics.Applet.getHeight() - (BAR_HEIGHT + BAR_SPACING + EXP_HEIGHT), (float)(BAR_WIDTH * Player.get().getStamina()/Player.get().getMaxStamina()), BAR_HEIGHT);
		// stamina red-zone
		if (Player.get().getStamina() / Player.get().getMaxStamina() >= Player.STAMINA_SLOW_GROWTH_FRAC)
		{
			Graphics.Applet.fill(255, 150, 0);
			Graphics.Applet.rect(DIST_FROM_SIDE, Graphics.Applet.getHeight() - (BAR_HEIGHT + BAR_SPACING + EXP_HEIGHT), (float)(BAR_WIDTH * Player.STAMINA_SLOW_GROWTH_FRAC), BAR_HEIGHT);
		}
		else
		{
			Graphics.Applet.fill(255, 150, 0);
			Graphics.Applet.rect(DIST_FROM_SIDE, Graphics.Applet.getHeight() - (BAR_HEIGHT + BAR_SPACING + EXP_HEIGHT), (float)(BAR_WIDTH * Player.get().getStamina()/Player.get().getMaxStamina()), BAR_HEIGHT);
		}
		// experience
		Graphics.Applet.fill(0, 255, 100);
		Graphics.Applet.stroke(0, 0);
		Graphics.Applet.rect(0, Graphics.Applet.getHeight() - EXP_HEIGHT, Graphics.Applet.getWidth() * Player.get().getExperience()/Algorithms.experience(Player.get().getLevel()), EXP_HEIGHT);
		// text
		Graphics.Applet.fill(0, 0, 0);
		Graphics.Applet.textAlign(PConstants.RIGHT);
		Graphics.Applet.textSize(18);
		Graphics.Applet.text((int)Player.get().getHealth() + "/" + (int)Player.get().getMaxHealth(), BAR_WIDTH, Graphics.Applet.getHeight() - (EXP_HEIGHT + 2*BAR_SPACING + BAR_HEIGHT + 3));
		Graphics.Applet.text((int)Player.get().getStamina() + "/" + (int)Player.get().getMaxStamina(), BAR_WIDTH, Graphics.Applet.getHeight() - (EXP_HEIGHT + BAR_SPACING + 3));
		Graphics.Applet.textAlign(PConstants.LEFT);
		Graphics.Applet.text("HEALTH", LABEL_DIST + DIST_FROM_SIDE, Graphics.Applet.getHeight() - (EXP_HEIGHT + 2*BAR_SPACING + BAR_HEIGHT + 3));
		Graphics.Applet.text("STAMINA", LABEL_DIST + DIST_FROM_SIDE, Graphics.Applet.getHeight() - (EXP_HEIGHT + BAR_SPACING + 3));
		Graphics.Applet.textSize(24);
		Graphics.Applet.fill(255, 255, 255);
		Graphics.Applet.text("Level: " + Player.get().getLevel(), 5, 25);
		Graphics.Applet.cursor(Graphics.Applet.loadImage("gui/cursor.png"), 1, 0);
		Graphics.Applet.text("Location" + Player.get().getLocation(), 10, Banished.height() - 65);
		String fpsString = Double.toString(Banished.getFrameRate());
		if (fpsString.contains("."))
			fpsString = fpsString.substring(0, Math.min(fpsString.length(), fpsString.indexOf('.') + 2));
		Graphics.Applet.textSize(12);
		Graphics.Applet.text("FPS: " + fpsString, Banished.width() - 80, 20);
	}

}
