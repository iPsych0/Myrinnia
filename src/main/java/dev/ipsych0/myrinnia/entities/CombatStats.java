package dev.ipsych0.myrinnia.entities;

import dev.ipsych0.myrinnia.tiles.Tile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CombatStats {
    // Combat attributes
    private int strength;
    private int dexterity;
    private int intelligence;
    private int defence;
    private int vitality;
    private double attackSpeed;
    private double movementSpeed;

    // Stat modifiers
    private int waterLevel;
    private int fireLevel;
    private int airLevel;
    private int earthLevel;

    public static class CombatStatsBuilder {
        private int waterLevel;
        private int fireLevel;
        private int airLevel;
        private int earthLevel;

        public CombatStatsBuilder() {
            // Default stats for each Entity
            this.waterLevel = 1;
            this.fireLevel = 1;
            this.airLevel = 1;
            this.earthLevel = 1;
        }
    }

    // Add methods
    public void addStrength(int strength) {
        this.strength += strength;
    }

    public void addDexterity(int dexterity) {
        this.dexterity += dexterity;
    }

    public void addIntelligence(int intelligence) {
        this.intelligence += intelligence;
    }

    public void addDefence(int defence) {
        this.defence += defence;
    }

    public void addVitality(int vitality) {
        this.vitality += vitality;
    }

    public void addAttackSpeed(double attackSpeed) {
        this.attackSpeed += attackSpeed;
    }

    public void addMovementSpeed(double movementSpeed) {
        this.movementSpeed += movementSpeed;
    }

    public void addWaterLevel(int waterLevel) {
        this.waterLevel += waterLevel;
    }

    public void addFireLevel(int fireLevel) {
        this.fireLevel += fireLevel;
    }

    public void addAirLevel(int airLevel) {
        this.airLevel += airLevel;
    }

    public void addEarthLevel(int earthLevel) {
        this.earthLevel += earthLevel;
    }

    // Subtract methods
    public void subtractStrength(int strength) {
        this.strength -= strength;
    }

    public void subtractDexterity(int dexterity) {
        this.dexterity -= dexterity;
    }

    public void subtractIntelligence(int intelligence) {
        this.intelligence -= intelligence;
    }

    public void subtractDefence(int defence) {
        this.defence -= defence;
    }

    public void subtractVitality(int vitality) {
        this.vitality -= vitality;
    }

    public void subtractAttackSpeed(double attackSpeed) {
        this.attackSpeed -= attackSpeed;
    }

    public void subtractMovementSpeed(double movementSpeed) {
        this.movementSpeed -= movementSpeed;
    }

    public void subtractWaterLevel(int waterLevel) {
        this.waterLevel -= waterLevel;
    }

    public void subtractFireLevel(int fireLevel) {
        this.fireLevel -= fireLevel;
    }

    public void subtractAirLevel(int airLevel) {
        this.airLevel -= airLevel;
    }

    public void subtractEarthLevel(int earthLevel) {
        this.earthLevel -= earthLevel;
    }
}
