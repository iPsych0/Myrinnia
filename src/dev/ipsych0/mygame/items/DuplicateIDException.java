package dev.ipsych0.mygame.items;

public class DuplicateIDException extends Exception{

	public DuplicateIDException(String name, int id) throws DuplicateIDException {
		super("Item: " + name + " with ID: " + id + " already exists. Please pick a unique ID!");
		throw this;
	}
}
