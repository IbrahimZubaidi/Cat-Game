package main;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Cat{
    int width, height;
    int vx, vy;
    int x, y;
    Image image;
    Cat()
    {
        this.width = 80; this.height = 75;
        this.vx = 10; this.vy = 5;
        this.x = 200; this.y = 427;
        image = new ImageIcon(getClass().getResource("/resources/catRight.png")).getImage();
    }
    Cat(int w, int h, int vex, int vey, int px, int py)
    {
        this.width = w; this.height = h;
        this.vx = px; this.vy = py;
        this.x = px; this.y = py;
    }
    Cat(int w, int h, int px, int py, String file)
    {
        this.width = w; this.height = h;
        this.vx = px; this.vy = py;
        this.x = px; this.y = py;
        image = new ImageIcon(getClass().getResource("/resources/" + file)).getImage();
    }
}
