package dev.ipsych0.itemmaker;

import java.io.File;
import java.util.Objects;

class JSONLoader {

    private static File itemJsonDirectory = new File("src/dev/ipsych0/myrinnia/items/json/");

    static int[] loadIdPrefixesFromJsonFiles() {
        File[] jsonFiles = Objects.requireNonNull(itemJsonDirectory).listFiles();
        int numberOfItems = 0;

        if (jsonFiles != null) {
            numberOfItems = jsonFiles.length;
        }

        int[] ids = new int[numberOfItems];
        for (int i = 0; i < numberOfItems; i++) {
            int id = Integer.parseInt(jsonFiles[i].getName().split("_")[0]);
            ids[i] = id;
        }
        return ids;
    }
}
