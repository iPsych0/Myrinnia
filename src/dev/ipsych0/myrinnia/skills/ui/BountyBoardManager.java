package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.entities.statics.BountyBoard;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.util.ArrayList;
import java.util.List;

public class BountyBoardManager {

    private static BountyBoardManager instance;
    private List<BountyBoard> bountyBoards;

    public static BountyBoardManager get() {
        if (instance == null) {
            instance = new BountyBoardManager();
        }
        return instance;
    }

    public BountyBoardManager() {
        bountyBoards = new ArrayList<>();
    }

    public void addBoard(BountyBoard board) {
        bountyBoards.add(board);
    }

    public BountyBoard getBoardByZone(Zone zone) {
        for (BountyBoard bountyBoard : bountyBoards) {
            if (bountyBoard.getBountyBoardUI().getZone().equals(zone)) {
                return bountyBoard;
            }
        }
        throw new IllegalArgumentException("Zone not found");
    }
}
