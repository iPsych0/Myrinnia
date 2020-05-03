package dev.ipsych0.myrinnia.quests;

import java.io.Serializable;

public interface OnCompletion extends Serializable {
    void giveReward();
}
