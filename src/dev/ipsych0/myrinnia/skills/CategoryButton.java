package dev.ipsych0.myrinnia.skills;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

public class CategoryButton implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5801506235295805009L;
	public int x, y, width, height;
	private SkillCategory category;
	private Rectangle bounds;
	private boolean hovering = false;
	
	public CategoryButton(SkillCategory category, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.category = category;
		
		bounds = new Rectangle(x, y, width, height);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(!hovering) {
			g.drawImage(Assets.genericButton[1], x, y, width, height, null);
		}else {
			g.drawImage(Assets.genericButton[0], x, y, width, height, null);
		}
		Text.drawString(g, category.getName(), x + width / 2, y + height / 2, true, Color.YELLOW, Assets.font14);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}

	public SkillCategory getCategory() {
		return category;
	}

	public void setCategory(SkillCategory category) {
		this.category = category;
	}

}
