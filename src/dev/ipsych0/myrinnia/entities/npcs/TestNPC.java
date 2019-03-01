package dev.ipsych0.myrinnia.entities.npcs;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.entities.statics.StaticEntity;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.tiles.Tiles;
import dev.ipsych0.myrinnia.utils.SaveManager;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.util.List;

public class TestNPC extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = -8566165980826138340L;
    private Script script;

    public TestNPC(float x, float y) {
        super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);

        script = Utils.loadScript("testnpc1.json");

        solid = true;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void die() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.teleportShrine2, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
        g.drawImage(Assets.teleportShrine1, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - 32 - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {
        // If the conversation was reset, reinitialize the first time we interact again
        if(speakingTurn == -1){
            chatDialogue = null;
            speakingTurn = 0;
            return;
        }
        if(script.getDialogues().get(speakingTurn).getText() != null) {
            if(chatDialogue != null && chatDialogue.getChosenOption() != null){
                chatDialogue.setChosenOption(null);
            }
            chatDialogue = new ChatDialogue(new String[]{script.getDialogues().get(speakingTurn).getText()});
            setSpeakingTurn(script.getDialogues().get(speakingTurn).getNextId());
        }else{
            if(chatDialogue != null && chatDialogue.getChosenOption() != null){
                setSpeakingTurn(script.getDialogues().get(speakingTurn).getOptions().get(chatDialogue.getChosenOption().getOptionID()).getNextId());
                chatDialogue.setChosenOption(null);
                interact();
                return;
            }
            List<Choice> choiceList = script.getDialogues().get(speakingTurn).getOptions();
            String[] choices = new String[choiceList.size()];
            for(int i = 0; i < choiceList.size(); i++){
                choices[i] = choiceList.get(i).getText();
            }
            chatDialogue = new ChatDialogue(choices);
        }
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    public void respawn() {

    }

}