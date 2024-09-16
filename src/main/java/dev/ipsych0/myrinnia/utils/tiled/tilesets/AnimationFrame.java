package dev.ipsych0.myrinnia.utils.tiled.tilesets;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AnimationFrame {
    @SerializedName("tileid")
    private int tileId;     // The ID of the tile for this animation frame
    private int duration;   // Duration in milliseconds
}
