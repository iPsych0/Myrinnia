package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class GraphicsState extends State {

    private Rectangle overlay;
    private UIManager uiManager;
    private UIImageButton fullscreenButton;

    public GraphicsState() {
        this.uiManager = new UIManager();

        overlay = new Rectangle(Handler.get().getWidth() / 2 - 320, 160, 640, 417);

        fullscreenButton = new UIImageButton(overlay.x + 24, overlay.y + 32, 64, 32, Assets.genericButton);

        uiManager.addObject(fullscreenButton);

    }

    @Override
    public void tick() {
        uiManager.tick();

        Rectangle mouse = Handler.get().getMouse();

        if(fullscreenButton.contains(mouse)){
            if (Handler.get().getMouseManager().isLeftPressed() && !Handler.get().getMouseManager().isDragged() && hasBeenPressed) {
                Handler.get().getGame().getDisplay().toggleFullScreen();
                hasBeenPressed = false;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        uiManager.render(g);

        g.drawImage(Assets.genericButton[1], overlay.x + overlay.width / 4, overlay.y + 64, overlay.width - overlay.width / 4 - 32, overlay.height - 168, null);

        Text.drawString(g, "Display:", overlay.x + 8, overlay.y + 32, false, Color.YELLOW, Assets.font24);
        Text.drawString(g, "Display mode:", overlay.x + 8, overlay.y + 80, false, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Resolution:", overlay.x + 8, overlay.y + 112, false, Color.YELLOW, Assets.font20);

        Text.drawString(g, "Graphics:", overlay.x + 8, overlay.y + 192, false, Color.YELLOW, Assets.font24);

        Text.drawString(g, "Anti-aliasing:", overlay.x + 8, overlay.y + 240, false, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Render quality:", overlay.x + 8, overlay.y + 272, false, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Text quality:", overlay.x + 8, overlay.y + 304, false, Color.YELLOW, Assets.font20);

    }
}
