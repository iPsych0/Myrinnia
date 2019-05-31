package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest.QuestState;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;

public class Campfire extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = 1894028112446761958L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();

    private Campfire(float x, float y) {
        super(x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT);

        script = Utils.loadScript("campfire.json");

        isNpc = true;
        attackable = false;
    }

    @Override
    public void tick() {

    }

    @Override
    public void die() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.eruption1[2], (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "startFirstQuest":
                if (!Handler.get().questStarted(QuestList.TheFirstQuest)) {
                    Handler.get().getQuest(QuestList.TheFirstQuest).setState(QuestState.IN_PROGRESS);
                    Handler.get().addQuestStep(QuestList.TheFirstQuest, "Investigate the fire.");
                }
                return true;
            case "progressFirstQuest":
                if (Handler.get().questInProgress(QuestList.TheFirstQuest)) {
                    Handler.get().getQuest(QuestList.TheFirstQuest).nextStep();
                    Handler.get().addQuestStep(QuestList.TheFirstQuest, "Solve the mystery of the campfire.");
                    return true;
                }
                return false;
            case "takeSword":
                if (Handler.get().questInProgress(QuestList.TheFirstQuest)) {
                    if (!Handler.get().invIsFull(Item.testSword)) {
                        Handler.get().getQuest(QuestList.TheFirstQuest).nextStep();
                        Handler.get().addQuestStep(QuestList.TheFirstQuest, "Reward:\n1x Test Sword\n Recipe: Purple Sword");
                        Handler.get().getQuest(QuestList.TheFirstQuest).nextStep();
                        Handler.get().getQuest(QuestList.TheFirstQuest).setState(QuestState.COMPLETED);
                        Handler.get().giveItem(Item.testSword, 1);
                        Handler.get().discoverRecipe(Item.purpleSword);
                    } else {
                        Handler.get().sendMsg("Your inventory is full.");
                        return false;
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
        Handler.get().getWorld().getEntityManager().addEntity(new Campfire(xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }

}