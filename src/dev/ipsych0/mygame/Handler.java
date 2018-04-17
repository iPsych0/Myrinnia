package dev.ipsych0.mygame;

import java.io.File;
import java.io.Serializable;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import dev.ipsych0.mygame.character.CharacterUI;
import dev.ipsych0.mygame.crafting.CraftingUI;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.gfx.GameCamera;
import dev.ipsych0.mygame.input.KeyManager;
import dev.ipsych0.mygame.input.MouseManager;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.quests.Quest;
import dev.ipsych0.mygame.quests.Quest.QuestState;
import dev.ipsych0.mygame.quests.QuestList;
import dev.ipsych0.mygame.quests.QuestManager;
import dev.ipsych0.mygame.quests.QuestStep;
import dev.ipsych0.mygame.skills.Skill;
import dev.ipsych0.mygame.skills.SkillResource;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.skills.SkillsUI;
import dev.ipsych0.mygame.utils.SaveManager;
import dev.ipsych0.mygame.worlds.Island;
import dev.ipsych0.mygame.worlds.IslandUnderground;
import dev.ipsych0.mygame.worlds.SwampLand;
import dev.ipsych0.mygame.worlds.TestLand;
import dev.ipsych0.mygame.worlds.World;
import dev.ipsych0.mygame.worlds.WorldHandler;

public class Handler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Game game;
	private World world;
	private Island island;
	private WorldHandler worldHandler;
	private Player player;
	private Random rand = new Random();
	private ChatWindow chatWindow;
	private InventoryWindow inventory;
	private EquipmentWindow equipment;
	private QuestManager questManager;
	private MapLoader mapLoader;
	private CraftingUI craftingUI;
	private CharacterUI characterUI;
	private SkillsUI skillsUI;
	private boolean soundMuted = false;
	
	/*
	 * Set to true for debug mode
	 */
	public static boolean debugMode = false;
	
	/*
	 * Index 0: Island
	 * Index 1: TestLand
	 * Index 2: SwampLand
	 * Index 3: IslandUnderground
	 */
	
	public Handler(Game game){
		this.game = game;
		
		// Instantiate the player and the worlds
		player = new Player(this, 5120, 5600);
		chatWindow = new ChatWindow(this, 0, 600); //228,314
		chatWindow.sendMessage("Welcome back!");
		inventory = new InventoryWindow(this, 828, 0);
		equipment = new EquipmentWindow(this, 828, 372);
		questManager = new QuestManager(this);
		mapLoader = new MapLoader();
		craftingUI = new CraftingUI(this, 0, 180);
		characterUI = new CharacterUI(this);
		skillsUI = new SkillsUI(this);
		island = new Island(this, "res/worlds/island.tmx", 0);
		worldHandler = new WorldHandler(this, island);
		worldHandler.addWorld(new TestLand(this, "res/worlds/testmap2.tmx", 1));
		worldHandler.addWorld(new SwampLand(this, "res/worlds/testmap.tmx", 2));
		worldHandler.addWorld(new IslandUnderground(this, "res/worlds/island_indoors.tmx", 3));
	}
	
	public SkillResource getSkillResource(SkillsList skill, Item item) {
		SkillResource resource = skillsUI.getSkill(skill).getResourceByItem(item);
		return resource;
	}
	
	public boolean playerHasSkillLevel(SkillsList skill, Item item) {
		SkillResource resource = skillsUI.getSkill(skill).getResourceByItem(item);
		if(resource != null) {
			if(skillsUI.getSkill(skill).getLevel() >= resource.getLevelRequirement()) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	
	public Skill getSkill(SkillsList skill) {
		return skillsUI.getSkill(skill);
	}
	
	public void discoverRecipe(Item item) {
		craftingUI.getCraftingRecipeList().getRecipeByItem(item).setDiscovered(this, true);
	}
	
	public boolean questStarted(QuestList quest) {
		if(questManager.getQuestMap().get(quest).getState() == QuestState.NOT_STARTED)
			return false;
		else
			return true;
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
	public int getRandomNumber(int min, int max){
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		return randomNumber;
	}
	
	/*
	 * Plays music (basic function.. needs expanding to check area)
	 */
	public void playMusic(String pathToMusic) {
		if(!soundMuted) {
			try {
				File file = new File(pathToMusic);
				if(file.exists()) {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(file));
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	/*
	 * Rounds off a number to two digits.
	 */
	public double roundOff(double value) {
	    return (double)Math.ceil(value * 10d) / 10d;
	}
	
	/*
	 * Drop an item to the world
	 * @params: An item, an amount, x + y position in the world (usually based on the Entity or Object location)
	 */
	public void dropItem(Item item, int amount, int x, int y) {
		getWorld().getItemManager().addItem((item.createNew(x, y, amount)));
	}
	
	/*
	 * Sends a message to the chat log
	 */
	public void sendMsg(String message) {
		getChatWindow().sendMessage(message);
	}
	
	/*
	 * Checks if the inventory is full when picking up/receiving an item
	 * @param: Provide the item that needs to be checked if it can be added to the inventory
	 */
	public boolean invIsFull(Item item) {
		return getInventory().inventoryIsFull(item);
	}
	
	/*
	 * Adds an item + quantity to the player's inventory. Usually followed by invIsFull() function
	 * @params: Provide the item and the amount to be added
	 */
	public void giveItem(Item item, int amount) {
		if(getInventory().findFreeSlot(item) == -1) {
			sendMsg("The item(s) were dropped to the floor.");
			dropItem(item, amount, (int)player.getX(), (int)player.getY());
		} else{
			getInventory().getItemSlots().get(getInventory().findFreeSlot(item)).addItem(item, amount);
		}
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
	
	/*
	 * Getters & Setters
	 */
	
	public int getWidth(){
		return game.getWidth();
	}
	
	public int getHeight(){
		return game.getHeight();
	}
	
	public KeyManager getKeyManager(){
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager(){
		return game.getMouseManager();
	}
	
	public GameCamera getGameCamera(){
		return game.getGameCamera();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

//	public void setWorld(int worldId) {
//		this.world = getWorldHandler().getWorlds().get(id);
//	}
	
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

	public MapLoader getMapLoader() {
		return mapLoader;
	}

	public void setMapLoader(MapLoader mapLoader) {
		this.mapLoader = mapLoader;
	}

	public CraftingUI getCraftingUI() {
		return craftingUI;
	}

	public void setCraftingUI(CraftingUI craftingUI) {
		this.craftingUI = craftingUI;
	}

	public SaveManager getSaveManager() {
		return game.getSaveManager();
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

}
