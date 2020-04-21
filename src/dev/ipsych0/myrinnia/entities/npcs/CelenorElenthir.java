package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CelenorElenthir extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);
    private Map<Integer, Boolean> questionsAskedMap = new HashMap<>();
    private Map<Integer, Boolean> questionsAskedMap2 = new HashMap<>();
    private static boolean questionsAsked;
    private static boolean questionsAsked2;

    public CelenorElenthir(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;

        quest.addNewCheck("book1", false);
        quest.addNewCheck("book2", false);
        quest.addNewCheck("book3", false);
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
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 0:
                if (quest.getQuestSteps().get(0).isFinished()) {
                    speakingTurn = 10;
                }
                if (quest.getQuestSteps().get(1).isFinished()) {
                    speakingTurn = 12;
                }
                if (quest.getQuestSteps().get(2).isFinished()) {
                    speakingTurn = 19;
                }
                if (quest.getQuestSteps().get(3).isFinished()) {
                    speakingTurn = 20;
                }
                break;
            case 9:
                if (!quest.getQuestSteps().get(0).isFinished()) {
                    quest.nextStep();
                }
                break;
            case 16:
            case 17:
            case 18:
                if (!questionsAsked) {
                    checkAllQuestionsAsked();
                }
                break;
            case 19:
                if (!quest.getQuestSteps().get(2).isFinished()) {
                    quest.nextStep();

                    // Add dialogue to shopkeeper for clue
                    Entity celenorShopkeeper = Handler.get().getEntityByZoneAndName(Zone.CelewynnInside, "Craftsman");
                    Script script = celenorShopkeeper.getScript();
                    script.getDialogues().get(1).getOptions().add(1,
                            new Choice("Have you had any unusual orders?",
                                    2,
                                    null));
                }
                break;
            case 22:
            case 23:
                if (!questionsAsked2) {
                    checkAllQuestionsAsked2();
                }
                break;
            case 24:
                if (!quest.getQuestSteps().get(4).isFinished()) {
                    quest.nextStep();
                }
                break;
        }
    }

    private void checkAllQuestionsAsked() {
        questionsAskedMap.put(speakingTurn, true);

        // Check if all questions are asked
        if (!questionsAsked && questionsAskedMap.size() == 3) {
            questionsAsked = true;
            // Add option to progress the quest
            script.getDialogues().get(15).getOptions().add(0, new Choice("What should I do now?", 19, null));
        }
    }

    private void checkAllQuestionsAsked2() {
        questionsAskedMap2.put(speakingTurn, true);

        // Check if all questions are asked
        if (!questionsAsked2 && questionsAskedMap2.size() == 2) {
            questionsAsked2 = true;
            // Add option to progress the quest
            script.getDialogues().get(21).getOptions().add(0, new Choice("What's our next move?", 24, null));
        }
    }
}
