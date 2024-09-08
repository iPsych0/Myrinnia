package dev.ipsych0.maprefactoring;

import java.io.File;

public class Main {

    private static File worldsDirectory = new File("../");

    public static void main(String[] args) {

        if(worldsDirectory.isDirectory()) {
            // Go over all files in the worlds directory
            for (File f : worldsDirectory.listFiles()) {
                // Check if it ends with .tmx extension and skip the dummy map
                if(f.getAbsolutePath().endsWith(".tmx") && !f.getAbsolutePath().endsWith("myrinnia_DUMMY_MAP.tmx")) {
                    new FileParser(f);
                }
            }
        }
    }
}
