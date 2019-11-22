package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.entities.Entity;

public abstract class StaticEntity extends Entity {


    /**
     *
     */
    private static final long serialVersionUID = -2206779374852046145L;

    protected StaticEntity(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        staticNpc = true;
        solid = true;
        attackable = false;
    }

    @Override
    public String[] getEntityInfo(Entity hoveringEntity) {
        if (attackable) {
            String[] name = new String[2];
            name[0] = hoveringEntity.getName();
            name[1] = "Health: " + health + "/" + maxHealth;
            return name;
        } else {
            String[] name = new String[1];
            name[0] = hoveringEntity.getName();
            return name;
        }
    }


}
