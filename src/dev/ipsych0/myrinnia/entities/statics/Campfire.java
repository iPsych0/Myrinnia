package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.gfx.Animation;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest.QuestState;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tiles.Tiles;

import java.awt.*;

public class Campfire extends StaticEntity {


    /**
     *
     */
    private static final long serialVersionUID = 1894028112446761958L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private Animation campfire;
    private String[] firstDialogue = {"Feel the fire.", "Leave."};
    private String[] secondDialogue = {"You almost burned your fingers trying to examine the fire. However, a sword is revealed."};
    private String[] thirdDialogue = {"Take the sword.", "Leave it."};

    public Campfire(float x, float y) {
        super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);

        isNpc = true;
        attackable = false;
        campfire = new Animation(125, Assets.campfire);
    }

    @Override
    public void tick() {
        campfire.tick();
    }

    @Override
    public void die() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(campfire.getCurrentFrame(), (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {

        switch (speakingTurn) {

            case 0:
                speakingTurn++;
                break;
            case 1:
                chatDialogue = new ChatDialogue(firstDialogue);
                speakingTurn++;
                break;
            case 2:
                if (chatDialogue == null) {
                    speakingTurn = 1;
                    break;
                }

                if (chatDialogue.getChosenOption().getOptionID() == 0) {
                    chatDialogue = new ChatDialogue(secondDialogue);
                    speakingTurn++;
                    if (!Handler.get().questStarted(QuestList.TheFirstQuest)) {
                        Handler.get().getQuest(QuestList.TheFirstQuest).setState(QuestState.IN_PROGRESS);
                        Handler.get().addQuestStep(QuestList.TheFirstQuest, "Investigate the fire.");
                        Handler.get().getQuest(QuestList.TheFirstQuest).nextStep();
                        Handler.get().addQuestStep(QuestList.TheFirstQuest, "Investigate the fire2.");
                        Handler.get().getQuest(QuestList.TheFirstQuest).nextStep();
                        Handler.get().addQuestStep(QuestList.TheFirstQuest, "Investigate the fire3.");
                        Handler.get().getQuest(QuestList.TheFirstQuest).nextStep();
                        Handler.get().addQuestStep(QuestList.TheFirstQuest, "Investigate the fire4.");
                        Handler.get().getQuest(QuestList.TheFirstQuest).nextStep();
                        Handler.get().addQuestStep(QuestList.TheFirstQuest, "Investigate the fire5.");
                        Handler.get().getQuest(QuestList.TheFirstQuest).nextStep();

                    }
                    break;
                } else if (chatDialogue.getChosenOption().getOptionID() == 1) {
                    chatDialogue = null;
                    speakingTurn = 1;
                    break;
                } else {
                    speakingTurn = 2;
                    break;
                }
            case 3:
                if (chatDialogue == null) {
                    speakingTurn = 1;
                    break;
                }
                chatDialogue = new ChatDialogue(thirdDialogue);
                speakingTurn++;
                break;
            case 4:
                if (chatDialogue == null) {
                    speakingTurn = 1;
                    break;
                }

                if (chatDialogue.getChosenOption().getOptionID() == 0) {
                    if (Handler.get().getQuest(QuestList.TheFirstQuest).getState() == QuestState.IN_PROGRESS) {
                        if (!Handler.get().invIsFull(Item.testSword)) {
                            Handler.get().getQuest(QuestList.TheFirstQuest).setState(QuestState.COMPLETED);
                            Handler.get().giveItem(Item.testSword, 1);
                            Handler.get().discoverRecipe(Item.purpleSword);
                            chatDialogue = null;
                            speakingTurn = 1;
                        } else {
                            chatDialogue = null;
                            speakingTurn = 1;
                            Handler.get().sendMsg("Your inventory is full.");
                        }
                        break;
                    } else {
                        chatDialogue = null;
                        speakingTurn = 1;
                    }
                } else if (chatDialogue.getChosenOption().getOptionID() == 1) {
                    chatDialogue = null;
                    speakingTurn = 1;
                    break;
                }
        }
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Campfire(xSpawn, ySpawn));
    }

    @Override
    protected void updateDialogue() {

    }

}