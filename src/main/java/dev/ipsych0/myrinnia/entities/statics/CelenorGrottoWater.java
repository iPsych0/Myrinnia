package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.utils.tiled.TmjMapLoader;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;
import java.util.Map;

public class CelenorGrottoWater extends GenericObject {

    public static boolean potionUsed;
    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);

    public CelenorGrottoWater(double x, double y, int width, int height, Map<String,String> props) {
        super(x, y, width, height, props);
        solid = false;
        attackable = false;
        isNpc = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public void postRender(Graphics2D g) {

    }

    @Override
    protected void die() {

    }

    @Override
    public void respawn() {
        Handler.get().getWorld().getEntityManager().addEntity(new CelenorGrottoWater(x, y, width, height, props));
    }

    @Override
    protected boolean choiceConditionMet(String condition) {
        if ("hasPotion".equals(condition)) {
            return Handler.get().playerHasItem(Item.potionOfDecontamination, 1) && !potionUsed;
        }
        return false;
    }

    @Override
    protected void updateDialogue() {
        switch (speakingTurn) {
            case 2:
                if (!potionUsed) {
                    potionUsed = true;
                    Handler.get().removeItem(Item.potionOfDecontamination, 1);
                    cleanse();
                }
                speakingTurn = -1;
                break;
        }
    }

    public static void cleanse() {
        // Get the right paths
        String aemirRiverflankPath = "./res/worlds/aemir_riverflank.tmj";
        String celewynnPath = "./res/worlds/celewynn.tmj";
        String celenorCavesPath = "./res/worlds/celenor_caves.tmj";

        // Reset the water to normal state
        new TmjMapLoader().setWorldDoc(aemirRiverflankPath);
        Handler.get().getWorldHandler().getWorldsMap().get(Zone.AemirRiverflank).loadWorldTiles();
        new TmjMapLoader().setWorldDoc(celewynnPath);
        Handler.get().getWorldHandler().getWorldsMap().get(Zone.Celewynn).loadWorldTiles();
        new TmjMapLoader().setWorldDoc(celenorCavesPath);
        Handler.get().getWorldHandler().getWorldsMap().get(Zone.CelenorCaves).loadWorldTiles();
    }
}
