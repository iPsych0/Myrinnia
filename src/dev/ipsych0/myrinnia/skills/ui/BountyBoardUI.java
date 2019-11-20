package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.DialogueBox;
import dev.ipsych0.myrinnia.ui.UIImageButton;
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
    private static Color completedColor = new Color(44, 255, 12, 62);
    private BountyPanel selectedPanel;
    public static boolean isOpen;
    public static boolean escapePressed;
    private UIImageButton exitButton;
    private UIImageButton acceptButton;
    private DialogueBox dialogueBox;

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

        exitButton = new UIImageButton(x + width - 32, y + 16, 16, 16, Assets.genericButton);
        acceptButton = new UIImageButton(x + width / 4, y + height - 48, 64, 32, Assets.genericButton);

        dialogueBox = new DialogueBox(x + width / 2 - 100, y + height / 2 - 100, 200, 200, new String[]{"Accept", "Leave"}, "Do you want to accept this bounty?", null);

        uiManager = new UIManager();
        uiManager.addObject(exitButton);
        uiManager.addObject(acceptButton);
    }

    public BountyBoardUI(Zone zone) {
        this(zone, null);
    }

    public void tick() {
        uiManager.tick();

        Rectangle mouse = Handler.get().getMouse();

        // Selecting a panel
        for (BountyPanel panel : panels) {
            if (panel.isHovering() && Handler.get().getMouseManager().isLeftPressed()) {
                selectedPanel = panel;
            }
        }

        // Closing the window
        if (Handler.get().getKeyManager().escape && escapePressed ||
                exitButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
            close();
        }

        // Open confirmation dialogue if accept pressed
        if (selectedPanel != null && acceptButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
            if (!dialogueBox.isMakingChoice()) {
                dialogueBox.setMakingChoice(true);
                dialogueBox.setOpen(true);
                DialogueBox.hasBeenPressed = false;
            }
        }

        // If player is making a choice, show the dialoguebox
        if (dialogueBox.isMakingChoice()) {
            dialogueBox.tick();
            confirmBounty(selectedPanel);
        }


    }

    private void confirmBounty(BountyPanel panel) {
        if (dialogueBox.isMakingChoice() && dialogueBox.getPressedButton() != null) {
            if ("Accept".equalsIgnoreCase(dialogueBox.getPressedButton().getButtonParam()[0])) {
                //TODO: ADD BOUNTY SOMEWHERE
                panel.setCompleted(true);
                Handler.get().playEffect("ui/ui_button_click.wav");
                dialogueBox.close();
            } else if ("Leave".equalsIgnoreCase(dialogueBox.getPressedButton().getButtonParam()[0])) {
                Handler.get().playEffect("ui/ui_button_click.wav");
                dialogueBox.close();
            }
        }
    }

    public void close() {
        selectedPanel = null;
        isOpen = false;
        escapePressed = false;
        dialogueBox.close();
    }

    public void render(Graphics2D g) {
        g.drawImage(Assets.uiWindow, x, y, width, height, null);
        Text.drawString(g, "Bounty Board (" + zone.getName() + ")", x + width / 2, y + 20, true, Color.YELLOW, Assets.font20);

        uiManager.render(g);

        for (BountyPanel panel : panels) {
            if (panel.isCompleted()) {
                g.setColor(completedColor);
                g.fillRoundRect(panel.getBounds().x, panel.getBounds().y, panel.getBounds().width, panel.getBounds().height, 18, 18);
            }
        }

        if (selectedPanel != null) {
            g.setColor(selectedColor);
            g.fillRoundRect(selectedPanel.getBounds().x, selectedPanel.getBounds().y, selectedPanel.getBounds().width, selectedPanel.getBounds().height, 18, 18);
        }

        Text.drawString(g, "Accept", acceptButton.x + acceptButton.width / 2, acceptButton.y + acceptButton.height / 2, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "X", exitButton.x + exitButton.width / 2, exitButton.y + exitButton.height / 2, true, Color.YELLOW, Assets.font14);

        // If player is making a choice, show the dialoguebox
        if (dialogueBox.isMakingChoice())
            dialogueBox.render(g);
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

    public List<BountyPanel> getPanels() {
        return panels;
    }
}
