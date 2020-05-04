package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.npcs.Dialogue;
import dev.ipsych0.myrinnia.ui.custom.BookUI;

import java.awt.*;

public class CelenorShrineBook extends GenericObject {


    private static final String[] content = new String[]{
            "TODO: Add content of book."
    };

    private final BookUI bookUI;

    public CelenorShrineBook(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = false;
        attackable = false;
        isNpc = true;

        bookUI = new BookUI(BookUI.PageType.FULL_BOOK, this.name, content);
    }

    @Override
    public void tick() {
        if (bookUI.isOpen()) {
            bookUI.tick();
        }
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {
        if (bookUI.isOpen()) {
            bookUI.render(g);
        }
    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new CelenorShrineBook(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 2:
                bookUI.setOpen(true);
                speakingTurn = -1;
                break;
        }
    }
}
