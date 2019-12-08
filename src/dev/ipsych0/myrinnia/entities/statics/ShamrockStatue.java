package dev.ipsych0.myrinnia.entities.statics;

import java.awt.*;

public class ShamrockStatue extends StaticEntity {

    public ShamrockStatue(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = true;
        attackable = false;
        isNpc = true;

        String text = script.getDialogues().get(0).getText();
        String newText = text.replace("{name}", name.replace("Statue of", ""));
        script.getDialogues().get(0).setText(newText);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }
}
