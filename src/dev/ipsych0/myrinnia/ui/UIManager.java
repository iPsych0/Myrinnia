package dev.ipsych0.myrinnia.ui;

import dev.ipsych0.myrinnia.Handler;

import java.awt.*;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class UIManager implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2791935865770773924L;
    private CopyOnWriteArrayList<UIObject> objects;

    public UIManager() {
        objects = new CopyOnWriteArrayList<>();
    }

    public void tick() {
        for (UIObject o : objects) {
            o.tick();
            if (o.getBounds().contains(Handler.get().getMouse())) {
                o.setHovering(true);
            } else {
                o.setHovering(false);
            }
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

    public void removeObject(UIObject o) {
        objects.remove(o);
    }

    public CopyOnWriteArrayList<UIObject> getObjects() {
        return objects;
    }

    public void setObjects(CopyOnWriteArrayList<UIObject> objects) {
        this.objects = objects;
    }
}
