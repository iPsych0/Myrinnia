package dev.ipsych0.mygame.abilityhud;

import java.awt.Color;
import java.awt.Graphics;

import dev.ipsych0.mygame.abilities.Ability;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.utils.Text;

public class AbilityTooltip {
	
	private int x, y, width, height;
	
	public AbilityTooltip(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, Ability ability) {
		g.drawImage(Assets.invScreen, x, y, width, height, null);
		ability.render(g, x + 4, y + 16);
		Text.drawString(g, ability.getName(), x + 40, y + 32, false, Color.YELLOW, Assets.font14);
		
		int yPosText = 64;
		int yOffset = 16;
		int index = 0;
		Text.drawString(g, "Type: "+ability.getAbilityType(), x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);
		Text.drawString(g, "Element: "+ability.getElement(), x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);
		Text.drawString(g, "Base dmg: "+ability.getBaseDamage(), x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);
		Text.drawString(g, "Cooldown: "+ability.getCooldownTimer() +"s", x + 4, y + yPosText + (index++ * yOffset), false, Color.YELLOW, Assets.font14);
		String[] description = Text.splitIntoLine("Description: "+ability.getDescription(), 22);
		for(int i = 0; i < description.length; i++) {
			Text.drawString(g, description[i], x + 4, y + yPosText + (index * yOffset) + (i * 16), false, Color.YELLOW, Assets.font14);
		}
	}

}
