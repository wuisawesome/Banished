package com.banished.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import processing.core.PConstants;

import com.banished.Banished;
import com.banished.Game;
import com.banished.core.entities.*;
import com.banished.core.entities.enemies.SlimeEntity;
import com.banished.core.entities.enemies.SpawnerEntity;
import com.banished.core.entities.nonliving.PortalForwardEntity;
import com.banished.graphics.Color;
import com.banished.graphics.Graphics;
import com.banished.graphics.Image;

@SuppressWarnings("unused")
public class World
{
	private TileGrid tiles;
	private List<Entity> entities;
	private List<DamageDisplay> damageDisplays;
	private Queue<Entity> entitiesToDie;
	private Queue<Entity> entitiesToAdd;
	private Queue<DamageDisplay> displaysToRemove;
	private Queue<DamageDisplay> displaysToAdd;
	
	public World(TileGrid tiles)
	{
		this.tiles = tiles;
		this.entities = new ArrayList<Entity>();
		this.damageDisplays = new ArrayList<DamageDisplay>();
		this.entitiesToDie = new LinkedList<Entity>();
		this.entitiesToAdd = new LinkedList<Entity>();
		this.displaysToRemove = new LinkedList<DamageDisplay>();
		this.displaysToAdd = new LinkedList<DamageDisplay>();
	}
	public World(int width, int height)
	{
		this(new TileGrid(width, height));
	}
	
	public int getWidth()
	{
		if (this.tiles == null) return -1;
		return this.tiles.getWidth();
	}
	public int getHeight()
	{
		if (this.getWidth() <= 0) return -1;
		return this.tiles.getHeight();
	}
	
	public TileGrid getTiles() { return this.tiles; }
	public List<Entity> getEntities() { return this.entities; }
	
	public Player getPlayer() { return Player.get(); }
	
	public void update(double frameTime)	
	{
		for (Entity entity : this.entities){
//		int size = entities.size();
//		for (int i = 0; i < size; i++){
//			Entity entity = entities.get(i);
			entity.update(frameTime);
			Location loc = entity.getLocation();
			Tile tile = getTiles().getTile(loc.getX(), loc.getY());
			
			if(tile == null)
				entity.removeFromWorld();
			else
				tile.entitySteppedOnTile(entity);
		}
		// TODO: do all of the EntityEventListener events.
		
		for (DamageDisplay disp : this.damageDisplays)
		{
			disp.update(frameTime);
			if(disp.getCount() >= DamageDisplay.DISP_TIME * frameTime)
				displaysToRemove.add(disp);
		}
		
		while (!entitiesToDie.isEmpty())
			this.entities.remove(entitiesToDie.remove());
		while (!entitiesToAdd.isEmpty())
			this.entities.add(entitiesToAdd.remove());
		while (!displaysToRemove.isEmpty())
			this.damageDisplays.remove(displaysToRemove.remove());
		while (!displaysToAdd.isEmpty())
			this.damageDisplays.add(displaysToAdd.remove());
	}
	public void render()
	{
		Graphics.Applet.pushMatrix();
		Location drawOffset = getDrawOffset();
		Graphics.Applet.translate(-(int)drawOffset.getX(), -(int)drawOffset.getY());
		
		if (this.tiles != null)
			this.tiles.render(this.getVisibleTiles());
		
		ArrayList<Entity> entities = new ArrayList<Entity>(this.entities.size());
		for (Entity entity : this.entities)
			entities.add(entity);
		Collections.sort(entities, new Comparator<Entity>()
		{
			public int compare(Entity e1, Entity e2)
			{
				double e1Y = e1.getLocation().getY(),
					   e2Y = e2.getLocation().getY();
				return (e1Y == e2Y) ? 0 : ((e1Y < e2Y) ? -1 : 1);
			}
		});
		
		for (Entity entity : entities)
		{
			Coordinate tile = entity.getLocation().toCoordinate();
			if (!this.tiles.isVisible(tile.getX(), tile.getY())) continue;
			
			if (Banished.SHOW_EDGE_DEBUGGING_INFO)
			{
				// USED ONLY FOR DEBUGGING PURPOSES
				entity.getShape().render();	
				if (LivingEntity.toTrack == entity)				
					((LivingEntity)entity).renderEdges();
			}
			
			if (entity instanceof SpawnerEntity)
			{
				Graphics.DrawImage(entity.getImage(),
						((SpawnerEntity)entity).getSpawnerColor(),
						entity.getLocation().sub(entity.getImageOffset()).mult(Tile.SIZE), ((SpawnerEntity)entity).getRotation());
				Graphics.DrawImage(entity.getImage(),
						((SpawnerEntity)entity).getSpawnerColor(),
						entity.getLocation().sub(entity.getImageOffset()).mult(Tile.SIZE), 90 - ((SpawnerEntity)entity).getRotation());
			}
			else if (entity instanceof PortalForwardEntity)
				Graphics.DrawImage(entity.getImage(), entity.getLocation().sub(entity.getImageOffset()).mult(Tile.SIZE), ((PortalForwardEntity)entity).getRotation());
			else if (entity instanceof SlimeEntity)
				Graphics.DrawImage(entity.getImage(), ((SlimeEntity)entity).getSlimeColor(), entity.getLocation().sub(entity.getImageOffset()).mult(Tile.SIZE));
			else
				Graphics.DrawImage(entity.getImage(), entity.getLocation().sub(entity.getImageOffset()).mult(Tile.SIZE));
			
			if (entity == Player.get())
			{
				Player p = Player.get();
				Graphics.DrawImage(p.getChestImage(), p.getLocation().sub(p.getImageOffset()).mult(Tile.SIZE));
				Graphics.DrawImage(p.getLegsImage(), p.getLocation().sub(p.getImageOffset()).mult(Tile.SIZE));
				Graphics.DrawImage(p.getShoesImage(), p.getLocation().sub(p.getImageOffset()).mult(Tile.SIZE));
				Graphics.DrawImage(p.getGlovesImage(), p.getLocation().sub(p.getImageOffset()).mult(Tile.SIZE));
				
				if (p.showLevelUpAnim())
					Graphics.DrawImage(p.getLevelUpAnimImage(), p.getLocation().sub(p.getImageOffset()).mult(Tile.SIZE));
			}
			
			drawHealthBar(entity);
		}
		
		for (DamageDisplay disp : damageDisplays)
		{
			Graphics.Applet.textAlign(PConstants.CENTER);
			Graphics.Applet.textSize(20);
			Graphics.Applet.fill(disp.getColor().getR() * 255, disp.getColor().getG() * 255, disp.getColor().getB() * 255, disp.getColor().getA() * 255);
			Graphics.Applet.text(disp.getDamage(), (float)disp.getLocation().getX() * Tile.SIZE, (float)disp.getLocation().getY() * Tile.SIZE);
		}
		
		Graphics.Applet.popMatrix();
	}
	private void drawHealthBar(Entity entity)
	{
		if (entity == Player.get()) return;
		
		if (!(entity instanceof LivingEntity)) return;
		LivingEntity ent = (LivingEntity)entity;
		if (ent.getHealth() == ent.getMaxHealth()) return;
		
		final int HEALTH_BAR_WIDTH = 30, HEALTH_BAR_HEIGHT = 6, BORDER_WIDTH = 1;
		
		int numRedPixels = (int)Math.ceil(ent.getHealth()/ent.getMaxHealth() * (HEALTH_BAR_WIDTH - 2 * BORDER_WIDTH));

		Graphics.Applet.noStroke();
		
		Graphics.Applet.fill(0);
		Graphics.Applet.rect((float)(ent.getLocation().getX() * Tile.SIZE - (HEALTH_BAR_WIDTH / 2)),
				(float)(ent.getLocation().getY() * Tile.SIZE - ent.getHealthBarYOffset() - (HEALTH_BAR_HEIGHT / 2)),
				HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
		
		Graphics.Applet.fill(255, 0, 0);
		Graphics.Applet.rect((float)(ent.getLocation().getX() * Tile.SIZE - (HEALTH_BAR_WIDTH / 2) + BORDER_WIDTH),
				(float)(ent.getLocation().getY() * Tile.SIZE - ent.getHealthBarYOffset() - (HEALTH_BAR_HEIGHT / 2) + BORDER_WIDTH),
				HEALTH_BAR_WIDTH - 2 * BORDER_WIDTH, HEALTH_BAR_HEIGHT - 2 * BORDER_WIDTH);
		
		Graphics.Applet.fill(0, 255, 0);
		Graphics.Applet.rect((float)(ent.getLocation().getX() * Tile.SIZE - (HEALTH_BAR_WIDTH / 2) + BORDER_WIDTH),
				(float)(ent.getLocation().getY() * Tile.SIZE - ent.getHealthBarYOffset() - (HEALTH_BAR_HEIGHT / 2) + BORDER_WIDTH),
				numRedPixels, HEALTH_BAR_HEIGHT - 2 * BORDER_WIDTH);
	}
	
	public List<Entity> getClosestEntities(Direction dir, double radius)
	{
		List<Entity> entities = new ArrayList<Entity>();
		
		for (Entity entity : this.entities)
		{
			if (entity == Player.get()) continue;
			if (getPlayer().getLocation().squareDistanceTo(entity.getLocation()) <= radius * radius)
			{
				Direction playerDir = getPlayer().getDirection();
				if (playerDir == Direction.Southwest || playerDir == Direction.Northwest) playerDir = Direction.West;
				else if (playerDir == Direction.Southeast || playerDir == Direction.Northeast) playerDir = Direction.East;
				
				Direction entityDir = Direction.fromVector(getPlayer().getLocation().sub(entity.getLocation()));
				entityDir = entityDir.opposite();
				if (entityDir == Direction.Southwest || entityDir == Direction.Northwest) entityDir = Direction.West;
				else if (entityDir == Direction.Southeast || entityDir == Direction.Northeast) entityDir = Direction.East;
				
				if (entityDir == playerDir)
					entities.add(entity);
			}
		}
		
		Collections.sort(entities, new EntityDistComparer(getPlayer().getLocation()));
		return entities;
	}
	private static class EntityDistComparer implements Comparator<Entity>
	{
		private Location playerLoc;
		
		public EntityDistComparer(Location playerLoc)
		{
			this.playerLoc = playerLoc;
		}
		
		public int compare(Entity entity1, Entity entity2)
		{
			return (int)(playerLoc.squareDistanceTo(entity1.getLocation())
					- playerLoc.squareDistanceTo(entity2.getLocation()));
		}
	}
	
	public void addEntity(Entity entity)
	{
		this.entities.add(entity);
	}
	
	public void removeEntity(Entity entity)
	{
		this.entities.remove(entities);
	}
	
	public void addEntityToDie(Entity entity)
	{
		this.entitiesToDie.add(entity);
	}
	
	public void addEntityToAdd(Entity entity)
	{
		this.entitiesToAdd.add(entity);
	}
	
	public void addDispToAdd(DamageDisplay disp)
	{
		this.displaysToAdd.add(disp);
	}
	
	public void addDispToRemove(DamageDisplay disp)
	{
		this.displaysToRemove.remove();
	}
	
	public void interact()
	{
		List<Entity> closest = getClosestEntities(Player.get().getDirection(), Player.INTERACTION_RANGE);
		if (closest.size() > 0)
			closest.get(0).onInteractedWith();
	}
	
	// screen + drawOffset = world
	public static Location getDrawOffset()
	{
		// (PlayerLoc * TileSize) - ([WIDTH, HEIGHT] / 2)
		return Player.get().getLocation().mult(Tile.SIZE).sub(new Location(Banished.width(), Banished.height()).div(2));
	}
	public static Location toWorldCoords(Location screenCoords)
	{
		return screenCoords.add(getDrawOffset()).div(Tile.SIZE);
	}
	public static Location toScreenCoords(Location worldCoords)
	{
		// screen = world - drawOffset
		return worldCoords.mult(Tile.SIZE).sub(getDrawOffset());
	}
	
	public TileBoundary getVisibleTiles()
	{
		Coordinate topLeft = toWorldCoords(new Location()).sub(new Location(1, 1)).toCoordinate();
		Coordinate bottomRight = toWorldCoords(new Location(Banished.width(), Banished.height())).add(new Location(1, 1)).toCoordinate();
		return new TileBoundary(topLeft.getX(), topLeft.getY(), bottomRight.getX(), bottomRight.getY());
	}
	
	public void saveToFile(String fileName)
	{
		// TODO: implement.
	}
	public static World loadFromFile(String fileName)
	{
		// TODO: implement.
		return null;
	}
}
