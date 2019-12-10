package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;

import java.awt.*;

public class Djaan extends Creature {

    public Djaan(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
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

//    @Override
//    protected boolean choiceConditionMet(String condition) {
//        switch (condition) {
//            case "bountyAccepted":
//                if (bounty.isAccepted()) {
//                    return true;
//                }
//                break;
//            default:
//                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
//                return false;
//        }
//        return false;
//    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Djaan(xSpawn, ySpawn, width, height, name, combatLevel, dropTable, jsonFile, animationTag, shopItemsFile, lastFaced));
    }

    @Override
    protected void updateDialogue() {

    }
}
