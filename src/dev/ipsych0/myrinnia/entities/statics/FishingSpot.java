package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class FishingSpot extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -4511991258183891329L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private Animation spinning;
    private boolean isFishing;
    private int fishingTimer = 0;
    private int minAttempts = 4, maxAttempts = 9;
    private int random = 0;
    private int attempts = 0;
    private Item fish;
    private Item rareMaterial;
    private Item rodUsed;
    private int timeToFish = 180;
    private int originalTimeToFish = 180;
    private int chanceToFish = 700; // 700/1000 = 70% chance to successfully get an ore
    private int originalChanceToFish = 700;
    private int chanceOfRareMaterial = 30; // 30/1000 = 3% chance
    private int originalChanceOfRareMaterial;
    private int experience;
    private int originalExperience;
    private static Map<Integer, Double> chanceToFishMap = Map.ofEntries(
            entry(Item.simpleFishingRod.getId(), 1.00),
            entry(Item.copperFishingRod.getId(), 1.05),
            entry(Item.ironFishingRod.getId(), 1.10),
            entry(Item.steelFishingRod.getId(), 1.15),
            entry(Item.platinumFishingRod.getId(), 1.20),
            entry(Item.titaniumFishingRod.getId(), 1.25),
            entry(Item.obsidianFishingRod.getId(), 1.30),
            entry(Item.primordialFishingRod.getId(), 1.35)
    );
    private static Map<Integer, Double> timeToFishMap = Map.ofEntries(
            entry(Item.simpleFishingRod.getId(), 1.00),
            entry(Item.copperFishingRod.getId(), 0.95),
            entry(Item.ironFishingRod.getId(), 0.90),
            entry(Item.steelFishingRod.getId(), 0.85),
            entry(Item.platinumFishingRod.getId(), 0.80),
            entry(Item.titaniumFishingRod.getId(), 0.75),
            entry(Item.obsidianFishingRod.getId(), 0.70),
            entry(Item.primordialFishingRod.getId(), 0.65)
    );

    public FishingSpot(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        isNpc = true;
        attackable = false;
        spinning = new Animation(125, Assets.whirlpool);

        if (name.equalsIgnoreCase("Mackerel Fishing Spot")) {
            fish = Item.mackerelFish;
            rareMaterial = null; // TODO: ADD RARE MATERIAL
            experience = 10;
            timeToFish = 120;
            chanceToFish = 850; // 85%
            chanceOfRareMaterial = 100; // 10% Chance
        } else if (name.equalsIgnoreCase("Trout Fishing Spot")) {
            fish = Item.trout;
            experience = 15;
            rareMaterial = null; // TODO: ADD RARE MATERIAL
            timeToFish = 150;
            chanceToFish = 825; // 82,5%
            chanceOfRareMaterial = 80; // 8% Chance
        } else if (name.equalsIgnoreCase("Snakehead Fishing Spot")) {
            fish = Item.snakehead;
            experience = 20;
            rareMaterial = null; // TODO: ADD RARE MATERIAL
            timeToFish = 180;
            chanceToFish = 800; // 80%
            chanceOfRareMaterial = 75; // 7,5% Chance
        } else if (name.equalsIgnoreCase("Clam Digging Spot")) {
            fish = Item.clam;
            experience = 50;
            minAttempts = 1;
            maxAttempts = 1;
            rareMaterial = null; // TODO: ADD RARE MATERIAL
            timeToFish = 300;
            chanceToFish = 775; // 77,5%
            chanceOfRareMaterial = 75; // 7,5% Chance
        } else if (name.equalsIgnoreCase("Eel Fishing Spot")) {
            fish = Item.eel;
            experience = 30;
            rareMaterial = null; // TODO: ADD RARE MATERIAL
            timeToFish = 210;
            chanceToFish = 750; // 75%
            chanceOfRareMaterial = 75; // 7,5% Chance
        } else {
            throw new IllegalArgumentException("Fishing Spot name not found: " + name);
        }

        // Store the original values
        originalChanceOfRareMaterial = chanceOfRareMaterial;
        originalChanceToFish = chanceToFish;
        originalExperience = experience;
        originalTimeToFish = timeToFish;
    }

    @Override
    public void tick() {
        spinning.tick();
        if (isFishing) {
            if (Handler.get().invIsFull(fish)) {
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
                if (roll < chanceToFish) {
                    Handler.get().giveItem(fish, 1);
                    Handler.get().sendMsg("You caught something!");
                    Handler.get().getSkillsUI().getSkill(SkillsList.FISHING).addExperience(experience);
                    if (rareMaterial != null && roll < chanceOfRareMaterial) {
                        Handler.get().giveItem(rareMaterial, 1);
                        Handler.get().sendMsg("You found a " + rareMaterial.getName() + "!");
                    }
                } else {
                    Handler.get().sendMsg("The fish got away...");
                }
                attempts++;
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
            if (Handler.get().playerHasSkillLevel(SkillsList.FISHING, fish)) {
                if (Handler.get().playerHasItemType(ItemType.FISHING_ROD)) {

                    List<Item> rods = new ArrayList<>();
                    for (ItemSlot is : Handler.get().getInventory().getItemSlots()) {
                        if (is.getItemStack() != null) {
                            if (is.getItemStack().getItem().isType(ItemType.FISHING_ROD)) {
                                rods.add(is.getItemStack().getItem());
                            }
                        }
                    }

                    // Get the best pickaxe we have in our inventory
                    rodUsed = rods.stream().max((o1, o2) -> {
                        Integer i1 = o1.getPrice();
                        Integer i2 = o2.getPrice();
                        return i1.compareTo(i2);
                    }).get();

                    // Update chances and time to mine based on pickaxe
                    chanceToFish *= chanceToFishMap.getOrDefault(rodUsed.getId(), 1.0);
                    timeToFish *= timeToFishMap.getOrDefault(rodUsed.getId(), 1.0);
                    Handler.get().sendMsg("Fishing...");
                    speakingTurn = 1;
                    isFishing = true;
                } else {
                    Handler.get().sendMsg("You need a fishing rod to catch fish.");
                }
            } else {
                Handler.get().sendMsg("You need a fishing level of " + Handler.get().getSkillResource(SkillsList.FISHING, fish).getLevelRequirement() + " to catch this type of fish.");
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
        Handler.get().getWorld().getEntityManager().addEntity(new FishingSpot(xSpawn, ySpawn, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
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
