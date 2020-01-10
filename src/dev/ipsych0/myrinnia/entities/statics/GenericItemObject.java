package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GenericItemObject extends StaticEntity {

    private List<ItemStack> items = new ArrayList<>();
    private boolean hasGivenItems;

    public GenericItemObject(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = false;
        attackable = false;
        isNpc = true;

        if (itemsShop == null) {
            throw new IllegalArgumentException("Provide item IDs and quantities in comma-separated K,V pairs.");
        }

        String[] items = itemsShop.replaceAll(" ", "").trim().split(",");
        if (items.length % 2 != 0) {
            throw new IllegalArgumentException("Odd number of arguments. Make sure you have matching K,V pairs.");
        }

        for (int i = 0; i < items.length; i += 2) {
            Item item = Item.items[Integer.parseInt(items[i])];
            int amount = Integer.parseInt(items[i + 1]);
            this.items.add(new ItemStack(item, amount));
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new GenericItemObject(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {

    }

    @Override
    public void interact() {
        if (!hasGivenItems) {
            if (chatDialogue != null) {
                chatDialogue = null;
                return;
            }
            if (items.size() == 1) {
                chatDialogue = new ChatDialogue(new String[]{"You found '" + items.get(0).getItem().getName() + "' in the " + name.toLowerCase() + "."});
            } else {
                chatDialogue = new ChatDialogue(new String[]{"You found some items in the " + name.toLowerCase() + "."});
            }
            for (ItemStack is : items) {
                Handler.get().giveItem(is.getItem(), is.getAmount());
            }
            hasGivenItems = true;
        } else {
            if (chatDialogue != null) {
                chatDialogue = null;
                return;
            }
            chatDialogue = new ChatDialogue(new String[]{"There is nothing here."});
        }
    }
}
