package dev.ipsych0.mygame.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;

public class Player extends Creature{
	
	// Walking Animations
	private Animation aDown, aUp, aLeft, aRight;
	
	// Attacking Animations
	private Animation attDown, attUp, attLeft, attRight;
	
	// Attack timer
	private long lastAttackTimer, attackCooldown = 1000, attackTimer = attackCooldown;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
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
		
		//Animations
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
		
		// Pickup listener
		pickUp();
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
		int arSize = 16;
		ar.width = arSize;
		ar.height = arSize;
		
		// Attack box setters
		if(handler.getKeyManager().aUp){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
		}
		else if(handler.getKeyManager().aDown){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
		}
		else if(handler.getKeyManager().aLeft){
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2 ;
		}
		else if(handler.getKeyManager().aRight){
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
			if(e.getCollisionBounds(0, 0).intersects(ar)){
				e.damage(20);
				return;
			}
		}
	}
	
	
	// TODO: Get this method working to pick up items from the world into the player's inventory.
	public void pickUp(){
		
		Rectangle cb = getCollisionBounds(0,0);
		Rectangle ar = new Rectangle();
		int arSize = 16;
		ar.width = arSize;
		ar.height = arSize;
		
		if(handler.getKeyManager().aUp){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
		}
		else if(handler.getKeyManager().aDown){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
		}
		else if(handler.getKeyManager().aLeft){
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2 ;
		}
		else if(handler.getKeyManager().aRight){
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height / 2 - arSize / 2 ;
		}
		else{
			return;
		}
		
		// TODO: For every item in the world, check if the player has collision with item, if yes, pick it up
		for(Item i : handler.getWorld().getItemManager().getItems()){
			if(getCollisionBounds(0, 0).intersects(ar)){
				i.;
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
		}
		if(handler.getKeyManager().down){
			yMove = speed;
		}
		if(handler.getKeyManager().left){
			xMove = -speed;
		}
		if(handler.getKeyManager().right){
			xMove = speed;
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
//		g.setColor(Color.red);
//		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
//				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
	}
	
	private BufferedImage getCurrentAnimationFrame(){
		if(xMove < 0)
			return aLeft.getCurrentFrame();
		else if(handler.getKeyManager().aLeft)
			return attLeft.getCurrentFrame();
		else if(xMove > 0)
			return aRight.getCurrentFrame();
		else if(handler.getKeyManager().aRight)
			return attRight.getCurrentFrame();
		else if(yMove < 0)
			return aUp.getCurrentFrame();
		else if(handler.getKeyManager().aDown)
			return attDown.getCurrentFrame();
		else if(yMove > 0)
			return aDown.getCurrentFrame();
		else if(handler.getKeyManager().aUp)
			return attUp.getCurrentFrame();
		else
			return aDown.getDefaultFrame();
		
	}
	

}
