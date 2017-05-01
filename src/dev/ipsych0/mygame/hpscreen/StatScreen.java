package dev.ipsych0.mygame.hpscreen;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;

public class StatScreen {

	public static boolean isCreated = false;
	public static boolean isOpen = false;
	
	private int x, y;
	private int width, height;
	private Handler handler;
	int alpha = 127;
	Color interfaceColour = new Color(130, 130, 130, alpha);
	
	private static CopyOnWriteArrayList<StatSlot> statSlots;
	private int numCols = 3;
	private int numRows = 2;
	private StatButton statButton;
	
	public StatScreen(Handler handler, int x, int y){
		if(isCreated == false){
			this.x = x;
			this.y = y;
			this.handler = handler;
			statSlots = new CopyOnWriteArrayList<StatSlot>();
			
			for(int i = 0; i < numCols; i++){
				for(int j = 0; j < numRows; j++){
					if(j == (numRows)){
						x += 8;
					}
					
					statSlots.add(new StatSlot(x + (i * (StatSlot.SLOTSIZE)), y + (j * StatSlot.SLOTSIZE) + 16, null));
					
					if(j == (numRows)){
						x -= 8;
					}
				}
			}
			
			int testNumber = 0;
			for(int i = 0; i < statSlots.size(); i++){
				statSlots.get(i).addStatSlot(Assets.black, "Test");
				testNumber++;
			}
			
			width = (3 * 42) - 29;
			height = 96;
			
			// Prevents multiple instances of the statscreen being created
			isCreated = true;
		}
	}
	
	public void tick(){
		if(isOpen){
			
			for(StatSlot ss : statSlots){
				ss.tick();
			}
			// iets qua klikken op HP / levels?
		}
	}
	
	public void render(Graphics g){
		if(isOpen){
			g.setColor(interfaceColour);
			g.fillRect(x - 16, y - 1, width + 32, height + 1);
			g.setColor(Color.BLACK);
			g.drawRect(x - 16, y - 1, width + 32, height + 1);
			g.setColor(Color.WHITE);
			g.drawString("Player Stats", x + 16, y + 13);
			
			for(StatSlot ss : statSlots){
				ss.render(g);
			}
		}
	}

	public static CopyOnWriteArrayList<StatSlot> getStatSlots() {
		return statSlots;
	}

	public static void setStatSlots(CopyOnWriteArrayList<StatSlot> statSlots) {
		StatScreen.statSlots = statSlots;
	}
	
}
