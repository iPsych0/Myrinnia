package dev.ipsych0.myrinnia.entities.buffs;

import dev.ipsych0.myrinnia.entities.Buff;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AttributeBuff extends Buff {

    private double statBuff;
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

    public AttributeBuff(Attribute attribute, Entity receiver, int durationSeconds, double statBuff) {
        this(attribute, receiver, durationSeconds, statBuff, false);
    }

    public AttributeBuff(Attribute attribute, Entity receiver, int durationSeconds, double statBuff, boolean percentageIncrease) {
        super(receiver, durationSeconds);
        this.attribute = attribute;
        this.percentageIncrease = percentageIncrease;
        this.statBuff = statBuff;
        this.img = attribute.getImg();
        this.setBuffId(attribute.getBuffId());
    }

    @Override
    public void apply() {
        Creature r = ((Creature) receiver);
        addStat(r);
    }

    @Override
    public void update() {

    }

    @Override
    public void clear() {
        Creature r = ((Creature) receiver);
        removeStat(r);
    }

    private void addStat(Creature r) {
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
                    r.setAttackSpeed((float) (r.getAttackSpeed() + statIncreaseDouble));
                } else {
                    r.setAttackSpeed((float) (r.getAttackSpeed() + statBuff));
                }
                break;
            case MOVSPD:
                double newMovSpd = r.getSpeed() * (1d + percentage);
                statIncreaseDouble = (int) Math.ceil(newMovSpd) - r.getSpeed();
                totalIncreaseDecimal += statIncreaseDouble;
                if (percentageIncrease) {
                    r.setSpeed((float) (r.getSpeed() + statIncreaseDouble));
                } else {
                    r.setSpeed((float) (r.getSpeed() + statBuff));
                }
                break;
        }
    }

    private void removeStat(Creature r) {
        switch (attribute) {
            case MOVSPD:
                if (percentageIncrease) {
                    r.setSpeed((float) (r.getSpeed() - totalIncreaseDecimal));
                } else {
                    r.setSpeed(r.getSpeed() - (int) statBuff * getTimesStacked());
                }
                break;
            case ATKSPD:
                if (percentageIncrease) {
                    r.setAttackSpeed((float) (r.getAttackSpeed() - totalIncreaseDecimal));
                } else {
                    r.setAttackSpeed(r.getAttackSpeed() - (int) statBuff * getTimesStacked());
                }
                break;
            case VIT:
                if (percentageIncrease) {
                    r.setVitality(r.getVitality() - totalIncrease);
                } else {
                    r.setVitality(r.getVitality() - (int) statBuff * getTimesStacked());
                }
                break;
            case INT:
                if (percentageIncrease) {
                    r.setIntelligence(r.getIntelligence() - totalIncrease);
                } else {
                    r.setIntelligence(r.getIntelligence() - (int) statBuff * getTimesStacked());
                }
                break;
            case DEX:
                if (percentageIncrease) {
                    r.setDexterity(r.getDexterity() - totalIncrease);
                } else {
                    r.setDexterity(r.getDexterity() - (int) statBuff * getTimesStacked());
                }
                break;
            case DEF:
                if (percentageIncrease) {
                    r.setDefence(r.getDefence() - totalIncrease);
                } else {
                    r.setDefence(r.getDefence() - (int) statBuff * getTimesStacked());
                }
                break;
            case STR:
                if (percentageIncrease) {
                    r.setStrength(r.getStrength() - totalIncrease);
                } else {
                    r.setStrength(r.getStrength() - (int) statBuff * getTimesStacked());
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
                    text = "Increases Strength by " + statBuff * getTimesStacked() + ".";
                }
                break;
            case INT:
                if (percentageIncrease) {
                    text = "Increases Intelligence by " + totalIncrease + ".";
                } else {
                    text = "Increases Intelligence by " + statBuff * getTimesStacked() + ".";
                }
                break;
            case DEF:
                if (percentageIncrease) {
                    text = "Increases Defence by " + totalIncrease + ".";
                } else {
                    text = "Increases Defence by " + statBuff * getTimesStacked() + ".";
                }
                break;
            case DEX:
                if (percentageIncrease) {
                    text = "Increases Dexterity by " + totalIncrease + ".";
                } else {
                    text = "Increases Dexterity by " + statBuff * getTimesStacked() + ".";
                }
                break;
            case VIT:
                if (percentageIncrease) {
                    text = "Increases Vitality by " + totalIncrease + ".";
                } else {
                    text = "Increases Vitality by " + statBuff * getTimesStacked() + ".";
                }
                break;
            case ATKSPD:
                if (percentageIncrease) {
                    text = "Increases Attack Speed by " + totalIncreaseDecimal + ".";
                } else {
                    text = "Increases Attack Speed by " + statBuff * getTimesStacked() + ".";
                }
                break;
            case MOVSPD:
                if (percentageIncrease) {
                    text = "Increases Movement Speed by " + totalIncreaseDecimal + ".";
                } else {
                    text = "Increases Movement Speed by " + statBuff * getTimesStacked() + ".";
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
