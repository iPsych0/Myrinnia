package dev.ipsych0.mygame.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.audio.AudioManager;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.ui.UIImageButton;
import dev.ipsych0.mygame.ui.UIManager;
import dev.ipsych0.mygame.ui.UIObject;
import dev.ipsych0.mygame.utils.Text;

public class PauseState extends State {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIManager uiManager;
	private boolean loaded = false;
	private Rectangle resumeButton, settingsButton, quitButton;

	public PauseState(Handler handler){
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		/*
		 * Resume Game Button
		 */
		uiManager.addObject(new UIImageButton(367, 376, 226, 96, Assets.genericButton));
		resumeButton = new Rectangle(367, 376, 226, 96);

		/*
		 * Settings Button
		 */
		uiManager.addObject(new UIImageButton(367, 480, 226, 96, Assets.genericButton));
		settingsButton = new Rectangle(367, 480, 226, 96);
		
		/*
		 * Quit Game Button
		 */
		uiManager.addObject(new UIImageButton(367, 584, 226, 96, Assets.genericButton));
		quitButton = new Rectangle(367, 584, 226, 96);
		
				
		
	}

	@Override
	public void tick() {
		if(State.getState() == this){
			// If our UIManager was disabled, enable it if we get back to this Menu State
			if(!loaded) {
				handler.getMouseManager().setUIManager(uiManager);
				loaded = true;
			}
			
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			for(UIObject o : uiManager.getObjects()) {
				if(o.getBounds().contains(mouse)) {
					o.setHovering(true);
				}else {
					o.setHovering(false);
				}
			}
			
			if(resumeButton.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					handler.getMouseManager().setUIManager(null);
					State.setState(handler.getGame().gameState);
					hasBeenPressed = false;
				}
			}
			
			if(settingsButton.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					// Stop loading this UIManager and go to the settings screen
					loaded = false;
					SettingState.previousState = this;
					State.setState(handler.getGame().settingState);
					handler.getMouseManager().setUIManager(null);
					hasBeenPressed = false;
				}
			}
			
			if(quitButton.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					AudioManager.cleanUp();
					System.exit(0);
					hasBeenPressed = false;
				}
			}
			
			uiManager.tick();
		}
	}

	@Override
	public void render(Graphics g) {
		if(State.getState() == this){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
			uiManager.render(g);
			
			// Render the text in the main menu
			Text.drawString(g, "Game Paused!", 480, 180, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Resume Game", 480, 424, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Settings", 480, 528, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Quit Game", 480, 632, true, Color.YELLOW, Assets.font32);
		}
	}
}
