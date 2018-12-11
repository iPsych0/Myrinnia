package dev.ipsych0.myrinnia.abilityoverview;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AbilityOverviewUI {

    private int x, y, width, height;
    private List<AbilityOverviewUIButton> uiButtons = new ArrayList<>();
    private Rectangle bounds;

    public AbilityOverviewUI(){
        this.width = 460;
        this.height = 460;
        this.x = Handler.get().getWidth() / 2 - width / 2;
        this.y = Handler.get().getHeight() / 2 - height / 2;
        this.bounds = new Rectangle(x, y, width, height);


        uiButtons.add(new AbilityOverviewUIButton(x + width / 2 - (width / 4) - 16, y + 32, CharacterStats.Melee));
        uiButtons.add(new AbilityOverviewUIButton(x + width / 2 - 16, y + 32,  CharacterStats.Ranged));
        uiButtons.add(new AbilityOverviewUIButton(x + width / 2 + (width / 4) - 16, y + 32, CharacterStats.Magic));

        uiButtons.add(new AbilityOverviewUIButton(x + width, y, CharacterStats.Fire));
        uiButtons.add(new AbilityOverviewUIButton(x + width, y + 32, CharacterStats.Air));
        uiButtons.add(new AbilityOverviewUIButton(x + width, y + 64, CharacterStats.Water));
        uiButtons.add(new AbilityOverviewUIButton(x + width, y + 96, CharacterStats.Earth));

    }

    public void tick(){

    }

    public void render(Graphics g){
        g.drawImage(Assets.shopWindow, x, y, width, height, null);

        Rectangle mouse = Handler.get().getMouse();

        for(AbilityOverviewUIButton button : uiButtons){
            button.render(g);
            if(button.getBounds().contains(mouse)){
                Text.drawString(g, button.getStat().toString(), x + width / 2, y + height / 2, true, Color.YELLOW, Assets.font20);
            }
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
