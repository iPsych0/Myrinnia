package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.cutscenes.Cutscene;
import dev.ipsych0.myrinnia.cutscenes.MoveCameraEvent;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.states.CutsceneState;
import dev.ipsych0.myrinnia.states.State;
import dev.ipsych0.myrinnia.utils.Utils;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class ShamrockSinkhole extends StaticEntity {

    private boolean cutsceneStarted;

    public ShamrockSinkhole(double x, double y, int width, int height) {
        super(x, y, width, height, null, 1, null, null, null, null);

        solid = true;
        attackable = false;
        isNpc = true;

        this.name = "Sinkhole";
        this.script = Utils.loadScript("shamrock_sinkhole.json");

        remove("Miner Robert");
        remove("Miner Albert");
        remove("Miner Aaron");

        Handler.get().getQuest(QuestList.WeDelvedTooDeep).nextStep();

    }

    @Override
    public void tick() {
        if (!cutsceneStarted) {
            State.setState(new CutsceneState(new Cutscene(
                    new MoveCameraEvent(Handler.get().getPlayer().getCollisionBounds(0,0), getCollisionBounds(0,0))
            )));

            cutsceneStarted = true;
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
                Handler.get().goToWorld(Zone.ShamrockMinesBasin, 76, 31);
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
