package dev.ipsych0.myrinnia.gfx;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

class FontLoader {

    public static Font loadFont(String path, float size) {
        try {
            InputStream is = FontLoader.class.getResourceAsStream(path);
            Font f = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, size);
            is.close();
            return f;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
