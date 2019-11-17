package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GenericNPC extends Creature {

    private String jsonScript;
    private String animation;

    public GenericNPC(float x, float y) {
        this(x, y, "TODO: MAKE GENERIC SCRIPT IF NONE PROVIDED", "DEFAULT_NAME", "genericMale1");
    }

    public GenericNPC(float x, float y, String jsonScript, String name, String animation) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.jsonScript = jsonScript;
        this.name = name;
        this.animation = animation;

        solid = true;
        attackable = false;
        isNpc = true;
        script = Utils.loadScript(jsonScript);

        BufferedImage[][] anims = Assets.getAnimationByTag(animation);
        aDown = new Animation(111, anims[0]);
        aLeft = new Animation(111, anims[1]);
        aRight = new Animation(111, anims[2]);
        aUp = new Animation(111, anims[3]);

        aDefault = aDown;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(getAnimationByLastFaced(),
                (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), null);
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new GenericNPC(getxSpawn(), getySpawn(), jsonScript, name, animation));
    }

    @Override
    protected void updateDialogue() {

    }

    @Override
    public String getName() {
        return name;
    }
}
