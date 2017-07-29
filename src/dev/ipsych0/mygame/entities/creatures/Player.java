package dev.ipsych0.mygame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.entities.statics.Whirlpool;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.gfx.Text;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.items.ItemSlot;
import dev.ipsych0.mygame.states.GameState;
import dev.ipsych0.mygame.tiles.Tiles;
import dev.ipsych0.mygame.worlds.World;

public class Player extends Creature{
	
	// NPC killcounts
	private int scorpionKC = 0;
	
	// Experience and levels
	private int attackExperience;
	private int attackLevel;
	private int MAX_HEALTH;
	
	public static boolean hasInteracted = false;
	public static boolean worldLoaded = false;
	
	// Walking Animations
	private Animation aDown, aUp, aLeft, aRight, aDefault;
	
	// Attacking Animations
	private Animation attDown, attUp, attLeft, attRight;
	
	// Last faced direction
	private Direction lastFaced = direction;
	
	// Attack timer
	private long lastAttackTimer, attackCooldown = (long) (600 / getAttackSpeed()), attackTimer = attackCooldown;
	
	private long lastRegenTimer, regenCooldown = 1000, regenTimer = regenCooldown;

	private boolean movementAllowed = true;
	
	// NPC ChatWindow
	
	private ChatWindow chatWindow;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
		//448, 482
		this.handler = handler;
			
		// Player combat/movement settings:
		setNpc(false);
		
		chatWindow = new ChatWindow(handler, 0, 608); //228,314
		chatWindow.sendMessage("Welcome back!");
		
		health = DEFAULT_HEALTH;
		
		speed = DEFAULT_SPEED + 2.5f;
		attackExperience = 0;
		attackLevel = 1;
		
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
		
		aDefault = new Animation(250, Assets.player_down);
	}

	@Override
	public void tick() {
		if(lastFaced == null){
			aDefault = new Animation(250, Assets.player_down);
		}
		if(lastFaced == Direction.LEFT){
			aDefault = new Animation(250, Assets.player_left);
		}
		if(lastFaced == Direction.RIGHT){
			aDefault = new Animation(250, Assets.player_right);
		}
		if(lastFaced == Direction.DOWN){
			aDefault = new Animation(250, Assets.player_down);
		}
		if(lastFaced == Direction.UP){
			aDefault = new Animation(250, Assets.player_up);
		}
		
		if(this.getCollisionBounds(0,0).intersects((handler.getWorld().getTile((int) x, (int) y).tilePosition(0, 0)))){
			setHealth(50);
		}

		//Animations
		aDefault.tick();
		aDown.tick();
		aUp.tick();
		aLeft.tick();
		aRight.tick();
		attDown.tick();
		attUp.tick();
		attLeft.tick();
		attRight.tick();
		
		// Chat
		chatWindow.tick();
		
		// Stat updates
		MAX_HEALTH = (int) (DEFAULT_HEALTH + Math.round(getVitality() * 1.5));
		attackCooldown = (long) (600 / getAttackSpeed());
		
		
		//Movement
		if(movementAllowed) {
			getInput();
			move();
		}
		handler.getGameCamera().centerOnEntity(this);
		
		// Attacks
		regenHealth();
		checkAttacks();
		
		// Player position
		if(handler.getKeyManager().position){
			getChatWindow().sendMessage("X coords: " + Float.toString(getX()) + " Y coords: " + Float.toString(getY()));
//			System.out.println("Current X and Y coordinates are X: " + handler.getWorld().getEntityManager().getPlayer().getX() +" and Y: " + 
//					handler.getWorld().getEntityManager().getPlayer().getY());
			System.out.println("Attack level = " + getAttackLevel());
			System.out.println("Attack XP = " + getAttackExperience());
			
		}
		if(handler.getKeyManager().talk && ChatWindow.chatIsOpen){
				if(!hasInteracted && playerIsNearNpc()){
					Entity.isCloseToNPC = true;
					hasInteracted = true;
					return;
				}
		}
		
		
	}
	
	public void addEquipmentStats(int equipSlot) {
		if(equipSlot == 12) {
			return;
		}
		if(handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack() != null){

			setAttackSpeed(getAttackSpeed() + handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getAttackSpeed());
			setVitality(getVitality() + handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getVitality());
			setPower(getPower() + handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getPower());
			setDefence(getDefence() + handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDefence());
			speed += handler.getWorld().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getMovementSpeed();
		}
		
//		for(int i = 0; i < handler.getWorld().getEquipment().getEquipmentSlots().size(); i++) {
//			System.out.println("Iteration: " + i);
//			if(handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack() != null){
//				System.out.println("Item at index " + i + " is: " + handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getItem().getName());
//				attackSpeed += handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getItem().getAttackSpeed();
//				vitality += handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getItem().getVitality();
//				power += handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getItem().getPower();
//				defence += handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getItem().getDefence();
//				speed += handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getItem().getMovementSpeed();
//			}else {
//				continue;
//			}
//		}
	}
	
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
		}
	}
	
	private void regenHealth() {
		if(health == MAX_HEALTH) {
			return;
		}
		
		if(health > MAX_HEALTH) {
			
			regenTimer += System.currentTimeMillis() - lastRegenTimer;
			lastRegenTimer = System.currentTimeMillis();
			if(regenTimer < regenCooldown)
				return;
			
			health -= 1;
			regenTimer = 0;
		}
		if(health < MAX_HEALTH){
		
			regenTimer += System.currentTimeMillis() - lastRegenTimer;
			lastRegenTimer = System.currentTimeMillis();
			if(regenTimer < regenCooldown)
				return;
			
			health += 1;
			
			regenTimer = 0;
		}
	}
	
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
		if(lastFaced == Direction.UP && handler.getKeyManager().attack){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
		}
		else if(lastFaced == Direction.DOWN && handler.getKeyManager().attack){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
		}
		else if(lastFaced == Direction.LEFT && handler.getKeyManager().attack){
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2 ;
		}
		else if(lastFaced == Direction.RIGHT && handler.getKeyManager().attack){
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
				e.damage(baseDamage + (int)(getPower() * 3));
				System.out.println("Damage = " + (baseDamage + (int) getPower() * 3));
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
		for(int i = 0; i < handler.getWorld().getInventory().getItemSlots().size(); i++){
			if(handler.getWorld().getInventory().getItemSlots().get(i).getItemStack() == null){
				continue;
			}
			handler.getWorld().getItemManager().addItem(handler.getWorld().getInventory().getItemSlots().get(i).getItemStack().getItem().createNew((int)this.x, (int)this.y, handler.getWorld().getInventory().getItemSlots().get(i).getItemStack().getAmount()));
			handler.getWorld().getInventory().getItemSlots().get(i).setItemStack(null);
		}
		for(int i = 0; i < handler.getWorld().getEquipment().getEquipmentSlots().size(); i++){
			if(handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack() == null){
				continue;
			}
			handler.getWorld().getItemManager().addItem(handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getItem().createNew((int)this.x, (int)this.y, handler.getWorld().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getAmount()));
			handler.getWorld().getEquipment().getEquipmentSlots().get(i).setItem(null);
		}
		if(!active){
			this.setActive(true);
			setHealth(DEFAULT_HEALTH);
			this.setX(256);
			this.setY(160);
		}
	}
	
	private void getInput(){
		xMove = 0;
		yMove = 0;
		
		if(handler.getKeyManager().up){
			yMove = -speed;
			direction = Direction.UP;
			lastFaced = Direction.UP;
		}
		if(handler.getKeyManager().down){
			yMove = speed;
			direction = Direction.DOWN;
			lastFaced = Direction.DOWN;
		}
		if(handler.getKeyManager().left){
			xMove = -speed;
			direction = Direction.LEFT;
			lastFaced = Direction.LEFT;
		}
		if(handler.getKeyManager().right){
			xMove = speed;
			direction = Direction.RIGHT;
			lastFaced = Direction.RIGHT;
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		g.setFont(GameState.myFont);
		
		g.setColor(Color.RED);
		// UNCOMMENT THIS BLOCK OF CODE TO SHOW THE PLAYER'S COLLISION RECTANGLE IN-GAME
		/*
		g.setColor(Color.red);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		*/
		g.setColor(Creature.hpColor);
		g.drawString(Integer.toString(handler.getWorld().getEntityManager().getPlayer().getHealth()) + "/" + handler.getWorld().getEntityManager().getPlayer().MAX_HEALTH,
				(int) (x - handler.getGameCamera().getxOffset() - 8), (int) (y - handler.getGameCamera().getyOffset() - 8 ));
		
		g.setFont(Assets.font14);
		g.drawImage(Assets.hpOverlay, 0, 0, 292, 96, null);
		g.drawString("HP: " + Double.toString(Handler.roundOff((double)health / (double)MAX_HEALTH * 100, 2)) + "%", 146, 34);
		
		Text.drawString(g, "Lv. ", 36, 28, false, Color.YELLOW, Assets.font20);
		Text.drawString(g, Integer.toString(getAttackLevel()), 42, 64, true, Color.YELLOW, Assets.font32);
		
	}
	
	public void postRender(Graphics g){
		chatWindow.render(g);
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			g.setFont(GameState.myFont);
			e.drawDamage(g);
		}
		if(Whirlpool.isFishing) {
			g.setColor(Color.WHITE);
			g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() - 32 ), width, height);
			g.setColor(Color.BLACK);
			g.drawRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() - 32 ), width, height);
			g.drawImage(Assets.fish, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() - 32 ), width, height, null);
		}
	}
	
	private BufferedImage getCurrentAnimationFrame(){
		// Walk and Attack animations
		if(lastFaced == Direction.LEFT){
			if(handler.getKeyManager().attack){
				return attLeft.getCurrentFrame();
			}
		}
		if(xMove < 0){
			return aLeft.getCurrentFrame();
		}
		if(lastFaced == Direction.RIGHT){
			if(handler.getKeyManager().attack){
				return attRight.getCurrentFrame();
			}
		}
		if(xMove > 0){
			return aRight.getCurrentFrame();
		}
		if(lastFaced == Direction.DOWN){
			if(handler.getKeyManager().attack){
				return attDown.getCurrentFrame();
			}
		}
		if(yMove > 0){
			return aDown.getCurrentFrame();
		}
		if(lastFaced == Direction.UP){
			if(handler.getKeyManager().attack){
				return attUp.getCurrentFrame();
			}
		}
		if(yMove < 0){
			return aUp.getCurrentFrame();
		}
		
		return aDefault.getDefaultFrame();
	}
	
	public boolean hasItem(Item item, int quantity){
		for(ItemSlot is : handler.getWorld().getEntityManager().getPlayer().handler.getWorld().getInventory().getItemSlots()){
			if(is.getItemStack() == null){
				continue;
			}
			if(is.getItemStack().getItem().getName() == item.getName() && is.getItemStack().getAmount() >= quantity){
				return true;
			}
		}
		return false;
	}

	@Override
	public void interact() {
		System.out.println("Oops, we're interacting with ourself. That's odd!");
	}
	
	public World getCurrentMap(){
		return handler.getWorld();
		
	}

	public ChatWindow getChatWindow() {
		return chatWindow;
	}

	public void setChatWindow(ChatWindow chatWindow) {
		this.chatWindow = chatWindow;
	}

	public boolean isMovementAllowed() {
		return movementAllowed;
	}

	public void setMovementAllowed(boolean movementAllowed) {
		this.movementAllowed = movementAllowed;
	}

}
