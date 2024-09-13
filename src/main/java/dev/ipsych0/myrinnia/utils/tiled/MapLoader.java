package dev.ipsych0.myrinnia.utils.tiled;

import dev.ipsych0.myrinnia.worlds.World;

import java.util.List;

public interface MapLoader {

    void setWorldDoc(String worldPath);
    int getWidth();
    int getHeight();
    List<TiledLayer> getMapTiles(World world);
    void initEnemiesItemsAndZoneTiles(String path, World world);
    List<Integer> getTiledFirstGid();
    int getImageIndex(String imagePath);
    int getTileColumns();
    int getTileCount();
    void clearTsxCache();
    void setTsxDoc(String path);
    void loadTiles(String path);
}
