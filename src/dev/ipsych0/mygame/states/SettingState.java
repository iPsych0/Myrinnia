package dev.ipsych0.mygame.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.ui.UIImageButton;
import dev.ipsych0.mygame.ui.UIManager;
import dev.ipsych0.mygame.ui.UIObject;
import dev.ipsych0.mygame.utils.Text;

public class SettingState extends State{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIManager uiManager;
	private boolean loaded = false;
	private Rectangle controlsButton, muteSoundButton, returnButton;

	public SettingState(Handler handler) {
		super(handler);
		this.uiManager = new UIManager(handler);
		
		/*
		 * Controls Button
		 */
		uiManager.addObject(new UIImageButton(367, 376, 226, 96, Assets.mainMenuButton));
		controlsButton = new Rectangle(367, 376, 226, 96);
		
		/*
		 * Mute Sound
		 */
		uiManager.addObject(new UIImageButton(367, 480, 226, 96, Assets.mainMenuButton));
		muteSoundButton = new Rectangle(367, 480, 226, 96);
		
		/*
		 * The return button to the main menu
		 */
		uiManager.addObject(new UIImageButton(367, 584, 226, 96, Assets.mainMenuButton));
		returnButton = new Rectangle(367, 584, 226, 96);
		
	}

	@Override
	public void tick() {
		if(State.getState() == handler.getGame().settingState){
			// If our UIManager was disabled, enable it if we get back to this Settings State
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
			
			if(controlsButton.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					System.out.println("Clicked Controls!");
					hasBeenPressed = false;
				}
			}
			
			if(muteSoundButton.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					System.out.println("Clicked Mute Sound!");
					if(!handler.isSoundMuted()) {
						handler.setSoundMuted(true);
					}else {
						handler.setSoundMuted(false);
					}
					hasBeenPressed = false;
				}
			}
			
			if(returnButton.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					handler.getMouseManager().setUIManager(null);
					State.setState(handler.getGame().menuState);
					loaded = false;
					hasBeenPressed = false;
				}
			}
			
			this.uiManager.tick();
		}
	}

	@Override
	public void render(Graphics g) {
		if(State.getState() == handler.getGame().settingState){
//			g.setColor(Color.BLACK);
//			g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
			g.drawImage(Assets.craftWindow, -40, -40, 1040, 800, null);
			this.uiManager.render(g);
			
			Text.drawString(g, "Welcome to Myrinnia", 480, 180, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Controls", 480, 424, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Mute Sound", 480, 528, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Return", 480, 632, true, Color.YELLOW, Assets.font32);
		}
		
	}

}
