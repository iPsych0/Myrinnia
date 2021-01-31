package dev.ipsych0.myrinnia.items.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
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
                if (entry.getBounds().contains(mouse)) {
                    hovering = true;
                    if (Handler.get().getMouseManager().isLeftPressed()) {
                        Item i = entry.getItem();
                        if (i.pickUpItem(i)) {
                            if (i.isPickedUp()) {
                                if (!ItemManager.soundPlayed) {
                                    Handler.get().playEffect("ui/pickup.ogg");
                                    ItemManager.soundPlayed = true;
                                }
                                Handler.get().getWorld().getItemManager().getDeleted().add(i);
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

    public void render(Graphics2D g) {
        if (open) {
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
            entries.add(new PickupEntry(item, x, y + (i * 16)));
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
