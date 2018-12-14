package dev.ipsych0.itemmaker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

class IDSerializer {

    static void saveIDs() {
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
            if (Files.notExists(Paths.get("src/dev/ipsych0/itemmaker/config/IDs.dat"))) {
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
