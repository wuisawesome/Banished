package com.banished.graphics;

import com.banished.core.Location;

import processing.core.*;

public class Graphics
{
	public static PApplet Applet;
	
	public static void Initialize(PApplet applet)
	{
		Applet = applet;
	}
	
	public static void Clear(Color color)
	{
		Applet.background(color.getR() * Color.COLOR_MAX_VALUE,
				color.getG() * Color.COLOR_MAX_VALUE,
				color.getB() * Color.COLOR_MAX_VALUE,
				color.getA() * Color.COLOR_MAX_VALUE);
	}
	
	public static PImage LoadImage(String fileName)
	{
		return Applet.loadImage(fileName);
	}
	
	public static void DrawImage(Image image, Location location)
	{
		DrawImage(image, Color.White, location);
	}
	public static void DrawImage(Image image, Location location, float rotation)
	{
		DrawImage(image, Color.White, location, rotation);
	}
	public static void DrawImage(Image image, Location location, float rotation, float scale)
	{
		DrawImage(image, Color.White, location, rotation, scale);
	}
	public static void DrawImage(Image image, Color tint, Location location)
	{
		if (image == null || image.toPImage() == null || location == null || tint == null) return;
		Applet.tint(tint.getR() * Color.COLOR_MAX_VALUE,
				tint.getG() * Color.COLOR_MAX_VALUE,
				tint.getB() * Color.COLOR_MAX_VALUE,
				tint.getA() * Color.COLOR_MAX_VALUE);
		Applet.image(image.toPImage(), (float)location.getX(), (float)location.getY());
	}
	public static void DrawImage(Image image, Color tint, Location location, float rotation)
	{
		if (image == null || image.toPImage() == null || location == null || tint == null) return;
		DrawImage(image, tint, location, rotation, 1f);
	}
	public static void DrawImage(Image image, Color tint, Location location, float rotation, float scale)
	{
		if (image == null || image.toPImage() == null || location == null || tint == null) return;
		// rotation and scale are around the middle of the image
		// rotation is in degrees. + is ccw, - is cw.
		Applet.pushMatrix();
		{
			Applet.translate((float)location.getX() + image.getWidth() / 2f,
					(float)location.getY() + image.getHeight() / 2f);
			Applet.rotate((float)Math.toRadians(rotation));
			Applet.scale(scale);
			Applet.tint(tint.getR() * Color.COLOR_MAX_VALUE,
					tint.getG() * Color.COLOR_MAX_VALUE,
					tint.getB() * Color.COLOR_MAX_VALUE,
					tint.getA() * Color.COLOR_MAX_VALUE);
			Applet.image(image.toPImage(), -image.getWidth() / 2f, -image.getHeight() / 2f);
		}
		Applet.popMatrix();
	}
	
	
}
