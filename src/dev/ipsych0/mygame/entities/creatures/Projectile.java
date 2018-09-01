package dev.ipsych0.mygame.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;
import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;

public class Projectile implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float x, y;
	private int width, height;
	private double xVelocity, yVelocity;
	private int maxX, maxY, minX, minY;
	private double angle;
	private static final int MAX_RADIUS = 320;
	private Animation magic;
	private Rectangle bounds;
	public boolean active;
	
	public Projectile(float x, float y, int mouseX, int mouseY, float velocity) {
		this.x = x;
		this.y = y;
		
		width = Creature.DEFAULT_CREATURE_WIDTH;
		height = Creature.DEFAULT_CREATURE_HEIGHT;
		
		bounds = new Rectangle((int)x,(int)y,width,height);
		
		bounds.x = 10;
		bounds.y = 14;
		bounds.width = 10;
		bounds.height = 10;
		
		// Max distance the projectile can travel
		maxX = (int) (x + MAX_RADIUS);
		maxY = (int) (y + MAX_RADIUS);
		minX = (int) (x - MAX_RADIUS);
		minY = (int) (y - MAX_RADIUS);
		
		// The angle and speed of the projectile
		angle = Math.atan2(mouseY - y, mouseX - x);
		xVelocity = velocity * Math.cos(angle);
		yVelocity = velocity * Math.sin(angle);
		
		magic = new Animation(83, Assets.magicProjectile);
		
		active = true;
		
		
	}
	
	public void tick() {
		if(active) {
			magic.tick();
			
			x += xVelocity;
			y += yVelocity;
			
			// If out of range, remove this projectile
			if(x > maxX || x < minX || y > maxY || y < minY) {
				active = false;
				return;
			}
		}
	}
	
	public void render(Graphics g) {
		if(active) {
			g.drawImage(magic.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
		}
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
