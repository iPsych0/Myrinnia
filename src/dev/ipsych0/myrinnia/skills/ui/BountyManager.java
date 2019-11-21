package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.entities.statics.BountyBoard;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.util.*;

public class BountyManager {

    private static BountyManager instance;
    private List<BountyBoard> bountyBoards;
    private Set<Bounty> activeBounties;

    public static BountyManager get() {
        if (instance == null) {
            instance = new BountyManager();
        }
        return instance;
    }

    public BountyManager() {
        bountyBoards = new ArrayList<>();
        activeBounties = new HashSet<>();
    }

    public void tick() {
        Iterator<Bounty> it = activeBounties.iterator();
        while (it.hasNext()) {
            Bounty b = it.next();
            if (b.isCompleted()) {
                it.remove();
            }
        }
    }

    public void addBounty(Bounty bounty) {
        activeBounties.add(bounty);
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

    public Bounty getBountyByZoneAndTask(Zone zone, String task) {
        // Get the requested board by zone
        BountyBoard bountyBoard = getBoardByZone(zone);

        // Get the requested task by board
        if (bountyBoard != null) {
            for (Bounty panel : bountyBoard.getBountyBoardUI().getPanels()) {
                if (panel.getTask().equalsIgnoreCase(task)) {
                    return panel;
                }
            }
        }
        throw new IllegalArgumentException("Zone or task not found.");
    }
}
