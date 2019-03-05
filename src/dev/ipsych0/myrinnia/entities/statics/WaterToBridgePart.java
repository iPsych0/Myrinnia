package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.chatwindow.ChatDialogue;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest.QuestState;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.tiles.Tiles;

import java.awt.*;

public class WaterToBridgePart extends StaticEntity {

    /**
     *
     */
    private static final long serialVersionUID = 3731445475301213402L;
    private boolean isFixed = false;
    private String[] firstDialogue = {"This bridge part looks like it can be fixed with some logs. I think 5 logs should do."};
    private String[] secondDialogue = {"Fix the bridge. (Use 5 logs)", "Leave the bridge."};

    public WaterToBridgePart(float x, float y) {
        super(x, y, Tiles.TILEWIDTH, Tiles.TILEHEIGHT);

        isNpc = true;
        attackable = false;
    }

    @Override
    public void tick() {
        if (isFixed) {
            die();
        }
    }

    @Override
    public void die() {
        this.active = false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.waterMiddleMiddle, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset())
                , width, height, null);
    }

    @Override
    public void interact() {

        switch (speakingTurn) {

            case 0:
                speakingTurn++;
                return;

            case 1:
                if (Handler.get().hasQuestReqs(QuestList.TheSecondQuest)) {
                    chatDialogue = new ChatDialogue(firstDialogue);
                    if (Handler.get().getQuest(QuestList.TheSecondQuest).getState() != QuestState.IN_PROGRESS) {
                        Handler.get().getQuest(QuestList.TheSecondQuest).setState(QuestState.IN_PROGRESS);
                        Handler.get().addQuestStep(QuestList.TheSecondQuest, "Fix the bridge [1/3]");
                    }
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
                if (Handler.get().playerHasItem(Item.regularLogs, 5)) {
                    chatDialogue = new ChatDialogue(secondDialogue);
                    speakingTurn++;
                    break;
                } else {
                    chatDialogue = null;
                    speakingTurn = 1;
                    break;
                }
            case 3:
                if (chatDialogue == null) {
                    speakingTurn = 1;
                    break;
                }

                if (chatDialogue.getChosenOption().getOptionID() == 0) {
                    chatDialogue = null;
                    if (Handler.get().playerHasItem(Item.regularLogs, 5)) {
                        Handler.get().removeItem(Item.regularLogs, 5);
                        isFixed = true;
                        Handler.get().sendMsg("You fixed the bridge!");
                        if (Handler.get().getQuest(QuestList.TheSecondQuest).getState() == QuestState.IN_PROGRESS) {
                            if (Handler.get().getQuest(QuestList.TheSecondQuest).getStep() == 2) {
                                Handler.get().getQuest(QuestList.TheSecondQuest).nextStep();
                                Handler.get().getQuest(QuestList.TheSecondQuest).setState(QuestState.COMPLETED);
                            } else {
                                Handler.get().addQuestStep(QuestList.TheSecondQuest, "Fix the bridge [" + (Handler.get().getQuest(QuestList.TheSecondQuest).getStep() + 2) + "/3]");
                                Handler.get().getQuest(QuestList.TheSecondQuest).nextStep();
                            }
                        }
                        break;
                    } else {
                        speakingTurn = 1;
                        break;
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

    }

    @Override
    protected void updateDialogue() {

    }

}