package dev.ipsych0.myrinnia.items;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;

import java.awt.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemManager implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1092891818645452920L;
    private CopyOnWriteArrayList<Item> items;
    private Collection<Item> deleted;
    private Collection<Item> added;
    private static boolean soundPlayed = false;
    private static int lastPlayed = 0;

    public ItemManager() {
        items = new CopyOnWriteArrayList<Item>();
        deleted = new CopyOnWriteArrayList<Item>();
        added = new CopyOnWriteArrayList<Item>();
    }

    public void tick() {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item i = it.next();

            // Check if we're hovering over the item
            i.setHovering(i.itemPosition(-Handler.get().getGameCamera().getxOffset(),-Handler.get().getGameCamera().getyOffset()).contains(Handler.get().getMouse()));

            // Checks player's pause for any items nearby to pick up
            if (Handler.get().getMouseManager().isRightPressed() && Handler.get().getPlayer().itemPickupRadius().intersects(i.itemPosition(0, 0))) {
                if (!Handler.get().getPlayer().hasRightClickedUI(Handler.get().getMouse())) {
                    if (i.pickUpItem(i)) {
                        if (i.isPickedUp()) {
                            if(!soundPlayed) {
                                Handler.get().playEffect("ui/pickup.wav");
                                soundPlayed = true;
                            }
                            deleted.add(i);
                        }
                    }
                }
            }

            // Adds small delay to sounds to prevent them from stacking when picking up multiple items at once
            if(soundPlayed){
                lastPlayed++;
                if(lastPlayed >= 5){
                    soundPlayed = false;
                    lastPlayed = 0;
                }
            }
            i.tick();
        }

        // If non-worldspawn Items are dropped, start timer for despawning
        if (added.size() > 0) {
            for (Item i : added) {
                i.startRespawnTimer();

                // If item is picked up, reset the timer
                if (i.isPickedUp()) {
                    i.setRespawnTimer(10800);
                    deleted.add(i);
                    added.remove(i);
                }
                // If the timer expires, remove the item
                else if (i.getRespawnTimer() == 0) {
                    deleted.add(i);
                    added.remove(i);
                }
            }
        }

        // If Item's timer is 0, remove the items from the world.
        if (deleted.size() > 0) {
            items.removeAll(deleted);
        }
    }

    public void render(Graphics g) {
        for (Item i : items) {
            i.render(g);
            if(i.isHovering()){
                drawHoverCorners(g, i, 1, 1, Color.BLACK);
                drawHoverCorners(g, i);
            }

            // Draw item bounds for picking up
//			g.drawRect((int)(i.itemPosition(0, 0).x - Handler.get().getGameCamera().getxOffset()), (int)(i.itemPosition(0, 0).y - Handler.get().getGameCamera().getyOffset()), i.itemPosition(0, 0).width, i.itemPosition(0, 0).height);
        }
    }

    public void addItem(Item i) {
        addItem(i, false);
    }

    public void addItem(Item i, boolean isWorldSpawn) {
        items.add(i);
        if(!isWorldSpawn){
            added.add(i);
        }
    }

    private void drawHoverCorners(Graphics g, Item i, int xOffset, int yOffset, Color color){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(2));

        // Top left corner
        g2.drawLine((int) (xOffset + i.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() + 6 - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() - Handler.get().getGameCamera().getyOffset()));
        g2.drawLine((int) (xOffset + i.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + 6 - Handler.get().getGameCamera().getyOffset()));

        // Top right corner
        g2.drawLine((int) (xOffset + i.getX() + Item.ITEMWIDTH - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() + Item.ITEMWIDTH - 6 - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() - Handler.get().getGameCamera().getyOffset()));
        g2.drawLine((int) (xOffset + i.getX() + Item.ITEMWIDTH - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() + Item.ITEMWIDTH - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + 6 - Handler.get().getGameCamera().getyOffset()));

        // Bottom left corner
        g2.drawLine((int) (xOffset + i.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() + 6 - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - Handler.get().getGameCamera().getyOffset()));
        g2.drawLine((int) (xOffset + i.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - 6 - Handler.get().getGameCamera().getyOffset()));

        // Bottom right corner
        g2.drawLine((int) (xOffset + i.getX() + Item.ITEMWIDTH - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() + Item.ITEMWIDTH - 6 - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - Handler.get().getGameCamera().getyOffset()));
        g2.drawLine((int) (xOffset + i.getX() + Item.ITEMWIDTH - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() + Item.ITEMWIDTH - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - 6 - Handler.get().getGameCamera().getyOffset()));
    }

    private void drawHoverCorners(Graphics g, Item i) {
        drawHoverCorners(g, i, 0, 0, Color.GREEN);
    }

    // Getters & Setters

    public CopyOnWriteArrayList<Item> getItems() {
        return items;
    }

    public void setItems(CopyOnWriteArrayList<Item> items) {
        this.items = items;
    }

}
