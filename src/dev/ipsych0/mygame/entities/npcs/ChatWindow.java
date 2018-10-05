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
	private static final long serialVersionUID = -5673402739754368073L;

	public static boolean chatIsOpen = true;
	
	private int x, y;
	private int width, height;

	private int numCols = 1;
	private int numRows = 7;
	private Rectangle windowBounds;
	
	private CopyOnWriteArrayList<TextSlot> textSlots;
	
	public ChatWindow(){
		textSlots = new CopyOnWriteArrayList<TextSlot>();
		width = numCols * (TextSlot.textWidth);
		height = numRows * (TextSlot.textHeight + 1);
		this.x = 8;
		this.y = Handler.get().getHeight() - height - 16;
		
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
		
		windowBounds = new Rectangle(x, y, width, height);
	}
	
	public void tick(){
		if(chatIsOpen){
			for(TextSlot ts : textSlots){
				ts.tick();
				
			}
		}
	}
	
	public void render(Graphics g){
		if(chatIsOpen){
			g.drawImage(Assets.chatwindow, x, y, width, height + 8, null);
			g.drawImage(Assets.chatwindowTop, x, y - 19, width, 20, null);
			
			Text.drawString(g, Handler.get().getPlayer().getZone().getName(), x + (width / 2), y - 8, true, Color.YELLOW, Assets.font14);
			
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
	    	getTextSlots().get(chatIndex).setMessage(message);
	    	//System.out.println("Added the line '" + message + "'");
	    	return true;
        }
        else{
        	System.err.println("Something went wrong with the chatIndex in sendMessage:ChatWindow (negative index)");
        	return false;
        }
    }
	
	/*
	 * Makes the chat shift and pushes messages off the stack to make room for new messages
	 */
	public int freeTextSlot() {
		// Als de chat leeg is, vul altijd de 1e slot
		if(textSlots.get(textSlots.size() - 1).getMessage() == null){
			return (textSlots.size() - 1);
		}
        for (int i = 0; i < textSlots.size(); i++) {
        	// Als textslot (i) != null is ...
        	if (textSlots.get(i).getMessage() != null) {
        		// Als alle slots vol zijn, maak de bovenste slot dan "null" en ga door met het 1e vakje (die zet ie dan weer naar 0, etc. voor de rest)
        		if(i == 0){
        			textSlots.get(0).setMessage(null);
        			continue;
        		}
        		// Zet textslot (i) in temporary
        		String temporary = textSlots.get(i).getMessage();
        		// Zet slot i - 1 (0 - 1 = -1) naar temp
        		textSlots.get(i - 1).setMessage(temporary);
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
