package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.npcs.Script;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.skills.FarmingResource;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.skills.ui.FarmingPatch;
import dev.ipsych0.myrinnia.skills.ui.FarmingUI;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;

public class VegetablePatch extends StaticEntity implements FarmingPatch {

    /**
     *
     */
    private static final long serialVersionUID = -8804679431303966524L;
    private FarmingResource resource;
    private FarmingUI farmingUI;
    private ItemStack yield;
    private boolean composted, watered;
    private Script finishedScript = Utils.loadScript("farming_patch_done.json");
    private Script plantableScript;
    private boolean finished;
    public static boolean tutorialDone;
    private Rectangle progressBar;
    private Rectangle totalBar;

    public VegetablePatch(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        totalBar = new Rectangle((int) (x + (width / 2d)) - 32, (int) (y - 16), 64, 16);
        progressBar = new Rectangle((int) (x + (width / 2d)) - 32, (int) (y - 16), 0, 16);
        plantableScript = Utils.loadScript(jsonFile);

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

        // If we've planted the seeds, but the yield is not finished, update timer
        if (resource != null && !finished && yield == null) {
            long currentTime = System.currentTimeMillis();
            if (((currentTime - resource.getTimePlanted()) / 1000L) >= resource.getTimeToGrow()) {
                yield = new ItemStack(resource.getItem(), resource.getHarvestQuantity());
                finished = true;
                script = finishedScript;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (resource != null && !finished) {
            for (int x = 0; x < width; x += 32) {
                for (int y = 0; y < height; y += 32) {
                    g.drawImage(Assets.cropsPlanted1, (int) (this.x + x - Handler.get().getGameCamera().getxOffset()), (int) (this.y + y - Handler.get().getGameCamera().getyOffset()), 32, 32, null);
                }
            }
        }
        if (yield != null) {
            for (int x = 0; x < width; x += 32) {
                for (int y = 0; y < height; y += 32) {
                    g.drawImage(yield.getItem().getTexture(), (int) (this.x + x - Handler.get().getGameCamera().getxOffset()), (int) (this.y + y - Handler.get().getGameCamera().getyOffset()), 32, 32, null);
                }
            }
        }
    }

    @Override
    public void drawProgressBar(Graphics2D g) {
        long currentTime = System.currentTimeMillis();
        double percentDone = ((currentTime - resource.getTimePlanted()) / 1000D / (double) resource.getTimeToGrow());

        progressBar.setSize((int) (totalBar.width * percentDone), 16);
        g.drawImage(Assets.uiWindow, (int) (totalBar.x - Handler.get().getGameCamera().getxOffset()), (int) (totalBar.y - Handler.get().getGameCamera().getyOffset()), totalBar.width, totalBar.height, null);

        g.setColor(Colors.progressBarColor);
        g.fillRoundRect((int) (progressBar.x - Handler.get().getGameCamera().getxOffset()), (int) (progressBar.y - Handler.get().getGameCamera().getyOffset()), progressBar.width, progressBar.height, 4, 4);

        g.setColor(Colors.progressBarOutlineColor);
        g.drawRoundRect((int) (progressBar.x - Handler.get().getGameCamera().getxOffset()), (int) (progressBar.y - Handler.get().getGameCamera().getyOffset()), progressBar.width, progressBar.height, 4, 4);

//        g.setColor(Color.BLACK);
//        g.drawRoundRect((int) (totalBar.x - Handler.get().getGameCamera().getxOffset()), (int) (totalBar.y - Handler.get().getGameCamera().getyOffset()), totalBar.width, totalBar.height, 4, 4);
    }

    @Override
    public void postRender(Graphics2D g) {
        if (farmingUI.isOpen()) {
            farmingUI.render(g);
        } else {
            // If nothing planted yet, draw the farming icon
            if (resource == null && yield == null) {
                g.drawImage(Assets.farmingIcon, (int) (x + width / 2 - 16 - Handler.get().getGameCamera().getxOffset()), (int) (y - 36 - Handler.get().getGameCamera().getyOffset()), 32, 32, null);
            }
            if (resource != null && !finished) {
                drawProgressBar(g);
            }
        }
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "openFarmingUI":
                if (resource != null) {
                    Handler.get().sendMsg("You have already planted " + resource.getSeed().getName() + " here.");
                    return false;
                }
                if (!farmingUI.isOpen()) {
                    farmingUI.setOpen(true);
                }
                return true;
            case "compostPatch":
                if (!composted) {
                    if (Handler.get().playerHasItemType(ItemType.COMPOST)) {
                        composted = true;
                    } else {
                        Handler.get().sendMsg("You don't have any compost.");
                    }
                } else {
                    Handler.get().sendMsg("This patch has already been treated with compost.");
                }
                // TODO: IMPLEMENT COMPOSTING
                return true;
            case "waterPatch":
                if (!watered) {
                    if (Handler.get().playerHasItemType(ItemType.WATERING_CAN)) {
                        watered = true;
                    } else {
                        Handler.get().sendMsg("You don't have a watering can.");
                    }
                } else {
                    Handler.get().sendMsg("This patch is already watered.");
                }
                // TODO: IMPLEMENT WATERING
                return true;
            case "yieldFinished":
                if (finished) {
                    Handler.get().giveItem(yield.getItem(), yield.getAmount());
                    Handler.get().getSkill(SkillsList.FARMING).addExperience(resource.getExperience());
                    script = plantableScript;
                    yield = null;
                    resource = null;
                    composted = false;
                    watered = false;
                    finished = false;
                    totalBar = new Rectangle((int) (x + (width / 2d)) - 32, (int) (y - 16), 64, 16);
                    progressBar = new Rectangle((int) (x + (width / 2d)) - 32, (int) (y - 16), 0, 16);
                    if (!tutorialDone) {
                        tutorialDone = true;
                        Handler.get().addTip(new TutorialTip("Vegetables can be used in cooking to create food."));
                    }
                }
                return true;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    public void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }

    @Override
    public void plant(ItemStack seeds) {
        if (resource != null) {
            Handler.get().sendMsg("You have already planted " + resource.getSeed().getName() + " here.");
            return;
        }
        // Get the resource by Item
        resource = (FarmingResource) Handler.get().getSkillResource(SkillsList.FARMING, seeds.getItem());
        if (resource == null) {
            System.err.println("Could not fetch the farming resource.");
            return;
        }

        // Check if we have enough seeds to plant.
        if (!Handler.get().playerHasItem(seeds.getItem(), resource.getQuantity())) {
            Handler.get().sendMsg("You need " + resource.getQuantity() + " " + seeds.getItem().getName() + " to plant that crop.");
            resource = null;
            return;
        }

        // Create new resource object based on the original model to have unique instances of planted seeds
        resource = new FarmingResource(resource.getLevelRequirement(), resource.getSeed(), resource.getQuantity(),
                resource.getCategory(), resource.getTimeToGrow() / 60L,
                resource.getHarvest(), resource.getHarvestQuantity(), resource.getExperience(), resource.getDescription());

        finished = false;

        // Plant the seed and remove from inventory.
        Handler.get().removeItem(seeds.getItem(), resource.getQuantity());
        resource.setTimePlanted(System.currentTimeMillis());
        Handler.get().sendMsg("You planted " + resource.getQuantity() + " " + seeds.getItem().getName() + ".");
    }
}
