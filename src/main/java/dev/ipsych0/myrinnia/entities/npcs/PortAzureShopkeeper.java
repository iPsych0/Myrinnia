package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;
import lombok.extern.slf4j.Slf4j;

import java.awt.Graphics2D;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
public class PortAzureShopkeeper extends ShopKeeper {

    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private Quest quest = Handler.get().getQuest(QuestList.GettingStarted);

    public PortAzureShopkeeper(float x, float y, int width, int height, Map<String, String> props) {
        super(x, y, width, height, props);
        shopName = "Port Azure's General Store";

        chatConditions = Map.of(
                "mayorQuest", () -> quest.getState() == QuestState.IN_PROGRESS && !quest.getQuestSteps().getFirst().isFinished(),
                "openShop", () -> {
                    if (!ShopWindow.isOpen) {
                        ShopWindow.open();
                    }
                    return false;
                }
        );

        chatActions = Map.of(
                "giveChosenItem", () -> {
                    if (chatDialogue.getChosenOption().getOptionID() == 0) {
                        Handler.get().giveItem(Item.simpleStaff, 1);
                        Handler.get().getQuest(QuestList.WaveGoodbye).addNewCheck("chosenItem", Item.simpleStaff);
                    } else if (chatDialogue.getChosenOption().getOptionID() == 1) {
                        Handler.get().giveItem(Item.simpleBow, 1);
                        Handler.get().getQuest(QuestList.WaveGoodbye).addNewCheck("chosenItem", Item.simpleBow);
                    } else if (chatDialogue.getChosenOption().getOptionID() == 2) {
                        Handler.get().giveItem(Item.simpleSword, 1);
                        Handler.get().getQuest(QuestList.WaveGoodbye).addNewCheck("chosenItem", Item.simpleSword);
                    }
                    Handler.get().addTip(new TutorialTip("Right-click an item in your inventory to equip it."));
                    Handler.get().getQuest(QuestList.GettingStarted).nextStep();
                }
        );

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void die() {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new PortAzureShopkeeper(xSpawn, ySpawn, width, height, props));
    }

}
