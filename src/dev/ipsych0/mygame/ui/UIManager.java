package dev.ipsych0.mygame.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.ipsych0.mygame.Handler;

public class UIManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Handler handler;
	private CopyOnWriteArrayList<UIObject> objects;
	
	public UIManager(Handler handler){
		this.handler = handler;
		objects = new CopyOnWriteArrayList<UIObject>();
	}
	
	public void tick(){
		for(UIObject o : objects)
			o.tick();
	}
	
	public void render(Graphics g){
		for(UIObject o : objects)
			o.render(g);
	}
	
	public void addObject(UIObject o){
		objects.add(o);
	}
	
	public void removeObject(UIObject o){
		objects.remove(o);
		}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public CopyOnWriteArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(CopyOnWriteArrayList<UIObject> objects) {
		this.objects = objects;
	}
}
