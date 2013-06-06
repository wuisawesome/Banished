package com.banished.core;

import com.banished.exceptions.InvalidTileTypeException;
import com.banished.graphics.Image;
import com.banished.graphics.ImageMap;

public class Tiles
{
	public static ImageMap tileImages;
	public static final int NumTiles = 48;
	
	public static Tile[] Tiles;
	public static Tile Empty,
					   Floor,
					   DoubleCorner_UpLeft,
					   DoubleCorner_DownLeft,
					   DoubleCorner_UpRight,
					   DoubleCorner_DownRight,
					   ConvexCorner_DownRight,
					   ConvexCorner_UpRight,
					   ConvexCorner_DownLeft,
					   ConvexCorner_UpLeft,
					   ConcaveCorner_UpLeft,
					   ConcaveCorner_DownLeft,
					   ConcaveCorner_UpRight,
					   ConcaveCorner_DownRight,
					   Wall_Down,
					   Wall_Up,
					   Wall_Left,
					   Wall_Right,
					   DoubleWall_Horizontal,
					   DoubleWall_Vertical,
					   WeirdWall_DownRight,
					   WeirdWall_UpRight,
					   WeirdWall_LeftDown,
					   WeirdWall_LeftUp,
					   WeirdWall_DownLeft,
					   WeirdWall_UpLeft,
					   WeirdWall_RightDown,
					   WeirdWall_RightUp,
					   HalfWallHalfCorners_Down,
					   HalfWallHalfCorners_Up,
					   HalfWallHalfCorners_Left,
					   HalfWallHalfCorners_Right,
					   OppositeCorners_Negative, // negative slope (-1)
					   OppositeCorners_Positive, // positive slope (1)
					   Pillar,
					   UTurn_Down,
					   UTurn_Up,
					   UTurn_Left,
					   UTurn_Right,
					   AdjacentCorners_Up,
					   AdjacentCorners_Down,
					   AdjacentCorners_Right,
					   AdjacentCorners_Left,
					   ThreeCorners_DownLeft,
					   ThreeCorners_UpLeft,
					   ThreeCorners_DownRight,
					   ThreeCorners_UpRight,
					   FourCorners;
	
	static
	{
		tileImages = new ImageMap();
		Tiles = new Tile[NumTiles];
		
		try
		{
			Tiles[0] = Empty = new Tile(new Coordinate(-1, -1), 0, null, true);
			Tiles[1] = Floor = makeTile(false, "tiles/Floor.png");
			Tiles[2] = DoubleCorner_UpLeft = makeTile(true, "tiles/DoubleCorner_UpLeft.png");
			Tiles[3] = DoubleCorner_DownLeft = makeTile(true, "tiles/DoubleCorner_DownLeft.png");
			Tiles[4] = DoubleCorner_UpRight = makeTile(true, "tiles/DoubleCorner_UpLeft.png", true);
			Tiles[5] = DoubleCorner_DownRight = makeTile(true, "tiles/DoubleCorner_DownLeft.png", true);
			Tiles[6] = ConvexCorner_DownRight = makeTile(true, "tiles/ConvexCorner_DownRight.png");
			Tiles[7] = ConvexCorner_UpRight = makeTile(true, "tiles/ConvexCorner_UpRight.png");
			Tiles[8] = ConvexCorner_DownLeft = makeTile(true, "tiles/ConvexCorner_DownLeft.png");
			Tiles[9] = ConvexCorner_UpLeft = makeTile(true, "tiles/ConvexCorner_UpLeft.png");
			Tiles[10] = ConcaveCorner_UpLeft = makeTile(true, "tiles/ConcaveCorner_UpLeft.png");
			Tiles[11] = ConcaveCorner_DownLeft = makeTile(true, "tiles/ConcaveCorner_DownLeft.png");
			Tiles[12] = ConcaveCorner_UpRight = makeTile(true, "tiles/ConcaveCorner_UpRight.png"); 
			Tiles[13] = ConcaveCorner_DownRight = makeTile(true, "tiles/ConcaveCorner_DownRight.png");
			Tiles[14] = Wall_Down = makeTile(true, "tiles/Wall_Down.png");
			Tiles[15] = Wall_Up = makeTile(true, "tiles/Wall_Up.png");
			Tiles[16] = Wall_Left = makeTile(true, "tiles/Wall_Left.png");
			Tiles[17] = Wall_Right = makeTile(true, "tiles/Wall_Right.png");
			Tiles[18] = DoubleWall_Horizontal = makeTile(true, "tiles/DoubleWall_Horizontal.png");
			Tiles[19] = DoubleWall_Vertical = makeTile(true, "tiles/DoubleWall_Vertical.png");
			Tiles[20] = WeirdWall_DownRight = makeTile(true, "tiles/WeirdWall_DownRight.png");
			Tiles[21] = WeirdWall_UpRight = makeTile(true, "tiles/WeirdWall_UpRight.png");
			Tiles[22] = WeirdWall_LeftDown = makeTile(true, "tiles/StoneWallRightLTCorner.png");
			Tiles[23] = WeirdWall_LeftUp = makeTile(true, "tiles/StoneWallRightLBCorner.png");
			Tiles[24] = WeirdWall_DownLeft = makeTile(true, "tiles/WeirdWall_DownRight.png", true);
			Tiles[25] = WeirdWall_UpLeft = makeTile(true, "tiles/WeirdWall_UpRight.png", true);
			Tiles[26] = WeirdWall_RightDown = makeTile(true, "tiles/StoneWallLeftRTCorner.png");
			Tiles[27] = WeirdWall_RightUp = makeTile(true, "tiles/StoneWallLeftRBCorner.png");
			Tiles[28] = HalfWallHalfCorners_Down = makeTile(true, "tiles/HalfWallHalfCorners_Down.png");
			Tiles[29] = HalfWallHalfCorners_Up = makeTile(true, "tiles/HalfWallHalfCorners_Up.png");
			Tiles[30] = HalfWallHalfCorners_Left = makeTile(true, "tiles/HalfWallHalfCorners_Left.png");
			Tiles[31] = HalfWallHalfCorners_Right = makeTile(true, "tiles/HalfWallHalfCorners_Left.png", true);
			Tiles[32] = OppositeCorners_Negative = makeTile(true, "tiles/StoneWallOppositeCornersLTRB.png");
			Tiles[33] = OppositeCorners_Positive = makeTile(true, "tiles/StoneWallOppositeCornersRTLB.png");
			Tiles[34] = Pillar = makeTile(true, "tiles/Pillar.png");
			Tiles[35] = UTurn_Down = makeTile(true, "tiles/UTurn_Down.png");
			Tiles[36] = UTurn_Up = makeTile(true, "tiles/UTurn_Up.png");
			Tiles[37] = UTurn_Left = makeTile(true, "tiles/UTurn_Left.png");
			Tiles[38] = UTurn_Right = makeTile(true, "tiles/UTurn_Left.png", true);
			Tiles[39] = AdjacentCorners_Up = makeTile(true, "tiles/AdjacentCorners_Up.png");
			Tiles[40] = AdjacentCorners_Down = makeTile(true, "tiles/AdjacentCorners_Down.png");
			Tiles[41] = AdjacentCorners_Right = makeTile(true, "tiles/AdjacentCorners_Right.png");
			Tiles[42] = AdjacentCorners_Left = makeTile(true, "tiles/AdjacentCorners_Right.png", true);
			Tiles[43] = ThreeCorners_DownLeft = makeTile(true, "tiles/ThreeCorners_DownLeft.png");
			Tiles[44] = ThreeCorners_UpLeft = makeTile(true, "tiles/ThreeCorners_UpLeft.png");
			Tiles[45] = ThreeCorners_DownRight = makeTile(true, "tiles/ThreeCorners_DownLeft.png", true);
			Tiles[46] = ThreeCorners_UpRight = makeTile(true, "tiles/ThreeCorners_UpLeft.png", true);
			Tiles[47] = FourCorners = makeTile(true, "tiles/FourCorners.png");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static int currentIndex = 1;
	private static Tile makeTile(boolean solid, String imageName)
			throws InvalidTileTypeException
	{
		Tile tile = new Tile(new Coordinate(-1, -1), currentIndex, currentIndex, solid);
		tileImages.load(imageName, currentIndex);
		
		//Tiles[currentIndex] = tile;
		
		currentIndex++;
		
		return tile;
	}
	private static Tile makeTile(boolean solid, String imageName, boolean flipHorizontal)
		throws InvalidTileTypeException
	{
		Tile tile = new Tile(new Coordinate(-1, -1), currentIndex, currentIndex, solid);
		Image image = Image.fromFile(imageName);
		if (flipHorizontal && image.toPImage() != null)
			image = image.flipHorizontal();
		tileImages.load(image, currentIndex);
		
		//Tiles[currentIndex] = tile;
		
		currentIndex++;
		
		return tile;
	}
}
