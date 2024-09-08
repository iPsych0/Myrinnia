package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;

import java.awt.*;

public class CelenorCollaborator extends Creature {

    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);
    private Animation teleportAnim = new Animation(1000 / (Assets.warpTeleport.length * 2), Assets.warpTeleport, true, true);
    private float alpha = 1.0f;
    public static boolean isTeleporting;

    public CelenorCollaborator(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop, Direction direction) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
        solid = true;
        attackable = false;
        isNpc = true;

    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        Composite current = g.getComposite();

        if (isTeleporting) {
            // Fade out the character while teleporting
            if (teleportAnim != null) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                alpha -= 0.02f;
                if (alpha < 0f) {
                    alpha = 0f;
                }
            }
        }
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);

        g.setComposite(current);

        if (isTeleporting) {
            if (teleportAnim != null) {
                if (teleportAnim.isTickDone()) {
                    teleportAnim = null;
                    active = false;
                } else {
                    teleportAnim.tick();
                    g.drawImage(teleportAnim.getCurrentFrame(), (int) (this.x - Handler.get().getGameCamera().getxOffset()),
                            (int) (this.y - 32 - Handler.get().getGameCamera().getyOffset()), null);
                }
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
    }
}
