package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.util.ArrayList;

public class PortAzureShopkeeper extends ShopKeeper {


    /**
     *
     */
    private static final long serialVersionUID = -3340636213278064668L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private ArrayList<ItemStack> shopItems;

    public PortAzureShopkeeper(float x, float y, String shopName) {
        super(shopName, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        script = Utils.loadScript("port_azure_shopkeeper.json");

        shopItems = new ArrayList<>();

        shopItems.add(new ItemStack(Item.regularLogs, 1));
        shopItems.add(new ItemStack(Item.regularOre, 1));
        shopItems.add(new ItemStack(Item.testSword, 1));

        shopWindow = new ShopWindow(shopItems);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.shopKeeper, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void die() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "mayorQuest":
                if (Handler.get().getQuest(QuestList.BountyHunter).getQuestSteps().get(0).isFinished()) {
                    if (!ShopWindow.isOpen) {
                        ShopWindow.open();
                    }
                }
                return true;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new PortAzureShopkeeper(xSpawn, ySpawn, shopName));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 5:
                if(chatDialogue.getChosenOption().getOptionID() == 0){
                    Handler.get().giveItem(Item.purpleSword, 1);
                } else if(chatDialogue.getChosenOption().getOptionID() == 1){
                    Handler.get().giveItem(Item.testAxe, 1);
                } else if(chatDialogue.getChosenOption().getOptionID() == 2){
                Handler.get().giveItem(Item.testSword, 1);
            }
                Handler.get().getQuest(QuestList.BountyHunter).nextStep();
                Handler.get().addQuestStep(QuestList.BountyHunter, "Report back to the mayor.");
        }
    }

    public String getName() {
        return "Shopkeeper";
    }

}
