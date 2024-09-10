package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;

public class SliderBar extends UIImageButton {

    private Rectangle slider;
    private int sliderXPos;
    private boolean dragging;
    private boolean changed;
    public static boolean released;

    public SliderBar(int x, int y, int width, int height, int sliderXPos) {
        super(x, y, width, height, Assets.genericButton);

        this.sliderXPos = sliderXPos;

        slider = new Rectangle(x + sliderXPos - 2, y, 4, height);
    }

    public void tick() {
        Rectangle mouse = Handler.get().getMouse();
        if (this.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
            dragging = true;
            changed = true;
            sliderXPos = mouse.x - x;
            slider.setLocation(x + (mouse.x - x), y);
        } else if (dragging && mouse.x < x && Handler.get().getMouseManager().isLeftPressed()) {
            sliderXPos = 0;
            slider.setLocation(x, y);
        } else if (dragging && mouse.x > x + width && Handler.get().getMouseManager().isLeftPressed()) {
            sliderXPos = 100;
            slider.setLocation(x + width, y);
        } else if (dragging && Handler.get().getMouseManager().isDragged()) {
            sliderXPos = mouse.x - x;
            slider.setLocation(x + (mouse.x - x), y);
        } else {
            released = true;
            dragging = false;
            changed = false;
        }
    }

    public void render(Graphics2D g) {
        g.drawImage(Assets.genericButton[1], x, y, width, height, null);
        g.setColor(Color.YELLOW);
        g.fillRect(slider.x, slider.y, slider.width, slider.height);
        Text.drawString(g, String.valueOf(sliderXPos) + "%", x + width + 32, y + height / 2, true, Color.YELLOW, Assets.font14);
    }

    public int getSliderXPos() {
        return sliderXPos;
    }

    public boolean isChanged() {
        return changed;
    }

    public boolean isDragging() {
        return dragging;
    }
}
