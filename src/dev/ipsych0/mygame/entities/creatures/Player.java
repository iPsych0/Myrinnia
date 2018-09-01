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
import dev.ipsych0.mygame.abilityhud.AbilitySlot;
import dev.ipsych0.mygame.bank.BankUI;
import dev.ipsych0.mygame.character.CharacterUI;
import dev.ipsych0.mygame.crafting.CraftingUI;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.entities.npcs.ShopKeeper;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.EquipSlot;
import dev.ipsych0.mygame.items.EquipmentWindow;
import dev.ipsych0.mygame.items.InventoryWindow;
import dev.ipsych0.mygame.items.ItemType;
import dev.ipsych0.mygame.quests.QuestHelpUI;
import dev.ipsych0.mygame.quests.QuestUI;
import dev.ipsych0.mygame.shop.ShopWindow;
import dev.ipsych0.mygame.skills.SkillsList;
import dev.ipsych0.mygame.skills.SkillsOverviewUI;
import dev.ipsych0.mygame.skills.SkillsUI;
import dev.ipsych0.mygame.states.GameState;
import dev.ipsych0.mygame.states.State;
import dev.ipsych0.mygame.states.UITransitionState;
import dev.ipsych0.mygame.utils.Text;
import dev.ipsych0.mygame.worlds.World;
import dev.ipsych0.mygame.worlds.Zone;

public class Player extends Creature{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// NPC killcounts
	private int scorpionKC = 0;
		
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
	private long lastMagicTimer, magicCooldown = (long) (600 / getAttackSpeed()), magicTimer = magicCooldown;
	
	// Regeneration timer
	private long lastRegenTimer, regenCooldown = 1000, regenTimer = regenCooldown;
	
	private int basePower, baseVitality, baseDefence;
	private double levelExponent = 1.1;
	private static boolean isLevelUp = false;
	private int levelUpTimer = 0;
	
	private boolean movementAllowed = true;
	public static boolean isMoving = false;
	
	public static boolean mouseMoved = false;
	private float xSpawn, ySpawn;
	
	private ShopKeeper shopKeeper;
	private Entity closestEntity;
	private Entity bankEntity;
	
	private int alpha = 200;
	private Color playerBoxColour = new Color(0, 255, 0, alpha);
	private boolean isLoaded = false;
	private Zone zone = Zone.Island;
	
	public Player(float x, float y) {
		super(x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
			
		// Player combat/movement settings:
		setNpc(false);
		
		xSpawn = 5152.0f;
		ySpawn = 5600.0f;
		
		basePower = 2;
		baseVitality = 2;
		baseDefence = 2;
		baseDamage = 1;
		
		maxHealth = (int) (DEFAULT_HEALTH + Math.round(vitality * 1.5));
		health = maxHealth;
		speed = DEFAULT_SPEED + 1.0f;
		
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
		
		respawnTimer = 1;
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
		
		if(inCombat) {
			combatTimer++;
		}
		
		if(combatTimer >= 300) {
			inCombat = false;
			combatTimer = 0;
		}
		
		Handler.get().getGameCamera().centerOnEntity(this);
				
		// Attacks
		if(!inCombat) {
			regenHealth();
		}
		
		// Debug button for in-game testing
		if(Handler.get().getKeyManager().position && debugButtonPressed){
			
//			maxHealth = (!Handler.debugMode) ? 10000 : (int) (DEFAULT_HEALTH + Math.round(getVitality() * 1.5));
//			health = (!Handler.debugMode) ? 10000 : (int) (DEFAULT_HEALTH + Math.round(getVitality() * 1.5));
//			
//			Handler.get().sendMsg("X coords: " + Float.toString(getX()) + " Y coords: " + Float.toString(getY()));
//			System.out.println("Current X and Y coordinates are X: " + Handler.get().getWorld().getEntityManager().getPlayer().getX() +" and Y: " + 
//					Handler.get().getWorld().getEntityManager().getPlayer().getY());
//			
//			speed = (speed == 7.0f) ? 2.5f : 7.0f; 
//			power = 250;
//			Handler.debugMode = (Handler.debugMode) ? false : true;
			
			State.setState(new UITransitionState(Handler.get().getGame().pauseState));
			
//			System.out.println("Attack level = " + getAttackLevel());
//			System.out.println("Attack XP = " + getAttackExperience());
//			System.out.println("Crafting XP = " + getCraftingExperience());
//			System.out.println("Crafting level = " + getCraftingLevel());
			
			
//			for(int i = 0; i < Handler.get().getInventory().getItemSlots().size(); i++) {
//				Handler.get().getInventory().getItemSlots().get(i).addItem(Item.testSword, 1);
//			}
			debugButtonPressed = false;
			
		}
		
		// If space button is pressed
		if(Handler.get().getKeyManager().talk){
			if(!hasInteracted) {
				if(playerIsNearNpc()){
					// And we're close to an NPC interact with it
					closestEntity = getClosestEntity();
					if(closestEntity.getChatDialogue() == null) {
						closestEntity.interact();
						hasInteracted = true;
						
						// If the closest Entity is a shop, open the shop
						if(closestEntity.isShop()) {
							shopKeeper = (ShopKeeper) getClosestEntity();
						}
						else if(closestEntity.isBank()) {
							bankEntity = getClosestEntity();
						}
					}else {
						if(closestEntity.getChatDialogue().getMenuOptions().length == 1) {
							closestEntity.interact();
							hasInteracted = true;
						}
					}
				}
			}
		}
		
		Rectangle mouse = new Rectangle(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY(), 1, 1);
		
		// If we're interacting with the closest Entity
		if(closestEntity != null) {
			// And it has a chatdialogue
			if(closestEntity.getChatDialogue() != null) {
				// If the Entity has an option-menu
				if(closestEntity.getChatDialogue().getChosenOption() != null) {
					// And we haven't interacted
					if(!hasInteracted) {
						// And we're still close to it
						if(playerIsNearNpc()) {
							// If we click the menu option, interact with it.
							closestEntity.interact();
							hasInteracted = true;
						}
					}
				// If the Entity only has a continue button (text only) and it's pressed
				}else if(closestEntity.getChatDialogue().getContinueButton() != null && closestEntity.getChatDialogue().getContinueButton().isPressed()) {
					if(!hasInteracted) {
						if(playerIsNearNpc()) {
							// Do the logic and set it to un-pressed and interact
							closestEntity.getChatDialogue().getContinueButton().setPressed(false);
							closestEntity.interact();
							hasInteracted = true;
						}
					}
				}
			}
		}
		
		if(closestEntity != null && closestEntity.getChatDialogue() != null) {
			closestEntity.getChatDialogue().tick();
		}
		
		// If the player moves, close the shop and chat dialogue
		if(closestEntity != null && isMoving) {
			Entity.isCloseToNPC = false;
			hasInteracted = false;

			closestEntity.setChatDialogue(null);
			closestEntity.setSpeakingTurn(closestEntity.getSpeakingCheckpoint());
			closestEntity.interact();
			closestEntity = null;
			
		}
		
		// If there are projectiles, tick them
		if(projectiles.size() > 0) {
			tickProjectiles();
		}
		
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
				setMouseAngle(x, y, (int) (Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset()),
						(int) (Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset()));
			setLastFaced();
		}
		
		// If the player is pressing the attack button
		if(Handler.get().getMouseManager().isLeftPressed() || Handler.get().getMouseManager().isLeftPressed() && Handler.get().getMouseManager().isDragged()){
			if(movementAllowed) {
				if(Handler.get().getEquipment().getEquipmentSlots().get(EquipSlot.MAINHAND.getSlotId()).getEquipmentStack() != null) {
					for(AbilitySlot as : Handler.get().getAbilityManager().getPlayerHUD().getSlottedAbilities()) {
						if(as.getAbility() != null) {
							if(as.getAbility().isChanneling() || as.getAbility().isSelected())
								return;
						}
					}
					//Check melee auto attack
					if(Handler.get().getEquipment().getEquipmentSlots().get(EquipSlot.MAINHAND.getSlotId()).getEquipmentStack().getItem().isType(ItemType.MELEE_WEAPON))
						checkMelee(mouse);
					// Check magic auto attack
					if(Handler.get().getEquipment().getEquipmentSlots().get(EquipSlot.MAINHAND.getSlotId()).getEquipmentStack().getItem().isType(ItemType.MAGIC_WEAPON)) {
						checkMagic(mouse);
					}
				}
			}
		}
		
	}
	
	/*
	 * Ticks the projectiles of the player
	 */
	@Override
	protected void tickProjectiles() {
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
				for(int i = 0; i < Handler.get().getWorld().getLayers().length; i++) {
					if(collisionWithTile((int)((p.getX() + 16) / 32), (int)((p.getY() + 16) / 32)) && p.active) {
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
		Rectangle mouse = new Rectangle(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY(), 1, 1);
		if(movementAllowed) {
			g.drawImage(getCurrentAnimationFrame(mouse), (int) (x - Handler.get().getGameCamera().getxOffset()),
					(int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
		}else {
			g.drawImage(getLastFacedImg(), (int) (x - Handler.get().getGameCamera().getxOffset()),
					(int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
		}
		
		Text.drawString(g, "FPS: " + Handler.get().getGame().getFramesPerSecond(), 4, 160, false, Color.YELLOW, Assets.font14);
		
		// UNCOMMENT THIS BLOCK OF CODE TO SHOW THE PLAYER'S COLLISION RECTANGLE IN-GAME
		
//		g.setColor(Color.RED);
//		g.fillRect((int) (x + bounds.x - Handler.get().getGameCamera().getxOffset()),
//				(int) (y + bounds.y - Handler.get().getGameCamera().getyOffset()), bounds.width, bounds.height);
		
		
		
		// Player box
//		g.setColor(Color.BLACK);
//		g.drawRect((int)(x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height);
		
		// Player item pickup radius
//		g.setColor(Color.BLACK);
//		g.drawRect((int)(itemPickupRadius().x - Handler.get().getGameCamera().getxOffset()), (int) (itemPickupRadius().y - Handler.get().getGameCamera().getyOffset()), itemPickupRadius().width, itemPickupRadius().height);
		
		// Draw HP above head
//		Text.drawString(g, Integer.toString(getHealth()) + "/" + maxHealth,
//				(int) (x - Handler.get().getGameCamera().getxOffset() - 4), (int) (y - Handler.get().getGameCamera().getyOffset() - 8 ), false, Creature.hpColor, GameState.myFont);
		
		//System.out.println((int) ((x) - (x % 16)));
//		
//		g.setColor(playerBoxColour);
//		g.fillRect((int) ((x) - Handler.get().getGameCamera().getxOffset()), (int) ((y) - Handler.get().getGameCamera().getyOffset()), 32, 32);
		
		/* UNCOMMENT THIS TO SEE MELEE HITBOX
		double angle = Math.atan2((Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset() - 16) - y, (Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset() - 16) - x);
		Rectangle ar = new Rectangle((int)(32 * Math.cos(angle) + (int)this.x), (int)(32 * Math.sin(angle) + (int)this.y), 32, 32);
		g.setColor(Color.MAGENTA);
		g.drawRect((int)(ar.x - Handler.get().getGameCamera().getxOffset()), (int)(ar.y - Handler.get().getGameCamera().getyOffset()), ar.width, ar.height);
		 */
				
		if(projectiles.size() > 0) {
			for(Projectile p : projectiles) {
				if(active)
					p.render(g);
			}
		}
		
		if(isLevelUp) {
			levelUpTimer++;
			Text.drawString(g, "Level up!", (int)(x - Handler.get().getGameCamera().getxOffset() + 16), (int)(y - Handler.get().getGameCamera().getyOffset() + 16 - levelUpTimer),
					true, Color.YELLOW, Assets.font32);
			if(levelUpTimer >= 60) {
				levelUpTimer = 0;
				isLevelUp = false;
			}
		}
		
	}
	
	public void levelUpStats() {
		
		isLevelUp = true;
		
//		// Get the old base power
//		int oldBasePower = basePower;
//		int oldBaseVitality = baseVitality;
//		int oldBaseDefence = baseDefence;
//		
//		// Every level, formula is: Exponent (1.1) * 0.9985
//		basePower = (int) Math.ceil(basePower * levelExponent) + 1;
//		baseVitality = (int) Math.ceil(baseVitality * levelExponent) + 1;
//		baseDefence = (int) Math.ceil(baseDefence * levelExponent) + 1;
		
		this.levelExponent *= 0.9985;
		
		this.baseDamage = (int) Math.ceil(baseDamage * levelExponent) + 1;
//		this.power += (basePower - oldBasePower);
//		this.vitality += (baseVitality - oldBaseVitality);
//		this.defence += (baseDefence - oldBaseDefence);
		
		this.maxHealth = (int) (DEFAULT_HEALTH + Math.round(vitality * 1.5));
		this.health = maxHealth;
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
		if(Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack() != null){

			// Sets the new stats
			attackSpeed += Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getAttackSpeed();
			vitality += Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getVitality();
			power += Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getPower();
			defence += Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDefence();
			speed += Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getMovementSpeed();
			attackCooldown = (long) (600 / attackSpeed);
			magicCooldown = (long) (600 / attackSpeed);
			int previousMaxHP = maxHealth;
			maxHealth = (int) (DEFAULT_HEALTH + Math.round(vitality * 1.5));
			if(health == previousMaxHP) {
				health = maxHealth;
			}
		}
	}
	
	/*
	 * Removes the equipment stats
	 */
	public void removeEquipmentStats(int equipSlot) {
		if(equipSlot == 12) {
			return;
		}
		if(Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack() != null){
			
			if(getAttackSpeed() - Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getAttackSpeed() < 0) {
				setAttackSpeed(0);
			}else {
				attackSpeed -= Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getAttackSpeed();
			}
			
			if(getVitality() - Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getVitality() < 0){
				setVitality(0);
			}else {
				vitality -= Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getVitality();
			}
			
			if(getPower() - Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getPower() < 0) {
				setPower(0);
			}else {
				power -= Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getPower();
			}
			
			if(getDefence() - Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDefence() < 0) {
				setDefence(0);
			}else {
				defence -= Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getDefence();
			}
			/*
			 * TODO: Als ik ooit movement speed reduction conditions wil maken, moet ik deze aanpassen
			 */
			if(speed - Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getMovementSpeed() < 1.0f) {
				speed = 1.0f;
			}else {
				speed -= Handler.get().getEquipment().getEquipmentSlots().get(equipSlot).getEquipmentStack().getItem().getMovementSpeed();
			}
			
			attackCooldown = (long) (600 / attackSpeed);
			magicCooldown = (long) (600 / attackSpeed);
			int previousMaxHP = maxHealth;
			maxHealth = (int) (DEFAULT_HEALTH + Math.round(vitality * 1.5));
			if(health >= previousMaxHP) {
				health = maxHealth;
			}
		}
	}
	
	/**
	 * Damage formula
	 */
//	@Override
//	public int getDamage(Entity dealer) {
//		// Default damage formula
//		Creature c = (Creature) dealer;
//		return (int) Math.floor((c.getBaseDamage() + c.getPower() / 2));
//	}
	
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
	
	/**
	 *  Checks if the mouse is left-clicked within a UI window
	 * @param mouse - mouse coordinates
	 * @return true if within window, false if not
	 */
	public boolean hasLeftClickedUI(Rectangle mouse) {
		if(InventoryWindow.isOpen && Handler.get().getInventory().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
			return true;
		if(EquipmentWindow.isOpen && Handler.get().getEquipment().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
			return true;
		if(ChatWindow.chatIsOpen && Handler.get().getChatWindow().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
			return true;
		if(CraftingUI.isOpen && Handler.get().getCraftingUI().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
			return true;
		if(ShopWindow.isOpen && Handler.get().getMouseManager().isLeftPressed())
			return true;
		if(BankUI.isOpen && Handler.get().getMouseManager().isLeftPressed())
			return true;
		if(QuestUI.isOpen && Handler.get().getQuestManager().getQuestUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
			return true;
		if(QuestHelpUI.isOpen && Handler.get().getQuestManager().getQuestUI().getQuestHelpUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
			return true;
		if(SkillsUI.isOpen && Handler.get().getSkillsUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
			return true;
		if(SkillsOverviewUI.isOpen && Handler.get().getSkillsUI().getOverviewUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
			return true;
		if(CharacterUI.isOpen && Handler.get().getCharacterUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
			return true;
		if(Handler.get().getHpOverlay().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
			return true;
		if(Handler.get().getAbilityManager().getPlayerHUD().getBounds().contains(mouse) && Handler.get().getMouseManager().isLeftPressed())
			return true;
		
		// If the mouse is not clicked in one of the UI windows, return false
		return false;
	}
	
	/**
	 *  Checks if the mouse is right-clicked within a UI window
	 * @param mouse - mouse coordinates
	 * @return true if within window, false if not
	 */
	public boolean hasRightClickedUI(Rectangle mouse) {
		if(InventoryWindow.isOpen && Handler.get().getInventory().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
			return true;
		if(EquipmentWindow.isOpen && Handler.get().getEquipment().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
			return true;
		if(ChatWindow.chatIsOpen && Handler.get().getChatWindow().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
			return true;
		if(CraftingUI.isOpen && Handler.get().getCraftingUI().getWindowBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
			return true;
		if(ShopWindow.isOpen && Handler.get().getMouseManager().isRightPressed())
			return true;
		if(BankUI.isOpen && Handler.get().getMouseManager().isRightPressed())
			return true;
		if(QuestUI.isOpen && Handler.get().getQuestManager().getQuestUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
			return true;
		if(QuestHelpUI.isOpen && Handler.get().getQuestManager().getQuestUI().getQuestHelpUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
			return true;
		if(SkillsUI.isOpen && Handler.get().getSkillsUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
			return true;
		if(SkillsOverviewUI.isOpen && Handler.get().getSkillsUI().getOverviewUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
			return true;
		if(CharacterUI.isOpen && Handler.get().getCharacterUI().getBounds().contains(mouse) && Handler.get().getMouseManager().isRightPressed())
			return true;

		
		// If the mouse is not clicked in one of the UI windows, return false
		return false;
	}
	
	/*
	 * Check for magic attacks
	 */
	public void checkMagic(Rectangle mouse){
		// Attack timers
		magicTimer += System.currentTimeMillis() - lastMagicTimer;
		lastMagicTimer = System.currentTimeMillis();
		if(magicTimer < magicCooldown)
			return;
		
		if(hasLeftClickedUI(mouse))
			return;
		
		magicTimer = 0;
		
		Handler.get().playEffect("fireball.wav", 0, 0);
		if(Handler.get().getMouseManager().isLeftPressed() || Handler.get().getMouseManager().isDragged()) {
			projectiles.add(new Projectile(x, y,
					(int) (mouse.getX() + Handler.get().getGameCamera().getxOffset() - 16),
					(int) (mouse.getY() + Handler.get().getGameCamera().getyOffset() - 16),
					9.0f));
		}
		
	}
	
	/*
	 * Checks melee attacks
	 */
	public void checkMelee(Rectangle mouse){
		// Attack timers
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCooldown)
			return;
		
		if(hasLeftClickedUI(mouse))
			return;
		
		attackTimer = 0;

		if(Handler.get().getMouseManager().isLeftPressed() || Handler.get().getMouseManager().isDragged()) {
			double angle = Math.atan2((mouse.getY() + Handler.get().getGameCamera().getyOffset() - 16) - y, (mouse.getX() + Handler.get().getGameCamera().getxOffset() - 16) - x);
			Rectangle ar = new Rectangle((int)(32 * Math.cos(angle) + (int)this.x), (int)(32 * Math.sin(angle) + (int)this.y), 32, 32);
			
			for(Entity e : Handler.get().getWorld().getEntityManager().getEntities()){
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
	}
	
	@Override
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public int getScorpionKC() {
		return scorpionKC;
	}

	public void addScorpionKC() {
		scorpionKC++;
	}

	@Override
	public void die(){
		System.out.println("You died!");
		// Drop all items
		for(int i = 0; i < Handler.get().getInventory().getItemSlots().size(); i++){
			if(Handler.get().getInventory().getItemSlots().get(i).getItemStack() == null){
				continue;
			}
			Handler.get().dropItem(Handler.get().getInventory().getItemSlots().get(i).getItemStack().getItem(),
					Handler.get().getInventory().getItemSlots().get(i).getItemStack().getAmount(), (int)x, (int)y);
			Handler.get().getInventory().getItemSlots().get(i).setItemStack(null);
		}
		// If we're dragging an item from inventory while dying, drop it too!
		if(Handler.get().getInventory().getCurrentSelectedSlot() != null) {
			Handler.get().dropItem(Handler.get().getInventory().getCurrentSelectedSlot().getItem(), Handler.get().getInventory().getCurrentSelectedSlot().getAmount(), (int) x, (int) y);
			Handler.get().getInventory().setCurrentSelectedSlot(null);
			InventoryWindow.hasBeenPressed = false;
			InventoryWindow.itemSelected = false;
		}
		// Drop all equipment
		for(int i = 0; i < Handler.get().getEquipment().getEquipmentSlots().size(); i++){
			if(Handler.get().getEquipment().getEquipmentSlots().get(i).getEquipmentStack() == null){
				continue;
			}
			Handler.get().dropItem(Handler.get().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getItem(),
					Handler.get().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getAmount(), (int)x, (int)y);
			removeEquipmentStats(Handler.get().getEquipment().getEquipmentSlots().get(i).getEquipmentStack().getItem().getEquipSlot());
			Handler.get().getEquipment().getEquipmentSlots().get(i).setItem(null);
		}
		// If we're dragging an item from equipment while dying, drop it too!
		if(Handler.get().getEquipment().getCurrentSelectedSlot() != null) {
			Handler.get().dropItem(Handler.get().getEquipment().getCurrentSelectedSlot().getItem(), Handler.get().getEquipment().getCurrentSelectedSlot().getAmount(), (int) x, (int) y);
			Handler.get().getEquipment().setCurrentSelectedSlot(null);
			EquipmentWindow.hasBeenPressed = false;
			EquipmentWindow.itemSelected = false;
		}
		
		// If we're dead, respawn
		if(!active){
			this.setActive(true);
			this.setHealth(maxHealth);
			
			Handler.get().setWorld(Handler.get().getWorldHandler().getWorlds().get(0));
			this.setX(xSpawn);
			this.setY(ySpawn);
		}
	}
	
	/*
	 * Handles movement, based on keyboard input (WASD)
	 */
	private void getInput(){
		xMove = 0;
		yMove = 0;
		
		if(Handler.get().getKeyManager().up){
			yMove = -speed;
			direction = Direction.UP;
			setMouseAngle(x, y, (int) (Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset()),
					(int) (Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset()));
		}
		if(Handler.get().getKeyManager().down){
			yMove = speed;
			direction = Direction.DOWN;
			setMouseAngle(x, y, (int) (Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset()),
					(int) (Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset()));
		}
		if(Handler.get().getKeyManager().left){
			xMove = -speed;
			direction = Direction.LEFT;
			setMouseAngle(x, y, (int) (Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset()),
					(int) (Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset()));
		}
		if(Handler.get().getKeyManager().right){
			xMove = speed;
			direction = Direction.RIGHT;
			setMouseAngle(x, y, (int) (Handler.get().getMouseManager().getMouseX() + Handler.get().getGameCamera().getxOffset()),
					(int) (Handler.get().getMouseManager().getMouseY() + Handler.get().getGameCamera().getyOffset()));
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
		
		System.out.println("Can't get the last faced animation frame");
		return aDefault.getCurrentFrame();
	}
	
	/*
	 * All movement/attacking animations based on directions
	 * @returns: the respective image
	 */
	private BufferedImage getAnimationDirection(Rectangle mouse) {
		
		/*
		 * Animations for attacking while walking
		 */
		
		if(xMove < 0 && Handler.get().getMouseManager().isLeftPressed()) {
			if(hasLeftClickedUI(mouse))
				return getAnimationByLastFaced(lastFaced);
			if(Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
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
		else if(xMove > 0 && Handler.get().getMouseManager().isLeftPressed()) {
			if(hasLeftClickedUI(mouse))
				return getAnimationByLastFaced(lastFaced);
			if(Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
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
		else if(yMove < 0 && Handler.get().getMouseManager().isLeftPressed()) {
			if(hasLeftClickedUI(mouse))
				return getAnimationByLastFaced(lastFaced);
			if(Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
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
		else if(yMove > 0 && Handler.get().getMouseManager().isLeftPressed()) {
			if(hasLeftClickedUI(mouse))
				return getAnimationByLastFaced(lastFaced);
			if(Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
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
		
		if(lastFaced == Direction.LEFT && Handler.get().getMouseManager().isLeftPressed()) {
			if(hasLeftClickedUI(mouse))
				return aLeft.getDefaultFrame();
			if(Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
				return aLeft.getDefaultFrame();
			else
				return attLeft.getCurrentFrame();
		}
		else if(lastFaced == Direction.RIGHT && Handler.get().getMouseManager().isLeftPressed()) {
			if(hasLeftClickedUI(mouse))
				return aRight.getDefaultFrame();
			if(Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
				return aRight.getDefaultFrame();
			else
				return attRight.getCurrentFrame();
		}
		else if(lastFaced == Direction.UP && Handler.get().getMouseManager().isLeftPressed()) {
			if(hasLeftClickedUI(mouse))
				return aUp.getDefaultFrame();
			if(Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
				return aUp.getDefaultFrame();
			else
				return attUp.getCurrentFrame();
		}
		else if(lastFaced == Direction.DOWN && Handler.get().getMouseManager().isLeftPressed()) {
			if(hasLeftClickedUI(mouse))
				return aDown.getDefaultFrame();
			if(Handler.get().getEquipment().getEquipmentSlots().get(1).getEquipmentStack() == null)
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
	
		// If lastFaced is null, return black tile
		return Assets.black;
	}
	
	/*
	 * Gets the current frame
	 * @returns the current frame image
	 */
	private BufferedImage getCurrentAnimationFrame(Rectangle mouse){
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
	
	public World getCurrentMap(){
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
	
	public Entity getBankEntity() {
		return bankEntity;
	}

	public void setBankEntity(Entity bankEntity) {
		this.bankEntity = bankEntity;
	}

	public Rectangle itemPickupRadius() {
		return new Rectangle((int) (x + bounds.x - 24), (int) (y + bounds.y - 24), (bounds.width + 40), (bounds.height + 36));
	}

	@Override
	public void respawn() {
		
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

}
