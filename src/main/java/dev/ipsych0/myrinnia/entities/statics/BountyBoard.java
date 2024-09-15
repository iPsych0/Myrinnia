package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.skills.ui.BountyBoardUI;
import dev.ipsych0.myrinnia.skills.ui.BountyManager;

import java.util.Map;

public abstract class BountyBoard extends StaticEntity {

    protected BountyBoard(float x, float y, int width, int height, Map<String,String> props) {
        super(x, y, width, height, props);

        BountyManager.get().addBoard(this);
    }

    public abstract BountyBoardUI getBountyBoardUI();

    @Override
    public String getName() {
        return "Bounty Board";
    }

    public void addPanel(int levelRequirement, String task, String description, String fullDescription) {
        getBountyBoardUI().addPanel(levelRequirement, task, description, fullDescription);
    }

    @Override
    public void interact() {
        BountyBoardUI.isOpen = true;
    }
}
