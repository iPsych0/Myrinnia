package dev.ipsych0.mygame.hpoverlay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.character.CharacterUI;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.skills.SkillsOverviewUI;
import dev.ipsych0.mygame.skills.SkillsUI;
import dev.ipsych0.mygame.utils.Text;

public class HPOverlay implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Handler handler;
	private Rectangle bounds;
	private Rectangle combatBar, hpBar, xpBar;
	private Rectangle skillsButton, characterButton, abilitiesButton;
	public static Color hpColorRed, hpColorGreen, xpColor;
	public static Color hpColorRedOutline, hpColorGreenOutline, xpColorOutline;
	public static boolean hasBeenPressed = false;
	
	public HPOverlay(Handler handler) {
		this.handler = handler;
		
		hpBar = new Rectangle(48,32,144,24);
		xpBar = new Rectangle(48,56,144,24);
		
		bounds = new Rectangle(0, 0, 208, 128);
		
		combatBar = new Rectangle(0,2,208,32);
		
		hpColorRed = new Color(140, 0, 0);
		hpColorGreen = new Color(50, 135, 0);
		xpColor = new Color(180, 135, 5);
		
		skillsButton = new Rectangle(bounds.x + bounds.width / 2 - 48, bounds.y + bounds.height - 40, 32, 32);
		characterButton = new Rectangle(bounds.x + bounds.width / 2 - 16, bounds.y + bounds.height - 40, 32, 32);
		abilitiesButton = new Rectangle(bounds.x + bounds.width / 2 + 16, bounds.y + bounds.height - 40, 32, 32);
		
		hpColorRedOutline = new Color(190, 0, 0);
		hpColorGreenOutline = new Color(50, 180, 0);
		xpColorOutline = new Color(240, 160, 5);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		Rectangle mouse = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
		
		// Draw the bars
		g.drawImage(Assets.genericButton[0], bounds.x, bounds.y, bounds.width, bounds.height, null);
		g.drawImage(Assets.genericButton[1], hpBar.x, hpBar.y, hpBar.width, hpBar.height, null);
		g.drawImage(Assets.genericButton[1], xpBar.x, xpBar.y, xpBar.width, xpBar.height, null);
		
		// Draw the HP/EXP pre-fix to bars
		g.drawImage(Assets.genericButton[1], hpBar.x - 32, hpBar.y, 32, 24, null);
		g.drawImage(Assets.genericButton[1], xpBar.x - 32, xpBar.y, 32, 24, null);
		
		// Skills UI button
		if(skillsButton.contains(mouse)) {
			g.drawImage(Assets.genericButton[0], skillsButton.x, skillsButton.y, skillsButton.width, skillsButton.height, null);
			if(handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
				hasBeenPressed = false;
				SkillsUI.isOpen = !SkillsUI.isOpen;
				CharacterUI.isOpen = false;
			}
		}else
			g.drawImage(Assets.genericButton[1], skillsButton.x, skillsButton.y, skillsButton.width, skillsButton.height, null);
		
		// Character UI button
		if(characterButton.contains(mouse)) {
			g.drawImage(Assets.genericButton[0], characterButton.x, characterButton.y, characterButton.width, characterButton.height, null);
			if(handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
				hasBeenPressed = false;
				SkillsUI.isOpen = false;
				SkillsOverviewUI.isOpen = false;
				CharacterUI.isOpen = !CharacterUI.isOpen;
			}
		}else
			g.drawImage(Assets.genericButton[1], characterButton.x, characterButton.y, characterButton.width, characterButton.height, null);
		
		// Abilities UI button
		if(abilitiesButton.contains(mouse)) {
			g.drawImage(Assets.genericButton[0], abilitiesButton.x, abilitiesButton.y, abilitiesButton.width, abilitiesButton.height, null);
			if(handler.getMouseManager().isLeftPressed() && hasBeenPressed) {
				hasBeenPressed = false;
				handler.sendMsg("Abilities menu coming soon!");
			}
		}
		else
			g.drawImage(Assets.genericButton[1], abilitiesButton.x, abilitiesButton.y, abilitiesButton.width, abilitiesButton.height, null);
		
		// UI button icons
		g.drawImage(Assets.hpOverlaySkillsIcon, skillsButton.x, skillsButton.y, 32, 32, null);
		g.drawImage(Assets.hpOverlayCharacterIcon, characterButton.x, characterButton.y, 32, 32, null);
		g.drawImage(Assets.hpOverlayAbilitiesIcon, abilitiesButton.x, abilitiesButton.y, 32, 32, null);
		
		Text.drawString(g, "HP", hpBar.x - 16, hpBar.y + hpBar.height / 2, true, Color.YELLOW, Assets.font14);
		Text.drawString(g, "XP", xpBar.x - 16, xpBar.y + xpBar.height / 2, true, Color.YELLOW, Assets.font14);
		
		// HP Bar
		g.setColor(hpColorRed);
		g.fillRoundRect(hpBar.x + 2, hpBar.y + 1, hpBar.width - 4, hpBar.height - 3, 2, 4);
		g.setColor(hpColorRedOutline);
		g.drawRoundRect(hpBar.x + 2, hpBar.y + 1, hpBar.width - 4, hpBar.height - 3, 2, 4);
		
		g.setColor(hpColorGreen);
		if(handler.getPlayer().getHealth() >= handler.getPlayer().getMaxHealth()) {
			g.fillRoundRect(hpBar.x + 2, hpBar.y + 1, hpBar.width - 4, hpBar.height - 3, 2, 4);
			
			g.setColor(hpColorGreenOutline);
			g.drawRoundRect(hpBar.x + 2, hpBar.y + 1, hpBar.width - 4, hpBar.height - 3, 2, 4);
		}else {
			g.fillRoundRect(hpBar.x + 2, hpBar.y + 1, (int)(hpBar.width * (double)handler.getPlayer().getHealth() /
					(double)handler.getPlayer().getMaxHealth()) - 4, hpBar.height - 3, 2, 4);
			
			g.setColor(hpColorGreenOutline);
			g.drawRoundRect(hpBar.x + 2, hpBar.y + 1, (int)(hpBar.width * (double)handler.getPlayer().getHealth() /
					(double)handler.getPlayer().getMaxHealth()) - 4, hpBar.height - 3, 2, 4);
		}
		
		// XP bar
		g.setColor(xpColor);
		g.fillRoundRect(xpBar.x + 2, xpBar.y + 1, (int)(xpBar.width * handler.getSkill(SkillsList.COMBAT).getExperience() / handler.getSkill(SkillsList.COMBAT).getNextLevelXp()) - 2, xpBar.height - 4, 2, 4);
		g.setColor(xpColorOutline);
		g.drawRoundRect(xpBar.x + 2, xpBar.y + 1, (int)(xpBar.width * handler.getSkill(SkillsList.COMBAT).getExperience() / handler.getSkill(SkillsList.COMBAT).getNextLevelXp()) - 2, xpBar.height - 4, 2, 4);
		
		Text.drawString(g, "Combat level: " + Integer.toString(handler.getSkillsUI().getSkill(SkillsList.COMBAT).getLevel()),
				combatBar.x + combatBar.width / 2 , combatBar.y + combatBar.height / 2, true, Color.YELLOW, Assets.font14);
		
		Text.drawString(g, handler.getPlayer().getHealth() + "/" + 
				handler.getPlayer().getMaxHealth(), hpBar.x + hpBar.width / 2, hpBar.y + hpBar.height / 2, true, Color.YELLOW, Assets.font14);
		
		Text.drawString(g, handler.getSkillsUI().getSkill(SkillsList.COMBAT).getExperience()+"/"+handler.getSkillsUI().getSkill(SkillsList.COMBAT).getNextLevelXp(),
				xpBar.x + xpBar.width / 2, xpBar.y + xpBar.height / 2, true, Color.YELLOW, Assets.font14);

		if(skillsButton.contains(mouse)) {
			g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 96, 32, null);
			Text.drawString(g, "Skills (L)", mouse.x + 56, mouse.y + 24, true, Color.YELLOW, Assets.font14);
		}
		if(characterButton.contains(mouse)) {
			g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 96, 32, null);
			Text.drawString(g, "Character (K)", mouse.x + 56, mouse.y + 24, true, Color.YELLOW, Assets.font14);
		}
		if(abilitiesButton.contains(mouse)) {
			g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 96, 32, null);
			Text.drawString(g, "Abilities (B)", mouse.x + 56, mouse.y + 24, true, Color.YELLOW, Assets.font14);
		}
		
		
//		g.drawString("FPS: " + String.valueOf(handler.getGame().getFramesPerSecond()), 2, 140);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

}
