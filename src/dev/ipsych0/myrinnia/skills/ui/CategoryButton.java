package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class CategoryButton extends UIImageButton implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5801506235295805009L;
    private SkillCategory category;

    public CategoryButton(SkillCategory category, int x, int y, int width, int height) {
        super(x, y, width, height, Assets.genericButton);
        this.category = category;

    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g) {
        super.render(g);
        Text.drawString(g, category.getName(), x + width / 2, y + height / 2, true, Color.YELLOW, Assets.font14);
    }

    public SkillCategory getCategory() {
        return category;
    }

    public void setCategory(SkillCategory category) {
        this.category = category;
    }

}
