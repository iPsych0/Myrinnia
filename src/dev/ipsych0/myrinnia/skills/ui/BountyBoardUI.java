package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.statics.BountyBoard;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BountyBoardUI {

    private Zone zone;
    private int x, y, width, height;
    private Rectangle bounds;
    private List<BountyPanel> panels;
    private UIManager uiManager;
    private int currentYPanel = 0;
    private static final int PANEL_WIDTH = 400, PANEL_HEIGHT = 64;
    private static Color selectedColor = new Color(0, 255, 255, 62);
    private BountyPanel selectedPanel;
    public static boolean isOpen;

    public BountyBoardUI(Zone zone, List<BountyPanel> panels) {
        this.zone = zone;
        this.panels = panels;
        width = 460;
        height = 460;
        x = Handler.get().getWidth() / 2 - width / 2;
        y = Handler.get().getHeight() / 2 - height / 2;

        bounds = new Rectangle(x, y, width, height);
        if (panels == null) {
            this.panels = new ArrayList<>();
        }

        uiManager = new UIManager();
    }

    public BountyBoardUI(Zone zone) {
        this(zone, null);
    }

    public void tick() {
        uiManager.tick();

        for (BountyPanel panel : panels) {
            if (panel.isHovering() && Handler.get().getMouseManager().isLeftPressed()) {
                selectedPanel = panel;
            }
        }

    }

    public void render(Graphics2D g) {
        g.drawImage(Assets.uiWindow, x, y, width, height, null);
        Text.drawString(g, "Bounty Board (" + zone.getName() + ")", x + width / 2, y + 20, true, Color.YELLOW, Assets.font20);

        uiManager.render(g);

        if (selectedPanel != null) {
            g.setColor(selectedColor);
            g.fillRoundRect(selectedPanel.getBounds().x, selectedPanel.getBounds().y, selectedPanel.getBounds().width, selectedPanel.getBounds().height, 18, 18);
        }
    }

    public void addPanel(String task, String description) {
        BountyPanel panel = new BountyPanel(task, description, x + 8, y + 40 + currentYPanel, PANEL_WIDTH, PANEL_HEIGHT);
        panels.add(panel);
        currentYPanel += PANEL_HEIGHT;
        uiManager.addObject(panel);
    }

    public void removePanel() {

    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Zone getZone() {
        return zone;
    }
}
