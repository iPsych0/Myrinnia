package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;

public class ChatWindow {
	
	public static boolean talkButtonPressed = false;
	
	private int x, y;
	private int width, height;
	private Handler handler;
	int alpha = 127;
	Color interfaceColour = new Color(130, 130, 130, alpha);
	public static Color chatColour = new Color(140, 0, 255);
	
	public ChatWindow(Handler handler, int x, int y){
		this.handler = handler;
		this.x = x;
		this.y = y;
		// hoi2
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.playerIsNearNpc())
				if(e.talking){
					g.setColor(interfaceColour);
					g.fillRect(x + 16, y + 192, width + 304, height + 64);
					g.setColor(Color.BLACK);
					g.drawRect(x + 16, y + 192, width + 304, height + 64);
					g.setColor(Color.BLACK);
					g.drawRect(x + 16, y + 192, width + 304, height + 16);
					e.says(g, "Lorraine", "This is a test.");
			}
		}
	}

}
