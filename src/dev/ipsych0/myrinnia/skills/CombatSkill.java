package dev.ipsych0.myrinnia.skills;

import dev.ipsych0.myrinnia.Handler;

public class CombatSkill extends Skill {

    /**
     *
     */
    private static final long serialVersionUID = -8746462876499708037L;

    @Override
    public void addLevel() {
        this.level++;
        Handler.get().getCharacterUI().addBaseStatPoints();
        Handler.get().getCharacterUI().addElementalStatPoints();
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
            Handler.get().getPlayer().levelUp();
            checkNextLevel();
        }
    }

}
