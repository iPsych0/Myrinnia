package dev.ipsych0.myrinnia.abilities;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.ui.abilityhud.AbilityHUD;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class AbilityManager implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1274154274799386875L;
    private List<Ability> allAbilities = new ArrayList<>();
    private List<Ability> activeAbilities = new ArrayList<>();
    private Color castBarColor = new Color(26, 240, 49, 224);
    private Color castBarBgColor = new Color(240, 31, 31, 224);
    private AbilityHUD abilityHUD;
    private static File abilitiesJsonDirectory = new File("src/dev/ipsych0/myrinnia/abilities/json/");
    public static Map<Class<? extends Ability>, Ability> abilityMap = new HashMap<>();


    /*
     * Abilities (maybe via file inladen)
     */
    public AbilityManager() {
        this.abilityHUD = new AbilityHUD();
//
//        try {
//            init();
//        } catch (Exception e) {
//            System.err.println("Failed to load abilities!");
//            System.exit(1);
//        }
    }

    private void init() throws IOException {
        final String path = "dev/ipsych0/myrinnia/abilities/json/";

        // Run with JAR file
        if(Handler.isJar) {
            final JarFile jar = new JarFile(Handler.jarFile);
            // Get all files and folders in the jar
            final Enumeration<JarEntry> entries = jar.entries();
            while(entries.hasMoreElements()) {
                final String entry = entries.nextElement().getName();
                // Look for the abilities/json folder for files that end with .json
                if (entry.startsWith(path) && entry.endsWith(".json")) {
                    // Get the json filename and load it
                    String jsonFile = entry.substring(entry.lastIndexOf("/")+1, entry.length());
                    allAbilities.add(Utils.loadAbility(jsonFile));
                }
            }
            jar.close();
        // Run with IDE
        } else {
            for (File f : abilitiesJsonDirectory.listFiles()) {
                allAbilities.add(Utils.loadAbility(f.getName()));
            }
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
                it.remove();
            }
        }
    }

    public void render(Graphics2D g) {
        abilityHUD.render(g);
        Iterator<Ability> it = activeAbilities.iterator();
        while (it.hasNext()) {
            Ability a = it.next();
            if (a.isActivated()) {
                if (!a.isCasting() && a.getCastingTime() > 0 && a.getCastingTimeTimer() <= a.getCastingTime() * 60 && a.getCastingTimeTimer() > 0) {
                    float timer = a.getCastingTimeTimer();
                    double castTime = a.getCastingTime() * 60;
                    double timeLeft = timer / castTime;
                    g.setColor(castBarBgColor);
                    g.fillRoundRect((int) (a.getCaster().getX() - Handler.get().getGameCamera().getxOffset() - 4), (int) (a.getCaster().getY() + a.getCaster().getHeight() - 4 - Handler.get().getGameCamera().getyOffset()),
                            a.getCaster().getWidth() + 4, 8, 2, 2);
                    g.setColor(castBarColor);
                    g.fillRoundRect((int) (a.getCaster().getX() - Handler.get().getGameCamera().getxOffset() - 4), (int) (a.getCaster().getY() + a.getCaster().getHeight() - 4 - Handler.get().getGameCamera().getyOffset()),
                            (int) (timeLeft * (a.getCaster().getWidth() + 4)), 8, 2, 2);
                    g.setColor(Color.BLACK);
                    g.drawRoundRect((int) (a.getCaster().getX() - Handler.get().getGameCamera().getxOffset() - 4), (int) (a.getCaster().getY() + a.getCaster().getHeight() - 4 - Handler.get().getGameCamera().getyOffset()),
                            a.getCaster().getWidth() + 4, 8, 2, 2);
                }
            }
        }
    }

    public List<Ability> getActiveAbilities() {
        return activeAbilities;
    }

    public void setActiveAbilities(List<Ability> activeAbilities) {
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
        return allAbilities
                .stream()
                .filter(a -> a.getElement() == element && a.getCombatStyle() == combatStyle)
                .collect(Collectors.toList());
    }
}
