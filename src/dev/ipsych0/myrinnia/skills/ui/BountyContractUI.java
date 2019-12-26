package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class BountyContractUI implements Serializable {

    private static final long serialVersionUID = -3447762773306372576L;
    private static Bounty bounty;
    private int x, y, width, height;
    public static boolean isOpen;
    private UIManager uiManager;
    private UIImageButton exitButton;
    private Rectangle bounds;

    public BountyContractUI() {
        width = 300;
        height = 300;
        x = Handler.get().getWidth() / 2 - width / 2;
        y = Handler.get().getHeight() / 2 - height / 2;
        bounds = new Rectangle(x, y, width, height);

        uiManager = new UIManager();
        exitButton = new UIImageButton(x + width / 2 - 48, y + height - 64, 96, 32, Assets.genericButton);
        uiManager.addObject(exitButton);
    }

    public void tick() {
        if (isOpen) {
            uiManager.tick();
            if (exitButton.isHovering() && Handler.get().getMouseManager().isLeftPressed() || Handler.get().getKeyManager().escape) {
                close();
            }
        }
    }

    public void render(Graphics2D g) {
        if (isOpen) {
            g.drawImage(Assets.uiWindow, x, y, width, height, null);
            uiManager.render(g);

            Text.drawString(g, bounty.getTask(), x + width / 2, y + 20, true, Color.YELLOW, Assets.font20);
            String[] lines = Text.splitIntoLine(bounty.getFullDescription(), 44);
            for (int i = 0; i < lines.length; i++) {
                Text.drawString(g, lines[i], x + width / 2, y + 48 + (i * 16), true, Color.YELLOW, Assets.font14);
            }

            Text.drawString(g, "Close", exitButton.x + exitButton.width / 2, exitButton.y + exitButton.height / 2, true, Color.YELLOW, Assets.font14);
        }
    }

    public static void open(Bounty bounty1) {
        bounty = bounty1;
        isOpen = true;
    }

    private void close() {
        isOpen = false;
        if (Handler.get().getMouseManager().isLeftPressed()) {
            MouseManager.justClosedUI = true;
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
