package dev.ipsych0.myrinnia.abilities.ui.abilityhud;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.hpoverlay.HPOverlay;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class XPBar implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -7205279220333584109L;
    private int x, y, width, height;

    public XPBar(int x, int y) {
        this.x = x;
        this.y = y - 32;
        this.width = 32;
        this.height = 64;
    }

    public void tick() {

    }

    public void render(Graphics2D g) {
        g.drawImage(Assets.uiWindow, x, y, width, height, null);
        // XP bar
        double xp = Handler.get().getSkill(SkillsList.COMBAT).getExperience();
        double nextLvl = Handler.get().getSkill(SkillsList.COMBAT).getNextLevelXp();
        double offset = xp / nextLvl;
        int yDiff = (int) (height * offset);
        g.setColor(Colors.xpColor);
        g.fillRect(x + 1, y + 1 + height - yDiff, width - 3, yDiff - 3);
        g.setColor(Colors.xpColorOutline);
        g.drawRect(x + 1, y + 1 + height - yDiff, width - 3, yDiff - 3);
//		
//		Text.drawString(g, Handler.get().getSkillsUI().getSkill(SkillsList.COMBAT).getExperience()+"/"+Handler.get().getSkillsUI().getSkill(SkillsList.COMBAT).getNextLevelXp(),
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
