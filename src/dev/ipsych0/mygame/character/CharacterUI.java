package dev.ipsych0.mygame.character;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.Text;

public class CharacterUI implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x = 0, y = 180, width = 192, height = 320;
	private Handler handler;
	public static boolean isOpen = false;
	
	public CharacterUI(Handler handler) {
		this.handler = handler;
		
	}
	
	public void tick() {
		if(isOpen) {
			
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			g.drawImage(Assets.shopWindow, x, y, width, height, null);
			
//			g.drawRect(x + 8, y + 14, 148, 24);
			Text.drawString(g, "Character stats", x + 14, y + 33, false, Color.YELLOW, Assets.font20);
//			g.drawRect(x + 156, y + 14, 176, 24);
//			Text.drawString(g, "Character abilities", x + 164, y + 33, false, Color.YELLOW, Assets.font20);
//			g.drawRect(x + 332, y + 14, 148, 24);
//			Text.drawString(g, "Character skills", x + 340, y + 33, false, Color.YELLOW, Assets.font20);
			
			Text.drawString(g, "Combat lvl: " + handler.getPlayer().getCombatLevel(), x + 16, y + 64, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "HP: " + handler.getPlayer().getHealth() + "/" + handler.getPlayer().getMAX_HEALTH(), x + 16, y + 80, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "EXP: 0/250", x + 16, y + 96, false, Color.YELLOW, Assets.font14);
			
			g.drawImage(Assets.mainMenuButton[1], x + 90, y + 132, 16, 16, null);
			g.drawImage(Assets.mainMenuButton[1], x + 90, y + 148, 16, 16, null);
			g.drawImage(Assets.mainMenuButton[1], x + 90, y + 164, 16, 16, null);
			Text.drawString(g, "Base stats:", x + 16, y + 128, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Melee: 0     +", x + 16, y + 144, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Ranged: 0   +", x + 16, y + 160, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Magic: 0     +", x + 16, y + 176, false, Color.YELLOW, Assets.font14);

			
			Text.drawString(g, "Equipment stats: ", x + 16, y + 208, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Power = " + handler.getPlayer().getPower(), x + 16, y + 224, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Defence = " + handler.getPlayer().getDefence(), x + 16, y + 240, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Vitality = " + handler.getPlayer().getVitality(), x + 16, y + 256, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "ATK Spd. = " + handler.getPlayer().getAttackSpeed(), x + 16, y + 272, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Mov. Spd. = " + handler.getPlayer().getSpeed(), x + 16, y + 288, false, Color.YELLOW, Assets.font14);
		}
	}

}
