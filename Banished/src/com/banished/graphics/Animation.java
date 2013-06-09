package com.banished.graphics;

public class Animation
{
	private Image[] images;
	private double switchTime;
	private boolean repeat;
	
	private double time;
	private boolean running;
	private int frame;
	
	public Animation(Image[] images, double switchTime, boolean repeat)
	{
		this(images, switchTime, repeat, 0);
	}
	public Animation(Image[] images, double switchTime, boolean repeat, int startFrame)
	{
		this.images = images;
		this.switchTime = switchTime;
		this.repeat = repeat;
		
		this.time = 0;
		this.running = false;
		this.frame = startFrame;
	}
	
	public int getNumImages() { return this.images.length; }
	
	public Image[] getImages() { return this.images; }
	public Image getImage(int frame) { return this.images[frame]; }
	public int getFrame() { return frame; }
	public Image getImage() { return this.images[this.frame]; }
	
	public void start()
	{
		this.running = true;
	}
	public void pause()
	{
		this.running = false;
	}
	public void stop()
	{
		this.stop(0);
	}
	public void stop(int startFrame)
	{
		this.pause();
		this.reset(startFrame);
	}
	public void reset()
	{
		this.reset(0);
	}
	public void reset(int startFrame)
	{
		this.time = 0;
		this.frame = startFrame;
	}
	
	public void update(double frameTime)
	{
		if (!running) return;
		
		this.time += frameTime;
		if (this.time >= this.switchTime)
		{
			if (!this.repeat && this.frame == this.getNumImages() - 1)
				this.pause();
			this.frame = (this.frame + 1) % this.getNumImages();
			this.time = 0;
		}
	}
	
	public boolean isRunning() { return this.running; }
}
