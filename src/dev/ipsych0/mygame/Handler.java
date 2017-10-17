package dev.ipsych0.mygame;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.GameCamera;
import dev.ipsych0.mygame.input.KeyManager;
import dev.ipsych0.mygame.input.MouseManager;
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
	private SwampLand swampLand;
	private WorldHandler worldHandler;
	private Player player;
	private Random rand = new Random();
	
	/*
	 * Index 0: SwampLand
	 * Index 1: TestLand
	 * Index 2: Island
	 */
	
	public Handler(Game game){
		this.game = game;
		player = new Player(this, 260, 220);
		swampLand = new SwampLand(this, player, "res/worlds/testmap.tmx", 0);
		worldHandler = new WorldHandler(this, swampLand);
		worldHandler.addWorld(new TestLand(this, player, "res/worlds/testmap2.tmx", 1));
		worldHandler.addWorld(new Island(this, player, "res/worlds/island.tmx", 2));
		worldHandler.addWorld(new IslandUnderground(this, player, "res/worlds/island_indoors.tmx", 3));
	}
	
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
	
	public int getRandomNumber(int min, int max){
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		return randomNumber;
	}
	
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
	
	public static double roundOff(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    
	    if(Double.isNaN(value) || Double.isInfinite(value)) {
	    	System.out.println("Oneindig of Not Number");
	    	return 100.0;
	    }
	    
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	/*
	 * Shortcut functions from here on out
	 */
	
	public void dropItem(Item item, int amount, int x, int y) {
		getWorld().getItemManager().addItem((item.createNew(x, y, amount)));
	}
	
	public void sendMsg(String message) {
		getPlayer().getChatWindow().sendMessage(message);
	}
	
	public boolean invIsFull(Item item) {
		return getWorld().getInventory().inventoryIsFull(item);
	}
	
	public void giveItem(Item item, int amount) {
		getWorld().getInventory().getItemSlots().get(getWorld().getInventory().findFreeSlot(item)).addItem(item, amount);
	}
	
	public boolean removeItem(Item item, int amount) {
		return getWorld().getInventory().removeItem(item, amount);
	}
	
	public boolean playerHasItem(Item item, int amount) {
		return getWorld().getInventory().playerHasItem(item, amount);
	}

}
