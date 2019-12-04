package dev.ipsych0.myrinnia.items;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.chatwindow.Filter;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Item implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5417348314768685085L;
    // ItemList

    public static final int ITEMWIDTH = 32, ITEMHEIGHT = 32;
    public static Item[] items = new Item[1024];


    /*
     * Items
     */
    public static Item lightWood = Utils.loadItem("0_lightwood.json", Assets.lightWood);
    public static Item azuriteOre = Utils.loadItem("1_azurite_ore.json", Assets.azuriteOre);
    public static Item magicSword = Utils.loadItem("2_magic_sword.json", Assets.undiscovered);
    public static Item beginnersSword = Utils.loadItem("3_beginners_sword.json", Assets.beginnersSword);
    public static Item coins = Utils.loadItem("4_coins.json", Assets.coins[0]);
    public static Item mackerelFish = Utils.loadItem("5_mackerel.json", Assets.mackerelFish, 10 * 60, (Use & Serializable) (i) -> {
        Handler.get().getPlayer().heal(20);
        Handler.get().removeItem(Item.mackerelFish, 1);
    });
    public static Item ryansAxe =  Utils.loadItem("6_ryans_axe.json", Assets.ryansAxe);
    public static Item simplePickaxe = Utils.loadItem("7_simple_pickaxe.json", Assets.simplePickaxe);
    public static Item beginnersStaff = Utils.loadItem("8_beginners_staff.json", Assets.beginnersStaff);
    public static Item bountyContract = Utils.loadItem("9_bounty_contract.json", Assets.bountyContract);
    public static Item simpleAxe = Utils.loadItem("10_simple_axe.json", Assets.simpleAxe);
    public static Item beginnersBow = Utils.loadItem("11_beginner's_bow.json", Assets.beginnersBow);
    public static Item simpleFishingRod = Utils.loadItem("12_simple_fishing_rod.json", Assets.simpleFishingRod);
    public static Item simpleSpellBook = Utils.loadItem("13_simple_spellbook.json", Assets.simpleSpellBook);
    public static Item simpleShield = Utils.loadItem("14_simple_shield.json", Assets.simpleShield);
    public static Item simpleQuiver = Utils.loadItem("15_simple_quiver.json", Assets.simpleQuiver);
    public static Item simpleSandals = Utils.loadItem("16_simple_sandals.json", Assets.simpleSandals);
    public static Item copperPickaxe = Utils.loadItem("17_copper_pickaxe.json", Assets.copperPickaxe);
    public static Item copperAxe = Utils.loadItem("18_copper_axe.json", Assets.copperAxe);
    public static Item copperOre = Utils.loadItem("19_copper_ore.json", Assets.copperOre);
    public static Item miningEquipment = Utils.loadItem("20_mining_equipment.json", Assets.miningEquipment);
    public static Item malachite = Utils.loadItem("21_malachite.json", Assets.malachite);
    public static Item dustyScroll = Utils.loadItem("22_dusty_scroll.json", Assets.dustyScroll, 0, (Use & Serializable) (i) -> {
        // TODO: UNLOCK EARTH ABILITY FOR MAGIC/RANGED/MELEE
        Handler.get().sendMsg("TODO: You unlocked [Earth Ability].");
        Handler.get().removeItem(Item.dustyScroll, 1);
    });

    // Class

    private ItemType[] itemTypes;
    private ItemRarity itemRarity;
    private ItemRequirement[] requirements;
    private transient BufferedImage texture;
    private String name;
    private final int id;
    private EquipSlot equipSlot;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int defence;
    private int vitality;
    private double attackSpeed;
    private double movementSpeed;
    private int x;
    private int y;
    private Rectangle bounds;
    private Rectangle position;
    private int count;
    private boolean pickedUp = false;
    public static boolean pickUpKeyPressed = false;
    private int price;
    private boolean stackable;
    private int respawnTimer = 10800;
    private boolean equippable;
    private boolean hovering;
    private Use use;
    private int useCooldown;
    private boolean used;
    private int usedTimer;

    public Item(BufferedImage texture, String name, int id, ItemRarity itemRarity, int price, boolean stackable, ItemType... itemTypes) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        this.itemTypes = itemTypes;
        this.itemRarity = itemRarity;
        this.price = price;
        this.stackable = stackable;
        this.equipSlot = EquipSlot.None;
        this.equippable = false;
        this.strength = 0;
        this.dexterity = 0;
        this.intelligence = 0;
        this.defence = 0;
        this.vitality = 0;
        this.attackSpeed = 0;
        this.movementSpeed = 0;

        // Prevent duplicate IDs when creating items
        try {
            if (items[id] != null && !name.equals(items[id].name)) {
                throw new DuplicateIDException(name, id);
            } else {
                // If the item already exists, don't create a new reference
                if (items[id] == null) {
                    items[id] = this;
                }
            }
        } catch (DuplicateIDException exc) {
            exc.printStackTrace();
            System.exit(1);
        }

        bounds = new Rectangle(0, 0, 32, 32);
        position = new Rectangle(0, 0, 32, 32);
    }

    public Item(BufferedImage texture, String name, int id, ItemRarity itemRarity,
                EquipSlot equipSlot, int strength, int dexterity, int intelligence, int defence, int vitality, double attackSpeed, double movementSpeed,
                int price, boolean stackable, ItemType[] itemTypes, ItemRequirement... requirements) {
        this(texture, name, id, itemRarity, price, stackable, itemTypes);
        this.equipSlot = equipSlot;
        this.equippable = true;
        this.requirements = requirements;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.defence = defence;
        this.vitality = vitality;
        this.attackSpeed = attackSpeed;
        this.movementSpeed = movementSpeed;
    }

    public void tick() {

    }

    public void render(Graphics2D g) {
        render(g, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()));
    }

    private void render(Graphics2D g, int x, int y) {
        g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
    }

    /*
     * Adds a new item equippable item to the world
     * @params: x,y pause and amount
     */
    public Item createItem(int x, int y, int amount) {
        Item i;
        if (this.isEquippable()) {
            i = new Item(texture, name, id, itemRarity, equipSlot, strength, dexterity, intelligence, defence, vitality, attackSpeed, movementSpeed, price, stackable, itemTypes, requirements);
        } else {
            i = new Item(texture, name, id, itemRarity, price, stackable, itemTypes);
        }

        i.setPosition(x, y);

        // If the item is stackable, set the amount
        if (i.stackable)
            i.setAmount(amount);
            // If the item is unstackable, the count is always 1.
        else
            i.setAmount(1);
        return i;
    }

    private void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*
     * Returns the pause of the item
     */
    public Rectangle itemPosition(double xOffset, double yOffset) {
        position.setBounds((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), 32, 32);
        return position;
    }

    /*
     * Item pickup function
     */
    public boolean pickUpItem(Item item) {
        int inventoryIndex = Handler.get().getInventory().findFreeSlot(item);
        if (inventoryIndex >= 0) {
            // If we have space
            if (id == item.getId()) {
                if (Handler.get().getInventory().getItemSlots().get(inventoryIndex).addItem(item, item.getCount())) {
                    Handler.get().sendMsg("Picked up " + item.getCount() + "x " + item.getName() + ".", Filter.LOOT);
                    pickedUp = true;
                    return true;
                }
            }
            System.out.println("Something went wrong picking up this item.");
            return false;
        }
        return false;
    }


    // Getters & Setters

    public int getEquipSlot() {
        return equipSlot.getSlotId();
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getDefence() {
        return defence;
    }

    public int getVitality() {
        return vitality;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public double getMovementSpeed() {
        return movementSpeed;
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

    private int getCount() {
        return count;
    }

    private void setAmount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isType(ItemType type) {
        if (itemTypes == null) {
            return false;
        }
        for (ItemType it : itemTypes) {
            if (it == type) {
                return true;
            }
        }
        return false;
    }

    public ItemType[] getItemTypes() {
        return itemTypes;
    }

    public void setItemType(ItemType[] itemTypes) {
        this.itemTypes = itemTypes;
    }

    public ItemRarity getItemRarity() {
        return itemRarity;
    }

    public void setItemRarity(ItemRarity itemRarity) {
        this.itemRarity = itemRarity;
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

    public ItemRequirement[] getRequirements() {
        return requirements;
    }

    public void setRequirements(ItemRequirement[] requirements) {
        this.requirements = requirements;
    }

    public void startRespawnTimer() {
        this.respawnTimer--;
    }

    public int getRespawnTimer() {
        return respawnTimer;
    }

    public void setRespawnTimer(int respawnTimer) {
        this.respawnTimer = respawnTimer;
    }

    public Rectangle getPosition() {
        return position;
    }

    public void setPosition(Rectangle position) {
        this.position = position;
    }

    private boolean isEquippable() {
        return equippable;
    }

    public void setEquippable(boolean equippable) {
        this.equippable = equippable;
    }

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    public Use getUse() {
        return use;
    }

    public void setUse(Use use) {
        this.use = use;
    }

    public int getUseCooldown() {
        return useCooldown;
    }

    public void setUseCooldown(int useCooldown) {
        this.useCooldown = useCooldown;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getUsedTimer() {
        return usedTimer;
    }

    public void setUsedTimer(int usedTimer) {
        this.usedTimer = usedTimer;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ImageIO.write(this.texture, "png", buffer);

        out.writeInt(buffer.size()); // Prepend image with byte count
        buffer.writeTo(out);         // Write image
        buffer.close();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        int size = in.readInt(); // Read byte count
        byte[] buffer = new byte[size];
        in.readFully(buffer); // Make sure you read all bytes of the image

        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        this.texture = ImageIO.read(is);
        is.close();
    }
}
