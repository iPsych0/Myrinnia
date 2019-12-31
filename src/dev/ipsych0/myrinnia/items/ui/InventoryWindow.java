package dev.ipsych0.myrinnia.items.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.chatwindow.Filter;
import dev.ipsych0.myrinnia.crafting.ui.CraftingSlot;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.equipment.EquipmentSlot;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.*;

public class InventoryWindow implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2445807581436976803L;
    public static boolean isOpen = true;
    public static boolean equipPressed = false;
    public static boolean hasBeenPressed = false;

    private int x, y;
    private int width, height;

    private int numCols = 3;
    private int numRows = 10;

    private List<ItemSlot> itemSlots;
    private ItemStack currentSelectedSlot;
    private ItemStack itemSwap;
    private ItemStack equipSwap;
    public static boolean itemSelected;
    private Rectangle windowBounds;
    private ItemTooltip itemTooltip;
    private Set<Item> usedItems;

    public InventoryWindow() {
        width = numCols * (ItemSlot.SLOTSIZE + 11) + 3;
        height = numRows * (ItemSlot.SLOTSIZE + 11) - 58;
        this.x = Handler.get().getWidth() - width - 8;
        this.y = 8;
        windowBounds = new Rectangle(x, y, width, height);
        itemSlots = new ArrayList<>();
        usedItems = new HashSet<>();

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                itemSlots.add(new ItemSlot(x + 17 + (i * (ItemSlot.SLOTSIZE)), y + 32 + (j * ItemSlot.SLOTSIZE), null));
            }
        }

        itemTooltip = new ItemTooltip(x - 160, y);

        itemSlots.get(findFreeSlot(Item.magicSword)).addItem(Item.magicSword, 1);

//        for (int i = 24; i < 42; i++) {
//            itemSlots.get(findFreeSlot(Item.items[i])).addItem(Item.items[i], 1);
//        }
    }


    public void tick() {
        Iterator<Item> it2 = usedItems.iterator();
        while (it2.hasNext()) {
            Item i = it2.next();
            // Manage item using
            if (i.getUse() != null && i.isUsed()) {
                i.setUsedTimer(i.getUsedTimer() + 1);
                if (i.getUsedTimer() >= i.getUseCooldown()) {
                    i.setUsed(false);
                    i.setUsedTimer(0);
                    it2.remove();
                }
            }

        }

        if (isOpen) {
            Rectangle mouse = Handler.get().getMouse();

            Iterator<ItemSlot> it = itemSlots.iterator();
            while (it.hasNext()) {
                ItemSlot is = it.next();

                is.tick();

                Rectangle slot = is.getBounds();

                // If an item is dragged
                if (Handler.get().getMouseManager().isDragged()) {
                    if (slot.contains(mouse) && !hasBeenPressed && !itemSelected) {
                        hasBeenPressed = true;

                        // Stick the item to the mouse
                        if (currentSelectedSlot == null) {
                            if (is.getItemStack() != null) {
                                currentSelectedSlot = is.getItemStack();
                                is.setItemStack(null);
                                itemSelected = true;
                                BankUI.inventoryLoaded = false;
                            } else {
                                hasBeenPressed = false;
                                return;
                            }
                        }
                    }
                }

                // If the item is released
                if (itemSelected && !Handler.get().getMouseManager().isDragged()) {
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
                                    BankUI.inventoryLoaded = false;

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
                            BankUI.inventoryLoaded = false;
                        }
                    }
                    if (CraftingUI.isOpen) {
                        for (CraftingSlot cs : Handler.get().getCraftingUI().getCraftingSlots()) {
                            if (itemSelected && cs.getBounds().contains(mouse) && !Handler.get().getMouseManager().isDragged()) {
                                // If the itemstack already holds an item
                                if (cs.getItemStack() != null) {
                                    if (currentSelectedSlot.getItem().isStackable()) {
                                        // And if the item in the slot is stackable
                                        if (cs.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
                                            // Add the item back to the inventory
                                            currentSelectedSlot = null;
                                            itemSelected = false;
                                            hasBeenPressed = false;
                                            Handler.get().getCraftingUI().findRecipe();

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
                                    cs.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount());
                                    currentSelectedSlot = null;
                                    itemSelected = false;
                                    hasBeenPressed = false;
                                    Handler.get().getCraftingUI().findRecipe();
                                }
                            }
                        }
                    }
                }

                // If the item is dragged outside the inventory
                if (itemSelected && !Handler.get().getMouseManager().isDragged()) {
                    if (Handler.get().getMouseManager().getMouseX() <= this.x) {
                        // Drop the item
                        if (!BankUI.isOpen && !CraftingUI.isOpen) {
                            Handler.get().dropItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount(), (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY());
                            currentSelectedSlot = null;
                            itemSelected = false;
                            BankUI.inventoryLoaded = false;
                        }
                        hasBeenPressed = false;
                    }
                }

                // If item is right-clicked
                if (slot.contains(mouse) && Handler.get().getMouseManager().isRightPressed() && equipPressed && !hasBeenPressed && !Handler.get().getMouseManager().isDragged() && !CraftingUI.isOpen && !ShopWindow.isOpen) {
                    if (is.getItemStack() != null) {
                        Item item = is.getItemStack().getItem();
                        if (item.getUse() != null) {
                            boolean hasUsed = false;
                            for (Item i : usedItems) {
                                if (i.getId() == item.getId()) {
                                    double cooldownLeft = Handler.get().roundOff(((double) i.getUseCooldown() / 60d) - (double) i.getUsedTimer() / (double) i.getUseCooldown());
                                    Handler.get().sendMsg("Item use on cooldown for another " + cooldownLeft + "s.", Filter.INFO);
                                    hasUsed = true;
                                    break;
                                }
                            }
                            if (!hasUsed) {
                                item.setUsed(true);
                                is.getItemStack().getItem().getUse().use(item);
                                addUsedItem(item);
                            }
                            hasBeenPressed = false;
                            equipPressed = false;
                            return;
                        }
                        if (Handler.get().getPlayer().isInCombat()) {
                            Handler.get().sendMsg("You cannot equip items while in combat.");
                            hasBeenPressed = false;
                            equipPressed = false;
                            return;
                        }
                        if (is.getItemStack().getItem().getEquipSlot() == EquipSlot.None.getSlotId()) {
                            // If the item's equipmentslot = 12, that means it's unequippable, so return
                            Handler.get().sendMsg("You cannot use " + is.getItemStack().getItem().getName() + ".");
                            equipPressed = false;
                            hasBeenPressed = false;
                            return;
                        }

                        // If the item's equipmentslot is a valid slot
                        if (is.getItemStack().getItem().getEquipSlot() != EquipSlot.None.getSlotId()) {
                            if (Handler.get().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).getEquipmentStack() != null &&
                                    is.getItemStack().getItem().getId() ==
                                            Handler.get().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).getEquipmentStack().getItem().getId()) {
                                // If trying to equip the exact same item, return message
                                Handler.get().sendMsg("You've already equipped this item!");
                                equipPressed = false;
                                hasBeenPressed = false;
                                return;
                            }

                            // If we don't have the required level to equip that item, return
                            if (is.getItemStack().getItem().getRequirements() != null && is.getItemStack().getItem().getRequirements().length > 0) {
                                StringBuilder missingReqs = new StringBuilder();
                                boolean missing = false;
                                boolean isCombat = false;
                                int combatLevelReq = 0;
                                for (int i = 0; i < is.getItemStack().getItem().getRequirements().length; i++) {
                                    if (is.getItemStack().getItem().getRequirements()[i].getStat().getLevel() < is.getItemStack().getItem().getRequirements()[i].getLevel()) {
                                        missing = true;
                                        if (i < is.getItemStack().getItem().getRequirements().length - 1) {
                                            missingReqs.append(is.getItemStack().getItem().getRequirements()[i].getLevel()).append(" ").append(is.getItemStack().getItem().getRequirements()[i].getStat().toString().toLowerCase()).append(" and ");
                                        } else {
                                            missingReqs.append(is.getItemStack().getItem().getRequirements()[i].getLevel()).append(" ").append(is.getItemStack().getItem().getRequirements()[i].getStat().toString().toLowerCase()).append(" points");
                                        }

                                        if (is.getItemStack().getItem().getRequirements()[i].getStat() == CharacterStats.Combat) {
                                            isCombat = true;
                                            combatLevelReq = is.getItemStack().getItem().getRequirements()[i].getLevel();
                                        }
                                    }
                                }
                                if (missing) {
                                    if (isCombat) {
                                        Handler.get().sendMsg("You need level " + combatLevelReq + " combat to equip this item.");
                                    } else {
                                        Handler.get().sendMsg("You need " + missingReqs + " to equip this item.");
                                    }
                                    equipPressed = false;
                                    hasBeenPressed = false;
                                    return;
                                }
                            }

                            // Play the equip sound
                            Handler.get().playEffect("ui/equip.ogg");

                            // If we have no item equipped in that slot
                            if (Handler.get().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).equipItem(is.getItemStack().getItem())) {
                                Handler.get().getPlayer().addEquipmentStats(is.getItemStack().getItem().getEquipSlot());
                                // Add equipment stats and subtract 1 from the item in our inventory
                                if (is.getItemStack().getAmount() >= 2) {
                                    is.getItemStack().setAmount(is.getItemStack().getAmount() - 1);
                                } else {
                                    // Or if only 1 item left, set the stack to null
                                    is.setItemStack(null);
                                }
                                equipPressed = false;
                                hasBeenPressed = false;
                                BankUI.inventoryLoaded = false;
                                return;
                            } else {

                                // Set the swaps
                                itemSwap = is.getItemStack();
                                equipSwap = Handler.get().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).getEquipmentStack();

                                // Remove the equipment stats
                                Handler.get().getPlayer().removeEquipmentStats(is.getItemStack().getItem().getEquipSlot());

                                // If more than one of the item
                                if (is.getItemStack().getAmount() >= 2) {
                                    // Subtract one from the inventory stack and then swap
                                    is.getItemStack().setAmount(is.getItemStack().getAmount() - 1);
                                    Handler.get().giveItem(equipSwap.getItem(), equipSwap.getAmount());
                                    Handler.get().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(is.getItemStack().getItem())).setItem(new ItemStack(itemSwap.getItem(), 1));

                                } else {
                                    // Otherwise, swap the items and set the inventory stack to null
                                    is.setItemStack(null);
                                    Handler.get().giveItem(equipSwap.getItem(), equipSwap.getAmount());
                                    Handler.get().getEquipment().getEquipmentSlots().get(checkEquipmentSlot(itemSwap.getItem())).setItem(itemSwap);

                                }

                                // Add the equipment stats after equipping
                                Handler.get().getPlayer().addEquipmentStats(itemSwap.getItem().getEquipSlot());

                                // Set the swaps back to null
                                equipPressed = false;
                                hasBeenPressed = false;
                                itemSwap = null;
                                equipSwap = null;
                                BankUI.inventoryLoaded = false;
                            }
                        } else {
                            equipPressed = false;
                            hasBeenPressed = false;
                            return;
                        }
                    } else {
                        equipPressed = false;
                        hasBeenPressed = false;
                        return;
                    }
                }

                // If right-clicked on an item while CraftingUI is open
                if (CraftingUI.isOpen) {
                    if (slot.contains(mouse) && Handler.get().getMouseManager().isRightPressed() && equipPressed && !hasBeenPressed && !Handler.get().getMouseManager().isDragged()) {

                        hasBeenPressed = true;
                        if (is.getItemStack() != null) {
                            if (Handler.get().getCraftingUI().findFreeSlot(is.getItemStack().getItem()) == -1) {
                                // If all crafting slots are full, return
                                hasBeenPressed = false;
                                Handler.get().sendMsg("You cannot add more than 4 items to the crafting window.");
                                equipPressed = false;
                                return;
                            } else {
                                // Otherwise, remove the stack from the inventory and put it in a crafting slot
                                Handler.get().getCraftingUI().getCraftingSlots().get(Handler.get().getCraftingUI().findFreeSlot(is.getItemStack().getItem())).addItem(is.getItemStack().getItem(), is.getItemStack().getAmount());
                                is.setItemStack(null);
                                // Update if there is a possible recipe
                                Handler.get().getCraftingUI().findRecipe();
                                hasBeenPressed = false;
                                return;
                            }
                        } else {
                            hasBeenPressed = false;
                            return;
                        }
                    }
                }
            }
        }
    }

    public void render(Graphics2D g) {
        if (isOpen) {
            g.drawImage(Assets.uiWindow, x, y, width, height, null);
            Text.drawString(g, "Inventory", x + 37, y + 24, false, Color.YELLOW, Assets.font14);

            Rectangle mouse = Handler.get().getMouse();

            for (int i = itemSlots.size() - 1; i >= 0; i--) {

                itemSlots.get(i).render(g);

                if (currentSelectedSlot != null) {
                    g.drawImage(currentSelectedSlot.getItem().getTexture(), Handler.get().getMouseManager().getMouseX() - 14,
                            Handler.get().getMouseManager().getMouseY() - 14, ItemSlot.SLOTSIZE - 4, ItemSlot.SLOTSIZE - 4, null);
                    if (currentSelectedSlot.getItem().isStackable())
                        Text.drawString(g, getAbbrevRenderAmount(currentSelectedSlot), Handler.get().getMouseManager().getMouseX() - 14, Handler.get().getMouseManager().getMouseY() - 4, false, Color.YELLOW, Assets.font14);
                }

                Rectangle temp2 = itemSlots.get(i).getBounds();


                // If hovering over an item in the inventory, draw the tooltip
                if (temp2.contains(mouse) && itemSlots.get(i).getItemStack() != null) {
                    itemTooltip.render(itemSlots.get(i).getItemStack().getItem(), g);
                }
            }
        }
    }

    public void addUsedItem(Item i) {
        usedItems.add(i);
    }

    public String getAbbrevRenderAmount(ItemStack itemStack) {
        if (itemStack.getAmount() >= 10_000 && itemStack.getAmount() < 100_000) {
            return Integer.toString(itemStack.getAmount()).substring(0, 2) + "k";
        } else if (itemStack.getAmount() >= 100_000 && itemStack.getAmount() < 1_000_000) {
            return Integer.toString(itemStack.getAmount()).substring(0, 3) + "k";
        } else if (itemStack.getAmount() >= 1_000_000) {
            // Move the decimal point 1 place per extra 0
            int offset = Integer.toString(itemStack.getAmount()).length() - 7;
            String amt = Integer.toString(itemStack.getAmount()).substring(0, 2 + offset);

            // Add decimal place for millions only if it doesn't end in 0
            if (amt.endsWith("0")) {
                amt = amt.substring(0, 1 + offset) + "M";
            } else {
                amt = amt.substring(0, 1 + offset) + "." + amt.substring(1 + offset) + "M";
            }
            return amt;
        } else {
            return Integer.toString(itemStack.getAmount());
        }
    }

    /*
     * Finds a free slot in the inventory
     * @returns: index if found, -1 if inventory is full
     */
    public int findFreeSlot(Item item) {
        boolean firstFreeSlotFound = false;
        int index = -1;
        for (int i = 0; i < itemSlots.size(); i++) {
            if (itemSlots.get(i).getItemStack() == null) {
                if (!firstFreeSlotFound) {
                    firstFreeSlotFound = true;
                    index = i;
                }
            } else if (itemSlots.get(i).getItemStack() != null && !item.isStackable()) {
            } else if (itemSlots.get(i).getItemStack() != null && item.isStackable()) {
                if (itemSlots.get(i).getItemStack().getItem().getId() == item.getId()) {
                    return i;
                }
            }
        }
        if (index != -1)
            return index;

        Handler.get().sendMsg("Your inventory is full.");
        return -1;
    }

    public void giveItem(Item item, int amount) {
        int playerX = (int) Handler.get().getPlayer().getX();
        int playerY = (int) Handler.get().getPlayer().getY();
        if (!item.isStackable()) {
            if (findFreeSlot(item) == -1) {
                if (amount >= 1) {
                    Handler.get().dropItem(item, amount, playerX, playerY);
                    if (amount != 1)
                        giveItem(item, (amount - 1));
                    else
                        Handler.get().sendMsg("The item(s) were dropped to the floor.");
                }
            } else {
                if (amount >= 1) {
                    getItemSlots().get(findFreeSlot(item)).addItem(item, amount);
                    giveItem(item, (amount - 1));
                }
            }
        } else {
            if (findFreeSlot(item) == -1) {
                Handler.get().dropItem(item, amount, playerX, playerY);
                Handler.get().sendMsg("The item(s) were dropped to the floor.");
            } else {
                getItemSlots().get(findFreeSlot(item)).addItem(item, amount);
            }
        }
    }

    /*
     * Checks the inventory for a given item & quantity met
     * @returns boolean: true if player has item + quantity, false if player doesn't have item or doesn't have enough
     */
    public boolean playerHasItem(Item item, int amount) {
        boolean found = false;
        int foundAmount = 0;
        for (int i = 0; i < itemSlots.size(); i++) {
            // Skip empty slots
            if (itemSlots.get(i).getItemStack() == null) {
                continue;
            }

            //If the item is found
            if (item.getId() == itemSlots.get(i).getItemStack().getItem().getId()) {
                if ((itemSlots.get(i).getItemStack().getAmount() - amount) < 0) {
                    foundAmount += itemSlots.get(i).getItemStack().getAmount();
                    if (foundAmount >= amount) {
                        found = true;
                        break;
                    }
                } else if ((itemSlots.get(i).getItemStack().getAmount() - amount) >= 0) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    /*
     * Checks if the player has the item+quantity and removes it
     * @returns boolean: true if successful, false if item+quantity requirement not met
     */
    public boolean removeItem(Item item, int amount) {
        if (!playerHasItem(item, amount)) {
            Handler.get().sendMsg("You don't have " + amount + "x " + item.getName().toLowerCase() + ".");
            return false;
        }

        List<Integer> matchSlots = new ArrayList<>();
        int foundAmount = 0;
        int leftOverAmount;
        for (int i = 0; i < itemSlots.size(); i++) {
            if (itemSlots.get(i).getItemStack() == null) {
                continue;
            }
            if (item.getId() == itemSlots.get(i).getItemStack().getItem().getId()) {
                if ((itemSlots.get(i).getItemStack().getAmount() - amount) < 0) {
                    matchSlots.add(i);
                    foundAmount += itemSlots.get(i).getItemStack().getAmount();
                    if (foundAmount >= amount) {
                        leftOverAmount = foundAmount - amount;
                        for (int j = 0; j < matchSlots.size(); j++) {
                            if (j == matchSlots.size() - 1) {
                                itemSlots
                                        .get(matchSlots.get(j))
                                        .getItemStack()
                                        .setAmount(leftOverAmount);
                                return true;
                            }
                            foundAmount -= itemSlots.get(matchSlots.get(j)).getItemStack().getAmount();
                            itemSlots.get(matchSlots.get(j)).setItemStack(null);
                        }
                    }
                } else if ((itemSlots.get(i).getItemStack().getAmount() - amount) == 0) {
                    itemSlots.get(i).setItemStack(null);
                    return true;
                } else if ((itemSlots.get(i).getItemStack().getAmount() - amount) >= 1) {
                    itemSlots.get(i).getItemStack().setAmount(itemSlots.get(i).getItemStack().getAmount() - amount);
                    return true;
                }
            }
        }
        return false;
    }

    public void empty() {
        for (ItemSlot is : itemSlots) {
            is.setItemStack(null);
        }
    }

    /*
     * Checks if the player has the item+quantity and removes it
     * @returns boolean: true if successful, false if item+quantity requirement not met
     */
    public boolean removeBankItemSlot(ItemSlot is) {
        for (ItemSlot itemSlot : itemSlots) {
            if (itemSlot.getItemStack() == is.getItemStack()) {
                itemSlot.setItemStack(null);
                return true;
            }
        }
        return false;
    }

    /*
     * Iterates over the inventory to see if there are any free slots
     * @returns boolean: true if full, false if not full
     */
    public boolean inventoryIsFull(Item item) {
        int emptySlots = 0;
        for (ItemSlot itemSlot : itemSlots) {
            if (itemSlot.getItemStack() == null) {
                emptySlots++;
                continue;
            }
            if (itemSlot.getItemStack().getItem().getId() == item.getId() && item.isStackable()) {
                return false;
            }
        }
        if (emptySlots == 0) {
            Handler.get().sendMsg("Your inventory is full.");
            return true;
        }
        return false;
    }

    /**
     * Iterates over the inventory to see if the player has an item of the specified type
     *
     * @param type - the ItemType to query for
     * @return true if player has such an item, false if not
     */
    public boolean playerHasItemType(ItemType type) {
        for (ItemSlot slot : itemSlots) {

            ItemStack is = slot.getItemStack();

            if (is == null || is.getItem().getItemTypes() == null)
                continue;

            if (is.getItem().isType(type)) {
                return true;
            }
        }

        for (EquipmentSlot slot : Handler.get().getEquipment().getEquipmentSlots()) {

            ItemStack is = slot.getEquipmentStack();

            if (is == null || is.getItem().getItemTypes() == null)
                continue;

            if (is.getItem().isType(type)) {
                return true;
            }
        }
        return false;
    }

    /*
     * Checks the equipment slot of an item
     * @returns int: index of the equipment slot to be filled
     */
    public int checkEquipmentSlot(Item item) {
        if (item.getEquipSlot() >= 0 && item.getEquipSlot() < 12)
            return item.getEquipSlot();

        throw new IllegalArgumentException(item.getName() + " does not have an equipment slot ID.");
    }

    public List<ItemSlot> getItemSlots() {
        return itemSlots;
    }

    public void setItemSlots(List<ItemSlot> itemSlots) {
        this.itemSlots = itemSlots;
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

}
