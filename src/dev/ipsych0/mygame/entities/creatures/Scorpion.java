package dev.ipsych0.mygame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.astar.AStarMap;
import dev.ipsych0.mygame.astar.Node;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.Item;
import dev.ipsych0.mygame.worlds.World;

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
	private int pathFindRadiusX = 256, pathFindRadiusY = 256;
	private Rectangle radius;
	private AStarMap map;
	
	private ArrayList<Projectile> projectiles;
	
	// Walking timer
	private int time = 0;
	
	private boolean initialized = false;
	
	 //Attack timer
	 private long lastAttackTimer, attackCooldown = 600, attackTimer = attackCooldown;

	public Scorpion(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		isNpc = false;
		
		// Creature stats
		setPower(0);
		setVitality(7);
		setDefence(0);
		speed = DEFAULT_SPEED;
		setAttackSpeed(DEFAULT_ATTACKSPEED);
		maxHealth = (int) (DEFAULT_HEALTH + Math.round(getVitality() * 1.5));
		health = maxHealth;
		combatLevel = 5;
		
		bounds.x = 0;
		bounds.y = 2;
		bounds.width = 28;
		bounds.height = 28;
		
		projectiles = new ArrayList<Projectile>();
		
		radius = new Rectangle((int)x - xRadius, (int)y - yRadius, xRadius * 2, yRadius * 2);
		
		map = new AStarMap(handler, (int)x - pathFindRadiusX, (int)y - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
	}

	@Override
	public void tick() {
		//randomWalk();
//		map = new AStarMap(handler, (int)x - pathFindRadiusX, (int)y - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
//		map.init();
//		if(handler.getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
		if(handler.getKeyManager().position && Player.debugButtonPressed) {
			System.out.println((x / 32) - (int)(x - pathFindRadiusX) / 32);
			List<Node> nodes = map.findPath((int)(x / 32) - (int)(x - pathFindRadiusX) / 32, (int)(y / 32) - (int) (y - pathFindRadiusY) / 32,
					(int)(handler.getPlayer().getX() / 32) - (int)(x - pathFindRadiusX) / 32, (int)(handler.getPlayer().getY() / 32) - (int) (y - pathFindRadiusY) / 32);
			System.out.println(nodes);
			Player.debugButtonPressed = false;
			/*
			 * Calculate A* path and move
			 */
//			xMove = + speed;
//			move();
		}else {
//			xMove = -speed;
//			move();
		}
		//checkAttacks();
		
		if(!initialized) {
			map.init();
			initialized = true;
		}
		
		Iterator<Projectile> it = projectiles.iterator();
		Collection<Projectile> deleted = new CopyOnWriteArrayList<Projectile>();
		while(it.hasNext()){
			Projectile p = it.next();
			if(!p.active){
				deleted.add(p);
			}
			for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
				if(!e.equals(handler.getPlayer())) {
					continue;
				}
				if(p.getCollisionBounds(0, 0).intersects(e.getCollisionBounds(0,0)) && p.active) {
					if(e.isAttackable()) {
						e.damage(this, e);
						p.active = false;
					}
					if(!e.isAttackable()) {
						p.active = false;
					}
				}
				for(int i = 0; i < handler.getWorld().getLayers().length; i++) {
					if(handler.getWorld().getTile(i, (int)((p.getX() + 16) / 32), (int)((p.getY() + 16) / 32)).isSolid() && p.active) {
						p.active = false;
					}
				}
			}
			p.tick();
		}
		
		projectiles.removeAll(deleted);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.scorpion, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
		g.setColor(Creature.hpColor);
		g.setFont(Creature.hpFont);
		
		g.drawString(Integer.toString(getHealth()) + "/" + maxHealth,
				(int) (x - handler.getGameCamera().getxOffset() - 8), (int) (y - handler.getGameCamera().getyOffset()));
		
		for(Projectile p : projectiles) {
			if(p.active) {
				p.render(g);
			}
		}
		
//		g.setColor(Color.BLACK);
//		g.drawRect((int)(radius.x - handler.getGameCamera().getxOffset()), (int)(radius.y - handler.getGameCamera().getyOffset()), (int)(radius.width), (int)(radius.height));
		
		map.render(g);
		
	}

	@Override
	public void die() {
		// Drop table stuff
		int randomNumber = randDrop.nextInt((max - min) + 1) + min;
		System.out.println("Rolled " + randomNumber + " on the RNG dice.");
		
		if(randomNumber <= 10){
			handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int) x, (int) y, 5));
		}
		if(randomNumber >= 11 && randomNumber <= 50){
			handler.getWorld().getItemManager().addItem(Item.oreItem.createNew((int) x, (int) y, 10));
			handler.dropItem(Item.purpleSword, 1, (int)x, (int)y);
		}
		handler.getWorld().getItemManager().addItem(Item.coinsItem.createNew((int) x, (int) y, 50));
		
		
		if(Lorraine.questStarted){
			handler.getWorld().getEntityManager().getPlayer().addScorpionKC();
		}
		
		handler.getWorld().getEntityManager().getPlayer().addAttackExperience(25);
		
		World currentWorld = handler.getWorld();
		
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		                currentWorld.getEntityManager().addEntity(new Scorpion(handler, xSpawn, ySpawn));
		                
		            }
		        }, 
		        5000 
		);
	}
	
	/*
	 * Damage formula
	 */
	@Override
	public int getDamage(Entity dealer) {
		return super.getDamage(dealer);
	}
	
	/*
	 * Checks the attack timers before the next attack 
	 */
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
		
		if(radius.intersects(handler.getPlayer().getCollisionBounds(0, 0))) {
			projectiles.add(new Projectile(handler, x, y, (int)handler.getPlayer().getX(), (int)handler.getPlayer().getY(), 3.0f));
		}
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(!e.equals(handler.getWorld().getEntityManager().getPlayer())){
				continue;
			}
			if(e.getCollisionBounds(0, 0).intersects(ar)){
				// TODO: Change damage calculation formula
				e.damage(this, e);
				return;
			}
		}
	}
	
	/*
	 * Walks into random directions at given intervals
	 */
	private void randomWalk() {
		time++;
		if(time % (randMove.nextInt(30) + 30) == 0){
			
			xMove = randMove.nextInt(3) - speed;
			yMove = randMove.nextInt(3) - speed;
			if(randMove.nextInt(3) == 0){
				xMove = 0;
				yMove = 0;
			}
			time = 0;
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
		radius = new Rectangle((int)x - xRadius, (int)y - yRadius, xRadius * 2, yRadius * 2);
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postRender(Graphics g) {
		// TODO Auto-generated method stub
	}
}
