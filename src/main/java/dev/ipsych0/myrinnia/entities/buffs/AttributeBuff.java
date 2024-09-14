package dev.ipsych0.myrinnia.entities.buffs;

import dev.ipsych0.myrinnia.entities.Buff;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.utils.Timer;
import dev.ipsych0.myrinnia.utils.TimerHandler;
import lombok.Getter;

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

    @Getter
    public enum Attribute {
        STR(Assets.strBuffIcon, 0),
        DEX(Assets.dexBuffIcon, 1),
        INT(Assets.intBuffIcon, 2),
        VIT(Assets.vitBuffIcon, 3),
        DEF(Assets.defBuffIcon, 4),
        ATKSPD(Assets.atkSpdBuffIcon, 5),
        MOVSPD(Assets.movSpdBuffIcon, 6);

        final BufferedImage img;
        final int buffId;

        Attribute(BufferedImage img, int buffId) {
            this.img = img;
            this.buffId = buffId;
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
        Entity r = receiver;
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
        removeStat(receiver, statBuff);
    }

    private void addStat(Entity r, double statBuff) {
        // Get percentage increase
        double percentage = statBuff / 100d;
        int statIncreaseInt;
        double statIncreaseDouble;

        switch (attribute) {
            case STR:
                double newStr = r.getStats().getStrength() * (1d + percentage);
                statIncreaseInt = (int) Math.ceil(newStr) - r.getStats().getStrength();
                totalIncrease += statIncreaseInt;
                if (percentageIncrease) {
                    r.getStats().setStrength(r.getStats().getStrength() + statIncreaseInt);
                } else {
                    r.getStats().setStrength(r.getStats().getStrength() + (int) statBuff);
                }
                break;
            case DEF:
                double newDef = r.getStats().getDefence() * (1d + percentage);
                statIncreaseInt = (int) Math.ceil(newDef) - r.getStats().getDefence();
                totalIncrease += statIncreaseInt;
                if (percentageIncrease) {
                    r.getStats().setDefence(r.getStats().getDefence() + statIncreaseInt);
                } else {
                    r.getStats().setDefence(r.getStats().getDefence() + (int) statBuff);
                }
                break;
            case DEX:
                double newDex = r.getStats().getDexterity() * (1d + percentage);
                statIncreaseInt = (int) Math.ceil(newDex) - r.getStats().getDexterity();
                totalIncrease += statIncreaseInt;
                if (percentageIncrease) {
                    r.getStats().setDexterity(r.getStats().getDexterity() + statIncreaseInt);
                } else {
                    r.getStats().setDexterity(r.getStats().getDexterity() + (int) statBuff);
                }
                break;
            case INT:
                double newInt = r.getStats().getIntelligence() * (1d + percentage);
                statIncreaseInt = (int) Math.ceil(newInt) - r.getStats().getIntelligence();
                totalIncrease += statIncreaseInt;
                if (percentageIncrease) {
                    r.getStats().setIntelligence(r.getStats().getIntelligence() + statIncreaseInt);
                } else {
                    r.getStats().setIntelligence(r.getStats().getIntelligence() + (int) statBuff);
                }
                break;
            case VIT:
                double newVit = r.getStats().getVitality() * (1d + percentage);
                statIncreaseInt = (int) Math.ceil(newVit) - r.getStats().getVitality();
                totalIncrease += statIncreaseInt;
                if (percentageIncrease) {
                    r.setVitality(r.getStats().getVitality() + statIncreaseInt);
                } else {
                    r.setVitality(r.getStats().getVitality() + (int) statBuff);
                }
                break;
            case ATKSPD:
                double newAtkSpd = r.getStats().getAttackSpeed() * (1d + percentage);
                statIncreaseDouble = (int) Math.ceil(newAtkSpd) - r.getStats().getAttackSpeed();
                totalIncreaseDecimal += statIncreaseDouble;
                if (percentageIncrease) {
                    r.getStats().setAttackSpeed((r.getStats().getAttackSpeed() + statIncreaseDouble));
                } else {
                    r.getStats().setAttackSpeed((r.getStats().getAttackSpeed() + statBuff));
                }
                break;
            case MOVSPD:
                double newMovSpd = r.getStats().getMovementSpeed() * (1d + percentage);
                statIncreaseDouble = (int) Math.ceil(newMovSpd) - r.getStats().getMovementSpeed();
                totalIncreaseDecimal += statIncreaseDouble;
                if (percentageIncrease) {
                    r.getStats().setMovementSpeed((r.getStats().getMovementSpeed() + statIncreaseDouble));
                } else {
                    r.getStats().setMovementSpeed((r.getStats().getMovementSpeed() + statBuff));
                }
                break;
        }
    }

    private void removeStat(Entity r, double statBuff) {
        switch (attribute) {
            case MOVSPD:
                if (percentageIncrease) {
                    r.getStats().setMovementSpeed((r.getStats().getMovementSpeed() - totalIncreaseDecimal));
                } else {
                    r.getStats().setMovementSpeed(r.getStats().getMovementSpeed() - statBuff);
                }
                break;
            case ATKSPD:
                if (percentageIncrease) {
                    r.getStats().setAttackSpeed((r.getStats().getAttackSpeed() - totalIncreaseDecimal));
                } else {
                    r.getStats().setAttackSpeed(r.getStats().getAttackSpeed() - statBuff);
                }
                break;
            case VIT:
                if (percentageIncrease) {
                    r.setVitality(r.getStats().getVitality() - totalIncrease);
                } else {
                    r.setVitality(r.getStats().getVitality() - (int) statBuff);
                }
                break;
            case INT:
                if (percentageIncrease) {
                    r.getStats().setIntelligence(r.getStats().getIntelligence() - totalIncrease);
                } else {
                    r.getStats().setIntelligence(r.getStats().getIntelligence() - (int) statBuff);
                }
                break;
            case DEX:
                if (percentageIncrease) {
                    r.getStats().setDexterity(r.getStats().getDexterity() - totalIncrease);
                } else {
                    r.getStats().setDexterity(r.getStats().getDexterity() - (int) statBuff);
                }
                break;
            case DEF:
                if (percentageIncrease) {
                    r.getStats().setDefence(r.getStats().getDefence() - totalIncrease);
                } else {
                    r.getStats().setDefence(r.getStats().getDefence() - (int) statBuff);
                }
                break;
            case STR:
                if (percentageIncrease) {
                    r.getStats().setStrength(r.getStats().getStrength() - totalIncrease);
                } else {
                    r.getStats().setStrength(r.getStats().getStrength() - (int) statBuff);
                }
                break;
        }
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if (this.isActive()) {

            int timeLeft = (this.timeLeft / 60) + 1;
            String text = String.valueOf(timeLeft);

            // Draw minutes left if time left is greater than 60 seconds
            if (timeLeft >= 60) {
                text = timeLeft / 60 + "m";
            }

            g.drawImage(img, x + 4, y + 4, ItemSlot.SLOTSIZE - 8, ItemSlot.SLOTSIZE - 8, null);
            Text.drawString(g, text, x + 18, y + 26, false, Color.YELLOW, Assets.font14);
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
        return switch (attribute) {
            case STR -> STR_NAME;
            case INT -> INT_NAME;
            case DEF -> DEF_NAME;
            case DEX -> DEX_NAME;
            case VIT -> VIT_NAME;
            case ATKSPD -> ATKSPD_NAME;
            case MOVSPD -> MOVSPD_NAME;
        };
    }
}
