package dev.ipsych0.mygame.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;

public class Player extends Creature{
	
	// Walking Animations
	private Animation aDown, aUp, aLeft, aRight, aDefault;
	
	// Attacking Animations
	private Animation attDown, attUp, attLeft, attRight;
	
	// Last faced direction
	private Direction lastFaced = direction;
	
	// Attack timer
	private long lastAttackTimer, attackCooldown = 600, attackTimer = attackCooldown;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		// Player combat/movement settings:
		
		speed = Creature.DEFAULT_SPEED + 2.0f;
		
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
		aDown.tick();
		aUp.tick();
		aLeft.tick();
		aRight.tick();
		aDefault.tick();
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
			System.out.println("Current X and Y coordinates are X: " + handler.getWorld().getEntityManager().getPlayer().getX() +" and Y: " + 
		handler.getWorld().getEntityManager().getPlayer().getY());
		}
		
		// Check teleports
		teleportTo();

	}
	
	private void teleportTo(){
		// Teleport at the house
		if(getX() <= 487 && getY() >= 506){
			if(getX() >= 470 && getY() <= 512){
				setX(544);
				setY(64);
			}
		}
		
		// Teleport from black area back
		if(getX() >= 534 && getY() <= 127)
			if(getX() <= 583 && getY() >= 110){
				setX(64);
				setY(64);
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
				e.damage(attackDamage + handler.getRandomSupplyAmount());
				return;
			}
		}
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	@Override
	public void die(){
		System.out.println("You died!");
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
		// Walk and Attack animations
		if(xMove < 0){
			return aLeft.getCurrentFrame();
		}
		else if(lastFaced == Direction.LEFT){
			if(handler.getKeyManager().attack){
				return attLeft.getCurrentFrame();
			}
		}
		else if(xMove > 0){
			return aRight.getCurrentFrame();
		}
		else if(lastFaced == Direction.RIGHT){
			if(handler.getKeyManager().attack){
				return attRight.getCurrentFrame();
			}
		}
		else if(yMove < 0){
			return aUp.getCurrentFrame();
		}
		else if(lastFaced == Direction.DOWN){
			if(handler.getKeyManager().attack){
				return attDown.getCurrentFrame();
			}
		}
		else if(yMove > 0){
			return aDown.getCurrentFrame();
		}
		else if(lastFaced == Direction.UP){
			if(handler.getKeyManager().attack){
				return attUp.getCurrentFrame();
			}
		}
		
		return aDefault.getDefaultFrame();
	}

	@Override
	public void interact() {
		
	}
	

}
