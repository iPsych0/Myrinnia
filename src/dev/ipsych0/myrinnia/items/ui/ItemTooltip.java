package dev.ipsych0.myrinnia.items.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemRarity;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class ItemTooltip implements Serializable {


    private static final long serialVersionUID = -5708178237486962575L;
    private int x, y;

    public ItemTooltip(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick() {

    }

    public void render(Item item, Graphics2D g) {
        if (item.getEquipSlot() == EquipSlot.None.getSlotId()) {
            g.drawImage(Assets.uiWindow, x, y, 160, 64, null);
        } else {
            if (item.getRequirements() == null || item.getRequirements().length == 0)
                g.drawImage(Assets.uiWindow, x, y, 160, 154, null);
            else
                g.drawImage(Assets.uiWindow, x, y, 160, 170 + (item.getRequirements().length * 16), null);
        }


        Text.drawString(g, item.getName(), x + 7, y + 16, false, Color.YELLOW, Assets.font14);

        /*
         * Draw the colour of the item's rarity
         */
        g.setColor(ItemRarity.getColor(item));
        Text.drawString(g, item.getItemRarity().toString(), x + 7, y + 32, false, g.getColor(), Assets.font14);

        if (item.getRequirements() != null && item.getRequirements().length > 0) {
            boolean hasStats = false;
            int numMatches = 0;
            for (int i = 0; i < item.getRequirements().length; i++) {
                if (item.getRequirements()[i].getStat().getLevel() < item.getRequirements()[i].getLevel()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.GREEN);
                    numMatches++;
                }
                Text.drawString(g, item.getRequirements()[i].getStat().toString() + ": " +
                                item.getRequirements()[i].getLevel(),
                        x + 7, y + 180 + (i * 16), false, g.getColor(), Assets.font14);
            }
            if (numMatches == item.getRequirements().length)
                hasStats = true;

            if (hasStats)
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.RED);
            Text.drawString(g, "Requirements:", x + 7, y + 164, false, g.getColor(), Assets.font14);
        }

        if (item.getEquipSlot() != EquipSlot.None.getSlotId()) {
            // Only compare stats if an item is actually equipped
            if (Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack() != null) {

                /*
                 * Draw strength colour red/green if stats are lower/higher
                 */
                if (item.getStrength() > Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getStrength()) {
                    g.setColor(Color.GREEN);
                } else if (item.getStrength() < Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getStrength()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.YELLOW);
                }
                Text.drawString(g, "STR: " + item.getStrength(), x + 7, y + 48, false, g.getColor(), Assets.font14);
                Text.drawString(g, "(" + (item.getStrength() - Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getStrength()) + ")", x + 116, y + 48, false, g.getColor(), Assets.font14);

                /*
                 * Draw dexterity colour red/green if stats are lower/higher
                 */
                if (item.getDexterity() > Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getDexterity()) {
                    g.setColor(Color.GREEN);
                } else if (item.getDexterity() < Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getDexterity()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.YELLOW);
                }
                Text.drawString(g, "DEX: " + item.getDexterity(), x + 7, y + 64, false, g.getColor(), Assets.font14);
                Text.drawString(g, "(" + (item.getDexterity() - Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getDexterity()) + ")", x + 116, y + 64, false, g.getColor(), Assets.font14);

                /*
                 * Draw intelligence colour red/green if stats are lower/higher
                 */
                if (item.getIntelligence() > Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getIntelligence()) {
                    g.setColor(Color.GREEN);
                } else if (item.getIntelligence() < Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getIntelligence()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.YELLOW);
                }
                Text.drawString(g, "INT: " + item.getIntelligence(), x + 7, y + 80, false, g.getColor(), Assets.font14);
                Text.drawString(g, "(" + (item.getIntelligence() - Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getIntelligence()) + ")", x + 116, y + 80, false, g.getColor(), Assets.font14);

                /*
                 * Draw defence colour red/green if stats are lower/higher
                 */

                if (item.getDefence() > Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getDefence()) {
                    g.setColor(Color.GREEN);
                } else if (item.getDefence() < Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getDefence()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.YELLOW);
                }
                Text.drawString(g, "DEF: " + item.getDefence(), x + 7, y + 96, false, g.getColor(), Assets.font14);
                Text.drawString(g, "(" + (item.getDefence() - Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getDefence()) + ")", x + 116, y + 96, false, g.getColor(), Assets.font14);

                /*
                 * Draw vitality colour red/green if stats are lower/higher
                 */
                if (item.getVitality() > Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getVitality()) {
                    g.setColor(Color.GREEN);
                } else if (item.getVitality() < Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getVitality()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.YELLOW);
                }
                Text.drawString(g, "VIT: " + item.getVitality(), x + 7, y + 112, false, g.getColor(), Assets.font14);
                Text.drawString(g, "(" + (item.getVitality() - Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getVitality()) + ")", x + 116, y + 112, false, g.getColor(), Assets.font14);

                /*
                 * Draw atk speed colour red/green if stats are lower/higher
                 */
                if (item.getAttackSpeed() > Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getAttackSpeed()) {
                    g.setColor(Color.GREEN);
                } else if (item.getAttackSpeed() < Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getAttackSpeed()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.YELLOW);
                }

                Text.drawString(g, "ATK Spd.: " + item.getAttackSpeed(), x + 7, y + 128, false, g.getColor(), Assets.font14);
                Text.drawString(g, "(" + (item.getAttackSpeed() - Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getAttackSpeed()) + ")", x + 116, y + 128, false, g.getColor(), Assets.font14);

                /*
                 * Draw movement speed colour red/green if stats are lower/higher
                 */
                if (item.getMovementSpeed() > Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getMovementSpeed()) {
                    g.setColor(Color.GREEN);
                } else if (item.getMovementSpeed() < Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getMovementSpeed()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.YELLOW);
                }
                Text.drawString(g, "MOV Spd.: " + item.getMovementSpeed(), x + 7, y + 144, false, g.getColor(), Assets.font14);
                Text.drawString(g, "(" + (item.getMovementSpeed() - Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getMovementSpeed()) + ")", x + 116, y + 144, false, g.getColor(), Assets.font14);

            } else {
                g.setColor(Color.YELLOW);
                Text.drawString(g, "STR: " + item.getStrength(), x + 7, y + 48, false, g.getColor(), Assets.font14);
                Text.drawString(g, "DEX: " + item.getDexterity(), x + 7, y + 64, false, g.getColor(), Assets.font14);
                Text.drawString(g, "INT: " + item.getIntelligence(), x + 7, y + 80, false, g.getColor(), Assets.font14);
                Text.drawString(g, "DEF: " + item.getDefence(), x + 7, y + 96, false, g.getColor(), Assets.font14);
                Text.drawString(g, "VIT: " + item.getVitality(), x + 7, y + 112, false, g.getColor(), Assets.font14);
                Text.drawString(g, "ATK Spd.: " + item.getAttackSpeed(), x + 7, y + 128, false, g.getColor(), Assets.font14);
                Text.drawString(g, "MOV Spd.: " + item.getMovementSpeed(), x + 7, y + 144, false, g.getColor(), Assets.font14);
            }
        }
    }
}
