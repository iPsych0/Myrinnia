package dev.ipsych0.myrinnia;

import dev.ipsych0.myrinnia.abilities.AbilityManager;
import dev.ipsych0.myrinnia.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.audio.AudioManager;
import dev.ipsych0.myrinnia.audio.Source;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.character.CharacterUI;
import dev.ipsych0.myrinnia.chatwindow.ChatWindow;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.devtools.DevToolUI;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.equipment.EquipmentWindow;
import dev.ipsych0.myrinnia.gfx.GameCamera;
import dev.ipsych0.myrinnia.gfx.ScreenShot;
import dev.ipsych0.myrinnia.hpoverlay.HPOverlay;
import dev.ipsych0.myrinnia.input.KeyManager;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.items.ui.InventoryWindow;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.Quest.QuestState;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestManager;
import dev.ipsych0.myrinnia.quests.QuestStep;
import dev.ipsych0.myrinnia.recap.RecapEvent;
import dev.ipsych0.myrinnia.recap.RecapManager;
import dev.ipsych0.myrinnia.skills.Skill;
import dev.ipsych0.myrinnia.skills.SkillResource;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.skills.ui.SkillsUI;
import dev.ipsych0.myrinnia.states.State;
import dev.ipsych0.myrinnia.states.ZoneTransitionState;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.worlds.PortAzure;
import dev.ipsych0.myrinnia.worlds.data.World;
import dev.ipsych0.myrinnia.worlds.data.WorldHandler;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.util.Random;

public class Handler implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -4768616559126746790L;
    private static Game game;
    private World world;
    private PortAzure portAzure;
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
    private boolean soundMuted = false;
    public static String initialWorldPath = "res/worlds/port_azure.tmx";
    public static boolean debugCollision = false;

    private static Handler handler;

    /*
     * Flag: Set to true for debug mode
     */
    public static boolean noclipMode = false;

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

        // Instantiate the player
        player = new Player(1536, 2496);

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

        // Set the starting world
        portAzure = new PortAzure(initialWorldPath);
        worldHandler = new WorldHandler(portAzure);
    }

    public void playMusic(Zone zone) {
        if (!soundMuted) {
            int buffer = -1;
            String songName = zone.getMusicFile();
            try {
                buffer = AudioManager.loadSound("res/music/songs/" + songName);
            } catch (FileNotFoundException e) {
                System.err.println("Couldn't find file: " + songName);
                e.printStackTrace();
            }

            // Fade from first song to the next
            AudioManager.fadeSongs(zone, buffer);
        }
    }

    public void playMusic(String song) {
        if (!soundMuted) {
            int buffer = -1;
            try {
                buffer = AudioManager.loadSound("res/music/songs/" + song);
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
     * @param volume Additional volume. Default volume is 0.15.
     */
    public void playEffect(String effect, float volume) {
        if (!soundMuted) {
            if (volume < -0.15f) {
                volume = -0.15f;
            }
            int buffer = -1;
            try {
                buffer = AudioManager.loadSound("res/music/sfx/" + effect);
            } catch (FileNotFoundException e) {
                System.err.println("Couldn't find file: " + effect);
                e.printStackTrace();
            }
            AudioManager.soundfxFiles.add(new Source());
            AudioManager.soundfxFiles.get(AudioManager.soundfxFiles.size() - 1).setVolume(0.15f + volume);
            AudioManager.soundfxFiles.get(AudioManager.soundfxFiles.size() - 1).setLooping(false);
            AudioManager.soundfxFiles.get(AudioManager.soundfxFiles.size() - 1).playEffect(buffer);

        }
    }

    public void addRecapEvent(String description) {
        this.recapManager.addEvent(new RecapEvent(ScreenShot.take(), description));
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

        for (int i = 0; i < q.getRequirements().length; i++) {
            // Check skill requirements
            if (q.getRequirements()[i].getSkill() != null) {
                if (getSkill(q.getRequirements()[i].getSkill()).getLevel() < q.getRequirements()[i].getLevel()) {
                    hasAllRequirements = false;
                }
            }
            // Check quest requirements
            else if (q.getRequirements()[i].getQuest() != null) {
                if (getQuest(q.getRequirements()[i].getQuest()).getState() != QuestState.COMPLETED) {
                    hasAllRequirements = false;
                }
                // Check miscellaneous requirements
            } else if (!q.getRequirements()[i].isTaskDone()) {
                hasAllRequirements = false;
            }
        }

        if (!hasAllRequirements)
            sendMsg("You do not meet the requirements to start " + q.getQuestName() + ".");

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

    /**
     * Go from your current world to the next
     *
     * @param zone - The new zone to enter
     * @param x    - The X pause in the new zone
     * @param y    - The Y pause in the new zone
     */
    public void goToWorld(Zone zone, int x, int y) {
        player.setX(x);
        player.setY(y);
        player.setZone(zone);
        getWorld().getEntityManager().setSelectedEntity(null);
        setWorld(worldHandler.getWorldsMap().get(zone));

        ZoneTransitionState transitionState = new ZoneTransitionState(zone);
        State.setState(transitionState);

        for (Source s : AudioManager.soundfxFiles)
            s.delete();
        playMusic(zone);
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

    public Skill getSkill(SkillsList skill) {
        return skillsUI.getSkill(skill);
    }

    /**
     * Unlock the specified recipe result item
     *
     * @param item - The resulting item of the recipe to be unlocked
     */
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

    public void addQuestStep(QuestList quest, String objective) {
        getQuest(quest).getQuestSteps().add(new QuestStep(objective));
    }

    /*
     * Generates a random numbers between min & max
     */
    public int getRandomNumber(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    /*
     * Rounds off a number to two digits.
     */
    public double roundOff(double value) {
        return (double) Math.ceil(value * 10d) / 10d;
    }

    /*
     * Drop an item to the world
     * @params: An item, an amount, x + y pause in the world (usually based on the Entity or Object location)
     */
    public void dropItem(Item item, int amount, int x, int y) {
        dropItem(item, amount, x, y, true);
    }

    public void dropItem(Item item, int amount, int x, int y, boolean despawn) {
        getWorld().getItemManager().addItem((item.createItem(x, y, amount)), despawn);
    }

    /*
     * Sends a message to the chat log
     */
    public void sendMsg(String message) {
        String[] text = Text.splitIntoLine(message, 66);
        for (String s : text) {
            getChatWindow().sendMessage(s);
        }
    }

    /*
     * Overloaded for printing numerical values to the chat
     */
    public void sendMsg(int message) {
        String[] text = Text.splitIntoLine(String.valueOf(message), 66);
        for (String s : text) {
            getChatWindow().sendMessage(s);
        }
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
        Properties prop = new Properties();
        OutputStream output = null;
        InputStream input = null;

        try {
            input = new FileInputStream("res/settings/config.properties");
            prop.load(input);

            prop.setProperty(propertyKey, propertyValue);

            output = new FileOutputStream("res/settings/config.properties");
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null && output != null) {
                try {
                    input.close();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String loadProperty(String propertyKey) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("res/settings/config.properties");
            prop.load(input);
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        Handler.game = game;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
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

    public boolean isSoundMuted() {
        return soundMuted;
    }

    public void setSoundMuted(boolean soundMuted) {
        this.soundMuted = soundMuted;
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
}
