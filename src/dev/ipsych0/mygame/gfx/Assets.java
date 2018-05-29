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
	whiteWallWindowTopLeft, whiteWallWindowTopRight, whiteWallWindowMiddleLeft, whiteWallWindowMiddleRight, whiteWallWindowBottomLeft, whiteWallWindowBottomRight, sandPillarTop, sandPillarBottom,
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
	boatBackMiddleMiddle, boatBackMiddleBottom, boatMiddle, workbench, ladderTop, ladderMiddle, ladderBottom;

	
	
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
	public static BufferedImage[] genericButton;
	
	// Item images
	public static BufferedImage wood, ore, testSword, purpleSword, testAxe, testPickaxe;
	public static BufferedImage[] coins;
	
	// Enemy images
	public static BufferedImage scorpion;
	
	// NPC images
	
	public static BufferedImage lorraine, banker;
	
	// Green House images
	public static BufferedImage greenHouseRoof, greenHouseWall, greenHouseEntrance;
	
	// Minimap images
	public static BufferedImage swampLand;
	
	// Inventory UI
	public static BufferedImage invScreen;
	
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
	public static BufferedImage hpOverlaySkillsIcon, hpOverlayCharacterIcon, hpOverlayAbilitiesIcon, hpOverlayQuestsIcon, hpOverlayMapIcon;
	
	public static BufferedImage[] whirlpool;
	
	public static BufferedImage fishingIcon, woodcuttingIcon, miningIcon;
	
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
		SpriteSheet shop_window = new SpriteSheet("/textures/shopwindow.png");
		/*
		 * Make skilling sheet for this
		 */
		SpriteSheet whirlPool = new SpriteSheet("/textures/whirlpool.png");
		SpriteSheet main_screen = new SpriteSheet("/textures/dark_priest.png");

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
		SpriteSheet object_sheet = new SpriteSheet("/textures/object_sprites11.png");
		/*
		 * Add animated tiles
		 */
		SpriteSheet animated_terrain = new SpriteSheet("/textures/animated_terrain.png");
		
		
		/*
		 * All Tiled Sprites
		 */
		SpriteSheet texture_sheet = new SpriteSheet("/textures/textures.png", true);
		SpriteSheet terrain_tile = new SpriteSheet("/textures/terrain.png", true);
		SpriteSheet trees_sheet = new SpriteSheet("/textures/trees.png", true);
		SpriteSheet city_sprites = new SpriteSheet("/textures/city_sprites.png", true);
		SpriteSheet roofs3_sheet = new SpriteSheet("/textures/roofs3.png", true);
		SpriteSheet cliffs_sheet = new SpriteSheet("/textures/cliffs.png", true);
		SpriteSheet objects17 = new SpriteSheet("/textures/object_sprites17.png", true);
		SpriteSheet furniture1 = new SpriteSheet("/textures/furniture1.png", true);
		SpriteSheet objects5 = new SpriteSheet("/textures/object_sprites5.png", true);
		SpriteSheet objects3 = new SpriteSheet("/textures/object_sprites3.png", true);
		SpriteSheet ship_sheet = new SpriteSheet("/textures/ship.png", true);
		
		// http://www.online-image-editor.com/ to remove white background from sprites, save as .png!
				
		/*
		 * Game UI Sprites
		 */
		equipmentPlaceHolders = new BufferedImage[12];
		
		earringSlot = equipSlots.imageCrop(0, 0, width, height);
		mainhandSlot = equipSlots.imageCrop(0, height, width, height);
		glovesSlot = equipSlots.imageCrop(0, height * 2, width, height);
		ringSlot1 = equipSlots.imageCrop(0, height * 3, width, height);
		helmSlot = equipSlots.imageCrop(width, 0, width, height);
		bodySlot = equipSlots.imageCrop(width, height, width, height);
		legsSlot = equipSlots.imageCrop(width, height * 2, width, height);
		bootsSlot = equipSlots.imageCrop(width, height * 3, width, height);
		amuletSlot = equipSlots.imageCrop(width * 2, 0, width, height);
		offhandSlot = equipSlots.imageCrop(width * 2, height, width, height);
		capeSlot = equipSlots.imageCrop(width * 2, height * 2, width, height);
		ringSlot2 = equipSlots.imageCrop(width * 2, height * 3, width, height);
		
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
		
		shopWindow = shop_window.imageCrop(0, 0, 460, 313);
		
		controlsScreen = controls_sheet.imageCrop(0, 0, 460, 313);
		
		magicProjectile = new BufferedImage[3];
		magicProjectile[0] = projectiles.imageCrop(width * 3, height * 4, width, height);
		magicProjectile[1] = projectiles.imageCrop(width * 4, height * 4, width, height);
		magicProjectile[2] = projectiles.imageCrop(width * 5, height * 4, width, height);
		
		fishingIcon = ui_sheet.imageCrop(width * 2, 0, width, height);
		woodcuttingIcon = ui_sheet.imageCrop(width * 3, 0, width, height);
		miningIcon = ui_sheet.imageCrop(width, 0, width, height);
		
		// Inventory sprites
		invScreen = ui_sheet.imageCrop(0, height * 9, 132, 329);
		
		// Equipment sprites
		equipScreen = ui_sheet.imageCrop(width * 13, height * 9, 132, 348);
		equipSlot = ui_sheet.imageCrop(width, 0, 32, 32);
		equipStats = ui_sheet.imageCrop(0, height * 20, 112, 160);
		
		// Chat sprites
		chatwindow = ui_sheet.imageCrop(0, height * 4, 432, 112);
		chatwindowTop = ui_sheet.imageCrop(0, height * 8, 432, 20);
		
		// HP Overlay sprites
		hpOverlay = ui_sheet.imageCrop(width * 3, 0, 160, 32);
		hpOverlayAbilitiesIcon = ui_sheet.imageCrop(width * 4, 0, width, height);
		hpOverlayCharacterIcon = ui_sheet.imageCrop(width * 5, 0, width, height);
		hpOverlaySkillsIcon = ui_sheet.imageCrop(width * 6, 0, width, height);
		hpOverlayQuestsIcon = ui_sheet.imageCrop(width * 7, 0, width, height);
		hpOverlayMapIcon = ui_sheet.imageCrop(width * 8, 0, width, height);
		
		// Crafting UI sprites
		craftWindow = ui_sheet.imageCrop(width * 5, height * 9, 242, 320);
		undiscovered = ui_sheet.imageCrop(0, 0, width, height);
		
		// Main menu background
		mainScreenBackground = main_screen.imageCrop(0, 0, 960, 720);
		
		/*
		 * Generic Button Sprites
		 */
		genericButton = new BufferedImage[2];
		genericButton[0] = ui_sheet.imageCrop(width * 9, 0, width * 7, height * 3);
		genericButton[1] = ui_sheet.imageCrop(width * 17, 0, width * 7, height * 3);
		
		/*
		 * Item Sprites
		 */
		wood = item_sheet.imageCrop(0, 0, width, height);
		ore = item_sheet.imageCrop(0, height, width, height);
		coins = new BufferedImage[4];
		coins[0] = item_sheet.imageCrop(width * 0, height * 2, width, height);
		coins[1] = item_sheet.imageCrop(width * 0, height * 3, width, height);
		coins[2] = item_sheet.imageCrop(width * 0, height * 4, width, height);
		coins[3] = item_sheet.imageCrop(width * 0, height * 5, width, height);
		testSword = item_sheet.imageCrop(width, height * 0, width, height);
		purpleSword = item_sheet.imageCrop(width * 2, height * 0, width, height);
		testAxe = item_sheet.imageCrop(0, height * 6, width, height);
		testPickaxe = item_sheet.imageCrop(0, height * 7, width, height);
		
		
		/*
		 * Interactable Object Sprites
		 */
		campfire = new BufferedImage[5];
		campfire[0] = objects17.imageCrop(0, height * 15, width, height);
		campfire[1] = objects17.imageCrop(width, height * 15, width, height);
		campfire[2] = objects17.imageCrop(width * 2, height * 15, width, height);
		campfire[3] = objects17.imageCrop(width * 3, height * 15, width, height);
		campfire[4] = objects17.imageCrop(width * 4, height * 15, width, height);
		
		teleportShrine1 = object_sheet.imageCrop(width * 7, height * 10, width, height);
		teleportShrine2 = object_sheet.imageCrop(width * 7, height * 11, width, height);
		teleportShrinePillar1 = object_sheet.imageCrop(width * 9, width * 10, width, height);
		teleportShrinePillar2 = object_sheet.imageCrop(width * 9, width * 11, width, height);
		
		workbench = city_sprites.imageCrop(width * 5, 0, width * 2, height * 2);
		
		/*
		 * Enemy Animations
		 */
		scorpion = enemy_sheet.imageCrop(0, 0, width, height);
		
		// NPC Sprites
		
		lorraine = lorraine_sprites.imageCrop(width * 7, 0, width, height);
		banker = lorraine_sprites.imageCrop(width * 10, 0, width, height);
		
		/*
		 * Player Animations
		 */
		player_attackingLeft = new BufferedImage[2];
		player_attackingRight = new BufferedImage[2];
		player_attackingDown = new BufferedImage[2];
		player_attackingUp = new BufferedImage[2];
		
		player_down = new BufferedImage[3];
		player_up = new BufferedImage[3];
		player_left = new BufferedImage[3];
		player_right = new BufferedImage[3];
		
		player_attackingLeft[0] = player_sheet.imageCrop(0, height * 4, width, height);
		player_attackingLeft[1] = player_sheet.imageCrop(width, height * 4, width, height);
		
		player_attackingRight[0] = player_sheet.imageCrop(0, height * 5, width, height);
		player_attackingRight[1] = player_sheet.imageCrop(width, height * 5, width, height);
		
		player_attackingDown[0] = player_sheet.imageCrop(0, height * 6, width, height);
		player_attackingDown[1] = player_sheet.imageCrop(width, height * 6, width, height);
		
		player_attackingUp[0] = player_sheet.imageCrop(0, height * 7, width, height);
		player_attackingUp[1] = player_sheet.imageCrop(width, height * 7, width, height);
		
		player_down[0] = player_sheet.imageCrop(0, 0, width, height);
		player_down[1] = player_sheet.imageCrop(width, 0, width, height);
		player_down[2] = player_sheet.imageCrop(width * 2, 0, width, height);
		
		player_up[0] = player_sheet.imageCrop(width * 6, height * 3, width, height);
		player_up[1] = player_sheet.imageCrop(width * 7, height * 3, width, height);
		player_up[2] = player_sheet.imageCrop(width * 8, height * 3, width, height);
		
		player_left[0] = player_sheet.imageCrop(0, height, width, height);
		player_left[1] = player_sheet.imageCrop(width, height, width, height);
		player_left[2] = player_sheet.imageCrop(width * 2, height, width, height);
		
		player_right[0] = player_sheet.imageCrop(0, height * 2, width, height);
		player_right[1] = player_sheet.imageCrop(width, height * 2, width, height);
		player_right[2] = player_sheet.imageCrop(width * 2, height * 2, width, height);
		
		/*
		 * Houses / Trees
		 */
		woodenRoofTopLeft = city_sprites.tileCrop(0, height * 23, width, height, true);
		woodenRoofTopMiddle = city_sprites.tileCrop(width, height * 23, width, height, true);
		woodenRoofTopRight = city_sprites.tileCrop(width * 2, height * 23, width, height, true);
		woodenRoofMiddleLeft = city_sprites.tileCrop(0, height * 24, width, height, true);
		woodenRoofMiddleMiddle = city_sprites.tileCrop(width, height * 24, width, height, true);
		woodenRoofMiddleRight = city_sprites.tileCrop(width * 2, height * 24, width, height, true);
		woodenRoofBottomLeft = city_sprites.tileCrop(0, height * 25, width, height, true);
		woodenRoofBottomMiddle = city_sprites.tileCrop(width, height * 25, width, height, true);
		woodenRoofBottomRight = city_sprites.tileCrop(width * 2, height * 25, width, height, true);
		
		greenRoofTopLeft = city_sprites.tileCrop(width * 5, height * 23, width, height, true);
		greenRoofTopMiddle = city_sprites.tileCrop(width * 6, height * 23, width, height, true);
		greenRoofTopRight = city_sprites.tileCrop(width * 7, height * 23, width, height, true);
		greenRoofMiddleLeft = city_sprites.tileCrop(width * 5, height * 24, width, height, true);
		greenRoofMiddleMiddle = city_sprites.tileCrop(width * 6, height * 24, width, height, true);
		greenRoofMiddleRight = city_sprites.tileCrop(width * 7, height * 24, width, height, true);
		greenRoofBottomLeft = city_sprites.tileCrop(width * 5, height * 25, width, height, true);
		greenRoofBottomMiddle = city_sprites.tileCrop(width * 6, height * 25, width, height, true);
		greenRoofBottomRight = city_sprites.tileCrop(width * 7, height * 25, width, height, true);

		wallLeft = city_sprites.tileCrop(0, height * 5, width, height, true);
		wallRight = city_sprites.tileCrop(width * 2, height * 5, width, height, true);
		wallMiddle = city_sprites.tileCrop(width, height * 5, width, height, true);
		entrance  = city_sprites.tileCrop(width * 6, height * 40, width, height);
		
		lightWallLeft = city_sprites.tileCrop(0, height * 4, width, height, true);
		lightWallMiddle = city_sprites.tileCrop(width, height * 4, width, height, true);
		lightWallRight = city_sprites.tileCrop(width * 2, height * 4, width, height, true);
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
		
		sandPillarTop = city_sprites.tileCrop(width * 5, height * 2, width, height, true);
		sandPillarBottom = city_sprites.tileCrop(width * 5, height * 3, width, height, true);
		
		smallWoodenStairTop = city_sprites.tileCrop(width * 7, height * 7, width, height);
		smallWoodenStairBottom = city_sprites.tileCrop(width * 7, height * 8, width, height);
		
		stairTopLeft = city_sprites.tileCrop(width * 4, height * 7, width, height);
		stairTopMiddle = city_sprites.tileCrop(width * 5, height * 7, width, height);
		stairTopRight = city_sprites.tileCrop(width * 6, height * 7, width, height);
		stairBottomLeft = city_sprites.tileCrop(width * 4, height * 8, width, height);
		stairBottomMiddle = city_sprites.tileCrop(width * 5, height * 8, width, height);
		stairBottomRight = city_sprites.tileCrop(width * 6, height * 8, width, height);
		
		tree1TopLeft = trees_sheet.tileCrop(0, height * 4, width, height, true);
		tree1TopRight = trees_sheet.tileCrop(width, height * 4, width, height, true);
		tree1BottomLeft = trees_sheet.tileCrop(0, height * 5, width, height, true);
		tree1BottomRight = trees_sheet.tileCrop(width, height * 5, width, height, true);
		tree1BatchTopLeft = trees_sheet.tileCrop(width * 2, height * 4, width, height, true);
		tree1BatchTopRight = trees_sheet.tileCrop(width * 3, height * 4, width, height, true);
		tree1BatchBottomLeft = trees_sheet.tileCrop(width * 2, height * 5, width, height, true);
		tree1BatchBottomRight = trees_sheet.tileCrop(width * 3, height * 5, width, height, true);
		palmTreeTop = trees_sheet.tileCrop(width, height * 12, width, height, true);
		palmTreeBottom = trees_sheet.tileCrop(width, height * 13, width, height, true);
		
		pineTreeTopLeft = trees_sheet.tileCrop(0, 0, width, height, true);
		pineTreeTopRight = trees_sheet.tileCrop(width, 0, width, height, true);
		pineTreeBottomLeft = trees_sheet.tileCrop(0, height, width, height, true);
		pineTreeBottomRight = trees_sheet.tileCrop(width, height, width, height, true);
		pineTreeBatchTopLeft = trees_sheet.tileCrop(width * 2, 0, width, height, true);
		pineTreeBatchTopRight = trees_sheet.tileCrop(width * 3, 0, width, height, true);
		pineTreeBatchBottomLeft = trees_sheet.tileCrop(width * 2, height, width, height, true);
		pineTreeBatchBottomRight = trees_sheet.tileCrop(width * 3, height, width, height, true);
		
//		pineTreeTopLeft = objects18.tileCrop(width * 4, 0, width, height);
//		pineTreeTopRight = objects18.tileCrop(width * 5, 0, width, height);
//		pineTreeBottomLeft = objects18.tileCrop(width * 4, height, width, height);
//		pineTreeBottomRight = objects18.tileCrop(width * 5, height, width, height);
//		pineTreeBatchTopLeft = objects18.tileCrop(width * 6, 0, width, height);
//		pineTreeBatchTopRight = objects18.tileCrop(width * 7, 0, width, height);
//		pineTreeBatchBottomLeft = objects18.tileCrop(width * 6, height, width, height);
//		pineTreeBatchBottomRight = objects18.tileCrop(width * 7, height, width, height);
		
		magicTreeLefter1 = trees_sheet.tileCrop(width * 7, height * 8, width, height, true);
		magicTreeLeft1 = trees_sheet.tileCrop(width * 8, height * 8, width, height, true);
		magicTreeRight1 = trees_sheet.tileCrop(width * 9, height * 8, width, height, true);
		magicTreeRighter1 = trees_sheet.tileCrop(width * 10, height * 8, width, height, true);
		magicTreeLefter2 = trees_sheet.tileCrop(width * 7, height * 9, width, height, true);
		magicTreeLeft2 = trees_sheet.tileCrop(width * 8, height * 9, width, height, true);
		magicTreeRight2 = trees_sheet.tileCrop(width * 9, height * 9, width, height, true);
		magicTreeRighter2 = trees_sheet.tileCrop(width * 10, height * 9, width, height, true);
		magicTreeLefter3 = trees_sheet.tileCrop(width * 7, height * 10, width, height, true);
		magicTreeLeft3 = trees_sheet.tileCrop(width * 8, height * 10, width, height, true);
		magicTreeRight3 = trees_sheet.tileCrop(width * 9, height * 10, width, height, true);
		magicTreeRighter3 = trees_sheet.tileCrop(width * 10, height * 10, width, height, true);
		magicTreeLefter4 = trees_sheet.tileCrop(width * 7, height * 11, width, height, true);
		magicTreeLeft4 = trees_sheet.tileCrop(width * 8, height * 11, width, height, true);
		magicTreeRight4 = trees_sheet.tileCrop(width * 9, height * 11, width, height, true);
		magicTreeRighter4 = trees_sheet.tileCrop(width * 10, height * 11, width, height, true);
		magicTreeLeft5 = trees_sheet.tileCrop(width * 8, height * 12, width, height, true);
		magicTreeRight5 = trees_sheet.tileCrop(width * 9, height * 12, width, height, true);
		
		whiteWallTopLeft = roofs3_sheet.tileCrop(0, 0, width, height, true);
		whiteWallTopMiddle = roofs3_sheet.tileCrop(width, 0, width, height, true);
		whiteWallTopRight = roofs3_sheet.tileCrop(width * 2, 0, width, height, true);
		whiteWallMiddleLeft = roofs3_sheet.tileCrop(0, height, width, height, true);
		whiteWallMiddleMiddle = roofs3_sheet.tileCrop(width, height, width, height, true);
		whiteWallMiddleRight = roofs3_sheet.tileCrop(width * 2, height, width, height, true);
		whiteWallBottomLeft = roofs3_sheet.tileCrop(0, height * 2, width, height, true);
		whiteWallBottomMiddle = roofs3_sheet.tileCrop(width, height * 2, width, height, true);
		whiteWallBottomRight = roofs3_sheet.tileCrop(width * 2, height * 2, width, height, true);
		
		whiteWallWindowTopLeft = roofs3_sheet.tileCrop(width * 4, 0, width, height, true);
		whiteWallWindowTopRight = roofs3_sheet.tileCrop(width * 5, 0, width, height, true);
		whiteWallWindowMiddleLeft = roofs3_sheet.tileCrop(width * 4, height, width, height, true);
		whiteWallWindowMiddleRight = roofs3_sheet.tileCrop(width * 5, height, width, height, true);
		whiteWallWindowBottomLeft = roofs3_sheet.tileCrop(width * 4, height * 2, width, height, true);
		whiteWallWindowBottomRight = roofs3_sheet.tileCrop(width * 5, height * 2, width, height, true);
		
		/*
		 * Furniture
		 */
		pot1 = objects17.tileCrop(width * 10, height * 8, width, height, true);
		basket1 = objects17.tileCrop(width * 14, height * 4, width, height, true);
		basketApples = objects17.tileCrop(width * 15, height * 4, width, height, true);
		waterBucket = objects17.tileCrop(width * 13, height * 5, width, height, true);
		table1 = furniture1.tileCrop(width * 13, height * 2, width, height, true);
		stoolTop1 = furniture1.tileCrop(width * 10, height, width, height);
		stoolBottom1 = furniture1.tileCrop(width * 9, height, width, height);
		sandPit = objects17.tileCrop(width * 5, height * 13, width, height);
		fireplaceTop = furniture1.tileCrop(width * 11, height * 7, width, height, true);
		fireplaceBottom = furniture1.tileCrop(width * 11, height * 8, width, height, true);
		bookcaseTopLeft = furniture1.tileCrop(width * 6, height * 12, width, height, true);
		bookcaseTopRight = furniture1.tileCrop(width * 7, height * 12, width, height, true);
		bookcaseBottomLeft = furniture1.tileCrop(width * 6, height * 13, width, height, true);
		bookcaseBottomRight = furniture1.tileCrop(width * 7, height * 13, width, height, true);
		bed1Top = furniture1.tileCrop(width * 4, height * 14, width, height, true);
		bed1Bottom = furniture1.tileCrop(width * 4, height * 15, width, height, true);
		drawer1 = furniture1.tileCrop(0, height * 10, width, height, true);
		smallBookcaseTop = furniture1.tileCrop(width * 5, height * 12, width, height, true);
		smallBookcaseBottom = furniture1.tileCrop(width * 5, height * 13, width, height, true);
		wardrobe1Top = furniture1.tileCrop(width * 2, height * 10, width, height, true);
		wardrobe1Bottom = furniture1.tileCrop(width * 2, height * 11, width, height, true);
		breadShelfTop = furniture1.tileCrop(0, height * 12, width, height, true);
		breadShelfBottom = furniture1.tileCrop(0, height * 13, width, height, true);
		bottleShelfTop = furniture1.tileCrop(width * 4, height * 12, width, height, true);
		bottleShelfBottom = furniture1.tileCrop(width * 4, height * 13, width, height, true);
		plateShelfTop = furniture1.tileCrop(width * 4, height * 10, width, height, true);
		plateShelfBottom = furniture1.tileCrop(width * 4, height * 11, width, height, true);
		painting1 = furniture1.tileCrop(width * 12, height * 11, width, height, true);
		painting2 = furniture1.tileCrop(width * 13, height * 11, width, height, true);
		painting3 = furniture1.tileCrop(width * 14, height * 11, width, height, true);
		painting4 = furniture1.tileCrop(width * 15, height * 11, width, height, true);
		worldMap1Left = furniture1.tileCrop(width * 14, height * 12, width, height, true);
		worldMap1Right = furniture1.tileCrop(width * 15, height * 12, width, height, true);
		bigPainting1Left = furniture1.tileCrop(width * 14, height * 13, width, height, true);
		bigPainting1Right = furniture1.tileCrop(width * 15, height * 13, width, height, true);
		wallNote = furniture1.tileCrop(width * 12, height * 12, width, height, true);
		
		crateApples = objects17.tileCrop(width * 12, height * 3, width, height, true);
		cratePotatoes = objects17.tileCrop(width * 10, height * 4, width, height, true);
		crateFish= objects17.tileCrop(width * 11, height * 4, width, height, true);
		crateGroceries = objects17.tileCrop(width * 12, height * 4, width, height, true);
		stackedCrateBottom = objects17.tileCrop(width * 10, height * 2, width, height, true);
		stackedCrateTop = objects17.tileCrop(width * 10, height, width, height, true);
		crate1 = objects17.tileCrop(width * 10, height * 3, width, height, true);
		emptyCrate = objects17.tileCrop(width * 11, height * 3, width, height, true);
		emptyBucket = objects17.tileCrop(width * 12, height * 5, width, height, true);
		emptyBarrel = objects17.tileCrop(width * 14, height * 2, width, height, true);
		barrel1 = objects17.tileCrop(width * 14, height, width, height, true);
		bed2Top = furniture1.tileCrop(0, height * 14, width, height, true);
		bed2Bottom = furniture1.tileCrop(0, height * 15, width, height, true);
		curtainLeftTop = furniture1.tileCrop(width, height * 3, width, height, true);
		curtainLeftBottom = furniture1.tileCrop(width, height * 4, width, height, true);
		curtainMiddleTop = furniture1.tileCrop(0, height * 3, width, height, true);
		curtainMiddleBottom = furniture1.tileCrop(0, height * 4, width, height, true);
		curtainRightTop = furniture1.tileCrop(width * 2, height * 3, width, height, true);
		curtainRightBottom = furniture1.tileCrop(width * 2, height * 4, width, height, true);

		signInn = objects5.tileCrop(width * 6, 0, width, height, true);
		signArmour = objects5.tileCrop(width * 3, 0, width, height, true);
		signWeapons = objects5.tileCrop(width * 2, 0, width, height, true);
		signWorkshop = objects5.tileCrop(width * 6, height, width, height, true);
		signBank = objects5.tileCrop(width * 4, 0, width, height, true);
		signShop = objects5.tileCrop(width * 3, height, width, height, true);
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

		/*
		 * Terrain Tile Sprites
		 */
		black = texture_sheet.tileCrop(width * 3, height * 6, width, height, true);
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
		greenMushroom = trees_sheet.tileCrop(width * 14, height * 8, width, height, true);
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
		
		waterSmallTopLeft = terrain_tile.tileCrop(width * 28, 0, width, height, true);
		waterSmallTopRight = terrain_tile.tileCrop(width * 29, 0, width, height, true);
		waterSmallBottomLeft = terrain_tile.tileCrop(width * 28, height, width, height, true);
		waterSmallBottomRight = terrain_tile.tileCrop(width * 29, height, width, height, true);
		waterTopLeft = terrain_tile.tileCrop(width * 27, height * 2, width, height, true);
		waterTopMiddle = terrain_tile.tileCrop(width * 28, height * 2, width, height, true);
		waterTopRight = terrain_tile.tileCrop(width * 29, height * 2, width, height, true);
		waterMiddleLeft = terrain_tile.tileCrop(width * 27, height * 3, width, height, true);
		waterMiddleMiddle = terrain_tile.tileCrop(width * 28, height * 3, width, height, true);
		waterMiddleRight = terrain_tile.tileCrop(width * 29, height * 3, width, height, true);
		waterBottomLeft = terrain_tile.tileCrop(width * 27, height * 4, width, height, true);
		waterBottomMiddle = terrain_tile.tileCrop(width * 28, height * 4, width, height, true);
		waterBottomRight = terrain_tile.tileCrop(width * 29, height * 4, width, height, true);
		waterFlow1 = terrain_tile.tileCrop(width * 27, height * 5, width, height, true);
		waterFlow2 = terrain_tile.tileCrop(width * 28, height * 5, width, height, true);
		waterFlow3 = terrain_tile.tileCrop(width * 29, height * 5, width, height, true);
		
		lavaSmallTopLeft = terrain_tile.tileCrop(width * 16, height * 0, width, height, true);
		lavaSmallTopRight = terrain_tile.tileCrop(width * 17, height * 0, width, height, true);
		lavaSmallBottomLeft = terrain_tile.tileCrop(width * 16, height * 1, width, height, true);
		lavaSmallBottomRight = terrain_tile.tileCrop(width * 17, height * 1, width, height, true);
		lavaTopLeft = terrain_tile.tileCrop(width * 15, height * 2, width, height, true);
		lavaTopMiddle = terrain_tile.tileCrop(width * 16, height * 2, width, height, true);
		lavaTopRight = terrain_tile.tileCrop(width * 17, height * 2, width, height, true);
		lavaMiddleLeft = terrain_tile.tileCrop(width * 15, height * 3, width, height, true);
		lavaMiddleMiddle = terrain_tile.tileCrop(width * 16, height * 3, width, height, true);
		lavaMiddleRight = terrain_tile.tileCrop(width * 17, height * 3, width, height, true);
		lavaBottomLeft = terrain_tile.tileCrop(width * 15, height * 4, width, height, true);
		lavaBottomMiddle = terrain_tile.tileCrop(width * 16, height * 4, width, height, true);
		lavaBottomRight = terrain_tile.tileCrop(width * 17, height * 4, width, height, true);
		lavaFlow1 = terrain_tile.tileCrop(width * 15, height * 5, width, height, true);
		lavaFlow2 = terrain_tile.tileCrop(width * 16, height * 5, width, height, true);
		lavaFlow3 = terrain_tile.tileCrop(width * 17, height * 5, width, height, true);
		
		sandWaterTopLeft = terrain_tile.tileCrop(width * 21, height * 8, width, height, true);
		sandWaterTopMiddle = terrain_tile.tileCrop(width * 22, height * 8, width, height, true);
		sandWaterTopRight = terrain_tile.tileCrop(width * 23, height * 8, width, height, true);
		sandWaterMiddleLeft = terrain_tile.tileCrop(width * 21, height * 9, width, height, true);
		sandWaterMiddleMiddle = terrain_tile.tileCrop(width * 22, height * 9, width, height, true);
		sandWaterMiddleRight = terrain_tile.tileCrop(width * 23, height * 9, width, height, true);
		sandWaterBottomLeft = terrain_tile.tileCrop(width * 21, height * 10, width, height, true);
		sandWaterBottomMiddle = terrain_tile.tileCrop(width * 22, height * 10, width, height, true);
		sandWaterBottomRight = terrain_tile.tileCrop(width * 23, height * 10, width, height, true);
		sandWaterSmallTopLeft = terrain_tile.tileCrop(width * 22, height * 6, width, height, true);
		sandWaterSmallTopRight = terrain_tile.tileCrop(width * 23, height * 6, width, height, true);
		sandWaterSmallBottomLeft = terrain_tile.tileCrop(width * 22, height * 7, width, height, true);
		sandWaterSmallBottomRight = terrain_tile.tileCrop(width * 23, height * 7, width, height, true);
		
		holeSmallTopLeft = terrain_tile.tileCrop(width * 19, height * 0, width, height, true);
		holeSmallTopRight = terrain_tile.tileCrop(width * 20, height * 0, width, height, true);
		holeSmallBottomLeft = terrain_tile.tileCrop(width * 19, height * 1, width, height, true);
		holeSmallBottomRight = terrain_tile.tileCrop(width * 20, height * 1, width, height, true);
		holeTopLeft = terrain_tile.tileCrop(width * 18, height * 2, width, height, true);
		holeTopMiddle = terrain_tile.tileCrop(width * 19, height * 2, width, height, true);
		holeTopRight = terrain_tile.tileCrop(width * 20, height * 2, width, height, true);
		holeMiddleLeft = terrain_tile.tileCrop(width * 18, height * 3, width, height, true);
		holeMiddleMiddle = terrain_tile.tileCrop(width * 19, height * 3, width, height, true);
		holeMiddleRight = terrain_tile.tileCrop(width * 20, height * 3, width, height, true);
		holeBottomLeft = terrain_tile.tileCrop(width * 18, height * 4, width, height, true);
		holeBottomMiddle = terrain_tile.tileCrop(width * 19, height * 4, width, height, true);
		holeBottomRight = terrain_tile.tileCrop(width * 20, height * 4, width, height, true);
		
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
		
		snowWaterTopLeft = terrain_tile.tileCrop(width * 21, height * 19, width, height, true);
		snowWaterTopMiddle = terrain_tile.tileCrop(width * 22, height * 19, width, height, true);
		snowWaterTopRight = terrain_tile.tileCrop(width * 23, height * 19, width, height, true);
		snowWaterMiddleLeft = terrain_tile.tileCrop(width * 21, height * 20, width, height, true);
		snowWaterMiddleMiddle = terrain_tile.tileCrop(width * 22, height * 20, width, height, true);
		snowWaterMiddleRight = terrain_tile.tileCrop(width * 23, height * 20, width, height, true);
		snowWaterBottomLeft = terrain_tile.tileCrop(width * 21, height * 21, width, height, true);
		snowWaterBottomMiddle = terrain_tile.tileCrop(width * 22, height * 21, width, height, true);
		snowWaterBottomRight = terrain_tile.tileCrop(width * 23, height * 21, width, height, true);
		snowWaterSmallTopLeft = terrain_tile.tileCrop(width * 22, height * 17, width, height, true);
		snowWaterSmallTopRight = terrain_tile.tileCrop(width * 23, height * 17, width, height, true);
		snowWaterSmallBottomLeft = terrain_tile.tileCrop(width * 22, height * 18, width, height, true);
		snowWaterSmallBottomRight = terrain_tile.tileCrop(width * 23, height * 18, width, height, true);
		
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
		
		cliffEntranceTop = cliffs_sheet.tileCrop(0, height * 5, width, height, true);
		cliffEntranceBottom = cliffs_sheet.tileCrop(0, height * 6, width, height);
		sandCliffTopLeft = cliffs_sheet.tileCrop(width * 5, 0, width, height, true);
		sandCliffTopMiddle = cliffs_sheet.tileCrop(width * 6, 0, width, height, true);
		sandCliffTopRight = cliffs_sheet.tileCrop(width * 7, 0, width, height, true);
		sandCliffMiddleLeft = cliffs_sheet.tileCrop(width * 5, height, width, height, true);
		sandCliffMiddleMiddle = cliffs_sheet.tileCrop(width * 6, height, width, height);
		sandCliffMiddleRight = cliffs_sheet.tileCrop(width * 7, height, width, height, true);
		sandCliffBottomLeft = cliffs_sheet.tileCrop(width * 5, height * 2, width, height, true);
		sandCliffBottomMiddle = cliffs_sheet.tileCrop(width * 6, height * 2, width, height, true);
		sandCliffBottomRight = cliffs_sheet.tileCrop(width * 7, height * 2, width, height, true);
		sandCliffLeft = cliffs_sheet.tileCrop(width * 5, height * 3, width, height, true);
		sandCliffMiddle = cliffs_sheet.tileCrop(width * 6, height * 3, width, height, true);
		sandCliffRight = cliffs_sheet.tileCrop(width * 7, height * 3, width, height, true);
		sandCliffFootLeft = cliffs_sheet.tileCrop(width * 5, height * 4, width, height, true);
		sandCliffFootMiddle = cliffs_sheet.tileCrop(width * 6, height * 4, width, height, true);
		sandCliffFootRight = cliffs_sheet.tileCrop(width * 7, height * 4, width, height, true);
		sandCliffCornerTopLeft = cliffs_sheet.tileCrop(width * 8, 0, width, height, true);
		sandCliffCornerTopRight = cliffs_sheet.tileCrop(width * 9, 0, width, height, true);
		sandCliffCornerBottomLeft = cliffs_sheet.tileCrop(width * 8, height, width, height, true);
		sandCliffCornerBottomRight = cliffs_sheet.tileCrop(width * 9, height, width, height, true);
		sandCliffCornerLowerLeft = cliffs_sheet.tileCrop(width * 8, height * 2, width, height, true);
		sandCliffCornerLowerRight = cliffs_sheet.tileCrop(width * 9, height * 2, width, height, true);
		sandCliffCornerLowestLeft = cliffs_sheet.tileCrop(width * 8, height * 3, width, height, true);
		sandCliffCornerLowestRight = cliffs_sheet.tileCrop(width * 9, height * 3, width, height, true);
		
		grassCliffTopLeft = cliffs_sheet.tileCrop(width * 17, 0, width, height, true);
		grassCliffTopMiddle = cliffs_sheet.tileCrop(width * 18, 0, width, height, true);
		grassCliffTopRight = cliffs_sheet.tileCrop(width * 19, 0, width, height, true);
		grassCliffMiddleLeft = cliffs_sheet.tileCrop(width * 17, height, width, height, true);
		grassCliffMiddleMiddle = cliffs_sheet.tileCrop(width * 18, height, width, height);
		grassCliffMiddleRight = cliffs_sheet.tileCrop(width * 19, height, width, height, true);
		grassCliffBottomLeft = cliffs_sheet.tileCrop(width * 17, height * 2, width, height, true);
		grassCliffBottomMiddle = cliffs_sheet.tileCrop(width * 18, height * 2, width, height, true);
		grassCliffBottomRight = cliffs_sheet.tileCrop(width * 19, height * 2, width, height, true);
		grassCliffLeft = cliffs_sheet.tileCrop(width * 17, height * 3, width, height, true);
		grassCliffMiddle = cliffs_sheet.tileCrop(width * 18, height * 3, width, height, true);
		grassCliffRight = cliffs_sheet.tileCrop(width * 19, height * 3, width, height, true);
		grassCliffFootLeft = cliffs_sheet.tileCrop(width * 17, height * 4, width, height, true);
		grassCliffFootMiddle = cliffs_sheet.tileCrop(width * 18, height * 4, width, height, true);
		grassCliffFootRight = cliffs_sheet.tileCrop(width * 19, height * 4, width, height, true);
		grassCliffCornerTopLeft = cliffs_sheet.tileCrop(width * 20, 0, width, height, true);
		grassCliffCornerTopRight = cliffs_sheet.tileCrop(width * 21, 0, width, height, true);
		grassCliffCornerBottomLeft = cliffs_sheet.tileCrop(width * 20, height, width, height, true);
		grassCliffCornerBottomRight = cliffs_sheet.tileCrop(width * 21, height, width, height, true);
		grassCliffCornerLowerLeft = cliffs_sheet.tileCrop(width * 20, height * 2, width, height, true);
		grassCliffCornerLowerRight = cliffs_sheet.tileCrop(width * 21, height * 2, width, height, true);
		grassCliffCornerLowestLeft = cliffs_sheet.tileCrop(width * 20, height * 3, width, height, true);
		grassCliffCornerLowestRight = cliffs_sheet.tileCrop(width * 21, height * 3, width, height, true);
		
		snowCliffTopLeft = cliffs_sheet.tileCrop(width * 29, 0, width, height, true);
		snowCliffTopMiddle = cliffs_sheet.tileCrop(width * 30, 0, width, height, true);
		snowCliffTopRight = cliffs_sheet.tileCrop(width * 31, 0, width, height, true);
		snowCliffMiddleLeft = cliffs_sheet.tileCrop(width * 29, height, width, height, true);
		snowCliffMiddleMiddle = cliffs_sheet.tileCrop(width * 30, height, width, height);
		snowCliffMiddleRight = cliffs_sheet.tileCrop(width * 31, height, width, height, true);
		snowCliffBottomLeft = cliffs_sheet.tileCrop(width * 29, height * 2, width, height, true);
		snowCliffBottomMiddle = cliffs_sheet.tileCrop(width * 30, height * 2, width, height, true);
		snowCliffBottomRight = cliffs_sheet.tileCrop(width * 31, height * 2, width, height, true);
		snowCliffLeft = cliffs_sheet.tileCrop(width * 29, height * 3, width, height, true);
		snowCliffMiddle = cliffs_sheet.tileCrop(width * 30, height * 3, width, height, true);
		snowCliffRight = cliffs_sheet.tileCrop(width * 31, height * 3, width, height, true);
		snowCliffFootLeft = cliffs_sheet.tileCrop(width * 29, height * 4, width, height, true);
		snowCliffFootMiddle = cliffs_sheet.tileCrop(width * 30, height * 4, width, height, true);
		snowCliffFootRight = cliffs_sheet.tileCrop(width * 31, height * 4, width, height, true);
		snowCliffCornerTopLeft = cliffs_sheet.tileCrop(width * 32, 0, width, height, true);
		snowCliffCornerTopRight = cliffs_sheet.tileCrop(width * 33, 0, width, height, true);
		snowCliffCornerBottomLeft = cliffs_sheet.tileCrop(width * 32, height, width, height, true);
		snowCliffCornerBottomRight = cliffs_sheet.tileCrop(width * 33, height, width, height, true);
		snowCliffCornerLowerLeft = cliffs_sheet.tileCrop(width * 32, height * 2, width, height, true);
		snowCliffCornerLowerRight = cliffs_sheet.tileCrop(width * 33, height * 2, width, height, true);
		snowCliffCornerLowestLeft = cliffs_sheet.tileCrop(width * 32, height * 3, width, height, true);
		snowCliffCornerLowestRight = cliffs_sheet.tileCrop(width * 33, height * 3, width, height, true);
		
		ladderTop = cliffs_sheet.tileCrop(width * 33, height * 4, width, height);
		ladderMiddle = cliffs_sheet.tileCrop(width * 33, height * 5, width, height);
		ladderBottom = cliffs_sheet.tileCrop(width * 33, height * 6, width, height);
		
		caveCliffTopLeft = terrain_tile.tileCrop(width * 24, height * 10, width, height, true);
		caveCliffTopMiddle = terrain_tile.tileCrop(width * 25, height * 10, width, height, true);
		caveCliffTopRight = terrain_tile.tileCrop(width * 26, height * 10, width, height, true);
		caveCliffMiddleLeft = terrain_tile.tileCrop(width * 24, height * 11, width, height, true);
		caveCliffMiddleMiddle = terrain_tile.tileCrop(width * 25, height * 11, width, height);
		caveCliffMiddleRight = terrain_tile.tileCrop(width * 26, height * 11, width, height, true);
		caveCliffBottomLeft = terrain_tile.tileCrop(width * 24, height * 12, width, height, true);
		caveCliffBottomMiddle = terrain_tile.tileCrop(width * 25, height * 12, width, height, true);
		caveCliffBottomRight = terrain_tile.tileCrop(width * 26, height * 12, width, height, true);
		caveCliffLeft = terrain_tile.tileCrop(width * 24, height * 13, width, height, true);
		caveCliffMiddle = terrain_tile.tileCrop(width * 25, height * 13, width, height, true);
		caveCliffRight = terrain_tile.tileCrop(width * 26, height * 13, width, height, true);
		caveCliffFootLeft = terrain_tile.tileCrop(width * 24, height * 14, width, height, true);
		caveCliffFootMiddle = terrain_tile.tileCrop(width * 25, height * 14, width, height, true);
		caveCliffFootRight = terrain_tile.tileCrop(width * 26, height * 14, width, height, true);
		caveCliffCornerTopLeft = terrain_tile.tileCrop(width * 30, height * 5, width, height, true);
		caveCliffCornerTopRight = terrain_tile.tileCrop(width * 31, height * 5, width, height, true);
		caveCliffCornerBottomLeft = terrain_tile.tileCrop(width * 30, height * 6, width, height, true);
		caveCliffCornerBottomRight = terrain_tile.tileCrop(width * 31, height * 6, width, height, true);
		caveCliffCornerLowerLeft = terrain_tile.tileCrop(width * 30, height * 7, width, height, true);
		caveCliffCornerLowerRight = terrain_tile.tileCrop(width * 31, height * 7, width, height, true);
		caveCliffCornerLowestLeft = terrain_tile.tileCrop(width * 30, height * 8, width, height, true);
		caveCliffCornerLowestRight = terrain_tile.tileCrop(width * 31, height * 8, width, height, true);
		
		// Ambiance tiles
		sparkleTile = terrain_tile.tileCrop(width * 16, height * 18, width, height);
		redMushroom = trees_sheet.tileCrop(width * 7, height * 6, width, height, true);
		blueMushroom = trees_sheet.tileCrop(width * 7, height * 5, width, height, true);
		smallRedRocks = terrain_tile.tileCrop(width * 31, height * 18, width, height, true);
		
		// Animated ambiance details
		sparkles = new BufferedImage[3];
		sparkles[0] = animated_terrain.imageCrop(0, 0, width, height);
		sparkles[1] = animated_terrain.imageCrop(width, 0, width, height);
		sparkles[2] = animated_terrain.imageCrop(width * 2, 0, width, height);
		
		
		// Skilling objects
		tree = texture_sheet.imageCrop(width, height * 2, width, height);
		rock = objects3.imageCrop(width * 4, height * 9, width, height);
		
		whirlpool = new BufferedImage[8];
		whirlpool[0] = whirlPool.imageCrop(0, 0, width, height);
		whirlpool[1] = whirlPool.imageCrop(width * 2, 0, width, height);
		whirlpool[2] = whirlPool.imageCrop(width, 0, width, height);
		whirlpool[3] = whirlPool.imageCrop(width * 3, 0, width, height);
		whirlpool[4] = whirlPool.imageCrop(0, height, width, height);
		whirlpool[5] = whirlPool.imageCrop(width * 2, height, width, height);
		whirlpool[6] = whirlPool.imageCrop(width, height, width, height);
		whirlpool[7] = whirlPool.imageCrop(width * 3, height, width, height);
		
	}
	
}
