package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Assets;

public class ChatDialogue {
	
	private int x, y, width, height;
	private Handler handler;
	private ArrayList<ChatOptions> chatOptions;
	private ContinueButton continueButton;
	public static boolean hasBeenPressed = false;
	private boolean choiceMenu = false;
	private Rectangle continueButtonBounds;
	private int optionID;
	private ChatOptions chosenOption;
	
	public ChatDialogue(Handler handler, int x, int y, boolean choice) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.choiceMenu = choice;
		
		width = 432;
		height = 112;
		
		chatOptions = new ArrayList<ChatOptions>();
		
		// Zie DialogueBox functie voor inladen!!!
		if(choice) {
			for(int i = 0; i < 5; i++) {
				chatOptions.add(new ChatOptions(handler, x + 16, y + 11 + (20 * i), i, "Option: " + i));
			}
		}else{
			continueButton = new ContinueButton(x + (width / 2) - 50, y + 12 + (20 * 4));
			continueButtonBounds = new Rectangle(continueButton.getX(), continueButton.getY(), continueButton.getWidth(), continueButton.getHeight());
		}
	}
	
	public void tick() {
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		
		if(choiceMenu) {
			for(ChatOptions option : chatOptions) {
				
				option.tick();
				
				Rectangle optionSlot = new Rectangle(option.getX(), option.getY(), option.getWidth(), option.getHeight());
				
				if(optionSlot.contains(mouse)) {
					option.setHovering(true);
					if(handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
						hasBeenPressed = false;
						System.out.println("Chose option: " + option.getOptionID());
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
				if(handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
					hasBeenPressed = false;
					Player.hasInteracted = false;
					continueButton.setPressed(true);
					System.out.println("Continue pressed.");
				}
			}else {
				continueButton.setHovering(false);
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.chatwindow, x, y, width, height + 8, null);
		
		if(choiceMenu) {
			for(ChatOptions option : chatOptions) {
				option.render(g);
			}
		}else {
			continueButton.render(g);
		}
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

	public boolean isChoiceMenu() {
		return choiceMenu;
	}

	public void setChoiceMenu(boolean choiceMenu) {
		this.choiceMenu = choiceMenu;
	}

}
