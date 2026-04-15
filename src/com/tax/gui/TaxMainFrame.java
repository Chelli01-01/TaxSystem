package com.tax.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.tax.database.DatabaseManager; 
import com.tax.model.TaxRecord;        

public class TaxMainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    
    // Input Fields
    private JTextField nameField, nicField, addressField, contactField;
    private JTextField incomeField, depAllowanceField, medAllowanceField;
    private JComboBox<String> empTypeBox;
    private JRadioButton rbResident, rbNonResident;
    
    // Search Components
    private JTextField txtSearchNIC = new JTextField(10);
    private JButton btnSearch = new JButton("Search NIC");
    
    // Output
    private JTextArea displayArea;

    // Design Colors
    private final Color PRIMARY_BLUE = new Color(0, 51, 102);
    private final Color BG_COLOR = new Color(245, 247, 250);

    public TaxMainFrame() {
        setTitle("Mauritius Tax Management System - 2026");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout(15, 15));

        // --- 1. Header Section with Search ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_BLUE);
        header.setBorder(new EmptyBorder(15, 25, 15, 25));
        
        JLabel title = new JLabel("Individual Income Tax Calculator");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        JLabel searchLabel = new JLabel("Search NIC: ");
        searchLabel.setForeground(Color.WHITE);
        searchPanel.add(searchLabel);
        searchPanel.add(txtSearchNIC);
        searchPanel.add(btnSearch);
        
        header.add(title, BorderLayout.WEST);
        header.add(searchPanel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // --- 2. Input Form ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new CompoundBorder(
            new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Taxpayer Details"),
            new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Populate Form Fields
        addFormField(formPanel, "Full Name:", nameField = new JTextField(20), gbc, 0);
        addFormField(formPanel, "NIC Number:", nicField = new JTextField(20), gbc, 1);
        addFormField(formPanel, "Address:", addressField = new JTextField(20), gbc, 2);
        addFormField(formPanel, "Contact Num:", contactField = new JTextField(20), gbc, 3);
        addFormField(formPanel, "Annual Income (MUR):", incomeField = new JTextField(20), gbc, 4);
        addFormField(formPanel, "Dependent Allowance:", depAllowanceField = new JTextField(20), gbc, 5);
        addFormField(formPanel, "Medical Allowance:", medAllowanceField = new JTextField(20), gbc, 6);

        // Employment Type Row
        gbc.gridy = 7; gbc.gridx = 0;
        formPanel.add(new JLabel("Employment Type:"), gbc);
        gbc.gridx = 1;
        empTypeBox = new JComboBox<>(new String[]{"Full-time", "Part-time", "Contract"});
        formPanel.add(empTypeBox, gbc);

        // Residency Status Row
        gbc.gridy = 8; gbc.gridx = 0;
        formPanel.add(new JLabel("Resident Status:"), gbc);
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setOpaque(false);
        rbResident = new JRadioButton("Resident", true);
        rbNonResident = new JRadioButton("Non-Resident");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbResident); bg.add(rbNonResident);
        radioPanel.add(rbResident); radioPanel.add(rbNonResident);
        gbc.gridx = 1;
        formPanel.add(radioPanel, gbc);

        // --- 3. Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setOpaque(false);

        JButton btnCalculate = createStyledButton("Calculate Tax", new Color(40, 167, 69));
        JButton btnSave = createStyledButton("Save to DB", new Color(0, 123, 255));
        JButton btnReceipt = createStyledButton("Generate Receipt", new Color(108, 117, 125));
        JButton btnBack = createStyledButton("Back to Menu", new Color(220, 53, 69));
        
        buttonPanel.add(btnCalculate);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnReceipt);
        buttonPanel.add(btnBack);

        // Layout Containers
        JPanel leftContainer = new JPanel(new BorderLayout());
        leftContainer.setOpaque(false);
        leftContainer.add(formPanel, BorderLayout.NORTH);
        leftContainer.add(buttonPanel, BorderLayout.CENTER);
        leftContainer.setBorder(new EmptyBorder(0, 20, 0, 20));
        add(leftContainer, BorderLayout.WEST);

        // Output Area
        displayArea = new JTextArea(20, 35);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBorder(new TitledBorder("Tax Report Summary"));
        add(scroll, BorderLayout.CENTER);

        // --- Action Listeners ---

        // SEARCH
        btnSearch.addActionListener(new SearchHandler());

        // CALCULATE
        btnCalculate.addActionListener(e -> {
            calculateTaxLogic();
        });

        // SAVE
        btnSave.addActionListener(e -> {
            saveToDatabaseLogic();
        });

        // RECEIPT (Place your FileHandler call here)
        btnReceipt.addActionListener(e -> {
        	try {
                // 1. Grab data from the GUI fields
                TaxRecord record = new TaxRecord();
                record.setName(nameField.getText());
                record.setNic(nicField.getText());
                record.setAddress(addressField.getText());
                record.setContactNum(contactField.getText());
                record.setAnnualIncome(Double.parseDouble(incomeField.getText()));
                record.setDepAllowance(Double.parseDouble(depAllowanceField.getText()));
                record.setMedAllowance(Double.parseDouble(medAllowanceField.getText()));
                record.setEmpType(empTypeBox.getSelectedItem().toString());

                // 2. Perform the math
                double taxable = Math.max(0, record.getAnnualIncome() - (record.getDepAllowance() + record.getMedAllowance()));
                record.setTaxableAmount(taxable);
                record.setTaxPayable(taxable * 0.10);

                // 3. Call the FileHandler
                com.tax.util.FileHandler.generateReceipt(record);

                JOptionPane.showMessageDialog(this, "Receipt saved as: TaxReceipt_" + record.getNic() + ".txt");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error generating receipt. Ensure all fields are filled.");
            }
        });
        
        buttonPanel.add(btnBack);
    }

    private void calculateTaxLogic() {
        try {
            double income = Double.parseDouble(incomeField.getText());
            double exemptions = Double.parseDouble(depAllowanceField.getText()) + Double.parseDouble(medAllowanceField.getText());
            double taxable = Math.max(0, income - exemptions);
            double tax = taxable * 0.10;

            displayArea.setText("======== TAX CALCULATION ========\n");
            displayArea.append("Name:    " + nameField.getText() + "\n");
            displayArea.append("NIC:     " + nicField.getText() + "\n");
            displayArea.append("---------------------------------\n");
            displayArea.append("Annual Income:   MUR " + income + "\n");
            displayArea.append("Total Exemptions: MUR " + exemptions + "\n");
            displayArea.append("Taxable Amount:  MUR " + taxable + "\n");
            displayArea.append("Tax Payable (10%): MUR " + tax + "\n");
            displayArea.append("=================================");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for income and allowances.");
        }
    }

    private void saveToDatabaseLogic() {
        try {
            TaxRecord record = new TaxRecord();
            record.setName(nameField.getText());
            record.setNic(nicField.getText());
            record.setAddress(addressField.getText());
            record.setContactNum(contactField.getText());
            record.setAnnualIncome(Double.parseDouble(incomeField.getText()));
            record.setDepAllowance(Double.parseDouble(depAllowanceField.getText()));
            record.setMedAllowance(Double.parseDouble(medAllowanceField.getText()));
            record.setEmpType(empTypeBox.getSelectedItem().toString());
            record.setResidentStatus(rbResident.isSelected() ? "Resident" : "Non-Resident");
            
            // Perform math before saving
            double taxable = Math.max(0, record.getAnnualIncome() - (record.getDepAllowance() + record.getMedAllowance()));
            record.setTaxableAmount(taxable);
            record.setTaxPayable(taxable * 0.10);

            DatabaseManager db = new DatabaseManager();
            db.insertTaxReturn(record);
            JOptionPane.showMessageDialog(this, "Record saved successfully to MySQL!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving: " + ex.getMessage());
        }
    }

    private void addFormField(JPanel panel, String labelText, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridy = row; gbc.gridx = 0; gbc.weightx = 0.1;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1; gbc.weightx = 0.9;
        panel.add(field, gbc);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(150, 40));
        return btn;
    }

    private class SearchHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchNIC = txtSearchNIC.getText().trim();
            if (searchNIC.isEmpty()) return;

            DatabaseManager db = new DatabaseManager();
            TaxRecord found = db.searchByNIC(searchNIC);

            if (found != null) {
                nameField.setText(found.getName());
                nicField.setText(found.getNic());
                addressField.setText(found.getAddress());
                contactField.setText(found.getContactNum());
                incomeField.setText(String.valueOf(found.getAnnualIncome()));
                depAllowanceField.setText(String.valueOf(found.getDepAllowance()));
                medAllowanceField.setText(String.valueOf(found.getMedAllowance()));
                empTypeBox.setSelectedItem(found.getEmpType());
                
                if (found.getResidentStatus().equals("Resident")) rbResident.setSelected(true);
                else rbNonResident.setSelected(true);

                displayArea.setText("Record found for: " + found.getName());
                JOptionPane.showMessageDialog(null, "Record Loaded!");
            } else {
                JOptionPane.showMessageDialog(null, "NIC not found.");
            }
        }
    }

    
}