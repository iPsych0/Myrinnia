package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.states.GameState;

public class ChatWindow {
	
	public static boolean talkButtonPressed = false;
	private boolean isCreated = false;
	
	private int x, y;
	private int width, height;
	private Handler handler;
	int alpha = 16;
	Color interfaceColour = new Color(130, 130, 130, alpha);

	private int numCols = 1;
	private int numRows = 6;
	public static Color chatColour = new Color(140, 0, 255);
	
	private static CopyOnWriteArrayList<TextSlot> textSlots;
	
	public ChatWindow(Handler handler, int x, int y){
		if(isCreated == false){
			this.handler = handler;
			this.x = x;
			this.y = y;
		
			textSlots = new CopyOnWriteArrayList<TextSlot>();
			
			for(int i = 0; i < numCols; i++){
				for(int j = 0; j < numRows; j++){
					if(j == (numRows)){
						x += 8;
					}
					
					textSlots.add(new TextSlot(x + (i * (TextSlot.textWidth)), y + (j * 12), null));
					
					if(j == (numRows)){
						x -= 8;
					}
				}
			}	
			width = numCols * (TextSlot.textWidth);
			height = numRows * (TextSlot.textHeight + 10);
			isCreated = true;
		}
	}
	
	public void tick(){
		if(talkButtonPressed){
			for(TextSlot ts : textSlots){
				ts.tick();
			}
		}
	}
	
	public void render(Graphics g){
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.playerIsNearNpc()){
				g.setColor(Color.BLACK);
				g.drawRect(x - 228, y + 170, width, height - 118);
				g.setColor(interfaceColour);
				g.fillRect(x - 228, y + 170, width, height - 118);
				g.setFont(GameState.myFont);
				g.setColor(Color.WHITE);
				g.drawString("Chat", x, y + 182);
				
				for(TextSlot ts : textSlots){
					ts.render(g);
				}
			}
		}
	}
	
	public CopyOnWriteArrayList<TextSlot> getTextSlots(){
		return textSlots;
	}
}
