package dev.ipsych0.myrinnia.entities.npcs;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Creature;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemStack;
import dev.ipsych0.myrinnia.shops.ShopWindow;

public class Lorraine extends ShopKeeper {


    /**
     *
     */
    private static final long serialVersionUID = -3340636213278064668L;
    private int xSpawn = (int) getX();
    private int ySpawn = (int) getY();
    private ArrayList<ItemStack> shopItems;
    private String[] firstDialogue = {"I would like to see your shops.", "Leave."};

    public Lorraine(float x, float y) {
        super("Lorraine's General Store", x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        shopItems = new ArrayList<ItemStack>();

        shopItems.add(new ItemStack(Item.regularLogs, 5));
        shopItems.add(new ItemStack(Item.regularOre, 10));
        shopItems.add(new ItemStack(Item.testSword, 1));

        shopWindow = new ShopWindow(shopItems);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.lorraine, (int) (x - Handler.get().getGameCamera().getxOffset()),
                (int) (y - Handler.get().getGameCamera().getyOffset()), width, height, null);
//		g.setColor(Color.red);
//		g.fillRect((int) (x + bounds.x - Handler.get().getGameCamera().getxOffset()),
//				(int) (y + bounds.y - Handler.get().getGameCamera().getyOffset()), bounds.width, bounds.height);
    }

    @Override
    public void die() {

    }

    @Override
    public void interact() {
        switch (speakingTurn) {

            case 0:
                speakingTurn++;
                return;

            case 1:
                if (!ShopWindow.isOpen) {
                    chatDialogue = new ChatDialogue(firstDialogue);
                    speakingTurn++;
                    break;
                } else {
                    speakingTurn = 1;
                    break;
                }
            case 2:
                if (chatDialogue == null) {
                    speakingTurn = 1;
                    break;
                }
                if (chatDialogue.getChosenOption().getOptionID() == 0) {
                    ShopWindow.open();
                    chatDialogue = null;
                    speakingTurn = 1;
                    break;
                }
                else if (chatDialogue.getChosenOption().getOptionID() == 1) {
                    chatDialogue = null;
                    speakingTurn = 1;
                    break;
                }
        }
    }

    @Override
    public void postRender(Graphics g) {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new Lorraine(xSpawn, ySpawn));
    }

}
