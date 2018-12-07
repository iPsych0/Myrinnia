package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilityhud.AbilitySlot;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class AbilityMaster extends AbilityTrainer implements Serializable {


    private static final long serialVersionUID = 4508934222180048865L;

    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private ArrayList<Ability> abilities;

    private String[] firstDialogue = {"I would like to learn new abilities.", "Could you reset my skill points for me?","Leave."};
    private String[] secondDialogue = {"I can reset your Skill Points for a fee of " + AbilityTrainer.resetCost + " gold."};
    private String[] thirdDialogue = {"Reset points (" + AbilityTrainer.resetCost + " gold).", "Never mind."};

    public AbilityMaster(float x, float y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        abilities = new ArrayList<>();

        for(AbilitySlot a : Handler.get().getAbilityManager().getPlayerHUD().getSlottedAbilities()){
            if(a.getAbility() != null) {
                abilities.add(a.getAbility());
            }
        }



        abilityShopWindow = new AbilityShopWindow(abilities);

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.lorraine, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    public void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new AbilityMaster(xSpawn, ySpawn));
    }

    @Override
    public void interact() {
        switch (speakingTurn) {

            case 0:
                speakingTurn++;
                return;

            case 1:
                if (!AbilityShopWindow.isOpen) {
                    chatDialogue = new ChatDialogue(firstDialogue);
                    speakingTurn++;
                    break;
                } else {
                    speakingTurn = 1;
                    break;
                }
            case 2:
                if (chatDialogue == null) {
                    speakingTurn = 1;
                    break;
                }
                if (chatDialogue.getChosenOption().getOptionID() == 0) {
                    if(Handler.get().getQuest(QuestList.AMysteriousFinding).getState() != Quest.QuestState.COMPLETED){
                        Handler.get().getQuest(QuestList.AMysteriousFinding).getRequirements()[0].setTaskDone(true);
                    }
                    AbilityShopWindow.isOpen = true;
                    chatDialogue = null;
                    speakingTurn = 1;
                    break;
                }
                else if (chatDialogue.getChosenOption().getOptionID() == 1) {
                    speakingTurn++;
                    chatDialogue = new ChatDialogue(secondDialogue);
                    break;
                }
            case 3:
                if (chatDialogue == null) {
                    speakingTurn = 1;
                    break;
                }
                chatDialogue = new ChatDialogue(thirdDialogue);
                speakingTurn++;
                break;
            case 4:
                if (chatDialogue == null) {
                    speakingTurn = 1;
                    break;
                }
                if (chatDialogue.getChosenOption().getOptionID() == 0) {
                    resetSkillPoints();
                    chatDialogue = null;
                    speakingTurn = 1;
                    break;
                }
                else if (chatDialogue.getChosenOption().getOptionID() == 1) {
                    chatDialogue = null;
                    speakingTurn = 1;
                    break;
                }

        }
    }
}
