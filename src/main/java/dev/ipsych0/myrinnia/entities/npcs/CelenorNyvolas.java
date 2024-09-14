package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.items.Item;

import java.awt.*;

public class CelenorNyvolas extends Entity {

    public CelenorNyvolas() {
        
        solid = true;
        attackable = false;
        isNpc = true;
        this.stats.setMovementSpeed(1.0);
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
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        if ("hasRake".equals(condition)) {
            return !Handler.get().playerHasItem(Item.rake, 1);
        }
        return false;
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 5:
                if (!Handler.get().playerHasItem(Item.rake, 1)) {
                    Handler.get().giveItem(Item.rake, 1);
                }
                break;
        }
    }
}
