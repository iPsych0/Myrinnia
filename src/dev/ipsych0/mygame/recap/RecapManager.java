package dev.ipsych0.mygame.recap;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;

import dev.ipsych0.mygame.Handler;

public class RecapManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<RecapEvent> events;
	
	public RecapManager() {
		this.events = new LinkedList<RecapEvent>();
	}
	
	public void addEvent(RecapEvent event) {
		if(events.size() >= 3) {
			events.addLast(event);
			events.removeFirst();
		}else {
			events.addLast(event);
		}
	}

	public LinkedList<RecapEvent> getEvents() {
		return events;
	}

	public void setEvents(LinkedList<RecapEvent> events) {
		this.events = events;
	}

}
