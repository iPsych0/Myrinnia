package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.cutscenes.Cutscene;
import dev.ipsych0.myrinnia.cutscenes.MoveEntityEvent;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.states.CutsceneState;
import dev.ipsych0.myrinnia.states.State;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.worlds.weather.Fog;
import dev.ipsych0.myrinnia.worlds.World;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class ExtrememistTriggerTile extends GenericObject {

    private Player player;
    private Fog fog = new Fog(Fog.Intensity.HEAVY);
    private World world;
    private World celenorForestEdge, wardensCabin, celenorThicket;
    private boolean cutsceneShown;
    private Rectangle cutsceneTrigger;

    public ExtrememistTriggerTile(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = false;
        attackable = false;
        isNpc = false;
        player = Handler.get().getPlayer();
        setOverlayDrawn(false);

        celenorForestEdge = Handler.get().getWorldHandler().getWorldsMap().get(Zone.CelenorForestEdge);
        wardensCabin = Handler.get().getWorldHandler().getWorldsMap().get(Zone.WardensCabin);
        celenorThicket = Handler.get().getWorldHandler().getWorldsMap().get(Zone.CelenorForestThicket);
        cutsceneTrigger = new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public void tick() {
        world = Handler.get().getWorld();
        if (celenorForestEdge.equals(world)) {
            if (player.getY() < this.getY()) {
                player.setY(this.y + Tile.TILEHEIGHT);
                interact();
                Handler.get().sendMsg("The fog is too thick to see anything. It's not safe to enter the forest like this.");
            }
            if (player.getY() <= (21 * 32)) {
                Handler.get().getWorld().fadeInWeatherEffect(fog);
            } else if (player.getY() > (21 * 32)) {
                Handler.get().getWorld().fadeOutWeatherEffect(fog);
            }
        } else if (wardensCabin.equals(world)) {
            if (!cutsceneShown) {
                if (cutsceneTrigger.contains(player.getCollisionBounds(0, 0))) {
                    Entity warden = Handler.get().getEntityByZoneAndName(Zone.WardensCabin, "Warden of Celenor");
                    State.setState(new CutsceneState(new Cutscene(
                            new MoveEntityEvent(warden, player.getX() + 16, player.getY() - 16d, false, () -> {
                                player.setClosestEntity(warden);
                                warden.interact();
                            }))));
                    cutsceneShown = true;
                    player.setMovementAllowed(false);
                }
            }
        } else if (celenorThicket.equals(world)) {
            if (player.getY() < this.getY()) {
                player.setY(this.y + Tile.TILEHEIGHT);
                interact();
                Handler.get().sendMsg("The fog is too thick to see anything. It's not safe to proceed.");
            }
            if (player.getY() <= (37 * 32)) {
                Handler.get().getWorld().fadeInWeatherEffect(fog);
            } else if (player.getY() > (37 * 32)) {
                Handler.get().getWorld().fadeOutWeatherEffect(fog);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }
}
