package dev.ipsych0.myrinnia.character;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;

public enum CharacterStats {

    Combat, Melee, Ranged, Magic, Fire, Air, Earth, Water;

    private int level;

    CharacterStats() {
        this.level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel() {
        this.level += 1;
        setCharacterPoints(this);
    }

    public void setLevel(int level) {
        this.level = level;
        setCharacterPoints(this);
    }

    private void setCharacterPoints(CharacterStats element) {
        Player player = Handler.get().getPlayer();
        switch (element) {
            case Water:
                player.setWaterLevel(this.getLevel());
                break;
            case Air:
                player.setAirLevel(this.getLevel());
                break;
            case Fire:
                player.setFireLevel(this.getLevel());
                break;
            case Earth:
                player.setEarthLevel(this.getLevel());
                break;
            default:
                return;
        }
    }

}
