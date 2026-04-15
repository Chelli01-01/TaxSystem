package com.tax.gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField userField = new JTextField(15);
    private JPasswordField passField = new JPasswordField(15);
    private JButton btnLogin = new JButton("Login");

    public LoginFrame() {
        setTitle("MTMS - System Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("Staff Login", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // Username
        gbc.gridwidth = 1; gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(userField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passField, gbc);

        // Login Button
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        btnLogin.setBackground(new Color(0, 51, 102));
        btnLogin.setForeground(Color.WHITE);
        add(btnLogin, gbc);

        // --- The Transition Logic ---
        btnLogin.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            // Simple hardcoded check for the demo
            if (username.equals("admin") && password.equals("tax2026")) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                
                // 1. Close this frame
                this.dispose(); 
                
                // 2. Navigate to MenuFrame
                new MenuFrame().setVisible(true); 
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}