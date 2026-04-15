package com.tax.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import com.tax.database.DatabaseManager;

public class LoginFrame extends JFrame {
    
    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("TaxVision2026 - Secure Access");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 247, 250)); 
        setLayout(new BorderLayout());

        // --- Corporate Header ---
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 51, 102)); 
        header.setPreferredSize(new Dimension(450, 70));
        JLabel lblHeader = new JLabel("SECURE PORTAL LOGIN");
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lblHeader);
        add(header, BorderLayout.NORTH);

     // --- Centered Login Card ---
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Row 0: Username Label + Field ---
        gbc.gridy = 0; 
        
        gbc.gridx = 0; // Column 0
        gbc.weightx = 0.3; // Give label less space
        card.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1; // Column 1
        gbc.weightx = 0.7; // Give box more space
        userField = new JTextField(12);
        userField.setPreferredSize(new Dimension(150, 28));
        card.add(userField, gbc);

        // --- Row 1: Password Label + Field ---
        gbc.gridy = 1; 
        
        gbc.gridx = 0; // Column 0
        gbc.weightx = 0.3;
        card.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1; // Column 1
        gbc.weightx = 0.7;
        passField = new JPasswordField(12);
        passField.setPreferredSize(new Dimension(150, 28));
        card.add(passField, gbc);

        // --- Row 2: Login Button (Spans both columns) ---
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Make the button wide enough to sit under both
        gbc.insets = new Insets(25, 0, 0, 0);

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setBackground(Color.BLACK);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(200, 40));
        btnLogin.setFocusPainted(false);
        btnLogin.setOpaque(true);
        btnLogin.setBorderPainted(false);
        
        card.add(btnLogin, gbc);

        wrapper.add(card);
        add(wrapper, BorderLayout.CENTER);

        // --- Logic: Database Validation ---
        btnLogin.addActionListener(e -> {
            DatabaseManager db = new DatabaseManager();
            String role = db.validateLogin(userField.getText(), new String(passField.getPassword()));

            if (role != null) {
                if (role.equalsIgnoreCase("admin")) {
                    new TaxMainFrame(true).setVisible(true); 
                } else {
                    new MenuFrame().setVisible(true); 
                }
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials stored in database.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}