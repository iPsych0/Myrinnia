package dev.ipsych0.mygame.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;

	// Tile images
	public static BufferedImage black, pathGrassTopLeft, pathGrassTopMiddle, pathGrassTopRight, pathGrassMiddleLeft,
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
	
	// Terrain images (paths, lava, water, etc)
	public static BufferedImage invisible, waterSmallTopLeft, waterSmallTopRight, waterSmallBottomLeft, waterSmallBottomRight, waterTopLeft, waterTopMiddle, waterTopRight,
								waterMiddleLeft, waterMiddleMiddle, waterMiddleRight, waterBottomLeft, waterBottomMiddle, waterBottomRight, waterFlow1, waterFlow2,
								waterFlow3, lavaSmallTopLeft, lavaSmallTopRight, lavaSmallBottomLeft, lavaSmallBottomRight, lavaTopLeft, lavaTopMiddle, lavaTopRight,
								lavaMiddleLeft, lavaMiddleMiddle, lavaMiddleRight, lavaBottomLeft, lavaBottomMiddle, lavaBottomRight, lavaFlow1, lavaFlow2, lavaFlow3,
								sandWaterTopLeft, sandWaterTopMiddle, sandWaterTopRight, sandWaterMiddleLeft, sandWaterMiddleMiddle, sandWaterMiddleRight, sandWaterBottomLeft,
								sandWaterBottomMiddle, sandWaterBottomRight, sandWaterSmallTopLeft, sandWaterSmallTopRight, sandWaterSmallBottomLeft, sandWaterSmallBottomRight,
								holeTopLeft, holeTopMiddle, holeTopRight, holeMiddleLeft, holeMiddleMiddle, holeMiddleRight, holeBottomLeft, holeBottomMiddle, holeBottomRight,
								holeSmallTopLeft, holeSmallTopRight, holeSmallBottomLeft, holeSmallBottomRight, transDirtTopLeft, transDirtTopMiddle, transDirtTopRight,
								transDirtMiddleLeft, transDirtMiddleMiddle, transDirtMiddleRight, transDirtBottomLeft, transDirtBottomMiddle, transDirtBottomRight,
								darkDirtTopLeft, darkDirtTopMiddle, darkDirtTopRight, darkDirtMiddleLeft, darkDirtMiddleMiddle, darkDirtMiddleRight, darkDirtBottomLeft,
								darkDirtBottomMiddle, darkDirtBottomRight, redDirtTopLeft, redDirtTopMiddle, redDirtTopRight, redDirtMiddleLeft, redDirtMiddleMiddle,
								redDirtMiddleRight, redDirtBottomLeft, redDirtBottomMiddle, redDirtBottomRight, greyDirtTopLeft, greyDirtTopMiddle, greyDirtTopRight,
								greyDirtMiddleLeft, greyDirtMiddleMiddle, greyDirtMiddleRight, greyDirtBottomLeft, greyDirtBottomMiddle, greyDirtBottomRight,
								greyDirtEffect1, greyDirtEffect2, greyDirtEffect3, redDirtEffect1, redDirtEffect2, redDirtEffect3, darkDirtEffect1, darkDirtEffect2,
								darkDirtEffect3, redDirtSmallTopLeft, redDirtSmallTopRight, redDirtSmallBottomLeft, redDirtSmallBottomRight, greyDirtSmallTopLeft,
								greyDirtSmallTopRight, greyDirtSmallBottomLeft, greyDirtSmallBottomRight, darkDirtSmallTopLeft, darkDirtSmallTopRight, darkDirtSmallBottomLeft,
								darkDirtSmallBottomRight, transDirtSmallTopLeft, transDirtSmallTopRight, transDirtSmallBottomLeft, transDirtSmallBottomRight, 
								sandTopLeft, sandTopMiddle, sandTopRight, sandMiddleLeft, sandMiddleMiddle, sandMiddleRight, sandBottomLeft, sandBottomMiddle, sandBottomRight,
								sandSmallTopLeft, sandSmallTopRight, sandSmallBottomLeft, sandSmallBottomRight, sandPattern1, sandPattern2, sandPattern3, snowTopLeft, snowTopMiddle,
								snowTopRight, snowMiddleLeft, snowMiddleMiddle, snowMiddleRight, snowBottomLeft, snowBottomMiddle, snowBottomRight, snowSmallTopLeft,
								snowSmallTopRight, snowSmallBottomLeft, snowSmallBottomRight, snowPattern1, snowPattern2, snowPattern3, snowWaterTopLeft, snowWaterTopMiddle,
								snowWaterTopRight, snowWaterMiddleLeft, snowWaterMiddleMiddle, snowWaterMiddleRight, snowWaterBottomLeft, snowWaterBottomMiddle, snowWaterBottomRight,
								snowWaterSmallTopLeft, snowWaterSmallTopRight, snowWaterSmallBottomLeft, snowWaterSmallBottomRight;
	
	// Object images
	public static BufferedImage teleportShrine1, teleportShrine2, teleportShrinePillar1, teleportShrinePillar2;
	
	
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
	public static BufferedImage[] coins;
	
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
		SpriteSheet object_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/object_sprites11.png"));
		
		// http://www.online-image-editor.com/ to remove white background from sprites, save as .png!
		
		// Menu sprites
		
		btn_start = new BufferedImage[2];
		
		btn_start[0] = menu_sheet.crop(0, 0, width * 7, height * 3);
		btn_start[1] = menu_sheet.crop(width * 7, 0, width * 7, height * 3);
		
		// Item Sprites
		
		coins = new BufferedImage[4];
		
		wood = item_sheet.crop(0, 0, width, height);
		ore = item_sheet.crop(0, height, width, height);
		coins[0] = item_sheet.crop(width * 0, height * 2, width, height);
		coins[1] = item_sheet.crop(width * 0, height * 3, width, height);
		coins[2] = item_sheet.crop(width * 0, height * 4, width, height);
		coins[3] = item_sheet.crop(width * 0, height * 5, width, height);
		
		// Object Sprites
		teleportShrine1 = object_sheet.crop(width * 7, height * 10, width, height);
		teleportShrine2 = object_sheet.crop(width * 7, height * 11, width, height);
		teleportShrinePillar1 = object_sheet.crop(width * 9, width * 10, width, height);
		teleportShrinePillar2 = object_sheet.crop(width * 9, width * 11, width, height);
		
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
		
		// Object sprites
		
		
		// Tile Sprites
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
		
		sandWaterTopLeft = terrain_tile.crop(width * 21, height * 8, width, height);
		sandWaterTopMiddle = terrain_tile.crop(width * 22, height * 8, width, height);
		sandWaterTopRight = terrain_tile.crop(width * 23, height * 8, width, height);
		sandWaterMiddleLeft = terrain_tile.crop(width * 21, height * 9, width, height);
		sandWaterMiddleMiddle = terrain_tile.crop(width * 22, height * 9, width, height);
		sandWaterMiddleRight = terrain_tile.crop(width * 23, height * 9, width, height);
		sandWaterBottomLeft = terrain_tile.crop(width * 21, height * 10, width, height);
		sandWaterBottomMiddle = terrain_tile.crop(width * 22, height * 10, width, height);
		sandWaterBottomRight = terrain_tile.crop(width * 23, height * 10, width, height);
		sandWaterSmallTopLeft = terrain_tile.crop(width * 22, height * 6, width, height);
		sandWaterSmallTopRight = terrain_tile.crop(width * 23, height * 6, width, height);
		sandWaterSmallBottomLeft = terrain_tile.crop(width * 22, height * 7, width, height);
		sandWaterSmallBottomRight = terrain_tile.crop(width * 23, height * 7, width, height);
		
		holeSmallTopLeft = terrain_tile.crop(width * 19, height * 0, width, height);
		holeSmallTopRight = terrain_tile.crop(width * 20, height * 0, width, height);
		holeSmallBottomLeft = terrain_tile.crop(width * 19, height * 1, width, height);
		holeSmallBottomRight = terrain_tile.crop(width * 20, height * 1, width, height);
		holeTopLeft = terrain_tile.crop(width * 18, height * 2, width, height);
		holeTopMiddle = terrain_tile.crop(width * 19, height * 2, width, height);
		holeTopRight = terrain_tile.crop(width * 20, height * 2, width, height);
		holeMiddleLeft = terrain_tile.crop(width * 18, height * 3, width, height);
		holeMiddleMiddle = terrain_tile.crop(width * 19, height * 3, width, height);
		holeMiddleRight = terrain_tile.crop(width * 20, height * 3, width, height);
		holeBottomLeft = terrain_tile.crop(width * 18, height * 4, width, height);
		holeBottomMiddle = terrain_tile.crop(width * 19, height * 4, width, height);
		holeBottomRight = terrain_tile.crop(width * 20, height * 4, width, height);
		
		transDirtTopLeft = terrain_tile.crop(width * 0, height * 2, width, height);
		transDirtTopMiddle = terrain_tile.crop(width * 1, height * 2, width, height);
		transDirtTopRight = terrain_tile.crop(width * 2, height * 2, width, height);
		transDirtMiddleLeft = terrain_tile.crop(width * 0, height * 3, width, height);
		transDirtMiddleMiddle = terrain_tile.crop(width * 1, height * 3, width, height);
		transDirtMiddleRight = terrain_tile.crop(width * 2, height * 3, width, height);
		transDirtBottomLeft = terrain_tile.crop(width * 0, height * 4, width, height);
		transDirtBottomMiddle = terrain_tile.crop(width * 1, height * 4, width, height);
		transDirtBottomRight = terrain_tile.crop(width * 2, height * 4, width, height);
		transDirtSmallTopLeft = terrain_tile.crop(width * 1, height * 0, width, height);
		transDirtSmallTopRight = terrain_tile.crop(width * 2, height * 0, width, height);
		transDirtSmallBottomLeft = terrain_tile.crop(width * 1, height * 1, width, height);
		transDirtSmallBottomRight = terrain_tile.crop(width * 2, height * 1, width, height);
		
		redDirtTopLeft = terrain_tile.crop(width * 6, height * 2, width, height);
		redDirtTopMiddle = terrain_tile.crop(width * 7, height * 2, width, height);
		redDirtTopRight = terrain_tile.crop(width * 8, height * 2, width, height);
		redDirtMiddleLeft = terrain_tile.crop(width * 6, height * 3, width, height);
		redDirtMiddleMiddle = terrain_tile.crop(width * 7, height * 3, width, height);
		redDirtMiddleRight = terrain_tile.crop(width * 8, height * 3, width, height);
		redDirtBottomLeft = terrain_tile.crop(width * 6, height * 4, width, height);
		redDirtBottomMiddle = terrain_tile.crop(width * 7, height * 4, width, height);
		redDirtBottomRight = terrain_tile.crop(width * 8, height * 4, width, height);
		redDirtEffect1 = terrain_tile.crop(width * 6, height * 5, width, height);
		redDirtEffect2 = terrain_tile.crop(width * 7, height * 5, width, height);
		redDirtEffect3 = terrain_tile.crop(width * 8, height * 5, width, height);
		redDirtSmallTopLeft = terrain_tile.crop(width * 7, height * 0, width, height);
		redDirtSmallTopRight = terrain_tile.crop(width * 8, height * 0, width, height);
		redDirtSmallBottomLeft = terrain_tile.crop(width * 7, height * 1, width, height);
		redDirtSmallBottomRight = terrain_tile.crop(width * 8, height * 1, width, height);

		
		greyDirtTopLeft = terrain_tile.crop(width * 9, height * 2, width, height);
		greyDirtTopMiddle = terrain_tile.crop(width * 10, height * 2, width, height);
		greyDirtTopRight = terrain_tile.crop(width * 11, height * 2, width, height);
		greyDirtMiddleLeft = terrain_tile.crop(width * 9, height * 3, width, height);
		greyDirtMiddleMiddle = terrain_tile.crop(width * 10, height * 3, width, height);
		greyDirtMiddleRight = terrain_tile.crop(width * 11, height * 3, width, height);
		greyDirtBottomLeft = terrain_tile.crop(width * 9, height * 4, width, height);
		greyDirtBottomMiddle = terrain_tile.crop(width * 10, height * 4, width, height);
		greyDirtBottomRight = terrain_tile.crop(width * 11, height * 4, width, height);
		greyDirtEffect1 = terrain_tile.crop(width * 9, height * 5, width, height);
		greyDirtEffect2 = terrain_tile.crop(width * 10, height * 5, width, height);
		greyDirtEffect3 = terrain_tile.crop(width * 11, height * 5, width, height);
		greyDirtSmallTopLeft = terrain_tile.crop(width * 10, height * 0, width, height);
		greyDirtSmallTopRight = terrain_tile.crop(width * 11, height * 0, width, height);
		greyDirtSmallBottomLeft = terrain_tile.crop(width * 10, height * 1, width, height);
		greyDirtSmallBottomRight = terrain_tile.crop(width * 11, height * 1, width, height);

		
		darkDirtTopLeft = terrain_tile.crop(width * 3, height * 2, width, height);
		darkDirtTopMiddle = terrain_tile.crop(width * 4, height * 2, width, height);
		darkDirtTopRight = terrain_tile.crop(width * 5, height * 2, width, height);
		darkDirtMiddleLeft = terrain_tile.crop(width * 3, height * 3, width, height);
		darkDirtMiddleMiddle = terrain_tile.crop(width * 4, height * 3, width, height);
		darkDirtMiddleRight = terrain_tile.crop(width * 5, height * 3, width, height);
		darkDirtBottomLeft = terrain_tile.crop(width * 3, height * 4, width, height);
		darkDirtBottomMiddle = terrain_tile.crop(width * 4, height * 4, width, height);
		darkDirtBottomRight = terrain_tile.crop(width * 5, height * 4, width, height);
		darkDirtEffect1 = terrain_tile.crop(width * 3, height * 5, width, height);
		darkDirtEffect2 = terrain_tile.crop(width * 4, height * 5, width, height);
		darkDirtEffect3 = terrain_tile.crop(width * 5, height * 5, width, height);
		darkDirtSmallTopLeft = terrain_tile.crop(width * 4, height * 0, width, height);
		darkDirtSmallTopRight = terrain_tile.crop(width * 5, height * 0, width, height);
		darkDirtSmallBottomLeft = terrain_tile.crop(width * 4, height * 1, width, height);
		darkDirtSmallBottomRight = terrain_tile.crop(width * 5, height * 1, width, height);
		
		snowTopLeft = terrain_tile.crop(width * 18, height * 14, width, height);
		snowTopMiddle = terrain_tile.crop(width * 19, height * 14, width, height);
		snowTopRight = terrain_tile.crop(width * 20, height * 14, width, height);
		snowMiddleLeft = terrain_tile.crop(width * 18, height * 15, width, height);
		snowMiddleMiddle = terrain_tile.crop(width * 19, height * 15, width, height);
		snowMiddleRight = terrain_tile.crop(width * 20, height * 15, width, height);
		snowBottomLeft = terrain_tile.crop(width * 18, height * 16, width, height);
		snowBottomMiddle = terrain_tile.crop(width * 19, height * 16, width, height);
		snowBottomRight = terrain_tile.crop(width * 20, height * 16, width, height);
		snowSmallTopLeft = terrain_tile.crop(width * 19, height * 12, width, height);
		snowSmallTopRight = terrain_tile.crop(width * 20, height * 12, width, height);
		snowSmallBottomLeft = terrain_tile.crop(width * 19, height * 13, width, height);
		snowSmallBottomRight = terrain_tile.crop(width * 20, height * 13, width, height);
		snowPattern1 = terrain_tile.crop(width * 18, height * 17, width, height);
		snowPattern2 = terrain_tile.crop(width * 19, height * 17, width, height);
		snowPattern3 = terrain_tile.crop(width * 20, height * 17, width, height);
		
		snowWaterTopLeft = terrain_tile.crop(width * 21, height * 19, width, height);
		snowWaterTopMiddle = terrain_tile.crop(width * 22, height * 19, width, height);
		snowWaterTopRight = terrain_tile.crop(width * 23, height * 19, width, height);
		snowWaterMiddleLeft = terrain_tile.crop(width * 21, height * 20, width, height);
		snowWaterMiddleMiddle = terrain_tile.crop(width * 22, height * 20, width, height);
		snowWaterMiddleRight = terrain_tile.crop(width * 23, height * 20, width, height);
		snowWaterBottomLeft = terrain_tile.crop(width * 21, height * 21, width, height);
		snowWaterBottomMiddle = terrain_tile.crop(width * 22, height * 21, width, height);
		snowWaterBottomRight = terrain_tile.crop(width * 23, height * 21, width, height);
		snowWaterSmallTopLeft = terrain_tile.crop(width * 22, height * 17, width, height);
		snowWaterSmallTopRight = terrain_tile.crop(width * 23, height * 17, width, height);
		snowWaterSmallBottomLeft = terrain_tile.crop(width * 22, height * 18, width, height);
		snowWaterSmallBottomRight = terrain_tile.crop(width * 23, height * 18, width, height);
		
		sandTopLeft = terrain_tile.crop(width * 18, height * 8, width, height);
		sandTopMiddle = terrain_tile.crop(width * 19, height * 8, width, height);
		sandTopRight = terrain_tile.crop(width * 20, height * 8, width, height);
		sandMiddleLeft = terrain_tile.crop(width * 18, height * 9, width, height);
		sandMiddleMiddle = terrain_tile.crop(width * 19, height * 9, width, height);
		sandMiddleRight = terrain_tile.crop(width * 20, height * 9, width, height);
		sandBottomLeft = terrain_tile.crop(width * 18, height * 10, width, height);
		sandBottomMiddle = terrain_tile.crop(width * 19, height * 10, width, height);
		sandBottomRight = terrain_tile.crop(width * 20, height * 10, width, height);
		sandSmallTopLeft = terrain_tile.crop(width * 19, height * 6, width, height);
		sandSmallTopRight = terrain_tile.crop(width * 20, height * 6, width, height);
		sandSmallBottomLeft = terrain_tile.crop(width * 19, height * 7, width, height);
		sandSmallBottomRight = terrain_tile.crop(width * 20, height * 7, width, height);
		sandPattern1 = terrain_tile.crop(width * 18, height * 11, width, height);
		sandPattern2 = terrain_tile.crop(width * 19, height * 11, width, height);
		sandPattern3 = terrain_tile.crop(width * 20, height * 11, width, height);




		
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
		
		tree = texture_sheet.crop(width, height * 2, width, height);
		rock = texture_sheet.crop(0, height * 3, width, height);
		
		// House Spites
		
		greenHouseRoof = house_sheet.crop(width * 8, height, width, height);
		greenHouseWall = house_sheet.crop(width * 8, height * 3, width, height);
		greenHouseEntrance = house_sheet.crop(width * 10, height * 3, width, height);
		
	}
	
}
