package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;   
import javax.swing.JPanel;

public class Panel extends JPanel {
    private Image backgroundImage;
     int backgroundX;
    
    public Panel(String image) {
        //this.setPreferredSize(new Dimension(500, 500));
        backgroundImage = new ImageIcon(getClass().getResource("/resources/" + image)).getImage();
        this.setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

