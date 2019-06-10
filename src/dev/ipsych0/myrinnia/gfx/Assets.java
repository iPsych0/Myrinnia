package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.utils.MapLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Assets {

    public static final int WIDTH = 32, HEIGHT = 32;

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
    public static BufferedImage[] player_attackingLeft, player_attackingRight, player_attackingDown, player_attackingUp;

    // Main menu buttons
    public static BufferedImage[] genericButton;
    public static BufferedImage[] scrollUpButton, scrollDownButton;

    // Item images
    public static BufferedImage wood, ore, testSword, purpleSword, testAxe, testPickaxe;
    public static BufferedImage[] coins;

    // Enemy images
    public static BufferedImage[] redScorpionUp, redScorpionDown, redScorpionLeft, redScorpionRight;

    // NPC images
    public static BufferedImage shopKeeperMerchantWater, femaleBanker;

    // Generic NPCs
    public static BufferedImage[] genericFemale1Down, genericFemale1Left, genericFemale1Right, genericFemale1Up;
    public static BufferedImage[] genericFemale2Down, genericFemale2Left, genericFemale2Right, genericFemale2Up;
    public static BufferedImage[] genericFemale3Down, genericFemale3Left, genericFemale3Right, genericFemale3Up;
    public static BufferedImage[] genericFemale4Down, genericFemale4Left, genericFemale4Right, genericFemale4Up;
    public static BufferedImage[] genericMale1Down, genericMale1Left, genericMale1Right, genericMale1Up;
    public static BufferedImage[] genericMale2Down, genericMale2Left, genericMale2Right, genericMale2Up;
    public static BufferedImage[] genericMale3Down, genericMale3Left, genericMale3Right, genericMale3Up;
    public static BufferedImage[] genericMale4Down, genericMale4Left, genericMale4Right, genericMale4Up;

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
    public static BufferedImage portAzureMayor, portAzureSailor;

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
        SpriteSheet rsCastlePuzzle = new SpriteSheet("/textures/RS_CASTLE_PUZZLE.png");
        SpriteSheet ui_sheet = new SpriteSheet("/textures/ui-items-new.png");
        SpriteSheet projectiles = new SpriteSheet("/textures/projectiles.png");
        SpriteSheet equipSlots = new SpriteSheet("/textures/equipment_placeholders.png");
        SpriteSheet texture_sheet = new SpriteSheet("/textures/textures.png");
        /*
         * Make skilling sheet for this
         */
        SpriteSheet whirlPool = new SpriteSheet("/textures/whirlpool.png");

        /*
         * Player/NPCs
         */
        SpriteSheet player_sheet = new SpriteSheet("/textures/npc_sprites/herosprites.png");
        SpriteSheet shopkeepers_sheet = new SpriteSheet("/textures/npc_sprites/shopkeepers_merchants.png");
        SpriteSheet bankers_sheet = new SpriteSheet("/textures/npc_sprites/bankers.png");
        SpriteSheet azureal_island_npcs = new SpriteSheet("/textures/npc_sprites/azureal_island_npcs.png");
        SpriteSheet generic_npcs = new SpriteSheet("/textures/npc_sprites/generic_npcs.png");
        SpriteSheet npc_sheet1 = new SpriteSheet("/textures/npc_sprites/npc_sheet1.png");
        SpriteSheet npc_sheet2 = new SpriteSheet("/textures/npc_sprites/npc_sheet2.png");
        SpriteSheet npc_sheet3 = new SpriteSheet("/textures/npc_sprites/npc_sheet3.png");
        SpriteSheet npc_sheet4 = new SpriteSheet("/textures/npc_sprites/npc_sheet4.png");
        SpriteSheet npc_sheet5 = new SpriteSheet("/textures/npc_sprites/npc_sheet5.png");

        /*
         * Add items to this
         */
        SpriteSheet item_sheet = new SpriteSheet("/textures/itemsprites.png");

        SpriteSheet enemy_sheet1 = new SpriteSheet("/textures/enemy_sprites/monster1.png");

        /*
         * Ability Animations
         */
        SpriteSheet ability_icons = new SpriteSheet("/textures/animations/abilitysheet.png");
        SpriteSheet ability_animations = new SpriteSheet("/textures/animations/ability_animations.png");

        /*
         * All Tiled Sprites
         */
        List<SpriteSheet> tileSheets = new ArrayList<>();
        tileSheets.add(new SpriteSheet("/textures/tiles/castle.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/desert.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/dungeon.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/house.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/inside.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/outside.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/terrain.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/water.png", true));
        tileSheets.add(new SpriteSheet("/textures/tiles/beach.png", true));

        Tile.tiles = new Tile[MapLoader.getTileCount()];

        for (SpriteSheet tileSheet : tileSheets) {
            for (int y = 0; y < tileSheet.getSheet().getHeight() / 32; y++) {
                for (int x = 0; x < tileSheet.getSheet().getWidth() / 32; x++) {
                    tileSheet.tileCrop(x, y);
                }
            }
        }

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
        testSword = item_sheet.imageCrop(1, 0);
        purpleSword = item_sheet.imageCrop(2, 0);
        testAxe = item_sheet.imageCrop(0, 6);
        testPickaxe = item_sheet.imageCrop(0, 7);


        /*
         * Enemy Animations
         */
        redScorpionDown = enemy_sheet1.npcCrop(9, 0, WIDTH, HEIGHT);
        redScorpionLeft = enemy_sheet1.npcCrop(9, 1, WIDTH, HEIGHT);
        redScorpionRight= enemy_sheet1.npcCrop(9, 2, WIDTH, HEIGHT);
        redScorpionUp = enemy_sheet1.npcCrop(9, 3, WIDTH, HEIGHT);

        // NPC Sprites

        shopKeeperMerchantWater = shopkeepers_sheet.singleNpcCrop(4, 0);
        femaleBanker = bankers_sheet.singleNpcCrop(4, 0);
        portAzureMayor = azureal_island_npcs.singleNpcCrop(1, 0);
        portAzureSailor = azureal_island_npcs.singleNpcCrop(4, 3);

        // Generic female NPCs
        genericFemale1Down = generic_npcs.npcCrop(0,0);
        genericFemale1Left = generic_npcs.npcCrop(0,1);
        genericFemale1Right = generic_npcs.npcCrop(0,2);
        genericFemale1Up = generic_npcs.npcCrop(0,3);

        genericFemale2Down = generic_npcs.npcCrop(0,4);
        genericFemale2Left = generic_npcs.npcCrop(0,5);
        genericFemale2Right = generic_npcs.npcCrop(0,6);
        genericFemale2Up = generic_npcs.npcCrop(0,7);

        genericFemale3Down = generic_npcs.npcCrop(3,4);
        genericFemale3Left = generic_npcs.npcCrop(3,5);
        genericFemale3Right = generic_npcs.npcCrop(3,6);
        genericFemale3Up = generic_npcs.npcCrop(3,7);

        genericFemale4Down = generic_npcs.npcCrop(9,4);
        genericFemale4Left = generic_npcs.npcCrop(9,5);
        genericFemale4Right = generic_npcs.npcCrop(9,6);
        genericFemale4Up = generic_npcs.npcCrop(9,7);

        // Generic male NPCs
        genericMale1Down = generic_npcs.npcCrop(3,0);
        genericMale1Left = generic_npcs.npcCrop(3,1);
        genericMale1Right = generic_npcs.npcCrop(3,2);
        genericMale1Up = generic_npcs.npcCrop(3,3);

        genericMale2Down = generic_npcs.npcCrop(6,0);
        genericMale2Left = generic_npcs.npcCrop(6,1);
        genericMale2Right = generic_npcs.npcCrop(6,2);
        genericMale2Up = generic_npcs.npcCrop(6,3);

        genericMale3Down = generic_npcs.npcCrop(9,0);
        genericMale3Left = generic_npcs.npcCrop(9,1);
        genericMale3Right = generic_npcs.npcCrop(9,2);
        genericMale3Up = generic_npcs.npcCrop(9,3);

        genericMale4Down = generic_npcs.npcCrop(6,4);
        genericMale4Left = generic_npcs.npcCrop(6,5);
        genericMale4Right = generic_npcs.npcCrop(6,6);
        genericMale4Up = generic_npcs.npcCrop(6,7);

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
        eruptionI = ability_icons.imageCrop(0, 0);
        fireballI = ability_icons.imageCrop(1, 0);
        mendWoundsI = ability_icons.imageCrop(2, 0);
        nimbleFeetI = ability_icons.imageCrop(3, 0);
        supersonicDashI = ability_icons.imageCrop(4, 0);


        /*
         * Creature Animations
         */

        aquatic_cultist_down = npc_sheet1.npcCrop(3, 0);
        aquatic_cultist_left = npc_sheet1.npcCrop(3, 1);
        aquatic_cultist_right = npc_sheet1.npcCrop(3, 2);
        aquatic_cultist_up = npc_sheet1.npcCrop(3, 3);

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
        tree = texture_sheet.imageCrop(1, 2);
        rock = texture_sheet.imageCrop(0, 3);

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