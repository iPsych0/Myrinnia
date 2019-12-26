package dev.ipsych0.myrinnia.bank;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.ui.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.items.ui.ItemTooltip;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BankUI implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1728128423147794469L;
    public static int x;
    public static int y;
    public static int width;
    private static int height;
    public static boolean isOpen = false;
    private List<ItemSlot> invSlots = new ArrayList<>();
    private List<BankTab> tabs = new ArrayList<>();
    private BankTab openedTab;
    private static final int MAX_TABS = 10;
    public static boolean inventoryLoaded;
    public static boolean hasBeenPressed = false;
    private Rectangle bounds;
    private boolean itemSelected;
    private ItemStack currentSelectedSlot;
    private Color selectedColor = new Color(0, 255, 255, 62);
    private UIImageButton exit;
    public static BankUI lastOpenedWindow;
    private ItemTooltip itemTooltip;
    private UIManager uiManager;

    public BankUI() {
        width = 460;
        height = 313;
        x = Handler.get().getWidth() / 2 - width / 2;
        y = Handler.get().getHeight() / 2 - height / 2;

        uiManager = new UIManager();

        // Add all the tabs
        for (int i = 0; i < MAX_TABS; i++) {
            tabs.add(new BankTab(x + (width / 2) - ((MAX_TABS * 32 / 2)) + (i * 32), y + 32, i));
        }

        // Add the inventory slots
        for (int i = 0; i < BankTab.ROWS; i++) {
            for (int j = 0; j < BankTab.COLS; j++) {
                invSlots.add(new ItemSlot(x + (width / 2) + 17 + (i * (ItemSlot.SLOTSIZE)), y + 80 + (j * ItemSlot.SLOTSIZE), null));
            }
        }

        bounds = new Rectangle(x, y, width, height);
        exit = new UIImageButton(x + width - 36, y + 12, 24, 24, Assets.genericButton);

        uiManager.addObject(exit);

        itemTooltip = new ItemTooltip(x - 160, y);

        // Initially always open the first tab
        openedTab = tabs.get(0);
    }

    public void tick() {
        if (isOpen) {
            // Checks if the inventory should be refreshed
            if (!inventoryLoaded) {
                loadInventory();
                inventoryLoaded = true;
            }

            Rectangle mouse = Handler.get().getMouse();

            uiManager.tick();

            if (Player.isMoving || exit.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() || Handler.get().getKeyManager().escape) {
                exit();
                return;
            }

            /*
             * BankTab mouse interaction
             */
            for (BankTab tab : tabs) {
                tab.tick();
                if (tab.getBounds().contains(mouse)) {
                    tab.setHovering(true);
                    if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                        tab.setOpen(true);
                        openedTab = tab;
                        for (BankTab tempTab : tabs) {
                            if (!tempTab.equals(tab))
                                tempTab.setOpen(false);
                        }
                        hasBeenPressed = false;
                    } else if (itemSelected && !Handler.get().getMouseManager().isDragged()) {
                        if (tab.getBounds().contains(mouse)) {
                            // If the itemstack already holds an item
                            if (tab.findFreeSlot(currentSelectedSlot.getItem()) != -1) {
                                if (currentSelectedSlot.getItem().isStackable()) {
                                    // And if the item in the slot is stackable
                                    if (tab.getBankSlots().get(tab.findFreeSlot(currentSelectedSlot.getItem())).addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
                                        // Add the item back to the inventory
                                        currentSelectedSlot = null;
                                        itemSelected = false;
                                        hasBeenPressed = false;

                                    } else {
                                        // If we cannot add the item to an existing stack
                                        hasBeenPressed = false;
                                        return;
                                    }
                                } else {
                                    // If the item is not stackable, we can add the item
                                    tab.getBankSlots().get(tab.findFreeSlot(currentSelectedSlot.getItem()))
                                            .addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount());
                                    currentSelectedSlot = null;
                                    itemSelected = false;
                                    hasBeenPressed = false;
                                }
                            } else {
                                //
                                hasBeenPressed = false;
                                return;
                            }
                        }
                    }
                } else {
                    tab.setHovering(false);
                }
            }

            if (openedTab != null) {
                /*
                 * Bankslot mouse interaction
                 */
                for (ItemSlot is : openedTab.getBankSlots()) {
                    Rectangle slot = is.getBounds();
                    if (slot.contains(mouse) && Handler.get().getMouseManager().isRightPressed() && hasBeenPressed) {
                        if (is.getItemStack() != null && !Handler.get().invIsFull(is.getItemStack().getItem())) {
                            Handler.get().giveItem(is.getItemStack().getItem(), is.getItemStack().getAmount());
                            is.setItemStack(null);
                            inventoryLoaded = false;
                            hasBeenPressed = false;
                            return;
                        } else {
                            hasBeenPressed = false;
                            return;
                        }
                    }

                    // If an item is dragged
                    else if (Handler.get().getMouseManager().isDragged()) {
                        if (slot.contains(mouse) && hasBeenPressed && !itemSelected) {
                            hasBeenPressed = false;

                            // Stick the item to the mouse
                            if (currentSelectedSlot == null) {
                                if (is.getItemStack() != null) {
                                    currentSelectedSlot = is.getItemStack();
                                    is.setItemStack(null);
                                    itemSelected = true;
                                } else {
                                    hasBeenPressed = false;
                                    return;
                                }
                            }
                        }
                    }

                    // If the item is released
                    else if (itemSelected && !Handler.get().getMouseManager().isDragged()) {
                        if (slot.contains(mouse)) {
                            // If the itemstack already holds an item
                            if (is.getItemStack() != null) {
                                if (currentSelectedSlot.getItem().isStackable()) {
                                    // And if the item in the slot is stackable
                                    if (is.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
                                        // Add the item back to the inventory
                                        currentSelectedSlot = null;
                                        itemSelected = false;
                                        hasBeenPressed = false;

                                    } else {
                                        // If we cannot add the item to an existing stack
                                        hasBeenPressed = false;
                                        return;
                                    }
                                } else {
                                    // If the item is not stackable / we cannot add the item
                                    hasBeenPressed = false;
                                }
                            } else {
                                // If the item stack == null, we can safely add it.
                                is.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount());
                                currentSelectedSlot = null;
                                itemSelected = false;
                                hasBeenPressed = false;
                            }
                        }
                    }
                }

                /*
                 * Inventory mouse interaction
                 */
                int slotIndex = 0;
                for (ItemSlot is : invSlots) {
                    Rectangle slot = is.getBounds();
                    if (slot.contains(mouse) && Handler.get().getMouseManager().isRightPressed() && hasBeenPressed) {
                        if (is.getItemStack() != null) {
                            if (openedTab.findFreeSlot(is.getItemStack().getItem()) != -1) {
                                openedTab.getBankSlots().get(openedTab.findFreeSlot(is.getItemStack().getItem()))
                                        .addItem(is.getItemStack().getItem(), is.getItemStack().getAmount());
                                Handler.get().getInventory().removeBankItemSlot(is);
                                inventoryLoaded = false;
                                hasBeenPressed = false;
                                return;
                            } else {
                                hasBeenPressed = false;
                                return;
                            }
                        }
                    }

                    // If an item is dragged
                    else if (Handler.get().getMouseManager().isDragged()) {
                        if (slot.contains(mouse) && hasBeenPressed && !itemSelected) {
                            hasBeenPressed = false;

                            // Stick the item to the mouse
                            if (currentSelectedSlot == null) {
                                if (is.getItemStack() != null) {
                                    currentSelectedSlot = is.getItemStack();
                                    Handler.get().getInventory().removeBankItemSlot(is);
                                    itemSelected = true;
                                    inventoryLoaded = false;
                                } else {
                                    hasBeenPressed = false;
                                    return;
                                }
                            }
                        }
                    }

                    // If the item is released
                    else if (itemSelected && !Handler.get().getMouseManager().isDragged()) {
                        if (slot.contains(mouse)) {
                            // If the itemstack already holds an item
                            if (is.getItemStack() != null) {
                                if (currentSelectedSlot.getItem().isStackable()) {
                                    // And if the item in the slot is stackable
                                    if (is.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
                                        // Add the item back to the inventory
                                        currentSelectedSlot = null;
                                        itemSelected = false;
                                        hasBeenPressed = false;
                                        inventoryLoaded = false;
                                        return;
                                    } else {
                                        // If we cannot add the item to an existing stack
                                        hasBeenPressed = false;
                                        return;
                                    }
                                } else {
                                    // If the item is not stackable / we cannot add the item
                                    hasBeenPressed = false;
                                }
                            } else {
                                // If the item stack == null, we can safely add it.
                                Handler.get().getInventory().getItemSlots().get(slotIndex)
                                        .addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount());
                                System.out.println(slotIndex);
                                currentSelectedSlot = null;
                                itemSelected = false;
                                hasBeenPressed = false;
                                inventoryLoaded = false;
                                return;
                            }
                        }
                    }
                    slotIndex++;
                }
            }
        }
    }

    public static void open() {
        AbilityOverviewUI.exit();
        BankUI.isOpen = true;
    }

    public void exit() {
        if(Handler.get().getMouseManager().isLeftPressed()){
            MouseManager.justClosedUI = true;
        }
        Handler.get().getPlayer().setBankEntity(null);
        isOpen = false;
        hasBeenPressed = false;
        if (currentSelectedSlot != null) {
            Handler.get().giveItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount());
            currentSelectedSlot = null;
            itemSelected = false;
        }
        inventoryLoaded = false;
    }

    /*
     * Refreshes the inventory in the shopwindow
     */
    private void loadInventory() {
        for (int i = 0; i < Handler.get().getInventory().getItemSlots().size(); i++) {
            invSlots.get(i).setItemStack(Handler.get().getInventory().getItemSlots().get(i).getItemStack());
        }
    }

    public void render(Graphics2D g) {
        if (isOpen) {
            g.drawImage(Assets.uiWindow, x, y, width, height, null);

            uiManager.render(g);

            for (ItemSlot is : invSlots) {
                is.render(g);

                if(is.getItemStack() != null && is.getBounds().contains(Handler.get().getMouse())){
                    itemTooltip.render(is.getItemStack().getItem(), g);
                }
            }

            for (BankTab tab : tabs) {
                tab.render(g);
                if (tab.isOpen()) {
                    g.setColor(selectedColor);
                    g.fillRect(tab.x, tab.y, tab.width, tab.height);
                    for(ItemSlot is : tab.getBankSlots()){
                        if(is.getItemStack() != null && is.getBounds().contains(Handler.get().getMouse())){
                            itemTooltip.render(is.getItemStack().getItem(), g);
                        }
                    }
                }
            }

            if (currentSelectedSlot != null) {
                g.drawImage(currentSelectedSlot.getItem().getTexture(), Handler.get().getMouseManager().getMouseX() - 14,
                        Handler.get().getMouseManager().getMouseY() - 14, ItemSlot.SLOTSIZE - 4, ItemSlot.SLOTSIZE - 4, null);
                if (currentSelectedSlot.getItem().isStackable())
                    Text.drawString(g, Integer.toString(currentSelectedSlot.getAmount()), Handler.get().getMouseManager().getMouseX() - 14, Handler.get().getMouseManager().getMouseY() - 4, false, Color.YELLOW, Assets.font14);
            }

            if (exit.contains(Handler.get().getMouse()))
                g.drawImage(Assets.genericButton[0], exit.x, exit.y, exit.width, exit.height, null);
            else
                g.drawImage(Assets.genericButton[1], exit.x, exit.y, exit.width, exit.height, null);

            Text.drawString(g, "X", exit.x + 11, exit.y + 11, true, Color.YELLOW, Assets.font20);
        }
    }

    /*
     * Finds a free slot in the inventory
     * @returns: index if found, -1 if inventory is full
     */
    public int findFreeSlot(Item item) {
        boolean firstFreeSlotFound = false;
        int index = -1;
        for (int i = 0; i < invSlots.size(); i++) {
            if (invSlots.get(i).getItemStack() == null) {
                if (!firstFreeSlotFound) {
                    firstFreeSlotFound = true;
                    index = i;
                }
            } else if (invSlots.get(i).getItemStack() != null && !item.isStackable()) {
            } else if (invSlots.get(i).getItemStack() != null && item.isStackable()) {
                if (invSlots.get(i).getItemStack().getItem().getId() == item.getId()) {
                    return i;
                }
            }
        }
        if (index != -1)
            return index;

        Handler.get().sendMsg("Your inventory is full.");
        return -1;
    }

    public BankTab getOpenedTab() {
        return openedTab;
    }

    public void setOpenedTab(BankTab openedTab) {
        this.openedTab = openedTab;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

}
