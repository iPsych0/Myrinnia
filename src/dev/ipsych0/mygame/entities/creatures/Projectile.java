package dev.ipsych0.mygame.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.Entity;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.tiles.Tiles;

public class Projectile {
	
	private float x, y;
	private int width, height;
	private float xSpawn, ySpawn;
	private double xVelocity, yVelocity;
	private int maxX, maxY, minX, minY;
	private int mouseX, mouseY;
	private double angle;
	private int maxRadius = 320;
	private Animation magic;
	private Handler handler;
	private Rectangle bounds;
	public boolean active = true;
	
	public Projectile(Handler handler, float x, float y, int mouseX, int mouseY, float velocity) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		
		width = Creature.DEFAULT_CREATURE_WIDTH;
		height = Creature.DEFAULT_CREATURE_HEIGHT;
		
		bounds = new Rectangle((int)x,(int)y,width,height);
		
		bounds.x = 10;
		bounds.y = 14;
		bounds.width = 10;
		bounds.height = 10;
		
		xSpawn = x;
		ySpawn = y;
		
		maxX = (int) (x + maxRadius);
		maxY = (int) (y + maxRadius);
		minX = (int) (x - maxRadius);
		minY = (int) (y - maxRadius);
		
		angle = Math.atan2(mouseY - y, mouseX - x);
		xVelocity = velocity * Math.cos(angle);
		yVelocity = velocity * Math.sin(angle);
		
		magic = new Animation(125, Assets.magicProjectile);
		
		
	}
	
	public void tick() {
		magic.tick();
		
		x += xVelocity;
		y += yVelocity;
		
		if(x > maxX || x < minX || y > maxY || y < minY) {
			active = false;
		}
		
		if(active) {
			for(Entity e: handler.getWorld().getEntityManager().getEntities()) {
				if(e.equals(handler.getPlayer())) {
					continue;
				}
				if(!e.isAttackable()) {
					continue;
				}
				if(e.isNpc()) {
					continue;
				}
				if(getCollisionBounds(0,0).intersects(e.getCollisionBounds((float)xVelocity, (float)yVelocity))){
					active = false;
					e.damage(5);
					return;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(magic.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())
				, width, height, null);
		
	}


	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public double getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public double getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

}
