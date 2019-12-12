package dev.ipsych0.myrinnia.shops;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class ShopSlot extends ItemSlot {

    public ShopSlot(int x, int y, ItemStack itemStack) {
        super(x, y, itemStack);
    }

    public void tick() {

    }

    public void render(Graphics2D g) {

        g.drawImage(Assets.genericButton[0], x, y, SLOTSIZE, SLOTSIZE, null);

        renderItem(g, x, y);
    }

    public void renderItem(Graphics2D g, int x, int y) {
        if (itemStack != null) {
            g.drawImage(itemStack.getItem().getTexture(), x, y, SLOTSIZE, SLOTSIZE, null);
            Text.drawString(g, Handler.get().getInventory().getAbbrevRenderAmount(itemStack), x, y + SLOTSIZE - 21, false, Color.YELLOW, Assets.font14);
        }
    }

    /*
     * Adds items to the inventory
     */
    public boolean addItem(Item item, int amount) {
        // If the item is stackable
        if (itemStack != null) {
            if (item.getId() == itemStack.getItem().getId()) {
                // If a stack already exists and the item is stackable, add to that stack
                this.itemStack.setAmount(this.itemStack.getAmount() + amount);
                return true;
            } else {
                return false;
            }
        } else {
            // Else create a new stack
            this.itemStack = new ItemStack(item, amount);
            return true;
        }
    }
}
