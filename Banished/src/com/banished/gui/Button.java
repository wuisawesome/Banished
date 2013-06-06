package com.banished.gui;

import processing.core.PConstants;

import com.banished.core.Coordinate;
import com.banished.graphics.Graphics;
import com.banished.graphics.Image;
import com.banished.input.Mouse;
import com.banished.input.Mouse.MouseButton;

public class Button
{
	private static Image buttonImage, highlightImage;
	
	private Coordinate location, size;
	private boolean mouseOver;
	private boolean beingClicked;
	
	ButtonCallback callback;
	
	private String text;
	
	public Button(Coordinate topLeft, ButtonCallback callback, String text)
	{
		this.location = topLeft;
		this.mouseOver = this.beingClicked = false;
		this.callback = callback;
		
		if (buttonImage == null || highlightImage == null)
		{
			buttonImage = new Image("gui/button.png");
			highlightImage = new Image("gui/button highlight.png");
		}
		this.size = new Coordinate(buttonImage.getWidth(), buttonImage.getHeight());
		
		this.text = text;
	}
	public Button(Coordinate location, boolean locIsTopLeft, ButtonCallback callback, String text)
	{
		this(location, callback, text);
		if (!locIsTopLeft)
			this.location = location.toLocation().sub(size.toLocation().div(2)).toCoordinate();
	}
	
	public void render()
	{
		Graphics.DrawImage(buttonImage, location.toLocation());
		if (mouseOver)
			Graphics.DrawImage(highlightImage, location.toLocation());
		Graphics.Applet.fill(255);
		Graphics.Applet.textAlign(PConstants.CENTER);
		Graphics.Applet.text(text, location.getX() + size.getX() / 2, location.getY() + size.getY() / 2);
	}
	public void update()
	{
		if (Mouse.getX() >= location.getX() && Mouse.getY() >= location.getY() && Mouse.getX() < location.getX() + size.getX() && Mouse.getY() < location.getY() + size.getY())
		{
			mouseOver = true;
			if (Mouse.wasPressed(MouseButton.Left))
				beingClicked = true;
			if (beingClicked && Mouse.wasReleased(MouseButton.Left))
			{
				beingClicked = false;
				if (this.callback != null)
					this.callback.onClicked(this);
			}
		}
		else
		{
			mouseOver = false;
			if (Mouse.wasReleased(MouseButton.Left))
				beingClicked = false;
		}
	}
	
	public static interface ButtonCallback
	{
		public void onClicked(Button sender);
	}
}