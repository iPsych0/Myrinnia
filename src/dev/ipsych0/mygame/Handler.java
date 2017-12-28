package dev.ipsych0.mygame;

import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.gfx.GameCamera;
import dev.ipsych0.mygame.input.KeyManager;
import dev.ipsych0.mygame.input.MouseManager;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.worlds.Island;
import dev.ipsych0.mygame.worlds.IslandUnderground;
import dev.ipsych0.mygame.worlds.SwampLand;
import dev.ipsych0.mygame.worlds.TestLand;
import dev.ipsych0.mygame.worlds.World;
import dev.ipsych0.mygame.worlds.WorldHandler;

public class Handler {

	private Game game;
	private World world;
	private Island island;
	private WorldHandler worldHandler;
	private Player player;
	private Random rand = new Random();
	private ChatWindow chatWindow;
	private InventoryWindow inventory;
	private EquipmentWindow equipment;
	
	/*
	 * Index 0: Island
	 * Index 1: TestLand
	 * Index 2: SwampLand
	 * Index 3: IslandUnderground
	 */
	
	public Handler(Game game){
		this.game = game;
		
		// Instantiate the player and the worlds
		player = new Player(this, 4096, 2944);
		chatWindow = new ChatWindow(this, 0, 600); //228,314
		chatWindow.sendMessage("Welcome back!");
		inventory = new InventoryWindow(this, 828, 0);
		equipment = new EquipmentWindow(this, 828, 372);
		island = new Island(this, player, chatWindow, inventory, equipment, "res/worlds/island.tmx", 0);
		worldHandler = new WorldHandler(this, island);
		worldHandler.addWorld(new TestLand(this, player, chatWindow, inventory, equipment, "res/worlds/testmap2.tmx", 1));
		worldHandler.addWorld(new SwampLand(this, player, chatWindow, inventory, equipment, "res/worlds/testmap.tmx", 2));
		worldHandler.addWorld(new IslandUnderground(this, player, chatWindow, inventory, equipment, "res/worlds/island_indoors.tmx", 3));
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
	
	/*
	 * Rounds off a number to two digits.
	 */
	public double roundOff(double value) {
	    return (double)Math.round(value * 10d) / 10d;
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
		getWorld().getChatWindow().sendMessage(message);
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
		getInventory().getItemSlots().get(getWorld().getInventory().findFreeSlot(item)).addItem(item, amount);
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

}
