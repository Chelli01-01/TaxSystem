package com.tax.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame {

    public MenuFrame() {
        // 1. Frame Setup
        setTitle("Name for our Tax System - Main Menu");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on screen
        setLayout(new BorderLayout());

        // 2. Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 51, 102)); // Professional dark blue
        JLabel lblTitle = new JLabel("MRA TAX MANAGEMENT SYSTEM");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        // 3. Center Panel (Buttons)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Padding around buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton btnCalculator = new JButton("Open Tax Calculator");
        JButton btnLogout = new JButton("Logout System");

        // Styling Buttons
        btnCalculator.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCalculator.setPreferredSize(new Dimension(200, 40));
        btnLogout.setFont(new Font("Arial", Font.PLAIN, 14));
        btnLogout.setPreferredSize(new Dimension(200, 40));

        // Adding components
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(btnCalculator, gbc);

        gbc.gridy = 1;
        buttonPanel.add(btnLogout, gbc);

        add(buttonPanel, BorderLayout.CENTER);

        // 4. Footer
        JLabel lblFooter = new JLabel("Logged in as Administrator", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Arial", Font.ITALIC, 11));
        add(lblFooter, BorderLayout.SOUTH);

        // --- BUTTON ACTIONS ---

        // Navigate to Tax Main Frame
        btnCalculator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TaxMainFrame().setVisible(true); // Open the calculator
                dispose(); // Close this menu
            }
        });

        // Logout back to Login Frame
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, 
                    "Are you sure you want to logout?", "Logout Confirmation", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    new LoginFrame().setVisible(true); // Go back to login
                    dispose(); // Close this menu
                }
            }
        });
    }
}
