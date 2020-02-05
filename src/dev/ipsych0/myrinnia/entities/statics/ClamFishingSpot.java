package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClamFishingSpot extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -4511991258183891329L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private Animation spinning;
    private boolean isFishing;
    private int fishingTimer = 0;
    private int minAttempts = 1, maxAttempts = 1;
    private int random = 0;
    private int attempts = 0;
    private Item clam;
    private List<ItemStack> rareMaterials;
    private int timeToFish;
    private int chanceToFish;
    private int chanceOfRareMaterial;
    private int experience;

    public ClamFishingSpot(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        isNpc = true;
        attackable = false;
        spinning = new Animation(125, Assets.whirlpool);

        clam = Item.clam;
        experience = 25;
        minAttempts = 1;
        maxAttempts = 1; // Only 1 attempt, either you find something or you don't (50% chance of clam)
        rareMaterials = new ArrayList<>(); // TODO: ADD UNCOMMON ITEMS
        rareMaterials.add(new ItemStack(Item.coins, 5));
        rareMaterials.add(new ItemStack(Item.glass, 1));
        rareMaterials.add(new ItemStack(Item.pileOfSand, 1));
        rareMaterials.add(new ItemStack(Item.crablingClaw, 1));
        rareMaterials.add(new ItemStack(Item.simpleSandals, 1));
        timeToFish = 300; // 5 seconds
        chanceToFish = 500; // 50%
        chanceOfRareMaterial = 200; // 20% Chance

    }

    @Override
    public void tick() {
        spinning.tick();
        if (isFishing) {
            if (Handler.get().invIsFull(clam)) {
                fishingTimer = 0;
                speakingTurn = -1;
                interact();
                setFishing(false);
            }
            if (Player.isMoving || Handler.get().getMouseManager().isLeftPressed() &&
                    !Handler.get().getPlayer().hasLeftClickedUI(Handler.get().getMouse())) {
                fishingTimer = 0;
                speakingTurn = 0;
                setFishing(false);
                return;
            }
            if (random != 0) {
                if (attempts == random) {
                    attempts = 0;
                    setFishing(false);
                    this.active = false;
                    this.die();
                }
            }

            fishingTimer++;

            if (fishingTimer >= timeToFish) {
                int roll = Handler.get().getRandomNumber(1, 1000);

                // We always dig up 1 pile of sand
                Handler.get().giveItem(Item.pileOfSand, 1);
                if (roll < chanceToFish) {
                    Handler.get().giveItem(clam, 1);
                    Handler.get().sendMsg("You found a clam!");
                    Handler.get().getSkillsUI().getSkill(SkillsList.FISHING).addExperience(experience);
                    if (roll < chanceOfRareMaterial) {
                        int rnd = Handler.get().getRandomNumber(0, rareMaterials.size() - 1);
                        Handler.get().giveItem(rareMaterials.get(rnd).getItem(), rareMaterials.get(rnd).getAmount());
                        Handler.get().sendMsg("You found " + rareMaterials.get(rnd).getAmount() + "x " + rareMaterials.get(rnd).getItem().getName() + ".");
                    }
                    attempts++;
                } else {
                    Handler.get().sendMsg("Nothing but sand here...");
                    attempts++;
                }
                speakingTurn = 1;
                fishingTimer = 0;

                if (attempts == minAttempts) {
                    random = Handler.get().getRandomNumber(minAttempts, maxAttempts);
                }
            }

        }
    }

    @Override
    public void die() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(spinning.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {
        if (this.speakingTurn == -1) {
            speakingTurn++;
            return;
        }
        if (this.speakingTurn == 0) {
            if (Handler.get().playerHasSkillLevel(SkillsList.FISHING, clam)) {
                if (Handler.get().playerHasItem(Item.rake, 1)) {
                    Handler.get().sendMsg("Digging...");
                    speakingTurn = 1;
                    isFishing = true;
                } else {
                    Handler.get().sendMsg("You need a rake to catch clams.");
                }
            } else {
                Handler.get().sendMsg("You need a fishing level of " + Handler.get().getSkillResource(SkillsList.FISHING, clam).getLevelRequirement() + " to catch clams.");
            }
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        g.drawImage(Assets.fishingIcon, (int) (x + width / 2 - 16 - Handler.get().getGameCamera().getxOffset()), (int) (y - 36 - Handler.get().getGameCamera().getyOffset()), 32, 32, null);
        if (isFishing) {
            StringBuilder pending = new StringBuilder();
            int dots = (int) Math.ceil(fishingTimer / 30d);
            for (int i = 0; i < dots; i++) {
                pending.append(".");
            }

            Text.drawString(g, pending.toString(), (int) (Handler.get().getPlayer().getX() + 16 - Handler.get().getGameCamera().getxOffset()),
                    (int) (Handler.get().getPlayer().getY() - 16 - Handler.get().getGameCamera().getyOffset()), true, Color.YELLOW, Assets.font24);
        }
    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new ClamFishingSpot(xSpawn, ySpawn, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {

    }

    public boolean isFishing() {
        return isFishing;
    }

    public void setFishing(boolean fishing) {
        isFishing = fishing;
    }
}