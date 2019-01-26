package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.entities.creatures.DamageType;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class HitSplat implements Serializable {

    private static final long serialVersionUID = 7859808120479358201L;
    private Entity receiver;
    private int damage;
    private boolean active = false;
    private int ty = 0;
    private int xOffset, yOffset;
    private transient BufferedImage dmgType;
    private transient Ability ability;

    public HitSplat(){
    }

    public HitSplat(Entity receiver, int damage, DamageType damageType) {
        this.receiver = receiver;
        this.damage = damage;
        active = true;
        xOffset = Handler.get().getRandomNumber(-16, 24);
        yOffset = Handler.get().getRandomNumber(-12, 12);

        switch (damageType){
            case STR:
                this.dmgType = Assets.meleeIcon;
                break;
            case DEX:
                this.dmgType = Assets.rangedIcon;
                break;
            case INT:
                this.dmgType = Assets.magicIcon;
                break;
        }
    }

    public HitSplat(Entity receiver, int damage, Ability ability) {
        this.receiver = receiver;
        this.damage = damage;
        this.ability = ability;
        active = true;
        xOffset = Handler.get().getRandomNumber(-16, 24);
        yOffset = Handler.get().getRandomNumber(-12, 12);
    }

    public void tick() {
        if (active) {

        }
    }

    public void render(Graphics g) {
        if (active) {
            ty++;
            if(ability == null) {
                g.drawImage(dmgType, (int) (receiver.x - Handler.get().getGameCamera().getxOffset() - 18 + xOffset),
                        (int) (receiver.y - Handler.get().getGameCamera().getyOffset() + 8 - ty + yOffset), 24, 24, null);
            }else{
                ability.render(g, (int) (receiver.x - Handler.get().getGameCamera().getxOffset() - 30 + xOffset),
                        (int) (receiver.y - Handler.get().getGameCamera().getyOffset() + 6 - ty + yOffset));
            }
            Text.drawString(g, String.valueOf(damage),
                    (int) (receiver.x - Handler.get().getGameCamera().getxOffset() + receiver.width / 4 + xOffset),
                    (int) (receiver.y - Handler.get().getGameCamera().getyOffset() + receiver.height - ty + yOffset), false, Color.RED, Assets.font32);

            if (ty >= 60) {
                active = false;
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
