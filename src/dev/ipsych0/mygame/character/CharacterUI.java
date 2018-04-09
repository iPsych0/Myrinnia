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
	private int baseStatPoints = 0;
	private int elementalStatPoints = 0;
	private int melee = 0, ranged = 0, magic = 0;
	private int fire = 0, air = 0, water = 0, earth = 0;
	
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
			Text.drawString(g, "Character stats:", x + 12, y + 33, false, Color.YELLOW, Assets.font20);
//			g.drawRect(x + 156, y + 14, 176, 24);
//			Text.drawString(g, "Character abilities", x + 164, y + 33, false, Color.YELLOW, Assets.font20);
//			g.drawRect(x + 332, y + 14, 148, 24);
//			Text.drawString(g, "Character skills", x + 340, y + 33, false, Color.YELLOW, Assets.font20);
			
			Text.drawString(g, "Combat lvl: " + handler.getPlayer().getCombatLevel(), x + 16, y + 64, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "HP: " + handler.getPlayer().getHealth() + "/" + handler.getPlayer().getMAX_HEALTH(), x + 16, y + 80, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "EXP: 0/250", x + 16, y + 96, false, Color.YELLOW, Assets.font14);
			
			g.drawImage(Assets.mainMenuButton[1], x + 90, y + 136, 16, 16, null);
			g.drawImage(Assets.mainMenuButton[1], x + 90, y + 152, 16, 16, null);
			g.drawImage(Assets.mainMenuButton[1], x + 90, y + 168, 16, 16, null);
			Text.drawString(g, "Combat stats:", x + 16, y + 128, false, Color.YELLOW, Assets.font14); Text.drawString(g, "(" + baseStatPoints + " points)", x + 120, y + 128, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Melee: " + melee+ "     +", x + 16, y + 148, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Ranged: "+ranged+"   +", x + 16, y + 164, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Magic: "+magic+"     +", x + 16, y + 180, false, Color.YELLOW, Assets.font14);

			g.drawImage(Assets.mainMenuButton[1], x + 90, y + 217, 16, 16, null);
			g.drawImage(Assets.mainMenuButton[1], x + 90, y + 233, 16, 16, null);
			g.drawImage(Assets.mainMenuButton[1], x + 90, y + 249, 16, 16, null);
			g.drawImage(Assets.mainMenuButton[1], x + 90, y + 265, 16, 16, null);
			Text.drawString(g, "Elemental stats: ", x + 16, y + 208, false, Color.YELLOW, Assets.font14); Text.drawString(g, "(" + elementalStatPoints + " points)", x + 120, y + 208, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Fire: "+fire+"        +", x + 16, y + 230, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Air: "+air+"         +", x + 16, y + 246, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Water: "+water+"     +", x + 16, y + 262, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Earth: "+earth+"      +", x + 16, y + 278, false, Color.YELLOW, Assets.font14);
		}
	}

	public int getBaseStatPoints() {
		return baseStatPoints;
	}

	public void setBaseStatPoints(int baseStatPoints) {
		this.baseStatPoints = baseStatPoints;
	}

	public int getElementalStatPoints() {
		return elementalStatPoints;
	}

	public void setElementalStatPoints(int elementalStatPoints) {
		this.elementalStatPoints = elementalStatPoints;
	}

	public int getMelee() {
		return melee;
	}

	public void setMelee(int melee) {
		this.melee = melee;
	}

	public int getRanged() {
		return ranged;
	}

	public void setRanged(int ranged) {
		this.ranged = ranged;
	}

	public int getMagic() {
		return magic;
	}

	public void setMagic(int magic) {
		this.magic = magic;
	}

	public int getFire() {
		return fire;
	}

	public void setFire(int fire) {
		this.fire = fire;
	}

	public int getAir() {
		return air;
	}

	public void setAir(int air) {
		this.air = air;
	}

	public int getWater() {
		return water;
	}

	public void setWater(int water) {
		this.water = water;
	}

	public int getEarth() {
		return earth;
	}

	public void setEarth(int earth) {
		this.earth = earth;
	}

}
