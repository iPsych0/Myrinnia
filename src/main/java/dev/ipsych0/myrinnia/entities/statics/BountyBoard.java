package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.skills.ui.BountyBoardUI;
import dev.ipsych0.myrinnia.skills.ui.BountyManager;

public abstract class BountyBoard extends Entity {

    protected BountyBoard() {

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
