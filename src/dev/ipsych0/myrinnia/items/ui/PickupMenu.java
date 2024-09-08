package dev.ipsych0.myrinnia.items.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PickupMenu {

    private int x, y;
    private List<Item> items;
    private List<PickupEntry> entries;
    private boolean open;

    public PickupMenu(int x, int y) {
        this.x = x;
        this.y = y;
        entries = new ArrayList<>();
    }

    public void tick() {
        if (open) {
            boolean hovering = false;
            for (PickupEntry entry : entries) {
                entry.tick();
                Rectangle mouse = Handler.get().getMouse();
                // If the mouse contains the bounds + padding, don't close the window yet
                if (entry.getBounds(-12, -12).contains(mouse)) {
                    hovering = true;
                    // If we contain the real bounds of an entry and left-press, pick up item
                    if (entry.getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
                        Item i = entry.getItem();
                        if (isStillInWorld(i)) {
                            if (i.pickUpItem(i)) {
                                if (i.isPickedUp()) {
                                    if (!ItemManager.soundPlayed) {
                                        Handler.get().playEffect("ui/pickup.ogg");
                                        ItemManager.soundPlayed = true;
                                    }
                                    Handler.get().getWorld().getItemManager().getDeleted().add(i);
                                }
                            }
                        }
                        open = false;
                        entries.clear();
                        break;
                    }
                }
            }

            if (!hovering) {
                open = false;
                entries.clear();
            }
        }
    }

    private boolean isStillInWorld(Item i) {
        ItemManager manager = Handler.get().getWorld().getItemManager();
        return manager.getItems().contains(i);
    }

    public void render(Graphics2D g) {
        if (open) {
            int highestWidth = 0;
            for (PickupEntry entry : entries) {
                int width = entry.getStrWidth(g);
                if (width > highestWidth) {
                    highestWidth = width;
                }
            }

            PickupEntry.WIDTH = highestWidth;
            for (PickupEntry entry : entries) {
                entry.render(g);
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        entries.clear();
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            entries.add(new PickupEntry(item, x, y + (i * PickupEntry.HEIGHT)));
        }

        int yOffset = entries.get(entries.size() - 1).getY() - (Handler.get().getHeight() - PickupEntry.HEIGHT * 3);
        if (yOffset > 0) {
            for (int i = 0; i < items.size(); i++) {
                PickupEntry entry = entries.get(i);
                entry.setY(entry.getY() - ((int) Math.ceil((double) yOffset / (double) PickupEntry.HEIGHT) * PickupEntry.HEIGHT));
            }
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
