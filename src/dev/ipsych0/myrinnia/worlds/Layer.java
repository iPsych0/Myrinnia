package dev.ipsych0.myrinnia.worlds;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    private List<NonNullTile> notEmptyTiles;

    public Layer() {
        notEmptyTiles = new ArrayList<>();
    }

    public void addTile(int id, int xPos, int yPos) {
        if (id == 0) {
            throw new IllegalArgumentException("Tiles cannot be empty. This list contains valid Tiles to be rendered.");
        }
        notEmptyTiles.add(new NonNullTile(id, xPos, yPos));
    }

    public List<NonNullTile> getNotEmptyTiles() {
        return notEmptyTiles;
    }

    public void setNotEmptyTiles(List<NonNullTile> notEmptyTiles) {
        this.notEmptyTiles = notEmptyTiles;
    }
}
