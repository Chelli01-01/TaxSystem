/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


    
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaxMainFrame extends JFrame {

    // Input Fields
    private JTextField nameField, nicField;
    private JTextField incomeField, depAllowanceField, medAllowanceField;
    
    // Result Display
    private JTextArea displayArea;

    // Colors for a Professional Look
    private final Color PRIMARY_BLUE = new Color(0, 51, 102); // Professional Navy
    private final Color BG_COLOR = new Color(245, 247, 250);

    public TaxMainFrame() {
        setTitle("Mauritius Tax Management System - 2026");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout(15, 15));

        // 1. Header Section
        JPanel header = new JPanel();
        header.setBackground(PRIMARY_BLUE);
        JLabel title = new JLabel("Individual Income Tax Calculator");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.add(title);
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        // 2. Input Form (GridBagLayout)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new CompoundBorder(
            new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Taxpayer Details"),
            new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8); // Spacing between components

        // Labels and Fields
        addFormField(formPanel, "Full Name:", nameField = new JTextField(20), gbc, 0);
        addFormField(formPanel, "NIC Number:", nicField = new JTextField(20), gbc, 1);
        addFormField(formPanel, "Annual Income (MUR):", incomeField = new JTextField(20), gbc, 2);
        addFormField(formPanel, "Dependent Allowance:", depAllowanceField = new JTextField(20), gbc, 3);
        addFormField(formPanel, "Medical Allowance:", medAllowanceField = new JTextField(20), gbc, 4);

        // 3. Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton btnCalculate = createStyledButton("Calculate Tax", new Color(40, 167, 69));
        JButton btnSave = createStyledButton("Save to DB", new Color(0, 123, 255));
        JButton btnReceipt = createStyledButton("Generate Receipt", new Color(108, 117, 125));

        buttonPanel.add(btnCalculate);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnReceipt);

        // Add form and buttons to a central container
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setOpaque(false);
        centerContainer.add(formPanel, BorderLayout.NORTH);
        centerContainer.add(buttonPanel, BorderLayout.CENTER);
        centerContainer.setBorder(new EmptyBorder(0, 20, 0, 20));
        
        add(centerContainer, BorderLayout.WEST);

        // 4. Output Display Area (Right side)
        displayArea = new JTextArea(20, 30);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBorder(new TitledBorder("Calculation Summary"));
        add(scroll, BorderLayout.CENTER);

        // --- Action Listeners ---

        btnCalculate.addActionListener(e -> {
            // Logic: Create Record -> Call Logic Class -> Display
            try {
                // Here you would instantiate your logic:
                // TaxRecord record = new TaxRecord(nameField.getText(), nicField.getText(), ...);
                // double tax = TaxSystemLogic.calculate(record);
                displayArea.setText("Processing calculation for: " + nameField.getText() + "...\n");
                displayArea.append("\nTaxable Income successfully calculated.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSave.addActionListener(e -> {
            // Logic: Trigger DB Save
            JOptionPane.showMessageDialog(this, "Record saved to Database.");
        });

        btnReceipt.addActionListener(e -> {
            // Logic: Call FileHandler to write PDF/Text
            JOptionPane.showMessageDialog(this, "Receipt generated in project root.");
        });
    }

    // Helper to keep GridBagLayout clean
    private void addFormField(JPanel panel, String labelText, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.9;
        panel.add(field, gbc);
    }

    // Helper for professional looking buttons
    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(140, 40));
        return btn;
    }

    public static void main(String[] args) {
        // Set System Look and Feel
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new TaxMainFrame().setVisible(true));
    }
}