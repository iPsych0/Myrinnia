package dev.ipsych0.mygame.states;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
		
		uiManager.addObject(new UIImageButton(367, 216, 226, 96, Assets.button_new_game, new ClickListener(){

			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
				play("res/music/myrinnia.wav");
			}}));
		
		uiManager.addObject(new UIImageButton(367, 312, 226, 96, Assets.button_continue, new ClickListener(){

			@Override
			public void onClick() {
				//handler.getMouseManager().setUIManager(null);
			}}));
		
		uiManager.addObject(new UIImageButton(367, 408, 226, 96, Assets.button_settings, new ClickListener(){

			@Override
			public void onClick() {
				//handler.getMouseManager().setUIManager(null);
			}}));
	}

	@Override
	public void tick() {
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
//		g.setColor(Color.BLACK);
//		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		g.drawImage(Assets.mainScreenBackground, 0, 0, 960, 720, null);
		uiManager.render(g);
	}
	
	private void play(String pathToMusic) {
		try {
			File file = new File(pathToMusic);
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
