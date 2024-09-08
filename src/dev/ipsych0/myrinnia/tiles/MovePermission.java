package dev.ipsych0.myrinnia.tiles;

import java.util.Map;

public class MovePermission {
    public static String NORMAL = "C"; // Default height = 2
    public static String HEIGHT_SEPARATOR = "0";
    public static String HEIGHT_1 = "8";
    public static String HEIGHT_3 = "10";
    public static String HEIGHT_4 = "14";
    public static String HEIGHT_5 = "18";
    public static String HEIGHT_6 = "22";
    public static String OVER_UNDER = "3C";

    // Marker IDs mapped to permissions
    public static Map<Integer, String> map = Map.ofEntries(
            Map.entry(23780, NORMAL),
            Map.entry(23781, HEIGHT_1),
            Map.entry(23782, HEIGHT_3),
            Map.entry(23783, HEIGHT_4),
            Map.entry(23788, HEIGHT_5),
            Map.entry(23789, HEIGHT_6),
            Map.entry(23790, OVER_UNDER),
            Map.entry(23791, HEIGHT_SEPARATOR)
    );


}
