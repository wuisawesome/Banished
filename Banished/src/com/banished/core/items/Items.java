package com.banished.core.items;

import com.banished.exceptions.InvalidItemCountException;
import com.banished.exceptions.InvalidItemTypeException;
import com.banished.exceptions.InvalidMaxStackSizeException;
import com.banished.graphics.ImageMap;

public class Items
{
	public static ImageMap itemImages;
	public static final int NumItems = 1;
	
	public static Item TestSword;
	
	static
	{
		itemImages = new ImageMap();
		
		try
		{
			TestSword = makeItem(0, 100, "Test Sword", "items/Sword.png");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static Item makeItem(int type, int maxStackSize, String name, String imageName)
			throws InvalidItemTypeException, InvalidItemCountException, InvalidMaxStackSizeException
	{
		Item item = new Item(type, 0, maxStackSize, name, type);
		itemImages.load(imageName, type);
		return item;
	}
}
