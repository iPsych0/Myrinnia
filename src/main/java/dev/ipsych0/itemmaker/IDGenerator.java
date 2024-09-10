package dev.ipsych0.itemmaker;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class IDGenerator implements Serializable {


    private static final long serialVersionUID = 3518540320277197237L;
    private static final int MAX_IDS = 1024;

    private Set<Integer> uniqueIds = new HashSet<>();

    private static IDGenerator idGenerator;

    private IDGenerator() {

    }

    static IDGenerator getInstance() {
        if (idGenerator == null) {
            idGenerator = new IDGenerator();
        }
        return idGenerator;
    }

    int getNextId() {
        // First ID is always 0
        if (uniqueIds.size() == 0) {
            uniqueIds.add(0);
            IDSerializer.saveIDs();
            return 0;
        }

        for (int i = 0; i < MAX_IDS; i++) {
            if (!uniqueIds.contains(i)) {
                uniqueIds.add(i);
                IDSerializer.saveIDs();
                return i;
            }
        }
        // If no unique IDs are found, exit, because we can't proceed
        System.err.println("No unique IDs available with range 0-1024! Please increase the range!");
        System.exit(1);
        return -1;

    }

    public Set<Integer> getUniqueIds() {
        return uniqueIds;
    }

    public void setUniqueIds(Set<Integer> uniqueIds) {
        this.uniqueIds = uniqueIds;
    }
}
