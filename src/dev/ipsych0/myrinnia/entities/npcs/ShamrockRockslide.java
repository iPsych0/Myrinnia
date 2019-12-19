package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.statics.StaticEntity;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;

public class ShamrockRockslide extends StaticEntity {

    public ShamrockRockslide(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.rockSlide,
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void postRender(Graphics2D g) {

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

    }

    @Override
    protected void updateDialogue() {

    }
}
