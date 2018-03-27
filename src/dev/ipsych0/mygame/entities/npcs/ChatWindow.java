package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.Text;

public class ChatWindow implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static boolean chatIsOpen = true;
	private boolean isCreated = false;
	
	private int x, y;
	private int width, height;
	private Handler handler;

	private int numCols = 1;
	private int numRows = 7;
	private Rectangle windowBounds;
	
	private CopyOnWriteArrayList<TextSlot> textSlots;
	
	public ChatWindow(Handler handler, int x, int y){
		if(!isCreated){
			this.handler = handler;
			this.x = x;
			this.y = y;
		
			textSlots = new CopyOnWriteArrayList<TextSlot>();
			
			for(int i = 0; i < numCols; i++){
				for(int j = 0; j < numRows; j++){
					if(j == (numRows)){
						x += 8;
					}
					
					textSlots.add(new TextSlot(x + (i * (TextSlot.textWidth)), y + (j * TextSlot.textHeight), null));
					
					if(j == (numRows)){
						x -= 8;
					}
				}
			}	
			width = numCols * (TextSlot.textWidth);
			height = numRows * (TextSlot.textHeight + 1);
			windowBounds = new Rectangle(x, y, width, height);
			isCreated = true;
		}
	}
	
	public void tick(){
		if(chatIsOpen){
			for(TextSlot ts : textSlots){
				ts.tick();
				
			}
		}
	}
	
	// Renders even if not within distance --> fix
	public void render(Graphics g){
		if(chatIsOpen){
//			g.setColor(interfaceColour);
//			g.fillRect(x, y, width, height - 121); 
//			g.setColor(Color.BLACK);
//			g.drawRect(x, y, width, height - 121);
//			g.setFont(GameState.myFont);
			
			g.drawImage(Assets.chatwindow, x, y, width, height + 8, null);
			g.drawImage(Assets.chatwindowTop, x, y - 9, width, 20, null);
			String world = handler.getWorld().getClass().getSimpleName().toString();
			world = world.substring(0,1).toUpperCase() + world.substring(1).toLowerCase();
			Text.drawString(g, world, x + (width / 2), y + 1, true, Color.YELLOW, Assets.font14);
			
			for(TextSlot ts : textSlots){
				ts.render(g);
				
			}
		}
	}
	
	/*
	 * Sends a message to the chat log
	 */
	public boolean sendMessage (String message) {
        int chatIndex = freeTextSlot();
       // System.out.println("The free slot in sendMessage = '" + chatIndex + "'");
        if(chatIndex >= 0){
	    	getTextSlots().get(chatIndex).addTextSlot(message);
	    	//System.out.println("Added the line '" + message + "'");
	    	return true;
        }
        else{
        	System.out.println("Something went wrong with the chatIndex in sendMessage:ChatWindow (negative index)");
        	return false;
        }
    }
	
	/*
	 * Makes the chat shift and pushes messages off the stack to make room for new messages
	 */
	public int freeTextSlot() {
		// Als de chat leeg is, vul altijd de 1e slot
		if(textSlots.get(textSlots.size() - 1).getNpcText() == null){
			return (textSlots.size() - 1);
		}
        for (int i = 0; i < textSlots.size(); i++) {
        	// Als textslot (i) != null is ...
        	if (textSlots.get(i).getNpcText() != null) {
        		// Als alle slots vol zijn, maak de bovenste slot dan "null" en ga door met het 1e vakje (die zet ie dan weer naar 0, etc. voor de rest)
        		if(i == 0){
        			textSlots.get(0).setNpcText(null);
        			continue;
        		}
        		// Zet textslot (i) in temporary
        		NPCText temporary = textSlots.get(i).getNpcText();
        		// Zet slot i - 1 (0 - 1 = -1) naar temp
        		textSlots.get(i - 1).setNpcText(temporary);
            }
        }
        return (textSlots.size() - 1);
	}
	
	public CopyOnWriteArrayList<TextSlot> getTextSlots(){
		return textSlots;
	}

	public Rectangle getWindowBounds() {
		return windowBounds;
	}

	public void setWindowBounds(Rectangle windowBounds) {
		this.windowBounds = windowBounds;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
