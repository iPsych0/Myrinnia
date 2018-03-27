package dev.ipsych0.mygame.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.ImageLoader;
import dev.ipsych0.mygame.ui.UIImageButton;
import dev.ipsych0.mygame.ui.UIManager;
import dev.ipsych0.mygame.ui.UIObject;
import dev.ipsych0.mygame.utils.SaveManager;
import dev.ipsych0.mygame.utils.Text;

public class MenuState extends State {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIManager uiManager;
	private boolean loaded = false;
	public static boolean loadButtonPressed = false;
	private Rectangle newGameButton, continueButton, settingsButton;

	public MenuState(Handler handler){
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		/*
		 * New Game Button
		 */
		uiManager.addObject(new UIImageButton(367, 376, 226, 96, Assets.mainMenuButton));
		newGameButton = new Rectangle(367, 376, 226, 96);

		
		/*
		 * Continue Button
		 */
		uiManager.addObject(new UIImageButton(367, 480, 226, 96, Assets.mainMenuButton));
		continueButton = new Rectangle(367, 480, 226, 96);
		
		/*
		 * Settings Button
		 */
		uiManager.addObject(new UIImageButton(367, 584, 226, 96, Assets.mainMenuButton));
		settingsButton = new Rectangle(367, 584, 226, 96);
		
		
	}

	@Override
	public void tick() {
		if(State.getState() == handler.getGame().menuState){
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
			
			if(newGameButton.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged()) {
					handler.getMouseManager().setUIManager(null);
					State.setState(handler.getGame().gameState);
					handler.playMusic("res/music/myrinnia.wav");
				}
			}
			
			if(continueButton.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged()) {
					handler.getMouseManager().setUIManager(null);
					loadButtonPressed = true;
//					SaveManager.loadGame(handler);
//					SaveManager.loadWorlds(handler);
//					SaveManager.loadInventory(handler);
//					SaveManager.loadEquipment(handler);
//					SaveManager.loadQuests(handler);
					SaveManager.loadHandler(handler);
					State.setState(handler.getGame().gameState);
					handler.playMusic("res/music/myrinnia.wav");
					loadButtonPressed = false;
				}
			}
			
			if(settingsButton.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged()) {
					// Stop loading this UIManager and go to the settings screen
					loaded = false;
					State.setState(handler.getGame().settingState);
					handler.getMouseManager().setUIManager(null);
				}
			}
			
			uiManager.tick();
		}
	}

	@Override
	public void render(Graphics g) {
		if(State.getState() == handler.getGame().menuState){
//		g.setColor(Color.BLACK);
//		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
			g.drawImage(Assets.craftWindow, -40, -40, 1040, 800, null);
			uiManager.render(g);
			
			// Render the text in the main menu
			Text.drawString(g, "Welcome to Myrinnia", 480, 180, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "New Game", 480, 424, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Continue", 480, 528, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Settings", 480, 632, true, Color.YELLOW, Assets.font32);
		}
	}
}
