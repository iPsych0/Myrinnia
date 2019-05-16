package dev.ipsych0.myrinnia.tiles;


import dev.ipsych0.myrinnia.gfx.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class AnimatedTile extends Tile {

    private List<Integer> animationTiles;
    private Animation animation;
    private boolean initialized;

    public AnimatedTile(BufferedImage texture, int id, boolean solid, List<Integer> animationTiles) {
        super(texture, id, solid);
        this.animationTiles = animationTiles;
    }

    public AnimatedTile(BufferedImage texture, int id, int[] x, int[] y, List<Integer> animationTiles) {
        super(texture, id, x, y);
        this.animationTiles = animationTiles;
    }

    public AnimatedTile(BufferedImage texture, int id, boolean solid, boolean postRendered, List<Integer> animationTiles) {
        super(texture, id, solid, postRendered);
        this.animationTiles = animationTiles;
    }

    @Override
    public void tick() {
        if(!initialized){
            BufferedImage[] tiles = new BufferedImage[animationTiles.size()];
            for(int i = 0; i < animationTiles.size(); i++){
                tiles[i] = Tile.tiles[animationTiles.get(i)].getTexture();
            }

            animation = new Animation(500, tiles);
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
