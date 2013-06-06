package com.banished.core.items;

import java.util.*;

import processing.core.PConstants;

import com.banished.Banished;
import com.banished.core.Location;
import com.banished.graphics.Graphics;
import com.banished.input.Mouse;
import com.banished.input.Mouse.MouseButton;

public class Inventories
{
	static Inventories instance;
	static { instance = new Inventories(); }
	
	List<IInventory> invs;
	boolean isMovingItem;
	Item movee;
	
	private Inventories()
	{
		invs = new ArrayList<IInventory>();
		isMovingItem = false;
		movee = null;
	}
	
	public static List<IInventory> getInventories()
	{
		return instance.invs;
	}
	
	public static void add(IInventory inv)
	{
		instance.invs.add(inv);
	}
	public static void remove(IInventory inv)
	{
		instance.invs.remove(inv);
	}
	
	public static void update()
	{
		for (IInventory inv : instance.invs)
			update(inv);
	}
	
	private static void update(IInventory inv)
	{
		if (!inv.isShown()) return;
		
		MouseButton pressed = getPressedMouseButton();
		if (pressed == null) return;
		
		//System.out.println("pressed = " + pressed);
		
		if (!instance.isMovingItem)
		{
			// check if the user is clicking on any item slot in the inventory.
			// if so, pick up the item.
			List<Object> markers = inv.getItemMarkers();
			System.out.println("will pick up: checking markers... " + markers.size());
			//System.out.println("markers size = " + markers.size());
			boolean clickedOnSlot = false;
			for (Object marker : markers)
			{
				//System.out.println("  " + marker);
				Location itemLoc = inv.getLocationFromMarker(marker);
				if (clickingOnItem(itemLoc))
				{
					System.out.println("  picking up item, marker = " + marker);
					instance.movee = inv.pickup(marker, pressed);
					instance.isMovingItem = instance.movee != null;
					clickedOnSlot = true;
					break;
				}
			}
			if (!clickedOnSlot)
				System.out.println("  ... did not find any slot");
		}
		else
		{
			// check if the user is clicking on any item slot in any inventory.
			// if so, ask that inventory to accept the item.
			for (IInventory otherInv : instance.invs)
			{
				if (!otherInv.isShown()) continue;
				
				List<Object> markers = otherInv.getItemMarkers();
				boolean clickedOnSlot = false;
				System.out.println("will drop: checking markers... " + markers.size());
				for (Object marker : markers)
				{
					Location itemLoc = otherInv.getLocationFromMarker(marker);
					if (clickingOnItem(itemLoc))
					{
						System.out.println("  accepting item, marker = " + marker);
						instance.movee = otherInv.accept(instance.movee, marker);
						instance.isMovingItem = instance.movee != null;
						clickedOnSlot = true;
						break;
					}
				}
				if (!clickedOnSlot)
					System.out.println("  ... did not find any slot");
			}
		}
	}

	public static void render()
	{
		for (IInventory inv : instance.invs)
		{
			if (inv.isShown())
			{
				inv.render();
				if (Banished.DEBUGGING)
				{
					List<Object> markers = inv.getItemMarkers();
					for (Object marker : markers)
					{
						Location loc = inv.getLocationFromMarker(marker);
						Graphics.Applet.pushMatrix();
						Graphics.Applet.stroke(0, 0, 255);
						Graphics.Applet.strokeWeight(2);
						Graphics.Applet.fill(0, 0, 255, 50);
						Graphics.Applet.rectMode(PConstants.CORNER);
						Graphics.Applet.rect((float)loc.getX(), (float)loc.getY(), Item.IMAGE_WIDTH, Item.IMAGE_HEIGHT);
						Graphics.Applet.textMode(PConstants.CORNER);
						Graphics.Applet.fill(255);
						Graphics.Applet.text(marker.toString(), (float)loc.getX(), (float)loc.getY());
						Graphics.Applet.popMatrix();
					}
				}
			}
		}
		
		if (instance.isMovingItem)
		{
			instance.movee.renderAt(
				Mouse.getX(), Mouse.getY(), 
				Item.IMAGE_WIDTH * 7 / 8, Item.IMAGE_HEIGHT * 7 / 8);
			Graphics.DrawImage(instance.movee.getImage(), Mouse.getLocation());
	
		}
	}
		
	static MouseButton getPressedMouseButton()
	{
		// check left, right, middle in that order
		if (Mouse.wasPressed(MouseButton.Left)) return MouseButton.Left;
		else if (Mouse.wasPressed(MouseButton.Right)) return MouseButton.Right;
		else if (Mouse.wasPressed(MouseButton.Middle)) return MouseButton.Middle;
		else return null;
	}
	
	static boolean clickingOnItem(Location topLeft)
	{
		double mx = Mouse.getX(), my = Mouse.getY();
		double x = topLeft.getX(), y = topLeft.getY(), 
			   w = Item.IMAGE_HEIGHT, h = Item.IMAGE_WIDTH;
		
		return (mx >= x) && (mx >= y) && (mx < x + w) && (my < y + h);
	}
}
