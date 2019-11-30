package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.AbilityManager;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;

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
                if (combatStyle == CharacterStats.Melee) {
                    // TODO: ADD ABILITIES HERE BASED ON FIRST WEAPON CHOICE
//                    AbilityManager.abilityMap.get()
                } else if (combatStyle == CharacterStats.Ranged) {

                } else {

                }

                quest.nextStep();
                quest.addStep("Return to Elder Selwyn.");
                die();
                break;
        }
    }
}
