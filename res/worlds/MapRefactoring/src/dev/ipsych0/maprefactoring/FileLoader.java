package dev.ipsych0.maprefactoring;

import java.io.File;

public class FileLoader {

    private File file;

    public FileLoader(File file) {
        this.file = file;
        load();
    }

    private void load(){
        new FileParser(file);
    }
}
