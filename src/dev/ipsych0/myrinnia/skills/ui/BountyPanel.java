package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class BountyPanel extends UIImageButton {

    private String task;
    private String description;

    public BountyPanel(String task, String description, int x, int y, int width, int height) {
        super(x, y, width, height, Assets.genericButton);
        this.task = task;
        this.description = description;

        if (description.length() > 100) {
            throw new IllegalArgumentException("Character limit for Bounty Panels is 100.");
        }
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g) {
        super.render(g);
        Text.drawString(g, task, x + 48, y + 20, false, Color.YELLOW, Assets.font20);
        String[] text = Text.splitIntoLine(description, 50);
        for (int i = 0; i < text.length; i++) {
            Text.drawString(g, text[i], x + 48, y + 40 + (i * 16), false, Color.YELLOW, Assets.font14);
        }
    }
}
