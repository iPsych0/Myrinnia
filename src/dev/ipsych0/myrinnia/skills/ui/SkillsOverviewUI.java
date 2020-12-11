package dev.ipsych0.myrinnia.skills.ui;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.input.MouseManager;
import dev.ipsych0.myrinnia.skills.Skill;
import dev.ipsych0.myrinnia.skills.SkillsList;
import dev.ipsych0.myrinnia.ui.ScrollBar;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.ui.ViewContainer;
import dev.ipsych0.myrinnia.utils.Colors;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SkillsOverviewUI implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5483332963034314338L;
    public int x = 280;
    public int y = 180;
    private int width = 384;
    private int height = 320;
    public static boolean isOpen = false;
    private Skill selectedSkill;
    private SkillCategory selectedCategory;
    private CategoryButton selectedButton;
    public static boolean hasBeenPressed = false;
    private ViewContainer<SkillResourceSlot> view;
    private static final int maxPerScreen = 8;
    private ArrayList<CategoryButton> categories;
    private Rectangle bounds;
    private UIImageButton exit;
    private UIManager uiManager;
    private List<SkillResourceSlot> craftingRecipes, skillResources;

    public SkillsOverviewUI() {

        categories = new ArrayList<>();

        bounds = new Rectangle(x, y, width, height);
        view = new ViewContainer.Builder<>(new Rectangle(x, y, width, height), new ArrayList<SkillResourceSlot>())
                .withOrientation(ViewContainer.VERTICAL)
                .andScrollBar(maxPerScreen, new Rectangle(x + width - 40, y + 40, 32, 256), false)
                .build();

        exit = new UIImageButton(x + width - 34, y + 10, 24, 24, Assets.genericButton);

        uiManager = new UIManager();
        uiManager.addObject(exit);
    }

    public void tick() {
        if (isOpen) {
            uiManager.tick();

            Rectangle mouse = Handler.get().getMouse();

            if (selectedSkill != null) {
                view.tick();

                for (CategoryButton cb : categories) {
                    if (selectedButton == null) {
                        selectedButton = cb;
                    }
                    if (cb.getBounds().contains(mouse)) {
                        cb.setHovering(true);
                        if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                            hasBeenPressed = false;
                            selectedCategory = cb.getCategory();
                            selectedButton = cb;
                            if (selectedSkill == Handler.get().getSkillsUI().getSkill(SkillsList.CRAFTING)) {
                                view.getScrollBar().setIndex(0);
                                view.updateContents(Handler.get().getCraftingUI().getCraftingManager().getSlotsByCategory(selectedCategory,
                                        categories.get(0).x + categories.get(0).width + 32 + 8,
                                        (y + 40)));
                            } else {
                                view.getScrollBar().setIndex(0);
                                view.updateContents(selectedSkill.getSlotsByCategory(selectedCategory,
                                        categories.get(0).x + categories.get(0).width + 32 + 8,
                                        (y + 40)));
                            }
                        }
                    } else {
                        cb.setHovering(false);
                    }

                    cb.tick();
                }
            }
        }
    }

    public void render(Graphics2D g) {
        if (isOpen) {
            g.drawImage(Assets.uiWindow, x, y, width, height, null);

            Rectangle mouse = Handler.get().getMouse();

            uiManager.render(g);

            if (selectedSkill != null) {
                Text.drawString(g, selectedSkill.toString(), x + width / 2, y + 20, true, Color.YELLOW, Assets.font20);

                if (selectedButton != null) {
                    g.setColor(Colors.selectedColor);
                    g.fillRoundRect(selectedButton.x, selectedButton.y, selectedButton.width, selectedButton.height, 4, 4);
                }

                for (CategoryButton cb : categories) {
                    cb.render(g);
                }

                if (exit.contains(mouse)) {
                    if (Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                        MouseManager.justClosedUI = true;
                        isOpen = false;
                        hasBeenPressed = false;
                        return;
                    }
                }
                Text.drawString(g, "X", exit.x + 11, exit.y + 11, true, Color.YELLOW, Assets.font20);

                view.render(g);
            }
        }
    }

    public Skill getSelectedSkill() {
        return selectedSkill;
    }

    public void setSelectedSkill(Skill selectedSkill) {
        this.selectedSkill = selectedSkill;
    }

    public ViewContainer<SkillResourceSlot> getView() {
        return view;
    }

    public void setView(ViewContainer<SkillResourceSlot> view) {
        this.view = view;
    }

    public SkillCategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(SkillCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public ArrayList<CategoryButton> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoryButton> categories) {
        this.categories = categories;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public CategoryButton getSelectedButton() {
        return selectedButton;
    }

    public void setSelectedButton(CategoryButton selectedButton) {
        this.selectedButton = selectedButton;
    }
}
