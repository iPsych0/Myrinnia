package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.skills.ui.Bounty;
import dev.ipsych0.myrinnia.skills.ui.BountyManager;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class ShamrockEdgar extends Creature {

    private Bounty bounty = BountyManager.get().getBountyByZoneAndTask(Zone.ShamrockTown, "It's mine");

    public ShamrockEdgar(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;
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
            case "bountyAccepted":
                if (bounty.isAccepted()) {
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
        Handler.get().getWorld().getEntityManager().addEntity(new ShamrockEdgar(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, lastFaced));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 0:
                // TODO: IF PLAYER HAS EQUIPMENT ITEM, SET SPEAKING TURN & CHECKPOINT TO 4
                if (false) {
                    speakingTurn = 4;
                    speakingCheckpoint = 4;
                }
                break;
            case 5:
                // TODO: GIVE PLAYER BETTER PICKAXE
                // TODO: REMOVE THE EQUIPMENT ITEM FROM INVENTORY
                bounty.setCompleted(true);
                Handler.get().getSkill(SkillsList.BOUNTYHUNTER).addExperience(100);
                Handler.get().removeItem(Item.bountyContract, 1);
                speakingCheckpoint = 6;
                break;
        }
    }
}
