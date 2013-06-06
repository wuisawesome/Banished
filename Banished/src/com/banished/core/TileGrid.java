package com.banished.core;

import com.banished.core.entities.Player;
import com.banished.exceptions.InvalidTileTypeException;
import com.banished.graphics.Graphics;
import com.banished.graphics.Image;
import java.io.*;

public class TileGrid
{
	private Tile[][] tiles;
	private boolean[][] visible;
	
	public TileGrid(Tile[][] tiles)
	{
		if (tiles == null)
			throw new IllegalArgumentException("The tiles of a tile grid may not be null.");
		
		this.tiles = tiles;
		visible = new boolean[getWidth()][getHeight()];
	}
	public TileGrid(int width, int height)
	{
		if (width <= 0 || height <= 0)
			throw new IllegalArgumentException(
					"The width and height of a tile grid must both be positive.");
		
		this.tiles = new Tile[width][height];
		this.visible = new boolean[width][height];
	}
	
	public int getWidth()
	{
		return tiles.length;
	}
	public int getHeight()
	{
		if (this.getWidth() == 0) return 0;
		return tiles[0].length;
	}
	
	public boolean isInBounds(int x, int y)
	{
		return x >= 0 && y >= 0 && x < this.getWidth() && y < this.getHeight();
	}
	public boolean isInBounds(Coordinate coordinate)
	{
		return isInBounds(coordinate.getX(), coordinate.getY());
	}
	
	public Tile getTile(int x, int y)
	{
		if (isInBounds(x, y))
			return this.tiles[x][y];
		return null;
	}
	
	public Tile getTile(double x, double y){
		return getTile((int)x,(int)y);
	}
	
	public void setTile(int x, int y, Tile tile)
	{
		if (isInBounds(x, y))
			this.tiles[x][y] = tile;
	}
	
	public boolean isSolid(int x, int y)
	{
		if (isInBounds(x, y))
		{
			Tile tile = this.getTile(x, y);
			if (tile == null) return true;
			return tile.isSolid();
		}
		return true;
	}
	public Image getImage(int x, int y)
	{
		if (isInBounds(x, y))
		{
			if (!isVisible(x, y))
				return null;
			
			Tile tile = this.getTile(x, y);
			if (tile == null) return null;
			return tile.getImage();
		}
		return null;
	}
	
	private static final double VISIBILITY = 6;
	public boolean isVisible(int x, int y)
	{
		if (isInBounds(x, y)) return this.visible[x][y];
		return false;
	}
	public void updateVisibility()
	{
		Location pLoc = Player.get().getLocation();
		TileBoundary bounds = new TileBoundary((int)(pLoc.getX() - VISIBILITY), (int)(pLoc.getY() - VISIBILITY),
				(int)(pLoc.getX() + VISIBILITY), (int)(pLoc.getY() + VISIBILITY));
		
		for (int x = bounds.getMinX(); x <= bounds.getMaxX(); x++)
			for (int y = bounds.getMinY(); y <= bounds.getMaxY(); y++)
				if (isInBounds(x, y) && new Location(x + .5, y + .5).distanceTo(pLoc) <= VISIBILITY)
					this.visible[x][y] = true;
	}
	
	/*
	 * THIS METHOD IS THE SOURCE OF INTENSIVE CPU USAGE
	 * %99 OF ALL RUNNING IS IN THIS METHOD
	 */
	public void render(TileBoundary bounds)
	{
		bounds = new TileBoundary(Math.max(0, bounds.getMinX()), Math.max(0, bounds.getMinY()),
				Math.min(bounds.getMaxX(), this.getWidth() - 1), Math.min(bounds.getMaxY(), this.getHeight() - 1));
		
		for (int x = bounds.getMinX(); x <= bounds.getMaxX(); x++)
			for (int y = bounds.getMinY(); y <= bounds.getMaxY(); y++)
				if (isVisible(x, y))
					Graphics.DrawImage(this.getTile(x, y).getImage(),
							this.getTile(x, y).getLocation().toLocation().mult(Tile.SIZE));
	}
	
	public boolean isFloor(int x, int y)
	{
		Tile tile = this.getTile(x, y);
		if (tile == null) return false;
		
		return tile.getType() == Tiles.Floor.getType();
	}
	
	public Edge[] getTileEdges(int x, int y)
	{
		Tile tile = this.getTile(x, y);
		if (tile == null) return new Edge[0];
		
		if (!tile.isSolid()) return new Edge[0];
		
		Coordinate location = tile.getLocation();
		
		Location topLeft = location.toLocation();
		Location topRight = new Coordinate(location.getX() + 1, location.getY()).toLocation();
		Location bottomRight = new Coordinate(location.getX() + 1, location.getY() + 1).toLocation();
		Location bottomLeft = new Coordinate(location.getX(), location.getY() + 1).toLocation();
		
		return new Edge[]
		{
			isFloor(x, y - 1) ? new Edge(topLeft, topRight, new Location(0, -1)) : null,
			isFloor(x + 1, y) ? new Edge(topRight, bottomRight, new Location(1, 0)) : null,
			isFloor(x, y + 1) ? new Edge(bottomRight, bottomLeft, new Location(0, 1)) : null,
			isFloor(x - 1, y) ? new Edge(bottomLeft, topLeft, new Location(-1, 0)) : null
		};
	}
	
	private static class WallTypeCalculator
	{
		private static class FloorMatrix
		{
			private static final int UP = 0, UP_RIGHT = 1, RIGHT = 2, DOWN_RIGHT = 3, DOWN = 4, DOWN_LEFT = 5, LEFT = 6, UP_LEFT = 7;
			
			private boolean[] floor;
			
			public FloorMatrix(boolean upLeft, boolean up, boolean upRight, boolean left, boolean right, boolean downLeft, boolean down, boolean downRight)
			{
				floor = new boolean[8];
				
				floor[UP] = up;
				floor[UP_RIGHT] = upRight;
				floor[RIGHT] = right;
				floor[DOWN_RIGHT] = downRight;
				floor[DOWN] = down;
				floor[DOWN_LEFT] = downLeft;
				floor[LEFT] = left;
				floor[UP_LEFT] = upLeft;
			}
			
			public int toByte()
			{
				int value = 0;
				
				if (floor[UP])         value |= 1 << 0;
				if (floor[UP_RIGHT])   value |= 1 << 1;
				if (floor[RIGHT])      value |= 1 << 2;
				if (floor[DOWN_RIGHT]) value |= 1 << 3;
				if (floor[DOWN])       value |= 1 << 4;
				if (floor[DOWN_LEFT])  value |= 1 << 5;
				if (floor[LEFT])       value |= 1 << 6;
				if (floor[UP_LEFT])    value |= 1 << 7;
				
				return value;
			}
		}
		private static FloorMatrix getSurroundingFloor(TileGrid grid, int x, int y)
		{
			return new FloorMatrix(
	                grid.isFloor(x - 1, y - 1), grid.isFloor(x, y - 1), grid.isFloor(x + 1, y - 1),
	                grid.isFloor(x - 1, y),                             grid.isFloor(x + 1, y),
	                grid.isFloor(x - 1, y + 1), grid.isFloor(x, y + 1), grid.isFloor(x + 1, y + 1));
		}
		private static class WallTypeLookupTable
		{
			private static int[] lookup = 
			{
				-1,15,12,15,17,7,17,7,13,21,41,21,17,7,17,7,
	            14,18,20,18,6,37,6,37,14,18,20,18,6,37,6,37,
	            11,25,33,25,26,3,26,3,40,29,44,29,26,3,26,3,
	            14,18,20,18,6,37,6,37,14,18,20,18,6,37,6,37,
	            16,9,23,9,19,35,19,35,22,5,30,5,19,35,19,35,
	            8,38,4,38,36,34,36,34,8,38,4,38,36,34,36,34,
	            16,9,23,9,19,35,19,35,22,5,30,5,19,35,19,35,
	            8,38,4,38,36,34,36,34,8,38,4,38,36,34,36,34,
	            10,15,39,15,27,7,27,7,32,21,43,21,27,7,27,7,
	            24,18,28,18,2,37,2,37,24,18,28,18,2,37,2,37,
	            42,25,45,25,31,3,31,3,46,29,47,29,31,3,31,3,
	            24,18,28,18,2,37,2,37,24,18,28,18,2,37,2,37,
	            16,9,23,9,19,35,19,35,22,5,30,5,19,35,19,35,
	            8,38,4,38,36,34,36,34,8,38,4,38,36,34,36,34,
	            16,9,23,9,19,35,19,35,22,5,30,5,19,35,19,35,
	            8,38,4,38,36,34,36,34,8,38,4,38,36,34,36,34
			};
			
			public static int get(FloorMatrix floor)
			{
				return lookup[floor.toByte()];
			}
		}
		public static Tile getWallType(TileGrid grid, int x, int y)
		{
			FloorMatrix floor = getSurroundingFloor(grid, x, y);
			int tileType = WallTypeLookupTable.get(floor);
			
			if (tileType == -1)
				return Tiles.Empty;
			else
				return Tiles.Tiles[tileType];
		}
	}
	
	public void calculateWalls()
	{
		calculateWalls(new Coordinate(0, 0), new Coordinate(getWidth() - 1, getHeight() - 1));
	}
	public void calculateWalls(Coordinate topLeft, Coordinate bottomRight)
	{
		try
		{
			for (int x = topLeft.getX(); x <= bottomRight.getX(); x++)
				for (int y = topLeft.getY(); y <= bottomRight.getY(); y++)
					if (!isFloor(x, y))
						setTile(x, y, new Tile(WallTypeCalculator.getWallType(this, x, y), new Coordinate(x, y)));
		}
		catch (InvalidTileTypeException e)
		{
			e.printStackTrace();
		}
	}
	
	private static class LevelReader
	{
		private static final char EMPTY = '.',
								  FLOOR = '#',
								  WALL = '|';
		private static Tile stringToTile(String str)
		{
			if (str.startsWith(Character.toString(EMPTY))) return Tiles.Empty;
			else if (str.startsWith(Character.toString(FLOOR))) return Tiles.Floor;
			
			str = str.substring(1);
			if (str.equals("DC_DL")) return Tiles.DoubleCorner_DownLeft;
			else if (str.equals("DC_UL")) return Tiles.DoubleCorner_UpLeft;
		    else if (str.equals("DC_DR")) return Tiles.DoubleCorner_DownRight;
		    else if (str.equals("DC_UR")) return Tiles.DoubleCorner_UpRight;
		    else if (str.equals("XC_DL")) return Tiles.ConvexCorner_DownLeft;
		    else if (str.equals("XC_UL")) return Tiles.ConvexCorner_UpLeft;
		    else if (str.equals("XC_DR")) return Tiles.ConvexCorner_DownRight;
		    else if (str.equals("XC_UR")) return Tiles.ConvexCorner_UpRight;
		    else if (str.equals("CC_DL")) return Tiles.ConcaveCorner_DownLeft;
		    else if (str.equals("CC_UL")) return Tiles.ConcaveCorner_UpLeft;
		    else if (str.equals("CC_DR")) return Tiles.ConcaveCorner_DownRight;
		    else if (str.equals("CC_UR")) return Tiles.ConcaveCorner_UpRight;
		    else if (str.equals("W_L")) return Tiles.Wall_Left;
		    else if (str.equals("W_R")) return Tiles.Wall_Right;
		    else if (str.equals("W_U")) return Tiles.Wall_Up;
		    else if (str.equals("W_D")) return Tiles.Wall_Down;
		    else if (str.equals("DW_H")) return Tiles.DoubleWall_Horizontal;
		    else if (str.equals("DW_V")) return Tiles.DoubleWall_Vertical;
		    else if (str.equals("WW_UR")) return Tiles.WeirdWall_UpRight;
		    else if (str.equals("WW_UL")) return Tiles.WeirdWall_UpLeft;
		    else if (str.equals("WW_RU")) return Tiles.WeirdWall_RightUp;
		    else if (str.equals("WW_RD")) return Tiles.WeirdWall_RightDown;
		    else if (str.equals("WW_DR")) return Tiles.WeirdWall_DownRight;
		    else if (str.equals("WW_DL")) return Tiles.WeirdWall_DownLeft;
		    else if (str.equals("WW_LU")) return Tiles.WeirdWall_LeftUp;
		    else if (str.equals("WW_LD")) return Tiles.WeirdWall_LeftDown;
		    else if (str.equals("HH_U")) return Tiles.HalfWallHalfCorners_Up;
		    else if (str.equals("HH_D")) return Tiles.HalfWallHalfCorners_Down;
		    else if (str.equals("HH_L")) return Tiles.HalfWallHalfCorners_Left;
		    else if (str.equals("HH_R")) return Tiles.HalfWallHalfCorners_Right;
		    else if (str.equals("OC_N")) return Tiles.OppositeCorners_Negative;
		    else if (str.equals("OC_P")) return Tiles.OppositeCorners_Positive;
		    else if (str.equals("P")) return Tiles.Pillar;
		    else if (str.equals("U_U")) return Tiles.UTurn_Up;
		    else if (str.equals("U_D")) return Tiles.UTurn_Down;
		    else if (str.equals("U_L")) return Tiles.UTurn_Left;
		    else if (str.equals("U_R")) return Tiles.UTurn_Right;
		    else if (str.equals("AC_U")) return Tiles.AdjacentCorners_Up;
		    else if (str.equals("AC_D")) return Tiles.AdjacentCorners_Down;
		    else if (str.equals("AC_L")) return Tiles.AdjacentCorners_Left;
		    else if (str.equals("AC_R")) return Tiles.AdjacentCorners_Right;
		    else if (str.equals("TC_UL")) return Tiles.ThreeCorners_UpLeft;
		    else if (str.equals("TC_UR")) return Tiles.ThreeCorners_UpRight;
		    else if (str.equals("TC_DL")) return Tiles.ThreeCorners_DownLeft;
		    else if (str.equals("TC_DR")) return Tiles.ThreeCorners_DownRight;
		    else if (str.equals("FC")) return Tiles.FourCorners;
		    else return null;
		}
		private static int nextIndexOf(String str, char[] search)
		{
			int i = 1;
			while (true)
			{
				if (i >= str.length()) break;
				boolean found = false;
				for (char ch : search)
					if (str.charAt(i) == ch) found = true;
				if (found) break;
				i++;
			}
			return i;
		}
		private static String findNextTile(String line)
		{
			if (line.charAt(0) == EMPTY) return Character.toString(EMPTY);
			else if (line.charAt(0) == FLOOR) return Character.toString(FLOOR);
			else if (line.charAt(0) == WALL)
				return Character.toString(WALL) + line.substring(1, nextIndexOf(line, new char[] { EMPTY, FLOOR, WALL }));
			else return null;
		}
		public static TileGrid fromFile(String fileName) throws IOException
		{
			String[] lines = Graphics.Applet.loadStrings(fileName);
			if (lines == null)
				throw new IOException("Could not find '" + fileName + "'.");
			int width = Integer.parseInt(lines[0].split(" ")[0]),
				height = Integer.parseInt(lines[0].split(" ")[1]);
			
			TileGrid grid = new TileGrid(width, height);
			
			for (int y = 0; y < height; y++)
			{
				for (int x = 0; x < width; x++)
				{
					String tileStr = findNextTile(lines[y + 1]);
					lines[y + 1] = lines[y + 1].substring(tileStr.length());
					
					try { grid.setTile(x, y, new Tile(stringToTile(tileStr), new Coordinate(x, y))); }
					catch (InvalidTileTypeException e) { e.printStackTrace(); }
				}
			}
			
			return grid;
		}
	}
	public static TileGrid fromFile(String fileName) throws IOException
	{
		return LevelReader.fromFile(fileName);
	}
}
