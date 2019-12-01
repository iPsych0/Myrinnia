package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.image.BufferedImage;

public class CombatSkill extends Skill {

    /**
     *
     */
    private static final long serialVersionUID = -8746462876499708037L;

    // Always start at 1 combat, so have 1 level.
    static {
        CharacterStats.Combat.addLevel();
    }

    @Override
    public BufferedImage getImg() {
        return Assets.meleeIcon;
    }

    @Override
    public void addLevel() {
        this.level++;
        CharacterStats.Combat.addLevel();
        // Add base
        Handler.get().getCharacterUI().addBaseStatPoints();
        Handler.get().getCharacterUI().addElementalStatPoints();

        // Increase ability points on level-up
        Handler.get().getPlayer().addAbilityPoints();
    }

    @Override
    public String toString() {
        return "Combat";
    }

    @Override
    protected void checkNextLevel() {
        if (experience >= nextLevelXp) {
            experience -= nextLevelXp;
            addLevel();
            nextLevelXp = (int) (nextLevelXp * 1.1);
            Player.isLevelUp = true;
            Handler.get().getPlayer().levelUp();
            checkNextLevel();
            Handler.get().playEffect("ui/level_up.wav", 0.1f);
        }
    }

}
