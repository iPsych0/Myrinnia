package dev.ipsych0.myrinnia.quests;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;
import java.io.Serializable;

public class QuestUI implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1884515804631042331L;
    public static boolean isOpen = false;
    private int x, y, width, height;
    private QuestHelpUI questHelpUI;
    private Quest selectedQuest;
    private Zone selectedZone;
    public static boolean hasBeenPressed;
    private Rectangle bounds;
    public static boolean renderingQuests = false;
    public static boolean escapePressed = false;
    private UIManager zoneManager, questsManager;
    private UIImageButton exitButton, backButton;
    private static boolean zonesInitialized, questsInitialized;

    public QuestUI() {
        this.x = 8;
        this.y = 150;
        this.width = 208;
        this.height = 400;
        bounds = new Rectangle(x, y, width, height);

        questHelpUI = new QuestHelpUI();
        zoneManager = new UIManager();
        questsManager = new UIManager();

    }

    private void initZones() {
        for (int i = 0; i < Handler.get().getQuestManager().getAllQuestLists().size(); i++) {
            UIImageButton slot = new UIImageButton(x + 4, y + 32 + (i * 16), width - 8, 16, Assets.genericButton);
            zoneManager.addObject(slot);
        }

        exitButton = new UIImageButton(x + (width / 2) / 2, y + height - 24, width / 2, 16, Assets.genericButton);
        zoneManager.addObject(exitButton);

        zonesInitialized = true;
    }

    private void initQuests() {
        questsManager.getObjects().clear();
        if (selectedZone != null) {
            for (int i = 0; i < Handler.get().getQuestManager().getZoneMap().get(selectedZone).size(); i++) {
                UIImageButton slot = new UIImageButton(x + 4, y + 32 + (i * 16), width - 8, 16, Assets.genericButton);
                questsManager.addObject(slot);
            }

            backButton = new UIImageButton(x + (width / 2) / 2, y + height - 24, width / 2, 16, Assets.genericButton);
            questsManager.addObject(backButton);
        }

        questsInitialized = true;
    }

    public void tick() {
        if (isOpen) {
            if (!zonesInitialized) {
                initZones();
            }

            // Check hovers on UI buttons
            if (!renderingQuests) {
                zoneManager.tick();
            } else {
                questsManager.tick();
            }

            if (QuestHelpUI.isOpen && Handler.get().getKeyManager().escape && escapePressed) {
                hasBeenPressed = false;
                escapePressed = false;
                QuestHelpUI.isOpen = false;
            } else if (!QuestHelpUI.isOpen && renderingQuests && Handler.get().getKeyManager().escape && escapePressed) {
                hasBeenPressed = false;
                escapePressed = false;
                renderingQuests = false;
            } else if (!QuestHelpUI.isOpen && Handler.get().getKeyManager().escape && escapePressed) {
                hasBeenPressed = false;
                escapePressed = false;
                renderingQuests = false;
                isOpen = false;
            }
        }
    }

    public void render(Graphics2D g) {
        if (isOpen) {
            if (!zonesInitialized) {
                initZones();
            }
            g.drawImage(Assets.uiWindow, x, y, width, height, null);

            Rectangle mouse = Handler.get().getMouse();

            if (!renderingQuests) {
                zoneManager.render(g);
            } else {
                questsManager.render(g);
            }

            if (!renderingQuests) {
                Text.drawString(g, "Zones", x + (width / 2) + 6, y + 19, true, Color.YELLOW, Assets.font14);
                for (int i = 0; i < Handler.get().getQuestManager().getAllQuestLists().size(); i++) {
                    if (zoneManager.getObjects().get(i).contains(mouse)) {
                        if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                            hasBeenPressed = false;
                            for (int j = 0; j < Handler.get().getQuestManager().getAllQuestLists().get(i).size(); j++) {
                                if (selectedZone == Handler.get().getQuestManager().getAllQuestLists().get(i).get(j).getZone()) {
                                    renderingQuests = true;
                                    questsInitialized = false;
                                } else if (selectedZone != Handler.get().getQuestManager().getAllQuestLists().get(i).get(j).getZone()) {
                                    renderingQuests = true;
                                    questsInitialized = false;
                                } else if (selectedZone == null) {
                                    renderingQuests = false;
                                }
                                selectedZone = Handler.get().getQuestManager().getAllQuestLists().get(i).get(j).getZone();
                                if (!questsInitialized) {
                                    initQuests();
                                }
                            }
                        }
                    }
                    Color questStatus = getZoneStatus(i);
                    Text.drawString(g, Handler.get().getQuestManager().getAllQuestLists().get(i).get(0).getZone().getName(), x + (width / 2) + 1, y + 41 + (i * 16), true, questStatus, Assets.font14);
                }

                if (exitButton.contains(mouse)) {
                    if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                        MouseManager.justClosedUI = true;
                        renderingQuests = false;
                        QuestUI.isOpen = false;
                        hasBeenPressed = false;
                    }
                }
                Text.drawString(g, "Exit", x + (width / 2), y + height - 16, true, Color.YELLOW, Assets.font14);

            } else {
                Text.drawString(g, "Quests", x + (width / 2) + 6, y + 19, true, Color.YELLOW, Assets.font14);
                for (int i = 0; i < Handler.get().getQuestManager().getZoneMap().get(selectedZone).size(); i++) {
                    Color color;
                    if (Handler.get().getQuestManager().getZoneMap().get(selectedZone).get(i).getState() == QuestState.NOT_STARTED)
                        color = new Color(255, 0, 0);
                    else if (Handler.get().getQuestManager().getZoneMap().get(selectedZone).get(i).getState() == QuestState.IN_PROGRESS)
                        color = Color.YELLOW;
                    else
                        color = Color.GREEN;

                    if (questsManager.getObjects().get(i).contains(mouse)) {
                        if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                            hasBeenPressed = false;
                            if (selectedQuest == Handler.get().getQuestManager().getZoneMap().get(selectedZone).get(i)) {
                                QuestHelpUI.isOpen = !QuestHelpUI.isOpen;
                            } else if (selectedQuest != Handler.get().getQuestManager().getZoneMap().get(selectedZone).get(i)) {
                                QuestHelpUI.isOpen = true;
                            } else if (selectedQuest == null) {
                                QuestHelpUI.isOpen = true;
                            }
                            selectedQuest = Handler.get().getQuestManager().getZoneMap().get(selectedZone).get(i);
                        }
                    }
                    Text.drawString(g, Handler.get().getQuestManager().getZoneMap().get(selectedZone).get(i).getQuestName(), x + (width / 2) + 1, y + 40 + (i * 16), true, color, Assets.font14);
                }

                if (backButton.contains(mouse)) {
                    if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                        renderingQuests = false;
                        QuestHelpUI.isOpen = false;
                        hasBeenPressed = false;
                    }
                }
                Text.drawString(g, "Back", x + (width / 2), y + height - 16, true, Color.YELLOW, Assets.font14);
            }


            if (QuestHelpUI.isOpen) {
                questHelpUI.render(g, selectedQuest);
            }
        }
    }

    private Color getZoneStatus(int index) {
        Color questStatus;
        int completed = 0;
        int started = 0;
        for (Quest quest : Handler.get().getQuestManager().getAllQuestLists().get(index)) {
            if (quest.getState() == QuestState.COMPLETED) {
                completed++;
            } else if (quest.getState() == QuestState.IN_PROGRESS) {
                started++;
            }
        }

        if (completed == Handler.get().getQuestManager().getAllQuestLists().get(index).size()) {
            questStatus = Color.GREEN;
        } else if (started >= 1 || completed >= 1) {
            questStatus = Color.YELLOW;
        } else {
            questStatus = Color.RED;
        }

        return questStatus;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public QuestHelpUI getQuestHelpUI() {
        return questHelpUI;
    }

    public void setQuestHelpUI(QuestHelpUI questHelpUI) {
        this.questHelpUI = questHelpUI;
    }

}
