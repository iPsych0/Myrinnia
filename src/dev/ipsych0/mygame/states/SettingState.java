package dev.ipsych0.mygame.states;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.ui.ClickListener;
import dev.ipsych0.mygame.ui.UIImageButton;
import dev.ipsych0.mygame.ui.UIManager;

public class SettingState extends State{
	
	private UIManager uiManager;
	private boolean loaded = false;

	public SettingState(Handler handler) {
		super(handler);
		this.uiManager = new UIManager(handler);
		
		
		/*
		 * The return button to the main menu
		 */
		uiManager.addObject(new UIImageButton(367, 592, 226, 96, Assets.mainMenuButton, new ClickListener(){ //367, 408, 226, 96

			@Override
			public void onClick() {
				// On Click return to the Main Menu, set this UI Manager to null and stop loading this screen
				State.setState(handler.getGame().settingState);
					loaded = false;
					State.setState(handler.getGame().menuState);
					handler.getMouseManager().setUIManager(null);
				
			}}));
		
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
				g.drawImage(Assets.mainScreenBackground, 0, 0, 960, 720, null);
				this.uiManager.render(g);
			}
		
	}

}
