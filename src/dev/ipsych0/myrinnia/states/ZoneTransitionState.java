package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.worlds.data.Zone;

import java.awt.*;

public class ZoneTransitionState extends AbstractTransitionState {

    /**
     *
     */
    private static final long serialVersionUID = 353118389669820751L;
    private Zone zone;
    private int secondYOffset = 4;
    private int idleTimer = 0;
    private static final int POPUP_HEIGHT = 48;
    private int yOffset = -POPUP_HEIGHT;

    public ZoneTransitionState(Zone zone) {
        super();
        this.zone = zone;
    }

    @Override
    public void tick() {
        Handler.get().getGame().gameState.tick();
        if (alpha == 0 && secondYOffset == -POPUP_HEIGHT) {
            State.setState(Handler.get().getGame().gameState);
        }
    }

    @Override
    public void render(Graphics g) {
        Handler.get().getGame().gameState.render(g);

        // Get the textWidth of the Zone name
        int textWidth = Text.getStringWidth(g, zone.getName(), Assets.font32);

        // Fade in UI element
        if (yOffset < 4) {
            g.drawImage(Assets.genericButton[0], Handler.get().getWidth() / 2 - (textWidth / 2) - 24, yOffset, textWidth + 48, POPUP_HEIGHT, null);
            Text.drawString(g, zone.getName(), Handler.get().getWidth() / 2, yOffset + (POPUP_HEIGHT / 2) - 2,
                    true, Color.YELLOW, Assets.font32);
            yOffset++;
        }
        // Wait 3 seconds, then fade out UI element
        if (yOffset == 4) {
            idleTimer++;
            g.drawImage(Assets.genericButton[0], Handler.get().getWidth() / 2 - (textWidth / 2) - 24, secondYOffset, textWidth + 48, POPUP_HEIGHT, null);
            Text.drawString(g, zone.getName(), Handler.get().getWidth() / 2, secondYOffset + (POPUP_HEIGHT / 2) - 2,
                    true, Color.YELLOW, Assets.font32);
            if (idleTimer > 180 && secondYOffset > -POPUP_HEIGHT) {
                secondYOffset--;
            }
        }

        // Fade from black
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        ((Graphics2D) g).setComposite(ac);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());
        if (alpha - (0.5 / 60) < 0)
            alpha = 0;
        else
            alpha -= (0.5 / 60);

        ((Graphics2D) g).dispose();
    }

}
