package main;

import java.awt.Image;
import javax.swing.ImageIcon;

// Cat class extends GameObject
public class Cat extends GameObject {
    int width, height;
    int vx, vy;
    Image image;

    // Default constructor
    Cat() {
        super();
        this.width = 80;
        this.height = 75;
        this.vx = 10;
        this.vy = 5;
        this.x = 200; 
        this.y = 427;
        image = new ImageIcon(getClass().getResource("/resources/catRight.png")).getImage();
    }

    // Parameterized constructor
    Cat(int w, int h, int vex, int vey, int px, int py) {
        super(px, py);
        this.width = w;
        this.height = h;
        this.vx = vex;
        this.vy = vey;
        image = new ImageIcon(getClass().getResource("/resources/catRight.png")).getImage();
    }

    // Constructor with custom image file
    Cat(int w, int h, int px, int py, String file) {
        super(px, py);
        this.width = w;
        this.height = h;
        image = new ImageIcon(getClass().getResource("/resources/" + file)).getImage();
    }
}
