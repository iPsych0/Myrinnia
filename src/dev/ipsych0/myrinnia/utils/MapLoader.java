package dev.ipsych0.myrinnia.utils;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.tiles.Tile;
import dev.ipsych0.myrinnia.worlds.data.World;
import dev.ipsych0.myrinnia.worlds.data.Zone;
import dev.ipsych0.myrinnia.worlds.data.ZoneTile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapLoader implements Serializable {

    /**
     * Util class to parse TMX maps
     */
    private static final long serialVersionUID = 5948158902228537298L;
    private static SAXParser saxParser;
    private static DocumentBuilder builder;
    public static Map<Integer, Boolean> solidTiles = new HashMap<>();
    public static Map<Integer, Boolean> postRenderTiles = new HashMap<>();
    public static Map<Integer, List<Point>> polygonTiles = new HashMap<>();
    public static Map<Integer, Map<Integer, Integer>> animationMap = new HashMap<>();
    private static Document doc, tsxDoc;
    private static int tileCount, lastId;

    static {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            saxParser = saxFactory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    private MapLoader() {
    }

    public static void setWorldDoc(String path) {
        // Creates new DocumentBuilder on the file
        InputStream is = Handler.class.getResourceAsStream(path);
        try {
            doc = builder.parse(is);
            doc.normalize();
            is.close();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void setTsxDoc(String path) {
        // Creates new DocumentBuilder on the file
        InputStream is = Handler.class.getResourceAsStream(path);
        try {
            tsxDoc = builder.parse(is);
            tsxDoc.normalize();
            is.close();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Returns the width of the map from Tiled
     * @params: String path in OS
     */
    public static void setSolidTiles(String path) {
        try {
            InputStream is = MapLoader.class.getResourceAsStream(path);
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
                            polygonTiles.put(currentId, polylines);
                        }
                    }

                }

                public void characters(char ch[], int start, int length) {
                    if (solidPropertyFound) {
                        solidTiles.put(currentId, solid);
                        solidPropertyFound = false;
                    }
                    if (postRenderedPropertyFound) {
                        postRenderTiles.put(currentId, postRender);
                        postRenderedPropertyFound = false;
                    }
                    if (animationPropertyFound) {
                        animationMap.put(currentId, animationIds);
                        animationPropertyFound = false;
                    }

                }

            };

            saxParser.parse(is, handler);

            solidTiles.put(0, false);
            postRenderTiles.put(0, false);

            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Returns the width of the map from Tiled
     * @params: String path in OS
     */
    public static int getMapWidth() {
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
    public static int getMapHeight() {
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
    public static String[] getMapTiles(String path) {

        if (doc != null) {
            // Get all tags
            NodeList maps = doc.getElementsByTagName("layer");

            // Index the String[] at the size of the number of layers
            String[] mapValues = new String[maps.getLength()];

            // Set variables to iterate over the maps
            int layer = 0;

            // Fill the layers in the String[] (The entire String with all Tile IDs per layer)
            while (layer < mapValues.length) {
                Node csvData = maps.item(layer);
                mapValues[layer] = csvData.getTextContent();
                layer++;
            }

            return mapValues;
        }

        return null;
    }

    public static void initEnemiesItemsAndZoneTiles(String path, World world) {
        try {
            InputStream is = MapLoader.class.getResourceAsStream(path);
            DefaultHandler handler = new DefaultHandler() {

                private int x, y, width, height;
                private TiledObjectType objectType;
                private int objectId;
                private int itemAmount;
                private Zone zone;
                private int goToX, goToY;
                private String customZoneName;
                private String customShopName;
                private String className;

                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) {

                    // Get object properties
                    if (qName.equalsIgnoreCase("object")) {
                        customZoneName = null;
                        customShopName = null;
                        className = null;
                        x = Integer.parseInt(attributes.getValue("x"));
                        y = Integer.parseInt(attributes.getValue("y"));
                        width = Integer.parseInt(attributes.getValue("width"));
                        height = Integer.parseInt(attributes.getValue("height"));
                        objectId = Integer.parseInt(attributes.getValue("id"));

                        // Get the object type (ITEM, NPC or ZONE_TILE)
                        try {
                            objectType = TiledObjectType.valueOf(attributes.getValue("type").toUpperCase());
                        } catch (Exception e) {
                            System.err.println("Object " + objectId + ": aObjectType '" + attributes.getValue("value") + "' is not a valid enum value. Typo or missing?");
                        }
                    } else if (qName.equalsIgnoreCase("property")) {
                        // Get the class name for the NPC
                        if (attributes.getValue("name").equalsIgnoreCase("npcClass")) {
                            if (TiledObjectType.NPC == objectType) {
                                className = attributes.getValue("value");
                                loadEntity(world, className, customShopName, x, y, width, height);
                            }
                        }
                        // Get the custom shop name if present
                        else if (attributes.getValue("name").equalsIgnoreCase("customShopName")) {
                            if (TiledObjectType.NPC == objectType) {
                                customShopName = attributes.getValue("value");
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
                                goToX = Integer.parseInt(attributes.getValue("value")) * Tile.TILEWIDTH;
                            }
                            // Get the new y-pos for the zone tile
                        } else if (attributes.getValue("name").equalsIgnoreCase("goToY")) {
                            if (TiledObjectType.ZONE_TILE == objectType) {
                                goToY = Integer.parseInt(attributes.getValue("value")) * Tile.TILEHEIGHT;
                            }
                            // Optional property to get custom zone name
                        } else if (attributes.getValue("name").equalsIgnoreCase("customZoneName")) {
                            if (TiledObjectType.ZONE_TILE == objectType) {
                                customZoneName = attributes.getValue("value");
                            }
                            // Get the zone to change to
                        } else if (attributes.getValue("name").equalsIgnoreCase("zone")) {
                            if (TiledObjectType.ZONE_TILE == objectType) {
                                try {
                                    zone = Zone.valueOf(attributes.getValue("value"));
                                    world.getZoneTiles().add(new ZoneTile(zone, x, y, width, height, goToX, goToY, customZoneName));
                                } catch (Exception e) {
                                    System.err.println("Could not load zone_tile for '" + attributes.getValue("value") + "'. Perhaps a typo? The value is case-sensitive. Please check myrinnia.worlds.data.Zone for values.");
                                }
                            }
                        }
                    }

                }

            };

            saxParser.parse(is, handler);

            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadEntity(World world, String className, String customShopName, int x, int y, int width, int height) {
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
                        e.printStackTrace();
                        System.err.println("Could not find Entity '" + className + "' in any package. (World: " + world.getWorldPath() + ")");
                    }
                }
            }
            // Get all constructors
            Constructor[] cstr = c.getDeclaredConstructors();
            Constructor cst = null;



            // Use default constructor if no custom shop name, otherwise use custom shop name constructor for NPC
            int constructorArguments = 2;

            if (customShopName != null) {
                constructorArguments = 3;
            }

            if (width != Creature.DEFAULT_CREATURE_WIDTH || height != Creature.DEFAULT_CREATURE_HEIGHT) {
                constructorArguments = 4;
            }

            for (Constructor t : cstr) {
                if (t.getParameterCount() == constructorArguments) {
                    cst = t;
                    break;
                }
            }

            // Invoke the right constructor based on arguments
            Entity e = null;
            if (constructorArguments == 2) {
                e = (Entity) cst.newInstance(x, y);
            } else if (constructorArguments == 3) {
                e = (Entity) cst.newInstance(customShopName, x, y);
            } else if (constructorArguments == 4) {
                e = (Entity) cst.newInstance(x, y, width, height);
            }

            // Add the NPC to the world
            world.getEntityManager().addEntity(e);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could not create Entity '" + className + "' in world: " + world.getWorldPath());
        }
    }

    private static void loadItem(World world, int x, int y, int itemId, int amount) {
        try {
            Item i = Item.items[itemId];
            world.getItemManager().addItem(i.createItem(x, y, amount), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] getTiledFirstGid() {

        if (doc != null) {
            // Get all tileset objects
            NodeList tilesets = doc.getElementsByTagName("tileset");

            int[] firstGids = new int[tilesets.getLength()];

            for (int i = 0; i < tilesets.getLength(); i++) {
                Node n = tilesets.item(i);
                Node inner = n.getAttributes().getNamedItem("firstgid");
                firstGids[i] = Integer.parseInt(inner.getNodeValue());
            }
            return firstGids;
        }

        System.out.println("Couldn't load in the Tiled firstGIDs. MapLoader::getFirstGids");
        return null;
    }

    public static int getImageIndex(String worldPath, String imagePath) {
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
                tsxFile = "/worlds/" + tsxFile;

                setTsxDoc(tsxFile);

                NodeList tileset = tsxDoc.getElementsByTagName("tileset");

                // Get the image name
                imageSource = tileset.item(0).getAttributes().item(1).getNodeValue();

                // Get the source path and remove the first two dots
                imageSource = "/textures/tiles/" + imageSource + ".png";

                if (imageSource.equalsIgnoreCase(imagePath)) {
                    setSolidTiles(tsxFile);
                    return i;
                }
            }
        }

        System.out.println("Couldn't find the index of the image resourcePath for world: " + worldPath + " - in: MapLoader::getImageIndex");
        System.out.println(imagePath + " - is not a tileset used in Tiled Map Editor.");
        return -1;
    }

    public static int getTileCount() {
        return 1 + tileCount;
    }

    public static int getTileColumns(String worldPath) {

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

        System.out.println("Artifact 'columns' not found for resourcePath: " + worldPath + " in: MapLoader::getTileColumns");
        return columns;
    }
}
