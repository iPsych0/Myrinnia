package dev.ipsych0.myrinnia.utils;

import dev.ipsych0.myrinnia.Handler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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

    public static InputStream getResource(String path) {
        try (InputStream is = new FileInputStream(getResourcePath(path))){
            return is;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
