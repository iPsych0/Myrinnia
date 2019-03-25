package dev.ipsych0.myrinnia.worlds.data;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.abilities.AbilityManager;
import dev.ipsych0.myrinnia.abilityoverview.AbilityOverviewUI;
import dev.ipsych0.myrinnia.bank.BankUI;
import dev.ipsych0.myrinnia.character.CharacterUI;
import dev.ipsych0.myrinnia.chatwindow.ChatWindow;
import dev.ipsych0.myrinnia.crafting.ui.CraftingUI;
import dev.ipsych0.myrinnia.entities.EntityManager;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.equipment.EquipmentWindow;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.hpoverlay.HPOverlay;
import dev.ipsych0.myrinnia.items.ItemManager;
import dev.ipsych0.myrinnia.items.ui.InventoryWindow;
import dev.ipsych0.myrinnia.quests.QuestManager;
import dev.ipsych0.myrinnia.shops.AbilityShopWindow;
import dev.ipsych0.myrinnia.shops.ShopWindow;
import dev.ipsych0.myrinnia.skills.ui.SkillsUI;
import dev.ipsych0.myrinnia.tiles.Tiles;
import dev.ipsych0.myrinnia.utils.MapLoader;
import dev.ipsych0.myrinnia.utils.Text;
import dev.ipsych0.myrinnia.utils.Utils;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class World implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2377316128534163815L;

    protected int width, height;
    protected int[][][] tiles;
    protected String[] layers;
    private Color night = new Color(0, 13, 35);
    protected String worldPath;
    private boolean nightTime = false;
    private int timeChecker = 60 * 60;

    // Entities

    protected EntityManager entityManager;

    // Items

    protected ItemManager itemManager;

    protected InventoryWindow inventory;
    protected EquipmentWindow equipment;
    protected CraftingUI craftingUI;
    protected Player player;
    protected ChatWindow chatWindow;
    protected QuestManager questManager;
    protected CharacterUI characterUI;
    protected SkillsUI skillsUI;
    protected HPOverlay hpOverlay;
    protected AbilityManager abilityManager;
    protected AbilityOverviewUI abilityOverviewUI;

    // Actual code ---v

    public World(String path) {

        this.worldPath = path;

        width = MapLoader.getMapWidth(path);
        height = MapLoader.getMapHeight(path);

        loadWorld(path);

        // World-specific classes
        this.player = Handler.get().getPlayer();
        this.inventory = Handler.get().getInventory();
        this.equipment = Handler.get().getEquipment();
        this.chatWindow = Handler.get().getChatWindow();
        this.questManager = Handler.get().getQuestManager();
        this.characterUI = Handler.get().getCharacterUI();
        this.skillsUI = Handler.get().getSkillsUI();
        this.hpOverlay = Handler.get().getHpOverlay();
        this.abilityManager = Handler.get().getAbilityManager();
        this.craftingUI = Handler.get().getCraftingUI();
        this.abilityOverviewUI = Handler.get().getAbilityOverviewUI();

        // This is each world's unique manager of Entities & Items
        entityManager = new EntityManager(player);
        itemManager = new ItemManager();

    }

    public void tick() {
        itemManager.tick();
        entityManager.tick();
        inventory.tick();
        equipment.tick();
        craftingUI.tick();
        chatWindow.tick();
        questManager.tick();
        characterUI.tick();
        skillsUI.tick();
        hpOverlay.tick();
        abilityManager.tick();
        abilityOverviewUI.tick();
        if (BankUI.isOpen && player.getBankEntity() != null)
            Handler.get().getBankUI().tick();
        if (ShopWindow.isOpen && player.getShopKeeper() != null)
            player.getShopKeeper().getShopWindow().tick();
        if (AbilityShopWindow.isOpen && player.getAbilityTrainer() != null) {
            player.getAbilityTrainer().getAbilityShopWindow().tick();
        }

        // Check for night-time every minute
        if (timeChecker++ >= 60 * 60) {
            timeChecker = 0;

            // Get the current hour of day
            Calendar c = Calendar.getInstance();
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

            // Set to night time if between 8 PM and 8 AM
            nightTime = (timeOfDay >= 20 && timeOfDay < 24) || timeOfDay >= 0 && timeOfDay < 8;
        }

    }

    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());
        // Set variables for rendering only the tiles that show on screen
        int xStart = (int) Math.max(0, Handler.get().getGameCamera().getxOffset() / Tiles.TILEWIDTH);
        int xEnd = (int) Math.min(width, (Handler.get().getGameCamera().getxOffset() + Handler.get().getWidth()) / Tiles.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, Handler.get().getGameCamera().getyOffset() / Tiles.TILEHEIGHT);
        int yEnd = (int) Math.min(height, (Handler.get().getGameCamera().getyOffset() + Handler.get().getHeight()) / Tiles.TILEHEIGHT + 1);

        // Render the tiles

        List<Tiles> renderOverTiles = new ArrayList<>();
        List<Integer> xCoords = new ArrayList<>();
        List<Integer> yCoords = new ArrayList<>();
        for (int i = 0; i < layers.length; i++) {
            for (int y = yStart; y < yEnd; y++) {
                for (int x = xStart; x < xEnd; x++) {
                    Tiles t = getTile(i, x, y);
                    if (t != Tiles.tiles[0]) {
                        if(t.isPostRendered()){
                            renderOverTiles.add(t);
                            xCoords.add((int) (x * Tiles.TILEWIDTH - Handler.get().getGameCamera().getxOffset()));
                            yCoords.add((int) (y * Tiles.TILEHEIGHT - Handler.get().getGameCamera().getyOffset()));
                            continue;
                        }
                        t.render(g, (int) (x * Tiles.TILEWIDTH - Handler.get().getGameCamera().getxOffset()),
                                (int) (y * Tiles.TILEHEIGHT - Handler.get().getGameCamera().getyOffset()));
                    }
                }
            }
        }

        // Items

        itemManager.render(g);

        // Entities
        entityManager.render(g);
        for(int i = 0; i < renderOverTiles.size(); i++){
            renderOverTiles.get(i).render(g, xCoords.get(i), yCoords.get(i));
        }
        entityManager.postRender(g);

//        if(nightTime) {
//            renderNight(g);
//        }

        // Chat
        chatWindow.render(g);


        hpOverlay.render(g);

        // Inventory & Equipment
        equipment.render(g);
        inventory.render(g);


        // MiniMap
        craftingUI.render(g);

        questManager.render(g);
        characterUI.render(g);
        skillsUI.render(g);

        abilityManager.render(g);

        abilityOverviewUI.render(g);

        if (BankUI.isOpen && player.getBankEntity() != null) {
            Handler.get().getBankUI().render(g);
            Text.drawString(g, "Bank of Myrinnia", BankUI.x + (BankUI.width / 2), BankUI.y + 16, true, Color.YELLOW, Assets.font14);
        }
        if (ShopWindow.isOpen && player.getShopKeeper() != null) {
            ShopWindow sw = player.getShopKeeper().getShopWindow();
            sw.render(g);
            Text.drawString(g, player.getShopKeeper().getShopName(), sw.x + (sw.width / 2), sw.y + 16, true, Color.YELLOW, Assets.font14);
        }
        if (AbilityShopWindow.isOpen && player.getAbilityTrainer() != null) {
            player.getAbilityTrainer().getAbilityShopWindow().render(g);
        }

    }

    public Tiles getTile(int layer, int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return null;
        return Tiles.tiles[tiles[layer][x][y]];
    }

    protected boolean standingOnTile(Rectangle box) {
        return box.intersects(player.getCollisionBounds(0, 0));
    }

    protected void renderNight(Graphics g) {
        float alpha = 0.6f;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        ((Graphics2D) g).setComposite(ac);
        g.setColor(night);
        g.fillRect(0, 0, Handler.get().getWidth(), Handler.get().getHeight());
        alpha = 1.0f;
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        ((Graphics2D) g).setComposite(ac);
    }

    protected void loadWorld(String path) {
        layers = MapLoader.getMapTiles(path);
        tiles = new int[layers.length][width][height];

        for (int i = 0; i < layers.length; i++) {
            // Splits worlds files by spaces and puts them all in an array
            layers[i] = layers[i].replace("\n", "").replace("\r", "").trim();
            String[] tokens = layers[i].split(",");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Loads in the actual tiles to the tiles[][][]
                    tiles[i][x][y] = Utils.parseInt(tokens[(x + y * width)]);
                }
            }
        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public String[] getLayers() {
        return layers;
    }

    public void setLayers(String[] layers) {
        this.layers = layers;
    }

    public InventoryWindow getInventory() {
        return inventory;
    }

    public void setInventory(InventoryWindow inventory) {
        this.inventory = inventory;
    }

    public EquipmentWindow getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentWindow equipment) {
        this.equipment = equipment;
    }

    public CraftingUI getCraftingUI() {
        return craftingUI;
    }

    public void setCraftingUI(CraftingUI craftingUI) {
        this.craftingUI = craftingUI;
    }

    public ChatWindow getChatWindow() {
        return chatWindow;
    }

    public void setChatWindow(ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public void setQuestManager(QuestManager questManager) {
        this.questManager = questManager;
    }

    public String getWorldPath() {
        return worldPath;
    }

    public void setWorldPath(String worldPath) {
        this.worldPath = worldPath;
    }

}
