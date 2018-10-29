package dev.ipsych0.myrinnia.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.tiles.Tiles;

public class Scorpion extends Creature {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 917078714756242679L;

	private boolean initialized = false;
	
	 //Attack timer
	 private long lastAttackTimer, attackCooldown = 600, attackTimer = attackCooldown;

	public Scorpion(float x, float y) {
		super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		isNpc = false;
		
		// Creature stats
		power = 5;
		vitality = 5;
		defence = 10;
		speed = DEFAULT_SPEED + 0.5f;
		attackSpeed = DEFAULT_ATTACKSPEED;
		maxHealth = (int) (DEFAULT_HEALTH + Math.round(vitality * 1.5));
		health = maxHealth;
		combatLevel = 2;
		
		double exponent = 1.1;
		for(int i = 1; i < combatLevel; i++) {
			baseDamage = (int)Math.ceil((baseDamage * exponent) + 1);
			exponent *= 0.9985;
		}
		attackRange = Tiles.TILEWIDTH * 6;
		
		bounds.x = 2;
		bounds.y = 2;
		bounds.width = 28;
		bounds.height = 28;
		
		pathFindRadiusX = 512 * 2;
		pathFindRadiusY = 512 * 2;
		
		radius = new Rectangle((int)x - xRadius, (int)y - yRadius, xRadius * 2, yRadius * 2);
		
		map = new AStarMap(this, (int)xSpawn - pathFindRadiusX, (int)ySpawn - pathFindRadiusY, pathFindRadiusX * 2, pathFindRadiusY * 2, xSpawn, ySpawn);
	}

	@Override
	public void tick() {
		if(!initialized) {
			map.init();
			initialized = true;
		}
		radius = new Rectangle((int)x - xRadius, (int)y - yRadius, xRadius * 2, yRadius * 2);
		
		combatStateManager();
		
		tickProjectiles();
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.scorpion, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
				, width, height, null);
		
		// Draw HP above head
//		Text.drawString(g, Integer.toString(getHealth()) + "/" + maxHealth, (int) (x - Handler.get().getGameCamera().getxOffset() - 6),
//				(int) (y - Handler.get().getGameCamera().getyOffset() - 8), false, Color.YELLOW, Creature.hpFont);
		
//		g.setColor(Color.BLACK);
//		g.drawRect((int)(radius.x - Handler.get().getGameCamera().getxOffset()), (int)(radius.y - Handler.get().getGameCamera().getyOffset()), (int)(radius.width), (int)(radius.height));
//		
//		map.render(g);
//		
//		if(nodes != null) {
//			for(Node n : nodes) {
//				g.setColor(pathColour);
//				g.fillRect((int)(n.getX() * 32 - Handler.get().getGameCamera().getxOffset()), (int)(n.getY() * 32 - Handler.get().getGameCamera().getyOffset()), 32, 32);
//			}
//		}
		
	}

	@Override
	public void die() {
		// Drop table stuff
		int randomNumber = Handler.get().getRandomNumber(1, 50);
		System.out.println("Rolled " + randomNumber + " on the RNG dice.");
		
		if(randomNumber <= 10){
			Handler.get().dropItem(Item.regularLogs, 5, (int)x, (int)y);
		}
		else if(randomNumber >= 11 && randomNumber <= 50){
			Handler.get().dropItem(Item.regularOre, 10, (int)x, (int)y);
			Handler.get().dropItem(Item.purpleSword, 1, (int)x, (int)y);
		}
		Handler.get().dropItem(Item.coins, 50, (int)x, (int)y);
		Handler.get().dropItem(Item.testAxe, 1, (int)x, (int)y);
		Handler.get().dropItem(Item.testPickaxe, 1, (int)x, (int)y);
		
//		FOR INSTA NEXT LEVEL: Handler.get().getSkill(SkillsList.COMBAT).getNextLevelXp()
		Handler.get().getSkill(SkillsList.COMBAT).addExperience(25);
	}
	
	/*
	 * Damage formula
	 */
//	@Override
//	public int getDamage(Entity dealer) {
//		return super.getDamage(dealer) - 7;
//	}
	
	/*
	 * Checks the attack timers before the next attack 
	 */
	protected void checkAttacks(){
		// Attack timers
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCooldown)
			return;
		
//		// Set attack-box
//		Rectangle cb = getCollisionBounds(0,0);
//		Rectangle ar = new Rectangle();
//		int arSize = Creature.DEFAULT_CREATURE_HEIGHT;
//		ar.width = arSize;
//		ar.height = arSize;
		
		attackTimer = 0;
		
		Handler.get().playEffect("fireball.wav");
		projectiles.add(new Projectile(x, y, (int)Handler.get().getPlayer().getX(), (int)Handler.get().getPlayer().getY(), 9.0f));
		
//		for(Entity e : Handler.get().getWorld().getEntityManager().getEntities()){
//			if(!e.equals(Handler.get().getPlayer())){
//				continue;
//			}
//			if(e.getCollisionBounds(0, 0).intersects(ar)){
//				// TODO: Change damage calculation formula
//				e.damage(this, e);
//				return;
//			}
//		}
	}

	@Override
	public void interact() {

	}

	@Override
	public void postRender(Graphics g) {
		
	}

	@Override
	public void respawn() {
		Handler.get().getWorld().getEntityManager().addEntity(new Scorpion(xSpawn, ySpawn));
	}
}
