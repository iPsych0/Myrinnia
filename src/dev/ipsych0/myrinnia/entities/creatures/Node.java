package dev.ipsych0.myrinnia.entities.creatures;

import java.io.Serializable;

public class Node implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 959446737334137173L;
    private int x, y;
    private static final int MOVEMENT_COST = 10;
    private boolean walkable;
    private Node parent;
    private int g, h;

    public Node(int x, int y, boolean walkable) {
        super();
        this.x = x;
        this.y = y;
        this.walkable = walkable;
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

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getG() {
        return g;
    }

    public void setG(Node parent) {
        g = (parent.getG() + MOVEMENT_COST);
    }

    public int calculateG(Node parent) {
        return (parent.getG() + MOVEMENT_COST);
    }

    public int getH() {
        return h;
    }

    public void setH(Node goal) {
        h = calcHeuristic(goal) * MOVEMENT_COST;
    }

    public int calcHeuristic(Node goal) {
        int xDistance = Math.abs(getX() - goal.getX());
        int yDistance = Math.abs(getY() - goal.getY());
        return Math.max(xDistance, yDistance);
    }

    public int getF() {
        return g + h;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof Node))
            return false;
        if (o == this)
            return true;

        Node n = (Node) o;
        if (n.getX() == x && n.getY() == y && n.isWalkable() == walkable)
            return true;
        return false;
    }


}
