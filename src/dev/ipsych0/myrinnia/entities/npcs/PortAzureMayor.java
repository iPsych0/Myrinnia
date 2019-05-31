package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.statics.StaticEntity;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestStep;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;

public class PortAzureMayor extends StaticEntity {


    private static final long serialVersionUID = 101550362959052644L;

    public PortAzureMayor(float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        script = Utils.loadScript("testnpc1.json");
        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void die() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.mayor, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "has100gold":
                if (Handler.get().playerHasItem(Item.coins, 100)) {
                    return true;
                }
                break;
            case "has1000gold":
                if (Handler.get().playerHasItem(Item.coins, 1000)) {
                    return true;
                }
                break;
            case "has1testsword":
                if (Handler.get().playerHasItem(Item.testSword, 1)) {
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
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 0:
                if (Handler.get().getQuest(QuestList.TheThirdQuest).getState() == Quest.QuestState.NOT_STARTED) {
                    Handler.get().getQuest(QuestList.TheThirdQuest).setState(Quest.QuestState.IN_PROGRESS);
                    Handler.get().getQuest(QuestList.TheThirdQuest).getQuestSteps().add(new QuestStep("Listen to the TestNPC's story."));
                }
                break;
            case 1:
                Handler.get().giveItem(Item.testPickaxe, 1);
                break;
            case 2:
                Handler.get().removeItem(Item.testPickaxe, 1);
                break;
            case 3:
                if (Handler.get().getQuest(QuestList.TheThirdQuest).getState() == Quest.QuestState.IN_PROGRESS) {
                    Handler.get().getQuest(QuestList.TheThirdQuest).nextStep();
                    Handler.get().getQuest(QuestList.TheThirdQuest).setState(Quest.QuestState.COMPLETED);
                }
                Handler.get().giveItem(Item.coins, 100);
                break;
            case 4:
                Handler.get().removeItem(Item.coins, 100);
                break;
            case 5:
                Handler.get().giveItem(Item.testSword, 1);
                break;
            case 6:
                Handler.get().removeItem(Item.testSword, 1);
                break;
            case 7:
                Handler.get().removeItem(Item.coins, 1000);
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {

    }

    public String getName() {
        return "Mayor";
    }


}