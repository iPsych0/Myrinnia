package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.hpoverlay.HPOverlay;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.skills.*;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class SkillsUI implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7078989753242847318L;
    private int x = 8;
    private int y = 180;
    private int width = 272;
    private int height = 320;
    public static boolean isOpen = false;
    public static boolean hasBeenPressed = false;
    private HashMap<SkillsList, Skill> skills;
    private ArrayList<Skill> skillsList;
    private UIImageButton crafting, fishing, mining, woodcutting, bountyHunter;
    private SkillsOverviewUI overviewUI;
    public static boolean escapePressed = false;
    private Rectangle bounds;
    private UIImageButton exit;
    private UIManager uiManager;

    public SkillsUI() {
        skillsList = new ArrayList<>();
        skills = new HashMap<>();

        skillsList.add(new CraftingSkill());
        skillsList.add(new WoodcuttingSkill());
        skillsList.add(new FishingSkill());
        skillsList.add(new MiningSkill());
        skillsList.add(new CombatSkill());
        skillsList.add(new BountyHunterSkill());


        // Sort the Skills
        skillsList.sort(Comparator.comparing(o -> o.getClass().getSimpleName()));

        // Sort the Enums
        List<SkillsList> skillsEnum = Arrays.asList(SkillsList.values());
        skillsEnum.sort((o1, o2) -> o1.toString().compareTo(o2.toString()));

        // Map the skills to the enums
        for (int i = 0; i < skillsList.size(); i++) {
            skills.put(skillsEnum.get(i), skillsList.get(i));
        }

        bountyHunter = new UIImageButton(x + 16, y + 40, width - 32, 32, Assets.genericButton);
        crafting = new UIImageButton(x + 16, y + 72, width - 32, 32, Assets.genericButton);
        fishing = new UIImageButton(x + 16, y + 104, width - 32, 32, Assets.genericButton);
        mining = new UIImageButton(x + 16, y + 136, width - 32, 32, Assets.genericButton);
        woodcutting = new UIImageButton(x + 16, y + 168, width - 32, 32, Assets.genericButton);

        bounds = new Rectangle(x, y, width, height);

        exit = new UIImageButton(x + (width / 2) / 2, y + height - 24, width / 2, 16, Assets.genericButton);

        uiManager = new UIManager();
        overviewUI = new SkillsOverviewUI();

        uiManager.addObject(bountyHunter);
        uiManager.addObject(crafting);
        uiManager.addObject(fishing);
        uiManager.addObject(mining);
        uiManager.addObject(woodcutting);
        uiManager.addObject(exit);
    }

    public void tick() {
        if (isOpen) {
            Rectangle mouse = Handler.get().getMouse();

            uiManager.tick();

            if (bountyHunter.contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    hasBeenPressed = false;
                    SkillsOverviewUI.isOpen = true;
                    overviewUI.setSelectedSkill(getSkill(SkillsList.BOUNTYHUNTER));
                    overviewUI.setSelectedCategory(SkillCategory.BountyTargets);
                    overviewUI.getScrollBar().setIndex(0);
                    overviewUI.getScrollBar().setListSize(getSkill(SkillsList.BOUNTYHUNTER).getListByCategory(SkillCategory.BountyTargets).size());
                    overviewUI.getScrollBar().setScrollMaximum(getSkill(SkillsList.BOUNTYHUNTER).getListByCategory(SkillCategory.BountyTargets).size());
                    overviewUI.getCategories().clear();
                    for (int i = 0; i < overviewUI.getSelectedSkill().getCategories().size(); i++) {
                        overviewUI.getCategories().add(new CategoryButton(overviewUI.getSelectedSkill().getCategories().get(i),
                                overviewUI.x + 16, overviewUI.y + 40 + (i * 32), 80, 32));
                    }
                }
            } else if (crafting.contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    hasBeenPressed = false;
                    SkillsOverviewUI.isOpen = true;
                    overviewUI.setSelectedSkill(getSkill(SkillsList.CRAFTING));
                    overviewUI.setSelectedCategory(SkillCategory.Weapons);
                    overviewUI.getScrollBar().setIndex(0);
                    overviewUI.getScrollBar().setListSize(Handler.get().getCraftingUI().getCraftingManager().getListByCategory(SkillCategory.Weapons).size());
                    overviewUI.getScrollBar().setScrollMaximum(Handler.get().getCraftingUI().getCraftingManager().getListByCategory(SkillCategory.Weapons).size());
                    overviewUI.getCategories().clear();
                    for (int i = 0; i < overviewUI.getSelectedSkill().getCategories().size(); i++) {
                        overviewUI.getCategories().add(new CategoryButton(overviewUI.getSelectedSkill().getCategories().get(i),
                                overviewUI.x + 16, overviewUI.y + 40 + (i * 32), 80, 32));
                    }
                }
            } else if (fishing.contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    hasBeenPressed = false;
                    SkillsOverviewUI.isOpen = true;
                    overviewUI.setSelectedSkill(getSkill(SkillsList.FISHING));
                    overviewUI.setSelectedCategory(SkillCategory.Fish);
                    overviewUI.getScrollBar().setIndex(0);
                    overviewUI.getScrollBar().setListSize(getSkill(SkillsList.FISHING).getListByCategory(SkillCategory.Fish).size());
                    overviewUI.getScrollBar().setScrollMaximum(getSkill(SkillsList.FISHING).getListByCategory(SkillCategory.Fish).size());
                    overviewUI.getCategories().clear();
                    for (int i = 0; i < overviewUI.getSelectedSkill().getCategories().size(); i++) {
                        overviewUI.getCategories().add(new CategoryButton(overviewUI.getSelectedSkill().getCategories().get(i),
                                overviewUI.x + 16, overviewUI.y + 40 + (i * 32), 80, 32));
                    }
                }
            } else if (mining.contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    hasBeenPressed = false;
                    SkillsOverviewUI.isOpen = true;
                    overviewUI.setSelectedSkill(getSkill(SkillsList.MINING));
                    overviewUI.setSelectedCategory(SkillCategory.Ores);
                    overviewUI.getScrollBar().setIndex(0);
                    overviewUI.getScrollBar().setListSize(getSkill(SkillsList.MINING).getListByCategory(SkillCategory.Ores).size());
                    overviewUI.getScrollBar().setScrollMaximum(getSkill(SkillsList.MINING).getListByCategory(SkillCategory.Ores).size());
                    overviewUI.getCategories().clear();
                    for (int i = 0; i < overviewUI.getSelectedSkill().getCategories().size(); i++) {
                        overviewUI.getCategories().add(new CategoryButton(overviewUI.getSelectedSkill().getCategories().get(i),
                                overviewUI.x + 16, overviewUI.y + 40 + (i * 32), 80, 32));
                    }
                }
            } else if (woodcutting.contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    hasBeenPressed = false;
                    SkillsOverviewUI.isOpen = true;
                    overviewUI.setSelectedSkill(getSkill(SkillsList.WOODCUTTING));
                    overviewUI.setSelectedCategory(SkillCategory.Trees);
                    overviewUI.getScrollBar().setIndex(0);
                    overviewUI.getScrollBar().setListSize(getSkill(SkillsList.WOODCUTTING).getListByCategory(SkillCategory.Trees).size());
                    overviewUI.getScrollBar().setScrollMaximum(getSkill(SkillsList.WOODCUTTING).getListByCategory(SkillCategory.Trees).size());
                    overviewUI.getCategories().clear();
                    for (int i = 0; i < overviewUI.getSelectedSkill().getCategories().size(); i++) {
                        overviewUI.getCategories().add(new CategoryButton(overviewUI.getSelectedSkill().getCategories().get(i),
                                overviewUI.x + 16, overviewUI.y + 40 + (i * 32), 80, 32));
                    }
                }
            }

            if (Handler.get().getKeyManager().escape && SkillsOverviewUI.isOpen && escapePressed) {
                SkillsOverviewUI.isOpen = false;
                escapePressed = false;
                return;
            } else if (Handler.get().getKeyManager().escape && !SkillsOverviewUI.isOpen && escapePressed) {
                SkillsUI.isOpen = false;
                escapePressed = false;
                return;
            }

            overviewUI.tick();
        }
    }

    public void render(Graphics2D g) {
        if (isOpen) {
            g.drawImage(Assets.uiWindow, x, y, width, height, null);

            uiManager.render(g);

            overviewUI.render(g);

            Rectangle mouse = Handler.get().getMouse();

            Text.drawString(g, "Skills", x + width / 2, y + 21, true, Color.YELLOW, Assets.font20);

            drawXpProgress(g, bountyHunter, SkillsList.BOUNTYHUNTER);
            drawXpProgress(g, crafting, SkillsList.CRAFTING);
            drawXpProgress(g, fishing, SkillsList.FISHING);
            drawXpProgress(g, mining, SkillsList.MINING);
            drawXpProgress(g, woodcutting, SkillsList.WOODCUTTING);

            Text.drawString(g, "Bounty Hunter lvl: " + getSkill(SkillsList.BOUNTYHUNTER).getLevel(), x + width / 2, y + 56, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Crafting lvl: " + getSkill(SkillsList.CRAFTING).getLevel(), x + width / 2, y + 88, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Fishing lvl: " + getSkill(SkillsList.FISHING).getLevel(), x + width / 2, y + 120, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Mining lvl: " + getSkill(SkillsList.MINING).getLevel(), x + width / 2, y + 152, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Woodcutting lvl: " + getSkill(SkillsList.WOODCUTTING).getLevel(), x + width / 2, y + 184, true, Color.YELLOW, Assets.font14);

            if (bountyHunter.contains(mouse)) {
                g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 112, 32, null);
                Text.drawString(g, String.valueOf(getSkill(SkillsList.BOUNTYHUNTER).getExperience()) + "/" + getSkill(SkillsList.BOUNTYHUNTER).getNextLevelXp() + " XP", mouse.x + 16, mouse.y + 30, false, Color.YELLOW, Assets.font14);
            }
            if (crafting.contains(mouse)) {
                g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 112, 32, null);
                Text.drawString(g, String.valueOf(getSkill(SkillsList.CRAFTING).getExperience()) + "/" + getSkill(SkillsList.CRAFTING).getNextLevelXp() + " XP", mouse.x + 16, mouse.y + 30, false, Color.YELLOW, Assets.font14);
            }
            if (fishing.contains(mouse)) {
                g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 112, 32, null);
                Text.drawString(g, String.valueOf(getSkill(SkillsList.FISHING).getExperience()) + "/" + getSkill(SkillsList.FISHING).getNextLevelXp() + " XP", mouse.x + 16, mouse.y + 30, false, Color.YELLOW, Assets.font14);
            }
            if (mining.contains(mouse)) {
                g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 112, 32, null);
                Text.drawString(g, String.valueOf(getSkill(SkillsList.MINING).getExperience()) + "/" + getSkill(SkillsList.MINING).getNextLevelXp() + " XP", mouse.x + 16, mouse.y + 30, false, Color.YELLOW, Assets.font14);
            }
            if (woodcutting.contains(mouse)) {
                g.drawImage(Assets.genericButton[1], mouse.x + 8, mouse.y + 8, 112, 32, null);
                Text.drawString(g, String.valueOf(getSkill(SkillsList.WOODCUTTING).getExperience()) + "/" + getSkill(SkillsList.WOODCUTTING).getNextLevelXp() + " XP", mouse.x + 16, mouse.y + 30, false, Color.YELLOW, Assets.font14);
            }

            if (exit.contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    MouseManager.justClosedUI = true;
                    hasBeenPressed = false;
                    SkillsOverviewUI.isOpen = false;
                    SkillsUI.isOpen = false;
                }
            }
            Text.drawString(g, "Exit", x + (width / 2), y + height - 16, true, Color.YELLOW, Assets.font14);
        }
    }

    private void drawXpProgress(Graphics2D g, Rectangle skillRect, SkillsList skill) {
        g.setColor(HPOverlay.xpColor);
        g.fillRect(skillRect.x + 2, skillRect.y + 1, skillRect.width * Handler.get().getSkill(skill).getExperience() / Handler.get().getSkill(skill).getNextLevelXp() - 2, skillRect.height - 4);
        g.setColor(HPOverlay.xpColorOutline);
        g.drawRect(skillRect.x + 2, skillRect.y + 1, skillRect.width * Handler.get().getSkill(skill).getExperience() / Handler.get().getSkill(skill).getNextLevelXp() - 2, skillRect.height - 4);

        // Icon
        g.drawImage(Handler.get().getSkill(skill).getImg(), skillRect.x + 4, skillRect.y, null);
    }

    public Skill getSkill(SkillsList skill) {
        return skills.get(skill);
    }

    public SkillsOverviewUI getOverviewUI() {
        return overviewUI;
    }

    public void setOverviewUI(SkillsOverviewUI overviewUI) {
        this.overviewUI = overviewUI;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
