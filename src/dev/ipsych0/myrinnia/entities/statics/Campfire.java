package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest.QuestState;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tiles.Tiles;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;

public class Campfire extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = 1894028112446761958L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private Animation campfire;

    public Campfire(float x, float y) {
        super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);

        script = Utils.loadScript("campfire.json");

        isNpc = true;
        attackable = false;
        campfire = new Animation(125, Assets.campfire);
    }

    @Override
    public void tick() {
        campfire.tick();
    }

    @Override
    public void die() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(campfire.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
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
    public void postRender(Graphics g) {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Campfire(xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }

}