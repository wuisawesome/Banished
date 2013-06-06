package com.banished.core;

import com.banished.graphics.Image;

public class Page {
	
	private Image page;
	
	public Page(String s)
	{
		page = Image.fromFile(s);
	}
	
	public Image getPage() { return page; }

}
