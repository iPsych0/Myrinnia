package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Utils;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class ShamrockSinkhole extends StaticEntity {
    private boolean hasFocus;
    private int focusTime = 240, focusTimer;

    public ShamrockSinkhole(double x, double y, int width, int height) {
        super(x, y, width, height, null, 1, null, null, null, null);

        solid = true;
        attackable = false;
        isNpc = true;

        this.name = "Sinkhole";
        this.script = Utils.loadScript("shamrock_sinkhole.json");

        Handler.get().getGameCamera().setFocusedEntity(this);
        Handler.get().getPlayer().setMovementAllowed(false);
        hasFocus = true;
    }

    @Override
    public void tick() {
        if (hasFocus) {
            Handler.get().getGameCamera().centerOnEntity(this);
            focusTimer++;
            if (focusTimer / 60 == 1) {
                remove("Miner Robert");
            } else if (focusTimer / 60 == 2) {
                remove("Miner Albert");
            } else if (focusTimer / 60 == 3) {
                remove("Miner Aaron");
            }
            if (focusTimer >= focusTime) {
                hasFocus = false;
                Handler.get().getGameCamera().centerOnEntity(Handler.get().getPlayer());
                Handler.get().getPlayer().setMovementAllowed(true);
            }
        }
    }

    private void remove(String miner) {
        for (Entity e : Handler.get().getWorld().getEntityManager().getEntities()) {
            if (e.getName() != null && e.getName().equalsIgnoreCase(miner)) {
                e.setActive(false);
                break;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        int x = (int) (this.x - Handler.get().getGameCamera().getxOffset());
        int y = (int) (this.y - Handler.get().getGameCamera().getyOffset());

        g.drawImage(Assets.shamrockSinkholeTL, x, y, null);
        g.drawImage(Assets.shamrockSinkholeTM, x + 32, y, null);
        g.drawImage(Assets.shamrockSinkholeTM, x + 64, y, null);
        g.drawImage(Assets.shamrockSinkholeTM, x + 96, y, null);
        g.drawImage(Assets.shamrockSinkholeTR, x + 128, y, null);

        g.drawImage(Assets.shamrockSinkholeML, x, y + 32, null);
        g.drawImage(Assets.shamrockSinkholeMM, x + 32, y + 32, null);
        g.drawImage(Assets.shamrockSinkholeMM, x + 64, y + 32, null);
        g.drawImage(Assets.shamrockSinkholeMM, x + 96, y + 32, null);
        g.drawImage(Assets.shamrockSinkholeMR, x + 128, y + 32, null);

        g.drawImage(Assets.shamrockSinkholeBL, x, y + 64, null);
        g.drawImage(Assets.shamrockSinkholeBM, x + 32, y + 64, null);
        g.drawImage(Assets.shamrockSinkholeBM, x + 64, y + 64, null);
        g.drawImage(Assets.shamrockSinkholeBM, x + 96, y + 64, null);
        g.drawImage(Assets.shamrockSinkholeBR, x + 128, y + 64, null);
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        switch (condition) {
            case "jump":
                Handler.get().goToWorld(Zone.ShamrockTown, 2, 35);
                return true;
            default:
                System.err.println("CHOICE CONDITION '" + condition + "' NOT PROGRAMMED!");
                return false;
        }
    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new ShamrockSinkhole(x, y, width, height));
    }

    @Override
    protected void updateDialogue() {

    }
}
