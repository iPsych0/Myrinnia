package dev.ipsych0.myrinnia.tiles;


import dev.ipsych0.myrinnia.gfx.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AnimatedTile extends Tile {

    private Map<Integer, Integer> animationTiles;
    private Animation animation;
    private boolean initialized;
    private static final int DEFAULT_ANIMATION_SPEED = 500;

    public AnimatedTile(BufferedImage texture, int id, boolean solid, Map<Integer, Integer> animationTiles) {
        super(texture, id, solid);
        this.animationTiles = animationTiles;
    }

    public AnimatedTile(BufferedImage texture, int id, int[] x, int[] y, Map<Integer, Integer> animationTiles) {
        super(texture, id, x, y);
        this.animationTiles = animationTiles;
    }

    public AnimatedTile(BufferedImage texture, int id, boolean solid, boolean postRendered, Map<Integer, Integer> animationTiles) {
        super(texture, id, solid, postRendered);
        this.animationTiles = animationTiles;
    }

    @Override
    public void tick() {
        if(!initialized){
            BufferedImage[] tiles = new BufferedImage[animationTiles.size()];
            Iterator<Integer> animationIt = animationTiles.keySet().iterator();
            int index = 0;
            int animationSpeed = DEFAULT_ANIMATION_SPEED;
            while (animationIt.hasNext()){
                Integer tileId = animationIt.next();
                tiles[index++] = Tile.tiles[tileId].getTexture();
                animationSpeed = animationTiles.get(tileId);
            }

            animation = new Animation(animationSpeed, tiles);
            initialized = true;
        }
        animation.tick();
    }

    @Override
    public void render(Graphics2D g, int x, int y) {
        if(animation != null) {
            g.drawImage(animation.getCurrentFrame(), x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
        }
    }
}
