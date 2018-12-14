package dev.ipsych0.myrinnia.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {

    private static final int width = 32, height = 32;

    // Fonts
    public static Font font14, font20, font32, font48, font64;

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
            woodenRoofMiddleMiddle, woodenRoofMiddleRight, woodenRoofBottomLeft, woodenRoofBottomMiddle, woodenRoofBottomRight, greenRoofTopLeft, greenRoofTopMiddle, greenRoofTopRight, greenRoofMiddleLeft,
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

    public static BufferedImage[][] puzzlePieces;

    public static void init() {

        /*
         * Fonts
         */
        font14 = FontLoader.loadFont("res/fonts/IBMPlexSans-Regular.otf", 14);
        font20 = FontLoader.loadFont("res/fonts/IBMPlexSans-Regular.otf", 20);
        font32 = FontLoader.loadFont("res/fonts/IBMPlexSans-Regular.otf", 32);
        font48 = FontLoader.loadFont("res/fonts/IBMPlexSans-Regular.otf", 48);
        font64 = FontLoader.loadFont("res/fonts/IBMPlexSans-Regular.otf", 64);


        /*
         * Sprite Sheets
         */
        SpriteSheet rsCastlePuzzle = new SpriteSheet("/textures/RS_CASTLE_PUZZLE.png");
        SpriteSheet ui_sheet = new SpriteSheet("/textures/ui-items-new.png");
        SpriteSheet projectiles = new SpriteSheet("/textures/projectiles.png");
        SpriteSheet equipSlots = new SpriteSheet("/textures/equipment_placeholders.png");
        SpriteSheet controls_sheet = new SpriteSheet("/textures/ControlsScreen.png");
        SpriteSheet shop_window = new SpriteSheet("/textures/shopwindow-new.png");
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

        puzzlePieces = new BufferedImage[rsCastlePuzzle.getSheet().getWidth() / 32][rsCastlePuzzle.getSheet().getHeight() / 32];
        for (int y = 0; y < rsCastlePuzzle.getSheet().getHeight() / 32; y++) {
            for (int x = 0; x < rsCastlePuzzle.getSheet().getWidth() / 32; x++) {
                puzzlePieces[x][y] = rsCastlePuzzle.imageCrop(x, y);
            }
        }

        /*
         * Game UI Sprites
         */
        equipmentPlaceHolders = new BufferedImage[12];

        earringSlot = equipSlots.imageCrop(0, 0);
        mainhandSlot = equipSlots.imageCrop(0, 1);
        glovesSlot = equipSlots.imageCrop(0, 2);
        ringSlot1 = equipSlots.imageCrop(0, 3);
        helmSlot = equipSlots.imageCrop(1, 0);
        bodySlot = equipSlots.imageCrop(1, 1);
        legsSlot = equipSlots.imageCrop(1, 2);
        bootsSlot = equipSlots.imageCrop(1, 3);
        amuletSlot = equipSlots.imageCrop(2, 0);
        offhandSlot = equipSlots.imageCrop(2, 1);
        capeSlot = equipSlots.imageCrop(2, 2);
        ringSlot2 = equipSlots.imageCrop(2, 3);

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
        magicProjectile[0] = projectiles.imageCrop(3, 4);
        magicProjectile[1] = projectiles.imageCrop(4, 4);
        magicProjectile[2] = projectiles.imageCrop(5, 4);

        fishingIcon = ui_sheet.imageCrop(2, 0);
        woodcuttingIcon = ui_sheet.imageCrop(3, 0);
        miningIcon = ui_sheet.imageCrop(1, 0);

        // Inventory sprites
        invScreen = ui_sheet.imageCrop(0, 9, 132, 329);

        // Equipment sprites
        equipScreen = ui_sheet.imageCrop(13, 9, 132, 348);
        equipSlot = ui_sheet.imageCrop(1, 0, 32, 32);
        equipStats = ui_sheet.imageCrop(0, 20, 112, 160);

        // Chat sprites
        chatwindow = ui_sheet.imageCrop(0, 4, 432, 112);
        chatwindowTop = ui_sheet.imageCrop(0, 8, 432, 20);

        // HP Overlay sprites
        hpOverlay = ui_sheet.imageCrop(3, 0, 160, 32);
        hpOverlayAbilitiesIcon = ui_sheet.imageCrop(4, 0);
        hpOverlayCharacterIcon = ui_sheet.imageCrop(5, 0);
        hpOverlaySkillsIcon = ui_sheet.imageCrop(6, 0);
        hpOverlayQuestsIcon = ui_sheet.imageCrop(7, 0);
        hpOverlayMapIcon = ui_sheet.imageCrop(8, 0);

        // Crafting UI sprites
        craftWindow = ui_sheet.imageCrop(5, 9, 242, 320);
        undiscovered = ui_sheet.imageCrop(0, 0);

        // Main menu background
        mainScreenBackground = main_screen.imageCrop(0, 0, 960, 720);

        /*
         * Generic Button Sprites
         */
        genericButton = new BufferedImage[2];
        genericButton[0] = ui_sheet.imageCrop(9, 0, width * 7, height * 3);
        genericButton[1] = ui_sheet.imageCrop(17, 0, width * 7, height * 3);

        /*
         * Item Sprites
         */
        wood = item_sheet.imageCrop(0, 0);
        ore = item_sheet.imageCrop(0, 1);
        coins = new BufferedImage[4];
        coins[0] = item_sheet.imageCrop(0, 2);
        coins[1] = item_sheet.imageCrop(0, 3);
        coins[2] = item_sheet.imageCrop(0, 4);
        coins[3] = item_sheet.imageCrop(0, 5);
        testSword = item_sheet.imageCrop(1, 0);
        purpleSword = item_sheet.imageCrop(2, 0);
        testAxe = item_sheet.imageCrop(0, 6);
        testPickaxe = item_sheet.imageCrop(0, 7);


        /*
         * Interactable Object Sprites
         */
        campfire = new BufferedImage[5];
        campfire[0] = objects17.imageCrop(0, 15);
        campfire[1] = objects17.imageCrop(1, 15);
        campfire[2] = objects17.imageCrop(2, 15);
        campfire[3] = objects17.imageCrop(3, 15);
        campfire[4] = objects17.imageCrop(4, 15);

        teleportShrine1 = object_sheet.imageCrop(7, 10);
        teleportShrine2 = object_sheet.imageCrop(7, 11);
        teleportShrinePillar1 = object_sheet.imageCrop(9, 10);
        teleportShrinePillar2 = object_sheet.imageCrop(9, 11);

        workbench = city_sprites.imageCrop(5, 0, 2, 2);

        /*
         * Enemy Animations
         */
        scorpion = enemy_sheet.imageCrop(0, 0);

        // NPC Sprites

        lorraine = lorraine_sprites.imageCrop(7, 0);
        banker = lorraine_sprites.imageCrop(10, 0);

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

        player_attackingLeft[0] = player_sheet.imageCrop(0, 4);
        player_attackingLeft[1] = player_sheet.imageCrop(1, 4);

        player_attackingRight[0] = player_sheet.imageCrop(0, 5);
        player_attackingRight[1] = player_sheet.imageCrop(1, 5);

        player_attackingDown[0] = player_sheet.imageCrop(0, 6);
        player_attackingDown[1] = player_sheet.imageCrop(1, 6);

        player_attackingUp[0] = player_sheet.imageCrop(0, 7);
        player_attackingUp[1] = player_sheet.imageCrop(1, 7);

        player_down[0] = player_sheet.imageCrop(0, 0);
        player_down[1] = player_sheet.imageCrop(1, 0);
        player_down[2] = player_sheet.imageCrop(2, 0);

        player_up[0] = player_sheet.imageCrop(6, 3);
        player_up[1] = player_sheet.imageCrop(7, 3);
        player_up[2] = player_sheet.imageCrop(8, 3);

        player_left[0] = player_sheet.imageCrop(0, 1);
        player_left[1] = player_sheet.imageCrop(1, 1);
        player_left[2] = player_sheet.imageCrop(2, 1);

        player_right[0] = player_sheet.imageCrop(0, 2);
        player_right[1] = player_sheet.imageCrop(1, 2);
        player_right[2] = player_sheet.imageCrop(2, 2);

        /*
         * Houses / Trees
         */
        woodenRoofTopLeft = city_sprites.tileCrop(0, 23, true);
        woodenRoofTopMiddle = city_sprites.tileCrop(1, 23, true);
        woodenRoofTopRight = city_sprites.tileCrop(2, 23, true);
        woodenRoofMiddleLeft = city_sprites.tileCrop(0, 24, true);
        woodenRoofMiddleMiddle = city_sprites.tileCrop(1, 24, true);
        woodenRoofMiddleRight = city_sprites.tileCrop(2, 24, true);
        woodenRoofBottomLeft = city_sprites.tileCrop(0, 25, true);
        woodenRoofBottomMiddle = city_sprites.tileCrop(1, 25, true);
        woodenRoofBottomRight = city_sprites.tileCrop(2, 25, true);

        greenRoofTopLeft = city_sprites.tileCrop(5, 23, true);
        greenRoofTopMiddle = city_sprites.tileCrop(6, 23, true);
        greenRoofTopRight = city_sprites.tileCrop(7, 23, true);
        greenRoofMiddleLeft = city_sprites.tileCrop(5, 24, true);
        greenRoofMiddleMiddle = city_sprites.tileCrop(6, 24, true);
        greenRoofMiddleRight = city_sprites.tileCrop(7, 24, true);
        greenRoofBottomLeft = city_sprites.tileCrop(5, 25, true);
        greenRoofBottomMiddle = city_sprites.tileCrop(6, 25, true);
        greenRoofBottomRight = city_sprites.tileCrop(7, 25, true);

        wallLeft = city_sprites.tileCrop(0, 5, true);
        wallRight = city_sprites.tileCrop(2, 5, true);
        wallMiddle = city_sprites.tileCrop(1, 5, true);
        entrance = city_sprites.tileCrop(6, 40);

        lightWallLeft = city_sprites.tileCrop(0, 4, true);
        lightWallMiddle = city_sprites.tileCrop(1, 4, true);
        lightWallRight = city_sprites.tileCrop(2, 4, true);
        woodenDoorTop = city_sprites.tileCrop(6, 10);
        woodenDoorBottom = city_sprites.tileCrop(6, 11);

        floorTopLeft = city_sprites.tileCrop(0, 9);
        floorTopMiddle = city_sprites.tileCrop(1, 9);
        floorTopRight = city_sprites.tileCrop(2, 9);
        floorMiddleLeft = city_sprites.tileCrop(0, 10);
        floorMiddleMiddle = city_sprites.tileCrop(1, 10);
        floorMiddleRight = city_sprites.tileCrop(2, 10);
        floorBottomLeft = city_sprites.tileCrop(0, 11);
        floorBottomMiddle = city_sprites.tileCrop(1, 11);
        floorBottomRight = city_sprites.tileCrop(2, 11);

        sandPillarTop = city_sprites.tileCrop(5, 2, true);
        sandPillarBottom = city_sprites.tileCrop(5, 3, true);

        smallWoodenStairTop = city_sprites.tileCrop(7, 7);
        smallWoodenStairBottom = city_sprites.tileCrop(7, 8);

        stairTopLeft = city_sprites.tileCrop(4, 7);
        stairTopMiddle = city_sprites.tileCrop(5, 7);
        stairTopRight = city_sprites.tileCrop(6, 7);
        stairBottomLeft = city_sprites.tileCrop(4, 8);
        stairBottomMiddle = city_sprites.tileCrop(5, 8);
        stairBottomRight = city_sprites.tileCrop(6, 8);

        tree1TopLeft = trees_sheet.tileCrop(0, 4, true);
        tree1TopRight = trees_sheet.tileCrop(1, 4, true);
        tree1BottomLeft = trees_sheet.tileCrop(0, 5, true);
        tree1BottomRight = trees_sheet.tileCrop(1, 5, true);
        tree1BatchTopLeft = trees_sheet.tileCrop(2, 4, true);
        tree1BatchTopRight = trees_sheet.tileCrop(3, 4, true);
        tree1BatchBottomLeft = trees_sheet.tileCrop(2, 5, true);
        tree1BatchBottomRight = trees_sheet.tileCrop(3, 5, true);
        palmTreeTop = trees_sheet.tileCrop(1, 12, true);
        palmTreeBottom = trees_sheet.tileCrop(1, 13, true);

        pineTreeTopLeft = trees_sheet.tileCrop(0, 0, true);
        pineTreeTopRight = trees_sheet.tileCrop(1, 0, true);
        pineTreeBottomLeft = trees_sheet.tileCrop(0, 1, true);
        pineTreeBottomRight = trees_sheet.tileCrop(1, 1, true);
        pineTreeBatchTopLeft = trees_sheet.tileCrop(2, 0, true);
        pineTreeBatchTopRight = trees_sheet.tileCrop(3, 0, true);
        pineTreeBatchBottomLeft = trees_sheet.tileCrop(2, 1, true);
        pineTreeBatchBottomRight = trees_sheet.tileCrop(3, 1, true);

//		pineTreeTopLeft = objects18.tileCrop(4, 0);
//		pineTreeTopRight = objects18.tileCrop(5, 0);
//		pineTreeBottomLeft = objects18.tileCrop(4, 1);
//		pineTreeBottomRight = objects18.tileCrop(5, 1);
//		pineTreeBatchTopLeft = objects18.tileCrop(6, 0);
//		pineTreeBatchTopRight = objects18.tileCrop(7, 0);
//		pineTreeBatchBottomLeft = objects18.tileCrop(6, 1);
//		pineTreeBatchBottomRight = objects18.tileCrop(7, 1);

        magicTreeLefter1 = trees_sheet.tileCrop(7, 8, true);
        magicTreeLeft1 = trees_sheet.tileCrop(8, 8, true);
        magicTreeRight1 = trees_sheet.tileCrop(9, 8, true);
        magicTreeRighter1 = trees_sheet.tileCrop(10, 8, true);
        magicTreeLefter2 = trees_sheet.tileCrop(7, 9, true);
        magicTreeLeft2 = trees_sheet.tileCrop(8, 9, true);
        magicTreeRight2 = trees_sheet.tileCrop(9, 9, true);
        magicTreeRighter2 = trees_sheet.tileCrop(10, 9, true);
        magicTreeLefter3 = trees_sheet.tileCrop(7, 10, true);
        magicTreeLeft3 = trees_sheet.tileCrop(8, 10, true);
        magicTreeRight3 = trees_sheet.tileCrop(9, 10, true);
        magicTreeRighter3 = trees_sheet.tileCrop(10, 10, true);
        magicTreeLefter4 = trees_sheet.tileCrop(7, 11, true);
        magicTreeLeft4 = trees_sheet.tileCrop(8, 11, true);
        magicTreeRight4 = trees_sheet.tileCrop(9, 11, true);
        magicTreeRighter4 = trees_sheet.tileCrop(10, 11, true);
        magicTreeLeft5 = trees_sheet.tileCrop(8, 12, true);
        magicTreeRight5 = trees_sheet.tileCrop(9, 12, true);

        whiteWallTopLeft = roofs3_sheet.tileCrop(0, 0, true);
        whiteWallTopMiddle = roofs3_sheet.tileCrop(1, 0, true);
        whiteWallTopRight = roofs3_sheet.tileCrop(2, 0, true);
        whiteWallMiddleLeft = roofs3_sheet.tileCrop(0, 1, true);
        whiteWallMiddleMiddle = roofs3_sheet.tileCrop(1, 1, true);
        whiteWallMiddleRight = roofs3_sheet.tileCrop(2, 1, true);
        whiteWallBottomLeft = roofs3_sheet.tileCrop(0, 2, true);
        whiteWallBottomMiddle = roofs3_sheet.tileCrop(1, 2, true);
        whiteWallBottomRight = roofs3_sheet.tileCrop(2, 2, true);

        whiteWallWindowTopLeft = roofs3_sheet.tileCrop(4, 0, true);
        whiteWallWindowTopRight = roofs3_sheet.tileCrop(5, 0, true);
        whiteWallWindowMiddleLeft = roofs3_sheet.tileCrop(4, 1, true);
        whiteWallWindowMiddleRight = roofs3_sheet.tileCrop(5, 1, true);
        whiteWallWindowBottomLeft = roofs3_sheet.tileCrop(4, 2, true);
        whiteWallWindowBottomRight = roofs3_sheet.tileCrop(5, 2, true);

        /*
         * Furniture
         */
        pot1 = objects17.tileCrop(10, 8, true);
        basket1 = objects17.tileCrop(14, 4, true);
        basketApples = objects17.tileCrop(15, 4, true);
        waterBucket = objects17.tileCrop(13, 5, true);
        table1 = furniture1.tileCrop(13, 2, true);
        stoolTop1 = furniture1.tileCrop(10, 1);
        stoolBottom1 = furniture1.tileCrop(9, 1);
        sandPit = objects17.tileCrop(5, 13);
        fireplaceTop = furniture1.tileCrop(11, 7, true);
        fireplaceBottom = furniture1.tileCrop(11, 8, true);
        bookcaseTopLeft = furniture1.tileCrop(6, 12, true);
        bookcaseTopRight = furniture1.tileCrop(7, 12, true);
        bookcaseBottomLeft = furniture1.tileCrop(6, 13, true);
        bookcaseBottomRight = furniture1.tileCrop(7, 13, true);
        bed1Top = furniture1.tileCrop(4, 14, true);
        bed1Bottom = furniture1.tileCrop(4, 15, true);
        drawer1 = furniture1.tileCrop(0, 10, true);
        smallBookcaseTop = furniture1.tileCrop(5, 12, true);
        smallBookcaseBottom = furniture1.tileCrop(5, 13, true);
        wardrobe1Top = furniture1.tileCrop(2, 10, true);
        wardrobe1Bottom = furniture1.tileCrop(2, 11, true);
        breadShelfTop = furniture1.tileCrop(0, 12, true);
        breadShelfBottom = furniture1.tileCrop(0, 13, true);
        bottleShelfTop = furniture1.tileCrop(4, 12, true);
        bottleShelfBottom = furniture1.tileCrop(4, 13, true);
        plateShelfTop = furniture1.tileCrop(4, 10, true);
        plateShelfBottom = furniture1.tileCrop(4, 11, true);
        painting1 = furniture1.tileCrop(12, 11, true);
        painting2 = furniture1.tileCrop(13, 11, true);
        painting3 = furniture1.tileCrop(14, 11, true);
        painting4 = furniture1.tileCrop(15, 11, true);
        worldMap1Left = furniture1.tileCrop(14, 12, true);
        worldMap1Right = furniture1.tileCrop(15, 12, true);
        bigPainting1Left = furniture1.tileCrop(14, 13, true);
        bigPainting1Right = furniture1.tileCrop(15, 13, true);
        wallNote = furniture1.tileCrop(12, 12, true);

        crateApples = objects17.tileCrop(12, 3, true);
        cratePotatoes = objects17.tileCrop(10, 4, true);
        crateFish = objects17.tileCrop(11, 4, true);
        crateGroceries = objects17.tileCrop(12, 4, true);
        stackedCrateBottom = objects17.tileCrop(10, 2, true);
        stackedCrateTop = objects17.tileCrop(10, 1, true);
        crate1 = objects17.tileCrop(10, 3, true);
        emptyCrate = objects17.tileCrop(11, 3, true);
        emptyBucket = objects17.tileCrop(12, 5, true);
        emptyBarrel = objects17.tileCrop(14, 2, true);
        barrel1 = objects17.tileCrop(14, 1, true);
        bed2Top = furniture1.tileCrop(0, 14, true);
        bed2Bottom = furniture1.tileCrop(0, 15, true);
        curtainLeftTop = furniture1.tileCrop(1, 3, true);
        curtainLeftBottom = furniture1.tileCrop(1, 4, true);
        curtainMiddleTop = furniture1.tileCrop(0, 3, true);
        curtainMiddleBottom = furniture1.tileCrop(0, 4, true);
        curtainRightTop = furniture1.tileCrop(2, 3, true);
        curtainRightBottom = furniture1.tileCrop(2, 4, true);

        signInn = objects5.tileCrop(6, 0, true);
        signArmour = objects5.tileCrop(3, 0, true);
        signWeapons = objects5.tileCrop(2, 0, true);
        signWorkshop = objects5.tileCrop(6, 1, true);
        signBank = objects5.tileCrop(4, 0, true);
        signShop = objects5.tileCrop(3, 1, true);
        woodenBridgeHorizontal = objects5.tileCrop(3, 2);
        woodenBridgeVertical = objects5.tileCrop(2, 2);
        logBridgeHorizontal = objects5.tileCrop(0, 2);
        logBridgeVertical = objects5.tileCrop(1, 2);
        dirtHole = objects5.tileCrop(1, 12);

        /*
         * Ship sprites
         */
        boatTop1 = ship_sheet.tileCrop(2, 0);
        boatTop2 = ship_sheet.tileCrop(3, 0);
        boatTop3 = ship_sheet.tileCrop(4, 0);
        boatTop4 = ship_sheet.tileCrop(1, 1);
        boatTop5 = ship_sheet.tileCrop(2, 1);
        boatTop6 = ship_sheet.tileCrop(3, 1);
        boatTop7 = ship_sheet.tileCrop(4, 1);
        boatTop8 = ship_sheet.tileCrop(5, 1);
        boatTop9 = ship_sheet.tileCrop(1, 2);
        boatTop10 = ship_sheet.tileCrop(2, 2);
        boatTop11 = ship_sheet.tileCrop(4, 2);
        boatTop12 = ship_sheet.tileCrop(5, 2);
        boatTop13 = ship_sheet.tileCrop(0, 3);
        boatTop14 = ship_sheet.tileCrop(1, 3);
        boatTop15 = ship_sheet.tileCrop(5, 3);
        boatTop16 = ship_sheet.tileCrop(6, 3);
        boatTop17 = ship_sheet.tileCrop(0, 4);
        boatTop18 = ship_sheet.tileCrop(1, 4);
        boatTop19 = ship_sheet.tileCrop(5, 4);
        boatTop20 = ship_sheet.tileCrop(6, 4);
        boatStairsTopLeft = ship_sheet.tileCrop(7, 5);
        boatStairsBottomLeft = ship_sheet.tileCrop(7, 6);
        boatStairsTopRight = ship_sheet.tileCrop(8, 5);
        boatStairsBottomRight = ship_sheet.tileCrop(8, 6);
        boatStairs = ship_sheet.tileCrop(7, 3);
        boatStairsMiddleTop = ship_sheet.tileCrop(9, 3);
        boatStairsMiddleBottom = ship_sheet.tileCrop(9, 4);
        boatRailingMiddleLeft = ship_sheet.tileCrop(0, 9);
        boatRailingMiddleRight = ship_sheet.tileCrop(2, 9);
        boatRailingLeft = ship_sheet.tileCrop(0, 10);
        boatRailingRight = ship_sheet.tileCrop(2, 10);
        boatRailingBottomLeft = ship_sheet.tileCrop(0, 11);
        boatRailingBottomRight = ship_sheet.tileCrop(2, 11);
        boatRailingBottom = ship_sheet.tileCrop(1, 11);
        boatRailingTop = ship_sheet.tileCrop(1, 10);
        boatBack1 = ship_sheet.tileCrop(0, 12);
        boatBack2 = ship_sheet.tileCrop(0, 13);
        boatBack3 = ship_sheet.tileCrop(0, 14);
        boatBack4 = ship_sheet.tileCrop(2, 12);
        boatBack5 = ship_sheet.tileCrop(2, 13);
        boatBack6 = ship_sheet.tileCrop(2, 14);
        boatBackMiddleMiddle = ship_sheet.tileCrop(1, 13);
        boatBackMiddleBottom = ship_sheet.tileCrop(1, 14);
        boatMiddle = ship_sheet.tileCrop(6, 0);

        /*
         * Terrain Tile Sprites
         */
        black = texture_sheet.tileCrop(3, 6, true);
        invisible = terrain_tile.tileCrop(7, 21);

        lightGrass = terrain_tile.tileCrop(1, 9);
        lightGrassPatch1 = terrain_tile.tileCrop(0, 11);
        lightGrassPatch2 = terrain_tile.tileCrop(1, 11);
        lightGrassPatch3 = terrain_tile.tileCrop(2, 11);
        flowerPatch1 = terrain_tile.tileCrop(3, 11);
        flowerPatch2 = terrain_tile.tileCrop(4, 11);
        flowerPatch3 = terrain_tile.tileCrop(5, 11);
        greyFlower1 = roofs3_sheet.tileCrop(4, 8);
        yellowFlower1 = roofs3_sheet.tileCrop(4, 9);
        purpleFlower1 = roofs3_sheet.tileCrop(4, 10);
        redFlower1 = roofs3_sheet.tileCrop(4, 11);
        plantPot1 = roofs3_sheet.tileCrop(4, 12);
        pinkFlower1 = trees_sheet.tileCrop(13, 8);
        greenMushroom = trees_sheet.tileCrop(14, 8, true);
        pinkFlower2 = trees_sheet.tileCrop(14, 9);

        darkGrass = terrain_tile.tileCrop(7, 9);
        darkGrassTopLeft = terrain_tile.tileCrop(6, 8);
        darkGrassTopMiddle = terrain_tile.tileCrop(7, 8);
        darkGrassTopRight = terrain_tile.tileCrop(8, 8);
        darkGrassMiddleLeft = terrain_tile.tileCrop(6, 9);
        darkGrassMiddleRight = terrain_tile.tileCrop(8, 9);
        darkGrassBottomLeft = terrain_tile.tileCrop(6, 10);
        darkGrassBottomMiddle = terrain_tile.tileCrop(7, 10);
        darkGrassBottomRight = terrain_tile.tileCrop(8, 10);
        darkGrassSmallTopLeft = terrain_tile.tileCrop(7, 6);
        darkGrassSmallTopRight = terrain_tile.tileCrop(8, 6);
        darkGrassSmallBottomLeft = terrain_tile.tileCrop(7, 7);
        darkGrassSmallBottomRight = terrain_tile.tileCrop(8, 7);
        darkGrassPatch1 = terrain_tile.tileCrop(8, 11);
        darkGrassPatch2 = terrain_tile.tileCrop(7, 11);
        darkGrassPatch3 = terrain_tile.tileCrop(6, 11);

        waterSmallTopLeft = terrain_tile.tileCrop(28, 0, true);
        waterSmallTopRight = terrain_tile.tileCrop(29, 0, true);
        waterSmallBottomLeft = terrain_tile.tileCrop(28, 1, true);
        waterSmallBottomRight = terrain_tile.tileCrop(29, 1, true);
        waterTopLeft = terrain_tile.tileCrop(27, 2, true);
        waterTopMiddle = terrain_tile.tileCrop(28, 2, true);
        waterTopRight = terrain_tile.tileCrop(29, 2, true);
        waterMiddleLeft = terrain_tile.tileCrop(27, 3, true);
        waterMiddleMiddle = terrain_tile.tileCrop(28, 3, true);
        waterMiddleRight = terrain_tile.tileCrop(29, 3, true);
        waterBottomLeft = terrain_tile.tileCrop(27, 4, true);
        waterBottomMiddle = terrain_tile.tileCrop(28, 4, true);
        waterBottomRight = terrain_tile.tileCrop(29, 4, true);
        waterFlow1 = terrain_tile.tileCrop(27, 5, true);
        waterFlow2 = terrain_tile.tileCrop(28, 5, true);
        waterFlow3 = terrain_tile.tileCrop(29, 5, true);

        lavaSmallTopLeft = terrain_tile.tileCrop(16, 0, true);
        lavaSmallTopRight = terrain_tile.tileCrop(17, 0, true);
        lavaSmallBottomLeft = terrain_tile.tileCrop(16, 1, true);
        lavaSmallBottomRight = terrain_tile.tileCrop(17, 1, true);
        lavaTopLeft = terrain_tile.tileCrop(15, 2, true);
        lavaTopMiddle = terrain_tile.tileCrop(16, 2, true);
        lavaTopRight = terrain_tile.tileCrop(17, 2, true);
        lavaMiddleLeft = terrain_tile.tileCrop(15, 3, true);
        lavaMiddleMiddle = terrain_tile.tileCrop(16, 3, true);
        lavaMiddleRight = terrain_tile.tileCrop(17, 3, true);
        lavaBottomLeft = terrain_tile.tileCrop(15, 4, true);
        lavaBottomMiddle = terrain_tile.tileCrop(16, 4, true);
        lavaBottomRight = terrain_tile.tileCrop(17, 4, true);
        lavaFlow1 = terrain_tile.tileCrop(15, 5, true);
        lavaFlow2 = terrain_tile.tileCrop(16, 5, true);
        lavaFlow3 = terrain_tile.tileCrop(17, 5, true);

        sandWaterTopLeft = terrain_tile.tileCrop(21, 8, true);
        sandWaterTopMiddle = terrain_tile.tileCrop(22, 8, true);
        sandWaterTopRight = terrain_tile.tileCrop(23, 8, true);
        sandWaterMiddleLeft = terrain_tile.tileCrop(21, 9, true);
        sandWaterMiddleMiddle = terrain_tile.tileCrop(22, 9, true);
        sandWaterMiddleRight = terrain_tile.tileCrop(23, 9, true);
        sandWaterBottomLeft = terrain_tile.tileCrop(21, 10, true);
        sandWaterBottomMiddle = terrain_tile.tileCrop(22, 10, true);
        sandWaterBottomRight = terrain_tile.tileCrop(23, 10, true);
        sandWaterSmallTopLeft = terrain_tile.tileCrop(22, 6, true);
        sandWaterSmallTopRight = terrain_tile.tileCrop(23, 6, true);
        sandWaterSmallBottomLeft = terrain_tile.tileCrop(22, 7, true);
        sandWaterSmallBottomRight = terrain_tile.tileCrop(23, 7, true);

        holeSmallTopLeft = terrain_tile.tileCrop(19, 0, true);
        holeSmallTopRight = terrain_tile.tileCrop(20, 0, true);
        holeSmallBottomLeft = terrain_tile.tileCrop(19, 1, true);
        holeSmallBottomRight = terrain_tile.tileCrop(20, 1, true);
        holeTopLeft = terrain_tile.tileCrop(18, 2, true);
        holeTopMiddle = terrain_tile.tileCrop(19, 2, true);
        holeTopRight = terrain_tile.tileCrop(20, 2, true);
        holeMiddleLeft = terrain_tile.tileCrop(18, 3, true);
        holeMiddleMiddle = terrain_tile.tileCrop(19, 3, true);
        holeMiddleRight = terrain_tile.tileCrop(20, 3, true);
        holeBottomLeft = terrain_tile.tileCrop(18, 4, true);
        holeBottomMiddle = terrain_tile.tileCrop(19, 4, true);
        holeBottomRight = terrain_tile.tileCrop(20, 4, true);

        transDirtTopLeft = terrain_tile.tileCrop(0, 2);
        transDirtTopMiddle = terrain_tile.tileCrop(1, 2);
        transDirtTopRight = terrain_tile.tileCrop(2, 2);
        transDirtMiddleLeft = terrain_tile.tileCrop(0, 3);
        transDirtMiddleMiddle = terrain_tile.tileCrop(1, 3);
        transDirtMiddleRight = terrain_tile.tileCrop(2, 3);
        transDirtBottomLeft = terrain_tile.tileCrop(0, 4);
        transDirtBottomMiddle = terrain_tile.tileCrop(1, 4);
        transDirtBottomRight = terrain_tile.tileCrop(2, 4);
        transDirtSmallTopLeft = terrain_tile.tileCrop(1, 0);
        transDirtSmallTopRight = terrain_tile.tileCrop(2, 0);
        transDirtSmallBottomLeft = terrain_tile.tileCrop(1, 1);
        transDirtSmallBottomRight = terrain_tile.tileCrop(2, 1);

        redDirtTopLeft = terrain_tile.tileCrop(6, 2);
        redDirtTopMiddle = terrain_tile.tileCrop(7, 2);
        redDirtTopRight = terrain_tile.tileCrop(8, 2);
        redDirtMiddleLeft = terrain_tile.tileCrop(6, 3);
        redDirtMiddleMiddle = terrain_tile.tileCrop(7, 3);
        redDirtMiddleRight = terrain_tile.tileCrop(8, 3);
        redDirtBottomLeft = terrain_tile.tileCrop(6, 4);
        redDirtBottomMiddle = terrain_tile.tileCrop(7, 4);
        redDirtBottomRight = terrain_tile.tileCrop(8, 4);
        redDirtEffect1 = terrain_tile.tileCrop(6, 5);
        redDirtEffect2 = terrain_tile.tileCrop(7, 5);
        redDirtEffect3 = terrain_tile.tileCrop(8, 5);
        redDirtSmallTopLeft = terrain_tile.tileCrop(7, 0);
        redDirtSmallTopRight = terrain_tile.tileCrop(8, 0);
        redDirtSmallBottomLeft = terrain_tile.tileCrop(7, 1);
        redDirtSmallBottomRight = terrain_tile.tileCrop(8, 1);


        greyDirtTopLeft = terrain_tile.tileCrop(9, 2);
        greyDirtTopMiddle = terrain_tile.tileCrop(10, 2);
        greyDirtTopRight = terrain_tile.tileCrop(11, 2);
        greyDirtMiddleLeft = terrain_tile.tileCrop(9, 3);
        greyDirtMiddleMiddle = terrain_tile.tileCrop(10, 3);
        greyDirtMiddleRight = terrain_tile.tileCrop(11, 3);
        greyDirtBottomLeft = terrain_tile.tileCrop(9, 4);
        greyDirtBottomMiddle = terrain_tile.tileCrop(10, 4);
        greyDirtBottomRight = terrain_tile.tileCrop(11, 4);
        greyDirtEffect1 = terrain_tile.tileCrop(9, 5);
        greyDirtEffect2 = terrain_tile.tileCrop(10, 5);
        greyDirtEffect3 = terrain_tile.tileCrop(11, 5);
        greyDirtSmallTopLeft = terrain_tile.tileCrop(10, 0);
        greyDirtSmallTopRight = terrain_tile.tileCrop(11, 0);
        greyDirtSmallBottomLeft = terrain_tile.tileCrop(10, 1);
        greyDirtSmallBottomRight = terrain_tile.tileCrop(11, 1);


        darkDirtTopLeft = terrain_tile.tileCrop(3, 2);
        darkDirtTopMiddle = terrain_tile.tileCrop(4, 2);
        darkDirtTopRight = terrain_tile.tileCrop(5, 2);
        darkDirtMiddleLeft = terrain_tile.tileCrop(3, 3);
        darkDirtMiddleMiddle = terrain_tile.tileCrop(4, 3);
        darkDirtMiddleRight = terrain_tile.tileCrop(5, 3);
        darkDirtBottomLeft = terrain_tile.tileCrop(3, 4);
        darkDirtBottomMiddle = terrain_tile.tileCrop(4, 4);
        darkDirtBottomRight = terrain_tile.tileCrop(5, 4);
        darkDirtEffect1 = terrain_tile.tileCrop(3, 5);
        darkDirtEffect2 = terrain_tile.tileCrop(4, 5);
        darkDirtEffect3 = terrain_tile.tileCrop(5, 5);
        darkDirtSmallTopLeft = terrain_tile.tileCrop(4, 0);
        darkDirtSmallTopRight = terrain_tile.tileCrop(5, 0);
        darkDirtSmallBottomLeft = terrain_tile.tileCrop(4, 1);
        darkDirtSmallBottomRight = terrain_tile.tileCrop(5, 1);

        snowTopLeft = terrain_tile.tileCrop(18, 14);
        snowTopMiddle = terrain_tile.tileCrop(19, 14);
        snowTopRight = terrain_tile.tileCrop(20, 14);
        snowMiddleLeft = terrain_tile.tileCrop(18, 15);
        snowMiddleMiddle = terrain_tile.tileCrop(19, 15);
        snowMiddleRight = terrain_tile.tileCrop(20, 15);
        snowBottomLeft = terrain_tile.tileCrop(18, 16);
        snowBottomMiddle = terrain_tile.tileCrop(19, 16);
        snowBottomRight = terrain_tile.tileCrop(20, 16);
        snowSmallTopLeft = terrain_tile.tileCrop(19, 12);
        snowSmallTopRight = terrain_tile.tileCrop(20, 12);
        snowSmallBottomLeft = terrain_tile.tileCrop(19, 13);
        snowSmallBottomRight = terrain_tile.tileCrop(20, 13);
        snowPattern1 = terrain_tile.tileCrop(18, 17);
        snowPattern2 = terrain_tile.tileCrop(19, 17);
        snowPattern3 = terrain_tile.tileCrop(20, 17);

        snowWaterTopLeft = terrain_tile.tileCrop(21, 19, true);
        snowWaterTopMiddle = terrain_tile.tileCrop(22, 19, true);
        snowWaterTopRight = terrain_tile.tileCrop(23, 19, true);
        snowWaterMiddleLeft = terrain_tile.tileCrop(21, 20, true);
        snowWaterMiddleMiddle = terrain_tile.tileCrop(22, 20, true);
        snowWaterMiddleRight = terrain_tile.tileCrop(23, 20, true);
        snowWaterBottomLeft = terrain_tile.tileCrop(21, 21, true);
        snowWaterBottomMiddle = terrain_tile.tileCrop(22, 21, true);
        snowWaterBottomRight = terrain_tile.tileCrop(23, 21, true);
        snowWaterSmallTopLeft = terrain_tile.tileCrop(22, 17, true);
        snowWaterSmallTopRight = terrain_tile.tileCrop(23, 17, true);
        snowWaterSmallBottomLeft = terrain_tile.tileCrop(22, 18, true);
        snowWaterSmallBottomRight = terrain_tile.tileCrop(23, 18, true);

        sandTopLeft = terrain_tile.tileCrop(18, 8);
        sandTopMiddle = terrain_tile.tileCrop(19, 8);
        sandTopRight = terrain_tile.tileCrop(20, 8);
        sandMiddleLeft = terrain_tile.tileCrop(18, 9);
        sandMiddleMiddle = terrain_tile.tileCrop(19, 9);
        sandMiddleRight = terrain_tile.tileCrop(20, 9);
        sandBottomLeft = terrain_tile.tileCrop(18, 10);
        sandBottomMiddle = terrain_tile.tileCrop(19, 10);
        sandBottomRight = terrain_tile.tileCrop(20, 10);
        sandSmallTopLeft = terrain_tile.tileCrop(19, 6);
        sandSmallTopRight = terrain_tile.tileCrop(20, 6);
        sandSmallBottomLeft = terrain_tile.tileCrop(19, 7);
        sandSmallBottomRight = terrain_tile.tileCrop(20, 7);
        sandPattern1 = terrain_tile.tileCrop(18, 11);
        sandPattern2 = terrain_tile.tileCrop(19, 11);
        sandPattern3 = terrain_tile.tileCrop(20, 11);

        cliffEntranceTop = cliffs_sheet.tileCrop(0, 5, true);
        cliffEntranceBottom = cliffs_sheet.tileCrop(0, 6);
        sandCliffTopLeft = cliffs_sheet.tileCrop(5, 0, true);
        sandCliffTopMiddle = cliffs_sheet.tileCrop(6, 0, true);
        sandCliffTopRight = cliffs_sheet.tileCrop(7, 0, true);
        sandCliffMiddleLeft = cliffs_sheet.tileCrop(5, 1, true);
        sandCliffMiddleMiddle = cliffs_sheet.tileCrop(6, 1);
        sandCliffMiddleRight = cliffs_sheet.tileCrop(7, 1, true);
        sandCliffBottomLeft = cliffs_sheet.tileCrop(5, 2, true);
        sandCliffBottomMiddle = cliffs_sheet.tileCrop(6, 2, true);
        sandCliffBottomRight = cliffs_sheet.tileCrop(7, 2, true);
        sandCliffLeft = cliffs_sheet.tileCrop(5, 3, true);
        sandCliffMiddle = cliffs_sheet.tileCrop(6, 3, true);
        sandCliffRight = cliffs_sheet.tileCrop(7, 3, true);
        sandCliffFootLeft = cliffs_sheet.tileCrop(5, 4, true);
        sandCliffFootMiddle = cliffs_sheet.tileCrop(6, 4, true);
        sandCliffFootRight = cliffs_sheet.tileCrop(7, 4, true);
        sandCliffCornerTopLeft = cliffs_sheet.tileCrop(8, 0, true);
        sandCliffCornerTopRight = cliffs_sheet.tileCrop(9, 0, true);
        sandCliffCornerBottomLeft = cliffs_sheet.tileCrop(8, 1, true);
        sandCliffCornerBottomRight = cliffs_sheet.tileCrop(9, 1, true);
        sandCliffCornerLowerLeft = cliffs_sheet.tileCrop(8, 2, true);
        sandCliffCornerLowerRight = cliffs_sheet.tileCrop(9, 2, true);
        sandCliffCornerLowestLeft = cliffs_sheet.tileCrop(8, 3, true);
        sandCliffCornerLowestRight = cliffs_sheet.tileCrop(9, 3, true);

        grassCliffTopLeft = cliffs_sheet.tileCrop(17, 0, true);
        grassCliffTopMiddle = cliffs_sheet.tileCrop(18, 0, true);
        grassCliffTopRight = cliffs_sheet.tileCrop(19, 0, true);
        grassCliffMiddleLeft = cliffs_sheet.tileCrop(17, 1, true);
        grassCliffMiddleMiddle = cliffs_sheet.tileCrop(18, 1);
        grassCliffMiddleRight = cliffs_sheet.tileCrop(19, 1, true);
        grassCliffBottomLeft = cliffs_sheet.tileCrop(17, 2, true);
        grassCliffBottomMiddle = cliffs_sheet.tileCrop(18, 2, true);
        grassCliffBottomRight = cliffs_sheet.tileCrop(19, 2, true);
        grassCliffLeft = cliffs_sheet.tileCrop(17, 3, true);
        grassCliffMiddle = cliffs_sheet.tileCrop(18, 3, true);
        grassCliffRight = cliffs_sheet.tileCrop(19, 3, true);
        grassCliffFootLeft = cliffs_sheet.tileCrop(17, 4, true);
        grassCliffFootMiddle = cliffs_sheet.tileCrop(18, 4, true);
        grassCliffFootRight = cliffs_sheet.tileCrop(19, 4, true);
        grassCliffCornerTopLeft = cliffs_sheet.tileCrop(20, 0, true);
        grassCliffCornerTopRight = cliffs_sheet.tileCrop(21, 0, true);
        grassCliffCornerBottomLeft = cliffs_sheet.tileCrop(20, 1, true);
        grassCliffCornerBottomRight = cliffs_sheet.tileCrop(21, 1, true);
        grassCliffCornerLowerLeft = cliffs_sheet.tileCrop(20, 2, true);
        grassCliffCornerLowerRight = cliffs_sheet.tileCrop(21, 2, true);
        grassCliffCornerLowestLeft = cliffs_sheet.tileCrop(20, 3, true);
        grassCliffCornerLowestRight = cliffs_sheet.tileCrop(21, 3, true);

        snowCliffTopLeft = cliffs_sheet.tileCrop(29, 0, true);
        snowCliffTopMiddle = cliffs_sheet.tileCrop(30, 0, true);
        snowCliffTopRight = cliffs_sheet.tileCrop(31, 0, true);
        snowCliffMiddleLeft = cliffs_sheet.tileCrop(29, 1, true);
        snowCliffMiddleMiddle = cliffs_sheet.tileCrop(30, 1);
        snowCliffMiddleRight = cliffs_sheet.tileCrop(31, 1, true);
        snowCliffBottomLeft = cliffs_sheet.tileCrop(29, 2, true);
        snowCliffBottomMiddle = cliffs_sheet.tileCrop(30, 2, true);
        snowCliffBottomRight = cliffs_sheet.tileCrop(31, 2, true);
        snowCliffLeft = cliffs_sheet.tileCrop(29, 3, true);
        snowCliffMiddle = cliffs_sheet.tileCrop(30, 3, true);
        snowCliffRight = cliffs_sheet.tileCrop(31, 3, true);
        snowCliffFootLeft = cliffs_sheet.tileCrop(29, 4, true);
        snowCliffFootMiddle = cliffs_sheet.tileCrop(30, 4, true);
        snowCliffFootRight = cliffs_sheet.tileCrop(31, 4, true);
        snowCliffCornerTopLeft = cliffs_sheet.tileCrop(32, 0, true);
        snowCliffCornerTopRight = cliffs_sheet.tileCrop(33, 0, true);
        snowCliffCornerBottomLeft = cliffs_sheet.tileCrop(32, 1, true);
        snowCliffCornerBottomRight = cliffs_sheet.tileCrop(33, 1, true);
        snowCliffCornerLowerLeft = cliffs_sheet.tileCrop(32, 2, true);
        snowCliffCornerLowerRight = cliffs_sheet.tileCrop(33, 2, true);
        snowCliffCornerLowestLeft = cliffs_sheet.tileCrop(32, 3, true);
        snowCliffCornerLowestRight = cliffs_sheet.tileCrop(33, 3, true);

        ladderTop = cliffs_sheet.tileCrop(33, 4);
        ladderMiddle = cliffs_sheet.tileCrop(33, 5);
        ladderBottom = cliffs_sheet.tileCrop(33, 6);

        caveCliffTopLeft = terrain_tile.tileCrop(24, 10, true);
        caveCliffTopMiddle = terrain_tile.tileCrop(25, 10, true);
        caveCliffTopRight = terrain_tile.tileCrop(26, 10, true);
        caveCliffMiddleLeft = terrain_tile.tileCrop(24, 11, true);
        caveCliffMiddleMiddle = terrain_tile.tileCrop(25, 11);
        caveCliffMiddleRight = terrain_tile.tileCrop(26, 11, true);
        caveCliffBottomLeft = terrain_tile.tileCrop(24, 12, true);
        caveCliffBottomMiddle = terrain_tile.tileCrop(25, 12, true);
        caveCliffBottomRight = terrain_tile.tileCrop(26, 12, true);
        caveCliffLeft = terrain_tile.tileCrop(24, 13, true);
        caveCliffMiddle = terrain_tile.tileCrop(25, 13, true);
        caveCliffRight = terrain_tile.tileCrop(26, 13, true);
        caveCliffFootLeft = terrain_tile.tileCrop(24, 14, true);
        caveCliffFootMiddle = terrain_tile.tileCrop(25, 14, true);
        caveCliffFootRight = terrain_tile.tileCrop(26, 14, true);
        caveCliffCornerTopLeft = terrain_tile.tileCrop(30, 5, true);
        caveCliffCornerTopRight = terrain_tile.tileCrop(31, 5, true);
        caveCliffCornerBottomLeft = terrain_tile.tileCrop(30, 6, true);
        caveCliffCornerBottomRight = terrain_tile.tileCrop(31, 6, true);
        caveCliffCornerLowerLeft = terrain_tile.tileCrop(30, 7, true);
        caveCliffCornerLowerRight = terrain_tile.tileCrop(31, 7, true);
        caveCliffCornerLowestLeft = terrain_tile.tileCrop(30, 8, true);
        caveCliffCornerLowestRight = terrain_tile.tileCrop(31, 8, true);

        // Ambiance tiles
        sparkleTile = terrain_tile.tileCrop(16, 18);
        redMushroom = trees_sheet.tileCrop(7, 6, true);
        blueMushroom = trees_sheet.tileCrop(7, 5, true);
        smallRedRocks = terrain_tile.tileCrop(31, 18, true);

        // Animated ambiance details
        sparkles = new BufferedImage[3];
        sparkles[0] = animated_terrain.imageCrop(0, 0);
        sparkles[1] = animated_terrain.imageCrop(1, 0);
        sparkles[2] = animated_terrain.imageCrop(2, 0);


        // Skilling objects
        tree = texture_sheet.imageCrop(1, 2);
        rock = objects3.imageCrop(4, 9);

        whirlpool = new BufferedImage[8];
        whirlpool[0] = whirlPool.imageCrop(0, 0);
        whirlpool[1] = whirlPool.imageCrop(2, 0);
        whirlpool[2] = whirlPool.imageCrop(1, 0);
        whirlpool[3] = whirlPool.imageCrop(3, 0);
        whirlpool[4] = whirlPool.imageCrop(0, 1);
        whirlpool[5] = whirlPool.imageCrop(2, 1);
        whirlpool[6] = whirlPool.imageCrop(1, 1);
        whirlpool[7] = whirlPool.imageCrop(3, 1);

    }

}