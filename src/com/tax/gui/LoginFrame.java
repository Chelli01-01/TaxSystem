package com.tax.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private final Color PRIMARY_BLUE = new Color(0, 51, 102); 
    private final Color BG_COLOR = new Color(245, 247, 250);

    public LoginFrame() {
        setTitle("System Login - Tax Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350); 
        setLocationRelativeTo(null); 
        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        // --- 1. Header Section ---
        JPanel header = new JPanel();
        header.setBackground(PRIMARY_BLUE);
        JLabel title = new JLabel("Admin Login");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(title);
        header.setBorder(new EmptyBorder(15, 10, 15, 10));
        add(header, BorderLayout.NORTH);

        // --- 2. Input Form ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // --- 3. Button Section ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(0, 20, 20, 20));

        JButton btnLogin = new JButton("Secure Login");
        btnLogin.setBackground(new Color(40, 167, 69)); 
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(150, 40));

        buttonPanel.add(btnLogin);
        add(buttonPanel, BorderLayout.SOUTH);

<<<<<<< HEAD
            // Simple hardcoded check for the demo
            if (username.equals("admin") && password.equals("tax2026")) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                
                // 1. Close this frame
                this.dispose(); 
                
                // 2. Navigate to MenuFrame
                new MenuFrame().setVisible(true); 
=======
        // --- Action Listener for Login ---
        btnLogin.addActionListener((ActionEvent e) -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (user.equals("admin") && pass.equals("admin123")) {
                this.dispose(); // Close login window
                SwingUtilities.invokeLater(() -> new TaxMainFrame().setVisible(true)); 
>>>>>>> branch 'master' of https://Chelli01-01@github.com/Chelli01-01/TaxSystem
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Invalid Username or Password.", 
                    "Authentication Failed", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}