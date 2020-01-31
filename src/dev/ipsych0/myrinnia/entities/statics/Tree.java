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

public class Tree extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -524381157898161854L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private boolean isWoodcutting;
    private int woodcuttingTimer = 0;
    private int minAttempts = 4, maxAttempts = 9;
    private int random = 0;
    private int attempts = 0;
    private Item logs;
    private Item rareMaterial;
    private Item axeUsed;
    private int timeToCut = 180;
    private int originalTimeToCut = 180;
    private int chanceToCut = 700; // 700/1000 = 70% chance to successfully get an ore
    private int originalChanceToCut = 700;
    private int chanceOfRareMaterial = 30; // 30/1000 = 3% chance
    private int originalChanceOfRareMaterial = 30;
    private int experience;
    private int originalExperience;
    private static Map<String, BufferedImage> textureMap = Map.ofEntries(
            entry("Weak Palm Tree", Assets.weakPalmTree),
            entry("Elm Tree", Assets.elmTree)
    );
    private static Map<Item, Double> chanceToCutMap = Map.ofEntries(
            entry(Item.simpleAxe, 1.0),
            entry(Item.copperAxe, 1.05),
            entry(Item.ironAxe, 1.1)
    );
    private static Map<Item, Double> timeToCutMap = Map.ofEntries(
            entry(Item.simpleAxe, 1.0),
            entry(Item.copperAxe, 0.95),
            entry(Item.ironAxe, 0.9)
    );

    public Tree(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        isNpc = true;
        attackable = false;

        if (name.equalsIgnoreCase("Weak Palm Tree")) {
            logs = Item.lightWood;
            rareMaterial = null; // TODO: ADD COCONUT
            experience = 10;
            timeToCut = 150;
            chanceToCut = 750; // 75%
            chanceOfRareMaterial = 100; // 10% Chance
            bounds.x = 32;
            bounds.y = 104;
            bounds.width = 32;
            bounds.height = 16;
        } else if (name.equalsIgnoreCase("Elm Tree")) {
            logs = Item.lightWood;
            rareMaterial = null; // TODO: ADD MAYBE SEED/BERRY ITEM
            experience = 10;
            timeToCut = 150;
            chanceToCut = 750; // 75%
            chanceOfRareMaterial = 100; // 10% Chance
            bounds.x = 17;
            bounds.y = 83;
            bounds.width = 29;
            bounds.height = 13;
        } else {
            throw new IllegalArgumentException("Tree name not found: " + name);
        }

        originalChanceOfRareMaterial = chanceOfRareMaterial;
        originalChanceToCut = chanceToCut;
        originalExperience = experience;
        originalTimeToCut = timeToCut;
    }

    @Override
    public void tick() {
        if (isWoodcutting) {
            if (Handler.get().invIsFull(logs)) {
                woodcuttingTimer = 0;
                speakingTurn = -1;
                interact();
                setWoodcutting(false);
            }
            if (Player.isMoving || Handler.get().getMouseManager().isLeftPressed() &&
                    !Handler.get().getPlayer().hasLeftClickedUI(Handler.get().getMouse())) {
                woodcuttingTimer = 0;
                speakingTurn = 0;
                setWoodcutting(false);
                return;
            }
            if (random != 0) {
                if (attempts == random) {
                    attempts = 0;
                    setWoodcutting(false);
                    this.active = false;
                    this.die();
                }
            }

            woodcuttingTimer++;

            if (woodcuttingTimer >= timeToCut) {
                int roll = Handler.get().getRandomNumber(1, 1000);
                if (roll < chanceToCut) {
                    Handler.get().giveItem(logs, 1);
                    Handler.get().sendMsg("You successfully chopped some logs.");
                    Handler.get().getSkillsUI().getSkill(SkillsList.WOODCUTTING).addExperience(experience);
                    attempts++;
                    if (rareMaterial != null && roll < chanceOfRareMaterial) {
                        Handler.get().giveItem(rareMaterial, 1);
                        Handler.get().sendMsg("You found a " + rareMaterial.getName() + "!");
                    }
                } else {
                    Handler.get().sendMsg("Your hatchet bounced off the tree...");
                    attempts++;
                }
                speakingTurn = 0;
                woodcuttingTimer = 0;

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
            if (Handler.get().playerHasSkillLevel(SkillsList.WOODCUTTING, logs)) {
                if (Handler.get().playerHasItemType(ItemType.AXE)) {

                    List<Item> axes = new ArrayList<>();
                    for (ItemSlot is : Handler.get().getInventory().getItemSlots()) {
                        if (is.getItemStack() != null) {
                            if (is.getItemStack().getItem().isType(ItemType.AXE)) {
                                axes.add(is.getItemStack().getItem());
                            }
                        }
                    }

                    // Get the best pickaxe we have in our inventory
                    axeUsed = axes.stream().max((o1, o2) -> {
                        Integer i1 = o1.getStrength();
                        Integer i2 = o2.getStrength();
                        return i1.compareTo(i2);
                    }).get();

                    // Update chances and time to mine based on pickaxe
                    chanceToCut *= chanceToCutMap.get(axeUsed);
                    timeToCut *= timeToCutMap.get(axeUsed);

                    Handler.get().sendMsg("Chop chop...");
                    speakingTurn = 1;
                    isWoodcutting = true;
                } else {
                    Handler.get().sendMsg("You need an axe to chop this tree.");
                }
            } else {
                Handler.get().sendMsg("You need a woodcutting level of " + Handler.get().getSkillResource(SkillsList.WOODCUTTING, logs).getLevelRequirement() + " to chop this tree.");
            }
        }
    }

    @Override
    public void postRender(Graphics2D g) {
        g.drawImage(Assets.woodcuttingIcon, (int) (x + width / 2 - 16 - Handler.get().getGameCamera().getxOffset()), (int) (y - 36 - Handler.get().getGameCamera().getyOffset()), 32, 32, null);
        if (isWoodcutting) {
            StringBuilder pending = new StringBuilder();
            int dots = (int) Math.ceil(woodcuttingTimer / 30d);
            for (int i = 0; i < dots; i++) {
                pending.append(".");
            }

            Text.drawString(g, pending.toString(), (int) (Handler.get().getPlayer().getX() + 16 - Handler.get().getGameCamera().getxOffset()),
                    (int) (Handler.get().getPlayer().getY() - 16 - Handler.get().getGameCamera().getyOffset()), true, Color.YELLOW, Assets.font24);
        }

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Tree(xSpawn, ySpawn, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {

    }

    public boolean isWoodcutting() {
        return isWoodcutting;
    }

    public void setWoodcutting(boolean woodcutting) {
        // If we stop cutting, reset all changes so they can be reapplied next time we cut
        if (!woodcutting) {
            timeToCut = originalTimeToCut;
            chanceToCut = originalChanceToCut;
            chanceOfRareMaterial = originalChanceOfRareMaterial;
            experience = originalExperience;
            axeUsed = null;
        }
        isWoodcutting = woodcutting;
    }
}
