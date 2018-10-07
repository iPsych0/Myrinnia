package dev.ipsych0.abilitymaker;

import dev.ipsych0.myrinnia.abilities.AbilityType;
import dev.ipsych0.myrinnia.character.CharacterStats;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    // Window settings
    private static final int WIDTH = 416;
    private static final int HEIGHT = 592;

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
            errorPanel = new JPanel();

    private JLabel elementLabel, combatStyleLabel, nameLabel, typeLabel, selectableLabel, cooldownLabel,
                   castingLabel, overcastLabel, baseDmgLabel, descriptionLabel, errorLabel;

    private JTextField nameInput, cooldownInput, castingInput, overcastInput, baseDmgInput, descriptionInput;

    private JComboBox<String> elementDropDown;
    private JComboBox<String> combatStyleDropDown;
    private JComboBox<String> typeDropDown;
    private JComboBox<String> selectableDropDown;

    private String[] elements = {"Fire", "Air", "Earth", "Water"};
    private String[] combatStyles = {"Melee", "Ranged", "Magic"};
    private String[] types = {"AutoAttack", "StandardAbility", "HealingAbility", "EliteAbility"};
    private String[] selectables = {"True", "False"};

    private JButton createButton = new JButton("Create Ability");

    public Window(){
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
            CharacterStats element = CharacterStats.valueOf((String)elementDropDown.getSelectedItem());
            CharacterStats combatStyle = CharacterStats.valueOf((String)combatStyleDropDown.getSelectedItem());
            String name = nameInput.getText();
            AbilityType abilityType = AbilityType.valueOf((String)typeDropDown.getSelectedItem());
            boolean selectable = Boolean.valueOf((String)selectableDropDown.getSelectedItem());
            double cooldownTime = -1;
            double castingTime = -1;
            double overcastTime = -1;
            int baseDamage = -1;
            String description = descriptionInput.getText();

            // Try to parse the fields, otherwise leave them at -1 so the validation fails.
            try {
                cooldownTime = Double.parseDouble(cooldownInput.getText());
                castingTime = Double.parseDouble(castingInput.getText());
                overcastTime = Double.parseDouble(overcastInput.getText());
                baseDamage = Integer.parseInt(baseDmgInput.getText());
            }catch (Exception exc){

            }

            // Validate the input
            boolean valid = JSONWriter.validate(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, description);

            // If valid, write to JSON file
            if(valid){
                errorLabel.setText("");
                JSONWriter.write(element, combatStyle, name, abilityType, selectable, cooldownTime, castingTime, overcastTime, baseDamage, description);
            }else{
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
        elementLabel = new JLabel("Element:", SwingConstants.CENTER);
        combatStyleLabel = new JLabel("Combat Style:", SwingConstants.CENTER);
        nameLabel = new JLabel("Ability name:", SwingConstants.CENTER);
        typeLabel = new JLabel("Ability type:", SwingConstants.CENTER);
        selectableLabel = new JLabel("Selectable:", SwingConstants.CENTER);
        cooldownLabel = new JLabel("Cooldown time:", SwingConstants.CENTER);
        castingLabel = new JLabel("Casting time:", SwingConstants.CENTER);
        overcastLabel = new JLabel("Overcast time:", SwingConstants.CENTER);
        baseDmgLabel = new JLabel("Base damage:", SwingConstants.CENTER);
        descriptionLabel = new JLabel("Description:", SwingConstants.CENTER);
        errorLabel = new JLabel("",SwingConstants.CENTER);

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

        // Dropdown options
        elementDropDown = new JComboBox<>(elements);
        combatStyleDropDown = new JComboBox<>(combatStyles);
        typeDropDown = new JComboBox<>(types);

        selectableDropDown = new JComboBox<>(selectables);
        selectableDropDown.setSelectedIndex(1);

        ((JLabel)elementDropDown.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)combatStyleDropDown.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)typeDropDown.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)selectableDropDown.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

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

        add(descriptionPanel);
        add(descriptionLabel);
        add(descriptionInput);

        add(Box.createRigidArea(new Dimension(0, 6)));

        add(errorPanel);
        add(errorLabel);

        add(Box.createRigidArea(new Dimension(0, 6)));

        add(createButton);

        add(Box.createRigidArea(new Dimension(0, 12)));

    }


}
