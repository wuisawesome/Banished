package com.banished.input;

import com.banished.core.Location;

public class Mouse
{
	public enum MouseButton { Left, Right, Middle }
	
	private static Location MouseLoc = new Location(-1, -1), PrevMouseLoc = new Location(-1, -1);
	private static boolean LeftDown, RightDown, MiddleDown,
						   PrevLeftDown, PrevRightDown, PrevMiddleDown;
	
	private static void setMouse(MouseButton button, boolean down)
	{
		switch (button)
		{
			case Left: LeftDown = down; break;
			case Middle: MiddleDown = down; break;
			case Right: RightDown = down; break;
		}
	}
	public static void setMouseLocation(Location location)
	{
		MouseLoc = location;
	}
	public static void setMouseDown(MouseButton button)
	{
		setMouse(button, true);
	}
	public static void setMouseUp(MouseButton button)
	{
		setMouse(button, false);
	}
	public static void nextFrame()
	{
		PrevLeftDown = LeftDown;
		PrevMiddleDown = MiddleDown;
		PrevRightDown = RightDown;
		PrevMouseLoc = new Location(MouseLoc);
	}
	
	public static Location getPreviousLocation()
	{
		return PrevMouseLoc;
	}
	public static Location getLocation()
	{
		return new Location(MouseLoc);
	}
	public static double getPreviousX()
	{
		return PrevMouseLoc.getX();
	}
	public static double getX()
	{
		return MouseLoc.getX();
	}
	public static double getPreviousY()
	{
		return PrevMouseLoc.getY();
	}
	public static double getY()
	{
		return MouseLoc.getY();
	}
	public static Location getDelta()
	{
		return new Location(getX() - getPreviousX(), getY() - getPreviousY());
	}
	
	private static boolean wasDown(MouseButton button)
	{
		switch (button)
		{
			case Left: return PrevLeftDown;
			case Middle: return PrevMiddleDown;
			case Right: return PrevRightDown;
			default: return false;
		}
	}
	public static boolean isDown(MouseButton button)
	{
		switch (button)
		{
			case Left: return LeftDown;
			case Middle: return MiddleDown;
			case Right: return RightDown;
			default: return false;
		}
	}
	public static boolean isUp(MouseButton button)
	{
		return !isDown(button);
	}
	public static boolean wasPressed(MouseButton button)
	{
		return isDown(button) && !wasDown(button);
	}
	public static boolean wasReleased(MouseButton button)
	{
		return isUp(button) && wasDown(button);
	}
}
