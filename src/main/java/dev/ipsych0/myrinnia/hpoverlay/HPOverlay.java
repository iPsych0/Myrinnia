package dev.ipsych0.myrinnia.hpoverlay;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.ui.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.character.CharacterUI;
import dev.ipsych0.myrinnia.chatwindow.Filter;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.QuestHelpUI;
import dev.ipsych0.myrinnia.quests.QuestUI;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.skills.ui.SkillsOverviewUI;
import dev.ipsych0.myrinnia.skills.ui.SkillsUI;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class HPOverlay implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 4442630766249828531L;
    private Rectangle bounds;
    private Rectangle combatBar, hpBar, xpBar;
    private UIImageButton skillsButton, characterButton, abilitiesButton, questsButton, mapButton;
    public static boolean hasBeenPressed = false;
    private UIManager uiManager;
    public static boolean isOpen = true;

    public HPOverlay() {

        uiManager = new UIManager();


        // Bars
        hpBar = new Rectangle(56, 38, 144, 24);
        xpBar = new Rectangle(56, 64, 144, 24);

        bounds = new Rectangle(8, 8, 208, 128);

        combatBar = new Rectangle(8, 10, 208, 32);

        // Buttons
        questsButton = new UIImageButton(bounds.x + bounds.width / 2 - 80, bounds.y + bounds.height - 40, 32, 32, Assets.genericButton);
        skillsButton = new UIImageButton(bounds.x + bounds.width / 2 - 48, bounds.y + bounds.height - 40, 32, 32, Assets.genericButton);
        characterButton = new UIImageButton(bounds.x + bounds.width / 2 - 16, bounds.y + bounds.height - 40, 32, 32, Assets.genericButton);
        abilitiesButton = new UIImageButton(bounds.x + bounds.width / 2 + 16, bounds.y + bounds.height - 40, 32, 32, Assets.genericButton);
        mapButton = new UIImageButton(bounds.x + bounds.width / 2 + 48, bounds.y + bounds.height - 40, 32, 32, Assets.genericButton);

        uiManager.addObject(questsButton);
        uiManager.addObject(skillsButton);
        uiManager.addObject(characterButton);
        uiManager.addObject(abilitiesButton);
        uiManager.addObject(mapButton);

    }

    public void tick() {
        if (isOpen) {
            uiManager.tick();
        }
    }

    public void render(Graphics2D g) {
        if (isOpen) {

            Composite current = g.getComposite();
            if (Handler.get().getGameCamera().isAtLeftBound() && Handler.get().getGameCamera().isAtTopBound()) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }

            Rectangle mouse = Handler.get().getMouse();

            // Draw the bars
            g.drawImage(Assets.uiWindow, bounds.x, bounds.y, bounds.width, bounds.height, null);

            g.drawImage(Assets.genericButton[1], hpBar.x, hpBar.y, hpBar.width, hpBar.height, null);
            g.drawImage(Assets.genericButton[1], xpBar.x, xpBar.y, xpBar.width, xpBar.height, null);

            // Draw the HP/EXP pre-fix to bars
            g.drawImage(Assets.genericButton[1], hpBar.x - 32, hpBar.y, 32, 24, null);
            g.drawImage(Assets.genericButton[1], xpBar.x - 32, xpBar.y, 32, 24, null);

            uiManager.render(g);

            // Quests UI button
            if (questsButton.contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    Handler.get().playEffect("ui/ui_button_click.ogg");
                    hasBeenPressed = false;
                    SkillsUI.isOpen = false;
                    SkillsOverviewUI.isOpen = false;
                    CharacterUI.isOpen = false;
                    QuestUI.isOpen = !QuestUI.isOpen;
                    if (QuestUI.renderingQuests)
                        QuestUI.renderingQuests = false;
                }
            }

            // Skills UI button
            if (skillsButton.contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    Handler.get().playEffect("ui/ui_button_click.ogg");
                    hasBeenPressed = false;
                    CharacterUI.isOpen = false;
                    QuestUI.isOpen = false;
                    QuestHelpUI.isOpen = false;
                    QuestUI.renderingQuests = false;
                    SkillsUI.isOpen = !SkillsUI.isOpen;
                }
            }

            // Character UI button
            if (characterButton.contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    Handler.get().playEffect("ui/ui_button_click.ogg");
                    hasBeenPressed = false;
                    SkillsUI.isOpen = false;
                    SkillsOverviewUI.isOpen = false;
                    QuestUI.isOpen = false;
                    QuestHelpUI.isOpen = false;
                    QuestUI.renderingQuests = false;
                    CharacterUI.isOpen = !CharacterUI.isOpen;
                }
            }

            // Abilities UI button
            if (abilitiesButton.contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    Handler.get().playEffect("ui/ui_button_click.ogg");
                    hasBeenPressed = false;
                    if (ShopWindow.lastOpenedWindow != null) {
                        ShopWindow.lastOpenedWindow.exit();
                    }
                    if (AbilityShopWindow.lastOpenedWindow != null) {
                        AbilityShopWindow.lastOpenedWindow.exit();
                    }
                    Handler.get().getBankUI().exit();
                    Handler.get().getCraftingUI().exit();
                    AbilityOverviewUI.isOpen = !AbilityOverviewUI.isOpen;
                }
            }

            // Map UI button
            if (mapButton.contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    hasBeenPressed = false;
                    if (ShopWindow.lastOpenedWindow != null) {
                        ShopWindow.lastOpenedWindow.exit();
                    }
                    if (AbilityShopWindow.lastOpenedWindow != null) {
                        AbilityShopWindow.lastOpenedWindow.exit();
                    }
                    Handler.get().getBankUI().exit();
                    Handler.get().getCraftingUI().exit();
                    // TODO: OPEN THE MAP HERE ONCE IT'S CREATED
                    Handler.get().playEffect("ui/ui_button_click.ogg");
                    Handler.get().sendMsg("Map coming soon!");
                }
            }


            // UI button icons
            g.drawImage(Assets.questsIcon, questsButton.x, questsButton.y, 32, 32, null);
            g.drawImage(Assets.craftingIcon, skillsButton.x, skillsButton.y, 32, 32, null);
            g.drawImage(Assets.characterIcon, characterButton.x, characterButton.y, 32, 32, null);
            g.drawImage(Assets.abilitiesIcon, abilitiesButton.x, abilitiesButton.y, 32, 32, null);
            g.drawImage(Assets.mapIcon, mapButton.x, mapButton.y, 32, 32, null);

            Text.drawString(g, "HP", hpBar.x - 16, hpBar.y + hpBar.height / 2, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "XP", xpBar.x - 16, xpBar.y + xpBar.height / 2, true, Color.YELLOW, Assets.font14);

            // HP Bar
            g.setColor(Colors.hpColorRed);
            g.fillRect(hpBar.x + 2, hpBar.y + 1, hpBar.width - 4, hpBar.height - 3);
            g.setColor(Colors.hpColorRedOutline);
            g.drawRect(hpBar.x + 2, hpBar.y + 1, hpBar.width - 4, hpBar.height - 3);

            g.setColor(Colors.hpColorGreen);
            if (Handler.get().getPlayer().getHealth() >= Handler.get().getPlayer().getMaxHealth()) {
                g.fillRect(hpBar.x + 2, hpBar.y + 1, hpBar.width - 4, hpBar.height - 3);

                g.setColor(Colors.hpColorGreenOutline);
                g.drawRect(hpBar.x + 2, hpBar.y + 1, hpBar.width - 4, hpBar.height - 3);
            } else {
                g.fillRect(hpBar.x + 2, hpBar.y + 1, (int) (hpBar.width * (double) Handler.get().getPlayer().getHealth() /
                        (double) Handler.get().getPlayer().getMaxHealth()) - 4, hpBar.height - 3);

                g.setColor(Colors.hpColorGreenOutline);
                g.drawRect(hpBar.x + 2, hpBar.y + 1, (int) (hpBar.width * (double) Handler.get().getPlayer().getHealth() /
                        (double) Handler.get().getPlayer().getMaxHealth()) - 4, hpBar.height - 3);
            }

            // XP bar
            g.setColor(Colors.xpColor);
            g.fillRect(xpBar.x + 2, xpBar.y + 1, xpBar.width * Handler.get().getSkill(SkillsList.COMBAT).getExperience() / Handler.get().getSkill(SkillsList.COMBAT).getNextLevelXp() - 2, xpBar.height - 4);
            g.setColor(Colors.xpColorOutline);
            g.drawRect(xpBar.x + 2, xpBar.y + 1, xpBar.width * Handler.get().getSkill(SkillsList.COMBAT).getExperience() / Handler.get().getSkill(SkillsList.COMBAT).getNextLevelXp() - 2, xpBar.height - 4);

            Text.drawString(g, "Combat level: " + Handler.get().getSkillsUI().getSkill(SkillsList.COMBAT).getLevel(),
                    combatBar.x + combatBar.width / 2, combatBar.y + combatBar.height / 2, true, Color.YELLOW, Assets.font14);

            Text.drawString(g, Handler.get().getPlayer().getHealth() + "/" +
                    Handler.get().getPlayer().getMaxHealth(), hpBar.x + hpBar.width / 2, hpBar.y + hpBar.height / 2, true, Color.YELLOW, Assets.font14);

            Text.drawString(g, Handler.get().getSkillsUI().getSkill(SkillsList.COMBAT).getExperience() + "/" + Handler.get().getSkillsUI().getSkill(SkillsList.COMBAT).getNextLevelXp(),
                    xpBar.x + xpBar.width / 2, xpBar.y + xpBar.height / 2, true, Color.YELLOW, Assets.font14);

            if (questsButton.contains(mouse)) {
                g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 96, 32, null);
                Text.drawString(g, "Quest Journal (Q)", mouse.x + 56, mouse.y + 24, true, Color.YELLOW, Assets.font14);
            } else if (skillsButton.contains(mouse)) {
                g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 96, 32, null);
                Text.drawString(g, "Skills (L)", mouse.x + 56, mouse.y + 24, true, Color.YELLOW, Assets.font14);
            } else if (characterButton.contains(mouse)) {
                g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 96, 32, null);
                Text.drawString(g, "Character (K)", mouse.x + 56, mouse.y + 24, true, Color.YELLOW, Assets.font14);
            } else if (abilitiesButton.contains(mouse)) {
                g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 96, 32, null);
                Text.drawString(g, "Abilities (B)", mouse.x + 56, mouse.y + 24, true, Color.YELLOW, Assets.font14);
            } else if (mapButton.contains(mouse)) {
                g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 96, 32, null);
                Text.drawString(g, "Map (M)", mouse.x + 56, mouse.y + 24, true, Color.YELLOW, Assets.font14);
            }

            if (Handler.get().getChatWindow().getFilters().contains(Filter.DRAWFPS)) {
                Text.drawString(g, "FPS: " + Handler.get().getGame().getFramesPerSecond(), 12, 160, false, Color.YELLOW, Assets.font14);
            }

            g.setComposite(current);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

}
