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
								snowWaterSmallTopLeft, snowWaterSmallTopRight, snowWaterSmallBottomLeft, snowWaterSmallBottomRight, black, darkGrass, darkGrassTopLeft,
								darkGrassTopMiddle, darkGrassTopRight, darkGrassMiddleLeft, darkGrassMiddleRight, darkGrassBottomLeft, darkGrassBottomMiddle, darkGrassBottomRight,
								darkGrassPatch1, darkGrassPatch2, darkGrassPatch3, darkGrassSmallTopLeft, darkGrassSmallTopRight, darkGrassSmallBottomLeft, darkGrassSmallBottomRight,
								lightGrass, lightGrassPatch1, lightGrassPatch2, lightGrassPatch3, flowerPatch1, flowerPatch2, flowerPatch3;
	
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
	snowCliffCornerBottomLeft, snowCliffCornerBottomRight, snowCliffCornerLowerLeft, snowCliffCornerLowerRight, snowCliffCornerLowestLeft, snowCliffCornerLowestRight, cliffEntranceTop, cliffEntranceBottom,
	caveCliffTopLeft, caveCliffTopMiddle, caveCliffTopRight, caveCliffMiddleLeft, caveCliffMiddleMiddle, caveCliffMiddleRight, caveCliffBottomLeft, caveCliffBottomMiddle, caveCliffBottomRight,
	caveCliffLeft, caveCliffMiddle, caveCliffRight, caveCliffFootLeft, caveCliffFootMiddle, caveCliffFootRight, caveCliffCornerTopLeft, caveCliffCornerTopRight, caveCliffCornerBottomLeft, 
	caveCliffCornerBottomRight, caveCliffCornerLowerLeft, caveCliffCornerLowerRight, caveCliffCornerLowestLeft, caveCliffCornerLowestRight, pot1, basket1, basketApples, table1,
	stoolTop1, sandPit, waterBucket, fireplaceTop, fireplaceBottom, stoolBottom1, bookcaseTopLeft, bookcaseTopRight, bookcaseBottomLeft, bookcaseBottomRight, bed1Top, bed1Bottom,
	drawer1, smallBookcaseTop, smallBookcaseBottom, wardrobe1Top, wardrobe1Bottom, breadShelfTop, breadShelfBottom, bottleShelfTop, bottleShelfBottom, plateShelfTop, plateShelfBottom,
	painting1, painting2, painting3, painting4, worldMap1Left, worldMap1Right, bigPainting1Left, bigPainting1Right, wallNote, crateApples, cratePotatoes, crateFish, crateGroceries, crate1,
	stackedCrateTop, stackedCrateBottom, emptyBucket, emptyCrate, emptyBarrel, barrel1, signInn, signArmour, signWeapons, signWorkshop, signBank, signShop, woodenBridgeHorizontal,
	woodenBridgeVertical, bed2Top, bed2Bottom, curtainLeftTop, curtainLeftBottom, curtainMiddleTop, curtainMiddleBottom, curtainRightTop, curtainRightBottom, pineTreeTopLeft,
	pineTreeTopRight, pineTreeBottomLeft, pineTreeBottomRight, pineTreeBatchTopLeft, pineTreeBatchTopRight, pineTreeBatchBottomLeft, pineTreeBatchBottomRight, logBridgeHorizontal, logBridgeVertical,
	purpleFlower1, pinkFlower1, pinkFlower2, greyFlower1, redFlower1, yellowFlower1, plantPot1, greenMushroom, magicTreeLefter1, magicTreeLeft1, magicTreeRight1, magicTreeRighter1,
	magicTreeLefter2, magicTreeLeft2, magicTreeRight2, magicTreeRighter2, magicTreeLefter3, magicTreeLeft3, magicTreeRight3, magicTreeRighter3, magicTreeLefter4, magicTreeLeft4,
	magicTreeRight4, magicTreeRighter4, magicTreeLeft5, magicTreeRight5, dirtHole, boatTop1, boatTop2, boatTop3, boatTop4, boatTop5, boatTop6, boatTop7, boatTop8, boatTop9, boatTop10, boatTop11, boatTop12,
	boatTop13, boatTop14, boatTop15, boatTop16, boatTop17, boatTop18, boatTop19, boatTop20, boatStairsTopLeft, boatStairsBottomLeft, boatStairs, boatStairsMiddleTop, boatStairsMiddleBottom, boatStairsTopRight,
	boatStairsBottomRight, boatRailingMiddleRight, boatRailingMiddleLeft, boatRailingLeft, boatRailingRight, boatRailingBottomLeft, boatRailingBottomRight, boatRailingBottom, boatRailingTop, boatBack1, boatBack2, boatBack3, boatBack4, boatBack5, boatBack6,
	boatBackMiddleMiddle, boatBackMiddleBottom, boatMiddle;

	
	
	// Ambiance images
	public static BufferedImage sparkleTile, redMushroom, blueMushroom, smallRedRocks;
	
	// Animated ambiance images
	public static BufferedImage[] sparkles;
	public static BufferedImage[] campfire;
								
	
	
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
	public static BufferedImage[] mainMenuButton;
	
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
	public static BufferedImage earringSlot, mainhandSlot, glovesSlot, ringSlot1, helmSlot, bodySlot, legsSlot, bootsSlot, amuletSlot, offhandSlot, capeSlot, ringSlot2;
	public static BufferedImage[] equipmentPlaceHolders;
	
	// Chatwindow UI
	public static BufferedImage chatwindow, chatwindowTop;
	
	// Crafting UI
	public static BufferedImage craftWindow;
	public static BufferedImage undiscovered;
	
	// HP Overlay UI
	public static BufferedImage hpOverlay;
	
	public static BufferedImage[] whirlpool;
	
	public static BufferedImage fish;
	
	public static BufferedImage shopWindow;
	
	public static BufferedImage controlsScreen;
	
	
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
		SpriteSheet ui_sheet = new SpriteSheet("/textures/ui-items.png");
		SpriteSheet projectiles = new SpriteSheet("/textures/projectiles.png");
		SpriteSheet equipSlots = new SpriteSheet("/textures/equipment_placeholders.png");
		SpriteSheet controls_sheet = new SpriteSheet("/textures/ControlsScreen.png");
		/*
		 * Make skilling sheet for this
		 */
		SpriteSheet whirlPool = new SpriteSheet("/textures/whirlpool.png");
		SpriteSheet main_screen = new SpriteSheet("/textures/dark_priest.png");

		/*
		 * Change this one
		 */
		SpriteSheet texture_sheet = new SpriteSheet("/textures/textures.png");
		SpriteSheet player_sheet = new SpriteSheet("/textures/herosprites.png");
		/*
		 * Add items to this
		 */
		SpriteSheet item_sheet = new SpriteSheet("/textures/itemsprites.png");
		
		SpriteSheet enemy_sheet = new SpriteSheet("/textures/enemysprites.png");
		/*
		 * Crop Lorraine out
		 */
		SpriteSheet lorraine_sprites = new SpriteSheet("/textures/lorrainesprites.png");
		
		SpriteSheet terrain_tile = new SpriteSheet("/textures/terrain.png");
		/*
		 * Add animated tiles
		 */
		SpriteSheet animated_terrain = new SpriteSheet("/textures/animated_terrain.png");
		
		
		/*
		 * All Tiled Sprites
		 */
		SpriteSheet trees_sheet = new SpriteSheet("/textures/trees.png");
		SpriteSheet object_sheet = new SpriteSheet("/textures/object_sprites11.png");
		SpriteSheet shop_window = new SpriteSheet("/textures/shopwindow.png");
		SpriteSheet city_sprites = new SpriteSheet("/textures/city_sprites.png");
		SpriteSheet roofs3_sheet = new SpriteSheet("/textures/roofs3.png");
		SpriteSheet cliffs_sheet = new SpriteSheet("/textures/cliffs.png");
		SpriteSheet objects17 = new SpriteSheet("/textures/object_sprites17.png");
		SpriteSheet furniture1 = new SpriteSheet("/textures/furniture1.png");
		SpriteSheet objects5 = new SpriteSheet("/textures/object_sprites5.png");
		SpriteSheet objects3 = new SpriteSheet("/textures/object_sprites3.png");
		SpriteSheet ship_sheet = new SpriteSheet("/textures/ship.png");
		
		// http://www.online-image-editor.com/ to remove white background from sprites, save as .png!
		
//		earringSlot, mainhandSlot, glovesSlot, ringSlot1, helmSlot, bodySlot, legsSlot, bootsSlot, amuletSlot, offhandSlot, capeSlot, ringSlot2;
		
		equipmentPlaceHolders = new BufferedImage[12];
		
		earringSlot = equipSlots.tileCrop(0, 0, width, height);
		mainhandSlot = equipSlots.tileCrop(0, height, width, height);
		glovesSlot = equipSlots.tileCrop(0, height * 2, width, height);
		ringSlot1 = equipSlots.tileCrop(0, height * 3, width, height);
		helmSlot = equipSlots.tileCrop(width, 0, width, height);
		bodySlot = equipSlots.tileCrop(width, height, width, height);
		legsSlot = equipSlots.tileCrop(width, height * 2, width, height);
		bootsSlot = equipSlots.tileCrop(width, height * 3, width, height);
		amuletSlot = equipSlots.tileCrop(width * 2, 0, width, height);
		offhandSlot = equipSlots.tileCrop(width * 2, height, width, height);
		capeSlot = equipSlots.tileCrop(width * 2, height * 2, width, height);
		ringSlot2 = equipSlots.tileCrop(width * 2, height * 3, width, height);
		
		equipmentPlaceHolders[0] = earringSlot;
		equipmentPlaceHolders[1] = mainhandSlot;
		equipmentPlaceHolders[2] = glovesSlot;
		equipmentPlaceHolders[3] = ringSlot1;
		equipmentPlaceHolders[4] = helmSlot;
		equipmentPlaceHolders[5] = bodySlot;
		equipmentPlaceHolders[6] = legsSlot;
		equipmentPlaceHolders[7] = bootsSlot;
		equipmentPlaceHolders[8] = amuletSlot;
		equipmentPlaceHolders[9] = offhandSlot;
		equipmentPlaceHolders[10] = capeSlot;
		equipmentPlaceHolders[11] = ringSlot2;
		
		shopWindow = shop_window.tileCrop(0, 0, 460, 313);
		
		controlsScreen = controls_sheet.tileCrop(0, 0, 460, 313);
		
		magicProjectile = new BufferedImage[3];
		magicProjectile[0] = projectiles.tileCrop(width * 9, height * 0, width, height);
		magicProjectile[1] = projectiles.tileCrop(width * 10, height * 0, width, height);
		magicProjectile[2] = projectiles.tileCrop(width * 11, height * 0, width, height);
		
		fish = ui_sheet.tileCrop(width * 2, 0, width, height);
		
		// Inventory sprites
		invSlot = ui_sheet.tileCrop(width, 0, 32, 32);
		invScreen = ui_sheet.tileCrop(0, height * 9, 132, 329);
		
		// Equipment sprites
		equipScreen = ui_sheet.tileCrop(width * 13, height * 9, 132, 348);
		equipSlot = ui_sheet.tileCrop(width, 0, 32, 32);
		equipStats = ui_sheet.tileCrop(0, height * 20, 112, 160);
		
		// Chat sprites
		chatwindow = ui_sheet.tileCrop(0, height * 4, 432, 112);
		chatwindowTop = ui_sheet.tileCrop(0, height * 8, 432, 20);
		
		// HP Overlay sprites
		hpOverlay = ui_sheet.tileCrop(0, height, 288, 96);
		
		// Crafting UI sprites
		craftWindow = ui_sheet.tileCrop(width * 5, height * 9, 242, 320);
		undiscovered = ui_sheet.tileCrop(0, 0, width, height);
		
		// Main menu background
		
		mainScreenBackground = main_screen.tileCrop(0, 0, 960, 720);
		
		// Minimap images

		
		// Menu sprites

		mainMenuButton = new BufferedImage[2];
		mainMenuButton[0] = ui_sheet.tileCrop(width * 9, 0, width * 7, height * 3);
		mainMenuButton[1] = ui_sheet.tileCrop(width * 17, 0, width * 7, height * 3);
		
		// Item Sprites
		
		wood = item_sheet.tileCrop(0, 0, width, height);
		ore = item_sheet.tileCrop(0, height, width, height);
		
		coins = new BufferedImage[4];
		coins[0] = item_sheet.tileCrop(width * 0, height * 2, width, height);
		coins[1] = item_sheet.tileCrop(width * 0, height * 3, width, height);
		coins[2] = item_sheet.tileCrop(width * 0, height * 4, width, height);
		coins[3] = item_sheet.tileCrop(width * 0, height * 5, width, height);
		testSword = item_sheet.tileCrop(width, height * 0, width, height);
		purpleSword = item_sheet.tileCrop(width * 2, height * 0, width, height);
		
		// Object Sprites
		campfire = new BufferedImage[5];
		campfire[0] = objects17.tileCrop(0, height * 15, width, height);
		campfire[1] = objects17.tileCrop(width, height * 15, width, height);
		campfire[2] = objects17.tileCrop(width * 2, height * 15, width, height);
		campfire[3] = objects17.tileCrop(width * 3, height * 15, width, height);
		campfire[4] = objects17.tileCrop(width * 4, height * 15, width, height);
		
		teleportShrine1 = object_sheet.tileCrop(width * 7, height * 10, width, height);
		teleportShrine2 = object_sheet.tileCrop(width * 7, height * 11, width, height);
		teleportShrinePillar1 = object_sheet.tileCrop(width * 9, width * 10, width, height);
		teleportShrinePillar2 = object_sheet.tileCrop(width * 9, width * 11, width, height);
		
		// Enemy Sprites
		
		scorpion = enemy_sheet.tileCrop(0, 0, width, height);
		
		// NPC Sprites
		
		lorraine = lorraine_sprites.tileCrop(width * 7, 0, width, height);
		
		// Player Sprites
		
		player_attackingLeft = new BufferedImage[2];
		player_attackingRight = new BufferedImage[2];
		player_attackingDown = new BufferedImage[2];
		player_attackingUp = new BufferedImage[2];
		
		player_down = new BufferedImage[3];
		player_up = new BufferedImage[3];
		player_left = new BufferedImage[3];
		player_right = new BufferedImage[3];
		
		player_attackingLeft[0] = player_sheet.tileCrop(0, height * 4, width, height);
		player_attackingLeft[1] = player_sheet.tileCrop(width, height * 4, width, height);
		
		player_attackingRight[0] = player_sheet.tileCrop(0, height * 5, width, height);
		player_attackingRight[1] = player_sheet.tileCrop(width, height * 5, width, height);
		
		player_attackingDown[0] = player_sheet.tileCrop(0, height * 6, width, height);
		player_attackingDown[1] = player_sheet.tileCrop(width, height * 6, width, height);
		
		player_attackingUp[0] = player_sheet.tileCrop(0, height * 7, width, height);
		player_attackingUp[1] = player_sheet.tileCrop(width, height * 7, width, height);
		
		player_down[0] = player_sheet.tileCrop(0, 0, width, height);
		player_down[1] = player_sheet.tileCrop(width, 0, width, height);
		player_down[2] = player_sheet.tileCrop(width * 2, 0, width, height);
		
		player_up[0] = player_sheet.tileCrop(width * 6, height * 3, width, height);
		player_up[1] = player_sheet.tileCrop(width * 7, height * 3, width, height);
		player_up[2] = player_sheet.tileCrop(width * 8, height * 3, width, height);
		
		player_left[0] = player_sheet.tileCrop(0, height, width, height);
		player_left[1] = player_sheet.tileCrop(width, height, width, height);
		player_left[2] = player_sheet.tileCrop(width * 2, height, width, height);
		
		player_right[0] = player_sheet.tileCrop(0, height * 2, width, height);
		player_right[1] = player_sheet.tileCrop(width, height * 2, width, height);
		player_right[2] = player_sheet.tileCrop(width * 2, height * 2, width, height);
		
		// Object sprites
		woodenRoofTopLeft = city_sprites.tileCrop(0, height * 23, width, height);
		woodenRoofTopMiddle = city_sprites.tileCrop(width, height * 23, width, height);
		woodenRoofTopRight = city_sprites.tileCrop(width * 2, height * 23, width, height);
		woodenRoofMiddleLeft = city_sprites.tileCrop(0, height * 24, width, height);
		woodenRoofMiddleMiddle = city_sprites.tileCrop(width, height * 24, width, height);
		woodenRoofMiddleRight = city_sprites.tileCrop(width * 2, height * 24, width, height);
		woodenRoofBottomLeft = city_sprites.tileCrop(0, height * 25, width, height);
		woodenRoofBottomMiddle = city_sprites.tileCrop(width, height * 25, width, height);
		woodenRoofBottomRight = city_sprites.tileCrop(width * 2, height * 25, width, height);
		
		greenRoofTopLeft = city_sprites.tileCrop(width * 5, height * 23, width, height);
		greenRoofTopMiddle = city_sprites.tileCrop(width * 6, height * 23, width, height);
		greenRoofTopRight = city_sprites.tileCrop(width * 7, height * 23, width, height);
		greenRoofMiddleLeft = city_sprites.tileCrop(width * 5, height * 24, width, height);
		greenRoofMiddleMiddle = city_sprites.tileCrop(width * 6, height * 24, width, height);
		greenRoofMiddleRight = city_sprites.tileCrop(width * 7, height * 24, width, height);
		greenRoofBottomLeft = city_sprites.tileCrop(width * 5, height * 25, width, height);
		greenRoofBottomMiddle = city_sprites.tileCrop(width * 6, height * 25, width, height);
		greenRoofBottomRight = city_sprites.tileCrop(width * 7, height * 25, width, height);

		wallLeft = city_sprites.tileCrop(0, height * 5, width, height);
		wallRight = city_sprites.tileCrop(width * 2, height * 5, width, height);
		wallMiddle = city_sprites.tileCrop(width, height * 5, width, height);
		entrance  = city_sprites.tileCrop(width * 6, height * 40, width, height);
		
		lightWallLeft = city_sprites.tileCrop(0, height * 4, width, height);
		lightWallMiddle = city_sprites.tileCrop(width, height * 4, width, height);
		lightWallRight = city_sprites.tileCrop(width * 2, height * 4, width, height);
		woodenDoorTop = city_sprites.tileCrop(width * 6, height * 10, width, height);
		woodenDoorBottom = city_sprites.tileCrop(width * 6, height * 11, width, height);
		
		floorTopLeft = city_sprites.tileCrop(0, height * 9, width, height);
		floorTopMiddle = city_sprites.tileCrop(width, height * 9, width, height);
		floorTopRight = city_sprites.tileCrop(width * 2, height * 9, width, height);
		floorMiddleLeft = city_sprites.tileCrop(0, height * 10, width, height);
		floorMiddleMiddle = city_sprites.tileCrop(width, height * 10, width, height);
		floorMiddleRight = city_sprites.tileCrop(width * 2, height * 10, width, height);
		floorBottomLeft = city_sprites.tileCrop(0, height * 11, width, height);
		floorBottomMiddle = city_sprites.tileCrop(width, height * 11, width, height);
		floorBottomRight = city_sprites.tileCrop(width * 2, height * 11, width, height);
		
		brownColumnTop = city_sprites.tileCrop(width * 5, height * 2, width, height);
		brownColumnBottom = city_sprites.tileCrop(width * 5, height * 3, width, height);
		
		smallWoodenStairTop = city_sprites.tileCrop(width * 7, height * 7, width, height);
		smallWoodenStairBottom = city_sprites.tileCrop(width * 7, height * 8, width, height);
		
		stairTopLeft = city_sprites.tileCrop(width * 4, height * 7, width, height);
		stairTopMiddle = city_sprites.tileCrop(width * 5, height * 7, width, height);
		stairTopRight = city_sprites.tileCrop(width * 6, height * 7, width, height);
		stairBottomLeft = city_sprites.tileCrop(width * 4, height * 8, width, height);
		stairBottomMiddle = city_sprites.tileCrop(width * 5, height * 8, width, height);
		stairBottomRight = city_sprites.tileCrop(width * 6, height * 8, width, height);
		
		
		tree1TopLeft = trees_sheet.tileCrop(0, height * 4, width, height);
		tree1TopRight = trees_sheet.tileCrop(width, height * 4, width, height);
		tree1BottomLeft = trees_sheet.tileCrop(0, height * 5, width, height);
		tree1BottomRight = trees_sheet.tileCrop(width, height * 5, width, height);
		tree1BatchTopLeft = trees_sheet.tileCrop(width * 2, height * 4, width, height);
		tree1BatchTopRight = trees_sheet.tileCrop(width * 3, height * 4, width, height);
		tree1BatchBottomLeft = trees_sheet.tileCrop(width * 2, height * 5, width, height);
		tree1BatchBottomRight = trees_sheet.tileCrop(width * 3, height * 5, width, height);
		palmTreeTop = trees_sheet.tileCrop(width, height * 12, width, height);
		palmTreeBottom = trees_sheet.tileCrop(width, height * 13, width, height);
		
		pineTreeTopLeft = trees_sheet.tileCrop(0, 0, width, height);
		pineTreeTopRight = trees_sheet.tileCrop(width, 0, width, height);
		pineTreeBottomLeft = trees_sheet.tileCrop(0, height, width, height);
		pineTreeBottomRight = trees_sheet.tileCrop(width, height, width, height);
		pineTreeBatchTopLeft = trees_sheet.tileCrop(width * 2, 0, width, height);
		pineTreeBatchTopRight = trees_sheet.tileCrop(width * 3, 0, width, height);
		pineTreeBatchBottomLeft = trees_sheet.tileCrop(width * 2, height, width, height);
		pineTreeBatchBottomRight = trees_sheet.tileCrop(width * 3, height, width, height);
		
//		pineTreeTopLeft = objects18.tileCrop(width * 4, 0, width, height);
//		pineTreeTopRight = objects18.tileCrop(width * 5, 0, width, height);
//		pineTreeBottomLeft = objects18.tileCrop(width * 4, height, width, height);
//		pineTreeBottomRight = objects18.tileCrop(width * 5, height, width, height);
//		pineTreeBatchTopLeft = objects18.tileCrop(width * 6, 0, width, height);
//		pineTreeBatchTopRight = objects18.tileCrop(width * 7, 0, width, height);
//		pineTreeBatchBottomLeft = objects18.tileCrop(width * 6, height, width, height);
//		pineTreeBatchBottomRight = objects18.tileCrop(width * 7, height, width, height);
		
		magicTreeLefter1 = trees_sheet.tileCrop(width * 7, height * 8, width, height);
		magicTreeLeft1 = trees_sheet.tileCrop(width * 8, height * 8, width, height);
		magicTreeRight1 = trees_sheet.tileCrop(width * 9, height * 8, width, height);
		magicTreeRighter1 = trees_sheet.tileCrop(width * 10, height * 8, width, height);
		magicTreeLefter2 = trees_sheet.tileCrop(width * 7, height * 9, width, height);
		magicTreeLeft2 = trees_sheet.tileCrop(width * 8, height * 9, width, height);
		magicTreeRight2 = trees_sheet.tileCrop(width * 9, height * 9, width, height);
		magicTreeRighter2 = trees_sheet.tileCrop(width * 10, height * 9, width, height);
		magicTreeLefter3 = trees_sheet.tileCrop(width * 7, height * 10, width, height);
		magicTreeLeft3 = trees_sheet.tileCrop(width * 8, height * 10, width, height);
		magicTreeRight3 = trees_sheet.tileCrop(width * 9, height * 10, width, height);
		magicTreeRighter3 = trees_sheet.tileCrop(width * 10, height * 10, width, height);
		magicTreeLefter4 = trees_sheet.tileCrop(width * 7, height * 11, width, height);
		magicTreeLeft4 = trees_sheet.tileCrop(width * 8, height * 11, width, height);
		magicTreeRight4 = trees_sheet.tileCrop(width * 9, height * 11, width, height);
		magicTreeRighter4 = trees_sheet.tileCrop(width * 10, height * 11, width, height);
		magicTreeLeft5 = trees_sheet.tileCrop(width * 8, height * 12, width, height);
		magicTreeRight5 = trees_sheet.tileCrop(width * 9, height * 12, width, height);

		
		
		whiteWallTopLeft = roofs3_sheet.tileCrop(0, 0, width, height);
		whiteWallTopMiddle = roofs3_sheet.tileCrop(width, 0, width, height);
		whiteWallTopRight = roofs3_sheet.tileCrop(width * 2, 0, width, height);
		whiteWallMiddleLeft = roofs3_sheet.tileCrop(0, height, width, height);
		whiteWallMiddleMiddle = roofs3_sheet.tileCrop(width, height, width, height);
		whiteWallMiddleRight = roofs3_sheet.tileCrop(width * 2, height, width, height);
		whiteWallBottomLeft = roofs3_sheet.tileCrop(0, height * 2, width, height);
		whiteWallBottomMiddle = roofs3_sheet.tileCrop(width, height * 2, width, height);
		whiteWallBottomRight = roofs3_sheet.tileCrop(width * 2, height * 2, width, height);
		
		whiteWallWindowTopLeft = roofs3_sheet.tileCrop(width * 4, 0, width, height);
		whiteWallWindowTopRight = roofs3_sheet.tileCrop(width * 5, 0, width, height);
		whiteWallWindowMiddleLeft = roofs3_sheet.tileCrop(width * 4, height, width, height);
		whiteWallWindowMiddleRight = roofs3_sheet.tileCrop(width * 5, height, width, height);
		whiteWallWindowBottomLeft = roofs3_sheet.tileCrop(width * 4, height * 2, width, height);
		whiteWallWindowBottomRight = roofs3_sheet.tileCrop(width * 5, height * 2, width, height);
		
		
		// Furniture
		pot1 = objects17.tileCrop(width * 10, height * 8, width, height);
		basket1 = objects17.tileCrop(width * 14, height * 4, width, height);
		basketApples = objects17.tileCrop(width * 15, height * 4, width, height);
		waterBucket = objects17.tileCrop(width * 13, height * 5, width, height);
		table1 = furniture1.tileCrop(width * 13, height * 2, width, height);
		stoolTop1 = furniture1.tileCrop(width * 10, height, width, height);
		stoolBottom1 = furniture1.tileCrop(width * 9, height, width, height);
		sandPit = objects17.tileCrop(width * 5, height * 13, width, height);
		fireplaceTop = furniture1.tileCrop(width * 11, height * 7, width, height);
		fireplaceBottom = furniture1.tileCrop(width * 11, height * 8, width, height);
		bookcaseTopLeft = furniture1.tileCrop(width * 6, height * 12, width, height);
		bookcaseTopRight = furniture1.tileCrop(width * 7, height * 12, width, height);
		bookcaseBottomLeft = furniture1.tileCrop(width * 6, height * 13, width, height);
		bookcaseBottomRight = furniture1.tileCrop(width * 7, height * 13, width, height);
		bed1Top = furniture1.tileCrop(width * 4, height * 14, width, height);
		bed1Bottom = furniture1.tileCrop(width * 4, height * 15, width, height);
		drawer1 = furniture1.tileCrop(0, height * 10, width, height);
		smallBookcaseTop = furniture1.tileCrop(width * 5, height * 12, width, height);
		smallBookcaseBottom = furniture1.tileCrop(width * 5, height * 13, width, height);
		wardrobe1Top = furniture1.tileCrop(width * 2, height * 10, width, height);
		wardrobe1Bottom = furniture1.tileCrop(width * 2, height * 11, width, height);
		breadShelfTop = furniture1.tileCrop(0, height * 12, width, height);
		breadShelfBottom = furniture1.tileCrop(0, height * 13, width, height);
		bottleShelfTop = furniture1.tileCrop(width * 4, height * 12, width, height);
		bottleShelfBottom = furniture1.tileCrop(width * 4, height * 13, width, height);
		plateShelfTop = furniture1.tileCrop(width * 4, height * 10, width, height);
		plateShelfBottom = furniture1.tileCrop(width * 4, height * 11, width, height);
		painting1 = furniture1.tileCrop(width * 12, height * 11, width, height);
		painting2 = furniture1.tileCrop(width * 13, height * 11, width, height);
		painting3 = furniture1.tileCrop(width * 14, height * 11, width, height);
		painting4 = furniture1.tileCrop(width * 15, height * 11, width, height);
		worldMap1Left = furniture1.tileCrop(width * 14, height * 12, width, height);
		worldMap1Right = furniture1.tileCrop(width * 15, height * 12, width, height);
		bigPainting1Left = furniture1.tileCrop(width * 14, height * 13, width, height);
		bigPainting1Right = furniture1.tileCrop(width * 15, height * 13, width, height);
		wallNote = furniture1.tileCrop(width * 12, height * 12, width, height);
		
		crateApples = objects17.tileCrop(width * 12, height * 3, width, height);
		cratePotatoes = objects17.tileCrop(width * 10, height * 4, width, height);
		crateFish= objects17.tileCrop(width * 11, height * 4, width, height);
		crateGroceries = objects17.tileCrop(width * 12, height * 4, width, height);
		stackedCrateBottom = objects17.tileCrop(width * 10, height * 2, width, height);
		stackedCrateTop = objects17.tileCrop(width * 10, height, width, height);
		crate1 = objects17.tileCrop(width * 10, height * 3, width, height);
		emptyCrate = objects17.tileCrop(width * 11, height * 3, width, height);
		emptyBucket = objects17.tileCrop(width * 12, height * 5, width, height);
		emptyBarrel = objects17.tileCrop(width * 14, height * 2, width, height);
		barrel1 = objects17.tileCrop(width * 14, height, width, height);
		bed2Top = furniture1.tileCrop(0, height * 14, width, height);
		bed2Bottom = furniture1.tileCrop(0, height * 15, width, height);
		curtainLeftTop = furniture1.tileCrop(width, height * 3, width, height);
		curtainLeftBottom = furniture1.tileCrop(width, height * 4, width, height);
		curtainMiddleTop = furniture1.tileCrop(0, height * 3, width, height);
		curtainMiddleBottom = furniture1.tileCrop(0, height * 4, width, height);
		curtainRightTop = furniture1.tileCrop(width * 2, height * 3, width, height);
		curtainRightBottom = furniture1.tileCrop(width * 2, height * 4, width, height);

		signInn = objects5.tileCrop(width * 6, 0, width, height);
		signArmour = objects5.tileCrop(width * 3, 0, width, height);
		signWeapons = objects5.tileCrop(width * 2, 0, width, height);
		signWorkshop = objects5.tileCrop(width * 6, height, width, height);
		signBank = objects5.tileCrop(width * 4, 0, width, height);
		signShop = objects5.tileCrop(width * 3, height, width, height);
		woodenBridgeHorizontal = objects5.tileCrop(width * 3, height * 2, width, height);
		woodenBridgeVertical = objects5.tileCrop(width * 2, height * 2, width, height);
		logBridgeHorizontal = objects5.tileCrop(0, height * 2, width, height);
		logBridgeVertical = objects5.tileCrop(width, height * 2, width, height);
		dirtHole = objects5.tileCrop(width, height * 12, width, height);
		
		/*
		 * Ship sprites
		 */
		boatTop1 = ship_sheet.tileCrop(width * 2, height * 0, width, height);
		boatTop2 = ship_sheet.tileCrop(width * 3, height * 0, width, height);
		boatTop3 = ship_sheet.tileCrop(width * 4, height * 0, width, height);
		boatTop4 = ship_sheet.tileCrop(width * 1, height * 1, width, height);
		boatTop5 = ship_sheet.tileCrop(width * 2, height * 1, width, height);
		boatTop6 = ship_sheet.tileCrop(width * 3, height * 1, width, height);
		boatTop7 = ship_sheet.tileCrop(width * 4, height * 1, width, height);
		boatTop8 = ship_sheet.tileCrop(width * 5, height * 1, width, height);
		boatTop9 = ship_sheet.tileCrop(width * 1, height * 2, width, height);
		boatTop10 = ship_sheet.tileCrop(width * 2, height * 2, width, height);
		boatTop11 = ship_sheet.tileCrop(width * 4, height * 2, width, height);
		boatTop12 = ship_sheet.tileCrop(width * 5, height * 2, width, height);
		boatTop13 = ship_sheet.tileCrop(width * 0, height * 3, width, height);
		boatTop14 = ship_sheet.tileCrop(width * 1, height * 3, width, height);
		boatTop15 = ship_sheet.tileCrop(width * 5, height * 3, width, height);
		boatTop16 = ship_sheet.tileCrop(width * 6, height * 3, width, height);
		boatTop17 = ship_sheet.tileCrop(width * 0, height * 4, width, height);
		boatTop18 = ship_sheet.tileCrop(width * 1, height * 4, width, height);
		boatTop19 = ship_sheet.tileCrop(width * 5, height * 4, width, height);
		boatTop20 = ship_sheet.tileCrop(width * 6, height * 4, width, height);
		boatStairsTopLeft = ship_sheet.tileCrop(width * 7, height * 5, width, height);
		boatStairsBottomLeft = ship_sheet.tileCrop(width * 7, height * 6, width, height);
		boatStairsTopRight = ship_sheet.tileCrop(width * 8, height * 5, width, height);
		boatStairsBottomRight = ship_sheet.tileCrop(width * 8, height * 6, width, height);
		boatStairs = ship_sheet.tileCrop(width * 7, height * 3, width, height);
		boatStairsMiddleTop = ship_sheet.tileCrop(width * 9, height * 3, width, height);
		boatStairsMiddleBottom = ship_sheet.tileCrop(width * 9, height * 4, width, height);
		boatRailingMiddleLeft = ship_sheet.tileCrop(width * 0, height * 9, width, height);
		boatRailingMiddleRight = ship_sheet.tileCrop(width * 2, height * 9, width, height);
		boatRailingLeft = ship_sheet.tileCrop(width * 0, height * 10, width, height);
		boatRailingRight = ship_sheet.tileCrop(width * 2, height * 10, width, height);
		boatRailingBottomLeft = ship_sheet.tileCrop(width * 0, height * 11, width, height);
		boatRailingBottomRight = ship_sheet.tileCrop(width * 2, height * 11, width, height);
		boatRailingBottom = ship_sheet.tileCrop(width * 1, height * 11, width, height);
		boatRailingTop = ship_sheet.tileCrop(width * 1, height * 10, width, height);
		boatBack1 = ship_sheet.tileCrop(width * 0, height * 12, width, height);
		boatBack2 = ship_sheet.tileCrop(width * 0, height * 13, width, height);
		boatBack3 = ship_sheet.tileCrop(width * 0, height * 14, width, height);
		boatBack4 = ship_sheet.tileCrop(width * 2, height * 12, width, height);
		boatBack5 = ship_sheet.tileCrop(width * 2, height * 13, width, height);
		boatBack6 = ship_sheet.tileCrop(width * 2, height * 14, width, height);
		boatBackMiddleMiddle = ship_sheet.tileCrop(width * 1, height * 13, width, height);
		boatBackMiddleBottom = ship_sheet.tileCrop(width * 1, height * 14, width, height);
		boatMiddle = ship_sheet.tileCrop(width * 6, height * 0, width, height);

		
//		boatTop1, boatTop2, boatTop3, boatTop4, boatTop5, boatTop6, boatTop7, boatTop8, boatTop9, boatTop10, boatTop11, boatTop12,
//		boatTop13, boatTop14, boatTop15, boatTop16, boatTop17, boatTop18, boatTop19, boatTop20, boatStairsTopLeft, boatStairsBottomLeft, boatStairs, boatStairsMiddleTop, boatStairsMiddleBottom, boatStairsTopRight,
//		boatStairsBottomRight, boatRailingMiddleLeft, boatRailingMiddleRight, boatRailingLeft, boatRailingRight, boatRailingBottomLeft, boatRailingBottomRight, boatRailingBottom, boatBack1, boatBack2, boatBack3, boatBack4, boatBack5, boatBack6,
//		boatBackMiddleMiddle, boatBackMiddleBottom, boatMiddle;
		

		
		/*
		 * Tile Sprites
		 */
		black = texture_sheet.tileCrop(width * 3, height * 6, width, height);
		invisible = terrain_tile.tileCrop(width * 7, height * 21, width, height);
		
		lightGrass = terrain_tile.tileCrop(width, height * 9, width, height);
		lightGrassPatch1 = terrain_tile.tileCrop(0, height * 11, width, height);
		lightGrassPatch2 = terrain_tile.tileCrop(width, height * 11, width, height);
		lightGrassPatch3 = terrain_tile.tileCrop(width * 2, height * 11, width, height);
		flowerPatch1 = terrain_tile.tileCrop(width * 3, height * 11, width, height);
		flowerPatch2 = terrain_tile.tileCrop(width * 4, height * 11, width, height);
		flowerPatch3 = terrain_tile.tileCrop(width * 5, height * 11, width, height);
		greyFlower1 = roofs3_sheet.tileCrop(width * 4, height * 8, width, height);
		yellowFlower1 = roofs3_sheet.tileCrop(width * 4, height * 9, width, height);
		purpleFlower1 = roofs3_sheet.tileCrop(width * 4, height * 10, width, height);
		redFlower1 = roofs3_sheet.tileCrop(width * 4, height * 11, width, height);
		plantPot1 = roofs3_sheet.tileCrop(width * 4, height * 12, width, height);
		pinkFlower1 = trees_sheet.tileCrop(width * 13, height * 8, width, height);
		greenMushroom = trees_sheet.tileCrop(width * 14, height * 8, width, height);
		pinkFlower2 = trees_sheet.tileCrop(width * 14, height * 9, width, height);

		
		darkGrass = terrain_tile.tileCrop(width * 7, height * 9, width, height);
		darkGrassTopLeft = terrain_tile.tileCrop(width * 6, height * 8, width, height);
		darkGrassTopMiddle = terrain_tile.tileCrop(width * 7, height * 8, width, height);
		darkGrassTopRight = terrain_tile.tileCrop(width * 8, height * 8, width, height);
		darkGrassMiddleLeft = terrain_tile.tileCrop(width * 6, height * 9, width, height);
		darkGrassMiddleRight = terrain_tile.tileCrop(width * 8, height * 9, width, height);
		darkGrassBottomLeft = terrain_tile.tileCrop(width * 6, height * 10, width, height);
		darkGrassBottomMiddle = terrain_tile.tileCrop(width * 7, height * 10, width, height);
		darkGrassBottomRight = terrain_tile.tileCrop(width * 8, height * 10, width, height);
		darkGrassSmallTopLeft = terrain_tile.tileCrop(width * 7, height * 6, width, height);
		darkGrassSmallTopRight = terrain_tile.tileCrop(width * 8, height * 6, width, height);
		darkGrassSmallBottomLeft = terrain_tile.tileCrop(width * 7, height * 7, width, height);
		darkGrassSmallBottomRight = terrain_tile.tileCrop(width * 8, height * 7, width, height);
		darkGrassPatch1 = terrain_tile.tileCrop(width * 8, height * 11, width, height);
		darkGrassPatch2 = terrain_tile.tileCrop(width * 7, height * 11, width, height);
		darkGrassPatch3 = terrain_tile.tileCrop(width * 6, height * 11, width, height);
		
		waterSmallTopLeft = terrain_tile.tileCrop(width * 28, 0, width, height);
		waterSmallTopRight = terrain_tile.tileCrop(width * 29, 0, width, height);
		waterSmallBottomLeft = terrain_tile.tileCrop(width * 28, height, width, height);
		waterSmallBottomRight = terrain_tile.tileCrop(width * 29, height, width, height);
		waterTopLeft = terrain_tile.tileCrop(width * 27, height * 2, width, height);
		waterTopMiddle = terrain_tile.tileCrop(width * 28, height * 2, width, height);
		waterTopRight = terrain_tile.tileCrop(width * 29, height * 2, width, height);
		waterMiddleLeft = terrain_tile.tileCrop(width * 27, height * 3, width, height);
		waterMiddleMiddle = terrain_tile.tileCrop(width * 28, height * 3, width, height);
		waterMiddleRight = terrain_tile.tileCrop(width * 29, height * 3, width, height);
		waterBottomLeft = terrain_tile.tileCrop(width * 27, height * 4, width, height);
		waterBottomMiddle = terrain_tile.tileCrop(width * 28, height * 4, width, height);
		waterBottomRight = terrain_tile.tileCrop(width * 29, height * 4, width, height);
		waterFlow1 = terrain_tile.tileCrop(width * 27, height * 5, width, height);
		waterFlow2 = terrain_tile.tileCrop(width * 28, height * 5, width, height);
		waterFlow3 = terrain_tile.tileCrop(width * 29, height * 5, width, height);
		
		lavaSmallTopLeft = terrain_tile.tileCrop(width * 16, height * 0, width, height);
		lavaSmallTopRight = terrain_tile.tileCrop(width * 17, height * 0, width, height);
		lavaSmallBottomLeft = terrain_tile.tileCrop(width * 16, height * 1, width, height);
		lavaSmallBottomRight = terrain_tile.tileCrop(width * 17, height * 1, width, height);
		lavaTopLeft = terrain_tile.tileCrop(width * 15, height * 2, width, height);
		lavaTopMiddle = terrain_tile.tileCrop(width * 16, height * 2, width, height);
		lavaTopRight = terrain_tile.tileCrop(width * 17, height * 2, width, height);
		lavaMiddleLeft = terrain_tile.tileCrop(width * 15, height * 3, width, height);
		lavaMiddleMiddle = terrain_tile.tileCrop(width * 16, height * 3, width, height);
		lavaMiddleRight = terrain_tile.tileCrop(width * 17, height * 3, width, height);
		lavaBottomLeft = terrain_tile.tileCrop(width * 15, height * 4, width, height);
		lavaBottomMiddle = terrain_tile.tileCrop(width * 16, height * 4, width, height);
		lavaBottomRight = terrain_tile.tileCrop(width * 17, height * 4, width, height);
		lavaFlow1 = terrain_tile.tileCrop(width * 15, height * 5, width, height);
		lavaFlow2 = terrain_tile.tileCrop(width * 16, height * 5, width, height);
		lavaFlow3 = terrain_tile.tileCrop(width * 17, height * 5, width, height);
		
		sandWaterTopLeft = terrain_tile.tileCrop(width * 21, height * 8, width, height);
		sandWaterTopMiddle = terrain_tile.tileCrop(width * 22, height * 8, width, height);
		sandWaterTopRight = terrain_tile.tileCrop(width * 23, height * 8, width, height);
		sandWaterMiddleLeft = terrain_tile.tileCrop(width * 21, height * 9, width, height);
		sandWaterMiddleMiddle = terrain_tile.tileCrop(width * 22, height * 9, width, height);
		sandWaterMiddleRight = terrain_tile.tileCrop(width * 23, height * 9, width, height);
		sandWaterBottomLeft = terrain_tile.tileCrop(width * 21, height * 10, width, height);
		sandWaterBottomMiddle = terrain_tile.tileCrop(width * 22, height * 10, width, height);
		sandWaterBottomRight = terrain_tile.tileCrop(width * 23, height * 10, width, height);
		sandWaterSmallTopLeft = terrain_tile.tileCrop(width * 22, height * 6, width, height);
		sandWaterSmallTopRight = terrain_tile.tileCrop(width * 23, height * 6, width, height);
		sandWaterSmallBottomLeft = terrain_tile.tileCrop(width * 22, height * 7, width, height);
		sandWaterSmallBottomRight = terrain_tile.tileCrop(width * 23, height * 7, width, height);
		
		holeSmallTopLeft = terrain_tile.tileCrop(width * 19, height * 0, width, height);
		holeSmallTopRight = terrain_tile.tileCrop(width * 20, height * 0, width, height);
		holeSmallBottomLeft = terrain_tile.tileCrop(width * 19, height * 1, width, height);
		holeSmallBottomRight = terrain_tile.tileCrop(width * 20, height * 1, width, height);
		holeTopLeft = terrain_tile.tileCrop(width * 18, height * 2, width, height);
		holeTopMiddle = terrain_tile.tileCrop(width * 19, height * 2, width, height);
		holeTopRight = terrain_tile.tileCrop(width * 20, height * 2, width, height);
		holeMiddleLeft = terrain_tile.tileCrop(width * 18, height * 3, width, height);
		holeMiddleMiddle = terrain_tile.tileCrop(width * 19, height * 3, width, height);
		holeMiddleRight = terrain_tile.tileCrop(width * 20, height * 3, width, height);
		holeBottomLeft = terrain_tile.tileCrop(width * 18, height * 4, width, height);
		holeBottomMiddle = terrain_tile.tileCrop(width * 19, height * 4, width, height);
		holeBottomRight = terrain_tile.tileCrop(width * 20, height * 4, width, height);
		
		transDirtTopLeft = terrain_tile.tileCrop(width * 0, height * 2, width, height);
		transDirtTopMiddle = terrain_tile.tileCrop(width * 1, height * 2, width, height);
		transDirtTopRight = terrain_tile.tileCrop(width * 2, height * 2, width, height);
		transDirtMiddleLeft = terrain_tile.tileCrop(width * 0, height * 3, width, height);
		transDirtMiddleMiddle = terrain_tile.tileCrop(width * 1, height * 3, width, height);
		transDirtMiddleRight = terrain_tile.tileCrop(width * 2, height * 3, width, height);
		transDirtBottomLeft = terrain_tile.tileCrop(width * 0, height * 4, width, height);
		transDirtBottomMiddle = terrain_tile.tileCrop(width * 1, height * 4, width, height);
		transDirtBottomRight = terrain_tile.tileCrop(width * 2, height * 4, width, height);
		transDirtSmallTopLeft = terrain_tile.tileCrop(width * 1, height * 0, width, height);
		transDirtSmallTopRight = terrain_tile.tileCrop(width * 2, height * 0, width, height);
		transDirtSmallBottomLeft = terrain_tile.tileCrop(width * 1, height * 1, width, height);
		transDirtSmallBottomRight = terrain_tile.tileCrop(width * 2, height * 1, width, height);
		
		redDirtTopLeft = terrain_tile.tileCrop(width * 6, height * 2, width, height);
		redDirtTopMiddle = terrain_tile.tileCrop(width * 7, height * 2, width, height);
		redDirtTopRight = terrain_tile.tileCrop(width * 8, height * 2, width, height);
		redDirtMiddleLeft = terrain_tile.tileCrop(width * 6, height * 3, width, height);
		redDirtMiddleMiddle = terrain_tile.tileCrop(width * 7, height * 3, width, height);
		redDirtMiddleRight = terrain_tile.tileCrop(width * 8, height * 3, width, height);
		redDirtBottomLeft = terrain_tile.tileCrop(width * 6, height * 4, width, height);
		redDirtBottomMiddle = terrain_tile.tileCrop(width * 7, height * 4, width, height);
		redDirtBottomRight = terrain_tile.tileCrop(width * 8, height * 4, width, height);
		redDirtEffect1 = terrain_tile.tileCrop(width * 6, height * 5, width, height);
		redDirtEffect2 = terrain_tile.tileCrop(width * 7, height * 5, width, height);
		redDirtEffect3 = terrain_tile.tileCrop(width * 8, height * 5, width, height);
		redDirtSmallTopLeft = terrain_tile.tileCrop(width * 7, height * 0, width, height);
		redDirtSmallTopRight = terrain_tile.tileCrop(width * 8, height * 0, width, height);
		redDirtSmallBottomLeft = terrain_tile.tileCrop(width * 7, height * 1, width, height);
		redDirtSmallBottomRight = terrain_tile.tileCrop(width * 8, height * 1, width, height);

		
		greyDirtTopLeft = terrain_tile.tileCrop(width * 9, height * 2, width, height);
		greyDirtTopMiddle = terrain_tile.tileCrop(width * 10, height * 2, width, height);
		greyDirtTopRight = terrain_tile.tileCrop(width * 11, height * 2, width, height);
		greyDirtMiddleLeft = terrain_tile.tileCrop(width * 9, height * 3, width, height);
		greyDirtMiddleMiddle = terrain_tile.tileCrop(width * 10, height * 3, width, height);
		greyDirtMiddleRight = terrain_tile.tileCrop(width * 11, height * 3, width, height);
		greyDirtBottomLeft = terrain_tile.tileCrop(width * 9, height * 4, width, height);
		greyDirtBottomMiddle = terrain_tile.tileCrop(width * 10, height * 4, width, height);
		greyDirtBottomRight = terrain_tile.tileCrop(width * 11, height * 4, width, height);
		greyDirtEffect1 = terrain_tile.tileCrop(width * 9, height * 5, width, height);
		greyDirtEffect2 = terrain_tile.tileCrop(width * 10, height * 5, width, height);
		greyDirtEffect3 = terrain_tile.tileCrop(width * 11, height * 5, width, height);
		greyDirtSmallTopLeft = terrain_tile.tileCrop(width * 10, height * 0, width, height);
		greyDirtSmallTopRight = terrain_tile.tileCrop(width * 11, height * 0, width, height);
		greyDirtSmallBottomLeft = terrain_tile.tileCrop(width * 10, height * 1, width, height);
		greyDirtSmallBottomRight = terrain_tile.tileCrop(width * 11, height * 1, width, height);

		
		darkDirtTopLeft = terrain_tile.tileCrop(width * 3, height * 2, width, height);
		darkDirtTopMiddle = terrain_tile.tileCrop(width * 4, height * 2, width, height);
		darkDirtTopRight = terrain_tile.tileCrop(width * 5, height * 2, width, height);
		darkDirtMiddleLeft = terrain_tile.tileCrop(width * 3, height * 3, width, height);
		darkDirtMiddleMiddle = terrain_tile.tileCrop(width * 4, height * 3, width, height);
		darkDirtMiddleRight = terrain_tile.tileCrop(width * 5, height * 3, width, height);
		darkDirtBottomLeft = terrain_tile.tileCrop(width * 3, height * 4, width, height);
		darkDirtBottomMiddle = terrain_tile.tileCrop(width * 4, height * 4, width, height);
		darkDirtBottomRight = terrain_tile.tileCrop(width * 5, height * 4, width, height);
		darkDirtEffect1 = terrain_tile.tileCrop(width * 3, height * 5, width, height);
		darkDirtEffect2 = terrain_tile.tileCrop(width * 4, height * 5, width, height);
		darkDirtEffect3 = terrain_tile.tileCrop(width * 5, height * 5, width, height);
		darkDirtSmallTopLeft = terrain_tile.tileCrop(width * 4, height * 0, width, height);
		darkDirtSmallTopRight = terrain_tile.tileCrop(width * 5, height * 0, width, height);
		darkDirtSmallBottomLeft = terrain_tile.tileCrop(width * 4, height * 1, width, height);
		darkDirtSmallBottomRight = terrain_tile.tileCrop(width * 5, height * 1, width, height);
		
		snowTopLeft = terrain_tile.tileCrop(width * 18, height * 14, width, height);
		snowTopMiddle = terrain_tile.tileCrop(width * 19, height * 14, width, height);
		snowTopRight = terrain_tile.tileCrop(width * 20, height * 14, width, height);
		snowMiddleLeft = terrain_tile.tileCrop(width * 18, height * 15, width, height);
		snowMiddleMiddle = terrain_tile.tileCrop(width * 19, height * 15, width, height);
		snowMiddleRight = terrain_tile.tileCrop(width * 20, height * 15, width, height);
		snowBottomLeft = terrain_tile.tileCrop(width * 18, height * 16, width, height);
		snowBottomMiddle = terrain_tile.tileCrop(width * 19, height * 16, width, height);
		snowBottomRight = terrain_tile.tileCrop(width * 20, height * 16, width, height);
		snowSmallTopLeft = terrain_tile.tileCrop(width * 19, height * 12, width, height);
		snowSmallTopRight = terrain_tile.tileCrop(width * 20, height * 12, width, height);
		snowSmallBottomLeft = terrain_tile.tileCrop(width * 19, height * 13, width, height);
		snowSmallBottomRight = terrain_tile.tileCrop(width * 20, height * 13, width, height);
		snowPattern1 = terrain_tile.tileCrop(width * 18, height * 17, width, height);
		snowPattern2 = terrain_tile.tileCrop(width * 19, height * 17, width, height);
		snowPattern3 = terrain_tile.tileCrop(width * 20, height * 17, width, height);
		
		snowWaterTopLeft = terrain_tile.tileCrop(width * 21, height * 19, width, height);
		snowWaterTopMiddle = terrain_tile.tileCrop(width * 22, height * 19, width, height);
		snowWaterTopRight = terrain_tile.tileCrop(width * 23, height * 19, width, height);
		snowWaterMiddleLeft = terrain_tile.tileCrop(width * 21, height * 20, width, height);
		snowWaterMiddleMiddle = terrain_tile.tileCrop(width * 22, height * 20, width, height);
		snowWaterMiddleRight = terrain_tile.tileCrop(width * 23, height * 20, width, height);
		snowWaterBottomLeft = terrain_tile.tileCrop(width * 21, height * 21, width, height);
		snowWaterBottomMiddle = terrain_tile.tileCrop(width * 22, height * 21, width, height);
		snowWaterBottomRight = terrain_tile.tileCrop(width * 23, height * 21, width, height);
		snowWaterSmallTopLeft = terrain_tile.tileCrop(width * 22, height * 17, width, height);
		snowWaterSmallTopRight = terrain_tile.tileCrop(width * 23, height * 17, width, height);
		snowWaterSmallBottomLeft = terrain_tile.tileCrop(width * 22, height * 18, width, height);
		snowWaterSmallBottomRight = terrain_tile.tileCrop(width * 23, height * 18, width, height);
		
		sandTopLeft = terrain_tile.tileCrop(width * 18, height * 8, width, height);
		sandTopMiddle = terrain_tile.tileCrop(width * 19, height * 8, width, height);
		sandTopRight = terrain_tile.tileCrop(width * 20, height * 8, width, height);
		sandMiddleLeft = terrain_tile.tileCrop(width * 18, height * 9, width, height);
		sandMiddleMiddle = terrain_tile.tileCrop(width * 19, height * 9, width, height);
		sandMiddleRight = terrain_tile.tileCrop(width * 20, height * 9, width, height);
		sandBottomLeft = terrain_tile.tileCrop(width * 18, height * 10, width, height);
		sandBottomMiddle = terrain_tile.tileCrop(width * 19, height * 10, width, height);
		sandBottomRight = terrain_tile.tileCrop(width * 20, height * 10, width, height);
		sandSmallTopLeft = terrain_tile.tileCrop(width * 19, height * 6, width, height);
		sandSmallTopRight = terrain_tile.tileCrop(width * 20, height * 6, width, height);
		sandSmallBottomLeft = terrain_tile.tileCrop(width * 19, height * 7, width, height);
		sandSmallBottomRight = terrain_tile.tileCrop(width * 20, height * 7, width, height);
		sandPattern1 = terrain_tile.tileCrop(width * 18, height * 11, width, height);
		sandPattern2 = terrain_tile.tileCrop(width * 19, height * 11, width, height);
		sandPattern3 = terrain_tile.tileCrop(width * 20, height * 11, width, height);
		
		cliffEntranceTop = cliffs_sheet.tileCrop(0, height * 5, width, height);
		cliffEntranceBottom = cliffs_sheet.tileCrop(0, height * 6, width, height);
		sandCliffTopLeft = cliffs_sheet.tileCrop(width * 5, 0, width, height);
		sandCliffTopMiddle = cliffs_sheet.tileCrop(width * 6, 0, width, height);
		sandCliffTopRight = cliffs_sheet.tileCrop(width * 7, 0, width, height);
		sandCliffMiddleLeft = cliffs_sheet.tileCrop(width * 5, height, width, height);
		sandCliffMiddleMiddle = cliffs_sheet.tileCrop(width * 6, height, width, height);
		sandCliffMiddleRight = cliffs_sheet.tileCrop(width * 7, height, width, height);
		sandCliffBottomLeft = cliffs_sheet.tileCrop(width * 5, height * 2, width, height);
		sandCliffBottomMiddle = cliffs_sheet.tileCrop(width * 6, height * 2, width, height);
		sandCliffBottomRight = cliffs_sheet.tileCrop(width * 7, height * 2, width, height);
		sandCliffLeft = cliffs_sheet.tileCrop(width * 5, height * 3, width, height);
		sandCliffMiddle = cliffs_sheet.tileCrop(width * 6, height * 3, width, height);
		sandCliffRight = cliffs_sheet.tileCrop(width * 7, height * 3, width, height);
		sandCliffFootLeft = cliffs_sheet.tileCrop(width * 5, height * 4, width, height);
		sandCliffFootMiddle = cliffs_sheet.tileCrop(width * 6, height * 4, width, height);
		sandCliffFootRight = cliffs_sheet.tileCrop(width * 7, height * 4, width, height);
		sandCliffCornerTopLeft = cliffs_sheet.tileCrop(width * 8, 0, width, height);
		sandCliffCornerTopRight = cliffs_sheet.tileCrop(width * 9, 0, width, height);
		sandCliffCornerBottomLeft = cliffs_sheet.tileCrop(width * 8, height, width, height);
		sandCliffCornerBottomRight = cliffs_sheet.tileCrop(width * 9, height, width, height);
		sandCliffCornerLowerLeft = cliffs_sheet.tileCrop(width * 8, height * 2, width, height);
		sandCliffCornerLowerRight = cliffs_sheet.tileCrop(width * 9, height * 2, width, height);
		sandCliffCornerLowestLeft = cliffs_sheet.tileCrop(width * 8, height * 3, width, height);
		sandCliffCornerLowestRight = cliffs_sheet.tileCrop(width * 9, height * 3, width, height);
		
		grassCliffTopLeft = cliffs_sheet.tileCrop(width * 17, 0, width, height);
		grassCliffTopMiddle = cliffs_sheet.tileCrop(width * 18, 0, width, height);
		grassCliffTopRight = cliffs_sheet.tileCrop(width * 19, 0, width, height);
		grassCliffMiddleLeft = cliffs_sheet.tileCrop(width * 17, height, width, height);
		grassCliffMiddleMiddle = cliffs_sheet.tileCrop(width * 18, height, width, height);
		grassCliffMiddleRight = cliffs_sheet.tileCrop(width * 19, height, width, height);
		grassCliffBottomLeft = cliffs_sheet.tileCrop(width * 17, height * 2, width, height);
		grassCliffBottomMiddle = cliffs_sheet.tileCrop(width * 18, height * 2, width, height);
		grassCliffBottomRight = cliffs_sheet.tileCrop(width * 19, height * 2, width, height);
		grassCliffLeft = cliffs_sheet.tileCrop(width * 17, height * 3, width, height);
		grassCliffMiddle = cliffs_sheet.tileCrop(width * 18, height * 3, width, height);
		grassCliffRight = cliffs_sheet.tileCrop(width * 19, height * 3, width, height);
		grassCliffFootLeft = cliffs_sheet.tileCrop(width * 17, height * 4, width, height);
		grassCliffFootMiddle = cliffs_sheet.tileCrop(width * 18, height * 4, width, height);
		grassCliffFootRight = cliffs_sheet.tileCrop(width * 19, height * 4, width, height);
		grassCliffCornerTopLeft = cliffs_sheet.tileCrop(width * 20, 0, width, height);
		grassCliffCornerTopRight = cliffs_sheet.tileCrop(width * 21, 0, width, height);
		grassCliffCornerBottomLeft = cliffs_sheet.tileCrop(width * 20, height, width, height);
		grassCliffCornerBottomRight = cliffs_sheet.tileCrop(width * 21, height, width, height);
		grassCliffCornerLowerLeft = cliffs_sheet.tileCrop(width * 20, height * 2, width, height);
		grassCliffCornerLowerRight = cliffs_sheet.tileCrop(width * 21, height * 2, width, height);
		grassCliffCornerLowestLeft = cliffs_sheet.tileCrop(width * 20, height * 3, width, height);
		grassCliffCornerLowestRight = cliffs_sheet.tileCrop(width * 21, height * 3, width, height);
		
		snowCliffTopLeft = cliffs_sheet.tileCrop(width * 29, 0, width, height);
		snowCliffTopMiddle = cliffs_sheet.tileCrop(width * 30, 0, width, height);
		snowCliffTopRight = cliffs_sheet.tileCrop(width * 31, 0, width, height);
		snowCliffMiddleLeft = cliffs_sheet.tileCrop(width * 29, height, width, height);
		snowCliffMiddleMiddle = cliffs_sheet.tileCrop(width * 30, height, width, height);
		snowCliffMiddleRight = cliffs_sheet.tileCrop(width * 31, height, width, height);
		snowCliffBottomLeft = cliffs_sheet.tileCrop(width * 29, height * 2, width, height);
		snowCliffBottomMiddle = cliffs_sheet.tileCrop(width * 30, height * 2, width, height);
		snowCliffBottomRight = cliffs_sheet.tileCrop(width * 31, height * 2, width, height);
		snowCliffLeft = cliffs_sheet.tileCrop(width * 29, height * 3, width, height);
		snowCliffMiddle = cliffs_sheet.tileCrop(width * 30, height * 3, width, height);
		snowCliffRight = cliffs_sheet.tileCrop(width * 31, height * 3, width, height);
		snowCliffFootLeft = cliffs_sheet.tileCrop(width * 29, height * 4, width, height);
		snowCliffFootMiddle = cliffs_sheet.tileCrop(width * 30, height * 4, width, height);
		snowCliffFootRight = cliffs_sheet.tileCrop(width * 31, height * 4, width, height);
		snowCliffCornerTopLeft = cliffs_sheet.tileCrop(width * 32, 0, width, height);
		snowCliffCornerTopRight = cliffs_sheet.tileCrop(width * 33, 0, width, height);
		snowCliffCornerBottomLeft = cliffs_sheet.tileCrop(width * 32, height, width, height);
		snowCliffCornerBottomRight = cliffs_sheet.tileCrop(width * 33, height, width, height);
		snowCliffCornerLowerLeft = cliffs_sheet.tileCrop(width * 32, height * 2, width, height);
		snowCliffCornerLowerRight = cliffs_sheet.tileCrop(width * 33, height * 2, width, height);
		snowCliffCornerLowestLeft = cliffs_sheet.tileCrop(width * 32, height * 3, width, height);
		snowCliffCornerLowestRight = cliffs_sheet.tileCrop(width * 33, height * 3, width, height);
		
		caveCliffTopLeft = terrain_tile.tileCrop(width * 24, height * 10, width, height);
		caveCliffTopMiddle = terrain_tile.tileCrop(width * 25, height * 10, width, height);
		caveCliffTopRight = terrain_tile.tileCrop(width * 26, height * 10, width, height);
		caveCliffMiddleLeft = terrain_tile.tileCrop(width * 24, height * 11, width, height);
		caveCliffMiddleMiddle = terrain_tile.tileCrop(width * 25, height * 11, width, height);
		caveCliffMiddleRight = terrain_tile.tileCrop(width * 26, height * 11, width, height);
		caveCliffBottomLeft = terrain_tile.tileCrop(width * 24, height * 12, width, height);
		caveCliffBottomMiddle = terrain_tile.tileCrop(width * 25, height * 12, width, height);
		caveCliffBottomRight = terrain_tile.tileCrop(width * 26, height * 12, width, height);
		caveCliffLeft = terrain_tile.tileCrop(width * 24, height * 13, width, height);
		caveCliffMiddle = terrain_tile.tileCrop(width * 25, height * 13, width, height);
		caveCliffRight = terrain_tile.tileCrop(width * 26, height * 13, width, height);
		caveCliffFootLeft = terrain_tile.tileCrop(width * 24, height * 14, width, height);
		caveCliffFootMiddle = terrain_tile.tileCrop(width * 25, height * 14, width, height);
		caveCliffFootRight = terrain_tile.tileCrop(width * 26, height * 14, width, height);
		caveCliffCornerTopLeft = terrain_tile.tileCrop(width * 30, height * 5, width, height);
		caveCliffCornerTopRight = terrain_tile.tileCrop(width * 31, height * 5, width, height);
		caveCliffCornerBottomLeft = terrain_tile.tileCrop(width * 30, height * 6, width, height);
		caveCliffCornerBottomRight = terrain_tile.tileCrop(width * 31, height * 6, width, height);
		caveCliffCornerLowerLeft = terrain_tile.tileCrop(width * 30, height * 7, width, height);
		caveCliffCornerLowerRight = terrain_tile.tileCrop(width * 31, height * 7, width, height);
		caveCliffCornerLowestLeft = terrain_tile.tileCrop(width * 30, height * 8, width, height);
		caveCliffCornerLowestRight = terrain_tile.tileCrop(width * 31, height * 8, width, height);
		
		// Ambiance tiles
		sparkleTile = terrain_tile.tileCrop(width * 16, height * 18, width, height);
		redMushroom = trees_sheet.tileCrop(width * 7, height * 6, width, height);
		blueMushroom = trees_sheet.tileCrop(width * 7, height * 5, width, height);
		smallRedRocks = terrain_tile.tileCrop(width * 31, height * 18, width, height);
		
		// Animated ambiance details
		sparkles = new BufferedImage[3];
		sparkles[0] = animated_terrain.tileCrop(0, 0, width, height);
		sparkles[1] = animated_terrain.tileCrop(width, 0, width, height);
		sparkles[2] = animated_terrain.tileCrop(width * 2, 0, width, height);
		
		// Skilling objects
		tree = texture_sheet.tileCrop(width, height * 2, width, height);
		rock = objects3.tileCrop(width * 4, height * 9, width, height);
		
		whirlpool = new BufferedImage[8];
		whirlpool[0] = whirlPool.tileCrop(0, 0, width, height);
		whirlpool[1] = whirlPool.tileCrop(width * 2, 0, width, height);
		whirlpool[2] = whirlPool.tileCrop(width, 0, width, height);
		whirlpool[3] = whirlPool.tileCrop(width * 3, 0, width, height);
		whirlpool[4] = whirlPool.tileCrop(0, height, width, height);
		whirlpool[5] = whirlPool.tileCrop(width * 2, height, width, height);
		whirlpool[6] = whirlPool.tileCrop(width, height, width, height);
		whirlpool[7] = whirlPool.tileCrop(width * 3, height, width, height);
		
	}
	
}
