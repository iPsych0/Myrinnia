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
		
		uiManager.addObject(new UIImageButton(367, 592, 226, 96, Assets.button_settings, new ClickListener(){ //367, 408, 226, 96

			@Override
			public void onClick() {
				State.setState(handler.getGame().settingState);
					loaded = false;
					State.setState(handler.getGame().menuState);
					handler.getMouseManager().setUIManager(null);
				
			}}));
		
	}

	@Override
	public void tick() {
		if(State.getState() == handler.getGame().settingState){
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
