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
    private String customZoneName;
    private int idleTimer = 0;
    private static final int IDLE_TIME = 120;
    private static final int POPUP_HEIGHT = 48;
    private float textAlpha = 0;
    private int xOffset = -408;
    private static final AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);

    public ZoneTransitionState(Zone zone, String customZoneName) {
        this.zone = zone;
        this.customZoneName = customZoneName;
    }

    public ZoneTransitionState(Zone zone) {
        this(zone, null);
    }

    @Override
    public void tick() {
        Handler.get().getGame().gameState.tick();
        if (alpha == 0 && idleTimer >= IDLE_TIME && textAlpha == 0) {
            State.setState(Handler.get().getGame().gameState);
        }
    }

    @Override
    public void render(Graphics2D g) {
        Handler.get().getGame().gameState.render(g);

        g.setComposite(ac);

        // Get the textWidth of the Zone name
        int textWidth;
        String name;
        if (customZoneName == null) {
            textWidth = Text.getStringBounds(g, zone.getName(), Assets.font32).width;
            name = zone.getName();
        } else {
            textWidth = Text.getStringBounds(g, customZoneName, Assets.font32).width;
            name = customZoneName;
        }

        // Fade in UI element
        if (textAlpha < 255f && idleTimer == 0) {
            if (xOffset <= 0) {
                xOffset += 8;
            }
            textAlpha += 4f;
            if (textAlpha > 255f) {
                textAlpha = 255f;
            }
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (textAlpha / 255f));
            g.setComposite(composite);
            g.drawImage(Assets.genericButton[0], xOffset + Handler.get().getWidth() / 2 - (textWidth / 2) - 36, POPUP_HEIGHT * 3 + POPUP_HEIGHT / 2, textWidth + 72, POPUP_HEIGHT, null);
            Text.drawString(g, name, xOffset + Handler.get().getWidth() / 2, POPUP_HEIGHT * 4,
                    true, Color.WHITE, Assets.font40);
        }

        // Wait 2 seconds, then fade out UI element
        else if (textAlpha <= 255f || idleTimer >= 1) {
            if (idleTimer <= IDLE_TIME) {
                idleTimer++;
            } else {
                textAlpha -= 6f;
                if (textAlpha < 0f) {
                    textAlpha = 0f;
                }
                if (xOffset >= 0) {
                    xOffset += 12;
                }
            }
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (textAlpha / 255f));
            g.setComposite(composite);
            g.drawImage(Assets.genericButton[0], xOffset + Handler.get().getWidth() / 2 - (textWidth / 2) - 36, POPUP_HEIGHT * 3 + POPUP_HEIGHT / 2, textWidth + 72, POPUP_HEIGHT, null);
            Text.drawString(g, name, xOffset + Handler.get().getWidth() / 2, POPUP_HEIGHT * 4,
                    true, Color.WHITE, Assets.font40);
        }

        g.setComposite(ac);

        // Fade from black
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g.setComposite(composite);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());
        if (alpha - (0.5 / 60) < 0)
            alpha = 0;
        else
            alpha -= (0.5 / 60);

        g.dispose();
    }

}
