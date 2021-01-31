package dev.ipsych0.droptabletool;

import dev.ipsych0.itemmaker.IDGenerator;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.items.ItemRarity;
import dev.ipsych0.myrinnia.items.ItemRequirement;
import dev.ipsych0.myrinnia.items.ItemType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

class JSONItem implements Serializable {

    private static final long serialVersionUID = -758455032925771207L;
    private ItemType[] itemTypes;
    private ItemRarity itemRarity;
    private ItemRequirement[] requirements;
    private BufferedImage texture;
    private String name;
    private int id;
    private EquipSlot equipSlot;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int defence;
    private int vitality;
    private float attackSpeed;
    private float movementSpeed;
    private int x;
    private int y;
    private Rectangle bounds;
    private Rectangle position;
    private int count;
    private boolean pickedUp = false;
    private static boolean pickUpKeyPressed = false;
    private int price;
    private boolean stackable;
    private boolean equippable;
    private int respawnTimer = 10800;
    private int useCooldown = 60;

    public JSONItem(){}

    public ItemType[] getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(ItemType[] itemTypes) {
        this.itemTypes = itemTypes;
    }

    public ItemRarity getItemRarity() {
        return itemRarity;
    }

    public void setItemRarity(ItemRarity itemRarity) {
        this.itemRarity = itemRarity;
    }

    public ItemRequirement[] getRequirements() {
        return requirements;
    }

    public void setRequirements(ItemRequirement[] requirements) {
        this.requirements = requirements;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EquipSlot getEquipSlot() {
        return equipSlot;
    }

    public void setEquipSlot(EquipSlot equipSlot) {
        this.equipSlot = equipSlot;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Rectangle getPosition() {
        return position;
    }

    public void setPosition(Rectangle position) {
        this.position = position;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public static boolean isPickUpKeyPressed() {
        return pickUpKeyPressed;
    }

    public static void setPickUpKeyPressed(boolean pickUpKeyPressed) {
        JSONItem.pickUpKeyPressed = pickUpKeyPressed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public boolean isEquippable() {
        return equippable;
    }

    public void setEquippable(boolean equippable) {
        this.equippable = equippable;
    }

    public int getRespawnTimer() {
        return respawnTimer;
    }

    public void setRespawnTimer(int respawnTimer) {
        this.respawnTimer = respawnTimer;
    }

    public int getUseCooldown() {
        return useCooldown;
    }

    public void setUseCooldown(int useCooldown) {
        this.useCooldown = useCooldown;
    }
}
