package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.items.ui.ItemStack;

import java.awt.*;

public interface FarmingPatch {

    void plant(ItemStack seeds);

    String getName();

    void drawProgressBar(Graphics2D g);

}
