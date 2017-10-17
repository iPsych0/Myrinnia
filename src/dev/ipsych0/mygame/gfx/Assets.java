package dev.ipsych0.mygame.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;
	
	// Fonts
	public static Font font14, font20, font32;	
	
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
								snowWaterSmallTopLeft, snowWaterSmallTopRight, snowWaterSmallBottomLeft, snowWaterSmallBottomRight, black, darkGrass, darkGrassPatch1, darkGrassPatch2,
								darkGrassPatch3, lightGrass, lightGrassPatch1, lightGrassPatch2, lightGrassPatch3, flowerPatch1, flowerPatch2, flowerPatch3;
	
	// Object images
	public static BufferedImage teleportShrine1, teleportShrine2, teleportShrinePillar1, teleportShrinePillar2, woodenRoofTopLeft, woodenRoofTopMiddle, woodenRoofTopRight, woodenRoofMiddleLeft,
	woodenRoofMiddleMiddle,woodenRoofMiddleRight, woodenRoofBottomLeft, woodenRoofBottomMiddle, woodenRoofBottomRight, greenRoofTopLeft, greenRoofTopMiddle, greenRoofTopRight, greenRoofMiddleLeft,
	greenRoofMiddleMiddle, greenRoofMiddleRight, greenRoofBottomLeft, greenRoofBottomMiddle, greenRoofBottomRight, wallLeft, wallRight, wallMiddle, entrance, floorTopLeft, floorTopMiddle,
	floorTopRight, floorMiddleLeft, floorMiddleMiddle, floorMiddleRight, floorBottomLeft, floorBottomMiddle, floorBottomRight, tree1TopLeft, tree1TopRight, tree1BottomLeft, tree1BottomRight,
	whiteWallTopLeft, whiteWallTopMiddle, whiteWallTopRight, whiteWallMiddleLeft, whiteWallMiddleMiddle, whiteWallMiddleRight, whiteWallBottomLeft, whiteWallBottomMiddle, whiteWallBottomRight,
	whiteWallWindowTopLeft, whiteWallWindowTopRight, whiteWallWindowMiddleLeft, whiteWallWindowMiddleRight, whiteWallWindowBottomLeft, whiteWallWindowBottomRight, brownColumnTop, brownColumnBottom,
	smallWoodenStairTop, smallWoodenStairBottom, stairTopLeft, stairTopMiddle, stairTopRight, stairBottomLeft, stairBottomMiddle, stairBottomRight, lightWallLeft, lightWallMiddle, lightWallRight,
	woodenDoorTop, woodenDoorBottom, palmTreeTop, palmTreeBottom, tree1BatchTopLeft, tree1BatchTopRight, tree1BatchBottomLeft, tree1BatchBottomRight, sandCliffTopLeft, sandCliffTopMiddle, sandCliffTopRight,
	sandCliffMiddleLeft, sandCliffMiddleMiddle, sandCliffMiddleRight, sandCliffBottomLeft, sandCliffBottomMiddle, sandCliffBottomRight, sandCliffLeft, sandCliffMiddle, sandCliffRight, sandCliffFootLeft,
	sandCliffFootMiddle, sandCliffFootRight, sandCliffCornerTopLeft, sandCliffCornerTopRight, sandCliffCornerBottomLeft, sandCliffCornerBottomRight, sandCliffCornerLowerLeft, sandCliffCornerLowerRight,
	sandCliffCornerLowestLeft, sandCliffCornerLowestRight, grassCliffTopLeft, grassCliffTopMiddle, grassCliffTopRight, grassCliffMiddleLeft, grassCliffMiddleMiddle, grassCliffMiddleRight,
	grassCliffBottomLeft, grassCliffBottomMiddle, grassCliffBottomRight, grassCliffLeft, grassCliffMiddle, grassCliffRight, grassCliffFootLeft, grassCliffFootMiddle, grassCliffFootRight,
	grassCliffCornerTopLeft, grassCliffCornerTopRight, grassCliffCornerBottomLeft, grassCliffCornerBottomRight, grassCliffCornerLowerLeft, grassCliffCornerLowerRight, grassCliffCornerLowestLeft,
	grassCliffCornerLowestRight, snowCliffTopLeft, snowCliffTopMiddle, snowCliffTopRight, snowCliffMiddleLeft, snowCliffMiddleMiddle, snowCliffMiddleRight, snowCliffBottomLeft, snowCliffBottomMiddle,
	snowCliffBottomRight, snowCliffLeft, snowCliffMiddle, snowCliffRight, snowCliffFootLeft, snowCliffFootMiddle, snowCliffFootRight, snowCliffCornerTopLeft, snowCliffCornerTopRight,
	snowCliffCornerBottomLeft, snowCliffCornerBottomRight, snowCliffCornerLowerLeft, snowCliffCornerLowerRight, snowCliffCornerLowestLeft, snowCliffCornerLowestRight, cliffEntranceTop, cliffEntranceBottom;
	
	
	// Ambiance images
	public static BufferedImage sparkleTile, redMushroom, blueMushroom, smallRedRocks;
	
	// Animated ambiance images
	public static BufferedImage[] sparkles;
								
	
	
	// Map item images (trees, rocks, etc)
	public static BufferedImage tree, rock;
	
	// Player animation images
	public static BufferedImage[] player_down, player_up, player_left, player_right;
	public static BufferedImage[] magicProjectile;
	
	// Player attack images
	public static BufferedImage[] player_attackingLeft, player_attackingRight, player_attackingDown, player_attackingUp;
	
	// Main menu background
	public static BufferedImage mainScreenBackground;
	
	// Main menu buttons
	public static BufferedImage[] button_new_game, button_continue, button_settings;
	
	// Item images
	public static BufferedImage wood, ore;
	public static BufferedImage[] coins;
	public static BufferedImage testSword;
	public static BufferedImage purpleSword;
	
	// Enemy images
	public static BufferedImage scorpion;
	
	// NPC images
	
	public static BufferedImage lorraine;
	
	// Green House images
	public static BufferedImage greenHouseRoof, greenHouseWall, greenHouseEntrance;
	
	// Minimap images
	public static BufferedImage swampLand;
	
	// Inventory UI
	public static BufferedImage invSlot, invScreen;
	
	// Equipment UI
	public static BufferedImage equipSlot, equipScreen, equipStats;
	
	// Chatwindow UI
	public static BufferedImage chatwindow, chatwindowTop;
	
	// Crafting UI
	public static BufferedImage craftWindow;
	
	// HP Overlay UI
	public static BufferedImage hpOverlay;
	
	public static BufferedImage[] whirlpool;
	
	public static BufferedImage fish;
	
	public static BufferedImage shopWindow;
	
	
	public static void init(){
		
		/*
		 * Fonts
		 */
		font14 = FontLoader.loadFont("res/fonts/optanebold.ttf", 14);
		font20 = FontLoader.loadFont("res/fonts/optanebold.ttf", 20);
		font32 = FontLoader.loadFont("res/fonts/optanebold.ttf", 32);
		
		
		/*
		 * Sprite Sheets
		 */
		SpriteSheet projectiles = new SpriteSheet(ImageLoader.loadImage("/textures/projectiles.png"));
		SpriteSheet swordSprites = new SpriteSheet(ImageLoader.loadImage("/textures/swordsprites.png"));
		SpriteSheet craftingWindow = new SpriteSheet(ImageLoader.loadImage("/textures/crafting.png"));
		SpriteSheet fishSheet = new SpriteSheet(ImageLoader.loadImage("/textures/fish.png"));
		SpriteSheet whirlPool = new SpriteSheet(ImageLoader.loadImage("/textures/whirlpool.png"));
		SpriteSheet HPOverlay = new SpriteSheet(ImageLoader.loadImage("/textures/hpoverlay.png"));  
		SpriteSheet chatWindowTop = new SpriteSheet(ImageLoader.loadImage("/textures/chatwindowtop.png"));  
		SpriteSheet chatWindow = new SpriteSheet(ImageLoader.loadImage("/textures/chatwindow.png")); 
		SpriteSheet equipmentStats = new SpriteSheet(ImageLoader.loadImage("/textures/equipstats.png"));
		SpriteSheet equipmentScreen = new SpriteSheet(ImageLoader.loadImage("/textures/equipmentscreen.png"));
		SpriteSheet inventoryScreen = new SpriteSheet(ImageLoader.loadImage("/textures/inventoryscreen.png"));
		SpriteSheet inventory = new SpriteSheet(ImageLoader.loadImage("/textures/invslot.png"));
		SpriteSheet main_screen = new SpriteSheet(ImageLoader.loadImage("/textures/dark_priest.png"));
		SpriteSheet swamp_land = new SpriteSheet(ImageLoader.loadImage("/textures/swampland.png"));
		SpriteSheet texture_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/textures.png"));
		SpriteSheet player_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/herosprites.png"));
		SpriteSheet menu_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/menu_sprites_new.png"));
		SpriteSheet item_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/itemsprites.png"));
		SpriteSheet enemy_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/enemysprites.png"));
		SpriteSheet house_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/housesprites.png"));
		SpriteSheet lorraine_sprites = new SpriteSheet(ImageLoader.loadImage("/textures/lorrainesprites.png"));
		SpriteSheet terrain_tile = new SpriteSheet(ImageLoader.loadImage("/textures/terrain.png"));
		SpriteSheet animated_terrain = new SpriteSheet(ImageLoader.loadImage("/textures/animated_terrain.png"));
		SpriteSheet trees_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/trees.png"));
		SpriteSheet object_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/object_sprites11.png"));
		SpriteSheet test_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/empty_sprite_sheet.png"));
		SpriteSheet shop_window = new SpriteSheet(ImageLoader.loadImage("/textures/shopwindow.png"));
		SpriteSheet city_sprites = new SpriteSheet(ImageLoader.loadImage("/textures/city_sprites.png"));
		SpriteSheet roofs3_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/roofs3.png"));
		SpriteSheet cliffs_sheet = new SpriteSheet(ImageLoader.loadImage("/textures/cliffs.png"));
		
		// http://www.online-image-editor.com/ to remove white background from sprites, save as .png!
		
		shopWindow = shop_window.crop(0, 0, 460, 313);
		
		magicProjectile = new BufferedImage[2];
		magicProjectile[0] = projectiles.crop(width * 3, height * 0, width, height);
		magicProjectile[1] = projectiles.crop(width * 0, height, width, height);
		
		purpleSword = swordSprites.crop(width, height * 0, width, height);
		
		fish = fishSheet.crop(0, 0, width, height);
		
		whirlpool = new BufferedImage[8];
		
		whirlpool[0] = whirlPool.crop(0, 0, width, height);
		whirlpool[1] = whirlPool.crop(width * 2, 0, width, height);
		whirlpool[2] = whirlPool.crop(width, 0, width, height);
		whirlpool[3] = whirlPool.crop(width * 3, 0, width, height);
		whirlpool[4] = whirlPool.crop(0, height, width, height);
		whirlpool[5] = whirlPool.crop(width * 2, height, width, height);
		whirlpool[6] = whirlPool.crop(width, height, width, height);
		whirlpool[7] = whirlPool.crop(width * 3, height, width, height);
		
		// Inventory sprites
		invSlot = inventory.crop(0, 0, 32, 32);
		invScreen = inventoryScreen.crop(0, 0, 132, 329);
		
		// Equipment sprites
		equipScreen = equipmentScreen.crop(0, 0, 132, 348);
		equipSlot = inventory.crop(0, 0, 32, 32);
		equipStats = equipmentStats.crop(0, 0, 112, 160);
		
		// Chat sprites
		chatwindow = chatWindow.crop(0, 0, 432, 112);
		chatwindowTop = chatWindowTop.crop(0, 0, 432, 20);
		
		// HP Overlay sprites
		hpOverlay = HPOverlay.crop(0, 0, 292, 96);
		
		// Crafting UI sprites
		craftWindow = craftingWindow.crop(0, 0, 242, 320);
		
		// Main menu background
		
		mainScreenBackground = main_screen.crop(0, 0, 960, 720);
		
		// Minimap images
		swampLand = swamp_land.crop(0, 0, 800, 800);
		
		// Menu sprites

		button_new_game = new BufferedImage[2];
		button_continue = new BufferedImage[2];
		button_settings = new BufferedImage[2];
		
		button_new_game[0] = menu_sheet.crop(width * 7, height * 3, width * 7, height * 3);
		button_new_game[1] = menu_sheet.crop(0, height * 3, width * 7, height * 3);
		
		button_continue[0] = menu_sheet.crop(width * 7, 0, width * 7, height * 3);
		button_continue[1] = menu_sheet.crop(0, 0, width * 7, height * 3);
		
		button_settings[0] = menu_sheet.crop(width * 7, height * 6, width * 7, height * 3);
		button_settings[1] = menu_sheet.crop(0, height * 6, width * 7, height * 3);
		
		// Item Sprites
		
		wood = item_sheet.crop(0, 0, width, height);
		ore = item_sheet.crop(0, height, width, height);
		coins = new BufferedImage[4];
		coins[0] = item_sheet.crop(width * 0, height * 2, width, height);
		coins[1] = item_sheet.crop(width * 0, height * 3, width, height);
		coins[2] = item_sheet.crop(width * 0, height * 4, width, height);
		coins[3] = item_sheet.crop(width * 0, height * 5, width, height);
		testSword = test_sheet.crop(width * 0, height * 0, width, height);
		
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
		woodenRoofTopLeft = city_sprites.crop(0, height * 23, width, height);
		woodenRoofTopMiddle = city_sprites.crop(width, height * 23, width, height);
		woodenRoofTopRight = city_sprites.crop(width * 2, height * 23, width, height);
		woodenRoofMiddleLeft = city_sprites.crop(0, height * 24, width, height);
		woodenRoofMiddleMiddle = city_sprites.crop(width, height * 24, width, height);
		woodenRoofMiddleRight = city_sprites.crop(width * 2, height * 24, width, height);
		woodenRoofBottomLeft = city_sprites.crop(0, height * 25, width, height);
		woodenRoofBottomMiddle = city_sprites.crop(width, height * 25, width, height);
		woodenRoofBottomRight = city_sprites.crop(width * 2, height * 25, width, height);
		
		greenRoofTopLeft = city_sprites.crop(width * 5, height * 23, width, height);
		greenRoofTopMiddle = city_sprites.crop(width * 6, height * 23, width, height);
		greenRoofTopRight = city_sprites.crop(width * 7, height * 23, width, height);
		greenRoofMiddleLeft = city_sprites.crop(width * 5, height * 24, width, height);
		greenRoofMiddleMiddle = city_sprites.crop(width * 6, height * 24, width, height);
		greenRoofMiddleRight = city_sprites.crop(width * 7, height * 24, width, height);
		greenRoofBottomLeft = city_sprites.crop(width * 5, height * 25, width, height);
		greenRoofBottomMiddle = city_sprites.crop(width * 6, height * 25, width, height);
		greenRoofBottomRight = city_sprites.crop(width * 7, height * 25, width, height);

		wallLeft = city_sprites.crop(0, height * 5, width, height);
		wallRight = city_sprites.crop(width * 2, height * 5, width, height);
		wallMiddle = city_sprites.crop(width, height * 5, width, height);
		entrance  = city_sprites.crop(width * 6, height * 40, width, height);
		
		lightWallLeft = city_sprites.crop(0, height * 4, width, height);
		lightWallMiddle = city_sprites.crop(width, height * 4, width, height);
		lightWallRight = city_sprites.crop(width * 2, height * 4, width, height);
		woodenDoorTop = city_sprites.crop(width * 6, height * 10, width, height);
		woodenDoorBottom = city_sprites.crop(width * 6, height * 11, width, height);
		
		floorTopLeft = city_sprites.crop(0, height * 9, width, height);
		floorTopMiddle = city_sprites.crop(width, height * 9, width, height);
		floorTopRight = city_sprites.crop(width * 2, height * 9, width, height);
		floorMiddleLeft = city_sprites.crop(0, height * 10, width, height);
		floorMiddleMiddle = city_sprites.crop(width, height * 10, width, height);
		floorMiddleRight = city_sprites.crop(width * 2, height * 10, width, height);
		floorBottomLeft = city_sprites.crop(0, height * 11, width, height);
		floorBottomMiddle = city_sprites.crop(width, height * 11, width, height);
		floorBottomRight = city_sprites.crop(width * 2, height * 11, width, height);
		
		brownColumnTop = city_sprites.crop(width * 5, height * 2, width, height);
		brownColumnBottom = city_sprites.crop(width * 5, height * 3, width, height);
		
		smallWoodenStairTop = city_sprites.crop(width * 7, height * 7, width, height);
		smallWoodenStairBottom = city_sprites.crop(width * 7, height * 8, width, height);
		
		stairTopLeft = city_sprites.crop(width * 4, height * 7, width, height);
		stairTopMiddle = city_sprites.crop(width * 5, height * 7, width, height);
		stairTopRight = city_sprites.crop(width * 6, height * 7, width, height);
		stairBottomLeft = city_sprites.crop(width * 4, height * 8, width, height);
		stairBottomMiddle = city_sprites.crop(width * 5, height * 8, width, height);
		stairBottomRight = city_sprites.crop(width * 6, height * 8, width, height);
		
		
		tree1TopLeft = trees_sheet.crop(0, height * 4, width, height);
		tree1TopRight = trees_sheet.crop(width, height * 4, width, height);
		tree1BottomLeft = trees_sheet.crop(0, height * 5, width, height);
		tree1BottomRight = trees_sheet.crop(width, height * 5, width, height);
		tree1BatchTopLeft = trees_sheet.crop(width * 2, height * 4, width, height);
		tree1BatchTopRight = trees_sheet.crop(width * 3, height * 4, width, height);
		tree1BatchBottomLeft = trees_sheet.crop(width * 2, height * 5, width, height);
		tree1BatchBottomRight = trees_sheet.crop(width * 3, height * 5, width, height);
		palmTreeTop = trees_sheet.crop(width, height * 12, width, height);
		palmTreeBottom = trees_sheet.crop(width, height * 13, width, height);
		
		whiteWallTopLeft = roofs3_sheet.crop(0, 0, width, height);
		whiteWallTopMiddle = roofs3_sheet.crop(width, 0, width, height);
		whiteWallTopRight = roofs3_sheet.crop(width * 2, 0, width, height);
		whiteWallMiddleLeft = roofs3_sheet.crop(0, height, width, height);
		whiteWallMiddleMiddle = roofs3_sheet.crop(width, height, width, height);
		whiteWallMiddleRight = roofs3_sheet.crop(width * 2, height, width, height);
		whiteWallBottomLeft = roofs3_sheet.crop(0, height * 2, width, height);
		whiteWallBottomMiddle = roofs3_sheet.crop(width, height * 2, width, height);
		whiteWallBottomRight = roofs3_sheet.crop(width * 2, height * 2, width, height);
		
		whiteWallWindowTopLeft = roofs3_sheet.crop(width * 4, 0, width, height);
		whiteWallWindowTopRight = roofs3_sheet.crop(width * 5, 0, width, height);
		whiteWallWindowMiddleLeft = roofs3_sheet.crop(width * 4, height, width, height);
		whiteWallWindowMiddleRight = roofs3_sheet.crop(width * 5, height, width, height);
		whiteWallWindowBottomLeft = roofs3_sheet.crop(width * 4, height * 2, width, height);
		whiteWallWindowBottomRight = roofs3_sheet.crop(width * 5, height * 2, width, height);


		
		/*
		 * Tile Sprites
		 */
		black = texture_sheet.crop(width * 3, height * 6, width, height);
		invisible = terrain_tile.crop(width * 7, height * 21, width, height);
		
		lightGrass = terrain_tile.crop(width, height * 9, width, height);
		lightGrassPatch1 = terrain_tile.crop(0, height * 11, width, height);
		lightGrassPatch2 = terrain_tile.crop(width, height * 11, width, height);
		lightGrassPatch3 = terrain_tile.crop(width * 2, height * 11, width, height);
		flowerPatch1 = terrain_tile.crop(width * 3, height * 11, width, height);
		flowerPatch2 = terrain_tile.crop(width * 4, height * 11, width, height);
		flowerPatch3 = terrain_tile.crop(width * 5, height * 11, width, height);
		
		darkGrass = terrain_tile.crop(width * 7, height * 9, width, height);
		darkGrassPatch1 = terrain_tile.crop(width * 8, height * 11, width, height);
		darkGrassPatch2 = terrain_tile.crop(width * 7, height * 11, width, height);
		darkGrassPatch3 = terrain_tile.crop(width * 6, height * 11, width, height);
		
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
		
		cliffEntranceTop = cliffs_sheet.crop(0, height * 5, width, height);
		cliffEntranceBottom = cliffs_sheet.crop(0, height * 6, width, height);
		sandCliffTopLeft = cliffs_sheet.crop(width * 5, 0, width, height);
		sandCliffTopMiddle = cliffs_sheet.crop(width * 6, 0, width, height);
		sandCliffTopRight = cliffs_sheet.crop(width * 7, 0, width, height);
		sandCliffMiddleLeft = cliffs_sheet.crop(width * 5, height, width, height);
		sandCliffMiddleMiddle = cliffs_sheet.crop(width * 6, height, width, height);
		sandCliffMiddleRight = cliffs_sheet.crop(width * 7, height, width, height);
		sandCliffBottomLeft = cliffs_sheet.crop(width * 5, height * 2, width, height);
		sandCliffBottomMiddle = cliffs_sheet.crop(width * 6, height * 2, width, height);
		sandCliffBottomRight = cliffs_sheet.crop(width * 7, height * 2, width, height);
		sandCliffLeft = cliffs_sheet.crop(width * 5, height * 3, width, height);
		sandCliffMiddle = cliffs_sheet.crop(width * 6, height * 3, width, height);
		sandCliffRight = cliffs_sheet.crop(width * 7, height * 3, width, height);
		sandCliffFootLeft = cliffs_sheet.crop(width * 5, height * 4, width, height);
		sandCliffFootMiddle = cliffs_sheet.crop(width * 6, height * 4, width, height);
		sandCliffFootRight = cliffs_sheet.crop(width * 7, height * 4, width, height);
		sandCliffCornerTopLeft = cliffs_sheet.crop(width * 8, 0, width, height);
		sandCliffCornerTopRight = cliffs_sheet.crop(width * 9, 0, width, height);
		sandCliffCornerBottomLeft = cliffs_sheet.crop(width * 8, height, width, height);
		sandCliffCornerBottomRight = cliffs_sheet.crop(width * 9, height, width, height);
		sandCliffCornerLowerLeft = cliffs_sheet.crop(width * 8, height * 2, width, height);
		sandCliffCornerLowerRight = cliffs_sheet.crop(width * 9, height * 2, width, height);
		sandCliffCornerLowestLeft = cliffs_sheet.crop(width * 8, height * 3, width, height);
		sandCliffCornerLowestRight = cliffs_sheet.crop(width * 9, height * 3, width, height);
		
		grassCliffTopLeft = cliffs_sheet.crop(width * 17, 0, width, height);
		grassCliffTopMiddle = cliffs_sheet.crop(width * 18, 0, width, height);
		grassCliffTopRight = cliffs_sheet.crop(width * 19, 0, width, height);
		grassCliffMiddleLeft = cliffs_sheet.crop(width * 17, height, width, height);
		grassCliffMiddleMiddle = cliffs_sheet.crop(width * 18, height, width, height);
		grassCliffMiddleRight = cliffs_sheet.crop(width * 19, height, width, height);
		grassCliffBottomLeft = cliffs_sheet.crop(width * 17, height * 2, width, height);
		grassCliffBottomMiddle = cliffs_sheet.crop(width * 18, height * 2, width, height);
		grassCliffBottomRight = cliffs_sheet.crop(width * 19, height * 2, width, height);
		grassCliffLeft = cliffs_sheet.crop(width * 17, height * 3, width, height);
		grassCliffMiddle = cliffs_sheet.crop(width * 18, height * 3, width, height);
		grassCliffRight = cliffs_sheet.crop(width * 19, height * 3, width, height);
		grassCliffFootLeft = cliffs_sheet.crop(width * 17, height * 4, width, height);
		grassCliffFootMiddle = cliffs_sheet.crop(width * 18, height * 4, width, height);
		grassCliffFootRight = cliffs_sheet.crop(width * 19, height * 4, width, height);
		grassCliffCornerTopLeft = cliffs_sheet.crop(width * 20, 0, width, height);
		grassCliffCornerTopRight = cliffs_sheet.crop(width * 21, 0, width, height);
		grassCliffCornerBottomLeft = cliffs_sheet.crop(width * 20, height, width, height);
		grassCliffCornerBottomRight = cliffs_sheet.crop(width * 21, height, width, height);
		grassCliffCornerLowerLeft = cliffs_sheet.crop(width * 20, height * 2, width, height);
		grassCliffCornerLowerRight = cliffs_sheet.crop(width * 21, height * 2, width, height);
		grassCliffCornerLowestLeft = cliffs_sheet.crop(width * 20, height * 3, width, height);
		grassCliffCornerLowestRight = cliffs_sheet.crop(width * 21, height * 3, width, height);
		
		snowCliffTopLeft = cliffs_sheet.crop(width * 29, 0, width, height);
		snowCliffTopMiddle = cliffs_sheet.crop(width * 30, 0, width, height);
		snowCliffTopRight = cliffs_sheet.crop(width * 31, 0, width, height);
		snowCliffMiddleLeft = cliffs_sheet.crop(width * 29, height, width, height);
		snowCliffMiddleMiddle = cliffs_sheet.crop(width * 30, height, width, height);
		snowCliffMiddleRight = cliffs_sheet.crop(width * 31, height, width, height);
		snowCliffBottomLeft = cliffs_sheet.crop(width * 29, height * 2, width, height);
		snowCliffBottomMiddle = cliffs_sheet.crop(width * 30, height * 2, width, height);
		snowCliffBottomRight = cliffs_sheet.crop(width * 31, height * 2, width, height);
		snowCliffLeft = cliffs_sheet.crop(width * 29, height * 3, width, height);
		snowCliffMiddle = cliffs_sheet.crop(width * 30, height * 3, width, height);
		snowCliffRight = cliffs_sheet.crop(width * 31, height * 3, width, height);
		snowCliffFootLeft = cliffs_sheet.crop(width * 29, height * 4, width, height);
		snowCliffFootMiddle = cliffs_sheet.crop(width * 30, height * 4, width, height);
		snowCliffFootRight = cliffs_sheet.crop(width * 31, height * 4, width, height);
		snowCliffCornerTopLeft = cliffs_sheet.crop(width * 32, 0, width, height);
		snowCliffCornerTopRight = cliffs_sheet.crop(width * 33, 0, width, height);
		snowCliffCornerBottomLeft = cliffs_sheet.crop(width * 32, height, width, height);
		snowCliffCornerBottomRight = cliffs_sheet.crop(width * 33, height, width, height);
		snowCliffCornerLowerLeft = cliffs_sheet.crop(width * 32, height * 2, width, height);
		snowCliffCornerLowerRight = cliffs_sheet.crop(width * 33, height * 2, width, height);
		snowCliffCornerLowestLeft = cliffs_sheet.crop(width * 32, height * 3, width, height);
		snowCliffCornerLowestRight = cliffs_sheet.crop(width * 33, height * 3, width, height);

		
		
		// Ambiance tiles
		sparkleTile = terrain_tile.crop(width * 16, height * 18, width, height);
		redMushroom = trees_sheet.crop(width * 7, height * 6, width, height);
		blueMushroom = trees_sheet.crop(width * 7, height * 5, width, height);
		smallRedRocks = terrain_tile.crop(width * 31, height * 18, width, height);
		
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
