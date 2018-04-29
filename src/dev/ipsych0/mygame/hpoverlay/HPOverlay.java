package dev.ipsych0.mygame.hpoverlay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.utils.Text;

public class HPOverlay implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Handler handler;
	private Rectangle bounds;
	
	public HPOverlay(Handler handler) {
		this.handler = handler;
		bounds = new Rectangle(0, 0, 292, 96);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.hpOverlay, bounds.x, bounds.y, bounds.width, bounds.height, null);
		Text.drawString(g, "HP: " + handler.roundOff((double)handler.getPlayer().getHealth() /
				(double)handler.getPlayer().getMAX_HEALTH() * 100) + "%", bounds.x + 176, bounds.y + 29, true, Color.YELLOW, Assets.font14);		
		Text.drawString(g, "Lv. ", bounds.x + 36, bounds.y + 28, false, Color.YELLOW, Assets.font20);
		Text.drawString(g, Integer.toString(handler.getSkillsUI().getSkill(SkillsList.COMBAT).getLevel()), bounds.x + 48, bounds.y + 64, true, Color.YELLOW, Assets.font32);
		Text.drawString(g, "EXP: "+handler.getSkillsUI().getSkill(SkillsList.COMBAT).getExperience()+"/"+handler.getSkillsUI().getSkill(SkillsList.COMBAT).getNextLevelXp(), bounds.x + 176, bounds.y + 54, true, Color.YELLOW, Assets.font14);
		
//		g.drawString("FPS: " + String.valueOf(handler.getGame().getFramesPerSecond()), 2, 140);
	}

}
