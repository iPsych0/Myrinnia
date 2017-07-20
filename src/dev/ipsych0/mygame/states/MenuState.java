package dev.ipsych0.mygame.states;

import java.awt.Graphics;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.ui.ClickListener;
import dev.ipsych0.mygame.ui.UIImageButton;
import dev.ipsych0.mygame.ui.UIManager;
import dev.ipsych0.mygame.utils.SaveManager;

public class MenuState extends State {
	
	private UIManager uiManager;

	public MenuState(Handler handler){
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		/*
		 * New Game Button
		 */
		uiManager.addObject(new UIImageButton(367, 400, 226, 96, Assets.button_new_game, new ClickListener(){ //367, 216, 226, 96

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
				handler.playMusic("res/music/myrinnia.wav");
			}}));
		
		/*
		 * Continue Button
		 */
		uiManager.addObject(new UIImageButton(367, 496, 226, 96, Assets.button_continue, new ClickListener(){ //367, 312, 226, 96

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
				SaveManager.loadGame(handler);
				SaveManager.loadInventory(handler);
				SaveManager.loadEquipment(handler);

			}}));
		
		/*
		 * Settings Button
		 */
		uiManager.addObject(new UIImageButton(367, 592, 226, 96, Assets.button_settings, new ClickListener(){ //367, 408, 226, 96

			@Override
			public void onClick() {
				//handler.getMouseManager().setUIManager(null);
			}}));
		
	}

	@Override
	public void tick() {
		if(State.getState() == handler.getGame().menuState){
			uiManager.tick();
		}
	}

	@Override
	public void render(Graphics g) {
		if(State.getState() == handler.getGame().menuState){
//		g.setColor(Color.BLACK);
//		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
			g.drawImage(Assets.mainScreenBackground, 0, 0, 960, 720, null);
			uiManager.render(g);
		}
	}
}
