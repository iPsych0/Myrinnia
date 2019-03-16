package dev.ipsych0.myrinnia.pathfinding;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.statics.StaticEntity;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AStarMap implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -2351067336940681663L;
    private int x, y, width, height, xSpawn, ySpawn;
    private Node[][] nodes;
    private Color unwalkableColour = new Color(255, 0, 0, 127);
    private Rectangle mapBounds;
    private Creature creature;
    public static boolean debugMode = false;

    public AStarMap(Creature creature, int x, int y, int width, int height, int xSpawn, int ySpawn) {
        this.creature = creature;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xSpawn = xSpawn;
        this.ySpawn = ySpawn;

        // Aantal nodes aanpassen dan?
        nodes = new Node[(int) (Math.floor(width / 32)) + 1][(int) (Math.floor(height / 32)) + 1];
        mapBounds = new Rectangle(x, y, width, height);
    }

    public void init() {
        mapBounds = new Rectangle(x, y, width, height);
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                nodes[i][j] = new Node(((i * 32) + x) / 32, ((j * 32) + y) / 32, true);
            }
        }
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                if (creature.collisionWithTile(((int) Math.floor((i * 32) + x) / 32), (int) Math.floor((j * 32) + y) / 32)) {
                    nodes[i][j].setWalkable(false);
                }
            }
        }

        for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
            if (e instanceof StaticEntity) {
                if (mapBounds.contains(e.getX(), e.getY()) && e.isSolid()) {
                    nodes[(int) Math.round(((e.getX()) / 32)) - (int) (x) / 32][(int) Math.round(((e.getY()) / 32)) - (int) (y) / 32].setWalkable(false);
                }
            }
        }

    }

    public void tick() {

    }

    public void render(Graphics g) {
        for (int i = 0; i < nodes.length - 1; i++) {
            for (int j = 0; j < nodes.length - 1; j++) {
                if (nodes[i][j] == null) {
                    return;
                }
                if (nodes[i][j].isWalkable()) {
                    g.setColor(Color.RED);
                    g.drawRect((int) (nodes[i][j].getX() * 32 - Handler.get().getGameCamera().getxOffset()), (int) (nodes[i][j].getY() * 32 - Handler.get().getGameCamera().getyOffset()), 32, 32);
                } else {
                    g.setColor(unwalkableColour);
                    g.fillRect((int) (nodes[i][j].getX() * 32 - Handler.get().getGameCamera().getxOffset()), (int) (nodes[i][j].getY() * 32 - Handler.get().getGameCamera().getyOffset()), 32, 32);
                }
            }
        }
    }

    public final List<Node> findPath(int startX, int startY, int goalX, int goalY) {

//		System.out.println("=================================");
//		System.out.println("Scorpion X: "+startX);
//		System.out.println("Scorpion Y: "+startY);
//		System.out.println("Player X: "+goalX);
//		System.out.println("Player Y: "+goalY);
//		System.out.println("=================================");
//		
//		System.out.println(nodes.length);

        if (startX <= -1) {
            creature.setxMove(creature.getSpeed());
            creature.move();
            return new ArrayList<Node>();
        } else if (startX >= nodes.length) {
            creature.setxMove(-creature.getSpeed());
            creature.move();
            return new ArrayList<Node>();
        }
        if (startY <= -1) {
            creature.setyMove(creature.getSpeed());
            creature.move();
            return new ArrayList<Node>();
        } else if (startY >= nodes.length) {
            creature.setyMove(-creature.getSpeed());
            creature.move();
            return new ArrayList<Node>();
        }

        if (goalX >= nodes.length - 1 || goalX < 0 || goalY >= nodes.length - 1 || goalY < 0) {
            goalX = (int) (xSpawn / 32 - x / 32);
            goalY = (int) (ySpawn / 32 - y / 32);
        }

        // If the goal node is standing on a non-walkable tile
        if (!nodes[goalX][goalY].isWalkable()) {
            creature.setState(CombatState.BACKTRACK);
            goalX = (int) (xSpawn / 32 - x / 32);
            goalY = (int) (ySpawn / 32 - y / 32);
        }


        // If our start pause is the same as our goal pause ...
        if (startX == goalX && startY == goalY) {
            creature.setState(CombatState.IDLE);
            // Return an empty path, because we don't need to move at all.
            return new ArrayList<Node>();
        }

        // The set of nodes already visited.
        List<Node> openList = new ArrayList<Node>();
        // The set of currently discovered nodes still to be visited.
        HashSet<Node> closedList = new HashSet<Node>();

        // Add starting node to open list.
        openList.add(nodes[startX][startY]);

        // This loop will be broken as soon as the current node pause is
        // equal to the goal pause.
        while (true) {
            // Gets node with the lowest F score from open list.
            Node current = lowestFInList(openList);
            // Remove current node from open list.
            openList.remove(current);
            // Add current node to closed list.
            closedList.add(current);

            // If the current node pause is equal to the goal pause ...
            if ((current.getX() - (this.x / 32) == goalX) && (current.getY() - (this.y / 32) == goalY)) {
                // Return a ArrayList containing all of the visited nodes.
                return calcPath(nodes[startX][startY], current);
            }

            HashSet<Node> adjacentNodes = getAdjacent(current, closedList);
            for (Node adjacent : adjacentNodes) {
                // If node is not in the open list ...
                if (!openList.contains(adjacent)) {
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
                else if (adjacent.getG() > adjacent.calculateG(current)) {
                    // Set current node as parent for this node.
                    adjacent.setParent(current);
                    // Set G costs of this node (costs from start to this node).
                    adjacent.setG(current);
                }
            }

            // If no path exists ...
            if (openList.isEmpty()) {
                // Return an empty list.
                return null;
            }
            // But if it does, continue the loop.
        }
    }

    private List<Node> calcPath(Node start, Node goal) {
        ArrayList<Node> path = new ArrayList<Node>();

        Node node = goal;
        boolean done = false;
        while (!done) {
            path.add(0, node);
            node = node.getParent();
            if (node == null || start == null) {
                return null;
            }
            if (node.equals(start)) {
                done = true;
            }
        }

        return path;
    }

    private Node lowestFInList(List<Node> list) {
        Node cheapest = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getF() < cheapest.getF()) {
                cheapest = list.get(i);
            }
        }
        return cheapest;
    }

    private HashSet<Node> getAdjacent(Node node, HashSet<Node> closedList) {
        HashSet<Node> adjacentNodes = new HashSet<Node>();
        int x = node.getX() - (this.x / 32);
        int y = node.getY() - (this.y / 32);

//		System.out.println(x + " and " + y);
//		System.out.println(this.x + " <--------------------- de X van de map");
//		System.out.println(this.y + " <--------------------- de Y van de map");

        Node adjacent;

        // Check left node
        if (x > 0) {
            adjacent = getNode(x - 1, y);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent)) {
                adjacentNodes.add(adjacent);
            }
        }

        // Check right node
        if (x < nodes.length) {
            adjacent = getNode(x + 1, y);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent)) {
                adjacentNodes.add(adjacent);
            }
        }

        // Check top node
        if (y > 0) {
            adjacent = this.getNode(x, y - 1);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent)) {
                adjacentNodes.add(adjacent);
            }
        }

        // Check bottom node
        if (y < nodes.length) {
            adjacent = this.getNode(x, y + 1);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent)) {
                adjacentNodes.add(adjacent);
            }
        }
        return adjacentNodes;
    }

    public Node getNode(int x, int y) {
        if (x >= 0 && x < nodes.length - 1 && y >= 0 && y < nodes.length - 1) {
            return nodes[x][y];
        } else {
            return null;
        }
    }

    public Rectangle getMapBounds() {
        return mapBounds;
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
