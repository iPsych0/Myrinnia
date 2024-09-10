package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.Entity;
import dev.ipsych0.myrinnia.skills.ui.BountyBoardUI;
import dev.ipsych0.myrinnia.worlds.World;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class BountyBoardShamrock extends BountyBoard {

    private static final long serialVersionUID = 4925882540927003315L;

    private BountyBoardUI bountyBoardUI;
    private static boolean hasRemovedShadyFigure;

    public BountyBoardShamrock(float x, float y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);

        bounds.x = 0;
        bounds.y = height / 2;
        bounds.height = height / 2;
        bounds.width = width;

        attackable = false;
        isNpc = true;

        bountyBoardUI = new BountyBoardUI(Zone.ShamrockTown);
        bountyBoardUI.addPanel(2, "It's mine", "My mining equipment was stolen. Please help me retrieve it.", "Hi, brave adventurer.\nLast night, some of my equipment was stolen. Could you find out whoever did this and retrieve my equipment? Someone in town might have witnessed something.\n\nI will await your return near the town square.\n\n- Edgar");
        bountyBoardUI.addPanel(3, "Heavy metal", "Under development...", "Under development...");
        bountyBoardUI.addPanel(5, "Ruling with an iron fist", "Under development...", "Under development...");
    }

    @Override
    public BountyBoardUI getBountyBoardUI() {
        return bountyBoardUI;
    }

    @Override
    public void tick() {
        if (!hasRemovedShadyFigure && bountyBoardUI.getPanels().get(0).isAccepted()) {
            hasRemovedShadyFigure = true;
            World malachiteHills = Handler.get().getWorldHandler().getWorldsMap().get(Zone.MalachiteHills);
            malachiteHills.init();
            remove(malachiteHills, "Shady Figure");
        }
    }

    private void remove(World world, String npcName) {
        for (Entity e : world.getEntityManager().getEntities()) {
            if (e.getName() != null && e.getName().equalsIgnoreCase(npcName)) {
                e.setActive(false);
                break;
            }
        }
    }

    @Override
    public void interact() {
        BountyBoardUI.isOpen = true;
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    public void die() {

    }

    @Override
    public void respawn() {

    }

    @Override
    protected void updateDialogue() {

    }
}
