package dev.ipsych0.mygame.shop;

import dev.ipsych0.mygame.Handler;
import dev.ipsych0.mygame.abilities.Ability;
import dev.ipsych0.mygame.gfx.Assets;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class AbilityShopWindow implements Serializable {

    private static final long serialVersionUID = 7862013290912383006L;

    private ArrayList<Ability> abilities;
    public static boolean isOpen;
    private int x, y, width, height;

    public AbilityShopWindow(ArrayList<Ability> abilities) {
        this.abilities = abilities;
        width = 460;
        height = 313;
        x = Handler.get().getWidth() / 2 - width / 2;
        y = Handler.get().getHeight() / 2 - height / 2;
    }

    public void tick(){

    }

    public void render(Graphics g){
        g.drawImage(Assets.shopWindow, x, y, width, height, null);

        g.drawImage(Assets.genericButton[0], x, y, width, height, null);
        abilities.get(0).render(g, x, y);
    }
}
