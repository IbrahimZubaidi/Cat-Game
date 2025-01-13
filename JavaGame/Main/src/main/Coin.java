package main;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Coin{
    int x, y;
    Image image;
    protected boolean collected;
    final private int value = 5;

    // Constructor
    public Coin(int x, int y) 
    { 
        super();
        this.x = x;
        this.y = y;
        this.collected= false;
        image = new ImageIcon(getClass().getResource("/resources/coin.png")).getImage();
    }
    public int getValue()
    {
        return value;
    }
}