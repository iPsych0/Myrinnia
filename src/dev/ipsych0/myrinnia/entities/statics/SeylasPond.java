package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.*;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;

import java.awt.*;

public class SeylasPond extends StaticEntity {

    private Quest quest = Handler.get().getQuest(QuestList.WaterMagic);

    public SeylasPond(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = false;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

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
                if (Handler.get().questInProgress(QuestList.WaterMagic)) {
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
                CharacterStats combatStyle = (CharacterStats) quest.getCheckValue("combatStyle");
                Ability a;
                if (combatStyle == CharacterStats.Melee) {
                    AbilityManager.abilityMap.get(FrostJabAbility.class).setUnlocked(true);
                    Handler.get().addTip(new TutorialTip("You unlocked 'Frost Jab'. Press B to open Ability Overview."));
                    a = AbilityManager.abilityMap.get(FrostJabAbility.class);

                } else if (combatStyle == CharacterStats.Ranged) {
                    AbilityManager.abilityMap.get(GlacialShotAbility.class).setUnlocked(true);
                    Handler.get().addTip(new TutorialTip("You unlocked 'Glacial Shot'. Press B to open Ability Overview."));
                    a = AbilityManager.abilityMap.get(GlacialShotAbility.class);
                } else {
                    AbilityManager.abilityMap.get(IceBallAbility.class).setUnlocked(true);
                    Handler.get().addTip(new TutorialTip("You unlocked 'Ice Ball'. Press B to open Ability Overview."));
                    a = AbilityManager.abilityMap.get(IceBallAbility.class);
                }

                script.getDialogues().get(3).setText(script.getDialogues().get(3).getText().replaceFirst("\\{AbilityName\\}", a.getName()));

                quest.nextStep();
                quest.addStep("Return to Elder Selwyn.");
                break;
            case 4:
                speakingTurn = -1;
                die();
                break;
        }
    }
}
