package dev.ipsych0.myrinnia.entities.buffs;

import dev.ipsych0.myrinnia.entities.Buff;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.utils.Timer;
import dev.ipsych0.myrinnia.utils.TimerHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class AttributeBuff extends Buff {

    private double statBuff;
    private double oldStatBuff;
    private boolean percentageIncrease;
    private int totalIncrease;
    private double totalIncreaseDecimal;
    private Attribute attribute;
    private transient BufferedImage img;
    private static final String STR_NAME = "Strength";
    private static final String DEX_NAME = "Dexterity";
    private static final String INT_NAME = "Intelligence";
    private static final String VIT_NAME = "Vitality";
    private static final String DEF_NAME = "Defence";
    private static final String ATKSPD_NAME = "Attack Speed";
    private static final String MOVSPD_NAME = "Movement Speed";

    public enum Attribute {
        STR(Assets.strBuffIcon, 0),
        DEX(Assets.dexBuffIcon, 1),
        INT(Assets.intBuffIcon, 2),
        VIT(Assets.vitBuffIcon, 3),
        DEF(Assets.defBuffIcon, 4),
        ATKSPD(Assets.atkSpdBuffIcon, 5),
        MOVSPD(Assets.movSpdBuffIcon, 6);

        BufferedImage img;
        int buffId;

        Attribute(BufferedImage img, int buffId) {
            this.img = img;
            this.buffId = buffId;
        }

        BufferedImage getImg() {
            return img;
        }

        int getBuffId() {
            return buffId;
        }
    }

    public AttributeBuff(Attribute attribute, Entity receiver, double durationSeconds, double statBuff) {
        this(attribute, receiver, durationSeconds, statBuff, false, false);
    }

    public AttributeBuff(Attribute attribute, Entity receiver, double durationSeconds, double statBuff, boolean isAdditive) {
        this(attribute, receiver, durationSeconds, statBuff, false, isAdditive);
    }

    public AttributeBuff(Attribute attribute, Entity receiver, double durationSeconds, double statBuff, boolean percentageIncrease, boolean isAdditive) {
        super(receiver, durationSeconds, isAdditive);
        this.attribute = attribute;
        this.percentageIncrease = percentageIncrease;
        this.statBuff = statBuff;
        this.img = attribute.getImg();
        this.setBuffId(attribute.getBuffId());
    }

    @Override
    public void apply() {
        Creature r = ((Creature) receiver);
        // If we already have a buff, first remove the current stat buff then apply the new one
        if (getTimesStacked() >= 1) {
            removeStat(r, statBuff);
            setTimesStacked(0);
            // If the time on the current buff is lower, we set the time to the newer buff
        } else {
            timeLeft = timeLeft + (int) effectDuration;
        }

        if (incomingBuff != null) {
            AttributeBuff inc = ((AttributeBuff) incomingBuff);
            // If additive, then stack the buffs and remove them independently after
            if (incomingBuff.isAdditive()) {
                this.statBuff += inc.statBuff;
                Timer timer = new Timer((long) (inc.effectDuration / 60), TimeUnit.SECONDS, () -> {
                    // Upon finishing, remove this additive buff.
                    removeStat(r, inc.statBuff);
                    this.statBuff -= inc.statBuff;
                });
                timeLeft += (int) incomingBuff.getEffectDuration();
                effectDuration += (int) incomingBuff.getEffectDuration();
                TimerHandler.get().addTimer(timer);
            } else if (this.isAdditive()) {
                oldStatBuff = this.statBuff;
                Timer timer = new Timer((this.timeLeft / 60L), TimeUnit.SECONDS, () -> {
                    // Upon finishing, remove this additive buff.
                    removeStat(r, this.oldStatBuff);
                    this.statBuff -= this.oldStatBuff;
                });
                this.statBuff += inc.statBuff;
                TimerHandler.get().addTimer(timer);
                // If the incoming buff has more time than the previous, make it better
                if (timeLeft < inc.effectDuration) {
                    this.effectDuration = (int) inc.effectDuration;
                    timeLeft = (int) effectDuration;
                }
                if (!incomingBuff.isAdditive()) {
                    this.setAdditive(false);
                }
            } else if (inc.statBuff >= statBuff) {
                this.statBuff = inc.statBuff;
                // If the incoming buff has more time than the previous, make it better
                if (timeLeft < inc.effectDuration) {
                    this.effectDuration = (int) inc.effectDuration;
                    timeLeft = (int) effectDuration;
                }
            }
        }
        addStat(r, statBuff);
    }

    @Override
    public void update() {

    }

    @Override
    public void clear() {
        Creature r = ((Creature) receiver);
        removeStat(r, statBuff);
    }

    private void addStat(Creature r, double statBuff) {
        // Get percentage increase
        double percentage = statBuff / 100d;
        int statIncreaseInt;
        double statIncreaseDouble;

        switch (attribute) {
            case STR:
                double newStr = r.getStrength() * (1d + percentage);
                statIncreaseInt = (int) Math.ceil(newStr) - r.getStrength();
                totalIncrease += statIncreaseInt;
                if (percentageIncrease) {
                    r.setStrength(r.getStrength() + statIncreaseInt);
                } else {
                    r.setStrength(r.getStrength() + (int) statBuff);
                }
                break;
            case DEF:
                double newDef = r.getDefence() * (1d + percentage);
                statIncreaseInt = (int) Math.ceil(newDef) - r.getDefence();
                totalIncrease += statIncreaseInt;
                if (percentageIncrease) {
                    r.setDefence(r.getDefence() + statIncreaseInt);
                } else {
                    r.setDefence(r.getDefence() + (int) statBuff);
                }
                break;
            case DEX:
                double newDex = r.getDexterity() * (1d + percentage);
                statIncreaseInt = (int) Math.ceil(newDex) - r.getDexterity();
                totalIncrease += statIncreaseInt;
                if (percentageIncrease) {
                    r.setDexterity(r.getDexterity() + statIncreaseInt);
                } else {
                    r.setDexterity(r.getDexterity() + (int) statBuff);
                }
                break;
            case INT:
                double newInt = r.getIntelligence() * (1d + percentage);
                statIncreaseInt = (int) Math.ceil(newInt) - r.getIntelligence();
                totalIncrease += statIncreaseInt;
                if (percentageIncrease) {
                    r.setIntelligence(r.getIntelligence() + statIncreaseInt);
                } else {
                    r.setIntelligence(r.getIntelligence() + (int) statBuff);
                }
                break;
            case VIT:
                double newVit = r.getVitality() * (1d + percentage);
                statIncreaseInt = (int) Math.ceil(newVit) - r.getVitality();
                totalIncrease += statIncreaseInt;
                if (percentageIncrease) {
                    r.setVitality(r.getVitality() + statIncreaseInt);
                } else {
                    r.setVitality(r.getVitality() + (int) statBuff);
                }
                break;
            case ATKSPD:
                double newAtkSpd = r.getAttackSpeed() * (1d + percentage);
                statIncreaseDouble = (int) Math.ceil(newAtkSpd) - r.getAttackSpeed();
                totalIncreaseDecimal += statIncreaseDouble;
                if (percentageIncrease) {
                    r.setAttackSpeed((r.getAttackSpeed() + statIncreaseDouble));
                } else {
                    r.setAttackSpeed((r.getAttackSpeed() + statBuff));
                }
                break;
            case MOVSPD:
                double newMovSpd = r.getSpeed() * (1d + percentage);
                statIncreaseDouble = (int) Math.ceil(newMovSpd) - r.getSpeed();
                totalIncreaseDecimal += statIncreaseDouble;
                if (percentageIncrease) {
                    r.setSpeed((r.getSpeed() + statIncreaseDouble));
                } else {
                    r.setSpeed((r.getSpeed() + statBuff));
                }
                break;
        }
    }

    private void removeStat(Creature r, double statBuff) {
        switch (attribute) {
            case MOVSPD:
                if (percentageIncrease) {
                    r.setSpeed((r.getSpeed() - totalIncreaseDecimal));
                } else {
                    r.setSpeed(r.getSpeed() - (int) statBuff);
                }
                break;
            case ATKSPD:
                if (percentageIncrease) {
                    r.setAttackSpeed((r.getAttackSpeed() - totalIncreaseDecimal));
                } else {
                    r.setAttackSpeed(r.getAttackSpeed() - (int) statBuff);
                }
                break;
            case VIT:
                if (percentageIncrease) {
                    r.setVitality(r.getVitality() - totalIncrease);
                } else {
                    r.setVitality(r.getVitality() - (int) statBuff);
                }
                break;
            case INT:
                if (percentageIncrease) {
                    r.setIntelligence(r.getIntelligence() - totalIncrease);
                } else {
                    r.setIntelligence(r.getIntelligence() - (int) statBuff);
                }
                break;
            case DEX:
                if (percentageIncrease) {
                    r.setDexterity(r.getDexterity() - totalIncrease);
                } else {
                    r.setDexterity(r.getDexterity() - (int) statBuff);
                }
                break;
            case DEF:
                if (percentageIncrease) {
                    r.setDefence(r.getDefence() - totalIncrease);
                } else {
                    r.setDefence(r.getDefence() - (int) statBuff);
                }
                break;
            case STR:
                if (percentageIncrease) {
                    r.setStrength(r.getStrength() - totalIncrease);
                } else {
                    r.setStrength(r.getStrength() - (int) statBuff);
                }
                break;
        }
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (this.isActive()) {
            g.drawImage(img, x + 4, y + 4, ItemSlot.SLOTSIZE - 8, ItemSlot.SLOTSIZE - 8, null);
            Text.drawString(g, String.valueOf((timeLeft / 60) + 1), x + 18, y + 26, false, Color.YELLOW, Assets.font14);
        }
    }

    @Override
    public String getDescription() {
        String text = null;
        switch (attribute) {
            case STR:
                if (percentageIncrease) {
                    text = "Increases Strength by " + totalIncrease + ".";
                } else {
                    text = "Increases Strength by " + statBuff + ".";
                }
                break;
            case INT:
                if (percentageIncrease) {
                    text = "Increases Intelligence by " + totalIncrease + ".";
                } else {
                    text = "Increases Intelligence by " + statBuff + ".";
                }
                break;
            case DEF:
                if (percentageIncrease) {
                    text = "Increases Defence by " + totalIncrease + ".";
                } else {
                    text = "Increases Defence by " + statBuff + ".";
                }
                break;
            case DEX:
                if (percentageIncrease) {
                    text = "Increases Dexterity by " + totalIncrease + ".";
                } else {
                    text = "Increases Dexterity by " + statBuff + ".";
                }
                break;
            case VIT:
                if (percentageIncrease) {
                    text = "Increases Vitality by " + totalIncrease + ".";
                } else {
                    text = "Increases Vitality by " + statBuff + ".";
                }
                break;
            case ATKSPD:
                if (percentageIncrease) {
                    text = "Increases Attack Speed by " + totalIncreaseDecimal + ".";
                } else {
                    text = "Increases Attack Speed by " + statBuff + ".";
                }
                break;
            case MOVSPD:
                if (percentageIncrease) {
                    text = "Increases Movement Speed by " + totalIncreaseDecimal + ".";
                } else {
                    text = "Increases Movement Speed by " + statBuff + ".";
                }
                break;
        }

        return text;
    }

    @Override
    public String toString() {
        String text = null;
        switch (attribute) {
            case STR:
                text = STR_NAME;
                break;
            case INT:
                text = INT_NAME;
                break;
            case DEF:
                text = DEF_NAME;
                break;
            case DEX:
                text = DEX_NAME;
                break;
            case VIT:
                text = VIT_NAME;
                break;
            case ATKSPD:
                text = ATKSPD_NAME;
                break;
            case MOVSPD:
                text = MOVSPD_NAME;
                break;
        }

        return text;
    }
}
