package dev.ipsych0.mygame.shop;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilities.Ability;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemSlot;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class AbilityShopWindow implements Serializable {

    private static final long serialVersionUID = 7862013290912383006L;

    private ArrayList<Ability> abilities;
    public static boolean isOpen;
    private int x, y, width, height;
    private static final int MAX_HORIZONTAL_SLOTS = 10;
    private ArrayList<AbilityShopSlot> shopSlots;
    public static boolean hasBeenPressed;
    private AbilityShopSlot selectedSlot;
    private Color selectedColor = new Color(0, 255, 255, 62);

    public AbilityShopWindow(ArrayList<Ability> abilities) {
        this.abilities = abilities;
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

        if(selectedSlot != null){
            g.setColor(selectedColor);
            g.fillRoundRect(selectedSlot.getX(), selectedSlot.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, 4, 4);
        }
    }
}
