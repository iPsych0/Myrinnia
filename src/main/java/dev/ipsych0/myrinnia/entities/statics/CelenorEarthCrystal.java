package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Map;

@Slf4j
public class CelenorEarthCrystal extends Entity {

    public static boolean puzzleCompleted;
    private boolean abilitiesReceived;
    private Animation chargeAnim;
    private boolean animShown;

    public CelenorEarthCrystal(double x, double y, int width, int height, Map<String,String> props) {
        super(x, y, width, height, props);
        solid = true;
        attackable = false;
        isNpc = true;
        staticNpc = true;

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
        Handler.get().getWorld().getEntityManager().addEntity(new CelenorEarthCrystal(x, y, width, height, props));
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
    protected void onDialogueShown(String action) {
        switch (speakingTurn) {
            case 3:
                Handler.get().sendMsg("Abilities learnt!");
                abilitiesReceived = true;
                break;
        }
    }
}
