package dev.ipsych0.droptabletool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.ipsych0.myrinnia.entities.droptables.DropTableEntry;
import dev.ipsych0.myrinnia.items.Item;
import dev.ipsych0.myrinnia.utils.Utils;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DropTableTool extends JFrame {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private List<DropTableEntry> entries = new ArrayList<>();

    // Preview UI
    private JFrame previewFrame;
    private JPanel previewPanel;
    private JTextArea previewText;
    private JScrollPane scrollPane;

    // Main UI
    private JPanel mainPanel;
    private JTextField itemInput;
    private JTextField amountInput;
    private JTextField weightInput;
    private JTextField itemIDTextField;
    private JTextField amountTextField;
    private JTextField weightTextField;
    private JButton addButton;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem newFile;

    public static void main(String[] args) throws Exception {
        DropTableTool window = new DropTableTool();
    }

    public DropTableTool() throws Exception {
        setSize(new Dimension(600, 200));
        setLocationRelativeTo(null);
        add(mainPanel);
        setTitle("Drop Table Creation Tool");

        // Menu Bar
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(Color.BLACK, 1));
        fileMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        fileMenu.setFont(new Font("myFont", Font.BOLD, 14));
        newFile = new JMenuItem("New JSON file");
        save = new JMenuItem("Save JSON file");
        load = new JMenuItem("Load JSON file");
        fileMenu.add(newFile);
        fileMenu.add(save);
        fileMenu.add(load);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        setVisible(true);
        pack();

        previewFrame = new JFrame();
        previewFrame.setLocation(getX() + getWidth() - 16, getY() - 200);
        previewFrame.setSize(new Dimension(300, 600));
        previewFrame.setTitle("Drop Table Entries");
        previewFrame.setVisible(true);

        previewPanel = new JPanel();
        previewPanel.setLayout(new FlowLayout());
        previewFrame.add(previewPanel);

        previewText = new JTextArea(30, 20);
        previewText.setText("Create drop table entries on the left\nscreen. You may export the entries\nto JSON using the 'File' menu. You\ncan also load in existing drop tables\nthrough the 'File' menu.");
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(previewText);
        previewPanel.add(scrollPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        previewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

        addNumbersOnlyInput();

        checkListeners();

        previewFrame.invalidate();
        previewFrame.validate();
        previewFrame.repaint();

    }

    private void checkListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!itemInput.getText().isEmpty() && !amountInput.getText().isEmpty() && !weightInput.getText().isEmpty()) {
                    int id, amount, weight;
                    try {
                        id = Integer.parseInt(itemInput.getText());
                        amount = Integer.parseInt(amountInput.getText());
                        weight = Integer.parseInt(weightInput.getText());
                        String itemName = getItemName(id);
                        entries.add(new DropTableEntry(id, itemName, amount, weight));
                    } catch (NumberFormatException exc) {
                        System.err.println("Could not parse ID, amount or weight to number.");
                    }
                    previewText.setText(gson.toJson(entries));
                } else {
                    System.err.println("Please fill in all three fields.");
                }
            }
        });

        // Action listeners for saving/loading file from menu
        newFile.addActionListener((l) -> {
            previewText.setText("");
            entries.clear();
        });
        save.addActionListener(new SaveL());
        load.addActionListener(new OpenL());
    }

    private String getItemName(int id) {
        if(id == -1)
            return "Nothing";

        File itemsDir = new File("src/dev/ipsych0/myrinnia/items/json/");

        for (File f : itemsDir.listFiles()) {
            if (f.getName().endsWith(".json")) {
                String[] tokens = f.getName().split("_");
                String fileId = tokens[0];
                // If we match the ID
                if (fileId.equalsIgnoreCase(String.valueOf(id))) {
                    JSONItem jsonItem = loadItem(f.getName());
                    if (jsonItem != null) {
                        return jsonItem.getName();
                    }
                }
            }
        }
        return "UNDEFINED";
    }

    private JSONItem loadItem(String jsonFile) {
        jsonFile = "dev/ipsych0/myrinnia/" + "items/json/" + jsonFile.toLowerCase();
        InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream(jsonFile);
        if (inputStream == null) {
            throw new IllegalArgumentException(jsonFile + " could not be found.");
        }
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            JSONItem t = gson.fromJson(reader, JSONItem.class);
            reader.close();
            inputStream.close();
            return t;
        } catch (final Exception e) {
            e.printStackTrace();
            System.err.println("Json file could not be loaded.");
            System.exit(1);
        }
        return null;
    }

    private void addNumbersOnlyInput() {
        ((AbstractDocument) itemInput.getDocument()).setDocumentFilter(new DocumentFilter() {
            Pattern regex = Pattern.compile("^(-)?(\\d*)$");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Matcher matcher = regex.matcher(text);
                if (!matcher.matches()) {
                    return;
                }
                super.replace(fb, offset, length, text, attrs);
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                Matcher matcher = regex.matcher(string);
                if (!matcher.matches()) {
                    return;
                }
                super.insertString(fb, offset, string, attr);
            }
        });

        ((AbstractDocument) amountInput.getDocument()).setDocumentFilter(new DocumentFilter() {
            Pattern regex = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Matcher matcher = regex.matcher(text);
                if (!matcher.matches()) {
                    return;
                }
                super.replace(fb, offset, length, text, attrs);
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                Matcher matcher = regex.matcher(string);
                if (!matcher.matches()) {
                    return;
                }
                super.insertString(fb, offset, string, attr);
            }
        });

        ((AbstractDocument) weightInput.getDocument()).setDocumentFilter(new DocumentFilter() {
            Pattern regex = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Matcher matcher = regex.matcher(text);
                if (!matcher.matches()) {
                    return;
                }
                super.replace(fb, offset, length, text, attrs);
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                Matcher matcher = regex.matcher(string);
                if (!matcher.matches()) {
                    return;
                }
                super.insertString(fb, offset, string, attr);
            }
        });
    }

    private JTextField filename = new JTextField(), dir = new JTextField();

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        itemInput = new JTextField();
        mainPanel.add(itemInput, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        amountInput = new JTextField();
        mainPanel.add(amountInput, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        weightInput = new JTextField();
        mainPanel.add(weightInput, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        itemIDTextField = new JTextField();
        itemIDTextField.setEditable(false);
        itemIDTextField.setHorizontalAlignment(10);
        itemIDTextField.setText("Item ID:");
        mainPanel.add(itemIDTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        amountTextField = new JTextField();
        amountTextField.setEditable(false);
        amountTextField.setText("Amount:");
        mainPanel.add(amountTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        weightTextField = new JTextField();
        weightTextField.setEditable(false);
        weightTextField.setText("Weight:");
        mainPanel.add(weightTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addButton = new JButton();
        addButton.setEnabled(true);
        addButton.setText("Add");
        mainPanel.add(addButton, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    public class OpenL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            c.setCurrentDirectory(new File("src/dev/ipsych0/myrinnia/entities/droptables"));
            // Demonstrate "Open" dialog:
            int rVal = c.showOpenDialog(DropTableTool.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                try {
                    // Load the file in
                    String file = c.getCurrentDirectory() + "\\" + c.getSelectedFile().getName();
                    InputStream is = new FileInputStream(file);
                    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
                    String line = buf.readLine();
                    StringBuilder sb = new StringBuilder();

                    // Read the file as String
                    while (line != null) {
                        sb.append(line).append("\n");
                        line = buf.readLine();
                    }
                    String fileAsString = sb.toString();

                    // Convert JSON String to list of entries
                    java.lang.reflect.Type listType = new TypeToken<ArrayList<DropTableEntry>>() {
                    }.getType();
                    entries = new Gson().fromJson(fileAsString, listType);

                    // Update the text
                    previewText.setText(gson.toJson(entries));

                    is.close();
                    buf.close();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        }
    }

    public class SaveL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            c.setCurrentDirectory(new File("src/dev/ipsych0/myrinnia/entities/droptables"));
            // Demonstrate "Save" dialog:
            int rVal = c.showSaveDialog(DropTableTool.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                // Write the JSON file
                try (FileWriter fileWriter = new FileWriter("src/dev/ipsych0/myrinnia/entities/droptables/" + c.getSelectedFile().getName())) {
                    fileWriter.write(previewText.getText());
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }
    }
}
