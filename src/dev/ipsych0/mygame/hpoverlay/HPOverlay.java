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
	private Rectangle combatBar, hpBar, xpBar;
	
	public HPOverlay(Handler handler) {
		this.handler = handler;
		
		combatBar = new Rectangle(16,2,144,32);
		
		hpBar = new Rectangle(16,32,144,32);
		xpBar = new Rectangle(16,64,144,32);
		
		bounds = new Rectangle(0, 0, 176, 104);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		// Draw the bars
		g.drawImage(Assets.genericButton[0], bounds.x, bounds.y, bounds.width, bounds.height, null);
		g.drawImage(Assets.genericButton[0], hpBar.x, hpBar.y, hpBar.width, hpBar.height, null);
		g.drawImage(Assets.genericButton[0], xpBar.x, xpBar.y, xpBar.width, xpBar.height, null);
		
		g.setColor(Color.RED);
		g.fillRoundRect(hpBar.x + 2, hpBar.y + 2, hpBar.width - 4, hpBar.height - 2, 4, 4);
		g.setColor(Color.GREEN);
		if(handler.getPlayer().getHealth() >= handler.getPlayer().getMAX_HEALTH()) {
			g.fillRoundRect(hpBar.x + 2, hpBar.y + 2, hpBar.width - 4, hpBar.height - 2, 4, 4);
		}else {
			g.fillRoundRect(hpBar.x + 2, hpBar.y + 2, (int)(hpBar.width * (double)handler.getPlayer().getHealth() /
					(double)handler.getPlayer().getMAX_HEALTH()) - 4, hpBar.height - 2, 4, 4);
		}
		
		g.setColor(Color.ORANGE);
		g.fillRoundRect(xpBar.x + 2, xpBar.y + 2, (int)(xpBar.width * handler.getSkill(SkillsList.COMBAT).getExperience() / handler.getSkill(SkillsList.COMBAT).getNextLevelXp()) - 2, xpBar.height - 4, 4, 4);
		
		Text.drawString(g, "Combat level: " + Integer.toString(handler.getSkillsUI().getSkill(SkillsList.COMBAT).getLevel()),
				combatBar.x + combatBar.width / 2 , combatBar.y + combatBar.height / 2, true, Color.YELLOW, Assets.font14);
		
		Text.drawString(g, "HP: " + handler.getPlayer().getHealth() + "/" + 
				handler.getPlayer().getMAX_HEALTH(), hpBar.x + hpBar.width / 2, hpBar.y + hpBar.height / 2, true, Color.YELLOW, Assets.font14);
		
		Text.drawString(g, "EXP: "+handler.getSkillsUI().getSkill(SkillsList.COMBAT).getExperience()+"/"+handler.getSkillsUI().getSkill(SkillsList.COMBAT).getNextLevelXp(),
				xpBar.x + xpBar.width / 2, xpBar.y + xpBar.height / 2, true, Color.YELLOW, Assets.font14);
//		
//		g.drawImage(Assets.genericButton[0], bounds.x + bounds.width / 2 - 49, bounds.y + bounds.height + 1, 32, 32, null);
//		g.drawImage(Assets.genericButton[0], bounds.x + bounds.width / 2 - 16, bounds.y + bounds.height + 1, 32, 32, null);
//		g.drawImage(Assets.genericButton[0], bounds.x + bounds.width / 2 + 17, bounds.y + bounds.height + 1, 32, 32, null);

		
		
//		g.drawString("FPS: " + String.valueOf(handler.getGame().getFramesPerSecond()), 2, 140);
	}

}
