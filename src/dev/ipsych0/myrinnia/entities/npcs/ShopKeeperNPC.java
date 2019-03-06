package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.util.ArrayList;

public class ShopKeeperNPC extends ShopKeeper {


    /**
     *
     */
    private static final long serialVersionUID = -3340636213278064668L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private ArrayList<ItemStack> shopItems;

    public ShopKeeperNPC(String shopName, float x, float y) {
        super(shopName, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        script = Utils.loadScript("shopkeeper.json");

        shopItems = new ArrayList<ItemStack>();

        shopItems.add(new ItemStack(Item.regularLogs, 5));
        shopItems.add(new ItemStack(Item.regularOre, 10));
        shopItems.add(new ItemStack(Item.testSword, 1));

        shopWindow = new ShopWindow(shopItems);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.lorraine, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "openShop":
                if (!ShopWindow.isOpen) {
                    ShopWindow.open();
                }
                return true;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new ShopKeeperNPC(shopName, xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }

    public String getName() {
        return "Shop owner";
    }

}
