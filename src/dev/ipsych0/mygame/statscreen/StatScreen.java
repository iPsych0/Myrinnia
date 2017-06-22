package dev.ipsych0.mygame.statscreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.states.GameState;

public class StatScreen {
	
	public static boolean isOpen = false;
	private boolean isCreated = false;
	
	private int x, y;
	private int width, height;
	private Handler handler;
	private boolean hasBeenPressed = false;
	private boolean statSlotClicked = false;
	private StatButton selectedButton;
	int alpha = 127;
	Color interfaceColour = new Color(130, 130, 130, alpha);
	
	private static CopyOnWriteArrayList<StatSlot> statSlots;
	
	public StatScreen(Handler handler, int x, int y){
		if(!isCreated){
			this.handler = handler;
			this.x = x;
			this.y = y;
			statSlots = new CopyOnWriteArrayList<StatSlot>();
			
			// Add statButtons here
			statSlots.add(new StatSlot(x + 17, y + 36, 96, 32, null));
			statSlots.add(new StatSlot(x + 17, y + 84, 96, 32, null));
			statSlots.add(new StatSlot(x + 17, y + 132, 96, 32, null));
			statSlots.add(new StatSlot(x + 17, y + 180, 96, 32, null));
			
			
			// Set the texture and text here
			statSlots.get(0).addButton(Assets.black, "Quests");
			statSlots.get(1).addButton(Assets.black, "Combat");
			statSlots.get(2).addButton(Assets.black, "Gathering");
			statSlots.get(3).addButton(Assets.black, "Artisan");
		
			isCreated = true;
		}
	}
	
	public void tick(){
		if(isOpen){
			Rectangle temp = new Rectangle(handler.getWorld().getHandler().getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);

			for(StatSlot ss : statSlots){
			
				Rectangle temp2 = new Rectangle(ss.getX(), ss.getY(), ss.getWidth(), ss.getHeight());
				
				if(temp2.contains(temp) && !statSlotClicked && handler.getMouseManager().isLeftPressed()){
					statSlotClicked = true;
					selectedButton = ss.getStatButton();
					hasBeenPressed = false;
				}
				else if(temp2.contains(temp) && statSlotClicked && handler.getMouseManager().isLeftPressed()){
					selectedButton = ss.getStatButton();
					hasBeenPressed = false;
				}
			}
		}
	}
	
	// Renders even if not within distance --> fix
	public void render(Graphics g){
		if(isOpen){
			g.setColor(interfaceColour);
			g.fillRect(x, y, 132, 238);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, 132, 238);
			g.setColor(Color.WHITE);
			g.drawString("Skills", x + 48, y + 16);
			// for button : buttons, render(g)
				
			for(StatSlot ss : statSlots){
				ss.render(g);
				
				if(statSlotClicked && selectedButton.getButtonText() == "Quests"){
					g.setColor(Color.RED);
					g.fillRect(200, 200, 200, 200);
					g.setColor(Color.BLACK);
					g.drawRect(200, 200, 200, 200);
				}
				if(statSlotClicked && selectedButton.getButtonText() == "Combat"){
					g.setColor(Color.GREEN);
					g.fillRect(200, 200, 200, 200);
					g.setColor(Color.BLACK);
					g.drawRect(200, 200, 200, 200);
				}
			}
		}
	}
}
