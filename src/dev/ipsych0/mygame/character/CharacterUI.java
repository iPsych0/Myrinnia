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
	private static final long serialVersionUID = 2534979108806910921L;
	public int x = 8, y = 180, width = 208, height = 320;
	public static boolean isOpen = false;
	private int baseStatPoints = 5;
	private int elementalStatPoints = 5;
	private Rectangle meleeUp, rangedUp, magicUp, fireUp, airUp, waterUp, earthUp;
	public static boolean hasBeenPressed = false;
	private Rectangle bounds;
	public static boolean escapePressed = false;
	private Rectangle exit;
	
	public CharacterUI() {		
		meleeUp = new Rectangle(x + 92, y + 136, 16, 16);
		rangedUp = new Rectangle(x + 92, y + 152, 16, 16);
		magicUp = new Rectangle(x + 92, y + 168, 16, 16);
		
		fireUp = new Rectangle(x + 92, y + 217, 16, 16);
		airUp = new Rectangle(x + 92, y + 233, 16, 16);
		waterUp = new Rectangle(x + 92, y + 249, 16, 16);
		earthUp = new Rectangle(x + 92, y + 265, 16, 16);
		
		bounds = new Rectangle(x, y, width, height);
		
		exit = new Rectangle(x + (width / 2) / 2, y + height - 24, width / 2, 16);
	}
	
	public void tick() {
		if(isOpen) {
			Rectangle mouse = Handler.get().getMouse();
			
			if(Handler.get().getKeyManager().escape && escapePressed) {
				isOpen = false;
				hasBeenPressed = false;
				return;
			}
			
			// If Base stats are upped
			if(baseStatPoints >= 1) {
				if(meleeUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					CharacterStats.Melee.addLevel();
					baseStatPoints -= 1;
					hasBeenPressed = false;
				}
				else if(rangedUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					CharacterStats.Ranged.addLevel();
					baseStatPoints -= 1;
					hasBeenPressed = false;
				}
				else if(magicUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					CharacterStats.Magic.addLevel();
					baseStatPoints -= 1;
					hasBeenPressed = false;
				}
			}
			
			// If Elemental stats are upped
			if(elementalStatPoints >= 1) {
				if(fireUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					CharacterStats.Fire.addLevel();
					elementalStatPoints -= 1;
					hasBeenPressed = false;
				}
				else if(airUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					CharacterStats.Air.addLevel();
					elementalStatPoints -= 1;
					hasBeenPressed = false;
				}
				else if(waterUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					CharacterStats.Water.addLevel();
					elementalStatPoints -= 1;
					hasBeenPressed = false;
				}
				else if(earthUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
					CharacterStats.Earth.addLevel();
					elementalStatPoints -= 1;
					hasBeenPressed = false;
				}
			}
			
		}
	}
	
	public void render(Graphics g) {
		if(isOpen) {
			g.drawImage(Assets.shopWindow, x, y, width, height, null);
			
			Rectangle mouse = Handler.get().getMouse();
			
			Text.drawString(g, "Character stats:", x + width / 2, y + 21, true, Color.YELLOW, Assets.font20);
			
			Text.drawString(g, "Combat lvl: " + Handler.get().getSkillsUI().getSkill(SkillsList.COMBAT).getLevel(), x + 16, y + 64, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "HP: " + Handler.get().getPlayer().getHealth() + "/" + Handler.get().getPlayer().getMaxHealth(), x + 16, y + 80, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "XP: "+Handler.get().getSkillsUI().getSkill(SkillsList.COMBAT).getExperience()+"/"+Handler.get().getSkillsUI().getSkill(SkillsList.COMBAT).getNextLevelXp(), x + 16, y + 96, false, Color.YELLOW, Assets.font14);
			
			// If we have points available, draw the 
			if(baseStatPoints >= 1) {
				if(meleeUp.contains(mouse)) {
					g.drawImage(Assets.genericButton[0], x + 92, y + 136, 16, 16, null);
				}else {
					g.drawImage(Assets.genericButton[1], x + 92, y + 136, 16, 16, null);
				}
				
				if(rangedUp.contains(mouse)) {
					g.drawImage(Assets.genericButton[0], x + 92, y + 152, 16, 16, null);
				}else {
					g.drawImage(Assets.genericButton[1], x + 92, y + 152, 16, 16, null);
				}
				
				if(magicUp.contains(mouse)) {
					g.drawImage(Assets.genericButton[0], x + 92, y + 168, 16, 16, null);
				}else {
					g.drawImage(Assets.genericButton[1], x + 92, y + 168, 16, 16, null);
				}
				Text.drawString(g, "+", x + 101, y + 144, true, Color.YELLOW, Assets.font14);
				Text.drawString(g, "+", x + 101, y + 160, true, Color.YELLOW, Assets.font14);
				Text.drawString(g, "+", x + 101, y + 176, true, Color.YELLOW, Assets.font14);
			}
			
			Text.drawString(g, "Combat stats:", x + 16, y + 128, false, Color.YELLOW, Assets.font14); Text.drawString(g, "(" + baseStatPoints + " points)", x + 128, y + 128, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Melee:   "+CharacterStats.Melee.getLevel(), x + 16, y + 148, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Ranged: "+CharacterStats.Ranged.getLevel(), x + 16, y + 164, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Magic:   "+CharacterStats.Magic.getLevel(), x + 16, y + 180, false, Color.YELLOW, Assets.font14);

			if(elementalStatPoints >= 1) {
				if(fireUp.contains(mouse)) {
					g.drawImage(Assets.genericButton[0], x + 92, y + 217, 16, 16, null);
				}else {
					g.drawImage(Assets.genericButton[1], x + 92, y + 217, 16, 16, null);
				}
				
				if(airUp.contains(mouse)) {
					g.drawImage(Assets.genericButton[0], x + 92, y + 233, 16, 16, null);
				}else {
					g.drawImage(Assets.genericButton[1], x + 92, y + 233, 16, 16, null);
				}
				
				if(waterUp.contains(mouse)) {
					g.drawImage(Assets.genericButton[0], x + 92, y + 249, 16, 16, null);
				}else {
					g.drawImage(Assets.genericButton[1], x + 92, y + 249, 16, 16, null);
				}
				
				if(earthUp.contains(mouse)) {
					g.drawImage(Assets.genericButton[0], x + 92, y + 265, 16, 16, null);
				}else {
					g.drawImage(Assets.genericButton[1], x + 92, y + 265, 16, 16, null);
				}
				
				Text.drawString(g, "+", x + 101, y + 225, true, Color.YELLOW, Assets.font14);
				Text.drawString(g, "+", x + 101, y + 241, true, Color.YELLOW, Assets.font14);
				Text.drawString(g, "+", x + 101, y + 257, true, Color.YELLOW, Assets.font14);
				Text.drawString(g, "+", x + 101, y + 273, true, Color.YELLOW, Assets.font14);
			}
			Text.drawString(g, "Elemental stats: ", x + 16, y + 208, false, Color.YELLOW, Assets.font14); Text.drawString(g, "(" + elementalStatPoints + " points)", x + 128, y + 208, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Fire:      "+CharacterStats.Fire.getLevel(), x + 16, y + 230, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Air:       "+CharacterStats.Air.getLevel(), x + 16, y + 246, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Water:   "+CharacterStats.Water.getLevel(), x + 16, y + 262, false, Color.YELLOW, Assets.font14);
			Text.drawString(g, "Earth:    "+CharacterStats.Earth.getLevel(), x + 16, y + 278, false, Color.YELLOW, Assets.font14);
			
			if(exit.contains(mouse)) {
				g.drawImage(Assets.genericButton[0], exit.x, exit.y, exit.width, exit.height, null);
				if(Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
					hasBeenPressed = false;
					isOpen = false;
				}
			}else {
				g.drawImage(Assets.genericButton[1], exit.x, exit.y, exit.width, exit.height, null);
			}
			Text.drawString(g, "Exit", x + (width / 2), y + height - 16, true, Color.YELLOW, Assets.font14);
		}
	}

	public int getBaseStatPoints() {
		return baseStatPoints;
	}

	public void addBaseStatPoints() {
		this.baseStatPoints++;
	}

	public void addBaseStatPoints(int points) {
		this.baseStatPoints += points;
	}

	public int getElementalStatPoints() {
		return elementalStatPoints;
	}

	public void addElementalStatPoints() {
		this.elementalStatPoints++;
	}

	public void addElementalStatPoints(int points) {
		this.elementalStatPoints += points;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

}
