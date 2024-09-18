package dev.ipsych0.maprefactoring;

import com.google.gson.JsonParseException;
import dev.ipsych0.myrinnia.utils.tiled.TiledMap;
import dev.ipsych0.myrinnia.utils.tiled.Tileset;
import dev.ipsych0.myrinnia.utils.tiled.TmjMapLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Slf4j
public class FileParser {

    private static final String PATH = "./res/correctTilesetElements.json";
    private static final TmjMapLoader mapLoader = new TmjMapLoader();

    public static void parse(File file) {
        List<Tileset> correctTilesets = mapLoader.loadTilesets(PATH);
        if (correctTilesets.isEmpty()) {
            throw new RuntimeException("Couldn't parse file: %s".formatted(PATH));
        }

        TiledMap map = mapLoader.loadMap(file.getPath()).orElseThrow(
                () -> new JsonParseException("Could not load TiledMap.")
        );

        map.setTilesets(correctTilesets);

        String fixedMap = mapLoader.saveMap(map);
        // Override the world file with the correct values
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            fileOut.write(fixedMap.getBytes());
        } catch (Exception e) {
            log.error("Could not override and save map file: %s".formatted(file.getName()));
        }
    }
}
