package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.input.KeyManager;

import java.awt.event.KeyEvent;

public abstract class StaticEntity extends Entity {


    /**
     *
     */
    private static final long serialVersionUID = -2206779374852046145L;

    protected StaticEntity(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        staticNpc = true;
        solid = true;
    }

    @Override
    public String[] getEntityInfo(Entity hoveringEntity) {
        if (script != null || isNpc) {
            String[] name = new String[2];
            name[0] = hoveringEntity.getName();
            String interactKey = KeyManager.interactKey == 0x20 ? "Space" : KeyEvent.getKeyText(KeyManager.interactKey);
            name[1] = "Press '" + interactKey + "' to interact";
            return name;
        }
        String[] name = new String[2];
        name[0] = hoveringEntity.getName();
        name[1] = "Health: " + health + "/" + maxHealth;
        return name;
    }


}
