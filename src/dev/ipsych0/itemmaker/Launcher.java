package dev.ipsych0.itemmaker;

import java.util.*;

class Launcher {

    public static void main(String[] args) {
        // Get the IDs
        IDGenerator idGenerator = IDGenerator.getInstance();
        Set<Integer> ids = IDSerializer.loadIDs();

        // Load all item ID prefixes from the file names
        int[] jsonItemIds = JSONLoader.loadIdPrefixesFromJsonFiles();

        if(jsonItemIds == null){
            System.err.println("Failed to load item id prefixes. Closing to prevent further failure.");
            System.exit(1);
        }

        // Find unused IDs
        Arrays.sort(jsonItemIds);

        if(jsonItemIds.length > 0) {
            int itemIndex = 0;
            for (int i = 0; i < ids.size(); i++) {

                try {
                    if (jsonItemIds[itemIndex] != i) {
                        ids.remove(i);
                        itemIndex--;
                    }
                }catch (Exception e){
                    List<Integer> topStackIds = new ArrayList<>();
                    for(int j = i; j < ids.size(); j++) {
                        topStackIds.add(j);
                    }
                    ids.removeAll(topStackIds);
                }

                itemIndex++;
            }
        }else{
            ids.clear();
        }

        int test = 0;
        test = 5 * test;
        List<Integer> test2 = new ArrayList<>();

        // Save the new IDs
        idGenerator.setUniqueIds(ids);
        IDSerializer.saveIDs();

        // Open the window
        new Window();
    }
}
