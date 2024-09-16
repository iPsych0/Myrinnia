package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.shops.Stock;
import dev.ipsych0.myrinnia.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ShopKeeper extends Creature {


    private static final long serialVersionUID = 3802705595380640443L;
    protected ShopWindow shopWindow;
    protected String shopName;
    protected List<Stock> itemStacks;

    ShopKeeper(float x, float y, int width, int height, Map<String, String> props) {
        super(x, y, width, height, props);
        if (shopItemsFile != null) {
            itemStacks = Utils.loadStocks(shopItemsFile);
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
