package com.tax.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SplashWindow extends JWindow {
    
    // Matching your established theme
    private final Color PRIMARY_BLUE = new Color(0, 51, 102);

    public SplashWindow() {
        // Set the size and center it on the screen
        setSize(500, 300);
        setLocationRelativeTo(null);

        // FIX: Apply the color directly to the window's absolute base layer
        getContentPane().setBackground(PRIMARY_BLUE);

        // Main container panel with a subtle border
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false); // Transparent! Lets the blue background shine through
        contentPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));

        // --- Text Container Setup ---
        // Create a transparent panel to neatly stack our labels
        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setOpaque(false); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = 0; // Row 0
        gbc.insets = new Insets(5, 5, 5, 5); 

        // --- 1. Main Title ---
        JLabel title = new JLabel("Mauritius Tax Management System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        textPanel.add(title, gbc);

        // --- 2. Subtitle / Extra Text ---
        gbc.gridy = 1; // Move down to Row 1
        JLabel subtitle = new JLabel("Welcome!");
        subtitle.setForeground(new Color(200, 200, 200)); 
        subtitle.setFont(new Font("SansSerif", Font.ITALIC, 14));
        textPanel.add(subtitle, gbc);

        // Put the grouped text panel into the dead center of the window
        contentPanel.add(textPanel, BorderLayout.CENTER);
        add(contentPanel);
        
     // --- 3. Version Number (Bottom Right) ---
        // Create the label and push the text to the right side
        JLabel versionLabel = new JLabel("Early Access v0.9.0", SwingConstants.RIGHT);
        versionLabel.setForeground(new Color(150, 150, 150)); // Subtle grey color
        versionLabel.setFont(new Font("SansSerif", Font.PLAIN, 10)); // Very small font
        
        // Add a tiny bit of padding (Top: 0, Left: 0, Bottom: 5, Right: 10) so it doesn't touch the edges
        versionLabel.setBorder(new EmptyBorder(0, 0, 5, 10)); 
        
        // Snap it to the bottom of the main window
        contentPanel.add(versionLabel, BorderLayout.SOUTH);
    }

    /**
     * Displays the splash screen for 2.5 seconds before launching the app.
     */
    public void showSplash() {
        setVisible(true);

        try {
            Thread.sleep(5000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dispose(); // Close splash screen

        // Launch the LoginFrame
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    // THIS is the only main method your application needs to start!
    public static void main(String[] args) {
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception e) {}

        SplashWindow splash = new SplashWindow();
        splash.showSplash();
    }
}