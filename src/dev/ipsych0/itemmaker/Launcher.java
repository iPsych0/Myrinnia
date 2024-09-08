package dev.ipsych0.itemmaker;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

class Launcher {

    public static void main(String[] args) {

        // Get the IDs
        IDGenerator idGenerator = IDGenerator.getInstance();

        // Check if file already exists.
        if (Files.exists(Paths.get("src/dev/ipsych0/itemmaker/config/IDs.json"))) {
            Set<Integer> ids = IDSerializer.loadIDs();

            // Load all item ID prefixes from the file names
            int[] jsonItemIds = JSONLoader.loadIdPrefixesFromJsonFiles();

            if (jsonItemIds == null) {
                System.err.println("Failed to load item id prefixes. Closing to prevent further failure.");
                System.exit(1);
            }

            // Find unused IDs
            Arrays.sort(jsonItemIds);

            if (jsonItemIds.length > 0 && jsonItemIds.length != ids.size()) {
                int itemIndex = 0;
                for (int i = 0; i < ids.size(); i++) {

                    try {
                        if (jsonItemIds[itemIndex] != i) {
                            ids.remove(i);
                            itemIndex--;
                        }
                    } catch (Exception e) {
                        List<Integer> topStackIds = new ArrayList<>();
                        for (int j = i; j < ids.size(); j++) {
                            topStackIds.add(j);
                        }
                        ids.removeAll(topStackIds);
                    }

                    itemIndex++;
                }
            }

            // Save the new IDs
            idGenerator.setUniqueIds(ids);
            IDSerializer.saveIDs();

        }

        // Open the window
        new Window();
    }
}