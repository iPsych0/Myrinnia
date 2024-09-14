package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.shops.Stock;
import dev.ipsych0.myrinnia.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class ShopKeeper extends Entity {


    private static final long serialVersionUID = 3802705595380640443L;
    protected ShopWindow shopWindow;
    protected String shopName;
    protected List<Stock> itemStacks;

    ShopKeeper() {
        
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
}
