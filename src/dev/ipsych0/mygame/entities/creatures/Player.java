package dev.ipsych0.mygame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.crafting.CraftingUI;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.entities.npcs.ShopKeeper;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.items.ItemStack;
import dev.ipsych0.mygame.items.ItemType;
import dev.ipsych0.mygame.shop.ShopWindow;
import dev.ipsych0.mygame.states.GameState;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.utils.SaveManager;
import dev.ipsych0.mygame.worlds.World;

public class Player extends Creature{
	
	// NPC killcounts
	private int scorpionKC = 0;
	
	// Experience and levels
	private int attackExperience;
	private int attackLevel;
	private int craftingExperience;
	private int craftingLevel;
	private int maxHealth;
	
	public static boolean hasInteracted = false;
	public static boolean worldLoaded = false;
	public static boolean debugButtonPressed = false;
	
	// Walking Animations
	private Animation aDown, aUp, aLeft, aRight, aDefault;
	
	// Attacking Animations
	private Animation attDown, attUp, attLeft, attRight;
	private ArrayList<Projectile> projectiles;
	
	// Last faced direction
	private Direction lastFaced = direction;
	
	// Attack timer
	private long lastAttackTimer, attackCooldown = (long) (600 / getAttackSpeed()), attackTimer = attackCooldown;
	
	// Magic timer
	private long lastMagicTimer, magicCooldown = (long) (300 / getAttackSpeed()), magicTimer = magicCooldown;
	
	// Regeneration timer
	private long lastRegenTimer, regenCooldown = 1000, regenTimer = regenCooldown;

	private boolean movementAllowed = true;
	public static boolean isMoving = false;
	
	public static boolean mouseMoved = false;
	private float xSpawn, ySpawn;
	
	private ShopKeeper shopKeeper;
	private Entity closestEntity;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
		//448, 482
		this.handler = handler;
			
		// Player combat/movement settings:
		setNpc(false);
		
		xSpawn = 5152.0f;
		ySpawn = 5600.0f;
		
		maxHealth = (int) (DEFAULT_HEALTH + Math.round(getVitality() * 1.5));
		health = maxHealth;
		speed = DEFAULT_SPEED + 2.5f;
		
		attackExperience = 0;
		attackLevel = 1;
		craftingExperience = 0;
		craftingLevel = 1;
		
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
		
		attDown = new Animation(333, Assets.player_attackingDown);
		attUp = new Animation(333, Assets.player_attackingUp);
		attLeft = new Animation(333, Assets.player_attackingLeft);
		attRight = new Animation(333, Assets.player_attackingRight);
		
		aDefault = aDown;
		
		projectiles = new ArrayList<Projectile>();
		
	}

	@Override
	public void tick() {
		
		//Movement
		if(movementAllowed) {
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
		
		handler.getGameCamera().centerOnEntity(this);
				
		// Attacks
		regenHealth();
		
		// Debug button for in-game testing
		if(handler.getKeyManager().position && debugButtonPressed){
			handler.sendMsg("X coords: " + Float.toString(getX()) + " Y coords: " + Float.toString(getY()));
//			System.out.println("Current X and Y coordinates are X: " + handler.getWorld().getEntityManager().getPlayer().getX() +" and Y: " + 
//					handler.getWorld().getEntityManager().getPlayer().getY());
			System.out.println("Attack level = " + getAttackLevel());
			System.out.println("Attack XP = " + getAttackExperience());
			System.out.println("Crafting XP = " + getCraftingExperience());
			System.out.println("Crafting level = " + getCraftingLevel());
			
			debugButtonPressed = false;
			
		}
		
		// If space button is pressed
		if(handler.getKeyManager().talk){
			if(!hasInteracted) {
				if(playerIsNearNpc()){
					// And we're close to an NPC interact with it
					closestEntity = getClosestEntity();
					closestEntity.interact();
					hasInteracted = true;
					
					// If the closest Entity is a shop, open the shop
					if(closestEntity.isShop())
						shopKeeper = (ShopKeeper) getClosestEntity();
					
				}
			}
		}
		
		if(closestEntity != null && closestEntity.getChatDialogue() != null) {
			System.out.println("Test");
			closestEntity.getChatDialogue().tick();
		}
		
		// If the shop is open, but we're moving, close it
		if(isMoving) {
			Entity.isCloseToNPC = false;
			hasInteracted = false;
			closestEntity = null;
			if(shopKeeper != null) {
				shopKeeper = null;
			}
			if(closestEntity != null) {
				closestEntity.setChatDialogue(null);
			}
		}
		
		// If there are projectiles, tick them
		if(projectiles.size() != 0) {
			tickProjectiles();
		}
		
		Rectangle mouse = new Rectangle(handler.getWorld().getHandler().getMouseManager().getMouseX(), handler.getWorld().getHandler().getMouseManager().getMouseY(), 1, 1);
		
		// If the mouse is not moved, use the WASD-keys to determine the direction
		if(!mouseMoved) {
			if(xMove < 0)
				lastFaced = Direction.LEFT;
			else if(xMove > 0)
				lastFaced = Direction.RIGHT;
			else if(yMove < 0)
				lastFaced = Direction.UP;
			else if(yMove > 0)
				lastFaced = Direction.DOWN;
			setLastFaced();
		}else {
			// If the mouse IS moved, make the player face the angle of the mouse.
			if(movementAllowed)
				setMouseAngle(x, y, (int) (handler.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset()),
						(int) (handler.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset()));
			setLastFaced();
		}
		
		// If the player is pressing the attack button
		if(handler.getMouseManager().isLeftPressed() || handler.getMouseManager().isLeftPressed() && handler.getMouseManager().isDragged()){
			if(movementAllowed) {
				if(handler.getWorld().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() != null) {
					/*
					 * If the player is wearing a melee weapon, check melee attacks
					 */
					if(handler.getWorld().getEquipment().getEquipmentSlots().get(1).getEquipmentStack().getItem().getItemType() == ItemType.MELEE_WEAPON)
						checkAttacks();
					/*
					 * If the player is wearing a magic weapon, fire magic attacks
					 */
					if(handler.getWorld().getEquipment().getEquipmentSlots().get(1).getEquipmentStack().getItem().getItemType() == ItemType.MAGIC_WEAPON) {
						checkMagic(mouse);
					}
				}
			}
		}
		
	}
	
	/*
	 * Ticks the projectiles of the player
	 */
	private void tickProjectiles() {
		Iterator<Projectile> it = projectiles.iterator();
		Collection<Projectile> deleted = new CopyOnWriteArrayList<Projectile>();
		while(it.hasNext()){
			Projectile p = it.next();
			if(!p.active){
				deleted.add(p);
			}
			for(Entity e : getCurrentMap().getEntityManager().getEntities()) {
				if(e.equals(this)) {
					continue;
				}
				if(p.getCollisionBounds(0, 0).intersects(e.getCollisionBounds(0,0)) && p.active) {
					if(!e.isAttackable()) {
						p.active = false;
					}
					if(e.isAttackable()) {
						e.damage(this, e);
						p.active = false;
					}
				}
			}
			p.tick();
		}
		
		projectiles.removeAll(deleted);
	}
	
	/*
	 * Sets the angle of the mouse
	 */
	public void setMouseAngle(float playerX, float playerY, int mouseX, int mouseY) {
		
		double angle = Math.atan2(mouseY - playerY, mouseX - playerX);
		
		double theta = Math.toDegrees(angle);
		if(theta < 0.0) {
			theta += 360;
		}
		
		if(theta >= 315 && theta < 360 || theta >= 0 && theta < 45) {
			lastFaced = Direction.RIGHT;
		}
		else if(theta >= 45 && theta < 135) {
			lastFaced = Direction.DOWN;
		}
		else if(theta >= 135 && theta < 225) {
			lastFaced = Direction.LEFT;
		}
		else if(theta >= 225 && theta < 315) {
			lastFaced = Direction.UP;
		}
		
	}
	
	/*
	 * Sets the last faced direction, based on last movement
	 */
	public void setLastFaced() {
		if(lastFaced == null){
			aDefault = aDown;
		}
		if(lastFaced == Direction.LEFT){
			aDefault = aLeft;
		}
		else if(lastFaced == Direction.RIGHT){
			aDefault = aRight;
		}
		else if(lastFaced == Direction.DOWN){
			aDefault = aDown;
		}
		else if(lastFaced == Direction.UP){
			aDefault = aUp;
		}
	}
	
	/*
	 * Returns the sprite of the last faced direction
	 */
	public BufferedImage getLastFacedImg() {
		if(lastFaced == null){
			return Assets.player_down[1];
		}
		if(lastFaced == Direction.LEFT){
			return Assets.player_left[1];
		}
		if(lastFaced == Direction.RIGHT){
			return Assets.player_right[1];
		}
		if(lastFaced == Direction.DOWN){
			return Assets.player_down[1];
		}
		if(lastFaced == Direction.UP){
			return Assets.player_up[1];
		}
		return Assets.player_down[1];
	}
	
	@Override
	public void render(Graphics g) {
		if(movementAllowed) {
			g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		}else {
			g.drawImage(getLastFacedImg(), (int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		}
		g.setFont(GameState.myFont);
		
		// UNCOMMENT THIS BLOCK OF CODE TO SHOW THE PLAYER'S COLLISION RECTANGLE IN-GAME
		/*
		g.setColor(Color.RED);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		*/
		g.setColor(Creature.hpColor);
		g.drawString(Integer.toString(getHealth()) + "/" + maxHealth,
				(int) (x - handler.getGameCamera().getxOffset() - 8), (int) (y - handler.getGameCamera().getyOffset() - 8 ));
		
		
		if(projectiles.size() >= 1) {
			for(Projectile p : projectiles) {
				if(active)
					p.render(g);
			}
		}
		
	}
	
	/*
	 * Adds the equipment stats
	 * @param: the item's equipment slot
	 */
	public void addEquipmentStats(int equipSlot) {
		if(equipSlot == 12) {
			// If slotnumber = 12 (unequippable) return
			return;
		}
		if(handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack() != null){

			// Sets the new stats
			setAttackSpeed(getAttackSpeed() + handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getAttackSpeed());
			setVitality(getVitality() + handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getVitality());
			setPower(getPower() + handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getPower());
			setDefence(getDefence() + handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDefence());
			speed += handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getMovementSpeed();
			attackCooldown = (long) (600 / getAttackSpeed());
			magicCooldown = (long) (300 / getAttackSpeed());
			maxHealth = (int) (DEFAULT_HEALTH + Math.round(getVitality() * 1.5));
		}
	}
	
	/*
	 * Removes the equipment stats
	 */
	public void removeEquipmentStats(int equipSlot) {
		if(equipSlot == 12) {
			return;
		}
		if(handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack() != null){
			
			if(getAttackSpeed() - handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getAttackSpeed() < 0) {
				setAttackSpeed(0);
			}else {
				setAttackSpeed(getAttackSpeed() - handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getAttackSpeed());
			}
			
			if(getVitality() - handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getVitality() < 0){
				setVitality(0);
			}else {
				setVitality(getVitality() - handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getVitality());
			}
			
			if(getPower() - handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getPower() < 0) {
				setPower(0);
			}else {
				setPower(getPower() - handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getPower());
			}
			
			if(getDefence() - handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDefence() < 0) {
				setDefence(0);
			}else {
				setDefence(getDefence() - handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDefence());
			}
			/*
			 * TODO: Als ik ooit movement speed reduction conditions wil maken, moet ik deze aanpassen
			 */
			if(speed - handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getMovementSpeed() < 1.0f) {
				speed = 1.0f;
			}else {
				speed -= handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getMovementSpeed();
			}
			
			attackCooldown = (long) (600 / getAttackSpeed());
			magicCooldown = (long) (300 / getAttackSpeed());
			maxHealth = (int) (DEFAULT_HEALTH + Math.round(getVitality() * 1.5));
		}
	}
	
	/*
	 * Regenerates health
	 */
	private void regenHealth() {
		if(health == maxHealth) {
			return;
		}
		
		regenTimer += System.currentTimeMillis() - lastRegenTimer;
		lastRegenTimer = System.currentTimeMillis();
		if(regenTimer < regenCooldown)
			return;
		
		// If current health is higher than your max health value, degenerate health
		if(health > maxHealth) {
			
			health -= 1;
			regenTimer = 0;
		}
		
		// If current health is lower than your max health value, regenerate health
		if(health < maxHealth){
			
			health += 1;
			
			regenTimer = 0;
		}
	}
	
	/*
	 * Check for magic attacks
	 */
	private void checkMagic(Rectangle mouse){
		// Attack timers
		magicTimer += System.currentTimeMillis() - lastMagicTimer;
		lastMagicTimer = System.currentTimeMillis();
		if(magicTimer < magicCooldown)
			return;
		
		if(InventoryWindow.isOpen && handler.getWorld().getInventory().getWindowBounds().contains(mouse) && handler.getMouseManager().isLeftPressed())
			return;
		if(EquipmentWindow.isOpen && handler.getWorld().getEquipment().getWindowBounds().contains(mouse) && handler.getMouseManager().isLeftPressed())
			return;
		if(ChatWindow.chatIsOpen && handler.getWorld().getChatWindow().getWindowBounds().contains(mouse) && handler.getMouseManager().isLeftPressed())
			return;
		if(CraftingUI.isOpen && handler.getWorld().getCraftingUI().getWindowBounds().contains(mouse) && handler.getMouseManager().isLeftPressed())
			return;
		if(ShopWindow.isOpen && handler.getMouseManager().isLeftPressed())
			return;
		
		magicTimer = 0;
		
		if(handler.getMouseManager().isLeftPressed() || handler.getMouseManager().isDragged()) {
			projectiles.add(new Projectile(handler, x, y,
					(int) (handler.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset() - 16),
					(int) (handler.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset() - 16),
					6.0f));
		}
		
	}
	
	/*
	 * Checks melee attacks
	 */
	private void checkAttacks(){
		// Attack timers
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCooldown)
			return;
		
		// Set attack-box
		Rectangle cb = getCollisionBounds(0,0);
		Rectangle ar = new Rectangle();
		int arSize = Creature.DEFAULT_CREATURE_HEIGHT;
		ar.width = arSize;
		ar.height = arSize;
		
		// Attack box setters
		if(lastFaced == Direction.UP && handler.getMouseManager().isLeftPressed()){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
		}
		else if(lastFaced == Direction.DOWN && handler.getMouseManager().isLeftPressed()){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
		}
		else if(lastFaced == Direction.LEFT && handler.getMouseManager().isLeftPressed()){
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2 ;
		}
		else if(lastFaced == Direction.RIGHT && handler.getMouseManager().isLeftPressed()){
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height / 2 - arSize / 2 ;
		}
		else{
			return;
		}
		
		attackTimer = 0;
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			if(!e.isAttackable())
				continue;
			if(e.getCollisionBounds(0, 0).intersects(ar)){
				// TODO: Change damage calculation formula
				e.damage(this, e);
				return;
			}
		}
	}
	
	@Override
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public int getScorpionKC() {
		return scorpionKC;
	}

	public int getAttackExperience() {
		return attackExperience;
	}

	public void setAttackExperience(int attackExperience) {
		this.attackExperience = attackExperience;
	}

	public void addAttackExperience(int attackXP) {
		this.attackExperience = attackExperience + attackXP;
	}

	public int getAttackLevel() {
		// Checks player levels (hard-coded)
		if(getAttackExperience() >= 50 && getAttackExperience() <= 99){
			setAttackLevel(2);
		}
		if(getAttackExperience() >= 100 && getAttackExperience() <= 199){
			setAttackLevel(3);
		}
		if(getAttackExperience() >= 200 && getAttackExperience() <= 449){
			setAttackLevel(4);
		}
		if(getAttackExperience() >= 450 && getAttackExperience() <= 999){
			setAttackLevel(5);
		}
		return attackLevel;
	}

	public void setAttackLevel(int level) {
		attackLevel = level;
	}

	public void addScorpionKC() {
		scorpionKC++;
	}

	@Override
	public void die(){
		System.out.println("You died!");
		// Drop all items
		for(int i = 0; i < handler.getWorld().getInventory().getItemSlots().size(); i++){
			if(handler.getWorld().getInventory().getItemSlots().get(i).getItemStack() == null){
				continue;
			}
			handler.getWorld().getItemManager().addItem(handler.getWorld().getInventory().getItemSlots().get(i).getItemStack().getItem().createNew((int)this.x, (int)this.y, handler.getWorld().getInventory().getItemSlots().get(i).getItemStack().getAmount()));
			handler.getWorld().getInventory().getItemSlots().get(i).setItemStack(null);
		}
		// If we're dragging an item from inventory while dying, drop it too!
		if(handler.getWorld().getInventory().getCurrentSelectedSlot() != null) {
			handler.dropItem(handler.getWorld().getInventory().getCurrentSelectedSlot().getItem(), handler.getWorld().getInventory().getCurrentSelectedSlot().getAmount(), (int) x, (int) y);
			handler.getWorld().getInventory().setCurrentSelectedSlot(null);
			InventoryWindow.hasBeenPressed = false;
			InventoryWindow.itemSelected = false;
		}
		// Drop all equipment
		for(int i = 0; i < handler.getWorld().getEquipment().getEquipmentSlots().size(); i++){
			if(handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack() == null){
				continue;
			}
			handler.getWorld().getItemManager().addItem(handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getItem().createNew((int)this.x, (int)this.y, handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getAmount()));
			removeEquipmentStats(handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getItem().getEquipSlot());
			handler.getWorld().getEquipment().getEquipmentSlots().get(i).setItem(null);
		}
		// If we're dragging an item from equipment while dying, drop it too!
		if(handler.getWorld().getEquipment().getCurrentSelectedSlot() != null) {
			handler.dropItem(handler.getWorld().getEquipment().getCurrentSelectedSlot().getItem(), handler.getWorld().getEquipment().getCurrentSelectedSlot().getAmount(), (int) x, (int) y);
			handler.getWorld().getEquipment().setCurrentSelectedSlot(null);
			EquipmentWindow.hasBeenPressed = false;
			EquipmentWindow.itemSelected = false;
		}
		// If we're dead, respawn
		if(!active){
			this.setActive(true);
			this.setHealth(DEFAULT_HEALTH);
			
			if(SaveManager.variables.size() != 0) {
				handler.setWorld(handler.getWorldHandler().getWorlds().get(Integer.valueOf(SaveManager.variables.get(4))));
				this.setX(Float.parseFloat(SaveManager.variables.get(1)));
				this.setY(Float.parseFloat(SaveManager.variables.get(2)));
			}else {
				handler.setWorld(handler.getWorldHandler().getWorlds().get(0));
				this.setX(xSpawn);
				this.setY(ySpawn);
			}
		}
	}
	
	/*
	 * Handles movement, based on keyboard input (WASD)
	 */
	private void getInput(){
		xMove = 0;
		yMove = 0;
		
		if(handler.getKeyManager().up){
			yMove = -speed;
			direction = Direction.UP;
			setMouseAngle(x, y, (int) (handler.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset()),
					(int) (handler.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset()));
		}
		if(handler.getKeyManager().down){
			yMove = speed;
			direction = Direction.DOWN;
			setMouseAngle(x, y, (int) (handler.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset()),
					(int) (handler.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset()));
		}
		if(handler.getKeyManager().left){
			xMove = -speed;
			direction = Direction.LEFT;
			setMouseAngle(x, y, (int) (handler.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset()),
					(int) (handler.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset()));
		}
		if(handler.getKeyManager().right){
			xMove = speed;
			direction = Direction.RIGHT;
			setMouseAngle(x, y, (int) (handler.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset()),
					(int) (handler.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset()));
		}
	}
	
	/*
	 * Post render for things that should be drawn over other Entities
	 */
	@Override
	public void postRender(Graphics g){
		if(closestEntity != null && closestEntity.getChatDialogue() != null) {
			closestEntity.getChatDialogue().render(g);
		}
	}
	
	/*
	 * Gets the animation based on last faced direction
	 * @returns the animation image
	 */
	private BufferedImage getAnimationByLastFaced(Direction lastFaced) {
		if(lastFaced == Direction.LEFT)
			return aLeft.getCurrentFrame();
		if(lastFaced == Direction.RIGHT)
			return aRight.getCurrentFrame();
		if(lastFaced == Direction.UP)
			return aUp.getCurrentFrame();
		if(lastFaced == Direction.DOWN)
			return aDown.getCurrentFrame();
		
		handler.sendMsg("Can't get lastFaced animation");
		return aDefault.getCurrentFrame();
	}
	
	/*
	 * All movement/attacking animations based on directions
	 * @returns: the respective image
	 */
	private BufferedImage getAnimationDirection() {
		
		/*
		 * Animations for attacking while walking
		 */
		
		if(xMove < 0 && handler.getMouseManager().isLeftPressed()) {
			if(handler.getWorld().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
				return getAnimationByLastFaced(lastFaced);
			else if(lastFaced == Direction.UP)
				return attUp.getCurrentFrame();
			else if(lastFaced == Direction.DOWN)
				return attDown.getCurrentFrame();
			else if(lastFaced == Direction.LEFT)
				return attLeft.getCurrentFrame();
			else if(lastFaced == Direction.RIGHT)
				return attRight.getCurrentFrame();
		}
		else if(xMove > 0 && handler.getMouseManager().isLeftPressed()) {
			if(handler.getWorld().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
				return getAnimationByLastFaced(lastFaced);
			else if(lastFaced == Direction.UP)
				return attUp.getCurrentFrame();
			else if(lastFaced == Direction.DOWN)
				return attDown.getCurrentFrame();
			else if(lastFaced == Direction.LEFT)
				return attLeft.getCurrentFrame();
			else if(lastFaced == Direction.RIGHT)
				return attRight.getCurrentFrame();
		}
		else if(yMove < 0 && handler.getMouseManager().isLeftPressed()) {
			if(handler.getWorld().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
				return getAnimationByLastFaced(lastFaced);
			else if(lastFaced == Direction.UP)
				return attUp.getCurrentFrame();
			else if(lastFaced == Direction.DOWN)
				return attDown.getCurrentFrame();
			else if(lastFaced == Direction.LEFT)
				return attLeft.getCurrentFrame();
			else if(lastFaced == Direction.RIGHT)
				return attRight.getCurrentFrame();
		}
		else if(yMove > 0 && handler.getMouseManager().isLeftPressed()) {
			if(handler.getWorld().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
				return getAnimationByLastFaced(lastFaced);
			else if(lastFaced == Direction.UP)
				return attUp.getCurrentFrame();
			else if(lastFaced == Direction.DOWN)
				return attDown.getCurrentFrame();
			else if(lastFaced == Direction.LEFT)
				return attLeft.getCurrentFrame();
			else if(lastFaced == Direction.RIGHT)
				return attRight.getCurrentFrame();
		}
		
		/*
		 * Animations for walking and moving mouse
		 */
		
		if(xMove < 0) {
			if(lastFaced == Direction.UP)
				return aUp.getCurrentFrame();
			else if(lastFaced == Direction.DOWN)
				return aDown.getCurrentFrame();
			else if(lastFaced == Direction.LEFT)
				return aLeft.getCurrentFrame();
			else if(lastFaced == Direction.RIGHT)
				return aRight.getCurrentFrame();
		}
		else if(xMove > 0) {
			if(lastFaced == Direction.UP)
				return aUp.getCurrentFrame();
			else if(lastFaced == Direction.DOWN)
				return aDown.getCurrentFrame();
			else if(lastFaced == Direction.LEFT)
				return aLeft.getCurrentFrame();
			else if(lastFaced == Direction.RIGHT)
				return aRight.getCurrentFrame();
		}
		else if(yMove < 0) {
			if(lastFaced == Direction.UP)
				return aUp.getCurrentFrame();
			else if(lastFaced == Direction.DOWN)
				return aDown.getCurrentFrame();
			else if(lastFaced == Direction.LEFT)
				return aLeft.getCurrentFrame();
			else if(lastFaced == Direction.RIGHT)
				return aRight.getCurrentFrame();
		}
		else if(yMove > 0) {
			if(lastFaced == Direction.UP)
				return aUp.getCurrentFrame();
			else if(lastFaced == Direction.DOWN)
				return aDown.getCurrentFrame();
			else if(lastFaced == Direction.LEFT)
				return aLeft.getCurrentFrame();
			else if(lastFaced == Direction.RIGHT)
				return aRight.getCurrentFrame();
		}
		
		/*
		 * Attacking animations while idle
		 */
		
		if(lastFaced == Direction.LEFT && handler.getMouseManager().isLeftPressed()) {
			if(handler.getWorld().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
				return aLeft.getDefaultFrame();
			else
				return attLeft.getCurrentFrame();
		}
		else if(lastFaced == Direction.RIGHT && handler.getMouseManager().isLeftPressed()) {
			if(handler.getWorld().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
				return aRight.getDefaultFrame();
			else
				return attRight.getCurrentFrame();
		}
		else if(lastFaced == Direction.UP && handler.getMouseManager().isLeftPressed()) {
			if(handler.getWorld().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
				return aUp.getDefaultFrame();
			else
				return attUp.getCurrentFrame();
		}
		else if(lastFaced == Direction.DOWN && handler.getMouseManager().isLeftPressed()) {
			if(handler.getWorld().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
				return aDown.getDefaultFrame();
			else
				return attDown.getCurrentFrame();
		}
		
		/*
		 * Animations while idle
		 */
		
		if(lastFaced == Direction.LEFT) {
			aDefault = aLeft;
			return aDefault.getDefaultFrame();
		}
		else if(lastFaced == Direction.RIGHT) {
			aDefault = aRight;
			return aDefault.getDefaultFrame();
		}
		else if(lastFaced == Direction.UP) {
			aDefault = aUp;
			return aDefault.getDefaultFrame();
		}
		else if(lastFaced == Direction.DOWN) {
			aDefault = aDown;
			return aDefault.getDefaultFrame();
		}
		
		// Prompt error if none of the above images are loaded (that means apparently none of the above animations could be returned)
		
		handler.sendMsg("Something went wrong loading the player sprite!");
		return Assets.black;
	}
	
	/*
	 * Gets the current frame
	 * @returns the current frame image
	 */
	private BufferedImage getCurrentAnimationFrame(){
		// Walk and Attack animations
		return getAnimationDirection();
	}

	/*
	 * This method should never be called, since the player cannot interact with itself
	 */
	@Override
	public void interact() {
		System.out.println("Oops, we're interacting with ourself. That's odd!");
	}
	
	// Getters & Setters
	
	public World getCurrentMap(){
		return handler.getWorld();
	}

	public boolean isMovementAllowed() {
		return movementAllowed;
	}

	public void setMovementAllowed(boolean movementAllowed) {
		this.movementAllowed = movementAllowed;
	}

	public int getCraftingExperience() {
		return craftingExperience;
	}

	public void setCraftingExperience(int craftingExperience) {
		this.craftingExperience = craftingExperience;
	}

	public int getCraftingLevel() {
		return craftingLevel;
	}

	public void setCraftingLevel(int craftingLevel) {
		this.craftingLevel = craftingLevel;
	}
	
	public void addCraftingExperience(int craftXP) {
		this.craftingExperience = craftingExperience + craftXP;
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
	
	public Rectangle itemPickupRadius() {
		return new Rectangle((int) (x + bounds.x - 48), (int) (y + bounds.y - 48), (bounds.width + 96), (bounds.height + 96));
	}

	public int getMAX_HEALTH() {
		return maxHealth;
	}

	public void setMAX_HEALTH(int mAX_HEALTH) {
		maxHealth = mAX_HEALTH;
	}

}
