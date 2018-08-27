package dev.ipsych0.mygame.abilityhud;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.hpoverlay.HPOverlay;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.utils.Text;

public class XPBar implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Handler handler;
	private int x, y, width, height;

	public XPBar(Handler handler, int x, int y) {
		this.handler = handler;
		this.x = x;
		this.y = y - 32;
		this.width = 32;
		this.height = 64;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.invScreen, x, y, width, height, null);
		// XP bar
		double xp = handler.getSkill(SkillsList.COMBAT).getExperience();
		double nextLvl = handler.getSkill(SkillsList.COMBAT).getNextLevelXp();
		double offset = xp / nextLvl;
		int yDiff = (int)(height * offset);
		g.setColor(HPOverlay.xpColor);
		g.fillRoundRect(x + 1, y + 1 + height - yDiff, width - 3, yDiff - 3, 2, 4);
		g.setColor(HPOverlay.xpColorOutline);
		g.drawRoundRect(x + 1, y + 1 + height - yDiff, width - 3, yDiff - 3, 2, 4);
//		
//		Text.drawString(g, handler.getSkillsUI().getSkill(SkillsList.COMBAT).getExperience()+"/"+handler.getSkillsUI().getSkill(SkillsList.COMBAT).getNextLevelXp(),
//				x + width / 2, y + height / 2, true, Color.YELLOW, Assets.font14);
		Text.drawString(g, "XP", x + width / 2, y + height / 2, true, Color.YELLOW, Assets.font14);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
