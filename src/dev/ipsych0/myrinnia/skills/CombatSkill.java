package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.tutorial.TutorialTip;

import java.awt.image.BufferedImage;

public class CombatSkill extends Skill {

    private boolean tooltipShown;

    /**
     *
     */
    private static final long serialVersionUID = -8746462876499708037L;

    // Always start at 1 combat, so have 1 level.
    static {
        CharacterStats.Combat.addLevel();
        Handler.get().getPlayer().addAbilityPoints();
    }

    @Override
    public BufferedImage getImg() {
        return Assets.meleeIcon;
    }

    @Override
    public void addLevel() {
        this.level++;
        CharacterStats.Combat.addLevel();
        Handler.get().getPlayer().setCombatLevel(level);

        // Add base
        Handler.get().getCharacterUI().addBaseStatPoints();
        Handler.get().getCharacterUI().addElementalStatPoints();

        if (!tooltipShown && this.level == 5) {
            tooltipShown = true;
            Handler.get().addTip(new TutorialTip("Press K to open your Character Stats.\n\nIncreasing an Elemental Stat will increase the effectiveness of abilities of that Element type."));
            Handler.get().addTip(new TutorialTip("You may customize your Stats to create a build you like. Some equipment have level requirements to be equipped."));
            Handler.get().addTip(new TutorialTip("If you wish to reset your points, you should see an Ability Master. It will cost more gold depending on your Combat level."));
        }

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
            if (!Player.isLevelUp) {
                Handler.get().playEffect("ui/level_up.ogg", 0.1f);
            }
            Player.isLevelUp = true;
            Handler.get().getPlayer().levelUp();
            checkNextLevel();
        } else {
            if (Player.isLevelUp) {
                Handler.get().sendMsg(toString() + " level rose to level " + this.getLevel() + "!");
            }
        }
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        Handler.get().getPlayer().setCombatLevel(level);
    }

}
