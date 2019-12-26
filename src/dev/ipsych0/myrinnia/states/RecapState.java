package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.recap.RecapEvent;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class RecapState extends State {

    /**
     *
     */
    private static final long serialVersionUID = 1824990655259776935L;
    private UIImageButton continueButton;
    private UIManager uiManager;
    private static int index = 0;

    public RecapState() {
        this.continueButton = new UIImageButton(Handler.get().getWidth() / 2 - 160, Handler.get().getHeight() - 128, 320, 96, Assets.genericButton);
        uiManager = new UIManager();
        uiManager.addObject(continueButton);

    }

    @Override
    public void tick() {
        uiManager.tick();
        if (Handler.get().getRecapManager().getEvents().size() == 0) {
            State.setState(new UITransitionState(Handler.get().getGame().gameState));
            Handler.get().playMusic(Handler.get().getPlayer().getZone());
        } else {
            Rectangle mouse = Handler.get().getMouse();
            if (continueButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                if (index == Handler.get().getRecapManager().getEvents().size() - 1) {
                    State.setState(new UITransitionState(Handler.get().getGame().gameState));
                    Handler.get().playMusic(Handler.get().getPlayer().getZone());
                    hasBeenPressed = false;
                } else {
                    State.setState(new UITransitionState(this));
                    index++;
                    hasBeenPressed = false;
                }

            }
        }


    }

    @Override
    public void render(Graphics2D g) {
        if (Handler.get().getRecapManager().getEvents().size() > 0) {

            RecapEvent event = Handler.get().getRecapManager().getEvents().get(index);
            g.drawImage(event.getImg(), 0, 0, Handler.get().getWidth(), Handler.get().getHeight(), null);
            g.setColor(new Color(27, 27, 27, 196));
            g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());

            Text.drawString(g, "You last did... [" + (index + 1) + "/" + Handler.get().getRecapManager().getEvents().size() + "]", Handler.get().getWidth() / 2, 96, true, Color.YELLOW, Assets.font32);

            Text.drawString(g, event.getDescription(), Handler.get().getWidth() / 2, Handler.get().getHeight() / 2 - 96, true, Color.YELLOW, Assets.font32);

            uiManager.render(g);
            Text.drawString(g, "Continue", continueButton.x + continueButton.width / 2, continueButton.y + continueButton.height / 2, true, Color.YELLOW, Assets.font32);
        }
    }

}
