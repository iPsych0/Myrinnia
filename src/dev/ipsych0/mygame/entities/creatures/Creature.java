package dev.ipsych0.mygame.entities.creatures;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
	protected int attackRange = Tiles.TILEWIDTH * 2;
	
	// A* stuff
	protected CombatState state;
	protected List<Node> nodes;
	protected AStarMap map;
	protected int pathFindRadiusX = 576, pathFindRadiusY = 576;
	private int alpha = 127;
	protected Color pathColour = new Color(0, 255, 255, alpha);
	
	// Walking timer
	private int time = 0;
	
	// RNG dice for random movement
	private Random randMove = new Random();
	
	// Radius variables:
	protected int xSpawn = (int)getX();
	protected int ySpawn = (int)getY();
	protected int xRadius = 192;
	protected int yRadius = 192;
	protected Rectangle radius;
	
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
		state = CombatState.IDLE;
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
		radius = new Rectangle((int)x - xRadius, (int)y - yRadius, xRadius * 2, yRadius * 2);
		
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
			lastFaced = Direction.RIGHT;
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tiles.TILEWIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tiles.TILEHEIGHT) && 
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tiles.TILEHEIGHT)) {
				x += xMove;
			} else{
				x = tx * Tiles.TILEWIDTH - bounds.x - bounds.width - 1;
			}
			
		}else if(xMove < 0){ // Moving left
			direction = Direction.LEFT;
			lastFaced = Direction.LEFT;
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
			lastFaced = Direction.UP;
			int ty = (int) (y + yMove + bounds.y) / Tiles.TILEHEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tiles.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tiles.TILEWIDTH, ty)) {
				y += yMove;
			} else{
				y = ty * Tiles.TILEHEIGHT + Tiles.TILEHEIGHT - bounds.y;
			}
			
		}else if (yMove > 0){ // Down
			direction = Direction.DOWN;
			lastFaced = Direction.DOWN;
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
		for(int i = 0; i < handler.getWorld().getLayers().length; i++) {
			if(handler.getWorld().getTile(i, x, y).isSolid()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * If the Entity is an NPC, this function returns only the name, else it returns name + combat info.
	 */
	@Override
	public String[] getEntityInfo(Entity hoveringEntity) {
		if(this.isNpc) {
			String[] name = new String[1];
			name[0] = hoveringEntity.getClass().getSimpleName();
			return name;
		}
		String[] name = new String[3];
		name[0] = hoveringEntity.getClass().getSimpleName() + " (level-" + getCombatLevel() + ")";
		name[1] = "Max hit:" + String.valueOf(this.getDamage(hoveringEntity));
		name[2] = "Health: " + String.valueOf(health) + "/" + String.valueOf(maxHealth);
		return name;
	}
	
	/*
	 * Walks into random directions at given intervals
	 */
	protected void randomWalk() {
		time++;
		int i = (randMove.nextInt(60) + 30);
		if(time % i == 0){
			
			int direction = randMove.nextInt(4);
			
			if(direction == 0){
				xMove = 0;
				yMove = 0;
			}
			
			if(direction == 1) {
				xMove = - speed;
			}
			if(direction == 2) {
				xMove = + speed;
			}
			if(direction == 3) {
				yMove = - speed;
			}
			if(direction == 4) {
				yMove = + speed;
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
		
	}

	/**
	 * Manages the different combat states of a Creature (IDLE, PATHFINDING, ATTACKING, BACKTRACKING)
	 */
	protected void combatStateManager() {
		// If the player is within the A* map AND moves within the aggro range, state = pathfinding (walk towards goal)
		if(handler.getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && handler.getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
			state = CombatState.PATHFINDING;
			int playerX = (int)Math.round(((handler.getPlayer().getX() + 8) / 32)) - (int)(xSpawn - pathFindRadiusX) / 32;
			int playerY = (int)Math.round(((handler.getPlayer().getY() + 8) / 32)) - (int) (ySpawn - pathFindRadiusY) / 32;
			
			if(playerX == map.getNodes().length || playerY == map.getNodes().length) {
				nodes = map.findPath((int)((x + 8) / 32) - (int)(xSpawn - pathFindRadiusX) / 32, (int)((y + 8) / 32) - (int) (ySpawn - pathFindRadiusY) / 32,
						(int)Math.round(((handler.getPlayer().getX() + 8) / 32)) - (int)(xSpawn - pathFindRadiusX) / 32, (int)Math.round(((handler.getPlayer().getY() + 8) / 32)) - (int) (ySpawn - pathFindRadiusY) / 32);
			}
			else{
				nodes = map.findPath((int)((x + 8) / 32) - (int)(xSpawn - pathFindRadiusX) / 32, (int)((y + 8) / 32) - (int) (ySpawn - pathFindRadiusY) / 32,
						playerX, playerY);
			}
		}
		
		// If the player has moved out of the initial aggro box, but is still within the A* map, keep following
		else if(!handler.getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && handler.getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.PATHFINDING) {
			int playerX = (int)Math.round(((handler.getPlayer().getX() + 8) / 32)) - (int)(xSpawn - pathFindRadiusX) / 32;
			int playerY = (int)Math.round(((handler.getPlayer().getY() + 8) / 32)) - (int) (ySpawn - pathFindRadiusY) / 32;
			
			if(playerX == map.getNodes().length || playerY == map.getNodes().length) {
				nodes = map.findPath((int)((x + 8) / 32) - (int)(xSpawn - pathFindRadiusX) / 32, (int)((y + 8) / 32) - (int) (ySpawn - pathFindRadiusY) / 32,
						(int)Math.round(((handler.getPlayer().getX() + 8) / 32)) - (int)(xSpawn - pathFindRadiusX) / 32, (int)Math.round(((handler.getPlayer().getY() + 8) / 32)) - (int) (ySpawn - pathFindRadiusY) / 32);
			}
			else{
				nodes = map.findPath((int)((x + 8) / 32) - (int)(xSpawn - pathFindRadiusX) / 32, (int)((y + 8) / 32) - (int) (ySpawn - pathFindRadiusY) / 32,
						playerX, playerY);
			}
		}
		
		// If the player has moved out of the aggro box and out of the A* map, 
		else if(!handler.getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && !handler.getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.PATHFINDING ||
				!handler.getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && !handler.getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.ATTACK) {
			state = CombatState.BACKTRACK;
		}
		
		// If the player is <= X * TileWidth away from the Creature, attack him.
		if(distanceToEntity(((int)this.getX() + this.getWidth() / 2), ((int)this.getY() + + this.getHeight() / 2),
				((int)handler.getPlayer().getX() + handler.getPlayer().getWidth() / 2), ((int)handler.getPlayer().getY() + handler.getPlayer().getHeight() / 2)) <= attackRange &&
				handler.getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) &&
				handler.getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
			state = CombatState.ATTACK;
			checkAttacks();
		}
		
		// If the Creature was attacking, but the player moved out of aggro range or out of the A* map bounds, backtrack to spawn.
		if(state == CombatState.ATTACK && !handler.getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) || state == CombatState.ATTACK && !handler.getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds())) {
			state = CombatState.BACKTRACK;
		}
		
		// If the Creature was following the player but he moved out of the aggro, backtrack.
//		if(!handler.getPlayer().getCollisionBounds(0, 0).intersects(getRadius()) && state == CombatState.PATHFINDING || state == CombatState.BACKTRACK) {
//			nodes = map.findPath((int)((x + 8) / 32) - (int)(xSpawn - pathFindRadiusX) / 32, (int)((y + 8) / 32) - (int) (ySpawn - pathFindRadiusY) / 32,
//					(int)Math.round(((xSpawn + 8) / 32)) - (int)(xSpawn - pathFindRadiusX) / 32, (int)Math.round(((ySpawn + 8) / 32)) - (int) (ySpawn - pathFindRadiusY) / 32);
		// If the Creature was following the player but he moved out of the A* map, backtrack.
		if(!handler.getPlayer().getCollisionBounds(0, 0).intersects(map.getMapBounds()) && state == CombatState.PATHFINDING || state == CombatState.BACKTRACK) {
			nodes = map.findPath((int)((x + 8) / 32) - (int)(xSpawn - pathFindRadiusX) / 32, (int)((y + 8) / 32) - (int) (ySpawn - pathFindRadiusY) / 32,
					(int)Math.round(((xSpawn + 8) / 32)) - (int)(xSpawn - pathFindRadiusX) / 32, (int)Math.round(((ySpawn + 8) / 32)) - (int) (ySpawn - pathFindRadiusY) / 32);
		}
		
		// If the Creature was not following or attacking the player, move around randomly.
		if(state == CombatState.IDLE) {
			randomWalk();
		}

		// If we have a path to follow and there are nodes left, followed the nodes.
		if(nodes != null) {
			if(nodes.size() != 0) {
				followAStar(nodes);
			}
		}
	}
	
	/*
	 * Override this method in the creature class
	 */
	protected void checkAttacks(){
		
	}
	
	/**
	 * Movement logic for following each node in the List nodes
	 * @param nodes - path of nodes to follow
	 */
	protected void followAStar(List<Node> nodes){
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
		
		
		// TODO: Still a bug of some sort in movement (primarily on the Y axis), if the if-statements are switched around, problem exists on the X-axis
		if (next.getX() != (int)((x + 8) / 32)){
			xMove = (next.getX() < (int)((x + 8) / 32) ? -speed : speed);
			move();
			if(x % 32 == 8) {
				//x -= x % 32;
				if(!((LinkedList<Node>) nodes).isEmpty())
					((LinkedList<Node>) nodes).removeFirst();
				
				xMove = 0;
			}

		}
		
		if(next.getY() != (int)((y + 8) / 32)) {
			yMove = (next.getY() < (int)((y + 8) / 32) ? -speed : speed);
			move();
			if(y % 32 == 8) {
				//y -= y % 32;
				if(!((LinkedList<Node>) nodes).isEmpty())
					((LinkedList<Node>) nodes).removeFirst();
				
				yMove = 0;
			}

		}
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

	public CombatState getState() {
		return state;
	}

	public void setState(CombatState state) {
		this.state = state;
	}

	public Rectangle getRadius() {
		return radius;
	}

	public void setRadius(Rectangle radius) {
		this.radius = radius;
	}

}
