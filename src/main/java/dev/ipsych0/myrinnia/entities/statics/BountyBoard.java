package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.skills.ui.BountyBoardUI;

public interface BountyBoard {
    BountyBoardUI getBountyBoardUI();

    default void interact() {
        BountyBoardUI.isOpen = true;
    }

    default void addPanel(int levelRequirement, String task, String description, String fullDescription) {
        getBountyBoardUI().addPanel(levelRequirement, task, description, fullDescription);
    }
}
