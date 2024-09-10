package dev.ipsych0.myrinnia.utils;

public class FileUtils {
    private FileUtils() {
    }

    public static String getResourcePath(String path) {
        return "./res%s".formatted(path);
    }
}
