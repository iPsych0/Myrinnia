package dev.ipsych0.myrinnia.utils;

import dev.ipsych0.myrinnia.Handler;
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
    public static Map<Integer, List<Integer>> animationMap = new HashMap<>();
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
                private List<Integer> animationIds = new ArrayList<>();
                private int currentId, firstGid;
                private int startX, startY;

                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) {

                    if(!init){
                        firstGid = 1 + lastId;
                        init = true;
                    }

                    if (qName.equalsIgnoreCase("tile")) {
                        // Always increment tile ID by 1, as every next TileSet starts at ID 0 again
                        currentId = 1 + lastId;

                        // If new tile checked, clear old data
                        if(currentId != lastId){
                            animationIds = new ArrayList<>();
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
                        if(currentId != lastId){
                            animationIds.clear();
                        }
                        animationPropertyFound = true;
                        animationIds.add(firstGid + Integer.parseInt(attributes.getValue("tileid")));
                    } else if (qName.equalsIgnoreCase("object")) {
                        startX = (int) Double.parseDouble(attributes.getValue("x"));
                        startY = (int) Double.parseDouble(attributes.getValue("y"));
                    } else if (qName.equalsIgnoreCase("polyline")) {
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
                    if(animationPropertyFound){
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
                Node groundMap = maps.item(layer);
                mapValues[layer] = groundMap.getTextContent();
                layer++;
            }

            return mapValues;
        }

        return null;
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
                imageSource = "/textures/" + imageSource + ".png";

                if(imageSource.equalsIgnoreCase(imagePath)) {
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
