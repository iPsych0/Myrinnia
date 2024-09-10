package dev.ipsych0.myrinnia.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dev.ipsych0.myrinnia.abilities.Ability;
import dev.ipsych0.myrinnia.abilities.data.AbilityManager;
import dev.ipsych0.myrinnia.entities.DropTableEntry;
import dev.ipsych0.myrinnia.entities.npcs.Script;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.Use;
import dev.ipsych0.myrinnia.quests.QuestVO;
import dev.ipsych0.myrinnia.shops.Stock;
import dev.ipsych0.myrinnia.states.monologues.Monologue;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Utils {

    private static Gson gson;
    private static int abilityCounter = 0;

    /**
     * Parses Strings to Integers for reading Tiles
     *
     * @param number - String parameter to parse
     * @return - Returns a number if it's valid, otherwise return a default black tile.
     */
    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            log.error("Exception", e);
            log.info("Couldn't load tile with ID: {}", number);
            return 0;
        }
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Polygon.class, new PolygonTypeAdapter())
                    .registerTypeAdapter(Arc2D.Double.class, new Arc2DTypeAdapter())
                    .setPrettyPrinting()
                    .create();
        }
        return gson;
    }

    private static <T> T loadObjectFromJsonFile(String path, final Class<?> clazz) {
        if (path == null) {
            throw new IllegalArgumentException("JSON file cannot be null/empty.");
        }
        try (BufferedReader reader = Files.newBufferedReader(Path.of(path))){
            return getGson().fromJson(reader, (Type) clazz);
        } catch (final Exception e) {
            log.error("Exception", e);
            log.error("Json file could not be loaded.");
            System.exit(1);
        }
        return null;
    }

    private static Class<?> getClassFromString(String jsonFile) {
        String name = null;
        try (BufferedReader reader = Files.newBufferedReader(Path.of(jsonFile))){
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("className")) {
                    name = line.substring(16, line.length() - 1).replace(" ", "");
                    return Class.forName(name);
                }
            }
        } catch (IOException e) {
            log.error("Exception", e);
            log.error("Json file could not be loaded.");
            System.exit(1);
        } catch (ClassNotFoundException e) {
            log.error("Class: {}, is not an existing class.", name);
            System.exit(1);
        }
        return null;
    }

    public static Ability loadAbility(String path) {
        Ability a = loadObjectFromJsonFile("./res/config/abilities/%s".formatted(path), getClassFromString("./res/config/abilities/%s".formatted(path)));
        a.setId(abilityCounter++);
        if (!AbilityManager.abilityMap.containsKey(a.getClass())) {
            AbilityManager.abilityMap.put(a.getClass(), a);
        }
        return a;
    }

    public static <T> T loadAbility(String path, Class<? extends Ability> clazz) {
        T t = loadObjectFromJsonFile("./res/config/abilities/%s".formatted(path), clazz);
        Ability a = ((Ability) t);
        a.setId(abilityCounter++);
        if (!AbilityManager.abilityMap.containsKey(a.getClass())) {
            AbilityManager.abilityMap.put(a.getClass(), a);
        }
        return t;
    }

    public static Item loadItem(String path, BufferedImage sprite) {
        Item i = loadObjectFromJsonFile("./res/config/items/%s".formatted(path), Item.class);
        Item.items[i.getId()] = i;
        i.setTexture(sprite);
        return i;
    }

    public static Item loadItem(String path, BufferedImage sprite, int cooldown, Use use) {
        Item i = loadObjectFromJsonFile("./res/config/items/%s".formatted(path), Item.class);
        Item.items[i.getId()] = i;
        i.setTexture(sprite);
        i.setUse(use);
        i.setUseCooldown(cooldown);
        return i;
    }

    public static Script loadScript(String path) {
        Script s = loadObjectFromJsonFile("./res/config/npcs/%s".formatted(path), Script.class);
        s.getDialogues().sort((o1, o2) -> {
            Integer i1 = o1.getId();
            Integer i2 = o2.getId();
            return i1.compareTo(i2);
        });
        return s;
    }

    public static List<Stock> loadStocks(String path) {
        List<Stock> s = new ArrayList<>(Arrays.asList(loadObjectFromJsonFile("./res/config/shops/%s".formatted(path), Stock[].class)));
        return s;
    }

    public static List<DropTableEntry> loadDropTable(String path) {
        List<DropTableEntry> e = new ArrayList<>(Arrays.asList(loadObjectFromJsonFile("./res/config/droptables/%s".formatted(path), DropTableEntry[].class)));
        return e;
    }

    public static Monologue loadMonologue(String path) {
        Monologue m = loadObjectFromJsonFile("./res/config/monologues/%s".formatted(path), Monologue.class);
        return m;
    }

    public static QuestVO loadQuest(String path) {
        QuestVO q = loadObjectFromJsonFile("./res/config/quests/%s".formatted(path), QuestVO.class);
        return q;
    }

    public static class PolygonTypeAdapter extends TypeAdapter<Polygon> {
        @Override
        public void write(JsonWriter out, Polygon polygon) throws IOException {
            // Serialize Polygon by writing its points
            out.beginObject();
            out.name("points");
            out.beginArray();
            for (int i = 0; i < polygon.npoints; i++) {
                out.beginObject();
                out.name("x").value(polygon.xpoints[i]);
                out.name("y").value(polygon.ypoints[i]);
                out.endObject();
            }
            out.endArray();
            out.endObject();
        }

        @Override
        public Polygon read(JsonReader in) throws IOException {
            // Deserialize Polygon from JSON
            Polygon polygon = new Polygon();
            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                if (name.equals("points")) {
                    in.beginArray();
                    while (in.hasNext()) {
                        in.beginObject();
                        int x = 0, y = 0;
                        while (in.hasNext()) {
                            String pointName = in.nextName();
                            if (pointName.equals("x")) {
                                x = in.nextInt();
                            } else if (pointName.equals("y")) {
                                y = in.nextInt();
                            }
                        }
                        polygon.addPoint(x, y);
                        in.endObject();
                    }
                    in.endArray();
                }
            }
            in.endObject();
            return polygon;
        }
    }

    public static class Arc2DTypeAdapter extends TypeAdapter<Arc2D.Double> {

        @Override
        public void write(JsonWriter out, Arc2D.Double arc) throws IOException {
            // Serialize the Arc2D object
            out.beginObject();
            out.name("x").value(arc.getX());
            out.name("y").value(arc.getY());
            out.name("width").value(arc.getWidth());
            out.name("height").value(arc.getHeight());
            out.name("start").value(arc.getAngleStart());
            out.name("extent").value(arc.getAngleExtent());
            out.name("type").value(arc.getArcType()); // Serialize the type
            out.endObject();
        }

        @Override
        public Arc2D.Double read(JsonReader in) throws IOException {
            // Deserialize the Arc2D object
            double x = 0, y = 0, width = 0, height = 0, start = 0, extent = 0;
            int type = Arc2D.PIE; // Default type

            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                switch (name) {
                    case "x":
                        x = in.nextDouble();
                        break;
                    case "y":
                        y = in.nextDouble();
                        break;
                    case "width":
                        width = in.nextDouble();
                        break;
                    case "height":
                        height = in.nextDouble();
                        break;
                    case "start":
                        start = in.nextDouble();
                        break;
                    case "extent":
                        extent = in.nextDouble();
                        break;
                    case "type":
                        type = in.nextInt();
                        break;
                    default:
                        in.skipValue();
                        break;
                }
            }
            in.endObject();

            // Return the deserialized Arc2D object
            return new Arc2D.Double(x, y, width, height, start, extent, type);
        }
    }

}
