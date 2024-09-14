package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.entities.Entity;import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class CelenorEarthCrystal extends Entity {

    public static boolean puzzleCompleted;
    private boolean abilitiesReceived;
    private Animation chargeAnim;
    private boolean animShown;

    public CelenorEarthCrystal() {
        
        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        if (!puzzleCompleted || abilitiesReceived) {
            g.drawImage(Assets.celenorUnchargedCrystal, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()),
                    width, height, null);
        }
        if (puzzleCompleted && !animShown) {
            if (chargeAnim == null)
                chargeAnim = new Animation((2000 / Assets.earthCharge.length), Assets.earthCharge, true);

            chargeAnim.tick();
            if (!chargeAnim.isTickDone()) {
                g.drawImage(chargeAnim.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - 128 - Handler.get().getGameCamera().getyOffset()),
                        32, 192, null);
            } else {
                animShown = true;
                chargeAnim = null;
            }
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        CelenorEarthCrystal respawn = new CelenorEarthCrystal();
        respawn.setX(x);
        respawn.setY(y);
        Handler.get().getWorld().getEntityManager().addEntity(respawn);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "hasCompletedPuzzle":
                return puzzleCompleted;
            case "hasReceivedAbilities":
                return abilitiesReceived;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 3:
                Handler.get().sendMsg("Abilities learnt!");
                abilitiesReceived = true;
                break;
        }
    }
}
