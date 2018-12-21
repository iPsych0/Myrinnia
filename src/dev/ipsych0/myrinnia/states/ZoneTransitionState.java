package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class ZoneTransitionState extends AbstractTransitionState {

    /**
     *
     */
    private static final long serialVersionUID = 353118389669820751L;
    private Zone zone;
    private int yOffset = -32;
    private int secondYOffset = 0;
    private int idleTimer = 0;

    public ZoneTransitionState(Zone zone) {
        super();
        this.zone = zone;
    }

    @Override
    public void tick() {
        Handler.get().getGame().gameState.tick();
        if (alpha == 0 && secondYOffset == -32) {
            State.setState(Handler.get().getGame().gameState);
        }
    }

    @Override
    public void render(Graphics g) {
        Handler.get().getGame().gameState.render(g);

        // Get the textWidth of the Zone name
        int textWidth = Text.getStringWidth(g, zone.toString(), Assets.font20);

        // Fade in UI element
        if (yOffset < 0) {
            g.drawImage(Assets.genericButton[0], Handler.get().getWidth() / 2 - (textWidth / 2) - 24, yOffset, textWidth + 48, 32, null);
            Text.drawString(g, zone.toString(), Handler.get().getWidth() / 2, yOffset + 16, true, Color.YELLOW, Assets.font20);
            yOffset++;
        }
        // Wait 3 seconds, then fade out UI element
        if (yOffset == 0) {
            idleTimer++;
            g.drawImage(Assets.genericButton[0], Handler.get().getWidth() / 2 - (textWidth / 2) - 24, secondYOffset, textWidth + 48, 32, null);
            Text.drawString(g, zone.toString(), Handler.get().getWidth() / 2, secondYOffset + 16, true, Color.YELLOW, Assets.font20);
            if (idleTimer > 180 && secondYOffset > -32) {
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
