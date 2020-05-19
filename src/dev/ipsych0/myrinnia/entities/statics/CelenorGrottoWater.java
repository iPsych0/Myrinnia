package dev.ipsych0.myrinnia.entities.statics;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.quests.Quest;
import dev.ipsych0.myrinnia.quests.QuestList;
import dev.ipsych0.myrinnia.utils.MapLoader;
import dev.ipsych0.myrinnia.worlds.Zone;

import java.awt.*;

public class CelenorGrottoWater extends GenericObject {

    public static boolean potionUsed;
    private Quest quest = Handler.get().getQuest(QuestList.ExtrememistBeliefs);

    public CelenorGrottoWater(double x, double y, int width, int height, String name, int level, String dropTable, String jsonFile, String animation, String itemsShop) {
        super(x, y, width, height, name, level, dropTable, jsonFile, animation, itemsShop);
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
        Handler.get().getWorld().getEntityManager().addEntity(new CelenorGrottoWater(x, y, width, height, name, 1, dropTable, jsonFile, animationTag, shopItemsFile));
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
        String aemirRiverflankPath;
        String celewynnPath;
        String celenorCavesPath;
        if (Handler.isJar) {
            aemirRiverflankPath = Handler.jarFile.getParentFile().getAbsolutePath() + "/worlds/aemir_riverflank.tmx";
            celewynnPath = Handler.jarFile.getParentFile().getAbsolutePath() + "/worlds/celewynn.tmx";
            celenorCavesPath = Handler.jarFile.getParentFile().getAbsolutePath() + "/worlds/celenor_caves.tmx";
        } else {
            aemirRiverflankPath = "/worlds/aemir_riverflank.tmx".replaceFirst("/", Handler.resourcePath);
            celewynnPath = "/worlds/celewynn.tmx".replaceFirst("/", Handler.resourcePath);
            celenorCavesPath = "/worlds/celenor_caves.tmx".replaceFirst("/", Handler.resourcePath);
        }

        // Reset the water to normal state
        MapLoader.setWorldDoc(aemirRiverflankPath);
        Handler.get().getWorldHandler().getWorldsMap().get(Zone.AemirRiverflank).loadWorld();
        MapLoader.setWorldDoc(celewynnPath);
        Handler.get().getWorldHandler().getWorldsMap().get(Zone.Celewynn).loadWorld();
        MapLoader.setWorldDoc(celenorCavesPath);
        Handler.get().getWorldHandler().getWorldsMap().get(Zone.CelenorCaves).loadWorld();
    }
}
