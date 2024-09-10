package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestState;

import java.awt.*;

public class ShamrockRockslide extends StaticEntity {

    private Quest quest = Handler.get().getQuest(QuestList.WeDelvedTooDeep);
    private Animation explosion = new Animation(250, Assets.eruption1, true);
    private boolean westDynamitePlaced, eastDynamitePlaced, northDynamitePlaced;
    private Creature.Direction locationToRockslide;
    private int dynamitePlaced = 0;
    public static boolean hasDetonated;

    public ShamrockRockslide(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        attackable = false;
        isNpc = true;
        quest.addNewCheck("dynamitePlaced", dynamitePlaced);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.rockSlide,
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    public void postRender(Graphics2D g) {
        if (dynamitePlaced >= 3 && hasDetonated) {
            explosion.tick();
            g.drawImage(explosion.getCurrentFrame(), (int) (x - 32 - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - 32 - Handler.get().getGameCamera().getyOffset()), width + 64, height + 64, null);

            if (explosion.isTickDone()) {
                active = false;
                die();
                Handler.get().getWorld().getEntityManager().addRuntimeEntity(new ShamrockSinkhole(1248, 1248, 5 * 32, 3 * 32));
                remove("Miner Aaron");
                remove("Miner Robert");
                remove("Miner Albert");
            }
        }
    }

    private void remove(String name) {
        for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
            if (e.getName() != null && e.getName().equalsIgnoreCase(name)) {
                e.setActive(false);
                break;
            }
        }
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "questStarted":
                if (quest.getState() == QuestState.IN_PROGRESS && !quest.getQuestSteps().get(0).isFinished()) {
                    return true;
                }
                break;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
        return false;
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 7:
                // If collection dynamite step
                if (quest.getQuestSteps().get(0).isFinished() && !quest.getQuestSteps().get(1).isFinished()) {
                    if (Handler.get().playerHasItem(Item.dynamite, 1)) {
                        if (Handler.get().getPlayer().getX() <= this.x) {
                            locationToRockslide = Creature.Direction.LEFT;
                        } else if (Handler.get().getPlayer().getX() >= (this.x + this.width)) {
                            locationToRockslide = Creature.Direction.RIGHT;
                        } else if (Handler.get().getPlayer().getY() <= this.y) {
                            locationToRockslide = Creature.Direction.UP;
                        }

                        if (locationToRockslide == Creature.Direction.LEFT && !westDynamitePlaced || locationToRockslide == Creature.Direction.RIGHT && !eastDynamitePlaced || locationToRockslide == Creature.Direction.UP && !northDynamitePlaced) {
                            speakingTurn = 10;
                            speakingCheckpoint = 10;
                            interact();
                            break;
                        }
                    }
                }

                //
                if (!quest.getQuestSteps().get(0).isFinished()) {
                    speakingCheckpoint = 7;
                    quest.nextStep();
                }
                break;
            case 10:
                // If collection dynamite step
                if (quest.getQuestSteps().get(0).isFinished() && !quest.getQuestSteps().get(1).isFinished()) {
                    if (Handler.get().playerHasItem(Item.dynamite, 1)) {
                        if (Handler.get().getPlayer().getX() <= this.x) {
                            locationToRockslide = Creature.Direction.LEFT;
                        } else if (Handler.get().getPlayer().getX() >= (this.x + this.width)) {
                            locationToRockslide = Creature.Direction.RIGHT;
                        } else if (Handler.get().getPlayer().getY() <= this.y) {
                            locationToRockslide = Creature.Direction.UP;
                        }
                    }
                }
                break;
            case 11:
                if (dynamitePlaced == 3) {
                    speakingTurn = -1;
                    Handler.get().sendMsg("Find and use the detonator to make the rock slide vanish.");
                    break;
                }
                if (Handler.get().playerHasItem(Item.dynamite, 1)) {
                    if (locationToRockslide == Creature.Direction.LEFT) {
                        if (!westDynamitePlaced) {
                            westDynamitePlaced = true;
                            Handler.get().removeItem(Item.dynamite, 1);
                            dynamitePlaced++;
                            quest.addNewCheck("dynamitePlaced", dynamitePlaced);
                            Handler.get().sendMsg("You placed a dynamite stick.");
                            if (dynamitePlaced == 3) {
                                quest.nextStep();
                                Handler.get().sendMsg("Find and use the detonator to make the rock slide vanish.");
                            }
                        } else {
                            Handler.get().sendMsg("You have already placed some dynamite west of the rock slide.");
                        }
                    } else if (locationToRockslide == Creature.Direction.RIGHT) {
                        if (!eastDynamitePlaced) {
                            eastDynamitePlaced = true;
                            Handler.get().removeItem(Item.dynamite, 1);
                            dynamitePlaced++;
                            quest.addNewCheck("dynamitePlaced", dynamitePlaced);
                            Handler.get().sendMsg("You placed a dynamite stick.");
                            if (dynamitePlaced == 3) {
                                quest.nextStep();
                                Handler.get().sendMsg("Find and use the detonator to make the rock slide vanish.");
                            }
                        } else {
                            Handler.get().sendMsg("You have already placed some dynamite east of the rock slide.");
                        }
                    } else if (locationToRockslide == Creature.Direction.UP) {
                        if (!northDynamitePlaced) {
                            northDynamitePlaced = true;
                            Handler.get().removeItem(Item.dynamite, 1);
                            dynamitePlaced++;
                            quest.addNewCheck("dynamitePlaced", dynamitePlaced);
                            Handler.get().sendMsg("You placed a dynamite stick.");
                            if (dynamitePlaced == 3) {
                                quest.nextStep();
                                Handler.get().sendMsg("Find and use the detonator to make the rock slide vanish.");
                            }
                        } else {
                            Handler.get().sendMsg("You have already placed some dynamite north of the rock slide.");
                        }
                    }
                }
                speakingTurn = -1;
                break;
        }
    }
}
