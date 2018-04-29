package dev.ipsych0.mygame.hpoverlay;

import java.awt.Color;
import java.awt.Graphics;
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
	
	public HPOverlay(Handler handler) {
		this.handler = handler;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(Assets.hpOverlay, 0, 0, 292, 96, null);
		Text.drawString(g, "HP: " + handler.roundOff((double)handler.getPlayer().getHealth() /
				(double)handler.getPlayer().getMAX_HEALTH() * 100) + "%", 176, 29, true, Color.YELLOW, Assets.font14);		
		Text.drawString(g, "Lv. ", 36, 28, false, Color.YELLOW, Assets.font20);
		Text.drawString(g, Integer.toString(handler.getSkillsUI().getSkill(SkillsList.COMBAT).getLevel()), 45, 64, true, Color.YELLOW, Assets.font32);
		Text.drawString(g, "EXP: "+handler.getSkillsUI().getSkill(SkillsList.COMBAT).getExperience()+"/"+handler.getSkillsUI().getSkill(SkillsList.COMBAT).getNextLevelXp(), 176, 54, true, Color.YELLOW, Assets.font14);
		
//		g.drawString("FPS: " + String.valueOf(handler.getGame().getFramesPerSecond()), 2, 140);
	}

}
