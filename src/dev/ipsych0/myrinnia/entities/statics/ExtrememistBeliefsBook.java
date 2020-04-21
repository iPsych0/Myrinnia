package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.npcs.Dialogue;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.ui.custom.BookUI;

import java.awt.*;

public class ExtrememistBeliefsBook extends StaticEntity {

    private static final String book1 = "Book of Exhaustion";
    private static final String book2 = "Book of Depletion";
    private static final String book3 = "Book of Exploitation";

    private static final String[] book1content = new String[]{
            "This is the content on page 1. Make sure that this fits on 1 page. Otherwise split the string and continue on the next page.",
            "This is the next page. On page 2 the story continues. This is to make sure that the reading remains pleasing to the eye.",
            "This is the third page, for which we had to press the next-button. This should be enough content for now.",
            "Just to be sure, we need to fill this page as well, as the next page-flip will contain only 1 page for testing.",
            "If you see only the left-page here, it worked."
    };
    private static final String[] book2content = new String[]{
            "There is only 1 page in this book, so no back or forward button."
    };
    private static final String[] book3content = new String[]{
            "This book has 2 pages. This is the first one.",
            "And this is the second. Also here there should be no back or forward button.",
            "What about 3 pages?"
    };

    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);
    private BookUI bookUI;

    public ExtrememistBeliefsBook(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
        solid = false;
        attackable = false;
        isNpc = true;

        // Replace the {name} variable with the name of the book.
        Dialogue first = script.getDialogues().get(0);
        first.setText(first.getText().replaceFirst("\\{name\\}", this.name));

        if (name.equalsIgnoreCase(book1)) {
            bookUI = new BookUI(BookUI.PageType.FULL_BOOK, this.name, book1content);
        } else if (name.equalsIgnoreCase(book2)) {
            bookUI = new BookUI(BookUI.PageType.FULL_BOOK, this.name, book2content);
        } else if (name.equalsIgnoreCase(book3)) {
            bookUI = new BookUI(BookUI.PageType.FULL_BOOK, this.name, book3content);
        }
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
        Handler.get().getWorld().getEntityManager().addEntity(new ExtrememistBeliefsBook(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 2:
                if (name.equalsIgnoreCase(book1)) {
                    quest.addNewCheck("book1", true);
                } else if (name.equalsIgnoreCase(book2)) {
                    quest.addNewCheck("book2", true);
                } else if (name.equalsIgnoreCase(book3)) {
                    quest.addNewCheck("book3", true);
                }
                bookUI.setOpen(true);
                speakingTurn = -1;
                break;
        }
    }
}
