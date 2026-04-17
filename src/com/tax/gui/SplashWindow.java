package com.tax.gui; 

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Provides a professional animated entry point for the application.
public class SplashWindow extends JWindow {
    
    private final Color PRIMARY_BLUE = new Color(0, 51, 102);
    private JLabel dynamicTextLabel; 

    public SplashWindow() {//Setting up the Window
        setSize(500, 300);
        setLocationRelativeTo(null);
        getContentPane().setBackground(PRIMARY_BLUE);
        
        	//Main Container with Window
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false); 
        contentPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));

        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setOpaque(false); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.insets = new Insets(5, 5, 5, 5); 

        //  Main Title
        gbc.gridy = 0;
        JLabel title = new JLabel("TaxVision2026");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        textPanel.add(title, gbc);

        // The Animated Typewriter Text
        gbc.gridy = 1; 
        gbc.insets = new Insets(15, 5, 5, 5);
        // Start the label completely empty
        dynamicTextLabel = new JLabel(""); 
        dynamicTextLabel.setForeground(Color.WHITE); // Terminal Green
        dynamicTextLabel.setFont(new Font("Monospaced", Font.ITALIC, 16)); // Hacker-style font
        textPanel.add(dynamicTextLabel, gbc);

        contentPanel.add(textPanel, BorderLayout.CENTER);
        
        // Version & Credits 
        JLabel creditsLabel = new JLabel("Early Access V0.9.5 - UoM ", SwingConstants.RIGHT);
        creditsLabel.setForeground(new Color(150, 150, 150)); 
        creditsLabel.setFont(new Font("SansSerif", Font.PLAIN, 10)); 
        creditsLabel.setBorder(new EmptyBorder(0, 0, 5, 10)); 
        contentPanel.add(creditsLabel, BorderLayout.SOUTH);

        add(contentPanel);
    }

    //Executes the typewriter animation using a Swing Timer.
    public void showSplash() {
        setVisible(true);

        // The text we want to type out
        String targetText = "Modernizing the Mauritian tax vision.";
        
        // This timer fires every 50 milliseconds to add one letter at a time
        Timer typewriterTimer = new Timer(50, new ActionListener() {
            int charIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (charIndex < targetText.length()) {
                	// Update label with an increasing substring to simulate typing
                    dynamicTextLabel.setText(targetText.substring(0, charIndex + 1));
                    charIndex++;
                } else {
                    // Typing is finished! We stop the timer.
                    ((Timer) e.getSource()).stop();
                    
                    // Hold the finished text on screen for 1 seconds, then launch
                    Timer pause = new Timer(1000, ev -> {
                        dispose();
                        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
                    });
                    pause.setRepeats(false);
                    pause.start();
                }
            }
        });
        
        typewriterTimer.start();
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
     // Start the application with the Splash Screen
        new SplashWindow().showSplash();
    }
}