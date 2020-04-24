package dev.ipsych0.myrinnia.puzzles;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.gfx.Assets;
import dev.ipsych0.myrinnia.ui.UIImageButton;
import dev.ipsych0.myrinnia.ui.UIManager;
import dev.ipsych0.myrinnia.utils.OnTaskCompleted;
import dev.ipsych0.myrinnia.utils.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PotionSort extends Puzzle {

    private OnTaskCompleted task;
    private UIManager uiManager;
    private UIImageButton exitButton;
    private int x, y, width, height;
    private static final int NUM_ROWS = 4, NUM_COLS = 4;
    private List<PotionSlot> potionSlots;
    private PotionSlot selectedSlot;
    private int selectedIndex;
    private static final int NUM_RED_POTS = 5, NUM_YEL_POTS = 6, NUM_GRN_POTS = 3, NUM_BLU_POTS = 2;
    private static int START_X, START_Y;
    private static int X_OFFSET = 128;
    public static boolean hasBeenPressed;
    public static boolean isOpen;

    public PotionSort(OnTaskCompleted task) {
        this.task = task;

        createRandomList();

        // Center the UI
        width = Assets.WIDTH * 16;
        height = Assets.HEIGHT * 15;
        x = Handler.get().getWidth() / 2 - (width / 2);
        y = Handler.get().getHeight() / 2 - (height / 2);

        uiManager = new UIManager();
        exitButton = new UIImageButton(x + width - 24, y + 8, 16, 16, Assets.genericButton);
        uiManager.addObject(exitButton);

        START_X = x + 32;
        START_Y = y + 32;
    }

    @Override
    public void tick() {
        Rectangle mouse = Handler.get().getMouse();

        int index = 0;
        for (int y = 0; y < NUM_ROWS; y++) {
            for (int x = 0; x < NUM_COLS; x++) {
                Rectangle bounds = potionSlots.get(index).getBounds();

                // If we click a slot
                if (bounds.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
                    hasBeenPressed = false;
                    if (selectedSlot != null) {
                        selectedSlot.setSelected(false);
                        if (selectedSlot.equals(potionSlots.get(index))) {
                            // If we clicked on the already selected slot, deselect it
                            selectedSlot = null;
                            break;
                        } else {
                            Collections.swap(potionSlots, selectedIndex, index);
                            checkCompletion();
                            selectedSlot = null;
                            Handler.get().playEffect("misc/cabinet_potion_swap.ogg");
                        }
                    } else {
                        // If selected slot is null, select it.
                        selectedSlot = potionSlots.get(index);
                        selectedIndex = index;
                        selectedSlot.setSelected(true);
                        Handler.get().playEffect("misc/cabinet_potion_swap.ogg");
                    }
                }

                index++;
            }
        }

        uiManager.tick();

    }

    public boolean hasExited(){
        Rectangle mouse = Handler.get().getMouse();

        // Return true when exit button is pressed.
        if (Handler.get().getKeyManager().escape || exitButton.contains(mouse) && Handler.get().getMouseManager().isLeftPressed() && hasBeenPressed) {
            hasBeenPressed = false;
            isOpen = false;
            if (selectedSlot != null) {
                selectedSlot.setSelected(false);
            }
            selectedSlot = null;
            return true;
        }

        return false;
    }

    private void checkCompletion() {
        int sumRow1 = getSum(0, 3);
        int sumRow2 = getSum(4, 7);
        int sumRow3 = getSum(8, 11);
        int sumRow4 = getSum(12, 15);

        if (sumRow1 == 8 && sumRow2 == 10 && sumRow3 == 20 && sumRow4 == 14) {
            onComplete();
        }
    }

    private int getSum(int startIndex, int endIndex) {
        int sum = 0;
        for (int i = startIndex; i <= endIndex; i++) {
            sum += potionSlots.get(i).getValue();
        }
        return sum;
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.celenorPotionCabinetBg, x, y, width, height, null);

        int index = 0;
        int Y_OFFSET = 128;
        for (int y = 0; y < NUM_ROWS; y++) {
            for (int x = 0; x < NUM_COLS; x++) {
                potionSlots.get(index).render(g, START_X + (x * X_OFFSET), START_Y + (y * Y_OFFSET));
                index++;
            }
            Y_OFFSET -= (y * 2);
        }

        uiManager.render(g);
        Text.drawStringInBox(g, "X", exitButton, Color.YELLOW, Assets.font20);
    }

    @Override
    public void onComplete() {
        task.onComplete();
    }

    private void createRandomList() {
        potionSlots = new ArrayList<>(16);

        // Add potions
        addPotions(NUM_RED_POTS, Color.RED, 1);
        addPotions(NUM_YEL_POTS, Color.YELLOW, 3);
        addPotions(NUM_GRN_POTS, Color.GREEN, 5);
        addPotions(NUM_BLU_POTS, Color.BLUE, 7);

        Collections.shuffle(potionSlots);

    }

    private void addPotions(int amount, Color color, int value) {
        for (int i = 0; i < amount; i++) {
            potionSlots.add(new PotionSlot(color, value));
        }
    }
}
