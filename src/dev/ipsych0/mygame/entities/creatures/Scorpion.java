package dev.ipsych0.mygame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
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
	private int xRadius = 256;
	private int yRadius = 256;
	private int pathFindRadiusX = 576, pathFindRadiusY = 576;
	private Rectangle radius;
	private AStarMap map;
	private List<Node> nodes;
	int alpha = 127;
	private Color pathColour = new Color(0, 255, 255, alpha);
	
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
		speed = DEFAULT_SPEED + 1.0f;
		setAttackSpeed(DEFAULT_ATTACKSPEED);
		maxHealth = (int) (DEFAULT_HEALTH + Math.round(getVitality() * 1.5));
		health = maxHealth;
		combatLevel = 5;
		
		bounds.x = 2;
		bounds.y = 2;
		bounds.width = 28;
		bounds.height = 28;
		
		projectiles = new ArrayList<Projectile>();
		
		radius = new Rectangle((int)x - xRadius, (int)y - yRadius, xRadius * 2, yRadius * 2);
		
		map = new AStarMap(handler, (int)x - pathFindRadiusX, (int)y - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
	}

	@Override
	public void tick() {
		if(!initialized) {
			map.init();
			initialized = true;
		}
		radius = new Rectangle((int)x - xRadius, (int)y - yRadius, xRadius * 2, yRadius * 2);
		//randomWalk();
		map = new AStarMap(handler, (int)x - pathFindRadiusX, (int)y - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2);
		map.init();
//		if(handler.getPlayer().getCollisionBounds(0, 0).intersects(radius)) {
		//if(handler.getKeyManager().position && Player.debugButtonPressed) {
		/*
		 * Calculate A* path and move
		 */
		//System.out.println((x / 32) - (int)(x - pathFindRadiusX) / 32);
		nodes = map.findPath((int)(x / 32) - (int)(x - pathFindRadiusX) / 32, (int)(y / 32) - (int) (y - pathFindRadiusY) / 32,
				(int)Math.round(((handler.getPlayer().getX() + 8) / 32)) - (int)(x - pathFindRadiusX) / 32, (int)Math.round(((handler.getPlayer().getY() + 8) / 32)) - (int) (y - pathFindRadiusY) / 32);
		//TODO: MAKE PLAYER THE CENTER OF THE ALGORITHM!
//		int numNodes = 0;
//		if(nodes != null) {
//			for(Node n : nodes) {
//				numNodes++;
//				System.out.println("================");
//				System.out.println("NODE: " + numNodes);
//				System.out.println("================");
//				System.out.println(n.getX() - (int)(x - pathFindRadiusX) / 32);
//				System.out.println(n.getY() - (int)(y - pathFindRadiusY) / 32);
//				System.out.println("================");
//			}
//		}
//			Player.debugButtonPressed = false;
//		}
//		}
		if(nodes != null) {
			if(nodes.size() != 0) {
				followAStar(nodes);
				//System.out.println("Zoeken...");
			}
		}

		//checkAttacks();
		
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
	
	private void followAStar(List<Node> nodes){
		this.nodes = nodes;
		
		if (nodes == null){
			return;
		}
		if (nodes.size() <= 0){
			return;
		}
		
		Node next = ((LinkedList<Node>) nodes).getFirst();
		
//		System.out.println("=========");
//		System.out.println(next.getX());
//		System.out.println(next.getY());
//		System.out.println("=========");
//		
//		System.out.println((int)x / 32);
//		System.out.println((int)y / 32);
//		System.out.println("=========");
		
		if (next.getX() != (int)(x / 32)){
			xMove = (next.getX() < (int)(x / 32) ? -speed : speed);
			move();
			if(x % 32 == 0) {
				//x -= x % 32;
				if(!((LinkedList<Node>) nodes).isEmpty())
					((LinkedList<Node>) nodes).removeFirst();
				
				//xMove %= 32;
			}

		}
		if(next.getY() != (int)(y / 32)) {
			yMove = (next.getY() < (int)(y / 32) ? -speed : speed);
			move();
			if(y % 32 == 0) {
				//y -= y % 32;
				if(!((LinkedList<Node>) nodes).isEmpty())
					((LinkedList<Node>) nodes).removeFirst();
				
				//yMove %= 32;
			}

		}
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
		
		if(nodes != null) {
			for(Node n : nodes) {
				g.setColor(pathColour);
				g.fillRect((int)(n.getX() * 32 - handler.getGameCamera().getxOffset()), (int)(n.getY() * 32 - handler.getGameCamera().getyOffset()), 32, 32);
			}
		}
		
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
			projectiles.add(new Projectile(handler, x, y, (int)handler.getPlayer().getX(), (int)handler.getPlayer().getY(), 6.0f));
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
