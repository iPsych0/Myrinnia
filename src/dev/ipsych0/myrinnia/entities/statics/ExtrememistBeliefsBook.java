package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.npcs.Dialogue;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.ui.custom.BookUI;

import java.awt.*;

public class ExtrememistBeliefsBook extends StaticEntity {

    private static final String book1 = "Halogwyr Manifest";
    private static final String book2 = "Book of Alchemy";
    private static final String book3 = "Research notes";

    private static final String[] book1content = new String[]{
            "We are the exiled elves from the Celenor group, the Halogwyr. We gathered our loyalists and went into hiding for years after our banishment.",
            "Here, we were able to practice the darker magics without disturbance and it is finally time to unite and strike back. The Celenor elves are both conservative and religious.",
            "It would only make sense that if we intend to immobilize them, we should strike at the heart of their ethics. If we anger their spirits, they will turn against them.",
            "Join us! The time has come to settle the score!\n\nSum of row 3: 20\nSum of row 4: 14"
    };
    private static final String[] book2content = new String[]{
            "Alchemy is a forbidden magic. During our hiatus, we have studied the properties of alchemical magic and have seen some promising breakthroughs.",
            "We managed to successfully create a potion that will pollute the Aemir river. This will hopefully anger their spirits, who will hold them responsible.",
            "To recreate it, the following enumeration lists the value properties of each potion:\n\nRed potion: 1\n" +
                    "Yellow potion: 3\n" +
                    "Green potion: 5\n" +
                    "Blue potion: 7\n"
    };
    private static final String[] book3content = new String[]{
            "This hidden cabin should buy us enough time to lay low. It’s hard to remember the sequence for the potion cabinet. I should probably write it down.",
            "The first row should create a sum of 8… The second row was… 10. Now what was the third and fourth row? I should check my other notes."
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
