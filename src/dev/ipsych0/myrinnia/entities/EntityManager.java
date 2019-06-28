package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.creatures.Projectile;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.*;

public class EntityManager implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4034952590793061132L;
    private Player player;
    private List<Entity> entities;
    private Collection<Entity> deadEntities;
    private Entity selectedEntity;
    public static boolean isPressed = false;
    private List<HitSplat> hitSplats;

    public EntityManager(Player player) {
        this.player = player;
        entities = Collections.synchronizedList(new ArrayList<>());
        deadEntities = Collections.synchronizedList(new ArrayList<>());
        hitSplats = Collections.synchronizedList(new ArrayList<>());
        addEntity(player);
    }

    public void tick() {
        // Iterate over all Entities and remove inactive ones
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();
            if (!e.isActive()) {
                deadEntities.add(e);
            }

            Rectangle mouse = Handler.get().getMouse();

            if (e instanceof Creature) {
                Iterator<Condition> condIt = ((Creature) e).getConditions().iterator();
                while (condIt.hasNext()) {
                    Condition c = condIt.next();
                    if (c.isActive()) {
                        c.tick();
                    } else {
                        condIt.remove();
                    }
                }
                Iterator<Buff> buffIt = ((Creature) e).getBuffs().iterator();
                while (buffIt.hasNext()) {
                    Buff b = buffIt.next();
                    if (b.isActive()) {
                        b.tick();
                    } else {
                        buffIt.remove();
                    }
                }
            }

            e.tick();
            // Update combat timers
            if (e.isDamaged() && e.getDamageDealer() != null) {
                e.updateCombatTimer();
            }

            // If we rightclick an Entity, lock it to the top of the screen.
            if (!isPressed && e.getCollisionBounds(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(mouse) && !e.equals(Handler.get().getPlayer()) && Handler.get().getMouseManager().isRightPressed()) {
                isPressed = true;
                if (e.isOverlayDrawn())
                    selectedEntity = e;
            }

            // If we clicked away, remove the locked UI component
            if (selectedEntity != null && !e.getCollisionBounds(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(mouse) &&
                    !Handler.get().getPlayer().hasRightClickedUI(mouse) &&
                    !e.equals(Handler.get().getPlayer()) && Handler.get().getMouseManager().isRightPressed() && !isPressed) {
                isPressed = true;
                selectedEntity = null;

                // Check if we're clicking on another Entity
                for (Entity e2 : entities) {
                    if (e2.getCollisionBounds(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(mouse)) {
                        selectedEntity = e2;
                        break;
                    }
                }
            }
        }

        // If enemies are dead, update the respawn timers
        if (deadEntities.size() > 0) {
            entities.removeAll(deadEntities);
            Iterator<Entity> dltd = deadEntities.iterator();
            while (dltd.hasNext()) {
                Entity e = dltd.next();
                e.startRespawnTimer();
                if (e.getRespawnTimer() == 0) {
                    e.respawn();
                    dltd.remove();
                }
            }
        }

        synchronized (entities) {
            // Sort the list for rendering
            entities.sort((o1, o2) -> {
                Float a = o1.getY() + o1.getHeight();
                Float b = o2.getY() + o2.getHeight();
                return a.compareTo(b);
            });
        }
    }

    public void render(Graphics2D g) {
        synchronized (entities) {
            Iterator<Entity> entityIt = entities.iterator();
            while (entityIt.hasNext()) {
                Entity e = entityIt.next();

                if (e.getDamageReceiver() != null && Handler.get().getPlayer().isInCombat()) {
                    if (e.isAttackable())
                        e.drawHP(g);
                }

                e.render(g);

                if (e instanceof Creature) {
                    for (Projectile p : ((Creature) e).getProjectiles()) {
                        if (p.active) {
                            p.render(g);
                        }
                    }
                }

                if (!e.equals(Handler.get().getPlayer())) {
                    e.postRender(g);
                }
            }

            synchronized (hitSplats) {
                Iterator<HitSplat> hitSplatIt = hitSplats.iterator();
                while (hitSplatIt.hasNext()) {
                    HitSplat hs = hitSplatIt.next();
                    if (hs.isActive()) {
                        hs.render(g);
                    } else {
                        hitSplatIt.remove();
                    }
                }
            }
        }
    }

    private void drawHoverCorners(Graphics2D g, Entity e, int xOffset, int yOffset, Color color) {
        Stroke defaultStroke = g.getStroke();

        g.setColor(color);
        g.setStroke(new BasicStroke(2));

        // Top left corner
        g.drawLine((int) (xOffset + e.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + e.getX() + (6 * (e.getWidth() / 32)) - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() - Handler.get().getGameCamera().getyOffset()));
        g.drawLine((int) (xOffset + e.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + e.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() + (6 * (e.getHeight() / 32)) - Handler.get().getGameCamera().getyOffset()));

        // Top right corner
        g.drawLine((int) (xOffset + e.getX() + e.getWidth() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + e.getX() + e.getWidth() - (6 * (e.getWidth() / 32)) - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() - Handler.get().getGameCamera().getyOffset()));
        g.drawLine((int) (xOffset + e.getX() + e.getWidth() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + e.getX() + e.getWidth() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() + (6 * (e.getHeight() / 32)) - Handler.get().getGameCamera().getyOffset()));

        // Bottom left corner
        g.drawLine((int) (xOffset + e.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() + e.getHeight() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + e.getX() + (6 * (e.getWidth() / 32)) - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() + e.getHeight() - Handler.get().getGameCamera().getyOffset()));
        g.drawLine((int) (xOffset + e.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() + e.getHeight() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + e.getX() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() + e.getHeight() - (6 * (e.getHeight() / 32)) - Handler.get().getGameCamera().getyOffset()));

        // Bottom right corner
        g.drawLine((int) (xOffset + e.getX() + e.getWidth() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() + e.getHeight() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + e.getX() + e.getWidth() - (6 * (e.getWidth() / 32)) - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() + e.getHeight() - Handler.get().getGameCamera().getyOffset()));
        g.drawLine((int) (xOffset + e.getX() + e.getWidth() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() + e.getHeight() - Handler.get().getGameCamera().getyOffset()), (int) (xOffset + e.getX() + e.getWidth() - Handler.get().getGameCamera().getxOffset()), (int) (yOffset + e.getY() + e.getHeight() - (6 * (e.getHeight() / 32)) - Handler.get().getGameCamera().getyOffset()));

        g.setStroke(defaultStroke);
    }

    public Entity getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(Entity selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public void postRender(Graphics2D g) {
        // Post renders for entities for additional
        player.postRender(g);

        // Keep rendering the selected Entity
        if (selectedEntity != null) {
            if (selectedEntity.active) {
                if (selectedEntity.isNpc()) {
                    drawHoverCorners(g, selectedEntity, 1, 1, Color.BLACK);
                    drawHoverCorners(g, selectedEntity, 0, 0, Color.YELLOW);
                } else if (selectedEntity.isAttackable()) {
                    drawHoverCorners(g, selectedEntity, 1, 1, Color.BLACK);
                    drawHoverCorners(g, selectedEntity, 0, 0, Color.RED);
                }
                selectedEntity.drawEntityOverlay(selectedEntity, g);
            } else {
                selectedEntity = null;
            }
        }

        synchronized (entities) {
            Iterator<Entity> it = entities.iterator();
            while (it.hasNext()) {
                Entity e = it.next();
                // If the mouse is hovered over an Entity, draw the overlay
                if (!e.equals(Handler.get().getPlayer()) && e.getCollisionBounds(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(Handler.get().getMouse())) {

                    // If Entity can be interacted with, show corner pieces on hovering
                    if (e.isNpc()) {
                        drawHoverCorners(g, e, 1, 1, Color.BLACK);
                        drawHoverCorners(g, e, 0, 0, Color.YELLOW);
                    } else if (e.isAttackable()) {
                        drawHoverCorners(g, e, 1, 1, Color.BLACK);
                        drawHoverCorners(g, e, 0, 0, Color.RED);
                    }
                    if (e.isOverlayDrawn()) {
                        e.drawEntityOverlay(e, g);
                    }
                }
            }

        }
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    // Getters & Setters

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<HitSplat> getHitSplats() {
        return hitSplats;
    }
}
