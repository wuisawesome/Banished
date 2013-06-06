package com.banished.graphics;

import processing.core.*;

public class Image
{
	private PImage image;
	
	public Image(String fileName)
	{
		try { this.image = Graphics.Applet.loadImage(fileName); }
		catch (Exception e)
		{
			System.err.println("Could not find image '" + fileName + "'.");
			this.image = null;
		}
	}
	private Image(PImage image)
	{
		this.image = image;
	}
	public static Image fromFile(String fileName)
	{
		return new Image(fileName);
	}
	
	public static Image fromScreen()
	{
		return new Image(Graphics.Applet.get());
	}
	
	public PImage toPImage()
	{
		return this.image;
	}
	public int getWidth()
	{
		return this.image.width;
	}
	public int getHeight()
	{
		return this.image.height;
	}
	
	public Image flipHorizontal()
	{
		PGraphics buffer = Graphics.Applet.createGraphics(getWidth(), getHeight(), PApplet.JAVA2D);
		
		buffer.beginDraw();
		{
			buffer.background(0, 0);
			buffer.pushMatrix();
			{
				buffer.scale(-1, 1); // flip horizontally
				buffer.image(this.image, getWidth(), 0);
			}
			buffer.popMatrix();
		}
		buffer.endDraw();
		
		return new Image(buffer);
	}
	public Image tint(Color color)
	{
		PGraphics buffer = Graphics.Applet.createGraphics(getWidth(), getHeight(), PConstants.JAVA2D);
		
		buffer.beginDraw();
		{
			buffer.background(0, 0);
			buffer.tint(color.getR() * Color.COLOR_MAX_VALUE, color.getG() * Color.COLOR_MAX_VALUE,
					color.getB() * Color.COLOR_MAX_VALUE, color.getA() * Color.COLOR_MAX_VALUE);
			buffer.image(this.image, 0, 0);
		}
		buffer.endDraw();
		
		return new Image(buffer);
	}
}
