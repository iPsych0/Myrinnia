package dev.ipsych0.mygame.astar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import dev.ipsych0.mygame.Handler;

public class AStarMap {
	
	private int x, y, width, height;
	private Handler handler;
	private Node[][] nodes;
	
	public AStarMap(Handler handler, int x, int y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		nodes = new Node[(int) (Math.floor(width / 32))][(int)(Math.floor(height / 32))];
		
		System.out.println(nodes[0].length);
		System.out.println(nodes.length);
		
		System.out.println(handler.getWorld());
		
		for(int i = 0; i < nodes.length; i++) {
			for(int j = 0; j < nodes.length; j++) {
				nodes[i][j] = new Node((i * 32) + x, (j * 32) + y, true);
			}
		}
		
	}
	
	public void init() {
		for(int i = 0; i < nodes.length; i++) {
			for(int j = 0; j < nodes.length; j++) {
				if(handler.getWorld().checkSolidLayer(((i * 32) + x) / 32, ((j * 32) + y) / 32)) {
					nodes[i][j].setWalkable(false);
				}
			}
		}
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height);
		
		for(int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes.length; j++) {
				if(nodes[i][j].isWalkable()) {
					g.setColor(Color.MAGENTA);
					g.drawRect((int)(nodes[i][j].getX() - handler.getGameCamera().getxOffset()), (int)(nodes[i][j].getY() - handler.getGameCamera().getyOffset()), 32, 32);
				}else {
					g.setColor(Color.DARK_GRAY);
					g.fillRect((int)(nodes[i][j].getX() - handler.getGameCamera().getxOffset()), (int)(nodes[i][j].getY() - handler.getGameCamera().getyOffset()), 32, 32);
				}
			}
		}
	}
	
	public Rectangle getMapBounds() {
		return new Rectangle(x, y, width, height);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
	
	

}
