package com.banished;

import java.awt.event.KeyEvent;

import com.banished.core.Location;
import com.banished.core.State;
import com.banished.core.Tile;
import com.banished.graphics.Graphics;
import com.banished.input.Key;
import com.banished.input.Mouse;
import com.banished.input.Mouse.MouseButton;

import processing.core.PApplet;

@SuppressWarnings("unused")
public class Banished extends PApplet
{
	public static final boolean FULLSCREEN = true;
	public static final boolean DEBUGGING = false;
	public static final boolean DEBUGGING_INVENTORY = false;
	public static boolean SHOW_EDGE_DEBUGGING_INFO = false;
	
	private static final long serialVersionUID = -4676927820109850700L;

	private final static int WIDTH = 960, HEIGHT = 540 ;
	
	private static Banished banished;
	
	public static void main(String[] args)
	{
		args = new String[FULLSCREEN ? 2 : 1];
		args[args.length - 1] = Banished.class.getName();
		if (FULLSCREEN) args[0] = "--present";
		PApplet.main(args);
	}
	
	public Banished()
	{
		banished = this;
	}
	public static double getFrameRate() { return banished.frameRate; }	
	public static int width() { return banished.width; }
	public static int height() { return banished.height; }
	public static void exitApp() { banished.exit(); }
	
	public boolean sketchFullScreen() { return FULLSCREEN; }
	
	public static Banished getApplet() { return banished; }
	
	public void setup()
	{
		Graphics.Initialize(this);
		
		this.size(FULLSCREEN ? this.screenWidth : WIDTH, FULLSCREEN ? this.screenHeight : HEIGHT);
		this.smooth();
		
		Game.run();
	}
	
	public void draw()
	{
		if (State.UpdateState(1f / this.frameRate))
			this.exit();
		State current = State.GetCurrentState();
		if (current == null)
		{
			this.exit();
			return;
		}
		
		State.DrawState();
		
		Key.nextFrame();
		Mouse.nextFrame();
	}
	
	public void keyPressed()
	{
		Key keyPressed = getKeyFromApplet(this.key, this.keyCode);
		Key.setKeyDown(keyPressed);
		
		if (this.key == ESC) this.key = 0;
	}
	public void keyReleased()
	{
		Key keyReleased = getKeyFromApplet(this.key, this.keyCode);
		Key.setKeyUp(keyReleased);
	}
	
	public void mousePressed()
	{
		if (this.mouseButton == PApplet.LEFT)
			Mouse.setMouseDown(MouseButton.Left);
		else if (this.mouseButton == PApplet.RIGHT)
			Mouse.setMouseDown(MouseButton.Right);
		else
			Mouse.setMouseDown(MouseButton.Middle);
	}
	public void mouseReleased()
	{
		if (this.mouseButton == PApplet.LEFT)
			Mouse.setMouseUp(MouseButton.Left);
		else if (this.mouseButton == PApplet.RIGHT)
			Mouse.setMouseUp(MouseButton.Right);
		else
			Mouse.setMouseUp(MouseButton.Middle);
	}
	public void mouseMoved()
	{
		Mouse.setMouseLocation(new Location(this.mouseX, this.mouseY));
	}
	public void mouseDragged()
	{
		this.mouseMoved();
	}
	
	private static Key getKeyFromApplet(char keyChar, int keyCode)
	{
		Key key = null;
		if (keyChar != PApplet.CODED)
		{
			if ((keyChar >= 'a' && keyChar <= 'z') || (keyChar >= 'A' && keyChar <= 'Z')
				|| (keyChar >= '0' && keyChar <= '9'))
			{
				key = Key.fromLetterOrNumber(keyChar);
			}
			else switch (keyChar)
			{
				case ' ': key = Key.Space; break;
				case PApplet.BACKSPACE: key = Key.Backspace; break;
				case PApplet.TAB: key = Key.Tab; break;
				case PApplet.ENTER:
				case PApplet.RETURN: 
					key = Key.Enter; break;
				case PApplet.ESC: key = Key.Escape; break;
				case PApplet.DELETE: key = Key.Delete; break;
			}
		}
		else switch (keyCode)
		{
			case PApplet.UP: key = Key.Up; break;
			case PApplet.DOWN: key = Key.Down; break;
			case PApplet.LEFT: key = Key.Left; break;
			case PApplet.RIGHT: key = Key.Right; break;
			
			case PApplet.ALT: key = Key.Alt; break;
			case PApplet.SHIFT: key = Key.Shift; break;
			case PApplet.CONTROL: key = Key.Ctrl; break;
			
			case KeyEvent.VK_F1: key = Key.F1; break;
			case KeyEvent.VK_F2: key = Key.F2; break;
			case KeyEvent.VK_F3: key = Key.F3; break;
			case KeyEvent.VK_F4: key = Key.F4; break;
			case KeyEvent.VK_F5: key = Key.F5; break;
			case KeyEvent.VK_F6: key = Key.F6; break;
			case KeyEvent.VK_F7: key = Key.F7; break;
			case KeyEvent.VK_F8: key = Key.F8; break;
			case KeyEvent.VK_F9: key = Key.F9; break;
			case KeyEvent.VK_F10: key = Key.F10; break;
			case KeyEvent.VK_F11: key = Key.F11; break;
			case KeyEvent.VK_F12: key = Key.F12; break;
		}
		return key;
	}
}
