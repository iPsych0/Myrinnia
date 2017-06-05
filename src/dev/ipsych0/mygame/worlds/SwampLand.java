package dev.ipsych0.mygame.worlds;

import java.awt.Graphics;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.entities.EntityManager;
import dev.ipsych0.mygame.entities.creatures.Player;
import dev.ipsych0.mygame.entities.creatures.Scorpion;
import dev.ipsych0.mygame.entities.npcs.ChatWindow;
import dev.ipsych0.mygame.entities.npcs.Lorraine;
import dev.ipsych0.mygame.entities.statics.Rock;
import dev.ipsych0.mygame.entities.statics.TeleportShrine1;
import dev.ipsych0.mygame.entities.statics.TeleportShrine2;
import dev.ipsych0.mygame.entities.statics.Tree;
import dev.ipsych0.mygame.gfx.Animation;
import dev.ipsych0.mygame.gfx.Assets;
import dev.ipsych0.mygame.items.ItemManager;
import dev.ipsych0.mygame.mapeditor.MapLoader;
import dev.ipsych0.mygame.mapeditor.MiniMap;
import dev.ipsych0.mygame.tiles.Ambiance;
import dev.ipsych0.mygame.tiles.Tiles;

public class SwampLand extends World{
	
	// MiniMap
	protected MiniMap miniMap;
	
	// Entities
	
	protected EntityManager entityManager;
	
	// Items
	
	protected ItemManager itemManager;

	public SwampLand(Handler handler, String path) {
		super(handler, path);
		this.handler = handler;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		
	}
}
