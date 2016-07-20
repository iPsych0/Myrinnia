package dev.ipsych0.mygame.teleports;

import dev.ipsych0.mygame.Handler;

public class TeleportManager {
	
	protected float x, y;
	private Handler handler;
	
	public TeleportManager(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void teleportPlayerTo(float x, float y){
		System.out.println("X = " + x);
		System.out.println("Y = " + y);
		handler.getWorld().getEntityManager().getPlayer().setX(x);
		handler.getWorld().getEntityManager().getPlayer().setX(y);
		System.out.println("X after move = " + handler.getWorld().getEntityManager().getPlayer().getX());
		System.out.println("Y after move = " + handler.getWorld().getEntityManager().getPlayer().getY());
	}

}
