package main;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

    Image backgroundImage;
    int backgroundX;
    public BackgroundPanel(String image) { 
       backgroundImage = new ImageIcon(getClass().getResource("/resources/" + image)).getImage();
    }
    //scrolling motion
    public void updateBackground(int characterX) { 
        backgroundX = -characterX % getWidth(); 
    }
    //draws the bg infinitely/until we say stop
        @Override
        public void paintComponent(Graphics g) { 
            super.paintComponent(g); 
            if (backgroundImage != null) { 
                for (int i = 0; i < getWidth() / backgroundImage.getWidth(this) + 2; i++) { 
                    g.drawImage(backgroundImage, backgroundX + i * backgroundImage.getWidth(this), 0, this); 
                } 
            } 
        }
}