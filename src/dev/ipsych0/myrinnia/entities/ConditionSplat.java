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
    public void render(Graphics2D g) {
        if (isActive()) {
            if (condition != null) {

                // Draw the condition sprite
                g.drawImage(condition.getImg(), (int) (receiver.x - Handler.get().getGameCamera().getxOffset() + receiver.width / 2 + 8 + xOffset),
                        (int) (receiver.y - Handler.get().getGameCamera().getyOffset() + receiver.height - 16 - ty), 16, 16, null);

                // Draw the damage number
                if (damage > 0) {
                    Text.drawString(g, String.valueOf(damage),
                            (int) (receiver.x - Handler.get().getGameCamera().getxOffset() + receiver.width + receiver.width / 2 - 8 + xOffset),
                            (int) (receiver.y - Handler.get().getGameCamera().getyOffset() + receiver.height - ty), false, Color.YELLOW, Assets.font24);
                }
            }
            ty++;


            if(ty >= 60){
                setActive(false);
            }
        }
    }
}
