package com.tax.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class MenuFrame extends JFrame {

    public MenuFrame() {
        setTitle("TaxVision2026 - Employee Menu");
        setSize(500, 450); // Slightly taller for better spacing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250)); // Match Login BG

        // --- 1. Header Panel ---
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 51, 102)); 
        header.setPreferredSize(new Dimension(500, 70));
        JLabel lblTitle = new JLabel("EMPLOYEE DASHBOARD");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lblTitle);
        add(header, BorderLayout.NORTH);

        // --- 2. Center Panel (The Card) ---
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(40, 50, 40, 50)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;

        // --- 3. Styled Buttons ---
        JButton btnCalc = createMenuButton("SUBMIT YOUR RETURNS");
        JButton btnLogout = createMenuButton("LOGOUT");
        
        // Custom styling for Logout to distinguish it
        btnLogout.setBackground(new Color(220, 53, 69)); // Keeping Logout red for safety

        gbc.gridy = 0; card.add(btnCalc, gbc);
        gbc.gridy = 1; card.add(btnLogout, gbc);
        
        wrapper.add(card);
        add(wrapper, BorderLayout.CENTER);

        // --- 4. Actions ---
        btnCalc.addActionListener(e -> {
            new TaxMainFrame(false).setVisible(true); // Employee mode (no search)
            dispose();
        });

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", 
                                                        "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }

    
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(240, 45));
        btn.setBackground(Color.BLACK); // Matching your requested Black style
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}