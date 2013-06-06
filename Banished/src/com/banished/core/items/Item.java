package com.banished.core.items;

import processing.core.PApplet;

import com.banished.core.Location;
import com.banished.graphics.Graphics;
import com.banished.graphics.Image;

public class Item
{
	public static final int IMAGE_WIDTH = 54,
							IMAGE_HEIGHT = 54; // or whatever it is
	
	private int type, count;
	private final int maxStackSize;
	private final String name;
	private Object imageId;
	
	private boolean consumable;
	
	public Item(Item copy, int count)
	{
		this(copy.type, count, copy.maxStackSize, copy.name, copy.imageId, copy.consumable);
	}
	
	public Item(int type, int count, int maxStackSize, String name, Object imageId, boolean consumable)
	{
		if (maxStackSize < 0)
			maxStackSize = 1;
		if (count < 0)
			count = 0;
		else if (count > maxStackSize)
			count = maxStackSize;
		
		this.type = type;
		this.count = count;
		this.maxStackSize = maxStackSize;
		this.name = name;
		this.imageId = imageId;
		
		this.consumable = consumable;
	}
	
	public int getType() { return this.type; }
	public int getCount() { return this.count; }
	public int getMaxStackSize() { return this.maxStackSize; }
	public Object getImageId() { return this.imageId; }
	public String getName() { return this.name; }
	
	public void setCount(int count)
	{
		if (count < 0)
			count = 0;
		else if (count > maxStackSize)
			count = maxStackSize;
		this.count = count;
	}
	
	public Image getImage()
	{
		return Items.itemImages.get(this.getImageId());
	}
	
	public void renderAt(double x, double y, double pxTextOffsetX, double pxTextOffsetY)
	{
		Image image = getImage();
		Graphics.DrawImage(image,  new Location(x, y));
		if (this.count != 1)
		{
			Graphics.Applet.pushMatrix();
			{
				Graphics.Applet.textAlign(PApplet.RIGHT);
				Graphics.Applet.fill(255);
				Graphics.Applet.text(Integer.toString(count), 
						(float)(x + pxTextOffsetX),
						(float)(y + pxTextOffsetY));
			}
			Graphics.Applet.popMatrix();
		}
	}
	
	public boolean isConsumable() { return consumable; }
	public void use() { }
	
	public String toText()
	{
		// TODO: implement();
		return null;
	}
	public static Item fromText(String text)
	{
		// TODO: implement();
		return null;
	}
}
