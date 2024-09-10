package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;


public class FarmingSlot extends UIImageButton implements Serializable {

    private static final long serialVersionUID = 6894810811350660833L;
    private ItemStack seeds;

    public FarmingSlot(int x, int y, ItemStack seeds) {
        super(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, Assets.genericButton);
        this.seeds = seeds;
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g) {
        super.render(g);
        g.drawImage(seeds.getItem().getTexture(), x, y, width, height, null);
        Text.drawString(g, String.valueOf(seeds.getAmount()), x, y + ItemSlot.SLOTSIZE - 21, false, Color.YELLOW, Assets.font14);
    }

    public ItemStack getSeed() {
        return seeds;
    }

    public void setSeeds(ItemStack seeds) {
        this.seeds = seeds;
    }
}
