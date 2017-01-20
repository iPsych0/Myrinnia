package dev.ipsych0.mygame.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;

	// Tile images
	public static BufferedImage lava, dirt, grass, stone, cave, ice, snow, black;
	public static BufferedImage pathGrassTopLeft, pathGrassTopMiddle, pathGrassTopRight, pathGrassMiddleLeft,
								path, pathGrassMiddleRight, pathGrassDownLeft, pathGrassDownMiddle,
								pathGrassDownRight, grassOnly, sand, sandGrassTopLeft, sandGrassTopMiddle, sandGrassTopRight, sandGrassMiddleLeft,
								sandGrassMiddleRight, sandGrassDownLeft, sandGrassDownMiddle, sandGrassDownRight, water, waterSandTopLeft,
								waterSandTopMiddle, waterSandTopRight, waterSandMiddleLeft, waterSandMiddleRight, waterSandDownLeft, waterSandDownMiddle, 
								waterSandDownRight, dirtSandTopLeft, dirtSandTopMiddle, dirtSandTopRight, dirtSandMiddleLeft, dirtSandMiddleRight, dirtSandDownLeft, 
								dirtSandDownMiddle, dirtSandDownRight, dirtGrassTopLeft, dirtGrassTopMiddle, dirtGrassTopRight, dirtGrassmiddleLeft, dirtGrassMiddleRight,
								dirtGrassDownLeft, dirtGrassDownMiddle, dirtGrassDownRight, pathDirtTopLeft, pathDirtTopMiddle, pathDirtTopRight, pathDirtMiddleLeft,
								pathDirtMiddleRight, pathDirtDownLeft, pathDirtDownMiddle, pathDirtDownRight, lavaPathTopLeft, lavaPathTopMiddle, lavaPathTopRight, 
								lavaPathMiddleLeft, lavaPathMiddleRight, lavaPathDownLeft, lavaPathDownMiddle, lavaPathDownRight, lavaSandTopLeft, lavaSandTopMiddle, 
								lavaSandTopRight, lavaSandMiddleLeft, lavaSandMiddleRight, lavaSandDownLeft, lavaSandDownMiddle, lavaSandDownRight, waterDirtTopLeft, 
								waterDirtTopMiddle, waterDirtTopRight, waterDirtMiddleLeft, waterDirtMiddleRight, waterDirtDownLeft, waterDirtDownMiddle, waterDirtDownRight,
								darkGrass, darkGrassPatch1, darkGrassPatch2, darkGrassPatch3;
	
	// Terrain images
	public static BufferedImage invisible, waterSmallTopLeft, waterSmallTopRight, waterSmallBottomLeft, waterSmallBottomRight, waterTopLeft, waterTopMiddle, waterTopRight,
								waterMiddleLeft, waterMiddleMiddle, waterMiddleRight, waterBottomLeft, waterBottomMiddle, waterBottomRight, waterFlow1, waterFlow2,
								waterFlow3, lavaSmallTopLeft, lavaSmallTopRight, lavaSmallBottomLeft, lavaSmallBottomRight, lavaTopLeft, lavaTopMiddle, lavaTopRight,
								lavaMiddleLeft, lavaMiddleMiddle, lavaMiddleRight, lavaBottomLeft, lavaBottomMiddle, lavaBottomRight, lavaFlow1, lavaFlow2, lavaFlow3;
	
	// Ambiance images
	public static BufferedImage sparkleTile, redMushroom, blueMushroom;
	
	// Animated ambiance images
	public static BufferedImage[] sparkles;
								
	
	
	// Map item images (trees, rocks, etc)
	public static BufferedImage tree, rock;
	
	// Player animation images
	public static BufferedImage[] player_down, player_up, player_left, player_right;
	
	// Player attack images
	public static BufferedImage[] player_attackingLeft, player_attackingRight, player_attackingDown, player_attackingUp;
	
	// Menu images
	public static BufferedImage[] btn_start;
	
	// Item images
	public static BufferedImage wood, ore;
	
	// Enemy images
	public static BufferedImage scorpion;
	
	// NPC images
	
	public static BufferedImage lorraine;
	
	// Green House images
	public static BufferedImage greenHouseRoof, greenHouseWall, greenHouseEntrance;
	
	
	public static void init(){
		SpriteSheet texture_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/textures.png"));
		SpriteSheet player_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/herosprites.png"));
		SpriteSheet menu_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/menusprites.png"));
		SpriteSheet item_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/itemsprites.png"));
		SpriteSheet enemy_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/enemysprites.png"));
		SpriteSheet house_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/housesprites.png"));
		SpriteSheet lorraine_sprites = new SpriteSheet(ImageLoader.loadImage("/textures/lorrainesprites.png"));
		SpriteSheet texture_tile = new SpriteSheet(ImageLoader.loadImage("/textures/texture_tiles.png"));
		SpriteSheet terrain_tile = new SpriteSheet(ImageLoader.loadImage("/textures/terrain.png"));
		SpriteSheet animated_terrain = new SpriteSheet(ImageLoader.loadImage("/textures/animated_terrain.png"));
		SpriteSheet trees_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/trees.png"));
		
		// http://www.online-image-editor.com/ to remove white background from sprites, save as .png!
		
		// Menu sprites
		
		btn_start = new BufferedImage[2];
		
		btn_start[0] = menu_sheet.crop(0, 0, width * 7, height * 3);
		btn_start[1] = menu_sheet.crop(width * 7, 0, width * 7, height * 3);
		
		// Item Sprites
		
		wood = item_sheet.crop(0, 0, width, height);
		ore = item_sheet.crop(0, height, width, height);
		
		// Enemy Sprites
		
		scorpion = enemy_sheet.crop(0, 0, width, height);
		
		// NPC Sprites
		
		lorraine = lorraine_sprites.crop(width * 7, 0, width, height);
		
		// Player Sprites
		
		player_attackingLeft = new BufferedImage[2];
		player_attackingRight = new BufferedImage[2];
		player_attackingDown = new BufferedImage[2];
		player_attackingUp = new BufferedImage[2];
		
		player_down = new BufferedImage[3];
		player_up = new BufferedImage[3];
		player_left = new BufferedImage[3];
		player_right = new BufferedImage[3];
		
		player_attackingLeft[0] = player_sheet.crop(0, height * 4, width, height);
		player_attackingLeft[1] = player_sheet.crop(width, height * 4, width, height);
		
		player_attackingRight[0] = player_sheet.crop(0, height * 5, width, height);
		player_attackingRight[1] = player_sheet.crop(width, height * 5, width, height);
		
		player_attackingDown[0] = player_sheet.crop(0, height * 6, width, height);
		player_attackingDown[1] = player_sheet.crop(width, height * 6, width, height);
		
		player_attackingUp[0] = player_sheet.crop(0, height * 7, width, height);
		player_attackingUp[1] = player_sheet.crop(width, height * 7, width, height);
		
		player_down[0] = player_sheet.crop(0, 0, width, height);
		player_down[1] = player_sheet.crop(width, 0, width, height);
		player_down[2] = player_sheet.crop(width * 2, 0, width, height);
		
		player_up[0] = player_sheet.crop(width * 6, height * 3, width, height);
		player_up[1] = player_sheet.crop(width * 7, height * 3, width, height);
		player_up[2] = player_sheet.crop(width * 8, height * 3, width, height);
		
		player_left[0] = player_sheet.crop(0, height, width, height);
		player_left[1] = player_sheet.crop(width, height, width, height);
		player_left[2] = player_sheet.crop(width * 2, height, width, height);
		
		player_right[0] = player_sheet.crop(0, height * 2, width, height);
		player_right[1] = player_sheet.crop(width, height * 2, width, height);
		player_right[2] = player_sheet.crop(width * 2, height * 2, width, height);
		
		// Tile Sprites
		
		lava = texture_sheet.crop(0, 0, width, height);
		dirt = texture_sheet.crop(width, 0, width, height);
		grass = texture_sheet.crop(width * 2, 0, width, height);
		stone = texture_sheet.crop(width * 3, 0, width, height);
		cave = texture_sheet.crop(width * 3, height * 2, width, height);
		ice = texture_sheet.crop(width * 3, height * 3, width, height);
		snow = texture_sheet.crop(width * 3, height * 5, width, height);
		black = texture_sheet.crop(width * 3, height * 6, width, height);
		
		path = texture_tile.crop(width, height, width, height);
		pathGrassTopLeft = texture_tile.crop(0, 0, width, height);
		pathGrassTopMiddle = texture_tile.crop(width, 0, width, height);
		pathGrassTopRight = texture_tile.crop(width * 2, 0, width, height);
		pathGrassMiddleLeft = texture_tile.crop(0, height, width, height);
		pathGrassMiddleRight = texture_tile.crop(width * 2, height, width, height);
		pathGrassDownLeft = texture_tile.crop(0, height * 2, width, height);
		pathGrassDownMiddle = texture_tile.crop(width , height * 2, width, height);
		pathGrassDownRight = texture_tile.crop(width * 2, height * 2, width, height);
		grassOnly = texture_tile.crop(width * 9 , 0, width, height);
		
		sand = texture_tile.crop(width * 4, height, width, height);
		sandGrassTopLeft = texture_tile.crop(width * 3, 0, width, height);
		sandGrassTopMiddle = texture_tile.crop(width * 4, 0, width, height);
		sandGrassTopRight = texture_tile.crop(width * 5, 0, width, height);
		sandGrassMiddleLeft = texture_tile.crop(width * 3, height, width, height);
		sandGrassMiddleRight = texture_tile.crop(width * 5, height, width, height);
		sandGrassDownLeft = texture_tile.crop(width * 3, height * 2, width, height);
		sandGrassDownMiddle = texture_tile.crop(width * 4, height * 2, width, height);
		sandGrassDownRight = texture_tile.crop(width * 5, height * 2, width, height);
		
		water = texture_tile.crop(width * 7, height, width, height);
		waterSandTopLeft = texture_tile.crop(width * 6, 0, width, height);
		waterSandTopMiddle = texture_tile.crop(width * 7, 0, width, height);
		waterSandTopRight = texture_tile.crop(width * 8, 0, width, height);
		waterSandMiddleLeft = texture_tile.crop(width * 6, height * 1, width, height);
		waterSandMiddleRight = texture_tile.crop(width * 8, height * 1, width, height);
		waterSandDownLeft = texture_tile.crop(width * 6, height * 2, width, height);
		waterSandDownMiddle = texture_tile.crop(width * 7, height * 2, width, height);
		waterSandDownRight = texture_tile.crop(width * 8, height * 2, width, height);
		
		dirtSandTopLeft = texture_tile.crop(0, height * 3, width, height);
		dirtSandTopMiddle = texture_tile.crop(width, height * 3, width, height);
		dirtSandTopRight = texture_tile.crop(width * 2, height * 3, width, height);
		dirtSandMiddleLeft = texture_tile.crop(0, height * 4, width, height);
		dirtSandMiddleRight = texture_tile.crop(width * 2, height * 4, width, height);
		dirtSandDownLeft = texture_tile.crop(0, height * 5, width, height);
		dirtSandDownMiddle = texture_tile.crop(width, height * 5, width, height);
		dirtSandDownRight = texture_tile.crop(width * 2, height * 5, width, height);
		
		dirt = texture_tile.crop(width * 4, height * 4, width, height);
		dirtGrassTopLeft = texture_tile.crop(width * 3, height * 3, width, height);
		dirtGrassTopMiddle = texture_tile.crop(width * 4, height * 3, width, height);
		dirtGrassTopRight = texture_tile.crop(width * 5, height * 3, width, height);
		dirtGrassmiddleLeft = texture_tile.crop(width * 3, height * 4, width, height);
		dirtGrassMiddleRight = texture_tile.crop(width * 5, height * 4, width, height);
		dirtGrassDownLeft = texture_tile.crop(width * 3, height * 5, width, height);
		dirtGrassDownMiddle = texture_tile.crop(width * 4, height * 5, width, height);
		dirtGrassDownRight = texture_tile.crop(width * 5, height * 5, width, height);
		
		pathDirtTopLeft = texture_tile.crop(width * 6, height * 3, width, height);
		pathDirtTopMiddle = texture_tile.crop(width * 7, height * 3, width, height);
		pathDirtTopRight = texture_tile.crop(width * 8, height * 3, width, height);
		pathDirtMiddleLeft = texture_tile.crop(width * 6, height * 4, width, height);
		pathDirtMiddleRight = texture_tile.crop(width * 8, height * 4, width, height);
		pathDirtDownLeft = texture_tile.crop(width * 6, height * 5, width, height);
		pathDirtDownMiddle = texture_tile.crop(width * 7, height * 5, width, height);
		pathDirtDownRight = texture_tile.crop(width * 8, height * 5, width, height);
		
		lavaPathTopLeft = texture_tile.crop(0, height * 6, width, height);
		lavaPathTopMiddle = texture_tile.crop(width, height * 6, width, height);
		lavaPathTopRight = texture_tile.crop(width * 2, height * 6, width, height);
		lavaPathMiddleLeft = texture_tile.crop(0, height * 7, width, height);
		lavaPathMiddleRight = texture_tile.crop(width * 2, height * 7, width, height);
		lavaPathDownLeft = texture_tile.crop(0, height * 8, width, height);
		lavaPathDownMiddle = texture_tile.crop(width, height * 8, width, height);
		lavaPathDownRight = texture_tile.crop(width * 2, height * 8, width, height);
		
		lava = texture_tile.crop(width * 4, height * 7, width, height);
		lavaSandTopLeft = texture_tile.crop(width * 3, height * 6, width, height);
		lavaSandTopMiddle = texture_tile.crop(width * 4, height * 6, width, height);
		lavaSandTopRight = texture_tile.crop(width * 5, height * 6, width, height);
		lavaSandMiddleLeft = texture_tile.crop(width * 3, height * 7, width, height);
		lavaSandMiddleRight = texture_tile.crop(width * 5, height * 7, width, height);
		lavaSandDownLeft = texture_tile.crop(width * 3, height * 8, width, height);
		lavaSandDownMiddle = texture_tile.crop(width * 4, height * 8, width, height);
		lavaSandDownRight = texture_tile.crop(width * 5, height * 8, width, height);
		
		waterDirtTopLeft = texture_tile.crop(width * 6, height * 6, width, height);
		waterDirtTopMiddle = texture_tile.crop(width * 7, height * 6, width, height);
		waterDirtTopRight = texture_tile.crop(width * 8, height * 6, width, height);
		waterDirtMiddleLeft = texture_tile.crop(width * 6, height * 7, width, height);
		waterDirtMiddleRight = texture_tile.crop(width * 8, height * 7, width, height);
		waterDirtDownLeft = texture_tile.crop(width * 6, height * 8, width, height);
		waterDirtDownMiddle = texture_tile.crop(width * 7, height * 8, width, height);
		waterDirtDownRight = texture_tile.crop(width * 8, height * 8, width, height);
		
		darkGrass = terrain_tile.crop(width * 7, height * 9, width, height);
		darkGrassPatch1 = terrain_tile.crop(width * 8, height * 11, width, height);
		darkGrassPatch2 = terrain_tile.crop(width * 7, height * 11, width, height);
		darkGrassPatch3 = terrain_tile.crop(width * 6, height * 11, width, height);
		
		// Static terrain tiles (water, lava, snow, sand, etc)
		invisible = terrain_tile.crop(width * 7, height * 21, width, height);
		
		waterSmallTopLeft = terrain_tile.crop(width * 28, 0, width, height);
		waterSmallTopRight = terrain_tile.crop(width * 29, 0, width, height);
		waterSmallBottomLeft = terrain_tile.crop(width * 28, height, width, height);
		waterSmallBottomRight = terrain_tile.crop(width * 29, height, width, height);
		waterTopLeft = terrain_tile.crop(width * 27, height * 2, width, height);
		waterTopMiddle = terrain_tile.crop(width * 28, height * 2, width, height);
		waterTopRight = terrain_tile.crop(width * 29, height * 2, width, height);
		waterMiddleLeft = terrain_tile.crop(width * 27, height * 3, width, height);
		waterMiddleMiddle = terrain_tile.crop(width * 28, height * 3, width, height);
		waterMiddleRight = terrain_tile.crop(width * 29, height * 3, width, height);
		waterBottomLeft = terrain_tile.crop(width * 27, height * 4, width, height);
		waterBottomMiddle = terrain_tile.crop(width * 28, height * 4, width, height);
		waterBottomRight = terrain_tile.crop(width * 29, height * 4, width, height);
		waterFlow1 = terrain_tile.crop(width * 27, height * 5, width, height);
		waterFlow2 = terrain_tile.crop(width * 28, height * 5, width, height);
		waterFlow3 = terrain_tile.crop(width * 29, height * 5, width, height);
		
		lavaSmallTopLeft = terrain_tile.crop(width * 16, height * 0, width, height);
		lavaSmallTopRight = terrain_tile.crop(width * 17, height * 0, width, height);
		lavaSmallBottomLeft = terrain_tile.crop(width * 16, height * 1, width, height);
		lavaSmallBottomRight = terrain_tile.crop(width * 17, height * 1, width, height);
		lavaTopLeft = terrain_tile.crop(width * 15, height * 2, width, height);
		lavaTopMiddle = terrain_tile.crop(width * 16, height * 2, width, height);
		lavaTopRight = terrain_tile.crop(width * 17, height * 2, width, height);
		lavaMiddleLeft = terrain_tile.crop(width * 15, height * 3, width, height);
		lavaMiddleMiddle = terrain_tile.crop(width * 16, height * 3, width, height);
		lavaMiddleRight = terrain_tile.crop(width * 17, height * 3, width, height);
		lavaBottomLeft = terrain_tile.crop(width * 15, height * 4, width, height);
		lavaBottomMiddle = terrain_tile.crop(width * 16, height * 4, width, height);
		lavaBottomRight = terrain_tile.crop(width * 17, height * 4, width, height);
		lavaFlow1 = terrain_tile.crop(width * 15, height * 5, width, height);
		lavaFlow2 = terrain_tile.crop(width * 16, height * 5, width, height);
		lavaFlow3 = terrain_tile.crop(width * 17, height * 5, width, height);
		
		
		
		// Ambiance tiles
		sparkleTile = terrain_tile.crop(width * 16, height * 18, width, height);
		redMushroom = trees_sheet.crop(width * 7, height * 6, width, height);
		blueMushroom = trees_sheet.crop(width * 7, height * 5, width, height);
		
		// Animated ambiance details
		sparkles = new BufferedImage[3];
		sparkles[0] = animated_terrain.crop(0, 0, width, height);
		sparkles[1] = animated_terrain.crop(width, 0, width, height);
		sparkles[2] = animated_terrain.crop(width * 2, 0, width, height);
		
		
		// Map icons like trees, rocks, etc
		
		tree = texture_sheet.crop(0, height, width, height);
		rock = texture_sheet.crop(0, height * 3, width, height);
		
		// House Spites
		
		greenHouseRoof = house_sheet.crop(width * 8, height, width, height);
		greenHouseWall = house_sheet.crop(width * 8, height * 3, width, height);
		greenHouseEntrance = house_sheet.crop(width * 10, height * 3, width, height);
		
	}
	
}
