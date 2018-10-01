package dev.ipsych0.mygame.shop;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilities.Ability;
import dev.ipsych0.mygame.character.CharacterStats;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.states.GameState;
import dev.ipsych0.mygame.utils.Text;

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

    public AbilityShopWindow(ArrayList<Ability> abilities) {
        width = 460;
        height = 313;
        x = Handler.get().getWidth() / 2 - width / 2;
        y = Handler.get().getHeight() / 2 - height / 2;

        if(abilities.isEmpty()){
            System.err.println("Ability shops must always have at least one ability to teach.");
            System.exit(1);
        }

        // Add the shop slots
        allSlots = new ArrayList<>(abilities.size());
        int xPos = 0;
        int yPos = 0;
        for(Ability a : abilities){
            if(xPos == MAX_HORIZONTAL_SLOTS){
                xPos = 0;
                yPos++;
            }
            allSlots.add(new AbilityShopSlot(a,x + 4 + xPos++ * 32, y + 128 + yPos * 32));
        }
        currentSlots = allSlots;

        buyButton = new Rectangle(x + width / 2 - 32, y + height - 64, 64, 32);
        exitButton = new Rectangle(x + width - 35, y + 10, 24, 24);

        allButton = new Rectangle(x + width / 2 - 32, y + 84, 64, 32);
        meleeButton = new Rectangle(x + width / 2 - 100, y + 44, 64, 32);
        rangedButton = new Rectangle(x + width / 2 - 32, y + 44, 64, 32);
        magicButton = new Rectangle(x + width / 2 + 36, y + 44, 64, 32);

        meleeSlots = new ArrayList<>();
        rangedSlots = new ArrayList<>();
        magicSlots = new ArrayList<>();

        setSubSlots(currentSlots);

        /**
         * TODO: REMOVE THIS DUMMY DATA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         */
        Handler.get().getPlayer().setAbilityPoints(10);
    }

    public void tick(){
        Rectangle mouse = Handler.get().getMouse();

        for(AbilityShopSlot slot : currentSlots){
            slot.tick();
            if(slot.getBounds().contains(mouse)) {
                if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    if(selectedSlot == null)
                        selectedSlot = slot;
                    else if(selectedSlot == slot)
                        selectedSlot = null;
                    else if(selectedSlot != slot)
                        selectedSlot = slot;

                    hasBeenPressed = false;
                }
            }
        }

        handleButtonClicks(mouse);
    }

    public void render(Graphics g){
        g.drawImage(Assets.shopWindow, x, y, width, height, null);

        Rectangle mouse = Handler.get().getMouse();

        for(AbilityShopSlot slot : currentSlots){
            if(slot.getBounds().contains(mouse)){
                slot.setHovering(true);
            }else{
                slot.setHovering(false);
            }
            slot.render(g);

            if(slot.getAbility().isUnlocked()){
                Text.drawString(g, "✓", slot.getX() + 24, slot.getY() + 28, true, Color.GREEN, Assets.font14);
            }
        }

        Text.drawString(g, "Ability Points: " + Handler.get().getPlayer().getAbilityPoints(), x + 4, y + 18, false, Color.YELLOW, Assets.font14);

        // Draw selected ability information
        if(selectedSlot != null){
            Ability a = selectedSlot.getAbility();
            g.setColor(selectedColor);
            g.fillRoundRect(selectedSlot.getX(), selectedSlot.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, 4, 4);

            Text.drawString(g, a.getName() + " costs: " + a.getPrice() + " ability points.", x + width / 2, buyButton.y + buyButton.height + 16, true, Color.YELLOW, Assets.font14);
        }

        // Draw the UI buttons
        drawButtons(g, mouse);
    }

    private void buyAbility(Ability ability){
        if(ability.isUnlocked()){
            Handler.get().sendMsg("You have already unlocked " + ability.getName() + ".");
            return;
        }
        int abilityPoints = Handler.get().getPlayer().getAbilityPoints();
        int price = ability.getPrice();

        if(abilityPoints >= price){
            Handler.get().getPlayer().setAbilityPoints(abilityPoints - price);
            ability.setUnlocked(true);
            Handler.get().sendMsg("Unlocked '" + ability.getName() + "'!");
            selectedSlot = null;
        }else{
            Handler.get().sendMsg("You don't have enough Ability Points.");
        }
    }

    private void handleButtonClicks(Rectangle mouse){
        // Buy button
        if(buyButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed){
            if(selectedSlot != null) {
                buyAbility(selectedSlot.getAbility());
            }
            hasBeenPressed = false;
            return;
        }

        // Exit button
        if(exitButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() || Player.isMoving || Handler.get().getKeyManager().escape){
            isOpen = false;
            hasBeenPressed = false;
            selectedSlot = null;
            return;
        }

        // All button
        if(allButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed){
            hasBeenPressed = false;
            selectedSlot = null;
            currentSlots = allSlots;
        }

        // Melee button
        if(meleeButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed){
            hasBeenPressed = false;
            selectedSlot = null;
            currentSlots = meleeSlots;
        }

        // Ranged button
        if(rangedButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed){
            hasBeenPressed = false;
            selectedSlot = null;
            currentSlots = rangedSlots;
        }

        // Magic button
        if(magicButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed){
            hasBeenPressed = false;
            selectedSlot = null;
            currentSlots = magicSlots;
        }

    }

    private void drawButtons(Graphics g, Rectangle mouse){
        // Buy button
        if(buyButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], buyButton.x, buyButton.y, buyButton.width, buyButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], buyButton.x, buyButton.y, buyButton.width, buyButton.height, null);
        Text.drawString(g, "Unlock", buyButton.x + buyButton.width / 2, buyButton.y + buyButton.height / 2, true, Color.YELLOW, Assets.font14);

        // Exit button
        if(exitButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], exitButton.x, exitButton.y, exitButton.width, exitButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], exitButton.x, exitButton.y, exitButton.width, exitButton.height, null);
        Text.drawString(g, "X", exitButton.x + 12, y + 10 + 12, true, Color.YELLOW, GameState.chatFont);

        // All button
        if(allButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], allButton.x, allButton.y, allButton.width, allButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], allButton.x, allButton.y, allButton.width, allButton.height, null);
        Text.drawString(g, "All", allButton.x + allButton.width / 2, allButton.y + allButton.height / 2, true, Color.YELLOW, GameState.chatFont);

        // Melee button
        if(meleeButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], meleeButton.x, meleeButton.y, meleeButton.width, meleeButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], meleeButton.x, meleeButton.y, meleeButton.width, meleeButton.height, null);
        Text.drawString(g, "Melee", meleeButton.x + meleeButton.width / 2, meleeButton.y + meleeButton.height / 2, true, Color.YELLOW, GameState.chatFont);

        // Ranged button
        if(rangedButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], rangedButton.x, rangedButton.y, rangedButton.width, rangedButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], rangedButton.x, rangedButton.y, rangedButton.width, rangedButton.height, null);
        Text.drawString(g, "Ranged", rangedButton.x + rangedButton.width / 2, rangedButton.y + rangedButton.height / 2, true, Color.YELLOW, GameState.chatFont);

        // Magic button
        if(magicButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], magicButton.x, magicButton.y, magicButton.width, magicButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], magicButton.x, magicButton.y, magicButton.width, magicButton.height, null);
        Text.drawString(g, "Magic", magicButton.x + magicButton.width / 2, magicButton.y + magicButton.height / 2, true, Color.YELLOW, GameState.chatFont);
    }

    private void setSubSlots(ArrayList<AbilityShopSlot> slots){
        int xPos = 0;
        int yPos = 0;
        for(AbilityShopSlot slot : slots){
            if(slot.getAbility().getCombatStyle() == CharacterStats.Melee){
                if(xPos == MAX_HORIZONTAL_SLOTS){
                    xPos = 0;
                    yPos++;
                }
                meleeSlots.add(new AbilityShopSlot(slot.getAbility(),x + 4 + xPos++ * 32, y + 128 + yPos * 32));
            }
        }
        xPos = 0;
        yPos = 0;
        for(AbilityShopSlot slot : slots){
            if(slot.getAbility().getCombatStyle() == CharacterStats.Ranged){
                if(xPos == MAX_HORIZONTAL_SLOTS){
                    xPos = 0;
                    yPos++;
                }
                rangedSlots.add(new AbilityShopSlot(slot.getAbility(),x + 4 + xPos++ * 32, y + 128 + yPos * 32));
            }
        }
        xPos = 0;
        yPos = 0;
        for(AbilityShopSlot slot : slots){
            if(slot.getAbility().getCombatStyle() == CharacterStats.Magic){
                if(xPos == MAX_HORIZONTAL_SLOTS){
                    xPos = 0;
                    yPos++;
                }
                magicSlots.add(new AbilityShopSlot(slot.getAbility(),x + 4 + xPos++ * 32, y + 128 + yPos * 32));
            }
        }
    }
}
