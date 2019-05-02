package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.creatures.Projectile;

import java.awt.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityManager implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4034952590793061132L;
    private Player player;
    private CopyOnWriteArrayList<Entity> entities;
    private Collection<Entity> deleted;
    private Entity selectedEntity;
    public static boolean isPressed = false;
    private CopyOnWriteArrayList<HitSplat> hitSplats;

    public EntityManager(Player player) {
        this.player = player;
        entities = new CopyOnWriteArrayList<>();
        deleted = new CopyOnWriteArrayList<>();
        hitSplats = new CopyOnWriteArrayList<>();
        addEntity(player);
    }

    public void tick() {
        // Iterate over all Entities and remove inactive ones
        Iterator<Entity> it = entities.iterator();
        Collection<Condition> deletedCondis = new CopyOnWriteArrayList<>();
        Collection<Buff> deletedBuffs = new CopyOnWriteArrayList<>();
        while (it.hasNext()) {
            Entity e = it.next();
            if (!e.isActive()) {
                deleted.add(e);
            }

            Rectangle mouse = Handler.get().getMouse();

            if (e instanceof Creature) {
                for (Condition c : ((Creature) e).getConditions()) {
                    if (c.isActive()) {
                        c.tick();
                    } else {
                        deletedCondis.add(c);
                    }
                }
                ((Creature) e).getConditions().removeAll(deletedCondis);
                deletedCondis.clear();
                for (Buff b : ((Creature) e).getBuffs()) {
                    if (b.isActive()) {
                        b.tick();
                    } else {
                        deletedBuffs.add(b);
                    }
                }
                ((Creature) e).getBuffs().removeAll(deletedBuffs);
                deletedBuffs.clear();
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
        if (deleted.size() > 0) {
            entities.removeAll(deleted);
            for (Entity e : deleted) {
                e.startRespawnTimer();
                if (e.getRespawnTimer() == 0) {
                    e.respawn();
                    deleted.remove(e);
                }
            }
        }

        // Sort the list for rendering
        entities.sort((o1, o2) -> {
            Float a = o1.getY() + o1.getHeight();
            Float b = o2.getY() + o2.getHeight();
            return a.compareTo(b);
        });
    }

    public void render(Graphics2D g) {
        for (Entity e : entities) {

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

        Collection<HitSplat> deleted = new CopyOnWriteArrayList<>();
        for (HitSplat hs : hitSplats) {
            if (hs.isActive()) {
                hs.render(g);
            } else {
                deleted.add(hs);
            }
        }
        hitSplats.removeAll(deleted);

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
                selectedEntity.drawEntityOverlay(selectedEntity, g);
                if (selectedEntity.isNpc()) {
                    drawHoverCorners(g, selectedEntity, 1, 1, Color.BLACK);
                    drawHoverCorners(g, selectedEntity, 0, 0, Color.YELLOW);
                } else if (selectedEntity.isAttackable()) {
                    drawHoverCorners(g, selectedEntity, 1, 1, Color.BLACK);
                    drawHoverCorners(g, selectedEntity, 0, 0, Color.RED);
                }
            } else {
                selectedEntity = null;
            }
        }

        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();
            // If the mouse is hovered over an Entity, draw the overlay
            if (!e.equals(Handler.get().getPlayer()) && e.getCollisionBounds(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(Handler.get().getMouse())) {
                if (e.isOverlayDrawn()) {
                    e.drawEntityOverlay(e, g);
                }

                // If Entity can be interacted with, show corner pieces on hovering
                if (e.isNpc()) {
                    drawHoverCorners(g, e, 1, 1, Color.BLACK);
                    drawHoverCorners(g, e, 0, 0, Color.YELLOW);
                } else if (e.isAttackable()) {
                    drawHoverCorners(g, e, 1, 1, Color.BLACK);
                    drawHoverCorners(g, e, 0, 0, Color.RED);
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

    public CopyOnWriteArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(CopyOnWriteArrayList<Entity> entities) {
        this.entities = entities;
    }

    public CopyOnWriteArrayList<HitSplat> getHitSplats() {
        return hitSplats;
    }
}
