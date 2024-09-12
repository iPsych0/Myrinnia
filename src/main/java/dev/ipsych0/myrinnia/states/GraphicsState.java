package dev.ipsych0.myrinnia.states;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.DropDownBox;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.ui.UIObject;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class GraphicsState extends State {

    private Rectangle overlay;
    private UIManager uiManager;
    private DropDownBox displayModeDropDown,
            resolutionDropDown,
            antiAliasingDropDown,
            renderQualityDropDown,
            textQualityDropDown;
    private static final String[] displayOptions = {"Fullscreen", "Windowed"},
            antiAliasingOptions = {"Quality", "None", "Default"},
            renderQualityOptions = {"Quality", "Speed", "Default"},
            textQualityOptions = {"Quality", "Speed", "Default"};
    public static boolean hasBeenPressed;

    private final DisplayMode[] resolutionOptions;

    public GraphicsState() {
        this.uiManager = new UIManager();

        // Request the best 4 resolutions & refresh rates
        resolutionOptions = Handler.get().getGame().getDisplay().getAvailableResolutions();
        String[] resolutions = new String[resolutionOptions.length];
        for (int i = 0; i < resolutionOptions.length; i++) {
            resolutions[i] = resolutionOptions[i].getWidth() + "x" +
                    resolutionOptions[i].getHeight() + "@" +
                    resolutionOptions[i].getRefreshRate();
        }

        overlay = new Rectangle(Handler.get().getWidth() / 2 - 320, 160, 640, 417);

        if (Handler.get().getGame().getDisplay().isFullScreen()) {
            displayModeDropDown = new DropDownBox(overlay.x + overlay.width / 4 + 16, overlay.y + 64,
                    128, 20, Arrays.asList(displayOptions));
        } else {
            displayModeDropDown = new DropDownBox(overlay.x + overlay.width / 4 + 16, overlay.y + 64,
                    128, 20, Arrays.asList(displayOptions), 1);
        }

        resolutionDropDown = new DropDownBox(overlay.x + overlay.width / 4 + 16, overlay.y + 96,
                128, 20, Arrays.asList(resolutions), 0);
        antiAliasingDropDown = new DropDownBox(overlay.x + overlay.width / 4 + 16, overlay.y + 224,
                128, 20, Arrays.asList(antiAliasingOptions), 0);
        renderQualityDropDown = new DropDownBox(overlay.x + overlay.width / 4 + 16, overlay.y + 256,
                128, 20, Arrays.asList(renderQualityOptions), 0);
        textQualityDropDown = new DropDownBox(overlay.x + overlay.width / 4 + 16, overlay.y + 288,
                128, 20, Arrays.asList(textQualityOptions), 0);

        uiManager.addObject(displayModeDropDown);
        uiManager.addObject(resolutionDropDown);
        uiManager.addObject(antiAliasingDropDown);
        uiManager.addObject(renderQualityDropDown);
        uiManager.addObject(textQualityDropDown);
    }

    @Override
    public void tick() {
        uiManager.tick();

        for (UIObject o : uiManager.getObjects()) {
            if (o instanceof DropDownBox box) {
                if (box.isItemChanged()) {
                    changeGraphics(box);
                    box.setItemChanged(false);
                }
            }
        }

    }

    @Override
    public void render(Graphics2D g) {
        Collections.reverse(uiManager.getObjects());
        uiManager.render(g);

        Text.drawString(g, "Display:", overlay.x + 8, overlay.y + 32, false, Color.YELLOW, Assets.font24);
        Text.drawString(g, "Display mode:", overlay.x + 8, overlay.y + 80, false, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Resolution:", overlay.x + 8, overlay.y + 112, false, Color.YELLOW, Assets.font20);

        Text.drawString(g, "Graphics:", overlay.x + 8, overlay.y + 192, false, Color.YELLOW, Assets.font24);

        Text.drawString(g, "Anti-aliasing:", overlay.x + 8, overlay.y + 240, false, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Render quality:", overlay.x + 8, overlay.y + 272, false, Color.YELLOW, Assets.font20);
        Text.drawString(g, "Text quality:", overlay.x + 8, overlay.y + 304, false, Color.YELLOW, Assets.font20);

        Collections.reverse(uiManager.getObjects());
    }

    private void changeGraphics(DropDownBox ddb) {
        int index = ddb.getSelectedIndex();
        if (ddb.equals(displayModeDropDown)) {
            if (index == 0) {
                Handler.get().getGame().getDisplay().setFullScreen();
            } else {
                Handler.get().getGame().getDisplay().setWindowedScreen();
            }
            Handler.get().getMouseManager().setLeftPressed(false);
        } else if (ddb.equals(resolutionDropDown)) {
            DisplayMode newMode = resolutionOptions[index];
            // Call the method to change the screen resolution
            Handler.get().getGame().getDisplay().changeResolution(newMode);
        } else if (ddb.equals(antiAliasingDropDown)) {
            if (index == 0) {
                Handler.get().getGame().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            } else if (index == 1) {
                Handler.get().getGame().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            } else {
                Handler.get().getGame().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
            }
        } else if (ddb.equals(renderQualityDropDown)) {
            if (index == 0) {
                Handler.get().getGame().setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            } else if (index == 1) {
                Handler.get().getGame().setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
            } else {
                Handler.get().getGame().setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
            }
        } else if (ddb.equals(textQualityDropDown)) {
            if (index == 0) {
                Handler.get().getGame().setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            } else if (index == 1) {
                Handler.get().getGame().setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            } else {
                Handler.get().getGame().setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
            }
        }
    }

    public DropDownBox getDisplayModeDropDown() {
        return displayModeDropDown;
    }

    public DropDownBox getResolutionDropDown() {
        return resolutionDropDown;
    }
}
