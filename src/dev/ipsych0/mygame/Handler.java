package dev.ipsych0.mygame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Random;

import dev.ipsych0.mygame.abilities.AbilityManager;
import dev.ipsych0.mygame.audio.AudioManager;
import dev.ipsych0.mygame.audio.Source;
import dev.ipsych0.mygame.bank.BankUI;
import dev.ipsych0.mygame.character.CharacterUI;
import dev.ipsych0.mygame.crafting.CraftingUI;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.gfx.GameCamera;
import dev.ipsych0.mygame.gfx.ScreenShot;
import dev.ipsych0.mygame.hpoverlay.HPOverlay;
import dev.ipsych0.mygame.input.KeyManager;
import dev.ipsych0.mygame.input.MouseManager;
import dev.ipsych0.mygame.items.EquipSlot;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemType;
import dev.ipsych0.mygame.quests.Quest;
import dev.ipsych0.mygame.quests.Quest.QuestState;
import dev.ipsych0.mygame.quests.QuestList;
import dev.ipsych0.mygame.quests.QuestManager;
import dev.ipsych0.mygame.quests.QuestStep;
import dev.ipsych0.mygame.recap.RecapEvent;
import dev.ipsych0.mygame.recap.RecapManager;
import dev.ipsych0.mygame.skills.Skill;
import dev.ipsych0.mygame.skills.SkillResource;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.skills.SkillsUI;
import dev.ipsych0.mygame.states.State;
import dev.ipsych0.mygame.states.ZoneTransitionState;
import dev.ipsych0.mygame.utils.SaveManager;
import dev.ipsych0.mygame.worlds.Island;
import dev.ipsych0.mygame.worlds.World;
import dev.ipsych0.mygame.worlds.WorldHandler;
import dev.ipsych0.mygame.worlds.Zone;

public class Handler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Game game;
	private World world;
	private Island island;
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
	private boolean soundMuted = false;
	public static String initialWorldPath = "res/worlds/island.tmx";
	
	private static Handler handler;
	
	/*
	 * Flag: Set to true for debug mode
	 */
	public static boolean debugMode = false;
	
	public static Handler get() {
		if(handler == null) {
			handler = new Handler();
			handler.init();
		}
		return handler;
	}
	
	private Handler(){
		
	}
	
	private void init() {
		handler.setGame(Game.get());
		
		// Instantiate the player
		player = new Player(5120, 5600);
		
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
		abilityManager = new AbilityManager();
		recapManager = new RecapManager();
		
		// Set the starting world
		island = new Island("res/worlds/island.tmx");
		worldHandler = new WorldHandler(island);
	}
	
	public void playMusic(Zone zone) {
		if(!soundMuted) {
			int buffer = -1;
			String songName = zone.getMusicFile();
			try {
				buffer = AudioManager.loadSound(new File("res/music/" + songName));
			} catch (FileNotFoundException e) {
				System.err.println("Couldn't find file: "+songName);
				e.printStackTrace();
				System.exit(0);
			}
			
			// Fade from first song to the next
			AudioManager.fadeSongs(zone, buffer);
		}
	}
	
	public void playEffect(String effect, float x, float y) {
		if(!soundMuted) {
			int buffer = -1;
			try {
				buffer = AudioManager.loadSound(new File("res/music/" + effect));
			} catch (FileNotFoundException e) {
				System.err.println("Couldn't find file: "+effect);
				e.printStackTrace();
				System.exit(0);
			}
			AudioManager.soundfxFiles.add(new Source());
			AudioManager.soundfxFiles.getLast().setVolume(0.2f);
			AudioManager.soundfxFiles.getLast().setLooping(false);
			AudioManager.soundfxFiles.getLast().playEffect(buffer);
			
		}
	}
	
	public void addRecapEvent(String description) {
		this.recapManager.addEvent(new RecapEvent(ScreenShot.take(), description));
	}
	
	/**
	 * Checks if the player has quest requirements to begin specified quest
	 * @param quest - The quest to check requirements for
	 * @return - true if requirements are met, false if not
	 */
	public boolean hasQuestReqs(QuestList quest) {
		Quest q = getQuest(quest);
		for(int i = 0; i < q.getRequirements().length; i++) {
			if(q.getRequirements()[i].getSkill() != null) {
				if(getSkill(q.getRequirements()[i].getSkill()).getLevel() < q.getRequirements()[i].getLevel()){
					return false;
				}
			}
			else if(q.getRequirements()[i].getQuest() != null) {
				if(getQuest(q.getRequirements()[i].getQuest()).getState() != QuestState.COMPLETED) {
					return false;
				}
			}
		}
		// TODO: ADD CHECK FOR MISCELLANEOUS REQUIREMENTS (DESCRIPTION)
		return true;
	}
	
	/**
	 * Go from your current world to the next
	 * @param zone - The new zone to enter
	 * @param x - The X position in the new zone
	 * @param y - The Y position in the new zone
	 */
	public void goToWorld(Zone zone, int x, int y) {
		player.setX(x);
		player.setY(y);
		player.setZone(zone);
		setWorld(worldHandler.getWorldsMap().get(zone));
		
		ZoneTransitionState transitionState = new ZoneTransitionState(zone);
		State.setState(transitionState);
		
		for(Source s : AudioManager.soundfxFiles)
			s.delete();
		playMusic(zone);
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
		}
		return false;
	}
	
	public Skill getSkill(SkillsList skill) {
		return skillsUI.getSkill(skill);
	}
	
	/**
	 * Unlock the specified recipe result item
	 * @param item - The resulting item of the recipe to be unlocked
	 */
	public void discoverRecipe(Item item) {
		craftingUI.getCraftingRecipeList().getRecipeByItem(item).setDiscovered(this, true);
	}
	
	public boolean questStarted(QuestList quest) {
		if(questManager.getQuestMap().get(quest).getState() == QuestState.NOT_STARTED)
			return false;
		
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
		int randomNumber = new Random().nextInt((max - min) + 1) + min;
		return randomNumber;
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
		if(item.getEquipSlot() == EquipSlot.NONE.getSlotId())
			getWorld().getItemManager().addItem((item.createUnequippableItem(x, y, amount)));
		else
			getWorld().getItemManager().addItem((item.createEquippableItem(x, y, amount)));
	}
	
	public void dropItem(Item item, int amount, int x, int y, boolean despawn) {
		if(item.getEquipSlot() == EquipSlot.NONE.getSlotId())
			getWorld().getItemManager().addItem((item.createUnequippableItem(x, y, amount)), despawn);
		else
			getWorld().getItemManager().addItem((item.createEquippableItem(x, y, amount)), despawn);
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
	
	public boolean playerHasItemType(ItemType type) {
		return getInventory().playerHasItemType(type);
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

}
