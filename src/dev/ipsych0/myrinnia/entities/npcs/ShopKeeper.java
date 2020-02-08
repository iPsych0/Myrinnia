package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.shops.Stock;
import dev.ipsych0.myrinnia.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class ShopKeeper extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = 3802705595380640443L;
    protected ShopWindow shopWindow;
    protected String shopName;
    protected List<Stock> itemStacks;

    ShopKeeper(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        if (itemsShop != null) {
            itemStacks = Utils.loadStocks(itemsShop);
        }

        attackable = false;
        isNpc = true;

        List<ItemStack> items = new ArrayList<>();
        if (itemStacks != null) {
            for (Stock s : itemStacks) {
                items.add(new ItemStack(Item.items[s.getId()], s.getAmount()));
            }
        }
        shopWindow = new ShopWindow(items);
        shopName = name + "'s Store";

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
