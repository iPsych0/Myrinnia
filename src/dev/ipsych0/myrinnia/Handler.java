package dev.ipsych0.myrinnia;

import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilities.data.AbilityManager;
import dev.ipsych0.myrinnia.abilities.ui.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.audio.Source;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.character.CharacterUI;
import dev.ipsych0.myrinnia.chatwindow.ChatWindow;
import dev.ipsych0.myrinnia.chatwindow.Filter;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.devtools.DevToolUI;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.HitSplat;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.equipment.EquipmentWindow;
import dev.ipsych0.myrinnia.gfx.GameCamera;
import dev.ipsych0.myrinnia.hpoverlay.HPOverlay;
import dev.ipsych0.myrinnia.input.KeyManager;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.items.Use;
import dev.ipsych0.myrinnia.items.ui.InventoryWindow;
import dev.ipsych0.myrinnia.pathfinding.CombatState;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestManager;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.recap.RecapEvent;
import dev.ipsych0.myrinnia.recap.RecapManager;
import dev.ipsych0.myrinnia.skills.Skill;
import dev.ipsych0.myrinnia.skills.SkillResource;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.skills.ui.BountyBoardUI;
import dev.ipsych0.myrinnia.skills.ui.BountyContractUI;
import dev.ipsych0.myrinnia.skills.ui.BountyManager;
import dev.ipsych0.myrinnia.skills.ui.SkillsUI;
import dev.ipsych0.myrinnia.states.State;
import dev.ipsych0.myrinnia.states.ZoneTransitionState;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;
import dev.ipsych0.myrinnia.tutorial.TutorialTipManager;
import dev.ipsych0.myrinnia.ui.CelebrationUI;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.worlds.World;
import dev.ipsych0.myrinnia.worlds.WorldHandler;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.util.Random;

public class Handler implements Serializable {

    public static final String resourcePath;
    public static final File jarFile;
    private Properties prop = new Properties();


    static {
        File jarFile1;
        try {
            jarFile1 = new File(Handler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (Exception e) {
            jarFile1 = null;
        }
        // Run with JAR file
        jarFile = jarFile1;
        if (jarFile != null && jarFile.isFile()) {
            System.out.println(jarFile.getAbsolutePath());
            resourcePath = "";
            isJar = true;
        } else {
            resourcePath = "res/";
        }
    }

    public static String initialWorldPath = "/worlds/port_azure.tmx";

    static {
        String fixedFile;
        if (Handler.isJar) {
            fixedFile = Handler.jarFile.getParentFile().getAbsolutePath() + initialWorldPath;
        } else {
            fixedFile = initialWorldPath.replaceFirst("/", Handler.resourcePath);
        }

        initialWorldPath = fixedFile;
    }


    /**
     *
     */
    private static final long serialVersionUID = -4768616559126746790L;
    private static Game game;
    private Random random;
    private World world;
    private World portAzure;
    private WorldHandler worldHandler;
    private Player player;
    private ChatWindow chatWindow;
    private InventoryWindow inventory;
    private EquipmentWindow equipment;
    private QuestManager questManager;
    private CraftingUI craftingUI;
    private CharacterUI characterUI;
    private SkillsUI skillsUI;
    private HPOverlay hpOverlay;
    private BankUI bankUI;
    private AbilityManager abilityManager;
    private RecapManager recapManager;
    private DevToolUI devToolUI;
    private AbilityOverviewUI abilityOverviewUI;
    private TutorialTipManager tutorialTipManager;
    private BountyContractUI contractUI;
    private CelebrationUI celebrationUI;
    public static boolean debugAStar;
    public static boolean debugCollision;
    public static boolean debugZones;
    public static boolean isJar;

    private static Handler handler;

    /*
     * Flag: Set to true for debug mode
     */
    public static boolean noclipMode = false;
    private static boolean propsLoaded;

    public static Handler get() {
        if (handler == null) {
            handler = new Handler();
            handler.init();
        }
        return handler;
    }

    private Handler() {

    }

    private void init() {
        handler.setGame(Game.get());

        random = new Random();

        // Instantiate the player
        player = new Player(77 * 32, 51 * 32);

        // Instantiate all interfaces
        chatWindow = new ChatWindow();
        inventory = new InventoryWindow();
        equipment = new EquipmentWindow();
        skillsUI = new SkillsUI();
        questManager = new QuestManager();
        craftingUI = new CraftingUI();
        characterUI = new CharacterUI();
        hpOverlay = new HPOverlay();
        bankUI = new BankUI();
        recapManager = new RecapManager();
        devToolUI = new DevToolUI();
        abilityManager = new AbilityManager();
        abilityOverviewUI = new AbilityOverviewUI();
        tutorialTipManager = new TutorialTipManager();
        contractUI = new BountyContractUI();
        celebrationUI = new CelebrationUI();

        // Set the starting world
        portAzure = new World.Builder(Zone.PortAzure, initialWorldPath).withTown().build();
        worldHandler = new WorldHandler(portAzure);
    }

    public void playMusic(Zone zone) {
        if (!AudioManager.soundMuted) {
            int buffer = -1;
            String songName = zone.getMusicFile();
            try {
                buffer = AudioManager.loadSound("/music/songs/" + songName);
            } catch (FileNotFoundException e) {
                System.err.println("Couldn't find file: " + songName);
                e.printStackTrace();
            }

            // Fade from first song to the next
            AudioManager.fadeSongs(zone, buffer);
        }
    }

    public void playMusic(String song) {
        if (!AudioManager.soundMuted) {
            int buffer = -1;
            try {
                buffer = AudioManager.loadSound("/music/songs/" + song);
            } catch (FileNotFoundException e) {
                System.err.println("Couldn't find file: " + song);
                e.printStackTrace();
            }

            // Fade from first song to the next
            AudioManager.fadeSongs(song, buffer);
        }
    }

    public void playEffect(String effect) {
        playEffect(effect, 0.0f);
    }

    /**
     * Play a sound effect one time with custom volume setting
     *
     * @param effect Name of the audio file
     * @param volume Additional volume. Default max volume is 0.15.
     */
    public void playEffect(String effect, float volume) {
        if (!AudioManager.sfxMuted) {
            if (volume < -AudioManager.sfxVolume) {
                volume = -AudioManager.sfxVolume;
            }
            int buffer = -1;
            try {
                buffer = AudioManager.loadSound("/music/sfx/" + effect);
            } catch (FileNotFoundException e) {
                System.err.println("Couldn't find file: " + effect);
                e.printStackTrace();
            }

            if (AudioManager.soundfxFiles.containsKey(buffer)) {
                Source s = AudioManager.soundfxFiles.get(buffer);
                s.setVolume(AudioManager.sfxVolume + volume);
                s.setLooping(false);
                s.playEffect(buffer);
            } else {
                Source s = new Source();
                AudioManager.soundfxFiles.put(buffer, s);
                s.setVolume(AudioManager.sfxVolume + volume);
                s.setLooping(false);
                s.playEffect(buffer);
            }
        }
    }

    public void addTip(TutorialTip tip) {
        if (chatWindow.getFilters().contains(Filter.SHOWTIPS)) {
            tutorialTipManager.addTip(tip);
        }
    }

    public void addRecapEvent(String description) {
        this.recapManager.addEvent(new RecapEvent(description));
    }

    /**
     * Checks if the player has quest requirements to begin specified quest
     *
     * @param quest - The quest to check requirements for
     * @return - true if requirements are met, false if not
     */
    public boolean hasQuestReqs(QuestList quest) {
        Quest q = getQuest(quest);
        boolean hasAllRequirements = true;

        if (q.getRequirements() == null) {
            return true;
        }

        for (int i = 0; i < q.getRequirements().size(); i++) {
            // Check skill requirements
            if (q.getRequirements().get(i).getSkill() != null) {
                if (getSkill(q.getRequirements().get(i).getSkill()).getLevel() < q.getRequirements().get(i).getLevel()) {
                    hasAllRequirements = false;
                    break;
                }
            }
            // Check quest requirements
            else if (q.getRequirements().get(i).getQuest() != null) {
                if (getQuest(q.getRequirements().get(i).getQuest()).getState() != QuestState.COMPLETED) {
                    hasAllRequirements = false;
                    break;
                }
                // Check miscellaneous requirements
            } else if (!q.getRequirements().get(i).isTaskDone()) {
                hasAllRequirements = false;
                break;
            }
        }

        return hasAllRequirements;
    }

    public Rectangle getMouse() {
        if (getMouseManager().getMouseCoords() != null) {
            getMouseManager().getMouseCoords().setLocation(getMouseManager().getMouseX(), getMouseManager().getMouseY());
            return getMouseManager().getMouseCoords();
        } else {
            getMouseManager().setMouseCoords(new Rectangle(getMouseManager().getMouseX(), getMouseManager().getMouseY(), 1, 1));
            return getMouseManager().getMouseCoords();
        }
    }

    public void goToWorld(Zone zone, int x, int y) {
        goToWorld(zone, x, y, null, null);
    }

    /**
     * Go from your current world to the next
     *
     * @param zone            - The new zone to enter
     * @param x               - The X coord in the new zone
     * @param y               - The Y coord in the new zone
     * @param customName      - Override the zone name
     * @param customMusicName - Override the music played
     */
    public void goToWorld(Zone zone, int x, int y, String customName, String customMusicName) {
        player.setX(x * Tile.TILEWIDTH);
        player.setY(y * Tile.TILEHEIGHT);
        player.setZone(zone);
        getWorld().getEntityManager().setSelectedEntity(null);

        // Reset all NPCs to their spawn location
        for (Entity e : world.getEntityManager().getEntities()) {
            if (e instanceof Creature && e.isAttackable() && !e.equals(player)) {
                Creature c = ((Creature) e);
                // Reset A* aggro
                c.setState(CombatState.BACKTRACK);
                e.setDamaged(false);
                // Clear buffs & condis & reset HP
                c.clearBuffs();
                c.clearConditions();
                c.setHealth(c.getMaxHealth());
                // Reset position
                e.setX(c.getxSpawn());
                e.setY(c.getySpawn());
            }
        }

        World w = worldHandler.getWorldsMap().get(zone);

        // Check if the world we're going to is a town, if so set a new spawnpoint here in case we die
        if (w.isTown()) {
            player.setLastSpawnPoint(w.getZone());
            player.setLastXSpawn(x);
            player.setLastYSpawn(y);
        }

        w.init();
        setWorld(w);

        if (getWorld().hasPermissionsLayer()) {
            Tile t = getWorld().getTile(getWorld().getLayers().length - 1, x, y);
            if (t == null) {
                player.setVerticality(0);
                player.setCurrentTile(Tile.tiles[23780]);
                player.setPreviousTile(Tile.tiles[23780]);
            } else {
                if (t.getPermission().equalsIgnoreCase("C")) {
                    player.setVerticality(0);
                    player.setCurrentTile(Tile.tiles[23780]);
                    player.setPreviousTile(Tile.tiles[23780]);
                } else if (t.getPermission().equalsIgnoreCase("10")) {
                    player.setVerticality(1);
                    player.setCurrentTile(t);
                    player.setPreviousTile(t);
                } else if (t.getPermission().equalsIgnoreCase("14")) {
                    player.setVerticality(2);
                    player.setCurrentTile(t);
                    player.setPreviousTile(t);
                }
            }
        } else {
            player.setVerticality(0);
            player.setCurrentTile(Tile.tiles[23780]);
            player.setPreviousTile(Tile.tiles[23780]);
        }

        ZoneTransitionState transitionState = new ZoneTransitionState(zone, customName);
        State.setState(transitionState);

        if (customMusicName == null) {
            playMusic(zone);
        } else {
            playMusic(customMusicName);
        }
    }

    /**
     * Gets an Entity by the Zone it's in and its name.
     *
     * @param zone Zone the Entity is in
     * @param name Name of the Entity
     * @return The Entity found, null if not found
     */
    public Entity getEntityByZoneAndName(Zone zone, String name) {
        // Make sure the world is initialized before accessing, to avoid nullpointers
        World world = Handler.get().getWorldHandler().getWorldsMap().get(zone);
        if (!world.isInitialized()) {
            world.init();
        }

        // Find Entity by name
        for (Entity e : world.getEntityManager().getEntities()) {
            if (e.getName() != null && e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

    public SkillResource getSkillResource(SkillsList skill, Item item) {
        return skillsUI.getSkill(skill).getResourceByItem(item);
    }


    public boolean playerHasSkillLevel(SkillsList skill, Item item) {
        SkillResource resource = skillsUI.getSkill(skill).getResourceByItem(item);
        if (resource != null) {
            return skillsUI.getSkill(skill).getLevel() >= resource.getLevelRequirement();
        }
        return false;
    }

    public boolean playerHasSkillLevel(SkillsList skill, int requirement) {
        return skillsUI.getSkill(skill).getLevel() >= requirement;
    }

    public BountyBoardUI getBountyBoardByZone(Zone zone) {
        return BountyManager.get().getBoardByZone(zone).getBountyBoardUI();
    }

    public Skill getSkill(SkillsList skill) {
        return skillsUI.getSkill(skill);
    }

    public void discoverRecipe(Item item) {
        craftingUI.getCraftingManager().getRecipeByItem(item).setDiscovered(true);
    }

    public boolean questStarted(QuestList quest) {
        return questManager.getQuestMap().get(quest).getState() != QuestState.NOT_STARTED;
    }

    public boolean questInProgress(QuestList quest) {
        return questManager.getQuestMap().get(quest).getState() == QuestState.IN_PROGRESS;
    }

    public boolean questCompleted(QuestList quest) {
        return questManager.getQuestMap().get(quest).getState() == QuestState.COMPLETED;
    }

    public Quest getQuest(QuestList quest) {
        return questManager.getQuestMap().get(quest);
    }

    /**
     * @param min INCLUSIVE minimum value
     * @param max INCLUSIVE maximum value
     * @return
     */
    public int getRandomNumber(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    /*
     * Rounds off a number to specified number of digits, default = .00 (2 digits)
     */
    public double roundOff(double value, int digits) {
        return Math.round(value * Math.pow(10.0d, (digits - 1))) / Math.pow(10.0d, (digits - 1));
    }

    public double roundOff(double value) {
        return roundOff(value, 2);
    }

    public void addHitSplat(Entity receiver, Entity damageDealer, DamageType damageType) {
        getWorld().getEntityManager().getHitSplats().add(new HitSplat(receiver, damageDealer.getDamage(damageType, damageDealer, receiver), damageType));
    }

    public void addHitSplat(Entity receiver, Entity damageDealer, DamageType damageType, Ability ability) {
        getWorld().getEntityManager().getHitSplats().add(new HitSplat(receiver, damageDealer.getDamage(damageType, damageDealer, receiver, ability), ability));
    }

    public void addHealSplat(Entity receiver, int healing) {
        getWorld().getEntityManager().getHitSplats().add(new HitSplat(receiver, healing));
    }

    /*
     * Drop an item to the world
     * @params: An item, an amount, x + y pos in the world (usually based on the Entity or Object location)
     */
    public void dropItem(Item item, int amount, int x, int y) {
        dropItem(item, amount, x, y, false);
    }

    private void dropItem(Item item, int amount, int x, int y, boolean isWorldSpawn) {
        Item i = item.createItem(x, y, amount);
        i.setTimeDropped(System.currentTimeMillis());
        if (item.getUse() != null) {
            i.setUse(item.getUse());
            i.setUseCooldown(item.getUseCooldown());
        }
        getWorld().getItemManager().addItem(i, isWorldSpawn);
    }

    /*
     * Sends a message to the chat log
     */
    public void sendMsg(String message, Filter filter) {
        String[] text = Text.splitIntoLine(message, 58);
        for (String s : text) {
            getChatWindow().sendMessage(s, filter);
        }
    }

    public void sendMsg(String message) {
        sendMsg(message, null);
    }

    /*
     * Checks if the inventory is full when picking up/receiving an item
     * @param: Provide the item that needs to be checked if it can be added to the inventory
     */
    public boolean invIsFull(Item item) {
        return getInventory().inventoryIsFull(item);
    }

    public boolean playerHasItemType(ItemType type) {
        return getInventory().playerHasItemType(type);
    }

    /*
     * Adds an item + quantity to the player's inventory. Usually followed by invIsFull() function
     * @params: Provide the item and the amount to be added
     */
    public void giveItem(Item item, int amount) {
        getInventory().giveItem(item, amount);
    }

    public void giveItemWithUse(Item item, int amount, int cooldown, Use use) {
        Item i = item.createItem(0, 0, amount);
        i.setUse(use);
        i.setUseCooldown(cooldown);
        getInventory().giveItem(i, amount);
    }

    public void giveItemWithUse(Item item, int amount, Use use) {
        Item i = item.createItem(0, 0, amount);
        i.setUse(use);
        getInventory().giveItem(i, amount);
    }


    /*
     * Removes an item + quantity from the inventory.
     * @params: Provide the item and the amount to be removed
     */
    public boolean removeItem(Item item, int amount) {
        return getInventory().removeItem(item, amount);
    }

    /*
     * Checks if the player has an item AND the specified amount in his inventory.
     * @params: Provide the item and the MINIMUM amount of that item to be checked
     */
    public boolean playerHasItem(Item item, int amount) {
        return getInventory().playerHasItem(item, amount);
    }

    public void saveProperty(String propertyKey, String propertyValue) {
        OutputStream output = null;

        try {
            prop.setProperty(propertyKey, propertyValue);
            if (isJar) {
                output = new FileOutputStream(Handler.jarFile.getParentFile().getAbsolutePath() + "/settings/config.properties");
            } else {
                output = new FileOutputStream(Handler.resourcePath + "settings/config.properties");
            }
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String loadProperty(String propertyKey) {
        InputStream input = null;

        try {
            if (!propsLoaded) {
                if (isJar) {
                    input = new FileInputStream(Handler.jarFile.getParentFile().getAbsolutePath() + "/settings/config.properties");
                } else {
                    input = new FileInputStream(Handler.resourcePath + "settings/config.properties");
                }
                prop.load(input);
                propsLoaded = true;
            }
            return prop.getProperty(propertyKey);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /*
     * Getters & Setters
     */

    public int getWidth() {
        return game.getWidth();
    }

    public int getHeight() {
        return game.getHeight();
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }

    public void setGameCamera(GameCamera camera) {
        game.setGameCamera(camera);
    }

    public Game getGame() {
        return game;
    }

    private void setGame(Game game) {
        Handler.game = game;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setWorld(Zone zone) {
        this.world = worldHandler.getWorldsMap().get(zone);
    }

    public WorldHandler getWorldHandler() {
        return worldHandler;
    }

    public void setWorldHandler(WorldHandler worldHandler) {
        this.worldHandler = worldHandler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ChatWindow getChatWindow() {
        return chatWindow;
    }

    public void setChatWindow(ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
    }

    public InventoryWindow getInventory() {
        return inventory;
    }

    public void setInventory(InventoryWindow inventory) {
        this.inventory = inventory;
    }

    public EquipmentWindow getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentWindow equipment) {
        this.equipment = equipment;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public void setQuestManager(QuestManager questManager) {
        this.questManager = questManager;
    }

    public CraftingUI getCraftingUI() {
        return craftingUI;
    }

    public void setCraftingUI(CraftingUI craftingUI) {
        this.craftingUI = craftingUI;
    }

    public CharacterUI getCharacterUI() {
        return characterUI;
    }

    public void setCharacterUI(CharacterUI characterUI) {
        this.characterUI = characterUI;
    }

    public SkillsUI getSkillsUI() {
        return skillsUI;
    }

    public void setSkillsUI(SkillsUI skillsUI) {
        this.skillsUI = skillsUI;
    }

    public HPOverlay getHpOverlay() {
        return hpOverlay;
    }

    public void setHpOverlay(HPOverlay hpOverlay) {
        this.hpOverlay = hpOverlay;
    }

    public BankUI getBankUI() {
        return bankUI;
    }

    public void setBankUI(BankUI bankUI) {
        this.bankUI = bankUI;
    }

    public AbilityManager getAbilityManager() {
        return abilityManager;
    }

    public void setAbilityManager(AbilityManager abilityManager) {
        this.abilityManager = abilityManager;
    }

    public RecapManager getRecapManager() {
        return recapManager;
    }

    public void setRecapManager(RecapManager recapManager) {
        this.recapManager = recapManager;
    }

    public static void setHandler(Handler handler) {
        Handler.handler = handler;
    }

    public DevToolUI getDevToolUI() {
        return devToolUI;
    }

    public void setDevToolUI(DevToolUI devToolUI) {
        this.devToolUI = devToolUI;
    }

    public AbilityOverviewUI getAbilityOverviewUI() {
        return abilityOverviewUI;
    }

    public void setAbilityOverviewUI(AbilityOverviewUI abilityOverviewUI) {
        this.abilityOverviewUI = abilityOverviewUI;
    }

    public TutorialTipManager getTutorialTipManager() {
        return tutorialTipManager;
    }

    public void setTutorialTipManager(TutorialTipManager tutorialTipManager) {
        this.tutorialTipManager = tutorialTipManager;
    }

    public BountyContractUI getContractUI() {
        return contractUI;
    }

    public void setContractUI(BountyContractUI contractUI) {
        this.contractUI = contractUI;
    }

    public CelebrationUI getCelebrationUI() {
        return celebrationUI;
    }

    public void setCelebrationUI(CelebrationUI celebrationUI) {
        this.celebrationUI = celebrationUI;
    }
}
