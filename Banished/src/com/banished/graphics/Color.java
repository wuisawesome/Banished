package com.banished.graphics;

public class Color
{
	public static float COLOR_MAX_VALUE = 255;
	
	public static final Color 
			Red = new Color(1, 0, 0),
			Green = new Color(0, 1, 0),
			Blue = new Color(0, 0, 1),
			Yellow = new Color(1, 1, 0),
			Magenta = new Color(1, 0, 1),
			Cyan = new Color(0, 1, 1),
			Black = new Color(0, 0, 0),
			White = new Color(1, 1, 1),
			Violet = new Color((float).5, 0, 1),
			Transparent = new Color(0, 0);
	
	private float r, g, b, a;
	
	public Color(float gray)
	{
		this(gray, 1);
	}
	public Color(float gray, float alpha)
	{
		this(gray, gray, gray, alpha);
	}
	public Color(float red, float green, float blue)
	{
		this(red, green, blue, 1);
	}
	public Color(float red, float green, float blue, float alpha)
	{
		this.r = red;
		this.g = green;
		this.b = blue;
		this.a = alpha;
	}
	public Color(Color copy)
	{
		this(copy.getR(), copy.getG(), copy.getB(), copy.getA());
	}
	
	public float getR() { return r; }
	public float getG() { return g; }
	public float getB() { return b; }
	public float getA() { return a; }
	public Color setR(float red) { r = red; return this; }
	public Color setG(float green) { g = green; return this; }
	public Color setB(float blue) { b = blue; return this; }
	public Color setA(float alpha) { a = alpha; return this; }
	
	public int toInt()
	{
		return Graphics.Applet.color(r * COLOR_MAX_VALUE, g * COLOR_MAX_VALUE,
				b * COLOR_MAX_VALUE, a * COLOR_MAX_VALUE);
	}
	public static Color fromInt(int color)
	{
		return new Color(Graphics.Applet.red(color) / COLOR_MAX_VALUE,
				Graphics.Applet.green(color) / COLOR_MAX_VALUE,
				Graphics.Applet.blue(color) / COLOR_MAX_VALUE,
				Graphics.Applet.alpha(color) / COLOR_MAX_VALUE);
	}
	
	public String toString()
	{
		return "[R:" + r + ",B:" + b + ",G:" + g + ",A:" + a + "]";
	}
	public static Color fromString(String str)
	{
		if (!str.startsWith("[") || !str.endsWith("]"))
			return null;
		if (!str.contains(","))
			return null;
		
		str = str.substring(1, str.length() - 1);
		
		String[] data = str.split(",");
		if (data.length != 4) return null;
		
		if (!data[0].startsWith("R:")) return null;
		if (!data[1].startsWith("G:")) return null;
		if (!data[2].startsWith("B:")) return null;
		if (!data[3].startsWith("A:")) return null;
		
		for (int i = 0; i < 4; i++)
			data[i] = data[i].substring(2);
		
		try
		{
			float[] vals = new float[4];
			for (int i = 0; i < 4; i++)
				vals[i] = Float.parseFloat(data[i]);
			
			return new Color(vals[0], vals[1], vals[2], vals[3]);
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}
}
