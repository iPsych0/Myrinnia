package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.equipment.EquipmentSlot;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.utils.Colors;
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
    private Rectangle progressBar, totalBar;

    private static Map<String, BufferedImage> textureMap = Map.ofEntries(
            entry("Azurite Rock", Assets.azuriteRock),
            entry("Copper Rock", Assets.copperRock),
            entry("Iron Rock", Assets.ironRock),
            entry("Clay Rock", Assets.clayRock),
            entry("Tungsten Rock", Assets.tungstenRock),
            entry("Coal Rock", Assets.coalRock),
            entry("Silver Rock", Assets.silverRock),
            entry("Platinum Rock", Assets.platinumRock),
            entry("Gold Rock", Assets.goldRock),
            entry("Titanium Rock", Assets.titaniumRock),
            entry("Palladium Rock", Assets.palladiumRock),
            entry("Obsidian Rock", Assets.obsidianRock),
            entry("Cobalt Rock", Assets.cobaltRock)
    );
    private static Map<Integer, Double> chanceToMineMap = Map.ofEntries(
            entry(Item.simplePickaxe.getId(), 1.00),
            entry(Item.copperPickaxe.getId(), 1.05),
            entry(Item.ironPickaxe.getId(), 1.10),
            entry(Item.steelPickaxe.getId(), 1.15),
            entry(Item.platinumPickaxe.getId(), 1.20),
            entry(Item.titaniumPickaxe.getId(), 1.25),
            entry(Item.obsidianPickaxe.getId(), 1.30),
            entry(Item.primordialPickaxe.getId(), 1.35)
    );
    private static Map<Integer, Double> timeToMineMap = Map.ofEntries(
            entry(Item.simplePickaxe.getId(), 1.00),
            entry(Item.copperPickaxe.getId(), 0.95),
            entry(Item.ironPickaxe.getId(), 0.90),
            entry(Item.steelPickaxe.getId(), 0.85),
            entry(Item.platinumPickaxe.getId(), 0.80),
            entry(Item.titaniumPickaxe.getId(), 0.75),
            entry(Item.obsidianPickaxe.getId(), 0.70),
            entry(Item.primordialPickaxe.getId(), 0.65)
    );

    public Rock(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        isNpc = true;
        attackable = false;

        if (name.equalsIgnoreCase("Azurite Rock")) {
            ore = Item.azuriteOre;
            rareMaterial = Item.lapisLazuli;
            experience = 10;
            timeToMine = 120;
            chanceToMine = 850; // 85%
            chanceOfRareMaterial = 100; // 10% Chance
        } else if (name.equalsIgnoreCase("Copper Rock")) {
            ore = Item.copperOre;
            rareMaterial = Item.malachite;
            experience = 15;
            timeToMine = 150;
            chanceToMine = 825; // 82,5%
            chanceOfRareMaterial = 80; // 8% chance
        } else if (name.equalsIgnoreCase("Iron Rock")) {
            ore = Item.ironOre;
            experience = 20;
            timeToMine = 180;
            chanceToMine = 800; // 80%
            chanceOfRareMaterial = 80; // 8% chance
        } else if (name.equalsIgnoreCase("Clay Rock")) {
            ore = Item.clay;
            experience = 15;
            timeToMine = 150;
            chanceToMine = 775; // 77,5%
            chanceOfRareMaterial = 50; // 5% chance
        } else if (name.equalsIgnoreCase("Tungsten Rock")) {
            ore = Item.tungstenOre;
            rareMaterial = Item.topaz;
            experience = 25;
            timeToMine = 210;
            chanceToMine = 750; // 75%
            chanceOfRareMaterial = 75; // 7,5% chance
        } else if (name.equalsIgnoreCase("Coal Rock")) {
            ore = Item.coalOre;
            experience = 35;
            timeToMine = 240;
            chanceToMine = 725; // 72,5%
            chanceOfRareMaterial = 65; // 6,5% chance
        } else if (name.equalsIgnoreCase("Silver Rock")) {
            ore = Item.silverOre;
            rareMaterial = Item.pearl;
            experience = 40;
            timeToMine = 270;
            chanceToMine = 700; // 70%
            chanceOfRareMaterial = 60; // 6% chance
        } else if (name.equalsIgnoreCase("Platinum Rock")) {
            ore = Item.platinumOre;
            experience = 45;
            timeToMine = 300;
            chanceToMine = 675; // 67,5%
            chanceOfRareMaterial = 55; // 5,5% chance
        } else if (name.equalsIgnoreCase("Gold Rock")) {
            ore = Item.goldOre;
            rareMaterial = Item.amethyst;
            experience = 55;
            timeToMine = 330;
            chanceToMine = 650; // 65%
            chanceOfRareMaterial = 50; // 5% chance
        } else if (name.equalsIgnoreCase("Titanium Rock")) {
            ore = Item.titaniumOre;
            experience = 70;
            timeToMine = 360;
            chanceToMine = 625; // 62,5%
            chanceOfRareMaterial = 45; // 4,5% chance
        } else if (name.equalsIgnoreCase("Palladium Rock")) {
            ore = Item.palladiumOre;
            rareMaterial = Item.diamond;
            experience = 90;
            timeToMine = 390;
            chanceToMine = 600; // 60%
            chanceOfRareMaterial = 40; // 4% chance
        } else if (name.equalsIgnoreCase("Obsidian Rock")) {
            ore = Item.obsidianShard;
            experience = 115;
            timeToMine = 420;
            chanceToMine = 575; // 57,5%
            chanceOfRareMaterial = 35; // 3,5% chance
        } else if (name.equalsIgnoreCase("Cobalt Rock")) {
            ore = Item.cobaltOre;
            rareMaterial = Item.onyx;
            experience = 140;
            timeToMine = 450;
            chanceToMine = 550; // 55%
            chanceOfRareMaterial = 30; // 3% chance
        } else {
            throw new IllegalArgumentException("Rock name not found: " + name);
        }

        // Store the original values
        originalChanceOfRareMaterial = chanceOfRareMaterial;
        originalChanceToMine = chanceToMine;
        originalExperience = experience;
        originalTimeToMine = timeToMine;

        totalBar = new Rectangle((int) (x + (width / 2d)) - 32, (int) (y - 16), 64, 16);
        progressBar = new Rectangle((int) (x + (width / 2d)) - 32, (int) (y - 16), 0, 16);
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

                    for (EquipmentSlot is : Handler.get().getEquipment().getEquipmentSlots()) {
                        if (is.getEquipmentStack() != null) {
                            if (is.getEquipmentStack().getItem().isType(ItemType.PICKAXE)) {
                                pickaxes.add(is.getEquipmentStack().getItem());
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
                    chanceToMine *= chanceToMineMap.getOrDefault(pickaxeUsed.getId(), 1.0);
                    timeToMine *= timeToMineMap.getOrDefault(pickaxeUsed.getId(), 1.0);

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
            drawProgressBar(g);
        }

    }

    public void drawProgressBar(Graphics2D g) {
        double percentDone = (double) miningTimer / (double) timeToMine;

        progressBar.setSize((int) (totalBar.width * percentDone), 16);
        g.drawImage(Assets.uiWindow, (int) (totalBar.x - Handler.get().getGameCamera().getxOffset()), (int) (totalBar.y - Handler.get().getGameCamera().getyOffset()), totalBar.width, totalBar.height, null);

        g.setColor(Colors.progressBarColor);
        g.fillRoundRect((int) (progressBar.x - Handler.get().getGameCamera().getxOffset()), (int) (progressBar.y - Handler.get().getGameCamera().getyOffset()), progressBar.width, progressBar.height, 4, 4);

        g.setColor(Colors.progressBarOutlineColor);
        g.drawRoundRect((int) (progressBar.x - Handler.get().getGameCamera().getxOffset()), (int) (progressBar.y - Handler.get().getGameCamera().getyOffset()), progressBar.width, progressBar.height, 4, 4);
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
