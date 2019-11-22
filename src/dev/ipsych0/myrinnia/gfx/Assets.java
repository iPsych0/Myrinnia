package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.utils.MapLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Assets {

    public static final int WIDTH = 32, HEIGHT = 32;
    private static List<SpriteSheet> tileSheets;

    // Fonts
    public static Font font14;
    public static Font font20;
    public static Font font24;
    public static Font font32;
    public static Font font40;
    public static Font font48;
    public static Font font64;

    // Map item images (trees, rocks, etc)
    public static BufferedImage tree, rock;
    public static BufferedImage bountyBoard1, bountyBoard2;
    public static BufferedImage bountyContract;

    /*
     * Creature Animations
     */
    public static BufferedImage[] player_down, player_up, player_left, player_right;
    public static BufferedImage[] aquatic_cultist_down, aquatic_cultist_up, aquatic_cultist_left, aquatic_cultist_right;
    public static BufferedImage[] fireProjectile;
    public static BufferedImage[] waterProjectile;
    public static BufferedImage[] earthProjectile;
    public static BufferedImage[] airProjectile;
    public static BufferedImage[] airCloud1, waterSplash1, movementBoost1, eruption1;
    public static BufferedImage eruptionI, fireballI, mendWoundsI, nimbleFeetI, supersonicDashI;

    // Player attack images
    public static BufferedImage[] player_melee_left, player_melee_right, player_melee_down, player_melee_up;
    public static BufferedImage[] player_magic_left, player_magic_right, player_magic_down, player_magic_up;
    public static BufferedImage[] player_ranged_left, player_ranged_right, player_ranged_down, player_ranged_up;

    // Main menu buttons
    public static BufferedImage[] genericButton;
    public static BufferedImage[] scrollUpButton, scrollDownButton;

    // Item images
    public static BufferedImage wood, ore, beginnersSword, beginnersStaff, ryansAxe, simplePickaxe, simpleAxe, beginnersBow;
    public static BufferedImage[] coins;

    // Enemy images
    public static BufferedImage[] blueScorpionUp, blueScorpionDown, blueScorpionLeft, blueScorpionRight;
    public static BufferedImage[] blueCrabUp, blueCrabDown, blueCrabLeft, blueCrabRight;
    public static BufferedImage[] azureBatUp, azureBatDown, azureBatLeft, azureBatRight;
    public static BufferedImage[] angryOwl, angryFlower;

    // NPC images
    public static BufferedImage shopKeeper, femaleBanker;

    // Generic NPCs
    public static BufferedImage[] genericFemale1Down, genericFemale1Left, genericFemale1Right, genericFemale1Up;
    public static BufferedImage[] genericFemale2Down, genericFemale2Left, genericFemale2Right, genericFemale2Up;
    public static BufferedImage[] genericFemale3Down, genericFemale3Left, genericFemale3Right, genericFemale3Up;
    public static BufferedImage[] genericFemale4Down, genericFemale4Left, genericFemale4Right, genericFemale4Up;
    public static BufferedImage[] genericFemale5Down, genericFemale5Left, genericFemale5Right, genericFemale5Up;
    public static BufferedImage[] genericFemale6Down, genericFemale6Left, genericFemale6Right, genericFemale6Up;
    public static BufferedImage[] genericFemale7Down, genericFemale7Left, genericFemale7Right, genericFemale7Up;
    public static BufferedImage[] genericFemale8Down, genericFemale8Left, genericFemale8Right, genericFemale8Up;
    public static BufferedImage[] genericFemale9Down, genericFemale9Left, genericFemale9Right, genericFemale9Up;
    public static BufferedImage[] genericFemale10Down, genericFemale10Left, genericFemale10Right, genericFemale10Up;
    public static BufferedImage[] genericFemale11Down, genericFemale11Left, genericFemale11Right, genericFemale11Up;
    public static BufferedImage[] genericFemale12Down, genericFemale12Left, genericFemale12Right, genericFemale12Up;
    public static BufferedImage[] genericMale1Down, genericMale1Left, genericMale1Right, genericMale1Up;
    public static BufferedImage[] genericMale2Down, genericMale2Left, genericMale2Right, genericMale2Up;
    public static BufferedImage[] genericMale3Down, genericMale3Left, genericMale3Right, genericMale3Up;
    public static BufferedImage[] genericMale4Down, genericMale4Left, genericMale4Right, genericMale4Up;
    public static BufferedImage[] genericMale5Down, genericMale5Left, genericMale5Right, genericMale5Up;
    public static BufferedImage[] genericMale6Down, genericMale6Left, genericMale6Right, genericMale6Up;
    public static BufferedImage[] genericMale7Down, genericMale7Left, genericMale7Right, genericMale7Up;
    public static BufferedImage[] genericMale8Down, genericMale8Left, genericMale8Right, genericMale8Up;
    public static BufferedImage[] genericMale9Down, genericMale9Left, genericMale9Right, genericMale9Up;
    public static BufferedImage[] genericMale10Down, genericMale10Left, genericMale10Right, genericMale10Up;
    public static BufferedImage[] genericMale11Down, genericMale11Left, genericMale11Right, genericMale11Up;
    public static BufferedImage[] genericMale12Down, genericMale12Left, genericMale12Right, genericMale12Up;

    public static BufferedImage[] portAzureRyanDown, portAzureRyanLeft, portAzureRyanRight, portAzureRyanUp;
    public static BufferedImage[] portAzureDouglasDown, portAzureDouglasLeft, portAzureDouglasRight, portAzureDouglasUp;

    // Equipment UI
    public static BufferedImage earringSlot;
    public static BufferedImage mainhandSlot;
    public static BufferedImage glovesSlot;
    public static BufferedImage ringSlot1;
    public static BufferedImage helmSlot;
    public static BufferedImage bodySlot;
    public static BufferedImage legsSlot;
    public static BufferedImage bootsSlot;
    public static BufferedImage amuletSlot;
    public static BufferedImage offhandSlot;
    public static BufferedImage capeSlot;
    public static BufferedImage ringSlot2;
    public static BufferedImage[] equipmentPlaceHolders;

    // Crafting UI
    public static BufferedImage undiscovered;

    // HP Overlay UI
    public static BufferedImage craftingIcon, characterIcon, abilitiesIcon, questsIcon, mapIcon;
    public static BufferedImage locked, unlocked;

    public static BufferedImage[] whirlpool;

    // Icons
    public static BufferedImage fishingIcon, woodcuttingIcon, miningIcon, meleeIcon, bountyHunterIcon;
    public static BufferedImage chillIcon, poisonIcon, burnIcon, bleedIcon, stunIcon;
    public static BufferedImage strBuffIcon, dexBuffIcon, intBuffIcon, defBuffIcon, vitBuffIcon, atkSpdBuffIcon,
            movSpdBuffIcon;

    public static BufferedImage uiWindow;

    public static BufferedImage[][] puzzlePieces;

    // Port Azure
    public static BufferedImage portAzureMayor, portAzureSailor, elderSelwyn;

    public static void init() {

        MapLoader.setWorldDoc(Handler.initialWorldPath);

        /*
         * Fonts
         */
        font14 = FontLoader.loadFont("/fonts/IBMPlexSans-Regular.otf", 14);
        font20 = FontLoader.loadFont("/fonts/IBMPlexSans-Regular.otf", 20);
        font24 = FontLoader.loadFont("/fonts/IBMPlexSans-Regular.otf", 24);
        font32 = FontLoader.loadFont("/fonts/IBMPlexSans-Regular.otf", 32);
        font40 = FontLoader.loadFont("/fonts/IBMPlexSans-Regular.otf", 40);
        font48 = FontLoader.loadFont("/fonts/IBMPlexSans-Regular.otf", 48);
        font64 = FontLoader.loadFont("/fonts/IBMPlexSans-Regular.otf", 64);


        /*
         * Sprite Sheets
         */
        SpriteSheet ui_sheet = new SpriteSheet("/textures/ui-items-new.png");
        SpriteSheet projectiles = new SpriteSheet("/textures/projectiles.png");
        SpriteSheet equipSlots = new SpriteSheet("/textures/equipment_placeholders.png");
        /*
         * Make skilling sheet for this
         */
        SpriteSheet whirlPool = new SpriteSheet("/textures/whirlpool.png");

        /*
         * Player/NPCs
         */
        SpriteSheet player_sheet = new SpriteSheet("/textures/npc_sprites/player.png");
        SpriteSheet azureal_island_npcs = new SpriteSheet("/textures/npc_sprites/azureal_island_npcs.png");
        SpriteSheet generic_males1 = new SpriteSheet("/textures/npc_sprites/generic_males1.png");
        SpriteSheet generic_females1 = new SpriteSheet("/textures/npc_sprites/generic_females1.png");

        /*
         * Add items to this
         */
        SpriteSheet item_sheet = new SpriteSheet("/textures/itemsprites.png");

        SpriteSheet enemy_sheet1 = new SpriteSheet("/textures/enemy_sprites/monster1.png");
        SpriteSheet enemy_sheet2 = new SpriteSheet("/textures/enemy_sprites/monster2.png");

        /*
         * Ability Animations
         */
        SpriteSheet ability_icons = new SpriteSheet("/textures/animations/abilitysheet.png");
        SpriteSheet ability_animations = new SpriteSheet("/textures/animations/ability_animations.png");

        /*
         * All Tiled Sprites
         */
        tileSheets = new ArrayList<>();
        tileSheets.add(new SpriteSheet("/textures/tiles/castle.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/desert.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/dungeon.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/house.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/inside.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/outside.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/terrain.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/water.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/beach.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/dark_dimension.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/farm_fort.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/inside2.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/inside3.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/outside2.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/outside3.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/outside4.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/ruindungeons_sheet_full.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/ship_tileset.png", true));

        Tile.tiles = new Tile[MapLoader.getTileCount()];

        for (SpriteSheet tileSheet : tileSheets) {
            for (int y = 0; y < tileSheet.getSheet().getHeight() / 32; y++) {
                for (int x = 0; x < tileSheet.getSheet().getWidth() / 32; x++) {
                    tileSheet.tileCrop(x, y);
                }
            }
        }

//        puzzlePieces = new BufferedImage[rsCastlePuzzle.getSheet().getWidth() / 32][rsCastlePuzzle.getSheet().getHeight() / 32];
//        for (int y = 0; y < rsCastlePuzzle.getSheet().getHeight() / 32; y++) {
//            for (int x = 0; x < rsCastlePuzzle.getSheet().getWidth() / 32; x++) {
//                puzzlePieces[x][y] = rsCastlePuzzle.imageCrop(x, y);
//            }
//        }

        /*
         * Game UI Sprites
         */
        equipmentPlaceHolders = new BufferedImage[12];

        equipmentPlaceHolders[0] = earringSlot = equipSlots.imageCrop(0, 0);
        equipmentPlaceHolders[1] = mainhandSlot = equipSlots.imageCrop(0, 1);
        equipmentPlaceHolders[2] = glovesSlot = equipSlots.imageCrop(0, 2);
        equipmentPlaceHolders[3] = ringSlot1 = equipSlots.imageCrop(0, 3);
        equipmentPlaceHolders[4] = helmSlot = equipSlots.imageCrop(1, 0);
        equipmentPlaceHolders[5] = bodySlot = equipSlots.imageCrop(1, 1);
        equipmentPlaceHolders[6] = legsSlot = equipSlots.imageCrop(1, 2);
        equipmentPlaceHolders[7] = bootsSlot = equipSlots.imageCrop(1, 3);
        equipmentPlaceHolders[8] = amuletSlot = equipSlots.imageCrop(2, 0);
        equipmentPlaceHolders[9] = offhandSlot = equipSlots.imageCrop(2, 1);
        equipmentPlaceHolders[10] = capeSlot = equipSlots.imageCrop(2, 2);
        equipmentPlaceHolders[11] = ringSlot2 = equipSlots.imageCrop(2, 3);

        uiWindow = ui_sheet.imageCrop(0, 4, WIDTH * 6, HEIGHT * 6);

        fireProjectile = new BufferedImage[3];
        fireProjectile[0] = projectiles.imageCrop(3, 4);
        fireProjectile[1] = projectiles.imageCrop(4, 4);
        fireProjectile[2] = projectiles.imageCrop(5, 4);

        airProjectile = new BufferedImage[3];
        airProjectile[0] = projectiles.imageCrop(9, 4);
        airProjectile[1] = projectiles.imageCrop(10, 4);
        airProjectile[2] = projectiles.imageCrop(1, 4);

        earthProjectile = new BufferedImage[3];
        earthProjectile[0] = projectiles.imageCrop(3, 0);
        earthProjectile[1] = projectiles.imageCrop(4, 0);
        earthProjectile[2] = projectiles.imageCrop(5, 0);

        waterProjectile = new BufferedImage[3];
        waterProjectile[0] = projectiles.imageCrop(9, 0);
        waterProjectile[1] = projectiles.imageCrop(10, 0);
        waterProjectile[2] = projectiles.imageCrop(11, 0);

        // Skill icons
        fishingIcon = ui_sheet.imageCrop(2, 0);
        woodcuttingIcon = ui_sheet.imageCrop(3, 0);
        miningIcon = ui_sheet.imageCrop(1, 0);
        meleeIcon = ui_sheet.imageCrop(2, 1);
        bountyHunterIcon = ui_sheet.imageCrop(5, 1);

        // Condition icons
        chillIcon = ui_sheet.imageCrop(0, 2);
        poisonIcon = ui_sheet.imageCrop(1, 2);
        burnIcon = ui_sheet.imageCrop(2, 2);
        bleedIcon = ui_sheet.imageCrop(3, 2);
        stunIcon = ui_sheet.imageCrop(4, 2);

        // Buff icons
        strBuffIcon = ui_sheet.imageCrop(0, 3);
        dexBuffIcon = ui_sheet.imageCrop(1, 3);
        intBuffIcon = ui_sheet.imageCrop(2, 3);
        defBuffIcon = ui_sheet.imageCrop(3, 3);
        vitBuffIcon = ui_sheet.imageCrop(4, 3);
        atkSpdBuffIcon = ui_sheet.imageCrop(5, 3);
        movSpdBuffIcon = ui_sheet.imageCrop(6, 3);

        // HP Overlay sprites
        abilitiesIcon = ui_sheet.imageCrop(4, 0);
        characterIcon = ui_sheet.imageCrop(5, 0);
        craftingIcon = ui_sheet.imageCrop(6, 0);
        questsIcon = ui_sheet.imageCrop(7, 0);
        mapIcon = ui_sheet.imageCrop(8, 0);

        // Crafting UI sprites
        undiscovered = ui_sheet.imageCrop(0, 0);

        /*
         * Generic Button Sprites
         */
        genericButton = new BufferedImage[3];
        genericButton[0] = ui_sheet.imageCrop(0, 10, WIDTH * 3, HEIGHT);
        genericButton[1] = ui_sheet.imageCrop(3, 10, WIDTH * 3, HEIGHT);
        genericButton[2] = ui_sheet.imageCrop(6, 10, WIDTH * 3, HEIGHT);

        scrollDownButton = new BufferedImage[2];
        scrollDownButton[0] = ui_sheet.imageCrop(6, 4);
        scrollDownButton[1] = ui_sheet.imageCrop(6, 5);

        scrollUpButton = new BufferedImage[2];
        scrollUpButton[0] = ui_sheet.imageCrop(6, 6);
        scrollUpButton[1] = ui_sheet.imageCrop(6, 7);

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
        beginnersSword = item_sheet.imageCrop(1, 0);
        beginnersStaff = item_sheet.imageCrop(2, 0);
        beginnersBow = item_sheet.imageCrop(3, 0);
        ryansAxe = item_sheet.imageCrop(0, 6);
        simplePickaxe = item_sheet.imageCrop(0, 7);
        bountyContract = item_sheet.imageCrop(0, 8);
        simpleAxe = item_sheet.imageCrop(0, 9);


        /*
         * Enemy Animations
         */
        angryOwl = new BufferedImage[2];
        angryOwl[0] = enemy_sheet1.imageCrop(6, 8, WIDTH, HEIGHT);
        angryOwl[1] = enemy_sheet1.imageCrop(7, 8, WIDTH, HEIGHT);

        angryFlower = new BufferedImage[2];
        angryFlower[0] = enemy_sheet1.imageCrop(6, 9, WIDTH, HEIGHT);
        angryFlower[1] = enemy_sheet1.imageCrop(7, 9, WIDTH, HEIGHT);

        blueScorpionDown = enemy_sheet1.npcCrop(9, 0, WIDTH, HEIGHT);
        blueScorpionLeft = enemy_sheet1.npcCrop(9, 1, WIDTH, HEIGHT);
        blueScorpionRight = enemy_sheet1.npcCrop(9, 2, WIDTH, HEIGHT);
        blueScorpionUp = enemy_sheet1.npcCrop(9, 3, WIDTH, HEIGHT);

        blueCrabDown = enemy_sheet2.npcCrop(0, 0, WIDTH, HEIGHT);
        blueCrabLeft = enemy_sheet2.npcCrop(0, 1, WIDTH, HEIGHT);
        blueCrabRight = enemy_sheet2.npcCrop(0, 2, WIDTH, HEIGHT);
        blueCrabUp = enemy_sheet2.npcCrop(0, 3, WIDTH, HEIGHT);

        azureBatDown = enemy_sheet1.npcCrop(9, 4, WIDTH, HEIGHT);
        azureBatLeft = enemy_sheet1.npcCrop(9, 5, WIDTH, HEIGHT);
        azureBatRight = enemy_sheet1.npcCrop(9, 6, WIDTH, HEIGHT);
        azureBatUp = enemy_sheet1.npcCrop(9, 7, WIDTH, HEIGHT);

        // NPC Sprites
//
        elderSelwyn = azureal_island_npcs.singleNpcCrop(1, 0);
        portAzureMayor = azureal_island_npcs.singleNpcCrop(4, 0);
        portAzureSailor = azureal_island_npcs.singleNpcCrop(7, 3);
        shopKeeper = azureal_island_npcs.singleNpcCrop(10, 0);
        femaleBanker = azureal_island_npcs.singleNpcCrop(13, 0);

        // Generic female NPCs
        genericFemale1Down = generic_females1.npcCrop(0, 0);
        genericFemale1Left = generic_females1.npcCrop(0, 1);
        genericFemale1Right = generic_females1.npcCrop(0, 2);
        genericFemale1Up = generic_females1.npcCrop(0, 3);

        genericFemale2Down = generic_females1.npcCrop(3, 0);
        genericFemale2Left = generic_females1.npcCrop(3, 1);
        genericFemale2Right = generic_females1.npcCrop(3, 2);
        genericFemale2Up = generic_females1.npcCrop(3, 3);

        genericFemale3Down = generic_females1.npcCrop(6, 0);
        genericFemale3Left = generic_females1.npcCrop(6, 1);
        genericFemale3Right = generic_females1.npcCrop(6, 2);
        genericFemale3Up = generic_females1.npcCrop(6, 3);

        genericFemale4Down = generic_females1.npcCrop(9, 0);
        genericFemale4Left = generic_females1.npcCrop(9, 1);
        genericFemale4Right = generic_females1.npcCrop(9, 2);
        genericFemale4Up = generic_females1.npcCrop(9, 3);

        genericFemale5Down = generic_females1.npcCrop(12, 0);
        genericFemale5Left = generic_females1.npcCrop(12, 1);
        genericFemale5Right = generic_females1.npcCrop(12, 2);
        genericFemale5Up = generic_females1.npcCrop(12, 3);

        genericFemale6Down = generic_females1.npcCrop(15, 0);
        genericFemale6Left = generic_females1.npcCrop(15, 1);
        genericFemale6Right = generic_females1.npcCrop(15, 2);
        genericFemale6Up = generic_females1.npcCrop(15, 3);

        genericFemale7Down = generic_females1.npcCrop(0, 4);
        genericFemale7Left = generic_females1.npcCrop(0, 5);
        genericFemale7Right = generic_females1.npcCrop(0, 6);
        genericFemale7Up = generic_females1.npcCrop(0, 7);

        genericFemale8Down = generic_females1.npcCrop(3, 4);
        genericFemale8Left = generic_females1.npcCrop(3, 5);
        genericFemale8Right = generic_females1.npcCrop(3, 6);
        genericFemale8Up = generic_females1.npcCrop(3, 7);

        genericFemale9Down = generic_females1.npcCrop(6, 4);
        genericFemale9Left = generic_females1.npcCrop(6, 5);
        genericFemale9Right = generic_females1.npcCrop(6, 6);
        genericFemale9Up = generic_females1.npcCrop(6, 7);

        genericFemale10Down = generic_females1.npcCrop(9, 4);
        genericFemale10Left = generic_females1.npcCrop(9, 5);
        genericFemale10Right = generic_females1.npcCrop(9, 6);
        genericFemale10Up = generic_females1.npcCrop(9, 7);

        genericFemale11Down = generic_females1.npcCrop(12, 4);
        genericFemale11Left = generic_females1.npcCrop(12, 5);
        genericFemale11Right = generic_females1.npcCrop(12, 6);
        genericFemale11Up = generic_females1.npcCrop(12, 7);

        genericFemale12Down = generic_females1.npcCrop(15, 4);
        genericFemale12Left = generic_females1.npcCrop(15, 5);
        genericFemale12Right = generic_females1.npcCrop(15, 6);
        genericFemale12Up = generic_females1.npcCrop(15, 7);

        // Generic male NPCs
        genericMale1Down = generic_males1.npcCrop(0, 0);
        genericMale1Left = generic_males1.npcCrop(0, 1);
        genericMale1Right = generic_males1.npcCrop(0, 2);
        genericMale1Up = generic_males1.npcCrop(0, 3);

        genericMale2Down = generic_males1.npcCrop(3, 0);
        genericMale2Left = generic_males1.npcCrop(3, 1);
        genericMale2Right = generic_males1.npcCrop(3, 2);
        genericMale2Up = generic_males1.npcCrop(3, 3);

        genericMale3Down = generic_males1.npcCrop(6, 0);
        genericMale3Left = generic_males1.npcCrop(6, 1);
        genericMale3Right = generic_males1.npcCrop(6, 2);
        genericMale3Up = generic_males1.npcCrop(6, 3);

        genericMale4Down = generic_males1.npcCrop(9, 0);
        genericMale4Left = generic_males1.npcCrop(9, 1);
        genericMale4Right = generic_males1.npcCrop(9, 2);
        genericMale4Up = generic_males1.npcCrop(9, 3);

        genericMale5Down = generic_males1.npcCrop(12, 0);
        genericMale5Left = generic_males1.npcCrop(12, 1);
        genericMale5Right = generic_males1.npcCrop(12, 2);
        genericMale5Up = generic_males1.npcCrop(12, 3);

        genericMale6Down = generic_males1.npcCrop(15, 0);
        genericMale6Left = generic_males1.npcCrop(15, 1);
        genericMale6Right = generic_males1.npcCrop(15, 2);
        genericMale6Up = generic_males1.npcCrop(15, 3);

        genericMale7Down = generic_males1.npcCrop(0, 4);
        genericMale7Left = generic_males1.npcCrop(0, 5);
        genericMale7Right = generic_males1.npcCrop(0, 6);
        genericMale7Up = generic_males1.npcCrop(0, 7);

        genericMale8Down = generic_males1.npcCrop(3, 4);
        genericMale8Left = generic_males1.npcCrop(3, 5);
        genericMale8Right = generic_males1.npcCrop(3, 6);
        genericMale8Up = generic_males1.npcCrop(3, 7);

        genericMale9Down = generic_males1.npcCrop(6, 4);
        genericMale9Left = generic_males1.npcCrop(6, 5);
        genericMale9Right = generic_males1.npcCrop(6, 6);
        genericMale9Up = generic_males1.npcCrop(6, 7);

        genericMale10Down = generic_males1.npcCrop(9, 4);
        genericMale10Left = generic_males1.npcCrop(9, 5);
        genericMale10Right = generic_males1.npcCrop(9, 6);
        genericMale10Up = generic_males1.npcCrop(9, 7);

        genericMale11Down = generic_males1.npcCrop(12, 4);
        genericMale11Left = generic_males1.npcCrop(12, 5);
        genericMale11Right = generic_males1.npcCrop(12, 6);
        genericMale11Up = generic_males1.npcCrop(12, 7);

        genericMale12Down = generic_males1.npcCrop(15, 4);
        genericMale12Left = generic_males1.npcCrop(15, 5);
        genericMale12Right = generic_males1.npcCrop(15, 6);
        genericMale12Up = generic_males1.npcCrop(15, 7);

        portAzureRyanDown = azureal_island_npcs.npcCrop(0, 4);
        portAzureRyanLeft = azureal_island_npcs.npcCrop(0, 5);
        portAzureRyanRight = azureal_island_npcs.npcCrop(0, 6);
        portAzureRyanUp = azureal_island_npcs.npcCrop(0, 7);

        portAzureDouglasDown = azureal_island_npcs.npcCrop(3, 4);
        portAzureDouglasLeft = azureal_island_npcs.npcCrop(3, 5);
        portAzureDouglasRight = azureal_island_npcs.npcCrop(3, 6);
        portAzureDouglasUp = azureal_island_npcs.npcCrop(3, 7);

        /*
         * Player Animations
         */

        player_magic_down = player_sheet.npcCrop(3, 0);
        player_magic_left = player_sheet.npcCrop(3, 1);
        player_magic_right = player_sheet.npcCrop(3, 2);
        player_magic_up = player_sheet.npcCrop(3, 3);

        player_melee_down = player_sheet.npcCrop(6, 0);
        player_melee_left = player_sheet.npcCrop(6, 1);
        player_melee_right = player_sheet.npcCrop(6, 2);
        player_melee_up = player_sheet.npcCrop(6, 3);

        player_ranged_down = player_sheet.npcCrop(9, 0);
        player_ranged_left = player_sheet.npcCrop(9, 1);
        player_ranged_right = player_sheet.npcCrop(9, 2);
        player_ranged_up = player_sheet.npcCrop(9, 3);

        player_down = player_sheet.npcCrop(0, 0);
        player_left = player_sheet.npcCrop(0, 1);
        player_right = player_sheet.npcCrop(0, 2);
        player_up = player_sheet.npcCrop(0, 3);

        /*
         * Ability Icons
         */
        eruptionI = ability_icons.imageCrop(0, 0);
        fireballI = ability_icons.imageCrop(1, 0);
        mendWoundsI = ability_icons.imageCrop(2, 0);
        nimbleFeetI = ability_icons.imageCrop(3, 0);
        supersonicDashI = ability_icons.imageCrop(4, 0);


        /*
         * Creature Animations
         */

//        aquatic_cultist_down = npc_sheet1.npcCrop(3, 0);
//        aquatic_cultist_left = npc_sheet1.npcCrop(3, 1);
//        aquatic_cultist_right = npc_sheet1.npcCrop(3, 2);
//        aquatic_cultist_up = npc_sheet1.npcCrop(3, 3);

        airCloud1 = new BufferedImage[7];
        airCloud1[0] = ability_animations.imageCrop(0, 0);
        airCloud1[1] = ability_animations.imageCrop(1, 0);
        airCloud1[2] = ability_animations.imageCrop(2, 0);
        airCloud1[3] = ability_animations.imageCrop(3, 0);
        airCloud1[4] = ability_animations.imageCrop(4, 0);
        airCloud1[5] = ability_animations.imageCrop(5, 0);
        airCloud1[6] = ability_animations.imageCrop(6, 0);

        waterSplash1 = new BufferedImage[5];
        waterSplash1[0] = ability_animations.imageCrop(0, 6);
        waterSplash1[1] = ability_animations.imageCrop(1, 6);
        waterSplash1[2] = ability_animations.imageCrop(2, 6);
        waterSplash1[3] = ability_animations.imageCrop(3, 6);
        waterSplash1[4] = ability_animations.imageCrop(4, 6);

        movementBoost1 = new BufferedImage[7];
        movementBoost1[0] = ability_animations.imageCrop(0, 3);
        movementBoost1[1] = ability_animations.imageCrop(1, 3);
        movementBoost1[2] = ability_animations.imageCrop(2, 3);
        movementBoost1[3] = ability_animations.imageCrop(3, 3);
        movementBoost1[4] = ability_animations.imageCrop(4, 3);
        movementBoost1[5] = ability_animations.imageCrop(5, 3);
        movementBoost1[6] = ability_animations.imageCrop(6, 3);

        eruption1 = new BufferedImage[7];
        eruption1[0] = ability_animations.imageCrop(0, 1);
        eruption1[1] = ability_animations.imageCrop(1, 1);
        eruption1[2] = ability_animations.imageCrop(2, 1);
        eruption1[3] = ability_animations.imageCrop(3, 1);
        eruption1[4] = ability_animations.imageCrop(4, 1);
        eruption1[5] = ability_animations.imageCrop(5, 1);
        eruption1[6] = ability_animations.imageCrop(6, 1);

        // Skilling objects
//        tree = texture_sheet.imageCrop(1, 2);
//        rock = texture_sheet.imageCrop(0, 3);
        bountyBoard1 = getSheetByFilename("outside3.png").imageCrop(6, 11, WIDTH * 2, HEIGHT * 2);
        bountyBoard2 = getSheetByFilename("outside3.png").imageCrop(6, 14, WIDTH * 2, HEIGHT * 2);

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

    public static BufferedImage[][] getAnimationByTag(String tag) {
        BufferedImage[][] frames = new BufferedImage[4][3];
        int count = 0;
        for (Field f : Assets.class.getDeclaredFields()) {
            if (f.getName().startsWith(tag)) {
                try {
                    if (f.getName().contains("Down")) {
                        frames[0] = (BufferedImage[]) f.get(null);
                        count++;
                    } else if (f.getName().contains("Left")) {
                        frames[1] = (BufferedImage[]) f.get(null);
                        count++;
                    } else if (f.getName().contains("Right")) {
                        frames[2] = (BufferedImage[]) f.get(null);
                        count++;
                    } else if (f.getName().contains("Up")) {
                        frames[3] = (BufferedImage[]) f.get(null);
                        count++;
                    }
                    if (count == 4) {
                        return frames;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return frames;
    }

    private static SpriteSheet getSheetByFilename(String fileName) {
        String prefix = "/textures/tiles/";
        for (SpriteSheet sheet : tileSheets) {
            if (sheet.getPath().equalsIgnoreCase(prefix + fileName)) {
                return sheet;
            }
        }
        throw new IllegalArgumentException("SpriteSheet not found: " + prefix + fileName);
    }

}