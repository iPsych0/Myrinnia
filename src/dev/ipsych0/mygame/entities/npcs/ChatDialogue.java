package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.Text;

public class ChatDialogue implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x, y, width, height;
	private ArrayList<ChatOptions> chatOptions;
	private ContinueButton continueButton;
	public static boolean hasBeenPressed = false;
	private String[] menuOptions;
	private Rectangle continueButtonBounds;
	private int optionID;
	private ChatOptions chosenOption;
	
	public ChatDialogue(String[] menuOptions) {
		this.width = Handler.get().getChatWindow().getWidth();
		this.height = Handler.get().getChatWindow().getHeight();
		this.x = Handler.get().getWidth() - width - 8;
		this.y = Handler.get().getHeight() - height - 16;
		this.menuOptions = menuOptions;
		
		chatOptions = new ArrayList<ChatOptions>();
		
		// Zie DialogueBox functie voor inladen!!!
		if(menuOptions.length > 1) {
			for(int i = 0; i < menuOptions.length; i++) {
				chatOptions.add(new ChatOptions(x + 16, y + 11 + (20 * i), i, menuOptions[i]));
			}
		}else{
			continueButton = new ContinueButton(x + (width / 2) - 50, y + 12 + (20 * 4));
			continueButtonBounds = new Rectangle(continueButton.getX(), continueButton.getY(), continueButton.getWidth(), continueButton.getHeight());
		}
	}
	
	public void tick() {
		Rectangle mouse = Handler.get().getMouse();
		
		if(menuOptions.length > 1) {
			for(ChatOptions option : chatOptions) {
				
				option.tick();
				
				Rectangle optionSlot = new Rectangle(option.getX(), option.getY(), option.getWidth(), option.getHeight());
				
				if(optionSlot.contains(mouse)) {
					option.setHovering(true);
					if(Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !Handler.get().getMouseManager().isDragged()) {
						hasBeenPressed = false;
						chosenOption = option;
						Player.hasInteracted = false;
					}
				}else {
					option.setHovering(false);
				}
			}
		}else {
			continueButton.tick();
			if(continueButtonBounds.contains(mouse)) {
				continueButton.setHovering(true);
				if(Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !Handler.get().getMouseManager().isDragged()) {
					hasBeenPressed = false;
					Player.hasInteracted = false;
					continueButton.setPressed(true);
				}
			}else {
				continueButton.setHovering(false);
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.chatwindow, x, y, width, height + 8, null);
		
		if(menuOptions.length > 1) {
			for(ChatOptions option : chatOptions) {
				option.render(g);
			}
		}else if(menuOptions.length == 1){
			for(int i = 0; i < Text.splitIntoLine(menuOptions[0], 60).length; i++) {
				Text.drawString(g, Text.splitIntoLine(menuOptions[0], 60)[i], x + (width / 2), y + 20 + (i * 12), true, Color.YELLOW, Assets.font14);
			}
			continueButton.render(g);
		}
		
		g.drawImage(Assets.chatwindowTop, x, y - 19, width, 20, null);
		Text.drawString(g, Handler.get().getPlayer().getClosestEntity().getClass().getSimpleName(), x + (width / 2), y - 9, true, Color.YELLOW, Assets.font14);
	}

	public ArrayList<ChatOptions> getChatOptions() {
		return chatOptions;
	}

	public void setChatOptions(ArrayList<ChatOptions> chatOptions) {
		this.chatOptions = chatOptions;
	}

	public int getOptionID() {
		return optionID;
	}

	public void setOptionID(int optionID) {
		this.optionID = optionID;
	}

	public ContinueButton getContinueButton() {
		return continueButton;
	}

	public void setContinueButton(ContinueButton continueButton) {
		this.continueButton = continueButton;
	}

	public ChatOptions getChosenOption() {
		return chosenOption;
	}

	public void setChosenOption(ChatOptions chosenOption) {
		this.chosenOption = chosenOption;
	}

	public String[] getMenuOptions() {
		return menuOptions;
	}

	public void setMenuOptions(String[] menuOptions) {
		this.menuOptions = menuOptions;
	}

}
