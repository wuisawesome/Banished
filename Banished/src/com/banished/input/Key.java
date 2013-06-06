package com.banished.input;

import java.util.HashSet;

public class Key
{
	public final static Key
		A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z,
		Alt, Shift, Ctrl, Tab, Backspace, Enter, Space, Escape, Delete,
		Num0, Num1, Num2, Num3, Num4, Num5, Num6, Num7, Num8, Num9,
		F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12,
		Up, Down, Left, Right;
	
	static
	{
		A = new Key("A"); B = new Key("B"); C = new Key("C"); D = new Key("D"); E = new Key("E");
		F = new Key("F"); G = new Key("G"); H = new Key("H"); I = new Key("I"); J = new Key("J");
		K = new Key("K"); L = new Key("L"); M = new Key("M"); N = new Key("N"); O = new Key("O");
		P = new Key("P"); Q = new Key("Q"); R = new Key("R"); S = new Key("S"); T = new Key("T");
		U = new Key("U"); V = new Key("V"); W = new Key("W"); X = new Key("X"); Y = new Key("Y");
		Z = new Key("Z");
		
		Alt = new Key("Alt"); Shift = new Key("Shift"); Ctrl = new Key("Ctrl"); Tab = new Key("Tab");
		Backspace = new Key("Backspace"); Enter = new Key("Enter"); Space = new Key("[Space]");
		Escape = new Key("Escape"); Delete = new Key("Delete");
		
		Num0 = new Key("0"); Num1 = new Key("1"); Num2 = new Key("2"); Num3 = new Key("3");
		Num4 = new Key("4"); Num5 = new Key("5"); Num6 = new Key("6"); Num7 = new Key("7");
		Num8 = new Key("8"); Num9 = new Key("9");
		
		F1 = new Key("F1"); F2 = new Key("F2"); F3 = new Key("F3"); F4 = new Key("F4");
		F5 = new Key("F5"); F6 = new Key("F6"); F7 = new Key("F7"); F8 = new Key("F8");
		F9 = new Key("F9"); F10 = new Key("F10"); F11 = new Key("F11"); F12 = new Key("F12");
		
		Up = new Key("Up"); Down = new Key("Down"); Left = new Key("Left"); Right = new Key("Right");
	}
	
	private static HashSet<Key> PrevKeysDown = new HashSet<Key>();
	private static HashSet<Key> KeysDown = new HashSet<Key>();
	
	public static void setKeyDown(Key key)
	{
		KeysDown.add(key);
	}
	public static void setKeyUp(Key key)
	{
		KeysDown.remove(key);
	}
	public static void nextFrame()
	{
		HashSet<Key> newKeysDown = new HashSet<Key>();
		for (Key key : KeysDown)
			newKeysDown.add(key);
		
		PrevKeysDown = KeysDown;
		KeysDown = newKeysDown;
	}
	
	public static boolean isDown(Key key)
	{
		return KeysDown.contains(key);
	}
	public static boolean isUp(Key key)
	{
		return KeysDown.contains(key);
	}
	public static boolean wasPressed(Key key)
	{
		// currently down  && not previously down
		return isDown(key) && !PrevKeysDown.contains(key);
	}
	public static boolean wasReleased(Key key)
	{
		// currently up  && previously down
		return isUp(key) && PrevKeysDown.contains(key);
	}
	
	public static Key fromLetterOrNumber(char key)
	{
		Key result = null;
		switch (key)
		{
			case 'a': case 'A': result = Key.A; break;
			case 'b': case 'B': result = Key.B; break;
			case 'c': case 'C': result = Key.C; break;
			case 'd': case 'D': result = Key.D; break;
			case 'e': case 'E': result = Key.E; break;
			case 'f': case 'F': result = Key.F; break;
			case 'g': case 'G': result = Key.G; break;
			case 'h': case 'H': result = Key.H; break;
			case 'i': case 'I': result = Key.I; break;
			case 'j': case 'J': result = Key.J; break;
			case 'k': case 'K': result = Key.K; break;
			case 'l': case 'L': result = Key.L; break;
			case 'm': case 'M': result = Key.M; break;
			case 'n': case 'N': result = Key.N; break;
			case 'o': case 'O': result = Key.O; break;
			case 'p': case 'P': result = Key.P; break;
			case 'q': case 'Q': result = Key.Q; break;
			case 'r': case 'R': result = Key.R; break;
			case 's': case 'S': result = Key.S; break;
			case 't': case 'T': result = Key.T; break;
			case 'u': case 'U': result = Key.U; break;
			case 'v': case 'V': result = Key.V; break;
			case 'w': case 'W': result = Key.W; break;
			case 'x': case 'X': result = Key.X; break;
			case 'y': case 'Y': result = Key.Y; break;
			case 'z': case 'Z': result = Key.Z; break;
			
			case '0': result = Key.Num0; break;
			case '1': result = Key.Num1; break;
			case '2': result = Key.Num2; break;
			case '3': result = Key.Num3; break;
			case '4': result = Key.Num4; break;
			case '5': result = Key.Num5; break;
			case '6': result = Key.Num6; break;
			case '7': result = Key.Num7; break;
			case '8': result = Key.Num8; break;
			case '9': result = Key.Num9; break;
		}
		return result;
	}
	
	private String name;
	public Key(String name)
	{
		this.name = name;
	}
	public String toString()
	{
		return this.name;
	}
}
