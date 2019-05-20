package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;

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
    }
}
