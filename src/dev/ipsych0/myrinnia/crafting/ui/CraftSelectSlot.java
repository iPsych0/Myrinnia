package dev.ipsych0.myrinnia.crafting.ui;

import dev.ipsych0.myrinnia.crafting.CraftingRecipe;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class CraftSelectSlot extends UIImageButton implements Serializable {

    private static final long serialVersionUID = -1885672491477527797L;
    private CraftingRecipe recipe;

    public CraftSelectSlot(int x, int y, CraftingRecipe recipe) {
        super(x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, Assets.genericButton);

        this.recipe = recipe;
    }

    public void tick() {
        super.tick();
    }

    public void render(Graphics2D g) {
        super.render(g);
        if (recipe.isDiscovered()) {
            g.drawImage(recipe.getResult().getItem().getTexture(), x, y, null);
            Text.drawString(g, String.valueOf(recipe.getResult().getAmount()), x + width + (width / 2) - 36, y + 8, false, Color.YELLOW, Assets.font14);
        } else {
            g.drawImage(Assets.undiscovered, x, y, null);
        }
    }

    public CraftingRecipe getRecipe() {
        return recipe;
    }

    public void setRecipe(CraftingRecipe recipe) {
        this.recipe = recipe;
    }
}
