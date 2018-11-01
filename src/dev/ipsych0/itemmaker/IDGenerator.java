package dev.ipsych0.itemmaker;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class IDGenerator implements Serializable {


    private static final long serialVersionUID = 3518540320277197237L;

    Set<Integer> uniqueIds = new HashSet<>();

    public static void setIdGenerator(IDGenerator idGenerator) {
        IDGenerator.idGenerator = idGenerator;
    }

    private static IDGenerator idGenerator;

    private IDGenerator() {

    }

    public static IDGenerator getInstance() {
        if (idGenerator == null) {
            idGenerator = new IDGenerator();
        }
        return idGenerator;
    }

    public int findNextId() {
        uniqueIds = IDSerializer.loadIDs();


        // First ID is always 0
        if (uniqueIds.size() == 0) {
            uniqueIds.add(0);
            IDSerializer.saveIDs();
            return 0;
        }

        // Hardcoded number of unique IDs (1024 as of now)
        for (int i = 0; i < 1024; i++) {
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
