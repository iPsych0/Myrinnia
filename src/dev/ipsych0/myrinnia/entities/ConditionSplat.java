package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class ConditionSplat extends HitSplat {

    private Condition condition;
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
                fadeOutCondition(g);
            }

            ty++;
            if (alpha <= 0) {
                setActive(false);
            }
        }
    }

    private void fadeOutCondition(Graphics2D g) {
        alpha -= ALPHA_PER_TICK;
        ALPHA_PER_TICK *= exponent;
        if (alpha < 0) {
            alpha = 0;
        }

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g.setComposite(ac);

        // Draw the condition sprite
        g.drawImage(condition.getImg(), (int) (receiver.x - Handler.get().getGameCamera().getxOffset() + receiver.width / 2 + 8 + xOffset),
                (int) (receiver.y - Handler.get().getGameCamera().getyOffset() + receiver.height - 16 - ty), 16, 16, null);

        // Draw the damage number
        if (damage > 0) {
            Text.drawString(g, String.valueOf(damage),
                    (int) (receiver.x - Handler.get().getGameCamera().getxOffset() + receiver.width + receiver.width / 2 - 8 + xOffset),
                    (int) (receiver.y - Handler.get().getGameCamera().getyOffset() + receiver.height - ty), false, Color.YELLOW, Assets.font24);
        }

        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        g.setComposite(ac);
    }
}
