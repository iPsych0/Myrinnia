package dev.ipsych0.itemmaker;

import java.util.*;

class Launcher {

    public static void main(String[] args) {

        IDSerializer.validateIDs();

        // Open the window
        new Window();
    }
}
