package dev.ipsych0.myrinnia.cutscenes;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.pathfinding.Node;
import dev.ipsych0.myrinnia.utils.Colors;

import java.awt.*;
import java.util.List;

public class MoveEntityEvent implements CutsceneEvent {

    private boolean finished;

    private Entity entity;
    private double startX, startY;
    private double goalX, goalY;
    private boolean instantly;
    private Creature creature;
    private List<Node> nodes;
    private int lastX, lastY;
    private int stuckTimerX = 0, stuckTimerY = 0;
    private double originalMovSpd;
    private double movSpd;

    public MoveEntityEvent(Entity entity, double goalX, double goalY, boolean instantly) {
        this.entity = entity;
        this.goalX = goalX;
        this.goalY = goalY;
        this.instantly = instantly;

        this.startX = entity.getX();
        this.startY = entity.getY();

        // Only set creature if it's an instance of Creature, otherwise null
        creature = (entity instanceof Creature) ? (Creature) entity : null;

        // Init A* map for creature
        if (creature != null) {
            // Temporarily set movement speed to 1.0 for even movement across the map
            originalMovSpd = creature.getSpeed();
            creature.setSpeed(1.0d);
            movSpd = creature.getSpeed();

            creature.getMap().init();
            nodes = creature.getMap().findPath(
                    (int) ((startX + creature.getWidth() / 4) / 32) - (creature.getxSpawn() - creature.getPathFindRadiusX()) / 32,
                    (int) ((startY + creature.getHeight() / 4) / 32) - (creature.getySpawn() - creature.getPathFindRadiusY()) / 32,
                    (int) (goalX / 32d) - (int) ((creature.getxSpawn() - creature.getPathFindRadiusX()) / 32d),
                    (int) (goalY / 32d) - (int) ((creature.getySpawn() - creature.getPathFindRadiusY()) / 32d));
        }

        lastX = (int) entity.getX();
        lastY = (int) entity.getY();
    }

    @Override
    public void tick() {
        if (instantly) {
            entity.setX(goalX);
            entity.setY(goalY);
            finished = true;
        } else {
            if (creature != null) {
                followAStar();
                creature.tickAnimation();
                if (nodes.isEmpty()) {
                    creature.setSpeed(originalMovSpd);
                    finished = true;
                }
            } else {
                if (entity.getX() != goalX) {
                    if (goalX < startX) {
                        // Move left
                        entity.setX(entity.getX() + -movSpd);
                    } else if (goalX > startX) {
                        // Move right
                        entity.setX(entity.getX() + movSpd);
                    }
                }
                if (entity.getY() != goalY) {
                    if (goalY < startY) {
                        // Move up
                        entity.setY(entity.getY() + -movSpd);
                    } else if (goalY > startY) {
                        // Move down
                        entity.setY(entity.getY() + movSpd);
                    }
                }

                if (entity.getX() == goalX && entity.getY() == goalY) {
                    finished = true;
                }
            }
        }
    }

    public void followAStar() {
        double x = creature.getX();
        double y = creature.getY();
        double speed = creature.getSpeed();

        if (nodes == null) {
            return;
        }
        if (nodes.size() <= 0) {
            finished = true;
            return;
        }

        Node next = nodes.get(0);

        int nextX = next.getX() * 32;
        int nextY = next.getY() * 32;

        if (x < nextX) {
            // Move right
            creature.setxMove(speed);
            creature.setyMove(0);
        } else if (x > nextX) {
            // Move right
            creature.setxMove(-speed);
            creature.setyMove(0);
        } else {
            // Stop moving, remove current node later to redetermine direction
            creature.setxMove(0);
            if (y == nextY) {
                creature.setyMove(0);
            }
        }

        if (y < nextY) {
            // Move down
            creature.setyMove(speed);
            creature.setxMove(0);
        } else if (y > nextY) {
            // Move up
            creature.setyMove(-speed);
            creature.setxMove(0);
        } else {
            // Stop moving, remove current node later to redetermine direction
            if (x == nextX) {
                creature.setxMove(0);
            }
            creature.setyMove(0);
        }

        if (creature.getxMove() == 0 && creature.getyMove() == 0) {
            if (!nodes.isEmpty())
                nodes.remove(0);
        }

        if (creature.getxMove() != 0 || creature.getyMove() != 0) {
            creature.move();
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (Handler.debugAStar) {
            g.setColor(Color.BLACK);

            creature.getMap().render(g);

            if (nodes != null) {
                for (Node n : nodes) {
                    g.setColor(Colors.pathFindingColor);
                    g.fillRect((int) (n.getX() * 32 - Handler.get().getGameCamera().getxOffset()), (int) (n.getY() * 32 - Handler.get().getGameCamera().getyOffset()), 32, 32);
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
