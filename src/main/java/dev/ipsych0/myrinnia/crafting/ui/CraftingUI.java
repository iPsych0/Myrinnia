package dev.ipsych0.myrinnia.crafting.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.ui.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.character.CharacterUI;
import dev.ipsych0.myrinnia.crafting.CraftingManager;
import dev.ipsych0.myrinnia.crafting.CraftingRecipe;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.InventoryWindow;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.items.ui.ItemTooltip;
import dev.ipsych0.myrinnia.publishers.CraftingPublisher;
import dev.ipsych0.myrinnia.quests.QuestHelpUI;
import dev.ipsych0.myrinnia.quests.QuestUI;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.skills.ui.SkillsOverviewUI;
import dev.ipsych0.myrinnia.skills.ui.SkillsUI;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CraftingUI implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 6741379998525736950L;
    private int x, y, width, height;
    public static boolean isOpen = false;
    private static boolean hasBeenPressed = false;
    private static boolean itemSelected = false;
    private List<CraftingSlot> craftingSlots;
    private CraftResultSlot crs;
    private UIImageButton make1Button, make5Button, make10Button, makeXButton, makeAllButton;
    public static boolean craftButtonPressed = false;
    public static boolean craftResultPressed = false;
    private ItemStack currentSelectedSlot;
    private CraftingManager craftingManager;
    private Rectangle windowBounds;
    private ItemTooltip itemTooltip;
    private UIManager uiManager;
    private List<CraftingRecipe> results;
    private List<CraftSelectSlot> selectSlots;
    private CraftSelectSlot selectedSlot;
    private Rectangle progressBar;
    private int progressTimer;
    private int craftInProgress;
    private UIImageButton abortButton;

    public CraftingUI() {
        this.width = 242;
        this.height = 384;
        this.x = Handler.get().getWidth() / 2 - width / 2;
        this.y = Handler.get().getHeight() / 2 - height / 2;

        windowBounds = new Rectangle(x, y, width, height);

        // First time it runs: set the window and dimensions/parameters
        craftingSlots = new ArrayList<>();
        craftingManager = new CraftingManager();
        uiManager = new UIManager();
        results = new ArrayList<>();
        selectSlots = new ArrayList<>();

        craftingSlots.add(new CraftingSlot(x + 32, y + 50, null));
        craftingSlots.add(new CraftingSlot(x + 80, y + 100, null));
        craftingSlots.add(new CraftingSlot(x + 128, y + 100, null));
        craftingSlots.add(new CraftingSlot(x + 176, y + 50, null));

        crs = new CraftResultSlot(x + width / 2 - 16, y + height - 224, null);
        make1Button = new UIImageButton(x + 40, y + height - 160, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, Assets.genericButton);
        make5Button = new UIImageButton(x + 72, y + height - 160, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, Assets.genericButton);
        make10Button = new UIImageButton(x + 104, y + height - 160, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, Assets.genericButton);
        makeXButton = new UIImageButton(x + 136, y + height - 160, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, Assets.genericButton);
        makeAllButton = new UIImageButton(x + 168, y + height - 160, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, Assets.genericButton);

        progressBar = new Rectangle(x + 48, make1Button.y + 64, width - 96, ItemSlot.SLOTSIZE);
        abortButton = new UIImageButton(x + width / 2 - 32, progressBar.y + progressBar.height + 8, 64, ItemSlot.SLOTSIZE, Assets.genericButton);
        abortButton.setVisible(false);

        for (CraftingSlot cs : craftingSlots) {
            uiManager.addObject(cs);
        }
        uiManager.addObject(crs);
        uiManager.addObject(make1Button);
        uiManager.addObject(make5Button);
        uiManager.addObject(make10Button);
        uiManager.addObject(makeXButton);
        uiManager.addObject(makeAllButton);
        uiManager.addObject(abortButton);

        itemTooltip = new ItemTooltip(x - 160, y);

    }

    public void tick() {
        if (Handler.get().getKeyManager().getLastUIKeyPressed() != -1) {
            exit();
            Handler.get().getKeyManager().setLastUIKeyPressed(-1);
        }
        if (isOpen) {

            uiManager.tick();

            Rectangle mouse = Handler.get().getMouse();

            crs.setHovering(crs.getBounds().contains(mouse));

            // If the window is closed or the player moves, re-add all items back to inventory or drop them if no space.
            if (Handler.get().getKeyManager().escape || Player.isMoving) {
                exit();
            }

            // If left-clicked on the "craft" button, craft the item
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged()) {
                if (abortButton.getBounds().contains(mouse) && craftButtonPressed) {
                    abortQueue();
                }
                if (selectedSlot != null) {
                    handleCraftBtnClicks(make1Button, 1, mouse);
                    handleCraftBtnClicks(make5Button, 5, mouse);
                    handleCraftBtnClicks(make10Button, 10, mouse);
                    handleCraftAllBtnClicks(makeAllButton, mouse);
                    handleCraftXBtnClicks(makeXButton, mouse);
                }
            }

            // If right-clicked on the crafted item
            if (Handler.get().getMouseManager().isRightPressed() && !Handler.get().getMouseManager().isDragged()) {
                if (crs.getBounds().contains(mouse) && craftResultPressed) {
                    // If there is no item in the slot, return
                    if (crs.getItemStack() == null) {
                        craftResultPressed = false;
                        return;
                    }

                    int numItems = crs.getItemStack().getAmount();
                    // If inventory is not full, add item
                    for (int i = 0; i < numItems; i++) {
                        if (Handler.get().invIsFull(crs.getItemStack().getItem())) {
                            break;
                        } else {
                            Handler.get().giveItem(crs.getItemStack().getItem(), 1);

                            if (crs.getItemStack().getAmount() > 1)
                                crs.getItemStack().setAmount(crs.getItemStack().getAmount() - 1);
                            else
                                crs.setItemStack(null);
                        }
                    }

                    craftResultPressed = false;
                }
            }

            for (CraftingSlot cs : craftingSlots) {

                cs.setHovering(cs.getBounds().contains(mouse));

                // If the player drags an item from a crafting slot
                if (Handler.get().getMouseManager().isDragged()) {
                    if (cs.getBounds().contains(mouse) && !hasBeenPressed && !itemSelected) {
                        hasBeenPressed = true;

                        // If we don't have anything selected, pick the item up.
                        if (currentSelectedSlot == null) {
                            if (cs.getItemStack() != null) {
                                currentSelectedSlot = cs.getItemStack();
                                cs.setItemStack(null);
                                itemSelected = true;
                                findRecipe();
                            } else {
                                hasBeenPressed = false;
                                return;
                            }
                        }
                    }
                }

                // If the item is released
                if (itemSelected && !Handler.get().getMouseManager().isDragged()) {
                    if (cs.getBounds().contains(mouse)) {
                        // If the itemstack already holds an item
                        if (cs.getItemStack() != null) {
                            if (currentSelectedSlot.getItem().isStackable()) {
                                // And if the item in the slot is stackable
                                if (cs.addItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount())) {
                                    // Add the item back to the inventory
                                    currentSelectedSlot = null;
                                    itemSelected = false;
                                    hasBeenPressed = false;
                                    findRecipe();

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
                            findRecipe();
                        }
                    }
                }

                // If an item is right-clicked
                if (InventoryWindow.isOpen) {
                    if (cs.getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed() && !hasBeenPressed && !Handler.get().getMouseManager().isDragged()) {
                        hasBeenPressed = true;
                        if (cs.getItemStack() != null) {
                            // If the inventory is full, return
                            if (Handler.get().invIsFull(cs.getItemStack().getItem())) {
                                hasBeenPressed = false;
                            } else {
                                // Otherwise add the item to the inventory
                                Handler.get().getInventory().getItemSlots().get(Handler.get().getInventory().findFreeSlot(cs.getItemStack().getItem())).addItem(cs.getItemStack().getItem(), cs.getItemStack().getAmount());
                                cs.setItemStack(null);
                                hasBeenPressed = false;
                                findRecipe();
                            }
                        } else {
                            hasBeenPressed = false;
                        }
                        return;
                    }

                    checkDragging(mouse);
                }
            }
        }
    }

    private void handleCraftXBtnClicks(UIImageButton makeXButton, Rectangle mouse) {
        if (makeXButton.getBounds().contains(mouse) && craftButtonPressed) {
            Handler.get().sendMsg("Make-X coming later.");
            craftButtonPressed = false;
        }
    }

    private void handleCraftAllBtnClicks(UIImageButton makeAllButton, Rectangle mouse) {
        int quantity = getNumCraftableItems(selectedSlot);
        handleCraftBtnClicks(makeAllButton, quantity, mouse);
    }

    private void abortQueue() {
        craftButtonPressed = false;
        abortButton.setVisible(false);
        craftInProgress = 0;
        progressTimer = 0;
    }

    private void handleCraftBtnClicks(UIImageButton button, int amount, Rectangle mouse) {
        if (button.getBounds().contains(mouse) && craftButtonPressed) {
            if (selectedSlot.getRecipe().isDiscovered()) {
                if (Handler.get().playerHasSkillLevel(SkillsList.CRAFTING, selectedSlot.getRecipe().getRequiredLevel())) {
                    queueItem(amount);
                } else {
                    Handler.get().sendMsg("You need a crafting level of " + selectedSlot.getRecipe().getRequiredLevel() + " to make this item.");
                }
            } else {
                Handler.get().sendMsg("You haven't discovered this recipe yet.");
            }

            craftButtonPressed = false;
        }
    }

    private void checkDragging(Rectangle mouse) {
        for (CraftingSlot cs : craftingSlots) {
            // If an item is dragged
            if (Handler.get().getMouseManager().isDragged()) {
                if (cs.getBounds().contains(mouse) && !hasBeenPressed && !itemSelected) {
                    hasBeenPressed = true;

                    // Stick the item to the mouse
                    if (currentSelectedSlot == null) {
                        if (cs.getItemStack() != null) {
                            currentSelectedSlot = cs.getItemStack();
                            cs.setItemStack(null);
                            itemSelected = true;
                        } else {
                            hasBeenPressed = false;
                            return;
                        }
                    }
                }
            }
        }
        if (itemSelected && currentSelectedSlot != null) {
            for (ItemSlot is : Handler.get().getInventory().getItemSlots()) {
                if (is.getBounds().contains(mouse) && !Handler.get().getMouseManager().isDragged()) {
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
            }
        }
    }

    public void findRecipe() {
        // Clear previous results
        results.clear();
        uiManager.removeAllObjects(selectSlots);
        selectSlots.clear();

        // Get the items in the slots
        List<ItemStack> filledSlots = new ArrayList<>();
        for (CraftingSlot cs : craftingSlots) {
            if (cs.getItemStack() != null) {
                filledSlots.add(cs.getItemStack());
            }
        }

        // Sort by item ID
        filledSlots.sort((o1, o2) -> {
            Integer i1 = o1.getItem().getId();
            Integer i2 = o2.getItem().getId();
            return i1.compareTo(i2);
        });

        // Iterate over crafting recipes
        int matches;
        for (int i = 0; i < craftingManager.getRecipes().size(); i++) {
            List<ItemStack> components = craftingManager.getRecipes().get(i).getComponents();

            // Next recipe if sizes are not equal
            if (components.size() != filledSlots.size()) {
                continue;
            }

            // Sort the recipe components
            components.sort((o1, o2) -> {
                Integer i1 = o1.getItem().getId();
                Integer i2 = o2.getItem().getId();
                return i1.compareTo(i2);
            });

            // Check if we meet all item and quantity requirements
            matches = 0;
            for (int j = 0; j < components.size(); j++) {
                if (components.get(j).getItem().getId() == filledSlots.get(j).getItem().getId()) {
                    matches++;
                }
            }

            // If we meet all requirements, recipe found
            if (matches == components.size()) {
                CraftingRecipe result = craftingManager.getRecipes().get(i);
                CraftSelectSlot slot = new CraftSelectSlot(x + width + (width / 2) - 36, y + 32 + (results.size() * 32), result);

                selectSlots.add(slot);
                results.add(result);

                // Select top recipe by default
                selectedSlot = selectSlots.get(0);

                uiManager.addObject(slot);
            }
        }
        // Clear selection when no recipe found
        if (results.isEmpty()) {
            selectedSlot = null;
        }
    }

    public void render(Graphics2D g) {
        if (isOpen) {

            g.drawImage(Assets.uiWindow, x, y, width, height, null);

            if (!results.isEmpty()) {
                g.drawImage(Assets.uiWindow, x + width, y, width - 40, height, null);
                Text.drawString(g, "You can craft: ", x + width + (width / 2) - 20, y + 16, true, Color.YELLOW, Assets.font14);

                for (CraftSelectSlot slot : selectSlots) {
                    if (slot.isHovering()) {
                        if (slot.getRecipe().isDiscovered()) {
                            itemTooltip.render(slot.getRecipe().getResult().getItem(), g);
                        }
                        if (Handler.get().getMouseManager().isLeftPressed() && craftButtonPressed) {
                            selectedSlot = slot;
                            craftButtonPressed = false;
                        }
                    }
                }
            }

            uiManager.render(g);

            if (crs.isHovering() && crs.getItemStack() != null) {
                itemTooltip.render(crs.getItemStack().getItem(), g);
            }

            for (CraftSelectSlot slot : selectSlots) {
                if (slot.getRecipe().isDiscovered()) {
                    if (!Handler.get().playerHasSkillLevel(SkillsList.CRAFTING, slot.getRecipe().getRequiredLevel())) {
                        g.setColor(Colors.insufficientAmountColor);
                        g.fillRoundRect(slot.x, slot.y, slot.width, slot.height, 4, 4);
                    } else if (getNumCraftableItems(slot) < 0) {
                        g.setColor(Colors.insufficientAmountColor);
                        g.fillRoundRect(slot.x, slot.y, slot.width, slot.height, 4, 4);
                    }
                }
            }

            if (selectedSlot != null && !results.isEmpty()) {
                g.setColor(Colors.selectedColor);
                g.fillRoundRect(selectedSlot.x, selectedSlot.y, selectedSlot.width, selectedSlot.height, 4, 4);
                drawRequirementAmounts(g);
            }

            Text.drawString(g, "Crafting", x + width / 2, y + 26, true, Color.YELLOW, Assets.font20);
            Text.drawString(g, "How many do you want to make?", x + width / 2, make1Button.y - 16, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "1", make1Button.x + make1Button.width / 2, make1Button.y + make1Button.height / 2, true, Color.YELLOW, Assets.font20);
            Text.drawString(g, "5", make5Button.x + make5Button.width / 2, make5Button.y + make5Button.height / 2, true, Color.YELLOW, Assets.font20);
            Text.drawString(g, "10", make10Button.x + make10Button.width / 2, make10Button.y + make10Button.height / 2, true, Color.YELLOW, Assets.font20);
            Text.drawString(g, "X", makeXButton.x + makeXButton.width / 2, makeXButton.y + makeXButton.height / 2, true, Color.YELLOW, Assets.font20);
            Text.drawString(g, "All", makeAllButton.x + makeAllButton.width / 2, makeAllButton.y + makeAllButton.height / 2, true, Color.YELLOW, Assets.font20);

            Rectangle mouse = Handler.get().getMouse();

            for (CraftingSlot cs : craftingSlots) {

                if (cs.getItemStack() != null && cs.getBounds().contains(mouse)) {
                    itemTooltip.render(cs.getItemStack().getItem(), g);
                }

                if (currentSelectedSlot != null) {
                    g.drawImage(currentSelectedSlot.getItem().getTexture(), Handler.get().getMouseManager().getMouseX(),
                            Handler.get().getMouseManager().getMouseY(), null);

                    Text.drawString(g, String.valueOf(currentSelectedSlot.getAmount()),
                            Handler.get().getMouseManager().getMouseX() + 12, Handler.get().getMouseManager().getMouseY() + 16, false, Color.YELLOW, Assets.font14);
                }
            }

            if (craftInProgress > 0 && selectedSlot != null) {
                progressTimer++;
                double percent = (double) progressTimer / (double) selectedSlot.getRecipe().getTimeToCraft();
                int progress = (int) ((double) progressBar.width * percent);
                g.setColor(Color.BLACK);
                g.drawRect(progressBar.x, progressBar.y, progressBar.width, progressBar.height);
                g.setColor(Colors.progressBarColor);
                g.fillRect(progressBar.x, progressBar.y, progress, progressBar.height);
                g.setColor(Colors.progressBarOutlineColor);
                g.fillRect(progressBar.x, progressBar.y, progress, progressBar.height);
                Text.drawString(g, Handler.get().roundOff(percent * 100d) + "%", progressBar.x + progressBar.width / 2, progressBar.y + progressBar.height / 2, true, Color.YELLOW, Assets.font14);

                if (progressTimer >= selectedSlot.getRecipe().getTimeToCraft()) {
                    createItem();
                }

                Text.drawString(g, "Abort", abortButton.x + abortButton.width / 2, abortButton.y + abortButton.height / 2, true, Color.YELLOW, Assets.font14);
                Text.drawString(g, "Queue: " + craftInProgress + " left.", x + width / 2, progressBar.y - 10, true, Color.YELLOW, Assets.font14);
            }
        }
    }

    public void openWindow() {
        CraftingUI.isOpen = true;
        CharacterUI.isOpen = false;
        QuestUI.isOpen = false;
        QuestHelpUI.isOpen = false;
        QuestUI.renderingQuests = false;
        SkillsUI.isOpen = false;
        SkillsOverviewUI.isOpen = false;
        AbilityOverviewUI.exit();
        findRecipe();
    }

    public void exit() {

        abortQueue();

        // Dump the dragging cursor item in the inventory
        if (currentSelectedSlot != null) {
            if (!Handler.get().invIsFull(currentSelectedSlot.getItem())) {
                Handler.get().giveItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount());
            } else {
                Handler.get().dropItem(currentSelectedSlot.getItem(), currentSelectedSlot.getAmount(),
                        (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY());
            }
            currentSelectedSlot = null;
            itemSelected = false;
            hasBeenPressed = false;
        }

        // Dump the recipe items in the inventory
        boolean invFull = false;
        for (CraftingSlot cs : craftingSlots) {
            if (cs.getItemStack() != null) {
                if (!Handler.get().invIsFull(cs.getItemStack().getItem())) {
                    Handler.get().giveItem(cs.getItemStack().getItem(), cs.getItemStack().getAmount());
                } else {
                    invFull = true;
                    Handler.get().dropItem(cs.getItemStack().getItem(), cs.getItemStack().getAmount(), (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY());

                }
                cs.setItemStack(null);
                findRecipe();
            }
        }
        if (invFull) {
            Handler.get().sendMsg("The remaining items in the crafting slots have been dropped.");
        }

        // Dump the remainder of crafting items in the inventory where possible
        if (crs.getItemStack() != null) {
            int numItems = crs.getItemStack().getAmount();
            for (int i = 0; i < numItems; i++) {
                if (!Handler.get().invIsFull(crs.getItemStack().getItem())) {
                    Handler.get().giveItem(crs.getItemStack().getItem(), 1);
                } else {
                    Handler.get().dropItem(crs.getItemStack().getItem(), 1,
                            (int) Handler.get().getPlayer().getX(), (int) Handler.get().getPlayer().getY());
                }
                if (crs.getItemStack().getAmount() > 1)
                    crs.getItemStack().setAmount(crs.getItemStack().getAmount() - 1);
                else
                    crs.setItemStack(null);
                findRecipe();
            }
        }

        isOpen = false;
    }

    /*
     * Finds the first free slot in the crafting UI to add an item to
     * @returns: the index in the List of Slots / -1 if no space
     */
    public int findFreeSlot(Item item) {
        boolean firstFreeSlotFound = false;
        int index = -1;
        for (int i = 0; i < craftingSlots.size(); i++) {
            if (craftingSlots.get(i).getItemStack() == null) {
                if (!firstFreeSlotFound) {
                    firstFreeSlotFound = true;
                    index = i;
                }
            } else if (craftingSlots.get(i).getItemStack() != null && item.isStackable()) {
                if (craftingSlots.get(i).getItemStack().getItem().getId() == item.getId()) {
                    return i;
                }
            }
        }
        if (index != -1)
            return index;
        return -1;
    }

    private void queueItem(int amount) {
        int craftable = getNumCraftableItems(selectedSlot);
        if (craftable > 0) {
            ItemStack result = selectedSlot.getRecipe().getResult();

            // If we try to craft a new item before claiming the last one, reject
            if (crs.getItemStack() != null && crs.getItemStack().getItem().getId() != result.getItem().getId()) {
                findRecipe();
                Handler.get().sendMsg("Please claim your crafted item before creating a new one.");
                return;
            }

            // Maximize to only the number of items we have the resources to craft
            if (craftInProgress + amount > craftable) {
                craftInProgress = craftable;
            } else {
                craftInProgress += amount;
            }

            abortButton.setVisible(true);

        } else {
            findRecipe();
            Handler.get().sendMsg("You don't have the required materials to make this item.");
        }
    }

    private void createItem() {
        if (getNumCraftableItems(selectedSlot) > 0) {
            progressTimer = 0;
            craftInProgress--;

            ItemStack result = selectedSlot.getRecipe().getResult();

            crs.addItem(result.getItem(), result.getAmount());

            removeComponentsFromSlots();

            CraftingPublisher.get().publish(result);

            Handler.get().getSkillsUI().getSkill(SkillsList.CRAFTING).addExperience(selectedSlot.getRecipe().getCraftingXP());

            if (craftInProgress < 1)
                abortButton.setVisible(false);

        } else {
            abortButton.setVisible(false);
            progressTimer = 0;
            craftInProgress = 0;
            selectedSlot = null;
            Handler.get().sendMsg("You don't have enough materials to make more!");
        }
    }

    private int getNumCraftableItems(CraftSelectSlot selectedSlot) {
        if (selectedSlot != null) {
            // Check the components from the slots
            List<Integer> amounts = new ArrayList<>();
            for (ItemStack c : selectedSlot.getRecipe().getComponents()) {
                int slotMatches = 0;
                for (CraftingSlot slot : craftingSlots) {
                    if (slot.getItemStack() != null) {
                        slotMatches++;
                        // If we found the slot matching the item
                        if (slot.getItemStack().getItem().getId() == c.getItem().getId()) {
                            int slotAmt = slot.getItemStack().getAmount();

                            // Calculate how many items we can make
                            amounts.add(slotAmt / c.getAmount());

                            // If the amount ends up less than 0 left, we don't have enough
                            if (slotAmt - c.getAmount() < 0) {
                                break;
                            }
                        }
                    }
                }
                if (slotMatches != selectedSlot.getRecipe().getComponents().size()) {
                    return -1;
                }
            }


            Optional<Integer> lowest = amounts.stream().min(Integer::compareTo);
            return lowest.orElse(-1);
        }
        return -1;
    }

    private void removeComponentsFromSlots() {
        // Remove the components from the slots
        for (ItemStack c : selectedSlot.getRecipe().getComponents()) {
            for (CraftingSlot slot : craftingSlots) {
                if (slot.getItemStack() != null) {
                    // If we found the slot matching the item, remove it
                    if (slot.getItemStack().getItem().getId() == c.getItem().getId()) {
                        int slotAmt = slot.getItemStack().getAmount();
                        if (slotAmt - c.getAmount() == 0) {
                            slot.setItemStack(null);
                        } else {
                            slot.getItemStack().setAmount(slotAmt - c.getAmount());
                        }
                    }
                }
            }
        }
    }

    private void drawRequirementAmounts(Graphics2D g) {
        for (ItemStack c : selectedSlot.getRecipe().getComponents()) {
            for (CraftingSlot slot : craftingSlots) {
                if (slot.getItemStack() != null) {
                    // If we found the slot matching the item, draw the quantity
                    if (slot.getItemStack().getItem().getId() == c.getItem().getId()) {
                        Color color = Color.GREEN;
                        if (slot.getItemStack().getAmount() < c.getAmount()) {
                            color = Color.RED;
                        }
                        Text.drawString(g, slot.getItemStack().getAmount() + "/" + c.getAmount(), slot.x + slot.width / 2, slot.y - 8, true, color, Assets.font14);
                    }
                }
            }
        }
    }

    public List<CraftingSlot> getCraftingSlots() {
        return craftingSlots;
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

    public CraftingManager getCraftingManager() {
        return craftingManager;
    }

    public Rectangle getWindowBounds() {
        return windowBounds;
    }

}
