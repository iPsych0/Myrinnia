package dev.ipsych0.myrinnia.pathfinding;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
public class Node implements Serializable {


    private static final long serialVersionUID = 959446737334137173L;
    @EqualsAndHashCode.Include
    private int x;
    @EqualsAndHashCode.Include
    private int y;
    private static final int MOVEMENT_COST = 10;
    @EqualsAndHashCode.Include
    private boolean walkable;
    private Node parent;
    private int g, h;

    public Node(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
    }

    public void setG(Node parent) {
        g = (parent.getG() + MOVEMENT_COST);
    }

    public int calculateG(Node parent) {
        return (parent.getG() + MOVEMENT_COST);
    }

    public void setH(Node goal) {
        h = calcHeuristic(goal) * MOVEMENT_COST;
    }

    private int calcHeuristic(Node goal) {
        return Math.max(Math.abs(x - goal.x), Math.abs(y - goal.y));
    }

    public int getF() {
        return g + h;
    }


}
