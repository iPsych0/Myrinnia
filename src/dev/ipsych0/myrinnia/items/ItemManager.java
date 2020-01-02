package dev.ipsych0.myrinnia.items;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ItemManager implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1092891818645452920L;
    private List<Item> items;
    private Collection<Item> deleted;
    private Collection<Item> added;
    private static boolean soundPlayed = false;
    private static int lastPlayed = 0;
    private transient Item lastHovered;

    public ItemManager() {
        items = new ArrayList<>();
        deleted = new ArrayList<>();
        added = new ArrayList<>();
    }

    public void tick() {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item i = it.next();

            // Check if we're hovering over the item
            i.setHovering(i.itemPosition(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(Handler.get().getMouse()));

            // Checks player's pause for any items nearby to pick up
            if (Handler.get().getMouseManager().isRightPressed() && Handler.get().getPlayer().itemPickupRadius().intersects(i.itemPosition(0, 0))) {
                if (!Handler.get().getPlayer().hasRightClickedUI(Handler.get().getMouse())) {
                    if (i.pickUpItem(i)) {
                        if (i.isPickedUp()) {
                            if (!soundPlayed) {
                                Handler.get().playEffect("ui/pickup.ogg");
                                soundPlayed = true;
                            }
                            deleted.add(i);
                        }
                    }
                }
            }

            // Adds small delay to sounds to prevent them from stacking when picking up multiple items at once
            if (soundPlayed) {
                lastPlayed++;
                if (lastPlayed >= 5) {
                    soundPlayed = false;
                    lastPlayed = 0;
                }
            }
            i.tick();
        }

        // If non-worldspawn Items are dropped, start timer for despawning
        Iterator<Item> addedIt = added.iterator();
        long currentTime = System.currentTimeMillis();

        while (addedIt.hasNext()) {
            Item i = addedIt.next();

            // If item is picked up, reset the timer
            if (i.isPickedUp()) {
                i.setRespawnTimer(i.getRespawnTime());
                deleted.add(i);
                addedIt.remove();
            }

            // If the timer expires, remove the item
            if (((currentTime - i.getTimeDropped()) / 1000L) >= i.getRespawnTime()) {
                deleted.add(i);
                addedIt.remove();
            }
        }


        // If Item's timer is 0, remove the items from the world.
        if (deleted.size() > 0) {
            items.removeAll(deleted);
            deleted.clear();
        }
    }

    public void render(Graphics2D g) {
        int count = 0;
        for (Item i : items) {
            i.render(g);
            drawHoverCorners(g, i, 1, 1, Color.BLACK);
            drawHoverCorners(g, i);
            if (i.isHovering()) {
                count++;
                lastHovered = i;
            }

            // Draw item bounds for picking up
//			g.drawRect((int)(i.itemPosition(0, 0).x - Handler.get().getGameCamera().getxOffset()), (int)(i.itemPosition(0, 0).y - Handler.get().getGameCamera().getyOffset()), i.itemPosition(0, 0).width, i.itemPosition(0, 0).height);
        }

        if (count == 0) {
            lastHovered = null;
        }

        if (lastHovered != null && count > 1) {
            Text.drawString(g, "+" + count, lastHovered.getX() + Item.ITEMWIDTH - (int) Handler.get().getGameCamera().getxOffset(),
                    lastHovered.getY() - (int) Handler.get().getGameCamera().getyOffset(), false, Color.GREEN, Assets.font20);
        }
    }

    public void postRender(Graphics2D g) {
        if (lastHovered != null) {
            g.drawImage(Assets.uiWindow, Handler.get().getWidth() / 2 - 100, 1, 200, 50, null);
            Text.drawString(g, lastHovered.getName(), Handler.get().getWidth() / 2, 12, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, lastHovered.getItemRarity().toString(), Handler.get().getWidth() / 2, 26, true, ItemRarity.getColor(lastHovered), Assets.font14);
        }

        for (Item item : items) {
            // If we're not hovering, check if Item is behind postRender tile and draw the overlay anyway
            int layers = Handler.get().getWorld().getLayers().length;
            boolean shouldRender = false;
            for (int i = 0; i < layers; i++) {
                Tile currentTile = Handler.get().getWorld().getTile(i, (item.getX() + Item.ITEMWIDTH / 2) / 32, (item.getY() + Item.ITEMHEIGHT / 2) / 32);
                if (currentTile != null && currentTile.isPostRendered()) {
                    shouldRender = true;
                    break;
                }
            }

            if (shouldRender) {
                drawHoverCorners(g, item, 1, 1, Color.BLACK);
                drawHoverCorners(g, item);
            }
        }
    }

    public void addItem(Item i) {
        addItem(i, false);
    }

    public void addItem(Item i, boolean isWorldSpawn) {
        items.add(i);
        if (!isWorldSpawn) {
            added.add(i);
        }
    }

    private void drawHoverCorners(Graphics2D g, Item i, int xOffset, int yOffset, Color color) {
        g.setColor(color);
        Stroke original = g.getStroke();
        g.setStroke(new BasicStroke(2));

        // Top left corner
        g.drawLine((int) (xOffset + i.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() + 6 - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() - Handler.get().getGameCamera().getyOffset()));
        g.drawLine((int) (xOffset + i.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + 6 - Handler.get().getGameCamera().getyOffset()));

        // Top right corner
        g.drawLine((int) (xOffset + i.getX() + Item.ITEMWIDTH - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() + Item.ITEMWIDTH - 6 - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() - Handler.get().getGameCamera().getyOffset()));
        g.drawLine((int) (xOffset + i.getX() + Item.ITEMWIDTH - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() + Item.ITEMWIDTH - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + 6 - Handler.get().getGameCamera().getyOffset()));

        // Bottom left corner
        g.drawLine((int) (xOffset + i.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() + 6 - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - Handler.get().getGameCamera().getyOffset()));
        g.drawLine((int) (xOffset + i.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - 6 - Handler.get().getGameCamera().getyOffset()));

        // Bottom right corner
        g.drawLine((int) (xOffset + i.getX() + Item.ITEMWIDTH - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() + Item.ITEMWIDTH - 6 - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - Handler.get().getGameCamera().getyOffset()));
        g.drawLine((int) (xOffset + i.getX() + Item.ITEMWIDTH - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + i.getX() + Item.ITEMWIDTH - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + i.getY() + Item.ITEMHEIGHT - 6 - Handler.get().getGameCamera().getyOffset()));

        g.setStroke(original);
    }

    private void drawHoverCorners(Graphics2D g, Item i) {
        drawHoverCorners(g, i, 0, 0, Color.GREEN);
    }

    // Getters & Setters

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
