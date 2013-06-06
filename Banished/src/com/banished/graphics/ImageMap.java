package com.banished.graphics;

import java.util.HashMap;
import java.util.Map;

public class ImageMap
{
	private Map<Object, Image> images;
	
	public ImageMap()
	{
		this.images = new HashMap<Object, Image>();
	}
	
	public Image load(String fileName, Object id)
	{
		Image image = Image.fromFile(fileName);
		this.images.put(id, image);
		return image;
	}
	public Image load(Image image, Object id)
	{
		this.images.put(id, image);
		return image;
	}
	public Image get(Object id)
	{
		return this.images.get(id);
	}
	public void delete(Object id)
	{
		this.images.remove(id);
	}
	public void clear()
	{
		this.images.clear();
	}
}
