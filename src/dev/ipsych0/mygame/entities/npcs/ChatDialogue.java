package dev.ipsych0.mygame.entities.npcs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;

public class ChatDialogue {
	
	private int x, y, width, height;
	private Handler handler;
	private int alpha = 127;
	private Color interfaceColour = new Color(100, 100, 100, alpha);
	private ArrayList<ChatOptions> chatOptions;
	private ContinueButton continueButton;
	public static boolean hasBeenPressed = false;
	private boolean choice = false;
	private Rectangle continueButtonBounds;
	
	public ChatDialogue(Handler handler, int x, int y, boolean choice) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.choice = choice;
		
		width = 432;
		height = 112;
		
		chatOptions = new ArrayList<ChatOptions>();
		
		if(choice) {
			for(int i = 0; i < 5; i++) {
				chatOptions.add(new ChatOptions(handler, x + 16, y + 11 + (20 * i), "Option: " + i));
			}
		}else{
			continueButton = new ContinueButton(x + (width / 2) - 50, y + 12 + (20 * 4));
			continueButtonBounds = new Rectangle(continueButton.getX(), continueButton.getY(), continueButton.getWidth(), continueButton.getHeight());
		}
	}
	
	public void tick() {
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		
		if(choice) {
			for(ChatOptions options : chatOptions) {
				options.tick();
				
				Rectangle optionSlot = new Rectangle(options.getX(), options.getY(), options.getWidth(), options.getHeight());
				
				if(optionSlot.contains(mouse)) {
					options.setHovering(true);
					if(handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
						hasBeenPressed = false;
						System.out.println(options.getMessage());
					}
				}else {
					options.setHovering(false);
				}
			}
		}else {
			continueButton.tick();
			if(continueButtonBounds.contains(mouse)) {
				continueButton.setHovering(true);
				if(handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
					hasBeenPressed = false;
					System.out.println("Continue pressed.");
				}
			}else {
				continueButton.setHovering(false);
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.chatwindow, x, y, width, height + 8, null);
		
		if(choice) {
			for(ChatOptions options : chatOptions) {
				options.render(g);
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

}
