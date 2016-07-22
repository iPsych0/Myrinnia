package dev.ipsych0.mygame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;

public class ChatWindow {
	
	public static boolean isTalking = false;
	
	private int x, y;
	private int width, height;
	private Handler handler;
	
	public ChatWindow(Handler handler, int x, int y){
		this.handler = handler;
		this.x = x;
		this.y = y;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(isTalking){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x + 64, y + 192, width + 224, height + 64);
			g.setColor(Color.BLACK);
			g.drawRect(x + 64, y + 192, width + 224, height + 64);
		}
	}

}
