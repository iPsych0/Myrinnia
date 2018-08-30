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

	public PauseState(){
		super();
		uiManager = new UIManager();
		Handler.get().getMouseManager().setUIManager(uiManager);
		
		/*
		 * Resume Game Button
		 */
		uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 376, 226, 96, Assets.genericButton));
		resumeButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 376, 226, 96);

		/*
		 * Settings Button
		 */
		uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 480, 226, 96, Assets.genericButton));
		settingsButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 480, 226, 96);
		
		/*
		 * Quit Game Button
		 */
		uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 584, 226, 96, Assets.genericButton));
		quitButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 584, 226, 96);
		
				
		
	}

	@Override
	public void tick() {
			// If our UIManager was disabled, enable it if we get back to this Menu State
			if(!loaded) {
				Handler.get().getMouseManager().setUIManager(uiManager);
				loaded = true;
			}
			
			Rectangle mouse = new Rectangle(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY(), 1, 1);
			
			for(UIObject o : uiManager.getObjects()) {
				if(o.getBounds().contains(mouse)) {
					o.setHovering(true);
				}else {
					o.setHovering(false);
				}
			}
			
			if(resumeButton.contains(mouse)) {
				if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					Handler.get().getMouseManager().setUIManager(null);
					State.setState(new UITransitionState(Handler.get().getGame().gameState));
					hasBeenPressed = false;
				}
			}
			
			if(settingsButton.contains(mouse)) {
				if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					// Stop loading this UIManager and go to the settings screen
					loaded = false;
					SettingState.previousState = this;
					State.setState(new UITransitionState(Handler.get().getGame().settingState));
					Handler.get().getMouseManager().setUIManager(null);
					hasBeenPressed = false;
				}
			}
			
			if(quitButton.contains(mouse)) {
				if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					AudioManager.cleanUp();
					System.exit(0);
					hasBeenPressed = false;
				}
			}
			
			uiManager.tick();
		
	}

	@Override
	public void render(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());
			uiManager.render(g);
			
			// Render the text in the main menu
			Text.drawString(g, "Game Paused!", Handler.get().getWidth() / 2, 180, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Resume Game", Handler.get().getWidth() / 2, 424, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Settings", Handler.get().getWidth() / 2, 528, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Quit Game", Handler.get().getWidth() / 2, 632, true, Color.YELLOW, Assets.font32);
		}
	
}
