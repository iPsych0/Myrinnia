package dev.ipsych0.mygame;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import dev.ipsych0.mygame.gfx.GameCamera;
import dev.ipsych0.mygame.input.KeyManager;
import dev.ipsych0.mygame.input.MouseManager;
import dev.ipsych0.mygame.worlds.SwampLand;
import dev.ipsych0.mygame.worlds.TestLand;
import dev.ipsych0.mygame.worlds.World;
import dev.ipsych0.mygame.worlds.WorldHandler;

public class Handler {
	
	private Game game;
	private World world;
	private SwampLand swampLand;
	private WorldHandler worldHandler;
	private int min = 1, max = 6;
	Random rand = new Random();
	
	public Handler(Game game){
		this.game = game;
		swampLand = new SwampLand(this, "res/worlds/testmap.tmx");
		worldHandler = new WorldHandler(this, swampLand);
		worldHandler.addWorld(new TestLand(this, "res/worlds/testmap2.tmx"));
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
	
	public int getRandomSupplyAmount(){
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		return randomNumber;
	}
	
	public void playMusic(String pathToMusic) {
		try {
			File file = new File(pathToMusic);
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
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

}
