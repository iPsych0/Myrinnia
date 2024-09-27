package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.GraniteWallAbility;
import dev.ipsych0.myrinnia.abilities.data.AbilityManager;

import java.awt.*;
import java.util.Map;

public class StozarAncientRocks extends GenericObject {

    public StozarAncientRocks(double x, double y, int width, int height, Map<String,String> props) {
        super(x, y, width, height, props);
        staticNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new StozarAncientRocks(x, y, width, height, props));
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        if ("hasLearntAbility".equals(condition)) {
            return AbilityManager.abilityMap.get(GraniteWallAbility.class).isUnlocked();
        }
        return false;
    }

    @Override
    protected void onDialogueShown(String action) {
        if (speakingTurn == 3) {
            AbilityManager.abilityMap.get(GraniteWallAbility.class).setUnlocked(true);
        }
    }
}
