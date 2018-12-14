package dev.ipsych0.myrinnia.items;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class EquipmentWindow implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7002329052826588263L;
    public static boolean isOpen = true;
    private int x, y;
    private int width, height;
    public static boolean hasBeenPressed = false;

    private int numCols = 3;
    private int numRows = 4;

    private CopyOnWriteArrayList<EquipmentSlot> equipmentSlots;
    private ItemStack currentSelectedSlot;
    public static boolean itemSelected;
    private Rectangle windowBounds;

    public EquipmentWindow() {
        this.width = numCols * (EquipmentSlot.SLOTSIZE + 11) + 3;
        this.height = numRows * (EquipmentSlot.SLOTSIZE + 8);
        this.x = Handler.get().getWidth() - width - 8;
        this.y = Handler.get().getInventory().getHeight() + 16;
        windowBounds = new Rectangle(x, y, width, height);

        equipmentSlots = new CopyOnWriteArrayList<EquipmentSlot>();

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                if (j == (numRows)) {
                    x += 8;
                }

                equipmentSlots.add(new EquipmentSlot(x + 17 + (i * (EquipmentSlot.SLOTSIZE)), y + 32 + (j * EquipmentSlot.SLOTSIZE), null));

                if (j == (numRows)) {
                    x -= 8;
                }
            }
        }
    }

    public void tick() {
        if (isOpen) {
            Rectangle mouse = Handler.get().getMouse();

            for (EquipmentSlot es : equipmentSlots) {

                es.tick();

                Rectangle temp2 = new Rectangle(es.getX(), es.getY(), EquipmentSlot.SLOTSIZE, EquipmentSlot.SLOTSIZE);

                // If mouse is dragged
                if (Handler.get().getMouseManager().isDragged()) {
                    if (temp2.contains(mouse) && !hasBeenPressed && !itemSelected) {
                        hasBeenPressed = true;

                        // Stick the item to the mouse
                        if (currentSelectedSlot == null) {
                            if (es.getEquipmentStack() != null) {
                                currentSelectedSlot = es.getEquipmentStack();
                                Handler.get().getPlayer().removeEquipmentStats(currentSelectedSlot.getItem().getEquipSlot());
                                System.out.println("Currently holding: " + es.getEquipmentStack().getItem().getName());
                                es.setItem(null);
                                itemSelected = true;
                            } else {
                                hasBeenPressed = false;
                                return;
                            }
                        }
                    }
                }

                // If right-clicked on an item
                if (temp2.contains(mouse) && Handler.get().getMouseManager().isRightPressed() && !hasBeenPressed) {
                    if (es.getEquipmentStack() != null) {
                        hasBeenPressed = true;
                        // Unequip the item and remove the equipment stats
                        if (Handler.get().getInventory().findFreeSlot(es.getEquipmentStack().getItem()) == -1) {
                            hasBeenPressed = false;
                            return;
                        }
                        Handler.get().getPlayer().removeEquipmentStats(es.getEquipmentStack().getItem().getEquipSlot());
                        Handler.get().getInventory().getItemSlots().get(Handler.get().getInventory().findFreeSlot(es.getEquipmentStack().getItem())).addItem(es.getEquipmentStack().getItem(), es.getEquipmentStack().getAmount());
                        es.setItem(null);
                        BankUI.inventoryLoaded = false;
                        hasBeenPressed = false;
                    } else {
                        hasBeenPressed = false;
                        return;
                    }
                }

                // If dragging an item outside the equipment window
                if (itemSelected && !Handler.get().getMouseManager().isDragged()) {
                    if (Handler.get().getMouseManager().getMouseX() <= this.x) {
                        // Drop the item
                        Handler.get().dropItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount(), (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY());
                        currentSelectedSlot = null;
                        hasBeenPressed = false;
                        itemSelected = false;
                    }
                }

                // If releasing a dragged item inside the equipment window
                if (itemSelected && !Handler.get().getMouseManager().isDragged()) {
                    if (temp2.contains(mouse)) {
                        if (getEquipmentSlots().get(Handler.get().getInventory().checkEquipmentSlot(currentSelectedSlot.getItem())).equipItem((currentSelectedSlot.getItem()))) {
                            // Add the stats back and put the item back
                            Handler.get().getPlayer().addEquipmentStats(currentSelectedSlot.getItem().getEquipSlot());
                            currentSelectedSlot = null;
                            itemSelected = false;
                            hasBeenPressed = false;

                        }
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        if (isOpen) {

            g.drawImage(Assets.equipScreen, x, y, 132, 348, null);
//			g.setColor(interfaceColour);
//			g.fillRect(x - 16, y - 16, width + 32, height);
//			g.setColor(Color.BLACK);
//			g.drawRect(x - 16, y - 16, width + 32, height);
            Text.drawString(g, "Equipment", x + 34, y + 24, false, Color.YELLOW, Assets.font14);

            for (int i = 0; i < equipmentSlots.size(); i++) {
                equipmentSlots.get(i).render(g, Assets.equipmentPlaceHolders[i]);
            }

            Rectangle mouse = Handler.get().getMouse();

            for (EquipmentSlot es : equipmentSlots) {
                Rectangle slot = new Rectangle(es.getX(), es.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);

                // If hovering over an item in the inventory, draw the tooltip
                if (slot.contains(mouse) && es.getEquipmentStack() != null) {
                    g.drawImage(Assets.shopWindow, x - 147, y, 148, 122, null);

                    Text.drawString(g, es.getEquipmentStack().getItem().getName(), x - 141, y + 16, false, Color.YELLOW, Assets.font14);

                    /*
                     * Draw the colour of the item's rarity
                     */
                    g.setColor(ItemRarity.getColor(es.getEquipmentStack().getItem()));
                    Text.drawString(g, es.getEquipmentStack().getItem().getItemRarity().toString(), x - 141, y + 32, false, g.getColor(), Assets.font14);

                    if (es.getEquipmentStack().getItem().getEquipSlot() != 12) {
                        // Only compare stats if an item is actually equipped
                        if (Handler.get().getEquipment().getEquipmentSlots().get(es.getEquipmentStack().getItem().getEquipSlot()).getEquipmentStack() != null) {
                            /*
                             * Draw item stats
                             */
                            g.setColor(Color.YELLOW);
                            Text.drawString(g, "Power: " + es.getEquipmentStack().getItem().getPower(), x - 141, y + 48, false, g.getColor(), Assets.font14);
                            Text.drawString(g, "Defence: " + es.getEquipmentStack().getItem().getDefence(), x - 141, y + 64, false, g.getColor(), Assets.font14);
                            Text.drawString(g, "Vitality: " + es.getEquipmentStack().getItem().getVitality(), x - 141, y + 80, false, g.getColor(), Assets.font14);
                            Text.drawString(g, "ATK Speed: " + es.getEquipmentStack().getItem().getAttackSpeed(), x - 141, y + 96, false, g.getColor(), Assets.font14);
                            Text.drawString(g, "Mov. Speed: " + es.getEquipmentStack().getItem().getMovementSpeed(), x - 141, y + 112, false, g.getColor(), Assets.font14);
                        }
                    }
                }
            }

            g.drawImage(Assets.shopWindow, x + 10, y + height + 12, 112, 160, null);

            int index = 0;
            Text.drawString(g, "Stats ", x + (width / 2), y + height + 24 + (16 * index++), true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Power = " + Integer.toString(Handler.get().getPlayer().getPower()), x + (width / 6) - 8, y + height + 32 + (16 * index++), false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Defence = " + Integer.toString(Handler.get().getPlayer().getDefence()), x + (width / 6) - 8, y + height + 32 + (16 * index++), false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Vitality = " + Integer.toString(Handler.get().getPlayer().getVitality()), x + (width / 6) - 8, y + height + 32 + (16 * index++), false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "ATK Spd. = " + Float.toString(Handler.get().getPlayer().getAttackSpeed()), x + (width / 6) - 8, y + height + 32 + (16 * index++), false, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Mov. Spd. = " + Float.toString(Handler.get().getPlayer().getSpeed()), x + (width / 6) - 8, y + height + 32 + (16 * index++), false, Color.YELLOW, Assets.font14);

            if (currentSelectedSlot != null) {
                g.drawImage(currentSelectedSlot.getItem().getTexture(), Handler.get().getMouseManager().getMouseX(),
                        Handler.get().getMouseManager().getMouseY(), null);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public CopyOnWriteArrayList<EquipmentSlot> getEquipmentSlots() {
        return equipmentSlots;
    }

    public void setEquipmentSlots(CopyOnWriteArrayList<EquipmentSlot> equipmentSlots) {
        this.equipmentSlots = equipmentSlots;
    }

    public Rectangle getWindowBounds() {
        return windowBounds;
    }

    public void setWindowBounds(Rectangle windowBounds) {
        this.windowBounds = windowBounds;
    }

    public ItemStack getCurrentSelectedSlot() {
        return currentSelectedSlot;
    }

    public void setCurrentSelectedSlot(ItemStack currentSelectedSlot) {
        this.currentSelectedSlot = currentSelectedSlot;
    }
}
