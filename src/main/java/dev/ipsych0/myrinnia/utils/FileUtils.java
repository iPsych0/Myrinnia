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
        return "./res%s".formatted(path);
    }
}
