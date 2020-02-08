package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.FrostJabAbility;
import dev.ipsych0.myrinnia.abilities.GlacialShotAbility;
import dev.ipsych0.myrinnia.abilities.IceBallAbility;
import dev.ipsych0.myrinnia.abilities.data.AbilityManager;
import dev.ipsych0.myrinnia.cutscenes.Cutscene;
import dev.ipsych0.myrinnia.cutscenes.MoveCameraEvent;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.states.CutsceneState;
import dev.ipsych0.myrinnia.states.State;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;

import java.awt.*;

public class SeylasPond extends StaticEntity {

    private Quest quest = Handler.get().getQuest(QuestList.WaveGoodbye);
    private boolean cutsceneShown;
    private Rectangle cutsceneTrigger = new Rectangle(1440, 4224, 320, 192);

    public SeylasPond(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = false;
        attackable = false;
        isNpc = true;

    }

    @Override
    public void tick() {
        if (!cutsceneShown) {
            if (cutsceneTrigger.contains(Handler.get().getPlayer().getCollisionBounds(0, 0))) {
                State.setState(new CutsceneState(new Cutscene(
                        new MoveCameraEvent(
                                Handler.get().getPlayer().getCollisionBounds(0, 0),
                                getCollisionBounds(0, 0)))));
                cutsceneShown = true;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {
        active = false;
    }

    @Override
    public void respawn() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "waterQuestStarted":
                if (Handler.get().questInProgress(QuestList.WaveGoodbye)) {
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
            case 3:
                quest.addNewCheck("hasDrunkWater", true);

                AbilityManager.abilityMap.get(FrostJabAbility.class).setUnlocked(true);
                AbilityManager.abilityMap.get(GlacialShotAbility.class).setUnlocked(true);
                AbilityManager.abilityMap.get(IceBallAbility.class).setUnlocked(true);
                Handler.get().addTip(new TutorialTip("You have unlocked new abilities. Press B to open Ability Overview."));

                quest.nextStep();
                break;
            case 4:
                speakingTurn = -1;
                die();
                break;
        }
    }
}
