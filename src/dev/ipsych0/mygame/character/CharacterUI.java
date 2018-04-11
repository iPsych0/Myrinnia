package dev.ipsych0.mygame.character;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.utils.Text;

public class CharacterUI implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x = 0, y = 180, width = 192, height = 320;
	private Handler handler;
	public static boolean isOpen = false;
	private int baseStatPoints = 5;
	private int elementalStatPoints = 5;
	private int melee = 0, ranged = 0, magic = 0;
	private int fire = 0, air = 0, water = 0, earth = 0;
	private Rectangle meleeUp, rangedUp, magicUp, fireUp, airUp, waterUp, earthUp;
	public static boolean hasBeenPressed = false;
	private Rectangle bounds;
	
	public CharacterUI(Handler handler) {
		this.handler = handler;
		
		meleeUp = new Rectangle(x + 92, y + 136, 16, 16);
		rangedUp = new Rectangle(x + 92, y + 152, 16, 16);
		magicUp = new Rectangle(x + 92, y + 168, 16, 16);
		
		fireUp = new Rectangle(x + 92, y + 217, 16, 16);
		airUp = new Rectangle(x + 92, y + 233, 16, 16);
		waterUp = new Rectangle(x + 92, y + 249, 16, 16);
		earthUp = new Rectangle(x + 92, y + 265, 16, 16);
		
		bounds = new Rectangle(x, y, width, height);
		
	}
	
	public void tick() {
		if(isOpen) {
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			// If Base stats are upped
			if(baseStatPoints >= 1) {
				if(meleeUp.contains(mouse) && handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					melee += 1;
					baseStatPoints -= 1;
					hasBeenPressed = false;
				}
				else if(rangedUp.contains(mouse) && handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					ranged += 1;
					baseStatPoints -= 1;
					hasBeenPressed = false;
				}
				else if(magicUp.contains(mouse) && handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					magic += 1;
					baseStatPoints -= 1;
					hasBeenPressed = false;
				}
			}
			
			// If Elemental stats are upped
			if(elementalStatPoints >= 1) {
				if(fireUp.contains(mouse) && handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					fire += 1;
					elementalStatPoints -= 1;
					hasBeenPressed = false;
				}
				else if(airUp.contains(mouse) && handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					air += 1;
					elementalStatPoints -= 1;
					hasBeenPressed = false;
				}
				else if(waterUp.contains(mouse) && handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					water += 1;
					elementalStatPoints -= 1;
					hasBeenPressed = false;
				}
				else if(earthUp.contains(mouse) && handler.getMouseManager().isLeftPressed() && !handler.getMouseManager().isDragged() && hasBeenPressed) {
					earth += 1;
					elementalStatPoints -= 1;
					hasBeenPressed = false;
				}
			}
			
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			g.drawImage(Assets.shopWindow, x, y, width, height, null);
			
			Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
			
			Text.drawString(g, "Character stats:", x + width / 2, y + 21, true, Color.YELLOW, Assets.font20);
			
			Text.drawString(g, "Combat lvl: " + handler.getSkillsUI().getSkill(SkillsList.COMBAT).getLevel(), x + 16, y + 64, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "HP: " + handler.getPlayer().getHealth() + "/" + handler.getPlayer().getMAX_HEALTH(), x + 16, y + 80, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "EXP: "+handler.getSkillsUI().getSkill(SkillsList.COMBAT).getExperience()+"/"+handler.getSkillsUI().getSkill(SkillsList.COMBAT).getNextLevelXp(), x + 16, y + 96, false, Color.YELLOW, Assets.font14);
			
			// If we have points available, draw the 
			if(baseStatPoints >= 1) {
				if(meleeUp.contains(mouse)) {
					g.drawImage(Assets.mainMenuButton[0], x + 92, y + 136, 16, 16, null);
				}else {
					g.drawImage(Assets.mainMenuButton[1], x + 92, y + 136, 16, 16, null);
				}
				
				if(rangedUp.contains(mouse)) {
					g.drawImage(Assets.mainMenuButton[0], x + 92, y + 152, 16, 16, null);
				}else {
					g.drawImage(Assets.mainMenuButton[1], x + 92, y + 152, 16, 16, null);
				}
				
				if(magicUp.contains(mouse)) {
					g.drawImage(Assets.mainMenuButton[0], x + 92, y + 168, 16, 16, null);
				}else {
					g.drawImage(Assets.mainMenuButton[1], x + 92, y + 168, 16, 16, null);
				}
				Text.drawString(g, "+", x + 101, y + 144, true, Color.YELLOW, Assets.font14);
				Text.drawString(g, "+", x + 101, y + 160, true, Color.YELLOW, Assets.font14);
				Text.drawString(g, "+", x + 101, y + 176, true, Color.YELLOW, Assets.font14);
			}
			
			Text.drawString(g, "Combat stats:", x + 16, y + 128, false, Color.YELLOW, Assets.font14); Text.drawString(g, "(" + baseStatPoints + " points)", x + 120, y + 128, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Melee:   "+melee, x + 16, y + 148, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Ranged: "+ranged, x + 16, y + 164, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Magic:   "+magic, x + 16, y + 180, false, Color.YELLOW, Assets.font14);

			if(elementalStatPoints >= 1) {
				if(fireUp.contains(mouse)) {
					g.drawImage(Assets.mainMenuButton[0], x + 92, y + 217, 16, 16, null);
				}else {
					g.drawImage(Assets.mainMenuButton[1], x + 92, y + 217, 16, 16, null);
				}
				
				if(airUp.contains(mouse)) {
					g.drawImage(Assets.mainMenuButton[0], x + 92, y + 233, 16, 16, null);
				}else {
					g.drawImage(Assets.mainMenuButton[1], x + 92, y + 233, 16, 16, null);
				}
				
				if(waterUp.contains(mouse)) {
					g.drawImage(Assets.mainMenuButton[0], x + 92, y + 249, 16, 16, null);
				}else {
					g.drawImage(Assets.mainMenuButton[1], x + 92, y + 249, 16, 16, null);
				}
				
				if(earthUp.contains(mouse)) {
					g.drawImage(Assets.mainMenuButton[0], x + 92, y + 265, 16, 16, null);
				}else {
					g.drawImage(Assets.mainMenuButton[1], x + 92, y + 265, 16, 16, null);
				}
				
				Text.drawString(g, "+", x + 101, y + 225, true, Color.YELLOW, Assets.font14);
				Text.drawString(g, "+", x + 101, y + 241, true, Color.YELLOW, Assets.font14);
				Text.drawString(g, "+", x + 101, y + 257, true, Color.YELLOW, Assets.font14);
				Text.drawString(g, "+", x + 101, y + 273, true, Color.YELLOW, Assets.font14);
			}
			Text.drawString(g, "Elemental stats: ", x + 16, y + 208, false, Color.YELLOW, Assets.font14); Text.drawString(g, "(" + elementalStatPoints + " points)", x + 120, y + 208, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Fire:      "+fire, x + 16, y + 230, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Air:       "+air, x + 16, y + 246, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Water:   "+water, x + 16, y + 262, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Earth:    "+earth, x + 16, y + 278, false, Color.YELLOW, Assets.font14);
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

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

}
