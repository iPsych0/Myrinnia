package dev.ipsych0.myrinnia.shops;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.equipment.EquipmentWindow;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.items.*;
import dev.ipsych0.myrinnia.items.ui.InventoryWindow;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.ui.TextBox;
import dev.ipsych0.myrinnia.ui.DialogueBox;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ShopWindow implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5778791089622654030L;
    public int x;
    public int y;
    public int width;
    private int height;
    private static final int NUM_COLS = 5;
    private static final int NUM_ROWS = 6;
    private CopyOnWriteArrayList<ItemSlot> itemSlots, invSlots;
    private Color selectedColor = new Color(0, 255, 255, 62);
    private ItemStack selectedShopItem;
    private ItemStack selectedInvItem;
    public static boolean isOpen = false;
    private ItemSlot tradeSlot;
    private static boolean inventoryLoaded = false;
    private Rectangle buyAllButton, sellAllButton, buy1Button, sell1Button, buyXButton, sellXButton, exit;
    private Rectangle windowBounds;
    public static boolean makingChoice = false;
    private DialogueBox dBox;
    private String[] answers = {"Yes", "No"};
    private ItemSlot selectedSlot = null;
    private static final int DIALOGUE_WIDTH = 300;
    private static final int DIALOGUE_HEIGHT = 150;
    private int restockTimer = 0;
    private int destockTimer = 0;
    private int seconds = 60;
    private int[] defaultStock;
    private ArrayList<ItemStack> shopItems;
    public static boolean hasBeenPressed = false;
    private static final double COMMISSION = 0.75;
    public static boolean escapePressed = false;
    public static ShopWindow lastOpenedWindow;


    public ShopWindow(ArrayList<ItemStack> shopItems) {
        this.shopItems = shopItems;
        this.width = 460;
        this.height = 313;
        this.x = Handler.get().getWidth() / 2 - width / 2;
        this.y = Handler.get().getHeight() / 2 - height / 2;

        // Initialize the shops slots and the inventory slots
        itemSlots = new CopyOnWriteArrayList<>();
        invSlots = new CopyOnWriteArrayList<>();

        // Add the shops slots
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {

                itemSlots.add(new ItemSlot(x + 17 + (i * (ItemSlot.SLOTSIZE)), y + 48 + (j * ItemSlot.SLOTSIZE), null));

            }
        }

        // Initialize the default stock of the shops
        defaultStock = new int[shopItems.size()];

        // Fill the shops slots with the shops items and set the default stock
        for (int i = 0; i < shopItems.size(); i++) {
            itemSlots.get(i).addItem(shopItems.get(i).getItem(), shopItems.get(i).getAmount());
            defaultStock[i] = shopItems.get(i).getAmount();
        }

        // Add the inventory slots
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {

                invSlots.add(new ItemSlot(x + (width / 2) + 17 + (i * (ItemSlot.SLOTSIZE)), y + 48 + (j * ItemSlot.SLOTSIZE), null));

            }
        }

        // Initialising all the buttons
        tradeSlot = new ItemSlot(x + (width / 2) - 16, y + (height / 2) + 64, null);

        buy1Button = new Rectangle(x + 17, y + (height / 2) + 64, 64, 32);
        sell1Button = new Rectangle(x + (width / 2) + 17, y + (height / 2) + 64, 64, 32);

        buyAllButton = new Rectangle(x + 81, y + (height / 2) + 64, 64, 32);
        sellAllButton = new Rectangle(x + (width / 2) + 81, y + (height / 2) + 64, 64, 32);

        buyXButton = new Rectangle(x + 145, y + (height / 2) + 64, 64, 32);
        sellXButton = new Rectangle(x + (width / 2) + 145, y + (height / 2) + 64, 64, 32);

        exit = new Rectangle(x + width - 35, y + 10, 24, 24);

        windowBounds = new Rectangle(x, y, width, height);

        // Instance of the DialogueBox
        dBox = new DialogueBox(x + (width / 2) - (DIALOGUE_WIDTH / 2), y + (height / 2) - (DIALOGUE_HEIGHT / 2), DIALOGUE_WIDTH, DIALOGUE_HEIGHT, answers, "Please confirm your trade.", true);

    }

    public void tick() {

        // Restock shops
        restock();

        if (isOpen) {

            // Close the inventory and equipment window
            InventoryWindow.isOpen = false;
            EquipmentWindow.isOpen = false;

            // Checks if the inventory should be refreshed
            if (!inventoryLoaded) {
                loadInventory();
                inventoryLoaded = true;
            }

            Rectangle mouse = Handler.get().getMouse();

            // Handles any UI button logic
            handleButtonClick(mouse);

            // Checks if shops is closed
            handleShopExit(mouse);

            submitShopRequest();

            tickShopSlots(mouse);

            tickInventory(mouse);

            tradeSlot.tick();

            // If player is making a choice, show the dialoguebox
            if (makingChoice)
                dBox.tick();

        }
    }

    public void render(Graphics2D g) {
        if (isOpen) {
            g.drawImage(Assets.uiWindow, x, y, width, height, null);

            Rectangle mouse = Handler.get().getMouse();

            // Buy/sell 1
            if (buy1Button.contains(mouse))
                g.drawImage(Assets.genericButton[0], buy1Button.x, buy1Button.y, buy1Button.width, buy1Button.height, null);
            else
                g.drawImage(Assets.genericButton[1], buy1Button.x, buy1Button.y, buy1Button.width, buy1Button.height, null);
            if (sell1Button.contains(mouse))
                g.drawImage(Assets.genericButton[0], sell1Button.x, sell1Button.y, sell1Button.width, sell1Button.height, null);
            else
                g.drawImage(Assets.genericButton[1], sell1Button.x, sell1Button.y, sell1Button.width, sell1Button.height, null);
            Text.drawString(g, "Buy 1", x + 17 + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Sell 1", x + 17 + (width / 2) + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);

            // Buy/sell ALL
            if (buyAllButton.contains(mouse))
                g.drawImage(Assets.genericButton[0], buyAllButton.x, buyAllButton.y, buyAllButton.width, buyAllButton.height, null);
            else
                g.drawImage(Assets.genericButton[1], buyAllButton.x, buyAllButton.y, buyAllButton.width, buyAllButton.height, null);
            if (sellAllButton.contains(mouse))
                g.drawImage(Assets.genericButton[0], sellAllButton.x, sellAllButton.y, sellAllButton.width, sellAllButton.height, null);
            else
                g.drawImage(Assets.genericButton[1], sellAllButton.x, sellAllButton.y, sellAllButton.width, sellAllButton.height, null);
            Text.drawString(g, "Buy all", x + 81 + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Sell all", x + 81 + (width / 2) + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);

            // Buy/sell X
            if (buyXButton.contains(mouse))
                g.drawImage(Assets.genericButton[0], buyXButton.x, buyXButton.y, buyXButton.width, buyXButton.height, null);
            else
                g.drawImage(Assets.genericButton[1], buyXButton.x, buyXButton.y, buyXButton.width, buyXButton.height, null);
            if (sellXButton.contains(mouse))
                g.drawImage(Assets.genericButton[0], sellXButton.x, sellXButton.y, sellXButton.width, sellXButton.height, null);
            else
                g.drawImage(Assets.genericButton[1], sellXButton.x, sellXButton.y, sellXButton.width, sellXButton.height, null);
            Text.drawString(g, "Buy X", x + 145 + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Sell X", x + 145 + (width / 2) + 32, y + (height / 2) + 64 + 16, true, Color.YELLOW, Assets.font14);

            // test stuff close button
            if (exit.contains(mouse))
                g.drawImage(Assets.genericButton[0], exit.x, exit.y, exit.width, exit.height, null);
            else
                g.drawImage(Assets.genericButton[1], exit.x, exit.y, exit.width, exit.height, null);
            Text.drawString(g, "X", exit.x + 11, exit.y + 11, true, Color.YELLOW, Assets.font20);
            for (ItemSlot is : itemSlots) {

                is.render(g);

            }

            for (ItemSlot is : invSlots) {
                is.render(g);

            }

            Text.drawString(g, "Shop stock", x + 81 + 32, y + 36, true, Color.YELLOW, Assets.font14);
            Text.drawString(g, "Inventory", x + 81 + (width / 2) + 32, y + 36, true, Color.YELLOW, Assets.font14);

            if (selectedInvItem != null)
                Text.drawString(g, selectedInvItem.getAmount() + " " + selectedInvItem.getItem().getName() + " will get you: " + (int) Math.floor((selectedInvItem.getItem().getPrice() * COMMISSION)) * selectedInvItem.getAmount() + " coins. (" + (int) Math.floor((selectedInvItem.getItem().getPrice() * COMMISSION)) + " each)", x + (width / 2), y + (height / 2) + 112, true, Color.YELLOW, Assets.font14);
            if (selectedShopItem != null)
                Text.drawString(g, selectedShopItem.getAmount() + " " + selectedShopItem.getItem().getName() + " will cost you: " + selectedShopItem.getItem().getPrice() * selectedShopItem.getAmount() + " coins. (" + selectedShopItem.getItem().getPrice() + " each)", x + (width / 2), y + (height / 2) + 112, true, Color.YELLOW, Assets.font14);

            if (selectedSlot != null) {
                g.setColor(selectedColor);
                g.fillRect(selectedSlot.getX(), selectedSlot.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
            }

            if (makingChoice)
                dBox.render(g);
        }
    }

    public static void open() {
        AbilityOverviewUI.exit();
        ShopWindow.isOpen = true;
    }

    public void setLastShopWindow() {
        ShopWindow.lastOpenedWindow = this;
    }

    public void exit() {
        if(Handler.get().getMouseManager().isLeftPressed()){
            MouseManager.justClosedUI = true;
        }
        isOpen = false;
        inventoryLoaded = false;
        DialogueBox.isOpen = false;
        TextBox.isOpen = false;
        Handler.get().getKeyManager().setTextBoxTyping(false);
        hasBeenPressed = false;
        selectedSlot = null;
        selectedInvItem = null;
        selectedShopItem = null;
        makingChoice = false;
        dBox.setPressedButton(null);
        InventoryWindow.isOpen = true;
        EquipmentWindow.isOpen = true;
        escapePressed = false;
    }

    private void handleShopExit(Rectangle mouse) {
        if (exit.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() || Player.isMoving) {
            exit();
            return;
        }

        if (Handler.get().getKeyManager().escape && makingChoice && escapePressed) {
            escapePressed = false;
            inventoryLoaded = false;
            DialogueBox.isOpen = false;
            TextBox.isOpen = false;
            Handler.get().getKeyManager().setTextBoxTyping(false);
            hasBeenPressed = false;
            makingChoice = false;
            dBox.setPressedButton(null);
        } else if (Handler.get().getKeyManager().escape && !makingChoice && escapePressed) {
            exit();
        }
    }

    private void handleButtonClick(Rectangle mouse) {
        /*
         * Buy 1 Button onClick
         */
        if (buy1Button.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedShopItem != null) {
            buyItem();
            hasBeenPressed = false;
            return;
        }

        /*
         * Sell 1 Button onClick
         */
        if (sell1Button.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedInvItem != null) {
            sellItem();
            hasBeenPressed = false;
            return;
        }

        /*
         * Buy All Button onClick
         */
        if (buyAllButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedShopItem != null) {
            makingChoice = true;
            DialogueBox.isOpen = true;
            TextBox.isOpen = false;
            dBox.setParam("BuyAll");
            hasBeenPressed = false;
            return;
        }

        /*
         * Sell All Button onClick
         */
        if (sellAllButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedInvItem != null) {
            makingChoice = true;
            DialogueBox.isOpen = true;
            TextBox.isOpen = false;
            dBox.setParam("SellAll");
            hasBeenPressed = false;
            return;
        }

        /*
         * Buy X Button onClick
         */
        if (buyXButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedShopItem != null) {
            makingChoice = true;
            DialogueBox.isOpen = true;
            TextBox.isOpen = true;
            dBox.setParam("BuyX");
            hasBeenPressed = false;
            return;
        }

        /*
         * Sell X Button onClick
         */
        if (sellXButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice && selectedInvItem != null) {
            makingChoice = true;
            DialogueBox.isOpen = true;
            TextBox.isOpen = true;
            dBox.setParam("SellX");
            hasBeenPressed = false;
        }
    }

    private void restock() {
        // Keeps a timer before restocking an item or decrementing an item
        restockTimer++;
        if (restockTimer >= (seconds * 60)) {
            for (int i = 0; i < shopItems.size(); i++) {
                if (itemSlots.get(i).getItemStack() != null) {
                    if (itemSlots.get(i).getItemStack().getAmount() < defaultStock[i]) {
                        itemSlots.get(i).getItemStack().setAmount(itemSlots.get(i).getItemStack().getAmount() + 1);
                    }
                    if (itemSlots.get(i).getItemStack().getAmount() > defaultStock[i]) {
                        itemSlots.get(i).getItemStack().setAmount(itemSlots.get(i).getItemStack().getAmount() - 1);
                    }
                }
            }
            restockTimer = 0;
        }

        // Keeps a timer before destocking non-stock items.
        destockTimer++;
        if (destockTimer >= (seconds * 180)) {
            for (int i = defaultStock.length; i < itemSlots.size(); i++) {
                if (itemSlots.get(i).getItemStack() != null)
                    itemSlots.get(i).getItemStack().setAmount(itemSlots.get(i).getItemStack().getAmount() - 1);
            }
            destockTimer = 0;
            clearNonStockItems();
        }
    }

    private void submitShopRequest() {
        // If the dialoguebox is open and player is making a choice
        if (makingChoice && dBox.getPressedButton() != null) {
            if (!dBox.getTextBox().getCharactersTyped().isEmpty()) {
                // If the user has typed in an amount and confirmed the trade per button, buy the item
                if ("Yes".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[0]) &&
                        "BuyX".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[1])) {
                    buyXItem(Integer.parseInt(dBox.getTextBox().getCharactersTyped()));
                    Handler.get().playEffect("ui/shop_trade.wav");
                }
                // If the user has typed in an amount and confirmed the trade per button, sell the item
                else if ("Yes".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[0]) &&
                        "SellX".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[1])) {
                    sellXItem(Integer.parseInt(dBox.getTextBox().getCharactersTyped()));
                    Handler.get().playEffect("ui/shop_trade.wav");
                }
            }
            // If the user has typed in an amount and confirmed the trade per button, buy the item
            if ("Yes".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[0]) &&
                    "BuyAll".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[1])) {
                buyAllItem();
                Handler.get().playEffect("ui/shop_trade.wav");
            }
            // If the user has typed in an amount and confirmed the trade per button, sell the item
            else if ("Yes".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[0]) &&
                    "SellAll".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[1])) {
                sellAllItem();
                Handler.get().playEffect("ui/shop_trade.wav");
            }

            dBox.setPressedButton(null);
            dBox.getTextBox().getSb().setLength(0);
            dBox.getTextBox().setIndex(0);
            dBox.getTextBox().setCharactersTyped(dBox.getTextBox().getSb().toString());
//			dBox = new DialogueBox(handler, x + (width / 2) - (dialogueWidth / 2), y + (height / 2) - (dialogueHeight / 2), dialogueWidth, dialogueHeight, answers, "Please confirm your trade.", true);
            DialogueBox.isOpen = false;
            TextBox.isOpen = false;
            Handler.get().getKeyManager().setTextBoxTyping(false);
            makingChoice = false;
            hasBeenPressed = false;
        }

        if (TextBox.enterPressed && makingChoice) {
            if(!dBox.getTextBox().getCharactersTyped().isEmpty()) {
                // If enter is pressed while making choice, this means a positive response ("Yes")
                dBox.setPressedButton(dBox.getButtons().get(0));
                dBox.getPressedButton().getButtonParam()[0] = "Yes";
                dBox.getPressedButton().getButtonParam()[1] = dBox.getParam();

                // If the user has typed in an amount and confirmed the trade per button, buy the item
                if ("Yes".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[0]) &&
                        "BuyX".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[1])) {
                    buyXItem(Integer.parseInt(dBox.getTextBox().getCharactersTyped()));
                    Handler.get().playEffect("ui/shop_trade.wav");
                }
                // If the user has typed in an amount and confirmed the trade per button, sell the item
                else if ("Yes".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[0]) &&
                        "SellX".equalsIgnoreCase(dBox.getPressedButton().getButtonParam()[1])) {
                    sellXItem(Integer.parseInt(dBox.getTextBox().getCharactersTyped()));
                    Handler.get().playEffect("ui/shop_trade.wav");
                }
            }

            dBox.setPressedButton(null);
            dBox.getTextBox().getSb().setLength(0);
            dBox.getTextBox().setIndex(0);
            dBox.getTextBox().setCharactersTyped(dBox.getTextBox().getSb().toString());
            DialogueBox.isOpen = false;
            TextBox.enterPressed = false;
            Handler.get().getKeyManager().setTextBoxTyping(false);
            makingChoice = false;
            hasBeenPressed = false;
        }
    }

    private void tickShopSlots(Rectangle mouse) {
        // Tick shops slots
        for (ItemSlot is : itemSlots) {
            is.tick();


            Rectangle slot = is.getBounds();

            // If left-clicked, select an item
            if (slot.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice) {
                if (is.getItemStack() != null) {
                    if (selectedShopItem == null) {
                        selectedInvItem = null;
                        selectedSlot = is;
                        selectedShopItem = is.getItemStack();
                        tradeSlot.setItemStack(selectedShopItem);
                        hasBeenPressed = false;
                        return;
                    }
                    // If we already have this item selected, deselect it
                    else if (selectedShopItem == is.getItemStack()) {
                        selectedSlot = null;
                        selectedInvItem = null;
                        selectedShopItem = null;
                        tradeSlot.setItemStack(null);
                        hasBeenPressed = false;
                        return;
                    }
                    // If clicked on a different item than the currently selected one, select new item
                    else if (selectedShopItem != is.getItemStack()) {
                        selectedSlot = is;
                        selectedInvItem = null;
                        selectedShopItem = is.getItemStack();
                        tradeSlot.setItemStack(selectedShopItem);
                        hasBeenPressed = false;
                        return;
                    }
                    // Otherwise, deselect current selected item
                } else {
                    selectedSlot = null;
                    selectedInvItem = null;
                    selectedShopItem = null;
                    tradeSlot.setItemStack(null);
                    hasBeenPressed = false;
                    return;
                }
            }
        }
    }

    private void tickInventory(Rectangle mouse) {
        // Tick inventory slots
        for (ItemSlot is : invSlots) {
            is.tick();

            Rectangle slot = is.getBounds();

            if (slot.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed && !makingChoice) {
                if (is.getItemStack() != null) {
                    Handler.get().playEffect("ui/ui_button_click.wav");
                    // If the price = -1, item cannot be sold
                    if (is.getItemStack().getItem().getPrice() <= -1) {
                        Handler.get().sendMsg("You cannot sell this item.");
                        hasBeenPressed = false;
                        return;
                    }
                    // Select an item
                    if (selectedInvItem == null) {
                        selectedSlot = is;
                        selectedShopItem = null;
                        selectedInvItem = is.getItemStack();
                        tradeSlot.setItemStack(selectedInvItem);
                        hasBeenPressed = false;
                        return;
                    }
                    // If we already have this item selected, deselect it
                    else if (selectedInvItem == is.getItemStack()) {
                        selectedSlot = null;
                        selectedShopItem = null;
                        selectedInvItem = null;
                        tradeSlot.setItemStack(null);
                        hasBeenPressed = false;
                        return;
                    }
                    // If clicked on a different item than the currently selected one, select new item
                    else if (selectedInvItem != is.getItemStack()) {
                        selectedSlot = is;
                        selectedShopItem = null;
                        selectedInvItem = is.getItemStack();
                        tradeSlot.setItemStack(selectedInvItem);
                        hasBeenPressed = false;
                        return;
                    }
                    // Otherwise, deselect current selected item
                } else {
                    selectedSlot = null;
                    selectedShopItem = null;
                    selectedInvItem = null;
                    tradeSlot.setItemStack(null);
                    hasBeenPressed = false;
                    return;
                }
            }

        }
    }

    /*
     * If item reaches 0 quantity that is not in default stock, remove it from the store
     */
    private void clearNonStockItems() {
        for (int i = shopItems.size(); i < itemSlots.size(); i++) {
            if (itemSlots.get(i).getItemStack() != null && itemSlots.get(i).getItemStack().getAmount() <= 0) {
                itemSlots.get(i).setItemStack(null);
            }
        }
    }

    /*
     * Refreshes the inventory in the shopwindow
     */
    private void loadInventory() {
        for (int i = 0; i < Handler.get().getInventory().getItemSlots().size(); i++) {
            invSlots.get(i).setItemStack(Handler.get().getInventory().getItemSlots().get(i).getItemStack());
        }
    }

    /*
     * Buys 1 item
     */
    private void buyItem() {
        if (tradeSlot.getItemStack() != null && selectedInvItem == null) {
            if (Handler.get().playerHasItem(Item.coins, (tradeSlot.getItemStack().getItem().getPrice()))) {
                Handler.get().playEffect("ui/shop_trade.wav");
                if (!Handler.get().invIsFull(tradeSlot.getItemStack().getItem()) && selectedSlot.getItemStack().getAmount() > 0) {
                    Handler.get().removeItem(Item.coins, (1 * tradeSlot.getItemStack().getItem().getPrice()));
                    Handler.get().giveItem(tradeSlot.getItemStack().getItem(), 1);
                    inventoryLoaded = false;

                    selectedShopItem.setAmount(selectedShopItem.getAmount() - 1);

                    if (selectedShopItem.getAmount() <= 0) {
                        selectedShopItem = null;
                        selectedSlot = null;
                        clearNonStockItems();
                    }
                } else {
                    selectedSlot = null;
                    selectedShopItem = null;
                }
                hasBeenPressed = false;
            } else {
                Handler.get().sendMsg("You don't have enough gold to buy 1x " + tradeSlot.getItemStack().getItem().getName());
                hasBeenPressed = false;
            }
        } else {
            hasBeenPressed = false;
        }
    }

    /*
     * Sells 1 item
     */
    private void sellItem() {
        if (tradeSlot.getItemStack() != null && selectedShopItem == null) {
            if (tradeSlot.getItemStack().getItem().getPrice() <= -1) {
                Handler.get().sendMsg("You cannot sell this item.");
                hasBeenPressed = false;
                return;
            }
            if (Handler.get().playerHasItem(tradeSlot.getItemStack().getItem(), 1)) {
                Handler.get().playEffect("ui/shop_trade.wav");
                if (findFreeSlot(tradeSlot.getItemStack().getItem()) != -1) {
                    Handler.get().removeItem(tradeSlot.getItemStack().getItem(), 1);
                    Handler.get().giveItem(Item.coins, (int) (Math.floor((tradeSlot.getItemStack().getItem().getPrice() * COMMISSION)) * 1));
                    itemSlots.get(findFreeSlot(tradeSlot.getItemStack().getItem())).addItem(tradeSlot.getItemStack().getItem(), 1);
                    inventoryLoaded = false;

                    if (tradeSlot.getItemStack().getAmount() == 1) {
                        selectedInvItem = null;
                        selectedSlot = null;
                        tradeSlot.setItemStack(null);
                    }
                } else {
                    Handler.get().sendMsg("You cannot sell any more items to the shops.");
                }
            }
            hasBeenPressed = false;
        } else {
            hasBeenPressed = false;
        }
    }

    /*
     * Buys all items in stock
     */
    private void buyAllItem() {
        if (tradeSlot.getItemStack() != null && selectedInvItem == null) {
            ArrayList<Integer> slots = getMatchSlots(tradeSlot.getItemStack().getItem());
            int i = 0;
            int buyAmount = 0;
            while (i < slots.size()) {
                if (Handler.get().playerHasItem(Item.coins, (tradeSlot.getItemStack().getAmount() * tradeSlot.getItemStack().getItem().getPrice()))) {
                    if (!Handler.get().invIsFull(tradeSlot.getItemStack().getItem())) {
                        Handler.get().removeItem(Item.coins, (tradeSlot.getItemStack().getAmount() * tradeSlot.getItemStack().getItem().getPrice()));
                        Handler.get().giveItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount());
                        buyAmount = tradeSlot.getItemStack().getAmount();
                    } else {
                        hasBeenPressed = false;
                        break;
                    }
                } else {
                    int coins = 0;
                    for (int j = 0; j < invSlots.size(); j++) {
                        if (invSlots.get(j).getItemStack() != null) {
                            if (invSlots.get(j).getItemStack().getItem().getId() == Item.coins.getId()) {
                                coins += invSlots.get(j).getItemStack().getAmount();
                            }
                        }
                    }
                    buyAmount = (int) Math.floor(coins / tradeSlot.getItemStack().getItem().getPrice());
                    Handler.get().removeItem(Item.coins, (buyAmount * tradeSlot.getItemStack().getItem().getPrice()));
                    Handler.get().giveItem(tradeSlot.getItemStack().getItem(), buyAmount);
                    Handler.get().sendMsg("You don't have enough gold to buy " + (tradeSlot.getItemStack().getAmount() - buyAmount) + "x " + tradeSlot.getItemStack().getItem().getName());
                    hasBeenPressed = false;
                    i++;
                    break;
                }
                i++;
            }
            int matches = 0;
            for (int j = 0; j < itemSlots.size(); j++) {
                if (matches == i)
                    break;
                if (itemSlots.get(j).getItemStack() == null)
                    continue;
                if (itemSlots.get(j).getItemStack().getItem().getId() == tradeSlot.getItemStack().getItem().getId()) {
                    itemSlots.get(j).getItemStack().setAmount((tradeSlot.getItemStack().getAmount() - buyAmount));
                    matches++;
                }
            }
            inventoryLoaded = false;
            tradeSlot.setItemStack(null);
            selectedSlot = null;
            clearNonStockItems();
            if (selectedShopItem != null)
                selectedShopItem = null;
        } else {
            hasBeenPressed = false;
        }
    }

    /*
     * Sells all items from the currently selected slot
     */
    private void sellAllItem() {
        if (tradeSlot.getItemStack() != null && selectedShopItem == null) {
            if (tradeSlot.getItemStack().getItem().getPrice() <= -1) {
                Handler.get().sendMsg("You cannot sell this item.");
                hasBeenPressed = false;
                return;
            }
            while (Handler.get().playerHasItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount())) {
                if (findFreeSlot(tradeSlot.getItemStack().getItem()) != -1) {
                    Handler.get().removeItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount());
                    Handler.get().giveItem(Item.coins, (int) (Math.floor((tradeSlot.getItemStack().getItem().getPrice() * COMMISSION)) * tradeSlot.getItemStack().getAmount()));
                    itemSlots.get(findFreeSlot(tradeSlot.getItemStack().getItem())).addItem(tradeSlot.getItemStack().getItem(), tradeSlot.getItemStack().getAmount());
                    inventoryLoaded = false;
                } else {
                    Handler.get().sendMsg("You cannot sell any more items to the shops.");
                    break;
                }
            }
            tradeSlot.setItemStack(null);
            selectedSlot = null;
            if (selectedInvItem != null)
                selectedInvItem = null;
            hasBeenPressed = false;
        } else {
            hasBeenPressed = false;
        }
    }

    /**
     * Buys X amount of items from the shops
     *
     * @param amount - quantity to buy
     */
    private void buyXItem(int amount) {
        if (tradeSlot.getItemStack() != null && selectedInvItem == null) {
            ArrayList<Integer> slots = getMatchSlots(tradeSlot.getItemStack().getItem());
            int i = slots.size();
            int index = 0;

            if (amount > i && !tradeSlot.getItemStack().getItem().isStackable()) {
                amount = i;
            } else if (amount > tradeSlot.getItemStack().getAmount() && tradeSlot.getItemStack().getItem().isStackable()) {
                amount = tradeSlot.getItemStack().getAmount();
            }
            while (index < amount) {
                if (Handler.get().playerHasItem(Item.coins, (1 * tradeSlot.getItemStack().getItem().getPrice()))) {
                    if (!Handler.get().invIsFull(tradeSlot.getItemStack().getItem())) {
                        Handler.get().removeItem(Item.coins, 1 * tradeSlot.getItemStack().getItem().getPrice());
                        Handler.get().giveItem(tradeSlot.getItemStack().getItem(), 1);

                    } else {
                        hasBeenPressed = false;
                        amount = index;
                        break;
                    }
                } else {
                    Handler.get().sendMsg("You don't have enough gold to buy " + (amount - index) + "x " + tradeSlot.getItemStack().getItem().getName());
                    hasBeenPressed = false;
                    amount = index;
                    break;
                }
                index++;
            }

            int matches = 0;
            for (int j = 0; j < itemSlots.size(); j++) {
                if (matches == amount)
                    break;
                if (itemSlots.get(j).getItemStack() == null)
                    continue;
                if (itemSlots.get(j).getItemStack().getItem().getId() == tradeSlot.getItemStack().getItem().getId()) {
                    if (itemSlots.get(j).getItemStack().getItem().isStackable() && itemSlots.get(j).getItemStack().getAmount() - amount >= 0) {
                        itemSlots.get(j).getItemStack().setAmount(itemSlots.get(j).getItemStack().getAmount() - amount);
                        matches++;
                    } else if (!itemSlots.get(j).getItemStack().getItem().isStackable() && itemSlots.get(j).getItemStack().getAmount() > 0) {
                        itemSlots.get(j).getItemStack().setAmount(0);
                        matches++;
                    }
                }
            }

            tradeSlot.setItemStack(null);
            clearNonStockItems();
            inventoryLoaded = false;
            selectedSlot = null;
            if (selectedShopItem != null)
                selectedShopItem = null;
        } else {
            hasBeenPressed = false;
        }
    }

    /**
     * Sells X items
     *
     * @param amount - quantity to sell
     */
    private void sellXItem(int amount) {
        if (tradeSlot.getItemStack() != null && selectedShopItem == null) {
            if (tradeSlot.getItemStack().getItem().getPrice() <= -1) {
                Handler.get().sendMsg("You cannot sell this item.");
                hasBeenPressed = false;
                return;
            }

            if (amount > tradeSlot.getItemStack().getAmount() && tradeSlot.getItemStack().getItem().isStackable()) {
                amount = tradeSlot.getItemStack().getAmount();
            }

            int inputAmount = amount;

            while (inputAmount != 0) {
                if (Handler.get().playerHasItem(tradeSlot.getItemStack().getItem(), 1)) {
                    if (findFreeSlot(tradeSlot.getItemStack().getItem()) != -1) {
                        Handler.get().removeItem(tradeSlot.getItemStack().getItem(), 1);
                        Handler.get().giveItem(Item.coins, (int) (Math.floor((tradeSlot.getItemStack().getItem().getPrice() * COMMISSION)) * 1));
                        itemSlots.get(findFreeSlot(tradeSlot.getItemStack().getItem())).addItem(tradeSlot.getItemStack().getItem(), 1);
                    } else {
                        Handler.get().sendMsg("You cannot sell any more items to the shops.");
                        break;
                    }

                } else {
                    break;
                }
                inputAmount--;
            }
            inventoryLoaded = false;
            tradeSlot.setItemStack(null);
            selectedSlot = null;
            if (selectedInvItem != null)
                selectedInvItem = null;
            hasBeenPressed = false;
        } else {
            hasBeenPressed = false;
        }
    }

    /*
     * Finds a free slot in the shops when selling an item
     * @returns -1 if no free slot found
     */
    private int findFreeSlot(Item item) {
        boolean firstFreeSlotFound = false;
        int index = -1;
        for (int i = 0; i < itemSlots.size(); i++) {
            if (itemSlots.get(i).getItemStack() == null) {
                if (!firstFreeSlotFound) {
                    firstFreeSlotFound = true;
                    index = i;
                }
            } else if (itemSlots.get(i).getItemStack() != null && !item.isStackable() && itemSlots.get(i).getItemStack().getAmount() == 0 && itemSlots.get(i).getItemStack().getItem().getId() == item.getId()) {
                return i;
            } else if (itemSlots.get(i).getItemStack() != null && !item.isStackable()) {
            } else if (itemSlots.get(i).getItemStack() != null && item.isStackable()) {
                if (itemSlots.get(i).getItemStack().getItem().getId() == item.getId()) {
                    return i;
                }
            }
        }
        if (index != -1)
            return index;
        System.out.println("No free inventory slot available.");
        return -1;
    }

    private ArrayList<Integer> getMatchSlots(Item item) {
        ArrayList<Integer> slots = new ArrayList<>();
        for (int i = 0; i < itemSlots.size(); i++) {
            if (itemSlots.get(i).getItemStack() == null) {
                continue;
            }
            if (itemSlots.get(i).getItemStack().getItem() == item)
                slots.add(i);
        }
        return slots;
    }

    public CopyOnWriteArrayList<ItemSlot> getItemSlots() {
        return itemSlots;
    }

    public void setItemSlots(CopyOnWriteArrayList<ItemSlot> itemSlots) {
        this.itemSlots = itemSlots;
    }

    public CopyOnWriteArrayList<ItemSlot> getInvSlots() {
        return invSlots;
    }

    public void setInvSlots(CopyOnWriteArrayList<ItemSlot> invSlots) {
        this.invSlots = invSlots;
    }

    public Rectangle getWindowBounds() {
        return windowBounds;
    }

    public void setWindowBounds(Rectangle windowBounds) {
        this.windowBounds = windowBounds;
    }

}
