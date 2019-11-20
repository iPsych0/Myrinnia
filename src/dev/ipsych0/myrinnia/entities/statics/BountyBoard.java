package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.skills.ui.BountyBoardManager;
import dev.ipsych0.myrinnia.skills.ui.BountyBoardUI;

public abstract class BountyBoard extends StaticEntity {

    protected BountyBoard(float x, float y, int width, int height) {
        super(x, y, width, height);

        BountyBoardManager.get().addBoard(this);
    }

    public abstract BountyBoardUI getBountyBoardUI();

    @Override
    public String getName() {
        return "Bounty Board";
    }

    public void addPanel(String task, String description, String fullDescription){
        getBountyBoardUI().addPanel(task, description, fullDescription);
    }

    public void removePanel(){
        getBountyBoardUI().removePanel();
    }

    @Override
    public void interact() {
        BountyBoardUI.isOpen = true;
    }
}
