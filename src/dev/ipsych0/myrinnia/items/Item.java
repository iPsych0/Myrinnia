package dev.ipsych0.myrinnia.items;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.chatwindow.Filter;
import dev.ipsych0.myrinnia.entities.Condition;
import dev.ipsych0.myrinnia.entities.Resistance;
import dev.ipsych0.myrinnia.entities.statics.ShamrockRockslide;
import dev.ipsych0.myrinnia.equipment.EquipSlot;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.utils.Utils;
import dev.ipsych0.myrinnia.worlds.Zone;

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
    public static Item mackerelFish = Utils.loadItem("5_mackerel.json", Assets.mackerelFish);
    public static Item ryansAxe = Utils.loadItem("6_ryans_axe.json", Assets.ryansAxe);
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
        Handler.get().sendMsg("TODO: You unlocked [Earth Ability].");
        Handler.get().removeItem(Item.dustyScroll, 1);
    });
    public static Item azuriteNecklace = Utils.loadItem("23_azurite_necklace.json", Assets.azuriteNecklace);
    public static Item weakAntidote, antidote, strongAntidote, weakPotionOfPrecision, potionOfPrecision, strongPotionOfPrecision,
            weakPotionOfMight, potionOfMight, strongPotionOfMight, weakPotionOfWisdom, potionOfWisdom, strongPotionOfWisdom,
            weakPotionofFortitude, potionofFortitude, strongPotionofFortitude, weakPotionOfVigor, potionOfVigor, strongPotionOfVigor;
    public static Item azureBatWing = Utils.loadItem("42_azure_bat_wing.json", Assets.azureBatWing);
    public static Item crablingClaw = Utils.loadItem("43_crabling_claw.json", Assets.crablingClaw);
    public static Item simpleGloves = Utils.loadItem("44_simple_gloves.json", Assets.simpleGloves);
    public static Item simpleBandana = Utils.loadItem("45_simple_bandana.json", Assets.simpleBandana);

    public static Item chitin = Utils.loadItem("46_chitin.json", Assets.chitin);
    public static Item scorpionTail = Utils.loadItem("47_scorpion_tail.json", Assets.scorpionTail);
    public static Item owlFeather = Utils.loadItem("48_owl_feather.json", Assets.owlFeather);
    public static Item dynamite = Utils.loadItem("49_dynamite.json", Assets.dynamite);
    public static Item detonator = Utils.loadItem("50_detonator.json", Assets.detonator, 0, (Use & Serializable) (i) -> {
        detonatorLogic();
    });
    public static Item vineRoot = Utils.loadItem("51_vine_root.json", Assets.vineRoot);
    public static Item simpleVest = Utils.loadItem("52_simple_vest.json", Assets.simpleVest);
    public static Item simpleTrousers = Utils.loadItem("53_simple_trousers.json", Assets.simpleTrousers);
    public static Item pileOfSand = Utils.loadItem("54_pile_of_sand.json", Assets.pileOfSand);
    public static Item pileOfAshes = Utils.loadItem("55_pile_of_ashes.json", Assets.pileOfAshes);
    public static Item glass = Utils.loadItem("56_glass.json", Assets.glass);
    public static Item lightWoodPlank = Utils.loadItem("57_lightwood_plank.json", Assets.lightWoodPlank);
    public static Item hardWood = Utils.loadItem("58_hardwood.json", Assets.hardWood);
    public static Item hardWoodPlank = Utils.loadItem("59_hardwood_plank.json", Assets.hardWoodPlank);
    public static Item ironOre = Utils.loadItem("60_iron_ore.json", Assets.ironOre);
    public static Item trout = Utils.loadItem("61_trout.json", Assets.trout);
    public static Item boneMeal = Utils.loadItem("62_bonemeal.json", Assets.boneMeal);
    public static Item rockyShell = Utils.loadItem("63_rocky_shell.json", Assets.rockyShell);
    public static Item tomatoSeeds = Utils.loadItem("64_tomato_seeds.json", Assets.tomatoSeeds);
    public static Item cabbageSeeds = Utils.loadItem("65_cabbage_seeds.json", Assets.cabbageSeeds);
    public static Item tomato = Utils.loadItem("66_tomato.json", Assets.tomato);
    public static Item cabbage = Utils.loadItem("67_cabbage.json", Assets.cabbage);
    public static Item wateringCan = Utils.loadItem("68_watering_can.json", Assets.wateringCan);

    static {
        initPotions();
    }

    /*
     * Class variables
     */

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
    private long respawnTime = 180L;
    private long timeDropped;
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

    public long getRespawnTime() {
        return respawnTime;
    }

    public void setRespawnTimer(long respawnTime) {
        this.respawnTime = respawnTime;
    }

    public long getTimeDropped() {
        return timeDropped;
    }

    public void setTimeDropped(long timeDropped) {
        this.timeDropped = timeDropped;
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

    /*
     * Items that have use cases
     */

    // Potions
    private static void initPotions() {
        weakAntidote = Utils.loadItem("24_weak_antidote.json", Assets.weakAntidote, 0, (Use & Serializable) (i) -> {
            Handler.get().getPlayer().addResistance(Handler.get().getPlayer(), new Resistance(Condition.Type.POISON, 60 * 60 * 5, 0.1));
            Handler.get().removeItem(Item.weakAntidote, 1);
        });
        antidote = Utils.loadItem("25_antidote.json", Assets.antidote, 0, (Use & Serializable) (i) -> {
            Handler.get().getPlayer().addResistance(Handler.get().getPlayer(), new Resistance(Condition.Type.POISON, 60 * 60 * 15, 0.2));
            Handler.get().removeItem(Item.antidote, 1);
        });
        strongAntidote = Utils.loadItem("26_strong_antidote.json", Assets.strongAntidote, 0, (Use & Serializable) (i) -> {
            Handler.get().getPlayer().addResistance(Handler.get().getPlayer(), new Resistance(Condition.Type.POISON, 60 * 60 * 30, 0.3));
            Handler.get().removeItem(Item.strongAntidote, 1);
        });

        weakPotionOfMight = Utils.loadItem("27_weak_potion_of_might.json", Assets.weakPotionOfMight, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.weakPotionOfMight, 1);
        });
        potionOfMight = Utils.loadItem("28_potion_of_might.json", Assets.potionOfMight, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.potionOfMight, 1);
        });
        strongPotionOfMight = Utils.loadItem("29_strong_potion_of_might.json", Assets.strongPotionOfMight, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.strongPotionOfMight, 1);
        });

        weakPotionOfPrecision = Utils.loadItem("30_weak_potion_of_precision.json", Assets.weakPotionOfPrecision, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.weakPotionOfPrecision, 1);
        });
        potionOfPrecision = Utils.loadItem("31_potion_of_precision.json", Assets.potionOfPrecision, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.potionOfPrecision, 1);
        });
        strongPotionOfPrecision = Utils.loadItem("32_strong_potion_of_precision.json", Assets.strongPotionOfPrecision, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.strongPotionOfPrecision, 1);
        });

        weakPotionOfWisdom = Utils.loadItem("33_weak_potion_of_wisdom.json", Assets.weakPotionOfWisdom, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.weakPotionOfWisdom, 1);
        });
        potionOfWisdom = Utils.loadItem("34_potion_of_wisdom.json", Assets.potionOfWisdom, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.potionOfWisdom, 1);
        });
        strongPotionOfWisdom = Utils.loadItem("35_strong_potion_of_wisdom.json", Assets.strongPotionOfWisdom, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.strongPotionOfWisdom, 1);
        });

        weakPotionofFortitude = Utils.loadItem("36_weak_potion_of_fortitude.json", Assets.weakPotionofFortitude, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.weakPotionofFortitude, 1);
        });
        potionofFortitude = Utils.loadItem("37_potion_of_fortitude.json", Assets.potionofFortitude, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.potionofFortitude, 1);
        });
        strongPotionofFortitude = Utils.loadItem("38_strong_potion_of_fortitude.json", Assets.strongPotionofFortitude, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.strongPotionofFortitude, 1);
        });

        weakPotionOfVigor = Utils.loadItem("39_weak_potion_of_vigor.json", Assets.weakPotionOfVigor, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.weakPotionOfVigor, 1);
        });
        potionOfVigor = Utils.loadItem("40_potion_of_vigor.json", Assets.potionOfVigor, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.potionOfVigor, 1);
        });
        strongPotionOfVigor = Utils.loadItem("41_strong_potion_of_vigor.json", Assets.strongPotionOfVigor, 0, (Use & Serializable) (i) -> {
            Handler.get().sendMsg("This potion has not been implemented yet.");
            Handler.get().removeItem(Item.strongPotionOfVigor, 1);
        });
    }

    private static void detonatorLogic() {
        if (!ShamrockRockslide.hasDetonated) {
            if (Handler.get().getPlayer().getZone() != Zone.ShamrockMines3) {
                Handler.get().sendMsg("You must use this detonator in Shamrock Mines B3");
                return;
            }
            if ((Integer) Handler.get().getQuest(QuestList.WeDelvedTooDeep).getCheckValue("dynamitePlaced") < 3) {
                Handler.get().sendMsg("You should place a dynamite stick north, east and west of the rock slide before using the detonator.");
            } else {
                Handler.get().sendMsg("You press the button and the dynamite explodes.");
                ShamrockRockslide.hasDetonated = true;
            }
        } else {
            Handler.get().sendMsg("There's no use for this anymore.");
        }
    }
}
