package dev.ipsych0.mygame.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Assets;

public class Item implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	// ItemList
	
	public static final int ITEMWIDTH = 24, ITEMHEIGHT = 24;
	public static Item[] items = new Item[32];
	public static Item woodItem = new Item(Assets.wood, "Logs", 0, ItemType.CRAFTING_MATERIAL, ItemRarity.Common, 12, 10, 10, 5, 0, 3.0f, 5, true);
	public static Item oreItem = new Item(Assets.ore, "Ore", 1, ItemType.CRAFTING_MATERIAL, ItemRarity.Uncommon, 12, 0, 0 ,0 ,0 ,0, 5, true);
	public static Item coinsItem = new Item(Assets.coins[0], "Coins", 2, ItemType.CURRENCY, ItemRarity.Rare, 12, 0 ,0 ,0 ,0 ,0, -1, true);
	public static Item testSword = new Item(Assets.testSword, "Sword", 3, ItemType.MELEE_WEAPON, ItemRarity.Unique, 1, 11, 9, 10, 0, 0, 10, false);
	public static Item purpleSword = new Item(Assets.purpleSword, "Purple Sword", 4, ItemType.MAGIC_WEAPON, ItemRarity.Exquisite, 1, 15, 5, 10, 0, 0, 20, false);
	
	// Class
	
	protected Handler handler;
	protected ItemType itemType;
	protected ItemRarity itemRarity;
	private transient BufferedImage texture;
	protected String name;
	protected final int id;
	protected final int equipSlot;
	protected final int power, defence, vitality;
	protected final float attackSpeed, movementSpeed;
	protected int x, y;
	protected Rectangle bounds;
	protected int count;
	protected boolean pickedUp = false;
	public static boolean pickUpKeyPressed = false;
	protected int price;
	public boolean stackable = true;
	
	public Item(BufferedImage texture, String name, int id, ItemType itemType, ItemRarity itemRarity, int equipSlot, int power, int defence, int vitality, float attackSpeed, float movementSpeed, int price, boolean isStackable){
		this.texture = texture;
		this.name = name;
		this.id = id;
		this.itemType = itemType;
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
		g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
	}
	
	/*
	 * Adds a new item to the world
	 * @params: x,y position and amount
	 */
	public Item createNew(int x, int y, int count){
		Item i = new Item(texture, name, id, itemType, itemRarity, equipSlot, power, defence, vitality, attackSpeed, movementSpeed, price, stackable);
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
		bounds.x = 1;
		bounds.y = 1;
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
	            	handler.sendMsg("Picked up " + item.getCount() + " " + item.name.toLowerCase() + "s.");
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
		return equipSlot;
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

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
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

}
