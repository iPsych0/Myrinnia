package dev.ipsych0.myrinnia.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

public class ScrollBar implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8698572954217286128L;
	public int x, y, width, height;
	private Rectangle scrollUp, scrollDown;
	private int scrollMinimum = 0, scrollMaximum;
	private int index = 0;
	private int itemsPerWindow;
	private int listSize;
	public static int clickTimer = 0;
	public static int scrollTimer = 0;
	
	public ScrollBar(int x, int y, int width, int height, int listSize, int itemsPerWindow) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.listSize = listSize;
		this.itemsPerWindow = itemsPerWindow;
		
		scrollUp = new Rectangle(x, y, 32, 32);
		scrollDown = new Rectangle(x, y + height - 32, 32, 32);
	}

	public void tick() {
		Rectangle mouse = Handler.get().getMouse();
		
		if(scrollUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
			scrollUp();
		}
		
		else if(scrollDown.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
			scrollDown();
		}
	}

	private void scrollUp() {
		// The first click, move it up once
		if(clickTimer == 0) {
			if(listSize < itemsPerWindow) {
				return;
			}
			if(index == scrollMinimum) {
				return;
			}else {
				index--;
			}
		}
		clickTimer++;

		// After .5 seconds of pressing, start scrolling
		if(clickTimer >= 30) {
			scrollTimer++;
			if(scrollTimer % 6 == 0) {
				if(listSize < itemsPerWindow) {
					return;
				}
				if(index == scrollMinimum) {
					return;
				}else {
					index--;
				}
			}
		}
	}

	public void render(Graphics g) {
		Rectangle mouse = Handler.get().getMouse();

		if(listSize > itemsPerWindow) {
			if(scrollUp.contains(mouse)) {
				g.drawImage(Assets.genericButton[0], scrollUp.x, scrollUp.y, scrollUp.width, scrollUp.height, null);
				Text.drawString(g, "^", scrollUp.x + 16, scrollUp.y + 24, true, Color.YELLOW, Assets.font32);
			}else {
				g.drawImage(Assets.genericButton[1], scrollUp.x, scrollUp.y, scrollUp.width, scrollUp.height, null);
				Text.drawString(g, "^", scrollUp.x + 16, scrollUp.y + 24, true, Color.YELLOW, Assets.font32);
			}

			if(scrollDown.contains(mouse)) {
				g.drawImage(Assets.genericButton[0], scrollDown.x, scrollDown.y, scrollDown.width, scrollDown.height, null);
				Text.drawString(g, "v", scrollDown.x + 16, scrollDown.y + 16, true, Color.YELLOW, Assets.font32);
			}else {
				g.drawImage(Assets.genericButton[1], scrollDown.x, scrollDown.y, scrollDown.width, scrollDown.height, null);
				Text.drawString(g, "v", scrollDown.x + 16, scrollDown.y + 16, true, Color.YELLOW, Assets.font32);
			}
		}
	}

	private void scrollDown() {
		// The first click, move it down once
		if(clickTimer == 0) {
			if(listSize < itemsPerWindow) {
				return;
			}
			if(index == scrollMaximum - itemsPerWindow) {
				return;
			}else {
				index++;
			}
		}
		clickTimer++;

		// After .5 seconds of pressing, start scrolling
		if(clickTimer >= 30) {
			scrollTimer++;
			if(scrollTimer % 6 == 0) {
				if(listSize < itemsPerWindow) {
					return;
				}
				if(index == scrollMaximum - itemsPerWindow) {
					return;
				}else {
					index++;
				}
			}
		}
	}

	public int getScrollMinimum() {
		return scrollMinimum;
	}

	public void setScrollMinimum(int scrollMinimum) {
		this.scrollMinimum = scrollMinimum;
	}

	public int getScrollMaximum() {
		return scrollMaximum;
	}

	public void setScrollMaximum(int scrollMaximum) {
		this.scrollMaximum = scrollMaximum;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

}
