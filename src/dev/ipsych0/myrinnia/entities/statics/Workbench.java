package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.gfx.Assets;

import java.awt.*;

public class Workbench extends StaticEntity {

    /**
     *
     */
    private static final long serialVersionUID = -8804679431303966524L;
    private String[] firstDialogue = {"You may use this workbench to craft items."};
    private String[] secondDialogue = {"Craft items", "Leave"};

    public Workbench(float x, float y) {
        super(x, y, 64, 64);
        bounds.y = 16;
        bounds.height -= 12;

        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.workbench, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);

    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    public void die() {

    }

    @Override
    public void interact() {
        switch (speakingTurn) {

            case 0:
                speakingTurn++;
                return;

            case 1:
                if (!CraftingUI.isOpen) {
                    chatDialogue = new ChatDialogue(firstDialogue);
                    speakingTurn++;
                    break;
                } else {
                    speakingTurn = 1;
                    break;
                }
            case 2:
                if (chatDialogue == null) {
                    speakingTurn = 1;
                    break;
                }
                chatDialogue = new ChatDialogue(secondDialogue);
                speakingTurn++;
                break;
            case 3:
                if (chatDialogue.getChosenOption().getOptionID() == 0) {
                    Handler.get().getCraftingUI().openWindow();
                    chatDialogue = null;
                    speakingTurn = 1;
                    break;
                } else if (chatDialogue.getChosenOption().getOptionID() == 1) {
                    chatDialogue = null;
                    speakingTurn = 1;
                    break;
                }
        }


    }

    @Override
    public void respawn() {

    }
}
