package dev.ipsych0.myrinnia.character;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class CharacterUI implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2534979108806910921L;
    private int x, y, width, height;
    public static boolean isOpen = false;
    private int baseStatPoints;
    private int elementalStatPoints;
    private UIImageButton meleeUp, rangedUp, magicUp, fireUp, airUp, waterUp, earthUp;
    public static boolean hasBeenPressed = false;
    private Rectangle bounds;
    public static boolean escapePressed = false;
    private UIImageButton exit;
    private UIManager uiManager, baseStatManager, elementalStatManager;

    public CharacterUI() {
        width = 272;
        height = 320;
        x = Handler.get().getWidth() / 2 - width / 2;
        y = Handler.get().getHeight() / 2 - height / 2;
        meleeUp = new UIImageButton(x + 112, y + 136, 16, 16, Assets.genericButton);
        rangedUp = new UIImageButton(x + 112, y + 152, 16, 16, Assets.genericButton);
        magicUp = new UIImageButton(x + 112, y + 168, 16, 16, Assets.genericButton);

        fireUp = new UIImageButton(x + 112, y + 217, 16, 16, Assets.genericButton);
        airUp = new UIImageButton(x + 112, y + 233, 16, 16, Assets.genericButton);
        waterUp = new UIImageButton(x + 112, y + 249, 16, 16, Assets.genericButton);
        earthUp = new UIImageButton(x + 112, y + 265, 16, 16, Assets.genericButton);

        bounds = new Rectangle(x, y, width, height);

        exit = new UIImageButton(x + (width / 2) / 2, y + height - 24, width / 2, 16, Assets.genericButton);

        uiManager = new UIManager();
        baseStatManager = new UIManager();
        elementalStatManager = new UIManager();

        baseStatManager.addObject(meleeUp);
        baseStatManager.addObject(rangedUp);
        baseStatManager.addObject(magicUp);

        elementalStatManager.addObject(fireUp);
        elementalStatManager.addObject(airUp);
        elementalStatManager.addObject(waterUp);
        elementalStatManager.addObject(earthUp);

        uiManager.addObject(exit);

        addBaseStatPoints();
        addElementalStatPoints();

    }

    public void tick() {
        if (isOpen) {
            Rectangle mouse = Handler.get().getMouse();

            if (Handler.get().getKeyManager().escape && escapePressed) {
                isOpen = false;
                hasBeenPressed = false;
                return;
            }

            if (exit.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                MouseManager.justClosedUI = true;
                hasBeenPressed = false;
                isOpen = false;
                return;
            }

            uiManager.tick();

            // If Base stats are upped
            if (baseStatPoints >= 1) {
                baseStatManager.tick();
                if (meleeUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    CharacterStats.Melee.addLevel();
                    baseStatPoints -= 1;
                    hasBeenPressed = false;
                } else if (rangedUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    CharacterStats.Ranged.addLevel();
                    baseStatPoints -= 1;
                    hasBeenPressed = false;
                } else if (magicUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    CharacterStats.Magic.addLevel();
                    baseStatPoints -= 1;
                    hasBeenPressed = false;
                }
            }

            // If Elemental stats are upped
            if (elementalStatPoints >= 1) {
                elementalStatManager.tick();
                if (fireUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    CharacterStats.Fire.addLevel();
                    elementalStatPoints -= 1;
                    hasBeenPressed = false;
                } else if (airUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    CharacterStats.Air.addLevel();
                    elementalStatPoints -= 1;
                    hasBeenPressed = false;
                } else if (waterUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    CharacterStats.Water.addLevel();
                    elementalStatPoints -= 1;
                    hasBeenPressed = false;
                } else if (earthUp.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                    CharacterStats.Earth.addLevel();
                    elementalStatPoints -= 1;
                    hasBeenPressed = false;
                }
            }

        }
    }

    public void render(Graphics2D g) {
        if (isOpen) {
            g.drawImage(Assets.uiWindow, x, y, width, height, null);

            Text.drawString(g, "Character stats", x + width / 2, y + 21, true, Color.YELLOW, Assets.font20);

            Text.drawString(g, "Combat lvl: " + Handler.get().getSkillsUI().getSkill(SkillsList.COMBAT).getLevel(), x + 16, y + 64, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "HP: " + Handler.get().getPlayer().getHealth() + "/" + Handler.get().getPlayer().getMaxHealth(), x + 16, y + 80, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "XP: " + Handler.get().getSkillsUI().getSkill(SkillsList.COMBAT).getExperience() + "/" + Handler.get().getSkillsUI().getSkill(SkillsList.COMBAT).getNextLevelXp(), x + 16, y + 96, false, Color.YELLOW, Assets.font14);

            // If we have points available, draw the
            if (baseStatPoints >= 1) {
                baseStatManager.render(g);
                Text.drawString(g, "+", x + 120, y + 144, true, Color.GREEN, Assets.font14);
                Text.drawString(g, "+", x + 120, y + 160, true, Color.GREEN, Assets.font14);
                Text.drawString(g, "+", x + 120, y + 176, true, Color.GREEN, Assets.font14);
            }

            Text.drawString(g, "Combat stats:", x + 16, y + 128, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "(" + baseStatPoints + " points)", x + 119, y + 128, false, Color.YELLOW, Assets.font14);

            Text.drawString(g, "Melee:", x + 16, y + 148, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, String.valueOf(CharacterStats.Melee.getLevel()), x + 82, y + 148, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Ranged:", x + 16, y + 164, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, String.valueOf(CharacterStats.Ranged.getLevel()), x + 82, y + 164, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Magic:", x + 16, y + 180, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, String.valueOf(CharacterStats.Magic.getLevel()), x + 82, y + 180, false, Color.YELLOW, Assets.font14);

            if (elementalStatPoints >= 1) {
                elementalStatManager.render(g);
                Text.drawString(g, "+", x + 120, y + 224, true, Color.GREEN, Assets.font14);
                Text.drawString(g, "+", x + 120, y + 240, true, Color.GREEN, Assets.font14);
                Text.drawString(g, "+", x + 120, y + 256, true, Color.GREEN, Assets.font14);
                Text.drawString(g, "+", x + 120, y + 272, true, Color.GREEN, Assets.font14);
            }

            Text.drawString(g, "Elemental stats: ", x + 16, y + 208, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "(" + elementalStatPoints + " points)", x + 136, y + 208, false, Color.YELLOW, Assets.font14);

            Text.drawString(g, "Fire:", x + 16, y + 230, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, String.valueOf(CharacterStats.Fire.getLevel()), x + 82, y + 230, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Air:", x + 16, y + 246, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, String.valueOf(CharacterStats.Air.getLevel()), x + 82, y + 246, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Water:", x + 16, y + 262, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, String.valueOf(CharacterStats.Water.getLevel()), x + 82, y + 262, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Earth:", x + 16, y + 278, false, Color.YELLOW, Assets.font14);
            Text.drawString(g, String.valueOf(CharacterStats.Earth.getLevel()), x + 82, y + 278, false, Color.YELLOW, Assets.font14);


            uiManager.render(g);
            Text.drawString(g, "Exit", x + (width / 2), y + height - 16, true, Color.YELLOW, Assets.font14);
        }
    }

    public int getBaseStatPoints() {
        return baseStatPoints;
    }

    public void addBaseStatPoints() {
        this.baseStatPoints++;
    }

    public void addBaseStatPoints(int points) {
        this.baseStatPoints += points;
    }

    public int getElementalStatPoints() {
        return elementalStatPoints;
    }

    public void addElementalStatPoints() {
        this.elementalStatPoints++;
    }

    public void addElementalStatPoints(int points) {
        this.elementalStatPoints += points;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

}
