package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;

import java.awt.*;

public class CelenorWarden extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);
    private Animation teleportAnim;
    private Player player;
    private float alpha = 1.0f;

    public CelenorWarden(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;

        player = Handler.get().getPlayer();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        Composite current = g.getComposite();

        // Fade out the character while teleporting
        if (teleportAnim != null) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            alpha -= 0.02f;
            if (alpha < 0f) {
                alpha = 0f;
            }
        }
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);

        g.setComposite(current);

        if (teleportAnim != null) {
            if (teleportAnim.isTickDone()) {
                teleportAnim = null;
                // After he teleported, you may continue again
                player.setMovementAllowed(true);
                player.setClosestEntity(null);
                active = false;
            } else {
                teleportAnim.tick();
                g.drawImage(teleportAnim.getCurrentFrame(), (int) (this.x - Handler.get().getGameCamera().getxOffset()),
                        (int) (this.y - 32 - Handler.get().getGameCamera().getyOffset()), null);
            }
        }
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 8:
                teleportAnim = new Animation(1000 / (Assets.warpTeleport.length * 2), Assets.warpTeleport, true, true);
                speakingTurn = -1;
                CelenorCollaborator.isTeleporting = true;
                Handler.get().playEffect("abilities/teleport1.ogg", 0.1f);
                break;
        }
    }
}
