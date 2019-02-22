package dev.ipsych0.myrinnia.entities.buffs;

import dev.ipsych0.myrinnia.entities.Buff;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class IntBuff extends Buff {

    private int intBuff;
    private boolean percentageIncrease;
    private int intIncrease;
    private static int totalIncrease;

    public IntBuff(Entity receiver, int durationSeconds, int intBuff) {
        this(receiver, durationSeconds, intBuff, false);
    }

    public IntBuff(Entity receiver, int durationSeconds, int intBuff, boolean percentageIncrease) {
        super(receiver, durationSeconds);
        this.percentageIncrease = percentageIncrease;
        this.intBuff = intBuff;
    }

    @Override
    public void apply() {
        Creature r = ((Creature) receiver);
        if(percentageIncrease){
            // Get percentage increase
            double percentage = (double) intBuff / 100d;

            // Get new STR value
            double newInt = r.getIntelligence() * (1d + percentage);

            // Get the number of STR points increased
            intIncrease = (int)newInt - r.getIntelligence();
            totalIncrease += intIncrease;

            r.setIntelligence(r.getIntelligence() + intIncrease);
        }else {
            r.setIntelligence(r.getIntelligence() + intBuff);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void clear() {
        Creature r = ((Creature) receiver);
        if(percentageIncrease){
            r.setIntelligence(r.getIntelligence() - totalIncrease);
        }else {
            r.setIntelligence(r.getIntelligence() - intBuff);
        }
    }

    @Override
    public void render(Graphics g, int x, int y) {
        if (this.isActive()) {
            g.drawImage(Assets.magicIcon, x + 4, y + 4, ItemSlot.SLOTSIZE - 8, ItemSlot.SLOTSIZE - 8, null);
            Text.drawString(g, String.valueOf((timeLeft / 60) + 1), x + 18, y + 26, false, Color.YELLOW, Assets.font14);
        }
    }
}
