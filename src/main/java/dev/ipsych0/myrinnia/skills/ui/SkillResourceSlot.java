package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.crafting.CraftingRecipe;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.skills.SkillResource;
import dev.ipsych0.myrinnia.ui.UIObject;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;

public class SkillResourceSlot extends UIObject implements Serializable {
    private SkillResource skillResource;
    private CraftingRecipe craftingRecipe;

    public SkillResourceSlot(SkillResource skillResource, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.skillResource = skillResource;
    }

    public SkillResourceSlot(CraftingRecipe craftingRecipe, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.craftingRecipe = craftingRecipe;
    }

    @Override
    public void tick() {
        Rectangle mouse = Handler.get().getMouse();
        if (craftingRecipe != null) {
            tickCrafting(mouse);
        } else if (skillResource != null) {
            tickOthers(mouse);
        }
    }

    private void tickCrafting(Rectangle mouse) {
        // Print the recipe ingredients or generic message to unlock the recipe
        if (getBounds().contains(mouse) && craftingRecipe.isDiscovered() && Handler.get().getMouseManager().isLeftPressed() && SkillsOverviewUI.hasBeenPressed) {
            Handler.get().sendMsg(craftingRecipe.toString());
            SkillsOverviewUI.hasBeenPressed = false;
        } else if (getBounds().contains(mouse) && !craftingRecipe.isDiscovered() && Handler.get().getMouseManager().isLeftPressed() && SkillsOverviewUI.hasBeenPressed) {
            Handler.get().sendMsg("Explore the world or do quests to unlock unknown recipes!");
            SkillsOverviewUI.hasBeenPressed = false;
        }
    }

    private void tickOthers(Rectangle mouse) {
        // Print the resource description when clicked
        if (getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && SkillsOverviewUI.hasBeenPressed) {
            Handler.get().sendMsg(skillResource.toString());
            SkillsOverviewUI.hasBeenPressed = false;
        }
    }

    @Override
    public void render(Graphics2D g) {
        Rectangle mouse = Handler.get().getMouse();
        if (craftingRecipe != null) {
            renderCrafting(g, mouse);
        } else if (skillResource != null) {
            renderOthers(g, mouse);
        }
    }

    private void renderOthers(Graphics2D g, Rectangle mouse) {
        g.drawImage(Assets.genericButton[1], x, y, 32, 32, null);
        if (getBounds().contains(mouse)) {
            g.drawImage(Assets.genericButton[0], x, y, 32, 32, null);
        } else {
            g.drawImage(Assets.genericButton[1], x, y, 32, 32, null);
        }

        g.drawImage(skillResource.getItem().getTexture(), x, y, 32, 32, null);
        Text.drawString(g, skillResource.getItem().getName(), x + width + 8, y + 20, false, Color.YELLOW, Assets.font14);
        Text.drawString(g, String.valueOf(skillResource.getLevelRequirement()), x - 16, y + 16, true, Color.YELLOW, Assets.font20);
    }

    private void renderCrafting(Graphics2D g, Rectangle mouse) {
        g.drawImage(Assets.genericButton[1], x, y, 32, 32, null);
        if (getBounds().contains(mouse)) {
            g.drawImage(Assets.genericButton[0], x, y, 32, 32, null);
        } else {
            g.drawImage(Assets.genericButton[1], x, y, 32, 32, null);
        }

        if (craftingRecipe.isDiscovered()) {
            g.drawImage(craftingRecipe.getResult().getItem().getTexture(), x, y, 32, 32, null);
            Text.drawString(g, craftingRecipe.getResult().getItem().getName(), x + width + 8, y + 20, false, Color.YELLOW, Assets.font14);
        } else {
            g.drawImage(Assets.undiscovered, x, y, 32, 32, null);
            Text.drawString(g, "Unknown", x + width + 8, y + 20, false, Color.YELLOW, Assets.font14);
        }
        Text.drawString(g, String.valueOf(craftingRecipe.getRequiredLevel()), x - 16, y + 16, true, Color.YELLOW, Assets.font20);
    }
}
