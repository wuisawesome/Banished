package com.banished.core;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.banished.Banished;
import com.banished.graphics.Graphics;
import com.banished.graphics.Image;

public class Book {
	
	private static boolean showBook = false;
	private static Image book = Image.fromFile("book/book.png");
	private static ArrayList<Page> pages = new ArrayList<Page>();
	private static int index = 0;
	
	public static void toggleBook()
	{
		showBook ^= true;
	}
	
	public static void render()
	{
		if(showBook)
		{
			Location drawLoc = new Location((Banished.width() - book.getWidth())/2, (Banished.height() - book.getHeight())/2);
			Graphics.DrawImage(book, drawLoc);
			if(pages.size() > 0)
				Graphics.DrawImage(pages.get(index).getPage(), drawLoc);
		}
	}
	
	public static void addPage(Page page)
	{
		pages.add(page);
	}
	
	public static boolean nextPage()
	{
		if(showBook){
			int temp = index;
			index = (int)Algorithms.increment(index, 1, 0, pages.size() - 1);
			if(temp == index)
				return false;
			return true;
		}
		return false;
	}
	
	public static boolean prevPage()
	{
		if(showBook){
			int temp = index;
			index = (int)Algorithms.increment(index, -1, 0, pages.size() - 1);
			if(temp == index)
				return false;
			return true;
		}
		return false;
	}
	
    public static void pageTurn(){
	    try{
	        AudioInputStream audio = AudioSystem.getAudioInputStream(new File("data/book/flipPages.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audio);
	        clip.start();
	    } catch(Exception e){
	        System.out.println("Cannot play sound");
	        e.printStackTrace();
	    }
	}

}
