package dev.ipsych0.myrinnia.utils.tiled;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;
import dev.ipsych0.myrinnia.SplashScreen;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.utils.tiled.tilesets.AnimationFrame;
import dev.ipsych0.myrinnia.utils.tiled.tilesets.TsjTileset;
import dev.ipsych0.myrinnia.worlds.World;
import dev.ipsych0.myrinnia.worlds.Zone;
import dev.ipsych0.myrinnia.worlds.ZoneTile;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class TmjMapLoader implements MapLoader {

    /**
     * Util class to parse TMJ maps (JSON)
     */
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setStrictness(Strictness.LENIENT)
            .create();

    @Getter
    private TiledMap map;
    @Getter
    private int width, height;
    private String worldPath;

    @Getter
    private TsjTileset tsjTileset;
    private final Map<String, TsjTileset> tsjCache = new HashMap<>();
    private final Set<String> readFiles = new HashSet<>();
    private int tileCount, lastId, firstGid, currentId;


    public void setWorldDoc(String worldPath) {
        this.worldPath = worldPath;
        loadMap(worldPath).ifPresent(map -> {
            this.map = map;
            this.width = map.getWidth();
            this.height = map.getHeight();
        });
    }

    public List<Integer> getTiledFirstGids() {
        return map.getTilesets().stream()
                .map(Tileset::getFirstGid)
                .toList();
    }

    @Override
    public int getImageIndex(String imagePath) {
        List<Tileset> tilesets = this.map.getTilesets();

        for (int i = 0; i < tilesets.size(); i++) {
            Tileset tileset = tilesets.get(i);
            String source = "./res/worlds/" + tileset.getSource();
            // Check if we've cached the tsx file already
            if (!tsjCache.containsKey(source)) {
                setTsxDoc(source);
            } else {
                this.tsjTileset = tsjCache.get(source);
            }

            String imageSrc = "./res/textures/tiles/" + tsjTileset.getName() + ".png";

            if (imageSrc.equalsIgnoreCase(imagePath)) {
                loadTiles(source);
                return i;
            }

        }
        log.info("Couldn't find the index of the image resourcePath for world: {} - in: MapLoader::getImageIndex", this.worldPath);
        log.info("{} - is not a tileset used in Tiled Map Editor.", imagePath);
        return -1;
    }

    @Override
    public int getTileColumns() {
        if (tsjTileset != null) {
            int columns = tsjTileset.getColumns();
            this.tileCount += tsjTileset.getTileCount();
            return columns;
        }

        log.info("Artifact 'columns' not found for resourcePath: {} in: MapLoader::getTileColumns", this.worldPath);
        return -1;
    }

    @Override
    public int getTileCount() {
        return 1 + tileCount;
    }

    @Override
    public void clearTsxCache() {
        tsjCache.clear();
    }

    @Override
    public void setTsxDoc(String path) {
        try (FileReader reader = new FileReader(path)) {
            this.tsjTileset = GSON.fromJson(reader, TsjTileset.class);
            // Add to cache for faster reads
            tsjCache.put(path, tsjTileset);
        } catch (Exception e) {
            log.error("Could not parse json Tilemap.", e);
        }
    }

    @Override
    public void loadTiles(String path) {
        if (readFiles.contains(path)) {
            return;
        }
        readFiles.add(path);

        firstGid = 1 + lastId;

        // Check all tile properties
        tsjTileset.getTiles().forEach(tile -> {
            currentId = firstGid + tile.getId();
            SplashScreen.addLoadedElement();

            Map<String, String> props = toMap(tile.getProperties());
            if (props.containsKey("solid")) {
                Tile.solidTiles.put(currentId, Boolean.parseBoolean(props.get("solid")));
            }
            if (props.containsKey("postRendered")) {
                Tile.postRenderTiles.put(currentId, Boolean.parseBoolean(props.get("postRendered")));
            }
            if (tile.getObjectgroup() != null) {
                List<Point> polyLines = new ArrayList<>();
                tile.getObjectgroup().getObjects().forEach(obj -> {
                    if (!obj.getPolygon().isEmpty()) {
                        obj.getPolygon().forEach(poly -> {
                            Point p = new Point();
                            p.setLocation(poly.getX(), poly.getY());
                            polyLines.add(p);
                        });
                    }
                });
                Tile.polygonTiles.put(currentId, polyLines);
            }
            if (!tile.getAnimation().isEmpty()) {
                Map<Integer, Integer> animationIds = tile.getAnimation().stream()
                        .collect(Collectors.toMap(anim -> firstGid + anim.getTileId(), AnimationFrame::getDuration));
                Tile.animationMap.put(currentId, animationIds);
            }

            lastId++;
        });

        Tile.solidTiles.put(0, false);
        Tile.postRenderTiles.put(0, false);

    }

    public void initEnemiesItemsAndZoneTiles(String path, World world) {
        for (TiledLayer layer : map.getLayers()) {
            if (layer.hasObjects()) {
                parseObjects(layer.getObjects(), world);
            }
        }
    }

    public Entity loadEntity(TileObject obj) {
        // Define the possible packages the class may be in
        String[] packages = {"npcs.", "creatures.", "statics."};
        Map<String, String> props = toMap(obj.getProperties());
        try {
            String className = props.get("npcClass");
            Class<?> c = null;
            for (int i = 0; i < packages.length; i++) {
                try {
                    c = Class.forName("dev.ipsych0.myrinnia.entities." + packages[i] + className);
                    break;
                } catch (Exception e) {
                    // Only use exception when the class is in none of the 3 packages mentioned above
                    if (i == packages.length - 1) {
                        log.error("Exception", e);
                        log.error("Could not find Entity '{}' in any package. (World: {})", className, this.worldPath);
                    }
                }
            }
            // Get all constructors
            for (Constructor t : c.getDeclaredConstructors()) {
                if (t.getParameterCount() == 5) {
                    // Invoke the right constructor based on arguments
                    return (Entity) t.newInstance(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight(), props);
                }
            }
        } catch (Exception e) {
            log.error("Could not create Entity '%s' in world: %s".formatted(props.get("npcClass"), this.worldPath), e);
        }
        return null;
    }

    private Item loadItem(TileObject obj) {
        try {
            Map<String, String> props = toMap(obj.getProperties());

            int itemId = Integer.parseInt(props.get("itemId"));
            int amount = Integer.parseInt(props.get("amount"));

            Item orig = Item.items[itemId];
            Item i = Item.items[itemId].createItem((int) obj.getX(), (int) obj.getY(), amount);
            if (orig.getUse() != null) {
                i.setUse(orig.getUse());
                i.setUseCooldown(orig.getUseCooldown());
            }
            return i;
        } catch (Exception e) {
            log.error("Exception", e);
        }
        return null;
    }

    private ZoneTile loadZoneTile(TileObject obj) {
        Map<String, String> props = toMap(obj.getProperties());
        int goToX = Integer.parseInt(props.get("goToX"));
        int goToY = Integer.parseInt(props.get("goToY"));
        Zone zone = Zone.valueOf(props.get("zone"));
        String customZoneName = props.get("customZoneName");
        String customZoneMusic = props.get("customZoneMusic");
        Creature.Direction direction = null;
        String directionProp = props.get("direction");
        if (directionProp != null) {
            direction = Creature.Direction.valueOf(directionProp);
        }

        return new ZoneTile(zone, (int) obj.getX(), (int) obj.getY(), obj.getWidth(), obj.getHeight(), goToX, goToY, customZoneName, customZoneMusic, direction);
    }

    private Map<String, String> toMap(List<Property> properties) {
        return properties.stream()
                .collect(Collectors.toMap(Property::getName, Property::getValue));
    }

    private void setClassName(TileObject obj, String className) {
        // Change existing prop if it's there
        for (Property prop : obj.getProperties()) {
            if (prop.getName().equalsIgnoreCase("npcClass")) {
                prop.setValue(className);
                return;
            }
        }

        // Otherwise add the property
        Property property = new Property();
        property.setName("npcClass");
        property.setValue(className);
        obj.getProperties().add(property);
    }

    private void parseObjects(List<TileObject> objects, World world) {
        objects.forEach(obj -> {
            TiledObjectType type = TiledObjectType.valueOf(obj.getType().toUpperCase());
            switch (type) {
                case NPC -> world.getEntityManager().addEntity(loadEntity(obj));
                case ITEM -> world.getItemManager().addItem(loadItem(obj), true);
                case COLLISION -> {
                    setClassName(obj, "CollisionTile");
                    world.getEntityManager().addEntity(loadEntity(obj));
                }
                case ZONE_TILE -> world.getZoneTiles().add(loadZoneTile(obj));
                default -> log.error("New object type '%s' not implemented!".formatted(obj.getType()));
            }
        });
    }

    public List<TiledLayer> getMapTiles(World world) {
        map.getLayers().forEach(layer -> {
            if ("Permissions".equalsIgnoreCase(layer.getName())) {
                world.hasPermissionsLayer(true);
            } else if ("Shadows".equalsIgnoreCase(layer.getName())) {
                world.hasShadowsLayer(true);
            }
        });

        return map.getLayers().stream()
                .filter(TiledLayer::hasTiles)
                .toList();
    }

    private Optional<TiledMap> loadMap(String path) {
        try (FileReader reader = new FileReader(path)) {
            return Optional.of(GSON.fromJson(reader, TiledMap.class));
        } catch (Exception e) {
            log.error("Could not parse json Tilemap.", e);
            return Optional.empty();
        }
    }

    /*
     * Testing
     */
    private static TiledMap testLoad(String path) {
        try (FileReader reader = new FileReader(path)) {
            return GSON.fromJson(reader, TiledMap.class);
        } catch (Exception e) {
            log.error("Could not parse json Tilemap.", e);
            return null;
        }
    }

    public static void main(String[] args) {
        TiledMap map = testLoad("./res/worlds/celewynn_inside.json");
        if (map != null) {
            System.out.println("Map loaded successfully: " + map.getWidth() + "x" + map.getHeight());
        } else {
            System.out.println("Failed to load map.");
        }
    }
}
