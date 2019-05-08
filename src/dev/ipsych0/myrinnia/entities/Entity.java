package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.entities.npcs.Choice;
import dev.ipsych0.myrinnia.entities.npcs.ChoiceCondition;
import dev.ipsych0.myrinnia.entities.npcs.Script;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.hpoverlay.HPOverlay;
import dev.ipsych0.myrinnia.tiles.Tiles;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class Entity implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -6319447656301966908L;
    protected float x, y;
    protected int width, height;
    protected Rectangle bounds;
    public static boolean isCloseToNPC = false;
    protected int health;
    protected static final int DEFAULT_HEALTH = 100;
    protected int maxHealth = DEFAULT_HEALTH;
    protected boolean active = true;
    protected boolean attackable = true;
    protected boolean isNpc = false;
    protected boolean drawnOnMap = false;
    protected boolean damaged = false;
    protected boolean staticNpc = false;
    protected boolean solid = true;
    private Entity damageDealer;
    private Entity damageReceiver;
    protected int speakingTurn = 0;
    private int speakingCheckpoint = -1;
    protected transient ChatDialogue chatDialogue;
    private boolean overlayDrawn = true;
    private int lastHit = 0;
    protected boolean inCombat = false;
    protected int combatTimer = 0;
    protected int respawnTimer = 600;
    protected Rectangle collision;
    protected Script script;
    private String name;
    private static final double DIVISION_QUOTIENT = 200.0;
    private static final double ABILITY_DMG_COEFFICIENT = 1.08;

    protected Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        health = DEFAULT_HEALTH;

        bounds = new Rectangle(0, 0, width, height);
        collision = new Rectangle((int) x + bounds.x, (int) y + bounds.y, bounds.width, bounds.height);
    }

    // Abstract Methods (EVERY object that is an Entity, MUST HAVE these methods)

    public abstract void tick();

    public abstract void render(Graphics2D g);

    public abstract void postRender(Graphics2D g);

    protected abstract void die();

    public abstract void respawn();

    protected abstract String[] getEntityInfo(Entity hoveringEntity);

    protected abstract void updateDialogue();

    /*
     * Checks the collision for Entities
     * @returns: true if collision, false if no collision
     */
    protected boolean checkEntityCollisions(float xOffset, float yOffset) {
        if (Handler.noclipMode && this.equals(Handler.get().getPlayer()))
            return false;
        for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (!e.solid)
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
                return true;
        }
        return false;
    }


    /*
     * Checks if the distance between the player and the closest Entity is <= 64px
     */
    protected boolean playerIsNearNpc() {
        // Looks for the closest entity and returns that entity
        if (distanceToEntity(((int) getClosestEntity().getX() + getClosestEntity().getWidth() / 2), ((int) getClosestEntity().getY() + +getClosestEntity().getHeight() / 2),
                ((int) Handler.get().getPlayer().getX() + Handler.get().getPlayer().getWidth() / 2), ((int) Handler.get().getPlayer().getY() + Handler.get().getPlayer().getHeight() / 2)) <= Tiles.TILEWIDTH * 2) {
            // Interact with the respective speaking turn
            isCloseToNPC = true;
            return true;
        } else {
            // Out of range
            isCloseToNPC = false;
            return false;
        }

    }

    /*
     * Returns the closest Entity to the Player
     */
    public Entity getClosestEntity() {
        double closestDistance;
        Entity closestEntity;
        HashMap<Double, Entity> hashMap = new HashMap<>();
        ArrayList<Double> pythagoras = new ArrayList<>();
        for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
            if (!e.isNpc()) {
                continue;
            }
            if (e.equals(Handler.get().getPlayer())) {
                continue;
            }

            int dx = (int) ((Handler.get().getPlayer().getX() + Handler.get().getPlayer().getWidth() / 2) - (e.getX() + e.getWidth() / 2));
            int dy = (int) ((Handler.get().getPlayer().getY() + Handler.get().getPlayer().getHeight() / 2) - (e.getY() + e.getHeight() / 2));
            hashMap.put(Math.sqrt(dx * dx + dy * dy), e);
            pythagoras.add(Math.sqrt(dx * dx + dy * dy));
        }
        Collections.sort(pythagoras);
        closestDistance = pythagoras.get(0);
        closestEntity = hashMap.get(closestDistance);
        return closestEntity;
    }

    /*
     * Damage formula for auto attacks
     */
    public int getDamage(DamageType damageType, Entity dealer, Entity receiver) {
        // Default damage formula
        Creature d = (Creature) dealer;
        Creature r = (Creature) receiver;
        int power;
        switch (damageType) {
            case STR:
                power = d.getStrength();
                break;
            case DEX:
                power = d.getDexterity();
                break;
            case INT:
                power = d.getIntelligence();
                break;
            default:
                power = 0;
                break;

        }
        return (int) Math.ceil((DIVISION_QUOTIENT / (DIVISION_QUOTIENT + r.getDefence())) * power) + d.getBaseDamage();
    }

    /*
     * Damage formula for abilities
     */
    public int getDamage(DamageType damageType, Entity dealer, Entity receiver, Ability ability) {
        // Default damage formula
        Creature d = (Creature) dealer;
        Creature r = (Creature) receiver;

        // Calculations
        double defenceRatio = (DIVISION_QUOTIENT / (DIVISION_QUOTIENT + r.getDefence()));
        double defaultDamage = (double) getDamage(damageType, dealer, receiver);
        double abilityDamage = ability.getBaseDamage() * Math.pow(ABILITY_DMG_COEFFICIENT, (double) ability.getElement().getLevel());
        double baseDamage = (double) d.getBaseDamage();

        // Formula
        return (int) Math.round((defaultDamage + abilityDamage) * defenceRatio + baseDamage);
//        return (int) Math.ceil((DIVISION_QUOTIENT / (DIVISION_QUOTIENT + r.getDefence())) * power) + d.getBaseDamage() + ability.getBaseDamage();
    }

    public void heal(int heal){
        if(health + heal >= maxHealth){
            health = maxHealth;
        }else{
            health += heal;
            Handler.get().addHealSplat(this, heal);
        }
    }

    /*
     * Deals the damage (subtracts the damage from HP)
     * @params: dealer = the Entity that deals the damage
     * 			receiver = the Entity that receives the damage
     */
    public void damage(DamageType damageType, Entity dealer, Entity receiver) {
        damageDealer = dealer;
        damageReceiver = receiver;
        damageReceiver.health -= damageDealer.getDamage(damageType, dealer, receiver);
        damageReceiver.damaged = true;
        damageReceiver.lastHit = 0;
        damageReceiver.combatTimer = 0;
        damageReceiver.inCombat = true;
        Handler.get().addHitSplat(receiver, damageDealer, damageType);
        if (damageDealer.equals(Handler.get().getPlayer())) {
            damageDealer.setInCombat(true);
            damageDealer.combatTimer = 0;
        }

        if (damageReceiver.health <= 0) {
            damageReceiver.active = false;
            damageReceiver.die();
        }
    }

    /*
     * Deals the damage (subtracts the damage from HP)
     * @params: dealer = the Entity that deals the damage
     * 			receiver = the Entity that receives the damage
     */
    public void damage(DamageType damageType, Entity dealer, Entity receiver, Ability ability) {
        damageDealer = dealer;
        damageReceiver = receiver;
        damageReceiver.health -= damageDealer.getDamage(damageType, dealer, receiver, ability);
        damageReceiver.damaged = true;
        damageReceiver.lastHit = 0;
        damageReceiver.combatTimer = 0;
        damageReceiver.inCombat = true;
        Handler.get().addHitSplat(receiver, damageDealer, damageType, ability);
        if (damageDealer.equals(Handler.get().getPlayer())) {
            damageDealer.setInCombat(true);
            damageDealer.combatTimer = 0;
        }

        if (damageReceiver.health <= 0) {
            damageReceiver.active = false;
            damageReceiver.die();
        }
    }

    public void addBuff(Entity dealer, Entity receiver, Buff buff) {
        damageDealer = dealer;
        damageReceiver = receiver;

        Creature r = ((Creature) receiver);

        boolean hasBuff = false;
        for (Buff b : r.getBuffs()) {
            // Check if the buff is already on the receiver
            if (b.equals(buff)) {
                hasBuff = true;

                // If that's the case, reapply the effect
                b.setEffectApplied(false);
                break;
            }
        }
        if (!hasBuff) {
            r.getBuffs().add(buff);
        }
    }

    public void addCondition(Entity dealer, Entity receiver, Condition condition) {
        damageDealer = dealer;
        damageReceiver = receiver;
        damageReceiver.damaged = true;
        damageReceiver.lastHit = 0;
        damageReceiver.combatTimer = 0;
        damageReceiver.inCombat = true;

        Creature r = ((Creature) receiver);

        boolean hasCondition = false;
        for (Condition c : r.getConditions()) {
            // Check if the condition is already on the receiver
            if (c.getType() == condition.getType()) {
                hasCondition = true;
                // If that's the case, increase the timeLeft
                c.setCurrentDuration(c.getCurrentDuration() + condition.getCurrentDuration());

                // If the new ability has a higher condition damage than the current one, increase the damage
                if (condition.getConditionDamage() > c.getConditionDamage()) {
                    c.setConditionDamage(condition.getConditionDamage());
                }
                // If we don't already have a condition of this type, simply add it
            }
        }
        if (!hasCondition) {
            r.getConditions().add(condition);
            Handler.get().getWorld().getEntityManager().getHitSplats().add(new ConditionSplat(receiver, condition, condition.getConditionDamage()));
        }


        if (damageDealer.equals(Handler.get().getPlayer())) {
            damageDealer.setInCombat(true);
            damageDealer.combatTimer = 0;
        }
    }

    public void tickCondition(Entity receiver, Condition condition) {
        damageReceiver = receiver;
        damageReceiver.health -= condition.getConditionDamage();
        damageReceiver.damaged = true;
        damageReceiver.lastHit = 0;
        damageReceiver.combatTimer = 0;
        damageReceiver.inCombat = true;

        if (damageReceiver.health <= 0) {
            damageReceiver.active = false;
            damageReceiver.die();
        }
    }

    /*
     * Drawing the hitsplat
     */
    public void updateCombatTimer() {
        if (damaged) {
            damageReceiver.lastHit++;

            if (damageReceiver.lastHit == 45) {
                damageReceiver.damaged = false;
                damageReceiver.lastHit = 0;
            }
        }
    }

    public void drawHP(Graphics2D g) {
        g.setColor(HPOverlay.hpColorRed);
        g.fillRoundRect((int) (x - Handler.get().getGameCamera().getxOffset() - 6),
                (int) (y - Handler.get().getGameCamera().getyOffset() - 8), width + 12, 6, 0, 4);
        g.setColor(HPOverlay.hpColorRedOutline);
        g.drawRoundRect((int) (x - Handler.get().getGameCamera().getxOffset() - 6),
                (int) (y - Handler.get().getGameCamera().getyOffset() - 8), width + 12, 6, 0, 4);

        g.setColor(HPOverlay.hpColorGreen);
        if (health >= maxHealth) {
            g.fillRoundRect((int) (x - Handler.get().getGameCamera().getxOffset() - 6),
                    (int) (y - Handler.get().getGameCamera().getyOffset() - 8), width + 12, 6, 0, 4);
            g.setColor(HPOverlay.hpColorGreenOutline);
            g.drawRoundRect((int) (x - Handler.get().getGameCamera().getxOffset() - 6),
                    (int) (y - Handler.get().getGameCamera().getyOffset() - 8), width + 12, 6, 0, 4);
        } else {
            g.fillRoundRect((int) (x - Handler.get().getGameCamera().getxOffset() - 6),
                    (int) (y - Handler.get().getGameCamera().getyOffset() - 8), (int) ((width + 12) * (double) this.getHealth() /
                            (double) this.getMaxHealth()), 6, 0, 4);
            g.setColor(HPOverlay.hpColorGreenOutline);
            g.drawRoundRect((int) (x - Handler.get().getGameCamera().getxOffset() - 6),
                    (int) (y - Handler.get().getGameCamera().getyOffset() - 8), (int) ((width + 12) * (double) this.getHealth() /
                            (double) this.getMaxHealth()), 6, 0, 4);
        }
    }

    /**
     * Draws an Entity's information to an overlay at the top of the screen
     *
     * @param hoveringEntity
     * @param g
     */
    public void drawEntityOverlay(Entity hoveringEntity, Graphics2D g) {
        int yPos = 12;
        g.drawImage(Assets.chatwindow, Handler.get().getWidth() / 2 - 100, 1, 200, 50, null);
        for (int i = 0; i < getEntityInfo(hoveringEntity).length; i++) {
            Text.drawString(g, getEntityInfo(hoveringEntity)[i], Handler.get().getWidth() / 2, yPos + (14 * i), true, Color.YELLOW, Assets.font14);
        }
    }


    /*
     * Check the distance to another entity
     */
    protected double distanceToEntity(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /*
     * Returns the collision bounds of an Entity
     */
    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        collision.setBounds((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
        return collision;
    }

    public void interact() {
        if (script == null) {
            return;
        }

        // If the conversation was reset, reinitialize the first time we interact again
        if (speakingTurn == -1) {
            chatDialogue = null;
            speakingTurn = 0;
            return;
        }
        // If there is only text to be displayed, advance to the next conversation
        if (script.getDialogues().get(speakingTurn).getText() != null) {
            chatDialogue = new ChatDialogue(new String[]{script.getDialogues().get(speakingTurn).getText()});
            chatDialogue.setChosenOption(null);
            updateDialogue();
            // If there is a condition to proceed, check the condition
            if(script.getDialogues().get(speakingTurn).getChoiceCondition() != null){
                ChoiceCondition choiceCondition = script.getDialogues().get(speakingTurn).getChoiceCondition();
                String condition = choiceCondition.getCondition();
                // Check if the condition is met
                if(choiceConditionMet(condition)){
                    setSpeakingTurn(script.getDialogues().get(speakingTurn).getNextId());
                }else{
                    setSpeakingTurn(choiceCondition.getFalseId());
                }
            }else{
                setSpeakingTurn(script.getDialogues().get(speakingTurn).getNextId());
            }

        } else {
            // If we only have a continue button, don't set a chosen 'option'
            if (chatDialogue != null && chatDialogue.getMenuOptions().length == 1) {
                chatDialogue.setChosenOption(null);
            }
            // If there is a choice menu and we selected a choice, handle the choice logic
            if (chatDialogue != null && chatDialogue.getChosenOption() != null) {
                Choice choice = script.getDialogues().get(speakingTurn).getOptions().get(chatDialogue.getChosenOption().getOptionID());
                // If there is a condition to proceed with the conversation, check it
                if (choice.getChoiceCondition() != null) {
                    String condition = choice.getChoiceCondition().getCondition();
                    if (choiceConditionMet(condition)) {
                        // If we meet the condition, proceed
                        setSpeakingTurn(choice.getNextId());
                    } else {
                        // If we don't meet the condition, return to whatever menu falseId points to
                        setSpeakingTurn(choice.getChoiceCondition().getFalseId());
                    }
                    chatDialogue.setChosenOption(null);
                    interact();
                    return;
                } else {
                    // If there is no condition, we can always proceed
                    if (chatDialogue.getMenuOptions().length > 1) {
                        setSpeakingTurn(choice.getNextId());
                    }
                    chatDialogue.setChosenOption(null);
                    interact();
                    return;
                }
            }

            // Update the list of dialogue choices
            List<Choice> choiceList = script.getDialogues().get(speakingTurn).getOptions();
            String[] choices = new String[choiceList.size()];
            for (int i = 0; i < choiceList.size(); i++) {
                choices[i] = choiceList.get(i).getText();
            }
            chatDialogue = new ChatDialogue(choices);
            updateDialogue();
        }
    }

    /**
     * MUST be overriden in the sub-class for specific behaviour!
     *
     * @param condition - The choice to check condition for
     * @return true if condition is met, false if condition is not met
     */
    protected boolean choiceConditionMet(String condition) {
        System.err.println("SHOULD NOT APPEAR: OVERRIDE CHOICE CONDITION CHECK IN SUBCLASS!");
        return false;
    }


    // Getters & Setters
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    protected int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAttackable() {
        return attackable;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public boolean isNpc() {
        return isNpc;
    }

    public void setNpc(boolean isNpc) {
        this.isNpc = isNpc;
    }

    public boolean isDrawnOnMap() {
        return drawnOnMap;
    }

    public void setDrawnOnMap(boolean drawnOnMap) {
        this.drawnOnMap = drawnOnMap;
    }

    public boolean isStaticNpc() {
        return staticNpc;
    }

    public void setStaticNpc(boolean staticNpc) {
        this.staticNpc = staticNpc;
    }

    public Entity getDamageDealer() {
        return damageDealer;
    }

    public void setDamageDealer(Entity damageDealer) {
        this.damageDealer = damageDealer;
    }

    public Entity getDamageReceiver() {
        return damageReceiver;
    }

    public void setDamageReceiver(Entity damageReceiver) {
        this.damageReceiver = damageReceiver;
    }

    public ChatDialogue getChatDialogue() {
        return chatDialogue;
    }

    public void setChatDialogue(ChatDialogue chatDialogue) {
        this.chatDialogue = chatDialogue;
    }

    public int getSpeakingTurn() {
        return speakingTurn;
    }

    public void setSpeakingTurn(int speakingTurn) {
        this.speakingTurn = speakingTurn;
    }

    public boolean isOverlayDrawn() {
        return overlayDrawn;
    }

    public void setOverlayDrawn(boolean overlayDrawn) {
        this.overlayDrawn = overlayDrawn;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isInCombat() {
        return inCombat;
    }

    private void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
    }

    public int getSpeakingCheckpoint() {
        return speakingCheckpoint;
    }

    public void setSpeakingCheckpoint(int speakingCheckpoint) {
        this.speakingCheckpoint = speakingCheckpoint;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void startRespawnTimer() {
        this.respawnTimer--;
    }

    public int getRespawnTimer() {
        return respawnTimer;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public void setName(String name) {
        this.name = name;
    }
}
