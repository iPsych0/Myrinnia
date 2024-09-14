package dev.ipsych0.myrinnia.utils.tiled;

import dev.ipsych0.myrinnia.SplashScreen;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.worlds.World;
import dev.ipsych0.myrinnia.worlds.Zone;
import dev.ipsych0.myrinnia.worlds.ZoneTile;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Deprecated
public class TmxMapLoader implements MapLoader, Serializable {

    /**
     * Util class to parse TMX maps (XML)
     */
    private static final long serialVersionUID = 5948158902228537298L;
    private static SAXParser saxParser;
    private static DocumentBuilder builder;
    private static Map<String, Document> tsxMap = new HashMap<>();
    private static Set<String> readFiles = new HashSet<>();
    private static Document doc, tsxDoc;
    private int tileCount, lastId;
    private String worldPath;

    static {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            saxParser = saxFactory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            log.error("Exception", e);
        }
    }

    public TmxMapLoader() {
    }

    public void setWorldDoc(String worldPath) {
        // Creates new DocumentBuilder on the file
        this.worldPath = worldPath;
        InputStream input;

        try {
            input = new FileInputStream(worldPath);
            doc = builder.parse(input);
            doc.normalize();
            input.close();
        } catch (SAXException | IOException e) {
            log.error("Exception", e);
        }
    }

    public void setTsxDoc(String path) {
        // Creates new DocumentBuilder on the file
        InputStream input;

        try {
            input = new FileInputStream(path);
            tsxDoc = builder.parse(input);
            tsxDoc.normalize();
            tsxMap.put(path, tsxDoc);
            input.close();
        } catch (SAXException | IOException e) {
            log.error("Exception", e);
        }
    }

    /*
     * Returns the width of the map from Tiled
     * @params: String path in OS
     */
    public void loadTiles(String path) {
        // If we've already loaded the tile properties for this tsx file, return
        if (readFiles.contains(path)) {
            return;
        }
        try {
            // Read file and add to list of read files
            InputStream is = new FileInputStream(path);
            readFiles.add(path);
            DefaultHandler handler = new DefaultHandler() {

                private boolean solidPropertyFound = false;
                private boolean postRenderedPropertyFound = false;
                private boolean animationPropertyFound = false;
                private boolean solid;
                private boolean postRender;
                private boolean init;
                private Map<Integer, Integer> animationIds = new HashMap<>();
                private int currentId, firstGid;
                private int startX, startY;

                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) {

                    if (!init) {
                        firstGid = 1 + lastId;
                        init = true;
                    }

                    if (qName.equalsIgnoreCase("tile")) {
                        // Always increment tile ID by 1, as every next TileSet starts at ID 0 again
                        currentId = 1 + lastId;

                        SplashScreen.addLoadedElement();

                        // If new tile checked, clear old data
                        if (currentId != lastId) {
                            animationIds = new HashMap<>();
                        }

                        lastId++;
                    } else if (qName.equalsIgnoreCase("property")) {
                        if (attributes.getValue("name").equalsIgnoreCase("solid")) {
                            solidPropertyFound = true;
                            solid = Boolean.parseBoolean(attributes.getValue("value"));
                        } else if (attributes.getValue("name").equalsIgnoreCase("postRendered")) {
                            postRenderedPropertyFound = true;
                            postRender = Boolean.parseBoolean(attributes.getValue("value"));
                        }
                    } else if (qName.equalsIgnoreCase("frame")) {
                        // If new tile checked, clear old data
                        if (currentId != lastId) {
                            animationIds.clear();
                        }
                        animationPropertyFound = true;
                        int tileId = Integer.parseInt(attributes.getValue("tileid"));
                        int animationSpeed = Integer.parseInt(attributes.getValue("duration"));

                        animationIds.put(firstGid + tileId, animationSpeed);
                    } else if (qName.equalsIgnoreCase("object")) {
                        startX = (int) Double.parseDouble(attributes.getValue("x"));
                        startY = (int) Double.parseDouble(attributes.getValue("y"));
                    } else if (qName.equalsIgnoreCase("polygon")) {
                        if (attributes.getQName(0).equalsIgnoreCase("points")) {
                            List<Point> polylines = new ArrayList<>();
                            // Split spaces to get the points
                            String[] splitBySpace = attributes.getValue("points").split(" ");
                            for (String split : splitBySpace) {
                                // Split the commas to get X and Y
                                String[] coords = split.split(",");
                                // Coords go right-left, so subtract from 32 (x = -12, y = -12)
                                polylines.add(new Point((int) (startX + Double.parseDouble(coords[0])), (int) (startY + Double.parseDouble(coords[1]))));
                            }
                            Tile.polygonTiles.put(currentId, polylines);
                        }
                    }

                }

                public void characters(char ch[], int start, int length) {
                    if (solidPropertyFound) {
                        Tile.solidTiles.put(currentId, solid);
                        solidPropertyFound = false;
                    }
                    if (postRenderedPropertyFound) {
                        Tile.postRenderTiles.put(currentId, postRender);
                        postRenderedPropertyFound = false;
                    }
                    if (animationPropertyFound) {
                        Tile.animationMap.put(currentId, animationIds);
                        animationPropertyFound = false;
                    }

                }

            };

            saxParser.parse(is, handler);

            Tile.solidTiles.put(0, false);
            Tile.postRenderTiles.put(0, false);

            is.close();

        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    /*
     * Returns the width of the map from Tiled
     * @params: String path in OS
     */
    public int getWidth() {
        int mapWidth = 0;

        if (doc != null) {
            // Gets the tag 'layer'
            NodeList tags = doc.getElementsByTagName("layer");
            Node layer = tags.item(tags.getLength() - 1);
            // Gets the width
            mapWidth = Integer.parseInt(layer.getAttributes().getNamedItem("width").getTextContent());
        }

        return mapWidth;
    }

    /*
     * Returns the height of the map from Tiled
     * @params: String path in OS
     */
    public int getHeight() {
        int mapHeight = 0;

        if (doc != null) {
            // Gets the tag 'layer'
            NodeList tags = doc.getElementsByTagName("layer");
            Node layer = tags.item(tags.getLength() - 1);
            // Gets the height
            mapHeight = Integer.parseInt(layer.getAttributes().getNamedItem("height").getTextContent());
        }


        return mapHeight;
    }
    /*
     * Returns all the tile IDs from the map
     * @param: path - input path from OS to read in the .tmx file
     * @returns: String[] mapValues - all Tile IDs per layer
     */
    public List<TiledLayer> getMapTiles(World world) {

        if (doc != null) {
            // Get all tags
            NodeList layers = doc.getElementsByTagName("layer");

            // Check for permissions layer (MUST be named 'Permissions')
            int layer = 0;
            while (layer < layers.getLength()) {
                if ("Permissions".equalsIgnoreCase(layers.item(layer).getAttributes().getNamedItem("name").getTextContent())) {
                    world.hasPermissionsLayer(true);
                    break;
                }
                if ("Shadows".equalsIgnoreCase(layers.item(layer).getAttributes().getNamedItem("name").getTextContent())) {
                    world.hasShadowsLayer(true);
                    break;
                }
                layer++;
            }

            // Index the String[] at the size of the number of layers
            List<TiledLayer> tileLayers = new ArrayList<>();

            // Set variables to iterate over the maps
            layer = 0;

            // Fill the layers in the String[] (The entire String with all Tile IDs per layer)
            while (layer < layers.getLength()) {
                Node csvData = layers.item(layer);
                String rawCsv = csvData.getTextContent();
                // Splits worlds files by spaces and puts them all in an array
                rawCsv = rawCsv.replace("\n", "").replace("\r", "").trim();
                String[] tokens = rawCsv.split(",");
                List<Integer> ids = Arrays.stream(tokens).map(Integer::parseInt).toList();
                TiledLayer tiledLayer = new TiledLayer();
                tiledLayer.setData(ids);
                tiledLayer.setName(layers.item(layer).getAttributes().getNamedItem("name").getTextContent());
                tiledLayer.setWidth(getWidth());
                tiledLayer.setWidth(getHeight());
                tileLayers.add(tiledLayer);
                layer++;
            }

            return tileLayers;
        }

        return null;
    }

    public void initEnemiesItemsAndZoneTiles(String path, World world) {
        try {
            InputStream is = new FileInputStream(path);

            DefaultHandler handler = new DefaultHandler() {

                private int x, y, width, height;
                private TiledObjectType objectType;
                private int objectId;
                private int itemAmount;
                private Zone zone;
                private int goToX, goToY;
                private String customZoneName;
                private String customMusicName;
                private String className;
                private String name;
                private Integer level;
                private String dropTable;
                private String animation;
                private String jsonFile;
                private String itemsShop;
                private Entity.Direction direction;

                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) {

                    // Get object properties
                    if (qName.equalsIgnoreCase("object")) {
                        customZoneName = null;
                        customMusicName = null;
                        className = null;
                        name = null;
                        level = null;
                        dropTable = null;
                        animation = null;
                        jsonFile = null;
                        itemsShop = null;
                        direction = null;
                        x = Integer.parseInt(attributes.getValue("x"));
                        y = Integer.parseInt(attributes.getValue("y"));
                        width = Integer.parseInt(attributes.getValue("width"));
                        height = Integer.parseInt(attributes.getValue("height"));
                        objectId = Integer.parseInt(attributes.getValue("id"));

                        // Get the object type (ITEM, NPC or ZONE_TILE)
                        try {
                            objectType = TiledObjectType.valueOf(attributes.getValue("type").toUpperCase());
                        } catch (Exception e) {
                            log.error("Object {}: aObjectType '{}' is not a valid enum value. Typo or missing?", objectId, attributes.getValue("value"));
                        }

                        if (TiledObjectType.COLLISION == objectType) {
                            className = "CollisionTile";
                            Entity e = loadEntity(world, className, x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
                            addToWorld(world, e);
                        }
                    } else if (qName.equalsIgnoreCase("property")) {
                        // Get the class name for the NPC
                        if (attributes.getValue("name").equalsIgnoreCase("npcClass")) {
                            if (TiledObjectType.NPC == objectType) {
                                className = attributes.getValue("value");
                                Entity e = loadEntity(world, className, x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
                                addToWorld(world, e);
                            }
                        } else if (attributes.getValue("name").equalsIgnoreCase("name")) {
                            if (TiledObjectType.NPC == objectType) {
                                name = attributes.getValue("value");
                            }
                        } else if (attributes.getValue("name").equalsIgnoreCase("level")) {
                            if (TiledObjectType.NPC == objectType) {
                                level = Integer.valueOf(attributes.getValue("value"));
                            }
                        } else if (attributes.getValue("name").equalsIgnoreCase("dropTable")) {
                            if (TiledObjectType.NPC == objectType) {
                                dropTable = attributes.getValue("value");
                            }
                        } else if (attributes.getValue("name").equalsIgnoreCase("jsonFile")) {
                            if (TiledObjectType.NPC == objectType) {
                                jsonFile = attributes.getValue("value");
                            }
                        } else if (attributes.getValue("name").equalsIgnoreCase("animation")) {
                            if (TiledObjectType.NPC == objectType) {
                                animation = attributes.getValue("value");
                            }
                        } else if (attributes.getValue("name").equalsIgnoreCase("itemsShop")) {
                            if (TiledObjectType.NPC == objectType) {
                                itemsShop = attributes.getValue("value");
                            }
                        } else if (TiledObjectType.NPC == objectType && attributes.getValue("name").equalsIgnoreCase("direction")) {
                            try {
                                direction = Entity.Direction.valueOf(attributes.getValue("value").toUpperCase());
                            } catch (Exception e) {
                                log.error("Could not convert {} to NPC Direction Enum.", attributes.getValue("value"));
                            }
                        } else if (attributes.getValue("name").equalsIgnoreCase("amount")) {
                            if (TiledObjectType.ITEM == objectType) {
                                itemAmount = Integer.parseInt(attributes.getValue("value"));
                            }
                            // Get the itemID
                        } else if (attributes.getValue("name").equalsIgnoreCase("itemId")) {
                            if (TiledObjectType.ITEM == objectType) {
                                int itemId = Integer.parseInt(attributes.getValue("value"));
                                loadItem(world, x, y, itemId, itemAmount);
                            }
                            // Get the new x-pos for the zone tile
                        } else if (attributes.getValue("name").equalsIgnoreCase("goToX")) {
                            if (TiledObjectType.ZONE_TILE == objectType) {
                                goToX = Integer.parseInt(attributes.getValue("value"));
                            }
                            // Get the new y-pos for the zone tile
                        } else if (attributes.getValue("name").equalsIgnoreCase("goToY")) {
                            if (TiledObjectType.ZONE_TILE == objectType) {
                                goToY = Integer.parseInt(attributes.getValue("value"));
                            }
                            // Optional property to get custom zone name
                        } else if (attributes.getValue("name").equalsIgnoreCase("customZoneName")) {
                            if (TiledObjectType.ZONE_TILE == objectType) {
                                customZoneName = attributes.getValue("value");
                            }
                            // Get the zone to change to
                        } else if (attributes.getValue("name").equalsIgnoreCase("customMusicName")) {
                            if (TiledObjectType.ZONE_TILE == objectType) {
                                customMusicName = attributes.getValue("value");
                            }
                            // Get the direction to interact with
                        } else if (TiledObjectType.ZONE_TILE == objectType && attributes.getValue("name").equalsIgnoreCase("direction")) {
                            try {
                                direction = Entity.Direction.valueOf(attributes.getValue("value").toUpperCase());
                            } catch (Exception e) {
                                log.error("Could not convert {} to NPC Direction Enum.", attributes.getValue("value"));
                            }
                            // Get the zone to change to
                        } else if (attributes.getValue("name").equalsIgnoreCase("zone")) {
                            if (TiledObjectType.ZONE_TILE == objectType) {
                                try {
                                    zone = Zone.valueOf(attributes.getValue("value"));
                                    world.getZoneTiles().add(new ZoneTile(zone, x, y, width, height, goToX, goToY, customZoneName, customMusicName, direction));
                                } catch (Exception e) {
                                    log.error("Could not load zone_tile for '{}'. Perhaps a typo? The value is case-sensitive. Please check myrinnia.worlds.data.Zone for values.", attributes.getValue("value"));
                                }
                            }
                        }
                    }

                }

            };

            saxParser.parse(is, handler);

            is.close();

        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    public static Entity loadEntity(World world, String className, int x, int y, int width, int height, String name, Integer level, String dropTable, String jsonFile, String animation, String itemsShop, Entity.Direction direction) {
        // Define the possible packages the class may be in
        String[] packages = {"npcs.", "creatures.", "statics."};
        try {
            Class<?> c = null;
            for (int i = 0; i < packages.length; i++) {
                try {
                    c = Class.forName("dev.ipsych0.myrinnia.entities." + packages[i] + className);
                    break;
                } catch (Exception e) {
                    // Only use exception when the class is in none of the 3 packages mentioned above
                    if (i == packages.length - 1) {
                        log.error("Exception", e);
                        log.error("Could not find Entity '{}' in any package. (World: {})", className, world.getWorldPath());
                    }
                }
            }
            // Get all constructors
            Constructor[] cstr = c.getDeclaredConstructors();
            Constructor cst = null;

            // Use default constructor if no custom properties
            int constructorArguments = 10;
            if (Creature.class.isAssignableFrom(c)) {
                // For creatures, call
                constructorArguments++;
            }

            for (Constructor t : cstr) {
                if (t.getParameterCount() == constructorArguments) {
                    cst = t;
                    break;
                }
            }

            if (level == null) {
                level = 1;
            }

            // Invoke the right constructor based on arguments
            Entity e;
            if (constructorArguments == 10) {
                e = (Entity) cst.newInstance(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
            } else {
                e = (Entity) cst.newInstance(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop, direction);
            }
            return e;
        } catch (Exception e) {
            log.error("Exception", e);
            log.error("Could not create Entity '{}' in world: {}", className, world.getWorldPath());
        }
        return null;
    }

    private static void addToWorld(World world, Entity e){
        // Add the NPC to the world
        world.getEntityManager().addEntity(e);
    }

    private static void loadItem(World world, int x, int y, int itemId, int amount) {
        try {
            Item orig = Item.items[itemId];
            Item i = Item.items[itemId].createItem(x, y, amount);
            if (orig.getUse() != null) {
                i.setUse(orig.getUse());
                i.setUseCooldown(orig.getUseCooldown());
            }
            world.getItemManager().addItem(i, true);
        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    public List<Integer> getTiledFirstGids() {

        if (doc != null) {
            // Get all tileset objects
            NodeList tilesets = doc.getElementsByTagName("tileset");

            List<Integer> firstGids = new ArrayList<>();

            for (int i = 0; i < tilesets.getLength(); i++) {
                Node n = tilesets.item(i);
                Node inner = n.getAttributes().getNamedItem("firstgid");
                firstGids.add(Integer.parseInt(inner.getNodeValue()));
            }
            return firstGids;
        }

        log.info("Couldn't load in the Tiled firstGIDs. MapLoader::getFirstGids");
        return null;
    }

    public void clearTsxCache() {
        tsxMap.clear();
    }


    public int getImageIndex(String imagePath) {
        String imageSource = null;

        if (doc != null) {
            // Get all tileset objects
            NodeList tilesets = doc.getElementsByTagName("tileset");

            for (int i = 0; i < tilesets.getLength(); i++) {
                // Get the tileset property
                Node tilesetObject = tilesets.item(i);

                // Get the contents of a tileset
                String tsxFile = tilesetObject.getAttributes().item(1).getNodeValue();

                // Get the source path and remove the first two dots
                tsxFile = "./res/worlds/" + tsxFile;

                // Only reload the document if we don't have a reference anymore.
                if (tsxMap.get(tsxFile) == null) {
                    setTsxDoc(tsxFile);
                } else {
                    tsxDoc = tsxMap.get(tsxFile);
                }

                NodeList tileset = tsxDoc.getElementsByTagName("tileset");

                // Get the image name
                imageSource = tileset.item(0).getAttributes().item(1).getNodeValue();

                // Get the source path and remove the first two dots
                imageSource = "./res/textures/tiles/" + imageSource + ".png";

                if (imageSource.contains(imagePath)) {
                    loadTiles(tsxFile);
                    return i;
                }
            }
        }

        log.info("Couldn't find the index of the image resourcePath for world: {} - in: MapLoader::getImageIndex", this.worldPath);
        log.info("{} - is not a tileset used in Tiled Map Editor.", imagePath);
        return -1;
    }

    public int getTileCount() {
        return 1 + tileCount;
    }

    public int getTileColumns() {

        int columns = -1;

        if (tsxDoc != null) {
            // Get all tileset objects
            NodeList tilesets = tsxDoc.getElementsByTagName("tileset");

            Node n = tilesets.item(0);
            Node inner = n.getAttributes().getNamedItem("columns");
            Node innerCount = n.getAttributes().getNamedItem("tilecount");
            columns = Integer.parseInt(inner.getNodeValue());
            tileCount += Integer.parseInt(innerCount.getNodeValue());
            return columns;
        }

        log.info("Artifact 'columns' not found for resourcePath: {} in: MapLoader::getTileColumns", this.worldPath);
        return columns;
    }
}
