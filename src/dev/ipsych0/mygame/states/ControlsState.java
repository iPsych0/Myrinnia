package dev.ipsych0.mygame.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.ui.UIImageButton;
import dev.ipsych0.mygame.ui.UIManager;
import dev.ipsych0.mygame.ui.UIObject;
import dev.ipsych0.mygame.utils.Text;

public class ControlsState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIManager uiManager;
	private boolean loaded = false;
	private Rectangle returnButton;

	public ControlsState(Handler handler) {
		super(handler);
		this.uiManager = new UIManager(handler);
		
		/*
		 * The return button to the main menu
		 */
		uiManager.addObject(new UIImageButton(367, 584, 226, 96, Assets.mainMenuButton));
		returnButton = new Rectangle(367, 584, 226, 96);
		
	}

	@Override
	public void tick() {
		if(State.getState() == handler.getGame().controlsState){
			// If our UIManager was disabled, enable it if we get back to this Settings State
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
			
			if(returnButton.contains(mouse)) {
				if(handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					handler.getMouseManager().setUIManager(null);
					State.setState(handler.getGame().settingState);
					loaded = false;
					hasBeenPressed = false;
				}
			}
			
			this.uiManager.tick();
		}
	}

	@Override
	public void render(Graphics g) {
		if(State.getState() == handler.getGame().controlsState){
//			g.setColor(Color.BLACK);
//			g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
			g.drawImage(Assets.craftWindow, -40, -40, 1040, 800, null);
			g.drawImage(Assets.controlsScreen, 250, 232, 460, 313, null);
			this.uiManager.render(g);
			
			Text.drawString(g, "Welcome to Myrinnia", 480, 180, true, Color.YELLOW, Assets.font32);
			
			Text.drawString(g, "Movement keys:", 373, 248, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "W", 373, 279, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "A", 325, 329, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "S", 373, 329, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "D", 421, 329, true, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "Interface toggles:", 374, 361, true, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "I", 300, 396, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Inventory", 324, 400, false, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "C", 300, 445, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Chatwindow", 324, 449, false, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "Q", 445, 396, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Quests", 469, 400, false, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "M", 445, 445, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Map", 469, 449, false, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "Spacebar", 374, 491, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Interact with NPCs/Objects", 374, 516, true, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "Left click:", 628, 396, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "- Attack", 624, 416, true, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "Right click:", 628, 448, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "- Pick up item", 624, 468, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "- Equip item", 624, 488, true, Color.YELLOW, Assets.font14);
			
			
			Text.drawString(g, "Return", 480, 632, true, Color.YELLOW, Assets.font32);
		}
		
	}

}
