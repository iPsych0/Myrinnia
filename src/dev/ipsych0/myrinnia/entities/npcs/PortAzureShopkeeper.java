package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestStep;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;

import java.awt.*;
import java.util.List;

public class PortAzureShopkeeper extends ShopKeeper {


    /**
     *
     */
    private static final long serialVersionUID = -3340636213278064668L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();

    public PortAzureShopkeeper(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        shopName = "Port Azure's General Store";
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
                List<QuestStep> steps = Handler.get().getQuest(QuestList.BountyHunter).getQuestSteps();
                if (!steps.isEmpty() && !steps.get(0).isFinished()) {
                    return true;
                }
                break;
            case "openShop":
                if (!ShopWindow.isOpen) {
                    ShopWindow.open();
                }
                break;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
        return false;
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new PortAzureShopkeeper(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, lastFaced));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 5:
                if (chatDialogue.getChosenOption().getOptionID() == 0) {
                    Handler.get().giveItem(Item.beginnersStaff, 1);
                } else if (chatDialogue.getChosenOption().getOptionID() == 1) {
                    Handler.get().giveItem(Item.beginnersBow, 1);
                } else if (chatDialogue.getChosenOption().getOptionID() == 2) {
                    Handler.get().giveItem(Item.beginnersSword, 1);
                }
                Handler.get().addTip(new TutorialTip("Right-click an item in your inventory to equip it."));
                Handler.get().getQuest(QuestList.BountyHunter).nextStep();
                Handler.get().addQuestStep(QuestList.BountyHunter, "Report back to the mayor.");
        }
    }

}
