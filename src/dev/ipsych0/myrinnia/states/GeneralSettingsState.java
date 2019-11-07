package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.chatwindow.Filter;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class GeneralSettingsState extends State {

    private Rectangle overlay;
    private UIManager uiManager;
    private UIImageButton infoButton, criticalButton, combatButton, lootButton, skillButton, timeStampButton;
    private UIImageButton drawFpsButton;
    private Set<Integer> activeButtons;

    public GeneralSettingsState() {
        this.uiManager = new UIManager();
        this.activeButtons = new HashSet<>();

        overlay = new Rectangle(Handler.get().getWidth() / 2 - 320, 160, 640, 417);

        // Set all buttons to active
        for (int i = 0; i < Filter.values().length; i++) {
            activeButtons.add(i);
        }

        infoButton = new UIImageButton(overlay.x + 96, overlay.y + 82, 16, 16, Assets.genericButton);
        criticalButton = new UIImageButton(overlay.x + 96, overlay.y + 98, 16, 16, Assets.genericButton);
        combatButton = new UIImageButton(overlay.x + 96, overlay.y + 114, 16, 16, Assets.genericButton);
        lootButton = new UIImageButton(overlay.x + 96, overlay.y + 130, 16, 16, Assets.genericButton);
        skillButton = new UIImageButton(overlay.x + 96, overlay.y + 146, 16, 16, Assets.genericButton);
        timeStampButton = new UIImageButton(overlay.x + 96, overlay.y + 162, 16, 16, Assets.genericButton);
        drawFpsButton = new UIImageButton(overlay.x + 96, timeStampButton.y + 50, 16, 16, Assets.genericButton);

        uiManager.addObject(infoButton);
        uiManager.addObject(criticalButton);
        uiManager.addObject(combatButton);
        uiManager.addObject(lootButton);
        uiManager.addObject(skillButton);
        uiManager.addObject(timeStampButton);
        uiManager.addObject(drawFpsButton);

    }

    @Override
    public void tick() {
        uiManager.tick();

        Rectangle mouse = Handler.get().getMouse();

        handleButtonClicks(mouse);
    }

    @Override
    public void render(Graphics2D g) {
        uiManager.render(g);

        Text.drawString(g, "General settings:", overlay.x + 8, overlay.y + 32, false, Color.YELLOW, Assets.font24);
        Text.drawString(g, "Chat messages:", overlay.x + 8, overlay.y + 64, false, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Info:", overlay.x + 8, overlay.y + 96, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Critical:", overlay.x + 8, overlay.y + 112, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Combat:", overlay.x + 8, overlay.y + 128, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Loot:", overlay.x + 8, overlay.y + 144, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Skill:", overlay.x + 8, overlay.y + 160, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Timestamp:", overlay.x + 8, overlay.y + 176, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Draw FPS:", overlay.x + 8, timeStampButton.y + 64, false, Color.YELLOW, Assets.font14);

        for (Integer i : activeButtons) {
            // FPS Button
            if (i == 6) {
                g.setColor(Color.GREEN);
                Text.drawString(g, "X", overlay.x + 96 + 8, overlay.y + 82 + 8 + i * 16 + 32, true, Color.GREEN, Assets.font14);
            } else {
                g.setColor(Color.GREEN);
                Text.drawString(g, "X", overlay.x + 96 + 8, overlay.y + 82 + 8 + i * 16, true, Color.GREEN, Assets.font14);
            }
        }

    }

    private void handleButtonClicks(Rectangle mouse) {
        Set<Filter> filters = Handler.get().getChatWindow().getFilters();
        if (infoButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                if (filters.contains(Filter.INFO)) {
                    filters.remove(Filter.INFO);
                    activeButtons.remove(0);
                } else {
                    filters.add(Filter.INFO);
                    activeButtons.add(0);
                }
            }
        }
        if (criticalButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                if (filters.contains(Filter.CRITICAL)) {
                    filters.remove(Filter.CRITICAL);
                    activeButtons.remove(1);
                } else {
                    filters.add(Filter.CRITICAL);
                    activeButtons.add(1);
                }
            }
        }
        if (combatButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                if (filters.contains(Filter.COMBAT)) {
                    filters.remove(Filter.COMBAT);
                    activeButtons.remove(2);
                } else {
                    filters.add(Filter.COMBAT);
                    activeButtons.add(2);
                }
            }
        }
        if (lootButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                if (filters.contains(Filter.LOOT)) {
                    filters.remove(Filter.LOOT);
                    activeButtons.remove(3);
                } else {
                    filters.add(Filter.LOOT);
                    activeButtons.add(3);
                }
            }
        }
        if (skillButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                if (filters.contains(Filter.SKILL)) {
                    filters.remove(Filter.SKILL);
                    activeButtons.remove(4);
                } else {
                    filters.add(Filter.SKILL);
                    activeButtons.add(4);
                }
            }
        }
        if (timeStampButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                if (filters.contains(Filter.TIMESTAMP)) {
                    filters.remove(Filter.TIMESTAMP);
                    activeButtons.remove(5);
                } else {
                    filters.add(Filter.TIMESTAMP);
                    activeButtons.add(5);
                }
            }
        }
        if (drawFpsButton.contains(mouse)) {
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                if (filters.contains(Filter.DRAWFPS)) {
                    filters.remove(Filter.DRAWFPS);
                    activeButtons.remove(6);
                } else {
                    filters.add(Filter.DRAWFPS);
                    activeButtons.add(6);
                }
            }
        }
        hasBeenPressed = false;
    }
}
