package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.entities.creatures.Player;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.items.ItemType;
import dev.ipsych0.myrinnia.items.ui.ItemSlot;
import dev.ipsych0.myrinnia.skills.FarmingResource;
import dev.ipsych0.myrinnia.skills.SkillResource;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FarmingUI implements Serializable {

    private static final long serialVersionUID = 2366561978033275640L;
    private int x, y, width, height;
    private SkillCategory category;
    private List<FarmingSlot> farmingSlots;
    private FarmingSlot selectedButton;
    private UIImageButton exitButton;
    private UIImageButton plantButton;
    private UIManager uiManager;
    private FarmingPatch farmingPatch;
    private boolean open;
    public static boolean escapePressed;
    private List<Integer> categoryItems;
    public static boolean hasBeenPressed;


    public FarmingUI(SkillCategory category, FarmingPatch farmingPatch) {
        this.category = category;
        this.farmingPatch = farmingPatch;

        categoryItems = new ArrayList<>();
        List<SkillResource> resources = Handler.get().getSkill(SkillsList.FARMING).getListByCategory(category);
        for (SkillResource res : resources) {
            categoryItems.add(((FarmingResource) res).getSeed().getId());
        }

        farmingSlots = new ArrayList<>();
        uiManager = new UIManager();

    }

    public void tick() {
        uiManager.tick();

        Rectangle mouse = Handler.get().getMouse();

        for (FarmingSlot fs : farmingSlots) {
            if (fs.contains(mouse) && Handler.get().getMouseManager().isLeftPressed()) {
                selectedButton = fs;
            }
        }

        // Closing the window
        if (Handler.get().getKeyManager().escape && escapePressed ||
                exitButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() || Player.isMoving) {
            close();
        }

        if (selectedButton != null && plantButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            hasBeenPressed = false;
            if (!categoryItems.contains(selectedButton.getSeed().getItem().getId())) {
                Handler.get().sendMsg("You can't plant that type of seed in a " + farmingPatch.getName() + ".");
                return;
            }
            farmingPatch.plant(selectedButton.getSeed());
            close();
        }
    }

    private void close() {
        escapePressed = false;
        selectedButton = null;
        open = false;
        MouseManager.justClosedUI = true;
    }

    public void render(Graphics2D g) {
        g.drawImage(Assets.uiWindow, x, y, width, height, null);
        uiManager.render(g);

        for (FarmingSlot fs : farmingSlots) {
            if (!categoryItems.contains(fs.getSeed().getItem().getId()) || !Handler.get().playerHasSkillLevel(SkillsList.FARMING, fs.getSeed().getItem())) {
                g.setColor(Colors.insufficientAmountColor);
                g.fillRoundRect(fs.x, fs.y, fs.width, fs.height, 4, 4);
            }
        }

        if (selectedButton != null) {
            g.setColor(Colors.selectedColor);
            g.fillRoundRect(selectedButton.x, selectedButton.y, selectedButton.width, selectedButton.height, 4, 4);
        }

        Text.drawString(g, farmingPatch.getName(),
                x + width / 2, y + 16,
                true, Color.YELLOW, Assets.font14);

        Text.drawString(g, "Plant",
                plantButton.x + plantButton.width / 2, plantButton.y + plantButton.height / 2,
                true, Color.YELLOW, Assets.font14);

        Text.drawString(g, "X",
                exitButton.x + exitButton.width / 2, exitButton.y + exitButton.height / 2,
                true, Color.YELLOW, Assets.font14);
    }

    private void initSlots() {
        if (!farmingSlots.isEmpty()) {
            uiManager.removeAllObjects(farmingSlots);
        }
        this.width = 256;
        this.x = Handler.get().getWidth() / 2 - width / 2;
        this.height = 256;
        this.y = Handler.get().getHeight() / 2 - height / 2;

        // Show the list of available seeds in the UI
        farmingSlots = new ArrayList<>();
        int x = 0;
        int yOffset = 0;
        for (ItemSlot is : Handler.get().getInventory().getItemSlots()) {
            if (is.getItemStack() != null) {
                Item i = is.getItemStack().getItem();
                if (i.isType(ItemType.SEED)) {
                    farmingSlots.add(new FarmingSlot((this.x + (x++ * 32) + 16), (this.y + (yOffset * 32) + 32), is.getItemStack()));
                }
                yOffset = farmingSlots.size() / 6;
                if (x >= 6) {
                    x = 0;
                }
            }
        }

        uiManager.addAllObjects(farmingSlots);
        uiManager.removeObject(plantButton);
        uiManager.removeObject(exitButton);

        plantButton = new UIImageButton(this.x + width / 2 - 32, y + height - 48, 64, 32, Assets.genericButton);
        exitButton = new UIImageButton(this.x + width - 24, y + 8, 16, 16, Assets.genericButton);

        uiManager.addObject(plantButton);
        uiManager.addObject(exitButton);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
        if (open) {
            initSlots();
        }
    }
}
