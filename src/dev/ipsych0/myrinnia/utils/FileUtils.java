package dev.ipsych0.myrinnia.utils;

import dev.ipsych0.myrinnia.Handler;

public class FileUtils {
    private FileUtils() {
    }

    public static String getResourcePath(String path) {
        String resourcePath;
        if (Handler.isJar) {
            resourcePath = Handler.jarFile.getParentFile().getAbsolutePath() + path;
        } else {
            resourcePath = path.replaceFirst("/", Handler.resourcePath);
        }
        return resourcePath;
    }
}
