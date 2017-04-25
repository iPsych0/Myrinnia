package dev.ipsych0.mygame.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;

public class Player extends Creature{
	
	// NPC killcounts
	private int scorpionKC = 0;
	
	// Experience and levels
	private int attackExperience;
	private int attackLevel;
	
	// Walking Animations
	private Animation aDown, aUp, aLeft, aRight, aDefault;
	
	// Attacking Animations
	private Animation attDown, attUp, attLeft, attRight;
	
	// Last faced direction
	private Direction lastFaced = direction;
	
	// Attack timer
	private long lastAttackTimer, attackCooldown = 500, attackTimer = attackCooldown;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		// Player combat/movement settings:
		
		speed = Creature.DEFAULT_SPEED + 3.0f;
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
		
		
		//Movement
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
		
		// Attacks
		checkAttacks();
		
		// Player position
		if(handler.getKeyManager().position){
//			handler.getWorld().getChatWindow().sendMessage("X coords: " + Float.toString(handler.getWorld().getEntityManager().getPlayer().getX()) + " Y coords: " + Float.toString(handler.getWorld().getEntityManager().getPlayer().getY()));
//			System.out.println("Current X and Y coordinates are X: " + handler.getWorld().getEntityManager().getPlayer().getX() +" and Y: " + 
//		handler.getWorld().getEntityManager().getPlayer().getY());
//			System.out.println("Attack level = " + getAttackLevel());
//			System.out.println("Attack XP = " + getAttackExperience());
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
				e.damage(attackDamage + (int)(getAttackLevel() * 2.5));
				System.out.println("Damage = " + (int)(getAttackLevel() * 2.5));
				return;
			}
		}
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public int getScorpionKC() {
		return scorpionKC;
	}

	public int getAttackExperience() {
		return attackExperience;
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
		setHealth(100);
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
		g.setFont(Creature.hpFont);
		
		// UNCOMMENT THIS BLOCK OF CODE TO SHOW THE PLAYER'S COLLISION RECTANGLE IN-GAME
		/*
		g.setColor(Color.red);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		*/
		g.setColor(Creature.hpColor);
		g.drawString(Integer.toString(handler.getWorld().getEntityManager().getPlayer().getHealth()),
				(int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() - 8 ));
	}
	
	private BufferedImage getCurrentAnimationFrame(){
		// Walk and Attack animations lol
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

	@Override
	public void interact() {
		System.out.println("Oops, we're interacting with ourself. That's odd!");
	}
	

}
