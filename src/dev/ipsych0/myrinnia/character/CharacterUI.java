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
    private UIImageButton meleeIcon, rangedIcon, magicIcon, fireIcon, airIcon, waterIcon, earthIcon;
    public static boolean hasBeenPressed = false;
    private Rectangle bounds;
    public static boolean escapePressed = false;
    private UIImageButton exit;
    private UIManager uiManager, baseStatManager, elementalStatManager;

    public CharacterUI() {
        width = 288;
        height = 384;
        x = Handler.get().getWidth() / 2 - width / 2;
        y = Handler.get().getHeight() / 2 - height / 2;

        meleeIcon = new UIImageButton(x + 16, y + 128, 32, 32, Assets.genericButton);
        rangedIcon = new UIImageButton(x + width / 2 - 16, y + 128, 32, 32, Assets.genericButton);
        magicIcon = new UIImageButton(x + width - 48, y + 128, 32, 32, Assets.genericButton);

        fireIcon = new UIImageButton(x + 16, y + 256, 32, 32, Assets.genericButton);
        airIcon = new UIImageButton(x + width / 2 - 52, y + 256, 32, 32, Assets.genericButton);
        waterIcon = new UIImageButton(x + width / 2 + 20 , y + 256, 32, 32, Assets.genericButton);
        earthIcon = new UIImageButton(x + width - 48, y + 256, 32, 32, Assets.genericButton);

        meleeUp = new UIImageButton(meleeIcon.x + 8, meleeIcon.y - 24, 16, 16, Assets.genericButton);
        rangedUp = new UIImageButton(rangedIcon.x + 8, rangedIcon.y - 24, 16, 16, Assets.genericButton);
        magicUp = new UIImageButton(magicIcon.x + 8, magicIcon.y - 24, 16, 16, Assets.genericButton);

        fireUp = new UIImageButton(fireIcon.x + 8, fireIcon.y - 24, 16, 16, Assets.genericButton);
        airUp = new UIImageButton(airIcon.x + 8, airIcon.y - 24, 16, 16, Assets.genericButton);
        waterUp = new UIImageButton(waterIcon.x + 8, waterIcon.y - 24, 16, 16, Assets.genericButton);
        earthUp = new UIImageButton(earthIcon.x + 8, earthIcon.y - 24, 16, 16, Assets.genericButton);

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

        uiManager.addObject(meleeIcon);
        uiManager.addObject(rangedIcon);
        uiManager.addObject(magicIcon);
        uiManager.addObject(fireIcon);
        uiManager.addObject(airIcon);
        uiManager.addObject(waterIcon);
        uiManager.addObject(earthIcon);
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

            Text.drawString(g, "Character stats", x + width / 2, y + 20, true, Color.YELLOW, Assets.font20);

            Text.drawString(g, "Combat stat points: " + baseStatPoints, x + width / 2, y + 48, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Elemental stat points: " + baseStatPoints, x + + width / 2, y + 64, true, Color.YELLOW, Assets.font14);

            uiManager.render(g);

            g.drawImage(Assets.meleeElement, meleeIcon.x, meleeIcon.y, null);
            g.drawImage(Assets.rangedElement, rangedIcon.x, rangedIcon.y, null);
            g.drawImage(Assets.magicElement, magicIcon.x, magicIcon.y, null);

            g.drawImage(Assets.fireElement, fireIcon.x, fireIcon.y, null);
            g.drawImage(Assets.airElement, airIcon.x, airIcon.y, null);
            g.drawImage(Assets.waterElement, waterIcon.x, waterIcon.y, null);
            g.drawImage(Assets.earthElement, earthIcon.x, earthIcon.y, null);

            Text.drawString(g, String.valueOf(CharacterStats.Melee.getLevel()), meleeIcon.x + meleeIcon.width / 2, meleeIcon.y + meleeIcon.height + 16, true, Color.YELLOW, Assets.font20);
            Text.drawString(g, String.valueOf(CharacterStats.Ranged.getLevel()), rangedIcon.x + rangedIcon.width / 2, rangedIcon.y + rangedIcon.height + 16, true, Color.YELLOW, Assets.font20);
            Text.drawString(g, String.valueOf(CharacterStats.Magic.getLevel()), magicIcon.x + magicIcon.width / 2, magicIcon.y + magicIcon.height + 16, true, Color.YELLOW, Assets.font20);

            Text.drawString(g, String.valueOf(CharacterStats.Fire.getLevel()), fireIcon.x + fireIcon.width / 2, fireIcon.y + fireIcon.height + 16, true, Color.YELLOW, Assets.font20);
            Text.drawString(g, String.valueOf(CharacterStats.Air.getLevel()), airIcon.x + airIcon.width / 2, airIcon.y + airIcon.height + 16, true, Color.YELLOW, Assets.font20);
            Text.drawString(g, String.valueOf(CharacterStats.Water.getLevel()), waterIcon.x + waterIcon.width / 2, waterIcon.y + waterIcon.height + 16, true, Color.YELLOW, Assets.font20);
            Text.drawString(g, String.valueOf(CharacterStats.Earth.getLevel()), earthIcon.x + earthIcon.width / 2, earthIcon.y + earthIcon.height + 16, true, Color.YELLOW, Assets.font20);

            // If we have points available, draw the
            if (baseStatPoints >= 1) {
                baseStatManager.render(g);
                Text.drawString(g, "+", meleeUp.x + meleeUp.width / 2, meleeUp.y + meleeUp.height / 2, true, Color.GREEN, Assets.font14);
                Text.drawString(g, "+", rangedUp.x + rangedUp.width / 2, rangedUp.y + rangedUp.height / 2, true, Color.GREEN, Assets.font14);
                Text.drawString(g, "+", magicUp.x + magicUp.width / 2, magicUp.y + magicUp.height / 2, true, Color.GREEN, Assets.font14);
            }

            if (elementalStatPoints >= 1) {
                elementalStatManager.render(g);
                Text.drawString(g, "+", fireUp.x + fireUp.width / 2, fireUp.y + fireUp.height / 2, true, Color.GREEN, Assets.font14);
                Text.drawString(g, "+", airUp.x + airUp.width / 2, airUp.y + airUp.height / 2, true, Color.GREEN, Assets.font14);
                Text.drawString(g, "+", waterUp.x + waterUp.width / 2, waterUp.y + waterUp.height / 2, true, Color.GREEN, Assets.font14);
                Text.drawString(g, "+", earthUp.x + earthUp.width / 2, earthUp.y + earthUp.height / 2, true, Color.GREEN, Assets.font14);
            }

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
