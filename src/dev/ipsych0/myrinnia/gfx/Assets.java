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

    // Weather effects
    public static BufferedImage rain, snow, sandStorm, fog;

    // Map item images (trees, rocks, etc)
    public static BufferedImage weakPalmTree, elmTree, oakTree, aspenTree, azuriteRock, copperRock, ironRock, tungstenRock;
    public static BufferedImage bountyBoard1, bountyBoard2;
    public static BufferedImage rockSlide, shamrockSinkholeTL, shamrockSinkholeTM, shamrockSinkholeTR, shamrockSinkholeML, shamrockSinkholeMM, shamrockSinkholeMR, shamrockSinkholeBL, shamrockSinkholeBM, shamrockSinkholeBR;
    public static BufferedImage ropeLadderMapTile;
    /*
     * Creature Animations
     */
    public static BufferedImage[] combatUpFront, combatUpBack;
    public static BufferedImage[] player_down, player_up, player_left, player_right;
    public static BufferedImage[] regularArrow, regularMagic, regularMelee, arrow2;
    public static BufferedImage[] fireProjectile;
    public static BufferedImage[] waterProjectile;
    public static BufferedImage[] earthProjectile;
    public static BufferedImage[] airProjectile;
    public static BufferedImage[] hedgeHogRoll;

    // Ability animations
    public static BufferedImage[] airCloud1, waterSplash1, movementBoost1, eruption1, iceBall1, poisonDart, glacialShot1, burrowMound, stunned, acidBomb,
            rockyConstrict, sandBlast;

    // Ability icons
    public static BufferedImage eruptionI, fireballI, mendWoundsI, nimbleFeetI, supersonicDashI, frostJabI, iceBallI, poisonDartI,
            glacialShotI, healingSpringI, arcaneRenewalI, acidBombI, cripplingImpactI, debilitatingShotI, debilitatingStrikeI,
            rockyConstrictI, sandblastI;

    // Player attack images
    public static BufferedImage[] player_melee_left, player_melee_right, player_melee_down, player_melee_up;
    public static BufferedImage[] player_magic_left, player_magic_right, player_magic_down, player_magic_up;
    public static BufferedImage[] player_ranged_left, player_ranged_right, player_ranged_down, player_ranged_up;

    // Main menu buttons
    public static BufferedImage[] genericButton;
    public static BufferedImage[] scrollUpButton, scrollDownButton;

    // Item images
    public static BufferedImage palmWood, azuriteOre, beginnersSword, beginnersStaff, ryansAxe, simplePickaxe, simpleAxe, bountyContract, beginnersBow,
            simpleFishingRod, mackerelFish, simpleSpellBook, simpleShield, simpleQuiver, simpleSandals, copperPickaxe, copperAxe,
            copperOre, malachite, miningEquipment, abilityScroll, azuriteNecklace, azureBatWing, crablingClaw, simpleGloves, simpleBandana,
            chitin, scorpionTail, owlFeather, dynamite, detonator, vineRoot, simpleVest, simpleTrousers, pileOfSand, pileOfAshes, glass,
            lightWoodPlank, hardWood, hardWoodPlank, ironOre, trout, boneMeal, rockyShell, tomatoSeeds, cabbageSeeds, tomato, cabbage,
            wateringCan, softLeather, stripOfCloth, lapisLazuli, azuriteEarrings, azuriteRingL, azuriteRingR, copperFishingRod,
            malachiteEarrings, malachiteRingL, malachiteRingR, malachiteAmulet, ironAxe, ironPickaxe, ironFishingRod, ironChainMail,
            studdedShield, ironSword, ironLegs, squiresCloak, ironHelm, ironBoots, ironGloves, softLeatherBody, ironQuiver, hardwoodBow,
            softLeatherLeggings, scoutsCloak, softLeatherCowl, softLeatherBoots, softLeatherGloves, woolenRobeTop, leatherSpellbook,
            hardwoodStaff, woolenRobeBottom, apprenticesCloak, woolenHat, woolenBoots, woolenGloves, rope, snakehead, clam, rake,
            tungstenOre, aspenwood, lightwood, strawberrySeeds, raspberrySeeds, blackberrySeeds, blueberrySeeds, appleTreeSeeds,
            bananaTreeSeeds, orangeTreeSeeds, apricotTreeSeeds, peachTreeSeeds, papayaTreeSeeds, starfruitTreeSeeds, dragonfruitTreeSeeds;
    public static BufferedImage[] coins;
    public static BufferedImage weakAntidote, antidote, strongAntidote, weakPotionOfPrecision, potionOfPrecision, strongPotionOfPrecision,
            weakPotionOfMight, potionOfMight, strongPotionOfMight, weakPotionOfWisdom, potionOfWisdom, strongPotionOfWisdom,
            weakPotionofFortitude, potionofFortitude, strongPotionofFortitude, weakPotionOfVigor, potionOfVigor, strongPotionOfVigor;

    // Farming icons
    public static BufferedImage cropsPlanted1;

    // Enemy images
    public static BufferedImage[] blueScorpionUp, blueScorpionDown, blueScorpionLeft, blueScorpionRight;
    public static BufferedImage[] blueCrabUp, blueCrabDown, blueCrabLeft, blueCrabRight;
    public static BufferedImage[] azureBatUp, azureBatDown, azureBatLeft, azureBatRight;
    public static BufferedImage[] angryOwl, angryFlower;
    public static BufferedImage[] venovineUp, venovineDown, venovineLeft, venovineRight;
    public static BufferedImage[] burrowingBeetleUp, burrowingBeetleDown, burrowingBeetleLeft, burrowingBeetleRight;
    public static BufferedImage[] hedgehogUp, hedgehogDown, hedgehogLeft, hedgehogRight;
    public static BufferedImage[] caveTrollUp, caveTrollDown, caveTrollLeft, caveTrollRight;

    // Generic Util NPC images
    public static BufferedImage[] shopKeeper1Down, shopKeeper1Left, shopKeeper1Right, shopKeeper1Up,
            shopKeeper2Down, shopKeeper2Left, shopKeeper2Right, shopKeeper2Up,
            shopKeeper3Down, shopKeeper3Left, shopKeeper3Right, shopKeeper3Up,
            shopKeeper4Down, shopKeeper4Left, shopKeeper4Right, shopKeeper4Up,
            shopKeeper5Down, shopKeeper5Left, shopKeeper5Right, shopKeeper5Up,
            shopKeeper6Down, shopKeeper6Left, shopKeeper6Right, shopKeeper6Up,
            femaleBankerDown, femaleBankerLeft, femaleBankerRight, femaleBankerUp,
            maleBankerDown, maleBankerLeft, maleBankerRight, maleBankerUp;

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
    public static BufferedImage[] genericFemale13Down, genericFemale13Left, genericFemale13Right, genericFemale13Up;
    public static BufferedImage[] genericFemale14Down, genericFemale14Left, genericFemale14Right, genericFemale14Up;
    public static BufferedImage[] genericFemale15Down, genericFemale15Left, genericFemale15Right, genericFemale15Up;
    public static BufferedImage[] genericFemale16Down, genericFemale16Left, genericFemale16Right, genericFemale16Up;
    public static BufferedImage[] genericFemale17Down, genericFemale17Left, genericFemale17Right, genericFemale17Up;
    public static BufferedImage[] genericFemale18Down, genericFemale18Left, genericFemale18Right, genericFemale18Up;

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
    public static BufferedImage[] genericMale13Down, genericMale13Left, genericMale13Right, genericMale13Up;
    public static BufferedImage[] genericMale14Down, genericMale14Left, genericMale14Right, genericMale14Up;
    public static BufferedImage[] genericMale15Down, genericMale15Left, genericMale15Right, genericMale15Up;
    public static BufferedImage[] genericMale16Down, genericMale16Left, genericMale16Right, genericMale16Up;
    public static BufferedImage[] genericMale17Down, genericMale17Left, genericMale17Right, genericMale17Up;
    public static BufferedImage[] genericMale18Down, genericMale18Left, genericMale18Right, genericMale18Up;

    // Male elves
    public static BufferedImage[] genericElfMale1Down, genericElfMale1Left, genericElfMale1Right, genericElfMale1Up;
    public static BufferedImage[] genericElfMale2Down, genericElfMale2Left, genericElfMale2Right, genericElfMale2Up;
    public static BufferedImage[] genericElfMale3Down, genericElfMale3Left, genericElfMale3Right, genericElfMale3Up;
    public static BufferedImage[] genericElfMale4Down, genericElfMale4Left, genericElfMale4Right, genericElfMale4Up;
    public static BufferedImage[] genericElfMale5Down, genericElfMale5Left, genericElfMale5Right, genericElfMale5Up;
    public static BufferedImage[] genericElfMale6Down, genericElfMale6Left, genericElfMale6Right, genericElfMale6Up;

    // Female elves
    public static BufferedImage[] genericElfFemale1Down, genericElfFemale1Left, genericElfFemale1Right, genericElfFemale1Up;
    public static BufferedImage[] genericElfFemale2Down, genericElfFemale2Left, genericElfFemale2Right, genericElfFemale2Up;
    public static BufferedImage[] genericElfFemale3Down, genericElfFemale3Left, genericElfFemale3Right, genericElfFemale3Up;
    public static BufferedImage[] genericElfFemale4Down, genericElfFemale4Left, genericElfFemale4Right, genericElfFemale4Up;
    public static BufferedImage[] genericElfFemale5Down, genericElfFemale5Left, genericElfFemale5Right, genericElfFemale5Up;
    public static BufferedImage[] genericElfFemale6Down, genericElfFemale6Left, genericElfFemale6Right, genericElfFemale6Up;

    // Port Azure
    public static BufferedImage[] portAzureRyanDown, portAzureRyanLeft, portAzureRyanRight, portAzureRyanUp;
    public static BufferedImage[] portAzureDouglasDown, portAzureDouglasLeft, portAzureDouglasRight, portAzureDouglasUp;
    public static BufferedImage[] portAzureDuncanDown, portAzureDuncanLeft, portAzureDuncanRight, portAzureDuncanUp;
    public static BufferedImage[] portAzureCooperDown, portAzureCooperLeft, portAzureCooperRight, portAzureCooperUp;
    public static BufferedImage[] portAzureSailorDown, portAzureSailorLeft, portAzureSailorRight, portAzureSailorUp;
    public static BufferedImage portAzureMayor, elderSelwyn;

    // Shamrock
    public static BufferedImage[] shamrockEdgarDown, shamrockEdgarLeft, shamrockEdgarRight, shamrockEdgarUp;
    public static BufferedImage[] shamrockEdvardDown, shamrockEdvardLeft, shamrockEdvardRight, shamrockEdvardUp;
    public static BufferedImage[] shamrockEsmundDown, shamrockEsmundLeft, shamrockEsmundRight, shamrockEsmundUp;
    public static BufferedImage[] shamrockJoraDown, shamrockJoraLeft, shamrockJoraRight, shamrockJoraUp;

    // Malachite
    public static BufferedImage[] malachiteThug1Down, malachiteThug1Left, malachiteThug1Right, malachiteThug1Up;
    public static BufferedImage[] malachiteThug2Down, malachiteThug2Left, malachiteThug2Right, malachiteThug2Up;
    public static BufferedImage[] malachiteThugLeaderDown, malachiteThugLeaderLeft, malachiteThugLeaderRight, malachiteThugLeaderUp;
    public static BufferedImage[] malachiteTonyDown, malachiteTonyLeft, malachiteTonyRight, malachiteTonyUp;

    // Celenor
    public static BufferedImage[] celenorElenthirDown, celenorElenthirLeft, celenorElenthirRight, celenorElenthirUp;
    public static BufferedImage[] celenorNyvolasDown, celenorNyvolasLeft, celenorNyvolasRight, celenorNyvolasUp;

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
    public static BufferedImage fishingIcon, woodcuttingIcon, miningIcon, meleeIcon, bountyHunterIcon, farmingIcon;
    public static BufferedImage chillIcon, poisonIcon, burnIcon, bleedIcon, stunIcon;
    public static BufferedImage resistanceIcon, weaknessIcon;
    public static BufferedImage strBuffIcon, dexBuffIcon, intBuffIcon, defBuffIcon, vitBuffIcon, atkSpdBuffIcon,
            movSpdBuffIcon;
    public static BufferedImage exclamationIcon;

    public static BufferedImage uiWindow;

    public static BufferedImage[][] puzzlePieces;

    public static BufferedImage mainBackground;

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
        SpriteSheet main_background = new SpriteSheet("/textures/pixel_art_example.png");
        mainBackground = main_background.imageCrop(0, 0, 1366, 768);

        SpriteSheet ui_sheet = new SpriteSheet("/textures/ui-items-new.png");
        SpriteSheet projectiles = new SpriteSheet("/textures/projectiles.png");
        SpriteSheet equipSlots = new SpriteSheet("/textures/equipment_placeholders.png");
        /*
         * Make skilling sheet for this
         */
        SpriteSheet whirlPool = new SpriteSheet("/textures/whirlpool.png");
        SpriteSheet mining_rocks = new SpriteSheet("/textures/mining_rocks.png");
        SpriteSheet woodcutting_trees = new SpriteSheet("/textures/woodcutting_trees.png");
        SpriteSheet farming_sheet = new SpriteSheet("/textures/farming_sheet.png");

        /*
         * Weather sprites
         */
        SpriteSheet rain_sheet = new SpriteSheet("/textures/weather/wsheet_rain1_1.png");
        rain = rain_sheet.imageCrop(0, 0, 128, 32);
        SpriteSheet snow_sheet = new SpriteSheet("/textures/weather/wsheet_snow_1.png");
        snow = snow_sheet.imageCrop(0, 0, 128, 32);
        SpriteSheet sand_sheet = new SpriteSheet("/textures/weather/wsheet_sand_1.png");
        sandStorm = sand_sheet.imageCrop(0, 0, 128, 32);
        SpriteSheet fog_sheet = new SpriteSheet("/textures/weather/wsheet_fog_1.png");
        fog = fog_sheet.imageCrop(0, 0, 128, 32);

        /*
         * Player/NPCs
         */
        SpriteSheet level_up = new SpriteSheet("/textures/animations/level_up.png");
        SpriteSheet player_sheet = new SpriteSheet("/textures/npc_sprites/player.png");
        SpriteSheet azureal_island_npcs = new SpriteSheet("/textures/npc_sprites/azureal_island_npcs.png");
        SpriteSheet shamrock_npcs = new SpriteSheet("/textures/npc_sprites/shamrock_npcs.png");
        SpriteSheet malachite_npcs = new SpriteSheet("/textures/npc_sprites/malachite_npcs.png");
        SpriteSheet celenor_npcs = new SpriteSheet("/textures/npc_sprites/celenor_npcs.png");
        SpriteSheet generic_males1 = new SpriteSheet("/textures/npc_sprites/generic_males1.png");
        SpriteSheet generic_females1 = new SpriteSheet("/textures/npc_sprites/generic_females1.png");
        SpriteSheet generic_elves1 = new SpriteSheet("/textures/npc_sprites/generic_elves1.png");
        SpriteSheet generic_util_npcs = new SpriteSheet("/textures/npc_sprites/generic_util_npcs.png");

        /*
         * Add items to this
         */
        SpriteSheet item_sheet = new SpriteSheet("/textures/itemsprites.png");

        SpriteSheet enemy_sheet1 = new SpriteSheet("/textures/enemy_sprites/monster1.png");
        SpriteSheet enemy_sheet2 = new SpriteSheet("/textures/enemy_sprites/monster2.png");
        SpriteSheet enemy_sheet3 = new SpriteSheet("/textures/enemy_sprites/monster3.png");

        SpriteSheet boss_sheet1 = new SpriteSheet("/textures/enemy_sprites/bosses1.png");

        /*
         * Ability Animations
         */
        SpriteSheet ability_icons = new SpriteSheet("/textures/animations/abilitysheet.png");
        SpriteSheet ability_animations = new SpriteSheet("/textures/animations/ability_animations.png");
        SpriteSheet regular_attacks = new SpriteSheet("/textures/regular_attacks.png");

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
        tileSheets.add(new SpriteSheet("/textures/tiles/z_tile_marker.png", true));

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
        farmingIcon = ui_sheet.imageCrop(7, 1);

        // Condition icons
        chillIcon = ui_sheet.imageCrop(0, 2);
        poisonIcon = ui_sheet.imageCrop(1, 2);
        burnIcon = ui_sheet.imageCrop(2, 2);
        bleedIcon = ui_sheet.imageCrop(3, 2);
        stunIcon = ui_sheet.imageCrop(4, 2);

        // Weakness & Resistance icons
        resistanceIcon = ui_sheet.imageCrop(4, 1);
        weaknessIcon = ui_sheet.imageCrop(6, 1);

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
        exclamationIcon = ui_sheet.imageCrop(3, 1);

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
        palmWood = item_sheet.imageCrop(0, 0);
        azuriteOre = item_sheet.imageCrop(0, 1);
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
        simpleFishingRod = item_sheet.imageCrop(0, 10);
        mackerelFish = item_sheet.imageCrop(0, 11);
        simpleSpellBook = item_sheet.imageCrop(0, 12);
        simpleShield = item_sheet.imageCrop(0, 13);
        simpleQuiver = item_sheet.imageCrop(0, 14);
        simpleSandals = item_sheet.imageCrop(0, 15);
        copperPickaxe = item_sheet.imageCrop(0, 16);
        copperAxe = item_sheet.imageCrop(0, 17);
        copperOre = item_sheet.imageCrop(0, 18);
        malachite = item_sheet.imageCrop(0, 19);
        miningEquipment = item_sheet.imageCrop(0, 20);
        abilityScroll = item_sheet.imageCrop(0, 21);
        azuriteNecklace = item_sheet.imageCrop(0, 22);
        azureBatWing = item_sheet.imageCrop(0, 23);
        crablingClaw = item_sheet.imageCrop(0, 24);
        simpleGloves = item_sheet.imageCrop(0, 25);
        simpleBandana = item_sheet.imageCrop(0, 26);
        chitin = item_sheet.imageCrop(0, 27);
        scorpionTail = item_sheet.imageCrop(0, 28);
        owlFeather = item_sheet.imageCrop(0, 29);

        dynamite = item_sheet.imageCrop(1, 1);
        detonator = item_sheet.imageCrop(1, 2);
        vineRoot = item_sheet.imageCrop(1, 3);
        simpleVest = item_sheet.imageCrop(1, 4);
        simpleTrousers = item_sheet.imageCrop(1, 5);
        pileOfSand = item_sheet.imageCrop(1, 6);
        pileOfAshes = item_sheet.imageCrop(1, 7);
        glass = item_sheet.imageCrop(1, 8);
        lightWoodPlank = item_sheet.imageCrop(1, 9);
        hardWood = item_sheet.imageCrop(1, 10);
        hardWoodPlank = item_sheet.imageCrop(1, 11);
        ironOre = item_sheet.imageCrop(1, 12);
        trout = item_sheet.imageCrop(1, 13);
        boneMeal = item_sheet.imageCrop(1, 14);
        rockyShell = item_sheet.imageCrop(1, 15);
        tomatoSeeds = item_sheet.imageCrop(1, 16);
        cabbageSeeds = item_sheet.imageCrop(1, 17);
        tomato = item_sheet.imageCrop(1, 18);
        cabbage = item_sheet.imageCrop(1, 19);
        wateringCan = item_sheet.imageCrop(1, 20);
        softLeather = item_sheet.imageCrop(1, 21);
        stripOfCloth = item_sheet.imageCrop(1, 22);
        lapisLazuli = item_sheet.imageCrop(1, 23);
        azuriteEarrings = item_sheet.imageCrop(1, 24);
        azuriteRingL = item_sheet.imageCrop(1, 25);
        azuriteRingR = item_sheet.imageCrop(1, 26);
        copperFishingRod = item_sheet.imageCrop(1, 27);
        malachiteEarrings = item_sheet.imageCrop(1, 28);
        malachiteAmulet = item_sheet.imageCrop(1, 29);
        malachiteRingL = item_sheet.imageCrop(2, 1);
        malachiteRingR = item_sheet.imageCrop(2, 2);
        ironAxe = item_sheet.imageCrop(2, 3);
        ironPickaxe = item_sheet.imageCrop(2, 4);
        ironFishingRod = item_sheet.imageCrop(2, 5);

        ironChainMail = item_sheet.imageCrop(2, 6);
        studdedShield = item_sheet.imageCrop(2, 7);
        ironSword = item_sheet.imageCrop(2, 8);
        ironLegs = item_sheet.imageCrop(2, 9);
        squiresCloak = item_sheet.imageCrop(2, 10);
        ironHelm = item_sheet.imageCrop(2, 11);
        ironBoots = item_sheet.imageCrop(2, 12);
        ironGloves = item_sheet.imageCrop(2, 13);

        softLeatherBody = item_sheet.imageCrop(2, 14);
        ironQuiver = item_sheet.imageCrop(2, 15);
        hardwoodBow = item_sheet.imageCrop(2, 16);
        softLeatherLeggings = item_sheet.imageCrop(2, 17);
        scoutsCloak = item_sheet.imageCrop(2, 18);
        softLeatherCowl = item_sheet.imageCrop(2, 19);
        softLeatherBoots = item_sheet.imageCrop(2, 20);
        softLeatherGloves = item_sheet.imageCrop(2, 21);

        woolenRobeTop = item_sheet.imageCrop(2, 22);
        leatherSpellbook = item_sheet.imageCrop(2, 23);
        hardwoodStaff = item_sheet.imageCrop(2, 24);
        woolenRobeBottom = item_sheet.imageCrop(2, 25);
        apprenticesCloak = item_sheet.imageCrop(2, 26);
        woolenHat = item_sheet.imageCrop(2, 27);
        woolenBoots = item_sheet.imageCrop(2, 28);
        woolenGloves = item_sheet.imageCrop(2, 29);

        rope = item_sheet.imageCrop(3, 1);
        snakehead = item_sheet.imageCrop(3, 2);
        clam = item_sheet.imageCrop(3, 3);
        rake = item_sheet.imageCrop(3, 4);
        tungstenOre = item_sheet.imageCrop(3, 5);
        aspenwood = item_sheet.imageCrop(3, 6);
        lightwood = item_sheet.imageCrop(3, 7);

        strawberrySeeds = item_sheet.imageCrop(3, 8);
        raspberrySeeds = item_sheet.imageCrop(3, 9);
        blackberrySeeds = item_sheet.imageCrop(3, 10);
        blueberrySeeds = item_sheet.imageCrop(3, 11);
        appleTreeSeeds = item_sheet.imageCrop(3, 12);
        bananaTreeSeeds = item_sheet.imageCrop(3, 13);
        orangeTreeSeeds = item_sheet.imageCrop(3, 14);
        apricotTreeSeeds = item_sheet.imageCrop(3, 15);
        peachTreeSeeds = item_sheet.imageCrop(3, 16);
        papayaTreeSeeds = item_sheet.imageCrop(3, 17);
        starfruitTreeSeeds = item_sheet.imageCrop(3, 18);
        dragonfruitTreeSeeds = item_sheet.imageCrop(3, 19);

        // Farming sprites
        cropsPlanted1 = farming_sheet.imageCrop(0, 0);

        // Potions
        weakAntidote = item_sheet.imageCrop(15, 15);
        antidote = item_sheet.imageCrop(15, 14);
        strongAntidote = item_sheet.imageCrop(15, 13);

        weakPotionOfMight = item_sheet.imageCrop(17, 15);
        potionOfMight = item_sheet.imageCrop(17, 14);
        strongPotionOfMight = item_sheet.imageCrop(17, 13);

        weakPotionOfPrecision = item_sheet.imageCrop(19, 15);
        potionOfPrecision = item_sheet.imageCrop(19, 14);
        strongPotionOfPrecision = item_sheet.imageCrop(19, 13);

        weakPotionOfWisdom = item_sheet.imageCrop(22, 15);
        potionOfWisdom = item_sheet.imageCrop(22, 14);
        strongPotionOfWisdom = item_sheet.imageCrop(22, 13);

        weakPotionofFortitude = item_sheet.imageCrop(20, 15);
        potionofFortitude = item_sheet.imageCrop(20, 14);
        strongPotionofFortitude = item_sheet.imageCrop(20, 13);

        weakPotionOfVigor = item_sheet.imageCrop(23, 15);
        potionOfVigor = item_sheet.imageCrop(23, 14);
        strongPotionOfVigor = item_sheet.imageCrop(23, 13);

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

        burrowingBeetleDown = enemy_sheet1.npcCrop(12, 0, WIDTH, HEIGHT);
        burrowingBeetleLeft = enemy_sheet1.npcCrop(12, 1, WIDTH, HEIGHT);
        burrowingBeetleRight = enemy_sheet1.npcCrop(12, 2, WIDTH, HEIGHT);
        burrowingBeetleUp = enemy_sheet1.npcCrop(12, 3, WIDTH, HEIGHT);

        hedgehogDown = enemy_sheet2.npcCrop(9, 4, WIDTH, HEIGHT);
        hedgehogLeft = enemy_sheet2.npcCrop(9, 5, WIDTH, HEIGHT);
        hedgehogRight = enemy_sheet2.npcCrop(9, 6, WIDTH, HEIGHT);
        hedgehogUp = enemy_sheet2.npcCrop(9, 7, WIDTH, HEIGHT);

        blueCrabDown = enemy_sheet2.npcCrop(0, 0, WIDTH, HEIGHT);
        blueCrabLeft = enemy_sheet2.npcCrop(0, 1, WIDTH, HEIGHT);
        blueCrabRight = enemy_sheet2.npcCrop(0, 2, WIDTH, HEIGHT);
        blueCrabUp = enemy_sheet2.npcCrop(0, 3, WIDTH, HEIGHT);

        azureBatDown = enemy_sheet1.npcCrop(9, 4, WIDTH, HEIGHT);
        azureBatLeft = enemy_sheet1.npcCrop(9, 5, WIDTH, HEIGHT);
        azureBatRight = enemy_sheet1.npcCrop(9, 6, WIDTH, HEIGHT);
        azureBatUp = enemy_sheet1.npcCrop(9, 7, WIDTH, HEIGHT);

        venovineDown = enemy_sheet3.npcCrop(0, 0, WIDTH, HEIGHT);
        venovineLeft = enemy_sheet3.npcCrop(0, 1, WIDTH, HEIGHT);
        venovineRight = enemy_sheet3.npcCrop(0, 2, WIDTH, HEIGHT);
        venovineUp = enemy_sheet3.npcCrop(0, 3, WIDTH, HEIGHT);

        // Bosses

        caveTrollDown = boss_sheet1.npcCrop(6, 0, WIDTH * 2, HEIGHT * 3);
        caveTrollLeft = boss_sheet1.npcCrop(6, 3, WIDTH * 2, HEIGHT * 3);
        caveTrollRight = boss_sheet1.npcCrop(6, 6, WIDTH * 2, HEIGHT * 3);
        caveTrollUp = boss_sheet1.npcCrop(6, 9, WIDTH * 2, HEIGHT * 3);

        // NPC Sprites
//
        elderSelwyn = azureal_island_npcs.singleNpcCrop(1, 0);
        portAzureMayor = azureal_island_npcs.singleNpcCrop(4, 0);

        shopKeeper1Down = generic_util_npcs.npcCrop(0, 0);
        shopKeeper1Left = generic_util_npcs.npcCrop(0, 1);
        shopKeeper1Right = generic_util_npcs.npcCrop(0, 2);
        shopKeeper1Up = generic_util_npcs.npcCrop(0, 3);

        shopKeeper2Down = generic_util_npcs.npcCrop(0, 4);
        shopKeeper2Left = generic_util_npcs.npcCrop(0, 5);
        shopKeeper2Right = generic_util_npcs.npcCrop(0, 6);
        shopKeeper2Up = generic_util_npcs.npcCrop(0, 7);

        shopKeeper3Down = generic_util_npcs.npcCrop(3, 4);
        shopKeeper3Left = generic_util_npcs.npcCrop(3, 5);
        shopKeeper3Right = generic_util_npcs.npcCrop(3, 6);
        shopKeeper3Up = generic_util_npcs.npcCrop(3, 7);

        shopKeeper4Down = generic_util_npcs.npcCrop(6, 4);
        shopKeeper4Left = generic_util_npcs.npcCrop(6, 5);
        shopKeeper4Right = generic_util_npcs.npcCrop(6, 6);
        shopKeeper4Up = generic_util_npcs.npcCrop(6, 7);

        shopKeeper5Down = generic_util_npcs.npcCrop(9, 4);
        shopKeeper5Left = generic_util_npcs.npcCrop(9, 5);
        shopKeeper5Right = generic_util_npcs.npcCrop(9, 6);
        shopKeeper5Up = generic_util_npcs.npcCrop(9, 7);

        shopKeeper6Down = generic_util_npcs.npcCrop(9, 0);
        shopKeeper6Left = generic_util_npcs.npcCrop(9, 1);
        shopKeeper6Right = generic_util_npcs.npcCrop(9, 2);
        shopKeeper6Up = generic_util_npcs.npcCrop(9, 3);

        femaleBankerDown = generic_util_npcs.npcCrop(3, 0);
        femaleBankerLeft = generic_util_npcs.npcCrop(3, 1);
        femaleBankerRight = generic_util_npcs.npcCrop(3, 2);
        femaleBankerUp = generic_util_npcs.npcCrop(3, 3);

        maleBankerDown = generic_util_npcs.npcCrop(6, 0);
        maleBankerLeft = generic_util_npcs.npcCrop(6, 1);
        maleBankerRight = generic_util_npcs.npcCrop(6, 2);
        maleBankerUp = generic_util_npcs.npcCrop(6, 3);

        portAzureSailorDown = azureal_island_npcs.npcCrop(6, 0);
        portAzureSailorLeft = azureal_island_npcs.npcCrop(6, 1);
        portAzureSailorRight = azureal_island_npcs.npcCrop(6, 2);
        portAzureSailorUp = azureal_island_npcs.npcCrop(6, 3);

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

        genericFemale13Down = generic_females1.npcCrop(0, 8);
        genericFemale13Left = generic_females1.npcCrop(0, 9);
        genericFemale13Right = generic_females1.npcCrop(0, 10);
        genericFemale13Up = generic_females1.npcCrop(0, 11);

        genericFemale14Down = generic_females1.npcCrop(3, 8);
        genericFemale14Left = generic_females1.npcCrop(3, 9);
        genericFemale14Right = generic_females1.npcCrop(3, 10);
        genericFemale14Up = generic_females1.npcCrop(3, 11);

        genericFemale15Down = generic_females1.npcCrop(6, 8);
        genericFemale15Left = generic_females1.npcCrop(6, 9);
        genericFemale15Right = generic_females1.npcCrop(6, 10);
        genericFemale15Up = generic_females1.npcCrop(6, 11);

        genericFemale16Down = generic_females1.npcCrop(9, 8);
        genericFemale16Left = generic_females1.npcCrop(9, 9);
        genericFemale16Right = generic_females1.npcCrop(9, 10);
        genericFemale16Up = generic_females1.npcCrop(9, 11);

        genericFemale17Down = generic_females1.npcCrop(12, 8);
        genericFemale17Left = generic_females1.npcCrop(12, 9);
        genericFemale17Right = generic_females1.npcCrop(12, 10);
        genericFemale17Up = generic_females1.npcCrop(12, 11);

        genericFemale18Down = generic_females1.npcCrop(15, 8);
        genericFemale18Left = generic_females1.npcCrop(15, 9);
        genericFemale18Right = generic_females1.npcCrop(15, 10);
        genericFemale18Up = generic_females1.npcCrop(15, 11);

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

        genericMale13Down = generic_males1.npcCrop(0, 8);
        genericMale13Left = generic_males1.npcCrop(0, 9);
        genericMale13Right = generic_males1.npcCrop(0, 10);
        genericMale13Up = generic_males1.npcCrop(0, 11);

        genericMale14Down = generic_males1.npcCrop(3, 8);
        genericMale14Left = generic_males1.npcCrop(3, 9);
        genericMale14Right = generic_males1.npcCrop(3, 10);
        genericMale14Up = generic_males1.npcCrop(3, 11);

        genericMale15Down = generic_males1.npcCrop(6, 8);
        genericMale15Left = generic_males1.npcCrop(6, 9);
        genericMale15Right = generic_males1.npcCrop(6, 10);
        genericMale15Up = generic_males1.npcCrop(6, 11);

        genericMale16Down = generic_males1.npcCrop(9, 8);
        genericMale16Left = generic_males1.npcCrop(9, 9);
        genericMale16Right = generic_males1.npcCrop(9, 10);
        genericMale16Up = generic_males1.npcCrop(9, 11);

        genericMale17Down = generic_males1.npcCrop(12, 8);
        genericMale17Left = generic_males1.npcCrop(12, 9);
        genericMale17Right = generic_males1.npcCrop(12, 10);
        genericMale17Up = generic_males1.npcCrop(12, 11);

        genericMale18Down = generic_males1.npcCrop(15, 8);
        genericMale18Left = generic_males1.npcCrop(15, 9);
        genericMale18Right = generic_males1.npcCrop(15, 10);
        genericMale18Up = generic_males1.npcCrop(15, 11);

        // Elves

        genericElfMale1Down = generic_elves1.npcCrop(0, 0);
        genericElfMale1Left = generic_elves1.npcCrop(0, 1);
        genericElfMale1Right = generic_elves1.npcCrop(0, 2);
        genericElfMale1Up = generic_elves1.npcCrop(0, 3);

        genericElfMale2Down = generic_elves1.npcCrop(3, 0);
        genericElfMale2Left = generic_elves1.npcCrop(3, 1);
        genericElfMale2Right = generic_elves1.npcCrop(3, 2);
        genericElfMale2Up = generic_elves1.npcCrop(3, 3);

        genericElfMale3Down = generic_elves1.npcCrop(6, 0);
        genericElfMale3Left = generic_elves1.npcCrop(6, 1);
        genericElfMale3Right = generic_elves1.npcCrop(6, 2);
        genericElfMale3Up = generic_elves1.npcCrop(6, 3);

        genericElfMale4Down = generic_elves1.npcCrop(9, 0);
        genericElfMale4Left = generic_elves1.npcCrop(9, 1);
        genericElfMale4Right = generic_elves1.npcCrop(9, 2);
        genericElfMale4Up = generic_elves1.npcCrop(9, 3);

        genericElfMale5Down = generic_elves1.npcCrop(12, 0);
        genericElfMale5Left = generic_elves1.npcCrop(12, 1);
        genericElfMale5Right = generic_elves1.npcCrop(12, 2);
        genericElfMale5Up = generic_elves1.npcCrop(12, 3);

        genericElfMale6Down = generic_elves1.npcCrop(15, 0);
        genericElfMale6Left = generic_elves1.npcCrop(15, 1);
        genericElfMale6Right = generic_elves1.npcCrop(15, 2);
        genericElfMale6Up = generic_elves1.npcCrop(15, 3);

        genericElfFemale1Down = generic_elves1.npcCrop(0, 4);
        genericElfFemale1Left = generic_elves1.npcCrop(0, 5);
        genericElfFemale1Right = generic_elves1.npcCrop(0, 6);
        genericElfFemale1Up = generic_elves1.npcCrop(0, 7);

        genericElfFemale2Down = generic_elves1.npcCrop(3, 4);
        genericElfFemale2Left = generic_elves1.npcCrop(3, 5);
        genericElfFemale2Right = generic_elves1.npcCrop(3, 6);
        genericElfFemale2Up = generic_elves1.npcCrop(3, 7);

        genericElfFemale3Down = generic_elves1.npcCrop(6, 4);
        genericElfFemale3Left = generic_elves1.npcCrop(6, 5);
        genericElfFemale3Right = generic_elves1.npcCrop(6, 6);
        genericElfFemale3Up = generic_elves1.npcCrop(6, 7);

        genericElfFemale4Down = generic_elves1.npcCrop(9, 4);
        genericElfFemale4Left = generic_elves1.npcCrop(9, 5);
        genericElfFemale4Right = generic_elves1.npcCrop(9, 6);
        genericElfFemale4Up = generic_elves1.npcCrop(9, 7);

        genericElfFemale5Down = generic_elves1.npcCrop(12, 4);
        genericElfFemale5Left = generic_elves1.npcCrop(12, 5);
        genericElfFemale5Right = generic_elves1.npcCrop(12, 6);
        genericElfFemale5Up = generic_elves1.npcCrop(12, 7);

        genericElfFemale6Down = generic_elves1.npcCrop(15, 4);
        genericElfFemale6Left = generic_elves1.npcCrop(15, 5);
        genericElfFemale6Right = generic_elves1.npcCrop(15, 6);
        genericElfFemale6Up = generic_elves1.npcCrop(15, 7);

        // Zone specific NPCs

        portAzureRyanDown = azureal_island_npcs.npcCrop(0, 4);
        portAzureRyanLeft = azureal_island_npcs.npcCrop(0, 5);
        portAzureRyanRight = azureal_island_npcs.npcCrop(0, 6);
        portAzureRyanUp = azureal_island_npcs.npcCrop(0, 7);

        portAzureDouglasDown = azureal_island_npcs.npcCrop(3, 4);
        portAzureDouglasLeft = azureal_island_npcs.npcCrop(3, 5);
        portAzureDouglasRight = azureal_island_npcs.npcCrop(3, 6);
        portAzureDouglasUp = azureal_island_npcs.npcCrop(3, 7);

        portAzureDuncanDown = azureal_island_npcs.npcCrop(6, 4);
        portAzureDuncanLeft = azureal_island_npcs.npcCrop(6, 5);
        portAzureDuncanRight = azureal_island_npcs.npcCrop(6, 6);
        portAzureDuncanUp = azureal_island_npcs.npcCrop(6, 7);

        portAzureCooperDown = azureal_island_npcs.npcCrop(9, 4);
        portAzureCooperLeft = azureal_island_npcs.npcCrop(9, 5);
        portAzureCooperRight = azureal_island_npcs.npcCrop(9, 6);
        portAzureCooperUp = azureal_island_npcs.npcCrop(9, 7);

        shamrockEdgarDown = shamrock_npcs.npcCrop(0, 0);
        shamrockEdgarLeft = shamrock_npcs.npcCrop(0, 1);
        shamrockEdgarRight = shamrock_npcs.npcCrop(0, 2);
        shamrockEdgarUp = shamrock_npcs.npcCrop(0, 3);

        shamrockEdvardDown = shamrock_npcs.npcCrop(3, 0);
        shamrockEdvardLeft = shamrock_npcs.npcCrop(3, 1);
        shamrockEdvardRight = shamrock_npcs.npcCrop(3, 2);
        shamrockEdvardUp = shamrock_npcs.npcCrop(3, 3);

        shamrockEsmundDown = shamrock_npcs.npcCrop(6, 0);
        shamrockEsmundLeft = shamrock_npcs.npcCrop(6, 1);
        shamrockEsmundRight = shamrock_npcs.npcCrop(6, 2);
        shamrockEsmundUp = shamrock_npcs.npcCrop(6, 3);

        shamrockJoraDown = shamrock_npcs.npcCrop(9, 0);
        shamrockJoraLeft = shamrock_npcs.npcCrop(9, 1);
        shamrockJoraRight = shamrock_npcs.npcCrop(9, 2);
        shamrockJoraUp = shamrock_npcs.npcCrop(9, 3);

        malachiteThug1Down = malachite_npcs.npcCrop(0, 0);
        malachiteThug1Left = malachite_npcs.npcCrop(0, 1);
        malachiteThug1Right = malachite_npcs.npcCrop(0, 2);
        malachiteThug1Up = malachite_npcs.npcCrop(0, 3);

        malachiteThug2Down = malachite_npcs.npcCrop(3, 0);
        malachiteThug2Left = malachite_npcs.npcCrop(3, 1);
        malachiteThug2Right = malachite_npcs.npcCrop(3, 2);
        malachiteThug2Up = malachite_npcs.npcCrop(3, 3);

        malachiteThugLeaderDown = malachite_npcs.npcCrop(6, 0);
        malachiteThugLeaderLeft = malachite_npcs.npcCrop(6, 1);
        malachiteThugLeaderRight = malachite_npcs.npcCrop(6, 2);
        malachiteThugLeaderUp = malachite_npcs.npcCrop(6, 3);

        malachiteTonyDown = malachite_npcs.npcCrop(9, 0);
        malachiteTonyLeft = malachite_npcs.npcCrop(9, 1);
        malachiteTonyRight = malachite_npcs.npcCrop(9, 2);
        malachiteTonyUp = malachite_npcs.npcCrop(9, 3);

        celenorElenthirDown = celenor_npcs.npcCrop(0, 0);
        celenorElenthirLeft = celenor_npcs.npcCrop(0, 1);
        celenorElenthirRight = celenor_npcs.npcCrop(0, 2);
        celenorElenthirUp = celenor_npcs.npcCrop(0, 3);

        celenorNyvolasDown = celenor_npcs.npcCrop(3, 0);
        celenorNyvolasLeft = celenor_npcs.npcCrop(3, 1);
        celenorNyvolasRight = celenor_npcs.npcCrop(3, 2);
        celenorNyvolasUp = celenor_npcs.npcCrop(3, 3);

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

        combatUpFront = new BufferedImage[10];
        combatUpFront[0] = level_up.imageCrop(0, 0, 64, 64);
        combatUpFront[1] = level_up.imageCrop(2, 0, 64, 64);
        combatUpFront[2] = level_up.imageCrop(4, 0, 64, 64);
        combatUpFront[3] = level_up.imageCrop(6, 0, 64, 64);
        combatUpFront[4] = level_up.imageCrop(8, 0, 64, 64);
        combatUpFront[5] = level_up.imageCrop(0, 2, 64, 64);
        combatUpFront[6] = level_up.imageCrop(2, 2, 64, 64);
        combatUpFront[7] = level_up.imageCrop(4, 2, 64, 64);
        combatUpFront[8] = level_up.imageCrop(6, 2, 64, 64);
        combatUpFront[9] = level_up.imageCrop(8, 2, 64, 64);

        combatUpBack = new BufferedImage[10];
        combatUpBack[0] = level_up.imageCrop(0, 4, 64, 64);
        combatUpBack[1] = level_up.imageCrop(2, 4, 64, 64);
        combatUpBack[2] = level_up.imageCrop(4, 4, 64, 64);
        combatUpBack[3] = level_up.imageCrop(6, 4, 64, 64);
        combatUpBack[4] = level_up.imageCrop(8, 4, 64, 64);
        combatUpBack[5] = level_up.imageCrop(0, 6, 64, 64);
        combatUpBack[6] = level_up.imageCrop(2, 6, 64, 64);
        combatUpBack[7] = level_up.imageCrop(4, 6, 64, 64);
        combatUpBack[8] = level_up.imageCrop(6, 6, 64, 64);
        combatUpBack[9] = level_up.imageCrop(8, 6, 64, 64);

        regularMelee = new BufferedImage[5];
        regularArrow = new BufferedImage[1];
        regularMagic = new BufferedImage[3];

        regularMelee[0] = regular_attacks.imageCrop(4, 0);
        regularMelee[1] = regular_attacks.imageCrop(5, 0);
        regularMelee[2] = regular_attacks.imageCrop(6, 0);
        regularMelee[3] = regular_attacks.imageCrop(7, 0);
        regularMelee[4] = regular_attacks.imageCrop(8, 0);

        regularArrow[0] = regular_attacks.imageCrop(0, 0);

        regularMagic[0] = regular_attacks.imageCrop(1, 0);
        regularMagic[1] = regular_attacks.imageCrop(2, 0);
        regularMagic[2] = regular_attacks.imageCrop(3, 0);

        /*
         * Ability Icons
         */
        eruptionI = ability_icons.imageCrop(0, 0);
        fireballI = ability_icons.imageCrop(1, 0);
        mendWoundsI = ability_icons.imageCrop(2, 0);
        nimbleFeetI = ability_icons.imageCrop(3, 0);
        supersonicDashI = ability_icons.imageCrop(4, 0);
        frostJabI = ability_icons.imageCrop(5, 0);
        iceBallI = ability_icons.imageCrop(6, 0);
        glacialShotI = ability_icons.imageCrop(7, 0);
        healingSpringI = ability_icons.imageCrop(8, 0);
        arcaneRenewalI = ability_icons.imageCrop(9, 0);
        poisonDartI = ability_icons.imageCrop(10, 0);
        acidBombI = ability_icons.imageCrop(11, 0);
        cripplingImpactI = ability_icons.imageCrop(12, 0);
        debilitatingShotI = ability_icons.imageCrop(13, 0);
        debilitatingStrikeI = ability_icons.imageCrop(14, 0);
        rockyConstrictI = ability_icons.imageCrop(15, 0);
        sandblastI = ability_icons.imageCrop(0, 1);


        /*
         * Creature Animations
         */

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

        iceBall1 = new BufferedImage[1];
        iceBall1[0] = ability_animations.imageCrop(0, 7);

        poisonDart = new BufferedImage[1];
        poisonDart[0] = ability_animations.imageCrop(1, 7);

        burrowMound = new BufferedImage[2];
        burrowMound[0] = ability_animations.imageCrop(2, 7);
        burrowMound[1] = ability_animations.imageCrop(3, 7);

        stunned = new BufferedImage[5];
        stunned[0] = ability_animations.imageCrop(3, 8);
        stunned[1] = ability_animations.imageCrop(4, 8);
        stunned[2] = ability_animations.imageCrop(5, 8);
        stunned[3] = ability_animations.imageCrop(6, 8);
        stunned[4] = ability_animations.imageCrop(7, 8);

        hedgeHogRoll = new BufferedImage[2];
        hedgeHogRoll[0] = ability_animations.imageCrop(4, 7);
        hedgeHogRoll[1] = ability_animations.imageCrop(5, 7);

        glacialShot1 = new BufferedImage[3];
        glacialShot1[0] = ability_animations.imageCrop(0, 8);
        glacialShot1[1] = ability_animations.imageCrop(1, 8);
        glacialShot1[2] = ability_animations.imageCrop(2, 8);

        acidBomb = new BufferedImage[8];
        acidBomb[0] = ability_animations.imageCrop(0, 9);
        acidBomb[1] = ability_animations.imageCrop(1, 9);
        acidBomb[2] = ability_animations.imageCrop(2, 9);
        acidBomb[3] = ability_animations.imageCrop(3, 9);
        acidBomb[4] = ability_animations.imageCrop(4, 9);
        acidBomb[5] = ability_animations.imageCrop(5, 9);
        acidBomb[6] = ability_animations.imageCrop(6, 9);
        acidBomb[7] = ability_animations.imageCrop(7, 9);

        rockyConstrict = new BufferedImage[5];
        rockyConstrict[0] = ability_animations.imageCrop(0, 10);
        rockyConstrict[1] = ability_animations.imageCrop(1, 10);
        rockyConstrict[2] = ability_animations.imageCrop(2, 10);
        rockyConstrict[3] = ability_animations.imageCrop(3, 10);
        rockyConstrict[4] = ability_animations.imageCrop(4, 10);

        arrow2 = new BufferedImage[1];
        arrow2[0] = ability_animations.imageCrop(0, 11);

        sandBlast = new BufferedImage[8];
        sandBlast[0] = ability_animations.imageCrop(0, 12);
        sandBlast[1] = ability_animations.imageCrop(1, 12);
        sandBlast[2] = ability_animations.imageCrop(2, 12);
        sandBlast[3] = ability_animations.imageCrop(3, 12);
        sandBlast[4] = ability_animations.imageCrop(4, 12);
        sandBlast[5] = ability_animations.imageCrop(5, 12);
        sandBlast[6] = ability_animations.imageCrop(6, 12);
        sandBlast[7] = ability_animations.imageCrop(7, 12);

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
        weakPalmTree = woodcutting_trees.imageCrop(0, 0, WIDTH * 3, HEIGHT * 4);
        elmTree = woodcutting_trees.imageCrop(3, 0, WIDTH * 2, HEIGHT * 3);
        oakTree = woodcutting_trees.imageCrop(5, 0, WIDTH * 3, HEIGHT * 3);
        aspenTree = woodcutting_trees.imageCrop(8, 0, WIDTH * 2, HEIGHT * 3);
        azuriteRock = mining_rocks.imageCrop(1, 0);
        copperRock = mining_rocks.imageCrop(2, 0);
        ironRock = mining_rocks.imageCrop(3, 0);
        tungstenRock = mining_rocks.imageCrop(4, 0);
        bountyBoard1 = getSheetByFilename("outside3.png").imageCrop(6, 11, WIDTH * 2, HEIGHT * 2);
        bountyBoard2 = getSheetByFilename("outside3.png").imageCrop(6, 14, WIDTH * 2, HEIGHT * 2);
        rockSlide = getSheetByFilename("outside2.png").imageCrop(1, 20, WIDTH * 2, HEIGHT * 2);

        SpriteSheet dungeon = getSheetByFilename("dungeon.png");
        shamrockSinkholeTL = dungeon.imageCrop(7, 9);
        shamrockSinkholeTM = dungeon.imageCrop(4, 9);
        shamrockSinkholeTR = dungeon.imageCrop(10, 9);
        shamrockSinkholeML = dungeon.imageCrop(6, 9);
        shamrockSinkholeMM = dungeon.imageCrop(1, 9);
        shamrockSinkholeMR = dungeon.imageCrop(11, 9);
        shamrockSinkholeBL = dungeon.imageCrop(14, 9);
        shamrockSinkholeBM = dungeon.imageCrop(13, 9);
        shamrockSinkholeBR = dungeon.imageCrop(15, 9);
        ropeLadderMapTile = dungeon.imageCrop(40, 26, WIDTH, HEIGHT * 3);

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