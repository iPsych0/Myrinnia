package dev.ipsych0.itemmaker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class IDSerializer {

    static void validateIDs(){
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

        if(jsonItemIds.length > 0 && jsonItemIds.length != ids.size()) {
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

        // Save the new IDs
        idGenerator.setUniqueIds(ids);
        IDSerializer.saveIDs();
    }

    static void saveIDs(){
        FileOutputStream f;
        ObjectOutputStream o;
        try {
            f = new FileOutputStream(new File("src/dev/ipsych0/itemmaker/config/IDs.dat"));

            // Write the Handler object
            o = new ObjectOutputStream(f);
            o.writeObject(IDGenerator.getInstance().getUniqueIds());
            o.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Set<Integer> loadIDs() {
        Set<Integer> ids = new HashSet<>();
        FileInputStream fin;
        ObjectInputStream oin;
        try {
            if(Files.notExists(Paths.get("src/dev/ipsych0/itemmaker/config/IDs.dat"))){
                saveIDs();
                return new HashSet<>();
            }
            fin = new FileInputStream("src/dev/ipsych0/itemmaker/config/IDs.dat");
            oin = new ObjectInputStream(fin);

            ids = (Set<Integer>) oin.readObject();

            oin.close();
            fin.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return ids;
    }
}
