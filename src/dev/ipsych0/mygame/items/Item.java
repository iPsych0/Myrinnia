package dev.ipsych0.mygame.items;

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

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;

public class Item implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	// ItemList
	
	public static final int ITEMWIDTH = 24, ITEMHEIGHT = 24;
	public static Item[] items = new Item[32];
	public static Item woodItem = new Item(Assets.wood, "Logs", 0, ItemRarity.Common, EquipSlot.NONE, 10, 10, 5, 0, 3.0f, 5, true, ItemType.CRAFTING_MATERIAL);
	public static Item oreItem = new Item(Assets.ore, "Ore", 1, ItemRarity.Uncommon, EquipSlot.NONE, 0, 0 ,0 ,0 ,0, 5, true, ItemType.CRAFTING_MATERIAL);
	public static Item coinsItem = new Item(Assets.coins[0], "Coins", 2, ItemRarity.Rare, EquipSlot.NONE, 0 ,0 ,0 ,0 ,0, -1, true, ItemType.CURRENCY);
	public static Item testSword = new Item(Assets.testSword, "Test Sword", 3, ItemRarity.Unique, EquipSlot.MAINHAND, 11, 9, 10, 0, 0, 10, false, ItemType.MELEE_WEAPON);
	public static Item purpleSword = new Item(Assets.purpleSword, "Purple Sword", 4, ItemRarity.Exquisite, EquipSlot.MAINHAND, 15, 5, 10, 0, 0, 20, false, ItemType.MAGIC_WEAPON);
	public static Item testAxe = new Item(Assets.testAxe, "Test Axe", 5, ItemRarity.Common, EquipSlot.MAINHAND, 5, 0, 0, 0, 0, 10, false, ItemType.MELEE_WEAPON, ItemType.AXE);
	public static Item testPickaxe = new Item(Assets.testPickaxe, "Test Pickaxe", 6, ItemRarity.Common, EquipSlot.MAINHAND, 5, 0, 0, 0, 0, 10, false, ItemType.MELEE_WEAPON, ItemType.PICKAXE);
	
	// Class
	
	private Handler handler;
	private ItemType[] itemTypes;
	private ItemRarity itemRarity;
	private transient BufferedImage texture;
	private String name;
	private final int id;
	private final EquipSlot equipSlot;
	private final int power, defence, vitality;
	private final float attackSpeed, movementSpeed;
	private int x, y;
	private Rectangle bounds;
	private int count;
	private boolean pickedUp = false;
	public static boolean pickUpKeyPressed = false;
	private int price;
	private boolean stackable;
	
	public Item(BufferedImage texture, String name, int id, ItemRarity itemRarity, EquipSlot equipSlot, int power, int defence, int vitality, float attackSpeed, float movementSpeed, int price, boolean isStackable, ItemType... itemTypes){
		this.texture = texture;
		this.name = name;
		this.id = id;
		this.itemTypes = itemTypes;
		this.itemRarity = itemRarity;
		this.equipSlot = equipSlot;
		this.power = power;
		this.defence = defence;
		this.vitality = vitality;
		this.attackSpeed = attackSpeed;
		this.movementSpeed = movementSpeed;
		this.price = price;
		this.stackable = isStackable;
		
		items[id] = this;
		bounds = new Rectangle(0, 0, ITEMWIDTH, ITEMHEIGHT);
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		if(handler == null)
			return;
		render(g, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x + 4, y + 4, ITEMWIDTH, ITEMHEIGHT, null);
	}
	
	/*
	 * Adds a new item to the world
	 * @params: x,y position and amount
	 */
	public Item createNew(int x, int y, int count){
		Item i = new Item(texture, name, id, itemRarity, equipSlot, power, defence, vitality, attackSpeed, movementSpeed, price, stackable, itemTypes);
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
		//
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 32;
		bounds.height = 32;
		
		Rectangle ir = new Rectangle();
		int arSize = ITEMWIDTH;
		ir.width = arSize;
		ir.height = arSize;
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	/*
	 * Item pickup function
	 */
	public boolean pickUpItem (Item item) {
        int inventoryIndex = handler.getInventory().findFreeSlot(item);
        if (inventoryIndex >= 0) {
        	// If we have space
            if(id == item.getId()){
            	if(handler.getInventory().getItemSlots().get(inventoryIndex).addItem(item, item.getCount())) {
	            	handler.sendMsg("Picked up " + item.getCount() + "x " + item.getName());
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

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

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
	
	private void writeObject(ObjectOutputStream out) throws IOException {
	    out.defaultWriteObject();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ImageIO.write(this.texture, "png", buffer);

        out.writeInt(buffer.size()); // Prepend image with byte count
        buffer.writeTo(out);         // Write image
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
	    in.defaultReadObject();

        int size = in.readInt(); // Read byte count
        byte[] buffer = new byte[size];
        in.readFully(buffer); // Make sure you read all bytes of the image

        this.texture = ImageIO.read(new ByteArrayInputStream(buffer));
    }

}
