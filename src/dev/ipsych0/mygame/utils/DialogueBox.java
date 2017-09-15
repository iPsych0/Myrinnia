package dev.ipsych0.mygame.utils;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;

public class DialogueBox {
	
	public int x, y, width, height;
	private ArrayList<DialogueButton> buttons;
	public static boolean isOpen = false;
	private Handler handler;
	public String[] answers;
	public String param = "";
	
	public DialogueBox(Handler handler, int x, int y, int width, int height, String[] answers) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		this.answers = answers;
		
		buttons = new ArrayList<DialogueButton>();
		
		for(int i = 0; i < answers.length; i++) {
			buttons.add(new DialogueButton(handler, x + (width / answers.length) - 32 + (i * 64), y + height - 48, 32, 32, answers[i]));
		}
		
	}
	
	public void tick() {
		if(isOpen) {
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			for(DialogueButton db : buttons) {
				db.tick();
				
				if(db.getButtonBounds().contains(mouse) && handler.getMouseManager().isLeftPressed()) {
					for(int i = 0; i < buttons.size(); i++) {
						if(db.getText() == answers[i])
							db.onClick(answers[i], param);
					}
				}
				
			}
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			
			g.drawImage(Assets.shopWindow, x, y, width, height, null);
			
			for(DialogueButton db : buttons) {
				db.render(g);
			}
		}
	}

	public ArrayList<DialogueButton> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<DialogueButton> buttons) {
		this.buttons = buttons;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
