package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.Serializable;
import java.util.Map;

@Slf4j
public class Banker extends Creature implements Serializable {

    private static final long serialVersionUID = -4843560960961688987L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();

    public Banker(float x, float y, int width, int height, Map<String, String> props) {
        super(x, y, width, height, props);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(), (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void die() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "openBank":
                if (!BankUI.isOpen) {
                    BankUI.open();
                }
                return true;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Banker(xSpawn, ySpawn, width, height, props));
    }

    @Override
    protected void updateDialogue() {

    }
}
