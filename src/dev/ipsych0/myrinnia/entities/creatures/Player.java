package dev.ipsych0.myrinnia.entities.creatures;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.ui.abilityhud.AbilitySlot;
import dev.ipsych0.myrinnia.abilities.ui.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.character.CharacterUI;
import dev.ipsych0.myrinnia.chatwindow.ChatWindow;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.Immunity;
import dev.ipsych0.myrinnia.entities.npcs.AbilityTrainer;
import dev.ipsych0.myrinnia.entities.npcs.Banker;
import dev.ipsych0.myrinnia.entities.npcs.ShopKeeper;
import dev.ipsych0.myrinnia.entities.statics.BountyBoard;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.equipment.EquipmentWindow;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.hpoverlay.HPOverlay;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.items.ui.InventoryWindow;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestHelpUI;
import dev.ipsych0.myrinnia.quests.QuestUI;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.skills.Skill;
import dev.ipsych0.myrinnia.skills.ui.BountyBoardUI;
import dev.ipsych0.myrinnia.skills.ui.BountyContractUI;
import dev.ipsych0.myrinnia.skills.ui.SkillsOverviewUI;
import dev.ipsych0.myrinnia.skills.ui.SkillsUI;
import dev.ipsych0.myrinnia.states.State;
import dev.ipsych0.myrinnia.states.UITransitionState;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.worlds.World;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Player extends Creature {


    /**
     *
     */
    private static final long serialVersionUID = -7176335479649325606L;

    public static boolean hasInteracted = false;
    public static boolean debugButtonPressed = false;

    // Attacking Animations
    private Animation attDown, attUp, attLeft, attRight;
    private Animation meleeAnimation;

    // Melee timer
    private long lastAttackTimer, attackCooldown = (long) (600 / getAttackSpeed()), attackTimer = attackCooldown;

    // Magic timer
    private long lastMagicTimer, magicCooldown = (long) (600 / getAttackSpeed()), magicTimer = magicCooldown;

    // Ranged timer
    private long lastRangedTimer, rangedCooldown = (long) (600 / getAttackSpeed()), rangedTimer = rangedCooldown;

    // Regeneration timer
    private long lastRegenTimer, regenCooldown = 1000, regenTimer = regenCooldown;

    private double levelExponent = 1.1;
    public static boolean isLevelUp;
    public static boolean isXpGained;
    public static Skill leveledSkill;
    public static Quest questCompleted;
    public static boolean isQuestCompleted;
    public static int xpGained;
    private int levelUpTimer, xpGainedTimer, questCompleteTimer;
    public static boolean expEffectPlayed;

    private boolean movementAllowed = true;
    public static boolean isMoving, hasMoved;

    public static boolean mouseMoved;
    private double xSpawn, ySpawn;

    // Entities we can interact with, with different functions
    private Entity closestEntity;
    private ShopKeeper shopKeeper;
    private AbilityTrainer abilityTrainer;
    private Banker bankEntity;
    private BountyBoard bountyBoard;

    private Zone zone = Zone.PortAzure;
    private Rectangle itemPickupRadius;

    private int abilityPoints;
    private double rotation;
    private double xPos, yPos;

    public Player(double x, double y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, null, 1, null, null, null, null, Direction.DOWN);

        xSpawn = x;
        ySpawn = y;
        attackable = true;

        // Player combat/movement settings:

        maxHealth = (int) (DEFAULT_HEALTH * 2 + Math.round(vitality * 1.5));
        health = maxHealth;
        speed = DEFAULT_SPEED + 0.5;

        // Set collision boundaries on sprite
        bounds.x = 10;
        bounds.y = 16;
        bounds.width = 14;
        bounds.height = 16;

        // Animations
        aDown = new Animation(250, Assets.player_down);
        aUp = new Animation(250, Assets.player_up);
        aLeft = new Animation(250, Assets.player_left);
        aRight = new Animation(250, Assets.player_right);

        attDown = new Animation(333, Assets.player_melee_down);
        attUp = new Animation(333, Assets.player_melee_up);
        attLeft = new Animation(333, Assets.player_melee_left);
        attRight = new Animation(333, Assets.player_melee_right);

        meleeAnimation = new Animation(48, Assets.regularMelee, true, false);

        aDefault = aDown;

        projectiles = new ArrayList<>();

        respawnTimer = 1;

        itemPickupRadius = new Rectangle((int) (x + bounds.x - 24), (int) (y + bounds.y - 24), (bounds.width + 40), (bounds.height + 36));

    }

    @Override
    public void tick() {

        //Movement
        if (movementAllowed) {
            getInput();
            move();
            aDefault.tick();
            aDown.tick();
            aUp.tick();
            aLeft.tick();
            aRight.tick();
            attDown.tick();
            attUp.tick();
            attLeft.tick();
            attRight.tick();
        }

        if (inCombat) {
            combatTimer++;
        }

        if (combatTimer >= 300) {
            inCombat = false;
            combatTimer = 0;
        }

        // Attacks
        if (!inCombat) {
            regenHealth();
        }

        // Debug button for in-game testing
        if (Handler.get().getKeyManager().pause && debugButtonPressed) {

//			maxHealth = (!Handler.debugMode) ? 10000 : (int) (DEFAULT_HEALTH + Math.round(getVitality() * 1.5));
//			health = (!Handler.debugMode) ? 10000 : (int) (DEFAULT_HEALTH + Math.round(getVitality() * 1.5));
//			
//			Handler.get().sendMsg("X coords: " + Float.toString(getX()) + " Y coords: " + Float.toString(getY()));
//			System.out.println("Current X and Y coordinates are X: " + Handler.get().getWorld().getEntityManager().getPlayer().getX() +" and Y: " + 
//					Handler.get().getWorld().getEntityManager().getPlayer().getY());
//			
//			speed = (speed == 7.0f) ? 2.5f : 7.0f; 
//			strength = 250;
//			Handler.debugMode = (Handler.debugMode) ? false : true;

            State.setState(new UITransitionState(Handler.get().getGame().pauseState));

//			System.out.println("Attack level = " + getAttackLevel());
//			System.out.println("Attack XP = " + getAttackExperience());
//			System.out.println("Crafting XP = " + getCraftingExperience());
//			System.out.println("Crafting level = " + getCraftingLevel());


//			for(int i = 0; i < Handler.get().getInventory().getItemSlots().size(); i++) {
//				Handler.get().getInventory().getItemSlots().get(i).addItem(Item.beginnersSword, 1);
//			}
            debugButtonPressed = false;

        }

        // If space button is pressed
        if (Handler.get().getKeyManager().talk) {
            if (!hasInteracted) {
                if (playerIsNearNpc()) {
                    // And we're close to an NPC interact with it
                    closestEntity = getClosestEntity();
                    if (closestEntity.getChatDialogue() == null) {
                        closestEntity.interact();
                        hasInteracted = true;
                        Handler.get().playEffect("ui/ui_button_click.wav");

                        // If the closest Entity is a shops, open the shops
                        if (closestEntity instanceof ShopKeeper) {
                            shopKeeper = (ShopKeeper) getClosestEntity();
                            shopKeeper.getShopWindow().setLastShopWindow();
                        } else if (closestEntity instanceof Banker) {
                            bankEntity = (Banker) getClosestEntity();
                        } else if (closestEntity instanceof AbilityTrainer) {
                            abilityTrainer = (AbilityTrainer) getClosestEntity();
                            abilityTrainer.getAbilityShopWindow().setLastOpenedWindow();
                        } else if (closestEntity instanceof BountyBoard) {
                            bountyBoard = (BountyBoard) getClosestEntity();
                        }
                    } else {
                        if (closestEntity.getChatDialogue().getMenuOptions().length == 1) {
                            closestEntity.interact();
                            hasInteracted = true;
                            Handler.get().playEffect("ui/ui_button_click.wav");
                        }
                    }
                }
            }
        }

        Rectangle mouse = Handler.get().getMouse();

        // If we're interacting with the closest Entity
        if (closestEntity != null) {
            // And it has a chatdialogue
            if (closestEntity.getChatDialogue() != null) {
                // If the Entity has an option-menu
                if (closestEntity.getChatDialogue().getChosenOption() != null) {
                    // And we haven't interacted
                    if (!hasInteracted) {
                        // And we're still close to it
                        if (playerIsNearNpc()) {
                            // If we click the menu option, interact with it.
                            closestEntity.interact();
                            hasInteracted = true;
                        }
                    }
                    // If the Entity only has a continue button (text only) and it's pressed
                } else if (closestEntity.getChatDialogue().getChatOptions().size() == 1 && closestEntity.getChatDialogue().getChatOptions().get(0).isPressed()) {
                    if (!hasInteracted) {
                        if (playerIsNearNpc()) {
                            // Do the logic and set it to un-pressed and interact
                            closestEntity.interact();
                            Handler.get().playEffect("ui/ui_button_click.wav");
                            hasInteracted = true;
                        }
                    }
                }
            }
        }

        if (closestEntity != null && closestEntity.getChatDialogue() != null) {
            closestEntity.getChatDialogue().tick();
        }

        // If the player moves, close the shops and chat dialogue
        if (closestEntity != null && isMoving && closestEntity.getChatDialogue() != null) {
            Entity.isCloseToNPC = false;
            hasInteracted = false;

            closestEntity.setChatDialogue(null);
            if (closestEntity.getSpeakingCheckpoint() != 0) {
                closestEntity.setSpeakingTurn(closestEntity.getSpeakingCheckpoint());
            } else {
                closestEntity.setSpeakingTurn(-1);
                closestEntity.interact();
            }
            closestEntity = null;
        }

        // If there are projectiles, tick them
        if (projectiles.size() > 0) {
            tickProjectiles();
        }

        // If the mouse is not moved, use the WASD-keys to determine the direction
        if (!mouseMoved) {
            if (xMove < 0)
                lastFaced = Direction.LEFT;
            else if (xMove > 0)
                lastFaced = Direction.RIGHT;
            else if (yMove < 0)
                lastFaced = Direction.UP;
            else if (yMove > 0)
                lastFaced = Direction.DOWN;
            setLastFaced();
        } else {
            // If the mouse IS moved, make the player face the angle of the mouse.
            if (movementAllowed)
                setMouseAngle(x, y, (int) (Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset()),
                        (int) (Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset()));
            setLastFaced();
        }

        // If the player is pressing the attack button
        if (Handler.get().getMouseManager().isLeftPressed() || Handler.get().getMouseManager().isLeftPressed() && Handler.get().getMouseManager().isDragged()) {
            if (movementAllowed) {
                if (Handler.get().getEquipment().getEquipmentSlots().get(EquipSlot.Mainhand.getSlotId()).getEquipmentStack() != null) {
                    if (MouseManager.justClosedUI) {
                        return;
                    }
                    for (AbilitySlot as : Handler.get().getAbilityManager().getAbilityHUD().getSlottedAbilities()) {
                        if (as.getAbility() != null) {
                            if (as.getAbility().isChanneling() || as.getAbility().isSelected())
                                return;
                        }
                    }
                    //Check melee auto attack
                    if (Handler.get().getEquipment().getEquipmentSlots().get(EquipSlot.Mainhand.getSlotId()).getEquipmentStack().getItem().isType(ItemType.MELEE_WEAPON))
                        checkMelee(mouse);
                        // Check magic auto attack
                    else if (Handler.get().getEquipment().getEquipmentSlots().get(EquipSlot.Mainhand.getSlotId()).getEquipmentStack().getItem().isType(ItemType.MAGIC_WEAPON)) {
                        checkMagic(mouse);
                    } else if (Handler.get().getEquipment().getEquipmentSlots().get(EquipSlot.Mainhand.getSlotId()).getEquipmentStack().getItem().isType(ItemType.RANGED_WEAPON)) {
                        checkRanged(mouse);
                    } else {
                        System.err.println("Item: '" + Handler.get().getEquipment().getEquipmentSlots().get(EquipSlot.Mainhand.getSlotId()).getEquipmentStack().getItem().getName() + "' does not have a melee/magic/ranged weapon type assigned to it.");
                    }
                }
            }
        }

        if (isXpGained && !expEffectPlayed) {
            Handler.get().playEffect("ui/exp_gain.wav");
            expEffectPlayed = true;
        }

    }

    /*
     * Ticks the projectiles of the player
     */
    @Override
    protected void tickProjectiles() {
        if (projectiles.size() < 1)
            return;

        Iterator<Projectile> it = projectiles.iterator();
        Collection<Projectile> deleted = new ArrayList<>();
        while (it.hasNext()) {
            Projectile p = it.next();

            p.tick();

            if (!p.isActive()) {
                deleted.add(p);
            }

            for (Entity e : getCurrentMap().getEntityManager().getEntities()) {
                if (e.equals(this)) {
                    continue;
                }
                if (p.getCollisionBounds(0, 0).intersects(e.getCollisionBounds(0, 0)) && p.isActive()) {
                    if (!e.isAttackable()) {
                        p.setActive(false);
                    }
                    if (e.isAttackable()) {
                        if (p.getAbility() != null) {
                            e.damage(p.getDamageType(), this, e, p.getAbility());
                        } else {
                            e.damage(p.getDamageType(), this, e);
                        }

                        if (p.getImpactSound() != null) {
                            Handler.get().playEffect(p.getImpactSound(), 0.1f);
                        }
                        p.setHitCreature((Creature) e);
                        p.setActive(false);
                    }
                }
            }
        }

        projectiles.removeAll(deleted);
    }

    /*
     * Sets the angle of the mouse
     */
    private void setMouseAngle(double playerX, double playerY, int mouseX, int mouseY) {

        double angle = Math.atan2(mouseY - playerY, mouseX - playerX);

        double theta = Math.toDegrees(angle);
        if (theta < 0.0) {
            theta += 360;
        }

        if (theta >= 315 && theta < 360 || theta >= 0 && theta < 45) {
            lastFaced = Direction.RIGHT;
        } else if (theta >= 45 && theta < 135) {
            lastFaced = Direction.DOWN;
        } else if (theta >= 135 && theta < 225) {
            lastFaced = Direction.LEFT;
        } else if (theta >= 225 && theta < 315) {
            lastFaced = Direction.UP;
        }

    }

    @Override
    public void render(Graphics2D g) {
        Rectangle mouse = Handler.get().getMouse();

        Handler.get().getGameCamera().centerOnEntity(this);

        if (movementAllowed) {
            g.drawImage(getCurrentAnimationFrame(mouse), (int) (x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
        } else {
            g.drawImage(getLastFacedImg(), (int) (x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
        }

        // UNCOMMENT THIS BLOCK OF CODE TO SHOW THE PLAYER'S COLLISION RECTANGLE IN-GAME

        if (Handler.debugCollision) {
            g.setColor(Color.RED);
            g.fillRect((int) (x + bounds.x - Handler.get().getGameCamera().getxOffset()),
                    (int) (y + bounds.y - Handler.get().getGameCamera().getyOffset()), bounds.width, bounds.height);
        }

        // Player item pickup radius
//		g.setColor(Color.BLACK);
//		g.drawRect((int)(itemPickupRadius().x - Handler.get().getGameCamera().getxOffset()), (int) (itemPickupRadius().y - Handler.get().getGameCamera().getyOffset()), itemPickupRadius().width, itemPickupRadius().height);

//		UNCOMMENT THIS TO SEE MELEE HITBOX
//		double angle = Math.atan2((Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset() - 16) - y, (Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset() - 16) - x);
//		Rectangle ar = new Rectangle((int)(32 * Math.cos(angle) + (int)this.x), (int)(32 * Math.sin(angle) + (int)this.y), 40, 40);
//		g.setColor(Color.MAGENTA);
//		g.drawRect((int)(ar.x - Handler.get().getGameCamera().getxOffset()), (int)(ar.y - Handler.get().getGameCamera().getyOffset()), ar.width, ar.height);

        if (projectiles.size() > 0) {
            for (Projectile p : projectiles) {
                if (active)
                    p.render(g);
            }
        }

        if (meleeAnimation != null) {
            if (meleeAnimation.isTickDone()) {
                meleeAnimation = null;
            } else {
                meleeAnimation.tick();

                AffineTransform old = g.getTransform();
                g.rotate(Math.toRadians(rotation), (int) (x + xPos + width / 2 - Handler.get().getGameCamera().getxOffset()), (int) (y + yPos + height / 2 - Handler.get().getGameCamera().getyOffset()));
                g.drawImage(meleeAnimation.getCurrentFrame(), (int) (x + xPos - Handler.get().getGameCamera().getxOffset()),
                        (int) (y + yPos - Handler.get().getGameCamera().getyOffset()), (int) (width * 1.25f), (int) (height * 1.25f), null);
                g.setTransform(old);
            }
        }

    }

    public void levelUp() {
        isLevelUp = true;

        this.levelExponent *= LEVEL_EXPONENT;

        // Change base damage and restore to full health
        this.baseDamage = (int) Math.ceil(baseDamage * levelExponent) + 1;
        this.maxHealth = (int) (DEFAULT_HEALTH * 2 + Math.round(vitality * 1.5));

        this.health = maxHealth;
    }

    /*
     * Adds the equipment stats
     * @param: the item's equipment slot
     */
    public void addEquipmentStats(int equipSlot) {
        if (equipSlot == EquipSlot.None.getSlotId()) {
            // If slotnumber = 12 (unequippable) return
            return;
        }

        if (Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack() != null) {

            // Sets the new stats
            attackSpeed += Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getAttackSpeed();
            vitality += Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getVitality();
            strength += Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getStrength();
            dexterity += Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDexterity();
            intelligence += Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getIntelligence();
            defence += Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDefence();
            speed += Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getMovementSpeed();
            attackCooldown = (long) (600 / attackSpeed);
            magicCooldown = (long) (600 / attackSpeed);
            int previousMaxHP = maxHealth;
            maxHealth = (int) (DEFAULT_HEALTH * 2 + Math.round(vitality * 1.5));
            if (health == previousMaxHP) {
                health = maxHealth;
            }
        }
    }

    public Item getMainHandWeapon() {
        ItemStack is = Handler.get().getEquipment().getEquipmentSlots().get(EquipSlot.Mainhand.getSlotId()).getEquipmentStack();
        return is == null ? null : is.getItem();
    }

    public void setWeaponAnimations(int equipSlot) {
        // Change weapon animations based on equipped weapon
        Item equipped = Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem();
        if (equipped.isType(ItemType.MELEE_WEAPON)) {
            attDown.setFrames(Assets.player_melee_down);
            attUp.setFrames(Assets.player_melee_up);
            attLeft.setFrames(Assets.player_melee_left);
            attRight.setFrames(Assets.player_melee_right);
        } else if (equipped.isType(ItemType.MAGIC_WEAPON)) {
            attDown.setFrames(Assets.player_magic_down);
            attUp.setFrames(Assets.player_magic_up);
            attLeft.setFrames(Assets.player_magic_left);
            attRight.setFrames(Assets.player_magic_right);
        } else if (equipped.isType(ItemType.RANGED_WEAPON)) {
            attDown.setFrames(Assets.player_ranged_down);
            attUp.setFrames(Assets.player_ranged_up);
            attLeft.setFrames(Assets.player_ranged_left);
            attRight.setFrames(Assets.player_ranged_right);
        }
    }

    /*
     * Removes the equipment stats
     */
    public void removeEquipmentStats(int equipSlot) {
        if (equipSlot == EquipSlot.None.getSlotId()) {
            return;
        }
        if (Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack() != null) {

            if (getAttackSpeed() - Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getAttackSpeed() < 0) {
                setAttackSpeed(0);
            } else {
                attackSpeed -= Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getAttackSpeed();
            }

            if (getVitality() - Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getVitality() < 0) {
                setVitality(0);
            } else {
                vitality -= Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getVitality();
            }

            if (getStrength() - Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getStrength() < 0) {
                setStrength(0);
            } else {
                strength -= Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getStrength();
            }

            if (getDexterity() - Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDexterity() < 0) {
                setDexterity(0);
            } else {
                dexterity -= Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDexterity();
            }

            if (getIntelligence() - Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getIntelligence() < 0) {
                setIntelligence(0);
            } else {
                intelligence -= Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getIntelligence();
            }

            if (getDefence() - Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDefence() < 0) {
                setDefence(0);
            } else {
                defence -= Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDefence();
            }

            if (speed - Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getMovementSpeed() < 1.0f) {
                speed = 1.0f;
            } else {
                speed -= Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getMovementSpeed();
            }

            attackCooldown = (long) (600 / attackSpeed);
            magicCooldown = (long) (600 / attackSpeed);
            maxHealth = (int) (DEFAULT_HEALTH * 2 + Math.round(vitality * 1.5));
            if (health >= maxHealth) {
                health = maxHealth;
            }
        }
    }

    /*
     * Regenerates health
     */
    private void regenHealth() {
        if (health == maxHealth) {
            return;
        }

        regenTimer += System.currentTimeMillis() - lastRegenTimer;
        lastRegenTimer = System.currentTimeMillis();
        if (regenTimer < regenCooldown)
            return;

        // If current health is higher than your max health value, degenerate health
        if (health > maxHealth) {

            health -= 1;
            regenTimer = 0;
        }

        // If current health is lower than your max health value, regenerate health
        if (health < maxHealth) {

            health += 1;

            regenTimer = 0;
        }
    }

    /**
     * Checks if the mouse is left-clicked within a UI window
     *
     * @param mouse - mouse coordinates
     * @return true if within window, false if not
     */
    public boolean hasLeftClickedUI(Rectangle mouse) {
        if (InventoryWindow.isOpen && Handler.get().getInventory().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (EquipmentWindow.isOpen && Handler.get().getEquipment().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (ChatWindow.chatIsOpen && Handler.get().getChatWindow().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (CraftingUI.isOpen && Handler.get().getCraftingUI().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (ShopWindow.isOpen && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (BankUI.isOpen && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (QuestUI.isOpen && Handler.get().getQuestManager().getQuestUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (QuestHelpUI.isOpen && Handler.get().getQuestManager().getQuestUI().getQuestHelpUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (SkillsUI.isOpen && Handler.get().getSkillsUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (SkillsOverviewUI.isOpen && Handler.get().getSkillsUI().getOverviewUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (CharacterUI.isOpen && Handler.get().getCharacterUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (HPOverlay.isOpen && Handler.get().getHpOverlay().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (Handler.get().getAbilityManager().getAbilityHUD().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (AbilityOverviewUI.isOpen && Handler.get().getAbilityOverviewUI().getClickableArea().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (BountyContractUI.isOpen && Handler.get().getContractUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
            return true;
        if (abilityTrainer != null && AbilityShopWindow.isOpen) {
            if (Handler.get().getMouseManager().isLeftPressed() && abilityTrainer.getAbilityShopWindow().getBounds().contains(mouse)) {
                return true;
            }
        }
        if (closestEntity != null && closestEntity.getChatDialogue() != null) {
            return Handler.get().getMouseManager().isLeftPressed() && closestEntity.getChatDialogue().getBounds().contains(mouse);
        }

        TutorialTip tip = Handler.get().getTutorialTipManager().getCurrentTip();
        if (tip != null && Handler.get().getMouseManager().isLeftPressed() && tip.getPopup().getBounds().contains(mouse)) {
            return true;
        }

        if (bountyBoard != null && BountyBoardUI.isOpen) {
            if (Handler.get().getMouseManager().isLeftPressed() && bountyBoard.getBountyBoardUI().getBounds().contains(mouse)) {
                return true;
            }
        }

        // If the mouse is not clicked in one of the UI windows, return false
        return false;
    }

    /**
     * Checks if the mouse is right-clicked within a UI window
     *
     * @param mouse - mouse coordinates
     * @return true if within window, false if not
     */
    public boolean hasRightClickedUI(Rectangle mouse) {
        if (InventoryWindow.isOpen && Handler.get().getInventory().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (EquipmentWindow.isOpen && Handler.get().getEquipment().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (ChatWindow.chatIsOpen && Handler.get().getChatWindow().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (CraftingUI.isOpen && Handler.get().getCraftingUI().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (ShopWindow.isOpen && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (BankUI.isOpen && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (QuestUI.isOpen && Handler.get().getQuestManager().getQuestUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (QuestHelpUI.isOpen && Handler.get().getQuestManager().getQuestUI().getQuestHelpUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (SkillsUI.isOpen && Handler.get().getSkillsUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (SkillsOverviewUI.isOpen && Handler.get().getSkillsUI().getOverviewUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (CharacterUI.isOpen && Handler.get().getCharacterUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (Handler.get().getHpOverlay().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (Handler.get().getAbilityManager().getAbilityHUD().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (AbilityOverviewUI.isOpen && Handler.get().getAbilityOverviewUI().getClickableArea().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (BountyContractUI.isOpen && Handler.get().getContractUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
            return true;
        if (abilityTrainer != null && AbilityShopWindow.isOpen) {
            if (Handler.get().getMouseManager().isRightPressed() && abilityTrainer.getAbilityShopWindow().getBounds().contains(mouse)) {
                return true;
            }
        }
        if (closestEntity != null && closestEntity.getChatDialogue() != null) {
            return Handler.get().getMouseManager().isRightPressed() && closestEntity.getChatDialogue().getBounds().contains(mouse);
        }
        TutorialTip tip = Handler.get().getTutorialTipManager().getCurrentTip();
        if (tip != null && Handler.get().getMouseManager().isRightPressed() && tip.getPopup().getBounds().contains(mouse)) {
            return true;
        }

        if (bountyBoard != null && BountyBoardUI.isOpen) {
            if (Handler.get().getMouseManager().isRightPressed() && bountyBoard.getBountyBoardUI().getBounds().contains(mouse)) {
                return true;
            }
        }

        // If the mouse is not clicked in one of the UI windows, return false
        return false;
    }

    private void checkRanged(Rectangle mouse) {
        rangedTimer += System.currentTimeMillis() - lastRangedTimer;
        lastRangedTimer = System.currentTimeMillis();
        if (rangedTimer < rangedCooldown)
            return;

        if (hasLeftClickedUI(mouse))
            return;

        // Change attacking animation depending on which weapon type
        setWeaponAnimations(EquipSlot.Mainhand.getSlotId());

        rangedTimer = 0;

        Handler.get().playEffect("abilities/ranged_shot.wav", 0.35f);
        if (Handler.get().getMouseManager().isLeftPressed() || Handler.get().getMouseManager().isDragged()) {
            projectiles.add(new Projectile(x, y,
                    (int) (mouse.getX() + Handler.get().getGameCamera().getxOffset() - 16),
                    (int) (mouse.getY() + Handler.get().getGameCamera().getyOffset() - 16),
                    9.0f, "abilities/ranged_shot_impact.wav", DamageType.DEX, Assets.regularArrow));
        }
    }

    /*
     * Check for magic attacks
     */
    public void checkMagic(Rectangle mouse) {
        // Attack timers
        magicTimer += System.currentTimeMillis() - lastMagicTimer;
        lastMagicTimer = System.currentTimeMillis();
        if (magicTimer < magicCooldown)
            return;

        if (hasLeftClickedUI(mouse))
            return;

        // Change attacking animation depending on which weapon type
        setWeaponAnimations(EquipSlot.Mainhand.getSlotId());

        magicTimer = 0;

        Handler.get().playEffect("abilities/magic_strike.wav");
        if (Handler.get().getMouseManager().isLeftPressed() || Handler.get().getMouseManager().isDragged()) {
            projectiles.add(new Projectile(x, y,
                    (int) (mouse.getX() + Handler.get().getGameCamera().getxOffset() - 16),
                    (int) (mouse.getY() + Handler.get().getGameCamera().getyOffset() - 16),
                    9.0f, "abilities/magic_strike_impact.wav", DamageType.INT, Assets.regularMagic));
        }

    }

    /*
     * Checks melee attacks
     */
    private void checkMelee(Rectangle mouse) {
        // Attack timers
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown)
            return;

        if (hasLeftClickedUI(mouse))
            return;

        // Change attacking animation depending on which weapon type
        setWeaponAnimations(EquipSlot.Mainhand.getSlotId());

        attackTimer = 0;

        meleeAnimation = new Animation(48, Assets.regularMelee, true, false);

        setMeleeSwing(mouse);

        Handler.get().playEffect("abilities/sword_swing.wav", -0.05f);

        if (Handler.get().getMouseManager().isLeftPressed() || Handler.get().getMouseManager().isDragged()) {
            double angle = Math.atan2((mouse.getY() + Handler.get().getGameCamera().getyOffset() - 16) - y, (mouse.getX() + Handler.get().getGameCamera().getxOffset() - 16) - x);
            Rectangle ar = new Rectangle((int) (32 * Math.cos(angle) + (int) this.x), (int) (32 * Math.sin(angle) + (int) this.y), 40, 40);

            for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
                if (e.equals(this))
                    continue;
                if (!e.isAttackable())
                    continue;
                if (e.getCollisionBounds(0, 0).intersects(ar)) {
                    e.damage(DamageType.STR, this, e);
                }
            }
        }
    }

    private void setMeleeSwing(Rectangle mouse) {
        // The angle and speed of the projectile
        double angle = Math.atan2((mouse.getY() + Handler.get().getGameCamera().getyOffset() - 16) - y, (mouse.getX() + Handler.get().getGameCamera().getxOffset() - 16) - x);

        // Set the rotation of the projectile in degrees (0 = RIGHT, 270 = UP, 180 = LEFT, 90 = DOWN)
        rotation = Math.toDegrees(angle);
        if (rotation < 0) {
            rotation += 360d;
        }

        double xOffset = 1.0f * Math.cos(angle);
        double yOffset = 1.0f * Math.sin(angle);


        // xPos change RIGHT
        if (rotation >= 270 || rotation < 90) {
            xPos = 20d * xOffset;
            // xPos change LEFT
        } else if (rotation >= 90 || rotation < 270) {
            xPos = 20d * xOffset;
        }

        // xPos change RIGHT
        if (rotation >= 180 || rotation <= 360) {
            yPos = 20d * yOffset;
            // xPos change LEFT
        } else if (rotation >= 0 || rotation < 180) {
            yPos = 20d * yOffset;
        }
    }

    @Override
    public void die() {
        Handler.get().sendMsg("You died!");

        // If we're dead, respawn
        if (!active) {
            respawn();
        }
    }

    @Override
    public void respawn() {
        // TODO: Go to last saved location!
        Handler.get().goToWorld(Zone.PortAzure, (int) xSpawn, (int) ySpawn);

        // Clear buffs & condis & reset HP
        clearBuffs();
        clearConditions();
        setHealth(maxHealth);
        setDamaged(false);
        setActive(true);
    }

    @Override
    protected void updateDialogue() {

    }

    @Override
    public String getName() {
        return "Player";
    }

    /*
     * Handles movement, based on keyboard input (WASD)
     */
    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (Handler.get().getKeyManager().up) {
            yMove = -speed;
            direction = Direction.UP;
            setMouseAngle(x, y, (int) (Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset()),
                    (int) (Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset()));
        }
        if (Handler.get().getKeyManager().down) {
            yMove = speed;
            direction = Direction.DOWN;
            setMouseAngle(x, y, (int) (Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset()),
                    (int) (Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset()));
        }
        if (Handler.get().getKeyManager().left) {
            xMove = -speed;
            direction = Direction.LEFT;
            setMouseAngle(x, y, (int) (Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset()),
                    (int) (Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset()));
        }
        if (Handler.get().getKeyManager().right) {
            xMove = speed;
            direction = Direction.RIGHT;
            setMouseAngle(x, y, (int) (Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset()),
                    (int) (Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset()));
        }
        if (xMove != 0 || yMove != 0) {
            isMoving = true;
            hasMoved = true;
        }
    }

    /*
     * Post render for things that should be drawn over other Entities
     */
    @Override
    public void postRender(Graphics2D g) {
        if (closestEntity != null && closestEntity.getChatDialogue() != null) {
            closestEntity.getChatDialogue().render(g);
        }

        if (isXpGained) {
            xpGainedTimer++;
            g.drawImage(leveledSkill.getImg(), (int) (x - Handler.get().getGameCamera().getxOffset() - 66),
                    (int) (y - Handler.get().getGameCamera().getyOffset() + 32 - xpGainedTimer), null);
            Text.drawString(g, "+" + xpGained + " XP",
                    (int) (x - Handler.get().getGameCamera().getxOffset() - 32),
                    (int) (y - Handler.get().getGameCamera().getyOffset() + 48 - xpGainedTimer),
                    false, Color.GREEN, Assets.font20);
            if (xpGainedTimer >= 60) {
                xpGainedTimer = 0;
                isXpGained = false;
            }
        }

        if (isLevelUp) {
            levelUpTimer++;
            Text.drawString(g, leveledSkill.toString() + " level up!", (int) (x - Handler.get().getGameCamera().getxOffset() + 12),
                    (int) (y - Handler.get().getGameCamera().getyOffset() + 32 - levelUpTimer),
                    true, Color.YELLOW, Assets.font24);
            if (levelUpTimer >= 60) {
                levelUpTimer = 0;
                isLevelUp = false;
            }
        }

        if (isQuestCompleted) {
            questCompleteTimer++;
            Text.drawString(g, questCompleted.getQuestName() + " completed!", (int) (x - Handler.get().getGameCamera().getxOffset() + 12),
                    (int) (y - Handler.get().getGameCamera().getyOffset() + 32 - questCompleteTimer),
                    true, Color.YELLOW, Assets.font24);
            if (questCompleteTimer >= 60) {
                questCompleteTimer = 0;
                isQuestCompleted = false;
            }
        }

        for (int i = 0; i < getConditions().size(); i++) {
            Rectangle slotPos = new Rectangle(Handler.get().getAbilityManager().getAbilityHUD().getBounds().x + (i * ItemSlot.SLOTSIZE),
                    Handler.get().getHeight() - ItemSlot.SLOTSIZE * 2 - 8,
                    32,
                    32);
            getConditions().get(i).render(g, slotPos.x, slotPos.y);
            if (slotPos.contains(Handler.get().getMouse())) {
                Handler.get().getAbilityManager().getAbilityHUD().getStatusTooltip().render(getConditions().get(i), g);
            }
        }

        int yOffset = 0;
        if (!getConditions().isEmpty()) yOffset = 1;
        // Always draw immunity buffs first on the left
        int immunityCount = 0;
        for (int i = 0; i < getImmunities().size(); i++) {
            immunityCount += ItemSlot.SLOTSIZE;
            Rectangle slotPos = new Rectangle(Handler.get().getAbilityManager().getAbilityHUD().getBounds().x + (i * ItemSlot.SLOTSIZE),
                    Handler.get().getHeight() - ItemSlot.SLOTSIZE * 2 - (ItemSlot.SLOTSIZE * yOffset) - 8,
                    32,
                    32);
            getImmunities().get(i).render(g, slotPos.x, slotPos.y);
            if (slotPos.contains(Handler.get().getMouse())) {
                Handler.get().getAbilityManager().getAbilityHUD().getStatusTooltip().render(getImmunities().get(i), g);
            }
        }
        // Draw buffs to the right of immunities
        for (int i = 0; i < getBuffs().size(); i++) {
            Rectangle slotPos = new Rectangle(immunityCount + Handler.get().getAbilityManager().getAbilityHUD().getBounds().x + (i * ItemSlot.SLOTSIZE),
                    Handler.get().getHeight() - ItemSlot.SLOTSIZE * 2 - (ItemSlot.SLOTSIZE * yOffset) - 8,
                    32,
                    32);
            getBuffs().get(i).render(g, slotPos.x, slotPos.y);
            if (slotPos.contains(Handler.get().getMouse())) {
                Handler.get().getAbilityManager().getAbilityHUD().getStatusTooltip().render(getBuffs().get(i), g);
            }
        }
    }

    /*
     * All movement/attacking animations based on directions
     * @returns: the respective image
     */
    private BufferedImage getAnimationDirection(Rectangle mouse) {

        /*
         * Animations for attacking while walking
         */

        if (xMove < 0 && Handler.get().getMouseManager().isLeftPressed()) {
            if (hasLeftClickedUI(mouse))
                return getAnimationByLastFaced();
            if (Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
                return getAnimationByLastFaced();
            else if (lastFaced == Direction.UP)
                return attUp.getCurrentFrame();
            else if (lastFaced == Direction.DOWN)
                return attDown.getCurrentFrame();
            else if (lastFaced == Direction.LEFT)
                return attLeft.getCurrentFrame();
            else if (lastFaced == Direction.RIGHT)
                return attRight.getCurrentFrame();
        } else if (xMove > 0 && Handler.get().getMouseManager().isLeftPressed()) {
            if (hasLeftClickedUI(mouse))
                return getAnimationByLastFaced();
            if (Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
                return getAnimationByLastFaced();
            else if (lastFaced == Direction.UP)
                return attUp.getCurrentFrame();
            else if (lastFaced == Direction.DOWN)
                return attDown.getCurrentFrame();
            else if (lastFaced == Direction.LEFT)
                return attLeft.getCurrentFrame();
            else if (lastFaced == Direction.RIGHT)
                return attRight.getCurrentFrame();
        } else if (yMove < 0 && Handler.get().getMouseManager().isLeftPressed()) {
            if (hasLeftClickedUI(mouse))
                return getAnimationByLastFaced();
            if (Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
                return getAnimationByLastFaced();
            else if (lastFaced == Direction.UP)
                return attUp.getCurrentFrame();
            else if (lastFaced == Direction.DOWN)
                return attDown.getCurrentFrame();
            else if (lastFaced == Direction.LEFT)
                return attLeft.getCurrentFrame();
            else if (lastFaced == Direction.RIGHT)
                return attRight.getCurrentFrame();
        } else if (yMove > 0 && Handler.get().getMouseManager().isLeftPressed()) {
            if (hasLeftClickedUI(mouse))
                return getAnimationByLastFaced();
            if (Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
                return getAnimationByLastFaced();
            else if (lastFaced == Direction.UP)
                return attUp.getCurrentFrame();
            else if (lastFaced == Direction.DOWN)
                return attDown.getCurrentFrame();
            else if (lastFaced == Direction.LEFT)
                return attLeft.getCurrentFrame();
            else if (lastFaced == Direction.RIGHT)
                return attRight.getCurrentFrame();
        }

        /*
         * Animations for walking and moving mouse
         */

        if (xMove < 0) {
            if (lastFaced == Direction.UP)
                return aUp.getCurrentFrame();
            else if (lastFaced == Direction.DOWN)
                return aDown.getCurrentFrame();
            else if (lastFaced == Direction.LEFT)
                return aLeft.getCurrentFrame();
            else if (lastFaced == Direction.RIGHT)
                return aRight.getCurrentFrame();
        } else if (xMove > 0) {
            if (lastFaced == Direction.UP)
                return aUp.getCurrentFrame();
            else if (lastFaced == Direction.DOWN)
                return aDown.getCurrentFrame();
            else if (lastFaced == Direction.LEFT)
                return aLeft.getCurrentFrame();
            else if (lastFaced == Direction.RIGHT)
                return aRight.getCurrentFrame();
        } else if (yMove < 0) {
            if (lastFaced == Direction.UP)
                return aUp.getCurrentFrame();
            else if (lastFaced == Direction.DOWN)
                return aDown.getCurrentFrame();
            else if (lastFaced == Direction.LEFT)
                return aLeft.getCurrentFrame();
            else if (lastFaced == Direction.RIGHT)
                return aRight.getCurrentFrame();
        } else if (yMove > 0) {
            if (lastFaced == Direction.UP)
                return aUp.getCurrentFrame();
            else if (lastFaced == Direction.DOWN)
                return aDown.getCurrentFrame();
            else if (lastFaced == Direction.LEFT)
                return aLeft.getCurrentFrame();
            else if (lastFaced == Direction.RIGHT)
                return aRight.getCurrentFrame();
        }

        /*
         * Attacking animations while idle
         */

        if (lastFaced == Direction.LEFT && Handler.get().getMouseManager().isLeftPressed()) {
            if (hasLeftClickedUI(mouse) || Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null) {
                return aLeft.getDefaultFrame();
            }
            return attLeft.getCurrentFrame();
        } else if (lastFaced == Direction.RIGHT && Handler.get().getMouseManager().isLeftPressed()) {
            if (hasLeftClickedUI(mouse) || Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null) {
                return aRight.getDefaultFrame();
            }
            return attRight.getCurrentFrame();
        } else if (lastFaced == Direction.UP && Handler.get().getMouseManager().isLeftPressed()) {
            if (hasLeftClickedUI(mouse) || Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null) {
                return aUp.getDefaultFrame();
            }
            return attUp.getCurrentFrame();
        } else if (lastFaced == Direction.DOWN && Handler.get().getMouseManager().isLeftPressed()) {
            if (hasLeftClickedUI(mouse) || Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null) {
                return aDown.getDefaultFrame();
            }
            return attDown.getCurrentFrame();
        }

        /*
         * Animations while idle
         */

        if (lastFaced == Direction.LEFT) {
            aDefault = aLeft;
            return aDefault.getDefaultFrame();
        } else if (lastFaced == Direction.RIGHT) {
            aDefault = aRight;
            return aDefault.getDefaultFrame();
        } else if (lastFaced == Direction.UP) {
            aDefault = aUp;
            return aDefault.getDefaultFrame();
        } else if (lastFaced == Direction.DOWN) {
            aDefault = aDown;
            return aDefault.getDefaultFrame();
        }

        return Assets.player_down[1];
    }

    /*
     * Gets the current frame
     * @returns the current frame image
     */
    private BufferedImage getCurrentAnimationFrame(Rectangle mouse) {
        // Walk and Attack animations
        return getAnimationDirection(mouse);
    }

    /*
     * This method should never be called, since the player cannot interact with itself
     */
    @Override
    public void interact() {
        System.out.println("Oops, we're interacting with ourself. That's odd!");
    }

    // Getters & Setters

    private World getCurrentMap() {
        return Handler.get().getWorld();
    }

    public boolean isMovementAllowed() {
        return movementAllowed;
    }

    public void setMovementAllowed(boolean movementAllowed) {
        this.movementAllowed = movementAllowed;
    }

    public Direction getLastFaced() {
        return lastFaced;
    }

    public void setLastFaced(Direction lastFaced) {
        this.lastFaced = lastFaced;
    }

    public ShopKeeper getShopKeeper() {
        return shopKeeper;
    }

    public Banker getBankEntity() {
        return bankEntity;
    }

    public void setBankEntity(Banker bankEntity) {
        this.bankEntity = bankEntity;
    }

    public Rectangle itemPickupRadius() {
        itemPickupRadius.setLocation((int) (x + bounds.x - 24), (int) (y + bounds.y - 24));
        return itemPickupRadius;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public AbilityTrainer getAbilityTrainer() {
        return abilityTrainer;
    }

    public void setAbilityTrainer(AbilityTrainer abilityTrainer) {
        this.abilityTrainer = abilityTrainer;
    }

    public BountyBoard getBountyBoard() {
        return bountyBoard;
    }

    public void setBountyBoard(BountyBoard bountyBoard) {
        this.bountyBoard = bountyBoard;
    }

    public int getAbilityPoints() {
        return abilityPoints;
    }

    public void setAbilityPoints(int abilityPoints) {
        this.abilityPoints = abilityPoints;
    }

    public void addAbilityPoints() {
        this.abilityPoints++;
    }
}
