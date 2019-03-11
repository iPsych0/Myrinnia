package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.tiles.Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {

    private static final int width = 32, height = 32;

    // Fonts
    public static Font font14, font20, font24, font32, font48, font64;

    // Terrain images (paths, lava, water, etc)
    public static BufferedImage invisible, blackTile;

    public static BufferedImage grassTopLeft, grassTopMiddle, grassTopRight, grassMiddleLeft, grassMiddleMiddle,
    grassMiddleRight, grassBottomLeft, grassBottomMiddle, grassBottomRight, grassSmallTopLeft, grassSmallTopRight,
    grassSmallBottomLeft, grassSmallBottomRight;

    // Object images


    // Ambiance images

    // Animated ambiance images
    public static BufferedImage[] sparkles;
    public static BufferedImage[] campfire;


    // Map item images (trees, rocks, etc)
    public static BufferedImage tree, rock;

    /*
     * Creature Animations
     */
    public static BufferedImage[] player_down, player_up, player_left, player_right;
    public static BufferedImage[] magicProjectile;
    public static BufferedImage[] airCloud1, waterSplash1, movementBoost1, eruption1;
    public static BufferedImage eruptionI, fireballI, mendWoundsI, nimbleFeetI, supersonicDashI;

    // Player attack images
    public static BufferedImage[] player_attackingLeft, player_attackingRight, player_attackingDown, player_attackingUp;

    // Main menu buttons
    public static BufferedImage[] genericButton;

    // Item images
    public static BufferedImage wood, ore, testSword, purpleSword, testAxe, testPickaxe;
    public static BufferedImage[] coins;

    // Enemy images
    public static BufferedImage scorpion;

    // NPC images

    public static BufferedImage lorraine, banker;

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
    public static BufferedImage locked, unlocked;

    public static BufferedImage[] whirlpool;

    // Icons
    public static BufferedImage fishingIcon, woodcuttingIcon, miningIcon, meleeIcon, rangedIcon, magicIcon;

    public static BufferedImage shopWindow;

    public static BufferedImage controlsScreen;

    public static BufferedImage[][] puzzlePieces;

    public static void init() {

        /*
         * Fonts
         */
        font14 = FontLoader.loadFont("res/fonts/IBMPlexSans-Regular.otf", 14);
        font20 = FontLoader.loadFont("res/fonts/IBMPlexSans-Regular.otf", 20);
        font24 = FontLoader.loadFont("res/fonts/IBMPlexSans-Regular.otf", 24);
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
        SpriteSheet texture_sheet = new SpriteSheet("/textures/textures.png");
        SpriteSheet objects17 = new SpriteSheet("/textures/object_sprites17.png");
        SpriteSheet objects3 = new SpriteSheet("/textures/object_sprites3.png");
        /*
         * Make skilling sheet for this
         */
        SpriteSheet whirlPool = new SpriteSheet("/textures/whirlpool.png");

        SpriteSheet player_sheet = new SpriteSheet("/textures/herosprites.png");
        /*
         * Add items to this
         */
        SpriteSheet item_sheet = new SpriteSheet("/textures/itemsprites.png");

        SpriteSheet enemy_sheet = new SpriteSheet("/textures/enemysprites.png");
        /*
         * Crop ShopKeeperNPC out
         */
        SpriteSheet lorraine_sprites = new SpriteSheet("/textures/lorrainesprites.png");
        /*
         * Add animated tiles
         */
        SpriteSheet animated_terrain = new SpriteSheet("/textures/animated_terrain.png");

        /*
         * Creature Animations
         */
        SpriteSheet ability_sheet = new SpriteSheet("/textures/animations/abilitysheet.png");
        SpriteSheet airCloud1_sheet = new SpriteSheet("/textures/animations/air_cloud1.png");
        SpriteSheet waterSplash1_sheet = new SpriteSheet("/textures/animations/water_splash1.png");
        SpriteSheet movementBoost1_sheet = new SpriteSheet("/textures/animations/movement_boost1.png");
        SpriteSheet eruption1_sheet = new SpriteSheet("/textures/animations/eruption1.png");

        /*
         * All Tiled Sprites
         */
        SpriteSheet myrinnia_tiles = new SpriteSheet("/textures/myrinnia_tileset.png", true);

        // Invisible Tile
        invisible = myrinnia_tiles.imageCrop(19, 29);
        Tiles.tiles[0] = new Tiles(invisible, 0, false);
        blackTile = texture_sheet.imageCrop(3, 6);

        grassTopLeft = myrinnia_tiles.tileCrop(0, 0);
        grassTopMiddle = myrinnia_tiles.tileCrop(1, 0);
        grassTopRight = myrinnia_tiles.tileCrop(2, 0);
        grassMiddleLeft = myrinnia_tiles.tileCrop(0, 1);
        grassMiddleMiddle = myrinnia_tiles.tileCrop(1, 1);
        grassMiddleRight = myrinnia_tiles.tileCrop(2, 1);
        grassBottomLeft = myrinnia_tiles.tileCrop(0, 2);
        grassBottomMiddle = myrinnia_tiles.tileCrop(1, 2);
        grassBottomRight = myrinnia_tiles.tileCrop(2, 2);
        grassSmallTopLeft = myrinnia_tiles.tileCrop(3, 0);
        grassSmallTopRight = myrinnia_tiles.tileCrop(4, 0);
        grassSmallBottomLeft = myrinnia_tiles.tileCrop(3, 1);
        grassSmallBottomRight = myrinnia_tiles.tileCrop(4, 1);


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
        meleeIcon = ui_sheet.imageCrop(2, 1);
        rangedIcon = ui_sheet.imageCrop(3,1);
        magicIcon = ui_sheet.imageCrop(4,1);

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

        /*
         * Generic Button Sprites
         */
        genericButton = new BufferedImage[2];
        genericButton[0] = ui_sheet.imageCrop(9, 0, width * 7, height * 3);
        genericButton[1] = ui_sheet.imageCrop(17, 0, width * 7, height * 3);

        locked = ui_sheet.imageCrop(0, 1, 16, 16);
        unlocked = ui_sheet.imageCrop(1, 1, 16, 16);

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
         * Ability Icons
         */
        eruptionI = ability_sheet.imageCrop(0,0);
        fireballI = ability_sheet.imageCrop(1,0);
        mendWoundsI = ability_sheet.imageCrop(2,0);
        nimbleFeetI = ability_sheet.imageCrop(3,0);
        supersonicDashI = ability_sheet.imageCrop(4,0);


        /*
         * Creature Animations
         */
        airCloud1 = new BufferedImage[7];
        airCloud1[0] = airCloud1_sheet.imageCrop(0, 0);
        airCloud1[1] = airCloud1_sheet.imageCrop(1, 0);
        airCloud1[2] = airCloud1_sheet.imageCrop(2, 0);
        airCloud1[3] = airCloud1_sheet.imageCrop(3, 0);
        airCloud1[4] = airCloud1_sheet.imageCrop(4, 0);
        airCloud1[5] = airCloud1_sheet.imageCrop(5, 0);
        airCloud1[6] = airCloud1_sheet.imageCrop(6, 0);

        waterSplash1 = new BufferedImage[5];
        waterSplash1[0] = waterSplash1_sheet.imageCrop(0,0);
        waterSplash1[1] = waterSplash1_sheet.imageCrop(1,0);
        waterSplash1[2] = waterSplash1_sheet.imageCrop(2,0);
        waterSplash1[3] = waterSplash1_sheet.imageCrop(3,0);
        waterSplash1[4] = waterSplash1_sheet.imageCrop(4,0);

        movementBoost1 = new BufferedImage[7];
        movementBoost1[0] = movementBoost1_sheet.imageCrop(0,0);
        movementBoost1[1] = movementBoost1_sheet.imageCrop(1,0);
        movementBoost1[2] = movementBoost1_sheet.imageCrop(2,0);
        movementBoost1[3] = movementBoost1_sheet.imageCrop(3,0);
        movementBoost1[4] = movementBoost1_sheet.imageCrop(4,0);
        movementBoost1[5] = movementBoost1_sheet.imageCrop(5,0);
        movementBoost1[6] = movementBoost1_sheet.imageCrop(6,0);

        eruption1 = new BufferedImage[7];
        eruption1[0] = eruption1_sheet.imageCrop(0,0);
        eruption1[1] = eruption1_sheet.imageCrop(1,0);
        eruption1[2] = eruption1_sheet.imageCrop(2,0);
        eruption1[3] = eruption1_sheet.imageCrop(3,0);
        eruption1[4] = eruption1_sheet.imageCrop(4,0);
        eruption1[5] = eruption1_sheet.imageCrop(5,0);
        eruption1[6] = eruption1_sheet.imageCrop(6,0);

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