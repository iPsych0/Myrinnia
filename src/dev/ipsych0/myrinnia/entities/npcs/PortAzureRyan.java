package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.skills.ui.Bounty;
import dev.ipsych0.myrinnia.skills.ui.BountyManager;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class PortAzureRyan extends Creature {

    private boolean hasRemovedAxe;
    private boolean completedBounty;

    public PortAzureRyan(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        attackable = false;
        isNpc = true;

        aDown = new Animation(250, Assets.portAzureRyanDown);
        aLeft = new Animation(250, Assets.portAzureRyanLeft);
        aRight = new Animation(250, Assets.portAzureRyanRight);
        aUp = new Animation(250, Assets.portAzureRyanUp);
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
            case "hasCompletedBounty":
                Bounty bounty = BountyManager.get().getBountyByZoneAndTask(Zone.PortAzure, "Cut the Crab");
                if (!bounty.isCompleted() && Handler.get().playerHasItem(Item.ryansAxe, 1)) {
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
        Handler.get().getWorld().getEntityManager().addEntity(new PortAzureRyan(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {
        Bounty bounty = BountyManager.get().getBountyByZoneAndTask(Zone.PortAzure, "Cut the Crab");
        switch (speakingTurn) {
            case 0:
                if (bounty.isCompleted()) {
                    speakingTurn = 3;
                }
                break;
            case 1:
                if (!hasRemovedAxe) {
                    Handler.get().removeItem(Item.ryansAxe, 1);
                    hasRemovedAxe = true;
                }
                break;
            case 2:
                if (hasRemovedAxe && !completedBounty) {
                    Handler.get().giveItem(Item.simpleAxe, 1);
                }
                break;
            case 3:
                if (!completedBounty) {
                    bounty.setCompleted(true);
                    Handler.get().getQuest(QuestList.BountyHunter).nextStep();
                    Handler.get().getQuest(QuestList.BountyHunter).setState(Quest.QuestState.COMPLETED);
                    Handler.get().getSkill(SkillsList.BOUNTYHUNTER).addExperience(150);
                    Handler.get().removeItem(Item.bountyContract, 1);
                    Handler.get().addTip(new TutorialTip("Press L to open your Skills Menu."));
                }
                completedBounty = true;
                break;
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
