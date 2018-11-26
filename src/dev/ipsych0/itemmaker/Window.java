package dev.ipsych0.itemmaker;

import dev.ipsych0.myrinnia.character.CharacterStats;
import dev.ipsych0.myrinnia.items.EquipSlot;
import dev.ipsych0.myrinnia.items.ItemRarity;
import dev.ipsych0.myrinnia.items.ItemRequirement;
import dev.ipsych0.myrinnia.items.ItemType;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

class Window extends JFrame {

    // Window settings
    private static final int WIDTH = 416;
    private static final int HEIGHT = 624;

    private JPanel
            namePanel = new JPanel(),
            rarityPanel = new JPanel(),
            stackablePanel = new JPanel(),
            pricePanel = new JPanel(),
            equippablePanel = new JPanel(),
            equipSlotPanel = new JPanel(),
            powerPanel = new JPanel(),
            defencePanel = new JPanel(),
            vitalityPanel = new JPanel(),
            attackSpeedPanel = new JPanel(),
            movementSpeedPanel = new JPanel(),
            itemTypePanel = new JPanel(),
            itemRequirementsPanel = new JPanel();

    private JLabel nameLabel, rarityLabel, stackableLabel, priceLabel, equippableLabel, equipSlotLabel,
            powerLabel, defenceLabel, vitalityLabel, attackSpeedLabel, movementSpeedLabel, itemTypeLabel, itemRequirementsLabel;

    private JTextField nameInput, priceInput, powerInput, defenceInput, vitalityInput, attackSpeedInput, movementSpeedInput,
            itemTypeInput, itemRequirementsInput;

    private JComboBox<String> rarityDropDown;
    private JComboBox<String> stackableDropDown;
    private JComboBox<String> equippableDropDown;
    private JComboBox<String> equipSlotDropDown;

    private String[] rarity = {"Common", "Uncommon", "Rare", "Exquisite", "Unique"};
    private String[] stackable = {"Stackable", "Un-stackable"};
    private String[] equippable = {"Equippable", "Un-equippable"};
    private String[] equipSlots = {"Helm", "Body", "Legs", "Boots", "Mainhand", "Offhand", "Earrings", "Cape", "Amulet", "Gloves", "Ring_Left", "Ring_Right"};

    private JButton createButton = new JButton("Create Item");

    Window() {
        super("Item Maker v1.0");
        init();
        addFields();
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();

        // Show the
        equippableDropDown.addActionListener(e -> {
            if (equippableDropDown.getSelectedIndex() == 0) {
                addEquipFields();
            } else {
                removeEquipFields();
            }
            pack();
        });

        createButton.addActionListener(e -> {

            // Check if our list of IDs is still valid, so we will always generate a unique ID
//            IDSerializer.validateIDs();

            if(nameInput.getText().isEmpty()){
                System.err.println("Please fill in a name.");
                return;
            }
            String name = nameInput.getText();
            ItemRarity rarity = ItemRarity.valueOf((String) rarityDropDown.getSelectedItem());
            boolean stackable = stackableDropDown.getSelectedIndex() == 0;
            int price;
            try {
                if (!priceInput.getText().isEmpty()) {
                    price = Integer.parseInt(priceInput.getText());
                } else {
                    System.err.println("Price field cannot be empty.");
                    return;
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Could not parse price value '" + priceInput.getText() + "'.");
                return;
            }
            boolean equippable = equippableDropDown.getSelectedIndex() == 0;

            EquipSlot equipSlot;
            int power, defence, vitality;
            float attackSpeed, movementSpeed;

            if (equippable) {
                if (equipSlotDropDown.getSelectedItem() == null || powerInput.getText().isEmpty() || defenceInput.getText().isEmpty() ||
                       vitalityInput.getText().isEmpty() || attackSpeedInput.getText().isEmpty() || movementSpeedInput.getText().isEmpty()) {
                    System.err.println("Please fill in -all- equipment stats.");
                    return;
                }
                equipSlot = EquipSlot.valueOf((String) equipSlotDropDown.getSelectedItem());
                power = Integer.parseInt(powerInput.getText());
                defence = Integer.parseInt(defenceInput.getText());
                vitality = Integer.parseInt(vitalityInput.getText());
                attackSpeed = Float.parseFloat(attackSpeedInput.getText());
                movementSpeed = Float.parseFloat(movementSpeedInput.getText());
            } else {
                equipSlot = EquipSlot.None;
                power = 0;
                defence = 0;
                vitality = 0;
                attackSpeed = 0;
                movementSpeed = 0;
            }

            // Try to get the ItemTypes
            ItemType[] itemTypes;
            try {
                String raw = itemTypeInput.getText();
                if (raw.length() > 0) {
                    String formatted = raw.replaceAll(" ", "");
                    String[] split = formatted.split(",");
                    itemTypes = new ItemType[split.length];
                    for (int i = 0; i < split.length; i++) {
                        String capitalized = split[i].toUpperCase();
                        try {
                            itemTypes[i] = ItemType.valueOf(capitalized);
                        } catch (IllegalArgumentException iae) {
                            System.err.println("'" + capitalized + "' is not a valid ItemType. See src/dev/ipsych0/myrinnia/items/ItemTypes.java for possible values.");
                            return;
                        }
                    }
                    if (equippable) {
                        if (equipSlot == EquipSlot.Mainhand) {
                            List<ItemType> typeList = Arrays.asList(itemTypes);
                            if (!typeList.contains(ItemType.MELEE_WEAPON) && !typeList.contains(ItemType.MAGIC_WEAPON) && !typeList.contains(ItemType.RANGED_WEAPON)) {
                                System.err.println("Mainhand weapons must have a MAGIC_WEAPON/RANGED_WEAPON/MELEE_WEAPON ItemType specified!");
                                return;
                            }
                        }
                    }
                } else {
                    if (equippable && equipSlot == EquipSlot.Mainhand) {
                        System.err.println("Mainhand weapons must have a MAGIC_WEAPON/RANGED_WEAPON/MELEE_WEAPON ItemType specified!");
                        return;
                    }
                    itemTypes = null;
                }
            } catch (Exception exc) {
                exc.printStackTrace();
                System.err.println("Could not parse one or more ItemTypes. Please check src/dev/ipsych0/myrinnia/items/ItemTypes.java for a list of possible values.");
                return;
            }

            // Try to get the ItemRequirements
            ItemRequirement[] itemRequirements;
            try {
                String raw = itemRequirementsInput.getText();
                if (raw.length() >= 2) {
                    String formatted = raw.replaceAll(" ", "");
                    String[] split = formatted.split(",");
                    if (split.length % 2 != 0) {
                        throw new IllegalArgumentException();
                    }
                    itemRequirements = new ItemRequirement[split.length / 2];
                    int index = 0;
                    for (int i = 0; i < split.length; i += 2) {
                        String stat = split[i].substring(0, 1).toUpperCase() + split[i].substring(1).toLowerCase();
                        ItemRequirement ir = new ItemRequirement(CharacterStats.valueOf(stat), Integer.parseInt(split[i + 1]));
                        itemRequirements[index++] = ir;
                    }
                } else {
                    itemRequirements = null;
                }
            } catch (IllegalArgumentException iae) {
                System.err.println("Please provide an even number of Key-Value pairs, separated by commas.");
                return;
            } catch (Exception exc) {
                exc.printStackTrace();
                System.err.println("Could not parse one or more ItemRequirements. Make sure you separate the values by commas using 'Stat,Level', like so: 'Melee,2,Ranged,5'. Please check myrinnia/character/CharacterStats.java for a list of possible values.");
                return;
            }

            boolean validated = JSONWriter.validate(name, rarity, price, stackable, equipSlot, power, defence, vitality, attackSpeed, movementSpeed, itemTypes, itemRequirements);

            if (validated) {
                try {
                    JSONWriter.write(name, rarity, price, stackable, equipSlot, power, defence, vitality, attackSpeed, movementSpeed, itemTypes, itemRequirements);
                } catch (Exception exc) {
                    exc.printStackTrace();
                    System.err.println("Failed to write to JSON file!");
                    System.exit(1);
                }
            } else {
                System.err.println("One or more fields are invalid. Please try again with the right format.");
            }

        });
    }

    /**
     * Removes the equipment related fields from the window
     */
    private void removeEquipFields() {

        remove(equipSlotPanel);
        remove(equipSlotLabel);
        remove(equipSlotDropDown);

        remove(powerPanel);
        remove(powerLabel);
        remove(powerInput);

        remove(defencePanel);
        remove(defenceLabel);
        remove(defenceInput);

        remove(vitalityPanel);
        remove(vitalityLabel);
        remove(vitalityInput);

        remove(attackSpeedPanel);
        remove(attackSpeedLabel);
        remove(attackSpeedInput);

        remove(movementSpeedPanel);
        remove(movementSpeedLabel);
        remove(movementSpeedInput);
    }

    /**
     * Adds the equipment related fields to the window
     */
    private void addEquipFields() {
        remove(createButton);

        add(equipSlotPanel);
        add(equipSlotLabel);
        add(equipSlotDropDown);

        add(powerPanel);
        add(powerLabel);
        add(powerInput);

        add(defencePanel);
        add(defenceLabel);
        add(defenceInput);

        add(vitalityPanel);
        add(vitalityLabel);
        add(vitalityInput);

        add(attackSpeedPanel);
        add(attackSpeedLabel);
        add(attackSpeedInput);

        add(movementSpeedPanel);
        add(movementSpeedLabel);
        add(movementSpeedInput);

        add(createButton);
    }

    private void addFields() {
        add(namePanel);
        add(nameLabel);
        add(nameInput);

        add(rarityPanel);
        add(rarityLabel);
        add(rarityDropDown);

        add(stackablePanel);
        add(stackableLabel);
        add(stackableDropDown);

        add(pricePanel);
        add(priceLabel);
        add(priceInput);

        add(itemTypePanel);
        add(itemTypeLabel);
        add(itemTypeInput);

        add(itemRequirementsPanel);
        add(itemRequirementsLabel);
        add(itemRequirementsInput);

        add(equippablePanel);
        add(equippableLabel);
        add(equippableDropDown);

//        add(errorPanel);
//        add(errorLabel);
//
        add(Box.createRigidArea(new Dimension(0, 4)));

        add(createButton);
    }

    private void init() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // The labels for the display text
        nameLabel = new JLabel("Item Name:", JLabel.CENTER);
        rarityLabel = new JLabel("Item Rarity:", JLabel.CENTER);
        stackableLabel = new JLabel("Is it stackable?", JLabel.CENTER);
        priceLabel = new JLabel("Buy price for shops:", JLabel.CENTER);
        equippableLabel = new JLabel("Is it equippable?:", JLabel.CENTER);
        equipSlotLabel = new JLabel("Which equipment slot does the item go in?", JLabel.CENTER);
        powerLabel = new JLabel("Power stat:", JLabel.CENTER);
        defenceLabel = new JLabel("Defence stat:", JLabel.CENTER);
        vitalityLabel = new JLabel("Vitality stat:", JLabel.CENTER);
        attackSpeedLabel = new JLabel("Attack Speed stat:", JLabel.CENTER);
        movementSpeedLabel = new JLabel("Movement Speed stat:", JLabel.CENTER);
        itemTypeLabel = new JLabel("Item Types (Comma separated)", JLabel.CENTER);
        itemRequirementsLabel = new JLabel("Requirements: (skill,level)", JLabel.CENTER);

        nameLabel.setMaximumSize(new Dimension(220, 0));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rarityLabel.setMaximumSize(new Dimension(220, 0));
        rarityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stackableLabel.setMaximumSize(new Dimension(220, 0));
        stackableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setMaximumSize(new Dimension(220, 0));
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        equippableLabel.setMaximumSize(new Dimension(220, 0));
        equippableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        equipSlotLabel.setMaximumSize(new Dimension(220, 0));
        equipSlotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        powerLabel.setMaximumSize(new Dimension(220, 0));
        powerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        defenceLabel.setMaximumSize(new Dimension(220, 0));
        defenceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        vitalityLabel.setMaximumSize(new Dimension(220, 0));
        vitalityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        attackSpeedLabel.setMaximumSize(new Dimension(220, 0));
        attackSpeedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        movementSpeedLabel.setMaximumSize(new Dimension(220, 0));
        movementSpeedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        itemTypeLabel.setMaximumSize(new Dimension(220, 0));
        itemTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        itemRequirementsLabel.setMaximumSize(new Dimension(220, 0));
        itemRequirementsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Input fields
        nameInput = new JTextField(64);
        nameInput.setMaximumSize(nameInput.getPreferredSize());
        powerInput = new JTextField(64);
        powerInput.setMaximumSize(powerInput.getPreferredSize());
        defenceInput = new JTextField(64);
        defenceInput.setMaximumSize(defenceInput.getPreferredSize());
        vitalityInput = new JTextField(64);
        vitalityInput.setMaximumSize(vitalityInput.getPreferredSize());
        attackSpeedInput = new JTextField(64);
        attackSpeedInput.setMaximumSize(attackSpeedInput.getPreferredSize());
        movementSpeedInput = new JTextField(64);
        movementSpeedInput.setMaximumSize(movementSpeedInput.getPreferredSize());
        priceInput = new JTextField(64);
        priceInput.setMaximumSize(priceInput.getPreferredSize());
        itemTypeInput = new JTextField(64);
        itemTypeInput.setMaximumSize(itemTypeInput.getPreferredSize());
        itemRequirementsInput = new JTextField(64);
        itemRequirementsInput.setMaximumSize(itemRequirementsInput.getPreferredSize());

        // Dropdown options
        rarityDropDown = new JComboBox<>(rarity);
        stackableDropDown = new JComboBox<>(stackable);
        equipSlotDropDown = new JComboBox<>(equipSlots);

        equippableDropDown = new JComboBox<>(equippable);
        equippableDropDown.setSelectedIndex(1);

        ((JLabel) rarityDropDown.getRenderer()).setMaximumSize(new Dimension(250, 0));
        ((JLabel) rarityDropDown.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel) stackableDropDown.getRenderer()).setMaximumSize(new Dimension(250, 0));
        ((JLabel) stackableDropDown.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel) equippableDropDown.getRenderer()).setMaximumSize(new Dimension(250, 0));
        ((JLabel) equippableDropDown.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel) equipSlotDropDown.getRenderer()).setMaximumSize(new Dimension(250, 0));
        ((JLabel) equipSlotDropDown.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        createButton.setMaximumSize(new Dimension(220, 0));
        createButton.setHorizontalAlignment(SwingConstants.CENTER);
    }

}
