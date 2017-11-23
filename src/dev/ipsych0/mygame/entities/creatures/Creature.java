package dev.ipsych0.mygame.entities.creatures;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.tiles.Tiles;

public abstract class Creature extends Entity {

	
	/*
	 * Default Creature variables
	 */
	public static final float DEFAULT_SPEED = 1.0f, DEFAULT_ATTACKSPEED = 1.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 32,
							DEFAULT_CREATURE_HEIGHT = 32;
	
	public static final int DEFAULT_DAMAGE = 10,
							DEFAULT_POWER = 0,
							DEFAULT_DEFENCE = 0,
							DEFAULT_VITALITY = 0;
	public static Font hpFont = new Font("SansSerif", Font.BOLD, 12);
	public static Color hpColor = new Color(255, 255, 48);
	protected int baseDamage;
	protected int power;
	protected int defence;
	protected int vitality;
	protected float attackSpeed;
	protected int maxHealth;
	protected int combatLevel;
	
	protected enum Direction{
		UP, DOWN, LEFT, RIGHT
	}
	
	protected Direction direction;
	
	// Last faced direction
	private Direction lastFaced;
	
	protected float speed;
	protected float xMove, yMove;
	
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		setBaseDamage(DEFAULT_DAMAGE);
		setPower(DEFAULT_POWER);
		setDefence(DEFAULT_DEFENCE);
		setVitality(DEFAULT_VITALITY);
		speed = DEFAULT_SPEED;
		setAttackSpeed(DEFAULT_ATTACKSPEED);
		maxHealth = (int) (DEFAULT_HEALTH + Math.round(getVitality() * 1.5));
		health = maxHealth;
		combatLevel = 1;
		xMove = 0;
		yMove = 0;
		drawnOnMap = true;
	}
	
	/*
	 * The default damage formula (see Entity::getDamage() function)
	 * NOTE: USE THIS METHOD WITH @Override IN SPECIFIC ENTITIES TO CREATE PERSONAL DAMAGE FORMULA!
	 */
	@Override
	public int getDamage(Entity dealer) {
		return super.getDamage(dealer);
	}

	/*
	 * Moves on the X or Y axis, keeping in mind the collision detection
	 */
	public void move(){
		if(!checkEntityCollisions(xMove, 0f))
			moveX();
		if(!checkEntityCollisions(0f, yMove))
			moveY();
	}
	
	/*
	 * Handles movement on the X-axis
	 */
	public void moveX(){
		if(xMove > 0){ // Moving right
			direction = Direction.RIGHT;
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tiles.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) && 
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT)) {
				x += xMove;
			} else{
				x = tx * Tiles.TILEWIDTH - bounds.x - bounds.width - 1;
			}
			
		}else if(xMove < 0){ // Moving left
			direction = Direction.LEFT;
			int tx = (int) (x + xMove + bounds.x) / Tiles.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT)) {

				x += xMove;
			} else{
				x = tx * Tiles.TILEWIDTH + Tiles.TILEWIDTH - bounds.x;
			}
		}
		else if(xMove == 0){
			direction = lastFaced;
		}
	}
	
	/*
	 * Handles movement on the Y-axis
	 */
	public void moveY(){
		if(yMove < 0){ // Up
			direction = Direction.UP;
			int ty = (int) (y + yMove + bounds.y) / Tiles.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty)) {
				y += yMove;
			} else{
				y = ty * Tiles.TILEHEIGHT + Tiles.TILEHEIGHT - bounds.y;
			}
			
		}else if (yMove > 0){ // Down
			direction = Direction.DOWN;
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tiles.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty)) {
				y += yMove;
			} else{
				y = ty * Tiles.TILEHEIGHT - bounds.y - bounds.height - 1;
			}
		}
		else if(yMove == 0){
			direction = lastFaced;
		}
	}
	
	/*
	 * Handles collision detection with Tiles
	 */
	protected boolean collisionWithTile(int x, int y){
		for(int i = 0; i < handler.getWorld().getFile().length; i++) {
			if(handler.getWorld().getTile(i, x, y).isSolid()) {
				return true;
			}
		}
		return false;
	}
	
	// GETTERS + SETTERS
	
	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public float getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(float attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public int getBaseDamage() {
		return baseDamage;
	}

	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getCombatLevel() {
		return combatLevel;
	}

	public void setCombatLevel(int combatLevel) {
		this.combatLevel = combatLevel;
	}

}
