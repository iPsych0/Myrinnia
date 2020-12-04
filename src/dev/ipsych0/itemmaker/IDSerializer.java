package dev.ipsych0.itemmaker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.ipsych0.myrinnia.utils.FileUtils;
import dev.ipsych0.myrinnia.utils.Utils;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class IDSerializer {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveIDs() {

        String json = gson.toJson(IDGenerator.getInstance().getUniqueIds());

        try (Writer fileWriter = new BufferedWriter(new FileWriter("src/dev/ipsych0/itemmaker/config/IDs.json"))) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<Integer> loadIDs() {
        InputStream is = Utils.class.getClassLoader().getResourceAsStream("dev/ipsych0/itemmaker/config/IDs.json");
        if (is == null) {
            throw new IllegalArgumentException("ID file could not be found.");
        }
        final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Type type = new TypeToken<HashSet<Integer>>(){}.getType();
        Set<Integer> ids = gson.fromJson(reader, type);

        return ids;
    }
}
