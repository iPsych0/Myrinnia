package dev.ipsych0.mygame.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;

public class Scorpion extends Creature {
	
	// RNG dice for drop table
	private Random randDrop = new Random();
	private int min = 1, max = 50;
	
	// RNG dice for random movement
	private Random randMove = new Random();
	
	// Radius variables:
	private int xSpawn = (int)getX();
	private int ySpawn = (int)getY();
	private int xRadius = 128;
	private int yRadius = 128;
	
	// Walking timer
	private int time = 0;
	
	 //Attack timer
	 private long lastAttackTimer, attackCooldown = 600, attackTimer = attackCooldown;

	public Scorpion(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		speed = DEFAULT_SPEED;
		isNpc = false;
		setNpc(false);
	}

	@Override
	public void tick() {
		randomWalk();
		checkAttacks();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.scorpion, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
		g.setColor(Creature.hpColor);
		g.setFont(Creature.hpFont);
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				g.drawString(Integer.toString(e.getHealth()),
						(int) (x - handler.getGameCamera().getxOffset() + 4), (int) (y - handler.getGameCamera().getyOffset()));
		}
	}

	@Override
	public void die() {
		handler.getWorld().getChatWindow().sendMessage("You killed the " + this.getClass().getSimpleName().toString());
		int randomNumber = randDrop.nextInt((max - min) + 1) + min;
		System.out.println("Rolled " + randomNumber + " on the RNG dice.");
		if(randomNumber <= 10){
			handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int) x, (int) y, 100));
		}
		if(randomNumber >= 11 && randomNumber <= 50){
			handler.getWorld().getItemManager().addItem(Item.oreItem.createNew((int) x, (int) y, 100));
		}
		handler.getWorld().getItemManager().addItem(Item.coinsItem.createNew((int) x, (int) y, 100));
		
		if(Lorraine.questStarted){
			handler.getWorld().getEntityManager().getPlayer().addScorpionKC();
		}
		
		handler.getWorld().getEntityManager().getPlayer().addAttackExperience(25);
		
	}
	
	
	
	private void checkAttacks(){
		// Attack timers
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCooldown)
			return;
		
		int direction = randMove.nextInt((max - min) + 1) + min;
		
		// Set attack-box
		Rectangle cb = getCollisionBounds(0,0);
		Rectangle ar = new Rectangle();
		int arSize = Creature.DEFAULT_CREATURE_HEIGHT;
		ar.width = arSize;
		ar.height = arSize;
		
		// Attack box setters
		if(direction <= 12){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
		}
		else if(direction <= 25){
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
		}
		else if(direction <= 37){
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2 ;
		}
		else if(direction <= 50){
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
			if(!e.equals(handler.getWorld().getEntityManager().getPlayer())){
				continue;
			}
			if(e.getCollisionBounds(0, 0).intersects(ar)){
				// TODO: Change damage calculation formula
				e.damage(baseDamage + handler.getRandomSupplyAmount());
				return;
			}
		}
	}
	
	
	private void randomWalk() {
		time++;
		if(time % (randMove.nextInt(30) + 30) == 0){
			
			xMove = randMove.nextInt(3) - speed;
			yMove = randMove.nextInt(3) - speed;
			if(randMove.nextInt(3) == 0){
				xMove = 0;
				yMove = 0;
			}
		}
		
		if(getX() > (xSpawn + xRadius)){
			xMove = - speed;
		}
		else if(getX() < (xSpawn - xRadius)){
			xMove = + speed;
		}
		else if(getY() > (ySpawn + yRadius)){
			yMove = - speed;
		}
		else if(getY() < (ySpawn - yRadius)){
			yMove = + speed;
		}
		move();
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
}
