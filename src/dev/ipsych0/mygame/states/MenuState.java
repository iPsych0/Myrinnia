package dev.ipsych0.mygame.states;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.ui.ClickListener;
import dev.ipsych0.mygame.ui.UIImageButton;
import dev.ipsych0.mygame.ui.UIManager;

public class MenuState extends State {
	
	private UIManager uiManager;

	public MenuState(Handler handler){
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new UIImageButton(112, 112, 226, 96, Assets.btn_start, new ClickListener(){

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
			}}));
	}

	@Override
	public void tick() {
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 480, 320);
		uiManager.render(g);
	}
}
