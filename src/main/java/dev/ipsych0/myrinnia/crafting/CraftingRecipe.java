package dev.ipsych0.myrinnia.crafting;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.items.ui.ItemStack;
import dev.ipsych0.myrinnia.skills.ui.SkillCategory;
import dev.ipsych0.myrinnia.ui.Celebration;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Setter
public class CraftingRecipe implements Serializable {


    private static final long serialVersionUID = 3659085845474939235L;
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

    public void setDiscovered(boolean discovered) {
        if (!this.discovered && discovered) {
            Handler.get().sendMsg("Discovered recipe for: " + result.getItem().getName() + ".");
            Handler.get().getCelebrationUI().addEvent(new Celebration(this, "Discovered recipe:\n" + result.getItem().getName()));
        }
        this.discovered = discovered;
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
}
