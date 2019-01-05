package dev.ipsych0.myrinnia.items;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class ItemTooltip {

    private int x, y;

    public ItemTooltip(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tick() {

    }

    public void render(Item item, Graphics g) {
        if (item.getEquipSlot() == EquipSlot.None.getSlotId()) {
            g.drawImage(Assets.shopWindow, x, y, 160, 64, null);
        } else {
            if (item.getRequirements() == null || item.getRequirements().length == 0)
                g.drawImage(Assets.shopWindow, x, y, 160, 122, null);
            else
                g.drawImage(Assets.shopWindow, x, y, 160, 138 + (item.getRequirements().length * 16), null);
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
                        x + 7, y + 148 + (i * 16), false, g.getColor(), Assets.font14);
            }
            if (numMatches == item.getRequirements().length)
                hasStats = true;

            if (hasStats)
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.RED);
            Text.drawString(g, "Requirements:", x + 7, y + 132, false, g.getColor(), Assets.font14);
        }

        if (item.getEquipSlot() != EquipSlot.None.getSlotId()) {
            // Only compare stats if an item is actually equipped
            if (Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack() != null) {
                /*
                 * Draw power colour red/green if stats are lower/higher
                 */

                if (item.getPower() > Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getPower()) {
                    g.setColor(Color.GREEN);
                } else if (item.getPower() < Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getPower()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.YELLOW);
                }
                Text.drawString(g, "Power: " + item.getPower(), x + 7, y + 48, false, g.getColor(), Assets.font14);
                Text.drawString(g, "(" + (item.getPower() - Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getPower()) + ")", x + 116, y + 48, false, g.getColor(), Assets.font14);

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
                Text.drawString(g, "Defence: " + item.getDefence(), x + 7, y + 64, false, g.getColor(), Assets.font14);
                Text.drawString(g, "(" + (item.getDefence() - Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getDefence()) + ")", x + 116, y + 64, false, g.getColor(), Assets.font14);

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
                Text.drawString(g, "Vitality: " + item.getVitality(), x + 7, y + 80, false, g.getColor(), Assets.font14);
                Text.drawString(g, "(" + (item.getVitality() - Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getVitality()) + ")", x + 116, y + 80, false, g.getColor(), Assets.font14);

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

                Text.drawString(g, "ATK Speed: " + item.getAttackSpeed(), x + 7, y + 96, false, g.getColor(), Assets.font14);
                Text.drawString(g, "(" + (item.getAttackSpeed() - Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getAttackSpeed()) + ")", x + 116, y + 96, false, g.getColor(), Assets.font14);

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
                Text.drawString(g, "Mov. Speed: " + item.getMovementSpeed(), x + 7, y + 112, false, g.getColor(), Assets.font14);
                Text.drawString(g, "(" + (item.getMovementSpeed() - Handler.get().getEquipment().getEquipmentSlots().get(item.getEquipSlot()).getEquipmentStack().getItem().getMovementSpeed()) + ")", x + 116, y + 112, false, g.getColor(), Assets.font14);

            } else {
                g.setColor(Color.YELLOW);
                Text.drawString(g, "Power: " + item.getPower(), x + 7, y + 48, false, g.getColor(), Assets.font14);
                Text.drawString(g, "Defence: " + item.getDefence(), x + 7, y + 64, false, g.getColor(), Assets.font14);
                Text.drawString(g, "Vitality: " + item.getVitality(), x + 7, y + 80, false, g.getColor(), Assets.font14);
                Text.drawString(g, "ATK Speed: " + item.getAttackSpeed(), x + 7, y + 96, false, g.getColor(), Assets.font14);
                Text.drawString(g, "Mov. Speed: " + item.getMovementSpeed(), x + 7, y + 112, false, g.getColor(), Assets.font14);
            }
        }
    }
}
