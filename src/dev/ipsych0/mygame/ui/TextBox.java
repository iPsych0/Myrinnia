package dev.ipsych0.mygame.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;
import dev.ipsych0.mygame.input.KeyManager;

public class TextBox implements KeyListener {
	
	public int x, y, width, height;
	public String charactersTyped = "";
	public boolean numbersOnly = false;
	private Handler handler;
	private Rectangle bounds;
	private boolean focus = false;
	private int index = 0;
	private StringBuilder sb;
	private boolean loaded = false;
	
	public TextBox(Handler handler, int x, int y, int width, int height, boolean numbersOnly) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.numbersOnly = numbersOnly;
		
		bounds = new Rectangle(x,y,width,height);
		
		sb = new StringBuilder(charactersTyped);
	}
	
	public void tick() {
		if(!loaded) {
			handler.getGame().getDisplay().getFrame().addKeyListener(this);
			handler.getGame().getDisplay().getCanvas().addKeyListener(this);
			loaded = true;
		}
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		if(bounds.contains(mouse) && handler.getMouseManager().isLeftPressed()) {
			focus = true;
			KeyManager.typingFocus = true;
		}
		
		if(!bounds.contains(mouse) && handler.getMouseManager().isLeftPressed()) {
			focus = false;
			KeyManager.typingFocus = false;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		
		if(charactersTyped != null)
			Text.drawString(g, charactersTyped, x + (width / 2), y + 16, true, Color.YELLOW, Assets.font14);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(focus) {
			
			if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
				if(index == 0) {
					return;
				}else {
					sb.deleteCharAt(index - 1);
					index--;
					charactersTyped = sb.toString();
				}
			}
			
			if(index > 9) {
				e.consume();
				return;
			}
		
			if(numbersOnly) {
				if(!Character.isDigit(e.getKeyChar())) {
					return;
				}else {
					System.out.println(e.getKeyChar());
					sb.append(e.getKeyChar());
					index++;
					charactersTyped = sb.toString();
				}
			}else {
				sb.append(e.getKeyChar());
				index++;
				charactersTyped = sb.toString();
			}
		}else {
			e.consume();
		}
	}

}
