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
	private Rectangle overlay;
	private Rectangle w, a, s, d, i, c, q, m, k, l, space;

	public ControlsState(Handler handler) {
		super(handler);
		this.uiManager = new UIManager(handler);
		
		overlay = new Rectangle(220, 232, 520, 313);
		
		// Interface buttons
		uiManager.addObject(new UIImageButton(overlay.x + 24, overlay.y + 24, 32, 32, Assets.genericButton));
		uiManager.addObject(new UIImageButton(overlay.x + 24, overlay.y + 72, 32, 32, Assets.genericButton));
		uiManager.addObject(new UIImageButton(overlay.x + 24, overlay.y + 120, 32, 32, Assets.genericButton));
		uiManager.addObject(new UIImageButton(overlay.x + 24, overlay.y + 168, 32, 32, Assets.genericButton));
		uiManager.addObject(new UIImageButton(overlay.x + 24, overlay.y + 216, 32, 32, Assets.genericButton));
		uiManager.addObject(new UIImageButton(overlay.x + 24, overlay.y + 264, 32, 32, Assets.genericButton));
		
		// Interface rectangles
		i = new Rectangle(overlay.x + 24, overlay.y + 24, 32, 32);
		q = new Rectangle(overlay.x + 24, overlay.y + 72, 32, 32);
		l = new Rectangle(overlay.x + 24, overlay.y + 120, 32, 32);
		m = new Rectangle(overlay.x + 24, overlay.y + 168, 32, 32);
		k = new Rectangle(overlay.x + 24, overlay.y + 216, 32, 32);
		c = new Rectangle(overlay.x + 24, overlay.y + 264, 32, 32);
		
		// WASD buttons
		uiManager.addObject(new UIImageButton(overlay.x + 224, overlay.y + 48, 32, 32, Assets.genericButton));
		uiManager.addObject(new UIImageButton(overlay.x + 224, overlay.y + 96, 32, 32, Assets.genericButton));
		uiManager.addObject(new UIImageButton(overlay.x + 176, overlay.y + 96, 32, 32, Assets.genericButton));
		uiManager.addObject(new UIImageButton(overlay.x + 272, overlay.y + 96, 32, 32, Assets.genericButton));
		
		// WASD rectangles
		w = new Rectangle(overlay.x + 224, overlay.y + 48, 32, 32);
		a = new Rectangle(overlay.x + 176, overlay.y + 96, 32, 32);
		s = new Rectangle(overlay.x + 224, overlay.y + 96, 32, 32);
		d = new Rectangle(overlay.x + 272, overlay.y + 96, 32, 32);
		
		// Spacebar
		uiManager.addObject(new UIImageButton(overlay.x + 192, overlay.y + 192, 96, 32, Assets.genericButton));
		space = new Rectangle(overlay.x + 192, overlay.y + 192, 96, 32);
		
		/*
		 * The return button to the main menu
		 */
		uiManager.addObject(new UIImageButton(367, 584, 226, 96, Assets.genericButton));
		returnButton = new Rectangle(367, 584, 226, 96);
		
	}

	@Override
	public void tick() {
		if(State.getState() == this){
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
		if(State.getState() == this){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
//			g.drawImage(Assets.craftWindow, -40, -40, 1040, 800, null);
			g.drawImage(Assets.controlsScreen, overlay.x, overlay.y, overlay.width, overlay.height, null);
			this.uiManager.render(g);
			
//			Text.drawString(g, "Welcome to Myrinnia", 480, 180, true, Color.YELLOW, Assets.font32);
			
			Text.drawString(g, "Movement keys:", i.x + 216, i.y, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "W", w.x + 16, w.y + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "A", a.x + 16, a.y + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "S", s.x + 16, s.y + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "D", d.x + 16, d.y + 16, true, Color.YELLOW, Assets.font14);

			Text.drawString(g, "I", i.x + 16, i.y + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Inventory", i.x + 48, i.y + 20, false, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "C", c.x + 16, c.y + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Chatwindow", c.x + 48, c.y + 20, false, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "Q", q.x + 16, q.y + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Quests", q.x + 48, q.y + 20, false, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "M", m.x + 16, m.y + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Map", m.x + 48, m.y + 20, false, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "K", k.x + 16, k.y + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Character", k.x + 48, k.y + 20, false, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "L", l.x + 16, l.y + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Skills", l.x + 48, l.y + 20, false, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "Spacebar", space.x + 48, space.y + 16, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Interact with NPCs/Objects:", space.x + 48, space.y - 24, true, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "Left click:", 648, 396, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "- Attack", 644, 416, true, Color.YELLOW, Assets.font14);
			
			Text.drawString(g, "Right click:", 648, 448, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "- Pick up item", 644, 468, true, Color.YELLOW, Assets.font14);
			Text.drawString(g, "- Equip item", 644, 488, true, Color.YELLOW, Assets.font14);
			
			
			Text.drawString(g, "Return", 480, 632, true, Color.YELLOW, Assets.font32);
		}
		
	}

}
