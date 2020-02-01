package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Rock extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -8123420086619425263L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private boolean isMining;
    private int miningTimer = 0;
    private int minAttempts = 4, maxAttempts = 9;
    private int random = 0;
    private int attempts = 0;
    private Item ore;
    private Item rareMaterial;
    private Item pickaxeUsed;
    private int timeToMine = 180;
    private int originalTimeToMine = 180;
    private int chanceToMine = 700; // 700/1000 = 70% chance to successfully get an ore
    private int originalChanceToMine = 700;
    private int chanceOfRareMaterial = 30; // 30/1000 = 3% chance
    private int originalChanceOfRareMaterial;
    private int experience;
    private int originalExperience;

    private static Map<String, BufferedImage> textureMap = Map.ofEntries(
            entry("Azurite Rock", Assets.azuriteRock),
            entry("Copper Rock", Assets.copperRock),
            entry("Iron Rock", Assets.ironRock)
    );
    private static Map<Integer, Double> chanceToMineMap = Map.ofEntries(
            entry(Item.simplePickaxe.getId(), 1.0),
            entry(Item.copperPickaxe.getId(), 1.05),
            entry(Item.ironPickaxe.getId(), 1.1)
    );
    private static Map<Integer, Double> timeToMineMap = Map.ofEntries(
            entry(Item.simplePickaxe.getId(), 1.0),
            entry(Item.copperPickaxe.getId(), 0.95),
            entry(Item.ironPickaxe.getId(), 0.9)
    );

    public Rock(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        isNpc = true;
        attackable = false;

        if (name.equalsIgnoreCase("Azurite Rock")) {
            ore = Item.azuriteOre;
            rareMaterial = Item.lapisLazuli;
            experience = 10;
            timeToMine = 150;
            chanceToMine = 750; // 75%
            chanceOfRareMaterial = 100; // 10% Chance
        } else if (name.equalsIgnoreCase("Copper Rock")) {
            ore = Item.copperOre;
            rareMaterial = Item.malachite;
            experience = 15;
            timeToMine = 180;
            chanceToMine = 700; // 70%
            chanceOfRareMaterial = 80; // 8% chance
        } else if (name.equalsIgnoreCase("Iron Rock")) {
            ore = Item.ironOre;
            experience = 20;
            timeToMine = 240;
            chanceToMine = 650; // 65%
        } else {
            throw new IllegalArgumentException("Rock name not found: " + name);
        }

        // Store the original values
        originalChanceOfRareMaterial = chanceOfRareMaterial;
        originalChanceToMine = chanceToMine;
        originalExperience = experience;
        originalTimeToMine = timeToMine;
    }

    @Override
    public void tick() {
        if (isMining) {
            if (Handler.get().invIsFull(ore)) {
                miningTimer = 0;
                speakingTurn = -1;
                interact();
                setMining(false);
            }
            if (Player.isMoving || Handler.get().getMouseManager().isLeftPressed() &&
                    !Handler.get().getPlayer().hasLeftClickedUI(Handler.get().getMouse())) {
                miningTimer = 0;
                speakingTurn = 0;
                setMining(false);
                return;
            }
            if (random != 0) {
                if (attempts == random) {
                    attempts = 0;
                    setMining(false);
                    this.active = false;
                    this.die();
                }
            }

            miningTimer++;

            if (miningTimer >= timeToMine) {
                int roll = Handler.get().getRandomNumber(1, 1000);
                if (roll < chanceToMine) {
                    Handler.get().giveItem(ore, 1);
                    Handler.get().sendMsg("You successfully mined some ore!");
                    Handler.get().getSkillsUI().getSkill(SkillsList.MINING).addExperience(experience);
                    attempts++;
                    if (rareMaterial != null && roll < chanceOfRareMaterial) {
                        Handler.get().giveItem(rareMaterial, 1);
                        Handler.get().sendMsg("You found a " + rareMaterial.getName() + "!");
                    }
                } else {
                    Handler.get().sendMsg("You missed the swing...");
                    attempts++;
                }
                speakingTurn = 0;
                miningTimer = 0;

                if (attempts == minAttempts - 1) {
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
        g.drawImage(textureMap.get(name), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {
        if (this.speakingTurn == -1) {
            speakingTurn++;
            return;
        }
        if (this.speakingTurn == 0) {
            if (Handler.get().playerHasSkillLevel(SkillsList.MINING, ore)) {
                if (Handler.get().playerHasItemType(ItemType.PICKAXE)) {

                    List<Item> pickaxes = new ArrayList<>();
                    for (ItemSlot is : Handler.get().getInventory().getItemSlots()) {
                        if (is.getItemStack() != null) {
                            if (is.getItemStack().getItem().isType(ItemType.PICKAXE)) {
                                pickaxes.add(is.getItemStack().getItem());
                            }
                        }
                    }

                    // Get the best pickaxe we have in our inventory
                    pickaxeUsed = pickaxes.stream().max((o1, o2) -> {
                        Integer i1 = o1.getStrength();
                        Integer i2 = o2.getStrength();
                        return i1.compareTo(i2);
                    }).get();

                    // Update chances and time to mine based on pickaxe
                    chanceToMine *= chanceToMineMap.get(pickaxeUsed.getId());
                    timeToMine *= timeToMineMap.get(pickaxeUsed.getId());

                    Handler.get().sendMsg("Mining...");
                    speakingTurn = 1;
                    isMining = true;
                } else {
                    Handler.get().sendMsg("You need a pickaxe to mine this rock.");
                }
            } else {
                Handler.get().sendMsg("You need a mining level of " + Handler.get().getSkillResource(SkillsList.MINING, ore).getLevelRequirement() + " to mine this rock.");
            }
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        g.drawImage(Assets.miningIcon, (int) (x + width / 2 - 16 - Handler.get().getGameCamera().getxOffset()), (int) (y - 36 - Handler.get().getGameCamera().getyOffset()), 32, 32, null);
        if (isMining) {
            StringBuilder pending = new StringBuilder();
            int dots = (int) Math.ceil(miningTimer / 30d);
            for (int i = 0; i < dots; i++) {
                pending.append(".");
            }

            Text.drawString(g, pending.toString(), (int) (Handler.get().getPlayer().getX() + 16 - Handler.get().getGameCamera().getxOffset()),
                    (int) (Handler.get().getPlayer().getY() - 16 - Handler.get().getGameCamera().getyOffset()), true, Color.YELLOW, Assets.font24);
        }

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Rock(xSpawn, ySpawn, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {

    }

    public boolean isMining() {
        return isMining;
    }

    public void setMining(boolean mining) {
        // If we stop mining, reset all changes so they can be reapplied next time we mine
        if (!mining) {
            timeToMine = originalTimeToMine;
            chanceToMine = originalChanceToMine;
            chanceOfRareMaterial = originalChanceOfRareMaterial;
            experience = originalExperience;
            pickaxeUsed = null;
        }
        isMining = mining;
    }
}
