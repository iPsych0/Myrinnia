package dev.ipsych0.myrinnia.character;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.image.BufferedImage;

public enum CharacterStats {

    Combat, Melee, Ranged, Magic, Fire, Air, Earth, Water;

    private int level;
    private String description;

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
            case Water -> player.setWaterLevel(this.getLevel());
            case Air -> player.setAirLevel(this.getLevel());
            case Fire -> player.setFireLevel(this.getLevel());
            case Earth -> player.setEarthLevel(this.getLevel());
        }
    }

    public void setDescription() {
        this.description = switch (this) {
            case Fire -> "The Fire element focuses on high damage, with moderate mobility, at the expense of sustainability and survivability.";
            case Air -> "The Air element provides high mobility and crowd control, with moderate damage, at the expensive of survivability.";
            case Water -> "The Water element grants high sustainability through healing, with moderate survivability, at the expense of damage and mobility.";
            case Earth -> "The Earth element gives high survivability through defensive utility, with moderate sustainability, at the expense of damage and mobility.";
            case Melee -> "Melee combat arts allow you to wield higher-leveled, better warrior's equipment.";
            case Ranged -> "Ranged combat arts allow you to wield higher-leveled, better ranger's equipment.";
            case Magic -> "Magic combat arts allow you to wield higher-leveled, better mage's equipment.";
            case Combat -> "Combat level gives an indication of battle-proficiency and also increases your base damage and health.";
        };
    }

    public BufferedImage getIcon() {
        return switch (this) {
            case Fire -> Assets.fireElement;
            case Air -> Assets.airElement;
            case Water -> Assets.waterElement;
            case Earth -> Assets.earthElement;
            case Melee -> Assets.meleeElement;
            case Ranged -> Assets.rangedElement;
            case Magic -> Assets.magicElement;
            case Combat -> Assets.meleeIcon;
        };
    }

    public String getDescription() {
        return description;
    }

}
