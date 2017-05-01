package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.states.GameState;

public class ChatWindow {
	
	public static boolean chatIsOpen = false;
	private boolean isCreated = false;
	
	private int x, y;
	private int width, height;
	private Handler handler;
	private static NPCText temporary;
	int alpha = 127;
	Color interfaceColour = new Color(100, 100, 100, alpha);

	private int numCols = 1;
	private int numRows = 6;
	public static Color chatColour = new Color(140, 0, 255);
	
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
		if(chatIsOpen){
			for(TextSlot ts : textSlots){
				ts.tick();
				
			}
		}
	}
	
	// Renders even if not within distance --> fix
	public void render(Graphics g){
		if(chatIsOpen){
			g.setColor(interfaceColour);
			g.fillRect(x - 228, y + 170, width, height - 118);
			g.setColor(Color.BLACK);
			g.drawRect(x - 228, y + 170, width, height - 118);
			g.setFont(GameState.myFont);
			g.setColor(Color.WHITE);
			g.drawString("Myrinnia", x, y + 182);
			
			for(TextSlot ts : textSlots){
				ts.render(g);
				
			}
		}
	}
	
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
	
	// Free slots vrijmaken!
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
        		temporary = textSlots.get(i).getNpcText();
        		// Zet slot i - 1 (0 - 1 = -1) naar temp
        		textSlots.get(i - 1).setNpcText(temporary);
            }
        }
        return (textSlots.size() - 1);
	}
	
	public CopyOnWriteArrayList<TextSlot> getTextSlots(){
		return textSlots;
	}
}
