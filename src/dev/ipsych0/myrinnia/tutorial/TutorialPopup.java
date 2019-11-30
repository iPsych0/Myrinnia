package dev.ipsych0.myrinnia.tutorial;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class TutorialPopup implements Serializable {

    private static final long serialVersionUID = 1144402697977172941L;
    private String[] lines;
    private int blinkTimer;
    private UIImageButton okButton;
    private UIManager uiManager;
    private boolean okPressed;
    private int originalBoundX = -192, originalButtonX;
    private Rectangle bounds = new Rectangle(originalBoundX, 144, 208, 128);

    public TutorialPopup(String[] lines) {
        this.lines = lines;

        int height = 64 + (lines.length * 16);
        if (height < 128) {
            height = 128;
        }
        bounds.setBounds(bounds.x, bounds.y, bounds.width, height);

        okButton = new UIImageButton(bounds.x + bounds.width / 2 - 32, bounds.y + bounds.height - 24, 64, 16, Assets.genericButton);
        uiManager = new UIManager();
        uiManager.addObject(okButton);
        originalButtonX = okButton.x;
    }

    public void render(Graphics2D g, int offset) {
        bounds.setLocation(originalBoundX + offset, bounds.y);
        okButton.setLocation(originalButtonX + offset, okButton.y);
        g.drawImage(Assets.uiWindow, bounds.x, bounds.y, bounds.width, bounds.height, null);

        blinkTimer++;
        if (blinkTimer < 30) {
            g.setColor(Color.YELLOW);
            Stroke stroke = g.getStroke();
            g.setStroke(new BasicStroke(3));
            g.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 2, 2);
            g.setStroke(stroke);
        }

        if (blinkTimer > 60) {
            blinkTimer = 0;
        }

        Text.drawString(g, "Tip:", bounds.x + bounds.width / 2, bounds.y + 10, true, Color.YELLOW, Assets.font20);
        for (int i = 0; i < lines.length; i++) {
            Text.drawString(g, lines[i], bounds.x + bounds.width / 2, bounds.y + 32 + (i * 16), true, Color.YELLOW, Assets.font14);
        }

        uiManager.tick();
        uiManager.render(g);
        Text.drawString(g, "Got it!", okButton.x + okButton.width / 2, okButton.y + okButton.height / 2 - 1, true, Color.YELLOW, Assets.font14);

        if (okButton.isHovering() && Handler.get().getMouseManager().isLeftPressed()) {
            okPressed = true;
            MouseManager.justClosedUI = true;
        }
    }

    public boolean isOkPressed() {
        return okPressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
