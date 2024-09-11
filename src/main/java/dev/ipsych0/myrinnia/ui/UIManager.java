package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class UIManager implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2791935865770773924L;
    private List<UIObject> objects;

    public UIManager() {
        objects = new ArrayList<>();
    }

    public void tick() {
        for (UIObject o : objects) {
            o.tick();
            o.setHovering(o.getBounds().contains(Handler.get().getMouse()));
        }
    }

    public void render(Graphics2D g) {
        for (UIObject o : objects) {
            o.render(g);
        }
    }

    public void addObject(UIObject o) {
        objects.add(o);
    }

    public void addAllObjects(List<? extends UIObject> objects) {
        this.objects.addAll(objects);
    }

    public void removeObject(UIObject o) {
        objects.remove(o);
    }

    public void removeAllObjects(List<? extends UIObject> objects) {
        this.objects.removeAll(objects);
    }

}
