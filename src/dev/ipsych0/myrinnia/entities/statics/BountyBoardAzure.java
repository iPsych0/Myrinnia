package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.quests.QuestStep;
import dev.ipsych0.myrinnia.skills.ui.BountyBoardUI;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;
import java.util.List;

public class BountyBoardAzure extends BountyBoard {

    private static final long serialVersionUID = 4925882540927003315L;

    private BountyBoardUI bountyBoardUI;

    public BountyBoardAzure(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        bounds.x = 0;
        bounds.y = height / 2;
        bounds.height = height / 2;
        bounds.width = width;

        attackable = false;
        isNpc = true;

        bountyBoardUI = new BountyBoardUI(Zone.PortAzure);
        bountyBoardUI.addPanel(1, "Cut the Crab", "A giant crab attacked me in Sunset Cove! I lost my axe. Someone please help!", "I was attacked by a giant crab in Sunset Cove! When I fled, I left behind my axe. Someone please retrieve my axe, you will be rewarded. I will be waiting north-west of Port Azure.\n\n- Ryan");
    }

    @Override
    public BountyBoardUI getBountyBoardUI() {
        return bountyBoardUI;
    }

    @Override
    public void tick() {

    }

    @Override
    public void interact() {
        List<QuestStep> steps = Handler.get().getQuest(QuestList.GettingStarted).getQuestSteps();
        if (steps != null) {
            if (steps.size() >= 3) {
                BountyBoardUI.isOpen = true;
            } else {
                Handler.get().sendMsg("I should probably ask the mayor what to do here first.");
            }
        } else {
            Handler.get().sendMsg("I should probably ask the mayor what to do here first.");
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.bountyBoard2, (int) (x - Handler.get().getGameCamera().getxOffset()), (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }
}
