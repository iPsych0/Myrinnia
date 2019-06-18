package dev.ipsych0.maprefactoring;

import java.io.File;

public class Main {

    private static File worldsDirectory = new File("../");

    public static void main(String[] args) {

        if(worldsDirectory.isDirectory()) {
            for (File f : worldsDirectory.listFiles()) {
                if(f.getAbsolutePath().endsWith("port_azure.tmx") && !f.getAbsolutePath().endsWith("myrinnia_DUMMY_MAP.tmx")) {
                    new FileLoader(f);
                }
            }
        }
    }
}
