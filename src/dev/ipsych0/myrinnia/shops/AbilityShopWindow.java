package dev.ipsych0.myrinnia.shops;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilityhud.AbilityTooltip;
import dev.ipsych0.myrinnia.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.ui.TextBox;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.ui.DialogueBox;
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
    private UIImageButton buyButton;
    private UIImageButton exitButton;
    private UIImageButton allButton, meleeButton, rangedButton, magicButton;
    private UIImageButton selectedButton;
    private Rectangle bounds;

    private DialogueBox dBox;
    private static final int DIALOGUE_WIDTH = 300;
    private static final int DIALOGUE_HEIGHT = 150;
    private String[] answers = {"Yes", "No"};
    private static boolean makingChoice = false;
    public static AbilityShopWindow lastOpenedWindow;

    private UIManager uiManager;

    private UIManager allManager;

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

        uiManager = new UIManager();
        allManager = new UIManager();

        // Add the shops slots
        allSlots = new ArrayList<>(abilities.size());
        int xPos = 0;
        int yPos = 0;
        int index = 0;
        for (Ability a : abilities) {
            if (xPos == MAX_HORIZONTAL_SLOTS) {
                xPos = 0;
                yPos++;
            }
            allSlots.add(new AbilityShopSlot(a, x + 16 + xPos++ * 32, y + 128 + yPos * 32));
            allManager.addObject(allSlots.get(index++));
        }
        currentSlots = allSlots;

        abilityTooltip = new AbilityTooltip(x - 160, y, 160, 224);

        buyButton = new UIImageButton(x + width / 2 - 32, y + height - 64, 64, 32, Assets.genericButton);
        exitButton = new UIImageButton(x + width - 35, y + 10, 24, 24, Assets.genericButton);

        allButton = new UIImageButton(x + width / 2 - 138, y + 64, 64, 32, Assets.genericButton);
        meleeButton = new UIImageButton(x + width / 2 - 70, y + 64, 64, 32, Assets.genericButton);
        rangedButton = new UIImageButton(x + width / 2 + 2, y + 64, 64, 32, Assets.genericButton);
        magicButton = new UIImageButton(x + width / 2 + 70, y + 64, 64, 32, Assets.genericButton);

        uiManager.addObject(buyButton);
        uiManager.addObject(exitButton);
        uiManager.addObject(allButton);
        uiManager.addObject(meleeButton);
        uiManager.addObject(rangedButton);
        uiManager.addObject(magicButton);

        meleeSlots = new ArrayList<>();
        rangedSlots = new ArrayList<>();
        magicSlots = new ArrayList<>();

        setSubSlots(currentSlots);
        selectedButton = allButton;

        // Instance of the DialogueBox
        dBox = new DialogueBox(x + (width / 2) - (DIALOGUE_WIDTH / 2), y + (height / 2) - (DIALOGUE_HEIGHT / 2), DIALOGUE_WIDTH, DIALOGUE_HEIGHT, answers, "", false);
    }

    public void setLastOpenedWindow() {
        AbilityShopWindow.lastOpenedWindow = this;
    }

    public void exit() {
        if(Handler.get().getMouseManager().isLeftPressed()){
            MouseManager.justClosedUI = true;
        }
        isOpen = false;
        hasBeenPressed = false;
        dBox.setPressedButton(null);
        makingChoice = false;
        DialogueBox.isOpen = false;
    }

    public void tick() {
        Rectangle mouse = Handler.get().getMouse();

        uiManager.tick();
        allManager.tick();

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

    public void render(Graphics2D g) {
        g.drawImage(Assets.shopWindow, x, y, width, height, null);

        Rectangle mouse = Handler.get().getMouse();

        uiManager.render(g);
        allManager.render(g);

        for (AbilityShopSlot slot : currentSlots) {
            if (slot.getBounds().contains(mouse)) {
                slot.setHovering(true);
                abilityTooltip.render(g, slot.getAbility());
            } else {
                slot.setHovering(false);
            }
            slot.render(g);

            if (slot.getAbility().isUnlocked()) {
                Text.drawString(g, "✓", (int) slot.getX() + 24, (int) slot.getY() + 28, true, Color.GREEN, Assets.font14);
            }
        }

        Text.drawString(g, "Ability Trainer", x + width / 2, y + 16, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Ability Points: " + Handler.get().getPlayer().getAbilityPoints(), x + width / 2, y + 36, true, Color.YELLOW, Assets.font14);

        // Draw selected ability information
        if (selectedSlot != null) {
            Ability a = selectedSlot.getAbility();
            g.setColor(selectedColor);
            g.fillRect((int) selectedSlot.getX(), (int) selectedSlot.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);

            Text.drawString(g, a.getName() + " costs: " + a.getPrice() + " ability points.", x + width / 2, buyButton.y + buyButton.height + 16, true, Color.YELLOW, Assets.font14);
        }

        // Draw the UI buttons
        drawButtons(g);

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
            Handler.get().playEffect("ui/shop_trade.wav");
            selectedSlot = null;
        } else {
            Handler.get().sendMsg("You don't have enough Ability Points.");
            Handler.get().playEffect("ui/ui_button_click.wav");
        }
    }

    private void checkSubmit() {
        if (makingChoice && dBox.getPressedButton() != null) {
            if ("Yes".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[0])) {
                buyAbility(selectedSlot.getAbility());
            } else if ("No".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[0])) {
                Handler.get().playEffect("ui/ui_button_click.wav");
            }
            dBox.setPressedButton(null);
            DialogueBox.isOpen = false;
            makingChoice = false;
            hasBeenPressed = false;
        }
    }

    public static void open() {
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
            selectedButton = allButton;
            resetUIManager();
            return;
        }

        // Melee button
        if (meleeButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            hasBeenPressed = false;
            selectedSlot = null;
            currentSlots = meleeSlots;
            selectedButton = meleeButton;
            resetUIManager();
            return;
        }

        // Ranged button
        if (rangedButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            hasBeenPressed = false;
            selectedSlot = null;
            currentSlots = rangedSlots;
            selectedButton = rangedButton;
            resetUIManager();
            return;
        }

        // Magic button
        if (magicButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            hasBeenPressed = false;
            selectedSlot = null;
            currentSlots = magicSlots;
            selectedButton = magicButton;
            resetUIManager();
        }
    }

    private void resetUIManager(){
        allManager.getObjects().clear();
        for(AbilityShopSlot as : currentSlots){
            allManager.addObject(as);
        }
    }

    private void drawButtons(Graphics2D g) {
        g.setColor(selectedColor);
        g.fillRect(selectedButton.x, selectedButton.y, selectedButton.width, selectedButton.height);
        Text.drawString(g, "Unlock", buyButton.x + buyButton.width / 2, buyButton.y + buyButton.height / 2, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "X", exitButton.x + 11, exitButton.y + 11, true, Color.YELLOW, Assets.font20);
        Text.drawString(g, "All", allButton.x + allButton.width / 2, allButton.y + allButton.height / 2, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Melee", meleeButton.x + meleeButton.width / 2, meleeButton.y + meleeButton.height / 2, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Ranged", rangedButton.x + rangedButton.width / 2, rangedButton.y + rangedButton.height / 2, true, Color.YELLOW, Assets.font14);
        Text.drawString(g, "Magic", magicButton.x + magicButton.width / 2, magicButton.y + magicButton.height / 2, true, Color.YELLOW, Assets.font14);
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
