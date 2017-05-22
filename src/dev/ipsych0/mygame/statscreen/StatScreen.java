package dev.ipsych0.mygame.statscreen;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.states.GameState;

public class StatScreen {
	
	public static boolean isOpen = false;
	private boolean isCreated = false;
	
	private int x, y;
	private int width, height;
	private Handler handler;
	int alpha = 127;
	Color interfaceColour = new Color(100, 100, 100, alpha);
	
	public StatScreen(Handler handler, int x, int y){
		if(!isCreated){
			this.handler = handler;
			this.x = x;
			this.y = y;
		
			isCreated = true;
		}
	}
	
	public void tick(){
		if(isOpen){
			// for button : buttons, tick()
		}
	}
	
	// Renders even if not within distance --> fix
	public void render(Graphics g){
		if(isOpen){
			// for button : buttons, render(g)
		}
	}
}
