package dev.ipsych0.myrinnia.abilities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.ItemSlot;

public class FireBallAbility extends Ability {

    /**
     *
     */
    private static final long serialVersionUID = -2623074711686137258L;

    public FireBallAbility(CharacterStats element, CharacterStats combatStyle, String name, AbilityType abilityType, boolean selectable,
                           double cooldownTime, double castingTime, double overcastTime, int baseDamage, String description) {
        super(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, description);

        price = 1;
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(Assets.magicProjectile[2], x, y, ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE, null);

    }

    @Override
    public void cast() {
        Handler.get().getMouseManager().setLeftPressed(true);
        Handler.get().getPlayer().checkMagic(new Rectangle(Handler.get().getMouseManager().getMouseX(), Handler.get().getMouseManager().getMouseY(), 1, 1));
        Handler.get().getMouseManager().setLeftPressed(false);
        setCasting(false);
    }

    @Override
    public void setCaster(Creature c) {
        this.caster = c;
        this.setActivated(true);
        System.out.println("Cast: " + this.getName());
    }

}
