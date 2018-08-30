package dev.ipsych0.mygame.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.audio.AudioManager;
import dev.ipsych0.mygame.audio.Source;
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
	private Rectangle soundPopup;
	private boolean displaySoundPressed = false;
	private int displaySoundTimer = 0;
	public static State previousState;

	public SettingState() {
		super();
		this.uiManager = new UIManager();
		
		/*
		 * Controls Button
		 */
		uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 376, 226, 96, Assets.genericButton));
		controlsButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 376, 226, 96);
		
		/*
		 * Mute Sound
		 */
		uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 480, 226, 96, Assets.genericButton));
		muteSoundButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 480, 226, 96);
		
		/*
		 * The return button to the main menu
		 */
		uiManager.addObject(new UIImageButton(Handler.get().getWidth() / 2 - 113, 584, 226, 96, Assets.genericButton));
		returnButton = new Rectangle(Handler.get().getWidth() / 2 - 113, 584, 226, 96);
		
		soundPopup = new Rectangle(Handler.get().getWidth() / 2 - 153, 224, 306, 58);
	}

	@Override
	public void tick() {
			// If our UIManager was disabled, enable it if we get back to this Settings State
			if(!loaded) {
				Handler.get().getMouseManager().setUIManager(uiManager);
				loaded = true;
			}
			
			Rectangle mouse = new Rectangle(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY(), 1, 1);
			
			for(UIObject o : uiManager.getObjects()) {
				if(o.getBounds().contains(mouse)) {
					o.setHovering(true);
				}else {
					o.setHovering(false);
				}
			}
			
			if(controlsButton.contains(mouse)) {
				if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					Handler.get().getMouseManager().setUIManager(null);
					State.setState(new UITransitionState(Handler.get().getGame().controlsState));
					loaded = false;
					hasBeenPressed = false;
				}
			}
			
			if(muteSoundButton.contains(mouse)) {
				if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					displaySoundTimer = 0;
					displaySoundPressed = true;
					if(!Handler.get().isSoundMuted()) {
						System.out.println("Muted sound!");
						Handler.get().setSoundMuted(true);
						for(Source s : AudioManager.soundfxFiles)
							s.delete();
						for(Source s : AudioManager.musicFiles)
							s.delete();
					}else {
						System.out.println("Unmuted sound!");
						Handler.get().setSoundMuted(false);
						Handler.get().playMusic(Handler.get().getPlayer().getZone());
					}
					hasBeenPressed = false;
				}
			}
			
			if(returnButton.contains(mouse)) {
				if(Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					Handler.get().getMouseManager().setUIManager(null);
					State.setState(new UITransitionState(previousState));
					loaded = false;
					hasBeenPressed = false;
					displaySoundPressed = false;
					displaySoundTimer = 0;
				}
			}
			
			this.uiManager.tick();
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());
//			g.drawImage(Assets.craftWindow, -40, -40, 1040, 800, null);
		this.uiManager.render(g);
		
		if(displaySoundPressed) {
			displaySoundTimer++;
			if(displaySoundTimer <= 120) {
				g.drawImage(Assets.chatwindow, soundPopup.x, soundPopup.y, soundPopup.width, soundPopup.height, null);
				if(!Handler.get().isSoundMuted())
					Text.drawString(g, "Sound unmuted!", soundPopup.x + soundPopup.width / 2, soundPopup.y + soundPopup.height / 2, true, Color.YELLOW, Assets.font20);
				else
					Text.drawString(g, "Sound muted!", soundPopup.x + soundPopup.width / 2, soundPopup.y + soundPopup.height / 2, true, Color.YELLOW, Assets.font20);
			}
			else {
				displaySoundTimer = 0;
				displaySoundPressed = false;
			}
		}
		
		if(previousState == Handler.get().getGame().menuState)
			Text.drawString(g, "Welcome to Myrinnia", Handler.get().getWidth() / 2, 180, true, Color.YELLOW, Assets.font32);
		else
			Text.drawString(g, "Game Paused!", Handler.get().getWidth() / 2, 180, true, Color.YELLOW, Assets.font32);
		
		Text.drawString(g, "Controls", Handler.get().getWidth() / 2, 424, true, Color.YELLOW, Assets.font32);
		Text.drawString(g, "Mute Sounds", Handler.get().getWidth() / 2, 528, true, Color.YELLOW, Assets.font32);
		Text.drawString(g, "Return", Handler.get().getWidth() / 2, 632, true, Color.YELLOW, Assets.font32);
	}
		
	

}
