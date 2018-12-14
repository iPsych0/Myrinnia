package dev.ipsych0.myrinnia.shops;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilityhud.AbilityTooltip;
import dev.ipsych0.myrinnia.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemSlot;
import dev.ipsych0.myrinnia.states.GameState;
import dev.ipsych0.myrinnia.ui.TextBox;
import dev.ipsych0.myrinnia.utils.DialogueBox;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class AbilityShopWindow implements Serializable {

    private static final long serialVersionUID = 7862013290912383006L;

    public static boolean isOpen;
    private int x, y, width, height;
    private static final int MAX_HORIZONTAL_SLOTS = 10;
    private ArrayList<AbilityShopSlot> allSlots;
    private ArrayList<AbilityShopSlot> meleeSlots, rangedSlots, magicSlots;
    private ArrayList<AbilityShopSlot> currentSlots;
    public static boolean hasBeenPressed;
    private AbilityShopSlot selectedSlot;
    private Color selectedColor = new Color(0, 255, 255, 62);
    private Rectangle buyButton;
    private Rectangle exitButton;
    private Rectangle allButton, meleeButton, rangedButton, magicButton;
    private Rectangle bounds;

    private DialogueBox dBox;
    private static final int DIALOGUE_WIDTH = 300;
    private static final int DIALOGUE_HEIGHT = 150;
    private String[] answers = {"Yes", "No"};
    public static boolean makingChoice = false;
    public static AbilityShopWindow lastOpenedWindow;

    private AbilityTooltip abilityTooltip;

    public AbilityShopWindow(ArrayList<Ability> abilities) {
        this.width = 460;
        this.height = 313;
        this.x = Handler.get().getWidth() / 2 - width / 2;
        this.y = Handler.get().getHeight() / 2 - height / 2;
        this.bounds = new Rectangle(x, y, width, height);

        if (abilities.isEmpty()) {
            System.err.println("Ability shops must always have at least one ability to teach.");
            System.exit(1);
        }

        // Add the shops slots
        allSlots = new ArrayList<>(abilities.size());
        int xPos = 0;
        int yPos = 0;
        for (Ability a : abilities) {
            if (xPos == MAX_HORIZONTAL_SLOTS) {
                xPos = 0;
                yPos++;
            }
            allSlots.add(new AbilityShopSlot(a, x + 16 + xPos++ * 32, y + 128 + yPos * 32));
        }
        currentSlots = allSlots;

        abilityTooltip = new AbilityTooltip(x - 160, y, 160, 224);

        buyButton = new Rectangle(x + width / 2 - 32, y + height - 64, 64, 32);
        exitButton = new Rectangle(x + width - 35, y + 10, 24, 24);

        allButton = new Rectangle(x + width / 2 - 138, y + 64, 64, 32);
        meleeButton = new Rectangle(x + width / 2 - 70, y + 64, 64, 32);
        rangedButton = new Rectangle(x + width / 2 + 2, y + 64, 64, 32);
        magicButton = new Rectangle(x + width / 2 + 70, y + 64, 64, 32);

        meleeSlots = new ArrayList<>();
        rangedSlots = new ArrayList<>();
        magicSlots = new ArrayList<>();

        setSubSlots(currentSlots);

        // Instance of the DialogueBox
        dBox = new DialogueBox(x + (width / 2) - (DIALOGUE_WIDTH / 2), y + (height / 2) - (DIALOGUE_HEIGHT / 2), DIALOGUE_WIDTH, DIALOGUE_HEIGHT, answers, "", false);

        /**
         * TODO: REMOVE THIS DUMMY DATA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         */
        Handler.get().getPlayer().setAbilityPoints(10);
    }

    public void setLastOpenedWindow(){
        AbilityShopWindow.lastOpenedWindow = this;
    }

    public void exit() {
        isOpen = false;
        hasBeenPressed = false;
        dBox.setPressedButton(null);
        makingChoice = false;
        DialogueBox.isOpen = false;
    }

    public void tick() {
        Rectangle mouse = Handler.get().getMouse();

        for (AbilityShopSlot slot : currentSlots) {
            slot.tick();
            if (slot.getBounds().contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    if (selectedSlot == null)
                        selectedSlot = slot;
                    else if (selectedSlot == slot)
                        selectedSlot = null;
                    else if (selectedSlot != slot)
                        selectedSlot = slot;

                    hasBeenPressed = false;
                }
            }
        }

        // If player is making a choice, show the dialoguebox
        if (makingChoice)
            dBox.tick();

        checkSubmit();

        handleButtonClicks(mouse);
    }

    public void render(Graphics g) {
        g.drawImage(Assets.shopWindow, x, y, width, height, null);

        Rectangle mouse = Handler.get().getMouse();

        for (AbilityShopSlot slot : currentSlots) {
            if (slot.getBounds().contains(mouse)) {
                slot.setHovering(true);
                abilityTooltip.render(g, slot.getAbility());
            } else {
                slot.setHovering(false);
            }
            slot.render(g);

            if (slot.getAbility().isUnlocked()) {
                Text.drawString(g, "✓", slot.getX() + 24, slot.getY() + 28, true, Color.GREEN, Assets.font14);
            }
        }

        Text.drawString(g, "Ability Trainer", x + width / 2, y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Ability Points: " + Handler.get().getPlayer().getAbilityPoints(), x + width / 2, y + 36, true, Color.YELLOW, Assets.font14);

        // Draw selected ability information
        if (selectedSlot != null) {
            Ability a = selectedSlot.getAbility();
            g.setColor(selectedColor);
            g.fillRoundRect(selectedSlot.getX(), selectedSlot.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, 4, 4);

            Text.drawString(g, a.getName() + " costs: " + a.getPrice() + " ability points.", x + width / 2, buyButton.y + buyButton.height + 16, true, Color.YELLOW, Assets.font14);
        }

        // Draw the UI buttons
        drawButtons(g, mouse);

        // If player is making a choice, show the dialoguebox
        if (makingChoice)
            dBox.render(g);
    }

    private void buyAbility(Ability ability) {
        if (ability.isUnlocked()) {
            Handler.get().sendMsg("You have already unlocked " + ability.getName() + ".");
            return;
        }
        int abilityPoints = Handler.get().getPlayer().getAbilityPoints();
        int price = ability.getPrice();

        if (abilityPoints >= price) {
            Handler.get().getPlayer().setAbilityPoints(abilityPoints - price);
            Handler.get().getAbilityManager().getAllAbilities().get(ability.getId()).setUnlocked(true);
            Handler.get().sendMsg("Unlocked '" + ability.getName() + "'!");
            selectedSlot = null;
        } else {
            Handler.get().sendMsg("You don't have enough Ability Points.");
        }
    }

    private void checkSubmit() {
        if (makingChoice && dBox.getPressedButton() != null) {
            if ("Yes".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[0])) {
                buyAbility(selectedSlot.getAbility());
            }
            dBox.setPressedButton(null);
            DialogueBox.isOpen = false;
            makingChoice = false;
            hasBeenPressed = false;
        }
    }

    public static void open(){
        AbilityOverviewUI.exit();
        AbilityShopWindow.isOpen = true;
    }

    private void handleButtonClicks(Rectangle mouse) {
        // Buy button
        if (buyButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            if (selectedSlot != null) {
                makingChoice = true;
                DialogueBox.isOpen = true;
                TextBox.isOpen = false;
                dBox.setParam("Unlock");
                dBox.setMessage("Do you want to learn '" + selectedSlot.getAbility().getName() + "'?");
            }
            hasBeenPressed = false;
            return;
        }

        // Exit button
        if (exitButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() || Player.isMoving || Handler.get().getKeyManager().escape) {
            exit();
            return;
        }

        // All button
        if (allButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            hasBeenPressed = false;
            selectedSlot = null;
            currentSlots = allSlots;
            return;
        }

        // Melee button
        if (meleeButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            hasBeenPressed = false;
            selectedSlot = null;
            currentSlots = meleeSlots;
            return;
        }

        // Ranged button
        if (rangedButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            hasBeenPressed = false;
            selectedSlot = null;
            currentSlots = rangedSlots;
            return;
        }

        // Magic button
        if (magicButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            hasBeenPressed = false;
            selectedSlot = null;
            currentSlots = magicSlots;
            return;
        }
    }

    private void drawButtons(Graphics g, Rectangle mouse) {
        // Buy button
        if (buyButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], buyButton.x, buyButton.y, buyButton.width, buyButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], buyButton.x, buyButton.y, buyButton.width, buyButton.height, null);
        Text.drawString(g, "Unlock", buyButton.x + buyButton.width / 2, buyButton.y + buyButton.height / 2, true, Color.YELLOW, Assets.font14);

        // Exit button
        if (exitButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], exitButton.x, exitButton.y, exitButton.width, exitButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], exitButton.x, exitButton.y, exitButton.width, exitButton.height, null);
        Text.drawString(g, "X", exitButton.x + 11, exitButton.y + 11, true, Color.YELLOW, Assets.font20);

        // All button
        if (allButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], allButton.x, allButton.y, allButton.width, allButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], allButton.x, allButton.y, allButton.width, allButton.height, null);
        Text.drawString(g, "All", allButton.x + allButton.width / 2, allButton.y + allButton.height / 2, true, Color.YELLOW, GameState.chatFont);

        // Melee button
        if (meleeButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], meleeButton.x, meleeButton.y, meleeButton.width, meleeButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], meleeButton.x, meleeButton.y, meleeButton.width, meleeButton.height, null);
        Text.drawString(g, "Melee", meleeButton.x + meleeButton.width / 2, meleeButton.y + meleeButton.height / 2, true, Color.YELLOW, GameState.chatFont);

        // Ranged button
        if (rangedButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], rangedButton.x, rangedButton.y, rangedButton.width, rangedButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], rangedButton.x, rangedButton.y, rangedButton.width, rangedButton.height, null);
        Text.drawString(g, "Ranged", rangedButton.x + rangedButton.width / 2, rangedButton.y + rangedButton.height / 2, true, Color.YELLOW, GameState.chatFont);

        // Magic button
        if (magicButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], magicButton.x, magicButton.y, magicButton.width, magicButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], magicButton.x, magicButton.y, magicButton.width, magicButton.height, null);
        Text.drawString(g, "Magic", magicButton.x + magicButton.width / 2, magicButton.y + magicButton.height / 2, true, Color.YELLOW, GameState.chatFont);
    }

    private void setSubSlots(ArrayList<AbilityShopSlot> slots) {
        int meleeXPos = 0;
        int meleeYPos = 0;
        int rangedXPos = 0;
        int rangedYPos = 0;
        int magicXPos = 0;
        int magicYPos = 0;

        for (AbilityShopSlot slot : slots) {
            if (slot.getAbility().getCombatStyle() == CharacterStats.Melee) {
                if (meleeXPos == MAX_HORIZONTAL_SLOTS) {
                    meleeXPos = 0;
                    meleeYPos++;
                }
                meleeSlots.add(new AbilityShopSlot(slot.getAbility(), x + 16 + meleeXPos++ * 32, y + 128 + meleeYPos * 32));

            } else if (slot.getAbility().getCombatStyle() == CharacterStats.Ranged) {
                if (rangedXPos == MAX_HORIZONTAL_SLOTS) {
                    rangedXPos = 0;
                    rangedYPos++;
                }
                rangedSlots.add(new AbilityShopSlot(slot.getAbility(), x + 16 + rangedXPos++ * 32, y + 128 + rangedYPos * 32));
            } else if (slot.getAbility().getCombatStyle() == CharacterStats.Magic) {
                if (magicXPos == MAX_HORIZONTAL_SLOTS) {
                    magicXPos = 0;
                    magicYPos++;
                }
                magicSlots.add(new AbilityShopSlot(slot.getAbility(), x + 16 + magicXPos++ * 32, y + 128 + magicYPos * 32));
            }
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
