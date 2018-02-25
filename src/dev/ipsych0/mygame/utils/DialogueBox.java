package dev.ipsych0.mygame.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.sun.glass.events.KeyEvent;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.ui.TextBox;

public class DialogueBox {
	
	public int x, y, width, height;
	private ArrayList<DialogueButton> buttons;
	public static boolean isOpen = false;
	private Handler handler;
	public String[] answers;
	public String param = "";
	public DialogueButton pressedButton = null;
	private TextBox tb;
	private String message;
	private boolean numbersOnly;

	public DialogueBox(Handler handler, int x, int y, int width, int height, String[] answers, String message, boolean numbersOnly) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		this.answers = answers;
		this.message = message;
		this.numbersOnly = numbersOnly;
		
		buttons = new ArrayList<DialogueButton>();
		
		for(int i = 0; i < answers.length; i++) {
			buttons.add(new DialogueButton(x + (width / answers.length) - 32 - (32 / answers.length) + (i * 64), y + height - 48, 32, 32, answers[i]));
		}
		
		tb = new TextBox(handler, x + (width / 2) - (width / 2) + 17, y + height - 96, width - 40, 32, numbersOnly);
	}
	
	public void tick() {
		
		if(isOpen) {
			
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			for(DialogueButton db : buttons) {
				db.tick();
				tb.tick();
				
				if(db.getButtonBounds().contains(mouse) && handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged()) {
					for(int i = 0; i < buttons.size(); i++) {
						if(db.getText().equals(answers[i]) && pressedButton == null) {
							pressedButton = db;
							pressedButton.pressedButton(answers[i], param);
							isOpen = false;
						}
					}
				}
				
			}
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			
			g.drawImage(Assets.shopWindow, x, y, width, height, null);
			
			Text.drawString(g, message, x + (width / 2), y + 32, true, Color.YELLOW, Assets.font14);
			
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1,1);
			for(DialogueButton db : buttons) {
				if(db.getButtonBounds().contains(mouse))
					db.setHovering(true);
				else
					db.setHovering(false);
				db.render(g);
			}
			
			tb.render(g);
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

	public DialogueButton getPressedButton() {
		return pressedButton;
	}

	public void setPressedButton(DialogueButton pressedButton) {
		this.pressedButton = pressedButton;
	}

	public TextBox getTextBox() {
		return tb;
	}

	public void setTextBox(TextBox tb) {
		this.tb = tb;
	}

}
