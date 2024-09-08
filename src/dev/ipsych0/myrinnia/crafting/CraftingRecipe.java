package dev.ipsych0.myrinnia.crafting;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;
import dev.ipsych0.myrinnia.ui.Celebration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class CraftingRecipe implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 3659085845474939235L;
    private ItemStack item1, item2, item3, item4;
    private ItemStack result;
    private ArrayList<ItemStack> components;
    private int craftingXP;
    private int requiredLevel;
    private boolean discovered;
    private int timeToCraft;
    private SkillCategory category;

    public CraftingRecipe(ItemStack result, int requiredLevel, int craftingXP, int timeToCraft, boolean discovered, SkillCategory category, ItemStack... items) {
        if(items == null || items.length < 1 || items.length > 4)
            throw new IllegalArgumentException("A crafting recipe must consist of 1 to 4 components.");

        this.result = result;
        this.requiredLevel = requiredLevel;
        this.category = category;
        this.craftingXP = craftingXP;
        this.timeToCraft = timeToCraft;
        this.discovered = discovered;

        components = new ArrayList<>();
        components.addAll(Arrays.asList(items));
    }

    public ArrayList<ItemStack> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<ItemStack> components) {
        this.components = components;
    }

    public int getCraftingXP() {
        return craftingXP;
    }

    public void setCraftingXP(int craftingXP) {
        this.craftingXP = craftingXP;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        if (!this.discovered && discovered) {
            Handler.get().sendMsg("Discovered recipe for: " + this.getResult().getItem().getName() + ".");
            Handler.get().getCelebrationUI().addEvent(new Celebration(this, "Discovered recipe:\n" + this.getResult().getItem().getName()));
        }
        this.discovered = discovered;
    }

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("You need: ");
        for (int i = 0; i < this.components.size(); i++) {
            s.append(components.get(i).getAmount()).append("x ");
            if (i == components.size() - 1) {
                s.append(components.get(i).getItem().getName());
            } else {
                s.append(components.get(i).getItem().getName()).append(", ");
            }
        }
        return s.toString();
    }

    public SkillCategory getCategory() {
        return category;
    }

    public void setCategory(SkillCategory category) {
        this.category = category;
    }

    public int getTimeToCraft() {
        return timeToCraft;
    }

    public void setTimeToCraft(int timeToCraft) {
        this.timeToCraft = timeToCraft;
    }
}
