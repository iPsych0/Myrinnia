package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Game;
import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.entities.creatures.Projectile;
import dev.ipsych0.myrinnia.entities.statics.FishingSpot;
import dev.ipsych0.myrinnia.entities.statics.Rock;
import dev.ipsych0.myrinnia.entities.statics.Tree;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.pathfinding.CombatState;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class EntityManager implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4034952590793061132L;
    private Player player;
    private List<Entity> entities;
    private Collection<Entity> deadEntities;
    private List<Entity> toBeAddedEntities;
    private Entity selectedEntity;
    public static boolean isPressed = false;
    private List<HitSplat> hitSplats;
    private int oocCounter; // Out-of-combat counter
    private int creatureCounter;
    public static boolean shiftPressed;
    private static Entity hoveringEntity;

    public EntityManager(Player player) {
        this.player = player;
        entities = new ArrayList<>();
        deadEntities = new ArrayList<>();
        toBeAddedEntities = new ArrayList<>();
        hitSplats = new ArrayList<>();
        addEntity(player);
    }

    public void tick() {
        oocCounter = 0;
        creatureCounter = 0;

        if (entities.addAll(toBeAddedEntities)) {
            toBeAddedEntities.clear();
        }

        // Iterate over all Entities and remove inactive ones
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {

            Entity e = it.next();
            if (!e.isActive()) {
                e.setTimeOfDeath(System.currentTimeMillis());
                deadEntities.add(e);
            }

            Rectangle mouse = Handler.get().getMouse();

            // Update buffs, conditions and immunities
            if (e.isActive()) {
                if (e instanceof Creature) {
                    Creature c = ((Creature) e);

                    if (c.isAttackable()) {
                        updateBuffsCondisAndImmunities(c);
                        creatureCounter++;

                        if (!c.equals(player) && c.getState() != CombatState.PATHFINDING && c.getState() != CombatState.ATTACK && !c.isInCombat()) {
                            c.regenHealth();
                            oocCounter++;
                        }
                    }
                }
            }

            // If all creatures are out of combat, regen health
            if (!player.isInCombat() && (creatureCounter - 1) == oocCounter) {
                player.regenHealth();
            }

            e.tick();

            // Update combat timers
            if (e.isDamaged() && e.getDamageDealer() != null) {
                e.updateCombatTimer();
            }

            // If we rightclick an Entity, lock it to the top of the screen.
            if (!isPressed && e.getFullBounds(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(mouse) && !e.equals(Handler.get().getPlayer()) && Handler.get().getMouseManager().isRightPressed()) {
                isPressed = true;
                if (e.isOverlayDrawn())
                    selectedEntity = e;
            }

            // If we clicked away, remove the locked UI component
            if (selectedEntity != null && !e.getFullBounds(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(mouse) &&
                    !Handler.get().getPlayer().hasRightClickedUI(mouse) &&
                    !e.equals(Handler.get().getPlayer()) && Handler.get().getMouseManager().isRightPressed() && !isPressed) {
                isPressed = true;
                selectedEntity = null;

                // Check if we're clicking on another Entity
                for (Entity e2 : entities) {
                    if (e2.equals(player))
                        continue;
                    if (e2.getFullBounds(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(mouse)) {
                        selectedEntity = e2;
                        break;
                    }
                }
            }
        }

        // If enemies are dead, update the respawn timers
        if (deadEntities.size() > 0) {
            long currentTime = System.currentTimeMillis();
            entities.removeAll(deadEntities);
            Iterator<Entity> dltd = deadEntities.iterator();
            while (dltd.hasNext()) {
                Entity e = dltd.next();
                if (e.isRespawner() && ((currentTime - e.getTimeOfDeath()) / 1000L) >= e.getRespawnTime()) {
                    e.respawn();
                    dltd.remove();
                } else if (!e.isRespawner()) {
                    // If the enemy doesn't respawn, stop checking
                    dltd.remove();
                }
            }
        }
    }

    private void updateBuffsCondisAndImmunities(Creature c) {
        if (!c.isActive()) {
            c.clearBuffs();
            c.clearConditions();
            return;
        }
        Iterator<Condition> condIt = c.getConditions().iterator();
        while (condIt.hasNext()) {
            Condition condi = condIt.next();
            if (condi.isActive()) {
                condi.tick();
            } else {
                condIt.remove();
            }
        }
        Iterator<Buff> buffIt = c.getBuffs().iterator();
        while (buffIt.hasNext()) {
            Buff b = buffIt.next();
            if (b.isActive()) {
                b.tick();
            } else {
                buffIt.remove();
            }
        }

        Iterator<Resistance> immunIt = c.getImmunities().iterator();
        while (immunIt.hasNext()) {
            Resistance i = immunIt.next();
            if (i.isActive()) {
                i.tick();
            } else {
                immunIt.remove();
            }
        }
    }

    public void render(Graphics2D g) {
        for (Entity e : entities) {
            if (e.getDamageReceiver() != null && Handler.get().getPlayer().isInCombat()) {
                if (e.isAttackable()) {
                    e.drawHP(g);
                }
            }

            e.render(g);

            if (e instanceof Creature) {
                for (Projectile p : ((Creature) e).getProjectiles()) {
                    if (p.active) {
                        p.render(g);
                    }
                }

                // Render post render tiles for this creature
                for (Tile t : ((Creature) e).getPostRenderTiles().keySet()) {
                    Point point = ((Creature) e).getPostRenderTiles().get(t);
                    t.render(g, (int) (point.getX() * 32 - Handler.get().getGameCamera().getxOffset()), (int) (point.getY() * 32 - Handler.get().getGameCamera().getyOffset()));
                }
            }

            // Draw the interaction bounds
//            Rectangle bounds = e.getInteractionBounds(-40, -40, 80, 128);
//            g.drawRect(bounds.x - (int) Handler.get().getGameCamera().getxOffset(), bounds.y - (int) Handler.get().getGameCamera().getyOffset(), bounds.width, bounds.height);


//            if (!e.equals(Handler.get().getPlayer())) {
//                e.postRender(g);
//            }
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
        // Keep rendering the selected Entity
        if (selectedEntity != null) {
            if (selectedEntity.active && selectedEntity.isOverlayDrawn()) {
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

        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();

            if (!e.equals(player)) {
                e.postRender(g);
                drawLevel(g, e);
            }

            // If the mouse is hovered over an Entity, draw the overlay
            if (!e.equals(Handler.get().getPlayer()) && e.isOverlayDrawn() && e.getFullBounds(-Handler.get().getGameCamera().getxOffset(), -Handler.get().getGameCamera().getyOffset()).contains(Handler.get().getMouse())) {
                // If Entity can be interacted with, show corner pieces on hovering
                if (e.isNpc()) {
                    hoveringEntity = e;
                    if (!Handler.get().getCursor().equals(Game.normalCursorHighlight)) {
                        Handler.get().changeCursor(Game.normalCursorHighlight);
                    }
                    drawHoverCorners(g, e, 1, 1, Color.BLACK);
                    drawHoverCorners(g, e, 0, 0, Color.YELLOW);
                    drawChatBubble(g, e);
                } else if (e.isAttackable()) {
                    hoveringEntity = e;
                    if (!Handler.get().getCursor().equals(Game.attackCursor)) {
                        Handler.get().changeCursor(Game.attackCursor);
                    }
                    drawHoverCorners(g, e, 1, 1, Color.BLACK);
                    drawHoverCorners(g, e, 0, 0, Color.RED);
                }
                e.drawEntityOverlay(e, g);

            } else {
                if (hoveringEntity != null && hoveringEntity.equals(e)) {
                    hoveringEntity = null;
                    Handler.get().changeCursor(Game.normalCursor);
                }
                // Skip the player
                if (e.equals(player)) {
                    continue;
                }

                // If we're not hovering, check if Entity is standing on postRender tile and draw the overlay anyway
                int layers = Handler.get().getWorld().getLayers().length;
                boolean shouldRender = false;
                for (int i = 0; i < layers; i++) {
                    Tile currentTile = Handler.get().getWorld().getTile(i, (int) (e.getX() + e.getWidth() / 2) / 32, (int) (e.getY() + e.getHeight() / 2) / 32);
                    if (currentTile != null && currentTile.isPostRendered()) {
                        shouldRender = true;
                        break;
                    }
                }

                if (shouldRender) {
                    if (e.isNpc()) {
                        drawHoverCorners(g, e, 1, 1, Color.BLACK);
                        drawHoverCorners(g, e, 0, 0, Color.YELLOW);
                    } else if (e.isAttackable()) {
                        drawHoverCorners(g, e, 1, 1, Color.BLACK);
                        drawHoverCorners(g, e, 0, 0, Color.RED);
                    }
                }
            }

            if (shiftPressed) {
                if (e.equals(player)) continue;
                if (e.isNpc()) {
                    drawHoverCorners(g, e, 1, 1, Color.BLACK);
                    drawHoverCorners(g, e, 0, 0, Color.YELLOW);
                } else if (e.isAttackable()) {
                    drawHoverCorners(g, e, 1, 1, Color.BLACK);
                    drawHoverCorners(g, e, 0, 0, Color.RED);
                }
            }
        }

        Iterator<HitSplat> hitSplatIt = hitSplats.iterator();
        while (hitSplatIt.hasNext()) {
            HitSplat hs = hitSplatIt.next();
            if (hs.isActive()) {
                hs.render(g);
            } else {
                hitSplatIt.remove();
            }
        }

        // Sort the list for rendering
        entities.sort((o1, o2) -> {
            Double a = o1.getY() + o1.getHeight();
            Double b = o2.getY() + o2.getHeight();
            return a.compareTo(b);
        });
    }

    private void drawChatBubble(Graphics2D g, Entity e) {
        if (e instanceof Rock || e instanceof Tree || e instanceof FishingSpot)
            return;

        int xPos = (e.width == Creature.DEFAULT_CREATURE_WIDTH) ? (int) e.x : (int) e.x + (e.width / 32) * 16 - 16;
        g.drawImage(Assets.chatBubble, (int) (xPos - Handler.get().getGameCamera().getxOffset()),
                (int) (e.y - 32 - Handler.get().getGameCamera().getyOffset()),
                32, 32, null);
    }

    /**
     * Changes all pixels of an old color into a new color, preserving the
     * alpha channel.
     */
    private static void changeColor(
            BufferedImage imgBuf,
            int oldRed, int oldGreen, int oldBlue,
            int newRed, int newGreen, int newBlue) {

        int RGB_MASK = 0x00ffffff;
        int ALPHA_MASK = 0xff000000;

        int oldRGB = oldRed << 16 | oldGreen << 8 | oldBlue;
        int toggleRGB = oldRGB ^ (newRed << 16 | newGreen << 8 | newBlue);

        int w = imgBuf.getWidth();
        int h = imgBuf.getHeight();

        int[] rgb = imgBuf.getRGB(0, 0, w, h, null, 0, w);
        for (int i = 0; i < rgb.length; i++) {
            if ((rgb[i] & RGB_MASK) == oldRGB) {
                rgb[i] ^= toggleRGB;
            }
        }
        imgBuf.setRGB(0, 0, w, h, rgb, 0, w);
    }

    private void drawLevel(Graphics2D g, Entity e) {
        if (!e.isAttackable() || !(e instanceof Creature)) {
            return;
        }

        Creature c = (Creature) e;
        int level = c.getCombatLevel();
        int playerLvl = Handler.get().getPlayer().getCombatLevel();
        int levelDiff = (playerLvl - level);
        Color color = getColorByLvlDiff(levelDiff);

        Text.drawString(g, "Lv. " + level,
                (int) (e.getX() + e.getWidth() / 2d - Handler.get().getGameCamera().getxOffset()),
                (int) (e.getY() + e.getHeight() + 8 - Handler.get().getGameCamera().getyOffset()),
                true, color, Assets.font14);
    }

    private Color getColorByLvlDiff(int levelDiff) {
        if (levelDiff <= -5) {
            return Colors.highestLvlcolor;
        } else if (levelDiff <= -3) {
            return Colors.higherLvlcolor;
        } else if (levelDiff <= -1) {
            return Colors.highLvlcolor;
        } else if (levelDiff == 0) {
            return Colors.sameLvlcolor;
        } else if (levelDiff >= 5) {
            return Colors.lowestLvlcolor;
        } else if (levelDiff >= 3) {
            return Colors.lowerLvlcolor;
        } else {
            return Colors.lowLvlcolor;
        }
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void addRuntimeEntity(Entity e) {
        addRuntimeEntity(e, true);
    }

    public void addRuntimeEntity(Entity e, boolean shouldRespawn) {
        e.setRespawner(shouldRespawn);
        toBeAddedEntities.add(e);
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
