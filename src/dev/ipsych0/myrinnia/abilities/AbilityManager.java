package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilityhud.AbilityHUD;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AbilityManager implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1274154274799386875L;
    private List<Ability> allAbilities = new ArrayList<>();
    private CopyOnWriteArrayList<Ability> activeAbilities = new CopyOnWriteArrayList<>();
    private Collection<Ability> deleted = new CopyOnWriteArrayList<>();
    private Color castBarColor = new Color(240, 160, 5, 224);
    private AbilityHUD abilityHUD;

    /*
     * Abilities (maybe via file inladen)
     */
    public AbilityManager() {
        this.abilityHUD = new AbilityHUD();
        try {
            for (File f : Utils.abilityJsonDirectory.listFiles()) {
                allAbilities.add(Utils.loadAbility(f.getName()));
            }
        } catch (Exception e) {
            System.err.println("Failed to load abilities!");
            System.exit(1);
        }
    }

    public void tick() {
        abilityHUD.tick();
        Iterator<Ability> it = activeAbilities.iterator();
        while (it.hasNext()) {
            Ability a = it.next();
            if (a.isActivated()) {
                a.tick();
            } else {
                deleted.add(a);
            }
        }

        // Clear the non-active abilities
        if (deleted.size() > 0) {
            activeAbilities.removeAll(deleted);
            deleted.clear();
        }
    }

    public void render(Graphics g) {
        abilityHUD.render(g);
        Iterator<Ability> it = activeAbilities.iterator();
        while (it.hasNext()) {
            Ability a = it.next();
            if (a.isActivated()) {
                if (!a.isCasting() && a.getCastingTime() > 0 && a.getCastingTimeTimer() <= a.getCastingTime() * 60 && a.getCastingTimeTimer() > 0) {
                    float timer = a.getCastingTimeTimer();
                    double castTime = a.getCastingTime() * 60;
                    double timeLeft = timer / castTime;
                    g.setColor(castBarColor);
                    g.fillRect((int) (a.getCaster().getX() - Handler.get().getGameCamera().getxOffset() - 4), (int) (a.getCaster().getY() + a.getCaster().getHeight() - 4 - Handler.get().getGameCamera().getyOffset()),
                            (int) (timeLeft * (a.getCaster().getWidth() + 4)), 8);
                    g.setColor(Color.BLACK);
                    g.drawRect((int) (a.getCaster().getX() - Handler.get().getGameCamera().getxOffset() - 4), (int) (a.getCaster().getY() + a.getCaster().getHeight() - 4 - Handler.get().getGameCamera().getyOffset()),
                            a.getCaster().getWidth() + 4, 8);
                }
            }
        }
    }

    public CopyOnWriteArrayList<Ability> getActiveAbilities() {
        return activeAbilities;
    }

    public void setActiveAbilities(CopyOnWriteArrayList<Ability> activeAbilities) {
        this.activeAbilities = activeAbilities;
    }

    public AbilityHUD getAbilityHUD() {
        return abilityHUD;
    }

    public void setAbilityHUD(AbilityHUD abilityHUD) {
        this.abilityHUD = abilityHUD;
    }

    public List<Ability> getAllAbilities() {
        return allAbilities;
    }

    public void setAllAbilities(List<Ability> allAbilities) {
        this.allAbilities = allAbilities;
    }

    public List<Ability> getAbilityByStyleAndElement(CharacterStats combatStyle, CharacterStats element) {
        List<Ability> filtered = new ArrayList<>(allAbilities);
        filtered.removeIf((a) -> a.getElement() != element || a.getCombatStyle() != combatStyle);
        return filtered;
    }
}
