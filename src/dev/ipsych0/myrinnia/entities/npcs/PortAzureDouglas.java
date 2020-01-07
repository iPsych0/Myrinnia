package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;

public class PortAzureDouglas extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.GatheringYourStuff);
    private boolean tipDisplayed = false;
    private boolean scriptChanged;

    public PortAzureDouglas(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;

        aDown = new Animation(250, Assets.portAzureDouglasDown);
        aLeft = new Animation(250, Assets.portAzureDouglasLeft);
        aRight = new Animation(250, Assets.portAzureDouglasRight);
        aUp = new Animation(250, Assets.portAzureDouglasUp);
        aDefault = aDown;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "hasCompletedBountyHunter":
                if (Handler.get().questCompleted(QuestList.GettingStarted)) {
                    return true;
                }
                break;
            case "has5logs":
                if (Handler.get().questInProgress(QuestList.GatheringYourStuff) && Handler.get().playerHasItem(Item.lightWood, 5)) {
                    return true;
                }
                break;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
        return false;
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new PortAzureDouglas(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, lastFaced));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 5:
                speakingCheckpoint = 5;
                if (!Handler.get().questStarted(QuestList.GatheringYourStuff)) {
                    quest.setState(QuestState.IN_PROGRESS);
                }
                break;
            case 7:
                if (!scriptChanged && Handler.get().questCompleted(QuestList.GatheringYourStuff)) {
                    scriptChanged = true;
                    script = Utils.loadScript("port_azure_douglas2.json");
                    speakingCheckpoint = 0;
                    speakingTurn = 0;
                    break;
                }
                speakingCheckpoint = 7;
                if (Handler.get().questInProgress(QuestList.GatheringYourStuff) && Handler.get().playerHasItem(Item.lightWood, 5)) {
                    Handler.get().giveItem(Item.simpleFishingRod, 1);
                    Handler.get().removeItem(Item.lightWood, 5);
                    quest.nextStep();
                }
                break;
            case 8:
                if(!tipDisplayed){
                    Handler.get().addTip(new TutorialTip("You can use fish and other ingredients to craft food. Food provides long term buffs that can be used to make your character stronger."));
                    tipDisplayed = true;
                }
                break;
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        if (Handler.get().hasQuestReqs(QuestList.GatheringYourStuff) && quest.getState() == QuestState.NOT_STARTED) {
            g.drawImage(Assets.exclamationIcon, (int) (x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - 32 - Handler.get().getGameCamera().getyOffset()), null);
        }
    }
}
