package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilities.ArcaneRenewalAbility;
import dev.ipsych0.myrinnia.abilities.HealingSpringAbility;
import dev.ipsych0.myrinnia.abilities.MendWoundsAbility;
import dev.ipsych0.myrinnia.abilities.data.AbilityManager;
import dev.ipsych0.myrinnia.abilities.ui.abilityhud.AbilitySlot;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;

import java.awt.*;

public class ElderSelwyn extends Creature {


    private static final long serialVersionUID = 101550362959052644L;
    private Quest quest = Handler.get().getQuest(QuestList.WaveGoodbye);
    private boolean tipShown;
    private Ability learntAbility;

    public ElderSelwyn(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
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
        g.drawImage(Assets.elderSelwyn,
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "hasCompletedMiningAndCrafting":
                if (Handler.get().questCompleted(QuestList.PreparingYourJourney)) {
                    return true;
                }
                break;
            case "hasSlottedAbility":
                for (AbilitySlot as : Handler.get().getAbilityManager().getAbilityHUD().getSlottedAbilities()) {
                    if (as.getAbility() != null && as.getAbility() == learntAbility) {
                        return true;
                    }
                }
                break;
            case "hasDrunkWater":
                return false;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
        return false;
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 5:
                if (quest.getState() == QuestState.NOT_STARTED) {
                    quest.setState(QuestState.IN_PROGRESS);
                    speakingCheckpoint = 5;
                }
                break;
            case 6:
                if (speakingCheckpoint != 6) {
                    speakingCheckpoint = 7;
                    Item chosenItem = (Item) quest.getCheckValue("chosenItem");
                    Class<? extends Ability> abilityClass;
                    if (chosenItem == Item.beginnersBow) {
                        abilityClass = MendWoundsAbility.class;
                        AbilityManager.abilityMap.get(MendWoundsAbility.class).setUnlocked(true);
                    } else if (chosenItem == Item.beginnersSword) {
                        AbilityManager.abilityMap.get(HealingSpringAbility.class).setUnlocked(true);
                        abilityClass = HealingSpringAbility.class;
                    } else {
                        AbilityManager.abilityMap.get(ArcaneRenewalAbility.class).setUnlocked(true);
                        abilityClass = ArcaneRenewalAbility.class;
                    }
                    learntAbility = AbilityManager.abilityMap.get(abilityClass);
                    quest.addNewCheck("combatStyle", learntAbility.getCombatStyle());
                    script.getDialogues().get(speakingTurn).setText(script.getDialogues().get(speakingTurn).getText().replaceFirst("\\{AbilityName\\}", learntAbility.getName()));
                }
                break;
            case 8:
                if (!tipShown) {
                    Dialogue current = script.getDialogues().get(speakingTurn);
                    current.setText(current.getText().replaceFirst("\\{CombatStyle\\}", learntAbility.getCombatStyle().toString()));
                    tipShown = true;
                    Handler.get().addTip(new TutorialTip("Press B to open your Abilities Overview."));
                }
                break;
            case 9:
                if (speakingCheckpoint != 9) {
                    speakingCheckpoint = 9;
                }
                break;
            case 14:
                if (speakingCheckpoint != 14) {
                    speakingCheckpoint = 14;
                    AbilityManager.abilityMap.get(ArcaneRenewalAbility.class).setUnlocked(true);
                    AbilityManager.abilityMap.get(HealingSpringAbility.class).setUnlocked(true);
                    AbilityManager.abilityMap.get(MendWoundsAbility.class).setUnlocked(true);
                }
                break;
            case 15:
                if (speakingCheckpoint != 15) {
                    speakingCheckpoint = 15;
                    quest.nextStep();
                    quest.addNewCheck("hasDrunkWater", false);
                } else {
                    if ((Boolean) quest.getCheckValue("hasDrunkWater")) {
                        speakingTurn = 19;
                        speakingCheckpoint = 19;
                    }
                }
                break;
            case 19:
                if (speakingCheckpoint != 19) {
                    speakingCheckpoint = 19;
                }
                break;
            case 20:
                if (speakingCheckpoint != 20) {
                    speakingCheckpoint = 20;
                }
                if (Handler.get().getQuest(QuestList.WaveGoodbye).getState() == QuestState.IN_PROGRESS) {
                    quest.nextStep();
                    quest.setState(QuestState.COMPLETED);
                    Handler.get().addTip(new TutorialTip("Captain Isaac should be ready for department. He's waiting on the southern docks."));
                }
                break;
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        if (Handler.get().hasQuestReqs(QuestList.WaveGoodbye) && Handler.get().getQuest(QuestList.WaveGoodbye).getState() == QuestState.NOT_STARTED) {
            g.drawImage(Assets.exclamationIcon, (int) (x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - 32 - Handler.get().getGameCamera().getyOffset()), null);
        }
    }

    @Override
    public void respawn() {

    }

}