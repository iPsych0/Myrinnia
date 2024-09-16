package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Map;

@Slf4j
public class ShamrockGuard extends Creature {

    public ShamrockGuard(float x, float y, int width, int height, Map<String, String> props) {
        super(x, y, width, height, props);
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
            case "hasAccess":
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
        Handler.get().getWorld().getEntityManager().addEntity(new ShamrockGuard(xSpawn, ySpawn, width, height, props));
    }

    @Override
    protected void updateDialogue() {

    }
}
