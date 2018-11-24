package dev.ipsych0.itemmaker;

import dev.ipsych0.myrinnia.utils.Utils;

import java.io.File;
import java.util.Objects;

class JSONLoader {


    static int[] loadIdPrefixesFromJsonFiles() {
        File[] jsonFiles = Objects.requireNonNull(Utils.itemJsonDirectory).listFiles();
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
