package com.banished.core.items;

import java.util.List;
import com.banished.core.Location;
import com.banished.input.Mouse.MouseButton;

// interface for any class which implements
// a storage mechanism for items.
public interface IInventory
{
	// is the inventory shown?
	// if not, it will not be rendered, and
	//   the user cannot interact with it
	boolean isShown();

	// render the inventory
	void render();
	
	// returns a list of markers that represent where
	// an item can be picked up from
	List<Object> getItemMarkers();
	
	// returns a list of markers that represent where 
	// items of a given type are allowed to be
	List<Object> getItemMarkers(int itemType);
	
	// gets an item location from a given marker
	Location getLocationFromMarker(Object marker);
	
	// called when the user clicks a slot in this IInventory
	// and wants to hold the item
	Item pickup(Object marker, MouseButton button);
	
	// this method is called when the user clicks on
	// a possible item location in this IInventory.
	// returns a new item to hold
	Item accept(Item item, Object marker);
}
