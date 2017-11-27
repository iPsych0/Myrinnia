package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;

public class ChatDialogue {
	
	private int x, y, width, height;
	private Handler handler;
	private ArrayList<ChatOptions> chatOptions;
	private ContinueButton continueButton;
	public static boolean hasBeenPressed = false;
	private String[] menuOptions;
	private Rectangle continueButtonBounds;
	private int optionID;
	private ChatOptions chosenOption;
	
	public ChatDialogue(Handler handler, int x, int y, String[] menuOptions) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.menuOptions = menuOptions;
		
		width = 432;
		height = 112;
		
		chatOptions = new ArrayList<ChatOptions>();
		
		// Zie DialogueBox functie voor inladen!!!
		if(menuOptions.length > 1) {
			for(int i = 0; i < menuOptions.length; i++) {
				chatOptions.add(new ChatOptions(handler, x + 16, y + 11 + (20 * i), i, menuOptions[i]));
			}
		}else{
			continueButton = new ContinueButton(x + (width / 2) - 50, y + 12 + (20 * 4));
			continueButtonBounds = new Rectangle(continueButton.getX(), continueButton.getY(), continueButton.getWidth(), continueButton.getHeight());
		}
	}
	
	public void tick() {
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		
		if(menuOptions.length > 1) {
			for(ChatOptions option : chatOptions) {
				
				option.tick();
				
				Rectangle optionSlot = new Rectangle(option.getX(), option.getY(), option.getWidth(), option.getHeight());
				
				if(optionSlot.contains(mouse)) {
					option.setHovering(true);
					if(handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
						hasBeenPressed = false;
						System.out.println("Chose option: '" + option.getMessage() + "'");
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
		
		if(menuOptions.length > 1) {
			for(ChatOptions option : chatOptions) {
				option.render(g);
			}
		}else if(menuOptions.length == 1){
			for(int i = 0; i < wrapText(menuOptions[0], 60).size(); i++) {
				Text.drawString(g, wrapText(menuOptions[0], 60).get(i), x + (width / 2), y + 16 + (i * 12), true, Color.YELLOW, Assets.font14);
			}
			continueButton.render(g);
		}
	}
	
	public static List<String> wrapText(String string, int maxChar) {

	    List<String> subLines = new ArrayList<String>();

	    int length = string.length();
	    int start = 0;
	    int end = maxChar;
	    if (length > maxChar) {

	        int noOfLines = (length / maxChar) + 1;

	        int endOfStr[] = new int[noOfLines];

	        for (int f = 0; f < noOfLines - 1; f++) {

	            int end1 = maxChar;

	            endOfStr[f] = end;

	            if (string.charAt(end - 1) != ' ') {

	                if (string.charAt(end - 2) == ' ') {

	                    subLines.add(string.substring(start, end - 1));
	                    start = end - 1;
	                    end = end - 1 + end1;

	                } else if (string.charAt(end - 2) != ' '
	                        && string.charAt(end) == ' ') {

	                    subLines.add(string.substring(start, end));
	                    start = end;
	                    end = end + end1;

	                } else if (string.charAt(end - 2) != ' ') {

	                    subLines.add(string.substring(start, end) + "-");
	                    start = end;
	                    end = end + end1;

	                } else if (string.charAt(end + 2) == ' ') {
	                    System.out.println("m here ............");
	                    int lastSpaceIndex = string.substring(start, end)
	                            .lastIndexOf("");
	                    subLines.add(string.substring(start, lastSpaceIndex));

	                    start = lastSpaceIndex;
	                    end = lastSpaceIndex + end1;
	                }

	            } else {

	                subLines.add(string.substring(start, end));
	                start = end;
	                end = end + end1;
	            }

	        }

	        subLines.add(string.substring(endOfStr[noOfLines - 2], length));

	    }

	    return subLines;
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
