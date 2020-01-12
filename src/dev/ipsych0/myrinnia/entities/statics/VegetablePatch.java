package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.skills.FarmingResource;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.skills.ui.FarmingPatch;
import dev.ipsych0.myrinnia.skills.ui.FarmingUI;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;

import java.awt.*;

public class VegetablePatch extends StaticEntity implements FarmingPatch {

    /**
     *
     */
    private static final long serialVersionUID = -8804679431303966524L;
    private FarmingResource resource;
    private FarmingUI farmingUI;

    public VegetablePatch(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);


        farmingUI = new FarmingUI(SkillCategory.Vegetables, this);

        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 0;
        bounds.height = 0;

        solid = false;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {
        if (farmingUI.isOpen()) {
            farmingUI.tick();
        }

        if (resource != null) {
            long currentTime = System.currentTimeMillis();
            if (((currentTime - resource.getTimePlanted()) / 1000L) >= resource.getTimeToGrow()) {
                // TODO: RESOURCE CAN BE HARVESTED
                System.out.println("Resource done!");
                resource = null;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (resource != null) {
            for (int x = 0; x < width; x += 32) {
                for (int y = 0; y < height; y += 32) {
                    g.drawImage(Assets.cropsPlanted1, (int) (this.x + x - Handler.get().getGameCamera().getxOffset()), (int) (this.y + y - Handler.get().getGameCamera().getyOffset()), 32, 32, null);
                }
            }
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        if (farmingUI.isOpen()) {
            farmingUI.render(g);
        } else {
            // If nothing planted yet, draw the farming icon
            if (resource == null) {
                g.drawImage(Assets.farmingIcon, (int) (x + width / 2 - 16 - Handler.get().getGameCamera().getxOffset()), (int) (y - 36 - Handler.get().getGameCamera().getyOffset()), 32, 32, null);
            }
        }
    }

    @Override
    public void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    public void interact() {
        switch (speakingTurn) {
            case 0:
                farmingUI.setOpen(true);
                break;
        }
    }

    @Override
    protected void updateDialogue() {

    }

    @Override
    public void plant(ItemStack seeds) {
        // Get the resource by Item
        resource = (FarmingResource) Handler.get().getSkillResource(SkillsList.FARMING, seeds.getItem());
        if (resource == null) {
            System.err.println("Could not fetch the farming resource.");
            return;
        }

        if (!Handler.get().playerHasItem(seeds.getItem(), resource.getQuantity())) {
            Handler.get().sendMsg("You need " + resource.getQuantity() + " " + seeds.getItem().getName() + " to plant that crop.");
            resource = null;
            return;
        }

        // Plant the seed and remove from inventory.
        Handler.get().removeItem(seeds.getItem(), resource.getQuantity());
        resource.setTimePlanted(System.currentTimeMillis());
        Handler.get().sendMsg("You planted " + resource.getQuantity() + " " + seeds.getItem().getName() + ".");
    }
}
