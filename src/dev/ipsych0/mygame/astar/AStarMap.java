package dev.ipsych0.mygame.astar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import dev.ipsych0.mygame.Handler;

public class AStarMap {
	
	private int x, y, width, height;
	private Handler handler;
	private Node[][] nodes;
	private int alpha = 127;
	private Color unwalkableColour = new Color(255, 0, 0, alpha);
	private Color startNodeColour = new Color(0, 0, 255, 96);
	private Rectangle mapBounds;
	
	public AStarMap(Handler handler, int x, int y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		nodes = new Node[(int) (Math.floor(width / 32))][(int)(Math.floor(height / 32))];
		
		for(int i = 0; i < nodes.length; i++) {
			for(int j = 0; j < nodes.length; j++) {
				nodes[i][j] = new Node(((i * 32) + x) / 32, ((j * 32) + y) / 32, true);
			}
		}
		
		mapBounds = new Rectangle(x, y, width, height);
	}
	
	public void init() {
		for(int i = 0; i < nodes.length; i++) {
			for(int j = 0; j < nodes.length; j++) {
				if(handler.getWorld().checkSolidLayer(((int)Math.floor((i * 32) + x) / 32), (int)Math.floor((j * 32) + y) / 32)) {
					nodes[i][j].setWalkable(false);
				}
			}
		}
	}
	
	public void tick() {
		mapBounds = new Rectangle(x, y, width, height);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height);
		
		g.setColor(startNodeColour);
		g.fillRect((int)(x + width / 2 - handler.getGameCamera().getxOffset()), (int)(y + height / 2 - handler.getGameCamera().getyOffset()), 32, 32);
		
		for(int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes.length; j++) {
				if(nodes[i][j].isWalkable()) {
					g.setColor(Color.MAGENTA);
					g.drawRect((int)(nodes[i][j].getX() * 32 - handler.getGameCamera().getxOffset()), (int)(nodes[i][j].getY() * 32 - handler.getGameCamera().getyOffset()), 32, 32);
				}else {
					g.setColor(unwalkableColour);
					g.fillRect((int)(nodes[i][j].getX() * 32 - handler.getGameCamera().getxOffset()), (int)(nodes[i][j].getY() * 32 - handler.getGameCamera().getyOffset()), 32, 32);
				}
			}
		}
	}
	
	public final List<Node> findPath(int startX, int startY, int goalX, int goalY)
	{
//		System.out.println("=================================");
//		System.out.println("Scorpion X: "+startX);
//		System.out.println("Scorpion Y: "+startY);
//		System.out.println("Player X: "+goalX);
//		System.out.println("Player Y: "+goalY);
//		System.out.println("=================================");
//		
//		System.out.println(nodes.length);
		
		if(goalX > nodes.length - 1 || goalX < 0 || goalY > nodes.length - 1 || goalY < 0) {
			System.out.println("---CALCULATING PATH WHEN PLAYER IS OUTSIDE THE ASTARMAP BOUNDS---");
			return null;
		}
		
		System.out.println(nodes[goalX][goalY].getX());
		System.out.println(nodes[goalX][goalY].getY());
		
		if(handler.getWorld().checkSolidLayer(nodes[goalX][goalY].getX(), nodes[goalX][goalY].getY())) {
			return null;
		}
		
		
		// If our start position is the same as our goal position ...
		if (startX == goalX && startY == goalY)
		{
			// Return an empty path, because we don't need to move at all.
			return new LinkedList<Node>();
		}

		// The set of nodes already visited.
		List<Node> openList = new LinkedList<Node>();
		// The set of currently discovered nodes still to be visited.
		List<Node> closedList = new LinkedList<Node>();

		// Add starting node to open list.
		openList.add(nodes[startX][startY]);

		// This loop will be broken as soon as the current node position is
		// equal to the goal position.
		while (true)
		{
			// Gets node with the lowest F score from open list.
			Node current = lowestFInList(openList);
			// Remove current node from open list.
			openList.remove(current);
			// Add current node to closed list.
			closedList.add(current);

			// If the current node position is equal to the goal position ...
			if ((current.getX() - (this.x / 32) == goalX) && (current.getY() - (this.y / 32) == goalY))
			{
				// Return a LinkedList containing all of the visited nodes.
				return calcPath(nodes[startX][startY], current);
			}

			List<Node> adjacentNodes = getAdjacent(current, closedList);
			for (Node adjacent : adjacentNodes)
			{
				// If node is not in the open list ...
				if (!openList.contains(adjacent))
				{
					// Set current node as parent for this node.
					adjacent.setParent(current);
					// Set H costs of this node (estimated costs to goal).
					adjacent.setH(nodes[goalX][goalY]);
					// Set G costs of this node (costs from start to this node).
					adjacent.setG(current);
					// Add node to openList.
					openList.add(adjacent);
				}
				// Else if the node is in the open list and the G score from
				// current node is cheaper than previous costs ...
				else if (adjacent.getG() > adjacent.calculateG(current))
				{
					// Set current node as parent for this node.
					adjacent.setParent(current);
					// Set G costs of this node (costs from start to this node).
					adjacent.setG(current);
				}
			}

			// If no path exists ...
			if (openList.isEmpty())
			{
				// Return an empty list.
				return new LinkedList<Node>();
			}
			// But if it does, continue the loop.
		}
	}
	
	private List<Node> calcPath(Node start, Node goal)
	{
		LinkedList<Node> path = new LinkedList<Node>();

		Node node = goal;
		boolean done = false;
		while (!done)
		{
			path.addFirst(node);
			node = node.getParent();
			if (node.equals(start))
			{
				done = true;
			}
		}
		return path;
	}
	
	private Node lowestFInList(List<Node> list)
	{
		Node cheapest = list.get(0);
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).getF() < cheapest.getF())
			{
				cheapest = list.get(i);
			}
		}
		return cheapest;
	}
	
	private List<Node> getAdjacent(Node node, List<Node> closedList)
	{
		List<Node> adjacentNodes = new LinkedList<Node>();
		int x = node.getX() - (this.x / 32);
		int y = node.getY() - (this.y / 32);
		
//		System.out.println(x + " and " + y);
//		System.out.println(this.x + " <--------------------- de X van de map");
//		System.out.println(this.y + " <--------------------- de Y van de map");

		Node adjacent;

		// Check left node
		if (x > 0)
		{
			adjacent = getNode(x - 1, y);
			if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent))
			{
				adjacentNodes.add(adjacent);
			}
		}

		// Check right node
		if (x < nodes.length)
		{
			adjacent = getNode(x + 1, y);
			if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent))
			{
				adjacentNodes.add(adjacent);
			}
		}

		// Check top node
		if (y > 0)
		{
			adjacent = this.getNode(x, y - 1);
			if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent))
			{
				adjacentNodes.add(adjacent);
			}
		}

		// Check bottom node
		if (y < nodes.length)
		{
			adjacent = this.getNode(x, y + 1);
			if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent))
			{
				adjacentNodes.add(adjacent);
			}
		}
		return adjacentNodes;
	}
	
	public Node getNode(int x, int y)
	{
		if (x >= 0 && x < nodes.length && y >= 0 && y < nodes.length)
		{
			return nodes[x][y];
		}
		else
		{
			return null;
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

	public Node[][] getNodes() {
		return nodes;
	}

	public void setNodes(Node[][] nodes) {
		this.nodes = nodes;
	}

	public void setMapBounds(Rectangle mapBounds) {
		this.mapBounds = mapBounds;
	}
	
	
	
	

}
