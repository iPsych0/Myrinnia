package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.AzureCrab;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.Use;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;
import dev.ipsych0.myrinnia.ui.DialogueBox;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BountyBoardUI implements Serializable {

    private static final long serialVersionUID = 7164283265721220038L;
    private Zone zone;
    private int x, y, width, height;
    private Rectangle bounds;
    private List<Bounty> panels;
    private UIManager uiManager;
    private int currentYPanel = 0;
    private static final int PANEL_WIDTH = 400, PANEL_HEIGHT = 64;
    private static Color selectedColor = new Color(0, 255, 255, 62);
    private static Color completedColor = new Color(44, 255, 12, 62);
    private static Color acceptedColor = new Color(196, 204, 17, 62);
    private Bounty selectedPanel;
    public static boolean isOpen;
    public static boolean escapePressed;
    private UIImageButton exitButton;
    private UIImageButton acceptButton;
    private DialogueBox dialogueBox;

    public BountyBoardUI(Zone zone, List<Bounty> panels) {
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
        acceptButton = new UIImageButton(x + width / 2 - 48, y + height - 48, 96, 32, Assets.genericButton);

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
        boolean hovering = false;
        for (Bounty panel : panels) {
            if (panel.isHovering()) {
                hovering = true;
                if (Handler.get().getMouseManager().isLeftPressed()) {
                    selectedPanel = panel;
                }
            }
        }

        // Closing the window
        if (Handler.get().getKeyManager().escape && escapePressed ||
                exitButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() || Player.isMoving) {
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

        // If we haven't clicked on a panel, but we have clicked in the UI, deselect
        if (!hovering && bounds.contains(mouse) && !acceptButton.contains(mouse) && !dialogueBox.isOpen() && Handler.get().getMouseManager().isLeftPressed()) {
            selectedPanel = null;
        }

    }

    private void confirmBounty(Bounty bounty) {
        if (dialogueBox.isMakingChoice() && dialogueBox.getPressedButton() != null) {
            if ("Accept".equalsIgnoreCase(dialogueBox.getPressedButton().getButtonParam()[0])) {
                if (Handler.get().playerHasSkillLevel(SkillsList.BOUNTYHUNTER, bounty.getLevelRequirement())) {
                    if (Handler.get().questInProgress(QuestList.GettingStarted) && !bounty.isAccepted()) {
                        Handler.get().getQuest(QuestList.GettingStarted).nextStep();
                        Handler.get().addTip(new TutorialTip("Right-click on items in your inventory to 'use' them. Click on the contract to open it."));
                        Handler.get().getWorldHandler().getWorldsMap().get(Zone.SunsetCove).getEntityManager().addEntity(new AzureCrab(672, 416, 64, 64, "King Azure Crab", 3, null, null, null, null, Creature.Direction.DOWN));
                    }
                    // Only get the bounty contract if we haven't accepted it yet or if we lost the contract (death/dropping)
                    if (!bounty.isAccepted() && !bounty.isCompleted() || !Handler.get().playerHasItem(Item.bountyContract, 1) && bounty.isAccepted() && !bounty.isCompleted()) {
                        BountyManager.get().addBounty(bounty);
                        bounty.setAccepted(true);
                        Handler.get().giveItemWithUse(Item.bountyContract, 1, 0, (Use & Serializable) i -> {
                            BountyContractUI.open(bounty);
                        });
                    } else if (bounty.isCompleted()) {
                        Handler.get().sendMsg("You have already completed this bounty.");
                    } else {
                        Handler.get().sendMsg("You have already accepted this bounty.");
                    }
                } else {
                    Handler.get().sendMsg("You need to be level " + bounty.getLevelRequirement() + " to accept this bounty.");
                }

                selectedPanel = null;
                Handler.get().playEffect("ui/ui_button_click.ogg");
                dialogueBox.close();
            } else if ("Leave".equalsIgnoreCase(dialogueBox.getPressedButton().getButtonParam()[0])) {
                Handler.get().playEffect("ui/ui_button_click.ogg");
                dialogueBox.close();
                selectedPanel = null;
            }
        }
    }

    public void close() {
        if (Handler.get().getMouseManager().isLeftPressed()) {
            MouseManager.justClosedUI = true;
        }
        selectedPanel = null;
        isOpen = false;
        escapePressed = false;
        dialogueBox.close();
    }

    public void render(Graphics2D g) {
        g.drawImage(Assets.uiWindow, x, y, width, height, null);
        Text.drawString(g, "Bounty Board (" + zone.getName() + ")", x + width / 2, y + 20, true, Color.YELLOW, Assets.font20);

        uiManager.render(g);

        for (Bounty panel : panels) {
            if (panel.isCompleted()) {
                g.setColor(completedColor);
                g.fillRoundRect(panel.getBounds().x, panel.getBounds().y, panel.getBounds().width, panel.getBounds().height, 18, 18);
            } else if (panel.isAccepted()) {
                g.setColor(acceptedColor);
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

    public void addPanel(int levelRequirement, String task, String description, String fullDescription) {
        Bounty panel = new Bounty(levelRequirement, task, description, fullDescription, x + 8, y + 40 + currentYPanel, PANEL_WIDTH, PANEL_HEIGHT);
        panels.add(panel);
        currentYPanel += PANEL_HEIGHT;
        uiManager.addObject(panel);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Zone getZone() {
        return zone;
    }

    public List<Bounty> getPanels() {
        return panels;
    }
}
