package dev.ipsych0.mygame.shop;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilities.Ability;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class AbilityShopWindow implements Serializable {

    private static final long serialVersionUID = 7862013290912383006L;

    public static boolean isOpen;
    private int x, y, width, height;
    private static final int MAX_HORIZONTAL_SLOTS = 10;
    private ArrayList<AbilityShopSlot> shopSlots;
    public static boolean hasBeenPressed;
    private AbilityShopSlot selectedSlot;
    private Color selectedColor = new Color(0, 255, 255, 62);
    private Rectangle buyButton;

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
        shopSlots = new ArrayList<>(abilities.size());
        int xPos = 0;
        int yPos = 0;
        for(Ability a : abilities){
            if(xPos == MAX_HORIZONTAL_SLOTS){
                xPos = 0;
                yPos++;
            }
            shopSlots.add(new AbilityShopSlot(a,x + 4 + xPos++ * 32, y +100 + yPos * 32));
        }

        buyButton = new Rectangle(x + width / 2 - 32, y + height - 64, 64, 32);

        /**
         * TODO: REMOVE THIS DUMMY DATA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         */
        Handler.get().getPlayer().setAbilityPoints(5);
    }

    public void tick(){
        Rectangle mouse = Handler.get().getMouse();

        for(AbilityShopSlot slot : shopSlots){
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
    }

    public void render(Graphics g){
        g.drawImage(Assets.shopWindow, x, y, width, height, null);

        Rectangle mouse = Handler.get().getMouse();

        for(AbilityShopSlot slot : shopSlots){
            if(slot.getBounds().contains(mouse)){
                slot.setHovering(true);
            }else{
                slot.setHovering(false);
            }
            slot.render(g);
        }

        Text.drawString(g, "Ability Points: " + Handler.get().getPlayer().getAbilityPoints(), x + 4, y + 18, false, Color.YELLOW, Assets.font14);

        // Draw selected ability information
        if(selectedSlot != null){
            Ability a = selectedSlot.getAbility();
            g.setColor(selectedColor);
            g.fillRoundRect(selectedSlot.getX(), selectedSlot.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, 4, 4);

            Text.drawString(g, a.getName() + " costs: " + a.getPrice() + " ability points.", x + 4, y + 32, false, Color.YELLOW, Assets.font14);

            if(buyButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed){
                buyAbility(selectedSlot.getAbility());
                hasBeenPressed = false;
            }
        }

        // Buy button
        if(buyButton.contains(mouse))
            g.drawImage(Assets.genericButton[0], buyButton.x, buyButton.y, buyButton.width, buyButton.height, null);
        else
            g.drawImage(Assets.genericButton[1], buyButton.x, buyButton.y, buyButton.width, buyButton.height, null);

        Text.drawString(g, "Unlock", buyButton.x + buyButton.width / 2, buyButton.y + buyButton.height / 2, true, Color.YELLOW, Assets.font14);
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
            shopSlots.remove(ability);
        }else{
            Handler.get().sendMsg("You don't have enough Ability Points.");
        }
    }
}
