package dev.ipsych0.mygame.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.ui.UIImageButton;
import dev.ipsych0.mygame.ui.UIManager;
import dev.ipsych0.mygame.ui.UIObject;
import dev.ipsych0.mygame.utils.SaveManager;
import dev.ipsych0.mygame.utils.Text;
import dev.ipsych0.mygame.worlds.Zone;

public class MenuState extends State {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIManager uiManager;
	private boolean loaded = false;
	private Rectangle newGameButton, continueButton, settingsButton;
	private boolean displayError = false;
	private int errorDisplayTimer = 0;
	private Rectangle errorPopup;

	public MenuState(Handler handler){
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		/*
		 * New Game Button
		 */
		uiManager.addObject(new UIImageButton(367, 376, 226, 96, Assets.genericButton));
		newGameButton = new Rectangle(367, 376, 226, 96);

		
		/*
		 * Continue Button
		 */
		uiManager.addObject(new UIImageButton(367, 480, 226, 96, Assets.genericButton));
		continueButton = new Rectangle(367, 480, 226, 96);
		
		/*
		 * Settings Button
		 */
		uiManager.addObject(new UIImageButton(367, 584, 226, 96, Assets.genericButton));
		settingsButton = new Rectangle(367, 584, 226, 96);
		
		errorPopup = new Rectangle(328, 224, 306, 58);
		
		
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
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					handler.getMouseManager().setUIManager(null);
					State.setState(handler.getGame().gameState);
					handler.playMusic(Zone.Island, 0, 0);
					hasBeenPressed = false;
				}
			}
			
			if(continueButton.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					Path path = Paths.get("res/savegames/savegame.sav");
					if (Files.notExists(path)) {
						System.out.println(path.toString() + " does not exist.");
						hasBeenPressed = false;
						displayError = true;
						errorDisplayTimer = 0;
						return;
					}else {
						handler.getMouseManager().setUIManager(null);
						State.setState(handler.getGame().gameState);
						SaveManager.loadHandler(handler);
						Zone zoneSong = Zone.Island;
						for(Zone z : Zone.values()) {
							if(handler.getWorld() == handler.getWorldHandler().getWorldsMap().get(z)) {
								zoneSong = z;
							}
						}
						handler.playMusic(zoneSong, 0, 0);
						hasBeenPressed = false;
					}
				}
			}
			
			if(settingsButton.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					// Stop loading this UIManager and go to the settings screen
					loaded = false;
					State.setState(handler.getGame().settingState);
					handler.getMouseManager().setUIManager(null);
					hasBeenPressed = false;
					displayError = false;
					errorDisplayTimer = 0;
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
			
			if(displayError) {
				errorDisplayTimer++;
				if(errorDisplayTimer <= 120) {
					g.drawImage(Assets.chatwindow, errorPopup.x, errorPopup.y, errorPopup.width, errorPopup.height, null);
					Text.drawString(g, "You do not have a save file yet!", errorPopup.x + errorPopup.width / 2, errorPopup.y + errorPopup.height / 2, true, Color.YELLOW, Assets.font20);
				}
				else {
					errorDisplayTimer = 0;
					displayError = false;
				}
			}
			
			// Render the text in the main menu
			Text.drawString(g, "Welcome to Myrinnia", 480, 180, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "New Game", 480, 424, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Continue", 480, 528, true, Color.YELLOW, Assets.font32);
			Text.drawString(g, "Settings", 480, 632, true, Color.YELLOW, Assets.font32);
		}
	}
}
