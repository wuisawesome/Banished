package com.banished.core.entities;

import com.banished.core.Direction;
import com.banished.graphics.Image;

public class EntityImageSet
{
	public static final int IMAGES_PER_SIDE = 3;
	private static final double DEFAULT_IMAGE_SWITCH_INTERVAL = .15;
	
	private Image[] left, right, up, down;
	
	private double imageSwitchInterval;
	private double elapsedTime;
	
	private int currentImageIndex;
	
	public EntityImageSet(Image[] left, Image[] right, Image[] up, Image[] down)
	{
		this(left, right, up, down, DEFAULT_IMAGE_SWITCH_INTERVAL);
	}
	public EntityImageSet(Image[] left, boolean mirrorSides, Image[] up, Image[] down)
	{
		this(left, mirrorSides, up, down, DEFAULT_IMAGE_SWITCH_INTERVAL);
	}
	public EntityImageSet(Image[] left, Image[] up, Image[] down)
	{
		this(left, up, down, DEFAULT_IMAGE_SWITCH_INTERVAL);
	}
	public EntityImageSet(Image[] allSides)
	{
		this(allSides, DEFAULT_IMAGE_SWITCH_INTERVAL);
	}
	public EntityImageSet(Image[] left, Image[] right, Image[] up, Image[] down, double imageSwitchInterval)
	{
		if (left.length != IMAGES_PER_SIDE || right.length != IMAGES_PER_SIDE
		    || up.length != IMAGES_PER_SIDE || down.length != IMAGES_PER_SIDE)
			throw new IllegalArgumentException();
		
		this.imageSwitchInterval = imageSwitchInterval;
		this.elapsedTime = 0;
		
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
		
		this.currentImageIndex = 0;
	}
	public EntityImageSet(Image[] left, boolean mirrorSides, Image[] up, Image[] down, double imageSwitchInterval)
	{
		this(left, new Image[IMAGES_PER_SIDE], up, down, imageSwitchInterval);
		
		this.right = new Image[IMAGES_PER_SIDE];
		for (int i = 0; i < IMAGES_PER_SIDE; i++)
			this.right[i] = mirrorSides ? this.left[i].flipHorizontal() : this.left[i];
	}
	public EntityImageSet(Image[] left, Image[] up, Image[] down, double imageSwitchInterval)
	{
		this(left, true, up, down, imageSwitchInterval);
	}
	public EntityImageSet(Image[] allSides, double imageSwitchInterval)
	{
		this(allSides, allSides, allSides, allSides, imageSwitchInterval);
	}
	
	public void update(double frameTime)
	{
		this.elapsedTime += frameTime;
		while (this.elapsedTime >= this.imageSwitchInterval)
		{
			this.elapsedTime -= this.imageSwitchInterval;
			
			this.currentImageIndex = (this.currentImageIndex + 1) % (IMAGES_PER_SIDE + 1);
		}
	}
	
	public Image get(Direction direction)
	{
		int imageIndex = -1;
		switch (this.currentImageIndex)
		{
			case 0: case 2:
				imageIndex = 0; break;
			case 1: imageIndex = 1; break;
			case 3: imageIndex = 2; break;
		}
		if (imageIndex < 0)
			return null;
		
		return get(direction, imageIndex);
	}
	public Image get(Direction direction, int frame)
	{
		switch (direction)
		{
			case Northeast: case East: case Southeast:
				return this.getRight(frame);
			case Northwest: case West: case Southwest:
				return this.getLeft(frame);
			case North: return this.getUp(frame);
			case South: return this.getDown(frame);
		}
		
		return null;
	}
	
	public Image getLeft(int frame)
	{
		return left[frame];
	}
	public Image getRight(int frame)
	{
		return right[frame];
	}
	public Image getUp(int frame)
	{
		return up[frame];
	}
	public Image getDown(int frame)
	{
		return down[frame];
	}
}
