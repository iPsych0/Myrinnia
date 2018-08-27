package dev.ipsych0.mygame.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.recap.RecapEvent;
import dev.ipsych0.mygame.utils.Text;

public class RecapState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Rectangle continueButton;
	private int index = 0;

	public RecapState(Handler handler) {
		super(handler);
		
		this.continueButton = new Rectangle(handler.getWidth() / 2 - 160, handler.getHeight() / 2, 320, 96);
	}

	@Override
	public void tick() {
		if(handler.getRecapManager().getEvents().size() == 0) {
			State.setState(new UITransitionState(handler, handler.getGame().gameState));
			handler.playMusic(handler.getPlayer().getZone());
			return;
		}else {
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			if(continueButton.contains(mouse) && handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
				if(index == handler.getRecapManager().getEvents().size() - 1) {
					State.setState(new UITransitionState(handler, handler.getGame().gameState));
					handler.playMusic(handler.getPlayer().getZone());
					hasBeenPressed = false;
					return;
				}else {
					State.setState(new UITransitionState(handler, this));
					index++;
					hasBeenPressed = false;
				}
				
			}
		}
		
		
	}

	@Override
	public void render(Graphics g) {
		if(handler.getRecapManager().getEvents().size() > 0) {
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			RecapEvent event = handler.getRecapManager().getEvents().get(index);
			g.drawImage(event.getImg(), 0, 0, event.getImg().getWidth(), event.getImg().getHeight(), null);
			g.setColor(new Color(27, 27, 27, 196));
			g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
			
			Text.drawString(g, "You last did... ["+(index+1)+"/"+handler.getRecapManager().getEvents().size() +"]", handler.getWidth() / 2, 96, true, Color.YELLOW, Assets.font32);
			
			Text.drawString(g, event.getDescription(), handler.getWidth() / 2, handler.getHeight() / 2 - 96, true, Color.YELLOW, Assets.font32);
			
			if(continueButton.contains(mouse)) {
				g.drawImage(Assets.genericButton[0], continueButton.x, continueButton.y, continueButton.width, continueButton.height, null);
			}else {
				g.drawImage(Assets.genericButton[1], continueButton.x, continueButton.y, continueButton.width, continueButton.height, null);
			}
			
			Text.drawString(g, "Continue", continueButton.x + continueButton.width / 2, continueButton.y + continueButton.height / 2, true, Color.YELLOW, Assets.font32);
		}
	}

}
