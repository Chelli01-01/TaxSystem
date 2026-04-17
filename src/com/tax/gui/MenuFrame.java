package com.tax.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

//This frame manages the "Session" state by carrying the username forward.
public class MenuFrame extends JFrame {
	private String sessionUser;
	public MenuFrame(String username) {
        this.sessionUser = username;
        
        //The Window Styling
        setTitle("TaxVision2026 - Employee Menu");
        setSize(500, 450); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        //Header Panel
        JPanel header = new JPanel();
        header.setBackground(new Color(0, 51, 102)); 
        header.setPreferredSize(new Dimension(500, 70));
        JLabel lblTitle = new JLabel("EMPLOYEE DASHBOARD");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lblTitle);
        add(header, BorderLayout.NORTH);

        //Center Panel(The Card)
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
        

        //  Styling the  Buttons 
        JButton btnCalc = createMenuButton("SUBMIT YOUR RETURNS");
        JButton btnLogout = createMenuButton("LOGOUT");
        
        
        btnLogout.setBackground(new Color(220, 53, 69)); 

        gbc.gridy = 0; card.add(btnCalc, gbc);
        gbc.gridy = 1; card.add(btnLogout, gbc);
        
        wrapper.add(card);
        add(wrapper, BorderLayout.CENTER);

      
        btnCalc.addActionListener(e -> {
            // Pass the stored sessionUser to the next frame
            new TaxMainFrame(false, sessionUser).setVisible(true); 
            dispose();// Closing the menu to free up resources
        });

        btnLogout.addActionListener(e -> {
        	// Basic safety check to prevent accidental logouts
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", 
                                                        "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }

    //The helper method used to simplify the styling process
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(240, 45));
        btn.setBackground(Color.BLACK); 
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}