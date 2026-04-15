package com.tax.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import com.tax.database.DatabaseManager; 
import com.tax.model.TaxRecord;
import com.tax.util.InputValidator;
import com.tax.util.FileHandler;

public class TaxMainFrame extends JFrame {
    private JTextField nameField, nicField, addressField, contactField, incomeField, medAllowanceField;
    private JTextField bankNameField, accountNumField, depCountField, depAllowanceField; 
    private JComboBox<String> empTypeBox, residentStatusBox;
    private JTextArea displayArea;
    private TaxRecord currentRecord; 
    private boolean isAdmin;

    public TaxMainFrame(boolean isAdmin) {
        this.isAdmin = isAdmin;
        setTitle("TaxVision2026 - Mauritius Tax Management");
        setSize(1100, 800); // Increased height for new fields
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 247, 250));
        setLayout(new BorderLayout(15, 15));

        // --- 1. Header ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 51, 102));
        header.setBorder(new EmptyBorder(15, 25, 15, 25));
        
        JLabel titleLabel = new JLabel(isAdmin ? "ADMIN CONTROL PANEL" : "EMPLOYEE TAX PORTAL");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        JTextField txtSearchNIC = new JTextField(12);
        JButton btnSearch = createStyledButton("SEARCH NIC", Color.WHITE);
        
        searchPanel.add(new JLabel("Search Record: ") {{ setForeground(Color.WHITE); }});
        searchPanel.add(txtSearchNIC);
        searchPanel.add(btnSearch);
        searchPanel.setVisible(isAdmin);
        
        header.add(titleLabel, BorderLayout.WEST);
        header.add(searchPanel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // --- 2. Input Form ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new CompoundBorder(
            new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Taxpayer & Banking Details"),
            new EmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.insets = new Insets(6, 8, 6, 8); // Slightly tighter insets for more fields

        // Core Profile Information
        addFormField(formPanel, "Full Name:", nameField = new JTextField(20), gbc, 0);
        addFormField(formPanel, "NIC Number:", nicField = new JTextField(20), gbc, 1);
        addFormField(formPanel, "Address:", addressField = new JTextField(20), gbc, 2); 
        addFormField(formPanel, "Contact Number:", contactField = new JTextField(20), gbc, 3);
        
        // Employment & Residency Dropdowns
        String[] empTypes = {"Full-time", "Part-time", "Contract", "Self-Employed"};
        String[] statuses = {"Resident", "Non-Resident"};
        empTypeBox = new JComboBox<>(empTypes);
        residentStatusBox = new JComboBox<>(statuses);
        
        addFormField(formPanel, "Employment Type:", empTypeBox, gbc, 4);
        addFormField(formPanel, "Resident Status:", residentStatusBox, gbc, 5);

        // Financial Data
        addFormField(formPanel, "Annual Income (Rs):", incomeField = new JTextField(20), gbc, 6);
        addFormField(formPanel, "No. of Dependents:", depCountField = new JTextField(20), gbc, 7);
        addFormField(formPanel, "Medical Relief:", medAllowanceField = new JTextField(20), gbc, 8);
        
        // Banking Data
        addFormField(formPanel, "Bank Name:", bankNameField = new JTextField(20), gbc, 9);
        addFormField(formPanel, "Account Number:", accountNumField = new JTextField(20), gbc, 10);
        
        depAllowanceField = new JTextField(20);
        depAllowanceField.setEditable(false);
        addFormField(formPanel, "Calculated Dep. Allowance:", depAllowanceField, gbc, 11);

        // --- 3. Action Buttons ---
        JButton btnCalculate = createStyledButton("CALCULATE", Color.WHITE);
        JButton btnSubmit = createStyledButton("SUBMIT RETURN", new Color(173, 216, 230)); 
        JButton btnBack = createStyledButton("BACK", new Color(255, 182, 193)); 
        
        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPnl.setOpaque(false);
        btnPnl.add(btnCalculate); 
        btnPnl.add(btnSubmit);
        btnPnl.add(btnBack);
        
        JPanel leftContainer = new JPanel(new BorderLayout());
        leftContainer.setOpaque(false);
        leftContainer.add(formPanel, BorderLayout.NORTH);
        leftContainer.add(btnPnl, BorderLayout.SOUTH);
        add(leftContainer, BorderLayout.WEST);

        // --- 4. Display Area ---
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        displayArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBorder(new TitledBorder("Live Calculation Summary"));
        add(scroll, BorderLayout.CENTER);

        // --- 5. Listeners ---
        btnCalculate.addActionListener(e -> calculateLogic());
        
        btnSubmit.addActionListener(e -> {
            if(currentRecord != null) {
                FileHandler.generateReceipt(currentRecord);
                new DatabaseManager().insertTaxReturn(currentRecord);
                JOptionPane.showMessageDialog(this, "Tax Return Submitted Successfully with Receipt Generation!");
            } else {
                JOptionPane.showMessageDialog(this, "Please run 'Calculate' before submitting.");
            }
        });

        btnSearch.addActionListener(e -> {
            TaxRecord f = new DatabaseManager().searchByNIC(txtSearchNIC.getText().trim());
            if(f != null) {
                currentRecord = f;
                nameField.setText(f.getName());
                nicField.setText(f.getNic());
                addressField.setText(f.getAddress());
                contactField.setText(f.getContactNum());
                empTypeBox.setSelectedItem(f.getEmpType());
                residentStatusBox.setSelectedItem(f.getResidentStatus());
                incomeField.setText(String.format("%.2f", f.getAnnualIncome()));
                bankNameField.setText(f.getBankName());
                accountNumField.setText(f.getAccountNumber());
                depAllowanceField.setText(String.format("%.2f", f.getDepAllowance()));
                medAllowanceField.setText(String.format("%.2f", f.getMedAllowance()));
                displayArea.setText("Record Found for: " + f.getName());
            }
        });

        btnBack.addActionListener(e -> {
            new MenuFrame().setVisible(true);
            dispose();
        });
    }

    private void calculateLogic() {
        if(!InputValidator.isNumeric(incomeField.getText(), "Annual Income")) return;
        
        try {
            double income = Double.parseDouble(incomeField.getText());
            double med = medAllowanceField.getText().trim().isEmpty() ? 0 : Double.parseDouble(medAllowanceField.getText());
            
            int numDeps = 0;
            if (!depCountField.getText().trim().isEmpty()) {
                numDeps = Integer.parseInt(depCountField.getText().trim());
            }
            
            // MRA Deduction Logic
            double calculatedDepAllowance = 0;
            if (numDeps == 1) calculatedDepAllowance = 110000;
            else if (numDeps == 2) calculatedDepAllowance = 190000;
            else if (numDeps == 3) calculatedDepAllowance = 275000;
            else if (numDeps >= 4) calculatedDepAllowance = 355000;

            double totalExceptions = calculatedDepAllowance + med;
            double taxableIncome = Math.max(0, income - totalExceptions);
            double taxPayable = taxableIncome * 0.10; // Flat 10%

            depAllowanceField.setText(String.format("%.2f", calculatedDepAllowance));

            // UI Summary Display
            displayArea.setText("");
            displayArea.append("==========================================\n");
            displayArea.append("           TAX CALCULATION SUMMARY     \n");
            displayArea.append("==========================================\n");
            displayArea.append(String.format(" Name:                    %s\n", nameField.getText()));
            displayArea.append(String.format(" Annual Income:           Rs %,.2f\n", income));
            displayArea.append(String.format(" Dependents:              %d\n", numDeps));
            displayArea.append("------------------------------------------\n");
            displayArea.append(String.format(" Dependent Allowance:   - Rs %,.2f\n", calculatedDepAllowance));
            displayArea.append(String.format(" Medical Relief:        - Rs %,.2f\n", med));
            displayArea.append(String.format(" TOTAL EXCEPTIONS:      = Rs %,.2f\n", totalExceptions));
            displayArea.append("------------------------------------------\n");
            displayArea.append(String.format(" TAXABLE INCOME:          Rs %,.2f\n", taxableIncome));
            displayArea.append("------------------------------------------\n");
            displayArea.append(String.format(" Applied Tax Rate:        10%% (Flat Rate)\n")); 
            displayArea.append(String.format(" TOTAL TAX PAYABLE:       Rs %,.2f\n", taxPayable));
            displayArea.append("==========================================\n");
            displayArea.append(" Status: Ready for Submission\n");

            // Mapping to Model for Database Persistence
            currentRecord = new TaxRecord();
            currentRecord.setName(nameField.getText());
            currentRecord.setNic(nicField.getText());
            currentRecord.setAddress(addressField.getText()); 
            currentRecord.setContactNum(contactField.getText());
            currentRecord.setEmpType((String) empTypeBox.getSelectedItem());
            currentRecord.setResidentStatus((String) residentStatusBox.getSelectedItem());
            currentRecord.setAnnualIncome(income);
            currentRecord.setDepAllowance(calculatedDepAllowance);
            currentRecord.setMedAllowance(med);
            currentRecord.setTaxableAmount(taxableIncome);
            currentRecord.setTaxPayable(taxPayable);
            currentRecord.setBankName(bankNameField.getText());
            currentRecord.setAccountNumber(accountNumField.getText());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter numeric values correctly.");
        }
    }

    
    // Overloaded addFormField for convenience
    private void addFormField(JPanel p, String l, JComponent f, GridBagConstraints g, int r) {
        g.gridy = r; g.gridx = 0; p.add(new JLabel(l), g);
        g.gridx = 1; p.add(f, g);
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.BLACK); 
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setPreferredSize(new Dimension(140, 35));
        b.setOpaque(true);
        b.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY)); 
        return b;
    }
}