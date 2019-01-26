package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class ConditionSplat extends HitSplat {

    private Entity receiver;
    private Condition condition;
    private int ty;
    private int xOffset;
    private int damage;

    public ConditionSplat(Entity receiver, Condition condition, int damage) {
        this.receiver = receiver;
        this.condition = condition;
        this.damage = damage;
        this.xOffset = Handler.get().getRandomNumber(-8, 8);
        setActive(true);
    }

    @Override
    public void tick() {
        if (isActive()) {

        }
    }

    @Override
    public void render(Graphics g) {
        if (isActive()) {
            if (condition != null) {

                // Draw the condition sprite
                g.drawImage(condition.getImg(), (int) (receiver.x - Handler.get().getGameCamera().getxOffset() + receiver.width / 2 + xOffset),
                        (int) (receiver.y - Handler.get().getGameCamera().getyOffset() + receiver.height - 16 - ty), 16, 16, null);

                // Draw the damage number
                if (damage > 0) {
                    Text.drawString(g, String.valueOf(damage),
                            (int) (receiver.x - Handler.get().getGameCamera().getxOffset() + receiver.width + receiver.width / 2 - 16 + xOffset),
                            (int) (receiver.y - Handler.get().getGameCamera().getyOffset() + receiver.height - ty), false, Color.RED, Assets.font20);
                }

                // If the enemy died, stop ticking, but finish the render of the last condition
                if(!receiver.isActive()){
                    if(ty == 60) {
                        this.setActive(false);
                        condition.setActive(false);
                        condition = null;
                    }
                    ty++;
                    return;
                }

                // If the duration is greater than 0 at any given time
                if (ty <= condition.getDuration()) {
                    // Tick the condition effect
                    if (ty == 0) {
                        condition.setDuration(condition.getDuration() - 60);
                        receiver.tickCondition(receiver, condition);
                    } else if (ty == 60) {
                        // After 1 second, recreate the damage splat
                        Handler.get().getWorld().getEntityManager().getHitSplats().add(new ConditionSplat(receiver, condition, damage));
                        this.setActive(false);

                    }
                // If the condition duration is 0, don't tick anymore, but let the last hitsplat disappear
                } else if(condition.getDuration() <= 0){
                    if(ty == 60) {
                        this.setActive(false);
                        condition.setActive(false);
                        condition = null;
                        return;
                    }
                }
            }
            ty++;
        }else{
            condition.setActive(false);
            condition = null;
        }
    }
}
