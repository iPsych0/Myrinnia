package dev.ipsych0.myrinnia.abilityoverview;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.states.GameState;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AbilityOverviewUI {

    private int x, y, width, height;
    private List<AbilityOverviewUIButton> uiButtons = new ArrayList<>();
    private Rectangle bounds;

    public static boolean isOpen;
    public static boolean escapePressed;
    public static boolean hasBeenPressed;

    private Rectangle innerUI;
    private Rectangle exit;

    public AbilityOverviewUI(){
        this.width = 460;
        this.height = 460;
        this.x = Handler.get().getWidth() / 2 - width / 2;
        this.y = Handler.get().getHeight() / 2 - height / 2;
        this.bounds = new Rectangle(x, y, width, height);


        uiButtons.add(new AbilityOverviewUIButton(x + width / 2 - (width / 4) - 32, y + 32, CharacterStats.Melee));
        uiButtons.add(new AbilityOverviewUIButton(x + width / 2 - 32, y + 32,  CharacterStats.Ranged));
        uiButtons.add(new AbilityOverviewUIButton(x + width / 2 + (width / 4) - 32, y + 32, CharacterStats.Magic));

        uiButtons.add(new AbilityOverviewUIButton(x + width, y + 32, CharacterStats.Fire));
        uiButtons.add(new AbilityOverviewUIButton(x + width, y + 64, CharacterStats.Air));
        uiButtons.add(new AbilityOverviewUIButton(x + width, y + 96, CharacterStats.Water));
        uiButtons.add(new AbilityOverviewUIButton(x + width, y + 128, CharacterStats.Earth));

        innerUI = new Rectangle(x + 32, y + 96, width - 64, height - 128);
        exit = new Rectangle(x + width - 35, y + 10, 24, 24);
    }

    public void tick(){
        if(isOpen){
            if(Handler.get().getKeyManager().escape && escapePressed) {
                escapePressed = false;
                isOpen = false;
                hasBeenPressed = false;
                return;
            }
        }
    }

    public void render(Graphics g){
        if(isOpen) {
            g.drawImage(Assets.shopWindow, x, y, width, height, null);
            g.drawImage(Assets.shopWindow, innerUI.x, innerUI.y, innerUI.width, innerUI.height, null);
            Text.drawString(g, "Ability Overview", x + width / 2, y + 16, true, Color.YELLOW, Assets.font14);

            Rectangle mouse = Handler.get().getMouse();

            // test stuff close button
            if(exit.contains(mouse))
                g.drawImage(Assets.genericButton[0], exit.x, exit.y, exit.width, exit.height, null);
            else
                g.drawImage(Assets.genericButton[1], exit.x, exit.y, exit.width, exit.height, null);
            Text.drawString(g, "X", exit.x + 11, exit.y + 11, true, Color.YELLOW, Assets.font20);

            for (AbilityOverviewUIButton button : uiButtons) {
                button.render(g);
                if (button.getBounds().contains(mouse)) {
                    Text.drawString(g, button.getStat().toString(), x + width / 2, y + height / 2, true, Color.YELLOW, Assets.font20);
                }
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
