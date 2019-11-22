package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;

import java.awt.*;

public class MayorWilson extends Creature {


    private static final long serialVersionUID = 101550362959052644L;

    public MayorWilson(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        walker = false;
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
        g.drawImage(Assets.portAzureMayor,
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "hasObtainedWeapon":
                if (Handler.get().questInProgress(QuestList.BountyHunter) && Handler.get().getQuest(QuestList.BountyHunter).getQuestSteps().get(0).isFinished()) {
                    return true;
                }
                if (Handler.get().questCompleted(QuestList.BountyHunter)) {
                    script.getDialogues().get(2).setText("Once you have learned all skills, you should ask Captain Isaac to take you with him! You can find him on the docks south of here.");
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
                if (Handler.get().questInProgress(QuestList.BountyHunter) && Handler.get().getQuest(QuestList.BountyHunter).getQuestSteps().get(0).isFinished()) {
                    script.getDialogues().get(0).setText("Welcome back!");
                }
                break;
            case 2:
                if (!Handler.get().questCompleted(QuestList.BountyHunter) && !Handler.get().questStarted(QuestList.BountyHunter)) {
                    Handler.get().addTip(new TutorialTip("Press Q to open your Quest Journal."));
                    Handler.get().getQuest(QuestList.BountyHunter).setState(Quest.QuestState.IN_PROGRESS);
                    Handler.get().addQuestStep(QuestList.BountyHunter, "Choose your first weapon from the store.");
                }
                break;
            case 6:
                if (Handler.get().questInProgress(QuestList.BountyHunter) && !Handler.get().getQuest(QuestList.BountyHunter).getQuestSteps().get(1).isFinished()) {
                    Handler.get().getQuest(QuestList.BountyHunter).nextStep();
                    Handler.get().addQuestStep(QuestList.BountyHunter, "Choose a bounty target from the board.");
                }
                break;
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {

    }

}