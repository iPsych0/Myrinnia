package dev.ipsych0.myrinnia.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Assets;

public abstract class Item implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5417348314768685085L;
	// ItemList
	
	public static final int ITEMWIDTH = 24, ITEMHEIGHT = 24;
	public static Item[] items = new Item[32];
	
	/*
	 * Unequippable Items 
	 */
	public static Item regularLogs = new UnequippableItem(Assets.wood, "Logs", 0, ItemRarity.Common, 5, true, ItemType.CRAFTING_MATERIAL);
	public static Item regularOre = new UnequippableItem(Assets.ore, "Ore", 1, ItemRarity.Uncommon, 5, true, ItemType.CRAFTING_MATERIAL);
	public static Item coins = new UnequippableItem(Assets.coins[0], "Coins", 2, ItemRarity.Rare, -1, true, ItemType.CURRENCY);
	public static Item regularFish = new UnequippableItem(Assets.fishingIcon, "Fish", 7, ItemRarity.Common, 5, true, ItemType.FOOD);
	
	/*
	 * Equippable item
	 */
	public static Item testSword = new EquippableItem(Assets.testSword, "Test Sword", 3, ItemRarity.Unique, EquipSlot.MAINHAND, 5, 0, 2, 0, 0, 10, false, new ItemType[] {ItemType.MELEE_WEAPON}, new ItemRequirement(CharacterStats.Melee, 2));
	public static Item purpleSword = new EquippableItem(Assets.purpleSword, "Purple Sword", 4, ItemRarity.Exquisite, EquipSlot.MAINHAND, 10, 5, 5, 0, 0, 20, false, new ItemType[] {ItemType.MAGIC_WEAPON});
	public static Item testAxe = new EquippableItem(Assets.testAxe, "Test Axe", 5, ItemRarity.Common, EquipSlot.MAINHAND, 5, 0, 0, 0, 0, 10, false, new ItemType[] {ItemType.MELEE_WEAPON, ItemType.AXE});
	public static Item testPickaxe = new EquippableItem(Assets.testPickaxe, "Test Pickaxe", 6, ItemRarity.Common, EquipSlot.MAINHAND, 5, 0, 0, 0, 0, 10, false, new ItemType[] {ItemType.MELEE_WEAPON, ItemType.PICKAXE});
	
	// Class
	
	protected ItemType[] itemTypes;
	protected ItemRarity itemRarity;
	protected ItemRequirement[] requirements;
	protected transient BufferedImage texture;
	protected String name;
	protected final int id;
	protected EquipSlot equipSlot;
	protected int power, defence, vitality;
	protected float attackSpeed, movementSpeed;
	protected int x, y;
	protected Rectangle bounds;
	protected Rectangle position;
	protected int count;
	protected boolean pickedUp = false;
	public static boolean pickUpKeyPressed = false;
	protected int price;
	protected boolean stackable;
	private int respawnTimer = 10800;
	
	public Item(BufferedImage texture, String name, int id, ItemRarity itemRarity, int price, boolean isStackable, ItemType... itemTypes){
		this.texture = texture;
		this.name = name;
		this.id = id;
		this.itemTypes = itemTypes;
		this.itemRarity = itemRarity;
		this.price = price;
		this.stackable = isStackable;
		
		// Prevent duplicate IDs when creating items
		try {
			if(items[id] != null && !name.equals(items[id].name)) {
				throw new DuplicateIDException(name, id);
			}
			else {
				// If the item already exists, don't create a new reference
				if(items[id] == null) {
					items[id] = this;
				}
			}
		}catch(DuplicateIDException exc) {
			exc.printStackTrace();
			System.exit(1);
		}
		bounds = new Rectangle(0, 0, 32, 32);
		position = new Rectangle(0, 0, 32, 32);
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		render(g, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()));
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x + 4, y + 4, ITEMWIDTH, ITEMHEIGHT, null);
	}
	
	/*
	 * Adds a new item equippable item to the world
	 * @params: x,y position and amount
	 */
	public Item createEquippableItem(int x, int y, int count){
		Item i;
		if(this.requirements == null) {
			i = new EquippableItem(texture, name, id, itemRarity, equipSlot, power, defence, vitality, attackSpeed, movementSpeed, price, stackable, itemTypes);
		}else {
			i = new EquippableItem(texture, name, id, itemRarity, equipSlot, power, defence, vitality, attackSpeed, movementSpeed, price, stackable, itemTypes, requirements);
		}
		i.setPosition(x, y);
		
		// If the item is stackable, set the amount
		if(i.stackable)
			i.setCount(count);
		// If the item is unstackable, the count is always 1.
		else
			i.setCount(1);
		return i;
	}
	
	public Item createUnequippableItem(int x, int y, int count){
		Item i = new UnequippableItem(texture, name, id, itemRarity, price, stackable, itemTypes);
		i.setPosition(x, y);
		
		// If the item is stackable, set the amount
		if(i.stackable)
			i.setCount(count);
		// If the item is unstackable, the count is always 1.
		else
			i.setCount(1);
		return i;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Returns the position of the item
	 */
	public Rectangle itemPosition(float xOffset, float yOffset){
		position.setBounds((int)(x + bounds.x + xOffset), (int)(y + bounds.y + yOffset), 32, 32);
		return position;
	}
	
	/*
	 * Item pickup function
	 */
	public boolean pickUpItem (Item item) {
        int inventoryIndex = Handler.get().getInventory().findFreeSlot(item);
        if (inventoryIndex >= 0) {
        	// If we have space
            if(id == item.getId()){
            	if(Handler.get().getInventory().getItemSlots().get(inventoryIndex).addItem(item, item.getCount())) {
	            	Handler.get().sendMsg("Picked up " + item.getCount() + "x " + item.getName());
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

	public int getPower() {
		return power;
	}

	public int getDefence() {
		return defence;
	}

	public int getVitality() {
		return vitality;
	}

	public float getAttackSpeed() {
		return attackSpeed;
	}

	public float getMovementSpeed() {
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
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
		for(ItemType it : itemTypes) {
			if(it == type) {
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