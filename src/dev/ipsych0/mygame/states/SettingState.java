package dev.ipsych0.mygame.states;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.ui.UIImageButton;
import dev.ipsych0.mygame.ui.UIManager;
import dev.ipsych0.mygame.utils.Text;

public class SettingState extends State{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIManager uiManager;
	private boolean loaded = false;

	public SettingState(Handler handler) {
		super(handler);
		this.uiManager = new UIManager(handler);
		
		
		/*
		 * The return button to the main menu
		 */
		uiManager.addObject(new UIImageButton(367, 584, 226, 96, Assets.mainMenuButton));
				// On Click return to the Main Menu, set this UI Manager to null and stop loading this screen
//				State.setState(handler.getGame().settingState);
//					loaded = false;
//					State.setState(handler.getGame().menuState);
//					handler.getMouseManager().setUIManager(null);
		
	}

	@Override
	public void tick() {
		if(State.getState() == handler.getGame().settingState){
			// If our UIManager was disabled, enable it if we get back to this Settings State
			if(!loaded) {
				handler.getMouseManager().setUIManager(uiManager);
				loaded = true;
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
			Text.drawString(g, "Return", 480, 632, true, Color.YELLOW, Assets.font32);
		}
		
	}

}
