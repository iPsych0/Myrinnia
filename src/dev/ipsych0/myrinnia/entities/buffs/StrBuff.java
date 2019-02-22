package dev.ipsych0.myrinnia.entities.buffs;

import dev.ipsych0.myrinnia.entities.Buff;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class StrBuff extends Buff {

    private int strBuff;
    private boolean percentageIncrease;
    private int strIncrease;
    private static int totalIncrease;

    public StrBuff(Entity receiver, int durationSeconds, int strBuff) {
        this(receiver, durationSeconds, strBuff, false);
    }

    public StrBuff(Entity receiver, int durationSeconds, int strBuff, boolean percentageIncrease) {
        super(receiver, durationSeconds);
        this.percentageIncrease = percentageIncrease;
        this.strBuff = strBuff;
    }

    @Override
    public void apply() {
        Creature r = ((Creature) receiver);
        if(percentageIncrease){
            // Get percentage increase
            double percentage = (double)strBuff / 100d;

            // Get new STR value
            double newStr = r.getStrength() * (1d + percentage);

            // Get the number of STR points increased
            strIncrease = (int)newStr - r.getStrength();
            totalIncrease += strIncrease;

            r.setStrength(r.getStrength() + strIncrease);
        }else {
            r.setStrength(r.getStrength() + strBuff);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void clear() {
        Creature r = ((Creature) receiver);
        if(percentageIncrease){
            r.setStrength(r.getStrength() - totalIncrease);
        }else {
            r.setStrength(r.getStrength() - strBuff);
        }
    }

    @Override
    public void render(Graphics g, int x, int y) {
        if (this.isActive()) {
            g.drawImage(Assets.testSword, x + 4, y + 4, ItemSlot.SLOTSIZE - 8, ItemSlot.SLOTSIZE - 8, null);
            Text.drawString(g, String.valueOf((timeLeft / 60) + 1), x + 18, y + 26, false, Color.YELLOW, Assets.font14);
        }
    }
}
