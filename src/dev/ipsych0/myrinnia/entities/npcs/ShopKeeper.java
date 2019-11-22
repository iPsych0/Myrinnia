package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.shops.ShopWindow;

import java.util.ArrayList;
import java.util.List;

public abstract class ShopKeeper extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = 3802705595380640443L;
    ShopWindow shopWindow;
    String shopName;
    protected List<ItemStack> itemStacks;

    ShopKeeper(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        if (itemsShop != null) {
            // TODO: LOAD ITEMSTACKS FROM FILE
//            itemStacks = Utils.loadShop(itemsShop);
        }
        attackable = false;
        isNpc = true;

        shopWindow = new ShopWindow(itemStacks);

    }

    public ShopWindow getShopWindow() {
        return shopWindow;
    }

    public void setShopWindow(ShopWindow shopWindow) {
        this.shopWindow = shopWindow;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
