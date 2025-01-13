
package main;

import java.awt.Image;
import javax.swing.ImageIcon;


public class Background {
    Image foreground, midground, background, finish;
    int foregroundX, midgroundX, backgroundX, finishX;
    int width, height;
    int repeats;
    Background()
    {
        this.foregroundX = 0; this.midgroundX = 0; this.backgroundX = 0; this.finishX = 0;
        this.width = 1000; this.height = 587; 
        this.repeats = 4;
        this.background = new ImageIcon(getClass().getResource("/resources/buildings.png")).getImage();
        this.midground = new ImageIcon(getClass().getResource("/resources/trees.png")).getImage();
        this.foreground = new ImageIcon(getClass().getResource("/resources/ground.png")).getImage();
        this.finish = new ImageIcon(getClass().getResource("/resources/finish.png")).getImage();
        
    }
    Background(Image n, Image fo, Image m, Image b, Image fi, int w, int h, int r)
    {
        this.foregroundX = 0; this.midgroundX = 0; this.backgroundX = 0; this.finishX = 0;
        this.width = w; this.height = h;
        this.repeats = r;
        this.background = new ImageIcon(getClass().getResource("/resources/" + b)).getImage();
        this.midground = new ImageIcon(getClass().getResource("/resources/" + m)).getImage();
        this.foreground = new ImageIcon(getClass().getResource("/resources/" + fo)).getImage();
        this.finish = new ImageIcon(getClass().getResource("/resources/" + fi)).getImage();
    }
}
