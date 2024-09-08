package dev.ipsych0.abilitymaker;

import dev.ipsych0.myrinnia.abilities.data.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    // Window settings
    private static final int WIDTH = 416;
    private static final int HEIGHT = 624;

    private JPanel
            elementPanel = new JPanel(),
            combatStylePanel = new JPanel(),
            namePanel = new JPanel(),
            typePanel = new JPanel(),
            selectablePanel = new JPanel(),
            cooldownPanel = new JPanel(),
            castingPanel = new JPanel(),
            overcastPanel = new JPanel(),
            baseDmgPanel = new JPanel(),
            descriptionPanel = new JPanel(),
            pricePanel = new JPanel(),
            errorPanel = new JPanel();

    private JLabel elementLabel, combatStyleLabel, nameLabel, typeLabel, selectableLabel, cooldownLabel,
            castingLabel, overcastLabel, baseDmgLabel, descriptionLabel, priceLabel, errorLabel;

    private JTextField nameInput, cooldownInput, castingInput, overcastInput, baseDmgInput, descriptionInput, priceInput;

    private JComboBox<String> elementDropDown;
    private JComboBox<String> combatStyleDropDown;
    private JComboBox<String> typeDropDown;
    private JComboBox<String> selectableDropDown;

    private String[] elements = {"Fire", "Air", "Earth", "Water"};
    private String[] combatStyles = {"Melee", "Ranged", "Magic"};
    private String[] types = {"StandardAbility", "HealingAbility", "EliteAbility"};
    private String[] selectables = {"True", "False"};

    private JButton createButton = new JButton("Create Ability");

    public Window() {
        super("Ability Maker v1.0");
        init();
        addFields();
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createButton.addActionListener(e -> {

            // Get the input fields
            CharacterStats element = CharacterStats.valueOf((String) elementDropDown.getSelectedItem());
            CharacterStats combatStyle = CharacterStats.valueOf((String) combatStyleDropDown.getSelectedItem());
            String name = nameInput.getText();
            AbilityType abilityType = AbilityType.valueOf((String) typeDropDown.getSelectedItem());
            boolean selectable = Boolean.valueOf((String) selectableDropDown.getSelectedItem());
            double cooldownTime = -1;
            double castingTime = -1;
            double overcastTime = -1;
            int baseDamage = -1;
            int price = -1;
            String description = descriptionInput.getText();
            String className = "";

            // Try to parse the fields, otherwise leave them at -1 so the validation fails.
            try {
                cooldownTime = Double.parseDouble(cooldownInput.getText());
                castingTime = Double.parseDouble(castingInput.getText());
                overcastTime = Double.parseDouble(overcastInput.getText());
                baseDamage = Integer.parseInt(baseDmgInput.getText());
                price = Integer.parseInt(priceInput.getText());
                className = name + "Ability";
            } catch (Exception exc) {

            }

            // Validate the input
            boolean valid = JSONWriter.validate(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, className, description);

            // If valid, write to JSON file
            if (valid) {
                errorLabel.setText("");
                JSONWriter.write(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, price, className, description);
            } else {
                errorLabel.setText("<html><font color='red'>Please fill in correct values.</font></html>");
            }
        });
    }

    /**
     * Initializes all the input fields
     */
    private void init() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // The labels for the display text
        elementLabel = new JLabel("Element:", JLabel.CENTER);
        combatStyleLabel = new JLabel("Combat Style:", JLabel.CENTER);
        nameLabel = new JLabel("Ability name:", JLabel.CENTER);
        typeLabel = new JLabel("Ability type:", JLabel.CENTER);
        selectableLabel = new JLabel("Selectable:", JLabel.CENTER);
        cooldownLabel = new JLabel("Cooldown time:", JLabel.CENTER);
        castingLabel = new JLabel("Casting time:", JLabel.CENTER);
        overcastLabel = new JLabel("Overcast time:", JLabel.CENTER);
        baseDmgLabel = new JLabel("Base damage:", JLabel.CENTER);
        descriptionLabel = new JLabel("Description:", JLabel.CENTER);
        priceLabel = new JLabel("Ability Point price at Ability Trainer:", JLabel.CENTER);
        errorLabel = new JLabel("", JLabel.CENTER);

        elementLabel.setMaximumSize(new Dimension(220, 0));
        elementLabel.setHorizontalAlignment(SwingConstants.CENTER);
        combatStyleLabel.setMaximumSize(new Dimension(220, 0));
        combatStyleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setMaximumSize(new Dimension(220, 0));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        typeLabel.setMaximumSize(new Dimension(220, 0));
        typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        selectableLabel.setMaximumSize(new Dimension(220, 0));
        selectableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cooldownLabel.setMaximumSize(new Dimension(220, 0));
        cooldownLabel.setHorizontalAlignment(SwingConstants.CENTER);
        castingLabel.setMaximumSize(new Dimension(220, 0));
        castingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        overcastLabel.setMaximumSize(new Dimension(220, 0));
        overcastLabel.setHorizontalAlignment(SwingConstants.CENTER);
        baseDmgLabel.setMaximumSize(new Dimension(220, 0));
        baseDmgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setMaximumSize(new Dimension(220, 0));
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setMaximumSize(new Dimension(220, 0));
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setMaximumSize(new Dimension(220, 0));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Input fields
        nameInput = new JTextField(64);
        nameInput.setMaximumSize(nameInput.getPreferredSize());
        cooldownInput = new JTextField(64);
        cooldownInput.setMaximumSize(cooldownInput.getPreferredSize());
        castingInput = new JTextField(64);
        castingInput.setMaximumSize(castingInput.getPreferredSize());
        overcastInput = new JTextField(64);
        overcastInput.setMaximumSize(overcastInput.getPreferredSize());
        baseDmgInput = new JTextField(64);
        baseDmgInput.setMaximumSize(baseDmgInput.getPreferredSize());
        descriptionInput = new JTextField(64);
        descriptionInput.setMaximumSize(descriptionInput.getPreferredSize());
        priceInput = new JTextField(64);
        priceInput.setMaximumSize(priceInput.getPreferredSize());

        // Dropdown options
        elementDropDown = new JComboBox<>(elements);
        combatStyleDropDown = new JComboBox<>(combatStyles);
        typeDropDown = new JComboBox<>(types);

        selectableDropDown = new JComboBox<>(selectables);
        selectableDropDown.setSelectedIndex(1);

        ((JLabel) elementDropDown.getRenderer()).setMaximumSize(new Dimension(250, 0));
        ((JLabel) elementDropDown.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel) combatStyleDropDown.getRenderer()).setMaximumSize(new Dimension(250, 0));
        ((JLabel) combatStyleDropDown.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel) typeDropDown.getRenderer()).setMaximumSize(new Dimension(250, 0));
        ((JLabel) typeDropDown.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel) selectableDropDown.getRenderer()).setMaximumSize(new Dimension(250, 0));
        ((JLabel) selectableDropDown.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        createButton.setMaximumSize(new Dimension(220, 0));
        createButton.setHorizontalAlignment(SwingConstants.CENTER);

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
           System.err.println("Could not create default system UI look.");
        }
    }

    /**
     * Adds all the fields to the frame
     */
    private void addFields() {
        add(elementPanel);
        add(elementLabel);
        add(elementDropDown);

        add(combatStylePanel);
        add(combatStyleLabel);
        add(combatStyleDropDown);

        add(namePanel);
        add(nameLabel);
        add(nameInput);

        add(typePanel);
        add(typeLabel);
        add(typeDropDown);

        add(selectablePanel);
        add(selectableLabel);
        add(selectableDropDown);

        add(cooldownPanel);
        add(cooldownLabel);
        add(cooldownInput);

        add(castingPanel);
        add(castingLabel);
        add(castingInput);

        add(overcastPanel);
        add(overcastLabel);
        add(overcastInput);

        add(baseDmgPanel);
        add(baseDmgLabel);
        add(baseDmgInput);

        add(pricePanel);
        add(priceLabel);
        add(priceInput);

        add(descriptionPanel);
        add(descriptionLabel);
        add(descriptionInput);

        add(Box.createRigidArea(new Dimension(0, 4)));

        add(errorPanel);
        add(errorLabel);

        add(Box.createRigidArea(new Dimension(0, 4)));

        add(createButton);

    }


}
