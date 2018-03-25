package dev.ipsych0.mygame.states;

import java.awt.Color;
import java.awt.Graphics;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.ui.ClickListener;
import dev.ipsych0.mygame.ui.UIImageButton;
import dev.ipsych0.mygame.ui.UIManager;
import dev.ipsych0.mygame.ui.UIObject;
import dev.ipsych0.mygame.utils.SaveManager;
import dev.ipsych0.mygame.utils.Text;

public class MenuState extends State {
	
	private UIManager uiManager;
	private boolean loaded = false;
	public static boolean loadButtonPressed = false;

	public MenuState(Handler handler){
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		/*
		 * New Game Button
		 */
		uiManager.addObject(new UIImageButton(367, 376, 226, 96, Assets.mainMenuButton, new ClickListener(){ //367, 216, 226, 96

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
				handler.playMusic("res/music/myrinnia.wav");
			}}));
		
		/*
		 * Continue Button
		 */
		uiManager.addObject(new UIImageButton(367, 480, 226, 96, Assets.mainMenuButton, new ClickListener(){ //367, 312, 226, 96

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				loadButtonPressed = true;
//				SaveManager.loadGame(handler);
				SaveManager.loadEntities(handler);
				SaveManager.loadInventory(handler);
				SaveManager.loadEquipment(handler);
				SaveManager.loadQuests(handler);
				State.setState(handler.getGame().gameState);
				handler.playMusic("res/music/myrinnia.wav");
				loadButtonPressed = false;

			}}));
		
		/*
		 * Settings Button
		 */
		uiManager.addObject(new UIImageButton(367, 584, 226, 96, Assets.mainMenuButton, new ClickListener(){ //367, 408, 226, 96

			@Override
			public void onClick() {
				// Stop loading this UIManager and go to the settings screen
				loaded = false;
				State.setState(handler.getGame().settingState);
				handler.getMouseManager().setUIManager(null);
				
			}}));
		
	}

	@Override
	public void tick() {
		if(State.getState() == handler.getGame().menuState){
			// If our UIManager was disabled, enable it if we get back to this Menu State
			if(!loaded) {
				handler.getMouseManager().setUIManager(uiManager);
				loaded = true;
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
